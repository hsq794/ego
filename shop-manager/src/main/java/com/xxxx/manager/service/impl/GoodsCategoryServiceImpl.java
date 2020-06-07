package com.xxxx.manager.service.impl;

import com.xxxx.common.utils.JsonUtil;
import com.xxxx.manager.mapper.GoodsCategoryMapper;
import com.xxxx.manager.pojo.GoodsCategory;
import com.xxxx.manager.pojo.GoodsCategoryExample;
import com.xxxx.manager.service.GoodsCategoryService;
import com.xxxx.manager.vo.GoodsCategoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhoubin
 * @since 1.0.0
 */
@Service
public class GoodsCategoryServiceImpl implements GoodsCategoryService {

	@Autowired
	private GoodsCategoryMapper goodsCategoryMapper;
	@Autowired
	private RedisTemplate redisTemplate;
	@Value("${goods.category.list.key}")
	private String goodsCategoryListKey;

	/**
	 * 商品分类-新增分类-查询顶级分类
	 * @return
	 */
	@Override
	public List<GoodsCategory> selectCategoryTopList() {
		//创建查询对象
		GoodsCategoryExample example = new GoodsCategoryExample();
		//设置查询条件    where parent_id=0
		example.createCriteria().andParentIdEqualTo((short) 0);
		return goodsCategoryMapper.selectByExample(example);
	}

	/**
	 * 商品分类-新增分类-级联查询
	 * @param parentId
	 * @return
	 */
	@Override
	public List<GoodsCategory> selectCategoryByParentId(Short parentId) {
		//创建查询对象
		GoodsCategoryExample example = new GoodsCategoryExample();
		//设置查询条件
		example.createCriteria().andParentIdEqualTo(parentId);
		return goodsCategoryMapper.selectByExample(example);
	}

	/**
	 * 商品分类-新增分类-保存
	 * @param goodsCategory
	 * @return
	 */
	@Override
	public int categorySave(GoodsCategory goodsCategory) {
		redisTemplate.delete(redisTemplate.keys("goods:category:*"));
		return goodsCategoryMapper.insertSelective(goodsCategory);
	}


	/**
	 * 商品分类-列表
	 * @return
	 */
	@Override
	public List<GoodsCategoryVo> selectCategoryListForView() {
		// //创建查询对象
		// GoodsCategoryExample example = new GoodsCategoryExample();
		// //设置查询条件  where parent_id=0 and level=1
		// example.createCriteria().andParentIdEqualTo((short) 0).andLevelEqualTo((byte) 1);
		// //查询一级分类
		// List<GoodsCategory> gcList01 = goodsCategoryMapper.selectByExample(example);
		// //准备List<GoodsCategoryVo>对象
		// List<GoodsCategoryVo> gcvList01 = new ArrayList<>();
		// for (GoodsCategory gc01 : gcList01) {
		// 	GoodsCategoryVo gcv01 = new GoodsCategoryVo();
		// 	BeanUtils.copyProperties(gc01,gcv01);
		// 	//清空查询条件
		// 	example.clear();
		// 	//查询二级分类
		// 	example.createCriteria().andParentIdEqualTo(gc01.getId()).andLevelEqualTo((byte) 2);
		// 	List<GoodsCategory> gcList02 = goodsCategoryMapper.selectByExample(example);
		// 	List<GoodsCategoryVo> gcvList02 = new ArrayList<>();
		// 	for (GoodsCategory gc02 : gcList02) {
		// 		GoodsCategoryVo gcv02 = new GoodsCategoryVo();
		// 		BeanUtils.copyProperties(gc02,gcv02);
		// 		//清空查询条件
		// 		example.clear();
		// 		//查询三级分类
		// 		example.createCriteria().andParentIdEqualTo(gc02.getId()).andLevelEqualTo((byte) 3);
		// 		List<GoodsCategory> gcList03 = goodsCategoryMapper.selectByExample(example);
		// 		List<GoodsCategoryVo> gcvList03 = new ArrayList<>();
		// 		for (GoodsCategory gc03 : gcList03) {
		// 			GoodsCategoryVo gcv03 = new GoodsCategoryVo();
		// 			BeanUtils.copyProperties(gc03,gcv03);
		// 			gcvList03.add(gcv03);
		// 		}
		// 		//把三级分类列表设置到二级分类对象中
		// 		gcv02.setChildren(gcvList03);
		// 		gcvList02.add(gcv02);
		// 	}
		// 	//把二级分类列表设置到一级分类对象中
		// 	gcv01.setChildren(gcvList02);
		// 	gcvList01.add(gcv01);
		// }
		ValueOperations valueOperations = redisTemplate.opsForValue();
		//从redis读取数据
		String gcvListJson = (String) valueOperations.get(goodsCategoryListKey);
		//判断是否为空，不为空说明存在数据，直接返回。
		if (!StringUtils.isEmpty(gcvListJson)){
			return JsonUtil.jsonToList(gcvListJson,GoodsCategoryVo.class);
		}
		//=======================JDK8新特性==========================
		//创建查询对象
		GoodsCategoryExample example = new GoodsCategoryExample();
		//查询所有商品分类
		List<GoodsCategory> gcList = goodsCategoryMapper.selectByExample(example);
		//将List<GoodsCategory>转成List<GoodsCategoryVo>
		List<GoodsCategoryVo> gcvList = gcList.stream().map(gc -> {
			GoodsCategoryVo gcv = new GoodsCategoryVo();
			BeanUtils.copyProperties(gc, gcv);
			return gcv;
		}).collect(Collectors.toList());
		//将List<GoodsCategoryVo>转成Map<Short, List<GoodsCategoryVo>>
		//按parentId分组，key就是parentId，value就是parentId对应的List<GoodsCategoryVo>
		Map<Short, List<GoodsCategoryVo>> map =
				gcvList.stream().collect(Collectors.groupingBy(GoodsCategoryVo::getParentId));
		//循环，给children赋值
		gcvList.forEach(gcv->gcv.setChildren(map.get(gcv.getId())));
		//拦截器，将level=1的list返回，也就是等级分类
		List<GoodsCategoryVo> gcvList01 =
				gcvList.stream().filter(gcv -> 1 == gcv.getLevel()).collect(Collectors.toList());
		//=======================JDK8新特性==========================
		//将值设置到redis
		valueOperations.set(goodsCategoryListKey, JsonUtil.object2JsonStr(gcvList01));
		return gcvList01;
	}

	/**
	 * 商品-查询所有分类
	 * @return
	 */
	@Override
	public List<GoodsCategory> selectList() {
		return goodsCategoryMapper.selectByExample(new GoodsCategoryExample());
	}
}
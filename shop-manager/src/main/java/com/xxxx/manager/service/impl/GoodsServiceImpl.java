package com.xxxx.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.common.result.BaseResult;
import com.xxxx.common.utils.JsonUtil;
import com.xxxx.manager.mapper.GoodsMapper;
import com.xxxx.manager.pojo.Goods;
import com.xxxx.manager.pojo.GoodsExample;
import com.xxxx.manager.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Service
public class GoodsServiceImpl implements GoodsService {
	@Autowired
	private GoodsMapper goodsMapper;
	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 商品新增-保存
	 * @param goods
	 * @return
	 */
	@Override
	public BaseResult saveGoods(Goods goods) {
		BaseResult baseResult;
		//判断goodsId是否为空，不为空说明之前保存过，直接返回失败
		if (null!=goods.getGoodsId()){
			return BaseResult.error();
		}
		//如果有详情，先转义
		if (!StringUtils.isEmpty(goods.getGoodsContent())){
			goods.setGoodsContent(HtmlUtils.htmlEscape(goods.getGoodsContent(),"UTF-8"));
		}
		int result = goodsMapper.insertSelective(goods);
		if (0<result){
			//保存时，清空redis
			redisTemplate.delete(redisTemplate.keys("goods:pageNum*"));
			baseResult = BaseResult.success();
			baseResult.setMessage(String.valueOf(goods.getGoodsId()));
		}else {
			baseResult = BaseResult.error();
		}
		return baseResult;
	}

	/**
	 * 商品-列表-分页查询
	 * @param goods
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	@Override
	public BaseResult selectGoodsListByPage(Goods goods, Integer pageNum, Integer pageSize) {
		//构建分页对象
		PageHelper.startPage(pageNum,pageSize);
		//创建查询对象
		GoodsExample example = new GoodsExample();
		GoodsExample.Criteria criteria = example.createCriteria();
		//商品列表RedisKey
		/*
		此功能查询分为七种
			a.无条件查询
			b.分类查询
			c.品牌查询
			d.关键词查询
			e.分类+品牌查询
			f.分类+关键词查询
			g.品牌+关键词查询
			h.分类+品牌+关键词查询
		无条件查询：goods:pageNum_:pageSize_:catId_:brandId_:goodsName_
		分类查询：goods:pageNum_:pageSize_:catId_123:brandId_:goodsName_
		品牌查询：goods:pageNum_:pageSize_:catId_:brandId_123:goodsName_
		关键词查询：goods:pageNum_:pageSize_:catId_:brandId_:goodsName_华为
		分类+品牌查询：goods:pageNum_:pageSize_:catId_123:brandId_123:goodsName_
		分类+关键词查询：goods:pageNum_:pageSize_:catId_123:brandId_:goodsName_华为
		品牌+关键词查询：goods:pageNum_:pageSize_:catId_:brandId_123:goodsName_华为
		分类+品牌+关键词查询：goods:pageNum_:pageSize_:catId_123:brandId_123:goodsName_华为
		 */
		//准备rediskey
		String[] goodsKeyArr = new String[]{
				"goods:pageNum_"+pageNum+":pageSize_"+pageSize+":",
				"catId_:",
				"brandId_:",
				"goodsName_:"
		};
		//设置查询条件
		//分类
		if (null!=goods.getCatId()&&0!=goods.getCatId()){
			criteria.andCatIdEqualTo(goods.getCatId());
			goodsKeyArr[1] = "catId_"+goods.getCatId()+":";
		}
		//品牌
		if(null!=goods.getBrandId()&&0!=goods.getBrandId()){
			criteria.andBrandIdEqualTo(goods.getBrandId());
			goodsKeyArr[2] = "brandId_"+goods.getBrandId()+":";
		}
		//关键词
		if(!StringUtils.isEmpty(goods.getGoodsName())){
			criteria.andGoodsNameLike("%"+goods.getGoodsName()+"%");
			goodsKeyArr[3] = "goodsName_"+goods.getGoodsName()+":";
		}
		//拼接Redis key
		String goodsListKey = Arrays.stream(goodsKeyArr).collect(Collectors.joining());
		ValueOperations valueOperations = redisTemplate.opsForValue();
		//查询redis，如果redis存在数据直接返回
		String pageInfoGoodsJson = (String) valueOperations.get(goodsListKey);
		if(!StringUtils.isEmpty(pageInfoGoodsJson)){
			return BaseResult.success(JsonUtil.jsonStr2Object(pageInfoGoodsJson,PageInfo.class));
		}
		//============错误代码=================
		// String goodsListJson = (String) valueOperations.get(goodsListKey);
		// if(!StringUtils.isEmpty(goodsListJson)){
		// 	List<Goods> goodsList = JsonUtil.jsonToList(goodsListJson, Goods.class);
		// 	PageInfo<Goods> pageInfo = new PageInfo<>(goodsList);
		// 	return BaseResult.success(pageInfo);
		// }
		//============错误代码=================


		//查询
		List<Goods> list = goodsMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(list)){
			//将查询结果设置至分页对象
			PageInfo<Goods> pageInfo = new PageInfo<>(list);
			valueOperations.set(goodsListKey,JsonUtil.object2JsonStr(pageInfo));
			//============错误代码=================
			// valueOperations.set(goodsListKey, JsonUtil.object2JsonStr(list));
			//============错误代码=================
			return BaseResult.success(pageInfo);
		}else {
			//没有数据，将空数据缓存，设置失效时间60s
			// valueOperations.set(goodsListKey,JsonUtil.object2JsonStr(new PageInfo<>(new ArrayList<Goods>())),60, TimeUnit.SECONDS);
		}
		return BaseResult.error();
	}
}
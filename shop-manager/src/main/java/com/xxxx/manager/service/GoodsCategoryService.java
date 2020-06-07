package com.xxxx.manager.service;

import com.xxxx.manager.pojo.GoodsCategory;
import com.xxxx.manager.vo.GoodsCategoryVo;

import java.util.List;

/**
 * 商品分类接口
 *
 * @author zhoubin
 * @since 1.0.0
 */
public interface GoodsCategoryService {

	/**
	 * 商品分类-新增分类-查询顶级分类
	 * @return
	 */
	List<GoodsCategory> selectCategoryTopList();


	/**
	 * 商品分类-新增分类-级联查询
	 * @param parentId
	 * @return
	 */
	List<GoodsCategory> selectCategoryByParentId(Short parentId);

	/**
	 * 商品分类-新增分类-保存
	 * @param goodsCategory
	 * @return
	 */
	int categorySave(GoodsCategory goodsCategory);

	/**
	 * 商品分类-列表
	 * @return
	 */
	List<GoodsCategoryVo> selectCategoryListForView();

	/**
	 * 商品-查询所有分类
	 * @return
	 */
	List<GoodsCategory> selectList();
}

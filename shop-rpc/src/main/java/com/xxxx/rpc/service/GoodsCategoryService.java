package com.xxxx.rpc.service;

import com.xxxx.rpc.vo.GoodsCategoryVo;

import java.util.List;

/**
 * 商品分类接口
 *
 * @author zhoubin
 * @since 1.0.0
 */
public interface GoodsCategoryService {
	/**
	 * 商品分类-列表
	 * @return
	 */
	List<GoodsCategoryVo> selectCategoryListForView();
}

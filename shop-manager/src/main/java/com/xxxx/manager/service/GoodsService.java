package com.xxxx.manager.service;

import com.xxxx.common.result.BaseResult;
import com.xxxx.manager.pojo.Goods;

/**
 * 商品
 *
 * @author zhoubin
 * @since 1.0.0
 */
public interface GoodsService {
	/**
	 * 商品新增-保存
	 * @param goods
	 * @return
	 */
	BaseResult saveGoods(Goods goods);


	/**
	 * 商品-列表-分页查询
	 * @param goods
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	BaseResult selectGoodsListByPage(Goods goods,Integer pageNum,Integer pageSize);
}

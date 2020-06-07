package com.xxxx.rpc.service;

import com.xxxx.common.result.ShopPageInfo;
import com.xxxx.rpc.vo.GoodsVo;

/**
 * 商品服务
 *
 * @author zhoubin
 * @since 1.0.0
 */
public interface SearchService {
	/**
	 * 搜索
	 * @param searchStr
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	ShopPageInfo<GoodsVo> doSearch(String searchStr, Integer pageNum, Integer pageSize);
}

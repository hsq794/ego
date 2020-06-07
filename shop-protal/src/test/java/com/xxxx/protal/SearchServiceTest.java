package com.xxxx.protal;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xxxx.common.result.ShopPageInfo;
import com.xxxx.common.utils.JsonUtil;
import com.xxxx.rpc.service.SearchService;
import com.xxxx.rpc.vo.GoodsVo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author zhoubin
 * @since 1.0.0
 */
@SpringBootTest
public class SearchServiceTest {

	@Reference(interfaceClass = SearchService.class)
	private SearchService searchService;

	@Test
	public void testSearch(){
		ShopPageInfo<GoodsVo> shopPageInfo = searchService.doSearch("中国移动联通电信", 1, 10);
		System.out.println(JsonUtil.object2JsonStr(shopPageInfo));
	}

}
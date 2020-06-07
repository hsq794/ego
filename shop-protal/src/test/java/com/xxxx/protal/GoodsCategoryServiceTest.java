package com.xxxx.protal;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xxxx.common.utils.JsonUtil;
import com.xxxx.rpc.service.GoodsCategoryService;
import com.xxxx.rpc.vo.GoodsCategoryVo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * 商品分类
 *
 * @author zhoubin
 * @since 1.0.0
 */
@SpringBootTest
public class GoodsCategoryServiceTest {

	@Reference(interfaceClass = GoodsCategoryService.class)
	private GoodsCategoryService goodsCategoryService;


	@Test
	public void testGoodsCategoryList(){
		List<GoodsCategoryVo> list = goodsCategoryService.selectCategoryListForView();
		System.out.println(JsonUtil.object2JsonStr(list));
	}

}
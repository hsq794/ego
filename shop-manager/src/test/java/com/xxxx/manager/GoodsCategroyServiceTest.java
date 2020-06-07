package com.xxxx.manager;

import com.xxxx.manager.service.GoodsCategoryService;
import com.xxxx.manager.vo.GoodsCategoryVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * 商品分类测试
 *
 * @author zhoubin
 * @since 1.0.0
 */
@SpringBootTest
public class GoodsCategroyServiceTest {
	@Autowired
	private GoodsCategoryService goodsCategoryService;

	@Test
	public void testSelectCategoryListForView(){
		List<GoodsCategoryVo> gcvList = goodsCategoryService.selectCategoryListForView();
		System.out.println(gcvList);
	}


	@Test
	public void testHtml(){
		String html = HtmlUtils.htmlEscape("<span>测试html转义与反转义</span>");
		System.out.println(html);

		System.out.println(HtmlUtils.htmlUnescape(html));

	}

}
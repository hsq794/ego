package com.xxxx.protal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xxxx.rpc.service.GoodsCategoryService;
import com.xxxx.rpc.vo.GoodsCategoryVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商品分类
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Controller
@RequestMapping("/goodsCategory")
public class GoodsCategoryController {

	@Reference(interfaceClass = GoodsCategoryService.class)
	private GoodsCategoryService goodsCategoryService;


	/**
	 * 商品分类
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public List<GoodsCategoryVo> selectGoodsCategoryList(){
		return goodsCategoryService.selectCategoryListForView();
	}

}
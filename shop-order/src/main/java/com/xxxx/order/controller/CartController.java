package com.xxxx.order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xxxx.common.pojo.Admin;
import com.xxxx.rpc.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 购物车
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Controller
@RequestMapping("/cart")
public class CartController {

	@Reference(interfaceClass = CartService.class)
	private CartService cartService;

	/**
	 * 获取购物车数量
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCartNum")
	@ResponseBody
	public Integer getCartNum(HttpServletRequest request){
		Admin admin = (Admin) request.getSession().getAttribute("user");
		return cartService.getCartNum(admin);
	}

}
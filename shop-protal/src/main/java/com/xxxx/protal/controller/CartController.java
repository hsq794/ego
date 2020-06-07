package com.xxxx.protal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xxxx.common.pojo.Admin;
import com.xxxx.common.result.BaseResult;
import com.xxxx.rpc.pojo.CartVo;
import com.xxxx.rpc.service.CartService;
import com.xxxx.rpc.vo.CartResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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
	 * 加入购物车
	 * @param cartVo
	 * @param request
	 * @return
	 */
	@RequestMapping("/addCart")
	@ResponseBody
	public BaseResult addCart(CartVo cartVo, HttpServletRequest request){
		Admin admin = (Admin) request.getSession().getAttribute("user");
		cartVo.setAddTime(new Date());
		return cartService.addCart(cartVo,admin);
	}


	/**
	 * 获取购物车数量
	 * @param request
	 * @return
	 */
	@RequestMapping("getCartNum")
	@ResponseBody
	public Integer getCartNum(HttpServletRequest request){
		Admin admin = (Admin) request.getSession().getAttribute("user");
		return cartService.getCartNum(admin);
	}


	/**
	 * 跳转至购物车列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getCartList")
	public String getCartList(HttpServletRequest request, Model model){
		Admin admin = (Admin) request.getSession().getAttribute("user");
		CartResult cartResult = cartService.getCartList(admin);
		model.addAttribute("cartResult",null==cartResult?new CartResult():cartResult);
		return "cart/list";
	}
}
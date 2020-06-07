package com.xxxx.protal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 订单
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Controller
@RequestMapping("/order")
public class OrderController {

	/**
	 * 跳转预订单页面
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/toPreOrder")
	public String toPreOrder(HttpServletRequest request) {
		String orderUrl = (String) request.getSession().getServletContext().getAttribute("orderUrl");
		return "redirect:" + orderUrl + "order/preOrder";
	}

	/**
	 * 异步回调
	 * @param model
	 * @return
	 */
	@RequestMapping("/callback")
	public String callback(Model model){
		model.addAttribute("result","success");
		System.out.println("异步回调成功！！！！！！！！");
		return "order/callback";
	}

}
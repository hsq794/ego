package com.xxxx.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Controller
@RequestMapping("/user")
public class UserController {

	/**
	 * 跳转至前台门户退出登录接口
	 * @param request
	 * @return
	 */
	@RequestMapping("logout")
	public String logout(HttpServletRequest request){
		return "redirect:"+request.getSession().getServletContext().getAttribute("protalUrl")+"user/logout";
	}

}
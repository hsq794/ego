package com.xxxx.protal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Controller
public class PageController {

	/**
	 * 页面跳转，RestFul风格
	 * @param page
	 * @return
	 */
	@RequestMapping("/{page}")
	public String page(@PathVariable String page){
		return page;
	}


	/**
	 * 页面跳转，首页
	 * @return
	 */
	@RequestMapping("/")
	public String index(){
		return "index";
	}


	/**
	 * 精确匹配登录
	 * @param redirectUrl
	 * @param model
	 * @return
	 */
	@RequestMapping("/login")
	public String login(String redirectUrl, Model model){
		model.addAttribute("redirectUrl",redirectUrl);
		return "login";
	}

}
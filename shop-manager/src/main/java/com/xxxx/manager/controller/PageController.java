package com.xxxx.manager.controller;

import org.springframework.stereotype.Controller;
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

}
package com.xxxx.order.controller;

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


}
package com.xxxx.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 搜索
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Controller
@RequestMapping("/search")
public class SearchController {

	/**
	 * 跳转至前台门户搜索
	 * @param request
	 * @param searchStr
	 * @return
	 */
	@RequestMapping("index")
	public String index(HttpServletRequest request, String searchStr){
		try {
			//对输入内容进行编码，防止中文乱码
			searchStr = URLEncoder.encode(searchStr,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "redirect:"+request.getSession().getServletContext().getAttribute("protalUrl")+"search/index?searchStr="+searchStr;
	}
}
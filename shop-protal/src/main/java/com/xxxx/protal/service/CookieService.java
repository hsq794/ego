package com.xxxx.protal.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie
 *
 * @author zhoubin
 * @since 1.0.0
 */
public interface CookieService {
	/**
	 * 设置ticket到cookie
	 * @param request
	 * @param response
	 * @param ticket
	 * @return
	 */
	boolean setCookie(HttpServletRequest request, HttpServletResponse response, String ticket);

	/**
	 * 获取cookie
	 * @param request
	 * @return
	 */
	String getCookie(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 清除cookie
	 * @param request
	 * @param response
	 * @return
	 */
	boolean deleteCookie(HttpServletRequest request, HttpServletResponse response);
}

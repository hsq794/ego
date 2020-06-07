package com.xxxx.manager.service.impl;

import com.xxxx.common.utils.CookieUtil;
import com.xxxx.manager.service.CookieService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhoubin
 * @since 1.0.0
 */
@Service
public class CookieServiceImpl implements CookieService {

	/**
	 * 设置ticket到cookie
	 * @param request
	 * @param response
	 * @param ticket
	 * @return
	 */
	@Override
	public boolean setCookie(HttpServletRequest request, HttpServletResponse response, String ticket) {
		try {
			CookieUtil.setCookie(request,response,"userTicket",ticket);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 获取cookie
	 * @param request
	 * @return
	 */
	@Override
	public String getCookie(HttpServletRequest request,HttpServletResponse response) {
		return CookieUtil.getCookieValue(request,response,"userTicket");
	}

	/**
	 * 清除cookie
	 * @param request
	 * @param response
	 * @return
	 */
	@Override
	public boolean deleteCookie(HttpServletRequest request, HttpServletResponse response) {
		try {
			CookieUtil.deleteCookie(request,response,"userTicket");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
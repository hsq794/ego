package com.xxxx.order.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 订单全局拦截器
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Component
public class OrderCommonInterceptor implements HandlerInterceptor {

	@Value("${shop.protal.url}")
	private String shopProtalUrl;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//获取application对象
		ServletContext servletContext = request.getSession().getServletContext();
		String orderUrl = (String) servletContext.getAttribute("protalUrl");
		if (StringUtils.isEmpty(orderUrl)){
			servletContext.setAttribute("protalUrl", this.shopProtalUrl);
		}
		return true;
	}
}
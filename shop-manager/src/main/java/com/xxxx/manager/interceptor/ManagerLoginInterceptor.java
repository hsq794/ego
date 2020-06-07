package com.xxxx.manager.interceptor;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xxxx.common.pojo.Admin;
import com.xxxx.common.utils.CookieUtil;
import com.xxxx.common.utils.JsonUtil;
import com.xxxx.sso.service.SsoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * 登录拦截器
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Component
public class ManagerLoginInterceptor implements HandlerInterceptor {
	@Reference(interfaceClass = SsoService.class)
	private SsoService ssoService;
	@Autowired
	private RedisTemplate<String,String> redisTemplate;
	@Value("${user.ticket}")
	private String userTicket;


	/**
	 * 请求处理的方法之前执行
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//获取ticket
		String ticket = CookieUtil.getCookieValue(request, response, "userTicket");
		if (!StringUtils.isEmpty(ticket)){
			//如果票据存在，验证票据返回用户信息
			Admin admin = ssoService.validate(ticket);
			request.getSession().setAttribute("user",admin);
			//将用户信息添加至session中，用于页面返显
			ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
			//重新设置失效时间
			valueOperations.set(userTicket+":"+ticket, JsonUtil.object2JsonStr(admin),30, TimeUnit.MINUTES);
			return true;
		}
		//票据不存在，或者用户信息不存在，重定向到登录页面
		response.sendRedirect(request.getContextPath()+"/login");
		return false;
	}

	/**
	 * 请求处理的方法之后执行
	 * @param request
	 * @param response
	 * @param handler
	 * @param modelAndView
	 * @throws Exception
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	/**
	 * 处理方法结束后执行清理
	 * @param request
	 * @param response
	 * @param handler
	 * @param ex
	 * @throws Exception
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
	                            Exception ex) throws Exception {

	}
}
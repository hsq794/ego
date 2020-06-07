package com.xxxx.protal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xxxx.common.pojo.Admin;
import com.xxxx.common.pojo.AdminWithBLOBs;
import com.xxxx.common.result.BaseResult;
import com.xxxx.protal.service.CookieService;
import com.xxxx.protal.service.UserService;
import com.xxxx.rpc.service.SendSmsService;
import com.xxxx.sso.service.SsoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Reference(interfaceClass = SsoService.class)
	private SsoService ssoService;
	@Autowired
	private CookieService cookieService;
	@Autowired
	private UserService userService;
	@Reference(interfaceClass = SendSmsService.class)
	private SendSmsService sendSmsService;

	/**
	 * 登录
	 * @param admin
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/login")
	@ResponseBody
	public BaseResult login(Admin admin, HttpServletRequest request, HttpServletResponse response){
		//登录返回ticket
		String ticket = ssoService.login(admin);
		if (!StringUtils.isEmpty(ticket)){
			//票据不为空，存入cookie
			boolean result = cookieService.setCookie(request, response, ticket);
			//将用户信息存入session中，方便页面返显
			request.getSession().setAttribute("user",admin);
			return result?BaseResult.success():BaseResult.error();
		}
		return BaseResult.error();
	}

	/**
	 * 登出
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response){
		//获取票据
		String ticket = cookieService.getCookie(request,response);
		if (!StringUtils.isEmpty(ticket)){
			//清除redis
			ssoService.logout(ticket);
			//清除session
			request.getSession().removeAttribute("user");
			//清除cookie
			cookieService.deleteCookie(request,response);
		}
		return "login";
	}


	/**
	 * 注册
	 * @param admin
	 * @return
	 */
	@RequestMapping("/register")
	@ResponseBody
	public BaseResult register(AdminWithBLOBs admin){
		BaseResult baseResult = userService.saveUser(admin);
		//说明注册成功
		if (200==baseResult.getCode()){
			// BaseResult baseResult1 = sendSmsService.sendSms(admin.getEmail());
			// //说明短信发送成功
			// if (200==baseResult1.getCode()){
			// 	return baseResult1;
			// }
			return baseResult;
		}
		return BaseResult.error();
	}
}
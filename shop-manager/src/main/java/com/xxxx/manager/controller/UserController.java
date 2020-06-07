package com.xxxx.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xxxx.common.enums.BaseResultEnum;
import com.xxxx.common.pojo.Admin;
import com.xxxx.common.result.BaseResult;
import com.xxxx.manager.service.CookieService;
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

	/**
	 * 登录
	 * @param admin
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/login")
	@ResponseBody
	public BaseResult login(Admin admin, HttpServletRequest request, HttpServletResponse response,String verify){
		BaseResult baseResult = new BaseResult();
		//从session中获取验证码
		String capText = (String) request.getSession().getAttribute("pictureVerifyKey");
		//验证码为空或者验证码不一致
		if (StringUtils.isEmpty(verify.trim())||!verify.trim().equalsIgnoreCase(capText)){
			baseResult.setCode(BaseResultEnum.PASS_ERROR_03.getCode());
			baseResult.setMessage(BaseResultEnum.PASS_ERROR_03.getMessage());
			return baseResult;
		}
		//登录返回ticket
		String ticket = ssoService.login(admin);
		if (!StringUtils.isEmpty(ticket)){
			//票据不为空，存入cookie
			boolean result = cookieService.setCookie(request, response, ticket);
			//将用户信息存入session中，方便页面返显
			request.getSession().setAttribute("user",admin);
			return result?BaseResult.success():BaseResult.error();
		}
		baseResult.setCode(BaseResultEnum.PASS_ERROR_04.getCode());
		baseResult.setMessage(BaseResultEnum.PASS_ERROR_04.getMessage());
		return baseResult;
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

}
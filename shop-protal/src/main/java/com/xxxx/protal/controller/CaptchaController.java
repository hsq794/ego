package com.xxxx.protal.controller;

import com.xxxx.common.result.BaseResult;
import com.xxxx.protal.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhoubin
 * @since 1.0.0
 */
@Controller
public class CaptchaController {
	@Autowired
	private CaptchaService captchaService;

	/**
	 * 核验验证码票据
	 * @param ticket
	 * @param randStr
	 * @return
	 */
	@RequestMapping("getCaptcha")
	@ResponseBody
	public BaseResult captcha(String ticket, String randStr){
		return captchaService.captcha(ticket,randStr);
	}

}
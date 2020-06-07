package com.xxxx.protal.service;

import com.xxxx.common.result.BaseResult;

/**
 * @author zhoubin
 * @since 1.0.0
 */
public interface CaptchaService {

	/**
	 * 核验验证码票据
	 * @param ticket
	 * @param randStr
	 * @return
	 */
	BaseResult captcha(String ticket, String randStr);
}

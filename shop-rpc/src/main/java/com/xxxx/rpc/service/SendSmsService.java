package com.xxxx.rpc.service;

import com.xxxx.common.result.BaseResult;

/**
 * 发送短信
 *
 * @author zhoubin
 * @since 1.0.0
 */
public interface SendSmsService {

	/**
	 * 发送短信
	 * @param phone
	 * @return
	 */
	BaseResult sendSms(String phone);
}

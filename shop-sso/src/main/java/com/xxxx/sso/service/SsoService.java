package com.xxxx.sso.service;

import com.xxxx.common.pojo.Admin;

/**
 * 单点登录
 *
 * @author zhoubin
 * @since 1.0.0
 */
public interface SsoService {

	/**
	 * 登录成功返回ticket
	 * @param admin
	 * @return
	 */
	String login(Admin admin);

	/**
	 * 验证ticket返回用户信息
	 * @param ticket
	 * @return
	 */
	Admin validate(String ticket);

	/**
	 * 登出
	 * @param ticket
	 */
	void logout(String ticket);
}

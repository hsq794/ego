package com.xxxx.protal.service;

import com.xxxx.common.pojo.AdminWithBLOBs;
import com.xxxx.common.result.BaseResult;

/**
 * 用户
 *
 * @author zhoubin
 * @since 1.0.0
 */
public interface UserService {

	/**
	 * 注册用户
	 * @param admin
	 * @return
	 */
	BaseResult saveUser(AdminWithBLOBs admin);
}

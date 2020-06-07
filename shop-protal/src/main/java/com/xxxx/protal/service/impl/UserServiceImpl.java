package com.xxxx.protal.service.impl;

import com.xxxx.common.pojo.AdminWithBLOBs;
import com.xxxx.common.result.BaseResult;
import com.xxxx.common.utils.Md5Util;
import com.xxxx.common.utils.RandomUtil;
import com.xxxx.protal.mapper.AdminMapper;
import com.xxxx.protal.service.UserService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author zhoubin
 * @since 1.0.0
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private AdminMapper adminMapper;
	@Autowired
	private AmqpTemplate amqpTemplate;

	/**
	 * 注册用户
	 *
	 * @param admin
	 * @return
	 */
	@Override
	public BaseResult saveUser(AdminWithBLOBs admin) {
		//获取盐
		String salt = RandomUtil.getRandom1();
		admin.setEcSalt(salt);
		//根据盐加密密码
		String password = Md5Util.getMd5WithSalt(admin.getPassword(), salt);
		admin.setPassword(password);
		//注册时间
		admin.setAddTime((int) LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")));
		//插入数据
		int result = adminMapper.insertSelective(admin);
		if (result>0){
			amqpTemplate.convertAndSend("smsExchange","registry.sms",admin.getEmail());
			System.out.println("发送消息："+admin.getEmail());
			return BaseResult.success();
		}
		return BaseResult.error();
	}
}
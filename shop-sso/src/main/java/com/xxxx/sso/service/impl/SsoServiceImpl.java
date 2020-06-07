package com.xxxx.sso.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xxxx.common.pojo.Admin;
import com.xxxx.common.pojo.AdminExample;
import com.xxxx.common.utils.JsonUtil;
import com.xxxx.common.utils.Md5Util;
import com.xxxx.common.utils.UUIDUtil;
import com.xxxx.sso.mapper.AdminMapper;
import com.xxxx.sso.service.SsoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zhoubin
 * @since 1.0.0
 */
@Service(interfaceClass = SsoService.class)
@Component
public class SsoServiceImpl implements SsoService {

	@Autowired
	private AdminMapper adminMapper;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Value("${user.ticket}")
	private String userTicket;

	/**
	 * 登录成功返回ticket
	 *
	 * @param admin
	 * @return
	 */
	@Override
	public String login(Admin admin) {
		//判断用户名
		if (StringUtils.isEmpty(admin.getUserName().trim())) {
			System.out.println("用户名为空");
			return null;
		}
		//判断密码
		if (StringUtils.isEmpty(admin.getPassword().trim())) {
			System.out.println("密码为空");
			return null;
		}
		//根据用户名查询
		AdminExample example = new AdminExample();
		example.createCriteria().andUserNameEqualTo(admin.getUserName());
		List<Admin> list = adminMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(list) || list.size() > 1) {
			System.out.println("用户名或密码错误");
			return null;
		}
		Admin a = list.get(0);
		//比较密码
		if (!a.getPassword().equals(Md5Util.getMd5WithSalt(admin.getPassword(), a.getEcSalt()))) {
			System.out.println("用户名或密码错误");
			return null;
		}
		//生成ticket
		String ticket = UUIDUtil.getUUID();
		//ticket和用户信息存入redis，失效时间30分钟
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		valueOperations.set(userTicket + ":" + ticket, JsonUtil.object2JsonStr(a), 30, TimeUnit.MINUTES);
		return ticket;
	}

	/**
	 * 验证ticket返回用户信息
	 *
	 * @param ticket
	 * @return
	 */
	@Override
	public Admin validate(String ticket) {
		if (StringUtils.isEmpty(ticket)){
			System.out.println("票据信息不存在");
			return null;
		}
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		String adminJson = valueOperations.get(userTicket + ":" + ticket);
		if (StringUtils.isEmpty(adminJson)){
			System.out.println("票据对应的用户信息不存在");
			return null;
		}
		return JsonUtil.jsonStr2Object(adminJson,Admin.class);
	}

	/**
	 * 登出
	 * @param ticket
	 */
	@Override
	public void logout(String ticket) {
		redisTemplate.delete(userTicket+":"+ticket);
	}
}
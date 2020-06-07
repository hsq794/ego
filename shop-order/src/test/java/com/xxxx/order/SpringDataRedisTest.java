package com.xxxx.order;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

/**
 * @author zhoubin
 * @since 1.0.0
 */
@SpringBootTest
public class SpringDataRedisTest {

	@Autowired
	private RedisTemplate<String,Object> redisTemplate;

	/**
	 * 测试redis自增id
	 */
	@Test
	public void testGetIncrement(){
		RedisAtomicLong entityIdCounter = new RedisAtomicLong("order:increment",redisTemplate.getConnectionFactory());
		long increment = entityIdCounter.getAndIncrement();
		System.out.println(increment);
	}
}
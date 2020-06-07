package com.xxxx.order;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 后台管理启动类
 *
 * @author zhoubin
 * @since 1.0.0
 */
@SpringBootApplication
@EnableDubboConfiguration
@MapperScan("com.xxxx.order.mapper")
public class OrderApplication {
	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}
}
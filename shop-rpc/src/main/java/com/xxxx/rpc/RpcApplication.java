package com.xxxx.rpc;

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
//开启dubbo
@EnableDubboConfiguration
@MapperScan("com.xxxx.rpc.mapper")
public class RpcApplication {
	public static void main(String[] args) {
		SpringApplication.run(RpcApplication.class, args);
	}
}
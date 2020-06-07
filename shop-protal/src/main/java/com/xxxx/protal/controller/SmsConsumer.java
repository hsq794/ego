package com.xxxx.protal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xxxx.rpc.service.SendSmsService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消费消息
 * @author zhoubin
 * @since 1.0.0
 */
@Component
@RabbitListener(queues = "smsQueue")
public class SmsConsumer {

	@Reference(interfaceClass = SendSmsService.class)
	private SendSmsService sendSmsService;

	//处理队列里的消息
	@RabbitHandler
	public void process(String phone){
		System.out.println("接收到消息："+phone);
		//发送短信
		sendSmsService.sendSms(phone);
	}

}
package com.xxxx.rpc.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import com.xxxx.common.result.BaseResult;
import com.xxxx.rpc.service.SendSmsService;
import org.springframework.stereotype.Component;

/**
 * @author zhoubin
 * @since 1.0.0
 */
@Service(interfaceClass = SendSmsService.class)
@Component
public class SendSmsServiceImpl implements SendSmsService {

	/**
	 * 发送短信
	 * @param phone
	 * @return
	 */
	@Override
	public BaseResult sendSms(String phone) {
		try{
			//创建Credential对象，鉴权
			Credential cred = new Credential("AKID2Xnoa8iWKssfNrF5pKZv2ye5HEvMHBHT", "BnEPLdgT0IMCnAdsrb5NisnP2UmuqZ3N");
			//创建HttpProfile对象，放入接口域名
			HttpProfile httpProfile = new HttpProfile();
			httpProfile.setEndpoint("sms.tencentcloudapi.com");
			//创建ClientProfile对象
			ClientProfile clientProfile = new ClientProfile();
			clientProfile.setHttpProfile(httpProfile);
			//创建SmsClient对象
			SmsClient client = new SmsClient(cred, "ap-shanghai", clientProfile);
			//请求参数
			String params = "{\"PhoneNumberSet\":[86"+phone+"],\"TemplateID\":\"490150\",\"Sign\":\"程序猿学习栈\",\"SmsSdkAppid\":\"1400291704\"}";
			SendSmsRequest req = SendSmsRequest.fromJsonString(params, SendSmsRequest.class);
			//SmsClient发送短信
			SendSmsResponse resp = client.SendSms(req);
			//发送成功
			if ("ok".equalsIgnoreCase(resp.getSendStatusSet()[0].getCode())){
				return BaseResult.success();
			}
		} catch (TencentCloudSDKException e) {
			System.out.println(e.toString());
		}
		return BaseResult.error();
	}
}
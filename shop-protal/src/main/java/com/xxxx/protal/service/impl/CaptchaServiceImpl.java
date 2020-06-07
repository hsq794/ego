package com.xxxx.protal.service.impl;

import com.tencentcloudapi.captcha.v20190722.CaptchaClient;
import com.tencentcloudapi.captcha.v20190722.models.DescribeCaptchaResultRequest;
import com.tencentcloudapi.captcha.v20190722.models.DescribeCaptchaResultResponse;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.xxxx.common.result.BaseResult;
import com.xxxx.protal.service.CaptchaService;
import org.springframework.stereotype.Service;

/**
 * @author zhoubin
 * @since 1.0.0
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {

	/**
	 * 核验验证码票据
	 * @param ticket
	 * @param randStr
	 * @return
	 */
	@Override
	public BaseResult captcha(String ticket, String randStr) {
		try{

			Credential cred = new Credential("AKID2Xnoa8iWKssfNrF5pKZv2ye5HEvMHBHT", "BnEPLdgT0IMCnAdsrb5NisnP2UmuqZ3N");

			HttpProfile httpProfile = new HttpProfile();
			httpProfile.setEndpoint("captcha.tencentcloudapi.com");

			ClientProfile clientProfile = new ClientProfile();
			clientProfile.setHttpProfile(httpProfile);

			CaptchaClient client = new CaptchaClient(cred, "ap-shanghai", clientProfile);

			String params = "{\"CaptchaType\":9,\"Ticket\":\""+ticket+"\",\"UserIp\":\"127.0.0.1\",\"Randstr\":\""+randStr+"\",\"CaptchaAppId\":2086582926,\"AppSecretKey\":\"0k_RaTXmkbL7LE7VbqT7uUw**\"}";
			DescribeCaptchaResultRequest req = DescribeCaptchaResultRequest.fromJsonString(params, DescribeCaptchaResultRequest.class);

			DescribeCaptchaResultResponse resp = client.DescribeCaptchaResult(req);

			System.out.println(DescribeCaptchaResultRequest.toJsonString(resp));
			if ("ok".equalsIgnoreCase(resp.getCaptchaMsg())){
				return BaseResult.success();
			}
		} catch (TencentCloudSDKException e) {
			System.out.println(e.toString());
		}
		return BaseResult.error();
	}
}
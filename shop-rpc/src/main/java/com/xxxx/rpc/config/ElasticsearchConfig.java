package com.xxxx.rpc.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Objects;

/**
 * Elasticsearch配置类
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Configuration
public class ElasticsearchConfig {
	//ES服务器地址
	@Value("${elasticsearch.address}")
	private String[] address;
	//ES连接方式
	private static final String SCHEME = "http";

	//根据服务器地址创建HttpHost对象
	@Bean
	public HttpHost buildHttpHost(String s){
		String[] split = s.split(":");
		if (2!=split.length){
			return null;
		}
		String host = split[0];
		int port = Integer.parseInt(split[1]);
		return new HttpHost(host,port,SCHEME);
	}

	//创建RestClientBuilder对象
	@Bean
	public RestClientBuilder restClientBuilder(){
		HttpHost[] httpHosts = Arrays.stream(address)
				.map(this::buildHttpHost)
				.filter(Objects::nonNull)
				.toArray(HttpHost[]::new);
		return RestClient.builder(httpHosts);
	}

	//创建RestHighLevelClient对象
	@Bean
	public RestHighLevelClient restHighLevelClient(@Autowired RestClientBuilder restClientBuilder){
		return new RestHighLevelClient(restClientBuilder);
	}

}
package com.xxxx.manager.service;

import com.xxxx.common.result.FileResult;

import java.io.InputStream;

/**
 * 上传接口
 *
 * @author zhoubin
 * @since 1.0.0
 */
public interface UploadService {
	/**
	 * 文件上传
	 * @param inputStream
	 * @param fileName
	 * @return
	 */
	FileResult upload(InputStream inputStream, String fileName);
}

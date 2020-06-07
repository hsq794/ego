package com.xxxx.manager.service.impl;

import com.xxxx.manager.mapper.BrandMapper;
import com.xxxx.manager.pojo.Brand;
import com.xxxx.manager.pojo.BrandExample;
import com.xxxx.manager.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 品牌
 * @author zhoubin
 * @since 1.0.0
 */
@Service
public class BrandServiceImpl implements BrandService {

	@Autowired
	private BrandMapper brandMapper;

	@Override
	public List<Brand> selectBrandList() {
		return brandMapper.selectByExample(new BrandExample());
	}
}
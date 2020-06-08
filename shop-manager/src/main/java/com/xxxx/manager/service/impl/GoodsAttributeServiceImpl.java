package com.xxxx.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.common.result.BaseResult;
import com.xxxx.manager.mapper.GoodsAttributeMapper;
import com.xxxx.manager.pojo.GoodsAttribute;
import com.xxxx.manager.pojo.GoodsAttributeExample;
import com.xxxx.manager.service.GoodsAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class GoodsAttributeServiceImpl implements GoodsAttributeService {

    @Autowired
    private GoodsAttributeMapper goodsAttributeMapper;

    @Override
    public List<GoodsAttribute> selectGoodsAttribute(Integer id) {
        GoodsAttributeExample goodsAttributeExample = new GoodsAttributeExample();
        if(StringUtils.isEmpty(id)){
            return goodsAttributeMapper.selectByExample(goodsAttributeExample);
        }
        goodsAttributeExample.createCriteria().andTypeIdEqualTo(id);
        return goodsAttributeMapper.selectByExample(goodsAttributeExample);
    }

    /**
     * 商品属性列表 分页
     * @param id
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public BaseResult selectGoodsAttributeList(Integer id, Integer pageNum, Integer pageSize) {
        //构建分页对象
        PageHelper.startPage(pageNum,pageSize);

        GoodsAttributeExample goodsAttributeExample = new GoodsAttributeExample();
        //商品模型id
        if (null!=id && 0!=id){
            goodsAttributeExample.createCriteria().andTypeIdEqualTo(id);
        }
        PageInfo<GoodsAttribute> pageInfo = new PageInfo<>(goodsAttributeMapper.selectByExample(goodsAttributeExample));
        return BaseResult.success(pageInfo);
    }
}

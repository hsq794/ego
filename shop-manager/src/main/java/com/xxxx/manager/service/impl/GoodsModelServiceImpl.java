package com.xxxx.manager.service.impl;

import com.xxxx.manager.mapper.GoodsTypeMapper;
import com.xxxx.manager.pojo.GoodsType;
import com.xxxx.manager.pojo.GoodsTypeExample;
import com.xxxx.manager.service.GoodsModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GoodsModelServiceImpl implements GoodsModelService {

    @Autowired
    private GoodsTypeMapper goodsTypeMapper;

    /**
     * 商品模型列表
     * @return
     */
    @Override
    public List<GoodsType> selectGoodsModel() {

        return goodsTypeMapper.selectByExample(new GoodsTypeExample());
    }
}

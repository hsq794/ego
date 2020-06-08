package com.xxxx.manager.service.impl;

import com.xxxx.common.result.BaseResult;
import com.xxxx.manager.mapper.GoodsTypeMapper;
import com.xxxx.manager.pojo.GoodsType;
import com.xxxx.manager.pojo.GoodsTypeExample;
import com.xxxx.manager.service.GoodsModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    /**
     * 商品模型添加
     * @param goodsType
     * @return
     */
    @Override
    public BaseResult addGoodsModel(GoodsType goodsType) {
        if(StringUtils.isEmpty(goodsType.getName())){
            return BaseResult.error();
        }
        GoodsTypeExample goodsTypeExample = new GoodsTypeExample();
        int row = goodsTypeMapper.insert(goodsType);
        return row>0?BaseResult.success():BaseResult.error();
    }
}

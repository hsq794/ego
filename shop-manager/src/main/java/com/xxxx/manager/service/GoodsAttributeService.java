package com.xxxx.manager.service;

import com.xxxx.common.result.BaseResult;
import com.xxxx.manager.pojo.GoodsAttribute;
import com.xxxx.manager.pojo.GoodsType;

import java.util.List;

public interface GoodsAttributeService {
    List<GoodsAttribute> selectGoodsAttribute(Integer id);
    GoodsAttribute selectGoodsAttributeById(Integer attrId);
    BaseResult selectGoodsAttributeList(Integer id,Integer pageNum,Integer pageSize);

    BaseResult addGoodsAttribute(GoodsAttribute goodsAttribute);
    BaseResult updateGoodsAttribute(GoodsAttribute goodsAttribute);

    BaseResult deleteGoodsAttribute(Integer typeId);
}

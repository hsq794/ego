package com.xxxx.manager.service;

import com.xxxx.common.result.BaseResult;
import com.xxxx.manager.pojo.GoodsType;

import java.util.List;

public interface GoodsModelService {
    List<GoodsType> selectGoodsModel();
    BaseResult addGoodsModel(GoodsType goodsType);
    BaseResult updateGoodsModel(GoodsType goodsType);

    GoodsType selectGoodsModelById(Short typeId);
    BaseResult deleteGoodsModel(short typeId);
}

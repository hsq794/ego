package com.xxxx.manager.service;


import com.xxxx.manager.pojo.GoodsSpec;

import java.util.List;

public interface GoodsSpecService {

    /**
     * 查询商品规格列表
     * @return
     */
    List<GoodsSpec> selectGoodsSpec();

    /**
     * 保存规格
     * @param goodsSpec
     * @return
     */
    int InsertGoodsSpec(GoodsSpec goodsSpec);

    /**
     * 查询某种模型
     */
    List<GoodsSpec> selectGoodsSpecByTypeName(String typeName);

    /**
     * 修改数据
     * @param goodsSpec
     * @return
     */
    int updateGoodsSpec(GoodsSpec goodsSpec);

    /**
     * 删除规格根据id
     * @param specId
     * @return
     */
    int deleteGoodsSpecById(Integer specId);

}

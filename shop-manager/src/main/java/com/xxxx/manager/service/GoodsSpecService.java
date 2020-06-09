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
     * 根据id 查询对象
     * @param specId
     * @return
     */
    GoodsSpec selectGoodsSpecBySpecId(Integer specId);
    /**
     * 查询某种模型
     */
    List<GoodsSpec> selectGoodsSpecById(Short id);

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

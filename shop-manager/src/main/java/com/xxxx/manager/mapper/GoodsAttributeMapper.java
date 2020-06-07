package com.xxxx.manager.mapper;

import com.xxxx.manager.pojo.GoodsAttribute;
import com.xxxx.manager.pojo.GoodsAttributeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsAttributeMapper {
    long countByExample(GoodsAttributeExample example);

    int deleteByExample(GoodsAttributeExample example);

    int deleteByPrimaryKey(Integer attrId);

    int insert(GoodsAttribute record);

    int insertSelective(GoodsAttribute record);

    List<GoodsAttribute> selectByExampleWithBLOBs(GoodsAttributeExample example);

    List<GoodsAttribute> selectByExample(GoodsAttributeExample example);

    GoodsAttribute selectByPrimaryKey(Integer attrId);

    int updateByExampleSelective(@Param("record") GoodsAttribute record, @Param("example") GoodsAttributeExample example);

    int updateByExampleWithBLOBs(@Param("record") GoodsAttribute record, @Param("example") GoodsAttributeExample example);

    int updateByExample(@Param("record") GoodsAttribute record, @Param("example") GoodsAttributeExample example);

    int updateByPrimaryKeySelective(GoodsAttribute record);

    int updateByPrimaryKeyWithBLOBs(GoodsAttribute record);

    int updateByPrimaryKey(GoodsAttribute record);
}
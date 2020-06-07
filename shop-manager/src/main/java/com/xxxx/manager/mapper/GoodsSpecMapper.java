package com.xxxx.manager.mapper;

import com.xxxx.manager.pojo.GoodsSpec;
import com.xxxx.manager.pojo.GoodsSpecExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsSpecMapper {
    long countByExample(GoodsSpecExample example);

    int deleteByExample(GoodsSpecExample example);

    int deleteByPrimaryKey(Integer specId);

    int insert(GoodsSpec record);

    int insertSelective(GoodsSpec record);

    List<GoodsSpec> selectByExampleWithBLOBs(GoodsSpecExample example);

    List<GoodsSpec> selectByExample(GoodsSpecExample example);

    GoodsSpec selectByPrimaryKey(Integer specId);

    int updateByExampleSelective(@Param("record") GoodsSpec record, @Param("example") GoodsSpecExample example);

    int updateByExampleWithBLOBs(@Param("record") GoodsSpec record, @Param("example") GoodsSpecExample example);

    int updateByExample(@Param("record") GoodsSpec record, @Param("example") GoodsSpecExample example);

    int updateByPrimaryKeySelective(GoodsSpec record);

    int updateByPrimaryKeyWithBLOBs(GoodsSpec record);

    int updateByPrimaryKey(GoodsSpec record);
}
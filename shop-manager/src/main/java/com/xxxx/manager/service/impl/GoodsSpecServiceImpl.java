package com.xxxx.manager.service.impl;

import com.xxxx.manager.mapper.GoodsSpecMapper;
import com.xxxx.manager.mapper.GoodsTypeMapper;
import com.xxxx.manager.pojo.*;
import com.xxxx.manager.service.GoodsSpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class GoodsSpecServiceImpl implements GoodsSpecService {

    @Autowired
    private GoodsSpecMapper gooodsSpecMapper;

    @Autowired
    private GoodsTypeMapper goodsTypeMapper;

    /**
     * 查询商品规格列表
     * @return
     */
    @Override
    public List<GoodsSpec> selectGoodsSpec() {
        return gooodsSpecMapper.selectByExample(new GoodsSpecExample());
    }

    /**
     * 保存规格
     * @param goodsSpec
     * @return
     */
    @Override
    public int InsertGoodsSpec(GoodsSpec goodsSpec) {
        //判断非空
        if(StringUtils.isEmpty(goodsSpec)){
            return 0;
        }
        //
        if(StringUtils.isEmpty(goodsSpec.getTypeId())){
            return 0;
        }
        //根据typeId查询typeName
        GoodsType goodsType=goodsTypeMapper.selectByPrimaryKey(goodsSpec.getTypeId());

        //赋值
        goodsSpec.setTypeName(goodsType.getName());
        return gooodsSpecMapper.insert(goodsSpec);
    }

    /**
     * 查询某种模型
     * @param typeName
     * @return
     */
    @Override
    public List<GoodsSpec> selectGoodsSpecByTypeName(String typeName) {
        //判断是否为空
        if(StringUtils.isEmpty(typeName)){
            return gooodsSpecMapper.selectByExample(new GoodsSpecExample());
        }
        //创建对象
        GoodsSpecExample example=new GoodsSpecExample();
        example.createCriteria().andTypeNameEqualTo(typeName);
        return gooodsSpecMapper.selectByExample(example);
    }



    /**
     * 修改数据
     * @param goodsSpec
     * @return
     */
    @Override
    public int updateGoodsSpec(GoodsSpec goodsSpec) {
        //判断非空
        if(StringUtils.isEmpty(goodsSpec)){
            return 0;
        }
        return gooodsSpecMapper.updateByExample(goodsSpec,new GoodsSpecExample());
    }

    /**
     * 删除数据规格根据id
     * @param specId
     * @return
     */
    @Override
    public int deleteGoodsSpecById(Integer specId) {
        //判断非空
        if(StringUtils.isEmpty(specId)){
            return 0;
        }

        return gooodsSpecMapper.deleteByPrimaryKey(specId);
    }

}

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

    @Override
    public GoodsAttribute selectGoodsAttributeById(Integer attrId) {
        if(StringUtils.isEmpty(attrId)){
            return null;
        }
        return goodsAttributeMapper.selectByPrimaryKey(attrId);
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

    /**
     * 商品属性添加功能实现
     * @param goodsAttribute
     * @return
     */
    @Override
    public BaseResult addGoodsAttribute(GoodsAttribute goodsAttribute) {
        if(StringUtils.isEmpty(goodsAttribute.getAttrName())){
            return BaseResult.error();
        }
        if (null==goodsAttribute.getTypeId()){
            return BaseResult.error();
        }
        if (null==goodsAttribute.getAttrIndex()){
            return BaseResult.error();
        }
        if (null==goodsAttribute.getAttrInputType()){
            return BaseResult.error();
        }else {
            if(1==goodsAttribute.getAttrInputType()){
                if (null==goodsAttribute.getAttrValues()){
                    return BaseResult.error();
                }
            }
        }
        goodsAttribute.setAttrOrder((byte) 50);
        int row = goodsAttributeMapper.insert(goodsAttribute);
        return row>0?BaseResult.success():BaseResult.error();
    }

    /**
     * 商品属性修改功能实现
     * @param goodsAttribute
     * @return
     */
    @Override
    public BaseResult updateGoodsAttribute(GoodsAttribute goodsAttribute) {
        if(null!=goodsAttribute.getAttrId()){
            GoodsAttribute goodsAttribute1 = goodsAttributeMapper.selectByPrimaryKey(goodsAttribute.getAttrId());
            if (null==goodsAttribute1) {
                return BaseResult.error();
            }
        }
        if(StringUtils.isEmpty(goodsAttribute.getAttrName())){
            return BaseResult.error();
        }
        if (null==goodsAttribute.getTypeId()){
            return BaseResult.error();
        }
        if (null==goodsAttribute.getAttrIndex()){
            return BaseResult.error();
        }
        if (null==goodsAttribute.getAttrInputType()){
            return BaseResult.error();
        }else {
            if(1==goodsAttribute.getAttrInputType()){
                if (null==goodsAttribute.getAttrValues()){
                    return BaseResult.error();
                }
            }
        }
        goodsAttribute.setAttrOrder((byte) 50);
        int row = goodsAttributeMapper.updateByPrimaryKey(goodsAttribute);
        return row>0?BaseResult.success():BaseResult.error();
    }

    @Override
    public BaseResult deleteGoodsAttribute(Integer typeId) {
        int row = goodsAttributeMapper.deleteByPrimaryKey(typeId);
        return row>0?BaseResult.success():BaseResult.error();
    }
}

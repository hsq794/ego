package com.xxxx.manager.controller;

import com.xxxx.common.result.BaseResult;
import com.xxxx.manager.pojo.GoodsAttribute;
import com.xxxx.manager.service.GoodsAttributeService;
import com.xxxx.manager.service.GoodsModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("goods/model")
public class GoodsAttributeController {

    @Autowired
    private GoodsAttributeService goodsAttributeService;

    @Autowired
    private GoodsModelService goodsModelService;

    /**
     * 商品属性
     * @return
     */
    @RequestMapping("/attribute")
    @ResponseBody
    public BaseResult goodsTypeList(Integer id, Integer pageNum, Integer pageSize) {
//        model.addAttribute("goodsModel",goodsModelService.selectGoodsModel());
        return goodsAttributeService.selectGoodsAttributeList(id,pageNum,pageSize);
    }

    /**
     * 页面跳转-商品属性-列表
     * @param model
     * @return
     */
    @RequestMapping("/attributeList")
    public String  goodsAttributeList(Model model,Integer id) {
        model.addAttribute("goodsModel",goodsModelService.selectGoodsModel());
        if (null!=id){
            model.addAttribute("typeId",id);
        }
        return "goods/model/goods-attribute";
    }

    /**
     * 商品属性添加页面跳转
     * @param model
     * @return
     */
    @RequestMapping("/attribute/addPage")
    public String goodsModelAdd(Model model,GoodsAttribute goodsAttribute) {
        if(null!=goodsAttribute.getAttrId()){
            model.addAttribute("attrId",goodsAttribute.getAttrId());
            GoodsAttribute goodsAttribute1 = goodsAttributeService.selectGoodsAttributeById(goodsAttribute.getAttrId());
            if(null== goodsAttribute){
                model.addAttribute("goodsAttribute",new GoodsAttribute());
            }else {
                model.addAttribute("goodsAttribute",goodsAttribute1);
            }
        }
        model.addAttribute("goodsModel",goodsModelService.selectGoodsModel());
        return "goods/model/attr-add";
    }

    /**
     * 商品属性添加功能实现
     * @param goodsAttribute
     * @return
     */
    @RequestMapping("/attribute/addOrUpdate")
    @ResponseBody
    public BaseResult goodsModelAdd(GoodsAttribute goodsAttribute) {
        if(null==goodsAttribute.getAttrId()){
            return goodsAttributeService.addGoodsAttribute(goodsAttribute);
        }else {
            return goodsAttributeService.updateGoodsAttribute(goodsAttribute);
        }
    }

    /**
     * 商品属性删除功能实现
     * @param typeId
     * @return
     */
    @RequestMapping("/attribute/delete")
    @ResponseBody
    public BaseResult goodsModelDelete(Integer typeId) {
        if(null==typeId){
            return BaseResult.error();
        }
        return goodsAttributeService.deleteGoodsAttribute(typeId);
    }


}

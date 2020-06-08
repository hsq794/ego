package com.xxxx.manager.controller;

import com.xxxx.common.result.BaseResult;
import com.xxxx.manager.service.GoodsAttributeService;
import com.xxxx.manager.service.GoodsModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @RequestMapping("/attribute/add")
    public String goodsModelAdd(Model model) {
        model.addAttribute("goodsModel",goodsModelService.selectGoodsModel());
        return "goods/model/attr-add";
    }
}

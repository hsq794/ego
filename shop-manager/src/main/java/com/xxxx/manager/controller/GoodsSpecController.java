package com.xxxx.manager.controller;

import com.xxxx.common.result.BaseResult;
import com.xxxx.manager.pojo.GoodsSpec;

import com.xxxx.manager.service.GoodsModelService;
import com.xxxx.manager.service.GoodsSpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("goods")
public class GoodsSpecController {

    @Autowired
    private GoodsSpecService goodsSpecService;

    @Autowired
    private GoodsModelService goodsModelService;

    /**
     * 查询商品规格列表
     * @param model
     * @return
     */
    @RequestMapping("/model/spec")
    public String goodsSpecList(Model model) {
        //全部模型
        model.addAttribute("typeList", goodsModelService.selectGoodsModel());
        //商品规则
        model.addAttribute("gmsList",goodsSpecService.selectGoodsSpec());
        return "goods/model/goods-spec";
    }

    /**
     * 添加规格
     * @return
     */
    @RequestMapping("/spec/add")
    public String goodsSpecAdd(Model model,Integer specId){
        //全部模型
        model.addAttribute("typeList", goodsModelService.selectGoodsModel());
        //根据id得到GoodSpec对象
        GoodsSpec goodsSpec=goodsSpecService.selectGoodsSpecBySpecId(specId);
        if(StringUtils.isEmpty(goodsSpec)){
            model.addAttribute("goodsSpec",new GoodsSpec());
        }else {
            model.addAttribute("goodsSpec",goodsSpec);
        }
        return "goods/model/spec-add";
    }

    /**
     * 保存规格
     * @param goodsSpec
     * @return
     */
    @ResponseBody
    @RequestMapping("/spec/save")
    public BaseResult goodsSpecSave(GoodsSpec goodsSpec){
        if(StringUtils.isEmpty(goodsSpec.getSpecId())){
            int result=goodsSpecService.InsertGoodsSpec(goodsSpec);
            return result>0?BaseResult.success():BaseResult.error();
        }else {
            int result=goodsSpecService.updateGoodsSpec(goodsSpec);
            return result>0?BaseResult.success():BaseResult.error();
        }

    }


    /**
     * 查询某模型
     * @param id
     * @param request
     * @return
     */

    @RequestMapping("/spec/select")
    public String goodsSpecByTypeName(Short id, HttpServletRequest request){
        //全部模型
        request.setAttribute("typeList", goodsModelService.selectGoodsModel());
        request.setAttribute("gmsList",goodsSpecService.selectGoodsSpecById(id));
        return "goods/model/goods-spec";
    }

   /* *//**
     * 修改规格数据
     * @param goodsSpec
     * @return
     *//*
    @ResponseBody
    @RequestMapping("/spec/update")
    public BaseResult updateGoodsSpec(GoodsSpec goodsSpec){
        int result=goodsSpecService.updateGoodsSpec(goodsSpec);
        return result>0?BaseResult.success():BaseResult.error();
    }
*/
    /**
     * 删除数据根据id
     * @param specId
     * @return
     */

    @RequestMapping("/spec/deletes")
    @ResponseBody
    public BaseResult deleteGoodsSpecById(Integer specId){
        int result=goodsSpecService.deleteGoodsSpecById(specId);
        return result>0?BaseResult.success():BaseResult.error();
    }
}

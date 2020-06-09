package com.xxxx.manager.controller;

import com.xxxx.common.result.BaseResult;
import com.xxxx.manager.pojo.GoodsSpec;
import com.xxxx.manager.pojo.GoodsType;
import com.xxxx.manager.service.GoodsModelService;
import com.xxxx.manager.service.GoodsSpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        model.addAttribute("gmsList",goodsSpecService.selectGoodsSpec());
        return "goods/model/goods-spec";
    }

    /**
     * 添加规格
     * @return
     */
    @RequestMapping("/spec/add")
    public String goodsSpecAdd(Model model){
        //全部模型
        model.addAttribute("typeList", goodsModelService.selectGoodsModel());
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
        int result=goodsSpecService.InsertGoodsSpec(goodsSpec);
        return result>0?BaseResult.success():BaseResult.error();
    }


    /**
     * 查询某模型
     * @param typeName
     * @param request
     * @return
     */

    @RequestMapping("/spec/select")
    public String goodsSpecByTypeName(String typeName, HttpServletRequest request){
        request.setAttribute("gmsList",goodsSpecService.selectGoodsSpecByTypeName(typeName));
        return "goods/model/goods-spec";
    }

    /**
     * 修改规格数据
     * @param goodsSpec
     * @return
     */
    @ResponseBody
    @RequestMapping("/spec/update")
    public BaseResult updateGoodsSpec(GoodsSpec goodsSpec){
        int result=goodsSpecService.updateGoodsSpec(goodsSpec);
        return result>0?BaseResult.success():BaseResult.error();
    }

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

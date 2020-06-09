package com.xxxx.manager.controller;

import com.xxxx.common.result.BaseResult;
import com.xxxx.common.result.FileResult;
import com.xxxx.manager.mapper.GoodsTypeMapper;
import com.xxxx.manager.pojo.Goods;
import com.xxxx.manager.pojo.GoodsCategory;
import com.xxxx.manager.pojo.GoodsImages;
import com.xxxx.manager.pojo.GoodsType;
import com.xxxx.manager.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 商品分类
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Controller
@RequestMapping("goods")
public class GoodsController {

	@Autowired
	private GoodsCategoryService goodsCategoryService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private GoodsImagesService goodsImagesService;
	@Autowired
	private UploadService uploadService;

	@Autowired
	private GoodsModelService goodsModelService;

	/**
	 * 页面跳转   商品分类-列表页
	 *
	 * @return
	 */
	@RequestMapping("/category/list")
	public String categoryList(Model model) {
		model.addAttribute("gcvList",goodsCategoryService.selectCategoryListForView());
		return "goods/category/category-list";
	}


	/**
	 * 页面跳转   商品分类-新增页
	 *
	 * @return
	 */
	@RequestMapping("/category/add")
	public String categoryAdd(Model model) {
		model.addAttribute("gcList", goodsCategoryService.selectCategoryTopList());
		return "goods/category/category-add";
	}

	/**
	 * 商品分类-新增分类-级联查询
	 *
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/category/{parentId}")
	@ResponseBody
	public List<GoodsCategory> selectCategoryList(@PathVariable Short parentId) {
		return goodsCategoryService.selectCategoryByParentId(parentId);
	}

	/**
	 * 商品分类-新增分类-保存
	 *
	 * @param goodsCategory
	 * @return
	 */
	@RequestMapping("/category/save")
	@ResponseBody
	public BaseResult categorySave(GoodsCategory goodsCategory) {
		int result = goodsCategoryService.categorySave(goodsCategory);
		return result > 0 ? BaseResult.success() : BaseResult.error();
	}

	/**
	 * 页面跳转-商品-列表
	 * @return
	 */
	@RequestMapping("/list")
	public String goodsList(Model model){
		//商品分类
		model.addAttribute("gcList",goodsCategoryService.selectList());
		//品牌
		model.addAttribute("bradnList",brandService.selectBrandList());
		return "goods/goods-list";
	}

	/**
	 * 页面跳转-商品-新增
	 * @return
	 */
	@RequestMapping("/add")
	public String goodsAdd(Model model){
		//顶级分类
		model.addAttribute("gcList",goodsCategoryService.selectCategoryTopList());
		model.addAttribute("brandList",brandService.selectBrandList());
		return "goods/goods-add";
	}


	/**
	 * 商品新增-保存
	 * @param goods
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public BaseResult saveGoods(Goods goods){
		return goodsService.saveGoods(goods);
	}


	/**
	 * 商品新增-相册保存
	 * @param file
	 * @param goodsId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/images/save")
	@ResponseBody
	public BaseResult goodsImagesSave(MultipartFile file,Integer goodsId) throws IOException {
		String filename = file.getOriginalFilename();
		String date = DateTimeFormatter.ofPattern("yyyy/MM/dd/").format(LocalDate.now());
		filename = date + System.currentTimeMillis() + filename.substring(filename.lastIndexOf("."));
		FileResult result = uploadService.upload(file.getInputStream(), filename);
		//判断图片是否上传成功
		if (!StringUtils.isEmpty(result.getFileUrl())){
			GoodsImages goodsImages = new GoodsImages();
			goodsImages.setGoodsId(goodsId);
			goodsImages.setImageUrl(result.getFileUrl());
			return goodsImagesService.saveGoodsImages(goodsImages);
		}
		return BaseResult.error();
	}


	/**
	 * 商品-列表-分页查询
	 * @param goods
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/listForPage")
	@ResponseBody
	public BaseResult selectGoodsListByPage(Goods goods,Integer pageNum,Integer pageSize){
		return goodsService.selectGoodsListByPage(goods,pageNum,pageSize);
	}

	/**
	 * 商品模型
	 * @param model
	 * @return
	 */
	@RequestMapping("/model/list")
	public String goodsModelList(Model model) {
		model.addAttribute("goodsModel",goodsModelService.selectGoodsModel());
		return "goods/model/goods-model";
	}

	/**
	 * 商品模型添加页面跳转
	 * @param model
	 * @return
	 */
	@RequestMapping("/model/addPage")
	public String goodsModelAdd(Model model,GoodsType goodsType) {
		if(!StringUtils.isEmpty(goodsType.getId())){
			model.addAttribute("typeId",goodsType.getId());
			GoodsType goodsModel = goodsModelService.selectGoodsModelById(goodsType.getId());
			if(null == goodsModel){
				model.addAttribute("goodsType",new GoodsType());
			}else {
				model.addAttribute("goodsType",goodsModel);
			}
		}
		model.addAttribute("goodsModel",goodsModelService.selectGoodsModel());
		return "goods/model/model-add";
	}

	@RequestMapping("/model/addOrUpdate")
	@ResponseBody
	public BaseResult goodsModelupdate(Model model,GoodsType goodsType) {
		if(null!=goodsType.getId()){
			return goodsModelService.updateGoodsModel(goodsType);
		}else {
			model.addAttribute("goodsModel",goodsModelService.selectGoodsModel());
			return goodsModelService.addGoodsModel(goodsType);
		}
	}

	/**
	 * 商品模型删除功能实现
	 * @param typeId
	 * @return
	 */
	@RequestMapping("/model/delete")
	@ResponseBody
	public BaseResult goodsModelDelete(Short typeId) {
		if(null==typeId){
			return BaseResult.error();
		}
		return goodsModelService.deleteGoodsModel(typeId);
	}


}
<html>
<head>
    <#include "../../head.ftl">
    <!-- 引入doT.js -->
    <script type="text/javascript" src="${ctx}/static/js/doT.min.js"></script>
    <script type="text/javascript">
    function delfunc(obj){
    	layer.confirm('确认删除？', {
    		  btn: ['确定','取消'] //按钮
    		}, function(){
   				$.ajax({
   					type : 'post',
   					url : $(obj).attr('data-url'),
   					data : {act:'del',del_id:$(obj).attr('data-id')},
   					dataType : 'json',
   					success : function(data){
   						if(data==1){
   							layer.msg('操作成功', {icon: 1});
   							$(obj).parent().parent().remove();
   						}else{
   							layer.msg(data, {icon: 2,time: 2000});
   						}
   						layer.closeAll();
   					}
   				})
    		}, function(index){
    			layer.close(index);
    			return false;// 取消
    		}
    	);
    }
    
    //全选
    function selectAll(name,obj){
    	$('input[name*='+name+']').prop('checked', $(obj).checked);
    }   
    
    function get_help(obj){
        layer.open({
            type: 2,
            title: '帮助手册',
            shadeClose: true,
            shade: 0.3,
            area: ['90%', '90%'],
            content: $(obj).attr('data-url'), 
        });
    }
    
    function delAll(obj,name){
    	var a = [];
    	$('input[name*='+name+']').each(function(i,o){
    		if($(o).is(':checked')){
    			a.push($(o).val());
    		}
    	})
    	if(a.length == 0){
    		layer.alert('请选择删除项', {icon: 2});
    		return;
    	}
    	layer.confirm('确认删除？', {btn: ['确定','取消'] }, function(){
    			$.ajax({
    				type : 'get',
    				url : $(obj).attr('data-url'),
    				data : {act:'del',del_id:a},
    				dataType : 'json',
    				success : function(data){
    					if(data == 1){
    						layer.msg('操作成功', {icon: 1});
    						$('input[name*='+name+']').each(function(i,o){
    							if($(o).is(':checked')){
    								$(o).parent().parent().remove();
    							}
    						})
    					}else{
    						layer.msg(data, {icon: 2,time: 2000});
    					}
    					layer.closeAll();
    				}
    			})
    		}, function(index){
    			layer.close(index);
    			return false;// 取消
    		}
    	);	
    }
    </script>        
  <meta name="__hash__" content="fd6059588f758033044de1141d249844_02bad2de3b662123d3092c856167c3f1"></head>
  <body style="background-color:#ecf0f5;">
 

<div class="wrapper">
  <div class="breadcrumbs" id="breadcrumbs">
	<ol class="breadcrumb">
	<li><a href="javascript:void();"><i class="fa fa-home"></i>&nbsp;&nbsp;后台首页</a></li>
	        
	        <li><a href="javascript:void();">商品管理</a></li>    
	        <li><a href="javascript:void();">商品属性</a></li>          
	</ol>
</div>

  <!-- Main content -->
  <section class="content">
    <div class="container-fluid">
      <div class="panel panel-default">
        <div class="panel-heading">
          <h3 class="panel-title"><i class="fa fa-list"></i> 商品属性</h3>
        </div>
        <div class="panel-body">
          <div class="navbar navbar-default">
              <form action="" id="search-form2" class="navbar-form form-inline" method="post" onsubmit="return false">
                <div class="form-group">
                  <select name="type_id" id="type_id" class="form-control">
                    	<option value="">所有模型</option>
                      <#list goodsModel as model>
                          <#if typeId??>
                              <option value="${model.id}" <#if (model.id==typeId)> selected</#if> >${model.name}</option>
                          <#else>
                              <option value="${model.id}">${model.name}</option>
                          </#if>

                      </#list>
                  </select>
                </div>                  
                <div class="form-group">                 
	                <button type="submit" onclick="ajax_get_table(1)" id="button-filter" class="btn btn-primary pull-right">
	                 <i class="fa fa-search"></i> 筛选
	                </button>
                </div>
                <button type="button" onclick="location.href='商品模型-2-属性列表-添加属性.html'" class="btn btn-primary pull-right">
                 <i class="fa fa-plus"></i> 添加属性
                </button>
              <input name="__hash__" value="fd6059588f758033044de1141d249844_02bad2de3b662123d3092c856167c3f1" type="hidden"></form>
          </div>
          <div id="ajax_return"><form method="post" enctype="multipart/form-data" target="_blank" id="form-goodsType">
    <div class="table-responsive">
        <table class="table table-bordered table-hover">
            <thead>
            <tr>
                <th style="width: 1px;" class="text-center"><input onclick="$('input[name*=\'selected\']').prop('checked', this.checked);" type="checkbox"></th>                
                <th class="sorting text-left">ID</th>
                <th class="sorting text-left">属性名称</th>
                <th class="sorting text-left">所属模型</th>
                <th class="sorting text-left">属性值的输入方式</th>
                <th class="sorting text-left">可选值列表</th>
                <th class="sorting text-center">筛选</th>
                <th class="sorting text-left">排序</th>
                <th class="sorting text-right">操作</th> 
            </tr>
            </thead>
            <tbody id="goodsContent">
            <#--<#list goodsAttribute as attribute>
            </#list>-->
            </tbody>
        </table>
    </div>
<input name="__hash__" value="9dfb4325c77ab60718bef22df39852ac_8ac79714c92a6310791e7291bbb4c681" type="hidden"></form>
<div class="row">
    <div class="col-sm-3 text-left"></div>
    <div class="col-sm-9 text-right">
        <div class="dataTables_paginate paging_simple_numbers"><ul class="pagination" id="pageContent">    </ul></div></div>
</div>
<script>
    // 点击分页触发的事件
    $(".pagination  a").click(function(){
        cur_page = $(this).data('p');
        ajax_get_table('search-form2',cur_page);
    });
 
</script></div>
        </div>
      </div>
    </div>
    <!-- /.row --> 
  </section>
  <!-- /.content --> 
</div>
<!-- /.content-wrapper -->

<!-- 编写商品模板 -->
<script type="template" id="goodsTemplate">
    {{ for(var i = 0; i < it.length; i++){ }}
    <tr>
        <td class="text-center">
            <input name="selected[]" value="6" type="checkbox">
        </td>
        <td class="text-right">{{=it[i].attrId}}</td>
        <td class="text-left">{{=it[i].attrName}}</td>
        <td class="text-left">{{=it[i].typeId}}</td>
        <td class="text-left">{{=it[i].attrInputType}}</td>
        <td class="text-left"></td>
        <td class="text-center">
            <img src="${ctx}/static/images/yes.png" onclick="changeTableVal('goods_attribute','attr_id','329','attr_index',this)" width="20" height="20">
        </td>
        <td class="text-left">
            <input onchange="updateSort('goods_attribute','attr_id','329','order',this)" onkeyup="this.value=this.value.replace(/[^\d]/g,'')" onpaste="this.value=this.value.replace(/[^\d]/g,'')" size="4" value="50" type="text">
        </td>
        <td class="text-right">
            <a href="商品模型-2-属性列表-添加属性.html" data-toggle="tooltip" title="" class="btn btn-primary" data-original-title="编辑"><i class="fa fa-pencil"></i></a>
            <a href="javascript:del_fun('/index/Admin/Goods/delGoodsAttribute/id/329');" id="button-delete6" data-toggle="tooltip" title="" class="btn btn-danger" data-original-title="删除"><i class="fa fa-trash-o"></i></a></td>
    </tr>
    {{ } }}
</script>

<!-- 编写分页模板 -->
<script type="template" id="pageTemplate">
    {{ if(it.hasPreviousPage){ }}
    <li class="paginate_button prev">
        <a href="javascript:ajax_get_table('{{=it.prePage}}');">上一页</a>
    </li>
    {{ } }}

    {{ for(var i = 1; i <= it.pages; i++){ }}
    <li class="paginate_button
        {{ if(i == it.pageNum){ }}
        active
        {{ } }}
        ">
        <a href="javascript:ajax_get_table('{{=i}}');">{{=i}}</a>
    </li>
    {{ } }}

    {{ if(it.hasNextPage){ }}
    <li class="paginate_button next">
        <a href="javascript:ajax_get_table('{{=it.nextPage}}');">下一页</a>
    </li>
    {{ } }}
</script>

<script>
    $(document).ready(function () {
        // ajax 加载商品列表
        ajax_get_table(1);

    });

    //ajax抓取页面 page为当前第几页
    function ajax_get_table(page) {
        var size=10;
        $.ajax({
            url: "${ctx}/goods/model/attribute",
            type: "GET",
            data: {
                id:$('#type_id option:selected') .val(),
                pageNum: page,
                pageSize: size
            },
            dataType: "JSON",
            success: function (result) {
                console.log(result);
                if (200 == result.code) {
                    if (result.pageInfo.list.length > 0) {
                        //获取商品列表模板
                        var goodsTemp = doT.template($("#goodsTemplate").text());
                        //填充数据
                        $("#goodsContent").html(goodsTemp(result.pageInfo.list));
                        //获取分页模板
                        var pageTemp = doT.template($("#pageTemplate").text());
                        //填充数据
                        $("#pageContent").html(pageTemp(result.pageInfo));
                    } else {
                        layer.msg("该分类或品牌暂无商品信息！");
                    }
                } else {
                    layer.msg("该分类或品牌暂无商品信息！");
                }
            },
            error: function (result) {
                console.log(result)
            }
        });
    }

    // ajax 抓取页面 form 为表单id  page 为当前第几页
   /* function ajax_get_table(form,page){
		cur_page = page; //当前页面 保存为全局变量
            $.ajax({
                type : "POST",
                url:"/index?m=Admin&c=goods&a=ajaxGoodsAttributeList&pageNum="+page,//+tab,
                data : $('#'+form).serialize(),// 你的formid
                success: function(data){
                    $("#ajax_return").html('');
                    $("#ajax_return").append(data);
                }
            });
        }*/
	 
</script>


</body></html>
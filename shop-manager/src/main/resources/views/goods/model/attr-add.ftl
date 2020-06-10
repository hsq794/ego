<!-- 设置项目根路径全局变量 -->
<#assign ctx=request.contextPath/>
<!DOCTYPE html>
<html>
<head>
    <#include "../../head.ftl">
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
  <meta name="__hash__" content="4f6b0fa49bbc753aefcdb9e57f808c4a_975a0691766e2b5a565f48898bb2c171"></head>
  <body style="background-color:#ecf0f5;">
 

<div class="wrapper">
    <div class="breadcrumbs" id="breadcrumbs">
	<ol class="breadcrumb">
	<li><a href="javascript:void();"><i class="fa fa-home"></i>&nbsp;&nbsp;后台首页</a></li>
	        
	        <li><a href="javascript:void();">商品管理</a></li>    
	        <li><a href="javascript:void();">添加修改属性</a></li>          
	</ol>
</div>

    <section class="content">
        <!-- Main content -->
        <div class="container-fluid">
            <div class="pull-right">
                <a href="javascript:history.go(-1)" data-toggle="tooltip" title="" class="btn btn-default" data-original-title="返回"><i class="fa fa-reply"></i></a>
            	<a href="javascript:;" class="btn btn-default" data-url="http://www.ego.cn/Doc/Index/article/id/1009/developer/user.html" onclick="get_help(this)"><i class="fa fa-question-circle"></i> 帮助</a>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="fa fa-list"></i> 商品属性</h3>
                </div>
                <div class="panel-body">
                    <ul class="nav nav-tabs">
                        <li class="active"><a href="#tab_tongyong" data-toggle="tab">商品属性</a></li>
                    </ul>
                    <!--表单数据-->
                    <form method="post" id="addEditGoodsAttributeForm">                    
                        <!--通用信息-->
                    <div class="tab-content">                 	  
                        <div class="tab-pane active" id="tab_tongyong">
                           
                            <table class="table table-bordered">
                                <tbody>
                                <tr>
                                    <td>属性名称：</td>
                                    <td>
                                        <input <#if goodsAttribute.attrName??>value="${goodsAttribute.attrName}"</#if> name="attrName" type="text" >
                                        <span id="err_attr_name" style="color:#F00; display:none;"></span>
                                    </td>
                                </tr>  
                                <tr>
                                    <td>所属商品类型：</td>
                                    <td>
                                        <select name="typeId" id="type_id" >
                                            <option value="">请选择</option>
                                            <#list goodsModel as model>
                                                <option value="${model.id}" <#if goodsAttribute.typeId??><#if (model.id==goodsAttribute.typeId)>selected</#if></#if> >${model.name}</option>
                                            </#list>
                                        </select>
                                        <span id="err_type_id" style="color:#F00; display:none;"></span>                                        
                                    </td>
                                </tr>  
                                <tr>
                                    <td>能否进行检索：</td>
                                    <td>

                                        <input value="0" name="attrIndex" <#if goodsAttribute.attrIndex??> <#if goodsAttribute.attrIndex==0>checked="checked"</#if ></#if > type="radio">不需要检索
                                        <input value="1" name="attrIndex" <#if goodsAttribute.attrIndex??> <#if goodsAttribute.attrIndex==1>checked="checked"</#if ></#if > type="radio">关键字检索

                                        <!--<input type="radio" value="2" name="attr_index"   />范围检索-->
                                    </td>
                                </tr>  

                                <tr>
                                    <td>属性是否可选：</td>
                                    <td>
                                        <input type="radio" value="0" name="attrType" <#if goodsAttribute.attrType??><#if goodsAttribute.attrType==0>checked="checked"</#if ></#if>  />唯一属性
                                        <input type="radio" value="1" name="attrType" <#if goodsAttribute.attrType??><#if goodsAttribute.attrType==1>checked="checked"</#if ></#if> />单选属性
                                        <input type="radio" value="2" name="attrType" <#if goodsAttribute.attrType??><#if goodsAttribute.attrType==2>checked="checked"</#if ></#if> />复选属性
                                    </td>
                                </tr>

                                <tr>
                                    <td>该属性值的录入方式：</td>
                                    <td>
                                        <input value="0" name="attrInputType" <#if goodsAttribute.attrInputType??><#if goodsAttribute.attrInputType==0>checked="checked"</#if ></#if >  type="radio">手工录入
                                        <input value="1" name="attrInputType" <#if goodsAttribute.attrInputType??><#if goodsAttribute.attrInputType==1>checked="checked"</#if ></#if >  type="radio">从下面的列表中选择（一行代表一个可选值）
                                        <input value="2" name="attrInputType" <#if goodsAttribute.attrInputType??><#if goodsAttribute.attrInputType==2>checked="checked"</#if ></#if >  type="radio">多行文本框
                                    </td>
                                </tr>  
                                <tr>
                                    <td>可选值列表：</td> 
                                    <td>
                                    <textarea rows="5" cols="30" name="attrValues"> <#if goodsAttribute.attrValues?? >${goodsAttribute.attrValues}</#if> </textarea>
                                    录入方式为手工或者多行文本时，此输入框不需填写。
                                    <span id="err_attr_values" style="color:#F00; display:none;"></span>
                                    </td>
                                </tr>

                                </tbody>                                
                                </table>
                        </div>                           
                    </div>              
                    <div class="pull-right">
                        <input name="attrId" <#if goodsAttribute.attrId?? >value="${goodsAttribute.attrId}"</#if> type="hidden">
                        <button class="btn btn-primary" title="" data-toggle="tooltip" type="button" onclick="ajax_submit_form('addEditGoodsAttributeForm','${ctx}/goods/model/attribute/addOrUpdate','${ctx}/goods/model/attribute/addPage','${ctx}/goods/model/attributeList');" data-original-title="保存"><i class="fa fa-save"></i></button>
                    </div>
			    <input name="__hash__" value="4f6b0fa49bbc753aefcdb9e57f808c4a_975a0691766e2b5a565f48898bb2c171" type="hidden"></form><!--表单数据-->
                </div>
            </div>
        </div>    <!-- /.content -->
    </section>
</div>

</body></html>
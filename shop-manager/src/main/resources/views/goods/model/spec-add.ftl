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
  <meta name="__hash__" content="a233532f2a8e0afcb9726f917e56fc98_0f966e0492f97242652d5d5f6a9a5e9a"></head>
  <body style="background-color:#ecf0f5;">
 

<div class="wrapper">
    <div class="breadcrumbs" id="breadcrumbs">
	<ol class="breadcrumb">
	<li><a href="javascript:void();"><i class="fa fa-home"></i>&nbsp;&nbsp;后台首页</a></li>
	        
	        <li><a href="javascript:void();">商品管理</a></li>    
	        <li><a href="javascript:void();">添加修改规格</a></li>          
	</ol>
</div>

    <section class="content">
        <!-- Main content -->
        <div class="container-fluid">
            <div class="pull-right">
                <a href="javascript:history.go(-1)" data-toggle="tooltip" title="" class="btn btn-default" data-original-title="返回"><i class="fa fa-reply"></i></a>
            	<a href="javascript:;" class="btn btn-default" data-url="http://www.ego.cn/Doc/Index/article/id/1008/developer/user.html" onclick="get_help(this)"><i class="fa fa-question-circle"></i> 帮助</a>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="fa fa-list"></i> 商品规格</h3>
                </div>
                <div class="panel-body">
                    <ul class="nav nav-tabs">
                        <li class="active"><a href="#tab_tongyong" data-toggle="tab">商品规格</a></li>
                    </ul>
                    <!--表单数据-->
                    <form method="post" id="addEditSpecForm">                    
                        <!--通用信息-->
                    <div class="tab-content">                 	  
                        <div class="tab-pane active" id="tab_tongyong">

                            <table class="table table-bordered">
                                <tbody>
                                <tr>
                                    <td>规格名称：</td>
                                    <td>
                                        <input <#if goodsSpec.specName??>value="${goodsSpec.specName}"</#if> name="specName" type="text">
                                        <span id="err_name" style="color:#F00; display:none;"></span>                                        
                                    </td>
                                </tr>  
                                <tr>
                                    <td>所属商品类型：</td>
                                    <td>

                                        <select name="typeId" id="type_id" >

                                            <option value="">请选择</option>
                                            <#list typeList as list>

                                            <option value="${list.id}" <#if goodsSpec.typeId??><#if (goodsSpec.typeId==list.id)>selected</#if></#if> >${list.name}</option>
                                           <#-- <option value="32">相机</option>
                                            <option value="4">手机</option>
                                            <option value="31">电池、电源、充电器</option><option value="8">化妆品</option><option value="9">精品手机</option><option value="30">洗衣机</option><option value="29">冰箱</option><option value="16">路由器</option><option value="15">平板电脑</option><option value="13">衣服</option><option value="17">网络盒子</option><option value="18">电视</option><option value="19">家纺</option><option value="20">吸顶灯</option><option value="21">床</option><option value="22">雨伞</option><option value="23">餐具</option><option value="24">毛呢大衣</option><option value="25">针织衫</option><option value="26">文胸</option><option value="27">香水</option><option value="28">珠宝</option><option value="34">测试</option>
                                      -->
                                            </#list>
                                        </select>

                                        <span id="err_type_id" style="color:#F00; display:none;"></span>                                        
                                    </td>
                                </tr>  
                                
                                <#--<tr style="display:none;">
                                    <td>能否进行检索：</td>
                                    <td>
                                        <input value="0" name="search_index" checked="checked" .="" type="radio">不需要检索
                                        <input value="1" name="search_index" checked="checked" type="radio">关键字检索
                                        <!--<input type="radio" value="2" name="search_index"   />范围检索&ndash;&gt;
                                    </td>
                                </tr>  -->
                                <tr>
                                    <td>规格项：</td> 
                                    <td>
                                    <textarea rows="5" cols="30" name="specValue"><#if goodsSpec.specValue??>${goodsSpec.specValue}</#if></textarea>
									一行为一个规格项
                                    <span id="err_items" style="color:#F00; display:none;"></span>
                                    </td>
                                </tr>       
                                <tr>
                                    <td>排序：</td>
                                    <td>
                                        <input <#if goodsSpec.specOrder??>value="${goodsSpec.specOrder}"</#if> name="specOrder" type="text">
                                        <span id="err_order" style="color:#F00; display:none;"></span>                                        
                                    </td>
                                </tr>                                                           
                                </tbody>                                
                                </table>

                        </div>                           
                    </div>              
                    <div class="pull-right">
                        <input name="specId" <#if goodsSpec.specId??>value="${goodsSpec.specId}"</#if> type="hidden">
                        <button class="btn btn-primary" title="" data-toggle="tooltip" type="button" onclick="ajax_submit_form('addEditSpecForm','${ctx}/goods/spec/save','${ctx}/goods/spec/add','${ctx}/goods/model/spec','specId');" data-original-title="保存"><i class="fa fa-save"></i></button>
                    </div>
			    <input name="__hash__" value="a233532f2a8e0afcb9726f917e56fc98_0f966e0492f97242652d5d5f6a9a5e9a" type="hidden"></form><!--表单数据-->
                </div>
            </div>
        </div>    <!-- /.content -->
    </section>
</div>

</body></html>
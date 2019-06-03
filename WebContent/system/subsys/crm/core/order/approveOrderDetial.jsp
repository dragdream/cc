<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
	String sid = request.getParameter("sid")==null?"0":request.getParameter("sid");
    String customerName = TeeStringUtil.getString(request.getParameter("customerName"), null);
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/userheader.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	<title>订单详情</title>
	
	<style>

.secondMenu{
	position:absolute;
	right:101%;
	top:0;
	background-color:#fff;
	display:none;
	border:1px solid #eee;
}
.secondMenu li{
    width:100px;
	height:30px;
	padding:0 20px;
	line-height:30px;
	
}
.secondMenu li:hover{
	background-color:#daeeff;
}
</style>
	
<script type="text/javascript" >
var sid = "<%=sid%>";
var customerName = "<%=customerName%>";
/**
   初始化列表
 */
var managerPerName = '';
var managerPerId = "";
var orderManagerId = "";
function doInit(){
	
	$("body").on("mouseover",".hasMenu",function(){
		$(".secondMenu").show();
	});
	$("body").on("mouseleave",".hasMenu",function(){
		$(".secondMenu").hide();
	});
	
	if(sid!="" && sid!=null && sid!="null"){
		var url = "<%=contextPath%>/TeeCrmOrderController/getInfoBySid.action?sid="+sid;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			bindJsonObj2Cntrl(json.rtData);
			managerPerName = json.rtData.managePersonName;
			managerPerId = json.rtData.managePersonId;
			orderManagerId = json.rtData.orderApprovalId;
			orderStatus = json.rtData.orderStatusDesc;
			addMenu(orderStatus);
		}
		
		getProductList(sid);
	}
}

//获取产品集合
function getProductList(sid){
	var url = "<%=contextPath%>/TeeCrmOrderController/getProductItem.action?sid="+sid;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		var productList = json.rtData;
	 	$.each( productList, function(i, item){
			addSaleFollowProcuctItem(item,2);
		}); 
	}
}

//更多操作中根据订单状态追加操作
function addMenu(data){
	var str;
	$(".btn-content").empty();
    if(loginPersonId==orderManagerId){
		if(data == "确认中"){
			$(".btn-group").show();
			str = '<li onclick="agree('+sid+');"><a href="javascript:void(0);">同意</a></li>'+
			      '<li onclick="reject('+sid+');"><a href="javascript:void(0);">驳回</a></li>';
			}else{
				$(".btn-group").hide();
			}
	}else{
		$(".btn-group").hide();
	}
	
	$(".btn-content").append(str);
}

/**
 * 新增产品明细行
 @param item ： 产品或者对象，
 @param type ： 操作类型   1-选择产品 2-数据加载 其他-点击新增
 */
function addSaleFollowProcuctItem(item , optType){
	var product_no_value = "";
	var product_name_value = "";
	var priductsModel_value = "";
	var units_value = "";
	var item_number_input_value = 1;
	var item_price_input_value = 0;
	var item_total_amount_input_value = 0;
	var productsId = 0;
	if(item){
		product_no_value = item.productsNo;
		product_name_value = item.productsName;
		priductsModel_value = item.productsModel;
		units_value = item.unitsDesc;
		item_number_input_value = item.productsNumber;
		item_price_input_value = item.price;
		item_total_amount_input_value = item.totalAmount;
		productsId = item.productsId;
		if(!item.productsId){//编辑
			productsId = item.sid;//选择产品
		}
	}
	//产品编号
	var product_no_input = $("<span>").addClass("BigInput easyui-validatebox").html(product_no_value);
	var productNo=$("<td>").append(product_no_input);
/* 	product_no_input.validatebox({ 
		required:true 
	});  */
	//产品名称 
	var product_name_input = $("<span>").addClass("BigInput easyui-validatebox").html(product_name_value);
	var productName=$("<td>").append(product_name_input);
	/* product_name_input.validatebox({ 
		required:true 
	}); */
	
	//产品类型
	var priductsModel=$("<td>").append($("<span>").addClass("BigInput").html(priductsModel_value));
	
	var units=$("<td>").append($("<span>").addClass("BigSelect").html(units_value));
	
	//数量
	var item_number_input = $("<span>").addClass("BigInput easyui-validatebox")
					.attr({"size":"5" , "name":"item_number","maxlength":"10"})
					.html(item_number_input_value);
	var productsNumber=$("<td>").append(item_number_input);
	/* item_number_input.validatebox({ 
		required:true ,
		validType:'integeZero[]'
	});  */

	//价格
	var item_price_input = $("<span>").addClass("BigInput easyui-validatebox")
		.attr({"size":"5" , "name":"item_price","maxlength":"10"})
		.html(item_price_input_value);
	var price = $("<td>").append(item_price_input);
	
	if(optType == 1){//直接选择产品
		item_total_amount_input_value = item_number_input_value * item_price_input_value;
	}
	//合计
	var item_total_amount_input = $("<span>").addClass("BigInput")
		.attr({"size":"5" , "name":"item_total_amount"})
		.html(item_total_amount_input_value);
	var totalAmount=$("<td>").append(item_total_amount_input);
	var price=$("<td>").append(item_price_input);
	
	var tr=$("<tr>").addClass("TableData");
	tr.append(productNo);
	tr.append(productName);
	tr.append(priductsModel);
	tr.append(units);
	tr.append(productsNumber);
	tr.append(price);
	tr.append(totalAmount);
	$("#productList").append(tr);
	
	if(optType == 1 || optType == 2){
		var old_product_total_amount = $("#product_total_amount").html();
		if(old_product_total_amount == ''){
			old_product_total_amount = 0;
		}
		$("#product_total_amount").html(item_total_amount_input_value + parseFloat(old_product_total_amount,10));
	}
	
}

//同意
function agree(sid){
	 $.MsgBox.Confirm ("提示", "确定同意此订单？",function(){
		 var url=contextPath+"/TeeCrmOrderController/agree.action?sid="+sid+"&orderStatus=2";
		 var jsonRs = tools.requestJsonRs(url,null);
			if(jsonRs.rtState){
				opener.$.MsgBox.Alert_auto("操作成功！");
				opener.datagrid.datagrid("unselectAll");
				opener.datagrid.datagrid('reload');
				CloseWindow();
			}else{
				$.MsgBox.Alert_auto(jsonRs.rtMsg);
			}
		 });
}

//驳回
function reject(sid){
		  var title = "驳回";
		  var url = contextPath + "/system/subsys/crm/core/order/reject.jsp?sid="+sid;
		  bsWindow(url ,title,{width:"600",height:"300",buttons:
				[
				 {name:"确定",classStyle:"btn-alert-blue"},
			 	 {name:"关闭",classStyle:"btn-alert-gray"}
				 ]
				,submit:function(v,h){
				var cw = h[0].contentWindow;
				if(v=="确定"){
					cw.doSaveOrUpdate(function(){
						opener.$.MsgBox.Alert_auto("操作成功！");
						opener.datagrid.datagrid("unselectAll");
						opener.datagrid.datagrid('reload');
						CloseWindow();
					});
				}else if(v=="关闭"){
					return true;
				}
			}});
}


/**
 * 删除
 */
function deleteById(sid){
	$.MsgBox.Confirm("提示","确定删除这个订单？删除后不可恢复！",function(){
		var url = contextPath+ "/TeeCrmOrderController/delById.action?sid="+sid;
		var json = tools.requestJsonRs(url, {sid:sid});
		if (json.rtState) {
			$.MsgBox.Alert_auto(json.rtMsg);
			opener.$.MsgBox.Alert_auto("删除成功！");
			opener.datagrid.datagrid("unselectAll");
			opener.datagrid.datagrid('reload');
			CloseWindow();
		} else {
			$.MsgBox.Alert_auto(json.rtMsg);
		}
	
	});
}

//编辑
function edit(){
	window.location.href = "<%=contextPath%>/system/subsys/crm/core/order/addOrUpdate.jsp?sid=" + sid +"&customerName="+customerName;
}



</script>
</head>
<body style="padding-left: 10px;padding-right: 10px;overflow-x:hidden;" onload="doInit();">

<div class="topbar clearfix" id="toolbar">
    <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/crm/img/icon_ddxq.png">
		<span class="title">{<%=customerName %>}--订单详情</span>
	</div>
     <div class="btn-group fr" style="margin-right: 20px;margin-top: 5px;display: none;">
		  <button type="button" class="btn-win-white btn-menu" >
		    更多操作<span class="caret-down"></span>
		  </button>
		  <ul class="btn-content">
		  </ul>
		</div>
</div>

	<table style="width: 100%;margin-top: 10px;">
	    <div style="margin-top: 15px;margin-bottom: 5px;">
	         <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align: text-top;"/>
		    &nbsp;<span style="font-size: 16px;line-height: 16px;color: #0050aa;font-family: 'MicroSoft YaHei';">基本信息</span>
		    <span style="padding-top: 10px;padding-bottom: 5px;" class="basic_border_grey fl"></span> 
	    
	    </div>

		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				客户名称：
			</td>
		    <td name ="customerName" id="customerName">
            </td>
		</tr>
		<tr>
            <td width="150" style="text-indent:15px;line-height: 30px;">商机名称：</td>
            <td align="left" name="chanceName" id="chanceName">
           </td>
        </tr>
        <tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				销售订单编号：
			</td>
		    <td name ="orderNo" id="orderNo">
            </td>
		</tr>
        <tr>
           <td width="150" style="text-indent:15px;line-height: 30px;">下单日期：</td>
		   <td id="orderTimeDesc" name="orderTimeDesc">
		   </td>
        </tr>
  </table>
  <table style="width: 100%;margin-top: 10px;">
	    <div style="margin-top: 15px;margin-bottom: 5px;">
	         <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align: text-top;"/>
		    &nbsp;<span style="font-size: 16px;line-height: 16px;color: #0050aa;font-family: 'MicroSoft YaHei';">订单明细</span>
		    <span style="padding-top: 10px;padding-bottom: 5px;" class="basic_border_grey fl"></span> 
	    
	    </div>
		<tr>
		    <td  width="150px;" style="text-indent: 15px;line-height: 30px;"> 产品信息：
	    	</td>
	    	<td colspan="2"  align="left" style="text-align: left;"> 
	    		 <div style="padding:0px 25px 0px 25px;">
	    			 <table class="TableList" style="width:100%" align="center" >
		    		 	<tr>
		    		 		<td class="TableData" align="center" style="background:#f0f0f0">产品编号</td>
		    		 		<td  class="TableData" align="center" style="background:#f0f0f0">产品名称</td>
		    		 		<td class="TableData" align="center" style="background:#f0f0f0">类别</td>
		    		 		<td class="TableData" align="center" style="background:#f0f0f0">计量单位</td>
		    		 		<td class="TableData" align="center" style="background:#f0f0f0">数量</td>
		    		 		<td class="TableData" align="center" style="background:#f0f0f0">单价</td>
		    		 		<td class="TableData" align="center" style="background:#f0f0f0">合计</td>
		    		 	</tr>
		    		 	<tbody id="productList">
		    		 	
		    		 	</tbody>
		    		 	<tr>
		    		 		<td colspan="8">产品合计：<span id="product_total_amount"></span></td>
		    		 	</tr>
	    		 	</table>
	    		 </div>	
	    	</td>
	    </tr>
</table>
 </br>
<table style="width: 100%;margin-top: 10px;">
	    <div style="margin-top: 15px;margin-bottom: 5px;">
	         <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align: text-top;"/>
		    &nbsp;<span style="font-size: 16px;line-height: 16px;color: #0050aa;font-family: 'MicroSoft YaHei';">收货信息</span>
		    <span style="padding-top: 10px;padding-bottom: 5px;" class="basic_border_grey fl"></span> 
	    
	    </div>
	    <tr>
           <td width="150px;" style="text-indent: 15px;line-height: 30px;">收货人：</td>
			<td name="receiverName" id="receiverName">
			</td>
        </tr>
         <tr>
           <td width="150px;" style="text-indent: 15px;line-height: 30px;">收货人电话：</td>
			<td name="receiverTelephone" id="receiverTelephone">
			</td>
        </tr>
         <tr>
           <td width="150" style="text-indent:15px;line-height: 30px;">交货日期：</td>
		   <td id="transactionsTimeDesc" name="transactionsTimeDesc">
		   </td>
        </tr>
	 </table>
	  </br>
	  	  
 <table style="width: 100%;">
    <tr>
		   <td class=TableHeader colSpan=2 noWrap>
		       <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:text-top;"/>
		       &nbsp;<span style="font-size: 16px;line-height: 16px;color: #0050aa;font-family: 'MicroSoft YaHei';">补充信息</span>
		       <span style="padding-top: 10px;padding-bottom: 5px;" class="basic_border_grey fl"></span>
		       
		   </td>
	</tr>
	<tr>
			<td width="150" style="text-indent:15px;line-height: 30px;">负责人：
			</td>
		    <td name="managePersonName" id="managePersonName">
            </td>
	</tr>
	<tr>
           <td width="150px;" style="text-indent: 15px;line-height: 30px;">订单管理员：</td>
			<td name="orderApprovalName" id="orderApprovalName">
			</td>
    </tr>
	<tr>
			<td width="150" style="text-indent:15px;line-height: 30px;">备注：
			</td>
		    <td name="remark" id="remark">
            </td>
	</tr>
	
</table>
</br>	  
 <table style="width: 100%;">
    <tr>
		   <td class=TableHeader colSpan=2 noWrap>
		       <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:text-top;"/>
		       &nbsp;<span style="font-size: 16px;line-height: 16px;color: #0050aa;font-family: 'MicroSoft YaHei';">其他</span>
		       <span style="padding-top: 10px;padding-bottom: 5px;" class="basic_border_grey fl"></span>
		       
		   </td>
	</tr>
	<tr>
			<td width="150" style="text-indent:15px;line-height: 30px;">状态：
			</td>
		    <td name="orderStatusDesc" id="orderStatusDesc">
            </td>
	</tr>
	
	<tr>
			<td width="150" style="text-indent:15px;line-height: 30px;">创建人：
			</td>
		    <td name="addPersonName" id="addPersonName">
            </td>
	</tr>
	<tr>
			<td width="150" style="text-indent:15px;line-height: 30px;">创建时间：
			</td>
		    <td name="createTimeDesc" id="createTimeDesc">
            </td>
	</tr>
	<tr>
			<td width="150" style="text-indent:15px;line-height: 30px;">最后变化时间：
			</td>
		    <td name="lastEditTimeDesc" id="lastEditTimeDesc">
            </td>
	</tr>

</table>

</body>

</html>
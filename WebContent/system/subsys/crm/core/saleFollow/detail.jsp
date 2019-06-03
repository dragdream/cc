<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%
	String sid = request.getParameter("sid")==null?"0":request.getParameter("sid");
	String model = TeeAttachmentModelKeys.CRM;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@ include file="/header/upload.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crm.js"></script>
<style>
	td{
		line-height:28px;
		min-height:28px;
	}
</style>

<script>
var sid = "<%=sid%>";
function doInit(){
	if(sid!="" && sid!=null && sid!="null"){
		var url = "<%=contextPath%>/TeeCrmSaleFollowController/getByIdForDetail.action?sid="+sid;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			bindJsonObj2Cntrl(json.rtData);
			if(json.rtData.isRemind==1){
				$("#isRemind").html("是");
			}else{
				$("#isRemind").html("否");
			}
			/**
			*处理附件
			*/
			var  attachmodels = json.rtData.attacheModels;
			for(var i=0;i<attachmodels.length;i++){
				var fileItem = tools.getAttachElement(attachmodels[i]);
				$("#attachments").append(fileItem);
			}
		}
		
		getProductList(sid);
	}
}

function getProductList(saleFollowId){
	var url = "<%=contextPath%>/TeeCrmSaleFollowController/getProductItem.action?saleFollowId="+saleFollowId;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		var productList = json.rtData;
		$.each( productList, function(i, item){
			addSaleFollowProcuctItem(item,2);
		});
	}
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
	if(item){
		product_no_value = item.productsNo;
		product_name_value = item.productsName;
		priductsModel_value = item.productsModel;
		units_value = item.unitsDesc;
		item_number_input_value = item.productsNumber;
		item_price_input_value = item.price;
		item_total_amount_input_value = item.totalAmount;
	}
	//产品编号
	var product_no_input = $("<span>").addClass("BigInput easyui-validatebox").html(product_no_value);
	var productNo=$("<td>").append(product_no_input);
	product_no_input.validatebox({ 
		required:true 
	}); 
	//产品名称 
	var product_name_input = $("<span>").addClass("BigInput easyui-validatebox").html(product_name_value);
	var productName=$("<td>").append(product_name_input);
	product_name_input.validatebox({ 
		required:true 
	});
	
	//产品类型
	var priductsModel=$("<td>").append($("<span>").addClass("BigInput").html(priductsModel_value));
	
	var units=$("<td>").append($("<span>").addClass("BigSelect").html(units_value));
	
	//数量
	var item_number_input = $("<span>").addClass("BigInput easyui-validatebox")
					.attr({"size":"5" , "name":"item_number","maxlength":"10"})
					.html(item_number_input_value);
	var productsNumber=$("<td>").append(item_number_input);
	item_number_input.validatebox({ 
		required:true ,
		validType:'integeZero[]'
	}); 

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

</script>
</head>
<body onload="doInit();" style="margin:0 auto;padding:0 auto;text-align:center;font-size:12px;margin-top:10px;">
<form id="form1" name="form1">
	<table style="width:90%;font-size:12px;" class='TableBlock' align="center">
		<tr class="TableHeaer" align='left'>
			<td colspan="4" class="TableHeader"  style="text-align: left;">
					本次跟踪信息：
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td>
				跟踪客户<span style="color:red;font-weight:bold;">*</span>：
			</td>
			<td>
				<span class="BigInput" type="text" id ="customerName" name='customerName'></span>
			</td>
			<td>
				跟踪方式：
				</td>
			<td>
				<span id="followTypeDesc" name="followTypeDesc" class="BigSelect"></span>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td>
				联系人：
				</td>
			<td>
				<span id="contantsName" name="contantsName" class="BigInput"></span>
			</td>
			<td>
				跟踪时间：
				</td>
			<td>
				<span type="text" id='followDateDesc'  name='followDateDesc'></span>
			</td>
		</tr >
		<tr class='TableData' align='left'>
			<td>
				跟踪状态：
			</td>
			<td colspan='3'>
				<span id="followResultDesc" name="followResultDesc">
				</span>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td>
				附件：
			</td>
			<td colspan="3">
		       	<span id="attachments"></span>
		    </td>
		</tr>
		<tr class='TableData' align='left'>
			<td>
				跟踪内容：
			</td>
			<td colspan='3'>
				<span id="followContent" name="followContent" class="BigTextarea" cols='60' rows='10'></span>
			</td>
		</tr>
	</table>
	<table style="width:90%;font-size:12px" class='TableBlock' align="center">
		<tr class="TableHeaer" align='left'>
			<td colspan="4" class="TableHeader"  style="text-align: left;">
					下次跟踪信息：
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td>
				下次联系人：
			</td>
			<td>
				<span type="text" class="BigInput" name="nextFollowUserName" id="nextFollowUserName" ></span>
			</td>
			<td>
				下次跟踪时间：
				</td>
			<td>
				<span type="text" id='nextFollowTimeDesc'  name='nextFollowTimeDesc' ></span>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td>
				下次跟踪内容：
			</td>
			<td colspan='3'>
				<span id="nextFollowContent" name="nextFollowContent"></span>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td>
				是否提醒：
			</td>
			<td colspan='3'>
				<span name="isRemind" id="isRemind"></sapn>
			</td>
		</tr>
		<tr align="left">
	    	<td nowrap class="TableHeader" colspan="4"  align="left" style="text-align: left;"> 产品信息
	    	</td>
	    </tr>
		<tr align="left">
	    	<td nowrap class="" colspan="4"  align="left" style="text-align: left;"> 

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
		<br/>
		<div id="control" style='margin:0 auto;text-align:center; height:28px;line-height:28px;width:"90%"'>
		<input id="sid" name="sid" type='hidden'value="<%=sid %>"/>
		<input id="back" name="back" type='button' class="btn btn-primary" value='返回' onclick='history.go(-1);'/>
	</div>
	<br/><br/>
</form>
</body>
</html>
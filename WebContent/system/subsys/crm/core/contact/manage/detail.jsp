<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
	String model = TeeAttachmentModelKeys.CRM;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@ include file="/header/upload.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crm.js"></script>
<script>
var sid = "<%=sid%>";
var CRM_PRODUCTS_UNITS_STR = "";
function doInit(){

	

	var list = getCrmCodeByParentCodeNo("PRODUCTS_UNITS_TYPE","");// 计量单位
	for(var i = 0; i <list.length ; i++){
		var prcTemp = list[i];
		CRM_PRODUCTS_UNITS_STR = CRM_PRODUCTS_UNITS_STR + "<option value='"+prcTemp.codeNo+"'>" + prcTemp.codeName + "</option>";
	}
	if( sid > 0){
		var url = "<%=contextPath%>/teeCrmContractController/getById.action?sid="+sid;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			var prc = json.rtData;
			bindJsonObj2Cntrl(prc);
			var contractSignDate = getFormatDateTimeStr(prc.contractSignDate ,'yyyy-MM-dd');
			$("#contractSignDate").html(contractSignDate);
			$("#contractDate").html(getFormatDateTimeStr(prc.contractStartDate ,'yyyy-MM-dd') + " 至  " + getFormatDateTimeStr(prc.contractEndDate ,'yyyy-MM-dd'));

			var productItemModel = prc.productItemModel;//产品明细
			$.each( productItemModel, function(i, item){
				addContactProcuctItem(item,2);
			});

			var recvPaymentModel = prc.recvPaymentModel;//付款明细
			$.each( recvPaymentModel, function(i, item){
				
				addContactRecvPaymentItem(item,2);
			});
		
			
			//附件
			var  attachmodels = prc.attachmodels;
			for(var i=0;i<attachmodels.length;i++){
				var temp = attachmodels[i];
				temp["priv"] = 3;
				var fileItem = tools.getAttachElement(temp);
				$("#attachments").append(fileItem);
			}
		}else{
			alert(json.rtMsg);
		}
	}

}

/**
 *获取产品明细列表转JSON数组
 */
function getProductItem(){
	var itemList = $("#contactList").find("tr");
	var productItem = new Array();
	for(var i =0; i <itemList.length ; i++){
		
		var itemTemp = itemList[i];
		var productsNo = $(itemTemp.cells[0]).find("input").val();
		var productsName = $(itemTemp.cells[1]).find("input").val();
		var productsModel = $(itemTemp.cells[2]).find("input").val();
		var units = $(itemTemp.cells[3]).find("select").val();
		var productsNumber = $(itemTemp.cells[4]).find("input").val();
		var price = $(itemTemp.cells[5]).find("input").val();
		
		var totalAmount = $(itemTemp.cells[6]).find("input").val();
		var itemp = {productsNo : productsNo , productsName: productsName , productsModel: productsModel,
				units:units,productsNumber:productsNumber,price:price , totalAmount:totalAmount};
		productItem.push(itemp);
	}
	return productItem;
}

/**
 *  获取回款明细列表转JSON数组
 */
function getRecvPaymentItem(){
	var itemList = $("#recvPaymentsList").find("tr");
	var productItem = new Array();
	var ids = "";
	for(var i =0; i <itemList.length ; i++){
		
		var itemTemp = itemList[i];
		var planRecvDate = $(itemTemp.cells[0]).find("input").val();
		var recvPayAmount = $(itemTemp.cells[1]).find("input").val();
		var recvPayParcent = $(itemTemp.cells[2]).find("input").val();
		var recvDate = $(itemTemp.cells[3]).find("input").val();
		var recvPayPerson = $(itemTemp.cells[4]).find("input").val();
		
		
		var makeInvice = $(itemTemp.cells[5]).find("select").val();
		var inviceNumberValue = $(itemTemp.cells[6]).find("input").val();
		var inviceDate = $(itemTemp.cells[7]).find("input").val();
		var remark = $(itemTemp.cells[8]).find("input").val();
		var sid = $(itemTemp.cells[9]).find("input[name='recvPaymentSid']").val();
		var itemp = {planRecvDate : planRecvDate , recvPayAmount: recvPayAmount , recvPayParcent: recvPayParcent,
				recvDate:recvDate,recvPayPerson:recvPayPerson,makeInvice:makeInvice ,inviceNumber:inviceNumberValue
				,inviceDate:inviceDate , remark:remark ,recvPaymentSid:sid};
		productItem.push(itemp);
		if(sid > 0){
			ids = ids + sid + ",";
		}
	}
	var temp = {recvPaymentList : productItem , ids : ids};
	return temp;
}



/**
 * 新增产品明细行
 @param item ： 产品或者对象，
 @param type ： 操作类型   1-选择产品 2-数据加载 其他-点击新增
 */
function addContactProcuctItem(item , optType){
	var product_no_value = "";
	var product_name_value = "";
	var priductsModel_value = "";
	var units_value = "";
	var item_number_input_value = 1;
	var item_price_input_value = 0;
	var item_total_amount_input_value = 0;
	var unitsDesc = "";
	if(item){
		product_no_value = item.productsNo;
		product_name_value = item.productsName;
		priductsModel_value = item.productsModel;
		units_value = item.units;
		unitsDesc = item.unitsDesc;
		item_number_input_value = item.productsNumber;
		item_price_input_value = item.price;
		item_total_amount_input_value = item.totalAmount;
	}
	//产品编号

	var productNo=$("<td >").append(product_no_value).addClass("TableData");
	//产品名称 
	var productName=$("<td>").append(product_name_value).addClass("TableData");

	
	//产品类型
	var priductsModel=$("<td>").append(priductsModel_value).addClass("TableData");
	
	var units=$("<td>").append(unitsDesc).addClass("TableData");
	
	//数量
	var productsNumber=$("<td>").append(item_number_input_value).attr({"align":"center"}).addClass("TableData");
	//价格
	var price = $("<td>").append(item_price_input_value).attr({"align":"center"}).addClass("TableData");
	if(optType == 1){//直接选择产品
		item_total_amount_input_value = item_number_input_value * item_price_input_value;
	}
	//合计
	var totalAmount=$("<td>").append(item_total_amount_input_value).attr({"align":"center"}).addClass("TableData");

	var tr=$("<tr>");
	tr.append(productNo);
	tr.append(productName);
	tr.append(priductsModel);
	tr.append(units);
	tr.append(productsNumber);
	tr.append(price);
	tr.append(totalAmount);
	$("#contactList").append(tr);
	
	if(optType == 1 || optType == 2){
		//getcantact_total_amount();
		var old_cantact_total_amount = $("#cantact_total_amount").html();
		if(old_cantact_total_amount == ''){
			old_cantact_total_amount = 0;
		}
		$("#cantact_total_amount").html(item_total_amount_input_value + parseFloat(old_cantact_total_amount,10));
		var old_item_total_amount_input_value = $("#contractAmount").val();
		if(old_item_total_amount_input_value == ''){
			old_item_total_amount_input_value = 0;
		}
		$("#contractAmount").val(item_total_amount_input_value + parseFloat(old_item_total_amount_input_value,10));
		
	}
	
}

/**
 * 获取合同总金额
 */
function getcantact_total_amount(){	
	var itemList = $("#contactList").find("input[name='item_total_amount']");
	var cantact_total_amount = 0;
	for(var i = 0; i <itemList.length ; i++){
		var item = itemList[i];
		var total_amount = 0;
		var temp_total_amount = item.value;
		if(temp_total_amount == ""){
			temp_total_amount = 0;
		}
		total_amount = parseFloat(temp_total_amount,10);
		cantact_total_amount = cantact_total_amount + total_amount;
	}
	$("#cantact_total_amount").empty();
	//alert(cantact_total_amount)
	$("#cantact_total_amount").html(cantact_total_amount);
	$("#contractAmount").val(cantact_total_amount);
}
/**
 * 点击产品调用函数
 */
function selectProductCallBackFunc(dataList){
	for(var i = 0 ; i<dataList.length ; i++){
		var item = dataList[i];
		item["productsNumber"] = 1;
		addContactProcuctItem(item , 1);
	}
}


/**
 * 设置回款信息
 */
function setPaymentMethod(value){
	if(value == '0'){//一次支付
	
	}else if(value == '1'){//多次支付
	
	}
}



/**
 * 新增回款明细行
 @param optType:操作类型  2-数据加载  其他-新增
 */
function addContactRecvPaymentItem(item , optType){
	var planRecvDateValue = "";// 预计回款日期
	var recvPayAmountValue = 0;// 回款金额
	var recvPayParcentVaule = 0;//回款百分比
	var recvDateValue = "";// 实际回款日期
	var recvPayPersonValue = "";//收款人
	var remarkValue = "";// 备注
	var makeInviceValue = "0";// 是否开发票
	var inviceNumberValue = "";// 	发票号
	var makeInviceValueDesc = "";
	var inviceDateValue = "";// 发票日期
	
	if($("#paymentMethod").val() == '0'  &&  $("#recvPaymentsList").find("tr").length >= 1){
		alert("付款方式为“一次支付”时只能添加一种付款信息！");
		return;
	}
	if(item){
		recvPaymentSid = item.sid;
		planRecvDateValue =  getFormatDateTimeStr(item.planRecvDate ,'yyyy-MM-dd');
		recvPayAmountValue = item.recvPayAmount;
		recvPayParcentVaule = item.recvPayParcent;
		recvDateValue = getFormatDateTimeStr(item.recvDate, "yyyy-MM-dd");
		recvPayPersonValue = item.managerUserName;
		remarkValue = item.remark;
		makeInviceValue = item.makeInvice;
		if(makeInvice == '1'){
			makeInviceValueDesc = "是";
		}
		inviceNumberValue = item.inviceNumber;
		inviceDateValue = getFormatDateTimeStr(item.inviceDate , "yyyy-MM-dd");
	}
	//预计回款时间
	var planRecvDate=$("<td class='TableData'>").append(planRecvDateValue).addClass("TableData");
	//回款金额
	var recvPayAmount=$("<td>").append(recvPayAmountValue).attr({"align":"center"}).addClass("TableData");
	
	//回款百分比
	var recvPayParcent = $("<td>").append(recvPayParcentVaule).attr({"align":"center"}).addClass("TableData");
	//实际回款时间
	var recvDate=$("<td>").append(recvDateValue).addClass("TableData");
	//收款人 
	var recvPayPerson = $("<td>").append(recvPayPersonValue).addClass("TableData");
	//是否开发票 
	var makeInvice = $("<td>").append(makeInviceValueDesc).addClass("TableData");
	//发票号
	var inviceNumber =$("<td>").append(inviceNumberValue).addClass("TableData");
	//发票日期
	var inviceDate = $("<td>").append(inviceDateValue).addClass("TableData");
	//备注
	var remark =$("<td>").append(remarkValue).addClass("TableData");
	//private String contractId;// 合同ID
	
	var tr=$("<tr>");
	tr.append(planRecvDate);
	tr.append(recvPayAmount);
	tr.append(recvPayParcent);
	tr.append(recvDate);
	tr.append(recvPayPerson);

	tr.append(makeInvice);
	tr.append(inviceNumber);
	tr.append(inviceDate);
	tr.append(remark);
	$("#recvPaymentsList").append(tr);
	
	if( optType == 2){
		//getcantact_total_amount();
		var recv_payments_total_amount = $("#recv_payments_total_amount").html();
		if(recv_payments_total_amount == ''){
			recv_payments_total_amount = 0;
		}
		$("#recv_payments_total_amount").html(recvPayAmountValue + parseFloat(recv_payments_total_amount,10));
				
	}
}

/**
 * 获取回款总金额
 */
function getrecvPayment_total_amount(){	
	var itemList = $("#recvPaymentsList").find("input[name='recvPayment_total_amount']");
	var cantact_total_amount = 0;
	for(var i = 0; i <itemList.length ; i++){
		var item = itemList[i];
		var total_amount = 0;
		var temp_total_amount = item.value;
		if(temp_total_amount == ""){
			temp_total_amount = 0;
		}
		total_amount = parseFloat(temp_total_amount,10);
		cantact_total_amount = cantact_total_amount + total_amount;
	}
	$("#recv_payments_total_amount").empty();
	//alert(cantact_total_amount)
	$("#recv_payments_total_amount").html(cantact_total_amount);
}


</script>
</head>
<body onload="doInit();" style="">
<form  method="post" name="form1" id="form1" >
	<table class="TableBlock" width="90%" align="center">
	    <tr>
	    	<td nowrap class="TableHeader" colspan="4"  style="text-align: left;"> <span  class="Big3">基本信息</span></td>
	    </tr>
	    <tr>
			<td nowrap class="TableData" width="120">合同编号：</td>
		    <td nowrap class="TableData" name="contractNo" >
		       
		    </td>
		    <td nowrap class="TableData" width="120">合同名称：<span style="color:red;"></span></td>
		    <td nowrap class="TableData" name="contractName" >
		        
		    </td>
	   </tr>
	   <tr>
			<td nowrap class="TableData" > 货币类别：</td>
		    <td nowrap class="TableData" name="currencyTypeDesc" >
		      
		    </td>
		    <td nowrap class="TableData" >合同状态：</td>
		    <td nowrap class="TableData"  name="contractStatusDesc">
		       >
		    </td>
	   </tr>
	   <tr>
			<td nowrap class="TableData" > 合同类型：</td>
		    <td nowrap class="TableData"  name="contractCodeDesc" > 
		       	
		    </td>
		    <td nowrap class="TableData" >结算方式：</td>
		    <td nowrap class="TableData"  name="accountsMethodDesc">
		       
		    </td>
	   </tr> 
	   <tr>
			<td nowrap class="TableData" > 合同签订日期：</td>
		    <td nowrap class="TableData"  name="contractSignDate" id="contractSignDate" >
		  		
		    </td>
		    <td nowrap class="TableData" >合同有效日期：</td>
		    <td nowrap class="TableData" id="contractDate">
		      
		    </td>
	    </tr>
	    <tr>
			<td nowrap class="TableData" >成交金额：</td>
		    <td nowrap class="TableData" name="contractAmount" >
		  	
		    </td>
		    <td nowrap class="TableData" ></td>
		    <td nowrap class="TableData">  
		    </td>
	    </tr>
	  	<tr align="left">
	    	<td nowrap class="TableHeader" colspan="4"  align="left" style="text-align: left;"> 产品信息
	    	</td>
	    </tr>
	    <tr align="left">
	    	<td nowrap class="" colspan="4"  align="left" style="text-align: left;"> 

	    		 <div style="padding:0px 25px 0px 25px;">
	    			 <table class="TableBlock" style="width:100%" align="center" >
		    		 	<tr>
		    		 		<td class="TableData" align="center">产品编号</td>
		    		 		<td  class="TableData" align="center">产品名称</td>
		    		 		<td class="TableData" align="center">类别</td>
		    		 		<td class="TableData" align="center">计量单位</td>
		    		 		<td class="TableData" align="center">数量</td>
		    		 		<td class="TableData" align="center">单价</td>
		    		 		<td class="TableData" align="center">合计</td>
		    		 	</tr>
		    		 	<tbody id="contactList">
		    		 	
		    		 	</tbody>
		    		 	<tr>
		    		 		<td colspan="8">合同总额：<span id="cantact_total_amount"></span></td>
		    		 	</tr>
	    		 	</table>
	    		 </div>	
	    	</td>
	    </tr>
	  
	  	<tr align="left">
	    	<td nowrap class="TableHeader" colspan="4"  align="left" style="text-align: left;"> 关联信息
	    	</td>
	    </tr>
	   <tr>
			<td nowrap class="TableData" >客户名称：</td>
		    <td nowrap class="TableData" id="customerInfoName" name="customerInfoName">
		              
		    </td>
		    <td nowrap class="TableData" >责任人：<span style=""></span></td>
		    <td nowrap class="TableData" name="responsibleUserName" id="responsibleUserName" >
		       </td>
	   </tr>
	    <tr>
			<td nowrap class="TableData" > 收款银行：</td>
		    <td nowrap class="TableData"  name="bueBank">
		    </td>
		    <td nowrap class="TableData" >付款方式：</td>
		    <td nowrap class="TableData" id="paymentMethodDesc" >
		     
		    </td>
	     </tr>
	 	 <tr>
			<td nowrap class="TableData" > 备注：</td>
		    <td nowrap class="TableData" colspan="3"  name="remark">    </td>
	     </tr>
	     <tr>
			<td nowrap class="TableData" > 附件：</td>
		    <td nowrap class="TableData" colspan="3">
		       	<span id="attachments"></span>
					<input type="hidden" id="dbAttachSid" name="dbAttachSid" value="">
					<div id="fileContainer2"></div>			
		    </td>
	     </tr>
	    <tr align="left" id="recv_payments_header" style="">
	    	<td nowrap class="TableHeader" colspan="4"  align="left" style="text-align: left;"> 付款信息
	    	</td>
	    </tr>
	    <tr align="left" id="recv_payments_info" style="">
	    	<td nowrap class="" colspan="4"  align="left" style="text-align: left;"> 
	    		 <div style="padding:0px 25px 0px 25px;">
	    			 <table class="TableList" style="width:100%" align="center" >
		    		 	<tr>
		    		 		<td class="TableData" align="center">预计回款日期</td>
		    		 		<td  class="TableData" align="center">回款金额</td>
		    		 		<td class="TableData" align="center">回款百分比</td>
		    		 		<td class="TableData" align="center">实际回款日期</td>
		    		 		<td class="TableData" align="center">收款人</td>
		    		 		<td class="TableData" align="center">是否开发票</td>
		    		 		<td class="TableData" align="center">发票号</td>
		    		 		<td class="TableData" align="center">发票日期</td>
		    		 		<td class="TableData" align="center">备注</td>
		    		 		</tr>
		    		 	<tbody id="recvPaymentsList">
			    		 	
		    		 	</tbody>
		    		 	<tr>
		    		 		<td colspan="9">付款总额：<span id="recv_payments_total_amount"></span></td>
		    		 	</tr>
	    		 	</table>
	    		 </div>	
	    	</td>
	    </tr>
	    
	</table>
</form>
<br>
</body>
</html>
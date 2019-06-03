<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
	String model = TeeAttachmentModelKeys.CRM_CUSTOMER_CANTRACT;
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
	getCrmCodeByParentCodeNo("CURRENCY_TYPE","currencyType");//货币类型
	getCrmCodeByParentCodeNo("CONTRACT_STATUS","contractStatus");// 合同状态
	getCrmCodeByParentCodeNo("CONTRACT_CODE","contractCode");// 合同类型 
	getCrmCodeByParentCodeNo("ACCOUNTS_METHOD","accountsMethod");// 结算方式
	

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
			$("#contractSignDate").val(contractSignDate);
			$("#contractStartDate").val(getFormatDateTimeStr(prc.contractStartDate ,'yyyy-MM-dd'));
			$("#contractEndDate").val(getFormatDateTimeStr(prc.contractEndDate ,'yyyy-MM-dd'));
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
				var fileItem = tools.getAttachElement(attachmodels[i]);
				$("#attachments").append(fileItem);
			}
		}else{
			alert(json.rtMsg);
		}
	}
	doInitUpload();
}

/**
 * 初始化附件上传
 */
function doInitUpload(){
	//多附件快速上传
	swfUploadObj = new TeeSWFUpload({
		fileContainer:"fileContainer2",//文件列表容器
		uploadHolder:"uploadHolder2",//上传按钮放置容器
		valuesHolder:"attachmentSidStr",//附件主键返回值容器，是个input
		quickUpload:true,//快速上传
		showUploadBtn:false,//不显示上传按钮
		queueComplele:function(){//队列上传成功回调函数，可有可无
			
		},
		renderFiles:true,//渲染附件
		post_params:{model:"<%=model%>"}//后台传入值，model为模块标志
	});
}

/**
 * 保存
 */
function commit(){

	if($("#form1").form("validate")){
		
		if($("#paymentMethod").val() == '0'  &&  $("#recvPaymentsList").find("tr").length > 1){
			alert("付款方式为“一次支付”时只能添加一种付款信息！");
			return;
		}
		var itemList = getProductItem();//产品明细
		var str = tools.jsonArray2String(itemList);
		var recvPaymentListStr = "[]";
		var recvPaymentIds = "";
		var temp = getRecvPaymentItem();//付款明细
		var recvPaymentList = temp.recvPaymentList;
		recvPaymentListStr = tools.jsonArray2String(recvPaymentList);
		recvPaymentIds = temp.ids;
		
		var param = tools.formToJson($("#form1"));
		var url = contextPath+"/teeCrmContractController/addOrUpdate.action";
		param["productsList"] = str;
		param["recvPaymentListStr"] = recvPaymentListStr;
		param["recvPaymentIds"] = recvPaymentIds;
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
			top.$.jBox.tip(json.rtMsg,"success" ,{timeout:1500});
			returnBackFunc();
		}else{
			top.$.jBox.tip(json.rtMsg,"error");
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
		var productsId = $(itemTemp.cells[7]).find("input[name='productsId']").val();
		
		var itemp = {productsNo : productsNo , productsName: productsName , productsModel: productsModel,
				units:units,productsNumber:productsNumber,price:price , totalAmount:totalAmount,productsId:productsId};
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
		var recvPayPerson = ""; /* $(itemTemp.cells[4]).find("input").val(); */
		
		
		var makeInvice = $(itemTemp.cells[4]).find("select").val();
		var inviceNumberValue = $(itemTemp.cells[5]).find("input").val();
		var inviceDate = $(itemTemp.cells[6]).find("input").val();
		var remark = $(itemTemp.cells[7]).find("input").val();
		var sid = $(itemTemp.cells[8]).find("input[name='recvPaymentSid']").val();
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
	var productsId = 0;
	if(item){
		product_no_value = item.productsNo;
		product_name_value = item.productsName;
		priductsModel_value = item.productsModel;
		units_value = item.units;
		item_number_input_value = item.productsNumber;
		item_price_input_value = item.price;
		item_total_amount_input_value = item.totalAmount;
		productsId = item.productsId;
		if(!item.productsId){//编辑
			productsId = item.sid;//选择产品
		}
	} 
	//产品编号
	var product_no_input = $("<input>").addClass("BigInput easyui-validatebox").val(product_no_value);
	var productNo=$("<td>").append(product_no_input);
	product_no_input.validatebox({ 
		required:true 
	}); 
	//产品名称 
	var product_name_input = $("<input>").addClass("BigInput easyui-validatebox").val(product_name_value);
	var productName=$("<td>").append(product_name_input);
	product_name_input.validatebox({ 
		required:true 
	});
	
	//产品类型
	var priductsModel=$("<td>").append($("<input>").addClass("BigInput").val(priductsModel_value));
	
	var units=$("<td>").append($("<select>").append(CRM_PRODUCTS_UNITS_STR).addClass("BigSelect").val(units_value));
	
	//数量
	var item_number_input = $("<input>").addClass("BigInput easyui-validatebox")
					.attr({"size":"5" , "name":"item_number","maxlength":"10"})
					.val(item_number_input_value)
					.blur( function () {onBlurFunc(this) } )
	var productsNumber=$("<td>").append(item_number_input);
	item_number_input.validatebox({ 
		required:true ,
		validType:'integeZero[]'
	}); 

	//价格
	var item_price_input = $("<input>").addClass("BigInput easyui-validatebox")
		.attr({"size":"5" , "name":"item_price","maxlength":"10"})
		.val(item_price_input_value)
		.blur( function () {onBlurFunc(this) }) ;
	var price = $("<td>").append(item_price_input);
	item_price_input.validatebox({ 
		required:true ,
		validType:'pointTwoNumber[]'
	}); 
	
	if(optType == 1){//直接选择产品
		item_total_amount_input_value = item_number_input_value * item_price_input_value;
	}
	//合计
	var item_total_amount_input = $("<input>").addClass("BigInput")
		.attr({"size":"5" , "name":"item_total_amount"})
		.val(item_total_amount_input_value)
		.blur( function () {
			getcantact_total_amount()
			}) ;
	var totalAmount=$("<td>").append(item_total_amount_input);
	var price=$("<td>").append(item_price_input);
	item_total_amount_input.validatebox({ 
		required:true ,
		validType:'pointTwoNumber[]'
	}); 
	
	//private String contractId;// 合同ID
	var productsIdInput = "<input type='hidden' name='productsId' value='" + productsId + "'>";
	var delTd=$("<td>").append($("<input>",{
		type:'button',
		value:'-'
	}).addClass("btn btn-primary").click(function(){
		$(this).parent("td").parent("tr").remove();
		getcantact_total_amount();
	})).append(productsIdInput);
	
	var tr=$("<tr>");
	tr.append(productNo);
	tr.append(productName);
	tr.append(priductsModel);
	tr.append(units);
	tr.append(productsNumber);
	tr.append(price);
	tr.append(totalAmount);
	tr.append(delTd );
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
 * 事件
 */
function onBlurFunc(obj){
	var item_number = $(obj).parent().parent().find("input[name='item_number']");
	var item_price = $(obj).parent().parent().find("input[name='item_price']");
	var item_total_amount = $(obj).parent().parent().find("input[name='item_total_amount']");
	//alert(item_number.val() + ":"+  item_price.val())
	item_total_amount.val(item_number.val() * item_price.val());
	getcantact_total_amount();
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
	var inviceDateValue = "";// 发票日期
	var recvPaymentSid = 0;//sid
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
		recvPayPersonValue = item.recvPayPerson;
		remarkValue = item.remark;
		makeInviceValue = item.makeInvice;
		inviceNumberValue = item.inviceNumber;
		inviceDateValue = getFormatDateTimeStr(item.inviceDate , "yyyy-MM-dd");
	}
	//预计回款时间
	var planRecvDateInput = $("<input>").addClass("BigInput easyui-validatebox Wdate")
			.attr({"size":"8" ,"maxlength":"10"})
			.val(planRecvDateValue)
			.click(function(){WdatePicker();});
	planRecvDateInput.validatebox({ 
		required:true 
	});
	var planRecvDate=$("<td>").append(planRecvDateInput);
	//回款金额
	var recvPayAmountValueInput = $("<input>").addClass("BigInput easyui-validatebox")
					.attr({"size":"5" ,"maxlength":"10" , "name":"recvPayment_total_amount"})
					.val(recvPayAmountValue)
					.blur( function () {getrecvPayment_total_amount()} );
	var recvPayAmount=$("<td>").append(recvPayAmountValueInput);
	recvPayAmountValueInput.validatebox({ 
		required:true ,
		validType:'pointTwoNumber[]'
	}); 
	
	//回款百分比
	var recvPayParcentVauleInput = $("<input>").addClass("BigInput easyui-validatebox")
					.attr({"size":"5" ,"maxlength":"10"})
					.val(recvPayParcentVaule);
	var recvPayParcent = $("<td>").append(recvPayParcentVauleInput);
	recvPayParcentVauleInput.validatebox({ 
		required:true ,
		validType:'pointTwoNumber[]'
	});
	
	//实际回款时间
	var recvDateValueInput = $("<input>").addClass("BigInput easyui-validatebox Wdate")
			.attr({"size":"8" ,"maxlength":"10"})
			.val(recvDateValue)
			.click(function(){WdatePicker();});

	var recvDate=$("<td>").append(recvDateValueInput);
	
	/* //收款人 
	var recvPayPersonValueInput = $("<input>").addClass("BigInput easyui-validatebox").val(recvPayPersonValue)
				.attr({"size":"7" ,"maxlength":"30"});
	var recvPayPerson = $("<td>").append(recvPayPersonValueInput);
	recvPayPersonValueInput.validatebox({ 
		required:true 
	}); */

	//是否开发票
	var makeInvice = $("<td>").append($("<select>").append("<option value='0'>否</option><option value='1'>是</option>").addClass("BigSelect").val(makeInviceValue));
	
	//发票号
	var inviceNumber =$("<td>").append($("<input>").addClass("BigInput")
			.val(inviceNumberValue)
			.attr({"size":"9" ,"maxlength":"10"}));
	
	
	//发票日期
	var inviceDateValueInput = $("<input>").addClass("BigInput easyui-validatebox Wdate")
			.attr({"size":"7" ,"maxlength":"10"})
			.val(inviceDateValue)
			.click(function(){WdatePicker();});
	var inviceDate = $("<td>").append(inviceDateValueInput);
	
	//备注
	var remark =$("<td>").append($("<input>").addClass("BigInput").attr({"size":"8" ,"maxlength":"10"}).val(remarkValue));
	
	//private String contractId;// 合同ID
	var recvPaymentSidInput = "<input type='hidden' name='recvPaymentSid' value='" + recvPaymentSid + "'>";
	var delTd=$("<td>").append($("<input>",{
		type:'button',
		value:'-'
	}).addClass("btn btn-primary").click(function(){
		$(this).parent("td").parent("tr").remove();

		getrecvPayment_total_amount();
	})).append(recvPaymentSidInput);
	var tr=$("<tr>");
	tr.append(planRecvDate);
	tr.append(recvPayAmount);
	tr.append(recvPayParcent);
	tr.append(recvDate);
	//tr.append(recvPayPerson);

	tr.append(makeInvice);
	tr.append(inviceNumber);
	tr.append(inviceDate);
	tr.append(remark);
	tr.append(delTd);
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


function returnBackFunc(){
	location.href = contextPath + "/system/subsys/crm/core/contact/manage/contactManage.jsp";
}

</script>
</head>
<body onload="doInit();" style="">
<div class="Big3" style="padding:3px 10px">
	<%if(sid > 0){ %>  
		编辑合同
	<%}else{ %>
		新建合同
	<%}%>
</div>
<form  method="post" name="form1" id="form1" >
	<table class="TableBlock" width="90%" align="center">
	    <tr>
	    	<td nowrap class="TableHeader" colspan="4"  style="text-align: left;"> <span  class="Big3">基本信息</span></td>
	    </tr>
	    <tr>
			<td nowrap class="TableData" width="120">合同编号：<span style="color:red;">*</span></td>
		    <td nowrap class="TableData">
		        <input type="text" name="contractNo" class="easyui-validatebox BigInput"  required="true"  size="20" maxlength="50" />&nbsp;
		    </td>
		    <td nowrap class="TableData" width="120">合同名称：<span style="color:red;">*</span></td>
		    <td nowrap class="TableData">
		        <input type="text" name="contractName" class="easyui-validatebox BigInput"  required="true"  size="20" maxlength="100" />&nbsp;
		    </td>
	   </tr>
	   <tr>
			<td nowrap class="TableData" > 货币类别：</td>
		    <td nowrap class="TableData">
		       	<select id="currencyType" name="currencyType" class="BigSelect">
		       	</select>
		    </td>
		    <td nowrap class="TableData" >合同状态：</td>
		    <td nowrap class="TableData">
		        <select id="contractStatus" name="contractStatus" class="BigSelect">
		       	</select>
		    </td>
	   </tr>
	   <tr>
			<td nowrap class="TableData" > 合同类型：</td>
		    <td nowrap class="TableData">
		       	<select id="contractCode" name="contractCode" class="BigSelect">
		       	</select>
		    </td>
		    <td nowrap class="TableData" >结算方式：</td>
		    <td nowrap class="TableData">
		        <select id="accountsMethod" name="accountsMethod" class="BigSelect">
		       	</select>
		    </td>
	   </tr> 
	   <tr>
			<td nowrap class="TableData" > 合同签订日期：<span style="color:red;">*</span></td>
		    <td nowrap class="TableData">
		  		<input type="text" name="contractSignDate" id="contractSignDate" size="10" maxlength="10"    required="true" value="" class="easyui-validatebox BigInput Wdate " onClick="WdatePicker()"  />&nbsp;&nbsp;
		    </td>
		    <td nowrap class="TableData" >合同有效日期：</td>
		    <td nowrap class="TableData">
		      	<input type="text" name="contractStartDate" id="contractStartDate" size="10" maxlength="10" value="" class="BigInput Wdate " onClick="WdatePicker()"  />&nbsp;&nbsp;
		      	至 &nbsp;&nbsp;<input type="text" id="contractEndDate" name="contractEndDate" size="10" maxlength="10" value="" class="BigInput Wdate " onClick="WdatePicker()"   />&nbsp;&nbsp;
		    </td>
	    </tr>
	    <tr>
			<td nowrap class="TableData" >成交金额：</td>
		    <td nowrap class="TableData">
		  		<input type="text" name="contractAmount" id="contractAmount" size="10" maxlength="10" value="0" class="BigInput easyui-validatebox" validType='pointTwoNumber[]' />&nbsp;&nbsp;
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
	    		 	 <input type="button" value="产品查询" class="btn btn-warning btn-xs" title="产品查询" onclick="selectProductInfo('[]' , 'selectProductCallBackFunc')" >&nbsp;&nbsp;
	    			 <table class="TableList" style="width:100%" align="center" >
		    		 	<tr>
		    		 		<td class="TableData" align="center">产品编号</td>
		    		 		<td  class="TableData" align="center">产品名称</td>
		    		 		<td class="TableData" align="center">类别</td>
		    		 		<td class="TableData" align="center">计量单位</td>
		    		 		<td class="TableData" align="center">数量</td>
		    		 		<td class="TableData" align="center">单价</td>
		    		 		<td class="TableData" align="center">合计</td>
		    		 		<td class="TableData" align='center' style="padding-left:5px;"><input type="button" name="addUser" id="addUser" required="true" value="+" class="btn btn-primary" onclick="addContactProcuctItem();"/> </td>
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
			<td nowrap class="TableData" >客户名称：<span style="color:red;">*</span></td>
		    <td nowrap class="TableData">
		        <input type="hidden" name="customerInfoId" id="customerInfoId"  class="easyui-validatebox BigInput"  size="15" maxlength="20" />&nbsp;
		         <input type="text" name="customerInfoName" id="customerInfoName" value=""  class="easyui-validatebox BigInput SmallStatic"  required="true"  readonly/>
	        	<a href="javascript:void(0);" class="orgAdd" onClick="selectCustomerInfo(['customerInfoId','customerInfoName'])">选择客户</a>
	        	<a href="javascript:void(0);" class="orgClear" onClick="clearData('customerInfoId','customerInfoName')">清空</a>
		    </td>
		    <td nowrap class="TableData" >责任人：<span style="color:red;">*</span></td>
		    <td nowrap class="TableData">
		        <input type="hidden" name="responsibleUserId" id="responsibleUserId" value=""/>
	        	<input cols=30 name="responsibleUserName" id="responsibleUserName" rows=2 class="SmallStatic  easyui-validatebox BigInput" wrap="yes" readonly required="true" />
	        	<a href="javascript:void(0);" class="orgAdd" onClick="selectSingleUser(['responsibleUserId','responsibleUserName'])">选择</a>
	        	<a href="javascript:void(0);" class="orgClear" onClick="clearData('responsibleUserId','responsibleUserName')">清空</a>
		    </td>
	   </tr>
	    <tr>
			<td nowrap class="TableData" > 收款银行：<span style="color:red;">*</span></td>
		    <td nowrap class="TableData">
		       	 <input type="text" name="bueBank" class="easyui-validatebox BigInput"  required="true"  maxlength="50" />&nbsp;
		    </td>
		    <td nowrap class="TableData" >付款方式：</td>
		    <td nowrap class="TableData">
		        <select id="paymentMethod" name="paymentMethod" class="BigSelect" onchange="setPaymentMethod(this.value)">
		        	<option value="0">一次付款</option>
		        	<option value="1">多次付款</option>
		       	</select>
		    </td>
	     </tr>
	 	 <tr>
			<td nowrap class="TableData" > 备注：</td>
		    <td nowrap class="TableData" colspan="3">
		       	 <textarea type="text" name="remark" class="easyui-validatebox BigTextarea"   rows="3" cols="80" ></textarea>&nbsp;
		    </td>
	     </tr>
	     <tr>
			<td nowrap class="TableData" > 附件：</td>
		    <td nowrap class="TableData" colspan="3">
		       	<span id="attachments"></span>
					<input type="hidden" id="dbAttachSid" name="dbAttachSid" value="">
	
					<div id="fileContainer2"></div>
					<a id="uploadHolder2" class="add_swfupload">
						<img src="<%=systemImagePath %>/upload/batch_upload.png"/>添加附件
					</a>
					<input id="attachmentSidStr" name="attachmentSidStr" type="hidden"/>
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
		    		 <!-- 		<td class="TableData" align="center">收款人</td> -->
		    		 		<td class="TableData" align="center">是否开发票</td>
		    		 		<td class="TableData" align="center">发票号</td>
		    		 		<td class="TableData" align="center">发票日期</td>
		    		 		<td class="TableData" align="center">备注</td>
		    		 		<td class="TableData" align='center' style="padding-left:5px;"><input type="button" name="addUser" id="addUser" required="true" value="+" class="btn btn-primary" onclick="addContactRecvPaymentItem();"/> </td>
		    		 	</tr>
		    		 	<tbody id="recvPaymentsList">
			    		 	<!-- <tr>
			    		 		<td class="TableData" align="center">
			    		 			<input class="BigInput" type="text" size="8" maxlength="10" onclick="WdatePicker();"></input>
			    		 		</td>
			    		 		<td  class="TableData" align="center">
			    		 			<input class="BigInput" type="text" size="5" maxlength="10"></input>
			    		 		</td>
			    		 		<td class="TableData" align="center">回款百分比</td>
			    		 		<td class="TableData" align="center">
			    		 			<input class="BigInput" type="text" size="5" maxlength="10"></input>
			    		 		</td>
			    		 		<td class="TableData" align="center">
			    		 			<input class="BigInput" type="text" size="8" maxlength="50"></input>
			    		 		</td>
			    		 		<td class="TableData" align="center">
			    		 			<select>
			    		 				<option value="0">否</option>
			    		 				<option value="1">是</option>
			    		 			</select>
			    		 		</td>
			    		 		<td class="TableData" align="center">
			    		 			<input class="BigInput" type="text" size="8" maxlength="50"></input>
			    		 		</td>
			    		 		<td class="TableData" align="center">
			    		 			<input class="BigInput" type="text" size="8" maxlength="10" onclick="WdatePicker();"></input>
			    		 		</td>
			    		 		<td class="TableData" align="center">
			    		 			<input class="BigInput" type="text" size="8" ></input>
			    		 		</td>
			    		 		
			    		 		<td class="TableData" align='center' style="padding-left:5px;"></td>
			    		 	</tr> -->
		    		 	</tbody>
		    		 	<tr>
		    		 		<td colspan="10">付款总额：<span id="recv_payments_total_amount"></span></td>
		    		 	</tr>
	    		 	</table>
	    		 </div>	
	    	</td>
	    </tr>
	     <tr>
		    <td nowrap  class="TableControl" colspan="4" align="center">
		        <input type="hidden" id="sid" name="sid"  value="0">
		        <input type="button" value="保存" class="btn btn-primary" title="保存" onclick="commit()" >&nbsp;&nbsp;
		        <input type="button" value="返回" class="btn btn-primary" title="返回" onClick="returnBackFunc();">
		    </td>
		</tr>
	</table>
</form>
<br>
</body>
</html>
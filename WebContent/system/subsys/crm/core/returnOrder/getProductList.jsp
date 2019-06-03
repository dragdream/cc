<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String callBackFunc = request.getParameter("callBackFunc") == null ? "" : request.getParameter("callBackFunc")  ;
	callBackFunc = callBackFunc.replace("\"", "\\\"");
	String orderId = TeeStringUtil.getString(request.getParameter("orderId"), "0");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品查询</title>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crm.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/core/product/manager/js/product.js"></script>
<script type="text/javascript">
var callBackFunc = "<%=callBackFunc%>";
var parentWindowObj = window.dialogArguments;
var orderId = "<%=orderId%>";
function doInit(){
	getInfoList();
}

var datagrid;
function getInfoList(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/TeeOrderProductsController/getOrderProductsInfoList.action?sid="+orderId,
		pagination:true,
		singleSelect:false,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:true,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:10},
			{field:'productsNo',title:'产品编号',width:50},
			{field:'productsName',title:'产品名称',width:80},
			{field:'productsModel',title:'规格型号',width:50},
			{field:'price',title:'价格',width:50,align:'center'},
			{field:'unitsDesc',title:'计量单位',width:50,align:'center'},
			{field:'productsNumber',title:'数量',width:50,align:'center'},
			{field:'totalAmount',title:'总价格',width:50,align:'center'},
		]]
	});
}



function selectOrderProductInfo(sid,productsName){
	var orderProductInfoArray = xparent["orderProductInfoArray"];
	to_id_field.value=sid;
	to_name_field.value=productsName;
	arguments.length = 2;//参数调用
	arguments[0] = sid;
	arguments[1] = productsName;
	trigger_callback(callBackFunc,arguments); 
	CloseWindow();
}
/**
 * 详情信息
 */
function showInfoFunc(sid){
	var url = contextPath + "/system/subsys/crm/core/product/manager/productDetail.jsp?sid=" + sid;
	location.href = url;
}

//根据条件查询
function doSearch(){

	getInfoList();
}

/**
 * 批量选择
 */
function batchSelectFunc(){
	var selections = $('#datagrid').datagrid('getSelections');
	if(selections.length==0){
		$.MsgBox.Alert_auto("至少选择一项！");
		return ;
	}	
	arguments.length = 1;//参数调用
	arguments[0] = selections;
	trigger_callback(callBackFunc,arguments); 
	
}


</script>
</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px">
	<button class="btn-win-white" onclick="batchSelectFunc()">批量选择数据</button>&nbsp;&nbsp;
	<table id="datagrid" fit="true" ></table>
</body>
</html>
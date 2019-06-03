<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String callBackFunc = request.getParameter("callBackFunc") == null ? "" : request.getParameter("callBackFunc")  ;
	callBackFunc = callBackFunc.replace("\"", "\\\"");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品查询</title>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@include file="/header/upload.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crm.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/core/product/manager/js/product.js"></script>
<script type="text/javascript">
var callBackFunc = "<%=callBackFunc%>";
var parentWindowObj = window.dialogArguments;

function doInit(){
	getInfoList();
}

var datagrid;
function getInfoList(){
	var queryParams=tools.formToJson($("#form1"));
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/crmProductsController/getManageInfoList.action",
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:true,
		queryParams:queryParams,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:10},
			{field:'productsNo',title:'产品编号',width:50},
			{field:'productsName',title:'产品名称',width:80},
			{field:'productsTypeName',title:'产品类别',width:50},
			{field:'productsModel',title:'规格型号',width:50},
			{field:'salePrice',title:'价格',width:50,align:'center'},
			{field:'status',title:'状态',width:50,formatter:function(value, rowData, rowIndex){
				if(value =="0"){
					value = "不可用";
				}else if(value =="1"){
					value = "可用";
				}
				return value;
			}},
			{field:'createTimeStr',title:'创建日期',width:50,formatter:function(value, rowData, rowIndex){
				if(value){
					value = value.substring(0,10);
				}
				return value;
			}}
		]]
	});
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
	<table id="datagrid" fit="true" ></table>
	<div id="toolbar">
		<form id="form1" name="form1" method="post">
		<div style="margin-top:10px;" id="searchDiv">
			<table>
				<tr>
					<td nowrap class="TableData"  width="15%;" >产品编号：</td>
					<td class="TableData" width="35%;" >
						<input style="height:25px;font-family:MicroSoft YaHei;" type="text" name="productsNo" id="productsNo" size="" class="BigInput  easyui-validatebox"  size="15" maxlength="100">
					</td>
					<td nowrap class="TableData"  width="15%;" >产品名称：</td>
					<td class="TableData" width="60%;" >
						<input style="height:25px;font-family:MicroSoft YaHei;" type="text" name="productsName" id="productsName" class="BigInput  easyui-validatebox"  size="15"  maxlength="100">
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData" width="" >录入日期：</td>
					<td class="TableData"  colspan="3">
						<input style="height:25px;font-family:MicroSoft YaHei;" type="text" name="createTimeStrMin" id="createTimeStrMin" size="" class="BigInput  easyui-validatebox" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'createTimeStrMin\',{d:0});}',dateFmt:'yyyy-MM-dd'})" required="true" value="">
						至
						<input style="height:25px;font-family:MicroSoft YaHei;" type="text" name="createTimeStrMax" id="createTimeStrMax" size="" class="BigInput  easyui-validatebox" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'createTimeStrMax\',{d:0});}',dateFmt:'yyyy-MM-dd'})" required="true" value="">
						&nbsp;&nbsp;
						<input style="width: 45px;height: 25px;" type="button"  value="查询" class="btn-win-white" onclick="doSearch();"/>&nbsp;&nbsp;
						<input style="width: 45px;height: 25px;" type="reset" value="重置" class="btn-win-white" >
					</td>
				</tr>
				<tr>
				
			</table>
		</div>
		</form>
		
		<div style="" id="optItem">
			
			<button class="btn-win-white" onclick="batchSelectFunc()">批量选择数据</button>&nbsp;&nbsp;

		</div>

	</div>
</body>
</html>
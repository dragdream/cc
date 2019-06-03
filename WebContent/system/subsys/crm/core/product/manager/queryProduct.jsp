<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品管理综合查询</title>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@include file="/header/upload.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/core/product/manager/js/product.js"></script>
<script type="text/javascript">
function doInit(){
	//getInfoList();
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
		border:false,
		queryParams:queryParams,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:10},
			{field:'productsNo',title:'产品编号',width:50,align:"center"},
			{field:'productsName',title:'产品名称',width:80,align:"center"},
			{field:'productsTypeName',title:'产品类别',width:50,align:"center"},
			{field:'productsModel',title:'规格型号',width:50,align:"center"},
			{field:'price',title:'成本价格',width:50,align:"center"},
			{field:'salePrice',title:'销售价格',width:50,align:"center"},
			{field:'status',title:'是否可用',width:40,align:"center",formatter:statusFunc},
			{field:'createTimeStr',title:'创建日期',width:50,formatter:function(value, rowData, rowIndex){
				if(value){
					value = value.substring(0,10);
				}
				return value;
			}},
			{field:'2',title:'操作',width:70,formatter:function(value, rowData, rowIndex){
				var str = "<a href='#' onclick='showInfoFunc("+rowData.sid+")'>详细信息 </a>";
				str += "&nbsp;&nbsp;<a href='#' onclick='toAddOrUpdate("+rowData.sid+")'>编辑</a>";
				str += "&nbsp;&nbsp;<a href='#' onclick='deleteSingleFunc("+rowData.sid+")'>删除</a>";
				return str;
			}}
		]]
	});
}

/**
 * 新建编辑信息
 */
function toAddOrUpdate(sid){
	var  url = contextPath + "/system/subsys/crm/core/product/manager/addOrUpdateProduct.jsp?sid=" + sid;
	window.location.href = url;
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
	$('#searchDiv').toggle();
	$("#optItem").show();
	$(".datagrid-view").show();
	getInfoList();
}
function toReturn(){
	window.location.reload();
}
</script>
</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px">
	<table id="datagrid" fit="true" ></table>
	<div id="toolbar">
		<div class="base_layout_top" style="position:static">
			<span class="easyui_h1">综合查询</span>
		</div>
		<form id="form1" name="form1" method="post">
		<div style="margin:0 auto;width:100%;" id="searchDiv">
			<table width="90%" class="TableBlock" style="margin:0 auto;margin-top:10px;">
				<tr>
					<td nowrap class="TableData"  width="15%;" >产品编号：</td>
					<td class="TableData" width="35%;" >
						<input type="text" name="productsNo" id="productsNo" size="" class="BigInput  easyui-validatebox"  size="15" maxlength="100">
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData"  width="15%;" >产品名称：</td>
					<td class="TableData" width="60%;" >
						<input type="text" name="productsName" id="productsName" class="BigInput  easyui-validatebox" style="width:145px;"  size="15"  maxlength="100" />
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData"  width="15%;" >生产厂商：</td>
					<td class="TableData" width="35%;" >
						<input type="text" name="manufacturer" id="manufacturer" size="" class="BigInput  easyui-validatebox" maxlength="100">
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData"  width="15%;" >生产地 ：</td>
					<td class="TableData" width="60%;" >
						<input type="text" name="manufacturerAdress" id="manufacturerAdress" size="" class="BigInput  easyui-validatebox" maxlength="100">
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData" width="" >录入日期：</td>
					<td class="TableData"  colspan="3">
						<input type="text" name="createTimeStrMin" id="createTimeStrMin" size="" class="BigInput  easyui-validatebox Wdate" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'createTimeStrMax\',{d:0});}',dateFmt:'yyyy-MM-dd'})"  value="">
						至
						<input type="text" name="createTimeStrMax" id="createTimeStrMax" size="" class="BigInput  easyui-validatebox Wdate" onClick="WdatePicker({minDate:'#F{$dp.$D(\'createTimeStrMin\',{d:0});}',dateFmt:'yyyy-MM-dd'})"  value="">
					</td>
				</tr>
				<tr>
				<tr>
					<td nowrap class="TableData" colspan=4 align="center">
						<input type="button"  value="查询" class="btn btn-primary" onclick="doSearch();"/>&nbsp;&nbsp;
						<input type="reset" value="重置" class="btn btn-default" >
					</td>
				</tr>
			</table>
		</div>
		</form>
		
		<div style="padding:10px;display: none;" id="optItem">
			<button class="btn btn-primary" onclick="toAddOrUpdate(0)">新增产品</button>&nbsp;&nbsp;
			<button class="btn btn-danger" onclick="batchDeleteFunc()">批量删除</button>&nbsp;&nbsp;
			<input type="button"  value="返回" class="btn btn-default" onclick="toReturn();"/>&nbsp;&nbsp;
		</div>

	</div>
</body>
</html>
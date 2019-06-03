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
<title>人才库查询</title>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/validator2.0.jsp"%>
<script type="text/javascript"
	src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/system/core/base/hr/js/hr.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/system/core/base/hr/js/hrPool.js"></script>


<script type="text/javascript">
var callBackFunc = "<%=callBackFunc%>";
var parentWindowObj = window.dialogArguments;
function doInit(){
	getInfoList();
}

var datagrid;
function getInfoList(){
	var queryParams=tools.formToJson($("#form1"));
	queryParams["isQueryType"] = "1";
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/recruitRequirementsController/getRecruitList.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
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
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'requNo',title:'需求编号',width:50},
			{field:'requJob',title:'需求岗位',width:100},
			{field:'requNum',title:'需求人数',width:20,formatter:function(value,rowData,rowIndex){
				if(value){
					value += "";
				}
				return value;
			}},
			{field:'requDeptStrName',title:'需求部门',width:100},
			{field:'requTimeStr',title:'用工日期',width:40,formatter:function(value, rowData, rowIndex){
				return value;
			}},
			{field:'requStatus',title:'需求状态',width:100,hidden:true},
			{field:'recruirementsPriv',title:'需求权限',width:100,hidden:true}
		
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
	if(checkForm()){
		getInfoList();
	}
}

function checkForm(){
	var check = $("#form1").valid(); 
	if(!check){
		return false; 
	}
	return true;
}
/**
 * 批量选择
 */
function batchSelectFunc(){
	var selections = $('#datagrid').datagrid('getSelections');
	if(selections.length==0){
		$.MsgBox.Alert_auto("至少选择一项");
		return ;
	}
	arguments.length = 1;//参数调用
	arguments[0] = selections;
	trigger_callback(callBackFunc,arguments); 
}
</script>
</head>
<body onload="doInit()" style="overflow: hidden; font-size: 12px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar">
		<form id="form1" name="form1" method="post">
			<div style="margin-top: 10px;" id="searchDiv">
				
							需求编号： <input class="BigInput" type="text" id="requNo" name='requNo' size="10" style="height: 23px;"/>&nbsp;&nbsp;&nbsp;
							需求岗位： <input class="BigInput" type="text" id="requJob" name='requJob' size="10" style="height: 23px;" /> &nbsp;&nbsp;&nbsp;
							用工日期： <input class="Wdate BigInput" type="text" id="requTimeStrMin" name='requTimeStrMin' size="8"
							class="BigInput" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  value="" style="height: 23px;width: 110px;"/>&nbsp;&nbsp;&nbsp;
							 至 <input class="Wdate BigInput" type="text" id="requTimeStrMax" name='requTimeStrMax' size="8"
							class="BigInput" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  value="" style="height: 23px;width: 110px;"/>&nbsp;&nbsp;&nbsp; 
							<input type="button" class="btn-win-white" onclick="doSearch();" value="查询">
							 &nbsp;&nbsp;<input type="reset" value="重置" class="btn-win-white">
							 &nbsp;&nbsp;<input type="button" value="批量选择数据" class="btn-win-white" onclick="batchSelectFunc()">
				
			</div>
		</form>

		<!-- <div style="" id="optItem">

			<button class="btn btn-warning" onclick="batchSelectFunc()">批量选择数据</button>
			&nbsp;&nbsp;

		</div> -->

	</div>
</body>
</html>
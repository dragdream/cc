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
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hr.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hrPool.js"></script>


<script type="text/javascript">
var callBackFunc = "<%=callBackFunc%>";
var parentWindowObj = window.dialogArguments;
function doInit(){
	getInfoList();
	var prcs = getHrCodeByParentCodeNo("POOL_POSITION" , "position");//岗位
	var prcs = getHrCodeByParentCodeNo("STAFF_HIGHEST_SCHOOL" , "employeeHighestSchool");//学历
}

var datagrid;
function getInfoList(){
	var queryParams=tools.formToJson($("#form1"));
	queryParams["employeeStatus"] = "1,3";
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/hrPoolController/queryHrPoolList.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		queryParams:queryParams,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'employeeName',title:'应聘者姓名',width:60},
			{field:'sex',title:'性别',width:30},
			{field:'employeeBirthStr',title:'出生日期',width:60,formatter:function(value,rowData,rowIndex){
				return value;
			}},
			{field:'employeePhone',title:'联系电话',width:100},
			{field:'employeeEmail',title:'邮箱',width:100},
			{field:'expectedSalary',title:'薪水',width:60},
			{field:'employeeHighestSchoolDesc',title:'学历',width:60,formatter:function(value,rowData,rowIndex){
				return value;
			}},
			{field:'employeeMajorDesc',title:'专业',width:60,formatter:function(value,rowData,rowIndex){
				return value;
			}},
			{field:'positionDesc',title:'岗位',width:60,formatter:function(value,rowData,rowIndex){
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
				姓名： <input
							type="text" name="employeeName" id="employeeName" 
							style="width: 120px;height: 23px;"
							class="BigInput" size="8" maxlength="100">
							
							&nbsp;&nbsp;&nbsp;&nbsp;期望薪水： <input type="text" name="minExpectedSalary" id="minExpectedSalary"
							style="width: 80px;height: 23px;"
							class="BigInput" size="5" maxlength="8"  validType ='number[]'>
							~
							 <input type="text" name="maxExpectedSalary" id="maxExpectedSalary"
							style="width: 80px;height: 23px;"
							class="BigInput" size="5" maxlength="8"  validType ='number[]'>
						
					
						    &nbsp;&nbsp;&nbsp;&nbsp;学历： <select
							name="employeeHighestSchool" id="employeeHighestSchool"
							class="BigSelect " style="height: 23px;">
								<option value="">请选择</option>

						</select> 
						
						&nbsp;&nbsp;&nbsp;&nbsp;
							岗位： <select name="position" id="position" class="BigSelect" style="height: 23px;">
								<option value="">请选择</option>
						</select>
							<input type="button" class="btn-win-white"
							onclick="doSearch();" value="查询"> &nbsp;&nbsp;
							<input
							type="reset" value="重置" class="btn-win-white">&nbsp;&nbsp;
							<input  onclick="batchSelectFunc()"
							type="button" value="批量选择数据" class="btn-win-white">
			</div>
		</form>

	<!-- 	<div style="" id="optItem">

			<button class="btn btn-warning" onclick="batchSelectFunc()">批量选择数据</button>
			&nbsp;&nbsp;

		</div> -->

	</div>
</body>
</html>
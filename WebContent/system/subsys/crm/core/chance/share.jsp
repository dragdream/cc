<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>共享机会管理</title>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/core/afterSaleService/js/saleService.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crm.js"></script>
<script type="text/javascript">
function doInit(){
	getInfoList();
}

var datagrid;
function getInfoList(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/crmChanceController/getShareChanceList.action",
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
		columns:[[
			//{field:'sid',checkbox:true,title:'ID',width:1},
			{field:'customerName',title:'客户名称',width:60},
			{field:'chanceName',title:'机会名称',width:80},
			{field:'forcastTimeStr',title:'预计成交日期',width:35},
			{field:'forcastCost',title:'预计成交金额',width:35},
			{field:'crUserName',title:'创建人姓名',width:40},
			{field:'createTimeStr',title:'创建时间',width:80},
			{field:'2',title:'操作',width:50,formatter:function(value, rowData, rowIndex){
				var str = "<a href='#' onclick='showInfoChance("+rowData.sid+")'>详情</a>";
				return str;
			}}
		]]
	});
}

//查看详情 
function showInfoChance(sid){
	var url = contextPath + "/system/subsys/crm/core/chance/chanceDetail.jsp?sid=" + sid;
	location.href = url;

}


</script>
</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px">
	<table id="datagrid" fit="true" ></table>
	<div id="toolbar">
		<div style="padding:10px;">
		</div>
	</div>
</body>
</html>
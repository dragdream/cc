<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String sortId = request.getParameter("sortId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<style>
</style>
<script>
var sortId = "<%=sortId%>";
function doInit(){
	$('#datagrid').datagrid({
		url:contextPath+'/flowType/datagrid.action?page=1&rows=10000&sortId='+sortId,
// 		pagination:true,
		singleSelect:true,
		striped: true,
		border: false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'flowName',title:'流程名称',width:50,sortable : true},
			{field:'_manage',title:'操作',width:50,formatter:function(value,rowData,rowIndex){
				var render = [];
				render.push("<a href='javascript:void(0)' onclick='setCondition("+rowData.sid+")'>设置条件</a>");
				render.push("<a href='javascript:void(0)' onclick='setTemplate("+rowData.sid+")'>设置模板</a>");
				return render.join("&nbsp;/&nbsp;");
			}}
		]]
	});

}

function setCondition(flowId){
	$("#frame").attr("src","condition.jsp?flowId="+flowId);
}

function setTemplate(flowId){
	$("#frame").attr("src","template.jsp?flowId="+flowId);
}
</script>
</head>
<body onload="doInit()" style="">
<div style="position:absolute;width:300px;left:0px;top:0px;bottom:0px;">
	<table id="datagrid" fit="true"></table>
</div>
<div style="position:absolute;left:300px;top:0px;bottom:0px;right:0px">
	<iframe id="frame" frameborder="0px" src="tip.jsp" style="width:100%;height:100%"></iframe>
</div>
</body>
</html>
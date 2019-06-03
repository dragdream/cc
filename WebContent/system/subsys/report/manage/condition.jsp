<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String flowId = request.getParameter("flowId");
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
var flowId = "<%=flowId%>";
function doInit(){
	$('#datagrid').datagrid({
		url:contextPath+'/report/datagridCondition.action?page=1&rows=10000&flowId='+flowId,
		singleSelect:true,
		striped: true,
		border: false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'conditionName',title:'条件名称',width:150},
			{field:'_manage',title:'操作',width:50,formatter:function(value,rowData,rowIndex){
				var render = [];
				render.push("<a href='javascript:void(0)' onclick='edit("+rowData.sid+")'>编辑</a>");
				render.push("<a href='javascript:void(0)' onclick='del("+rowData.sid+")'>删除</a>");
				return render.join("&nbsp;/&nbsp;");
			}}
		]]
	});
}

function edit(conditionId){
	openFullWindow(contextPath+'/system/subsys/report/manage/conditionAddOrUpdate.jsp?flowId='+flowId+'&conditionId='+conditionId);
}

function del(conditionId){
	if(window.confirm('是否删除该条件？')){
		var json = tools.requestJsonRs(contextPath+'/report/delCondition.action?conditionId='+conditionId,{});
		if(json.rtState){
			alert("已删除");
			window.location.reload();
		}else{
			alert(json.rtMsg);
		}
	}
}
</script>
</head>
<body onload="doInit()">
<div id="toolbar">
	<button class="btn btn-one" onclick="openFullWindow('conditionAddOrUpdate.jsp?flowId=<%=flowId%>')">添加</button>
</div>
<table id="datagrid"  fit="true"></table>
</body>
</html>
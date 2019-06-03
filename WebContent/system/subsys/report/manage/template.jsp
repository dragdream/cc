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
<%@include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<style>
</style>
<script>
var flowId = "<%=flowId%>";
function doInit(){
	$('#datagrid').datagrid({
		url:contextPath+'/report/datagridTemplate.action?page=1&rows=10000&flowId='+flowId,
		singleSelect:true,
		striped: true,
		border: false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'tplName',title:'模板名称',width:150},
			{field:'_manage',title:'操作',width:50,formatter:function(value,rowData,rowIndex){
				var render = [];
				render.push("<a href='javascript:void(0)' onclick='preview("+rowData.sid+")'>预览</a>");
				render.push("<a href='javascript:void(0)' onclick='edit("+rowData.sid+")'>编辑</a>");
				render.push("<a href='javascript:void(0)' onclick='del("+rowData.sid+")'>删除</a>");
				return render.join("&nbsp;/&nbsp;");
			}}
		]]
	});
}

function edit(templateId){
	openFullWindow('templateAddOrUpdate.jsp?flowId='+flowId+'&templateId='+templateId);
}

function del(templateId){
	if(window.confirm('是否删除该条件？')){
		var json = tools.requestJsonRs(contextPath+'/report/delTemplate.action?templateId='+templateId,{});
		if(json.rtState){
			alert("已删除");
			window.location.reload();
		}else{
			alert(json.rtMsg);
		}
	}
}

function preview(templateId){
	openFullWindow(contextPath+"/system/subsys/report/flow_report_show.jsp?templateId="+templateId);
}
</script>
</head>
<body onload="doInit()">
<div id="toolbar" style="padding:5px">
	<button class="btn-win-white" onclick="openFullWindow('templateAddOrUpdate.jsp?flowId=<%=flowId%>')">添加</button>
</div>
<table id="datagrid"></table>
</body>
</html>
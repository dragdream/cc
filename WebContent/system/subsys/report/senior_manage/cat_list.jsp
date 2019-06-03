<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	TeePerson loginPerson = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script>

function doInit(){
	$('#datagrid').datagrid({
		url:contextPath+'/seniorReportCat/datagrid.action',
		pagination:true,
		singleSelect:true,
		striped: true,
		border: false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		fitColumns:false,//列是否进行自动宽度适应
		pageSize:30,
		columns:[[
			{field:'name',title:'分类名称',width:150},
			{field:'sortNo',title:'排序号',width:150},
			{field:'color',title:'颜色',width:50,formatter:function(value,rowData,rowIndex){
				return "<div style='background:"+value+"'>&nbsp;</div>";
			}},
			{field:'_manage',title:'操作',width:100,formatter:function(value,rowData,rowIndex){
				var render = [];
				render.push("<a href='#' onclick='edit("+rowData.sid+")'>编辑</a>");
				render.push("<a href='javascript:void(0)' onclick='del("+rowData.sid+")'>删除</a>");
				return render.join("&nbsp;&nbsp;");
			}}
		]]
	});
}

function add(){
	window.location = "catAddOrUpdate.jsp";
}

function edit(id){
	window.location = "catAddOrUpdate.jsp?sid="+id;
}

function del(id){
	if(window.confirm("是否要删除该分类？注意：删除后会级联删除分类下报表，请注意！")){
		var json = tools.requestJsonRs(contextPath+"/seniorReportCat/delete.action",{sid:id});
		window.location.reload();
	}
}

</script>
</head>
<body onload="doInit()" >
<div id="toolbar">
	<button class="btn btn-primary" onclick="add()">添加</button>
</div>
<table id="datagrid" fit="true"></table>
</body>
</html>
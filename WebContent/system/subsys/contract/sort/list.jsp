<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>分类设置</title>
	<script type="text/javascript" charset="UTF-8">
	$(function() {
		$('#datagrid').datagrid({
			url:contextPath+'/contractSort/datagrid.action',
			pagination:true,
			singleSelect:true,
			toolbar:'#toolbar',//工具条对象
			border:false,
			idField:'sid',//主键列
			fitColumns : false,
			sortOrder: 'desc',
			striped: true,
			remoteSort: false,
			columns:[[
				{field:'name',title:'类型名称',width:100},
				{field:'categoryName',title:'所属分类',width:100},
				{field:'_manage',title:'操作',formatter:function(value,rowData,rowIndex){
					var render = [];
					render.push("<a href='#' onclick='edit("+rowData.sid+")'>编辑</a>");
					render.push("<a href='javascript:void(0)' onclick='del("+rowData.sid+")'>删除</a>");
					return render.join("&nbsp;&nbsp;");
				}}
			]]
		});
	});
	
	function edit(sid){
		window.location = "addOrUpdate.jsp?sid="+sid;
	}
	
	function del(sid){
		if(window.confirm("是否要删除该分类？注：删除后会级联删除该分类下的类别和合同！请慎重！")){
			var json = tools.requestJsonRs(contextPath+"/contractSort/delete.action?sid="+sid,{});
			window.location.reload();
		}
	}
	</script>
</head>
<body fit="true">
<table id="datagrid" fit="true"></table>
<div id="toolbar">
	<div class="base_layout_top" style="position:static">
		<span class="easyui_h1">类型设置</span>
	</div>
	<div style="padding:10px">
		<button class="btn btn-primary" onclick="window.location = 'addOrUpdate.jsp'">添加</button>
	</div>
	
</div>
</body>
</html>
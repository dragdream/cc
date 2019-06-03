<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>仓库列表</title>
	<script type="text/javascript" charset="UTF-8">
	$(function() {
		$('#datagrid').datagrid({
			url:contextPath+'/repDepository/depositoryList.action',
			singleSelect:true,
			toolbar:'#toolbar',//工具条对象
			border:false,
			idField:'sid',//主键列
			fitColumns:false,
			striped: true,
			remoteSort: true,
			columns:[[
				{field:'code',title:'仓库编号',width:100},
				{field:'name',title:'仓库名称',width:100},
				{field:'managerName',title:'库管员',width:100},
				{field:'_oper',title:'操作',width:200,formatter:function(data,row,index){
					var render = [];
					render.push("<a href='#' onclick='edit("+row.sid+")'>编辑</a>");
					render.push("<a href='javascript:void(0)' onclick='del("+row.sid+")'>删除</a>");
					render.push("<a href='javascript:void(0)' onclick='clear0("+row.sid+")'>清除库存记录</a>");
					return render.join("&nbsp;&nbsp;");
				}}
			]]
		});
	});
	
	function create(){
		window.location = "addOrUpdate.jsp";
	}
	
	function edit(sid){
		window.location = "addOrUpdate.jsp?sid="+sid;
	}
	
	function del(sid){
		if(window.confirm("慎重操作！是否要删除仓库信息？")){
			var json = tools.requestJsonRs(contextPath+"/repDepository/delete.action",{sid:sid});
			window.location.reload();
		}
	}
	
	function clear0(sid){
		if(window.confirm("慎重操作！是否要清除该仓库的库存记录？")){
			var json = tools.requestJsonRs(contextPath+"/repDepository/clear.action",{sid:sid});
			alert("清除成功！");
		}
	}
	
	</script>
</head>
<body style="overflow:hidden">
<div id="toolbar">
	<div class="base_layout_top" style="position:static">
		<span class="easyui_h1">仓库管理</span>
	</div>
	<div style="padding:10px" >
		<button class="btn btn-info" onclick="create()">新建仓库</button>
	</div>
</div>
<table id="datagrid" fit="true"></table>
</body>
</html>
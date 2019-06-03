<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp" %>
<title>业务表管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/bisTable/findTableByDatasource.action?dataSource=<%=request.getParameter("dataSource")%>',
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		//idField:'formId',//主键列
		fitColumns:false,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',title:'业务表名称',width:100,checkbox:true},
			{field:'tableDesc',title:'业务表名称',width:100},
			{field:'tableName',title:'业务表标志',width:100}
		]]
	});
}

function select(){
	var rows = datagrid.datagrid('getSelections');
	if(rows.length==0){
		alert("请选择至少一个业务表");
		return;
	}
	for(var i=0;i<rows.length;i++){
		xparent.appendTableItem({sid:rows[i].sid,index:xparent.globalIndex++,name:rows[i].tableName,desc:rows[i].tableDesc});
	}
	CloseWindow();
}

</script>
</head>
<body onload="doInit()">
<table id="datagrid" fit="true"></table>
<div id="toolbar">
	<div style="text-align:left;">
		<button class="btn btn-primary" onclick="select()">选择</button>
	</div>
</div>

</body>
</html>

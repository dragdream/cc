<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script>
var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/TeeMysqlBackupController/datagrid.action",
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'timeDesc',title:'备份时间',width:100},
			{field:'path',title:'备份路径',width:420},
			{field:'2',title:'操作',width:200,formatter:function(e,rowData,index){
				return "<a href='#' onclick='back("+index+")'>还原</a>";
			}}
		]]
	});
}

/**
 * 还原
 */
function back(index){
	datagrid.datagrid("unselectAll");
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		top.$.jBox.tip("请选择需要修改的内容","info");
		return;
	}
	var sid = selection.sid;
	var url =contextPath+"/TeeMysqlBackupController/backSql.action?sid="+sid;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		alert(json.rtMsg);
	}else{
		alert(json.rtMsg);
	}
	
}
</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar">
		<br/>
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
   int boxId=TeeStringUtil.getInteger(request.getParameter("boxId"), 0);
%>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<title>文件管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="<%=contextPath%>/system/core/base/attend/js/attend.js"></script>
<script type="text/javascript">
var boxId=<%=boxId %>;
var datagrid;
function  doInit(){
	getList();
}
/**
 *查询管理
 */
function getList(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/TeeDamFilesController/getFileListByBoxId.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:false,
		queryParams:{boxId:boxId},
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:200},
			{field:'title',title:'文件标题',width:200},
			{field:'number',title:'编号',width:200},
			{field:'unit',title:'发/来文单位',width:200},
			{field:'retentionPeriodStr',title:'保管期限',width:200},
			{field:'mj',title:'密级',width:200},
			{field:'hj',title:'紧急程度',width:200},
			{field:'createTimeStr',title:'创建日期',width:250},
			{field:'opt_',title:'操作',width:300,formatter:function(value,rowData,rowIndex){
				var opt="<a  href='#' onclick='detail("+rowData.sid+")'>查看</a>&nbsp;&nbsp;&nbsp;";
				opt+="<a  href='#' onclick='edit("+rowData.sid+")'>整理</a>&nbsp;&nbsp;&nbsp;";
			    return opt;
			}}
		]]
	});
	
}

//查看 
function detail(sid){
	var url=contextPath+"/system/core/base/dam/fileManagement/box/detail.jsp?sid="+sid;
    window.location.href=url;
}

//整理
function edit(sid){
	var url=contextPath+"/system/core/base/dam/fileManagement/box/edit.jsp?sid="+sid;
	window.location.href=url;
}

</script>
</head>
<body class="" onload="doInit();">
  <div id="toolbar" class = "clearfix">
  
  </div>
  <table id="datagrid" fit="true"></table>
</body>

</html>
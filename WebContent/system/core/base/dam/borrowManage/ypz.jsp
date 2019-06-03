<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<title>已批准</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="<%=contextPath%>/system/core/base/attend/js/attend.js"></script>
<script type="text/javascript">
var datagrid;
function  doInit(){
	
	query();
}
/**
 *查询管理
 */
function query(){
	var params = tools.formToJson($("#form1"));
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/TeeFileBorrowController/getApproved.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		queryParams:params,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			/* {field:'sid',checkbox:true,title:'ID',width:200}, */
			{field:'viewUserName',title:'借阅人',width:200},
			{field:'viewTimeStr',title:'借阅时间',width:250},
			{field:'fileTitle',title:'文件标题',width:200},
			{field:'fileNumber',title:'文件编号',width:200},
			{field:'fileUnit',title:'发/来文单位',width:250},
			{field:'fileRt',title:'保管期限',width:100},
			{field:'fileMj',title:'密级',width:100},
			{field:'fileHj',title:'紧急程度',width:100},
			{field:'approveTimeStr',title:'审批时间',width:250},
			{field:'opt_',title:'操作',width:200,formatter:function(value,rowData,rowIndex){	
				opt="<a  href='#' onclick='detail("+rowData.fileId+")'>查看</a>&nbsp;&nbsp;&nbsp;";
			    return opt;
			}}
		]]
	});	

}


//查看档案的全部详情
function  detail(sid){
	var url=contextPath+"/system/core/base/dam/fileManagement/file/detail.jsp?sid="+sid;
    openFullWindow(url);
}
</script>
</head>
<body class="" onload="doInit();">
  <table id="datagrid" fit="true"></table>
</body>

</html>
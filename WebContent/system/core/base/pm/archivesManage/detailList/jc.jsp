<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String humanDocSid=request.getParameter("humanDocSid");
    String personName = request.getParameter("personName");
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>奖惩信息</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script>
var datagrid;
var humanDocSid='<%=humanDocSid%>';
var personName='<%=personName%>';
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/TeeHumanSanctionController/datagrid.action?humanDocSid=<%=humanDocSid%>',
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'sanType',title:'奖惩项目',width:100},
			{field:'sanDateDesc',title:'获奖日期',width:100},
			{field:'validDateDesc',title:'工资生效月份',width:100},
			{field:'pays',title:'奖惩金额',width:100},
			{field:'content',title:'说明',width:100},
			{field:'remark',title:'备注',width:100},
			{field:'2',title:'操作',formatter:function(e,rowData){
				return "<a href='javascript:void();' onclick='showDetail("+rowData.sid+")'>查看</a>";
			}}
		]]
	});
}
function showDetail(sid){
	var title="查看详情";
	var url = contextPath+"/system/core/base/pm/archivesManage/jc_detail.jsp?sid="+sid+"&personName="+personName;
	bsWindow(url,title,{width:"700",height:"300",buttons:
		[
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
	     if(v=="关闭"){
	    	datagrid.datagrid("unselectAll");
			return true;
		}
	}});
}
</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px;padding-left: 10px;padding-right: 10px;">
	<table id="datagrid" fit="true"></table>
	<div class="topbar clearfix" id="toolbar">
    <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/base/pm/img/icon_detail.png">
		<span class="title">奖惩管理</span>
	</div>
</div>
	
	<div id="toolbar" style="padding-top: 5px;">
		</br>
	</div>
</body>
</html>
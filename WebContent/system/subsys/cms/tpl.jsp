<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String siteId = request.getParameter("siteId");
	String isNew = request.getParameter("isNew");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp" %>
<%@include file="/header/easyui2.0.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/TeeMenu.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/layout/layout.js"></script>
<script>
var datagrid;
var siteId = <%=siteId%>;
function doInit(){
	
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/cmsSiteTemplate/listTemplates.action?siteId='+siteId,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		pageList: [50,60,70,80,90,100],
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'tplName',title:'模板名称',width:100},
			{field:'tplFileName',title:'文件名',width:100},
			{field:'tplDesc',title:'模板描述',width:100},
			{field:'2',title:'操作',formatter:function(e,rowData){
				var html = [];
				html.push("<a href='javascript:void(0)' onclick='edit("+rowData.sid+")'>编辑</a>");
				html.push("&nbsp;&nbsp;<a href='javascript:void(0)' onclick='del("+rowData.sid+")'>删除</a>");
				return html.join("");
			}}
		]]
	});
}

function commit(){
	
}

function crTpl(){
	openFullWindow(contextPath+"/system/subsys/cms/tplmgr.jsp?isNew=true&siteId="+siteId,"新建模板");
}

function edit(sid){
	openFullWindow(contextPath+"/system/subsys/cms/tplmgr.jsp?siteId="+siteId+"&sid="+sid,"更新模板");
}

function del(sid){
	$.MsgBox.Confirm ("提示", "确认要删除该模板吗？", function(){
		var url = contextPath+"/cmsSiteTemplate/delTemplate.action";
		var json = tools.requestJsonRs(url,{sid:sid});
		if(json.rtState){
			$.MsgBox.Alert_auto("刪除成功！");
			datagrid.datagrid("load");
		}  
	  });
}

</script>
</head>
<body onload="doInit();" style="padding-left: 10px;padding-right: 10px;">
<table id="datagrid" fit="true"></table>
<div id="toolbar" class = "topbar clearfix">
    <div class="fl" style="position:static;">
		<span class="title">模板管理</span>
	</div>
	<div class = "right fr clearfix">
	    <button type="button" class="btn-win-white" onclick="crTpl()">新建模板</button>
	    <button type="button" class="btn-win-white" onclick="window.location='site.jsp'">返回</button>	
	</div>
</div>

</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>

<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css"/>

<script type="text/javascript">
var datagrid;
var id = '<%=id%>';
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/detailController/listByPage.action',
		queryParams:{id:id},
		pagination:true,
		singleSelect:true,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		idField:'uuid',//主键列
		fitColumns:true,//列是否进行自动宽度适应  
		columns:[[
		    {field:'uuid',checkbox : true,}, 
			{field:'detailSeries',title:'编号',width:100},
			{field:'detailChapter',title:'章',width:50},
			{field:'detailStrip',title:'条',width:50},
			{field:'detailFund',title:'款',width:100,},
			{field:'detailItem',title:'项',width:100},
			{field:'detailCatalog',title:'目',width:100},
			{field:'content',title:'内容',width:100},
		]],
		singleSelect: false,
		selectOnCheck: true,
		checkOnSelect: true
	}); 
}

</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px;padding-left: 10px;padding-right: 10px;">
	<table id="datagrid" fit="true">
	</table>
	<div id="toolbar" class="titlebar clearfix">
	<div id="toolbar" class = "topbar clearfix">
	<!-- <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="/common/zt_webframe/imgs/xzbg/dsjgl/icon_dsjgl.png">&nbsp;&nbsp;
		<span class="title">测试填报法律</span>
	</div> -->
	<!-- <div class = "right fr t_btns" >
	    <button style="cursor: pointer;"  onclick="add()"><span style="padding-right: 13px;">新增</span></button>&nbsp;&nbsp;
	    <button style="cursor: pointer;"  onclick="edit()"><span style="padding-right: 13px;">编辑</span></button>&nbsp;&nbsp;
	    <button style="cursor: pointer;"  onclick="del()"><span style="padding-right: 13px;">删除</span></button>&nbsp;&nbsp;
   	</div> -->
    <div style="clear:both"></div>
    </div>
</div>
</body>
</html>
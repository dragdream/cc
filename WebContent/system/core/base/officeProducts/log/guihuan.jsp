<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script>
var showFlag=1;
var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/officeRecordController/datagrid5.action',
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		queryParams:{showFlag:1},
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'proCode',title:'用品编码',width:100},
			{field:'proName',title:'用品名称',width:100},
			{field:'depositoryName',title:'用品库',width:100},
			{field:'categoryName',title:'用品类别',width:100},
			{field:'regCount',title:'领用数量',width:100},
			{field:'originStock',title:'原库存',width:100},
			{field:'operatorName',title:'调度人员',width:100},
			{field:'actionTimeDesc',title:'调度时间',width:100},
			{field:'regUserName',title:'归还人',width:100}
		]]
	});
	$(".datagrid-header-row td div span").each(function(i,th){
		var val = $(th).text();
		 $(th).html("<label style='font-weight: bolder;'>"+val+"</label>");
	});
}


function query(showFlag){
	window.showFlag = showFlag;
	$('#datagrid').datagrid('load',{showFlag:showFlag});
}


</script>
<style>
.datagrid-header-check input, .datagrid-cell-check input
{
	vertical-align:top;
}

</style>
</head>
<body onload="doInit()" style="font-size:12px;padding-left: 10px;padding-right: 10px;">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar" class = "topbar clearfix">
		<!-- <div class="base_layout_top" style="position:static">
			<span class="easyui_h1">归还记录</span>
		</div> -->
		<div class="fl" style="position:static;">
		    <img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/ypjl/icon_归还记录.png">
		    <span class="title">归还记录</span>
	    </div>
<!--		<button class="btn btn-primary">刷新</button>-->
<!--		<button class="btn btn-danger">删除</button>-->
		<div style="clear:both"></div>
	</div>
</body>
</html>
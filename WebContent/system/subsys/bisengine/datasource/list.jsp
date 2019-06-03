<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp" %>
<title>数据源</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/bisDataSource/datagrid.action',
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',title:'ID',width:20},
			{field:'dsName',title:'数据源名称',width:100},
			{field:'dbType',title:'数据库类型',width:100},
			{field:'dataSource',title:'数据源类型',width:100,formatter:function(data){
				if(data==1){
					return "内部数据源";
				}else{
					return "外部数据源";
				}
			}},
			{field:'driverClass',title:'连接驱动类',width:100},
			{field:'driverUrl',title:'驱动连接字符串',width:100},
			{field:'_manage',title:'管理',width:100,formatter:function(e,rowData){
				var dataType=rowData.dataSource;
				var render = [];
				render.push("<a href='#' onclick='edit("+rowData.sid+")'>编辑</a>");
				render.push("<a href='javascript:void(0)' onclick='del("+rowData.sid+")'>删除</a>");
				if(dataType==2){//外部数据源进行连接测试
					render.push("<a href='javascript:void(0)' onclick='testConnect("+rowData.sid+")'>连接测试</a>");
				}
				
				return render.join("&nbsp;&nbsp;&nbsp;");
			}}
		]]
	});
}

function add(){
	window.location = "addOrUpdate.jsp";
}

function edit(identity){
	window.location = "addOrUpdate.jsp?sid="+identity;
}

function del(identity){
	  $.MsgBox.Confirm ("提示", "是否删除该数据源？", function(){
		  var json = tools.requestJsonRs(contextPath+"/bisDataSource/delete.action",{sid:identity});
			if(json.rtState){
				$.MsgBox.Alert_auto("删除成功！",function(){
					datagrid.datagrid("reload");
				});
				
			}
	  });
}

//连接测试
function testConnect(sid){
	var url=contextPath+"/bisDataSource/testConnect.action";
	var json=tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		$.MsgBox.Alert_auto("连接成功！");
	}else{
		$.MsgBox.Alert_auto(json.rtMsg);
	}
	
	
}
</script>
</head>
<body onload="doInit()" style="font-size:12px;padding-left: 10px;padding-right: 10px">
<table id="datagrid" fit="true"></table>
<div id="toolbar" class = "topbar clearfix">
	<div class="fl" style="position:static;">
		<span class="title">数据源</span>
	</div>
	<div class = "right fr clearfix">
		<button class="btn-win-white" onclick="add()">添加</button>
	</div>
</div>
</body>
</html>

<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/header/header.jsp" %>
	<%@ include file="/header/easyui.jsp" %>
	<title>业务引擎测试</title>
	<script type="text/javascript">
	var datagrid;
	function doInit(){
		datagrid = $('#datagrid').datagrid({
			url:contextPath+'/bisDataFetch/dflist.action?dfid=test',//test为数据模型获取的唯一标识
			singleSelect:false,
			toolbar:'#toolbar',//工具条对象
			pagination:true,
			checkbox:false,
			border:false,
			//idField:'formId',//主键列
			fitColumns:true,//列是否进行自动宽度适应
			columns:[[
				{field:'主键',title:'主键',width:100,checkbox:true},
				{field:'姓名',title:'姓名',width:100},
				{field:'年龄',title:'年龄',width:100},
				{field:'出生日期',title:'出生日期',width:100,formatter:function(data,rowData){
					return ""+getFormatDateStr(data,"yyyy-MM-dd");
				}},
				{field:'_manage',title:'操作',width:100,formatter:function(data,rowData){
					var render = [];
					render.push("<a href=\"javascript:void();\" onclick=\"edit('"+rowData["主键"]+"')\">编辑</a>");
					render.push("&nbsp;&nbsp;<a href=\"javascript:void();\" onclick=\"del('"+rowData["主键"]+"')\">删除</a>");
					return render.join("");
				}}
			]]
		});
	}
	
	function add(){
		window.location = "bisadd.jsp";
	}
	
	function edit(id){
		window.location = "bisedit.jsp?bisId="+id;
	}
	
	function del(id){
		if(window.confirm("是否删除该数据项？")){
			var json = tools.requestJsonRs(contextPath+"/bisDataFetch/dfdelete.action?bisTableName=bis_test&bisKeyName=BIS_ID&bisKeyValue="+id);
			if(json.rtState){
				window.location.reload();
			}
		}
	}
	</script>
</head>
<body onload="doInit()">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar">
		<div class="moduleHeader">
			<b><i class="glyphicon glyphicon-sound-stereo"></i>&nbsp;业务引擎数据模型测试</b>
		</div>
		<div style="text-align:left;">
			<button class="btn btn-primary" onclick="add()">添加</button>
		</div>
	</div>
</body>
</html>

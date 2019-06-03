<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>gez资源选择</title>
	
	<script type="text/javascript" charset="UTF-8">
	var contextPath = '<%=contextPath%>';
	var datagrid;
	$(function() {
		query();
	});

	function doPageHandler(){
		query();
	}
	
	function query(){
		var para =  tools.formToJson($("#form")) ;

		$('#datagrid').datagrid({
			url:contextPath+'/gezReport/datagrid.action',
			pagination:true,
			singleSelect:true,
			queryParams:para,
			striped: true,
			border: false,
			toolbar:'#toolbar',//工具条对象
			checkbox:true,
			fitColumns:false,//列是否进行自动宽度适应
			pageSize:30,
			columns:[[
				{field:'opt',title:'操作',width:50,formatter:function(data,rowData){
					return "<a href='#' onclick=\"sel('"+rowData.resId+"','"+rowData.resName+"')\">选择</a>";
				}},
				{field:'resId',title:'资源ID',width:100},
				{field:'resName',title:'资源名称',width:250},
				{field:'resType',title:'类型',width:150,formatter:function(data){
					if(data==18){
						return "复杂报表";
					}else if(data==10008){
						return "通用查询";
					}else if(data==4){
						return "统计表报";
					}else if(data==8){
						return "简单填报表";
					}
				}}
			]]
		});
	}
	
	function sel(resId,resName){
		xparent.document.getElementById("resId").value = resId;
		xparent.document.getElementById("resName").value = resName;
		xparent.document.getElementById("tplName").value = resName;
		window.close();
	}
	
	</script>
</head>
<body fit="true">
<table id="datagrid" fit="true"></table>
<div id="toolbar">
<form id="form" style="padding:10px">
	资源ID：<input type="text" id="resId" name="resId" class="BigInput"/>
	&nbsp;&nbsp;
	资源名称：<input type="text" id="resName" name="resName" class="BigInput"/>
	&nbsp;&nbsp;
	<button class="btn btn-default" type="button" onclick="query()">查询</button>
</form>
</div>
<iframe id="frame0" style="display:none"></iframe>
</body>
</html>
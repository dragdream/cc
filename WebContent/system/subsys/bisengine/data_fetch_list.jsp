<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp" %>
<%
	int bisTableId = TeeStringUtil.getInteger(request.getParameter("bisTableId"),0);
%>
<title>数据获取</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
var datagrid;
var bisTableId = <%=bisTableId%>
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/bisDataFetch/datagrid.action?bisTableId='+bisTableId+"&page=1&rows=10000",
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'identity',title:'数据源唯一标识',width:100},
			{field:'fieldsShow',title:'显示字段',width:100},
			{field:'sql',title:'数据SQL',width:100},
			{field:'remark',title:'备注',width:100},
			{field:'_manage',title:'管理',width:100,formatter:function(e,rowData){
				var render = [];
				render.push("<a href=\"javascript:void();\" onclick=\"edit('"+rowData.identity+"')\">编辑</a>");
				render.push("&nbsp;&nbsp;<a href=\"javascript:void();\" onclick=\"del('"+rowData.identity+"')\">删除</a>");
				return render.join("");
			}}
		]]
	});
}

function add(){
	window.location = "data_fetch_mgr.jsp?bisTableId="+bisTableId;
}

function edit(identity){
	window.location = "data_fetch_mgr.jsp?bisTableId="+bisTableId+"&identity="+encodeURI(identity);
}

function del(identity){
	if(window.confirm("是否删除该数据获取规则？")){
		var json = tools.requestJsonRs(contextPath+"/bisDataFetch/del.action",{identity:identity});
		if(json.rtState){
			datagrid.datagrid("reload");
		}
	}
}
</script>
</head>
<body onload="doInit()">
<table id="datagrid" fit="true"></table>
<div id="toolbar">
	<div class="moduleHeader">
		<b><i class="glyphicon glyphicon-sound-stereo"></i>&nbsp;数据获取规则</b>
	</div>
	<div style="text-align:left;">
		<button class="btn btn-primary" onclick="add()">添加</button>
	</div>
</div>
</body>
</html>

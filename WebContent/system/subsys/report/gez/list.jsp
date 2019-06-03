<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	TeePerson loginPerson = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script>

function doInit(){
	
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

function refresh(){
	$.jBox.tip("正在获取数据，请稍候","loading");
	tools.requestJsonRs(contextPath+"/gezReport/refresh.action",{},true,function(json){
		if(json.rtState){
			$.jBox.tip("数据获取完成","info");
			$('#datagrid').datagrid("reload");
		}else{
			$.jBox.closeTip();
			alert(json.rtMsg);
		}
		
	});
}


</script>
</head>
<body onload="doInit()" >
<div id="toolbar">
<form id="form" style="padding:10px">
	资源ID：<input type="text" id="resId" name="resId" class="BigInput"/>
	&nbsp;&nbsp;
	资源名称：<input type="text" id="resName" name="resName" class="BigInput"/>
	&nbsp;&nbsp;
	<button class="btn btn-default" type="button" onclick="query()">查询</button>
	&nbsp;&nbsp;
	<button class="btn btn-success" onclick="refresh()">刷新资源</button>
</form>
</div>
<table id="datagrid" fit="true"></table>

</body>
</html>
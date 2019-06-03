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
	$('#datagrid').datagrid({
		url:contextPath+'/report/viewableReports.action',
		pagination:true,
		singleSelect:true,
		striped: true,
		border: false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'tplName',title:'报表名称',width:150},
			{field:'tplName1',title:'设计人',width:150},
			{field:'_manage',title:'操作',width:50,formatter:function(value,rowData,rowIndex){
				var render = [];
				render.push("<a href='#' onclick='view("+rowData.sid+")'>查看</a>");
// 				render.push("<a href='javascript:void(0)' onclick='del("+rowData.sid+")'>删除</a>");
				return render.join("&nbsp;/&nbsp;");
			}}
		]]
	});
}

function view(sid){
	window.location = contextPath+"/system/subsys/report/flow_report_show.jsp?templateId="+sid;
}

function search(){
	$('#datagrid').datagrid("reload",{tplName:$("#tplName").val()});
}

</script>
</head>
<body onload="doInit()" >
<table id="datagrid" fit="true"></table>
<div id="toolbar">
	<div class="base_layout_top" style="position:static">
		<table width="100%">
			<tr>
				<td>
					<span class="easyui_h1"><i class="glyphicon glyphicon-sound-stereo"></i>&nbsp;流程报表</span>
				</td>
				<td align=right>
					报表名称：
					<input type="text" class="BigInput" name="tplName" id="tplName"/>
					&nbsp;&nbsp;
					<button class="btn btn-success" onclick="search()">查询</button>&nbsp;&nbsp;
				</td>
			</tr>
		</table>
	</div>
	<br/>
</div>
</body>
</html>
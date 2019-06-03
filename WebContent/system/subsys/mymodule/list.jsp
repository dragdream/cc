<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp" %>
<%
	String moduleId = request.getParameter("moduleId");
	TeePerson loginUser = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
	
%>
<title>全质量定义模块开发平台</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="list.js"></script>
<script>
var moduleId = "<%=moduleId%>";
var userId = "<%=loginUser.getUuid()%>";
</script>
</head>
<body onload="doInit()">
<table id="datagrid" fit="true"></table>
<div id="toolbar">
	<div class="moduleHeader">
		<b><i class="glyphicon glyphicon-sound-stereo"></i>&nbsp;<span id="module_title"></span></b>
	</div>
	<form id="searchForm" style="padding:5px"></form>
	<div style="text-align:left;">
		<button class="btn btn-primary" onclick="add()" id="newBtn" style="display:none">新建</button>
	</div>
</div>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String menuSid = request.getParameter("sid");
	if(menuSid == null){
		menuSid= "";
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style='overflow:hidden;'>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件管理</title>
<script>
var op = "";//操作
var ids = "";//操作对象id串

</script>
<style>
	.ui-layout-west{
		height:100%;
		margin-top:5px;
	}
	.ui-layout-center{
		position:absolute;
		top:0px;
		left:180px;
		bottom:5px;
		right:0px;
		padding:0 0 0px 10px;
		border-left:2px solid #e2e2e2;
	}
</style>
</head>
<body onload="">
	<div class="ui-layout-west">
		<iframe id="file_tree" src="left.jsp?menuSid=<%=menuSid %>" frameborder=0 style="width:100%;height:100%"></iframe>
	</div>
	<div class="ui-layout-center">
		<iframe id="file_main" src="comfireNo.jsp" frameborder=0 style="width:100%;height:100%"></iframe>
	</div>
</body>
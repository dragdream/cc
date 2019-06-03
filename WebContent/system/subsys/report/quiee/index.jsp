<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String menuSid = request.getParameter("sid");
	if(menuSid == null){
		menuSid= "";
	}


%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
	<!-- jQuery 布局器 -->
	<script type="text/javascript" src="<%=request.getContextPath() %>/common/jqueryui/jquery.layout-latest.js"></script>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报表维护</title>
<script>
var op = "";//操作
var ids = "";//操作对象id串
function doInit(){
	$("body").layout();
}
</script>
</head>
<body onload="doInit()">
	<div class="ui-layout-west">
		<iframe id="file_tree" src="left.jsp?menuSid=<%=menuSid %>" frameborder=0 style="width:100%;height:100%"></iframe>
	</div>
	<div class="ui-layout-center">
		<iframe id="file_main" src="comfireNo.jsp" frameborder=0 style="width:100%;height:100%"></iframe>
	</div>
</body>
<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/header/header.jsp" %>
<%
	int userId = TeeStringUtil.getInteger(request.getParameter("userId"),0);
%>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/TeeMenu.js"></script>
<style>
</style>
<script>
var userId = <%=userId%>;
function doInit(){
}
</script>
</head>
<body onload="doInit();">
	
</body>
</html>
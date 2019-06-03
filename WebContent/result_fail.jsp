<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page import="com.tianee.webframe.util.auth.TeeAuthUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp"%>
<title><%=ieTitle%></title>
<style>
td{
border:1px solid #dddad5;
}
</style>
<script>
function doInit(){
	messageMsg("授权文件格式或类型错误！<br/><a href='<%=contextPath %>/system/core/system/auth_info.jsp'>返回</a>","content","error");
}
</script>
</head>
<body id="content" onload="doInit()">
	
</body>
</html>

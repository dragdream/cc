<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp" %>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
function doInit(){
	messageMsg("请点击左侧卷库或者卷盒查看档案！","message","info");
}

</script>
</head>
<body id="body" style="padding:5px;background:transparent" onload="doInit()">
   <div id="message" style="margin-top: 20px">
   
   </div>
</body>
</html>

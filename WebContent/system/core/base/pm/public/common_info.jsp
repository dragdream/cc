<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp" %>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
function doInit(){
	messageMsg("请点击左侧的人员，以查看其各项数据信息","body","info");
}

</script>
</head>
<body id="body" style="padding:5px;" onload="doInit()">

</body>
</html>

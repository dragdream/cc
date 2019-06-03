<%@ page language="java"  import="java.util.*,java.text.SimpleDateFormat" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp"%>
<%@include file="/header/easyui.jsp"%>
<%@include file="/header/upload.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<style>
.diaryTable {
	border-collapse: collapse;
}

.diaryTable td {
	border: 1px solid #e2e2e2;
	font-size: 14px;
	padding: 5px;
}
</style>
<script>
function doInit(){
		messageMsg("模块开发中。。。。。。。。。。。。。。。。。。", "container" ,'' ,500);
}
</script>
</head>
<body class="" topmargin="5" onload="doInit();">
<div class="moduleHeader">
		<b><i class="glyphicon glyphicon-sound-stereo"></i>&nbsp;考勤方式设置</b>
</div>
<div id="dutyTable"></div>
<br>
<div id="container">

</div>
</body>
</html>
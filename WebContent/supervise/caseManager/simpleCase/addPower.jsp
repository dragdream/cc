<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
</meta>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/caseManager/simpleCase/js/addPower.js"></script>

<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<%
	String subjectId = request.getParameter("subjectId");
%>
<script>
	var subjectId = "<%=subjectId%>";
</script>
<title>违法行为</title>
</head>
<body onload="doInit()" fit="true">
    <div id="toolbar" class="clearfix">
            <div id="outwarp">
        <div class="left fl setHeight">
        </div>
    </div>
    </div>
    <table id="selectPowerTable" fit="true"></table>
</body>
</html>
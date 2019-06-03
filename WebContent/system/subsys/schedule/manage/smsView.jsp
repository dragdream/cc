<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@page import="com.tianee.oa.core.org.bean.*"%>
<%@page import="java.text.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String scheduleId = request.getParameter("scheduleId");
	String extId = request.getParameter("extId");
	String type = request.getParameter("type");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp" %>
<script>
var scheduleId = "<%=scheduleId%>";
var type = <%=type%>;
var extId = "<%=extId%>";
function doInit(){
	if(type=="1"){//批阅
		tools.requestJsonRs(contextPath+"/scheduleReported/read.action",{uuid:extId});
	}else{//查看
		tools.requestJsonRs(contextPath+"/scheduleShared/read.action",{uuid:extId});
	}
	
	window.location = "detail.jsp?scheduleId="+scheduleId;
}

</script>
</head>
<body onload="doInit();" style="margin:0px;">
</body>
</html>
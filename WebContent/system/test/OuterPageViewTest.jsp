<%@page import="com.tianee.oa.core.workflow.flowrun.bean.FlowRunToken"%>
<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
	<%@ include file="/header/header2.0.jsp" %>
	<title>业务引擎测试</title>
	<script type="text/javascript">
	<%	
		//从token中获取流程数据
		FlowRunToken token = new FlowRunToken(request);
	%>
	//从token中获取到三个重要数据
	var runId = "<%=token.getRunId()%>";
	var frpSid = "<%=token.getFrpSid()%>";
	var flowId = "<%=token.getFlowId()%>";
	
	</script>
</head>
<body onload="doInit()" style="padding:10px">
	预览外接表单的界面（自行写业务逻辑）
</body>
</html>

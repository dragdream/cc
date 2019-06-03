<%@page import="com.tianee.oa.oaconst.TeeConst"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tianee.oa.core.org.bean.TeePerson" %>
<%
String contextPath = request.getContextPath();
String basePath = request.getScheme() + "://"
		+ request.getServerName() + ":" + request.getServerPort()
		+ contextPath + "/";
String mobilePath = contextPath + "/system/mobile";

TeePerson loginUser = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
%>
<!DOCTYPE HTML>
<html>
<head>
<title>列表展示</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<link href="<%=mobilePath%>/phone/style/style.css" rel="stylesheet" type="text/css" />
<link href="<%=contextPath%>/common/images/workflow/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=contextPath %>/common/jquery/jquery-min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/mobile/js/tools.js"></script> 
<script>
var  contextPath = "<%=contextPath %>";
var mobilePath ="<%=mobilePath%>";
var userId = <%=loginUser.getUuid()%>;
</script>
<%
	String itemId = request.getParameter("itemId");
	String frpSid = request.getParameter("frpSid");
%>
<script type="text/javascript">
var itemId = "<%=itemId%>";
var frpSid = "<%=frpSid%>";
function doInit(){
	tools.requestJsonRs(contextPath+"/mobileWorkflow/formListView.action",{itemId:itemId,frpSid:frpSid},true,function(json){
		$("#viewlist").html(json.rtData);
	});
}

</script>
<style>
p{
font-size:16px;
padding:5px;
}
p input{
border:1px solid gray;
}
.readonly{
background:#e2e2e2;
}
.title{
font-size:16px;
font-weight:bold;
background:#428bca;
padding:10px;
color:white;
}
textarea{
border:1px solid gray;
}
</style>
</head>
<body onload="doInit();" style="">
	<div id="viewlist" class="wf">
	</div>
</body>
</html>
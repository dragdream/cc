<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header/header.jsp" %>
<%
	String smsIds = request.getParameter("smsIds");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.List"%>
<%@page import="java.util.Map"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href = "<%=cssPath %>/style.css">
<script type="text/javascript" src="<%=contextPath %>/common/jquery/jquery-min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/tabs/tabs.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/tools.js"></script>

<title>Insert title here</title>
<script type="text/javascript">
var smsIds = '<%=smsIds%>';
function doInit() {
	var url = contextPath + '/sms/viewDetails.action?smsIds=' + smsIds;
	var para =  {};
	var jsonRs = tools.requestJsonRs(url,para);
	var jsonObj = jsonRs.rtData;
	$("#layout").layout({auto:true});//开启布局器
	for(var i = 0;i<jsonObj.length;i++){
		var flag = false;
		if(i==0){
			flag = true;
		}
		$.addTab("tabs","tabs-content",{title:jsonObj[i].text,url:contextPath+jsonObj[i].url,active:flag});
	}
	
}
</script>
</head>
<body onload="doInit()" style="margin:0px;overflow:hidden;">
<div id="layout">
	<div id="tabs" layout="north" height="35" class="tee_tab_header"></div>
	<div id="tabs-content" layout="center"></div>
</div>
</body>
</html>
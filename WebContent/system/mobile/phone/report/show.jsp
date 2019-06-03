<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>报表</title>
<%
String contextPath = request.getContextPath();
String mobilePath = contextPath + "/system/mobile";
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=1000, initial-scale=1, user-scalable=yes, minimum-scale=1, maximum-scale=20">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<script type="text/javascript" src="<%=contextPath %>/system/mobile/js/zepto.min.js"></script> 
<script type="text/javascript" src="<%=contextPath %>/system/mobile/js/tools.js"></script> 
<script src="<%=contextPath %>/common/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/highcharts/js/highcharts.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/highcharts/js/modules/exporting.src.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/highcharts/js/highcharts-more.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/seniorreport.js"></script>


<script type="text/javascript">
var contextPath = "<%=contextPath %>";
var mobilePath ="<%=mobilePath%>";
var reportId = "<%=request.getParameter("reportId")%>";
function doInit(){
	try{
		window.external.setTitle("报表预览");
	}catch(ex){}
	
	window.report = new SeniorReport({reportId:reportId,target:$("#container"),success:function(){
		report.show();
	}});
}

</script>


</head>
<body onload="doInit();">
<div id="container">
	
</div>
</body>
</html>
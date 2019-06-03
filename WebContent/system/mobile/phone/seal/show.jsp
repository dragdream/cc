<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>签章预览</title>
<%
String contextPath = request.getContextPath();
String mobilePath = contextPath + "/system/mobile";
String deviceNo = (String)session.getAttribute("deviceNo");
String uuid = request.getParameter("uuid");
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<script type="text/javascript" src="<%=contextPath %>/system/mobile/js/tools.js"></script> 
<script src="<%=contextPath %>/common/easyui/jquery.min.js"></script>


<script type="text/javascript">
var contextPath = "<%=contextPath %>";
var mobilePath ="<%=mobilePath%>";
var deviceNo = "<%=deviceNo%>";
var uuid = "<%=uuid%>";
function doInit(){
	try{
		window.external.setTitle("签章预览");
	}catch(ex){}
	
	tools.requestJsonRs(contextPath+"/mobileSeal/get.action?uuid="+uuid,{},true,function(json){
		var sealData = json.rtData.sealData;
		$("#img").attr("src",sealData);
		if(json.rtData.deviceNo==""){
			$("#bindBtn").show();
			$("#pwd").show();
		}
	});
}

function bind(){
	tools.requestJsonRs(contextPath+"/mobileSeal/binding.action?uuid="+uuid+"&deviceNo="+deviceNo,{pwd:$("#pwd").val()},true,function(json){
		$("#bindBtn").hide();
		$("#pwd").hide();
	});
}

</script>


</head>
<body onload="doInit();">
<center>
<img id="img" />
<br/><br/>
<input type="password" name="pwd" id="pwd" style="border:1px solid gray;padding:5px;display:none" placeholder="请输入签章密码"/>
<br/><br/>
<button id="bindBtn" style="display:none" onclick="bind()">绑定该设备</button>
</center>
</body>
</html>
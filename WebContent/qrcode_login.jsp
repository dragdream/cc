<%@page import="com.tianee.oa.core.general.TeeSysCodeManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tianee.webframe.util.global.TeeSysProps"%>
<!DOCTYPE HTML>
<html>
<head>
<%
	String guid = request.getParameter("guid");
%>
<title>登录验证</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<script>
var contextPath = "<%=request.getContextPath()%>";
var guid = "<%=guid%>";
function doInit(){	
	
}

function doLogin(){
	mui.ajax(contextPath+"/systemAction/qrCodeLoginIdentityWrite.action",{
		type:"post",
		dataType:"html",
		data:{guid:guid},
		timeout:10000,
		success:function(json){
			CloseWindow();
		},
		error:function(){
			
		}
	});
}
</script>
<style>
</style>
</head>
<body>
	<div style="text-align:center;margin-top:50px">
		<img src="images/vertify.png" style="width:120px"/>
	</div>
	<div style="text-align:center;margin-top:30px;font-family:微软雅黑">
		Windows扫码确认登录
	</div>
	<div style="text-align:center;margin-top:100px;font-family:微软雅黑">
		<button class="mui-btn mui-btn-primary" onclick="doLogin()" style="width:120px">确认登录</button>
	</div>
	<div style="text-align:center;margin-top:20px;font-family:微软雅黑">
		<button class="mui-btn mui-btn-link" onclick="CloseWindow()">取消登录</button>
	</div>
</body>
</html>

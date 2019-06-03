<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>移动办主界面</title>
<%@ include file="/system/mobile/header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<link href="<%=mobilePath%>/phone/style/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=mobilePath %>/js/iscroll.js"></script>
<script type="text/javascript">
function toNews(){
	window.location.href = "<%=contextPath%>/system/mobile/phone/news/index.jsp";
}

function toWork(){
	window.location.href = "<%=contextPath%>/system/mobile/phone/workflow/index.jsp";
}

function toNotify(){
	window.location.href = "<%=contextPath%>/system/mobile/phone/notify/index.jsp";
}

function toCalendar(){
	window.location.href = "<%=contextPath%>/system/mobile/phone/calendar/index.jsp";
}

function toReport(){
	window.location.href = "<%=contextPath%>/system/mobile/phone/report/index.jsp";
}

function toDiary(){
	window.location.href = "<%=contextPath%>/system/mobile/phone/diary/index.jsp";
}

function toEmail(){
	window.location.href = "<%=contextPath%>/system/mobile/phone/email/index.jsp";
}
function toPersonDisk(){
	window.location.href = "<%=contextPath%>/system/mobile/phone/fileNetdisk/person/index.jsp";
}


function toPublicDisk(){
	window.location.href = "<%=contextPath%>/system/mobile/phone/fileNetdisk/public/index.jsp";
}
</script>
<style type="text/css" media="all">



</style>
</head>
<body>
<div class="header" ><a href="<%=contextPath%>" target="">中腾OA移动办公</a></div>

<div class="wrapper">
	<table class="app_table">
		<tbody>
			<tr>
				<td>
					<a href="#" class="" onclick="toNews();">新闻</a>
				</td>
				<td>
					<a href="#"  onclick="toNotify();">公告</a>
				</td>
				<td>
					<a href="#" onclick="toCalendar();">日程</a>
				</td>
			</tr>
			
			<tr>
				<td>
					<a href="javascript:void(0)" onclick="toWork();">工作流 </a>
				</td>
				<td>
					<a href="javascript:void(0)" onclick="toReport()">报表门户</a>
				</td>
				<td>
					<a href="javascript:void(0)" onclick="toDiary()">工作日志</a>
				</td>
			</tr>
			
				<tr>
				<td>
					<a href="javascript:void(0)" onclick="toEmail()">邮件 </a>
				</td>
				<td>
					<a  href="javascript:void(0)" onclick="toPersonDisk()">个人网盘</a>
				</td>
				<td>
					<a  href="javascript:void(0)" onclick="toPublicDisk()">公共网盘</a>
				</td>
			</tr>
		</tbody>
	</table>
</div>


<div class="footer"></div>
</body>
</html>
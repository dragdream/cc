<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String uuid = request.getParameter("uuid");
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<title>用户信息</title>
<link  rel="stylesheet" type="text/css" href="<%=contextPath %>/system/core/person/css/person.css" />

<style type="text/css">
table{
border:0px;
}
td{
padding:4px;
}
</style>
<script type="text/javascript" src="<%=contextPath %>/system/mobile/js/zepto.min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/mobile/js/tools.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/core/person/js/person.js"></script>
<script type="text/javascript">
var uuid = "<%=uuid%>";
var contextPath = "<%=contextPath%>";
function doInit(){
	try{
		window.external.setTitle("人员详情");
	}catch(e){}
	
	var personObj = getPersonInfo(uuid);
	if(personObj && personObj.uuid){
		 bindJsonObj2Cntrl(personObj);
		 $("#userName1").html(personObj.userName);
		 var sexDesc = "男";
		 if(personObj.sex == '1'){
			 sexDesc = "女";
		 }
		 $("#sexDesc").html(sexDesc);
		 var onlineTimeDesc = getTimeMilisecondDesc(personObj.online);
		 $("#onlineTimeDesc").html(onlineTimeDesc);
		 
		 if(personObj.avatar){
			 var url = "<%=contextPath%>/attachmentController/downFile.action?id=" + personObj.avatar + "&model=person";
			 $("#avatarImg").attr("src",url);
		 }


	}else{
		//alert("没有相关人员！");
	}
	
}

function check() {
    return true;
}
</script>

</head>
<body onload="doInit()" style="overflow:hidden;">

<table style="font-size:17px;">
	<tr>
		<td rowspan="9" style="vertical-align:top">
			<div id="auatar" class="photo"><img id="avatarImg" border="0" src="<%=contextPath %>/system/core/person/imgs/avatar.png" width="60" hegiht="60"></div>
            <div class="photo_cover"></div>
		</td>
	</tr>
	<tr>
		<td colspan="2"><span id="userName"></span>&nbsp;&nbsp;<span style="color:gray" id="sexDesc"></span>&nbsp;&nbsp;<span style="color:gray" id="deptIdName"></span></td>
	</tr>
	<tr>
		<td colspan="2"><span id="email"></span></td>
	</tr>
	<tr>
		<td style="font-weight:bold;">手机：</td>
		<td id="mobilNo" style="color:blue"></td>
	</tr>
	<tr>
		<td style="font-weight:bold;">电话：</td>
		<td id="telNoDept" colspan="2" style="color:blue"></td>
	</tr>
	<tr>
		<td style="font-weight:bold;">角色：</td>
		<td id="userRoleStrName"></td>

	</tr>
	<tr>
		<td style="font-weight:bold;">辅助角色：</td>
		<td id="userRoleOtherName" colspan="2"></td>
	</tr>
	<tr>
		<td style="font-weight:bold;">QQ：</td>
		<td id="oicqNo"></td>
	</tr>
	<tr>
		<td style="font-weight:bold;">在线时长：</td>
		<td id="onlineTimeDesc"></td>
	</tr>
</table>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String uuid = request.getParameter("uuid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<title>用户信息</title>
<link  rel="stylesheet" type="text/css" href="<%=contextPath %>/system/core/person/css/person.css" />

<style type="text/css">
td{
padding:4px;
}
</style>
<script type="text/javascript" src="<%=contextPath %>/system/core/person/js/person.js"></script>
<script type="text/javascript">
var uuid = "<%=uuid%>";
function doInit(){
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
		 
		 $("#oicqNo").attr("href","tencent://message/?uin="+personObj.oicqNo).html(personObj.oicqNo);

	}else{
		alert("没有相关人员！");
	}
}


function check() {
    return true;
}
</script>

</head>
<body onload="doInit()" style="overflow:hidden;">
<div class="base_layout_top">
<h4><i class="glyphicon glyphicon-user"></i>&nbsp;&nbsp;人员信息</h4>
</div>
<div class="base_layout_center" style="padding:20px">
	
<center>
	<table class="TableBlock" style="font-size:12px;width:700px">
		<tr>
			<td class="TableData TableBG">头像：</td>
			<td class="TableData">
				<img id="avatarImg" src="<%=contextPath %>/system/core/person/imgs/avatar.png" >
			</td>
		</tr>
		<tr>
			<td class="TableData TableBG" width="100px">用户名：</td>
			<td class="TableData" id="userName"></td>
		</tr>
		<tr>
			<td class="TableData TableBG">部门：</td>
			<td class="TableData" id="deptIdName"></td>
		</tr>
		<tr>
			<td class="TableData TableBG">角色：</td>
			<td class="TableData" id="userRoleStrName"></td>
		</tr>
		<tr>
			<td class="TableData TableBG">辅助角色：</td>
			<td class="TableData" id="userRoleOtherName"></td>
		</tr>
		<tr>
			<td class="TableData TableBG">邮件：</td>
			<td class="TableData" id="email"></td>
		</tr>
		<tr>
			<td class="TableData TableBG">手机：</td>
			<td class="TableData" id="mobilNo"></td>
		</tr>
		<tr>
			<td class="TableData TableBG">电话：</td>
			<td class="TableData" id="telNoDept"></td>
		</tr>
		<tr>
			<td class="TableData TableBG">OICQ：</td>
			<td class="TableData">
				<a href="" id="oicqNo" target="_blank"></a>
			</td>
		</tr>
		<tr>
			<td class="TableData TableBG">在线时长：</td>
			<td class="TableData" id="onlineTimeDesc"></td>
		</tr>
		<tr>
			<td class="TableData TableBG">操作：</td>
			<td class="TableData">
				<a href="javascript:void(0)" onclick="openFullWindow('<%=request.getContextPath() %>/system/core/email/send.jsp?toUsers=<%=uuid%>')">邮件</a>
			</td>
		</tr>
	</table>
</center>
</div>
</body>
</html>
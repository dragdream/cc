<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.tianee.oa.core.general.model.TeeSmsModel" %>
<%@ page import="com.tianee.webframe.util.str.TeeJsonUtil" %>
<%@ page import="com.tianee.webframe.util.date.TeeLunarCalendarUtils" %>
<%@ page import="com.tianee.webframe.util.date.TeeWeather" %>
<%@ page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page import="com.tianee.webframe.util.date.*"%>
<%@ page import="com.tianee.webframe.util.auth.TeeAuthUtil"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/header/smsHeader.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=cssPathSecond%>/sms.css">
<script type="text/javascript" charset="UTF-8">
var qUserId = "<%=new String(request.getParameter("qUserId"))%>";
function doInit(){
	var url = contextPath+"/personManager/getPsersonInfoByUserId.action";
	var json = tools.requestJsonRs(url,{userId:qUserId});
	var avatar = json.rtData.avatar;
	if(avatar==null || avatar=="" || avatar=="0"){
		avatar = systemImagePath+"/photo_pic.jpg";
	}else{
		avatar = contextPath+"/attachmentController/downFile.action?id="+avatar+"&model=person";
	}
	
	var content = "<div >" +
						"<center style='display:none'><img style='height:130px;width:105px;margin-bottom:5px;border:1px solid #706c6a' src='"+avatar+"'/></center>" +
						"<div class='sms_title_block'>姓名</div>" +
						"<div class='sms_info_block'>"+json.rtData.userName+"</div>" +
						"<div style='clear:both'></div>" +
						"<div class='sms_title_block'>角色</div>" +
						"<div class='sms_info_block'>"+json.rtData.userRoleStrName+"</div>" +
						"<div style='clear:both'></div>" +
						"<div class='sms_title_block'>终端</div>" +
						"<div class='sms_info_block'>"+"Web"+"</div>" +
						"<div style='clear:both'></div>" +
						"<div class='sms_title_block'>部门</div>" +
						"<div class='sms_info_block'>"+json.rtData.deptIdName+"</div>" +
						"<div style='clear:both'></div>" +
						"<div class='sms_title_block'>手机</div>" +
						"<div class='sms_info_block'>"+json.rtData.mobilNo+"</div>" +
						"<div style='clear:both'></div>" +
						"<div class='sms_title_block'>电话</div>" +
						"<div class='sms_info_block'>"+json.rtData.telNoHome+"</div>" +
						"<div style='clear:both'></div>" +
						"<div class='sms_title_block'>邮箱</div>" +
						"<div class='sms_info_block'>"+json.rtData.email+"</div>" +
						"<div style='clear:both'></div>" +
						"<div class='sms_title_block'>QQ</div>" +
						"<div class='sms_info_block'>"+json.rtData.oicqNo+"</div>" +
						"<div style='clear:both'></div>" +
					"</div>";
					
	$("body").append(content);
	
}

</script>
</head>
<body onload="doInit()" style="font-size:12px;padding:5px;overflow:hidden">

</body>
</html>
		        
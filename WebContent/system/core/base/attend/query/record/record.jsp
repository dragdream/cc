<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String userId=request.getParameter("userId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<title>考勤记录</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<script>

function doInit(){
	$.addTab("tab","tab-content",[
                              {title:"上下班记录",url:contextPath+"/system/core/base/attend/query/record/register.jsp?userId=<%=userId%>"},
                              {title:"请假记录",url:contextPath+"/system/core/base/attend/query/record/leave.jsp?userId=<%=userId%>"},
                              {title:"外出记录",url:contextPath+"/system/core/base/attend/query/record/out.jsp?userId=<%=userId%>"},
                              {title:"出差记录",url:contextPath+"/system/core/base/attend/query/record/evection.jsp?userId=<%=userId%>"},
                              {title:"加班记录",url:contextPath+"/system/core/base/attend/query/record/overtime.jsp?userId=<%=userId%>"},
                              ]); 
	}

</script>

</head>
<body onload="doInit()"  style="overflow: hidden;padding-left:10px;padding-right:10px;padding-top: 5px">
	<div class="titlebar clearfix" >
		<img class = 'tit_img' style="margin-right:10px" src="<%=contextPath %>/common/zt_webframe/imgs/kqjl/icon_考勤记录-.png">
		<p class="title">考勤记录</p>
		<ul id = 'tab' class = 'tab clearfix'>
			
		</ul>
		<span class="basic_border_grey fl"></span>
	</div>
	
	<div id="tab-content" style="padding-left: 10px;padding-right:10px"></div>

</body>
</html>


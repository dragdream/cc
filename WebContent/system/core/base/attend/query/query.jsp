<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String deptIds=request.getParameter("deptIds");
String startDateDesc=request.getParameter("startDateDesc");
String endDateDesc=request.getParameter("endDateDesc");
String countMonth=request.getParameter("countMonth");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<title>考勤统计结果</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%-- <link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/js/tabs/demo.css"/>
<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css"/>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/tabs/tabs.js"></script> --%>

<script>

function doInit(){
	<%-- $.addTab("tabs","tabs-content",{url:contextPath+"/system/core/base/attend/query/register.jsp?deptId=<%=deptId%>&startDateDesc=<%=startDateDesc%>&endDateDesc=<%=endDateDesc%>",title:"上下班统计结果",active:true});
	$.addTab("tabs","tabs-content",{url:contextPath+"/system/core/base/attend/query/leave.jsp?deptId=<%=deptId%>&startDateDesc=<%=startDateDesc%>&endDateDesc=<%=endDateDesc%>",title:"请假统计结果",active:false});
	$.addTab("tabs","tabs-content",{url:contextPath+"/system/core/base/attend/query/out.jsp?deptId=<%=deptId%>&startDateDesc=<%=startDateDesc%>&endDateDesc=<%=endDateDesc%>",title:"外出统计结果",active:false});
	$.addTab("tabs","tabs-content",{url:contextPath+"/system/core/base/attend/query/evection.jsp?deptId=<%=deptId%>&startDateDesc=<%=startDateDesc%>&endDateDesc=<%=endDateDesc%>",title:"出差统计结果",active:false});
	$.addTab("tabs","tabs-content",{url:contextPath+"/system/core/base/attend/query/overtime.jsp?deptId=<%=deptId%>&startDateDesc=<%=startDateDesc%>&endDateDesc=<%=endDateDesc%>",title:"加班统计结果",active:false});
	$.addTab("tabs","tabs-content",{url:contextPath+"/system/core/base/attend/query/annualLeave.jsp?deptId=<%=deptId%>&startDateDesc=<%=startDateDesc%>&endDateDesc=<%=endDateDesc%>",title:"年假统计结果",active:false});
     --%>
	$.addTab("tab","tab-content",[{url:contextPath+"/system/core/base/attend/query/register.jsp?deptIds=<%=deptIds%>&startDateDesc=<%=startDateDesc%>&endDateDesc=<%=endDateDesc%>&countMonth=<%=countMonth %>",title:"上下班统计结果"},
	                                {url:contextPath+"/system/core/base/attend/query/leave.jsp?deptIds=<%=deptIds%>&startDateDesc=<%=startDateDesc%>&endDateDesc=<%=endDateDesc%>",title:"请假统计结果"},
	                                {url:contextPath+"/system/core/base/attend/query/out.jsp?deptIds=<%=deptIds%>&startDateDesc=<%=startDateDesc%>&endDateDesc=<%=endDateDesc%>",title:"外出统计结果"},
	                                {url:contextPath+"/system/core/base/attend/query/evection.jsp?deptIds=<%=deptIds%>&startDateDesc=<%=startDateDesc%>&endDateDesc=<%=endDateDesc%>",title:"出差统计结果"},
	                                {url:contextPath+"/system/core/base/attend/query/overtime.jsp?deptIds=<%=deptIds%>&startDateDesc=<%=startDateDesc%>&endDateDesc=<%=endDateDesc%>",title:"加班统计结果"},
	                                {url:contextPath+"/system/core/base/attend/query/annualLeave.jsp?deptIds=<%=deptIds%>&startDateDesc=<%=startDateDesc%>&endDateDesc=<%=endDateDesc%>",title:"年假统计结果"}]);
}

</script>

</head>
<body onload="doInit()"  style="overflow: hidden;padding-left:10px;padding-right:10px;padding-top: 5px;">
<!-- <div id="tabs" class="base_layout_top"></div>
<div id="tabs-content" class="base_layout_center"></div>
 -->
 <div class="titlebar clearfix" >
		    <img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/kqgl/iconz_请假审批管理.png">
		    <span class="title">考勤统计</span>
		<ul id = 'tab' class = 'tab clearfix'>
			
		</ul>
		<span class="basic_border_grey fl"></span>
	</div>
	
	<div id="tab-content" style="padding-left: 10px;padding-right:10px"></div>
 
</body>
</html>


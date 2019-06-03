<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.tianee.oa.core.partthree.util.TeePartThreeUtil" %>
<%
   TeePerson  loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
   boolean hasPriv=TeePartThreeUtil.checkHasPriv(loginUser, "SYS_LOG");
   if(!hasPriv){//没有权限
	   response.sendRedirect("/system/core/system/partthree/error.jsp");
   }
   
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/tabs/tabs.js"></script>

<script>

function doInit(){
	$.addTab("tabs","tabs-content",[
	                                {url:contextPath+"/system/core/sysLog/summaryLog.jsp",title:"日志概况",active:true}
									,{url:contextPath+"/system/core/sysLog/yearsLog.jsp",title:"年度日志",active:false}
									,{url:contextPath+"/system/core/sysLog/timesLog.jsp",title:"时段统计",active:false}
									,{url:contextPath+"/system/core/sysLog/query.jsp",title:"日志管理",active:false}
									,{url:contextPath+"/system/core/sysLog/threePart.jsp",title:"三员日志管理",active:false}]);
}

</script>

</head>
<body onload="doInit()"  style="margin-top:5px;overflow:hidden">
<div id="tabs" class="base_layout_top"></div>
<div id="tabs-content" class="base_layout_center"></div>

</body>

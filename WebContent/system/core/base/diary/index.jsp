<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp" %>
<%@include file="/header/upload.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
html{
}

</style>

<%
	TeePerson person = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
%>
<!-- jQuery 布局器 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/common/jqueryui/jquery.layout-latest.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>

</head>
<body onload="" style="overflow: hidden;">
	<div class="titlebar clearfix" style="background-color: #f5f6f7;width: auto;height:60px;padding:5px;">
		<img class = 'tit_img' style="margin-right: 10px;" src="<%=contextPath%>/common/zt_webframe/imgs/grbg/personLogs/icon_wdrz.png">
		<p class="title">工作日志</p>
		<ul id = 'tab' class = 'tab clearfix'>
			
		</ul>
		<span class="basic_border_grey fl"></span>
	</div>
	
	<div id="tab-content"></div>
	
</body>
<script>
 $.addTab("tab","tab-content",[{title:"我的日志",url:contextPath+"/system/core/base/diary/myLogs.jsp"},
                              {title:"共享日志",url:contextPath+"/system/core/base/diary/index_share.jsp"},
                              {title:"下属日志",url:contextPath+"/system/core/base/diary/index_branch.jsp"}
                              ]); 

</script>
</html>
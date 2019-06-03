<%@page import="com.tianee.webframe.util.auth.TeeFunctionControl"%>
<%TeeFunctionControl.distinguishTheVersion(request,response);%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	String flowId = TeeStringUtil.getString(request.getParameter("flowId"), "");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	
<script>

</script>

</head>
<body onload="" style="overflow: hidden;padding-left:10px;padding-right:10px;padding-top: 5px">
	
	<div class="titlebar clearfix" >
		<img class = 'tit_img' style="margin-right:10px" src="<%=contextPath %>/common/zt_webframe/imgs/gwgl/gwds/icon_gongwendaishou.png">
		<p class="title">公文待收</p>
		<ul id = 'tab' class = 'tab clearfix'>
			
		</ul>
		<span class="basic_border_grey fl"></span>
	</div>
	
	<div id="tab-content" style="padding-left: 10px;padding-right:10px"></div>
	
</body>
<script>
 $.addTab("tab","tab-content",[{title:"待签收",url:contextPath+"/system/subsys/doc/daishou/daiqianshou.jsp?flowId=<%=flowId%>"},
                              {title:"已签收",url:contextPath+"/system/subsys/doc/daishou/yiqianshou.jsp?flowId=<%=flowId%>"},
                              {title:"拒签收",url:contextPath+"/system/subsys/doc/daishou/juqianshou.jsp?flowId=<%=flowId%>"},
                              ]); 

</script>

</html>
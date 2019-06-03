<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.tianee.oa.core.general.TeeSysCodeManager" %>
<%
	String infoType=request.getParameter("infoType");
	String infoTypeDesc = TeeSysCodeManager.getChildSysCodeNameCodeNo("NEWS_TYPE",infoType);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header.jsp" %>

<script>


function doInit(){
	$("#group").group();
	changePage(1);
}


function changePage(index){
	switch(index){
	case 1:
		$("#frame0").attr("src",contextPath+"/system/core/base/infoPub/person/queryNotLookList.jsp?infoType=<%=infoType%>");
		break;
	case 2:
		$("#frame0").attr("src",contextPath+"/system/core/base/infoPub/person/queryLookAllList.jsp?infoType=<%=infoType%>");
		break;
	case 3:
		$("#frame0").attr("src",contextPath+"/system/core/base/infoPub/person/queryInfo.jsp?infoType=<%=infoType%>");
		break;
	}
}

</script>

</head>

<body onload="doInit()"  style="overflow:hidden">
<div class="base_layout_left" style="text-align:center;width:150px;" >
	<i class="glyphicon glyphicon-edit nav_icon"></i>
	<br/><br/><br/>
	<ul class="my_nav" id="group">
		<li class='active' onclick="changePage(1)">未读<%=infoTypeDesc %></li>
		<li onclick="changePage(2)">全部<%=infoTypeDesc %></li>
		<li onclick="changePage(3)"><%=infoTypeDesc %>查询</li>
	</ul>
</div>
<div class="base_layout_right" style="left:151px;overflow:hidden">
	<iframe id="frame0" frameborder=0 style="width:100%;height:100%"></iframe>
</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

<%@ include file="/header/header2.0.jsp" %>
<%
String typeId = request.getParameter("typeId");
typeId=typeId==null?"":typeId;
%>
<script>


</script>

</head>
<body onload="" style="overflow: hidden;padding-left:10px;padding-right:10px;padding-top: 5px">
	
	<div class="titlebar clearfix">
		<img class ='tit_img' style="margin-right:10px" src="<%=contextPath %>/common/zt_webframe/imgs/xzbg/tzgg/icon_gonggaochakan.png"/>
		<p class="title">公告查看</p>
		<ul id = 'tab' class = 'tab clearfix'>
			
		</ul>
		<span class="basic_border_grey fl"></span>
	</div>
	
	<div id="tab-content" style="padding-left: 10px;padding-right:10px;"></div>
	
</body>
<script>
 $.addTab("tab","tab-content",[{title:"未读信息",url:contextPath+"/system/core/base/notify/person/queryNotLookList.jsp?typeId=<%=typeId%>"},
                              {title:"已读信息",url:contextPath+"/system/core/base/notify/person/queryLookedList.jsp?typeId=<%=typeId%>"},
                              {title:"信息查询",url:contextPath+"/system/core/base/notify/person/queryNotify.jsp?typeId=<%=typeId%>"},
                              ]); 

</script>

</html>
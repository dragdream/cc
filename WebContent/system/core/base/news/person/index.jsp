<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header2.0.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath() %>/common/jqueryui/jquery.layout-latest.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<style type="text/css">
html{
  padding: 5px;
  width: 100%;
  height: 100%;
  box-sizing: border-box;
}
</style>
<%
String typeId = request.getParameter("typeId");
typeId=typeId==null?"":typeId;
%>


</head>

<body style="overflow:hidden">

<div class="titlebar clearfix" >
		<img class = 'tit_img' style="margin-right: 10px;" src="<%=contextPath%>/common/zt_webframe/imgs/xzbg/xwfb/icon_news.png">
		<p class="title">新闻查收</p>
		<ul id = 'tab' class = 'tab clearfix'>
			
		</ul>
		<span class="basic_border_grey fl"></span>
</div>
	
	<div id="tab-content" style="padding-left: 10px;padding-right: 10px;"></div>
	

</body>
<script>
 $.addTab("tab","tab-content",[{title:"未读新闻",url:contextPath+"/system/core/base/news/person/queryNotLookList.jsp?typeId=<%=typeId%>"},
                              {title:"全部新闻",url:contextPath+"/system/core/base/news/person/queryLookAllList.jsp?typeId=<%=typeId%>"},
                              {title:"新闻查询",url:contextPath+"/system/core/base/news/person/queryNews.jsp?typeId=<%=typeId%>"},
                              ]); 

</script>
</html>
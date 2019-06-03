<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"), 0);
	int flowSortId = TeeStringUtil.getInteger(request.getParameter("flowSortId"), 0);//流程分类类型sid
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%-- <script type="text/javascript" src="<%=contextPath%>/common/js/tabs/tabs.js"></script> --%>
	<!-- jQuery 布局器 -->
<%-- <script type="text/javascript" src="<%=request.getContextPath() %>/common/jqueryui/jquery.layout-latest.js"></script> --%>
	
<script>

function doInit(){
	$.addTab("tab","tab-content",[{title:"借书审批",url:contextPath+"/system/subsys/booksManagement/bookManage/borrowTrue2.jsp"},
	                                {title:"还书审批",url:contextPath+"/system/subsys/booksManagement/bookManage/returnTrue2.jsp"}]);
}

</script>

</head>
<body onload="doInit()" style="overflow: hidden;padding-left:10px;padding-right:10px;padding-top: 5px;">
<!-- <div id="tabs" class="base_layout_top"></div>
<div id="tabs-content" class="base_layout_center"></div> -->
<div class="titlebar clearfix" >
		    <img id="img1" class = 'tit_img' style="margin-right: 10px;" src="<%=contextPath %>/common/zt_webframe/imgs/gzl/icon_我的工作.png">
		    <span class="title">审批</span>
		<ul id = 'tab' class = 'tab clearfix'>
			
		</ul>
		<span class="basic_border_grey fl"></span>
	</div>
	
	<div id="tab-content" style="padding-left: 10px;padding-right:10px"></div>
	

</body>
</html>
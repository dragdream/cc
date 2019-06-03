<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<%@ include file="/header/header2.0.jsp"%>

<script type="text/javascript"
		src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/jqueryui/jquery.layout-latest.js"></script>
	<%-- <script type="text/javascript" src="<%=contextPath%>/system/core/base/meeting/js/meeting.js"></script> --%>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<style type="text/css">
html {
	padding: 5px;
}
</style>
	<%
		String optType = request.getParameter("optType");
		optType = optType == null ? "" : optType;
	%>

</head>
<body style="overflow: hidden;">
	<div class="titlebar clearfix">
		<img class='tit_img' style="margin-right: 10px;"
			src="<%=contextPath%>/common/zt_webframe/imgs/xzbg/gdzc/icon_ywmx.png">
			<p class="title">业务明细</p>
			<ul id='tab' class='tab clearfix'>

			</ul> <span class="basic_border_grey fl"></span>
	</div>

	<div id="tab-content" style="padding-left: 10px; padding-right: 10px;"></div>

</body>
<script>
 $.addTab("tab","tab-content",[{title:"领用明细",url:contextPath+"/system/core/base/fixedAssets/record/appDetailsManage.jsp?optType=<%=optType%>"},
                              {title:"归还明细",url:contextPath+"/system/core/base/fixedAssets/record/backDetailsManage.jsp?optType=<%=optType%>"},
                              {title:"维修明细",url:contextPath+"/system/core/base/fixedAssets/record/repairDetailsManage.jsp?optType=<%=optType%>"},
                              {title:"报废明细",url:contextPath+"/system/core/base/fixedAssets/record/discardDetailsManage.jsp?optType=<%=optType%>"}]);
</script>

</html>
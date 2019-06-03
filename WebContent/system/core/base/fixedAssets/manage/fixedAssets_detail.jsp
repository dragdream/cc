<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
     	String sid = request.getParameter("sid");
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/jqueryui/jquery.layout-latest.js"></script>

<script>
var sid = "<%=sid%>";
</script>
<style type="text/css">
html {
	padding: 5px;
	width: 100%;
    height: 100%;
    box-sizing: border-box;
	
}
</style>

</head>
<body style="font-size:12px;">
<body style="overflow: hidden;">
	<div class="titlebar clearfix">
		<img class='tit_img' style="margin-right: 10px;"
			src="<%=contextPath%>/common/zt_webframe/imgs/xzbg/gdzc/icon_zcxq.png">
			<p class="title">资产详情</p>
			<ul id='tab' class='tab clearfix'>

			</ul> <span class="basic_border_grey fl"></span>
	</div>

	<div id="tab-content" style="padding-left: 10px; padding-right: 10px;top:61px;"></div>

</body>
<script>
 $.addTab("tab","tab-content",[{title:"基本信息",url:contextPath+"/system/core/base/fixedAssets/manage/fixedAssets_detailBaseInfo.jsp?sid=<%=sid%>"},
                              {title:"资产流向",url:contextPath+"/system/core/base/fixedAssets/manage/fixedAssets_detailTurn.jsp?sid=<%=sid%>"},
                              {title:"折旧记录",url:contextPath+"/system/core/base/fixedAssets/manage/fixedAssets_detailRecord.jsp?sid=<%=sid%>"}   
                              ]);
</script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/header/header.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>桌面模块设置</title>
	<script type="text/javascript" src="<%=contextPath %>/common/js/tabs/tabs.js"></script>
	<!-- jQuery 布局器 -->
	<script type="text/javascript" src="<%=contextPath %>/common/jqueryui/jquery.layout-latest.js"></script>

	<script>
	function doInit(){
		$.addTab("tabs","tabs-content",[{title:"桌面管理",url:contextPath+"/system/core/system/sysPorlet/desktop/index.jsp",active:true},
		                                {title:"模块管理",url:contextPath+"/system/core/system/sysPorlet/modules.jsp",active:false}]);
	}

	</script>
</head>
<body onload="doInit()" style="overflow:hidden">
<div id="tabs" class="base_layout_top"></div>
<div id="tabs-content" class="base_layout_center" style="overflow:hidden"></div>
</body>
</html>
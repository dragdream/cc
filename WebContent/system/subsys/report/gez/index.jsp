<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/tabs/tabs.js"></script>
	<!-- jQuery 布局器 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/common/jqueryui/jquery.layout-latest.js"></script>
	
<script>

function doInit(){
	
	//$("#layout").layout({auto:true});
	$.addTab("tabs","tabs-content",[{title:"资源列表",url:contextPath+"/system/subsys/report/gez/list.jsp",active:true},
	                                {title:"参数设置",url:contextPath+"/system/subsys/report/gez/params.jsp",active:false}]);
}

</script>

</head>
<body onload="doInit()" style="padding:5px 0px 0px 1px;">
<div id="tabs" class="base_layout_top"></div>
<div id="tabs-content" class="base_layout_center"></div>

</body>
</html>
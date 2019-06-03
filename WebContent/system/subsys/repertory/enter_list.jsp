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
	$.addTab("tabs","tabs-content",[{title:"待办入库",url:contextPath+"/system/core/workflow/flowrun/list/getReceivedWorks.jsp?flowId=48",active:true},
	                                {title:"办结入库",url:contextPath+"/system/core/workflow/flowrun/list/getHandledWorks.jsp?flowId=48",active:false},
	                                {title:"<div onclick='createNewWork(48);window.event.cancelBubble = true;'>入库登记</div>",url:undefined,active:false}]);
}

</script>

</head>
<body onload="doInit()" style="padding:5px 0px 0px 1px;">
<div id="tabs" class="base_layout_top"></div>
<div id="tabs-content" class="base_layout_center"></div>

</body>
</html>
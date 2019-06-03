<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/tabs/tabs.js"></script>
<%
int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
String currEnd = request.getParameter("currEnd");
%>	
<script>

function doInit(){
	$.addTab("tabs","tabs-content",[{title:"单人排班",url:contextPath+"/system/core/base/pm/onduty/addOrUpdate.jsp?sid=<%=sid%>&&currEnd='<%=currEnd%>'",active:true},
	                                {title:"批量排班",url:contextPath+"/system/core/base/pm/onduty/addOrUpdateBatch.jsp",active:false},
	                                {title:"排班导入",url:contextPath+"/system/core/base/pm/onduty/import.jsp",active:false}]);
}

</script>

</head>
<body onload="doInit()" style="overflow:hidden">
<div id="tabs" class="base_layout_top"></div>
<div id="tabs-content" class="base_layout_center"></div>
</body>
</html>
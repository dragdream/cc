<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ include file="/header/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/gray/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css"/>
<script type="text/javascript" src="<%=contextPath%>/common/js/tools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/easyuiTools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sys.js"></script>
<script>


function doInit(){

	easyuiTools.addTab({src:contextPath+"/system/core/workflow/workmanage/flowTimerTask/addTimerTask.jsp",title:"新建定时任务",closable:false,reload:true},$("#tabDiv"));
	easyuiTools.addTab({src:contextPath+"/system/core/workflow/workmanage/flowTimerTask/manageTimerTask.jsp",title:"定时任务管理",closable:false},$("#tabDiv"));
	$("#tabDiv").tabs("select",0);
	
}

</script>

</head>
<body onload="doInit()">
<div id="tabDiv" class="easyui-tabs" vfit="true" border="0"></div>
</body>
</html>
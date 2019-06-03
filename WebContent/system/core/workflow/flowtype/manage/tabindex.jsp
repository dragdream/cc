<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	int flowTypeId = TeeStringUtil.getInteger(request.getParameter("flowTypeId"),0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/js/tabs/demo.css"/>
<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css"/>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/tabs/tabs.js"></script>
<link href="<%=cssPath%>/bootstrap.min.css" rel="stylesheet">
<script>

function doInit(){
	<%-- easyuiTools.addTab({src:contextPath+"/system/core/workflow/flowtype/manage/edit.jsp?flowTypeId=<%=flowTypeId%>",title:"流程基本属性",closable:false,reload:true},$("#tabDiv"));
	easyuiTools.addTab({src:contextPath+"/system/core/workflow/workmanage/flowPriv/manager.jsp?flowTypeId=<%=flowTypeId%>",title:"权限管理",closable:false,reload:true},$("#tabDiv"));
	easyuiTools.addTab({src:contextPath+"/system/core/workflow/workmanage/flowTimerTask/manageTimerTask.jsp?flowTypeId=<%=flowTypeId%>",title:"定时任务",closable:false,reload:true},$("#tabDiv"));
	$("#tabDiv").tabs("select",0); --%>
	

	var opts = [{title:"流程基本属性",url:contextPath+"/system/core/workflow/flowtype/manage/edit.jsp?flowTypeId=<%=flowTypeId%>",active:true},
	            {title:"流程高级配置",url:contextPath+"/system/core/workflow/flowtype/manage/senior_setting.jsp?flowTypeId=<%=flowTypeId%>",active:false}
	            ];
	
	$.addTab("tabs","tabs-content",opts);
	//easyuiTools.addTab({src:contextPath+"/system/core/workflow/flowtype/flowdesign/setprops/ext.jsp",title:"扩展功能",closable:false},$("#tabDiv"));
}

</script>

</head>
<body onload="doInit()"  style="margin:0px;overflow:hidden">
<div id="tabs" class="base_layout_top"></div>
<div id="tabs-content" class="base_layout_center"></div>

</body>
</html>
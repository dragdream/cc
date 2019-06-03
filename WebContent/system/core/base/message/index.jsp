<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header2.0.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/js/tabs/demo.css"/>
<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css"/>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/tabs/tabs.js"></script>

<script>

function doInit(){
	$("#layout").layout({auto:true});//开启布局器
	$.addTab("tabs","tabs-content",{url:contextPath+"/system/core/base/message/manager/index.jsp",title:"消息管理",active:true});
	$.addTab("tabs","tabs-content",{url:contextPath+"/system/core/base/message/group/index.jsp",title:"群组管理",active:false});
	//easyuiTools.addTab({src:contextPath+"/system/core/workflow/flowtype/flowdesign/setprops/ext.jsp",title:"扩展功能",closable:false},$("#tabDiv"));
}

</script>

</head>
<body onload="doInit()"  style="margin:0px;overflow:hidden">
<div id="layout">
	<div id="tabs" layout="north" height="35" class="tee_tab_header"></div>
	<div id="tabs-content" layout="center"></div>
</div>
</body>
</html>


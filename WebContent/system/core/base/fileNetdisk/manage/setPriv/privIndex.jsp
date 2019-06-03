<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>权限设置</title>
<script type="text/javascript" src="<%=contextPath%>/common/js/tabs/tabs.js"></script>
<script type="text/javascript">
function doInit(){
	$("#layout").layout({auto:true});//开启布局器
	$.addTab("tabs","tabs-content",{url:contextPath+"/system/core/base/fileNetdisk/manage/setPriv/userPriv.jsp",title:"用户权限设置",active:true});
	$.addTab("tabs","tabs-content1",{url:contextPath+"/system/core/base/fileNetdisk/manage/setPriv/deptPriv.jsp",title:"部门权限设置",active:false});
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
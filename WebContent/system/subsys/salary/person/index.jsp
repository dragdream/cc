<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/tabs/tabs.js"></script>
<script>

function doInit(){
	$("#layout").layout({auto:true});//开启布局器
	$.addTab("tabs","tabs-content",{title:"员工薪酬基数设置",url:contextPath+"/system/subsys/salary/person/index_person.jsp",active:true});
	$.addTab("tabs","tabs-content",{title:"员工薪酬基数批量设置",url:contextPath+"/salaryManage/personListMore.action",active:false});
}

</script>

</head>
<body onload="doInit()" >
<div id="layout">
	<div id="tabs" layout="north" height="40" class="tee_tab_header" style="margin-top:5px"></div>
	<div id="tabs-content" layout="center"></div>
</div>
</body>
</html>
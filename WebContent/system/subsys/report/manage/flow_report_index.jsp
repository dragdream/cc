<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	TeePerson loginPerson = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<style>
</style>
<script>
var zTreeObj ;
function doInit(){
	//加载组织树
	var url = contextPath+"/flowSort/getFlowSortTree4Form.action";
	var config = {
			zTreeId:"treeDiv",
			requestURL:url,
			onClickFunc:clickEvent
		};
	zTreeObj = ZTreeTool.config(config);
}

function clickEvent(event, treeId, treeNode) {
	$("#frame")[0].contentWindow.location=contextPath+"/system/subsys/report/manage/right.jsp?sortId="+treeNode.id;
}

function getSelectIds(){
	var id = ZTreeTool.getSelectCheckedNodeIds(true,true);
}

</script>
</head>
<body onload="doInit()" style="overflow:hidden">
<div style="position:absolute;left:0px;top:0px;bottom:0px;width:150px;overflow:auto;">
	<ul id="treeDiv" class="ztree"  style="overflow:visible;border:0px;"></ul>
</div> 
<div style="position:absolute;left:150px;top:0px;bottom:0px;right:0px;overflow:auto;border-left:1px solid #c4d8f2">
	<iframe frameborder="0" id="frame" style="height:100%;width:100%" src="tip.jsp"></iframe>
</div>
</body>
</html>
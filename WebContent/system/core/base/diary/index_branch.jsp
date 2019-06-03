<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp" %>
<%@ include file="/header/ztree.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>

<script>
function doInit(){
	$("#layout").layout({auto:true});
	initTree();
	
}





function initTree(){
	//加载组织树
	var url = contextPath+"/diaryController/getXsTree.action";
	var config = {
			zTreeId:"deptIdZTree",
			requestURL:url,
			onClickFunc:clickEvent
		};
	zTreeObj = ZTreeTool.config(config);
}

function clickEvent(event, treeId, treeNode){

	$("#frame").attr("src",contextPath+"/system/core/base/diary/index_branch_right.jsp?userId="+treeNode.id);
	
	
}


</script>
</head>
<body onload="doInit();" style="overflow:hidden;">
<div id="layout">
	
	<div layout="center">
		<div layout="west" width="210" style="overflow-y:auto">
			<ul id="deptIdZTree" class="ztree" style="margin-top: 0;"></ul>
		</div>
		<div layout="center">
			<iframe id="frame" frameborder="0" style="width:100%;height:100%" src="common_info.jsp"></iframe>
		</div>
	</div>
</div>
</body>
</html>
<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/header/header.jsp" %>
<%@ include file="/header/ztree.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>

<script>
function doInit(){
	$("#layout").layout({auto:true});

	/* 获取部门树 */

 	var moduleId = "3";
 	var deptIdZTree= "deptIdZTree";
 	var param = {onClickFunc:onClickTest,defaultOpen:true};//onClickFunc:  点击事件    defaultOpen:默认展开  true-展开   false-不展开*/
	getPostOrgTreeTool.getPostOrgTreeByModule(moduleId ,deptIdZTree , param);
	
}

function onClickTest(event, treeId, treeNode) {
	
	if( treeNode.id && treeNode.id.split(";").length <2){//如果没改变别是人员
		$("#frame").attr("src",contextPath+"/system/core/base/pm/public/right.jsp?userId="+treeNode.id);
	}
	
}

</script>
</head>
<body onload="doInit();" style="overflow:hidden">
<div id="layout">
	<div layout="north" height="60">
		<div class="moduleHeader">
			<div class="pull-left">
				<b><i class="glyphicon glyphicon-list-alt"></i>&nbsp;下属监控</b>
			</div>
			<div style="clear:both"></div>
		</div>
	</div>
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
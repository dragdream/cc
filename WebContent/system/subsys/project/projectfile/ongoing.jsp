<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>进行中</title>
</head>
<script>
var zTreeObj ;
function doInit(){
	initTree();
}



function initTree(){
	//加载组织树
	var url = contextPath+"/projectController/getProjectTree.action?status=3";
	var config = {
			zTreeId:"treeDiv",
			requestURL:url,
			onClickFunc:clickEvent
		};
	zTreeObj = ZTreeTool.config(config);

	
}


function clickEvent(event, treeId, treeNode) {
	if(treeNode.params.type==1){//项目节点
		$("#frame0").attr("src",contextPath+"/system/subsys/project/projectdetail/basic/info.jsp?uuid="+treeNode.id);
	}else if(treeNode.params.type==2){//网盘节点
		$("#frame0").attr("src",contextPath+"/system/subsys/project/projectfile/list.jsp?diskId="+treeNode.id+"&&diskName="+treeNode.name+"&&projectId="+treeNode.params.projectId);
		
	}
}
</script>
<body style="overflow:hidden" onload="doInit()" >
	<div style="position:absolute;left:0px;top:0px;bottom:0px;width:240px;background-color:#f9f9f9;">
		<ul id="treeDiv" class="ztree" style="overflow:hidden;overflow-y:auto;border:0px;background:#f9f9f9;margin-top: 10px"></ul>
	</div>
	<div style="position:absolute;top:0px;bottom:0px;right:0px;left:241px;">
		<iframe id="frame0" style="height:100%;width:100%" src="comfireNo.jsp"></iframe>
	</div>
</body>
</html>
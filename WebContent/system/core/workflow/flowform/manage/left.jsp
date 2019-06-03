<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>Tenee办公自动化智能管理平台</title>
	<link href="<%=cssPath %>/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=contextPath %>/common/jquery/ztree/css/demo.css" type="text/css">
<link rel="stylesheet" href="<%=contextPath %>/common/jquery/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=contextPath %>/common/jquery/jquery-min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/tools.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/ZTreeSync.js"></script>
<script type="text/javascript">
var zTreeObj ;
var contextPath = "<%=contextPath %>";
function doInit(){
	initTree();
}

function initTree(){
	//加载组织树
	var url = contextPath+"/flowForm/getTreeData.action";
	var config = {
			zTreeId:"treeDiv",
			requestURL:url,
			onClickFunc:clickEvent
		};
	zTreeObj = ZTreeTool.config(config);
}

function clickEvent(event, treeId, treeNode) {
	if(treeNode.params.type==1){//分类节点
		parent.document.getElementById("right").contentWindow.location=contextPath+"/system/core/workflow/flowform/manage/list.jsp?sortId="+treeNode.id+"&sortName="+encodeURI(treeNode.name);
	}else if(treeNode.params.type==2){//表单节点
		parent.document.getElementById("right").contentWindow.location=contextPath+"/system/core/workflow/flowform/manage/edit.jsp?formId="+treeNode.id;
	}
}
function getSelectIds(){
	var id = ZTreeTool.getSelectCheckedNodeIds(true,true);
}

</SCRIPT>
</head>
<body onload="doInit()" style="background:#f9f9f9">
<div class="base_layout_top" stye="baclground:#cdcdcd">
	<span class="easyui_h1">表单分类</span>
</div>
<div class="base_layout_center">
	<ul id="treeDiv" class="ztree" style="overflow:hidden;overflow-y:auto;border:0px;width:180px;height:auto;background:#f9f9f9"></ul>
</div>

</body>

</html>
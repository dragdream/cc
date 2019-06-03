<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String paths = request.getParameter("paths");
	String type = request.getParameter("type");
	String rootId=request.getParameter("rootId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>图片库树</title>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script>
var paths="<%=paths%>";
var type="<%=type%>";
var rootId="<%=rootId%>";
var nodeId = "";

function doInit(){
	getFileTree();
}

function getFileTree(){
	var url =  "<%=contextPath %>/teeImgBaseController/getImgBaseTree.action?isManager=1";
	var config = {
			zTreeId:"selectFolderZtree",
			requestURL:url,
           	onClickFunc:onClickTree
		};
	zTreeObj = ZTreeTool.config(config); 	
}

function onClickTree(event, treeId, treeNode) {
	var sid = treeNode.id;
	nodeId = sid;
}


function commit(){
	if(nodeId==""){
		$.MsgBox.Alert_auto("请选择目的目录！");
		return;
	}
	if(nodeId==rootId&&type=="cut"){
		$.MsgBox.Alert_auto("请不要选择文件原目录作为目标目录！");
		return;
	}
	var  url = contextPath+"/teeImgBaseController/copyOrCutFiles.action?paths="+paths+"&sid="+nodeId+"&type="+type;
	var json = tools.requestJsonRs(url);
	return json;
}

</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px">
	<div style='float:left;width:100%;'>
			<ul id="selectFolderZtree" class="ztree" style="border:0px;width:95%;height:"></ul>
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String boxIds =TeeStringUtil.getString(request.getParameter("boxIds"));
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background-color:#f2f2f2">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>卷库树</title>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script>
var boxIds="<%=boxIds%>";

var nodeId = "";

function doInit(){
	getStoreHouseTree();
}

function getStoreHouseTree(){
	var url =  "<%=contextPath %>/TeeStoreHouseController/treeNode.action";
	var config = {
			zTreeId:"storeHouseTree",
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
		$.MsgBox.Alert_auto("请选择将要归档的卷库！");
		return;
	}
	var  url = contextPath+"/damBoxController/archive.action?storeHouseId="+nodeId+"&boxIds="+boxIds;
	var json = tools.requestJsonRs(url);
	return json;
}

</script>

</head>
<body onload="doInit()" style="overflow:auto;font-size:12px;background-color: #f2f2f2">
	<div style='float:left;width:100%;'>
			<ul id="storeHouseTree" class="ztree" style="border:0px;width:95%;height:"></ul>
	</div>
</body>
</html>
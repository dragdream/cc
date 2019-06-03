<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp" %>
<%@include file="/header/ztree.jsp" %>
<title>部门归档</title>
<script type="text/javascript">
var zTreeObj ;

var storeId=0;
var storeName="";
function doInit(){
	
	var url = contextPath+"/TeeStoreHouseController/treeNode.action";
	var config = {
			zTreeId:"ztree",
			requestURL:url,
			param:{},
			onClickFunc:function(event, treeId, treeNode){
				storeId= treeNode.id;
				storeName=treeNode.name;
				
			},
			async:true
			
		};
	zTreeObj = ZTreeTool.config(config);
}


function sel(){
	if(storeId==0){
		top.$.MsgBox.Alert_auto("请选择归档目录！");
		return null;
	}else{
		return {storeName:storeName,storeId:storeId};
	}
}


</script>
</head>
<body onload="doInit();">
   <div style="width: 100%;height: 100%">
		<ul id="ztree" class="ztree" style="padding:5px;margin:5px"></ul>
	</div>
</body>
</html>
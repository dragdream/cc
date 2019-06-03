<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String siteId = request.getParameter("siteId");
	String channelId = request.getParameter("channelId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background-color: #f2f2f2;">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp" %>
<%@include file="/header/ztree.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/TeeMenu.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/layout/layout.js"></script>
<script>
var siteId ="<%=siteId%>" ;
var channelId = "<%=channelId%>";
var zTreeObj ;
function doInit(){
	
	var url = contextPath+"/cmsTreeNode/treeNode.action";
	var param = {siteIds:siteId,chnls:channelId};
	var config = {
			zTreeId:"ztree",
			requestURL:url,
			param:param,
			onClickFunc:function(event, treeId, treeNode){
				var sp = treeNode.id.split(";");
				//clickIt(treeNode.extend1,sp[0],sp[1]);
			},
			async:true
			
		};
	zTreeObj = ZTreeTool.config(config);
}

function commit(){
	var nodes = zTreeObj.getSelectedNodes();
	if(nodes.length==0){
		$.MsgBox.Alert_auto("请选择一个站点/栏目");
		return;
	}
	
	var sp = nodes[0].id.split(";");
	var chnlId = 0;
	if(sp[1]=="chnl"){
		chnlId = sp[0];
	}
	
	var targetIds = channelId.split(",");
	for(var i=0;i<targetIds.length;i++){
		tools.requestJsonRs(contextPath+"/cmsChannel/copyChannel.action",{sid:targetIds[i],parentChnl:chnlId});
	}
	return true;
}

</script>
</head>
<body onload="doInit();" style="margin:0px;padding-top: 20px;background-color: #f2f2f2;">
	<ul id="ztree" class="ztree" style="padding:0px;margin:0px"></ul>
</body>
</html>
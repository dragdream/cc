<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp" %>
<%@include file="/header/ztree.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/TeeMenu.js"></script>
<script>
var zTreeObj ;
function doInit(){
	
	var url = contextPath+"/cmsTreeNode/checkTreeNode.action";
	var config = {
			zTreeId:"ztree",
			requestURL:url,
			param:{},
			onClickFunc:function(event, treeId, treeNode){
				var sp = treeNode.id.split(";");
				clickIt(treeNode.extend1,sp[0],sp[1],treeNode.extend2);
			},
			async:true
			
		};
	zTreeObj = ZTreeTool.config(config);
}

function clickIt(siteId,channelId,type,hasPriv){
	var node;
	if(type=="site"){
		node = zTreeObj.getNodeByParam("id",siteId+";site",null);
	}else if(type=="chnl"){
		if(hasPriv==1||hasPriv=="1"){
			$("#frame").attr("src",contextPath+"/system/subsys/cms/docs.jsp?siteId="+siteId+"&channelId="+channelId);
		}else{
			$("#frame").attr("src",contextPath+"/system/subsys/cms/tip1.jsp");
		}
		
		node = zTreeObj.getNodeByParam("id",channelId+";chnl",null);
	}
	zTreeObj.expandNode(node,true,true,true);
}

function refreshSiteNode(siteId){
	var node = zTreeObj.getNodeByParam("id",siteId+";site",null);
	zTreeObj.reAsyncChildNodes(node, "refresh");
}

</script>
</head>
<body onload="doInit();" style="overflow:hidden;padding-left: 10px;padding-right: 10px;padding-top: 8px;">
<div id="layout">
	<div layout="north" height="40" style="position: absolute;height: 40px;width: 100%;top:15px">
		<div style="vertical-align: middle;">
			<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/cms/image/sh.png">
		    <span class="title">文档审核</span>
		    <span class="basic_border_grey" ></span>
		</div>
	</div>
	<div  style="overflow:auto;position: absolute;top:51px;width: 230px;">
		<ul id="ztree" class="ztree" style="padding:0px;margin:0px"></ul>
	</div>
	<div  style="overflow:hidden;position: absolute;top:41px;right: 0px;bottom: 0px;left: 231px;" >
		<iframe id="frame" frameborder="0" style="width:100%;height:100%" src="tip.jsp"></iframe>
	</div>
</div>
</body>
</html>
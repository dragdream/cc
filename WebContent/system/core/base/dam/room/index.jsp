<%@page import="com.tianee.webframe.util.auth.TeeFunctionControl"%>
<%TeeFunctionControl.distinguishTheVersion(request,response);%>
<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp" %>
<%@include file="/header/ztree.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script>
var zTreeObj ;
function doInit(){
	
	var url = contextPath+"/TeeStoreHouseController/treeNode.action";
	var config = {
			zTreeId:"ztree",
			requestURL:url,
			param:{},
			onClickFunc:function(event, treeId, treeNode){
				var id= treeNode.id;
				clickIt(id);
			},
			async:true
			
		};
	zTreeObj = ZTreeTool.config(config);
}

function clickIt(id){
	//var node;
	$("#frame").attr("src",contextPath+"/system/core/base/dam/room/edit.jsp?sid="+id);
	//node = zTreeObj.getNodeByParam("id",siteId+";site",null);

}

function refreshSiteNode(id){
	var node = zTreeObj.getNodeByParam("id",id,null);
	zTreeObj.reAsyncChildNodes(node, "refresh");
}



//新建卷库
function add(){
	
	$("#frame").attr("src",contextPath+"/system/core/base/dam/room/add.jsp");
}
</script>
</head>
<body onload="doInit();" style="overflow:hidden;padding-left: 10px;padding-right: 10px;padding-top: 8px;">
<div id="layout">
	<div height="40" style="position: absolute;height: 40px;width: 100%;top:15px" >
		<div style="vertical-align: middle;">
			<img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/base/dam/imgs/icon_jkgl.png">
		    <span class="title">卷库管理</span> 
		    <input type="button" onclick="add()" value="新建卷库" class="btn-win-white fr" style="margin-right: 20px;" />
		    <span class="basic_border_grey" style="margin-top: 10px;margin-right: 20px" ></span>  
		</div>	
		
	</div>
	
	<div  style="overflow:auto;position: absolute;top:60px;width: 210px;height: 90%">
		<ul id="ztree" class="ztree" style="padding:0px;margin:0px"></ul>
	</div>
	<div style="overflow:hidden;position: absolute;top:53px;right: 0px;bottom: 0px;left: 220px;border-left:solid 2px #f2f2f2;">
		<iframe id="frame" frameborder="0" style="width:100%;height: 100%" src="add.jsp"></iframe>
	</div>
</div>
</body>
</html>
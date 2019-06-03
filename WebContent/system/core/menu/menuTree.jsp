<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	int parentId = TeeStringUtil.getInteger(request.getParameter("sid"), 0);//上级Id
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<title>菜单树</title>
<link href="<%=cssPath %>/style.css" rel="stylesheet" type="text/css" />
<link  rel="stylesheet" type="text/css" href="<%=cssPath %>/stylebootstrap.css" />
<link rel="stylesheet" href="<%=contextPath %>/common/jquery/ztree/css/demo.css" type="text/css"/>
<link rel="stylesheet" href="<%=contextPath %>/common/jquery/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"/>

<style type="text/css">
div#rMenu {position:absolute; visibility:hidden; top:0; background-color: #555;text-align: left;padding: 2px;}
div#rMenu ul li{
	margin: 1px 0;
	padding: 0 5px;
	cursor: pointer;
	list-style: none outside none;
	background-color: #DFDFDF;
}
	</style>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/jquery-min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/tools.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/ZTreeSync.js"></script>
<script type="text/javascript">
var zTreeObj ;
var parentId = "<%=parentId%>";
function doInit(){
	var url = "<%=contextPath %>/sysMenu/getMenuTree.action?sid=" + parentId;
	var config = {
			zTreeId:"ztree",
			requestURL:url,
			param:{"para1":"111"},
			onClickFunc:addMenuFunc
			
		};
	zTreeObj = ZTreeTool.config(config);

}

function addMenuFunc(event, treeId, treeNode) {
	xparent.document.getElementById("pMenuIdDesc").value  = treeNode.name;  
	xparent.document.getElementById("pMenuId").value  = treeNode.id; 
}
function getSelectIds(){
	var id = ZTreeTool.getSelectCheckedNodeIds(true,true);
	alert(id);
}

</SCRIPT>
	<style type="text/css">
	
	.ztree li span.button.pIcon01_ico_open{margin-right:2px; background: url(<%=contextPath %>/common/jquery/ztree/css/zTreeStyle/img/diy/1_open.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.pIcon01_ico_close{margin-right:2px; background: url(<%=contextPath %>/common/jquery/ztree/css/zTreeStyle/img/diy/1_close.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.pIcon02_ico_open, .ztree li span.button.pIcon02_ico_close{margin-right:2px; background: url<%=contextPath %>/common/jquery/ztree/css/zTreeStyle/img/diy/2.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.icon01_ico_docu{margin-right:2px; background: url<%=contextPath %>/common/jquery/ztree/css/zTreeStyle/img/diy/3.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.icon02_ico_docu{margin-right:2px; background: url(<%=contextPath %>/common/jquery/ztree/css/zTreeStyle/img/diy/4.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.icon03_ico_docu{margin-right:2px; background: url(<%=contextPath %>/common/jquery/ztree/css/zTreeStyle/img/diy/5.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.icon04_ico_docu{margin-right:2px; background: url(<%=contextPath %>/common/jquery/ztree/css/zTreeStyle/img/diy/6.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.icon05_ico_docu{margin-right:2px; background: url(<%=contextPath %>/common/jquery/ztree/css/zTreeStyle/img/diy/7.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.icon06_ico_docu{margin-right:2px; background: url(<%=contextPath %>/common/jquery/ztree/css/zTreeStyle/img/diy/8.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
	
	</style>

</HEAD>

<BODY onload="doInit()">

<div align="center" style="padding-top:10px;">
<input type="button" class="btn btn-primary" value="关闭" onclick="CloseWindow();">

<ul id="ztree" class="ztree" style="overflow-y:auto;height:360px;width:270px;"></ul>
</div>


</BODY>
</HTML>
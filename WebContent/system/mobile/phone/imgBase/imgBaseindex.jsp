<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
   
%>

<!DOCTYPE HTML>
<html>
<head>
<title>图库</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<script type="text/javascript" src="js/iscroll.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/system/mobile/js/tools.js"></script>
<style>
#topPopover {
	position: fixed;
	top: 16px;
	right: 6px;
	width: 120px;
}
#topPopover .mui-popover-arrow {
	left: auto;
	right: 6px;
}
</style>
<script>
    function doInit(){
    	getFileTree();
    }
    function getFileTree(){
    	var url =  "<%=contextPath %>/teeImgBaseController/getImgBaseTree.action";
    	var config = {
    			zTreeId:"selectFolderZtree",
    			requestURL:url,
               	onClickFunc:onClickTree
    		};
    	zTreeObj = ZTreeTool.config(config); 	
    }
    function onClickTree(event, treeId, treeNode) {
    	var sid = treeNode.id;
    	$("#tips").css("display","none");
    	$("#loadMore").css("display","inline-block");
    	$("#controlArea").css("display","");
    	curPage=1;
    	rootId = getRoot(sid);
    	window.location.href="items.jsp?sid="+sid+"&rootId="+rootId+"&folder="+encodeURI(treeNode.name);
    }
    
    /**
     * 获取树根几点id;
     */
    function getRoot(sid){
    	zTreeObj = getZTrreObj(); 
    	var node = zTreeObj.getNodeByParam("id",sid,null);
    	if(node.getParentNode()==null){
    		return node.id;
    	}else{
    		return getRoot(node.getParentNode().id);
    	}
    }
     /**
      * 获取树对象
      */
     function getZTrreObj(){
       zTreeObj = $.fn.zTree.getZTreeObj("selectFolderZtree"); 
       return zTreeObj;
     }
</script>
</head>
<body onload="doInit();">
	<header class="mui-bar mui-bar-nav">
		<span class="mui-icon mui-icon-back" onclick="CloseWindow();"></span>
		<h1 class="mui-title">图片库</h1>
		<a class="mui-action-menu mui-icon mui-icon-more mui-pull-right" href="#topPopover" style="display: none;"></a>
	</header>
	<div>
	
	
	</div>
	<!--下拉刷新容器-->
	<div id="pullrefresh" class="mui-content mui-scroll-wrapper">
		<div class="mui-scroll">
			<!--数据列表-->
			<ul class="mui-table-view" id="list">
				<div class="forScoll" style="margin-top: 10px">
	    <ul id="selectFolderZtree" class="ztree" style="border:0px;width:100%;height:atuo;"></ul>
	</div>
			</ul>
		</div>
	</div>
	
	<!--右上角弹出菜单-->
	<div id="topPopover" class="mui-popover">
		<ul class="mui-table-view">
		    <li class="mui-table-view-cell" onclick="window.location = 'index.jsp?status=0';">未审批</li>
		    <li class="mui-table-view-cell" onclick="window.location = 'index.jsp?status=1';">已审批</li>
		  </ul>
	</div>
 
</body>
</html>
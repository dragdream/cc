<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="background-color: #f2f2f2;">



<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<title>卷库树</title>
</head>
<script type="text/javascript">
function doInit(){
	getTree();
}


function getTree(){
	var url =  contextPath+"/TeeStoreHouseController/treeNode.action";
	var config = {
			zTreeId:"ztree",
			requestURL:url,
           	onClickFunc:onClickTree,
			async:true
			
		};
	zTreeObj = ZTreeTool.config(config); 	
}

/**
 *  点击
 */
function onClickTree(event, treeId, treeNode) {
	var sid = treeNode.id;
	var name = treeNode.name;
	
	$("#parentId").val(sid);
	$("#parentName").val(name);
	
}
</script>


<body onload="doInit();" style="background-color: #f2f2f2;overflow-x:hidden ">
 <ul id="ztree" class="ztree" style="overflow-x:hidden;border:0px;width:98%;"></ul>
 
 <form id="form1" name="form1" action="" method="post">
   
 	<input type="hidden" id="parentId" name="parentId" value=""/>
 	<input type="hidden" id="parentName" name="parentName" value=""/>
 </form>
 
 
 
</body>
</html>
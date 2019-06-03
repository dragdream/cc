<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background-color:#f2f2f2">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件目录</title>
<script type="text/javascript">



function getFileTree(){
	var url =  "<%=contextPath %>/fileNetdisk/getAllFolderTree.action";
	var config = {
			zTreeId:"selectFolderZtree",
			requestURL:url,
           	onClickFunc:onClickTree,
			async:false,
			checkController:{
				enable : true,
				chkboxType: { "Y": "s", "N": "s" }
               },
			
		};
	zTreeObj = ZTreeTool.config(config); 	
}
/**
 *  点击
 */
function onClickTree(event, treeId, treeNode) {
	//alert(treeId + " treeNode>>" + treeNode);
	var sid = treeNode.id;
	var folderName = treeNode.name;
	//alert(sid + " folderName>>" + folderName);
	$("#folderSid").val(sid);
}


//点击确定  将选中的文件夹的名称串和id串传回父页面
function commit(){
	//获取所有被选中的
	 var treeObj=$.fn.zTree.getZTreeObj("selectFolderZtree");
     var nodes=treeObj.getCheckedNodes(true);
     var ids="";
	 var names="";
     for(var i=0;i<nodes.length;i++){
    	 if(i!=(nodes.length-1)){
    		 names+=nodes[i].name + ",";
             ids+=nodes[i].id+",";
    	 }else{
    		 names+=nodes[i].name;
             ids+=nodes[i].id;
    	 }
     }
    parent.document.getElementById('diskNames').value=names;
    parent.document.getElementById('diskIds').value=ids;
}







function doInit(){
  getFileTree();
}

</script>

</head>
<body onload="doInit();" style="background-color: #f2f2f2;overflow: hidden">
 <ul id="selectFolderZtree" class="ztree" style="overflow-x:hidden;border:0px;width:98%;height:200px;"></ul>
 
 <form id="form1" name="form1" action="" method="post">
 	<input type="hidden" id="folderSid" name="folderSid" value="">
 </form>
 
 
 
</body>
</html>
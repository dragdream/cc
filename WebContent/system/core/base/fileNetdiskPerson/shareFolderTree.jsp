<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>共享目录</title>
<script type="text/javascript">

function doInit(){
  getFileTree();
}

function getFileTree(){
	var url =  "<%=contextPath %>/fileNetdiskPerson/getShareFolderTree.action";
	var config = {
			zTreeId:"selectFolderZtree",
			requestURL:url,
           	onClickFunc:onClickTree,
			async:false,
			asyncExtend:true,
			onAsyncSuccess:onAsyncSuccessFunc
			
		};
	zTreeObj = ZTreeTool.config(config); 	
}
/**
 * zNodesLength ：树节点数
 * rtMsg： 返回的json rtMsg 信息
 */
function onAsyncSuccessFunc(zNodesLength , jsonData){
	//获取人员数组
	var nodeId = jsonData.rtData.nodeId;
	if(nodeId>0){
	  openFolderFunc(nodeId);
	}else{
	  openFolderFunc(0);
	}
}
/**
 *  点击
 */
function onClickTree(event, treeId, treeNode) {
	//var folderName = treeNode.name;
	var sid = treeNode.id;
	openFolderFunc(sid);
	
}

/**
 * 打开文件夹
 * @param sid
 */
function openFolderFunc(sid){
  var url = contextPath + "/system/core/base/fileNetdiskPerson/fileNetdiskShareList.jsp?shareFolderSid=" + sid + "&sid=" + sid;
  parent.file_main.location.href=url;
}

</script>
</head>
<body onload="doInit();">
 <ul id="selectFolderZtree" class="ztree" style="overflow-x:hidden;border:0px;width:95%;height:90%;"></ul>
 
</body>
</html>
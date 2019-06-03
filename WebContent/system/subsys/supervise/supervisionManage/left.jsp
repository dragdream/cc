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
<title></title>
<script type="text/javascript">
var zTreeObj;
function doInit(){
  getFileTree();
}

function getFileTree(){
	var url =  "<%=contextPath %>/supTypeController/getSupTypeTree.action";
	var config = {
			zTreeId:"selectFolderZtree",
			requestURL:url,
           	onClickFunc:onClickTree,
			async:false,
			onAsyncSuccess:onAsyncSuccessFunc
		};
	zTreeObj = ZTreeTool.config(config); 	
}



/**
 *  点击
 */
function onClickTree(event, treeId, treeNode) {
	//alert(treeId + " treeNode>>" + treeNode);
	var sid = treeNode.id;
	//var folderName = treeNode.name;
	//alert(sid + " folderName>>" + folderName);
	//$("#folderSid").val(sid);
	openFolderFunc(sid);
	
}

/**
 * zNodesLength ：树节点数
 * rtMsg： 返回的json rtMsg 信息
 */
function onAsyncSuccessFunc(zNodesLength , jsonData){
	//alert(tools.jsonObj2String(jsonData));
	//返回第一个id，然后右侧显示该目录文件
// 	var parentSid = jsonData.rtData.parentSid;
// 	if(parentSid>0){
// 	  openFolderFunc(parentSid);
// 	}
	
}

/**
 * 获取树对象
 */
function getZTrreObj(){
  zTreeObj = $.fn.zTree.getZTreeObj("selectFolderZtree"); 
  return zTreeObj;
}


 /**
 * 刷新 zTree 
 */
function reloadZTreeNodeFunc(){
  var zTreeObj = getZTrreObj();
  alert(zTreeObj);
  zTreeObj.refresh();
}

/**
 * 打开文件夹
 * @param sid
 */
function openFolderFunc(sid){
	var url="list.jsp?typeId="+sid;
	  parent.$("#file_main")[0].contentWindow.location.href=url;
}
</script>
</head>
<body onload="doInit();">
 <ul id="selectFolderZtree" class="ztree" style="border:0px;width:95%;height:"></ul>
 
</body>
</html>
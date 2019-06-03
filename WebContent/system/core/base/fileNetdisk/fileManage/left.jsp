<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String menuSid = request.getParameter("menuSid");
	if(menuSid == null){
		menuSid= "";
	}

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公共网盘目录</title>
<script type="text/javascript">
var zTreeObj;
function doInit(){
  getFileTree();
}

function getFileTree(){
	var url =  "<%=contextPath %>/fileNetdisk/getNetdiskFolderTree.action?menuSid=<%=menuSid%>";
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


//刷新整个树二不刷新右侧页面
function refreshFileTree(openFolderSid){
	var url =  "<%=contextPath %>/fileNetdisk/getNetdiskFolderTree.action?openFolderSid=" + openFolderSid;
	var config = {
			zTreeId:"selectFolderZtree",
			requestURL:url,
           	onClickFunc:onClickTree,
			async:false,
			asyncExtend:true,
			onAsyncSuccess:function(){}
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
 * 更新树节点
 * var nodeObj = {id:nodeId,name:nodeName,iconSkin:iconSkin,pid:nodeParentId};
 */
function updateZTreeNode(nodeObj){
	if(nodeObj && nodeObj.id){
		zTreeObj = getZTrreObj(); 
		var node = zTreeObj.getNodeByParam("id",nodeObj.id,null);
		if(node){
			if(nodeObj.name){
				node.name = nodeObj.name;
			}
			if(nodeObj.iconSkin){
				node.iconSkin = nodeObj.iconSkin;
			}
			zTreeObj.updateNode(node);
		}
		
	}
}

/**
 * 在节点id为parentNodeId的节点下创建树节点newNode
 * var newNode = {id:nodeId,name:nodeName,iconSkin:iconSkin,pid:nodeParentId};
 */
function createZTreeNode(parentNodeId,newNode){
  var zTreeObj = getZTrreObj();
  var parentNode = zTreeObj.getNodeByParam("id",parentNodeId,null);
  zTreeObj.addNodes(parentNode, newNode,false);
}

 /**
  * 根据节点id删除节点（多个节点id用逗号隔开）
  */
 function deledteZTreeNode(nodeIds){
   if(nodeIds){
     nodeIds +="";
     var zTreeObj = getZTrreObj();
     var nodeIdArray = nodeIds.split(",");
     for(var i=0;i<nodeIdArray.length;i++){
       if(nodeIdArray[i]){
         var deleteNode = zTreeObj.getNodeByParam("id",nodeIdArray[i],null);
         if(deleteNode){
           zTreeObj.removeNode(deleteNode);
         }
       }
     }
   }
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
	var url="fileManage.jsp?sid="+sid;
	  parent.$("#file_main")[0].contentWindow.location.href=url;
}
</script>
</head>
<body onload="doInit();">
 <ul id="selectFolderZtree" class="ztree" style="border:0px;width:95%;height:"></ul>
 
</body>
</html>
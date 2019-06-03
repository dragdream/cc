<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报表目录</title>
<script type="text/javascript">
var zTreeObj;
function doInit(){
  //获取报表目录树
  getFileTree();
}


function filter(treeId, parentNode, childNodes) {
	if(childNodes.rtState){
		childNodes = childNodes.rtData;
		if (!childNodes)
			return null;
		for ( var i = 0, l = childNodes.length; i < l; i++) {
			var childNodesName = childNodes[i].name;
			if(!childNodesName){
				childNodesName = "";
			}
			childNodes[i].name = childNodesName.replace(/\.n/g, '.');
		}
		return childNodes;
	}else{
		alert(childNodes.rtMsg);
		return "";
	}
}

//获取报表目录树
function getFileTree(){
	var url =  contextPath+"/quieeReportController/getReportFolderTree.action";
	var setting = {
		async: {
			enable: true,
			url:url,
			autoParam:["id"],
			//otherParam:{"otherParam":"zTreeAsyncTest"},
			dataFilter: filter
		},
		callback:{
			onClick:function(event, treeId, treeNode){
				var url="reportManage.jsp?sid="+treeNode.id;
				parent.$("#file_main")[0].contentWindow.location.href=url;
			}
		
		  
		}
	};
		
	$.fn.zTree.init($("#selectFolderZtree"), setting);
	
}







 /**
 * 刷新 zTree 
 */
function reload(){
  window.location.reload();
}

</script>
</head>
<body onload="doInit();">
 <ul id="selectFolderZtree" class="ztree" style="border:0px;width:95%;"></ul>
 
</body>
</html>
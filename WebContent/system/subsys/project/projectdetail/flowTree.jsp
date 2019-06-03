<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<title>流程树</title>
</head>
<script type="text/javascript">
function doInit(){
	getFileTree();
}


function getFileTree(){
	var url =  contextPath+"/flowType/getTreeData.action";
	var config = {
			zTreeId:"selectFlowTypeZtree",
			requestURL:url,
           	onClickFunc:onClickTree,	
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
	$("#flowId").val(sid);
	$("#flowName").val(folderName);
	
}


//点击确定  将选中的文件夹的名称串和id串传回父页面
function commit(){
	//获取所有被选中的
	 var treeObj=$.fn.zTree.getZTreeObj("selectFlowTypeZtree");
     var nodes=treeObj.getCheckedNodes(true);
     var ids="";
	 var names="";
     for(var i=0;i<nodes.length;i++){
    	 if(nodes[i].params.type==2){//代表是表单节点
    		 if(i!=(nodes.length-1)){
        		 names+=nodes[i].name + ",";
                 ids+=nodes[i].id+",";
        	 }else{
        		 names+=nodes[i].name;
                 ids+=nodes[i].id;
        	 } 
    	 }
    	 
     }
    return {ids:ids,names:names};
}
</script>


<body onload="doInit();" style="background-color: #f2f2f2;overflow-x:hidden ">
 <ul id="selectFlowTypeZtree" class="ztree" style="overflow-x:hidden;border:0px;width:98%;height:200px;"></ul>
 
 <form id="form1" name="form1" action="" method="post">
 	<input type="hidden" id="flowId" name="flowId" value=""/>
 	<input type="hidden" id="flowName" name="flowName" value=""/>
 </form>
 
 
 
</body>
</html>
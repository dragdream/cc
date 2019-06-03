<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>微信组织机构</title>

<script type="text/javascript" charset="UTF-8">
function doInit(){
	getDeptTree();
}

/**
 * 马上加载
 */
 var zTreeObj ;
 function getDeptTree(){
		
	 var url = "<%=contextPath %>/weixinDept/getWeixinOrg.action";
	 var config = {
				zTreeId:"orgZtree",
				requestURL:url,
				param:{"para1":"111"},
				async:false
			};
		zTreeObj = ZTreeTool.config(config);
 }
 function onUserAsyncSuccess(event, treeId, treeNode, msg) {//异步执行成功后
	// expandNodes();
	 
}
 /**
   *第一级展开部门
   */
function expandNodes() {
	 if(!zTreeObj){
		zTreeObj = $.fn.zTree.getZTreeObj("orgZtree"); 
	 }
	var nodes = zTreeObj.getNodes();
	zTreeObj.expandNode(nodes[0], true, false, false);
	if (nodes[0].isParent && nodes[0].zAsync  && nodes[0].id =='0') {//是第一级节点
		expandNodes(nodes[0].children);
	}
}

</script>
</head>
<body onload="doInit();">
	<fieldset>
		<legend>
			<h5 style="font-size:14px;font-weight:bold;font-family:微软雅黑">微信组织机构-用户</h5>
		</legend>
	  
		<ul id="orgZtree" class="ztree" style="overflow:auto;border:0px;margin-top:15px;width:98%;min-height:330px; padding:2px;"></ul>
	</fieldset>
</body>
</html>
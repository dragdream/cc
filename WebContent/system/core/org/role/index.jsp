<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.tianee.oa.core.partthree.util.TeePartThreeUtil" %>

<%
   TeePerson  loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
   boolean hasPriv=TeePartThreeUtil.checkHasPriv(loginUser, "SYS_USER_ROLE");
   if(!hasPriv){//没有权限
	   response.sendRedirect("/system/core/system/partthree/error.jsp");
   }
   
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<script>
function doInit(){
	getDeptTree();
	changePage("/system/core/org/role/role_manage.jsp?deptId=0") ;
}
function  changePage(url){
	$("#frame0").attr("src", url);
}

/**
 * 马上加载
 */
 var zTreeObj ;
 function getDeptTree(){
		var url = "/orgManager/checkOrg.action";
		var jsonObj = tools.requestJsonRs(url);
		if(jsonObj.rtState){
			var json = jsonObj.rtData;
			if(json.sid){
				var url = "/deptManager/getOrgDeptTree.action";
				var config = {
						zTreeId:"orgZtree",
						requestURL:url,
						param:{"para1":"111"},
						onAsyncSuccess:onDeptAsyncSuccess,
						onClickFunc:deptOnClick
						
					};
				zTreeObj = ZTreeTool.config(config);
			}else{
				alert("单位信息未录入，请您先填写单位信息！");
				return;
			}
		}
 }
 function onDeptAsyncSuccess(event, treeId, treeNode, msg) {//异步执行成功后

	expandNodes(); 
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

/**
 * 点击节点
 */
function deptOnClick(event, treeId, treeNode) {
	var uuid = treeNode.id;
	if(uuid.split(";").length == 2){
		changePage("/system/core/org/role/role_manage.jsp?deptId=" + uuid.split(";")[0]+"&deptName="+encodeURI(treeNode.name)) ;
	}else{
		changePage("/system/core/org/role/role_manage.jsp?deptId=0") ;
	}
}




</script>
</head>
<body onload="doInit()"  style="overflow:hidden">
	<div style="overflow-y:auto;overflow-x:hidden;position:absolute;left:0px;top:0px;bottom:0px;width:250px;background:#f0f0f0">
		<div class="panel-body" style="padding-top:50px;">
       		<ul id="orgZtree" class="ztree" style="overflow:auto;border:0px;margin-top:0px;width:100%;height:100%;min-height:330px; padding:2px;"></ul>
   	    </div>
	</div>
	<div style="position:absolute;left:252px;top:0px;bottom:0px;right:0px">
		<iframe id="frame0" frameborder=0 style="width:100%;height:100%"></iframe>
	</div>
</body>
</html>



<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>
<%

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="cache-control" content="no-cache, must-revalidate"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>ache, must-revalidate">
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/ztree.jsp"%>
<title>获取按模块、管理范围权限树</title>
<%-- <link rel="stylesheet" href="<%=contextPath%>/common/jquery/ztree/css/demo.css" type="text/css"> --%>
<style>
</style>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/easyuiTools.js"></script>

<script type="text/javascript">

function doOnload(){
 	/* 获取部门树 */

 	var moduleId = "3";
 	var deptIdZTree= "deptIdZTree";
 	var param = {onClickFunc:onClickTest,defaultOpen:false};//onClickFunc:  点击事件    defaultOpen:默认展开  true-展开   false-不展开*/
	getPostOrgTreeTool.getPostOrgTreeByModule(moduleId ,deptIdZTree , param);

   // $("[title]").tooltips();//title样式
}

function onClickTest(event, treeId, treeNode) {
	
	if( treeNode.id && treeNode.id.split(";").length <2){//如果没改变别是人员
		alert(treeNode.id +":" + treeNode.name)
	}
	
}
</script>
<body class="" topmargin="5" onload="doOnload();">
	<form name="form1" method="post" style="margin-bottom: 5px;">
		
		<ul id="deptIdZTree" class="ztree"style="margin-top: 0; width: 200px;"></ul>
	
				
	</form>
	
</body>
</html>


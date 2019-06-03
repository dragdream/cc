<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
  TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
  int isAdmin = 0;//是否是超级管理员
  if(TeePersonService.checkIsAdminPriv(loginUser)){
	  isAdmin = 1;
  }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
	<%@ include file="/header/header2.0.jsp" %>
	<%@ include file="/header/ztree.jsp" %>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<!-- jQuery 布局器 -->
	<link rel="stylesheet" type="text/css" href="/xzfy/css/organ/organ.css" />
</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px;">
<div id="layout" >

	<iframe id="frame" frameborder=0 style="width:100%;height:880px;"></iframe>
	
	<div layout="center" style="padding-left:2px;position:absolute;left:281px;top:0px;bottom:0px;right:0px;">
		<iframe id="frame0" frameborder=0 style="width:100%;height:100%"></iframe>
	</div>
</div>
<script type="text/javascript">
function doInit(){
	
	//创建组织机构树
	$("#frame").attr("src", '/xzfy/jsp/organ/tree.jsp?type=2');
	//获取组织机构列表
	$("#frame0").attr("src", '/xzfy/jsp/organperson/personlist.jsp');
}
</script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.tianee.oa.core.partthree.util.TeePartThreeUtil" %>
<%
   TeePerson  loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
   boolean hasPriv=TeePartThreeUtil.checkHasPriv(loginUser, "SYS_MENU_GROUP_PRIV");
   if(!hasPriv){//没有权限
	   response.sendRedirect("/system/core/system/partthree/error.jsp");
   }
   
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
	<title>模块权限管理</title>
	

	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/js/tabs/demo.css"/>
	<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css"/>
	
	<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/tabs/tabs.js"></script>

	<script type="text/javascript" charset="UTF-8">
	function doInit(){
		$.addTab("tabs","tabs-content",{title:"模块权限管理",url:contextPath+"/system/core/menuGroup/manageGroup.jsp",active:true});
		$.addTab("tabs","tabs-content",{title:"添加/删除菜单权限",url:contextPath+"/system/core/menuGroup/setMuiltGroupPriv.jsp",active:false});
		$.addTab("tabs","tabs-content",{title:"添加/删除模块权限",url:contextPath+"/system/core/menuGroup/setMuiltPersonGroupPriv.jsp",active:false});
	}

	
	</script>
</head>
<body style="margin-top:5px;overflow:hidden" onload="doInit();">
<div id="tabs" class="base_layout_top"></div>
<div id="tabs-content" class="base_layout_center"></div>

</body>
</html>
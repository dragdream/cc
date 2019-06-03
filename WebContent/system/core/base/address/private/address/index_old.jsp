<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<!-- jQuery 布局器 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/common/jqueryui/jquery.layout-latest.js"></script>

<title>通讯录</title>
<script>
	function doInit(){
		$("body").layout();
		
	}
	</script>
</head>
<body onload="doInit()">
  
    <div class="ui-layout-west">
		<iframe id="messagemanage"   name="messagemanage" style="width:100%;height:100%"  src="<%=contextPath%>/system/core/base/address/private/address/left.jsp" frameborder="NO" scrolling="auto" noresize ></iframe>
	</div>
	<div class="ui-layout-center">
		<iframe id="rightList"  name="rightList" style="width:100%;height:100%" src="<%=contextPath%>/system/core/base/address/private/group/index.jsp" scrolling="auto" frameborder="NO"></iframe>
	</div>
</body>
</html>


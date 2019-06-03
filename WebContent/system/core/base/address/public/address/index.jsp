<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header2.0.jsp" %>

<title>消息管理</title>
<style>
	html{
		overflow:hidden;
	}
	.ui-layout-west{
	    
		position:absolute;
		width:220px;
		top:0;
		left:0px;
		bottom:0px;
	}
	.ui-layout-center{
		position:absolute;
		left:203px;
		top:0px;
		right:0px;
		bottom:0px;
		background-color:#fff;
	}
	
</style>
</head>
<body   style="overflow:auto;width: 100%;height:100%;box-sizing:border-box;">
  
    <div class="ui-layout-west">
		<iframe id="messagemanage"   name="messagemanage" style="width:100%;height:100%"  src="<%=contextPath%>/system/core/base/address/public/address/left.jsp" frameborder="NO" scrolling="auto" noresize ></iframe>
	</div>
	<div class="ui-layout-center">
		<iframe id="rightList"  name="rightList" style="width:100%;height:100%" src="<%=contextPath%>/system/core/base/address/public/group/index1.jsp" scrolling="auto" frameborder="NO"></iframe>
	</div>
</body>
</html>


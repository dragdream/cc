<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/ztree.jsp" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/common/font-awesome/4.7.0/css/font-awesome.min.css" />
	<link rel="stylesheet" type="text/css" href="/xzfy/css/organ/deptTree.css" />

</head>
<body onload="doInit()" style="overflow:hidden">
	<div id="toolbar" class="topbar clearfix" style="position:absolute;top:0px;left:0px;right:0px;height:20px">
   		<div class="fl">
   		&nbsp;&nbsp;<button type="button" class="btn-win-white"  onclick="doSubmit();">保存</button>&nbsp;&nbsp;<button type="button" class="btn-win-white" onclick="location='manageGroup.jsp'">返回</button>
   		&nbsp;&nbsp;&nbsp;&nbsp;
  		</div>
	</div>
	<div id="mainDiv" style="position:absolute;top:60px;left:0px;right:0px;bottom:0px;overflow:auto">
		<div class='zTreeBackground'>
			<ul id='tree' class='ztree'></ul>
		</div>
	</div>
	<script type="text/javascript" src="/xzfy/js/organ/deptTree.js" ></script>
</body>
</html>
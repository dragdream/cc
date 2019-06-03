<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>考核自评</title>
<%@ include file="/header/header.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/tabs/tabs.js"></script>
<script>

function doInit(){

 	$.addTab("tabs","tabs-content",[{title:"未自评",url:contextPath+"/system/core/base/examine/self/noSelf.jsp",active:true},
 	                               {title:"已自评",url:contextPath+"/system/core/base/examine/self/self.jsp",active:false},
	                                {title:"全部",url:contextPath+"/system/core/base/examine/self/allself.jsp",active:false}]);
}

</script>

</head>
<body onload="doInit()"  style="margin-top:5px;overflow:hidden">
<div id="tabs" class="base_layout_top"></div>
<div id="tabs-content" class="base_layout_center"></div>

</body>
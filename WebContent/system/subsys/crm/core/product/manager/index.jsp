<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品管理</title>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/tabs/tabs.js"></script>

<script>

function doInit(){
	$("#group").group();
	changePage(1);
}


function changePage(index){
	switch(index){
	case 1:
		$("#frame0").attr("src",contextPath+"/system/subsys/crm/core/product/manager/productManage.jsp");
		break;
	case 2:
		$("#frame0").attr("src",contextPath+"/system/subsys/crm/core/product/manager/queryProduct.jsp");
		break;
	}
}


</script>

</head>
<body onload="doInit()"  style="margin-top:5px;overflow:hidden">
<div class="base_layout_left" style="text-align:center;width:150px;" >
	<i class="glyphicon glyphicon-inbox nav_icon"></i>
	<br/><br/><br/>
	<ul class="my_nav" id="group">
		<li class='active' onclick="changePage(1)">产品管理</li>
		<li onclick="changePage(2)">综合查询</li>
	</ul>
</div>
<div class="base_layout_right" style="left:151px;overflow:hidden">
	<iframe id="frame0" frameborder=0 style="width:100%;height:100%"></iframe>
</div>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	
<script>

function doInit(){
	$("#group").group();
	changePage(1);
}

function changePage(index){
	switch(index){
	case 1:
		$("#frame0").attr("src",contextPath+"/system/subsys/contract/category/list.jsp");
		break;
	case 2:
		$("#frame0").attr("src",contextPath+"/system/subsys/contract/sort/list.jsp");
		break;
	}
}

</script>

</head>
<body onload="doInit()" style="overflow:hidden">
<div class="base_layout_left" style="text-align:center;width:150px;" >
	<i class="glyphicon glyphicon-cog nav_icon"></i>
	<br/><br/><br/>
	<ul class="my_nav" id="group">
		<li class='active' onclick="changePage(1)">分类设置</li>
		<li onclick="changePage(2)">类型设置</li>
	</ul>
</div>
<div class="base_layout_right" style="left:151px;overflow:hidden">
	<iframe id="frame0" frameborder=0 style="width:100%;height:100%"></iframe>
</div>

</body>
</html>
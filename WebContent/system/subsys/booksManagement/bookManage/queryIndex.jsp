<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
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
		$("#frame0").attr("src",contextPath+"/bookManage/bookSearch.action");
		break;
	case 2:
		$("#frame0").attr("src",contextPath+"/system/subsys/booksManagement/bookManage/query.jsp?status=0");
		break;
	case 3:
		$("#frame0").attr("src",contextPath+"/system/subsys/booksManagement/bookManage/query.jsp?status=1");
		break;
	case 4:
		$("#frame0").attr("src",contextPath+"/system/subsys/booksManagement/bookManage/query.jsp?status=2");
		break;
	}
}


</script>

</head>
<body onload="doInit()" style="margin:0px;overflow:hidden;">
<div class="base_layout_left" style="text-align:center;width:150px;" >
	<i class="glyphicon glyphicon-book nav_icon"></i>
	<br/><br/><br/>
	<ul class="my_nav" id="group">
		<li class='active' onclick="changePage(1)">图书查询</li>
		<li onclick="changePage(2)">待批借阅</li>
		<li onclick="changePage(3)">已批借阅</li>
		<li onclick="changePage(4)">未准借阅</li>
	</ul>
</div>
<div class="base_layout_right" style="left:151px;overflow:hidden">
	<iframe id="frame0" frameborder=0 style="width:100%;height:100%"></iframe>
</div>
</body>
</html>
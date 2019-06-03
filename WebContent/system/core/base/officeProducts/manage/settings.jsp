<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>

function doInit(){
	$("#group").group();
	changePage(1);
}

function changePage(sel){
	if(sel==1){
		$("#frame0").attr("src",contextPath+"/system/core/base/officeProducts/manage/settings_depository.jsp");
	}else if(sel==2){
		$("#frame0").attr("src",contextPath+"/system/core/base/officeProducts/manage/settings_category.jsp");
	}
}

</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px">
<div class="base_layout_left" style="text-align:center;width: 0px;
    display: none;" >
	<i class="glyphicon glyphicon-phone-alt nav_icon"></i>
	<br/><br/>
	<ul class="my_nav" id="group">
		<li class='active' onclick="changePage(1)">&nbsp;&nbsp;用品库设置</li>
		<li onclick="changePage(2)">&nbsp;&nbsp;用品类别设置</li>
	</ul>
</div>
<div class="base_layout_right" style="left:0px;overflow:hidden;">
	<iframe id="frame0" frameborder=0 style="width:100%;height:100%"></iframe>
</div>
</body>
</html>
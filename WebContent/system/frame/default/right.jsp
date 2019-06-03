<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="overflow:auto;">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/smsHeader.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<title>组织机构</title>
<link href="css/index.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="<%=cssPath%>/menu.css">

<script type="text/javascript" src="<%=contextPath%>/system/frame/default/js/index.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jquery/portlet/jquery-ui-1.9.2.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jquery/portlet/jquery.portlet.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/TeeMenu.js"></script> 
<script type="text/javascript" src="<%=contextPath%>/system/core/org/orgUser/orgUser.js"></script>	

<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>


<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/fullcalendar/fullcalendar/fullcalendar.css">

<script type="text/javascript">
function doInit(){
	$("#layout").layout({auto:true});
	//设置高度
	 /* var clientHeight = parent.$(".c_right fl").clientHeight;
	 $("#orgUserZtree").css("height",clientHeight-47); */
	getORGInfo();
	
	
}

</script>
</head>

<body style="background:url(images/content_bg.png) left top;" onload="doInit();"  style="overflow:auto;">

<div id="layout">

   	<div layout="north" height="40" class="c_right_tab clearfix"><span class="c_right_tab_span" item='1'>在线人员</span><span>全部人员</span></div>
   <div layout="center">
	<ul id="orgUserZtree"  class="ztree" style="border:0px;width:95%;;overflow-y:auto;"></ul></a>
	
	</div>
</div>





<script>


 $(".c_right_tab span").click(function () {
	$(".c_right_tab span").removeClass("c_right_tab_span");
	$(this).addClass("c_right_tab_span");
	var item = $(this).attr("item");
	if(item == '1'){
		onlineUser();
	}else{
		allUser();
	}
	//var index = $(this).index();
	
	/* $('.c_right_list').hide();
	
	$('.c_right_list').eq(index).show(); */
}) ;
</script>
</body>
</html>

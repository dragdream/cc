<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header2.0.jsp" %>
<!-- jQuery 布局器 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/common/jqueryui/jquery.layout-latest.js"></script>

<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script>

function doInit(){
	changePage(1);
}

function changePage(sel){
	if(sel==1){
		$("#frame0").attr("src",contextPath+"/system/core/sms/receiveSmsList.jsp");//外出
	}else if(sel==2){
		$("#frame0").attr("src",contextPath+"/system/core/sms/sendSmsList.jsp");//请假
	}
}

</script>

</head>

<body onload="doInit()" style="padding-left10px;padding-right10px; overflow:hidden;">
<div id="toolbar" class = "toolbar clearfix">
   <div class="right fl setHeight">
	<ul style="margin-left:0px;" class="my_nav" >
		<li style="cursor:pointer;margin-right:15px;" class='active fl ' onclick="changePage(1)">&nbsp;&nbsp;接收的消息</li>
		<li style="cursor:pointer;" class="fl " onclick="changePage(2)">&nbsp;&nbsp;发送的消息</li>
	</ul>
   </div> 
	<span style="border-bottom: 2px solid #b0deff;margin-top:0;" class="basic_border fl"></span>
</div>
<div class="" style="left:151px;overflow:hidden;position:absolute;top:30px;left:0;right:0;bottom:0;">
	<iframe id="frame0" frameborder=0 style="width:100%;height:100%"></iframe>
</div>
</body>
<script>
	$(".my_nav li").on("click",function(){
		$(this).addClass("active").siblings().removeClass("active");
	});
</script>
</html>


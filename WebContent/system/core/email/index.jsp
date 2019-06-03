<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<title>邮件箱</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<!-- jQuery 布局器 -->
<script type="text/javascript" src="<%=contextPath%>/system/core/email/js/email.js"></script>
<script>
function doInit(){
	refreshEmailMain();
	$("#group").group();
	$("#xs").group();
	$("#mailBoxList").group();
}
/***
 * 刷新页面
 */
function refreshEmailMain(){
	getEmailCount();
	setClick();
}
/**
 * 获取各种状态总数
 */
function getEmailCount(){
	var url =  "<%=contextPath %>/emailController/getEmailCount.action";
	var jsonObj = tools.requestJsonRs(url);
	if(jsonObj.rtState){
		var prc = jsonObj.rtData;
		$("#receiveCount").html(prc.receiveCount);
		$("#saveCount").html(prc.saveCount);
		$("#sendCount").html(prc.sendCount);
		$("#deleteCount").html(prc.deleteCount);

		var ulStr = "";
		var mailBoxList = eval(prc.mailBoxList);
		jQuery.each(mailBoxList,function(i,prcs){
			//alert(prcs.boxName);
			var boxName = prcs.boxName;
			var mailCount = prcs.mailCount;
			var mailBoxSId = prcs.sid;
			ulStr  += "<li  href='javascript:void(0);' onclick='changeMailBoxFunc(" + mailBoxSId + ")'>" + boxName + "<span class='badge'>"+mailCount+"</span></li>";
		});
		ulStr += "";
		$("#mailBoxList").empty();
		$("#mailBoxList").append(ulStr);

	}else{
		alert(jsonObj.rtMsg);
	}
}

function changePage(id){
	if(id==1){//收件箱
		document.getElementById("frame0").src = "<%=contextPath%>/system/core/email/emailReceiveManage.jsp";
	}else if(id==2){//星标邮件

	}else if(id==3){//草稿箱
		document.getElementById("frame0").src = "<%=contextPath%>/system/core/email/emailDraftBoxManage.jsp";
	}else if(id==4){//已发件箱
		document.getElementById("frame0").src = "<%=contextPath%>/system/core/email/sendEmailManage.jsp";
	}else if(id==5){//已删除邮件箱
		document.getElementById("frame0").src = "<%=contextPath%>/system/core/email/deleteEmailManage.jsp";
	}else if(id==6){//查询邮件
		document.getElementById("frame0").src = "<%=contextPath%>/system/core/email/search.jsp";
	}
	$("#xs li").removeClass("active");
	$("#mailBoxList li").removeClass("active");
}

/**
 * 自定义邮箱
 */
function changeMailBoxFunc(sid){
	document.getElementById("frame0").src = "<%=contextPath%>/system/core/email/emailBoxListManage.jsp?mailBoxSid=" + sid;
	$("#group li").removeClass("active");
	$("#xs li").removeClass("active");
}

function returnPageFunc(type){
	//alert(type);
	refreshEmailMain();
	changePage(type);

}

function setClick(){
/* 	$(".list-group .list-group-item").click(function (){
		$(".list-group .list-group-item").removeClass("active");
		$(this).addClass("active");
	}); */
	/* $(".list-group .list-group-item").on("click",function(){
		$(".list-group .list-group-item").removeClass("active");
		$(this).addClass("active");
	}) */
	$(".base_layout_left").on("click",".my_nav li",function(){
		$(".my_nav li").removeClass("active");
		$(this).addClass("active");
	});

}

function switch1(flag){
	if(flag==1){
		document.getElementById("frame0").src = "send.jsp";
	}else{
		document.getElementById("frame0").src = "emailReceiveManage.jsp";
	}
	$("#group li").removeClass("active");
	$("#mailBoxList li").removeClass("active");
}
</script>


<style>
.list-group-item{
	/* background-color: #f5f5f5; */
	font-size:14px;
	padding:8px 15px;
}
.base_layout_left{
	background-color: #eaedf1;
}
.list-group{
	margin:6px;
}
.my_nav li{
	line-height: 40px;
	text-align: left;
	text-indent: 25px;
	background-color: #eaedf1;
	cursor:pointer;
	margin-right:2px;
}
hr{
	border: none;
	height: 1px;
	color: #fff;
	background-color: #fff;
}
.badge{
	float: right;
	margin-right: 10px;
	margin-top: 10px;
	width: 30px;
	height: 20px;
	line-height: 20px;
	text-indent: 0!important;
	color: #fff;
	text-align: center;
	background-color:#27a9f3;
	border-radius:10px;
	-webkit-border-radius:10px;
	-moz-border-radius:10px;
	position:absolute;
	left:105px;
}
#xs img{
	vertical-align: middle;
}
.forScoll{
	overflow: auto;
}
li.active{
	background:#fff;
}
li.active .badge{
background:green;
}
</style>
</head>
<body onload="doInit()" style="overflow:hidden;">
<div class="base_layout_left fl" style="position:absolute;top:0;left:0;bottom:1px;text-align:center;width:150px;font-size: 16px;" >
	<div class="left_title" style="width: 100%;height:60px;background: #d9dee4;line-height:60px">
		<img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_shezhizhongxin.png" style="display:inline-block;float:left;margin-left:10px;vertical-align: middle;margin-top: 10px;"/>
		<span class='fl' style ="margin-left:8px;" class='titleName'>个人邮件</span>
	</div>
	<div class="forScoll">
	<ul class="my_nav" id="xs">
	 <li onclick="switch1(1)">写邮件</li>
	 <li onclick="switch1(2)">收邮件</li>
	</ul>
	<hr style="margin:5px;"/>
	<ul class="my_nav" id="group">
		<li  href="javascript:void(0);" class="active"  onclick="changePage(1)" >收件箱<span id="receiveCount" class="badge"></span></li>
		<li   style="display:none;" href="javascript:void(0);"    onclick="changePage(2)">星标邮件(0)</li>
		<li  href="javascript:void(0);"   onclick="changePage(3)">草稿箱<span id="saveCount" class="badge"></span></li>
		<li  href="javascript:void(0);"    onclick="changePage(4)">已发送<span id="sendCount" class="badge"></span></li>
		<li   href="javascript:void(0);"   onclick="changePage(5)">已删除<span id="deleteCount" class="badge"></span></li>
		<li   href="javascript:void(0);"   onclick="changePage(6)">查询邮件</li>
		<li  style="display:none;"   href="javascript:void(0);" onclick="changePage(6)">垃圾箱(0)</li>
	</ul>
	<hr style="margin:5px;"/>
	<ul class="my_nav" id="mailBoxList" style="position:absolute;bottom:36px;left:0px;width:168px;top:362px;overflow-y:auto">
	</ul>


		<div  align="center" style="position: absolute;bottom: 1px;width:100%;">
			<!-- <div class="btn-group dropup email_opt_float">
				  <button type="button" class="btn btn-default">设置外部发信箱</button>
				  <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
				    <span class="caret"></span>
				    <span class="sr-only">Toggle Dropdown</span>
				  </button>
			</div> -->

			  <div class="btn-group" style="width:100%;">
				  <button type="button" class="btn-menu" data-toggle="dropdown" style="width:100%;border:none;line-height: 35px;text-align: center;border-top: 1px solid #f2f2f2;background-color: #d9dee4;color: #000;outline: none;font-family: 'Microsoft Yahei';cursor: pointer;">
				    邮箱设置  &nbsp;&nbsp;<span style = 'margin-top:16px;' class="caret-up"></span>
				  </button>
				  <ul class="btn-content-up" >
				    <li onclick="setWebMail();"><a href="javascript:void(0);" >外部邮箱设置</a></li>
				    <li onclick="setEmail();"><a href="javascript:void(0);" >管理邮件箱</a></li>

				  </ul>
			 </div>
		</div>
	</div>
</div>
<div class="base_layout_right fl" style="position:absolute;top:0;left:150px;bottom:0;right:0;overflow:hidden;">
	<iframe id="frame0" name="frame0" frameborder=0 style="width:100%;height:100%;padding:0;box-sizing:border-box;" src="emailReceiveManage.jsp"></iframe>
</div>
</body>
<script type="text/javascript">
	// console.log(window.parent);
	// $(window.parent).find("iframe").css("padding","0")
</script>
</html>
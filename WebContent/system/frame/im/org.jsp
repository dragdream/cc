<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>

<style type="text/css">
.allUser  {
  width:50%;
  float:right;
  text-align: center;
  background-color: #e2e3e7;
  height:30px;
  font-size:12px;
  line-height: 30px;
  text-align: center;
  color:#3b424d;
 
}
.onlineUser {
   width:50%;
  float:left;
   height:30px;
  line-height: 30px;
  text-align: center;
  background-color: #e2e3e7;
  font-size:12px;
  color:#3b424d;
}

.selectUsertype{
  background-color: #d1d1d1;
  width:50%;
  font-size:12px;
}
</style>
<script src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" charset="UTF-8">

function doInit(){
// 	$("#layout").layout({auto:true});
	sel(2);
	setUserType();
}

function sel(opt){
	opt = parseInt(opt);
	switch(opt)
	{
	case 1:$("#frame").attr("src",contextPath+"/system/frame/im/onlineUser.jsp?userOptType=" + opt);break;
	case 2:$("#frame").attr("src",contextPath+"/system/frame/im/onlineUser.jsp?userOptType=" + opt);break;
	}
}

/**
 * 设置在线、全部人员信息
 */
function setUserType(){
	$("#south").find("a").click(function(){
		$("#south").find("a").removeClass("selectUsertype");
		$(this).addClass("selectUsertype");
		var  optType = $(this).attr("item");
		sel(optType);
	});
}
</script>
</head>
<body onload="doInit()" style="overflow:hidden">
<div id="south" style="position:absolute;left:0px;right:0px;top:0px;height:30px">
	<a href='javascript:void(0);' class="onlineUser selectUsertype" item=2>显示在线</a>
	<a href='javascript:void(0);' class="allUser "  item=1>显示全部</a>
</div>
<div style="position:absolute;left:0px;right:0px;top:31px;bottom:0px">
<iframe id="frame" style="height:100%;width:100%;border:0px;" frameborder="0"></iframe>
</div>
</body>
</html>
		        
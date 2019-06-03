<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%

	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);//获取Id
	int isLooked = TeeStringUtil.getInteger(request.getParameter("isLooked"), 0);//读取状态 0-未读 1-已读
%>
<!DOCTYPE HTML>
<html>
<head>
<title>详情</title>
<%@ include file="/system/mobile/header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<link href="<%=mobilePath%>/phone/style/style.css?v=1" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=mobilePath %>/js/iscroll.js"></script>
<script type="text/javascript" src="<%=mobilePath %>/phone/js/index.js"></script>

<style type="text/css" media="all">
</style>

<script type="text/javascript">

/**
 * 页面加载是即可
 */
var sid = <%=sid%>;
var isLooked= <%=isLooked%>;

//手机屏幕宽度
var width;
function doInit(){
	var param = {sid:sid,isLooked:isLooked};
	//获取所有已发布的新闻
	var url = "<%=contextPath%>/mobileNewsContoller/getNewsById.action";
	$.ajax({
	  type: 'POST',
	  url: url,
	  data: param,
	  timeout: 6000,
	  success: function(data){
		 //获取手机屏幕的宽度
		  width=parseInt($(window).width());
		  
		  var dataJson = eval('(' + data + ')');
      	  var rtData = dataJson.rtData;
      	  var rtState = dataJson.rtState;
		  if(rtState){

			  if(rtData.format=="2"){
				  window.location=rtData.url;
			  }
			  
			  $("#content").append(rtData.content);
			  //改变图片的宽度  使图片自适应手机的宽度
			  $("#content img").each(function(i,obj){
// 				  obj.onload=function(){		
// 					  if(this.width > width){
// 						  this.width = width-40; 	  
// 					  }
// 				  }
				$(obj).css({maxWidth:"100%"});
			  });
			  
		  }else{
			  
		  } 
	  },
	  error: function(xhr, type){
	    alert('服务器请求超时!');
	  }
	});
	
}
</script>


</head>
<body onload="doInit();">
<div id="content" style="background-color:#fff;">
</div>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%

	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);//获取Id
	int isLooked = TeeStringUtil.getInteger(request.getParameter("isLooked"), 0);//读取状态 0-未读 1-已读
%>
<!DOCTYPE HTML>
<html>
<head>
<title>详情</title>
<script>
var DING_APPID = "<%=TeeSysProps.getString("DING_NOTIFY_APPID")%>";
</script>
<%@ include file="/system/mobile/header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<link href="<%=mobilePath%>/phone/style/style.css?v=1" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=mobilePath %>/js/iscroll.js"></script>
<script type="text/javascript" src="<%=mobilePath %>/phone/js/index.js"></script>
<script type="text/javascript" src="<%=mobilePath %>/js/util.js"></script>

<style type="text/css" media="all">
<%

    //水印
    int waterMark=TeeSysProps.getInt("WATER_MARK");
	if(waterMark==1){
		%>
		body{
			background-image:url('/systemAction/generateWaterMark.action');
		}
		<%
	}
%>

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
	document.getElementById('info').style.left = '0';
	//loadData(0);//加载数据
	var param = {sid:sid,isLooked:isLooked};
	//获取所有已发布的新闻
	var url = "<%=contextPath%>/mobileNotifyController/getNotifyById.action";
	$.ajax({
	  type: 'POST',
	  url: url,
	  data: param,
	  timeout: 6000,
	  success: function(data){
		//获取手机屏幕的宽度
		  width=parseInt(window.innerWidth);
		  
		 // alert(data)
		  var dataJson = eval('(' + data + ')');
      	  var rtData = dataJson.rtData;
      	  var rtMsg = dataJson.rtMsg;
      	  var rtState = dataJson.rtState;
		  if(rtState){
		 	  $("#subject").append(rtData.subject);
			  $("#creater").append(rtData.fromPersonName);
			  var sendTime = getFormatDateStr(rtData.sendTime , 'yyyy-MM-dd HH:mm:ss');//发布时间
				
			  $("#publish_time").append(sendTime);

			  $("#content").append(rtData.content);
			  var  attachments = rtData.attachmentsMode;//附件
			  if(attachments.length > 0){
				  $("#attathList").show();
				  $.each(attachments, function(index, item){  
					  $("#attathList").append("<div ><a href='javascript:void(0);'  onclick=\"GetFile('"+item.sid+"','"+item.fileName+"','"+item.attachmentName+"')\" >"+item.fileName + "&nbsp;&nbsp;("+item.sizeDesc+")"+"</a><div>");
				  });
			  }
			  //clickCount
			  //改变图片的宽度  使图片自适应手机的宽度
			  $("#content img").each(function(i,obj){
				  obj.onload=function(){		
					  if(this.width > width){
						  this.width = width-40; 	  
					  }
					  
				  }
			  });
			  
		  }else{
			  
		  } 
		  loadData(1);//加载数据
	  },
	  error: function(xhr, type){
	    alert('服务器请求超时!');
	  }
	});
	
}
</script>


</head>
<body onload="doInit();">

<!-- <div class="header">
	<button type="button" class="button" value="返回"  onclick="goNotifyHome();" style="position:absolute;left:15px;margin-top:4px;">返回</button>
	<a href="javascript:void();">查看公告</a>
</div> -->
<div id="info" style="background:none">
	<div class="read_title_info" id="subject" style="padding-top:20px;"></div>
	
	<div class="content_info" id="creater">创建人：</div>
	
	<div class="content_info" id="publish_time">发布时间：</div>
	
	
	<div class="content_info"  style="border:0px;padding-top:10px;position: relative;" id="content"></div>
	
	
	<div class="content_attath_list"  style="margin-top:10px;" id="attathList"></div>
</div>
<!-- <div class="footer"></div> -->

</body>
</html>
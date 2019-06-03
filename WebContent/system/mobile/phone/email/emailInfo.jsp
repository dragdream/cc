<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page import="com.tianee.oa.oaconst.TeeModuleConst"%>
<%

	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);//获取Id
	int optType = TeeStringUtil.getInteger(request.getParameter("optType"), 0);//操作类型0-收件箱 1- 发件箱
%>
<!DOCTYPE HTML>
<html>
<head>
<title>邮件详情</title>
<script>
var DING_APPID = "<%=TeeModuleConst.MODULE_SORT_DD_APP_ID.get("019")%>";
</script>
<%@ include file="/system/mobile/mui/header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<link href="<%=contextPath%>/system/mobile/phone/style/style.css?v=1" rel="stylesheet" type="text/css" />
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
var optType = <%=optType%>;

//手机屏幕宽度
var width;

function doInit(){
	if(userAgent.indexOf("DingTalk")!=-1 || userAgent.indexOf("MicroMessenger")!=-1){//如果是钉钉或者微信，则显示操作区
		$("#navBottom").show();
		a1.addEventListener("tap",function(){
			window.location = "index.jsp";
		});
		a2.addEventListener("tap",function(){
			window.location = "addOrUpdate.jsp?optType=0&sid="+sid;
		});
		a3.addEventListener("tap",function(){
			window.location = "addOrUpdate.jsp?optType=3&sid="+sid;
		});
	}
	
	document.getElementById('data_info').style.left = '0';
	//loadData(0);//加载数据
	var param = {sid:sid};
	//获取收件箱
	var url = "<%=contextPath%>/mobileEmailController/getById.action";
	if(optType == 1){//获取发件箱
		url =  "<%=contextPath%>/mobileEmailController/getEmailDetailByMailBodyId.action";
	}
	
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
			  $("#creater").append(rtData.fromUserName);
			  if(optType != 2){
				  $("#creater").show();
			  }
			  $("#subject").append(rtData.subject);
			  if(rtData.toUserName){
			  	  $("#toUserName").show();
			  	  $("#toUserName").append(rtData.toUserName);
			  }
			  if(rtData.copyUserName){
			  	  $("#copyUserName").show();
			  	  $("#copyUserName").append(rtData.copyUserName);
			  }
			  if(rtData.toWebmail){
			  	  $("#toWebmail").show();
			  	  $("#toWebmail").append(rtData.toWebmail);
			  }
			  $("#sendTimeStr").show();
			  $("#sendTimeStr").append(rtData.sendTimeStr);
			  $("#content").append(rtData.content);
				
			  var  attachments = rtData.attachMentModel;//附件
			  if(attachments.length > 0){
				  $("#attathList").show();
				  $.each(attachments, function(index, item){  
					  //$("#attathList").append("<div ><a href='<%=contextPath%>/attachmentController/downFile.action?id="+ item.sid + "' target='black'  item_id="+item.sid+">"+item.fileName + "&nbsp;&nbsp;("+item.sizeDesc+")"+"</a><div>");
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
		  //loadData(1);//加载数据
	  },
	  error: function(xhr, type){
	    alert('服务器请求超时!');
	  }
	});
	
}

function toAddOrUpdateDiary(sid){
	window.location.href = mobilePath + "/phone/email/addOrUpdate.jsp?sid=" + sid;
}

</script>


</head>
<body onload="doInit();" style="margin-bottom:5px;">

<%-- <div class="header">
	<div class="" style="position:absolute;left:15px;;">
		<button type="button" class="button" value="返回"  onclick="goEmailHome();" style="">返回</button>
	</div>
	<div  style="position:absolute;right:10px">
		<button type="button" class="button" value="编辑"  onclick="toAddOrUpdateDiary(<%=sid %>);" style="">编辑</button>
	</div>
    <a href="javascript:void();">邮件详情</a>
	
</div> --%>
<div id="data_info" style="background:none">
	<div class="read_title_info" id="subject" style="padding-top:20px;"></div>
	<br/>
	
	<div class="content_info" id="creater" style="display:none;">发送人：</div>
	
	<div class="content_info" id="toUserName" style="display:none;">收件人：</div>
	
	<div class="content_info" id="copyUserName" style="display:none;">抄送人：</div>
	
	<div class="content_info" id="secretName" style="display:none;">密送人：</div>
	
	<div class="content_info" id="toWebmail" style="display:none;">外部收件人:</div>
	
	<div class="content_info" id="sendTimeStr" style="display:none;">发送时间：</div>
	
	<div class="content_info"  style="border:0px;padding-top:10px;position: relative;" id="content"></div>
	
	<div class="content_attath_list"  style="margin-top:10px;display:none;" id="attathList"></div>
</div>
<!-- <div class="footer"></div> -->
<!-- 底部操作栏 -->
<nav class="mui-bar mui-bar-tab" id="navBottom" style="display:none">
	<a id="a1" class="mui-tab-item mui-active">
		<span class="mui-icon mui-icon-undo"></span>
		<span  class="mui-tab-label" >返回</span>
	</a>
 	<a id="a2" class="mui-tab-item mui-active">
		<span class="mui-icon mui-icon-upload"></span>
		<span  class="mui-tab-label" >回复</span>
	</a>
	<a id="a3" class="mui-tab-item mui-active">
		<span class="mui-icon mui-icon-paperplane"></span>
		<span  class="mui-tab-label" >转发</span>
	</a>
</nav>
</body>
</html>
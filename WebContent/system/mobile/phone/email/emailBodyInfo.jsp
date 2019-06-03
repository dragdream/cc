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
			  $("#content").append(rtData.content);
		  }else{
			  
		  } 
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
<div id="data_info" style="background-color:#fff;">
	<div class="content_info"  style="border:0px;padding-top:10px;position: relative;" id="content"></div>
</div>
</body>
</html>
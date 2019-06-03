<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String meetingId = request.getParameter("meetingId");//会议Id
	String attendFlag = request.getParameter("attendFlag");//状态

%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>缺席确认</title>

<%@ include file="/system/mobile/header.jsp" %>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<link href="<%=mobilePath%>/phone/style/style.css?v=1" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="../../mui/css/app.css" type="text/css">

<link rel="stylesheet" href="../../mui/css/mui.min.css" type="text/css">

<script type="text/javascript" src="<%=mobilePath %>/js/iscroll.js"></script>
<script type="text/javascript" src="<%=mobilePath %>/phone/js/index.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/core/base/meeting/js/meeting.js"></script>
<script src="<%=contextPath %>/common/ckeditor/ckeditor.js"></script>
<style type="text/css" media="all"></style>

<style>
.app-row{
	margin-left:14px;
	padding-top:10px;
	padding-right:10px;
	padding-bottom:10px;
	border-bottom:1px solid #f0f0f0;
}
.app-row br{
	margin:0px;
}
.app-row-content{
	margin-left:14px;
	padding-top:10px;
	padding-right:10px;
	padding-bottom:10px;
	border-bottom:1px solid #f0f0f0;
	color:gray;
}
.textarea{
	height:200px;
}
.shuoming{
	background-color:#5cb85c!important;
	color:#fff!important;
	margin:10px 0!important;
	border-radius:5px!important;
}
.mui-input-group .mui-input-row:after{
	left:10px!important;
	right:10px!important;
	
}
</style>


</style>
<script type="text/javascript">
var sid="<%=meetingId%>";
var attendFlag="<%=attendFlag%>";
//验证缺席原因不能为空
function checkForm(){
	//获取缺席内容
	var reason=$("#remark").val();
	if(reason!=""&&reason!=null){
		return true; 
	}else{
		alert("缺席原因不能为空！");
		return false; 
	}
	
}

//缺席确认
function doSaveOrUpdate(){
	if(checkForm()){
		var reason=$("#remark").val();
	    var url = "<%=contextPath %>/TeeMeetingAttendConfirmController/updateAttendFlag.action";
	    var para ={meetingId:sid,attendFlag:attendFlag,remark:reason};
	    var jsonRs = tools.requestJsonRs(url,para);
	    if(jsonRs.rtState){
	    	var url1="/system/mobile/phone/meeting/meetingDetail.jsp?meetingId="+sid;
	    	window.location.href=url1;
	    }else{
	      alert(jsonRs.rtMsg);
	    }
	}
}

//返回
function goback(){
	var url="/system/mobile/phone/meeting/meetingDetail.jsp?meetingId="+sid;
	window.location.href=url;
}
</script>

</head>
<body   style="overflow:auto; margin-bottom: 10px;" >
<div id="muiContent" class="mui-content">
  
    <div  class="mui-input-group">
		<div class="mui-input-row">
			<label style='font-size:18px;'>缺席说明：</label>
		</div>
		
		<div class="mui-input-row"  style="height:inherit">
			<textarea class='mui-bar-nav~ mui-content textarea' id="remark" name="remark"  placeholder="请填写缺席原因"></textarea>
			<br/><br/>
		</div>
		
		<div align="center">
           <input type="button" class = 'shuoming' value="确认缺席" class="btn btn-success" onclick="doSaveOrUpdate()" >
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
           <input type="button" class = 'shuoming' value="返回" class="btn btn-success" onclick="goback()">
	    </div>
	</div>
</div>
</body>
</html>
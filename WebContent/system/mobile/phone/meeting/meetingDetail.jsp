<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<%
	int sid = TeeStringUtil.getInteger(request.getParameter("meetingId"), 0);//会议申请Id
	int showConfirmFlag = TeeStringUtil.getInteger(request.getParameter("showConfirmFlag"), 0);//是否显示参确认 1-不显示（短消息提醒显示）

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>会议详情</title>

<%@ include file="/system/mobile/header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<link href="<%=mobilePath%>/phone/style/style.css?v=1" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=mobilePath %>/js/iscroll.js"></script>
<script type="text/javascript" src="<%=mobilePath %>/phone/js/index.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/core/base/meeting/js/meeting.js"></script>
<script src="<%=contextPath %>/common/ckeditor/ckeditor.js"></script>
<style type="text/css" media="all">
</style>


<script type="text/javascript">

var sid = <%=sid%>;

function doInit()
{
	//隐藏参会缺席 按钮 
	$("#isShow").hide();
	
	updateReadFlagFunc(sid);
	if(sid > 0){
		getById(sid);
	}

	
}



/**
 * 获取外出 by Id
 */
//手机屏幕宽度
var width;
function getById(id){
	
	document.getElementById('data_info').style.left = '0';
	
	var param = {sid:id};
	//获取所有已发布的新闻
	var url = "<%=contextPath%>/meetManage/getById.action";
	$.ajax({
	  type: 'POST',
	  url: url,
	  data: param,
	  timeout: 6000,
	  success: function(data){
		 
		 //获取手机屏幕的宽度
		  width=parseInt(window.innerWidth);
		 
		 
		  var dataJson = eval('(' + data + ')');
      	  var prc = dataJson.rtData;
      	  var rtMsg = dataJson.rtMsg;
      	  var rtState = dataJson.rtState;
      	  
		  if(rtState){
			
			  if (prc && prc.sid) {
					if(prc.isConfirm==0&&prc.isAttend){
						
						$("#isShow").show();
					}
						
					
					$("#meetName").append(prc.meetName);
					$("#subject").append(prc.subject);
					$("#userName").append(prc.userName);
					$("#time").append("从  "+prc.startTimeStr+" 至  "+prc.endTimeStr);
					$("#meetRoomName").append(prc.meetRoomName);
					$("#managerName").append(prc.managerName);
					$("#recorderName").append(prc.recorderName);
					$("#attendeeOut").append(prc.attendeeOut);
					$("#attendeeName").append(prc.attendeeName);
					$("#equipmentNames").append(prc.equipmentNames);
					$("#meetDesc").append(prc.meetDesc);
					
			  //附件	
			  var  attachments = prc.attacheModels;
			  if(attachments.length > 0){
				  $("#attathList").show();
				  $.each(attachments, function(index, item){  
					  $("#attathList").append("<div ><a href='javascript:void(0);' onclick=\"GetFile('"+item.sid+"','"+item.fileName+"','"+item.attachmentName+"')\">"+item.fileName + "&nbsp;&nbsp;("+item.sizeDesc+")"+"</a><div>");
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
	   }
	  }
	});
}	

function meetingConfirmFunc(status){
	
	setMeetingConfirmFunc('<%=sid%>',status); 
}

/**
 * 参加会议或缺席
 */
function setMeetingConfirmFunc(sid,status){
	if(status == '1'){//参会
		var url = "<%=contextPath %>/TeeMeetingAttendConfirmController/updateAttendFlag.action" ;
	    var para =  {meetingId:sid,attendFlag:status};
	    
	    $.ajax({
	  	  type: 'POST',
	  	  url: url,
	  	  data: para,
	  	  timeout: 6000,
	  	  success: function(data){
	  		var dataJson = eval('(' + data + ')');
	  		var rtMsg=dataJson.rtMsg;
	  		if(rtMsg){
	  			window.location.reload(); 
	  		}
	  		
	  	  }
	    });
	}else{//缺席  跳转到缺席确认页面
		var url = contextPath + "/system/mobile/phone/meeting/absentReason.jsp?meetingId=" + sid + "&attendFlag=" + status;
		window.location.href=url;
	}
    /*   var title = "缺席说明";
      var buttonName = "缺席确认";
      var url = contextPath + "/system/core/base/meeting/personal/setMeetingConfirm.jsp?meetingId=" + sid + "&attendFlag=" + status;
      bsWindow(url ,title,{width:"750",height:"320",buttons:
		[
		 {name:buttonName,classStyle:"btn btn-primary"},
	 	 {name:"关闭",classStyle:"btn btn-primary"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="缺席确认" ){
			cw.doSaveOrUpdate(function(){
				$.jBox.tip("保存成功！", "info", {timeout: 1800});
				window.location.reload();
				BSWINDOW.modal("hide");
			});
		}else if(v=="关闭"){
			return true;
		}
	}}); */
}
	






</script>

</head>

<body onload="doInit();" style="overflow:auto; margin-bottom: 10px;">
  <div id="data_info" style="background-color:#fff;">
	
	
	<div class="content_info" id="meetName">名称：</div>
	
	<div class="content_info" id="subject">主题：</div>
	
	<div class="content_info" id="userName">申请人：</div>
	
	<div class="content_info" id="time">会议时间：</div>
	
	<div class="content_info" id="meetRoomName">会议室：</div>
	
	<div class="content_info" id="managerName">审批管理员：</div>
	
	<div class="content_info" id="recorderName">会议纪要员：</div>
	
	<div class="content_info" id="attendeeOut">出席人员（外部）：</div>
	
	<div class="content_info" id="attendeeName">出席人员（内部）：</div>
	
	<div class="content_info" id="equipmentNames">会议室设备：</div>
	
	<div class="content_info" id="meetDesc">会议描述：</div>
	
	<div class="content_attath_list"  style="margin-top:10px;" id="attathList">附件：</div>
	
	<br>
	<div align="center" id="isShow">
	   <div></div>
       <input type="button" value="参会" class="btn btn-success" onclick="meetingConfirmFunc('1')" >
       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       <input type="button" value="缺席" class="btn btn-success" onclick="meetingConfirmFunc('2')">
	</div>
	
</div>



</body>


</html>

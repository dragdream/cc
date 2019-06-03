
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header2.0.jsp"%>
<title>会议申请管理</title>
<link href='<%=contextPath %>/common/fullcalendar/fullcalendar/fullcalendar.css' rel='stylesheet' />
<link href='<%=contextPath %>/common/fullcalendar/fullcalendar/fullcalendar.print.css' rel='stylesheet' media='print' />
<link  rel='stylesheet' href='<%=contextPath %>/system/core/base/calendar/css/calendar.css'type="text/css" />

<script  type="text/javascript"  src='<%=contextPath %>/common/fullcalendar/lib/jquery-ui.custom.min.js'></script>
<script  type="text/javascript"  src='<%=contextPath %>/common/fullcalendar/fullcalendar/fullcalendar.min.js'></script>


<script type="text/javascript">
function  doOnload(){
	//queryOut();
	
	$.addTab("tab","tab-content",[{title:"会议申请",url:contextPath+"/system/core/base/meeting/personal/manager_add.jsp"},
                              {title:"待审批会议",url:contextPath+"/system/core/base/meeting/personal/manager.jsp?meetStatus=0"},
                              {title:"已审批会议",url:contextPath+"/system/core/base/meeting/personal/manager.jsp?meetStatus=1"},
							  {title:"进行中会议",url:contextPath+"/system/core/base/meeting/personal/manager.jsp?meetStatus=2"},
							  {title:"未批会议",url:contextPath+"/system/core/base/meeting/personal/manager.jsp?meetStatus=3"}
                              ]);
	
}

function setMeetRoom(id){
	$("#meetRoomSid").val(id);
}

/**
 * 设置会议状态页面
 */
function setMeetType(meetStatus){
	if(meetStatus ||meetStatus == 0){
		window.location.href = "<%=contextPath%>/system/core/base/meeting/personal/manager.jsp?meetStatus=" + meetStatus;
	}
	
}
</script>
</head>
<body class="" onload="doOnload();" style="overflow:hidden;padding-top: 5px">


<table id="datagrid" fit="true"></table>
	<div id="toolbar" style="min-width:950px;padding-left: 10px;padding-right: 10px;">
		<div class="titlebar clearfix">
		    <img class = 'tit_img' style="margin-right: 10px; margin-top: 3px" src="<%=contextPath %>/common/zt_webframe/imgs/hygl/hysq/icon_会议.png">
			<p class="title">会议申请管理</p>
			<ul id = 'tab' class = 'tab clearfix'>
			
		     </ul>
			 <span class="basic_border_grey fl"></span>
			<div id='color_type' class="btn-group fr" style="margin-top: -30px;">
				 <span style="" id="color_menu" class="color_menu" style="">
	                <a style="margin-left:10px;margin-top: 0px;float:left;" id="calcolor" class="color" href="javascript:void(0);" index="0"></a>
	                <span style="float:left;">&nbsp;待审批&nbsp;</span>
	                <a  style="margin-top: 0px;float:left;" id="calcolor3" class="color3" href="javascript:void(0);" index="2"></a>
	                <span style="float:left;">&nbsp;已批准&nbsp;</span>
	                <a style="margin-top: 0px;float:left;"  id="calcolor1" class="color1"  href="javascript:void(0);" index="1"></a>
	                <span style="float:left;">&nbsp;进行中&nbsp;</span>
	                <a  style="margin-top: 0px;float:left;" id="calcolor4" class="color4" href="javascript:void(0);" index="3"></a>
	                <span style="float:left;">&nbsp;未批准&nbsp;</span>
	                <a  style="margin-top: 0px;float:left;" id="calcolor6" class="color6" href="javascript:void(0);" index="6"> </a>
           			  <span style="float:left;">&nbsp;已结束&nbsp;</span>
           		</span>
			</div>
			<!--<div class="btn-group pull-right" data-toggle="buttons">
			  <label class="btn btn-default active" onclick="setMeetType();">
			    <input type="radio" name="options" id="option1" >会议申请
			  </label>
			   <label class="btn btn-default"  onclick="setMeetType(0);">
			    <input type="radio" name="options" id="option2">待审批会议
			  </label>
			  <label class="btn btn-default"  onclick="setMeetType(1);">
			    <input type="radio" name="options" id="option2">已审批会议
			  </label>
			   <label class="btn btn-default"  onclick="setMeetType(2);">
			    <input type="radio" name="options" id="option2">进行中会议
			  </label>
			   <label class="btn btn-default"  onclick="setMeetType(3);">
			    <input type="radio" name="options" id="option2">未批会议
			  </label>
			</div>-->
			<div style="clear:both"></div>
		</div>
    <div id="tab-content" style="padding-left: 10px;padding-right: 10px"></div>
	<div id='listDiv'>
		
		<div id="meetRoomSelect"></div>
		<div id='meetfullcalendar'>
   
		</div>	


		<!-- <input type="hidden" name="meetRoomSid" id="meetRoomSid" /> -->
	
	</div>
		
<script>
	$("body").on("click","#tab li",function(){
		if($(this).text()=="会议申请"){
				$("#color_type").show();
}else{
	$("#color_type").hide();
}
})
</script>
</body>

</html>
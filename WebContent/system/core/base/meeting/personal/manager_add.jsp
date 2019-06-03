
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
<script  type="text/javascript"  src='<%=contextPath %>/system/core/base/meeting/js/fullcalendar.js'></script>
<script  type="text/javascript"  src='<%=contextPath %>/system/core/base/meeting/js/meeting.js'></script>

<style>
	/*修改样式*/
		.fc-state-active{
			background-color:#0eb3ff!important;
			border-radius:4px;
		}
		.fc-state-default{
			background-color:#e5f5fc;
			border:1px solid #abd5eb;
			border-radius:4px;
			cursor:pointer;
		}
		.fc-header-left .fc-button-prev{
			float:left;
			text-shadow:none;
			margin-right:10px;
		}
		.fc-header-left .fc-button-next{
			float:right;
			text-shadow:none;
		}
		.fc-header-left .fc-button-today{
			float:left;
			text-shadow:none;
			margin-right:10px;
		}
		.fc-button-month{
			margin-right:10px!important;
		}
		.fc-button-agendaWeek {
			margin-right:10px!important;
		}
		.fc-header-space{
			padding:0!important;
		}
		.fc-header-left{
			width:134px;
		}
</style>
</head>


<script type="text/javascript">
function  doOnload(){
	//queryOut();
	
	
	
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

var $newbutton ;
function doInit(){
	/*var $week=$('<div id=\"weekbtn\"><div class=\"weekcurrent\">第52周</div><span id="icon-down" class=\"icon-down\"></span><div class=\"weekdropdown\"><select class=\"span2\"><option value="2000" >2000年</option><option value="2001" >2001年</option><option value="2002" >2002年</option><option value="2003" >2003年</option><option value="2004" >2004年</option><option value="2005" >2005年</option><option value="2006" >2006年</option><option value="2007" >2007年</option><option value="2008" >2008年</option><option value="2009" >2009年</option><option value="2010" >2010年</option><option value="2011" >2011年</option><option value="2012" >2012年</option><option value="2013" selected>2013年</option><option value="2014" >2014年</option><option value="2015" >2015年</option><option value="2016" >2016年</option><option value="2017" >2017年</option><option value="2018" >2018年</option><option value="2019" >2019年</option><option value="2020" >2020年</option><option value="2021" >2021年</option><option value="2022" >2022年</option><option value="2023" >2023年</option><option value="2024" >2024年</option><option value="2025" >2025年</option><option value="2026" >2026年</option><option value="2027" >2027年</option><option value="2028" >2028年</option><option value="2029" >2029年</option></select><div id=\"dropdownwrapper\"></div><span id=\"icon-chevron-left\" class=\"prebtn icon-chevron-left\"></span><span id=\"icon-chevron-right\" class=\"nextbtn icon-chevron-right\"></span></div></div>');
    $(".fc-button-today").parent().append($week);*/	
    var postMeetRoom = selectPostMeetRoom();
    var liStr = "";
    var meetRoomSid = "";
    var meetRoomName = "选择会议室";
    for(var i = 0;i<postMeetRoom.length ; i++){
    	var meetRoom = postMeetRoom[i];
    	if(i == 0){
    		meetRoomSid = meetRoom.sid;
    		meetRoomName = meetRoom.mrName;
    	}
    	//liStr = liStr + "<li role=\"presentation\"><a role=\"menuitem\" onclick='setMeetRoom("+meetRoom.sid+")' tabIndex=\"-1\" href=\"\">" +meetRoom.mrName + "</a></li>";
    	liStr = liStr + "<option value="+ meetRoom.sid+">" +  meetRoom.mrName + "</option>";
    }
    $newbutton=$("<select  name='meetRoomSid' id='meetRoomSid' class='BigSelect' style='margin-top:1px;margin-right:20px;height:29px;padding-top:3px;padding-bottom:3px;' onchange='onchangeMeetRoom(this.value)'> "
    		+ "<option value=''>-请选择会议室-</option>"
    		+ liStr
    		
    		+"</select>"
    );
	/* var $newbutton=$("<div class='btn-group pull-left' data-toggle='buttons'>"
			  +"<label class=\"btn btn-default\" >"
				+"<ul class='nav navbar-nav'>"
			 	 +"<li class='dropdown'>"
			 		 +"<a id='' class='dropdown-toggle' role='button' href='#' data-toggle='dropdown' style ='padding:0px;'>" + meetRoomName + " <b class='caret'></b></a>"
		 				   + "<ul class='dropdown-menu' role='menu' aria-labelledby='meetRoom'  id='meetLi' style='min-width:100px;'>"
	    				+liStr
		 	  		 +"</ul>"
		 	     +"</li>"
		 	    +"</ul>"
	 		+"</label>"
			+"<div>"
	); */
    //$("#meetLi").append(liStr);
    $("#meetRoomSelect").append($newbutton);
   setMeetRoom(meetRoomSid);
}
/**
 * 移动会议室
 */
function replaceMeetRoom(){
	 $newbutton.insertBefore($(".fc-button-month"));	
}

</script>

<body class="" topmargin="5" onload="doOnload();" style="overflow:hidden;">


	<div id='listDiv'>
		
		<div id="meetRoomSelect"></div>
		<div id='meetfullcalendar'>
   
		</div>	


		<!-- <input type="hidden" name="meetRoomSid" id="meetRoomSid" /> -->
	
	</div>
		
</body>

</html>
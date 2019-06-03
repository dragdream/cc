
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
<title>车辆申请管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href='<%=contextPath %>/common/fullcalendar/fullcalendar/fullcalendar.css' rel='stylesheet' />
<link href='<%=contextPath %>/common/fullcalendar/fullcalendar/fullcalendar.print.css' rel='stylesheet' media='print' />
<link  rel='stylesheet' href='<%=contextPath %>/system/core/base/calendar/css/calendar.css'type="text/css" />

<script  type="text/javascript"  src='<%=contextPath %>/common/fullcalendar/lib/jquery-ui.custom.min.js'></script>
<script  type="text/javascript"  src='<%=contextPath %>/common/fullcalendar/fullcalendar/fullcalendar.min.js'></script>
<script  type="text/javascript"  src='<%=contextPath %>/system/core/base/vehicle/js/fullcalendar.js'></script>
<script  type="text/javascript"  src='<%=contextPath %>/system/core/base/vehicle/js/vehicle.js'></script>

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

}

function setVehicle(id){
	$("#vehicleId").val(id);
}

/**
 * 设置会议状态页面
 */
function setMeetType(status){
	if(status){
	  window.location.href = "<%=contextPath%>/system/core/base/vehicle/personal/vehicleManege.jsp?status=" + status;
	}else{
	  window.location.href = "<%=contextPath%>/system/core/base/vehicle/personal/index.jsp";
	}
	
}

var $newbutton ;
function doInit(){
    var postVehicle = selectPostVehicle();
    var liStr = "";
    var vehicleId = "";
    var vehicleName = "";
    for(var i = 0;i<postVehicle.length ; i++){
    	var vehicle = postVehicle[i];
    	if(i == 0){
    		vehicleId = vehicle.sid;
    		vehicleName = vehicle.vModel;
    	}
    	//liStr = liStr + "<li role=\"presentation\"><a role=\"menuitem\" onclick='setMeetRoom("+meetRoom.sid+")' tabIndex=\"-1\" href=\"\">" +meetRoom.mrName + "</a></li>";
    	liStr = liStr + "<option value="+ vehicle.sid+">" +  vehicle.vModel + "</option>";
    }
    $newbutton=$("<select  name='vehicleId' id='vehicleId' class='BigSelect' style='margin-right:20px;height:29px;padding-top:3px;padding-bottom:3px;' onchange='onchangeMeetRoom(this.value)'> "
    		+ "<option value=''>-请选择车辆-</option>"
    		+ liStr
    		+"</select>"
    );
    $("#vehicleSelect").append($newbutton);
   setVehicle(vehicleId);
}
/**
 * 移动会议室
 */
function replaceVehicle(){
	 $newbutton.insertBefore($(".fc-button-month"));	
}
</script>

<body class="" topmargin="5" onload="doOnload();" style="overflow:hidden;">


	<div id='listDiv'>
		
		   <div id="vehicleSelect"></div>
		   <div id='vehiclefullcalendar'>
		   </div>
	
	 </div>
		
</body>

</html>
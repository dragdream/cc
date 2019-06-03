
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
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


<script type="text/javascript">
function  doOnload(){
	
	$.addTab("tab","tab-content",[{title:"车辆申请",url:contextPath+"/system/core/base/vehicle/personal/manager_add.jsp"},
	                              {title:"待审批车辆",url:contextPath+"/system/core/base/vehicle/personal/vehicleManege.jsp?status=0"},
	                              {title:"已审批车辆",url:contextPath+"/system/core/base/vehicle/personal/vehicleManege.jsp?status=1"},
								  {title:"进行中车辆",url:contextPath+"/system/core/base/vehicle/personal/vehicleManege.jsp?status=2"},
								  {title:"未批车辆",url:contextPath+"/system/core/base/vehicle/personal/vehicleManege.jsp?status=3"},
								  {title:"已结束车辆",url:contextPath+"/system/core/base/vehicle/personal/vehicleManege.jsp?status=4"}
	                              ]);
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
</head>
<body class="" onload="doOnload();" style="overflow:hidden;padding-top: 5px">
<table id="datagrid" fit="true"></table>
   <div id="toolbar" style="min-width:950px;padding-left: 10px;padding-right: 10px;">
     <div class="titlebar clearfix">
		    <img class = 'tit_img' style="margin-right: 10px; margin-top: 3px" src="<%=contextPath %>/common/zt_webframe/imgs/xzbg/clgl/clsq.png">
			<p class="title">车辆申请管理</p>
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
			<div style="clear:both"></div>
		</div>
		
		<div id="tab-content" style="padding-left: 10px;padding-right: 10px"></div>
  </div>
  
  <script>
	$("body").on("click","#tab li",function(){
		if($(this).text()=="车辆申请"){
	    $("#color_type").show();
    }else{
	    $("#color_type").hide();
    }
   });
</script>

</body>

</html>
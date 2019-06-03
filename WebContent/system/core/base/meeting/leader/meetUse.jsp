
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String startTimeStr = TeeUtility.getCurDateTimeStr("yyyy-MM-dd");
	String endTimeStr = startTimeStr;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp"%>
<title>会议使用情况</title>

<link href='<%=contextPath %>/common/fullcalendar/fullcalendar/fullcalendar.css' rel='stylesheet' />
<link href='<%=contextPath %>/common/fullcalendar/fullcalendar/fullcalendar.print.css' rel='stylesheet' media='print' />
<link  rel='stylesheet' href='<%=contextPath %>/system/core/base/calendar/css/calendar.css'type="text/css" />

<script  type="text/javascript"  src='<%=contextPath %>/common/fullcalendar/lib/jquery-ui.custom.min.js'></script>
<script  type="text/javascript"  src='<%=contextPath %>/common/fullcalendar/fullcalendar/fullcalendar.min.js'></script>
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
<script type="text/javascript">
var startTimeStr = "<%=startTimeStr%>";
var endTimeStr = "<%=endTimeStr%>";
var currDate = new Date();
var date = new Date();
function  doOnload(){
	doInit();
	getAllMeet(startTimeStr , endTimeStr);
	
	//Date = getFormatDateStr(startTimeStr , 'yyyy-MM-dd');
}


function doInit(){

    var postMeetRoom = getAllRoom();
    var liStr = "";
	if(postMeetRoom.length > 0){
		  var tableStr = "<table class='TableBlock_page' width='100%'  style='margin-top:20px'><tbody id='listTbody'>";
		  tableStr = tableStr + "<tr class='TableHeader' id='tbl_header'>"
	     	 + "<td width='250px' align='center'  >会议室名称/时间</td>";
		  for(var i = 0;i<24 ; i++){
			  tableStr = tableStr  + "<td width='28px' align='center' id='HOUR_"+i+"'>" + i + "</td>";
		  }
			
		 tableStr = tableStr +"</tr>";
	
		for(var j = 0;j<postMeetRoom.length ; j++){
	    	var meetRoom = postMeetRoom[j];
	    	  tableStr = tableStr + "<tr class=''>"
		     	 + "<td width='250px' align='left'  class='TableData'>" + meetRoom.mrName + "</td>";
	    	 // for(var i = 0;i<24 ; i++){
				  tableStr = tableStr  + "<td width='' align='' colspan='24' name='MEET_ROOM_TD' id='MEET_ROOM_" + meetRoom.sid  +"'></td>";
			  //}
	    	  tableStr = tableStr + "</tr>";
	    }
		 tableStr = tableStr + "<tbody></table>";
		 $("#listDiv").html(tableStr);
	}else{
		messageMsg("暂无会议室", "listDiv" ,'' ,380);
	}
    
}
 
/**
 * 获取所有会议室
 *@param today 是否今天
 */
function getAllMeet(startTimeStr , endTimeStr , today){
	//清空数据
	$("td[name='MEET_ROOM_TD']").empty();
	$("#dateStr").html(startTimeStr);
	if(today && today == '1'){//如果设置今天，则给日期赋值为今天
		var yesterday_milliseconds=currDate.getTime();            
		date.setTime(yesterday_milliseconds);       
	}
	var url =   "<%=contextPath %>/meetManage/getAllMeetByTime.action";
	var para = {startTimeStr:startTimeStr,endTimeStr:endTimeStr };
	var jsonObj = tools.requestJsonRs(url , para);
	if(jsonObj.rtState){
		
		var prcs = jsonObj.rtData;
		
		if(prcs.length> 0){
			var th = $("#tbl_header")[0];
			var week_width = 0;
			    if(!th){
			    	return;
			    }
			 for(var j = 1; j < th.cells.length; j++){
			     week_width += th.cells[j].offsetWidth;
			 } 
			 
			 for(var i = 0 ; i<prcs.length ; i++){
				 var prc = prcs[i];
				 var id = prc.sid;
				 var meetName = prc.meetName;
				 var startTimeStr = prc.startTimeStr;
				 var endTimeStr = prc.endTimeStr;
				 var meetRoomId = prc.meetRoomId;
				 var status = prc.status;
				 if(status == 1){
					 status = "fc-event-color3";
				}else if(status == 2){
					status = "fc-event-color1";
				}else if(status == 3){
					status = "fc-event-color4";
				}else if(status  == 4){
					status = "fc-event-color6";
				}
				
				 var start = parseInt(startTimeStr.substring(11,13) , 10);
				 var end = parseInt(endTimeStr.substring(11,13) , 10);
				 var endMinute = parseInt(endTimeStr.substring(14,16) , 10);
				 if(endMinute >0){
					 end = end + 1;
				 }
					//计算偏移量和长度
				 var left = width = 0;//固定
				 var leftF = widthF = 0; //百分比
				
			     for(var j = 1; j < start; j++){
			       	left += th.cells[j].offsetWidth + 2;
			 
			       //	leftF = leftF + 14.2;
			     }
				// alert(start +":" + end)
			     for(var j = start; j <= end; j++){
			      // width += th.cells[j].clientWidth;
			       width = width + 26 ;
			 
			       //widthF = widthF + 11.6 + (1.8 * (j-startWeekE));
			     }
	
			     var div = "<div id='" + id +  "' title='" + meetName + "' style='margin-left:" + (left)+ "px;width: " +(width)+ "px; min-wdith:80px; min-height: 30px; margin-top:3px;margin-bottom:3px;' class='fc-event fc-event-vert fc-event-draggable fc-event-start fc-event-end fc-event-color " + status + " ui-draggable ui-resizable'>"+
			 		" <div class=\"fc-event-inner\">"+
				 	 "<div class=\"fc-event-time\">"+startTimeStr.substring(11,16) + "<br>" + endTimeStr.substring(11,16) + "</div>"+
	 	  		 		"<div class=\"fc-event-title\"></div>"+
	 	  			 "</div>"+
	 		  "</div>";
 				$("#MEET_ROOM_" + meetRoomId).append(div);
 				$("#" + id).click(function(event){
 					meetingJBoxDetail(id);
				 });
			 }
		}
	}else{
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}

/**
 * 设置前一天/后一天
 * @param type 0-前一天  1-后天
 */
function setPreNext(type){
    var datastrTemp =  date.pattern("yyyy-MM-dd");
	if(type == "0"){
		var yesterday_milliseconds=date.getTime()-1000*60*60*24;            
		date.setTime(yesterday_milliseconds);       
		
	}else if(type == "1"){
		var yesterday_milliseconds=date.getTime() + 1000*60*60*24;            
		date.setTime(yesterday_milliseconds);  
	}
	/* var strYear = date.getFullYear();    
    var strDay = date.getDate();    
	var strMonth = date.getMonth()+1;  
    if(strMonth<10) {    
		strMonth="0"+strMonth;    
	} */    
    var datastr =  date.pattern("yyyy-MM-dd");
    getAllMeet(datastr);
} 

</script>
</head>
<body class="" topmargin="5" onload="doOnload();" style="padding-left: 10px;padding-right: 10px;padding-top: 5px">


<table id="datagrid" fit="true"></table>
	<div id="toolbar" style="min-width:800px;">
		<div class="moduleHeader">
			<!-- <b class="pull-left"><i class="glyphicon glyphicon-user"></i>&nbsp;会议室使用情况</b> -->
			
			<div class="btn-group pull-left" style=''>
				 <span style='margin-right:20px;' class="fc-button fc-button-prev fc-state-default fc-corner-left" unselectable="on" onclick="setPreNext(0)">‹</span>
				 <span style='float:right;margin-left:20px;' class="fc-button fc-button-next fc-state-default fc-corner-right" unselectable="on" onclick="setPreNext(1)">›</span>
				 <span class="fc-header-space"></span><span class="fc-button fc-button-today fc-state-default fc-corner-left fc-corner-right" unselectable="on" onclick="getAllMeet('<%=startTimeStr %>' , '' ,'1')">今天</span>
	
			</div>
			<span id="dateStr" style='display:inline-block;width:47.22%;text-align:center;font-size:16px;' ></span>
			<div class="btn-group pull-right">
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

	<div id='listDiv' style="margin:0 auto;">
		
	

		
		
	</div>
		

</script>
</body>

</html>
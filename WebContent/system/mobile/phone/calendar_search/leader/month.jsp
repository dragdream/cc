
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>
<%
	TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
	Date date = new Date();
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat dateFormatWeek = new SimpleDateFormat("E");
	SimpleDateFormat dateFormatTime = new SimpleDateFormat("HH:mm:ss");
	Calendar c = Calendar.getInstance();
	String currWeek = dateFormatWeek.format(date);//系统当前星期 几
	String currDateStr = dateFormat1.format(date);//系统当前时间
	int currYear = c.get(Calendar.YEAR);//.parseInt(dateStr.substring(0, 4));//系统当前年份
	int currMonth = c.get(Calendar.MONTH) + 1;//Integer.parseInt(dateStr.substring(5, 7)); //系统当前月份
	int currDay = c.get(Calendar.DAY_OF_MONTH);//Integer.parseInt(dateStr.substring(8, 10));//系统当前日
	int currWeekth = c.get(Calendar.WEEK_OF_YEAR);//系统当前周
	int currMaxDayOfMonth=c.getActualMaximum(Calendar.DAY_OF_MONTH);//本月份的最大天数 
	
	//从其它页面传过来
	String status = "0";//状态 0-全部 1-进行中 2-已完成 3-已超时
	String dateStr = request.getParameter("dateStr");
	String statusStr = request.getParameter("statusStr");
	String deptId = request.getParameter("deptId") == null ? "" :  request.getParameter("deptId");//传过来的部门Id
	String fromView = request.getParameter("fromView") == null ? "week" :  request.getParameter("fromView");//传过来的部门Id
	if (deptId == null && !fromView.equals("month")) {//不是月视图
		deptId = person.getDept().getUuid() + "";
	}
	int year = currYear;
	int month = currMonth;
	int day = currDay;
	int weekth = currWeekth;
	int maxDayOfDay = currMaxDayOfMonth;
	if (dateStr != null ) {
		date = dateFormat1.parse(dateStr);
		c.setTime(date);
		year = c.get(Calendar.YEAR);
		month  = c.get(Calendar.MONTH) + 1;//获取月
		maxDayOfDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);//获取最大天数 
	}
	if (statusStr != null) {
		status = statusStr;
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/ztree.jsp"%>
<title>今天</title>
<link href='<%=contextPath%>/common/fullcalendar/fullcalendar/fullcalendar.css' rel='stylesheet' />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/gray/easyui.css"/>
<link rel="stylesheet" href="<%=contextPath%>/common/jquery/ztree/css/demo.css" type="text/css"/>
<style>
</style>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/easyuiTools.js"></script>
<script type="text/javascript" src='../js/leaderCalendar.js?v=1'></script>
<script type="text/javascript" src='../js/affair.js?v=1'></script>

<script type="text/javascript">
var currDateStr = '<%=currDateStr%>';
var currYear = '<%=currYear%>';
var currMonth = '<%=currMonth%>';
var currDay  = '<%=currDay%>';
var moduleId = '<%=TeeModelIdConst.CALENDAR_POST_PRIV%>';
var status = '<%=status%>';
var deptId = '<%=deptId%>';
var dateList = new Array();
var fromView = '<%=fromView%>';//从哪个页面过来的day、week、month
function doOnload(){

    var statusName = "全部";
    var colorTypes = ["","#0000FF","#0000FF","#FF0000","#00AA00"];
    var colorType = colorTypes[status];
    var statusNames = ['全部','未开始','进行中','已超时','已完成'];
    for(var i = 0;i<statusNames.length;i++){
   		if(i==status){
     		statusName = statusNames[i];  
   		}
    }
  
 	/* 获取部门树 */
	getLeaderMonthPostDept(moduleId);


    $("[title]").tooltips();//title样式
}

function getCalendar(){
	$("#tableDiv").empty();
	var deptId = document.getElementById("deptId").value;
	var userList = new Array();
	var userIds = deptId ;
	
	
  	var url =   contextPath + "/calendarManage/getCalOfMonthByUserId.action";
	var month = $("#month").val();
	var year = $("#year").val();
	var day = $("#day").val();
	
	var para =  {userIds:userIds , year : year ,month: month  ,moduleId:3};
	var jsonCal = tools.requestJsonRs(url,para);
	 var prcsJson = new Array();
	if(jsonCal.rtState){
		prcsJson = jsonCal.rtData;
	}else{
		alert(jsonCal.rtMsg);
		return;
	}
    dateList = prcsJson.dateList;//日期数组
    var calendarList = prcsJson.calendarList;//日程
    var affairList = prcsJson.affairList;//周期性事务
  
    if(userIds != ""){
	  /* 创建Table */

	     createMonthTable(dateList , currDateStr , userIds);

	     createCalendarTd(calendarList);
	     
	     createAffairList(affairList);
	 
    }else{
    	messageMsg("没有查到到相关人员", "tableDiv" ,'' ,400);
    }
}

/**
 * 创建日程
 */
function createCalendarTd(calendarList , dateList ){
	$.each(calendarList , function(i , prc){
	//for ( var i = 0; i < calendarList.length; i++) {
		//var prc = calendarList[i];
        var seqId = prc.sid;
        var userId = prc.userId;
        var userName = prc.userName;
        var managerName = prc.managerName;
        var overStatus = prc.overStatus;
        var status = prc.status;
        var calLevel = prc.calLevel;
        var calType = prc.calType;
        var content = prc.content;
       
        var startTimeStr = prc.startTimeStr;
        var endTime = prc.endTimeStr;
        var statusName = "状态: 进行中";
        var calLevelName = "未指定";
        var calTypeName = "类型: 工作事务";
        if(managerName!='' && managerName != null){
          managerName = "\n安排人 ："+managerName;
        }else{
        	managerName = "";
        }
        if(calLevel==''){
          calLevel = 0;
        }
        var statusStyle = "color:#0000FF";
        if(status=='1'){
          statusName = "状态: 未开始";
        }
        if(status=='2'){
          statusName = "状态: 已超时";
          statusStyle = "color:#FF0000";
        }
        if(overStatus=='1'){
          statusName = "状态: 已完成";
          statusStyle = "color:#00AA00";
        } 
        if(calType=='2'){
          calTypeName = "类型: 个人事务";
        }
        /* if(userId==value){
       
        }  */
        var  title = calTypeName + "\n"+ statusName  + managerName;
        var dayStatus = prc.allDayStatus;
        if(dayStatus != 0){//跨天
        	//createAllDayCalendarTr(prc,'' , title);
        	 var div = "<div  title='"+ title +"' id='" + seqId + "' style='min-height: 30px; width:60%; margin-top:3px;margin-bottom:3px;' class='fc-event fc-event-vert fc-event-draggable fc-event-start fc-event-end fc-event-color ui-draggable ui-resizable'>"+
          	 " <div class=\"fc-event-inner\">"+
           	  "<div class=\"fc-event-time\">"+startTimeStr + "至" + endTime + "</div>"+
          	 	  "<div class=\"fc-event-title\">" + content + "</div>"+
          	 	  "</div>"+
          	 	
      	     "</div>"; 
      	     $("#spanMonth").show();
        	  $("#spanMonthCalendar").append(div);
        }else{//不跨天
        	
        	 var div = "<div  title='"+ title +"' id='" + seqId + "' style='min-height: 30px;  margin-top:3px;margin-bottom:3px;' class='fc-event fc-event-vert fc-event-draggable fc-event-start fc-event-end fc-event-color ui-draggable ui-resizable'>"+
          	 " <div class=\"fc-event-inner\">"+
           	  "<div class=\"fc-event-time\">"+startTimeStr.substring(11,16)+ "</div>"+
          	 	  "<div class=\"fc-event-title\">" + content + "</div>"+
          	 	  "</div>"+
          	 	
      	     "</div>"; 
            $("#td_"+ userId +"_" + startTimeStr.substring(0,10)).append(div);
            //alert(seqId+":"+  $("#" + seqId));

        }
        $("#" + seqId).click(function(event){
        	toClickCalendar(prc);
        });
       
	});
}
/**
 * 本月
 */
function today(){
	if(currMonth.length == 1){
		currMonth = "0" + currMonth;
	}
	if(currDay.length == 1){
		currDay = "0" + currDay;
	}
	$("#year").val(currYear);
	$("#month").val(currMonth);

	
	getCalendar();
	//window.location.href = "<%=contextPath%>/system/core/base/calendar/leader/month.jsp";
}
</script>
</head>
<body class="" topmargin="5"   style="padding:5px 5px 0px 5px;" onload="doOnload();">
	<form name="form1" method="post" style="margin-bottom: 5px;">
		<table style="width:100%">
			<tr>
				<td>	
					<input type="hidden" value="25" name="DAY"> 
					<a href="javascript: void(0)"  onclick="today()" class="btn btn-primary"><span>本月</span></a>

					<select id="year" name="year" class="BigSelect"
					onchange="getCalendar();">
					<%
						for (int i = 2000; i < 2050; i++) {
							if (i == year) {
					%>
					<option value="<%=i%>" selected="selected"><%=i%>年
					</option>
					<%
						} else {
					%>
					<option value="<%=i%>"><%=i%>年
					</option>
					<%
						}
						}
					%>
				</select>
				<!-- 月-->
				<select id="month" name="month" class="BigSelect" onchange="getCalendar();">
				  <%
				      for(int i=1;i<13;i++){
				         if(i>=10){
				         	if(i==month){
				  %>
				    <option value="<%=i %>" selected="selected"><%=i %>月</option>
				      	  <%}else{ %>
				    <option value="<%=i %>"><%=i %>月</option>
				      <%
				            }    
				       }else{
				            if(i==month){
				      %>
				      <option value="0<%=i %>" selected="selected">0<%=i %>月</option>
				           <%}else{ %>
				   	   <option value="0<%=i %>">0<%=i %>月</option>
				 <%
				      	     }
				         }
				     }        
				  %>
				</select>
				
				<%--  <a href="javascript:set_week(1);" class="ArrowButtonR" title="下一周"><img src="<%=imgPath%>/nextpage.gif"></img></a>
		  <a href="javascript:set_year(1);" class="ArrowButtonRR" title="下一年"><img src="<%=imgPath%>/nextnextpage.png"></a>&nbsp;
		 --%>
					<!-- <a id="statusName" href="javascript:void(0);" class="dropdown" onclick="showMenuStatus(event);" hidefocus="true">
					<span id="name">全部</span></a>&nbsp;  -->
					<input type="hidden" id="status" name="status" value="0"></input>
				</td>
			</tr>
			<tr>
				<td align='left'>
				
				  	<input type="button" value="查询" class="btn btn-primary" title="查询" onclick="selectCalendar();">
					 <input id="deptId" name="deptId" type="text" style="display: none;" value='' />
	
					<ul id="deptIdZTree" class="ztree"style="margin-top: 0; width: 200px; display: none;"></ul>
	
					<span class="fc-button fc-button-month fc-state-default fc-corner-left fc-state-active" unselectable="on"  onclick="setCalendarView('month','month')">月</span>
					<span class="fc-button fc-button-agendaWeek fc-state-default " unselectable="on" onclick="setCalendarView('week' , 'month')"  style="cursor: pointer;">周</span> 
					<span class="fc-button fc-button-agendaDay fc-state-default fc-corner-right " unselectable="on" style="cursor: pointer;"  onclick="setCalendarView('day','month')">日</span>
					<!--    <a class="calendar-view day-view" href="javascript:set_view('day');" title="日视图"></a>
				   <a class="calendar-view week-view" href="javascript:set_view('week');" title="周视图"></a>
				   <a class="calendar-view month-view" href="javascript:set_view('month');" title="月视图"></a> -->
					<input type="hidden" id="myDeptId" name="myDeptId" value=""></input>
				</td>
			</tr>
		</table>
	</form>
	<input type="hidden" name="userIds" id="userIds" value="">
	<div id="tableDiv" align="" style="padding-top:5px;">
		
	</div>
</body>
</html>


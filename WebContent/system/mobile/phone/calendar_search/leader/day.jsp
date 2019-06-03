
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>
<%
	TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
	Date currDate = new Date();
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy年-MM月-w周-dd日");
	SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat dateFormat4 = new SimpleDateFormat("MM/dd");
	SimpleDateFormat dateFormatWeek = new SimpleDateFormat("E");
	Calendar c = Calendar.getInstance();
	String currWeek = dateFormatWeek.format(currDate);//系统当前星期 几
	String currDateStr = dateFormat.format(currDate);//系统当前时间
	String currSimpDateStr = dateFormat3.format(currDate);//系统当前时间字符串
	int currYear = c.get(Calendar.YEAR);//.parseInt(dateStr.substring(0, 4));//系统当前年份
	int currMonth = c.get(Calendar.MONTH) + 1;//Integer.parseInt(dateStr.substring(5, 7)); //系统当前月份
	int currDay = c.get(Calendar.DAY_OF_MONTH);//Integer.parseInt(dateStr.substring(8, 10));//系统当前日
	int currWeekth = c.get(Calendar.WEEK_OF_YEAR);//系统当前周
	
	int currMaxDayOfMonth=c.getActualMaximum(Calendar.DAY_OF_MONTH);//本月份的最大天数 
	//从其它页面传过来
	String status = "0";//状态 0-全部 1-进行中 2-已完成 3-已超时
	String dateStr = request.getParameter("dateStr");
	String statusStr = request.getParameter("statusStr");
	String deptId = request.getParameter("deptId");//传过来的部门Id
	String fromView = request.getParameter("fromView") == null ? "week" :  request.getParameter("fromView");//传过来的部门Id
	if (deptId == null && !fromView.equals("month")) {//不是月视图
		deptId = person.getDept().getUuid() + "";
	}
	int year = currYear;
	int month = currMonth;
	int day = currDay;
	Date date = currDate;
	String dateTimeStr = currSimpDateStr;//系统当前时间字符串
	int maxDayOfDay = currMaxDayOfMonth;
	if (dateStr != null) {
		date = dateFormat1.parse(dateStr);
		c.setTime(date);
		maxDayOfDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);//获取最大天数 
		year = c.get(Calendar.YEAR);// 年份
		month= c.get(Calendar.MONTH) + 1;//月份
		day = c.get(Calendar.DAY_OF_MONTH);//日
		//dateTimeStr =  dateFormat3.format(c.getTime());//系统时间字符串
	}
	/* System.out.println(year +":"+month + ":" + day); */
	if (statusStr != null) {
		status = statusStr;
	}		

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
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
<script type="text/javascript" src="<%=contextPath %>/common/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript">
var year = '<%=year%>';
var currYear = '<%=currYear%>';
var currMonth = '<%=currMonth%>';
var currDay = '<%=currDay%>';
var moduleId = '<%=TeeModelIdConst.CALENDAR_POST_PRIV%>';
var deptId = '<%=deptId%>';
var status = '<%=status%>';
var fromView = '<%=fromView%>';//从哪个页面过来的day、week、month
var dateList = new Array();
function doOnload(){
	//alert(deptId);
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
	getLeaderPostDept(deptId,moduleId);


    $("[title]").tooltips();//title样式
}
var date ;//日期
function getCalendar(){
	$("#tableDiv").empty();
	var deptId = document.getElementById("deptId").value;
	var userList = new Array();
	var userIds ;
	
	if(deptId != ""){
		/* 根据部门查询人员 */
		var userJson = getUserByDeptId(deptId , moduleId);
		userList = userJson.rtData;//人员数组
		userIds = userJson.rtMsg;//人员Id字符串，以逗号分隔
	}else{
		userIds = $("#userIds").val();
		userList = userDatas;
	}

  	var url =   contextPath + "/calendarManage/getCalOfDayByUserIds.action";
	var month = $("#month").val();
	var year = $("#year").val();
	var day = $("#day").val();
	date = year + "-" + month + "-" + day;
	var para =  {userIds:userIds , dateStr : year + "-" + month + "-" + day ,moduleId:3};
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
  
    if(userList.length > 0){
	  /* 创建Table */
	    createDayTable(dateList , '');

	    //列出本部门所有的人员
	    var prcs = new Array;
	    for(var i = 0;i<userList.length;i++){
	        var user = userList[i];
	        var value = user.uuid;
	        var name = user.userName;
	        createNullCalendarTr(value,name);//创建Tr
		}

	    createCalendarTd(calendarList , dateList);
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
        if(managerName !='' && managerName != null){
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
        var time = startTimeStr.substring(11,16);
        if(dayStatus != 0){//跨天
        	time = startTimeStr + "至" + endTime;
        }
        var div = "<div title='"+ title +"' id='" + seqId + "' style='min-height: 30px;width:30%;  margin-top:3px;margin-bottom:3px;' class='fc-event fc-event-vert fc-event-draggable fc-event-start fc-event-end fc-event-color ui-draggable ui-resizable'>"+
     	 " <div class=\"fc-event-inner\">"+
      	  "<div class=\"fc-event-time\">" + time + "</div>"+
     	 	  "<div class=\"fc-event-title\">" + content + "</div>"+
     	 	  "</div>"+
 	     "</div>"; 
        $("#td_"+ userId +"_" + dateList[0].simpDate).append(div);
        $("#" + seqId).click(function(event){
        	toClickCalendar(prc);
        });
       
	});
}

/***
 * 空日程创建Tr
 */
function createNullCalendarTr(value,name){
  	//判断今天是星期几
  	if(!$("#tr_" + value)[0]){//如果不存在
  		var curDate = new Date();
  	    var week = curDate.getDay();
  	    if(week == 0){
  	        week = 7;
  	    }
  	    
  	    var tmp = "<td id='td_"+value+"_name' nowrap width='80' align='center'  >"+name+"</td>";
  	    var tdClass="TableData";
  	    if(week==0){
  	         tdClass = "TableContent";
  	    }
  		var tdClassStyle = "";
 
  		var currSimpDateStr = '<%=currSimpDateStr%>';
 		//alert(date +":"+currSimpDateStr )
        if(date==currSimpDateStr){//如果是当天
        	tdClassStyle= "background-color:rgb(252, 248, 227);";
        }
  	    tmp = tmp  +"<td id='td_"+value+"_"+ dateList[0].simpDate +"' class='"+tdClass+"' style='"+tdClassStyle+"' title='双击建立事务' onclick='toNewCalendarByDay("+value+",\""+(dateList[0].simpDate)+"\")'></td>";
  	    var tr = "<tr id='tr_" + value + "' class='TableData' style='min-height:30px;"+tdClassStyle+"'>" + tmp + "</tr>" ;
  	    $("#tbody").append(tr); 
  	}
    
}
/**
 * 点击几天
 */
function today(){
	$("#year").val(currYear);
	if(currMonth.length == 1){
		currMonth = "0" + currMonth;
	}
	if(currDay.length == 1){
		currDay = "0" + currDay;
	}
	$("#month").val(currMonth);
	$("#day").val(currDay);
	getCalendar();

	//window.location.href = "<%=contextPath%>/system/core/base/calendar/leader/day.jsp";
}
/**
 * 改变年份和月份  处理最大日
 */
function onchangYearOrMonth(){
	var year = $("#year").val();
	var month = $("#month").val();
	var day = $("#day").val();
	//alert(year + ":"+ month +":" + day)
	var date = new Date(parseInt(year,10) ,parseInt(month,10) , 0);
	var maxDay = date.getDate();
	var daySelect = $("#day");
	daySelect.empty();
	var daySelectStr = "";
	for(var i = 1; i<= maxDay ; i++ ){
		var iStr = i;
		if(i<10){
			iStr = "0" + i;
		}
		var selected = "";
		if(day == iStr){
			selected = "selected";
		}
		if(maxDay == i && selected == ''){
			selected = "selected";
		}
		daySelectStr = daySelectStr + "<option value ='" + iStr + "' " + selected + ">" +  iStr + "</option>";
	}
	daySelect.append(daySelectStr);
	getCalendar();
}
</script>
<body class="" topmargin="5" onload="doOnload();"  style="padding:5px 5px 0px 5px;">
	<form name="form1" method="post" style="margin-bottom: 5px;">
		<table style="width:100%">
			<tr>
				<td>	
					<input type="hidden" value="25" name="DAY"> 
					<a href="javascript: void(0)"  onclick="today()" class="btn btn-primary"><span>今天</span></a>
					<%-- <input type="text" name="date" id="date" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="BigInput" value="<%=dateTimeStr%>"/>
				 --%>
				<span style="" >
					<select id="year" name="year" class="BigSelect"
					onchange="onchangYearOrMonth()">
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
				<select id="month" name="month" class="BigSelect" onchange="onchangYearOrMonth();">
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
				
				<!-- 日 -->
				<select id="day" name="day"  class='BigSelect'  onchange="getCalendar();">
			    <%
			        for(int i=1;i<=maxDayOfDay;i++){
				        String dayS = "";
				        String dayDesc = "";
				        String selected = "";
				        if(i==day){
				            selected = "selected = \"selected\"";
				        }
				     
				        if (i > 9) {
				            dayDesc = String.valueOf(i);
				            dayS = dayDesc;
				        }else {
				            dayDesc = "0" + i;
				            dayS = dayDesc;
				        }
	  			  %>
	  			  		<option value="<%=dayS %>"  <%=selected %>><%=dayDesc %>日</option>    
	    		 <%
				   	}
	     		 %>
		  </select>
		  
		  </span>
				<%--  <a href="javascript:set_week(1);" class="ArrowButtonR" title="下一周"><img src="<%=imgPath%>/nextpage.gif"></img></a>
		  <a href="javascript:set_year(1);" class="ArrowButtonRR" title="下一年"><img src="<%=imgPath%>/nextnextpage.png"></a>&nbsp;
		 --%>
				<!-- 	<a id="statusName" href="javascript:void(0);" class="dropdown" onclick="showMenuStatus(event);" hidefocus="true">
					<span id="name">全部</span></a>&nbsp;  -->
					<input type="hidden" id="status" name="status" value="0"></input>
				</td>
			</tr>
			<tr>
				<td align='left'>
				
				  	<input type="button" value="查询" class="btn btn-primary" title="查询" onclick="selectCalendar();">
				    <input id="deptId" name="deptId" type="text" style="display: none;" value='' />
					<input id="userId" name="userId" type="text" style="display: none;" value='' />
					<ul id="deptIdZTree" class="ztree"style="margin-top: 0; width: 200px; display: none;"></ul>
	
					<span class="fc-button fc-button-month fc-state-default fc-corner-left" unselectable="on" style="cursor: pointer;" onclick="setCalendarView('month','day')">月</span>
					<span class="fc-button fc-button-agendaWeek fc-state-default " unselectable="on" onclick="setCalendarView('week' ,  'day')"  style="cursor: pointer;">周</span> 
					<span class="fc-button fc-button-agendaDay fc-state-default fc-corner-right fc-state-active" unselectable="on"  >日</span>
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


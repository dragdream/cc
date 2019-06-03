
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>
<%
	TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
	Date date = new Date();
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy年-MM月-w周-dd日");
	SimpleDateFormat dateFormat4 = new SimpleDateFormat("MM/dd");
	SimpleDateFormat dateFormatWeek = new SimpleDateFormat("E");
	Calendar c = Calendar.getInstance();
	String currWeek = dateFormatWeek.format(date);//系统当前星期 几
	String currDateStr = dateFormat1.format(date);//系统当前时间
	int currYear = c.get(Calendar.YEAR);//.parseInt(dateStr.substring(0, 4));//系统当前年份
	int currMonth = c.get(Calendar.MONTH);//Integer.parseInt(dateStr.substring(5, 7)); //系统当前月份
	int currDay = c.get(Calendar.DAY_OF_MONTH);//Integer.parseInt(dateStr.substring(8, 10));//系统当前日
	int currWeekth = c.get(Calendar.WEEK_OF_YEAR);//系统当前周
	
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
	if (dateStr != null ) {
		date = dateFormat1.parse(dateStr);
		c.setTime(date);
		year = c.get(Calendar.YEAR);
		weekth = c.get(Calendar.WEEK_OF_YEAR);//获取周
	}
	if (statusStr != null) {
		status = statusStr;
	}
	
	int maxWeek =TeeDateUtil.getMaxWeekOfYear(year);//一年最大周
	if(maxWeek <=52){
		Calendar[] maxWeenStartAndEndDate = TeeDateUtil.getStartToEndDate(year, maxWeek);//一年最后一周开始和结束
		Calendar endCal = maxWeenStartAndEndDate[1];
		if(!dateFormat1.format(endCal.getTime()).equals(year + "-12-31")){//如果是最后一天，
	maxWeek = 53;
		}
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
<script type="text/javascript" src='../js/leaderCalendar.js?v=3'></script>
<script type="text/javascript" src='../js/affair.js?v=1'></script>
<script src="<%=request.getContextPath() %>/system/mobile/js/orgselect.js?v=3"></script>

<script type="text/javascript">
var currDateStr = '<%=currDateStr%>';
var currYear = '<%=currYear%>';
var currWeekth = '<%=currWeekth%>';
var deptId = '<%=deptId%>';
var moduleId = '<%=TeeModelIdConst.CALENDAR_POST_PRIV%>';
var status = '<%=status%>';
var fromView = '<%=fromView%>';//从哪个页面过来的day、week、month
var dateList = new Array();
var userList = new Array();
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
    document.getElementById("status").value=status;

 	/* 获取部门树 */
	getLeaderPostDept(deptId , moduleId);

    $("[title]").tooltips();//title样式
}

function getCalendar(){
	$("#tableDiv").empty();
	var deptId = document.getElementById("deptId").value;
	
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

	
  	var url =   contextPath + "/calendarManage/getCalOfWeekByUserIds.action";
	var weekth = $("#week").val();
	var year = $("#year").val();
	var para =  {userIds:userIds , year: year , weekth: weekth ,moduleId:3};
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
	    createWeekTable(dateList , '');

	    //列出本部门所有的人员
	    var prcs = new Array;
	    for(var i = 0;i<userList.length;i++){
	        var user = userList[i];
	        var value = user.uuid;
	        var name = user.userName;
	        createNullCalendarTr(value,name);//创建Tr
		}
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
        	createAllDayCalendarTr(prc,'' , title);
        }else{//不跨天
        	var rand = Math.random()*1000;
       	 var div = "<div title='"+ title +"' sid='" + seqId+rand + "' style='min-height: 30px;  margin-top:3px;margin-bottom:3px;' class='fc-event fc-event-vert fc-event-draggable fc-event-start fc-event-end fc-event-color ui-draggable ui-resizable'>"+
         	 " <div class=\"fc-event-inner\">"+
          	  "<div class=\"fc-event-time\">"+startTimeStr.substring(11,16)+ "</div>"+
         	 	  "<div class=\"fc-event-title\">" + content + "</div>"+
         	 	  "</div>"+
         	 	
     	     "</div>"; 
           $("#td_"+ userId +"_" + startTimeStr.substring(0,10)).append(div);
           var sp = prc.actorIds.split(",");
           for(var j=0;j<sp.length;j++){
           	$("#td_"+ sp[j] +"_" + startTimeStr.substring(0,10)).append(div);
           }
           $("div[sid='"+seqId+rand+"']").click(function(event){
           	toClickCalendar(prc);
           	window.event.cancelBubble = true;
           });
        	
        }
       
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
  	    for(var i = 1;i<8 ; i++){
  	        var tdClassStyle = "";
  	        if(week==i&&  dateList[i-1].simpDate == currDateStr){
  	            tdClassStyle= "background-color:rgb(252, 248, 227);";
  	     	}
  	        tmp = tmp  +"<td id='td_"+value+"_"+ dateList[i-1].simpDate +"'  class='TableData' title='双击建立事务' onclick='toNewCalendarByDay("+value+",\""+(dateList[i-1].simpDate)+"\")' style='"+tdClassStyle+"'></td>";
  	    }
  	    var tr = "<tr id='tr_" + value + "' class='TableData' style='min-height:30px;'>" + tmp + "</tr>" ;
  	    $("#tbody").append(tr); 
  	}
    
}
/**
 * 创建跨天Tr
 *	title:日程标题
 */
function createAllDayCalendarTr(calendar , currDate ,title){
	 var sid = calendar.sid;
		var userId = calendar.userId;
		var userName = calendar.userName;
		var startTimeStr = calendar.startTimeStr;
		var endTimeStr = calendar.endTimeStr;
		var content = calendar.content;
		var actorIds = calendar.actorIds.split(",");
		
		if(!$("#td_" + userId + "_allDay")[0]){//不存在
			  var userNameTd = $("#td_"+userId+"_name");
			// var ss = $(userNameTd).parent().find("td:first-child")[0];
			if($(userNameTd)[0]){
				var userNameTdStr = $(userNameTd)[0].outerHTML;//获取td所用信息
				 $("#td_"+userId+"_name").remove();//删除
				 var trStr = "<tr class='TableData' height='30px'>"+
				 	userNameTdStr+
			 	    " <td colspan=7 id='td_"+userId+"_allDay'></td>";
				  + "</tr>";
				$(trStr).insertBefore("#tr_" + userId);	 //添加之前添加行
				$("#td_"+userId+"_name").attr("rowspan",2);//合并
			}
		}
		   //计算周宽度
		var th = $("#tbl_header")[0];
		var week_width = 0;
		    if(!th){
		    	return;
		    }
		 for(var j = 1; j < th.cells.length; j++){
		     week_width += th.cells[j].offsetWidth;
		 }  
		 
		 var startWeekE = setWeekDate(startTimeStr);//开始属于星期几
		 var endWeekE = setWeekDate(endTimeStr);//结束星期几
		 
		  if(startTimeStr.substr(0,10) <= dateList[0].simpDate){
			  startWeekE = 1;
	  	  }
	  	  if(endTimeStr.substr(0,10)>= dateList[6].simpDate){
	  		endWeekE = 7;
	      }
		//计算偏移量和长度
		 var left = width = 0;//固定
		 var leftF = widthF = 0; //百分比
	     for(var j = 1; j < startWeekE; j++){
	       	left += th.cells[j].offsetWidth;
	       	leftF = leftF + 14.2;
	     }
	     for(var j = startWeekE; j <= endWeekE; j++){
	       width += th.cells[j].clientWidth;
	       widthF = widthF + 11.6 + (1.8 * (j-startWeekE));
	     }
	     if(left + width > week_width - 6){
	     	 // width = week_width - left - 6;
	     	  //widthF = 13.7 * 7;
	     }
	     var rand = Math.random()*1000;
		 var div = "<div sid='" + sid+rand +  "' title='" + title + "' style='margin-left:" + (leftF)+ "%;width: " +(widthF)+ "%; min-wdith:100px; min-height: 30px; margin-top:3px;margin-bottom:3px;' class='fc-event fc-event-vert fc-event-draggable fc-event-start fc-event-end fc-event-color ui-draggable ui-resizable'>"+
	  			 		" <div class=\"fc-event-inner\">"+
	  				 	 "<div class=\"fc-event-time\">"+startTimeStr + "至" + endTimeStr + "</div>"+
	  	 	  		 		"<div class=\"fc-event-title\">" + content + "</div>"+
	  	 	  			 "</div>"+
		 		  "</div>";
	    $("#td_"+ userId +"_allDay").append(div);
	    $("div[sid='"+sid+rand+"']").click(function(event){
	    	toClickCalendar(calendar);
	    });
	    
	    
	    for(var i=0;i<actorIds.length;i++){
	    	userId = actorIds[i];
	    	if(!$("#td_" + userId + "_allDay")[0]){//不存在
	  		  var userNameTd = $("#td_"+userId+"_name");
	  		// var ss = $(userNameTd).parent().find("td:first-child")[0];
	  		if($(userNameTd)[0]){
	  			var userNameTdStr = $(userNameTd)[0].outerHTML;//获取td所用信息
	  			 $("#td_"+userId+"_name").remove();//删除
	  			 var trStr = "<tr class='TableData' height='30px'>"+
	  			 	userNameTdStr+
	  		 	    " <td colspan=7 id='td_"+userId+"_allDay'></td>";
	  			  + "</tr>";
	  			$(trStr).insertBefore("#tr_" + userId);	 //添加之前添加行
	  			$("#td_"+userId+"_name").attr("rowspan",2);//合并
	  		}
	  	}
	  	   //计算周宽度
	  	var th = $("#tbl_header")[0];
	  	var week_width = 0;
	  	    if(!th){
	  	    	return;
	  	    }
	  	 for(var j = 1; j < th.cells.length; j++){
	  	     week_width += th.cells[j].offsetWidth;
	  	 }  
	  	 
	  	 var startWeekE = setWeekDate(startTimeStr);//开始属于星期几
	  	 var endWeekE = setWeekDate(endTimeStr);//结束星期几
	  	 
	  	  if(startTimeStr.substr(0,10) <= dateList[0].simpDate){
	  		  startWeekE = 1;
	    	  }
	    	  if(endTimeStr.substr(0,10)>= dateList[6].simpDate){
	    		endWeekE = 7;
	        }
	  	//计算偏移量和长度
	  	 var left = width = 0;//固定
	  	 var leftF = widthF = 0; //百分比
	       for(var j = 1; j < startWeekE; j++){
	         	left += th.cells[j].offsetWidth;
	         	leftF = leftF + 14.2;
	       }
	       for(var j = startWeekE; j <= endWeekE; j++){
	         width += th.cells[j].clientWidth;
	         widthF = widthF + 11.6 + (1.8 * (j-startWeekE));
	       }
	       if(left + width > week_width - 6){
	       	 // width = week_width - left - 6;
	       	  //widthF = 13.7 * 7;
	       }
	       var rand = Math.random()*1000;
	  	 var div = "<div sid='" + sid+rand +  "' title='" + title + "' style='margin-left:" + (leftF)+ "%;width: " +(widthF)+ "%; min-wdith:100px; min-height: 30px; margin-top:3px;margin-bottom:3px;' class='fc-event fc-event-vert fc-event-draggable fc-event-start fc-event-end fc-event-color ui-draggable ui-resizable'>"+
	    			 		" <div class=\"fc-event-inner\">"+
	    				 	 "<div class=\"fc-event-time\">"+startTimeStr + "至" + endTimeStr + "</div>"+
	    	 	  		 		"<div class=\"fc-event-title\">" + content + "</div>"+
	    	 	  			 "</div>"+
	  	 		  "</div>";
	      $("#td_"+ userId +"_allDay").append(div);
	      $("div[sid='"+sid+rand+"']").click(function(event){
	      	toClickCalendar(calendar);
	      });
	    }
}

function today(){
	$("#week").val(currWeekth);
	$("#year").val(currYear);
	getCalendar();

	//window.location.href = "<%=contextPath%>/system/core/base/calendar/leader/week.jsp";
}
</script>
</head>
<body class="" topmargin="5"  style="padding:5px 5px 0px 5px;" onload="doOnload();">
	<form name="form1" method="post" style="margin-bottom: 5px;">
		<table style="width:99%">
			<tr>
				<td>	
					<input type="hidden" value="25" name="DAY"> 
					<a href="javascript: void(0)"  onclick="today()" class="btn btn-primary"><span>本周</span></a>
				<!-- 年 -->
				<%--   <a href="javascript:set_year(-1)";  title="上一年"><img src="<%=imgPath%>/prevpreviouspage.png"></img></a>
		  <a href="javascript:set_week(-1);" class="ArrowButtonR" title="上一周"><img src="<%=imgPath%>/previouspage.gif"></img></a>
		 --%>
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
				<!-- 周 -->
				<select id="week" name="week" class="BigSelect"
					onchange="getCalendar();">
					<%
						for (int i = 1; i <= maxWeek; i++) {
							if (i == currWeekth) {
					%>
					<option value="<%=i%>" selected="selected">
						第<%=i%>周
					</option>
					<%
						} else {
					%>
					<option value="<%=i%>">
						第<%=i%>周
					</option>
					<%
						}
						}
					%>
				</select>
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
				
				  	<input type="button" value="查询" class="btn btn-primary" title="查询" onclick="selectCalendar();"/>
					 <input id="deptId" name="deptId" type="text" style="display: none;" value='<%=deptId %>' />
	
					<ul id="deptIdZTree" class="ztree"style="margin-top: 0; width: 200px; display: none;"></ul>
	
					<span class="fc-button fc-button-month fc-state-default fc-corner-left" unselectable="on" style="cursor: pointer;" onclick="setCalendarView('month' , 'week')">月</span>
					<span class="fc-button fc-button-agendaWeek fc-state-default fc-state-active" unselectable="on">周</span> 
					<span class="fc-button fc-button-agendaDay fc-state-default fc-corner-right" unselectable="on" style="cursor: pointer;"  onclick="setCalendarView('day', 'week')">日</span>
					<!--    <a class="calendar-view day-view" href="javascript:set_view('day');" title="日视图"></a>
				   <a class="calendar-view week-view" href="javascript:set_view('week');" title="周视图"></a>
				   <a class="calendar-view month-view" href="javascript:set_view('month');" title="月视图"></a> -->
					<input type="hidden" id="myDeptId" name="myDeptId" value=""></input>
				</td>
			</tr>
		</table>
	</form>
	<input type="hidden" name="userIds" id="userIds" value=""/>
	<div id="tableDiv" align="" style="padding-top:5px;">
		
	</div>
</body>
</html>



	<%@ page language="java" contentType="text/html; charset=UTF-8"
			 pageEncoding="UTF-8"     import="java.util.*,java.text.SimpleDateFormat" %>
			<%
	  Date curDate = new Date();
	  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  SimpleDateFormat dateFormatWeek = new SimpleDateFormat("E");
	  Calendar c = Calendar.getInstance();
	  String curDateStr = dateFormat.format(curDate);
	  int currMonth = Integer.parseInt(curDateStr.substring(5,7));
	  int currDay = Integer.parseInt(curDateStr.substring(8,10));
	  int currHour = Integer.parseInt(curDateStr.substring(11,13));
	  int currMinute = Integer.parseInt(curDateStr.substring(14,16));
	  int currSecond = Integer.parseInt(curDateStr.substring(17,19));
	  String currTime = curDateStr.substring(11,19);
	  c.setTime(curDate);
	  int currWeek = c.get(Calendar.DAY_OF_WEEK);
	  if(currWeek == 1){
		  currWeek = 7;
	  }else{
		  currWeek = currWeek - 1;
	  }
%>

		<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
		<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
		<meta http-equiv="X-UA-Compatible" content="IE=Edge">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<%@ include file="/header/header2.0.jsp" %>
		<%@ include file="/header/easyui2.0.jsp" %>
		<title>日程安排</title>
		<link href='<%=contextPath %>/common/fullcalendar/fullcalendar/fullcalendar.css' rel='stylesheet' />
		<link href='<%=contextPath %>/common/fullcalendar/fullcalendar/fullcalendar.print.css' rel='stylesheet' media='print' />
		<link  rel='stylesheet' href='<%=contextPath %>/system/core/base/calendar/css/calendar.css'type="text/css" />

		<style type="text/css">


		</style>
		<script  type="text/javascript"  src='<%=contextPath %>/common/fullcalendar/lib/jquery-ui.custom.min.js'></script>
		<script  type="text/javascript"  src='<%=contextPath %>/common/fullcalendar/fullcalendar/fullcalendar.min.js'></script>
		<script type="text/javascript" src="<%=contextPath %>/common/My97DatePicker/WdatePicker.js"></script>
		<script  type="text/javascript"  src='<%=contextPath %>/system/core/base/calendar/js/calendar.js'></script>
		<script  type="text/javascript"  src='<%=contextPath %>/system/core/base/calendar/js/affair.js'></script>


		<script type="text/javascript">
		var currWeek = '<%=currWeek%>';
		var currDay = '<%=currDay%>';
		var currMonth = '<%=currMonth%>';
		var currTime = '<%=currTime%>';

		function getView(){
		var view = $('#calendar').fullCalendar('getView');
		alert("The view's title is " + view.title);
		}

		/***
		* 弹出
		*/
		function openAddCalendar(){
		/* $.jBox(addUpdateCalFrom,{title:"新建日程",width:500,height:370,buttons: {}});
		$("#START_DATE").val($.fullCalendar.formatDate(new Date(),'yyyy-MM-dd HH:mm'));
		$("#END_DATE").val($.fullCalendar.formatDate(new Date(),'yyyy-MM-dd ' + '23:59'));
		setRemindTimePopover();*/
		$("#calendar_add_update").empty();
		$("#calendar_add_update").append(addUpdateCalFrom);
		$("#START_DATE").val($.fullCalendar.formatDate(new Date(),'yyyy-MM-dd HH:mm'));
		$("#END_DATE").val($.fullCalendar.formatDate(new Date(),'yyyy-MM-dd ' + '23:59'));
		//setRemindTimePopover();
		}
		

		/***
		* overstatus状态类型事件
		*/
		$(document).ready(function(){
		$("#current_status").mouseover(function(){
		$("#status_menu").css("display","block");
		<%--$(this).css("border-bottom","none");--%>
		});
		$("#current_status").mouseout(function(){
		$(this).css("border-bottom","1px solid #ccc");
		});
		$("#status_menu a").mouseover(function(){
		<%--$("#current_status").css("border-bottom","none");--%>
		});
		$("#status_menu a").mouseout(function(){
		$("#current_status").css("border-bottom","1px solid #ccc");
		});


		$("#status_menu a").click(function(event){
		var index = $(this).attr('index');//更改状态
		var calId =  $("#calId").val();//日程Id
		//alert(index +":"+ calId);
		updateOverStatus(calId , index);

		});



		//得到提醒时间设置select
		getRemindTimeDataContent(<%=currHour%>,<%=currMinute%>,<%=currSecond%>);
		getAddUpdateCalFrom();//新建或者更新日程表单


		});

		/***
		* 更改状态
		* @para calId 日程Id
		* @para index 更改状态  0 -未完成  1-完成
		*/
		function updateOverStatus(calId , index){
		var url =  contextPath +  "/calendarManage/updateOverStatus.action";
		var para = {sid:calId,overStatus:index};
		var jsonObj = tools.requestJsonRs(url,para);
		//alert(jsonObj.rtState);
		if(jsonObj.rtState){
		//$("#status_menu").hide();
		$("#status_menu").css("display","none");
		calendar.fullCalendar('removeEvents' ,calId);//删除节点
		//alert(data.title)
		calendar.fullCalendar('renderEvent', jsonObj.rtData); //在添加

		if(index=="1"){
		$("#status").text("已完成");
		}else{
		$("#status").text("未完成");
		}
		return jsonObj.rtData;
		}else{
		alert(jsonObj.rtMsg);
		}
		}

		</script>

		<style>

		body {
		text-align: center;
		font-size: 14px;
		font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
		overflow:hidden;
		padding:5px;
		}

		#calendar {
		margin: 0 auto;
		}

		.modal-test {
		width: 564px;
		position: absolute;
		display: none;
		z-index: 999;
		}

		.modal-test .modal-header {
		width: 100%;
		height: 50px;
		background-color: #8ab0e6;
		}

		.modal-test .modal-header .modal-title {
		color: #fff;
		font-size: 16px;
		line-height: 50px;
		margin-left: 20px;
		float: left;
		}

		.modal-test .modal-header .modal-win-close {
		color: #fff;
		font-size: 16px;
		line-height: 50px;
		margin-right: 20px;
		float: right;
		cursor: pointer;
		}

		.modal-test .modal-body {
		width: 100%;
		height: 410px;
		background-color: #f4f4f4;
		}

		.modal-test .modal-body ul {
		overflow: hidden;
		clear: both;
		}

		.modal-test .modal-body ul li {
		width: 510px;
		height: 30px;
		line-height: 30px;
		margin-top: 25px;
		margin-left: 20px;
		}

		.modal-test .modal-body ul li span {
		display: inline-block;
		float: left;
		vertical-align: middle;
		}

		.modal-test .modal-body ul li input {
		display: inline-block;
		float: right;
		width: 400px;
		height: 25px;
		}

		.modal-test .modal-footer {
		width: 100%;
		height: 60px;
		background-color: #f4f4f4;
		}

		.modal-test .modal-footer input {
		margin-top: 12px;
		float: right;
		margin-right: 20px;
		}
		.attach_div{
		background-color:#fff;
		}
		.attach_div>a{
		display:block;
		}
		.attach_div>a:hover{
		background-color:#007eff;
		color:#fff;
		}
		#calendar-quick-setup{
		border: 1px solid rgb(222, 224, 223);
		box-shadow: #aaa 3px 2px 9px;
		}
		#calendar-quick-detail{
		border: 1px solid rgb(222, 224, 223);
		box-shadow: #aaa 3px 2px 9px;
		}
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
		<body>


		<div id='calendar'>

		</div>



		<!-- 点击触发事件，快速新建 -->
		<div id='calendar-quick-setup' class="popover  right " style='background-color:#fbfafb; max-width:300px;position:absolute;z-index:9;padding:10px;'>
		<div class="arrow"></div>
		<div class="popover-content">

		<form class="form" style="text-align:left;line-height:25px;">
		<div class="control-group" style="margin-bottom:10px;">
		<textarea id="quick-calendar-title" placeholder="内容" class="input" style="width:240px;line-height:24px;"></textarea>
		</div>
		<div class="control-group" style="margin-bottom:10px;">
		<span id="quick-begin-time"></span>
		<span style="margin:0 2px;" id="quick-name">至</span>
		<span id="quick-finish-time"></span>
		</div>
		</form>
		<div class="row-fluid" >

		<div class="span9" >
		<div class="pull-left fl" style="padding-bottom:10px;">
		<button class="btn btn-primary btn-alert-blue modal-menu-test" type="button" data-cmd="editmore">完整编辑</button>
		</div>
		<div class="pull-right fr">
		<button class="btn btn-primary btn-alert-blue"  data-cmd="ok" class='edit_ok' type="button" onclick="">确认</button>
		<button class="btn btn-alert-gray" data-cmd="cancel" class='edit_cancel' type="button" onclick="">取消</button>
		</div>
		</div>
		</div>
		</div>
		</div>


		<!-- 点击日程弹出浮动框   --  查询 -->
		<div id='calendar-quick-detail' class="popover fade auto" style="width:300px;max-width:300px;background-color:#fbfafb;position:absolute;z-index:9;padding:10px;">


		<div class="arrow"></div>
		<div class="popover-content">

		<form class="form" style="text-align:left;line-height:25px;">
		<div class="control-group" style="width:200px;">
		<h4 style="word-break: break-all;"></h4>
		<h5 style="word-break: break-all;"></h5>
		</div>

		<div class="control-group" >
		<span id="BEGIN_TIME"></span>
		<span id="TO_SPAN" style="margin:0 2px;">至</span>
		<span id="FINISH_TIME"></span>

		</div>
		</form>
		<div class="row-fluid">

		<div class="span9 cleaarfix">
		<div class="pull-left fl" style="">
		<button class="btn btn-danger btn-del-red" data-cmd="delete" style='padding:2px 3px;' type="button" id="delete">删除</button>
		</div>
		<div class="pull-right fr">
		<button class="btn btn-primary btn-alert-blue" data-cmd="detail" type="button" id="detail">详情</button>
		<button class="btn btn-primary btn-alert-blue modal-menu-test" data-cmd="edit" type="button" id="edit">编辑</button>
		<button class="btn btn-alert-gray"  data-cmd="cancel" type="button" onclick="$('#calendar-quick-detail').hide();">取消</button>
		</div>
		</div>
		</div>
		<div class="btn-group" id="statuslist" >
		<span id="current_status" ><span id="status"></span><span class="caret-down"></span></span>
		<div id="status_menu" class="attach_div small" >
		<a href="javascript:void(0);" id="no-finished" index='0' ><i class=""></i>未完成</a>
		<a href="javascript:void(0);" id="finished" index='1'><i class="icon-dropdown-checkbox"></i>已完成</a>
		</div>
		</div>
		<input type="hidden" id="calId" value='0'/>

		</div>


		</div>

		<br>



		<!-- 日程新建和编辑--->
		<form   method="post" name="form1" id="form1">
		<div id='calendar-add-update' class="modal fade auto" tabindex="-1" role="dialog" aria-labelledby="calendar-add-update" aria-hidden="true">
		<div class="modal-test"  style="">
		<div class="modal-content">
		<div class="modal-header">
		<!--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		<h4 class="modal-title" id="myModalLabel">日程/周期性事务</h4>-->
		<p class="modal-title">日程/周期性事务</p>
		<span class="modal-win-close">×</span>

		</div>
		<div class="modal-body" id="calendar_add_update">

		</div>
		<div class="modal-footer clearfix">
		<input class="modal-btn-close btn-alert-gray" type="button"
		value='关闭' />
		<input type="button" value="保存" class="modal-save btn-alert-blue" onclick="addOrUpdateCal();">&nbsp;&nbsp;

		<input type= "text" id="sid" name="sid" style="display:none;" value='0'/>
		<input type="text" id="oldMenuId" name="oldMenuId" style="display:none;"/>
		</div>
		</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->

		</div>
		</form>
		</body>

		<script type="text/javascript">
		/****
		* 新建查询、新建按钮fc-button fc-state-default
		*/
		$(document).ready(function(){

		var $newbutton=$("<span type=\"button\" class=\"fc-button fc-button-month fc-state-default modal-menu-test\" data-toggle=\"modal\" data-target=\"#calendar-add-update\" onclick=\"$(this).modal(); openAddCalendar();\">新建</span> "
		+"&nbsp;&nbsp;<span type=\"button\" class=\"fc-button fc-button-month fc-state-default\" id=\"searchButton\" onclick=\"location='query.jsp'\" >查询</span>"
		+"<span>&nbsp;&nbsp;</span>");
		$newbutton.insertBefore($(".fc-button-month"));
		//$(".fc-header-right").append($newbutton );
		});


		/*弹出框点击事件*/
		<%--$("body").on("click",".fc-event-time",function(){//显示详情框--%>
		<%--$("#calendar-quick-detail").show();--%>
		<%--});--%>
		<%--$("body").on("click",".fc-widget-content",function(){//显示添加内容框--%>
		<%--$("#calendar-quick-setup").show();--%>
		<%--});--%>

		</script>
		</html>

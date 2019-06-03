
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header.jsp" %>
<title>日程</title>
<link href='<%=contextPath %>/common/fullcalendar/fullcalendar/fullcalendar.css' rel='stylesheet' />
<link href='<%=contextPath %>/common/fullcalendar/fullcalendar/fullcalendar.print.css' rel='stylesheet' media='print' />
<script src='<%=contextPath %>/common/fullcalendar/lib/jquery.min.js'></script>
<script src='<%=contextPath %>/common/fullcalendar/lib/jquery-ui.custom.min.js'></script>
<script src='<%=contextPath %>/common/fullcalendar/fullcalendar/fullcalendar.min.js'></script>

<script type="text/javascript">

$(document).ready(function() {

	var date = new Date();
	var d = date.getDate();
	var m = date.getMonth();
	var y = date.getFullYear();
/* 	var quickCreat = $('#calendar-quick-setup').calendarPopover().data('calendarPopover');
    var quickDetail = $('#calendar-quick-detail').calendarPopover().data('calendarPopover');
    //var calendarSetup = $('#calendar-setup').modal().data('modal');
    //calendarSetup.hide();
    
    var clearView = function()
    {
        quickCreat.hide();
        quickDetail.hide();
    }; */
	
	$('#calendar').fullCalendar({
		
	
		
		header: {
			left: 'prev,next today',
			center: 'title',
			right: 'agendaWeek,month,agendaDay'
		},
		defaultView:'agendaWeek',//初始化显示视图
		editable: true,//是否可以点击
		firstDay :1,//第一天显示星期几0-星期天
		isRTL:false,//从右到左显示
		weekends:true,//是否显示周六/日
		hiddenDays:[],//不显示周几，0、1、2。。。6
		weekMode:'variable' ,//确定在月视图中显示周数。也决定了每个星期的高度,
		//'fixed':6个星期，高度一样
		//'liquid':日历将要么4 ，5或6周，视乎周month.The高度将拉伸以填充可用高度，以确定byheight ， contentHeight ，或aspectRatio 。??对比不太清楚
		//'variable':日历将具有任一4 ，5或6周，取决于month.Each周将具有相同的恒定的高度，这意味着该日历的高度将changemonth至一个月??
		weekNumbers:true,//确实周数在日历上显示
		weekNumberCalculation:'iso',//计算方法所显示的weekNumbers设置周数。您也可以指定一个函数，它接受一个单一的原生Date对象，并returnsan整周数。由于FullCalendar目前缺乏一个内置的internationlizationsystem ，这只是实现本地化的周数的方法。
		height:600,//将使整个日历（包括头）​​的像素高度,最小值
		contentHeight:600,//将日历的内容区域中的像素高度 ,最小值  ，也可初始化之后调整，方法：$('#calendar').fullCalendar('option', 'contentHeight', 650);
		aspectRatio:1.35,//确定日历的宽度与高度的长径比,也可初始化后调整，方法：$('#calendar').fullCalendar('option', 'aspectRatio', 1.8);
		handleWindowResize:true,//是否自动调整大小的日历，当浏览器窗口大小。
		viewRender:viewReaderFunc,//这个回调将得到当用户更改视图或当任何thedate导航方法被调用触发
		viewDestroy:viewDestroyFunc,//呈现的日期范围需要被推倒时触发
		dayRender:dayRenderFunc,//修改元素
		windowResize: windowResizeFunc,//触发后在日历的尺寸已经由于被调整浏览器窗口改变
		
		
		allDayText:'跨天',//全天显示顶部名称
		axisFormat:'HH',//确定将在议程的意见，垂直轴显示的时间文本
		slotMinutes:30,//垂直的频率，用于显示的时间段，以分钟为单位,默认是30
		snapMinutes:30,//鼠标移动，时间间隔，以一个拖拽的事件将捕捉到粒度，分钟单位
		defaultEventMinutes:120,//默认情况下，如果一个事件对象为没有结束，它会显示为2小时。此选项仅影响出现在议程插槽的事件，这意味着他们有全天设置为true 。
		firstHour:8,//从0点-23点，初始化显示几点
		slotEventOverlap:false,//是否重点
		
		
		selectable:true,//是否可以拖拉选择
		selectHelper:true,//是否绘制一个“占位符”事件当用户拖动
		unselectCancel: '.popover, .modal, .datepicker, .timepicker',
		select:selectFunc,//选择后触发事件
		//unselect:selectFunc,//选择后触发事件
		
		
		timeFormat: 'HH:mm' ,//显示格式类型
		buttonText:{
		    prev:     '&lsaquo;', // <
		    next:     '&rsaquo;', // >
		    prevYear: '&laquo;',  // <<
		    nextYear: '&raquo;',  // >>
		    today:    '今天',
		    month:    '月',
		    week:     '周',
		    day:      '日'
		},
		monthNames:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],//全月名
		monthNamesShort:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],//缩写
		dayNames:['星期一','星期二','星期三','星期四','星期五','星期六'],//星期
		dayNamesShort:['日','一','二','三','四','五','六'],//缩写周
		weekNumberTitle:'周 ',//显示数名称
		events: [
			{
				title: 'All Day Eventasd asda sd asd ',
				name:"aaa",
				start: new Date(y, m, 1)
			},
			{
				title: 'Long Event',
				start: new Date(y, m, d-5),
				end: new Date(y, m, d-1)
			},
			{
				id: 999,
				title: 'Repeating Event',
				start: new Date(y, m, d-3, 16, 0),
				allDay: false
			},
			{
				id: 999,
				title: 'Repeating Event',
				start: new Date(y, m, d+4, 16, 0),
			
				allDay: false
			},
			{
				title: 'Meeting',
				start: new Date(y, m, d, 10, 30),
				editable:true,//是否可以编辑
				startEditable:false,//开始时间可以可以编辑
				allDay: false
			},
			{
				title: 'Lunch',
				start: new Date(y, m, d, 12, 0),
				end: new Date(y, m, d, 14, 0),
				allDay: false
			},
			{
				title: 'Birthday Party',
				start: new Date(y, m, d+1, 19, 0),
				end: new Date(y, m, d+1, 22, 30),
				allDay: false
			},
			{
				title: 'Click for Google',
				start: new Date(y, m, 28),
				end: new Date(y, m, 29 ),
				/* visEnd:new Date(y, m, 30), 没起作用？？*/
				url: 'http://google.com/'
			}
		],eventClick: function(event) {
			//alert("dd")
	        if (event.url) {
	            //window.open(event.url);
	            
	        }
	        event.title = "CLICKED!";

	       $('#calendar').fullCalendar('updateEvent', event);//更新
	        $('#calendar').fullCalendar('removeEvents' , 'ID');//删除
	      /*   var source = {
					title: '测试',
					start: new Date(y, m, d+1, 17, 0),
					end: new Date(y, m, d+1, 18, 30),
					allDay: false
				};
	        $('#calendar').fullCalendar( 'addEventSource', source ); */

			return true;
	    },
	        dayClick: function(date, allDay, jsEvent, view) {//当用户点击某一天，空白

	            if (allDay) {
	                alert('Clicked on the entire day: '  + $.fullCalendar.formatDate( date,'yyyy-MM-dd HH:mm'));
	            }else{
	                alert('Clicked on the slot: ' + $.fullCalendar.formatDate( date,'yyyy-MM-dd HH:mm'));
	            }

	            alert('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);

	            alert('Current view: ' + view.name);
	            $('#calendar').renderEvent(jsEvent ,true);

	            // change the day's background color just for fun
	        /*     $(this).css('background-color', 'red'); */

	        }


	});
	
});

function viewReaderFunc( view, element ){
	alert("viewReaderFunc:" + view);
	//dd
	//ss
}

function viewDestroyFunc( view, element ){
	alert("viewDestroyFunc:" + view);
}
function windowResizeFunc(view){
	alert("windowResizeFunc:" +view );
}
function dayRenderFunc(date, cell ){
	//alert("dayRenderFunc:" +date +":"+ cell );
}

function getView(){
	var view = $('#calendar').fullCalendar('getView');
	alert("The view's title is " + view.title);
}


function selectFunc( startDate, endDate, allDay, jsEvent, view ){
	if (allDay) {
	    alert('select AllDay : strat:'  + $.fullCalendar.formatDate( startDate,'yyyy-MM-dd HH:mm') + " end "  + $.fullCalendar.formatDate( endDate,'yyyy-MM-dd HH:mm') );
	}else{
	    alert('select : strat:' + $.fullCalendar.formatDate( startDate,'yyyy-MM-dd HH:mm') + " end "  + $.fullCalendar.formatDate( endDate,'yyyy-MM-dd HH:mm') );
	}
	
	
}
</script>
<style>

body {
	margin-top: 40px;
	text-align: center;
	font-size: 14px;
	font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
	}

#calendar {
	width: 900px;
	margin: 0 auto;
	}

</style>
</head>
<body>
<input type="button" value="获取当前试图" onclick="getView();">
<div id='calendar'></div>

<br>
</body>
</html>

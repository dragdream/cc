

/*开始初始化日期对象*/
$(document).ready(function() {

	doInit();
	
	
	var date = new Date();
	var d = date.getDate();
	var m = date.getMonth();
	var y = date.getFullYear();
	
	
    
    window.calendar =  $('#meetfullcalendar').fullCalendar({
		header: {
			left: 'prev,next today',
			center: 'title',
			right: 'month,agendaWeek'
		},
		defaultView:'agendaWeek',//初始化显示视图
		editable: true,//是否可以点击
		firstDay :1,//第一天显示星期几0-星期天
		isRTL:false,//从右到左显示
		weekends:true,//是否显示周六/日
		//eventColor: 'red',//设置所有事件
		//eventBackgroundColor:'#378006',//背景
		//eventTextColor:'black',//字体颜色
		//eventBorderColor:'red',//边框颜色
		
		hiddenDays:[],//不显示周几，0、1、2。。。6
		weekMode:'variable' ,//确定在月视图中显示周数。也决定了每个星期的高度,
		//'fixed':6个星期，高度一样
		//'liquid':日历将要么4 ，5或6周，视乎周month.The高度将拉伸以填充可用高度，以确定byheight ， contentHeight ，或aspectRatio 。??对比不太清楚
		//'variable':日历将具有任一4 ，5或6周，取决于month.Each周将具有相同的恒定的高度，这意味着该日历的高度将changemonth至一个月??
		weekNumbers:true,//确实周数在日历上显示
		weekNumberCalculation:'iso',//计算方法所显示的weekNumbers设置周数。您也可以指定一个函数，它接受一个单一的原生Date对象，并returnsan整周数。由于FullCalendar目前缺乏一个内置的internationlizationsystem ，这只是实现本地化的周数的方法。
		height:$(window).height()-$("#meetfullcalendar").offset().top-15,//将使整个日历（包括头）​​的像素高度,最小值
		contentHeight:$(window).height()-$("#meetfullcalendar").offset().top-15,//将日历的内容区域中的像素高度 ,最小值  ，也可初始化之后调整，方法：$('#calendar').fullCalendar('option', 'contentHeight', 650);
		aspectRatio:1.35,//确定日历的宽度与高度的长径比,也可初始化后调整，方法：$('#calendar').fullCalendar('option', 'aspectRatio', 1.8);
		handleWindowResize:true,//是否自动调整大小的日历，当浏览器窗口大小。
		viewRender:viewReaderFunc,//这个回调将得到当用户更改视图或当任何thedate导航方法被调用触发
		viewDestroy:function ( view, element ){
			//alert("viewDestroyFunc:" + view);
		//	clearView();

		},//呈现的日期范围需要被推倒时触发
		dayRender:dayRenderFunc,//修改元素
		windowResize: windowResizeFunc,//触发后在日历的尺寸已经由于被调整浏览器窗口改变
		allDaySlot:false,//是否显示全天
		//allDayText:'跨天',//全天显示顶部名称
		axisFormat:'HH',//确定将在议程的意见，垂直轴显示的时间文本
	    titleFormat: { 
               month: 'yyyy年MM月',
              // week: "yyyy年MM月d日{ '&#8212;' [ yyyy年][MM月]d日}",
               week: "yyyy年MM月 第 {[W} 周",
               day: 'yyyy年MM月d日, dddd'
           },//提起title格式
           columnFormat:{
        	   month: 'ddd', // Mon
        	   week: 'M/d ddd', // Mon 9/7
        	   day: 'dddd M/d' // Monday 9/7
        	   },//列标题显示
		slotMinutes:30,//垂直的频率，用于显示的时间段，以分钟为单位,默认是30
		snapMinutes:30,//鼠标移动，时间间隔，以一个拖拽的事件将捕捉到粒度，分钟单位
		defaultEventMinutes:120,//默认情况下，如果一个事件对象为没有结束，它会显示为2小时。此选项仅影响出现在议程插槽的事件，这意味着他们有全天设置为true 。
		firstHour:8,//从0点-23点，初始化显示几点
		slotEventOverlap:false,//是否重叠
		/* dragOpacity: {//设置拖动时事件的透明度     
			agenda: .5,  
			'':.6   
			},  */
		selectable:true,//是否可以拖拉选择
		selectHelper:true,//是否绘制一个“占位符”事件当用户拖动
		unselectCancel: '.popover, .modal, .datepicker, .timepicker',
		timeFormat: 'HH:mm' ,//显示格式类型
		buttonText:{
		    prev:     '&lsaquo;', // <
		    next:     '&rsaquo;', // >
		    prevYear: '&laquo;',  // <<
		    nextYear: '&raquo;',  // >>
		    today:    '本周',
		    month:    '月',
		    week:     '周',
		    day:      '日'
		},
		monthNames:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],//全月名
		monthNamesShort:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],//缩写
		dayNames:['星期一','星期二','星期三','星期四','星期五','星期六'],//星期
		dayNamesShort:['周日','周一','周二','周三','周四','周五','周六'],//缩写周
		weekNumberTitle:'',//显示周数名称
		select:function selectFunc( startDate, endDate, allDay, jsEvent, view ){
			if (allDay) {
			   // alert('select AllDay : strat:'  + $.fullCalendar.formatDate( startDate,'yyyy-MM-dd HH:mm') + " end "  + $.fullCalendar.formatDate( endDate,'yyyy-MM-dd HH:mm') );
			}else{
			   // alert('select : strat:' + $.fullCalendar.formatDate( startDate,'yyyy-MM-dd HH:mm') + " end "  + $.fullCalendar.formatDate( endDate,'yyyy-MM-dd HH:mm') );
			}
		    var startStr = $.fullCalendar.formatDate( startDate,'yyyy-MM-dd HH:mm');
		    var endStr =  $.fullCalendar.formatDate( endDate,'yyyy-MM-dd HH:mm');
		    var meeting = {sid:0 , startTime : startStr , endTime:endStr };
		    toAddOrUpdateMeeting( jsEvent  , view , meeting , calendar);
		},//不选择后触发事件
		unselect: function()
        {
          //  clearView();
        },//未选择后触发事件
		
		events: function(start, end, callback) {//json，从后台读取
		//	alert(start)
			var meetRoomSid = $("#meetRoomSid").val();
			var startTimeStr = $.fullCalendar.formatDate( start,'yyyy-MM-dd' );	//开始时间
			var endTimeStr = $.fullCalendar.formatDate( end,'yyyy-MM-dd' );//结束时间
			var url =  contextPath +  "/meetManage/getPersonalAllMeet.action";
			var para = {startTimeStr:startTimeStr,endTimeStr:endTimeStr,meetRoomId:meetRoomSid};
			var jsonObj = tools.requestJsonRs(url,para);
			//alert(jsonObj.rtState);
			if(jsonObj.rtState){
				var json = jsonObj.rtData;
				var dataArray = new Array();
				callback(json);
			}else{
				alert(jsonObj.rtMsrg);
			}
		}
		,eventClick: function(calEvent, jsEvent, view){
			  	var begin_date = calEvent['start'];//开始时间
		        var end_date = calEvent['end'];//结束时间
		        var cal_id = calEvent['id'];//Id

		        toMeetingDetail(calEvent , calendar);
    
	    },
	    dayClick: function(date, allDay, jsEvent, view) {//当用户点击某一天，空白

	     },
        eventDragStart: function(event, jsEvent, ui, view)
        {
        	//alert("eventDragStartFunc");//拖曳事件开始时触发
           // clearView();
        },
        eventDragStop: function(event)
        {
          // clearView();
        	//alert("eventDragStopFunc");////拖曳事件停止时触发，此回调是保证被触发用户拖动一个事件后，即使该事件不会改变日期/时间。它被触发之前的event'sinformation已被修改（如果移动到一个新的日期/时间）和theeventDrop回调之前是triggere
        },
        eventDrop: function(event)
        {
           // clearView();
            updateChangeCal(event.id , event.start, event.end);
            //alert("eventDropFunc:拖动改变");//拖动停止时触发的事件已经转移到一个不同的日期/时间
		
        },
        eventResizeStart: function(event)
        {
          //  alert("eventResizeStartFunc");//调整大小事件开始时触发
            //log('rs', this, arguments);
        },
        eventResizeStop : function(event)
        {
           // alert("eventResizeStartFunc");//调整大小事件结束时触发
            //log('rs', this, arguments);
        },
        eventResize: function()
        {
        	// alert("eventResizeFunc:拉动改变");//触发调整大小时会停止，事件持续时间已经改变
        	updateChangeCal(arguments[0]['id'] ,  arguments[0]['start'], arguments[0]['end']);	
        }
	});

  /* 移动会议室 */
    replaceMeetRoom();
});


/**
 * 查看会议
 * @param calEvent - 对象
 * @param 
 */
function toMeetingDetail(calEvents , view, calendar){
	var sid = calEvents['id'];
	var deleteable = calEvents['deleteable'];
	var editable = calEvents['editable'];
	var opts = new Array();
	if(editable){
		opts.push({name:"修改",classStyle:"btn-alert-blue"});
	}
	if(deleteable){
		opts.push({name:"删除",classStyle:"btn-del-red"});
	}
	opts.push({name:"关闭",classStyle:"btn-alert-gray"});
	var url = contextPath + "/system/core/base/meeting/personal/meetingdetail.jsp?id=" + sid ;
	bsWindow(url ,"查看会议详情",{width:"650",height:"400",buttons:opts
		
	,submit:function(v,h,f,d){
		var cw = h[0].contentWindow;
		if(v=="修改"){
			toAddOrUpdateMeeting(calEvents , view , calEvents ,calendar);
		}else if(v == "删除"){
			var isClose = false;
			//var submit = function (v, h, f) {
			var submit = function () {
			   // if (v == 'ok'){
			    	isClose = deleteCal(sid , calendar);
			    	//if(isClose){
			    		//window.BSWINDOW.modal("hide");
			    		//getCalendar();
			    		d.remove();
			    	//}
			    	isClose =  true;
			    //}
			    isClose =  true; //close
			};
			//$.jBox.confirm("确定要删除吗？", "提示", submit);
			$.MsgBox.Confirm("提示", "确定删除所选记录,删除后将不可恢复！", submit);
			//return isClose;
		}else if(v=="关闭"){
            window.location.reload();
			return true;
		}
	}});
	
}

/**
 *  新建会议
 * @param jsEvent  对象
 * @param view  视图
 * @param meeting  meet对象
 * @param calendar fullCalendar 对象
 * @returns
 */
function toAddOrUpdateMeeting(jsEvent , view , meeting  ,calendar){
	var meetingRoomId = $("#meetRoomSid").val();
	if(meetingRoomId == ""){
		//$.jBox.tip("请选择会议室！");
		$.MsgBox.Alert_auto("请选择会议室！");
		return ;
	}
	var id = meeting.id;
	var startTime  = "";
	var endTime = "";
	if(!id){
		 startTime = meeting.startTime;
		 endTime = meeting.endTime;
	}
	
	
	var meetDate = startTime.substring(0,10);
	startTime = startTime.substring(11,startTime.length);
	endTime = endTime.substring(11,endTime.length);
	var url = contextPath + "/system/core/base/meeting/personal/apply/addOrUpdate.jsp?id=" + id  + "&meetRoomId=" + meetingRoomId + "&meetDate=" + meetDate + "&startTime=" + startTime + "&endTime=" + endTime;
	var title = "会议申请";

	if(id > 0){
		title = "编辑会议";
	}
	bsWindow(url ,title,{width:"800",height:"400",buttons:
		[
		 {name:"保存",classStyle:"modal-save btn-alert-blue"},
	 	 {name:"关闭",classStyle:"modal-btn-close btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			var isOk = cw.doSaveOrUpdate(function(){
				//$.jBox.tip("保存成功！", "info", {timeout: 1800});
				//$.MsgBox.Alert_auto("保存成功！");
	    		//BSWINDOW.modal("hide");
				window.parent.location.reload();
	    		onchangeMeetRoom(0);
			});
		
		//	window.location.reload();
			//return isOk;
		}else if(v=="关闭"){
			return true;
		}
	}});
}

//*** 测试 ***/
/**
 * 切换视图事件
 */
function viewReaderFunc( view, element ){
	var today ="本周";
	$("body").css("overflow-y","hidden");
	if(view.name == "agendaDay"){
		today= "今天";
		//today = right: 'month,agendaWeek,agendaDay'
	}else if(view.name == "agendaWeek"){
		today= "本周";
	}else if(view.name == "month"){
		today= "本月";
		$("body").css("overflow-y","auto");
	}
	$(".fc-button-today").html(today);
}
/**
 * 改变时触发事件
 */
function windowResizeFunc(view){
	//alert("windowResizeFunc:" +view );
}
function dayRenderFunc(date, cell ){
	//alert("dayRenderFunc:" +date +":"+ cell );
}

/****结束****/
/**
 * 拖动获取拖拉改变日程，更改数据库
 * @param id
 * @param start
 * @param end
 * @returns
 */
function updateChangeCal( id, start , end){
	//alert(start.getTime())
	/*start = $.fullCalendar.formatDate( start,'yyyy-MM-dd HH:mm');
	end = $.fullCalendar.formatDate( end,'yyyy-MM-dd HH:mm');
	*/
	var url =  contextPath +  "/meetManage/updateChangeMeet.action";
	var para = {startTime:start.getTime(),endTime:end.getTime()  ,sid:id};
	var jsonObj = tools.requestJsonRs(url,para);
	if(jsonObj.rtState){
		var json = jsonObj.rtData;
		return json;
	
		//callback(json);
	}else{
		//alert(jsonObj.rtMsg);
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
		onchangeMeetRoom(0);
	}
	
}


/**
 * 删除 byId
 * @param id
 */
function deleteCal(id ,calendar){
	var url = contextPath + "/meetManage/deleteById.action";
	var jsonRs = tools.requestJsonRs(url,{sid:id});
	if(jsonRs.rtState){
		//alert(jsonRs.rtMsg);
		 //$.jBox.tip('删除成功！');
		 $.MsgBox.Alert_auto("删除成功！");
		 if(calendar){
			 calendar.fullCalendar('removeEvents' ,id);//删除节点
		 }else{
			 window.calendar.fullCalendar('removeEvents' ,id);//删除节点
		 }
		
		 //window.BSWINDOW.modal("hide");
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
}



/**
 * 改变会议室
 */
function onchangeMeetRoom(roomId){
	//alert(roomId);
    window.calendar.fullCalendar('refetchEvents') ;//从所有源并且重新渲染到屏幕上
}



/*开始初始化日期对象*/
$(document).ready(function() {

	doInit();
	var date = new Date();
	var d = date.getDate();
	var m = date.getMonth();
	var y = date.getFullYear();
    window.calendar =  $('#vehiclefullcalendar').fullCalendar({
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
		height:$(window).height()-$("#vehiclefullcalendar").offset().top-15,//将使整个日历（包括头）​​的像素高度,最小值
		contentHeight:$(window).height()-$("#vehiclefullcalendar").offset().top-15,//将日历的内容区域中的像素高度 ,最小值  ，也可初始化之后调整，方法：$('#calendar').fullCalendar('option', 'contentHeight', 650);
		aspectRatio:1.35,//确定日历的宽度与高度的长径比,也可初始化后调整，方法：$('#calendar').fullCalendar('option', 'aspectRatio', 1.8);
		handleWindowResize:true,//是否自动调整大小的日历，当浏览器窗口大小。
		viewRender:viewReaderFunc,//这个回调将得到当用户更改视图或当任何thedate导航方法被调用触发
		viewDestroy:function ( view, element ){
			//alert("viewDestroyFunc:" + view);
		//	clearView();

		},//呈现的日期范围需要被推倒时触发
		dayRender:dayRenderFunc,//修改元素
		windowResize: windowResizeFunc,//触发后在日历的尺寸已经由于被调整浏览器窗口改变
		allDaySlot:true,//是否显示全天
		allDayText:'跨天',//全天显示顶部名称
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
		    var timeObj = {sid:0 , startTime : startStr , endTime:endStr };
		    toAddOrUpdateInfo( jsEvent  , view , timeObj , calendar);
		},//不选择后触发事件
		unselect: function()
        {
          //  clearView();
        },//未选择后触发事件
		
		events: function(start, end, callback) {//加载页面调用，json，从后台读取
		//	alert(start)
			var vehicleId = $("#vehicleId").val();
			var startTimeStr = $.fullCalendar.formatDate( start,'yyyy-MM-dd' );	//开始时间
			var endTimeStr = $.fullCalendar.formatDate( end,'yyyy-MM-dd' );//结束时间
			//alert(startTimeStr + ">>" + endTimeStr);
			var url =  contextPath +  "/vehicleUsageManage/getAllVelicleUsage.action";
			var para = {vuStartStr:startTimeStr,vuEndStr:endTimeStr,vehicleId:vehicleId};
			var jsonObj = tools.requestJsonRs(url,para);
			if(jsonObj.rtState){
				var json = jsonObj.rtData;
				var dataArray = new Array();
				callback(json);
			}else{
				//alert(jsonObj.rtMsrg);
				$.MsgBox.Alert_auto(jsonObj.rtMsrg);
			}
		}
		,eventClick: function(calEvent, jsEvent, view){//单击事件
			      var begin_date = calEvent['start'];//开始时间
		        var end_date = calEvent['end'];//结束时间
		        var cal_id = calEvent['id'];//Id
		        //alert(cal_id);
		        applyVehicleDetail(calEvent , calendar);//详情页面
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
           // alert("eventDropFunc:拖动改变");//拖动停止时触发的事件已经转移到一个不同的日期/时间
		
        },
        eventResizeStart: function(event)
        {
          //  alert("eventResizeStartFunc");//调整大小事件开始时触发
            //log('rs', this, arguments);
        },
        eventResizeStop : function(event)
        {
            //alert("eventResizeStartFunc");//调整大小事件结束时触发
            //log('rs', this, arguments);
        },
        eventResize: function()
        {
        	// alert("eventResizeFunc:拉动改变");//触发调整大小时会停止，事件持续时间已经改变
        	updateChangeCal(arguments[0]['id'] ,  arguments[0]['start'], arguments[0]['end']);	
        }
	});

  /* 移动车辆 */
    replaceVehicle();
});


/**
 * 查看车辆
 * @param calEvent - 对象
 * @param 
 */
function applyVehicleDetail(calEvents , view, calendar){
	var sid = calEvents['id'];
	var deleteable = calEvents['deleteable'];
	var editable = calEvents['editable'];
	//alert(editable);
	var opts = new Array();
	if(editable){
		opts.push({name:"修改",classStyle:"btn-alert-blue"});
	}
	if(deleteable){
		opts.push({name:"删除",classStyle:"btn-del-red"});
	}
	opts.push({name:"关闭",classStyle:"btn-alert-gray"});
	var url = contextPath + "/system/core/base/vehicle/personal/applyVehicleDetail.jsp?id=" + sid ;
	bsWindow(url ,"查看车辆申请详情",{width:"750px",height:"360px",buttons:opts
		
	,submit:function(v,h,f,d){
		var cw = h[0].contentWindow;
		if(v=="修改"){
		  var timeObj = {sid:sid, startTime : "" , endTime:"" };
			toAddOrUpdateInfo(calEvents , view , timeObj ,calendar);
		}else if(v == "删除"){
			var isClose = false;
			var submit = function () {
			 
			    	isClose = deleteCal(sid , calendar);
			    	//if(isClose){
			    		//window.BSWINDOW.modal("hide");
			    		//getCalendar();
			    		d.remove();
			    	//}
			    	isClose =  true;
			    }
			    isClose =  true; //close
		
			//$.jBox.confirm("确定要删除吗？", "提示", submit);
			 $.MsgBox.Confirm ("提示", "确定要删除吗？",submit);
		}else if(v=="关闭"){
			return true;
		}
	}});
	
}

/**
 *  新建车辆申请
 * @param jsEvent  对象
 * @param view  视图
 * @param meeting  meet对象
 * @param calendar fullCalendar 对象
 * @returns
 */
function toAddOrUpdateInfo(jsEvent , view , timeObj  ,calendar){
	var vehicleSid = $("#vehicleId").val();
	if(vehicleSid == ""){
		$.MsgBox.Alert_auto("请选择车辆！");
		$("#vehicleId").focus();
		return ;
	}
	var id = timeObj.sid;
	var startTime  = timeObj.startTime;
	var endTime = timeObj.endTime;
	var url = contextPath + "/system/core/base/vehicle/personal/applyVehicle.jsp?sid=" + id  + "&vehicleSid=" + vehicleSid + "&startTime=" + startTime + "&endTime=" + endTime;
	var title = "车辆申请";
	if(id > 0){
		title = "编辑车辆申请";
	}
	bsWindow(url ,title,{width:"850",height:"360",buttons:
		[
		 {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			cw.doSaveOrUpdate(function(){
				$.MsgBox.Alert_auto("保存成功！");
				window.location.reload();
			});
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
	start = $.fullCalendar.formatDate( start,'yyyy-MM-dd HH:mm');
	end = $.fullCalendar.formatDate( end,'yyyy-MM-dd HH:mm');
	
	var url =  contextPath +  "/vehicleUsageManage/updateChangeVehicle.action";
	var para = {vuStartStr:start,vuEndStr:end  ,sid:id};
	var jsonObj = tools.requestJsonRs(url,para);
	if(jsonObj.rtState){
		var json = jsonObj.rtData;
		return json;
		//callback(json);
	}else{
		alert(jsonObj.rtMsg);
	}
	
}


/**
 * 删除 byId
 * @param id
 */
function deleteCal(id ,calendar){
	var url = contextPath + "/vehicleUsageManage/deleteById.action";
	var jsonRs = tools.requestJsonRs(url,{sid:id});
	if(jsonRs.rtState){
		//alert(jsonRs.rtMsg);
		 $.MsgBox.Alert_auto("删除成功！");
		 if(calendar){
			 calendar.fullCalendar('removeEvents' ,id);//删除节点
		 }else{
			 window.calendar.fullCalendar('removeEvents' ,id);//删除节点
		 }
		
		 //window.BSWINDOW.modal("hide");
		 return true;
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
}



/**
 * 改变车辆
 */
function onchangeMeetRoom(roomId){
	//alert(roomId);
    window.calendar.fullCalendar('refetchEvents') ;//从所有源并且重新渲染到屏幕上
}

(function($){
    
    $.fn.calendarPopover = function(opts)
    {
        return this.each(function(){
            $(this).data('calendarPopover', new CalendarPopover( $(this), opts ));
        });
    };
    $.fn.calendarPopover.defaults = {
        title: '',
        container: window,
        content: ''
    };
    var CalendarPopover = function()
    {
        this.init.apply(this, arguments);
    };

    CalendarPopover.prototype.init = function(el, cfg)
    {
        this.$el = el;
        var opts = this.options = $.extend(true, {}, $.fn.calendarPopover.defaults, cfg);
        this.$container = $(opts.container);
        this.$content = this.$el.find('.popover-content');
        opts.content && this.setContent(opts.content);
        this.bindEvent();
    };    
    CalendarPopover.prototype.bindEvent = function()
    {
        var self = this,
            $el = this.$el,
            opts = this.options;
        $el.on('click', '[data-cmd]', function()
        {
            var act = this.getAttribute('data-cmd'),
                cb = opts.callbacks || {};
            if( act && cb[act] && cb[act]() == false )
            {
                return false;
            };
            self.hide();
        });        
    };
    CalendarPopover.prototype.setCallbacks = function(callbacks)
    {
        this.options.callbacks = callbacks;
    };
    CalendarPopover.prototype.show = function($target)
    {
        var $pop = this.$el,
            $container = this.$container,
            cWidth = $container.width(),
            cHeight = $container.height(),
            tWidth = $target.width(),
            pWidth = $pop.width(),
            pos = $target.offset(),
            arr = ['top','bottom','left','right'],
            dirc = 'left';
 /*
        console && console.dir({
            target: $target[0],
            'pos.left': pos.left,
            tWidth: tWidth,
            pWidth: pWidth,
            cWidth: cWidth,
            left: pos.left + tWidth + pWidth > cWidth - 100,
            right: pos.left + tWidth + pWidth < cWidth - 100,
            h : $pop.height(),
            top: pos.top
        })*/
        if(pos.left + tWidth + pWidth < cWidth)
        {
            $pop.css({
                top: pos.top + ( $target.height() - $pop.height() )/2,
                left: pos.left + tWidth + 5
            });
            dirc = 'right';
        }
        else if((pos.left + tWidth + pWidth > cWidth) && (pos.left > pWidth) )
        {
            $pop.css({
                top:  pos.top + ( $target.height() - $pop.height() )/2 - 5,
                left: pos.left - pWidth - 5
            })
            dirc = 'left';
        }
		else if(pos.top < $pop.height())
		{
			$pop.css({
                top:  pos.top + $target.height(),
                left: pos.left + ( tWidth - pWidth )/2
            })
            dirc = 'bottom';
        }
		else
		{
			$pop.css({
                top:  pos.top - $target.height(),
                left: pos.left + ( tWidth - pWidth )/2
            })
            dirc = 'top';
        }
        
        $pop.removeClass(arr.join(' ')).addClass(dirc + ' in show');
    };
    CalendarPopover.prototype.setContent = function(c)
    {
        this.$content.html(c);
    };
    CalendarPopover.prototype.hide = function()
    {
        this.$el.removeClass('in show');
    };
})(jQuery); 

/*开始初始化日期对象*/
$(document).ready(function() {

	var date = new Date();
	var d = date.getDate();
	var m = date.getMonth();
	var y = date.getFullYear();
	
	//快速新建
	var quickCreat = $('#calendar-quick-setup').calendarPopover().data('calendarPopover');
	
	//快速点击
    var quickDetail = $('#calendar-quick-detail').calendarPopover().data('calendarPopover');
    //var calendarSetup = $('#calendar-setup').modal().data('modal');
    //calendarSetup.hide();
   // alert(quickDetail +":quickDetail");
    //隐藏对象
    var clearView = function()
    {
        quickCreat.hide();
        quickDetail.hide();
    }; 
    
    window.calendar =  $('#calendar').fullCalendar({
		header: {
			left: 'prev,next today',
			center: 'title',
			right: 'month,agendaWeek,agendaDay'
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
		weekMode:'liquid' ,//确定在月视图中显示周数。也决定了每个星期的高度,
		//'fixed':6个星期，高度一样
		//'liquid':日历将要么4 ，5或6周，视乎周month.The高度将拉伸以填充可用高度，以确定byheight ， contentHeight ，或aspectRatio 。??对比不太清楚
		//'variable':日历将具有任一4 ，5或6周，取决于month.Each周将具有相同的恒定的高度，这意味着该日历的高度将changemonth至一个月??
		weekNumbers:true,//确实周数在日历上显示
		weekNumberCalculation:'iso',//计算方法所显示的weekNumbers设置周数。您也可以指定一个函数，它接受一个单一的原生Date对象，并returnsan整周数。由于FullCalendar目前缺乏一个内置的internationlizationsystem ，这只是实现本地化的周数的方法。
		height:$(window).height()-$("#calendar").offset().top-45,//将使整个日历（包括头）​​的像素高度,最小值
		contentHeight:$(window).height()-$("#calendar").offset().top-45,//将日历的内容区域中的像素高度 ,最小值  ，也可初始化之后调整，方法：$('#calendar').fullCalendar('option', 'contentHeight', 650);
		aspectRatio:1.35,//确定日历的宽度与高度的长径比,也可初始化后调整，方法：$('#calendar').fullCalendar('option', 'aspectRatio', 1.8);
		handleWindowResize:true,//是否自动调整大小的日历，当浏览器窗口大小。
		viewRender:viewReaderFunc,//这个回调将得到当用户更改视图或当任何thedate导航方法被调用触发
		viewDestroy:function ( view, element ){
			//alert("viewDestroyFunc:" + view.name);
			clearView();

		},//呈现的日期范围需要被推倒时触发
		dayRender:dayRenderFunc,//修改元素
		windowResize: windowResizeFunc,//触发后在日历的尺寸已经由于被调整浏览器窗口改变
		allDaySlot:true,//是否显示全天
		allDayText:'跨天',//全天显示顶部名称
		axisFormat:'HH',//确定将在议程的意见，垂直轴显示的时间文本
	    titleFormat: { 
               month: 'yyyy年MM月',
               week: "yyyy年MM月d日{ '&#8212;' [ yyyy年][MM月]d日}",
               week: "yyyy年MM月 第 {[W} 周",
               day: 'yyyy年MM月d日, dddd'
           },//提起title格式
           columnFormat:{
        	   month: 'ddd', // Mon
        	   week: 'M/d ddd', // Mon 9/7
        	   day: 'M/d dddd' // Monday 9/7
        	   },//列标题显示
		slotMinutes:30,//垂直的频率，用于显示的时间段，以分钟为单位,默认是30
		snapMinutes:30,//鼠标移动，时间间隔，以一个拖拽的事件将捕捉到粒度，分钟单位
		defaultEventMinutes:120,//默认情况下，如果一个事件对象为没有结束，它会显示为2小时。此选项仅影响出现在议程插槽的事件，这意味着他们有全天设置为true 。
		firstHour:8,//从0点-23点，初始化显示几点
		slotEventOverlap:false,//是否重点
		/* dragOpacity: {//设置拖动时事件的透明度     
			agenda: .5,  
			'':.6   
			},  */
		selectable:true,//是否可以拖拉选择
		selectHelper:true,//是否绘制一个“占位符”事件当用户拖动
		unselectCancel: '.popover, .modal, .datepicker, .timepicker',
		select:function selectFunc( startDate, endDate, allDay, jsEvent, view ){
			if (allDay) {
			   // alert('select AllDay : strat:'  + $.fullCalendar.formatDate( startDate,'yyyy-MM-dd HH:mm') + " end "  + $.fullCalendar.formatDate( endDate,'yyyy-MM-dd HH:mm') );
			}else{
			   // alert('select : strat:' + $.fullCalendar.formatDate( startDate,'yyyy-MM-dd HH:mm') + " end "  + $.fullCalendar.formatDate( endDate,'yyyy-MM-dd HH:mm') );
			}
			quickDetail.hide();
			var title = '',
		    $target;   
			
		    if($(jsEvent.target).is('.fc-select-helper'))
		    {
		    	$target = $(jsEvent.target);
		    }
		    else if($(this.element).find('.fc-select-helper').first().size())
		    {
		        $target = $(this.element).find('.fc-select-helper').first();
		    } 
		    else	
		    {
		    	$target = $(jsEvent.target);
		    }
		    quickCreat.show($target);
		    $('#quick-calendar-title').val('');   //清空填写内容
		    $('#quick-calendar-title').focus();//光标移至填写内容
		    //$('#quick-calendar-title').val(title);
		    //$('#quick-calendar-title').val(originalTitle);
		    var startStr = $.fullCalendar.formatDate( startDate,'yyyy-MM-dd HH:mm');
		    var start_new = startStr.split(" ") ;
		    var endStr =  $.fullCalendar.formatDate( endDate,'yyyy-MM-dd HH:mm');
		    var end_new =  endStr.split(" ");
		    var show_date_type = arguments[2] ? '1' : '0';
		   /* alert(arguments[2] == true)*/
		    if(arguments[2] == true)//点歌点击日期（月、周（跨天）、日（跨天））
		    {
		        $('#quick-begin-time').html(startStr);
		        if(startStr == endStr)
		        {
		            $('#quick-finish-time').html(end_new[0] + " 23:59");
		            $('#quick-name').html("至");
		        }
		        else
		        {
		            $('#quick-name').html("至");
		            $('#quick-finish-time').html(end_new[0] + " 23:59" );
		        }
		    }
		    else
		    {
		        $('#quick-begin-time').html(startStr);
		        $('#quick-name').html("至");
		        $('#quick-finish-time').html(endStr);
		    }
		    
		    quickCreat.setCallbacks({
                ok: function()
                {
                    var newTitle = $('#quick-calendar-title').val();
                    if(newTitle=="")
                    {
                        alert("事务内容不能为空");
                        return false;
                    }
                    else
                    {          	
                    	var data = addCal('' , '' , '' ,  '');
                    	 if(data.length> 0 ){
        					 for(var i = 0;i<data.length;i++){
        							calendar.fullCalendar('renderEvent', data[i] ); // 等同于 TeeCalendar.renderEvent(tt ,true); 
        					 }
                    	 }
              
                    }
                },
                cancel: function()
                {
                	calendar.fullCalendar( 'unselect' ); //点击取消按钮触发                    
                },
                editmore: function()
                {
                	//$.jBox("get:" + contextPath + "/system/core/base/calendar/addOrUpdate.jsp","新建日程",500,400);
                   // $.jBox(addUpdateCalFrom,{title:"新建日程",width:500,height:370,buttons: {}});
	               	 $("#calendar_add_update").empty();
	            	 $("#calendar_add_update").append(addUpdateCalFrom);
	            	 $('#calendar-add-update').modal('show');
                	setRemindTimePopover();
                	$("#START_DATE").val(startStr);
                	$("#END_DATE").val( $('#quick-finish-time').html());
                    $("#CAL_CONTENT").val($('#quick-calendar-title').val());
                   // $("#myModal").modal('show');
                }
            });
	

		},//不选择后触发事件
		unselect: function()
        {
            clearView();
        },//未选择后触发事件
		
		
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
		dayNames:['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],//星期
		dayNamesShort:['周日','周一','周二','周三','周四','周五','周六'],//缩写周
		weekNumberTitle:'',//显示数名称
		events: function(start, end, callback) {//json，从后台读取
			var startTimeStr = $.fullCalendar.formatDate( start,'yyyy-MM-dd' );	//开始时间
			var endTimeStr = $.fullCalendar.formatDate( end,'yyyy-MM-dd' );//结束时间
			var url =  contextPath +  "/calendarManage/selectByTime.action";
			var para = {startTime:startTimeStr,endTime:endTimeStr};
			var jsonObj = tools.requestJsonRs(url,para);
			//alert(jsonObj.rtState);
			if(jsonObj.rtState){
				var json = jsonObj.rtData;
				//var dataArray = new Array();
				callback(json);
				
			}else{
				alert(jsonObj.rtMsrg);
			}
		}
		,eventClick: function(calEvent, jsEvent, view){
			  	var begin_date = calEvent['start'];//开始时间
		        var end_date = calEvent['end'];//结束时间
		        var cal_id = calEvent['id'];//Id
		        var calAffairType = calEvent['type'];//类型
		        var status =  calEvent['status'];//办理状态 0-未完成 1-已完成 2-已超时
		        var editable = calEvent.editable;
		        var begin_date1 = $.fullCalendar.formatDate(begin_date,'yyyy-MM-dd HH:mm');
		        var end_date1 = $.fullCalendar.formatDate( end_date , 'yyyy-MM-dd HH:mm');
	         	if(calAffairType == 'affair'){
	         		begin_date1 = calEvent['startTime'].substring(0,16);
	         		end_date1 =  calEvent['endTime'].substring(0,16);
		        }

		        $("#calendar-quick-detail h5").text(calEvent['title']);   
		  
		        $("#BEGIN_TIME").text(begin_date1);
		        if(end_date1){
		            $('#TO_SPAN').show();
		            $("#FINISH_TIME").show().text(end_date1); 
		        }else{
		            $('#TO_SPAN').hide();                    
		            $("#FINISH_TIME").hide().text(end_date1); 
		        }
		      /*  calEvent.urls=="" ? $("#detail").hide() : $("#detail").show();*/
		        $("#calId").val(cal_id);
		        if(calAffairType=="calendar" && calEvent.deleteable) 
		        {
		        	$("#calendar-quick-detail h4").text('');
		            $("#statuslist").show();
		           // $("#edit-id").val(cal_id);     
		            if(status=="0" || status=="2" )
		            {
		            	if(status=="2"){
		            		$("#status").text("已超时");
		            	}else{
		            		$("#status").text("未完成");
		            	}
		                
		                $("#no-finished > i").addClass("icon-dropdown-checkbox icon-dropdown-checkbox-checked");
		                $("#finished > i").removeClass();
		            }
		            else
		            {
		                $("#status").text("已完成");
		                $("#finished > i").addClass("icon-dropdown-checkbox icon-dropdown-checkbox-checked");
		                $("#no-finished > i").removeClass();
		            }  
		        }
		        else
		        {
		            $("#statuslist").hide();
		            
		            $("#calendar-quick-detail h4").text( calEvent['remindTypeDesc']);
		        }
		        if(calEvent.deleteable)//是否允许删除
		        {
		            $("#delete").show();
		            $("#edit").show();
		        }
		        else
		        {
		        	$("#calendar-quick-detail h4").text('');
		            $("#delete").hide();
		            $("#edit").hide();
		        }                            
		        var $event = $(this);    
		        quickDetail.show($event);    
		        quickDetail.setCallbacks({
		            edit: function()
		            { 
		            	  var title = "日程";
		            	  if(calAffairType == 'affair'){
		            		  title = "周期性事务";
		            	  }
		            	  
		                 // $.jBox(addUpdateCalFrom,{title:"编辑"+ title,width:500,height:400,buttons: {}});
		            	 $("#calendar_add_update").empty();
		            	 $("#calendar_add_update").append(addUpdateCalFrom);
		            	 $('#calendar-add-update').modal('show');
		            	 
		            	  setRemindTimePopover();//
		            	  var calendarObj = getCalById(cal_id);//获取日程
		            
		                  $("#CAL_CONTENT").val($("#calendar-quick-detail h5").text());
		                  if(calendarObj){
		                      $("#START_DATE").val(calendarObj.startTimeStr);
			                  $("#END_DATE").val(calendarObj.endTimeStr);
		                	  $("#calLevel").val(calendarObj.calLevel);
		                	  if(calendarObj.calLevel && calendarObj.calLevel != 0){
		                		  $("#cal_color").attr("class","color" + calendarObj.calLevel);
		                	  }
		                	 // alert(calendarObj.isWeekend )
		                	  if(calendarObj.isWeekend == 1){
		                		  $("#isWeekend")[0].checked = true;
		                	  }
		                	  $("#calType").val(calendarObj.calType);//工作类型
		                	  $("#actorIds").val(calendarObj.actorIds);//参与者
		                	  $("#actorNames").val(calendarObj.actorNames);
		                	  if(calendarObj.calAffType == 1){//周期性事务
		                		  $("#calAffType")[0].checked = true;
		                		  $("#remindType").val(calendarObj.remindType);
		                		  setCalAffType($("#calAffType")[0]);
		                		/*  if(calendarObj.remindType == 3 || calendarObj.remindType == 4 || calendarObj.remindType == 5){//周、月、年
		          					  $("#remindTime" + calendarObj.remindType).val(calendarObj.remindTimeStr);
		          				  }else{*/
		          					  $("#remindTime").val(calendarObj.remindTimeStr);
		          				  //}
		          				  sel_change(calendarObj.remindType);
		          				  if(calendarObj.remindType == 3){
		          					  $("#remindDate3").val(calendarObj.remindDate);
		          				  }else if(calendarObj.remindType == 4){
		          					  $("#remindDate4" ).val(calendarObj.remindDate);
		          				  }else if(calendarObj.remindType == 5){
		          					  var remindDateArray = calendarObj.remindDate.split("-");
		          					  $("#remindDate5Mon").val(remindDateArray[0]);
		          					  $("#remindDate5Day").val(remindDateArray[1]);
		          				  } 
		                	  }
		                  }
		                  $("#sid").val(cal_id);
		            },
		            cancel: function()
		            {   
		                clearView();                     
		            },
		            'delete': function()
		            {
		                if( confirm('删除后无法恢复，确认删除？'))
		                {
		                	deleteCal(cal_id ,calendar );
		                }
		            },
		            'detail': function()
		            {   
		            	var buttons =  "<input type=\"button\" value=\"关闭\" class=\"btn btn-primary\" onclick=\"top.$.jBox.close();\">&nbsp;&nbsp;"
		                if(calAffairType == 'affair'){//周期性事务
		                	//top.$.jBox("iframe:"+ contextPath + "/system/core/base/calendar/affair/detail.jsp?id=" + cal_id,{title:"查看事务",width:400,height:300,buttons: {}});
		                	toClickCalendar(cal_id , 1);
		                }else{//
		                	toClickCalendar(cal_id , 0);
		                	//top.$.jBox("iframe:"+ contextPath + "/system/core/base/calendar/detail.jsp?id=" + cal_id,{title:"查看日程",width:400,height:300,buttons: {}});
		                }
		             }
		            
		        });
		        //$("#statuslist").mouseover($("#status_menu").show()).mouseout($("#status_menu").hide());
		        $("#statuslist").bind("mouseover",function(){$("#status_menu").show()});
		        $("#statuslist").bind("mouseout",function(){$("#status_menu").hide()});      
		        jsEvent.stopPropagation();
    
	    },
	    dayClick: function(date, allDay, jsEvent, view) {//当用户点击某一天，空白

	     },
        eventDragStart: function(event, jsEvent, ui, view)
        {
        	//alert("eventDragStartFunc");//拖曳事件开始时触发
            clearView();
        },
        eventDragStop: function(event)
        {
           clearView();
        	//alert("eventDragStopFunc");////拖曳事件停止时触发，此回调是保证被触发用户拖动一个事件后，即使该事件不会改变日期/时间。它被触发之前的event'sinformation已被修改（如果移动到一个新的日期/时间）和theeventDrop回调之前是triggere
        },
        eventDrop: function(event)
        {
            clearView();
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

});


/**
 * 点击日程
 * @param cal
 */
function toClickCalendar(sid , calAffType){

	var url = contextPath + "/system/core/base/calendar/detail.jsp?id=" + sid ;
	if(calAffType == 1){
		url =  contextPath + "/system/core/base/calendar/affair/detail.jsp?id=" + sid ;
	}
	bsWindow(url ,"日程安排详情",{width:"560px",height:"180px",buttons:
		[
		/* {name:"修改",classStyle:"btn btn-primary"},
		 {name:"删除",classStyle:"btn btn-danger"},*/
	 	 {name:"关闭",classStyle:"btn btn-primary"}
		 ]
	,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="修改"){
			toUpdateCalendarByDay(cal.sid);
		}else if(v == "删除"){
			var isClose = false;
			var submit = function (v, h, f) {
			    if (v == 'ok'){
			    	isClose = deleteCalAff(cal.sid);
			    	if(isClose){
			    		window.BSWINDOW.modal("hide");
			    		getCalendar();
			    	}
			    	isClose =  true;
			    }
			    isClose =  true; //close
			};
			$.jBox.confirm("确定要删除吗？", "提示", submit);
			//return isClose;
		}else if(v=="关闭"){
			return true;
		}
	}});
	
}

/**
 * 快速 新建日程
 * @param start  开始时间
 * @param end  结束时间
 * @param allDay  是否在款天
 * @param content 内容
 * @returns
 */
function addCal(start , end , allDay ,  content){
	var content = $("#quick-calendar-title").val();
	var start = $("#quick-begin-time").html();
	var end = $("#quick-finish-time").html();
	var url =  contextPath +  "/calendarManage/addOrUpdate.action";
	var para = {startDate:start,endDate:end , content:content,calType:'1' , calLevel:'0' ,calAffType:0};
	var jsonObj = tools.requestJsonRs(url,para);
	//alert(jsonObj.rtState);
	if(jsonObj.rtState){
		var json = jsonObj.rtData;
		return json;
		//callback(json);
	}else{
		alert(jsonObj.rtMsrg);
	}
}

//*** 测试 ***/
/**
 * 切换试图事件
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
/**
 * 获取日程byId
 * @param id
 * @returns
 */
function getCalById(id){
	var url =  contextPath +  "/calendarManage/selectById.action";
	var para = {sid:id};
	var jsonObj = tools.requestJsonRs(url,para);
	//alert(jsonObj.rtState);
	if(jsonObj.rtState){
		return jsonObj.rtData;
	}else{
		alert(jsonObj.rtMsg);
	}
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
	start = $.fullCalendar.formatDate( start,'yyyy-MM-dd HH:mm');
	end = $.fullCalendar.formatDate( end,'yyyy-MM-dd HH:mm');
	
	var url =  contextPath +  "/calendarManage/updateChangeCal.action";
	var para = {startDate:start,endDate:end  ,sid:id};
	var jsonObj = tools.requestJsonRs(url,para);
	//alert(jsonObj.rtState);
	if(jsonObj.rtState){
		var json = jsonObj.rtData;
		return json;
		//callback(json);
	}else{
		alert(jsonObj.rtMsrg);
	}
	
}

/***
 * 新建或者更新
 */
function addOrUpdateCal(){
	if (check()){
		var url = contextPath + "/calendarManage/addOrUpdate.action";
		var para =  tools.formToJson($("#form1")) ;
		var view = calendar.fullCalendar('getView');
		// 处理获取所有的周期性事务 有效
		para['startTimeStr']  = $.fullCalendar.formatDate( view.start,'yyyy-MM-dd HH:mm');
		para['endTimeStr'] = $.fullCalendar.formatDate( view.end,'yyyy-MM-dd HH:mm');
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			//alert(jsonRs.rtMsg);
			 $.jBox.tip('保存成功。');
			 var data = jsonRs.rtData;
			 var msg = jsonRs.rtMsg;
			// alert(data.id +":"+ msg);
			 if(msg == '0'){//新建
				 if(data.length> 0 ){
					 for(var i = 0;i<data.length;i++){
						 calendar.fullCalendar('renderEvent', data[i]); //在添加
					 }	 
				 }
					 //calendar.fullCalendar('renderEvent', data ); // 等同于 TeeCalendar.renderEvent(tt ,true);
			 }else if(msg == '1'){//更新
				// alert(data.id);
				 calendar.fullCalendar('removeEvents' ,para.sid);//删除节点
				 if(data.length> 0 ){
					 for(var i = 0;i<data.length;i++){
						 calendar.fullCalendar('renderEvent', data[i]); //在添加
					 }
				 }
		
			 }
			 //$.jBox.close();
			 $('#calendar-add-update').modal('hide')

		}else{
			alert(jsonRs.rtMsg);
		}
	}
}
/**
 * 检查
 * @returns {Boolean}
 */
function check() {
	var CAL_CONTENT = $("#CAL_CONTENT");
	if(!CAL_CONTENT.val()){
		alert("内容是必填项！");
		return false ;
	}
	var START_DATE = $("#START_DATE");
	var END_DATE = $("#END_DATE");
	if(START_DATE.val() == ''){
		alert("开始时间是必填项！");
		START_DATE[0].select();
		return false ;
	}
	if(END_DATE.val() == ''){
		alert("结束时间是必填项！");
		END_DATE[0].select();
		return false ;
	}

	if(END_DATE.val() != ''){
		if(START_DATE.val() >= END_DATE.val() ){
			alert("结束日期不能小于开始日期");
			//$.jBox.alert("结束日期不能小于开始日期", '日程');
			END_DATE[0].select();
			return false;
		}
	}
	
	var calAffType = $("#calAffType")[0] ;
	if(calAffType.checked  ==  true){
		var remindTime = $("#remindTime")[0];
		if(!isTimeTee(remindTime.value)){
			alert("提醒时间格式不对,如(13:30:30)");
			remindTime.focus();
			remindTime.select();
			return false;
		}
	}else{
		var beforeDay = $("#beforeDay")[0];
		var beforeHour = $("#beforeHour")[0];
		var beforeMinute = $("#beforeMinute")[0];
		if(beforeDay.value != "" && !isIntegeOrZero(beforeDay.value)){
			alert("请输入整数!");
			beforeDay.focus();
			beforeDay.select();
			return false;
		}
		
		if(beforeHour.value != "" && !isIntegeOrZero(beforeHour.value)){
			alert("请输入整数!");
			beforeHour.focus();
			beforeHour.select();
			return false;
		}
		if(beforeMinute.value != "" && !isIntegeOrZero(beforeMinute.value)){
			alert("请输入整数!");
			beforeMinute.focus();
			beforeMinute.select();
			return false;
		}
	}
	return true;
	
}
/**
 * 删除 byId
 * @param id
 */
function deleteCal(id ,calendar){
	var url = contextPath + "/calendarManage/deleteById.action";
	var jsonRs = tools.requestJsonRs(url,{sid:id});
	if(jsonRs.rtState){
		//alert(jsonRs.rtMsg);
		 $.jBox.tip('删除成功！');
		 calendar.fullCalendar('removeEvents' ,id);//删除节点
		
	}else{
		alert(jsonRs.rtMsg);
	}
}

/***
 * 新建日程表单
 */
var addUpdateCalFrom =  "";
var weekArray = ['','星期一' , '星期二' ,'星期三','星期四' , '星期五' ,'星期六' ,'星期日'];
function getAddUpdateCalFrom(){
	var remindDate3 = "<select class=\"BigSelect\" id=\"remindDate3\" name=\"remindDate3\">";//周
	for(var i= 1 ;  i <= 7 ;i++){
		var selected = "";
		if(i == currWeek){
			selected = "selected";
		}
		remindDate3 = remindDate3 + "<option value=\""+i+"\" " +selected +" >" + weekArray[i] +"</option>";

	}
	remindDate3 = remindDate3 + "</select>";
	
	var dayStr = "";//日
	for(var i= 1 ;  i < 32 ;i++){
		var selected = "";
		if(i == currDay){
			selected = "selected";
		}
		dayStr = dayStr + "<option value=\""+ i +"\" " +selected +" >" + i +"日</option>";

	}

	var monthStr = "";//月
	for(var i= 1 ;  i < 13 ;i++){
		var selected = "";
		if(i == currDay){
			selected = "selected";
		}
		monthStr = monthStr + "<option value=\""+ i +"\" " +selected +" >" + i +"月</option>";

	}

	
	var form =  ""+
	 "<table class=\"TableBlock\" width=\"100%\" align=\"center\">"+
		"<tr class=\"TableData\">"+
			"<td nowrap>事务内容：</td>"+
			"<td nowrap align=\"left\">"+
				 "<textarea  name=\"content\" id=\"CAL_CONTENT\"  class=\"easyui-validatebox BigTextarea\" required=\"true\" rows=\"5\" cols=\"50\"></textarea><font color=\"red\">(必填)</font>"+
         "</td>"+
		"</tr>"+
		
		"<tr class=\"TableData\">"+
			"<td nowrap>开始时间：</td>"+
			"<td nowrap align=\"left\">"+
			 	"<input type=\"text\"  name=\"startDate\"  id=\"START_DATE\" class=\"BigInput\" onClick=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})\"/>"+
       " </td>"+
		"</tr>"+
		"<tr class=\"TableData\">"+
			"<td nowrap>结束时间：</td>"+
			"<td nowrap align=\"left\">"+
			 	"<input type=\"text\"  name=\"endDate\"  id=\"END_DATE\" class=\"BigInput\" onClick=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})\"/>"+
        "</td>"+
		"</tr>"+
		
		"<tr class=\"TableData\">"+
			"<td nowrap>事务类型：</td>"+
			"<td nowrap align=\"left\">"+
				 "<select class=\"BigSelect\" name=\"calType\" id=\"calType\">"+
                   " <option value=\"1\">工作事务</option>"+
						"<option value=\"2\">个人事务</option>"+
            " </select>"+
           "</td>"+
			"</tr>"+
		"<tr class=\"TableData\">"+
			"<td nowrap>优先级：</td>"+
			"<td nowrap align=\"left\">"+
//		      "<span class=\"color_wrapper\" style=''>"+
//          "<a hideFocus=\"true\" style=\"margin-right:30px;\" id=\"cal_color\"  class='color' index=\"0\" onclick='setLevel();'></a>"+
//          "<span style=\"display: none;\" id=\"color_menu\" class=\"color_menu\" >"+
//                "<a style=\"margin-top: 0px;\" id=\"calcolor\" class=\"color\" href=\"javascript:;\" index=\"0\"></a>"+
//                "<a id=\"calcolor1\" class=\"color1\"  href=\"javascript:;\" index=\"1\"></a>"+
//                "<a id=\"calcolor2\" class=\"color2\" href=\"javascript:;\" index=\"2\"></a>"+
//                "<a id=\"calcolor3\" class=\"color3\" href=\"javascript:;\" index=\"3\"></a>"+
//                "<a id=\"calcolor4\" class=\"color4\" href=\"javascript:;\" index=\"4\"></a>"+
//                "<a id=\"calcolor5\" class=\"color5\" href=\"javascript:;\" index=\"5\"></a>"+
//                "<a id=\"calcolor6\" class=\"color6\" href=\"javascript:;\" index=\"6\"> </a>"+
//           "</span>"+
//           "<input id=\"calLevel\" name=\"calLevel\" value=\"0\" type=\"hidden\">"+
			"<select class='BigSelect' name='calLevel' id='calLevel'>"+
			"<option value='0'>未指定</option>"+
			"<option value='1'>重要/紧急</option>"+
			"<option value='2'>重要/不紧急</option>"+
			"<option value='3'>不重要/紧急</option>"+
			"<option value='4'>不重要/不紧急</option>"+
			"</select>"+
       "</span>"+
			"</td>"+
		"</tr>"+
		"<tr class=\"TableData\">"+
			"<td nowrap>是否重复：</td>"+
			"<td nowrap align=\"left\">"+
			   "<input name=\"calAffType\" id='calAffType' type=\"checkbox\" value=\"1\"  onclick='setCalAffType(this);'></label for='calAffType'></label>"+
			"</td>"+
	     "</tr>"+
	     
	     "<tr class=\"TableData\" id=\"remindTypeTr\" style=\"display:none;\">"+
			"<td nowrap>提醒类型：</td>"+
			"<td nowrap align=\"left\">"+
          "<select class=\"BigSelect\" id=\"remindType\" name='remindType' onchange=\"sel_change(this.value)\">"+
              "<option value=\"2\">按天重复</option>"+
              "<option value=\"3\">按周重复</option>"+
              "<option value=\"4\">按月重复</option>"+
              "<option value=\"5\">按年重复</option>"+
          "</select>"+    
          
           "<span id='dayShow'>&nbsp;&nbsp;<input type=\"checkbox\" name=\"isWeekend\" id=\"isWeekend\"  value='1'></input>&nbsp;选中为排除周六、日 </span>"+
       "</td>"+
	     "</tr>"+
	     "<tr class=\"TableData\" id=\"remindTimeTr\" style=\"display:none;\">"+
			"<td nowrap>提醒时间：</td>"+
			"<td nowrap align=\"left\">"+
			 	"<span id='day' >"+
			 		"</span>"+
			 	"<span id='week' style='display:none;'>"+
			 	remindDate3 + "&nbsp;&nbsp;"+
             "</span>"+
             "<span id='mon' style='display:none;'>"+
             	"<select class=\"BigSelect\" id=\"remindDate4\" name=\"remindDate4\" >"+
             		dayStr + 
             	"</select>&nbsp;&nbsp;"+
             "</span>"+
             
             "<span id='years' style='display:none;'>"+
	                "<select class=\"BigSelect\" id=\"remindDate5Mon\" name=\"remindDate5Mon\">"+
	                	monthStr + 
					"</select>"+
					"&nbsp;&nbsp;<select class=\"BigSelect\" id=\"remindDate5Day\" name=\"remindDate5Day\">"+
						dayStr + 
            	"</select>&nbsp;&nbsp;"+
             "</span>"+
         	"<input id=\"remindTime\" name=\"remindTime\" value='" + currTime +"' type=\"text\" onclick=\"setRemindTime(this);\" style=\"width:60px;\" class=\"BigInput easyui-validatebox\" validType=\"time[]\"   data-placement=\"right\" data-content=\"\" data-toggle=\"popover\" data-html=\"true\">"+
           	"&nbsp;为空为当前时间"+
			"</td>"+
	    "</tr>"+
		"<tr class=\"TableData\">"+
		"<td nowrap>参与者：</td>"+
		"<td nowrap align=\"left\">"+
			"<input id=\"actorIds\" name=\"actorIds\" type=\"hidden\">"+
			"<textarea  name=\"actorNames\" id=\"actorNames\"  class=\"easyui-validatebox BigTextarea SmallStatic\" required=\"true\" rows=\"2\" cols=\"35\" readonly=\"readonly\"></textarea>"+
			"&nbsp;&nbsp;<a href=\"javascript:;\" class=\"orgAdd\" onClick=\"selectUser(['actorIds','actorNames'],'1')\">选择</a> &nbsp;&nbsp;"+
		    "<a href=\"javascript:;\" class=\"orgClear\" onClick=\"clearData('actorIds','actorNames')\">清空</a>"+
       "</td>"+
       
       "</tr>"+
		"<tr class=\"TableData\" id=\"beforeRemindTr\">"+
		"<td nowrap>提前时间：</td>"+
		"<td nowrap align=\"left\">"+
			"提前<input  name=\"beforeDay\" class='BigInput' id=\"beforeDay\" type=\"text\" value='' size='3' maxlength='5'> 天"+
				"<input  name=\"beforeHour\" class='BigInput' id=\"beforeHour\" type=\"text\" value='' size='3' maxlength='5'> 小时"+
				"<input  name=\"beforeMinute\" class='BigInput' id=\"beforeMinute\" type=\"text\" value='' size='3' maxlength='5'> 分钟提醒"+
		"</td>"+
		"</tr>"+
		/*"</tr>"+
		"<tr class=\"TableData\">"+
		"<td nowrap>提醒：</td>"+
		"<td nowrap align=\"left\">"+
			"<input id=\"smsRemind\" name=\"smsRemind\" type=\"checkbox\" value='1'> <label for='smsRemind'>是否使用内部短信</label>"+
		"</td>"+
		"</tr>"+
		*/
	"</table>";
	addUpdateCalFrom = form;
};




/***
 * 设置优先级别
 */
function setLevel(){
	$("#color_menu").show();
	$("#color_menu a").click(function(event){
		var index =$(this).attr('index') ;
		if(!index || index == 0 ){
			index = "";
		}
		$("#cal_color").attr("class","color" + index);
		$("#color_menu").hide();
		$("#calLevel").val(index);
	});
}
/***
 * 设置日程类型
 * @param obj
 */
function setCalAffType(obj){
	if(obj.checked == true){
		$("#remindTypeTr").show();
		$("#remindTimeTr").show();
		$("#beforeRemindTr").hide();
	}else{
		$("#remindTypeTr").hide();
		$("#remindTimeTr").hide();
		$("#beforeRemindTr").show();//提前多少时间提醒
	}
}

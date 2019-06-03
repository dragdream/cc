<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no" />
    <meta charset="utf-8">
	<meta name="format-detection" content="telephone=no,email=no,adress=no">
		
	<!--公共样式-->
	<link rel="stylesheet" href="css/reset.css" />
		
	<!--主要样式-->
	<link rel="stylesheet" type="text/css" href="css/simple-calendar.css">
	<link rel="stylesheet" href="css/calendar.css" />
	<title>课表查询</title>
    <%@ include file="/system/mobile/mui/header.jsp" %>
    <script src="<%=contextPath %>/common/js/tools2.0.js?v=1"></script>
</head>
<body>
		<div class="inner">
			<div id='calendar' class="sc-calendar">
				<div class="sc-header">
					<div class="sc-title">
						<div class="year"><span class="sc-select-year" name=""></span>年</div>
						<div class="month">
							<div class="arrow sc-mleft"></div>
							<div class="monthdiv">
								<span class="sc-select-month" name=""></span>
							</div>
							<div class="arrow sc-mright"></div>
						</div>
					</div>
					<div class="sc-week"></div> 
				</div>
				<div class="sc-body">
					<div class="sc-days"></div>
				</div>
			</div>
			<div class="announcement">
				<ul class="matter">
				</ul>
			</div>
		</div>
		
		<script type="text/javascript" src="js/jquery.min.js" ></script>
		<script type="text/javascript" src="js/simple-calendar.js"></script>
		<script type="text/javascript" src="js/hammer-2.0.8-min.js"></script>
		<script type="text/javascript">
			var myCalendar = new SimpleCalendar('#calendar');
			var strArr={};
			  var mark ={};
			$(function(){
				var monthCH = $('.sc-select-month').text();
				$(".sc-mleft").click(function(){
					myCalendar.subMonth();
					mleft();
				})
			    $(".sc-mright").click(function(){
			   		myCalendar.addMonth();
			   		mleft();
			    })
			    $(".sc-month").click(function(){
			   		$(".sc-month").css("border-radius","");
			   		$(".sc-month").css("border","");
			   		$(this).css("border-radius","25px");
			   		$(this).css("border","1px solid red");
			    })
		    });
			
			//滑动切换
			var myElement = document.getElementById('calendar');
 			var hammer = new Hammer(myElement);
			hammer.on("swipeleft", function (ev) {
			 	myCalendar.addMonth();
			});
			hammer.on("swiperight", function (ev) {
			 	myCalendar.subMonth();
			});
			function mleft(){
				   var year = $('.sc-select-year').text();
				   var monthCH = $('.sc-select-month').text();
				   var totalOfMonthDays=$(".sc-month").length;
				   var month = SimpleCalendar.prototype.languageData.months_CH.indexOf(monthCH)+1;
				   var url = "<%=contextPath%>/teePbOnDutyController/findDutyByDate3.action";
				   var para = {year : year,month : month,totalOfMonthDays : totalOfMonthDays};
				   var json=tools.requestJsonRs(url,para);
				   var data=json.rtData;
				   mark= data;
			       myCalendar._defaultOptions.mark=mark;
				   myCalendar.updateMark(year,month);
			}
			mleft();
			//添加标记
			
			myCalendar.update();
			
			
			
			//显示当天的活动在初始化mark之后
			//初始化今天的活动
			announceList($('.sc-today'));
			//有标记的日期点击事件
			$('#calendar').on("click", '.sc-selected', function() {
				announceList($(this));
			});
			
			//显示选择日期当天的活动
			function announceList(v){
				console.log(v);
				if(v.children().hasClass('sc-mark-show')){
					var year = $('.sc-select-year').text();
					var monthCH = $('.sc-select-month').text();
					var day = v.children()[1].innerText;
					var month = SimpleCalendar.prototype.languageData.months_CH.indexOf(monthCH)+1;
					var date = year +"-" + month +"-"+ day;
					var content = mark[date];
					console.log(content);
					var matterHtml='';
					//(var i=0;i<content.length;i++){
						matterHtml +='<li class="announceItem"><div><div class="fl announceImg">'
							+'</div>'
							+'<p class="announceContent">值班部门：'+content[0]+'</p>'
							+'<p class="announceContent">带班领导：'+content[1]+'</p>';
							for(var i=2;i<content.length;i++){
								matterHtml+='<p class="announceContent">'+content[i].childName+'：'+content[i].userName+'</p>';
							}
					//}
					$('.matter').html(matterHtml);
				}else{
					var matterHtml=''
					matterHtml +='<li class="announceItem"><div><p class="announceContent">当前日期暂无活动</p></div></li>';
					$('.matter').html(matterHtml);
				}
			}
			
			
			
		</script>
	</body>

</html>
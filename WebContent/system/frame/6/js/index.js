var theme6icon = "purple";
$(document).ready(function(){
	theme6icon = "purple";
	getMenuList();
	getSkinFunc();
	//获取提前或延后考核时间
	getTimesTips();
	//获取当前用户当天已经签到的次数（从1至6）
	getRegisterStatue();
	
	//获取考核时间段
	getDutyConfigByUser();
	
	//显示时间
	showTimeFunc();
	
	setInterval("lunarTimeCounting()",1000);
	
	//**************屏幕自适应
	function adjustWidthHeight() {
	//加载时适应浏览器高度
	var width = $(window).outerWidth(); 
    var height = $(window).outerHeight();
	var topHeight =$("#header").outerHeight();
	$('#main,#sidebar,#main_conetnt,#message_box').css('height', height - topHeight);
	$('.sidebar_nav_ul').css('height',height-(topHeight+58));
	$('.two_menu').css('max-height',height-(topHeight+28));
	}
	adjustWidthHeight();
	$(window).resize(function() {
	//改变窗体大小时适应浏览器高度
	adjustWidthHeight();
	});
	
	//**************侧栏导航 
	$(function(){
		var n_li = $('.sidebar_nav_ul').children('li');
		var aDiv = $('.sidebar_box').children('.two_menu');
		n_li.hover(function(){
			var i = n_li.index($(this));
			var top_1 = $(this).offset().top-80;
			var oH2 = aDiv.eq(i).outerHeight();
			var oH1 = $('#sidebar').outerHeight();
			function way(){
				n_li.removeClass("on").eq(i).addClass("on");
				var childs = aDiv.hide().eq(i).find("li");
				if(childs.length>0){
					aDiv.hide().eq(i).show().css('overflow','auto');
				}
				$('#sidebar').css('overflow','inherit');
				if(oH2>(oH1-40)){//判断二级菜单显示位置
					$('.two_menu').css('top','-2px');
				}else{
					if((oH1-top_1)>oH2){
						$('.two_menu').css('top',top_1-5);
					}else{
						$('.two_menu').css('top',top_1+(oH1-top_1-oH2-10));
					}
				}
			};
			timer=setTimeout(way,50);
		},function(){
			clearTimeout(timer);
		});
		$(".two_menu").hover(function(){
			$(this).show(0);
			$(this).css('overflow','auto');
		},function(){
			$(this).delay(200).hide(0);
		});
		$(document).bind("mouseover",function(e){
			var target  = $(e.target);//表示当前对象，切记，如果没有e这个参数，即表示整个BODY对象
			if(target.closest(".sidebar_nav_ul,.two_menu").length == 0){
				$(".two_menu").hide();
				$('.sidebar_nav_ul').children('li').removeClass('on');
			}
		});
	});
	//侧栏上下滚动
	$('.sidebar_upward').click(function(){
		$('.sidebar_nav_ul').animate({scrollTop:0},500); 
	});
	
	$('.sidebar_down').click(function(){
		$('.sidebar_nav_ul').animate({scrollTop:$('.sidebar_nav_ul')[0].offsetHeight},1000); 
	});
	//侧栏导航展开收起
	var bOk=true;
	$('.sidebar_spread').click(function(){
		if(bOk){
			$('#sidebar').animate({width:'50px',left:'-22px'},500);
			$('.sidebar_nav').animate({width:'80px'},500);
			$(this).addClass('out');
			$(".logo").animate({width:'58px'},500).children().hide();
			bOk=false;
		}else{
			$('#sidebar').animate({width:'200px',left:'0px'},500);
			$('.sidebar_nav').animate({width:'200px'},500);
			$(this).removeClass('out');
			$(".logo").animate({width:'200px'},500,function(){
				$(this).children().show();
			});
			bOk=true;
		}
		
	});
	//**************头部
	$(function(){
		$('.search').children('span').click(function(){
			$(".quick_search_div").toggle();
		});
	});
	$(document).bind("click",function(e){
			var target  = $(e.target);//表示当前对象，切记，如果没有e这个参数，即表示整个BODY对象
			if(target.closest(".search_content,.search").length == 0){
				$(".search_content").hide();
			}
	});
	//**************桌面
	$(function(){
		$('.modular').find('.toggle').click(function(){
			$(this).toggleClass('on');
			$(this).parent('.modular_hd').nextAll('.modular_bd').slideToggle();
		});
	});
	//**换肤
	$('.operating_button').find('.btn1').click(function(){
		//alert(GAO_SU_BO_VERSION)
		if(GAO_SU_BO_VERSION == '1'){
			//GAO_SU_BO_VERSION
			var url = contextPath + "/personManager/getCloudAccountAndPwd.action";
			var para =  {} ;
			var jsonRs = tools.requestJsonRs(url,para);
			//alert(jsonRs);
			if(jsonRs.rtState){
				var data = jsonRs.rtData;
				if(data.accountId && data.accountId != ''){
					//var accountPwd = data.accountPwd;
					window.open(GSB_OA_CLOUD_LOGIN_URL + "?phone=" + data.accountId + "&password=" + data.accountPwd + "&from=1&flag=submit", 'gaosubo');
					//直接登录
				}else{
					$(".modal_backdrop_fade_in").show();
					$(".top_floor_div").show();
					$(".top_floor_div").unbind('click');
					var isClose = true;
					$(".top_floor_div").bind('click',function(){
						if(isClose){
							$(this).hide();
							//alert("dd")
							$(".modal_backdrop_fade_in").hide();
						}else{
							isClose = true;
						}
						
					});
					$(".top_floor_div").delegate("div","click",function(){
						isClose = false;
					});
				}
				
			}else{
				alert(jsonRs.rtMsg);
			}
		}else{
			$(this).children('.sign_in').toggle();
		}
		
	});
	$(document).bind("click",function(e){
			var target  = $(e.target);//表示当前对象，切记，如果没有e这个参数，即表示整个BODY对象
			if(target.closest(".btn1,.sign_in").length == 0){
				$(".sign_in").hide();
			}
	});
	$('.sign_in').find('li.blue').click(function(){
		$('.sign_in').find('li').removeClass('on');
		$(this).addClass('on');
		$('#skin').attr('href','style/index-blue.css');
		setCookie("skinChange1" , "blue" , 365);
		theme6icon = "blue";
	});
	$('.sign_in').find('li.purple').click(function(){
		$('.sign_in').find('li').removeClass('on');
		$(this).addClass('on');
		$('#skin').attr('href','style/index-purple.css');
		setCookie("skinChange1" , "purple" , 365);
		theme6icon = "purple";
	});
	//登记
	$('.operating_button').find('.btn2').click(function(){
		$(this).children('.skin_peeler').toggle();
	});
	$(document).bind("click",function(e){
			var target  = $(e.target);//表示当前对象，切记，如果没有e这个参数，即表示整个BODY对象
			if(target.closest(".btn2").length == 0){
				$(".skin_peeler").hide();
			}
	});
	//人员信息
	$('.user_information').find('h3').click(function(){
		$(this).next('.user_content').toggle();
	});
	$(document).bind("click",function(e){
			var target  = $(e.target);//表示当前对象，切记，如果没有e这个参数，即表示整个BODY对象
			if(target.closest(".user_information").length == 0){
				$(".user_content").hide();
			}
	});
	//组织机构
	var aOk=true;
	$('.message').click(function(){
		if(aOk){
			$('#message_box').animate({right:'0px'},500);
			aOk=false;
		}else{
			$("#message_box").animate({right:'-240px'},500);
			aOk=true;
		}
		
	});
	//********************** 签到提示
	$(window).load(function(){
		function moveRocket(){
			$('.sign_in_tid').animate({'top':'+=4'},250)
						.animate({'top':'-=4'},250)
						setTimeout(moveRocket);
		}
		moveRocket();
	});
	$(document).bind("click",function(e){
			var target  = $(e.target);//表示当前对象，切记，如果没有e这个参数，即表示整个BODY对象
			if(target.closest(".message,#message_box").length == 0){
				$("#message_box").animate({right:'-240px'},500);
				aOk=true;
			}
	});
	$(".sms").click(function(){
		$('.sign_in_tid').hide();
		smsAlert();
	});
});


function  getMenuList(){
	var url = contextPath + "/teeMenuGroup/getPrivSysMenu.action";
	var jsonObj = tools.requestJsonRs(url);
	var desc = "";
	var desc2 = "";
	
	var twoMenuDesc = "";
	var threeMenuDesc = "";
	
	var thirdMenuArray = new Array();
	var pMenuId = "";
	if(jsonObj.rtState){
		var json = jsonObj.rtData;
		AllMenuList =json;
		var counter = 0;
		jQuery.each(json, function(i, sysMenu) {
			var menuId = sysMenu.menuId;
		    var menuName = sysMenu.menuName;
		    var  menuCode = sysMenu.menuCode ;
		    var menuIcon = sysMenu.icon;
		    if(!menuIcon && menuIcon == ''){
		    	menuIcon = "";
		    }
		    if(i == 0){
		    	pMenuId = menuId;
		    }
		    counter ++;
			if(menuId.length == 3){//主菜单
				desc =  " <li id='menu-lv1-"+menuId+"' class='nav_1' title='" + menuName + "' menuIcon='"+menuIcon+"'>" +
						" <a menuName='" + menuName + "' menuCode='" + menuCode +"'><img id='iconImg"+menuId+"' src='" + contextPath + "/system/frame/6/icons/default/" + menuIcon+ "'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + menuName +"</a>"
						+ " </span></li>";
				$("#sidebar_nav_ul").append(desc);
				twoMenuDesc = "<div  class='two_menu' targetIcon='iconImg"+menuId+"' menuIcon='"+menuIcon+"'>"
					+ "<ul id='menu-lv2-"+menuId+"'>"
					+ "</ul>"
					+ "</div>";
				$("#sidebar_box").append(twoMenuDesc);
				
			}else if(menuId.length == 6){//二级菜单
				var parentMenuId = menuId.substring(0,3);
				desc2 = "<li id='menu-lv3-li-"+menuId+"'>"
					+ " <a href='javascript:void(0);'  level='" + pMenuId + "' id='" + menuId + "' menuName='" + menuName + "'menuCode='" + menuCode + "'>"+ menuName + "</a>"
					+ " </li>";
				$("#menu-lv2-"+ parentMenuId).append(desc2);
			}else if(menuId.length == 9){//三级菜单
				var secondMenuId = menuId.substring(0,6);
				
				var three_ul = $("#menu-lv3-ul-"+secondMenuId);
				if(three_ul.length==0){
					var three_li = $("#menu-lv3-li-"+secondMenuId);
					three_li.addClass("cu");
					var menuName1 = three_li.children("a:first").html();
					three_li.html("<p>"+menuName1+"</p>");
					
					threeMenuDesc = "<div  class='three_menu'>"
						+ "<ul id='menu-lv3-ul-"+secondMenuId+"'>"
						+ "</ul>"
						+ "</div>";
					
					three_li.append(threeMenuDesc);
				}
				desc2 = "<li>"
				+ " <a href='javascript:void(0);' level='" + secondMenuId + "' id='" + menuId + "' menuName='" + menuName + "'menuCode='" + menuCode + "'>"+ menuName + "</a>"
				+ " </li>";
				$("#menu-lv3-ul-"+secondMenuId).append(desc2);
			}
		});
	}
	initMenuFunc();
}

/**
 * 初始化菜单触发时事件
 */
function initMenuFunc(){
	jQuery('#sidebar_nav_ul li').hover(function(){
		var menuIcon = $(this).attr("menuIcon");
		$(this).find("img").attr("src",contextPath + "/system/frame/6/icons/"+theme6icon+"/"+menuIcon);
	},function(){
		var menuIcon = $(this).attr("menuIcon");
		$(this).find("img").attr("src",contextPath + "/system/frame/6/icons/default/"+menuIcon);
	});
	
	jQuery('.two_menu').hover(function(){
		var menuIcon = $(this).attr("menuIcon");
		var targetIcon = $("#"+$(this).attr("targetIcon"));
		targetIcon.attr("src",contextPath + "/system/frame/6/icons/"+theme6icon+"/"+menuIcon);
		
	},function(){
		var menuIcon = $(this).attr("menuIcon");
		var targetIcon = $("#"+$(this).attr("targetIcon"));
		targetIcon.attr("src",contextPath + "/system/frame/6/icons/default/"+menuIcon);
	});
	
	//第一级菜单 触发时事件
	jQuery('#sidebar_nav_ul a').bind('click', function(){
		var menuName  = jQuery(this).attr("menuName");
		var menuId  = jQuery(this).attr("id");
		var menuCode =  jQuery(this).attr("menuCode");
		if(menuCode != "" && menuCode!= 'undefined'){
			//toSrcUrl(menuName,menuCode , true);
			//$.addTab("tabs","tabs-content",{title:menuName,url:contextPath + menuCode,active:true,closable:true,cache:true});
			addTabFunc(menuName,menuCode);
		}
	});
	//二级和三级菜单触发事件
	jQuery('#sidebar_box a').bind('click', function(){
		var menuName  = jQuery(this).attr("menuName");
		var menuId  = jQuery(this).attr("id");
		var menuCode =  jQuery(this).attr("menuCode");
		if(menuCode != ""){
			//$.addTab("tabs","tabs-content",{title:menuName,url:contextPath + menuCode,active:true,closable:true,cache:true});
			setTimeout(function(){
				addTabFunc(menuName,menuCode);
			},200);
		}
	});
}

function addTabFunc(menuName,menuCode){
	var frame = $('#contentFrame');
	var parent = $("#contentFrame").parent();
	if(frame[0]){
	    frame.attr('src', 'about:blank');
	    frame[0].contentWindow.document.write('');
	    frame[0].contentWindow.document.close();  
	    frame.remove();
	}
	$("<iframe id=\"contentFrame\" style=\"width:100%;height:100%;\" frameborder=\"no\" src=\""+(contextPath + menuCode)+"\"></iframe>").appendTo(parent);
}


function getSkinFunc(){
	var skin = getCookie("skinChange1");
	$(".skin_peeler li").removeClass("on");
	if(skin == "blue"){
		theme6icon = "blue";
		$('.sign_in').find('li').removeClass('on');
		$('.sign_in').find('li.blue').addClass('on');
		$('#skin').attr('href','style/index-blue.css');
	}else if(skin == "purple"){
		theme6icon = "purple";
		$('.sign_in').find('li').removeClass('on');
		$('.sign_in').find('li.purple').addClass('on');
		$('#skin').attr('href','style/index-purple.css');
	}
}



var serverTime = new Date();
//获取服务器签到时间
function getServerTime(){
	var url = contextPath+"/TeeAttendConfigController/getServerTime.action";
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		serverTime.setTime(json.rtData);
	}else{
		top.$.jBox.tip(json.rtMsg,"error");
	}
}
//显示系统时间
function showTimeFunc(){
	var oTime=document.getElementById('time');
	var aImg=oTime.getElementsByTagName('img');
	serverTime.setSeconds(serverTime.getSeconds()+1);
	var str=toDou(serverTime.getHours())+toDou(serverTime.getMinutes())+toDou(serverTime.getSeconds());
	for(var i=0;i<aImg.length;i++){
		aImg[i].src='images/date/'+str.charAt(i)+'.png';
	}
	
	window.setTimeout('showTimeFunc()',1000);
	getSignInTime();
	
}

function toDou(isNum){
	if(isNum<10){
		return '0'+isNum	
	}else{
		return ''+isNum	
	}
};

var workonBeforeMin = 0;
var workonAfterMin = 0;
var workoutBeforeMin = 0;
var workoutAfterMin = 0;
/**
* 获取提前或延后考核时间
*/
function getTimesTips(){
	var url = contextPath+"/TeeAttendConfigController/getAttendTimes.action";
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		var prc = json.rtData;
		if(prc.workonBeforeMin){
			workonBeforeMin = prc.workonBeforeMin;
		}
		if(prc.workonAfterMin){
			workonAfterMin = prc.workonAfterMin;
		}
		if(prc.workoutBeforeMin){
			workoutBeforeMin = prc.workoutBeforeMin;
		}
		if(prc.workoutAfterMin){
			workoutAfterMin = prc.workoutAfterMin;
		}
		
	}
}



//获取签到时间
var dutyTimeDesc1 = new Date();
var dutyTimeDesc2 = new Date();
var dutyTimeDesc3 = new Date();
var dutyTimeDesc4 = new Date();
var dutyTimeDesc5 = new Date();
var dutyTimeDesc6 = new Date();

//提前时间
var workonBeforeDesc1 = new Date();
var workonBeforeDesc2 = new Date();
var workonBeforeDesc3 = new Date();
var workonBeforeDesc4 = new Date();
var workonBeforeDesc5 = new Date();
var workonBeforeDesc6 = new Date();


//延后时间
var dutyTimeAfterDesc1 = new Date();
var dutyTimeAfterDesc2 = new Date();
var dutyTimeAfterDesc3 = new Date();
var dutyTimeAfterDesc4 = new Date();
var dutyTimeAfterDesc5 = new Date();
var dutyTimeAfterDesc6 = new Date();

//获取考核时间段
function getDutyConfigByUser(){
	dutyTimeDesc1.setHours(0, 0, 0, 0);
	dutyTimeDesc2.setHours(0, 0, 0, 0);
	dutyTimeDesc3.setHours(0, 0, 0, 0);
	dutyTimeDesc4.setHours(0, 0, 0, 0);
	dutyTimeDesc5.setHours(0, 0, 0, 0);
	dutyTimeDesc6.setHours(0, 0, 0, 0);
	
	dutyTimeAfterDesc1.setHours(0, 0, 0, 0);
	dutyTimeAfterDesc2.setHours(0, 0, 0, 0);
	dutyTimeAfterDesc3.setHours(0, 0, 0, 0);
	dutyTimeAfterDesc4.setHours(0, 0, 0, 0);
	dutyTimeAfterDesc5.setHours(0, 0, 0, 0);
	dutyTimeAfterDesc6.setHours(0, 0, 0, 0);
	
	var url = contextPath+"/TeeAttendConfigController/getDutyConfigByUser.action";
	var json = tools.requestJsonRs(url);
	if(json.rtState && json.rtData){
		var data = json.rtData;
		if(data.dutyTimeDesc1){
			var hour = data.dutyTimeDesc1.split(":")[0];
			var min = data.dutyTimeDesc1.split(":")[1];
			var sec = data.dutyTimeDesc1.split(":")[2];
			dutyTimeDesc1.setHours(hour, min, sec, 0);
			//dutyTimeAfterDesc1.setTime(dutyTimeDesc1);
			//提前
			var workonBeforeMinTime = Math.floor(workonBeforeMin *60*1000);
			workonBeforeDesc1.setTime(dutyTimeDesc1.getTime()-workonBeforeMinTime);
			//延后
			var workonAfterMinTime = Math.floor(workonAfterMin *60*1000);
			dutyTimeAfterDesc1.setTime(dutyTimeDesc1.getTime()+workonAfterMinTime);
			//alert(dutyTimeDesc1.toLocaleTimeString() + ">>"  + workonBeforeDesc1.toLocaleTimeString());
		}
		if(data.dutyTimeDesc2){//下班
			dutyTimeDesc2.setHours(data.dutyTimeDesc2.split(":")[0], data.dutyTimeDesc2.split(":")[1], data.dutyTimeDesc2.split(":")[2], 0);
			
			//提前
			var workoutBeforeMinTime = Math.floor(workoutBeforeMin *60*1000);
			workonBeforeDesc2.setTime(dutyTimeDesc2.getTime()-workoutBeforeMinTime);
			//延后
			var workoutAfterMinTime = Math.floor(workoutAfterMin *60*1000);
			dutyTimeAfterDesc2.setTime(dutyTimeDesc2.getTime()+workoutAfterMinTime);
		}
		if(data.dutyTimeDesc3){
			dutyTimeDesc3.setHours(data.dutyTimeDesc3.split(":")[0], data.dutyTimeDesc3.split(":")[1], data.dutyTimeDesc3.split(":")[2], 0);
			//提前
			var workonBeforeMinTime = Math.floor(workonBeforeMin *60*1000);
			workonBeforeDesc3.setTime(dutyTimeDesc3.getTime()-workonBeforeMinTime);
			//延后
			var workonAfterMinTime = Math.floor(workonAfterMin *60*1000);
			dutyTimeAfterDesc3.setTime(dutyTimeDesc3.getTime()+workonAfterMinTime);
		}
		if(data.dutyTimeDesc4){//下班
			dutyTimeDesc4.setHours(data.dutyTimeDesc4.split(":")[0], data.dutyTimeDesc4.split(":")[1], data.dutyTimeDesc4.split(":")[2], 0);
			//提前
			var workoutBeforeMinTime = Math.floor(workoutBeforeMin *60*1000);
			workonBeforeDesc4.setTime(dutyTimeDesc4.getTime()-workoutBeforeMinTime);
			//延后
			var workoutAfterMinTime = Math.floor(workoutAfterMin *60*1000);
			dutyTimeAfterDesc4.setTime(dutyTimeDesc4.getTime()+workoutAfterMinTime);
		}
		if(data.dutyTimeDesc5){
			dutyTimeDesc5.setHours(data.dutyTimeDesc5.split(":")[0], data.dutyTimeDesc5.split(":")[1], data.dutyTimeDesc5.split(":")[2], 0);
			//提前
			var workonBeforeMinTime = Math.floor(workonBeforeMin *60*1000);
			workonBeforeDesc5.setTime(dutyTimeDesc5.getTime()-workonBeforeMinTime);
			//延后
			var workonAfterMinTime = Math.floor(workonAfterMin *60*1000);
			dutyTimeAfterDesc5.setTime(dutyTimeDesc5.getTime()+workonAfterMinTime);
		}
		if(data.dutyTimeDesc6){//下班
			dutyTimeDesc6.setHours(data.dutyTimeDesc6.split(":")[0], data.dutyTimeDesc6.split(":")[1], data.dutyTimeDesc6.split(":")[2], 0);
			//提前
			var workoutBeforeMinTime = Math.floor(workoutBeforeMin *60*1000);
			workonBeforeDesc6.setTime(dutyTimeDesc6.getTime()-workoutBeforeMinTime);
			//延后
			var workoutAfterMinTime = Math.floor(workoutAfterMin *60*1000);
			dutyTimeAfterDesc6.setTime(dutyTimeDesc6.getTime()+workoutAfterMinTime);
		}
	}
}
var ii = 0;
//签到判断
function getSignInTime(){
	var signFlag = getSignInFlag();
	//alert(signFlag);
	if(signFlag ==1){//
		ii++;
		$("#signInButton").css("background","#03aee6");
		$("#signInButton").removeAttr("disabled");
		if(ii%2!=0){
//			$(".sign_in").addClass("sign_in_flash");
		}else{
//			$(".sign_in").removeClass("sign_in_flash");
		}
//		$(".sign_in_tid").show();
	}else{
		$("#signInButton").css("background","#cdcdcd");
		$("#signInButton").attr("disabled","true");
//		$(".sign_in").removeClass("sign_in_flash");
//		$(".sign_in_tid").hide();
	}
}

var singInflag = 0;
function getSignInFlag(){
	var flag=0;
	//alert(serverTime.toLocaleTimeString() + ">>" + workonBeforeDesc4.toLocaleTimeString() + ">>" + dutyTimeAfterDesc4.toLocaleTimeString());
	if(serverTime.getTime()>=workonBeforeDesc1.getTime() && serverTime.getTime() <=dutyTimeAfterDesc1.getTime()){//上班
		flag=1;
		singInflag=1;
	}else if(serverTime.getTime()>=workonBeforeDesc2.getTime() && serverTime.getTime() <=dutyTimeAfterDesc2.getTime()){
		flag=1;
		singInflag=2;
	}else if(serverTime.getTime()>=workonBeforeDesc3.getTime() && serverTime.getTime() <=dutyTimeAfterDesc3.getTime()){
		flag=1;
		singInflag=3;
	}else if(serverTime.getTime()>=workonBeforeDesc4.getTime() && serverTime.getTime() <=dutyTimeAfterDesc4.getTime()){
		flag=1;
		singInflag=4;
	}else if(serverTime.getTime()>=workonBeforeDesc5.getTime() && serverTime.getTime() <=dutyTimeAfterDesc5.getTime()){
		flag=1;
		singInflag=5;
	}else if(serverTime.getTime()>=workonBeforeDesc6.getTime() && serverTime.getTime() <=dutyTimeAfterDesc6.getTime()){
		flag=1;
		singInflag=6;
	}
	
	var flag1 = isRegisterFunc(singInflag);
	if(flag1 ==0 &&flag ==1){
		flag =1;
	}else{
		flag =0;
	}
	return flag;
}

function signInFunc(){
	if(singInflag ==1){
		register(getFormatDateStr(dutyTimeDesc1.getTime(),"HH:mm:ss"),singInflag);
	}else if(singInflag ==2){
		register(getFormatDateStr(dutyTimeDesc2.getTime(),"HH:mm:ss"),singInflag);
	}else if(singInflag ==3){
		register(getFormatDateStr(dutyTimeDesc3.getTime(),"HH:mm:ss"),singInflag);
	}else if(singInflag ==4){
		register(getFormatDateStr(dutyTimeDesc4.getTime(),"HH:mm:ss"),singInflag);
	}else if(singInflag ==5){
		register(getFormatDateStr(dutyTimeDesc5.getTime(),"HH:mm:ss"),singInflag);
	}else if(singInflag ==6){
		register(getFormatDateStr(dutyTimeDesc6.getTime(),"HH:mm:ss"),singInflag);
	}
	$("#signInButton").css("background","#cdcdcd");
	$("#signInButton").attr("disabled","true");
	
	//$(".sign_in").find(".container").slideUp();
	$('.operating_button').find('.btn2').children('.skin_peeler').hide();
}

var registerStatue= "";
//签到
function register(registerTime,on){
	var url = contextPath+"/TeeAttendDutyController/addDuty.action?on="+on+"&registerTime="+registerTime;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		//top.$.jBox.tip(json.rtMsg,"success");
		registerStatue+=","+on;
	}else{
		top.$.jBox.tip(json.rtMsg,"error");
	}
}

//获取当前用户当天已经签到的次数（从1至6）
function getRegisterStatue(){
	var url = contextPath+"/TeeAttendDutyController/getRegisterStatue.action";
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		var data  = json.rtData;  
		registerStatue = data.registerStatue;
	}else{
		alert(json.rtMsg);
	}
}

function isRegisterFunc(on){
	var flag = 0;
	var registerStatueStr = registerStatue.split(",");
	for(var i=0;i<registerStatueStr.length;i++){
		if(registerStatueStr[i] == on){
			flag = 1;
			return flag;
		}
	}
	return flag;
}

function lunarTimeCounting(){
	$("#timeDiv").html(refreshCalendarClock());
}


//高速波云平台登录
function saveAndLogin(){
	var cloudUserName = $("#cloudUserName").val();
	if(cloudUserName == ''){
		alert("账号不能为空!");
		return ;
	}
	var url = contextPath + "/personManager/updateCloudAccountAndPwd.action";
	var para =  tools.formToJson($("#cloudForm")) ;
	var jsonRs = tools.requestJsonRs(url,para);
	//alert(jsonRs);
	if(jsonRs.rtState){
		//保存并直接登录
		var data = jsonRs.rtData;
		openFullWindow(GSB_OA_CLOUD_LOGIN_URL + "?phone=" + data.accountId + "&password=" + data.accountPwd + "&from=1&flag=submit" , 'gaosubo');
	}else{
		alert(jsonRs.rtMsg);

	}
}

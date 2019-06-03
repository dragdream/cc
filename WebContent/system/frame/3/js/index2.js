
var AllMenuList = new Array();
/**
 * 获取菜单,初始化
 */
function  getMenuList(){
	var url = contextPath + "/teeMenuGroup/getPrivSysMenu.action";
	var  MENU_EXPAND_SINGLE = "0";//是否同时可以展示多个菜单 0 -多个； 1- 只有一个
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
				desc =  " <li id='menu-lv1-"+menuId+"' class='' title='" + menuName + "'>" +
						" <span style=\"background: url('" + contextPath + "/system/frame/3/icons/" + menuIcon+ "') no-repeat 18px center;\">" +
						" <a href='javascript:void(0)' menuName='" + menuName + "' menuCode='" + menuCode +"'>" + menuName +"</a>"
						+ " </span></li>";
				$("#sidebar_ul").append(desc);
				twoMenuDesc = "<div  class='two_menu'>"
					+ "<ul id='menu-lv2-"+menuId+"'>"
					+ "</ul>"
					+ "</div>";
				$("#bd").append(twoMenuDesc);
				
			}else if(menuId.length == 6){//二级菜单
				var parentMenuId = menuId.substring(0,3);
				desc2 = "<li id='menu-lv3-li-"+menuId+"'>"
					+ " <a href='javascript:void(0);'  level='" + pMenuId + "' id='" + menuId + "' menuName='" + menuName + "'menuCode='" + menuCode + "'>"+ menuName + "</a>"
					+ " </li>";
				
				  /* + '<a href="javascript:void(0)" class="list-group-item"  style="border:0px;font-size:12px" level="' + pMenuId + '" id="' + menuId +'" menuName="' + menuName + '" menuCode="' + menuCode +'">'
				   + '<i class="glyphicon glyphicon-play-circle" style="float:left;"></i>'
				   + temp
				   + '&nbsp;&nbsp;&nbsp;' + menuName + ''
				   + "<i style='clear:both;'></i>"
				   + '</a>';*/
				$("#menu-lv2-"+ parentMenuId).append(desc2);
				   //$("#menuNav2").append(desc2);
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
	//$.addTab("tabs","tabs-content",{title:"桌面",url:"个人桌面.htm",active:true});
	$.addTab("tabs","tabs-content",{title:"桌面",url:contextPath + "/system/frame/3/portal/index.jsp",active:true,cache:true});
}


/**
 * 初始化菜单触发时事件
 */
function initMenuFunc(){
	//jQuery('#menuNav2 a').css(bg);
	
	//第一级菜单 触发时事件
	jQuery('#sidebar_ul a').bind('click', function(){
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
	jQuery('#bd a').bind('click', function(){
		var menuName  = jQuery(this).attr("menuName");
		var menuId  = jQuery(this).attr("id");
		var menuCode =  jQuery(this).attr("menuCode");
		if(menuCode != ""){
			//$.addTab("tabs","tabs-content",{title:menuName,url:contextPath + menuCode,active:true,closable:true,cache:true});
			addTabFunc(menuName,menuCode);
		}
	});
}

function addTabFunc(menuName,menuCode){
	setTimeout(function(){
		$.addTab("tabs","tabs-content",{title:menuName,url:contextPath + menuCode,active:true,closable:true,cache:true});
	},100);
}




function getSkinFunc(){
	var skin = getCookie("skinChange");
	$(".skin_peeler li").removeClass("on");
	if(skin == "blue"){
		$("#header,#tabs,#footer").removeClass("green").removeClass("orange");
		$(".skin_peeler li").eq(0).addClass("on");
	}else if(skin == "orange"){
		$("#header,#tabs,#footer").removeClass("green").addClass("orange");
		$(".skin_peeler li").eq(1).addClass("on");
	}else if(skin == "green"){
		$("#header,#tabs,#footer").removeClass("orange").addClass("green");
		$(".skin_peeler li").eq(2).addClass("on");
	}else{
		$("#header,#tabs,#footer").removeClass("green").removeClass("orange");
		$(".skin_peeler li").eq(0).addClass("on");
	}
}




/**
 * 设置cookie
 * @param name 名称
 * @param value 值
 * @param Days  时间
 */
function setCookie(name , value , Days){
	var exp  = new Date();
	exp.setTime(exp.getTime() + Days*24*60*60*1000);//天数
	document.cookie = name + "="+ escape (value) + ";path=/;expires=" + exp.toGMTString();
}

/**
 *读取cookie
 *@param name 名称
 */
function getCookie(name){
   var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
   if (arr != null){
   	return unescape(arr[2]);
   }else{
   	return null;
   }
   return null;
}

var serverTime = new Date();
//获取服务器签到时间
function getServerTime(){
	var url = contextPath+"/TeeAttendConfigController/getServerTime.action";
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		alert(json.rtData);
		serverTime.setTime(json.rtData);
	}else{
		top.$.jBox.tip(json.rtMsg,"error");
	}
}
//显示系统时间
function showTimeFunc(){
	serverTime.setSeconds(serverTime.getSeconds()+1);
	var timeStr = (serverTime).pattern("HH:mm:ss");
	$("#signInTime").text(timeStr);
	window.setTimeout('showTimeFunc()',1000);
	getSignInTime();
	
}

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
			//$(".sign_in").addClass("sign_in_flash");
		}else{
			//$(".sign_in").removeClass("sign_in_flash");
		}
		//$(".sign_in_tid").show();
	}else{
		$("#signInButton").css("background","#cdcdcd");
		$("#signInButton").attr("disabled","true");
		//$(".sign_in").removeClass("sign_in_flash");
		//$(".sign_in_tid").hide();
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
	
	$(".sign_in").find(".container").slideUp();
}

/**
*
*注销
*/
function doLogout(){
	var msg = loginOutText + "\n确定要注销吗?";
	if(confirm(msg)){
		var url = contextPath + "/systemAction/doLoginout.action";
		var para = {};
		var jsonObj = tools.requestJsonRs(url,para);
		if(jsonObj){
			var json = jsonObj.rtData;
		}else{
			alert(jsonObj.rtMsg);
		}
		window.location.href = "/"+contextPath;
	}
}


/**
 * 获取在线人员
 */
function queryOnlineUserCount(){
	var url = contextPath + "/personManager/queryOnlineUserCount.action?";
	var jsonRs = tools.requestJsonRs(url);
	if(jsonRs.rtState){
		var data  = jsonRs.rtData;  
		$("#onlineUserCount").html('<span>在线（' + data.onlineUserCount + '）人</span>');
	}else{
		alert(jsonRs.rtMsg);
	}
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




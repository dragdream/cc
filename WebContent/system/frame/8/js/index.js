var AllMenuList = new Array();
/**
 * 获取菜单,初始化
 */
function  getMenuList(){
	
	//判断cookie中是否有skin_new;有则从cookie中读取,并设置皮肤和按钮;如果没有则给个默认值;
	var skin = getCookie("skin_new");
	var imgPath="";
	if(skin == "0"||skin == "1"||skin == "2"){
		$("#skin_8").attr("href","./skin/"+skin+"/style.css");
		$("#sms_box_id").attr("class","title"+skin);
		imgPath=contextPath+"/system/frame/8/images/icons/"+skin+"/";
	}else{
		$("#skin_8").attr("href","./skin/0/style.css");
		$("#sms_box_id").attr("class","title0");
		imgPath=contextPath+"/system/frame/8/images/icons/0/";
	}
		
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
		    var menuCode = sysMenu.menuCode ;
		    var uuid = $.trim(sysMenu.uuid);
		    var menuIcon = sysMenu.icon;
		    if(!menuIcon && menuIcon == ''){
		    	menuIcon = "";
		    }
		    if(i == 0){
		    	pMenuId = menuId;
		    }
		    counter ++;
		    var firstLevelCount = $("#sidebar_ul li").length;
			if(menuId.length == 3){//主菜单
				desc="<LI node-data='"+menuId+"'><A  style='cursor:pointer' "+(menuCode!=""?"title='"+menuName+"' data-open='0' data-id='"+menuId+"' data-url='"+menuCode+"' onclick='P.headNavBar.openApp(this)'":"")+"><SPAN>"+menuName+"</SPAN></A></LI>";
				if( firstLevelCount<5){
					$("#sidebar_ul").append(desc);
				}else{
					$("#sidebar_more").append(desc);
				}
			}else{//二级或者三级菜单
				var parentMenuId = menuId.substring(0,3);
				if(menuId.length==6 && menuCode==""){//如果是二级菜单的话，如果没有超链接，则不显示
					return;
				}
				desc2 = "<DIV class='header_sub_main clearfix' node-type='appSubNode'"
						+"node-data='"+parentMenuId+"' style='display:inline-block;'>"
						+"<A title='"+menuName+"' class='nllink'"
						+"	onclick='P.headNavBar.openApp(this)' style='cursor:pointer'"
						+"	data-open='0' data-id='"+menuId+"' data-url='"+menuCode+"'>"
//						+"	<DIV class='icon-"+uuid+"'></DIV>"
						+"	<DIV class='icon iconsDiv'  title='"+menuName+"' icon_name='"+menuIcon+"' style='background:url(\""+imgPath+menuIcon+"\") no-repeat  center'></DIV>"
						+"	<SPAN>"+menuName+"</SPAN>"
						+"	</A>"
						+"	</DIV>";
				$("#secondMenu").append(desc2);
				$("#secondMenuDetail").append(desc2);
			}
		});
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
/***
 * 获取组织机构信息
 */
var isClickUser = false;
var orgInfoId = "orgInfoId";
function getORGInfo(){
     var tp = { left:"240px "};
     $("#orgInfoId .arrow").css( tp);//调整位置
    
	if(!isClickUser){
		$("#closeOrg").append('<i class="glyphicon glyphicon-remove"/>');
		$("#closeOrg").click(function(){
			$("#" + orgInfoId).hide();
		});
		onlineUser();
		isClickUser = true;
	}
	//隐藏或者显示
	if($("#" + orgInfoId).is(':hidden')){
	    $("#" + orgInfoId).show();
	}else{
		$("#" + orgInfoId).hide();
	}
}




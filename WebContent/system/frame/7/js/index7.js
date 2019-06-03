/**
 * 获取菜单,初始化
 */
function  getMenuList(){
	
	var url = contextPath + "/teeMenuGroup/getPrivSysMenu.action";
	var jsonObj = tools.requestJsonRs(url);
	var desc = "";
	var desc2 = "";
	
	var twoMenuDesc = "";
	var threeMenuDesc = "";
	
	var pMenuId = "";
	if(jsonObj.rtState){
		var json = jsonObj.rtData;
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
		    //<img src='images/sidebar/nav-1.png' ></img>
			if(menuId.length == 3){//主菜单
				desc =  " <li id='menu-lv1-"+menuId+"' title='" + menuName + "'>"
						+ " 	<span >"
						+ " 		<img style='margin-top:5px' src='" + contextPath + "/system/frame/7/icons/" + menuIcon+ "' ></img>" 
						+ " 	</span>" 
						+ " 	<a href='javascript:void(0)' menuName='" + menuName + "' menuCode='" + menuCode +"'>" + menuName +"</a>"
						+ "</li>";
				$("#sidebar_nav_ul").append(desc);
				
				twoMenuDesc = "<div  class='two_menu'>"
							+ "		<ul id='menu-lv2-"+menuId+"'></ul>"
							+ "</div>";
				$("#twoMenu").append(twoMenuDesc);
				
			}else if(menuId.length == 6){//二级菜单
				
				var parentMenuId = menuId.substring(0,3);
				desc2 = "<li id='menu-lv3-li-"+menuId+"'>"
					+ " 	<a href='javascript:void(0);'  level='" + pMenuId + "' id='" + menuId + "' menuName='" + menuName + "'menuCode='" + menuCode + "'>"+ menuName + "</a>"
					+ " </li>";
				$("#menu-lv2-"+ parentMenuId).append(desc2);
				
			}else if(menuId.length == 9){//三级菜单
				
				var secondMenuId = menuId.substring(0,6);
				var three_ul = $("#menu-lv3-ul-"+secondMenuId);
				if(three_ul.length==0){
					var three_li = $("#menu-lv3-li-"+secondMenuId);
					three_li.addClass("submenu");
					var menuName1 = three_li.children("a:first").html();
					three_li.html("<h6>"+menuName1+"</h6>");
					
					threeMenuDesc = "<div  class='three_menu'>"
								+ "		<ul id='menu-lv3-ul-"+secondMenuId+"'></ul>"
								+ "</div>";
					three_li.append(threeMenuDesc);
					
				}
				desc2 = "<li>"
					+ " 	<a href='javascript:void(0);' level='" + secondMenuId + "' id='" + menuId + "' menuName='" + menuName + "'menuCode='" + menuCode + "'>"+ menuName + "</a>"
					+ " </li>";
				$("#menu-lv3-ul-"+secondMenuId).append(desc2);
				
			}
		});
	}
	$.addTab("tabs","tabs-content",{title:"桌面",url:contextPath + "/system/frame/default/mainForSimple.jsp",active:true,cache:true});
	//$.addTab("tabs","tabs-content",{title:"桌面",url:"个人桌面.htm",active:true});
	initMenuFunc();
	remindCheck();
}


function remindCheck(){
	tools.requestJsonRs(contextPath+"/sms/remindCheck.action",{},true,function(json){
		if(json.rtState){
			if(json.rtData.smsFlag!=0){//弹出短消息
				if(autoPopSms=="0"){
					$('.sign_in_tid').show();
				}else{
					smsAlert();
				}
			}
			if(json.rtData.msgFlag!=0){//弹出通讯消息
				var offlineMsgJson = tools.requestJsonRs(contextPath+"/messageManage/getOfflineMessages.action");
				for(var i=0;i<offlineMsgJson.rtData.length;i++){
					socketHandler(offlineMsgJson.rtData[i]);
				}
			}
		}
		setTimeout("remindCheck()",1000*10);//10秒
	});
}




/**
 * 初始化菜单触发时事件
 */
function initMenuFunc(){
	//jQuery('#menuNav2 a').css(bg);
	
	//第一级菜单 触发时事件
	jQuery('#sidebar_nav_ul a').bind('click', function(){
		var menuName  = jQuery(this).attr("menuName");
		var menuId  = jQuery(this).attr("id");
		var menuCode =  jQuery(this).attr("menuCode");
		if(menuCode != "" && menuCode!= 'undefined'){
			addTabFunc(menuName,menuCode);
		}
	});
	//二级和三级菜单触发事件
	jQuery('#twoMenu a').bind('click', function(){
		var menuName  = jQuery(this).attr("menuName");
		var menuId  = jQuery(this).attr("id");
		var menuCode =  jQuery(this).attr("menuCode");
		if(menuCode != ""){
			addTabFunc(menuName,menuCode);
		}
	});
}



























/**
 * 添加菜单Tab页
 * @param menuName
 * @param menuCode
 */
function addTabFunc(menuName,menuCode){
	setTimeout(function(){
		$.addTab("tabs","tabs-content",{title:menuName,url:contextPath + menuCode,active:true,closable:true,cache:true});
	},100);
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


















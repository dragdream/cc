
/**
 * 初始化bootstrap各种事件
 */
function initBootstrapFunc(){
	//前期与时间
	$("#weatherAndDate").popover();
	$("#orgInfo").popover();

	queryMenuType();
	

}


/**
 * 获取在线人员
 */
function queryOnlineUserCount(){
	var url = contextPath + "/personManager/queryOnlineUserCount.action?";
	var jsonRs = tools.requestJsonRs(url);
	if(jsonRs.rtState){
		var data  = jsonRs.rtData;
		$("#onlineUserCount").html("<span style='font-size:12px'>当前在线人数：" + data.onlineUserCount + "人</span>");
	}else{
		alert(jsonRs.rtMsg);
	}
}

/**
 * 初始化查询类型
 */
function queryMenuType(){
	$("#queryMenuType li a").click(function(even){
		$("#queryType").attr("title",$(this).html());//复制title属性
		$("#queryType").html($(this).html() + "<span class='caret'></span>");
	});
	

}
/**
 * 点击查询按钮
 */
function queryI(index){
	var queryType = $("#queryType").attr("title");;
	var queryInfo = $("#queryInfo")[0].value;
	if(queryInfo == ''){
		
		$("#menuQuery").html("<li><a href=\"#\" >请输入信息</a></li>");
		return;
	}
	queryType = "用户";
	if(queryType == '用户'){
		var url = contextPath + "/orgSelectManager/queryUserByUserIdOrUserName.action";
		var jsonRs = tools.requestJsonRs(url , {userName: queryInfo});
		if(jsonRs.rtState){
			var dataList  = jsonRs.rtData;
			var dataStr = "";
			for(var i = 0; i <dataList.length ; i++){
				///dataStr = dataStr + "<li><a href=\"#\" data-toggle=\"modal\" data-target=\"#myModal\" onclick='toUserInfo(" + dataList[i].uuid + ");'>" + dataList[i].userName + "</a></li>";
				dataStr = dataStr + "<li><a href=\"#\"  onclick='toUserInfo(" + dataList[i].uuid + ");'>" + dataList[i].userName + "</a></li>";
				
			}
			if(dataStr == "" ){
				dataStr =  "<li><a href=\"#\"  >未查到相关信息</a></li>";
			}
			$("#menuQuery").html(dataStr);
			//$("#menu1")[0].style.display = "block";
			//$("#onlineUserCount").html("共" + data.onlineUserCount + "人在线");
		}else{
			alert(jsonRs.rtMsg);
		}
	}else{
		
		$("#menu" + index).html("<li><a href=\"#\" >该模块查询开发中...</a></li>");
		return false;
	}
	
}


/**
 * 弹出人员信息界面
 * @param uuid
 */
function toUserInfo(uuid){
	//$('#myModal').modal();
	var url = contextPath +  "/system/core/person/userinfo.jsp?uuid=" + uuid;
	var url = contextPath +  "/system/core/person/userinfo.jsp?uuid=" + uuid;
	top.bsWindow(url ,"用户详细信息",{width:"800",height:"300",buttons:[
	    {name:"关闭",classStyle:"btn btn-primary"}
	    ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			//cw.commit();
		
			if(isStatus){
				getCalendar();
				return true;
			}
		}else if(v=="关闭"){
			return true;
		}
	}});
}


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
	var thirdMenuArray = new Array();
	var pMenuId = "";
	if(jsonObj.rtState){
		var json = jsonObj.rtData;
		AllMenuList =json;
		jQuery.each(json, function(i, sysMenu) {
			var menuId = sysMenu.menuId;
		    var menuName = sysMenu.menuName;
		    var  menuCode = sysMenu.menuCode ;
		    var menuIcon = sysMenu.icon;
		    if(!menuIcon && menuIcon == ''){
		    	menuIcon = "glyphicon glyphicon-list";
		    }
		    if(i == 0){
		    	pMenuId = menuId;
		    }
			if(menuId.length == 3){//主菜单
				desc =  " <li id='menu-lv1-"+menuId+"' class='menuItem'>" +
						"<a href='#' onclick='getSecondMenu(\""+menuId+"\")' menuName='" + menuName + "' menuCode='" + menuCode +"'>" +
						"<i class='" + menuIcon + "'></i>" + 
					    "<span class='menu-text'  style='font-size:14px;font-family:' >" +menuName+ "</span>" + 
					    "<b class=''></b>" 
					    +"</a></li>";
				$("#menuNav").append(desc);
			}else if(menuId.length == 6 &&  (menuId.substring(0,3) == pMenuId) ){//主菜单
				var temp = '<i class="glyphicon glyphicon-chevron-right creat pull-right"></i>';
				if(menuCode != ""){
					temp = 	'';
				}
				desc2 = ""
				   + '<a href="javascript:void(0)" class="list-group-item"  style="border:0px;font-size:12px" level="' + pMenuId + '" id="' + menuId +'" menuName="' + menuName + '" menuCode="' + menuCode +'">'
				   + '<i class="glyphicon glyphicon-play-circle" style="float:left;"></i>'
				   + temp
				   + '&nbsp;&nbsp;&nbsp;' + menuName + ''
				   + "<i style='clear:both;'></i>"
				   + '</a>';
				$("#menuNav2").append(desc2);
			}else if(menuId.length == 9 &&  (menuId.substring(0,3) == pMenuId) ){//主菜单
				var secondMenuId = menuId.substring(0,6);
				var thirdTempDesc  =  '<a href="javascript:void(0)" class="list-group-item"  style="border:0px;display:none;font-size:12px" level="' + secondMenuId + '"  id="' + menuId +'" menuName="' + menuName + '" menuCode="' + menuCode +'">'
				   + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class="glyphicon glyphicon-play-circle" ></i>'
				   + '&nbsp;&nbsp;&nbsp;' + menuName
				   + '</a>';
				var thirdTemp = {menuId : menuId , menuDesc:thirdTempDesc};
				var tempLevel = $("#" + secondMenuId).nextAll("[level='"+ pMenuId+"']");
				if(tempLevel.length > 0){
					$(tempLevel[0]).before(thirdTempDesc);
				}else{//如果没有则在上级后面添加
					$("#" + secondMenuId).after(thirdTempDesc);
							
				} 
			}
		});
	}
	

	/*for(var i = 0 ; i<thirdTemp.length ; i++){
		
	}*/
	
	//点击菜单
	jQuery('ul.nav li.menuItem a').bind('click', function(){
		//alert(jQuery(this).attr('class').indexOf('active'));
		
		/* if(MENU_EXPAND_SINGLE == '1'){//是否同时可以展示多个菜单 0 -多个； 1- 只有一个
			jQuery(this).parent().siblings().removeClass('active open');
		}else{
			jQuery(this).parent().siblings().removeClass('active open');
		} */
		jQuery(this).parent().siblings().removeClass('active open');
		if(jQuery(this).parent().attr('class').indexOf('active') < 0 ){
			jQuery(this).parent().addClass('active open');
		}else{
			jQuery(this).parent().removeClass('active open');
		}
	});
	initMenuFunc();
	var temp = {desc:desc , desc2 : desc2};
	return temp;
}

/**
 * 获取第二级菜单
 * @param pMenuId
 * @returns {String}
 */
function getSecondMenu(pMenuId){

	var desc2  = "";
	var desc3 = "";
	$("#menuNav2").empty();
	jQuery.each(AllMenuList, function(i, sysMenu) {
		var menuId = sysMenu.menuId;
	    var menuName = sysMenu.menuName;
	    var  menuCode = sysMenu.menuCode ;
	    var menuIcon = sysMenu.icon;
	    if(!menuIcon && menuIcon == ''){
	    	menuIcon = "glyphicon glyphicon-list";
	    }
		if(menuId.length == 6 &&  (menuId.substring(0,3) == pMenuId) ){//主菜单
			var temp = '<i class="glyphicon glyphicon-chevron-right pull-right"></i>';
			if(menuCode != ""){
				temp = 	'';
			}
			desc2 = ""
			   + '<a href="javascript:void(0)" class="list-group-item"  style="border:0px;font-size:12px" level="' + pMenuId + '" id="' + menuId +'" menuName="' + menuName + '" menuCode="' + menuCode +'">'
			   + '<i class="glyphicon glyphicon-play-circle"  style="float:left;" ></i>'
			   + temp
			   + '&nbsp;&nbsp;&nbsp;' + menuName
			   + "<i style='clear:both;'></i>"
			   + '</a>';
			$("#menuNav2").append(desc2);
		}else if(menuId.length == 9 &&  (menuId.substring(0,3) == pMenuId) ){//主菜单
			var secondMenuId = menuId.substring(0,6);
			var thirdTempDesc  =  '<a href="javascript:void(0)" class="list-group-item"  style="border:0px;display:none;font-size:12px" level="' + secondMenuId + '"  id="' + menuId +'" menuName="' + menuName + '" menuCode="' + menuCode +'">'
			   + '&nbsp;&nbsp;&nbsp;<i class="glyphicon glyphicon-play-circle" ></i>'
			   + '&nbsp;&nbsp;&nbsp;' + menuName
			   + '</a>';
			var thirdTemp = {menuId : menuId , menuDesc:thirdTempDesc};
			var tempLevel = $("#" + secondMenuId).nextAll("[level='"+ pMenuId+"']");
			if(tempLevel.length > 0){
				$(tempLevel[0]).before(thirdTempDesc);
			}else{//如果没有则在上级后面添加
				$("#" + secondMenuId).after(thirdTempDesc);
						
			} 
		}
	});
	//$("#menuNav2").append(  desc2 );
	initMenuFunc();
	return desc2;
}

/**
 * 初始化菜单
 */
function initMenuFunc(){
	//jQuery('#menuNav2 a').css(bg);
	jQuery('#menuNav2 a').bind('click', function(){
		//之前选中的对象
		var selectMenu = jQuery('#menuNav2 a.active')[0];
		if(selectMenu && (selectMenu != this)){
			var selectMenuName  = jQuery(selectMenu).attr("menuName");
			var selectMenuId  = jQuery(selectMenu).attr("id");
			var selectMenuCode =  jQuery(selectMenu).attr("menuCode");
			//隐藏
			jQuery('#menuNav2').find("[level='"+ selectMenuId+"']").hide();
		}
		jQuery('#menuNav2 a').removeClass("active");
		
		$(this).addClass("active");
		var menuName  = jQuery(this).attr("menuName");
		var menuId  = jQuery(this).attr("id");
		var menuCode =  jQuery(this).attr("menuCode");
		if(menuCode != ""){
			toSrcUrl(menuName,menuCode , true);
		}else{
			
			if(jQuery("#menuNav2 a[level='"+ menuId+"']").is(":hidden")){
				jQuery("#menuNav2 a[level='"+ menuId+"']").show();
			}else{
				jQuery("#menuNav2 a[level='"+ menuId+"']").hide();
			}
		}
		
	});
	
	//第一级菜单 出发时事件
	jQuery('#menuNav a').bind('click', function(){
		var menuName  = jQuery(this).attr("menuName");
		var menuId  = jQuery(this).attr("id");
		var menuCode =  jQuery(this).attr("menuCode");
		if(menuCode != "" && menuCode!= 'undefined'){
			toSrcUrl(menuName,menuCode , true);
		}
	});
	
}
/**
 * 转转页面
 */
function toSrcUrl(menuName,menuCode , isHide){
	
	if(menuCode && menuCode != ''){
		addTabs(menuName,contextPath + menuCode);
		if(isHide){
			hideMenuInfo();//隐藏菜单
		}
	}

}

var isClickUser = false;
/***
 * 获取组织机构信息
 */
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



/**
 * 获取在线人员
 */
function getOnlineUser(){
	var url = contextPath + "/orgManager/checkOrg.action";
	var jsonObj = tools.requestJsonRs(url);
	//alert(jsonObj);
	if(jsonObj.rtState){
		var json = jsonObj.rtData;
		if(json.sid){
			var url = contextPath + "/orgSelectManager/getOnlineOrgUserTree.action";
			var config = {
				zTreeId:"orgUserZtree",
				requestURL:url,
				param:{"para1":"111"},
				onClickFunc:personOnClick,
				async:false,
				onRightClickFunc:onRightClickFunc,
				onAsyncSuccess:callBackOrgFunc
		};

		zTreeObj = ZTreeTool.config(config);
			//expandNodes(zTreeObj);
		}else{
			alert("单位信息未录入，请您先填写单位信息！");
			return;
		}
	}
}


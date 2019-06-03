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
		$("#onlineUserCount").html('<span>当前<a href="javascript:;" class="a_color1" >' + data.onlineUserCount + '</a>人在线</span>');
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
//	var queryType = $("#queryType").attr("title");;
//	var queryInfo = $("#queryInfo")[0].value;
//	if(queryInfo == ''){
//		
//		$("#menuQuery").html("<li><a href=\"#\" >请输入信息</a></li>");
//		return;
//	}
//	queryType = "用户";
//	if(queryType == '用户'){
//		var url = contextPath + "/orgSelectManager/queryUserByUserIdOrUserName.action";
//		var jsonRs = tools.requestJsonRs(url , {userName: queryInfo});
//		if(jsonRs.rtState){
//			var dataList  = jsonRs.rtData;
//			var dataStr = "";
//			for(var i = 0; i <dataList.length ; i++){
//				///dataStr = dataStr + "<li><a href=\"#\" data-toggle=\"modal\" data-target=\"#myModal\" onclick='toUserInfo(" + dataList[i].uuid + ");'>" + dataList[i].userName + "</a></li>";
//				dataStr = dataStr + "<li><a href=\"#\"  onclick='toUserInfo(" + dataList[i].uuid + ");'>" + dataList[i].userName + "</a></li>";
//				
//			}
//			if(dataStr == "" ){
//				dataStr =  "<li><a href=\"#\"  >未查到相关信息</a></li>";
//			}
//			$("#menuQuery").html(dataStr);
//			//$("#menu1")[0].style.display = "block";
//			//$("#onlineUserCount").html("共" + data.onlineUserCount + "人在线");
//		}else{
//			alert(jsonRs.rtMsg);
//		}
//	}else{
//		
//		$("#menu" + index).html("<li><a href=\"#\" >该模块查询开发中...</a></li>");
//		return false;
//	}
	
}


/**
 * 弹出人员信息界面
 * @param uuid
 */
function toUserInfo(uuid){
	//top.msgFrame.createDlg(userId);
	//openPostWindow(contextPath +  "/system/core/person/userinfo.jsp",{uuid:uuid},"_blank");
	dialog(contextPath+"/system/core/person/userinfo.jsp?uuid="+uuid,450,160);
}

/**
 * 邮件
 * @param type:类型，ifBox:分类箱id
 */
function toEmailDetail(id){
	var url = contextPath + "/system/core/email/readSmsEmailBody.jsp?sid="+id;
	openFullWindow(url);
}

function toFlowRunDetail(runId,flowId){
	var url = contextPath+"/system/core/workflow/flowrun/print/index.jsp?runId="+runId+"&view=15&flowId="+flowId;
	openFullWindow(url);
}


/**
 * 获取菜单,初始化
 */
function  getMenuList(){
	var url = contextPath + "/teeMenuGroup/getPrivSysMenu.action";
	var  MENU_EXPAND_SINGLE = "0";//是否同时可以展示多个菜单 0 -多个； 1- 只有一个
	var jsonObj = tools.requestJsonRs(url);
	if(jsonObj.rtState){
		var json = jsonObj.rtData;
		var temp = 0;
		jQuery.each(json, function(i, sysMenu) {
			var menuId = sysMenu.menuId;
		    var menuName = sysMenu.menuName;
		    var  menuCode = sysMenu.menuCode ;
		    var menuIcon = sysMenu.icon;
		    if(!menuIcon && menuIcon == ''){
		    	menuIcon = "glyphicon glyphicon-list";
		    }
			if(menuId.length == 3){//主菜单
			
				$("#menuNav").append(" <li id='menu-lv1-"+menuId+"' class='menuItem'>" +
						"<a href='javascript:void(0);'>" +
						"<i class='" + menuIcon + "'></i>" + 
					    "<span class='menu-text'  style='font-size:14px;font-family:'>" +menuName+ "</span>" + 
					    "<b class='arrow icon-angle-down'></b>" 
					    +"</a></li>");
				
			}else if(menuId.length == 6){//二级菜单
				var parentMenuId = menuId.substring(0,3);
				//alert($("#menu-lv1-" + parentMenuId)[0])
				if($("#menu-lv1-" + parentMenuId + " ul")[0]){
					$("#menu-ul-lv2-" + parentMenuId).append(
							"<li class='menuItem'  id='menu-lv2-"+menuId+"'>" +
							"<a href='javascript:void(0);'>" +
							"<i class='icon-double-angle-right'></i>"+
							"<i class='glyphicon glyphicon-plus' style='opacity:0;filter:alpha(0);'></i>&nbsp;"+
			            	 "<span style='font-family:'>"+menuName +"</span>"+ 
			            	"</a>" + 
							"</li>" 
							);
				}else{
					$("#menu-lv1-" + parentMenuId).append("<ul class='submenu'  id='menu-ul-lv2-"+parentMenuId+"'>" +
							"<li class='menuItem' id='menu-lv2-"+menuId+"'>" +
							"<a href='javascript:void(0);'>" +
							"<i class='icon-double-angle-right'></i>"+
							"<i class='glyphicon glyphicon-plus' style='opacity:0;filter:alpha(0);'></i>&nbsp;"+
							"<span style='font-family:'>"+menuName +"</span>"+ 
			            	"</a>" + 
							"</li>" + 
							"</ul>");
				}
				
					
				$("#menu-lv2-" + menuId).children('a').bind("click",function(){
					 toSrcUrl(menuName,menuCode);
				});
			}else if(menuId.length == 9){//三级菜单
				var parentMenuId = menuId.substring(0,6);
				$("#menu-lv2-"  + parentMenuId+" .glyphicon-plus").css({opacity:1});//更改二级菜单样式
				if($("#menu-lv2-" + parentMenuId + " ul")[0]){
					$("#menu-ul-lv3-" + parentMenuId).append(
							"<li class='menuItem'  id='menu-lv3-"+menuId+"'>" +
							"<a href='javascript:void(0);'>" +
							"<i class='icon-leaf'></i>&nbsp;&nbsp;"+
							"<span style='font-family:;font-size:12px'>"+menuName +"</span>"+ 
			            	"</a>" + 
							"</li>" 
							);
				}else{
					$("#menu-lv2-" + parentMenuId).append("<ul class='submenu' id='menu-ul-lv3-"+parentMenuId+"'>" +
							"<li class='menuItem'  id='menu-lv3-"+menuId+"'>" +
							"<a href='javascript:void(0);'>" +
							"<i class='icon-leaf'></i>&nbsp;&nbsp;"+
							"<span style='font-family:;font-size:12px'>"+menuName +"</span>"+ 
			            	"</a>" + 
							"</li>" + 
							"</ul>");
				}
				
				$("#menu-lv3-" + menuId).children('a').bind("click",function(){
					 toSrcUrl(menuName,menuCode);
				});
			}
			
	
		});
		MENU_EXPAND_SINGLE = jsonObj.rtMsg;
	}
	return MENU_EXPAND_SINGLE;
}




/**
 * 获取菜单,初始化
 */
function  getMenuListNew(){
	var url = contextPath + "/teeMenuGroup/getPrivSysMenu.action";
	var  MENU_EXPAND_SINGLE = "0";//是否同时可以展示多个菜单 0 -多个； 1- 只有一个
	var jsonObj = tools.requestJsonRs(url);
	if(jsonObj.rtState){
		var json = jsonObj.rtData;
		var temp = 0;
		jQuery.each(json, function(i, sysMenu) {
			var menuId = sysMenu.menuId;
		    var menuName = sysMenu.menuName;
		    var  menuCode = sysMenu.menuCode ;
		    var menuIcon = sysMenu.icon;
		    if(!menuIcon && menuIcon == ''){
		    	menuIcon = "glyphicon glyphicon-list";
		    }
		    var tempDesc = "";
			if(menuId.length == 3){//主菜单
				if(temp==0){
					tempDesc = "style='margin-top:0;'";
				}
				$("#menu_list_obj").append(" <li id='menu-lv1-"+menuId+"' class=''>" +
						"<a href='javascript:void(0);' " + tempDesc + " class='yjcd' style=''>" +
						"<span></span>"+ menuName + " </a>"  
					     +"</li>");
			
				temp++;
				if(menuCode && menuCode != ''){
					$("#menu-lv1-" + menuId).children('a').bind("click",function(){
						 toSrcUrl(menuName,menuCode);
					});
				}
				
			}else if(menuId.length == 6){//二级菜单
				var parentMenuId = menuId.substring(0,3);
				//alert($("#menu-lv1-" + parentMenuId)[0])
				
				
				if($("#menu-lv2-ul-"+parentMenuId)[0]){
					$("#menu-lv2-ul-"+parentMenuId).append("<li   id='menu-lv2-"+menuId+"'>" +
					"<a href='javascript:void(0);' class='ejcd' ><span style='opacity:0;filter:alpha(opacity=0)'></span>" +
					 menuName + " </a>" + 
					"</li>");
				}else{
					$("#menu-lv1-" + parentMenuId).append(
							"<ul class='child'  id='menu-lv2-ul-"+parentMenuId+"'><li   id='menu-lv2-"+menuId+"'>" +
							"<a href='javascript:void(0);' class='ejcd' ><span style='opacity:0;filter:alpha(opacity=0)'></span>" +
							 menuName + " </a>" + 
							"</li></ul>" 
							);
				}
				
					
				$("#menu-lv2-" + menuId).children('a').bind("click",function(){
					 toSrcUrl(menuName,menuCode);
				});
			}else if(menuId.length == 9){//三级菜单
				var parentMenuId = menuId.substring(0,6);
				//$("#menu-lv2-"  + parentMenuId+" .glyphicon-plus").css({opacity:1});//更改二级菜单样式
				if($("#menu-lv3-ul-"+parentMenuId)[0]){
					$("#menu-lv3-ul-" + parentMenuId).append(
							"<li   id='menu-lv3-"+menuId+"'>" +
							"<a href='javascript:void(0);' class='sjcd' >" +
							 menuName + " </a>" + 
							"</li>"
							);
				}else{
					$("#menu-lv2-" + parentMenuId).append(
							"<ul class='child'  id='menu-lv3-ul-"+parentMenuId+"'><li   id='menu-lv3-"+menuId+"'>" +
							"<a href='javascript:void(0);' class='sjcd' >" +
							 menuName + " </a>" + 
							"</li></ul>" 
							);	
				}
			
				$("#menu-lv2-" + parentMenuId).children('a').each(function(i, n){
					 var innerText = $(n).text();
//					 $(this).empty();
//					 $(this).append("<span></span>" +innerText );
					 $(this).find("span:first").css({opacity:1});
				});
				$("#menu-lv3-" + menuId).children('a').bind("click",function(){
					 toSrcUrl(menuName,menuCode);
				});
			}
		});
		MENU_EXPAND_SINGLE = jsonObj.rtMsg;
	}
	
	$('#Demo').perfectScrollbar();
	$('#Demo').perfectScrollbar('update');
	$(".menu_list").find("li:has(ul)").children("a").click(function(){
		
		if($(this).next("ul").is(":hidden"))
		{
			if(MENU_EXPAND_SINGLE == '1'){//是否同时可以展示多个菜单 0 -多个； 1- 只有一个
				jQuery(this).parent().siblings().children("ul").css("display","none");
			}
			$(this).next("ul").css("display","block");
			var demo=$("#Demo").height();
			var content=$(".content").height();
			if(content>demo)
			{
				$('#Demo').perfectScrollbar();
				$('#Demo').perfectScrollbar('update');
				$('.ps-scrollbar-y').css('display','block');
			}
			return false;
		}
		else
		{
			$(this).next("ul").css("display","none"); 
			$(this).next("ul").children("li").find("ul").css("display","none");
			var demo=$("#Demo").height();
			var content=$(".content").height();
			if(content<demo)
			{
				$('.ps-scrollbar-y').css('display','none');
			}
			return false;
		}
	});
	return MENU_EXPAND_SINGLE;
}

/**
 * 转转页面
 */
function toSrcUrl(menuName,menuCode , isHide){
	
	if(menuCode && menuCode != ''){
		setTimeout(function(){
			//addNewTabs(menuName,contextPath + menuCode);
			$("#iframe0").attr("src",contextPath + menuCode);
			if(isHide){
				hideMenuInfo();//隐藏菜单
			}
		},100);
		
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


function queryPerson(){
	var url =   contextPath+"/orgSelectManager/queryUserByUserIdOrUserName.action";
	var para = {userName:$("#queryInfo").val()};
	var jsonObj = tools.requestJsonRs(url , para);
	if(jsonObj.rtState){
		var prcs = jsonObj.rtData;
		if(prcs.length > 0){
			var render = ["<ul class='search_ul'>"];
			for(var i =0 ; i< prcs.length ; i++){
				 var prc = prcs[i];
				 var sid = prc.sid;
				 render.push("<li onclick=\"toUserInfo('"+prc.sid+"')\">"+prc.userName+"<span style='color:#a9a9a9'>（"+prc.deptName+"）</span></li>");
			}
			render.push("</ul>");
			$("#searchDataDiv").html(render.join("")) ;
		}else{
			$("#searchDataDiv").html("<center>未查到相关用户！</center>") ;
		}
	}else{
		alert(jsonObj.rtMsg);
	}
}

function queryEmail(){
	var url =   contextPath+"/mail/selectMailForQueryIndex.action";
	var para = {key:$("#queryInfo").val(),maxSize:100};
	var jsonObj = tools.requestJsonRs(url , para);
	if(jsonObj.rtState){
		var prcs = jsonObj.rtData;
		if(prcs.length > 0){
			 var render = ["<ul class='search_ul'>"];
			 for(var i =0 ; i< prcs.length ; i++){
				 var prc = prcs[i];
				 var sid = prc.sid;
				 render.push("<li onclick='toEmailDetail(" +prc.sid+")'>"+ prc.subject +"</li>");
			 }
			 render.push("</ul>");
			 $("#searchDataDiv").html(render.join("")) ;
		}else{
			 $("#searchDataDiv").html("<center>未查到相关邮件！</center>") ;
		}
	}else{
		alert(jsonObj.rtMsg);
	}
}

/**
 * 查询公共网盘
 */
function queryFile(){
	var url =   contextPath+"/fileNetdisk/queryFileNetdiskByName.action";
	var para = {fileName:$("#queryInfo").val()};
	var jsonObj = tools.requestJsonRs(url , para);
	if(jsonObj.rtState){
		var prcs = jsonObj.rtData;
		if(prcs.length > 0){
			var render = ["<ul class='search_ul'>"];
			 for(var i =0 ; i< prcs.length ; i++){
				 var prc = prcs[i];
				 var sid = prc.sid;
				 render.push("<li class='fileItem' priv='" + 3 + "' sid='" + prc.attachSid +"' fileName='"+prc.fileName+"' fileExt='"+prc.fileExt+"'></li>");
			 }
			 render.push("</ul>");
			 $("#searchDataDiv").html(render.join("")) ;
			 $(".fileItem").each(function(i,obj){
					var attachModel = {fileName:$(obj).attr("fileName"),
					priv:parseInt($(obj).attr("priv"))
					,ext:$(obj).attr("fileExt"),sid:$(obj).attr("sid")};
					var fileItem = tools.getAttachElement(attachModel,{});
					$(obj).append(fileItem);
			});
		}else{
			 $("#searchDataDiv").html("<center>未查到相关网盘文件信息！</center>") ;
		}
	}else{
		alert(jsonObj.rtMsg);
	}
}


function queryWorkFlow(){
	var url =   contextPath+"/workflow/quickSearch.action";
	var para = {psnName:$("#queryInfo").val()};
	var jsonObj = tools.requestJsonRs(url , para);
	if(jsonObj.rtState){
		var prcs = jsonObj.rtData;
		if(prcs.length > 0){
			var render = ["<ul class='search_ul'>"];
			 for(var i =0 ; i< prcs.length ; i++){
				 var prc = prcs[i];
				 var sid = prc.sid;
				 render.push("<li onclick='toFlowRunDetail(" +prc.runId + "," + prc.flowId + ",0)'><span style='color:#a9a9a9'>"+prc.runId+"</span>&nbsp;"+prc.runName+"</li>");
			 }
			 render.push("</ul>");
			 $("#searchDataDiv").html(render.join("")) ;
		}else{
			 $("#searchDataDiv").html("<center>未查到相关流程！</center>") ;
		}
	}else{
		alert(jsonObj.rtMsg);
	}
}

var KEY_ENTER = 0X000D;
/**
 * 处理键盘按键press事件 ----  查询
 */
//function documentKeypress(e){
//  var id = document.activeElement.id;
//  if (id != 'queryInfo'){
//    return;
//  }
//  
//  var currKey = 0;
//  var e = e || event;
//  currKey = e.keyCode || e.which || e.charCode;
//  //alert(queryInfo +":"+ currKey + ":"+ (currKey == KEY_ENTER))
//  if (currKey == KEY_ENTER && $("#queryInfo").val() != '' ){
//	  queryI(1);
//	 $('#queryInfo').dropdown();//
//
//  }
//}
//document.onkeypress = documentKeypress;


/**
 * 获取模块待办数量
 */
function getModelHandCount(){
	var url = contextPath + "/systemAction/getModelHandCount.action";
	var jsonObj = tools.requestJsonRs(url);
	if(jsonObj.rtState){
		var prc = jsonObj.rtData;
	/*	$("#calendarCount").html("("+prc.calendarCount+")");
		$("#emailCount").html(prc.emailCount);*/
		$("#smsCount").html("("+ prc.smsCount +")");
		//$("#messageCount").html("("+prc.messageCount+")");
		var attendDutyInfo = prc.attendDutyInfo;//考勤
		$("#onDuty").html(attendDutyInfo.dutyStartTime);
		$("#offDuty").html(attendDutyInfo.dutyEndTime);
		
		

	}else{
		alert(jsonObj.rtMsg);
	}
}

//消息回调函数
function socketHandlerInterface(json){
	if(json.t==QType.USER_ONLINE || json.t==QType.USER_OFFLINE){
		queryOnlineUserCount();
		try{
			$("#iframe1")[0].contentWindow.location.reload();
		}catch(e){
			
		}
	}else if(json.t=QType.SMS_HANDLE){
		getModelHandCount();
		$("#msgImg").attr("src",contextPath+"/system/frame/default/images/msg_changing.gif");
		setTimeout(function(){
			$("#msgImg").attr("src",contextPath+"/system/frame/default/images/icon_msg.png");
		},10000);
	}
}
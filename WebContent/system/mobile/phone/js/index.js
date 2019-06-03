
/**
 * 跳转至登录界面
 */
function goLogin(){
	
}

/**
 * 返回主界面
 */
function goHome(){
	window.location.href = mobilePath + "/phone/system/index.jsp";
}

/**
 * 返回新闻主界面
 */
function goNewsHome(){
	window.location.href = mobilePath + "/phone/news/index.jsp";
}

/**
 * 返回公告主界面
 */
function goNotifyHome(){
	window.location.href = mobilePath + "/phone/notify/index.jsp";
}

/**
 * 返回日志主界面
 */
function goDiaryHome(){
	window.location.href = mobilePath + "/phone/diary/index.jsp";
}
/**
 * 返回邮件主界面
 */
function goEmailHome(){
	window.location.href = mobilePath + "/phone/email/index.jsp";
}



/**
 * 加载数据样式  -- 居中显示
 * @param type
 */
function loadData(type){
	if(type == 1){
		tip(1);
	}else{
		tip(0,ten_lang.pda["msg_2"]);
	}
}

/**
 * 加载数据样式  -- 居中显示
 * @param type
 */
function tip(type,msg){
	if(type == 1){
		$("#tee_mobile_tip").hide();
		$("#tee_mobile_tip_bg").hide();
	}else{
		if($("#tee_mobile_tip").length==0){
			$("body").append("<div id='tee_mobile_tip_bg' class='tee_tip_bg'></div>");
			$("body").append("<div id='tee_mobile_tip' class='tee_tip'>"+msg+"</div>");
		}
		$("#tee_mobile_tip_bg").show();
		$("#tee_mobile_tip").show().html(msg).css({marginLeft:-parseInt($("#tee_mobile_tip").css("width"))/2});
	}
}

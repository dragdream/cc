<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page import="com.tianee.webframe.util.auth.TeeAuthUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
    	String TOP_BANNER_FONT = (String) request.getSession().getAttribute("TOP_BANNER_FONT") == null ? "" : (String) request.getSession().getAttribute("TOP_BANNER_FONT");//顶部字样样式
    	String TOP_BANNER_TEXT = (String) request.getSession().getAttribute("TOP_BANNER_TEXT") == null ? "" : (String) request.getSession().getAttribute("TOP_BANNER_TEXT");//顶部文字
    	String BOTTOM_STATUS_TEXT = (String) request.getSession().getAttribute("BOTTOM_STATUS_TEXT") == null ? "" : (String) request.getSession().getAttribute("BOTTOM_STATUS_TEXT");//底部文字
    	String TOP_ATTACHMENT_ID = TeeStringUtil.getString( request.getSession().getAttribute("TOP_ATTACHMENT_ID"));//顶部图片Id TeeAttachment.sid
    %>
<!DOCTYPE html>
<html style="overflow:hidden;">
<head>
<%@ include file="/header/header2.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>办公智能管理平台</title>
<link href="css/index.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="css/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style_without_tab.css"/> 
<link rel="stylesheet" type="text/css" href="<%=cssPathSecond%>/index.css" />
<link rel="stylesheet" type="text/css" href="<%=cssPathSecond%>/menu.css" /> 
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/system/frame/default/css/query.css" />

<script type="text/javascript" src="<%=contextPath%>/system/frame/ispirit/js/sms.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/frame/default/js/index.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/frame/2/js/sms.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/bootstrap/js/bootstrap_typeahead.js"></script>	
<script src="<%=contextPath%>/system/frame/default/js/perfect-scrollbar.with-mousewheel.min.js"></script> 
<script src="<%=contextPath%>/system/frame/default/js/jquery-mousewheel.js"></script> 
<script type="text/javascript" src="<%=contextPath%>/system/frame/default/js/index.js"></script>

<style>
#Demo { position:absolute; left:15px; top:101px; bottom:40px; width: 218px; overflow: hidden; }
.ps-container .ps-scrollbar-y { position: absolute; right:9px; width:6px; background-color:#1A99CC;cursor:pointer;}
.ps-scrollbar-y:hover {background-color:#48b8e5;}
.panel-body  iframe{ position:absolute; left:0; top:0px; right:0;}
</style>

<script type="text/javascript">
var loader;
function  doInit(){
	$("#iframe0").attr("src", "<%=contextPath%>/system/frame/default/main.jsp");
	//菜单
	getMenuListNew();
	
	getModelHandCount();//获取模块的数量
	
 	getCurrLunarDate();
	
	//在线人数
	queryOnlineUserCount();
	
	queryUserInfo();
	
	//系统当前时间
	setCurrTimes();//
	
	//创建拖动事件
	  $("#queryFrame").draggable();
// 	  smsAlert();
	remindCheck();
	
	setTopBanner();
} 
/**
 * 设置头部banner
 */
function setTopBanner(){
	/* 顶部字体样式处理 */
	var TOP_BANNER_FONT= "<%=TOP_BANNER_FONT%>";
	var TOP_ATTACHMENT_ID = "<%=TOP_ATTACHMENT_ID%>";
	var TOP_BANNER_TEXT =  "<%=TOP_BANNER_TEXT%>";
	TOP_BANNER_FONT = TOP_BANNER_FONT + " margin-left: 10px; line-height:70px; ;height: 70px;background-position: 0 center; ";//filter:Shadow(Direction=120, color=#000000); filter: Glow(Direction=120, color=#000000);
	//TOP_ATTACHMENT_ID = "";
	  //  alert(TOP_BANNER_FONT +":"+TOP_BANNER_FONT);
	if(TOP_ATTACHMENT_ID != ""){//有图片不显示文字
		$("#logoImg").show();
		var url = "<%=contextPath%>/attachmentController/downFile.action?id=" + TOP_ATTACHMENT_ID + "&model=system";
		$("#logoImg").attr({"src": url,"height":"70px"});
	}else{
		if(TOP_BANNER_TEXT != ""){
			$('#logo').append().attr("style", TOP_BANNER_FONT).css({
			 
	    	});
		
			$("#logoText").html(TOP_BANNER_TEXT);
		}else{
			$("#logoImg").show();
			$("#logoImg").attr({"src": "img/default.png","height":"70px"});
			
		}
		
	}
	
}
/**
*
*控制面板
*/
function toDesktop(){
	var srcUrl = "<%=contextPath%>/system/core/person/setdescktop/index.jsp";
	addNewTabs("控制面板",srcUrl);
}

/**
*
*注销
*/
function doLogout(){
	var msg = loginOutText + "\n确定要注销吗?";
	if(confirm(msg)){
		var url = "<%=contextPath%>/systemAction/doLoginout.action";
		var para = {};
		var jsonObj = tools.requestJsonRs(url,para);
		if(jsonObj){
			var json = jsonObj.rtData;
			
		}else{
			alert(jsonObj.rtMsg);
		}
		window.location.href = "<%=contextPath%>";
	}
}


/**
直接从后台重新读取
*过了当天，系统从后台从新取其次
*
*/
function getCurrLunarDate(){
	var url = "<%=contextPath%>/systemAction/getCurrLunarDate.action";
	var para = {};
	var jsonObj = tools.requestJsonRs(url,para);
	if(jsonObj.rtState){
		var dateInfo = jsonObj.rtData;
		todayLunarStr = dateInfo;
		var dateInfoJson = eval('(' + dateInfo + ')');
		var date = dateInfoJson.date;
		var week = dateInfoJson.week;
		var time = dateInfoJson.time;
		var lunarDate = dateInfoJson.lunarDate;
		startTime = dateInfoJson.timeStr ;
		//$("#currDate").append( date + "&nbsp;&nbsp;" + week +"&nbsp;" + time + "&nbsp;" + lunarDate );
		$("#currDate").attr("title",  lunarDate);
		$("#currDate").append("今天是&nbsp;&nbsp;"+ date + "&nbsp;&nbsp;" + week  );
		//.html("<font color='red'>"+ date + "<br>" + week +" " + time + "<br>" + lunarDate +"</font>");
	}else{
		//alert(jsonObj.rtMsg);
	}
}
/**
	加载天气和日期
*/
function showWeatherAndDate(){
	//setIntervalCurrDate();
	getCurrLunarDate();
}

/**自动补全查询人员**/

function queryUserInfo(){
	$.fn.typeahead.Constructor.prototype.blur = function() {
  		   var that = this;
  		      setTimeout(function () { that.hide() ;}, 250);
  	};   
  	$('#queryInfo').typeahead({
  		 source: function (typeahead, query) {

  			  
  			    return  $.post(contextPath + "/orgSelectManager/queryUserByUserIdOrUserName.action", { userName: query }, function (data) {
  				var dataJson = eval('(' + data + ')');
  	    			//var ss =  $.parseJSON(datassss);  
  				return typeahead.process(dataJson.rtData);
  			});  
  			
  		} ,
  	 	highlighter: function (item) {                
  	 			//alert(item)
  			return item.substring(0,item.length-this.query.length -1);//split(";")[0] ;
  		}  
  		,property: "name"
  			
  		,onselect:onselectUserFunc
	 });
}

/**选中触发时间**/
function onselectUserFunc(val){
	//alert(val.name +":"+ val.id);
	toUserInfo(val.id);
}


/**
 *更多查询
 */
 function switchMoreQuery(){
	
 	if($("#queryFrame").is(":hidden")){
			$("#queryFrame").show(300);
			$("#queryFrameIframe").attr("src",contextPath+"/system/core/system/query/index.jsp");
		}else{
			$("#queryFrame").hide(300);
			$("#queryFrameIframe").attr("src","");
		}
		
 }
 
var date = new Date();
//将时间减去1秒，计算天、时、分、秒 
function setCurrTimes(){ 

 	 window.setTimeout("setCurrTimes()", 1000);
 	 //timestr=date.toLocaleString();
 	 date.setSeconds(date.getSeconds()+1);
 	 
 	var hours = date.getHours();       //获取当前小时数(0-23)
 	if(hours < 10){
 		hours = "0" + hours;
 	}
 	var minutes = date.getMinutes();     //获取当前分钟数(0-59)
 	if(minutes < 10){
 		minutes = "0" + minutes;
 	}
 	//var seconds = date.getSeconds();     //获取当前秒数(0-59)
 	 $("#currTime")[0].innerHTML=hours +":" + minutes ;
}


function remindCheck(){
	var json = tools.requestJsonRs(contextPath+"/sms/remindCheck.action");
	if(json.rtState){
		if(json.rtData!=0){//弹出短消息
			smsAlert();
		}
	}
}
</script>
</head>

<body class="ui-layout-container" onload="doInit();" >


<div id="queryFrame" style="border:2px solid #00aff0;color:#00aff0;position:absolute;background:white;right:18px;top:75px;height:400px;width:300px;z-index:100000;display:none;" class="frame-info">
	<div id="queryFrameTitle" style="height:30px;line-height:26px;padding-left:10px;padding-right:10px;cursor:move;font-size:14px">
		<div style="float:left"><b >更多查询</b></div>
		<div style="float:right"><b  href="javascript:void(0)" onclick="switchMoreQuery()"><i class="glyphicon glyphicon-remove" style="color:#0177bf;"></i></b></div>
		<div style="clear:both;"></div>
	</div>
	<iframe id="queryFrameIframe" frameborder="0" style="width:100%;height:370px;margin-top:30px"></iframe>
</div>
<div class="box">
	<div class="header clearfix">
    	<div class="logo fl"  id="logo">
       
        	
        	 <span id="logoText"></span>
 				<img id="logoImg" src="" alt="" title=""  style="display:none;"/>
        </div>
        <div class="header_r fr clearfix" style="overflow:visible">
        <!--
        	<div class="header_r_t clearfix">
            	<a href="javascript:void(0);"  style="text-decoration: none;" onclick="addNewTabs('我的收藏', '<%=contextPath%>/system/core/fav/manage.jsp')">我的收藏</a>
            	
            	<a href="javascript:void(0);" onclick="toDesktop();">控制面板</a>
            	<a href="javascript:void();" onclick="addNewTabs('修改密码', '<%=contextPath%>/system/core/person/setdescktop/password.jsp')">修改密码</a>
            </div>
            -->
           <%
           	String notSearch = TeeStringUtil.getString(person.getNotSearch());
                      		if(!notSearch.equals("1")){//禁止登录
           %>
            <div class="header_r_b">
            	<input type="text" value=""   id="queryInfo" />

                <a href="javascript:void(0);"  onclick="switchMoreQuery()"  title="更多"></a>
            </div>
            
            <%
                        	}
                        %>
        </div>
    </div>
    <div class="nav clearfix">
    	<div class="nav_l fl">
        	<div class="clearfix">
        		<span class="fl"><%=userId%>(<%=currUserDeptName%>-<%=currUserRoleName%>)，您好！</span><a href="javascript:void(0);" class="fl"  id="currDate"></a>
	   			<a href="javascript:void(0);" class="fl" style="color:red;" >&nbsp;&nbsp;
        	 <%
        	 	Map<String,String> registInfo = TeeAuthUtil.getRegistInfo();
        	 	 			Map<String,String> delayInfo = TeeAuthUtil.getDelayInfo();
        	 		
        	 	 	
        	 	 			if(registInfo==null){
        	 	 				if(delayInfo==null){
        	 	 					delayInfo = new HashMap();
        	 	 				}
        	 	 				Calendar currentDate = Calendar.getInstance();
        	 		 			String endTimeDesc = delayInfo.get("endTime")==null?TeeDateUtil.format(currentDate.getTime(),"yyyy-MM-dd hh:mm:ss"):delayInfo.get("endTime");
        	 		 			Calendar endTime = TeeDateUtil.parseCalendar("yyyy-MM-dd hh:mm:ss",endTimeDesc+" 23:59:59");
        	 		 			//out.print("&nbsp;&nbsp;<a href='javascript:void(0);' class='fl' style='color:red ;'>此版本为试用版，剩余"+(endTime.get(Calendar.DAY_OF_YEAR)-currentDate.get(Calendar.DAY_OF_YEAR))+"天，如需购买，请拨打服务热线：400-6188-272</a>");
        	 %>
		 			
	 			此版本为试用版，剩余"<%=(endTime.get(Calendar.DAY_OF_YEAR)-currentDate.get(Calendar.DAY_OF_YEAR)) %>"天，如需购买，请拨打服务热线：4006-188-272
	 			<%	
	 				}
 				%>
 			</a> 
        	</div>
     	   
        </div>
    
        <ul class="nav_r fr clearfix">
        	<li id="onlineUserCount"></li>
        	<li><span class="rcap"   onclick="addNewTabs('消息事务', '<%=contextPath %>/system/core/sms/index.jsp')">消息事务<a href="javascript:void(0);" class="a_color2"  id="smsCount"></a></span></li>
            <!--<li><span class="rcap"   onclick="addNewTabs('日程安排', '<%=contextPath %>/system/core/base/calendar/index.jsp')">日程安排<a href="javascript:void(0);" class="a_color2"  id="calendarCount"></a></span></li>-->
            <!-- <li><span class="gzdb">工作待办<a href="javascript:void(0);" class="a_color2">（2）</a></span></li> -->
            <!-- <li><span class="desktop" onclick="addNewTabs('我的桌面', '<%=contextPath %>/system/frame/default/main.jsp')" >我的桌面<a href="javascript:void(0);"></a></span></li>-->
            <li><span onclick="doLogout();" class="Logout span_border_none">注销<a href="javascript:void(0);" ></a></span></li>
        </ul>

    </div>
    <div class="container clearfix">
        <div class="left"></div>
        <div class="right"></div>
    	<div class="c_left fl">
        	<div class="c_left_box">
                <div class="post_left"></div>
                <div class="c_left_content">
                    <div class="deta">
                        <a href="javascript:void(0);" class="qdsj fl"  onclick="addNewTabs('签到', '<%=contextPath %>/system/core/base/attend/duty/index.jsp');">
                        	<span>签&nbsp;&nbsp;&nbsp;到</span>
                            <strong id="currTime" style='font-family:" Verdana ,新宋体", "宋体"'></strong>
                        </a>
                        <div class="deta_right fl">
                        	<p class="clearfix" style="height:18px;"><span class="fl">上班</span><a class="fl" href="javascript:void(0);" id="onDuty"></a></p>
                            <p class="clearfix" style="height:18px;"><span class="fl">下班</span><a class="fl" href="javascript:void(0);" id="offDuty"></a></p>
                        </div>
                    </div>
                    <!--  
                    <div class="yjxx">
                    	<h3 >消息邮件</h3>
                        <a href="javascript:void(0);" onclick="addNewTabs('邮件箱', '<%=contextPath %>/system/core/email/index.jsp');"> 邮件箱（<span id="emailCount"></span>）</a>
                        <a href="javascript:void(0);"   onclick="addNewTabs('消息短信', '<%=contextPath %>/system/core/sms/index.jsp');">消息短信（<span id="smsCount"></span>）</a>
                   	<a href="javascript:void(0);" onclick=" change_size() ">短消息（<span id="messageCount"></span>）</a>
                    </div>
                    -->
                    <div class="sxcd">
                    	<div class="sxcd_t"></div>
                        <div class="sxcd_c" id="Demo">
                        	<div class="menu_list content" >
                            <ul id="menu_list_obj">
                                <!--   <li>
                                      <a class="yjcd" style="margin-top:0;" href="javascript:void(0);"><span></span>一级菜单</a>
                                      <ul class="child">
                                         <li>
                                            <a class="ejcd" href="javascript:void(0);"><span></span>二级菜单</a>
                                            <ul class="child">
                                               <li>
                                                   <a class="sjcd" onclick="add()" href="javascript:void(0);">三级菜单</a>
                                               </li>
                                            </ul>
                                           </li>
                                      </ul>
                                  </li>
                                  -->
                               </ul> 
                            </div>
                        </div>
                        <div class="sxcd_b"></div>
                    </div>
                </div>
        	</div>
        </div>
        <div class="c_center fl">
        	<div class="c_center_box">
                <div id="mainTabs" class="easyui-tabs c_center_title" data-options="tools:'#tab-tools'">
                	<div title="我的桌面">
                        <iframe class="iframe" width="100%" height="100%" frameborder="0"  id="iframe0"></iframe>  
                    </div>  
                </div>
            </div>
        </div>
        <div class="c_right fl">
        	<iframe width="220px" height="100%" frameborder="0" id="iframe1"></iframe>
        </div>
    </div>
</div>
<embed height="0" width="0" src="<%=contextPath%>/system/frame/inc/socket.swf" flashvars="userId=<%=person.getUserId() %>&userName=<%=person.getUserName() %>&ip=<%=request.getServerName() %>&port=<%=TeeSysProps.getString("TCP_SOCKET_PORT") %>&device=Web&securePort=<%=TeeSysProps.getString("SECURE_PORT") %>" style="position:absolute;top:0px;left:0px"></embed>
<div style="right: 5px; bottom: 30px; width: 200px; max-height:500px;overflow-y:auto;overflow-x:hidden; position: absolute;" id="smsInfo">
</div>

<!-- 短消息面板 -->
<div class="smsbox" id="smsbox" style="display:none">
	<div class="title">
	<span style="float:left;">消息提醒</span>
	<span style="float:right;cursor:pointer" onclick="$('#smsbox').hide()">×</span>
	<div style="clear:both"></div>
	</div>
	<div class="content" id="smsBoxContent">
		<div class="smsbox_loadmore" id="loadMore">
			点击加载更多
		</div>
	</div>
</div>

</body>
<script src="js/perfect-scrollbar.with-mousewheel.min.js"></script>
<script>
function change_size() {
	/* var width = parseInt($("#Width").val());
	var height = parseInt($("#Height").val());

	if(!width || isNaN(width)) {
		width = 200;
	}
	if(!height || isNaN(height)) {
		height = 300;
	}
	$("#Demo").width(width).height(height);

	// update perfect scrollbar
	$('#Demo').perfectScrollbar('update'); */
	
}
/* $(function() {
	$('#Demo').perfectScrollbar();
});
 */


</script>
<script>

$(".header_r_b input").focus(function (){
	$(".header_r_b span").css("display","none");
});
$(".header_r_b input").blur(function (){
	$(".header_r_b span").css("display","block");
});





$(".header_r_b input").focus(function (){
	$(".header_r_b span").css("display","none");
});
$(".header_r_b input").blur(function (){
	$(".header_r_b span").css("display","block");
});

$(document).ready(function(){
$(".left").click(function(){
	$(".left").toggleClass("left2");
	$(".c_center").toggleClass("c_center2");
	$(".c_left").toggleClass("none");
});

/**
 * 先隐藏右边
 */
$(".right").toggleClass("right2");
$(".c_center").toggleClass("c_center3");
$(".c_right").toggleClass("none");

var isClickRight = false;
$(".right,#onlineUserCount").click(function()
	{
		$(".right").toggleClass("right2");
		$(".c_center").toggleClass("c_center3");
		$(".c_right").toggleClass("none");
		if(!isClickRight){//判断是否点击过显示组织机构
			isClickRight = true;
			$("#iframe1").attr("src", "<%=contextPath %>/system/frame/default/right.jsp");
		}
	});

});
var index = 1;


function remove(){
	var tab = $('#tt').tabs('getSelected');
	if (tab){
		var index = $('#tt').tabs('getTabIndex', tab);
		$('#tt').tabs('close', index);
	}
	
}

/**
 * 邮件、短信
 */
$(".yjxx a").click(function()
{
	$(".yjxx a").removeClass("a_active");
	$(this).addClass("a_active");
});
</script>

</html>

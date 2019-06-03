<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page import="com.tianee.webframe.util.auth.TeeAuthUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
String TOP_BANNER_TEXT = TeeSysProps.getString("TOP_BANNER_TEXT");
String BOTTOM_STATUS_TEXT = TeeSysProps.getString("BOTTOM_STATUS_TEXT");
String TOP_ATTACHMENT_ID = TeeSysProps.getString("TOP_ATTACHMENT_ID");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="overflow:hidden">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<%@ include file="/header/upload.jsp" %>
<title><%=ieTitle %></title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="<%=request.getContextPath() %>/zatp.ico" type="image/x-icon"/>
<link href="<%=contextPath %>/system/frame/default/css/index.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/system/frame/default/css/easyui.css"/>

<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style_without_tab.css"> 
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/system/frame/default/css/sms.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/system/frame/default/css/cmp-all.css">

<link rel="stylesheet" type="text/css" href="<%=cssPathSecond%>/index.css" />
<link rel="stylesheet" type="text/css" href="<%=cssPathSecond%>/menu.css"> 
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/system/frame/default/css/query.css">

<link rel="stylesheet" type="text/css" href="<%=cssPathSecond%>/index1.css">
<link rel="stylesheet" type="text/css" href="<%=cssPathSecond%>/sms.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/fullcalendar/fullcalendar/fullcalendar.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/system/frame/2/js/sms.css">

<style>
#Demo { position:absolute; left:15px; top:101px; bottom:40px; width: 218px; overflow: hidden; }
.ps-container .ps-scrollbar-y { position: absolute; right:9px; width:6px; background-color:#1A99CC;cursor:pointer;}
.ps-scrollbar-y:hover {background-color:#48b8e5;}
.panel-body  iframe{ position:absolute; left:0; top:0px; right:0;}
</style>	
	
	<link  href="<%=contextPath%>/common/jquery/portlet/jquery.portlet.css?v=1.1.2" type="text/css" rel="stylesheet" />
	<link  href="<%=contextPath%>/common/jquery/portlet/jquery-ui-1.9.2.custom.css" type="text/css" rel="stylesheet" />
	
	 <script type="text/javascript" src="<%=contextPath %>/common/jquery/ux/jquery.ux.tee.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/common/jquery/ux/jquery.ux.container.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/common/jquery/ux/jquery.ux.panel.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/common/jquery/ux/jquery.ux.window.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/jquery/portlet/jquery-ui-1.9.2.min.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/jquery/portlet/jquery.portlet.js"></script>
   <script type="text/javascript" src="<%=request.getContextPath()%>/common/easyui/jquery.easyui.min.js"></script>
	
<script type="text/javascript" src="<%=contextPath %>/system/frame/ispirit/js/sms.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/frame/2/js/sms.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/bootstrap/js/bootstrap_typeahead.js"></script>	
<script src="<%=contextPath%>/system/frame/default/js/perfect-scrollbar.with-mousewheel.min.js"></script> 
<script src="<%=contextPath%>/system/frame/default/js/jquery-mousewheel.js"></script> 
<script type="text/javascript" src="<%=contextPath%>/system/frame/5/js/index.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/lazyloader.js"></script>
<script type="text/javascript">


$(function(){ 

	$("#iframe0").attr("src", "<%=contextPath %>/system/frame/default/main.jsp");
});

var loader;
function  doInit(){
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
		var url = "<%=contextPath %>/systemAction/doLoginout.action";
		var para = {};
		var jsonObj = tools.requestJsonRs(url,para);
		if(jsonObj){
			var json = jsonObj.rtData;
			
		}else{
			alert(jsonObj.rtMsg);
		}
		window.location.href = "/"+"<%=contextPath%>";
	}
}


/**
直接从后台重新读取
*过了当天，系统从后台从新取其次
*
*/
function getCurrLunarDate(){
	var url = "<%=contextPath %>/systemAction/getCurrLunarDate.action";
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
// 	$.fn.typeahead.Constructor.prototype.blur = function() {
//   		   var that = this;
//   		      setTimeout(function () { that.hide() ;}, 250);
//   	};   
//   	$('#queryInfo').typeahead({
//   		 source: function (typeahead, query) {

  			  
//   			    return  $.post(contextPath + "/orgSelectManager/queryUserByUserIdOrUserName.action", { userName: query }, function (data) {
//   				var dataJson = eval('(' + data + ')');
//   	    			//var ss =  $.parseJSON(datassss);  
//   				return typeahead.process(dataJson.rtData);
//   			});  
  			
//   		} ,
//   	 	highlighter: function (item) {                
//   	 			//alert(item)
//   			return item.substring(0,item.length-this.query.length -1);//split(";")[0] ;
//   		}  
//   		,property: "name"
  			
//   		,onselect:onselectUserFunc
// 	 });
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
	tools.requestJsonRs(contextPath+"/sms/remindCheck.action",{},true,function(json){
		if(json.rtState){
			if(json.rtData.smsFlag!=0){//弹出短消息
				msgFrame.playSound(2);
				smsAlert();
			}
			if(json.rtData.msgFlag!=0){//弹出通讯消息
				msgFrame.playSound(1);
				var offlineMsgJson = tools.requestJsonRs(contextPath+"/messageManage/getOfflineMessages.action");
				for(var i=0;i<offlineMsgJson.rtData.length;i++){
					socketHandler(offlineMsgJson.rtData[i]);
				}
			}
		}
		setTimeout("remindCheck()",1000*10);//10秒
	});
}

function enterKeywords(){
	var type = $("#searchType").val();
	if($("#queryInfo").val()!=""){
		$("#searchDataDiv").show();
		$("#searchDataDiv")[0].contentWindow.word = $("#queryInfo").val();
		$("#searchDataDiv")[0].contentWindow.doSearchUser();
	}else{
		$("#searchDataDiv").html("").hide();
	}
}


function changeSrc(url){
	$("#iframe0").attr("src",url);
}
</script>
</head>

<body class="ui-layout-container" onload="doInit();" >

<div class="box">
	<div class="header clearfix">
    	<div class="logo fl"  id="logo">
       	<%
			if(!"".equals(TOP_BANNER_TEXT)){//有标题，则优先显示标题
				%>
				<table style="height:70px;border-collapse:collapse;width:100%;margin-left:10px">
					<tr>
						<td style="font-size:16px;color:white;font-family:微软雅黑;font-weiht:bold;line-height:16px">
							<%=TOP_BANNER_TEXT %>
						</td>
					</tr>
				</table>
				<%
			}else if(!"".equals(TOP_ATTACHMENT_ID)){//有图片，则显示图片信息
				%>
				<table style="height:70px;border-collapse:collapse;width:100%;margin-left:10px">
					<tr>
						<td style="font-size:16px;color:white;font-family:微软雅黑;font-weiht:bold;line-height:16px">
							<img src="/attachmentController/downFile.action?id=<%=TOP_ATTACHMENT_ID %>"  style="height:40px;margin-top:5px" />
						</td>
					</tr>
				</table>
				<%
			}else{
				%>
				<table style="height:70px;border-collapse:collapse;width:100%;margin-left:10px">
					<tr>
						<td style="font-size:14px;color:white;font-family:微软雅黑;font-weiht:bold;line-height:16px">
							<img src="logo.png" style="height:40px;margin-top:5px"/>
						</td>
					</tr>
				</table>
				<%
			}
			%>
        </div>
        <div class="header_r fr clearfix" style="overflow:visible">
        <!--
        	<div class="header_r_t clearfix">
            	<a href="javascript:void(0);"  style="text-decoration: none;" onclick="addNewTabs('我的收藏', '<%=contextPath %>/system/core/fav/manage.jsp')">我的收藏</a>
            	
            	<a href="javascript:void(0);" onclick="toDesktop();">控制面板</a>
            	<a href="javascript:void();" onclick="addNewTabs('修改密码', '<%=contextPath %>/system/core/person/setdescktop/password.jsp')">修改密码</a>
            </div>
            -->
           <%  
              String notSearch = TeeStringUtil.getString(person.getNotSearch());
           		if(!notSearch.equals("1")){//禁止搜索
            %>
<style>
.search_ul{
margin:0px;
padding:0px;
}
.search_ul li{
padding:6px;
}
.search_ul li:hover{
cursor:pointer;
background:gray;
color:white;
}
</style>
<div style="float:right;width:200px">
<DIV style="MARGIN-TOP: 30px; PADDING-RIGHT: 0px;float:left" id=funcbar_right>
<DIV class=search>
<DIV class=search-body style="width:150px">
<DIV class=search-input>
<INPUT style="width:140px;BORDER-BOTTOM: 0px; BORDER-LEFT: 0px; BORDER-TOP: 0px; BORDER-RIGHT: 0px; box-shadow: inset 0px 0px 0px rgba(0, 0, 0, 0.1)" id="queryInfo" type="text" onkeyup="enterKeywords()"/> 
<!-- <select id="searchType" style="color:gray;width:60px;border:0px;BORDER-BOTTOM: 0px; BORDER-LEFT: 0px; BORDER-TOP: 0px; BORDER-RIGHT: 0px;margin-top:8px;"> -->
<!-- 	<option value="1">用户</option> -->
<!-- 	<option value="2">邮件</option> -->
<!-- 	<option value="3">公盘</option> -->
<!-- 	<option value="4">工作流</option> -->
<!-- </select> -->
</DIV>
<DIV id=search_clear class=search-clear onclick="" tOpacity="1" tooltipsText="更多查询" alt jQuery18007394651838957456="37"></DIV>
</DIV>
</DIV>
</DIV>

<iframe frameborder=0 src="<%=contextPath %>/system/core/base/quicksearch/search_result.jsp" id="searchDataDiv" style="position:absolute;display:none;border:1px solid gray;height:300px;width:175px;right:40px;top:58px;z-index:100"></iframe>
</div>
            
            <%} %>
        </div>
    </div>
    <div class="nav clearfix" style="padding-left:15px;">
    	<div class="nav_l fl">
        	<div class="clearfix">
        		<a class="fl" style="cursor:pointer;" title="部门：<%=currUserDeptName%>角色：<%=currUserRoleName%>"><img src="<%=contextPath %>/system/frame/default/images/status_online.png" /><%=userName %>，您好！</a><a href="javascript:void(0);" class="fl"  id="currDate"></a>
	   			<a href="http://www.zatp.com.cn/" target="_blank" class="fl" style="color:red;" >&nbsp;&nbsp;
	   			<%-- <%=TeeAuthUtil.getInfoString() %> --%>
 			</a> 
        	</div>
     	   
        </div>
    
        <ul class="nav_r fr clearfix">
        	<li><span class="span_border_none" id="onlineUserCount"></span></li>
        	<!-- <li><span class="rcap"   onclick="addNewTabs('消息事务', '<%=contextPath %>/system/core/sms/index.jsp')">消息事务<a href="javascript:void(0);" class="a_color2"  id="smsCount"></a></span></li> -->
            <!--<li><span class="rcap"   onclick="addNewTabs('日程安排', '<%=contextPath %>/system/core/base/calendar/index.jsp')">日程安排<a href="javascript:void(0);" class="a_color2"  id="calendarCount"></a></span></li>-->
            <!-- <li><span class="gzdb">工作待办<a href="javascript:void(0);" class="a_color2">（2）</a></span></li> -->
            <!-- <li><span class="desktop" onclick="addNewTabs('我的桌面', '<%=contextPath %>/system/frame/default/main.jsp')" >我的桌面<a href="javascript:void(0);"></a></span></li>-->
            <li><span class="span_border_none" title="我的桌面" onclick="changeSrc('<%=contextPath %>/system/frame/default/main.jsp')"><img src="<%=contextPath %>/system/frame/default/images/nav_l_bg.png" /></span></li>
            <li><span class="span_border_none" title="消息事务" onclick="changeSrc('<%=contextPath %>/system/core/sms/index.jsp')"><img id="msgImg" src="<%=contextPath %>/system/frame/default/images/icon_msg.png" /></span></li>
            <li><span class="span_border_none" title="收藏夹" onclick="changeSrc('<%=contextPath %>/system/core/fav/manage.jsp')"><img src="<%=contextPath %>/common/images/favorite_click.png" /></span></li>
            <li><span class="span_border_none" title="控制面板" onclick="changeSrc('<%=contextPath %>/system/core/person/setdescktop/')"><img src="<%=contextPath %>/system/frame/default/images/icon_setting.png" /></span></li>
            <li><span onclick="doLogout();"  title="注销" class="span_border_none"><img src="<%=contextPath %>/system/frame/default/images/door_open.png" /></span></li>
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
                        <a href="javascript:void(0);" class="qdsj fl"  onclick="changeSrc('<%=contextPath %>/system/core/base/attend/duty/index.jsp');">
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
        <div class="c_center fl" style="bottom:5px;">
        	<div class="c_center_box" style='height:100%;'>
                        <iframe class="iframe" width="100%" height="100%" frameborder="0"  id="iframe0"></iframe>  
                <!-- <div id="mainTabs" class="easyui-tabs c_center_title" data-options="tools:'#tab-tools'">
                	<div title="我的桌面">
                    </div>  
                </div> -->
            </div>
        </div>
        <div class="c_right fl">
        	<iframe width="220px" height="100%" frameborder="0" id="iframe1"></iframe>
        </div>
    </div>
</div>
<div style="right: 5px; bottom: 30px; width: 200px; max-height:500px;overflow-y:auto;overflow-x:hidden; position: absolute;" id="smsInfo">
</div>

<!-- 短消息面板 -->
<div class="smsbox" id="smsbox" style="display:none">
	<div class="title">
	<span style="float:left;">消息提醒</span>
	<span style="float:right;cursor:pointer" onclick="$('#smsbox').hide()">×</span>
	<span style="float:right;cursor:pointer" class="smsbox_icon2" onclick="smsDetails()">消息列表</span>
	<span style="float:right;cursor:pointer" class="smsbox_icon1" onclick="smsViewAlls()">全部已阅</span>
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

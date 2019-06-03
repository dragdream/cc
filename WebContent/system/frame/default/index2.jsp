<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.tianee.oa.core.general.model.TeeSmsModel" %>
<%@ page import="com.tianee.webframe.util.str.TeeJsonUtil" %>
<%@ page import="com.tianee.webframe.util.date.TeeLunarCalendarUtils" %>
<%@ page import="com.tianee.webframe.util.date.TeeWeather" %>
<%@ page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page import="com.tianee.webframe.util.date.*"%>
<%@ page import="com.tianee.webframe.util.auth.TeeAuthUtil"%>
<%
	String todayLunarStr = TeeLunarCalendarUtils.today();


	String TOP_BANNER_FONT = (String) request.getSession().getAttribute("TOP_BANNER_FONT") == null ? "" : (String) request.getSession().getAttribute("TOP_BANNER_FONT");//顶部字样样式
	String TOP_BANNER_TEXT = (String) request.getSession().getAttribute("TOP_BANNER_TEXT") == null ? "" : (String) request.getSession().getAttribute("TOP_BANNER_TEXT");//顶部文字
	String BOTTOM_STATUS_TEXT = (String) request.getSession().getAttribute("BOTTOM_STATUS_TEXT") == null ? "" : (String) request.getSession().getAttribute("BOTTOM_STATUS_TEXT");//底部文字
	String TOP_ATTACHMENT_ID = TeeStringUtil.getString( request.getSession().getAttribute("TOP_ATTACHMENT_ID"));//顶部图片Id TeeAttachment.sid
%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/header/smsHeader.jsp" %>
	<%@ include file="/header/ztree.jsp" %>
	<title><%=ieTitle%></title>
	<link rel="stylesheet" type="text/css" href="css/sms.css">
	<link rel="stylesheet" type="text/css" href="css/cmp-all.css">
	<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style_without_tab.css">
	<link rel="stylesheet" type="text/css" href="<%=cssPath%>/theme.css">
	<link rel="stylesheet" type="text/css" href="<%=cssPath%>/index1.css" />
	<link rel="stylesheet" type="text/css" href="<%=cssPath%>/menu.css">
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/system/frame/default/css/query.css">
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/system/frame/default/css/index1.css">
	<link  href="<%=contextPath%>/common/jquery/portlet/jquery.portlet.css?v=1.1.2" type="text/css" rel="stylesheet" />
	<link  href="<%=contextPath%>/common/jquery/portlet/jquery-ui-1.9.2.custom.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="<%=contextPath%>/common/jquery/ux/jquery.ux.tee.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/jquery/ux/jquery.ux.container.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/jquery/ux/jquery.ux.panel.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/jquery/ux/jquery.ux.window.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/tabs/tabs.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/system/frame/ispirit/js/sms.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/system/frame/default/js/index.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/jquery/portlet/jquery-ui-1.9.2.min.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/jquery/portlet/jquery.portlet.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/TeeMenu.js"></script> 
	<script type="text/javascript" src="<%=contextPath%>/system/core/org/orgUser/orgUser.js"></script>	
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/system/frame/2/styles/style1/css/sms.css">
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/fullcalendar/fullcalendar/fullcalendar.css">
	<script type="text/javascript" src="<%=contextPath%>/system/frame/2/js/sms.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/bootstrap/js/bootstrap_typeahead.js"></script>	
	<!-- jQuery 布局器 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/common/jqueryui/jquery.layout-latest.js"></script>
	
	<!-- 图片预览器 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/picexplore/jquery.mousewheel.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/picexplore/picexplore.js"></script>
<link rel="stylesheet" href="<%=contextPath%>/common/js/picexplore/picexplore.css" type="text/css"/>
	<script type="text/javascript" charset="UTF-8">
	var contextPath = "<%=contextPath%>";
	var layout;
	function addTabs(title,url,closable){
		if(closable==undefined){
			closable = true;
		}
		$.addTab("tabs","tabs-content",{title:title,url:url,cache:true,closable:closable});
	}

	function doInit(){
		$.jBox.tip("正在初始化布局","loading");
		
		layout = $("body").layout({
			name:"outerLayout",
			north:{
				size:40,
				slidable:false,
				resizable:false,
				spacing_open:0,
				spacing_closed:0
			},
			south:{
				size:30,
				slidable:false,
				resizable:false,
				spacing_open:0,
				spacing_closed:0
			},
			center__childOptions:{
				north__size:40,
				north__slidable:false,
				north__resizable:false,
				north__spacing_open:0,
				north__spacing_closed:0,
				north__childOptions:{
					east__size:280,
					east__slidable:false,
					east__resizable:false,
					east__spacing_open:0,
					east__spacing_closed:0,
					west__size:280,
					west__slidable:false,
					west__resizable:false,
					west__spacing_open:0,
					west__spacing_closed:0
				}
			}
		});

		innerLayout = $("#innerCenter").layout({
			west:{
				size:240,
				slidable:false,
				resizable:false,
				spacing_open:2,
				spacing_closed:2
			}
		});
		$("body").animate({opacity:1},500);
		setTimeout(function(){

			/* 顶部字体样式处理 */
			var TOP_BANNER_FONT= "<%=TOP_BANNER_FONT%>";
			var TOP_ATTACHMENT_ID = "<%=TOP_ATTACHMENT_ID%>";
			TOP_BANNER_FONT = TOP_BANNER_FONT + " margin-left: 10px; line-height:35px; ;height: 45px;background-position: 0 center; ";//filter:Shadow(Direction=120, color=#000000); filter: Glow(Direction=120, color=#000000);
			//TOP_ATTACHMENT_ID = "";
			    
			if(TOP_ATTACHMENT_ID != ""){//有图片不显示文字
				$("#logoImg").show();
				var url = "<%=contextPath%>/attachmentController/downFile.action?id=" + TOP_ATTACHMENT_ID + "&model=system";
				$("#logoImg").attr({"src": url,"height":"40px"});
			}else{
				$('#logo').append().attr("style", TOP_BANNER_FONT).css({
					 
			    });
				
				$("#logoText").html("<%=TOP_BANNER_TEXT%>");
			}
			
			
			/** 加载菜单 **/
			var MENU_EXPAND_SINGLE = getMenuList();
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
			addTabs("桌面模块",contextPath+"/portlet/portlet.action",false);
			//layout.addToggleBtn("#yincang", "west" );

			  //mainIframe.location = contextPath +'/portlet/portlet.action';
			  
			  /* 初始化bootstrap各种事件 */
			  initBootstrapFunc();
			  /**  在线人员 ***/
			  queryOnlineUserCount();
			  
			  
			  $("#ceshi").css("z-index",111);
			  $("#tabs-content").css({"z-index":1 , "position":"absolute"});
			  $("#westHome").css({"z-index":9999 , "position":"absolute"});
			  /* $("#center").css({"z-index":10000 , "position":"absolute"});
			  $("#ceshi").css({"z-index":10000 , "position":"absolute"});
			  
			  $("#ui-layout-west-menu").css({"z-index":10000 , "position":"absolute"});
			 */
			  //$("#tabs").css("width",822);
			  //$("#tabs").css("left",20);position:absolute;z-index:1;

			  $("#menu-arrow").toggle(function(){
				  minMenu(innerLayout);
			  },function(){
				  openMenu(innerLayout);
				 
			  });

			  $("[title]").tooltips();

			  //创建拖动事件
			  $("#orgFrame").draggable();
			  $("#smsFrame").draggable();
			  $("#favFrame").draggable();
			  $.jBox.closeTip(true);
		},500);
		
		//查询人员自动补全
		queryUserInfo();


		
		setTimeout(function(){
			//查询一次后台数据库，获取未弹出短消息标记
			var url = contextPath+"/sms/remindCheck.action";
			tools.requestJsonRs(url,{},true,function(json){
				if(json.rtData>0){
					smsAlert();
				}
			});
		},2000);
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
	*
	*控制面板
	*/
	function toDesktop(){
		/* document.getElementById("portlet").innerHTML = "";
		document.getElementById("portlet").style.display="none"; */
		var srcUrl = "<%=contextPath%>/system/core/person/setdescktop/index.jsp";
		addTabs("控制面板",srcUrl);
	}
	/**
	*获取天气信息
	*/
	function getWeather(){
		var url = "<%=contextPath%>/weatherManage/getWeather.action";
		var para = {};
		var jsonObj = tools.requestJsonRs(url,para);
		if(jsonObj.rtState){
			if(jsonObj.rtData && jsonObj.rtData.length > 0){
				var json = jsonObj.rtData[0];
				$("#mainframeWeather").html(json.city +":" + json.temp +":"+ json.weather +":"+ json.wind);
			}
		}else{
			//alert(jsonObj.rtMsg);
		}
	}
	
	var spaceTime = 5;
	//setInterval("setIntervalCurrDate()",spaceTime * 1000); //每个5秒执行一次
	var startTime ;
	var todayLunarStr = "<%=todayLunarStr%>";
	function setIntervalCurrDate(){
		var dateInfo = todayLunarStr;
		var dateInfoJson = eval('(' + dateInfo + ')');
		var date = dateInfoJson.date;
		var week = dateInfoJson.week;
		var time = dateInfoJson.time;
		if(!startTime){//第一次加载
			startTime = dateInfoJson.timeStr;
		}else{
			startTime = startTime + spaceTime;
			//getCurrLunarDate();
			if(startTime >= 86400 ){//如果超过当天，重新从后台读取
				getCurrLunarDate();
			}else{
				var minute =  Math.floor(startTime/60);
				var hour = Math.floor(minute/60) ;
				time = hour +  ":" + (minute % 60);
			}
		}
		
		var lunarDate = dateInfoJson.lunarDate;
		$("#mainframeDate").html(date + "<br>" + week +" " + time + "<br>" + lunarDate );
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
			$("#weatherAndDate").attr("data-content","<strong>"+ date + "<br>" + week +" " + time + "<br>" + lunarDate +"</strong>");
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
	
	/**
	*	菜单操作
	*/
    function openMenu(layout){
        $('#west').removeClass('menu-min');
        layout.sizePane("west", 240);
        $("#pushMenuRight").hide();
        $("#pushMenuLeft").show();
        $("#closeMenuToRight").hide();
        $("#menu-arrow").attr("src","img/close.png");
	}
    function minMenu(layout){
        $('#west').addClass('menu-min');
        layout.sizePane("west", 30);
        $("#pushMenuRight").show();
        $("#pushMenuLeft").hide();
        $("#menu-arrow").attr("src","img/open.png");
    }

    //弹出短消息框
    function smsAlert(){
        var url = contextPath+"/sms/popup.action";
        tools.requestJsonRs(url,{},true);
    	$("#smsFrame").show(300,function(){
    		$("#smsFrameIframe").attr("src",contextPath+"/system/core/sms/smsbox.jsp");
        });
    }
    
    function switchOrg(){
		if($("#orgFrame").is(":hidden")){
			$("#orgFrame").show(300);
			$("#orgFrameIframe").attr("src",contextPath+"/system/core/org/orgUser/index.jsp");
		}else{
			$("#orgFrame").hide(300);
			$("#orgFrameIframe").attr("src","");
		}
    }

    function switchFav(){
    	if($("#favFrame").is(":hidden")){
			$("#favFrame").show(300);
			$("#favFrameIframe").attr("src",contextPath+"/system/core/fav/list.jsp");
		}else{
			$("#favFrame").hide(300);
			$("#favFrameIframe").attr("src","");
		}
    }

    function switchSms(){
		if($("#smsFrame").is(":hidden")){
			$("#smsFrame").show(300,function(){
	    		$("#smsFrameIframe").attr("src",contextPath+"/system/core/sms/smsbox.jsp");
	        });
		}else{
			$("#smsFrame").hide(300);
			$("#smsFrameIframe").attr("src","");
		}
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
    
    /**
    **常用
    */
    function switchShortcutMenu(){
    	if($("#shortcutMenuFrame").is(":hidden")){
			$("#shortcutMenuFrame").show(300);
			$("#shortcutMenuFrameIframe").attr("src",contextPath+"/system/core/menu/shortcutMenu.jsp");
		}else{
			$("#shortcutMenuFrame").hide(300);
			$("#shortcutMenuFrameIframe").attr("src","");
		}
    }
    
    /**自动补全查询人员**/
   
    function queryUserInfo(){
    	$.fn.typeahead.Constructor.prototype.blur = function() {
      		   var that = this;
      		      setTimeout(function () { that.hide() ;}, 250);
      	};   
      	$('#queryInfo').typeahead({
      		 source: function (typeahead, query) {
      			//alert(query)
      			  
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
	</script>
</head>
<body onload="doInit()">
<div id="orgFrame" style="color:#0177bf;position:absolute;background:white;left:32px;top:70px;height:400px;width:240px;z-index:100000;display:none;border:1px solid #0177bf;border-left:4px solid #0177bf;">
	<div id="orgFrameTitle" style="height:30px;line-height:26px;padding-left:10px;padding-right:10px;cursor:move;font-size:14px">
		<b style="float:left">组织机构</b>
		<a style="float:right" href="javascript:void(0)" onclick="switchOrg()"><i class="glyphicon glyphicon-remove" style="color:#0177bf;"></i></a>
	</div>
	<iframe id="orgFrameIframe" frameborder="0" style="width:100%;height:370px"></iframe>
</div>

<div id="smsFrame" style="color:#0177bf;position:absolute;background:white;left:200px;top:75px;height:500px;width:400px;z-index:100000;display:none;border:1px solid #0177bf;border-left:4px solid #0177bf;">
	<div id="smsFrameTitle" style="height:30px;line-height:26px;padding-left:10px;padding-right:10px;cursor:move;font-size:14px">
		<b style="float:left">消息盒子</b>
		<a style="float:right" href="javascript:void(0)" onclick="switchSms()"><i class="glyphicon glyphicon-remove" style="color:#0177bf;"></i></a>
	</div>
	<iframe id="smsFrameIframe" frameborder="0" style="width:100%;height:470px"></iframe>
</div>



<div id="favFrame" style="color:#0177bf;position:absolute;background:white;left:86px;top:75px;height:400px;width:240px;z-index:100000;display:none;border:1px solid #0177bf;border-left:4px solid #0177bf;">
	<div id="favFrameTitle" style="height:30px;line-height:26px;padding-left:10px;padding-right:10px;cursor:move;font-size:14px">
		<b style="float:left">收藏夹</b>
		<a style="float:right" href="javascript:void(0)" onclick="switchFav()"><i class="glyphicon glyphicon-remove" style="color:#0177bf;"></i></a>
	</div>
	<iframe id="favFrameIframe" frameborder="0" style="width:100%;height:370px"></iframe>
</div>

<div id="shortcutMenuFrame" style="color:#0177bf;position:absolute;background:white;left:120px;top:75px;height:400px;width:200px;z-index:100000;display:none;border:1px solid #0177bf;border-left:4px solid #0177bf;">
	<div id="shortcutMenuFrameTitle" style="height:30px;line-height:26px;padding-left:10px;padding-right:10px;cursor:move;font-size:14px">
		<b style="float:left">常用</b>
		<a style="float:right" href="javascript:void(0)" onclick="switchShortcutMenu()"><i class="glyphicon glyphicon-remove" style="color:#0177bf;"></i></a>
	</div>
	<iframe id="shortcutMenuFrameIframe" frameborder="0" style="width:100%;height:370px"></iframe>
</div>

<div id="queryFrame" style="color:#0177bf;position:absolute;background:white;right:18px;top:75px;height:400px;width:300px;z-index:100000;display:none;border:1px solid #0177bf;border-left:4px solid #0177bf;">
	<div id="queryFrameTitle" style="height:30px;line-height:26px;padding-left:10px;padding-right:10px;cursor:move;font-size:14px">
		<b style="float:left">更多查询</b>
		<a style="float:right" href="javascript:void(0)" onclick="switchMoreQuery()"><i class="glyphicon glyphicon-remove" style="color:#0177bf;"></i></a>
	</div>
	<iframe id="queryFrameIframe" frameborder="0" style="width:100%;height:370px"></iframe>
</div>

<!-- Center -->
<div class="pane ui-layout-center " style="background:#0177bf;" id="center" >
<embed height="0" width="0" src="<%=contextPath%>/system/frame/inc/socket.swf" flashvars="userId=<%=person.getUserId()%>&userName=<%=person.getUserName()%>&ip=<%=request.getServerName()%>&port=<%=TeeSysProps.getString("TCP_SOCKET_PORT")%>&device=Web&securePort=<%=TeeSysProps.getString("SECURE_PORT")%>" style="position:absolute;top:0px;left:0px"></embed>
	<div class="pane ui-layout-north" style="background:#0177bf;overflow:visible" id="ceshi">
		<!-- <li class="glyphicon glyphicon-chevron-right" style="top: 12px;left:0px;display:;" onclick="pushMenu();" id="pushMenuToRight"></li> -->
		<div class="ui-layout-west" id="ui-layout-west-menu"   >
			<div class="" style="padding-top:8px">
				<img src="img/org.png" onclick="getORGInfo()" style="margin-left:15px;cursor:pointer;"/>
				<img src="img/favor.png" onclick="switchFav()" style="margin-left:15px;cursor:pointer;"/>
				<img src="img/recent.png"  onclick="switchShortcutMenu()" style="margin-left:15px;cursor:pointer;"/>
				<img src="img/sms.png" onclick="switchSms()"  style="margin-left:15px;cursor:pointer;"/>
				<img id="menu-arrow" title="折叠/开启左侧菜单" src="img/close.png" style="margin-left:25px;cursor:pointer;"/>
			</div>
		</div>
		<div id="tabs" class="ui-layout-center" >
			
			
		</div>
		<div class="ui-layout-east" style="overflow:visible">
			<!-- 	搜索人员 -->
			<div id="funcbar_right" style="padding-right:10px;margin-top:4px;">
				<div class="search">
				   <div class="search-body">
				      <div class="search-input">
				     	 <input type="text" value="" id="queryInfo"     style="border:0px;box-shadow:inset 0px 0px 0px rgba(0,0,0,0.1);" >
				      </div>  
				      <div onclick="switchMoreQuery()" style="" class="search-clear" id="search_clear" title="更多查询"> </div>
				   </div>
				</div>
			</div> 
		</div>
	</div>
	<div id="innerCenter" class="pane ui-layout-center" style="">
				<div class="pane ui-layout-west ui-layout-pane-westHome " id="westHome" style="">
					<div id="west" style='overflow-y:auto;'>
					   <div class="menu-wrapper">
					       <div class="menu-scroller">
					           <ul class="nav nav-list" id="menuNav">
					           </ul>
					        </div>
					    </div>
					</div>
				</div>
				<div id="tabs-content" class="pane ui-layout-center" style="background:white;padding-left:0px;padding-top:0px;"></div>
	</div>
</div>

<!-- North -->
<div class="pane ui-layout-north" style="background:#0177bf">
	<table style="width:100%">
		<tr>
			<td>
				<div id="logo"   style="">
					<%-- <%=TOP_BANNER_TEXT %> --%>
					 <span id="logoText"></span>
					 <img id="logoImg" src="" style="display:none"/>
				</div>
		    </td>
			<td style="text-align:left;width:100px;">
				<div class="mainframeuser"  ></div>
           		<div class="mainframeuservavtar"> </div>
<!--           		<div class="mainframeuserinfo" title="<%=userInfo%>"> <%=userNameDesc%></div>-->
			</td>
			<td style="width:250px;">
				 <div class="mainframe" style="color:white">
			        	<div class="weather" id="weatherAndDate" onclick="showWeatherAndDate();" data-container="body"   data-placement="bottom" data-content="" data-toggle="popover" data-html="true"  >
			                <i class="glyphicon glyphicon-time"></i>
			            </div>
						<div class="portlet"  onclick="showSetPortletDialog(0,'增加桌面模块');" title="增加桌面模块" >
			                <i class="glyphicon glyphicon-plus"></i>
			            </div>
						<div class="desktop"  onclick="toDesktop();" title="控制面板" >
			                <i class="glyphicon glyphicon-cog"></i>
			            </div>
					  	<div class="login_out"  onclick="doLogout();" title="注销">
 							<i class="glyphicon glyphicon-off"></i>
			            </div> 
					</div>													
			</td>
		</tr>
	</table>
</div>

<!-- South -->
<div class="pane ui-layout-south" style="background:#0177bf">

 <table style="width:100%;line-height:10px;">
 	<tr>
 		<td style="width:350px" nowrap="nowrap">
 			<span style="font-size:12px;color:white;padding-left:10px;">
				当前用户：<%=userNameDesc%>
				&nbsp;&nbsp;部门：<%=deptNameDesc%>
				&nbsp;&nbsp;角色：<%=roleNameDesc%>
				<br/>
			</span>
 		</td >
 			
 		<td align="center" nowrap="nowrap">
 			<span style="padding-left:0px;color:white;font-size:12px">
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
 					 			
 				 				out.print("此版本为试用版，剩余"+(endTime.get(Calendar.DAY_OF_YEAR)-currentDate.get(Calendar.DAY_OF_YEAR))+"天，正式版无此提示");
 				 			}else{
 				 				out.print(BOTTOM_STATUS_TEXT);
 				 			}
 			%>
 			</span>
 		</td>
 		
 		<td align="center" style="width:100px;">
 		
 		</td>
 		<td style="width:80px" nowrap="nowrap">
 		<div style="float:right;padding:8px;color:white;cursor: pointer;" id="onlineUserCount"  onclick="getORGInfo()"></div>
 		</td>
 	</tr>

 </table>
	
</div>

<!-- 组织机构 -->
<div class="popover2 top" id="orgInfoId"  style="position:absolute;right:15px;bottom:40px;width:285px;max-width:285px;height:480px;z-index:10000;background-color:rgb(249, 249, 249);margin:0px;display:none">
    <div class="arrow" id="orgArrowId"></div>
    <h3 class="popover-title" align="center">
    	<span id="">
    		<button type="button" class="btn btn-primary"  onclick="onlineUser()">在线人员</button> &nbsp;&nbsp;&nbsp;&nbsp;
<button type="button" class="btn btn-primary" onclick="allUser()">全部人员</button>
    	</span>
        <span style="margin-left:36px;cursor:pointer"  id="closeOrg"></span> 
    </h3>
    <div class="popover-content" style="padding-top:0px;background-color:rgb(249, 249, 249)">
     	 <p style="">
     	 	<ul id="orgUserZtree" class="ztree" style="border:0px;width:252px;height:395px;overflow-y:auto;"></ul>
     	 </p>
    </div>
</div>

<div style="right: 5px; bottom: 30px; width: 200px; max-height:500px;overflow-y:auto;overflow-x:hidden; position: absolute;" id="smsInfo">
</div>

<script type="text/javascript">
$("body").css({opacity:0});
</script>
</body>

</html>
		        
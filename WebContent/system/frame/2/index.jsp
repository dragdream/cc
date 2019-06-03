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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<%@ include file="/header/smsHeader.jsp" %>
 	<%@ include file="/header/ztree.jsp" %>
	<title><%=ieTitle %></title>
	<link rel="stylesheet" type="text/css" href="css/sms.css">
	<link rel="stylesheet" type="text/css" href="css/cmp-all.css">
	<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style_without_tab.css">
	<link rel="stylesheet" type="text/css" href="<%=cssPathSecond%>/index.css" />
	 <link rel="stylesheet" type="text/css" href="<%=cssPathSecond%>/menu.css"> 
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/system/frame/default/css/query.css">
	
	<link rel="stylesheet" type="text/css" href="<%=cssPathSecond%>/index1.css">
	<link rel="stylesheet" type="text/css" href="<%=cssPathSecond%>/sms.css">
	<link rel="stylesheet" type="text/css" href="<%=contextPath %>/system/frame/2/js/sms.css">
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/fullcalendar/fullcalendar/fullcalendar.css">
	<!-- jQuery 布局器 -->
	<script type="text/javascript" src="<%=request.getContextPath() %>/common/jqueryui/jquery.layout-latest.js"></script>


	<link  href="<%=contextPath%>/common/jquery/portlet/jquery.portlet.css?v=1.1.2" type="text/css" rel="stylesheet" />
	<link  href="<%=contextPath%>/common/jquery/portlet/jquery-ui-1.9.2.custom.css" type="text/css" rel="stylesheet" />
	
	<style type="text/css">
		.popover2.top .arrow {
  			border-top-color: rgb(249, 249, 249);
  		}
	</style>
	
	<script type="text/javascript" src="<%=contextPath %>/common/jquery/ux/jquery.ux.tee.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/common/jquery/ux/jquery.ux.container.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/common/jquery/ux/jquery.ux.panel.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/common/jquery/ux/jquery.ux.window.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/tabs/tabs.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/system/frame/ispirit/js/sms.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/system/frame/2/js/index.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/jquery/portlet/jquery-ui-1.9.2.min.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/jquery/portlet/jquery.portlet.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/TeeMenu.js"></script> 
	<script type="text/javascript" src="<%=contextPath%>/system/core/org/orgUser/orgUser.js"></script>	
	<script type="text/javascript" src="<%=contextPath%>/common/bootstrap/js/bootstrap_typeahead.js"></script>		        

	<script type="text/javascript" src="<%=contextPath%>/system/core/org/orgUser/orgUser.js"></script>	
	<!-- 图片预览器 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/common/js/picexplore/jquery.mousewheel.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/common/js/picexplore/picexplore.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/frame/2/js/sms.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/lazyloader.js"></script>
<link rel="stylesheet" href="<%=contextPath %>/common/js/picexplore/picexplore.css" type="text/css"/>
	<script type="text/javascript" charset="UTF-8">
	var contextPath = "<%=contextPath%>";
	var layout;
	var loader;
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
					east__size:160,
					east__slidable:false,
					east__resizable:false,
					east__spacing_open:0,
					east__spacing_closed:0,
					west__size:160,
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
		
			addTabs("桌面模块",contextPath+"/system/frame/2/portal/index.jsp",false);
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

		//初始化菜单
		initMenu();
		
		remindCheck();
	}
	var start_menu = "start_menu";
	var menuInfoId = "menuInfoId";
	function initMenu(){
		 $("#" + start_menu).click(function(){
				var cityObj = $(this);
				var cityOffset = cityObj.offset();
				var leftLength = cityOffset.left;
				var topLength = cityOffset.top;
			 	/* 	if (isBrowserVersonTop()) {  //判断是否需要处理兼容模式
			 			leftLength = leftLength + xScrollLength;
			 			topLength = topLength + yScrollLength;
			 		} */
				 var left = (leftLength + cityObj.outerWidth()/2 - 16) + "px";
			     var tp = { left:left };
			     $("#menuInfoId .arrow").css( tp);//调整位置
			     if($("#" + menuInfoId).is(':hidden')){
			    	 $("#" + menuInfoId).show();
			    	 getMenuInfo();
			     }else{
			    	 $("#" + menuInfoId).hide();
			     }
			     isClickMenuInfo = true;
		}); 
		 
		
	}
	
	//隐藏
	function hideMenuInfo(){
		$("#menuInfoId").hide();
	}
	var isClickMenuInfo = false;
	/**
	* 获取菜单信息
	*/
	function getMenuInfo(){
		var test = false;
		if(!isClickMenuInfo){
			$("#closeMenu").append('<i class="glyphicon glyphicon-remove"/>');
			$("#closeMenu").click(function(){
				$("#" + menuInfoId).hide();
			});
	 

	        var menuInfo = getMenuList();
			 $(document).on('click', function(e) {
				   // e.target ;// 判断e.target是不是属于菜单下的东西
				/* var a = false;
				var ss = $("#" + menuInfoId).find("*");//.filter(e.target);
				
				 for(var i =0 ;i<ss.length ; i++){
					 if(e.target == ss[i]){
						 a= true;
						 break;
					 }
				 } */
				 var exstisElementLength = $("#" + menuInfoId).find(e.target).length;//判断是否存在
			    if($("#" + start_menu)[0] != e.target  &&  exstisElementLength == 0){
			    	$("#" + menuInfoId).hide();
			    	test = true;
			    }else{
			    	test = false ;
			    }
			 });
			  $("#" + start_menu).blur(function(e){
				  var isFocus = $("#" + menuInfoId ).is(":focus");  
				//  alert( $("#" + menuInfoId ).find("*").length)
				  var isFocus2 = $("#" + menuInfoId ).find("*").is(":focus");
				  var isFocus3 = $("#menu-all").find("*").is(":focus");
				 // alert(isFocus2 +":"+ isFocus3)
				 if(false==isFocus && isFocus2 == false  &&  test == false){  
					// $("#" + menuInfoId).hide();
				 }  
			 }); 
	     
		}
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
		var url = "<%=contextPath %>/weatherManage/getWeather.action";
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

    
    /**自动补全查询人员**/
   
    function queryUserInfo(){
    	$.fn.typeahead.Constructor.prototype.blur = function() {
      		   var that = this;
      		      setTimeout(function () { that.hide() }, 250);
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
    /**选中触发事件**/
    function onselectUserFunc(val){
    	//alert(val.name +":"+ val.id);
    	toUserInfo(val.id);
    }
    
    function remindCheck(){
    	tools.requestJsonRs(contextPath+"/sms/remindCheck.action",{},true,function(json){
			if(json.rtState){
				if(json.rtData.smsFlag!=0){//弹出短消息
					smsAlert();
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
	</script>
</head>
<body onload="doInit()" id="body">
<div id="orgFrame" style="color:#0177bf;position:absolute;background:white;left:32px;top:70px;height:400px;width:240px;z-index:100000;display:none;border:1px solid #0177bf;border-left:4px solid #0177bf;">
	<div id="orgFrameTitle" style="height:30px;line-height:26px;padding-left:10px;padding-right:10px;cursor:move;font-size:14px">
		<b style="float:left">组织机构</b>
		<a style="float:right" href="javascript:void(0)" onclick="switchOrg()"><i class="glyphicon glyphicon-remove" style="color:#0177bf;"></i></a>
	</div>
	<iframe id="orgFrameIframe" frameborder="0" style="width:100%;height:370px"></iframe>
</div>

<div id="favFrame" style="color:#0177bf;position:absolute;background:white;left:86px;top:75px;height:400px;width:240px;z-index:100000;display:none;border:1px solid #0177bf;border-left:4px solid #0177bf;">
	<div id="favFrameTitle" style="height:30px;line-height:26px;padding-left:10px;padding-right:10px;cursor:move;font-size:14px">
		<b style="float:left">收藏夹</b>
		<a style="float:right" href="javascript:void(0)" onclick="switchFav()"><i class="glyphicon glyphicon-remove" style="color:#0177bf;"></i></a>
	</div>
	<iframe id="favFrameIframe" frameborder="0" style="width:100%;height:370px"></iframe>
</div>


<div id="queryFrame" style="color:#c50000;position:absolute;background:white;right:18px;top:75px;height:400px;width:300px;z-index:100000;" class="frame-info">
	<div id="queryFrameTitle" style="height:30px;line-height:26px;padding-left:10px;padding-right:10px;cursor:move;font-size:14px">
		<b style="float:left">更多查询</b>
		<a style="float:right" href="javascript:void(0)" onclick="switchMoreQuery()"><i class="glyphicon glyphicon-remove" style="color:#0177bf;"></i></a>
	</div>
	<iframe id="queryFrameIframe" frameborder="0" style="width:100%;height:370px"></iframe>
</div>

<!-- Center -->
<div class="pane ui-layout-center " style="" id="center">
	<div class="pane ui-layout-north " style="overflow:visible" id="ceshi">
		<!-- <li class="glyphicon glyphicon-chevron-right" style="top: 12px;left:0px;display:;" onclick="pushMenu();" id="pushMenuToRight"></li> -->
		<div class="ui-layout-west" id="ui-layout-west-menu"   >
			 <div class="" style="padding-left:20px">
			  
    			<a id="start_menu" href="#" data-animation="true" data-selector="true" data-container="body" data-trigger="click" data-placement="bottom" data-content="" data-toggle="popover" data-html="true"></a>
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
			<!-- 	<div class="pane ui-layout-west ui-layout-pane-westHome " id="westHome" style="">
					<div id="west">
					   <div class="menu-wrapper">
					       <div class="menu-scroller">
					           <ul class="nav nav-list" id="menuNav">
					          	 
					           </ul>
					        </div>
					    </div>
					</div>
				</div> -->
				<div id="tabs-content" class="pane ui-layout-center south-center-padding" style=""></div>
	</div>
</div>

<!-- North -->
<div class="pane ui-layout-north" style="" id="north-table">
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
<!--           		<div class="mainframeuserinfo" title="<%=userInfo%>"> <%=userNameDesc %></div>-->
			</td>
			<td style="width:250px;">
				 <div class="mainframe" style="color:white">
			        	<div class="weather" id="weatherAndDate" onclick="showWeatherAndDate();" data-container="body"   data-placement="bottom" data-content="" data-toggle="popover" data-html="true"  >
			                <i class="glyphicon glyphicon-time"></i>
			            </div>
						<!-- <div class="portlet"  onclick="showSetPortletDialog(0,'增加桌面模块');" title="增加桌面模块" >
			                <i class="glyphicon glyphicon-plus"></i>
			            </div> -->
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
<div class="pane ui-layout-south" id="south-table" >

 <table style="width:100%;line-height:10px;">
 	<tr>
 		<td style="width:350px" nowrap="nowrap">
 			<span style="font-size:12px;color:white;padding-left:10px;">
				当前用户：<%=userNameDesc %>
				&nbsp;&nbsp;部门：<%=deptNameDesc %>
				&nbsp;&nbsp;角色：<%=roleNameDesc %>

			</span>
 		</td >
 			
 		<td align="center" nowrap="nowrap">
 			<span style="padding-left:0px;color:white;font-size:12px">
 			<%=TeeAuthUtil.getInfoString() %>
 			</span>
 		</td>
 		
 		<td align="center" style="width:100px;">
 		
 		</td>
 		<td style="width:80px" nowrap="nowrap">
 		<div style="float:right;padding:8px 15px 8px 8px ;color:white;cursor: pointer;" id="onlineUserCount" onclick="getORGInfo()"></div>
 		</td>
 	</tr>

 </table>
	
</div>


	 <!-- 菜单功能 -->
	<div class="popover bottom menu-bg" id="menuInfoId"  style="position:absolute;left:18px;top:75px;height:480px;width:400px;max-width:500px;z-index:10000;">
        <div class="arrow" id="arrowId"></div>
        <h3 class="popover-title" style="">
        	<div id="userNameInfo"style="float:left;"><%=person.getUserName() %></div>
        	
        	<div style="float:right;cursor:pointer"  id="closeMenu"></div>
        	<div style="clear:both;"></div>
        </h3>
        <div class="popover-content" style="padding:0px 0px 0px 8px;" >
         	 <p style="">
         		 <div class="menu-all" id="menu-all" style="height:420px;overflow-y:auto;overflow-x:hidden;" >
         	
	        		<table align="left" style="overflow-y:auto;overflow-x:hidden;">
	        			<tr>
	        				<td align="left"  style="width: 160px;vertical-align:top;">
	        		
							     <ul class="nav nav-list" id="menuNav" style="" >
	
							    </ul>
						    </td>
						      
						    <td style="border-left:1px solid #ebebeb;" align="left"></td>
						    <td  align="left" style="width: 225px;vertical-align:top;">
						   		 <div  class="list-group" id="menuNav2" style="width:225px; height:415px;margin-bottom:0px;position:relative;">
					         	
					      		</div>
					        </td>
					      </tr>
					   </table>
				</div>	
         	 
<!-- <UL id=menuNav class="nav nav-list" sizcache035983770506639706="49.0.5" sizset="true"> 

</UL>  --> 	 </p>
        </div>
    </div>
    
	<!-- 组织机构 -->
    <div class="popover2 top" id="orgInfoId"  style="position:absolute;right:15px;bottom:40px;width:285px;max-width:285px;height:480px;z-index:10000;background-color:rgb(249, 249, 249);margin:0px;">
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

    
    </div  >
    
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
    
    
<script type="text/javascript">
$("body").css({opacity:0});

function smsDetails(){
	addTabs("消息事务",contextPath+'/system/core/sms/index.jsp');
	$("#smsbox").hide();
}

</script>
</body>

</html>
		
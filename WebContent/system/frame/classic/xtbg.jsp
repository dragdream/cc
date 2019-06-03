<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.*" %>
<%@ page import="com.tianee.oa.oaconst.TeeConst" %>
<%@ page import="com.tianee.oa.core.org.bean.TeePerson" %>
<%@ page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page import="com.tianee.webframe.util.date.TeeLunarCalendarUtils" %>
<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@ page import="com.tianee.webframe.util.global.TeeSysCustomerProps" %>
<%

	String contextPath = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ contextPath + "/";
	//获取主题的索引号
	int styleIndex = 1;
	Integer styleInSession = (Integer) request.getSession().getAttribute("STYLE_TYPE_INDEX");
	if (styleInSession != null) {
		styleIndex = styleInSession;
	}
	String stylePath = contextPath + "/common/styles";
	String imgPath = stylePath + "/style" + styleIndex + "/img";
	String cssPath = stylePath + "/style" + styleIndex + "/css";
	String systemImagePath = contextPath+"/common/images";
	
	//第二套风格
	int STYLE_TYPE_INDEX_2 = TeeStringUtil.getInteger( request.getSession().getAttribute("STYLE_TYPE_INDEX_2"), 1);
	String cssPathSecond = contextPath + "/system/frame/2/styles/style" + STYLE_TYPE_INDEX_2 + "/css";
	String imgPathSecond = contextPath + "/system/frame/2/styles/style" + STYLE_TYPE_INDEX_2 + "/img";
	
	
	String loginOutText = TeeSysProps.getString("LOGIN_OUT_TEXT");
	String ieTitle = TeeSysProps.getString("IE_TITLE");
	String secUserMem = (String) request.getSession().getAttribute("SEC_USER_MEM") == null ? "" : (String) request.getSession().getAttribute("SEC_USER_MEM");//是否记忆用户名
    
	//单点跳转参数
	String tzcs=(String) request.getSession().getAttribute("cuo");
	String outUrl=TeeSysCustomerProps.getString("MHLJ");
	
    
	//菜单权限   所属的应用系统
	int sysId=TeeStringUtil.getInteger(request.getParameter("sysId"),0);	
%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>北京市文化执法协同办公平台</title>
<%@ include file="/header/userheader.jsp" %>
<link rel="stylesheet" href="css/one.css">
<link rel="stylesheet" href="css/reset.css">
<!-- 其他工具库类 -->
<script src="<%=contextPath %>/common/zt_webframe/js/jquery-1.11.1.js"></script>
<script src="<%=contextPath %>/common/js/tools.js"></script>
<script src="<%=contextPath %>/common/js/sys2.0.js"></script>
<script src="<%=contextPath %>/common/js/sysUtil.js"></script>
<script src="<%=contextPath %>/common/js/src/orgselect.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/easyuiTools.js"></script>
<script src="<%=contextPath %>/common/zt_webframe/js/package.js"></script>
<script src="js/divscroll.js"></script>
<script type="text/javascript" >
window.UEDITOR_HOME_URL = "<%=contextPath%>/common/ueditor/";
$.browser = {};
$.browser.mozilla = /firefox/.test(navigator.userAgent.toLowerCase());
$.browser.webkit = /webkit/.test(navigator.userAgent.toLowerCase());
$.browser.opera = /opera/.test(navigator.userAgent.toLowerCase());
$.browser.msie = /msie/.test(navigator.userAgent.toLowerCase());
</script>
<!-- 图片预览器 -->
<script type="text/javascript" src="<%=contextPath%>/common/js/lazyloader.js"></script>
<link rel="stylesheet" href="css/index.css?v=1">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/common/zt_webframe/css/package1.css">
 <script type="text/javascript" src="<%=contextPath%>/system/frame/3/js/jquery.SuperSlide.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/frame/4/js/index2.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/frame/2/js/index.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/frame/2/js/sms.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jbox-v2.3/jBox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jbox-v2.3/jBox/i18n/jquery.jBox-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/frame/3/js/tabs.js"></script>
<script type="text/javascript">
var loginUserRoleId=0;
var  sysId=<%=sysId %>;
/** 常量定义 **/
var TDJSCONST = {
  YES: 1,
  NO: 0
};
/** 变量定义 **/
var contextPath = "<%=contextPath %>";
var imgPath = "<%=imgPath %>";
var cssPath = "<%=cssPath%>";
var stylePath = "<%=stylePath%>";
var cssPathSecond = "<%=cssPathSecond%>";
var imgPathSecond = "<%=imgPathSecond%>";
var loginOutText = "<%=loginOutText%>";
var uploadFlashUrl = "<%=contextPath %>/common/swfupload/swfupload.swf";
var commonUploadUrl = "<%=contextPath %>/attachmentController/commonUpload.action";
var systemImagePath = "<%=systemImagePath%>";
var xparent;
var tzcs="<%=tzcs%>";
var outUrl="<%=outUrl%>";
if(window.dialogArguments){
	xparent = window.dialogArguments;
}else if(window.opener){
	xparent = opener;
}else{
	xparent = window;
}
function parseNumber(value, defValue) {
  if (isNaN(value)) {
    return defValue;
  }
  return value * 1;
}
/**
*
*注销
*/
function doLogout(){
	var msg = loginOutText + "\n确定要注销吗?";
	$.MsgBox.Confirm("提示",msg,function(){
		var url = contextPath + "/systemAction/doLoginout.action";
		var para = {};
		var jsonObj = tools.requestJsonRs(url,para);
		if(jsonObj){
			var json = jsonObj.rtData;
		}else{
			$.MsgBox.Alert("提示", jsonObj.rtMsg);
		}
		window.location.href =contextPath+"/login.jsp";
});
}

function closeLogin(){
	window.close();
}

function remindCheck(){
	tools.requestJsonRs(contextPath+"/sms/remindCheck.action",{},true,function(json){
		if(json.rtState){
			
			if(json.rtData.smsFlag!=0){//弹出短消息
				msgFrame.playSound(2);
				if(autoPopSms=="0"){
					shake();
					$('.sign_in_tid').show();
				}else{
					smsAlert();
				}
			}
			if(json.rtData.msgFlag!=0){//弹出通讯消息
				msgFrame.playSound(1);
				var offlineMsgJson = tools.requestJsonRs(contextPath+"/messageManage/getOfflineMessages.action");
				for(var i=0;i<offlineMsgJson.rtData.length;i++){
					socketHandler(offlineMsgJson.rtData[i]);
				}
				offlineMsgJson = null;
			}
		}
		setTimeout("remindCheck()",10*1000);//10秒
	});
}
function smsDetails(){
	openFullWindow(contextPath+'/system/core/sms/index.jsp',"消息事务");
	$("#smsbox").hide();
}

function openFullWindow(actionUrl, title) {
	  var winParam = "menubar=0,toolbar=0,status=0";
	  winParam += ",scrollbars=1,resizable=1";
	  var left = 0;
	  var top = 0;
	  var width = window.screen.availWidth;
	  var height = window.screen.availHeight;
//	  if($.browser.webkit){
//		  height = parseInt(height)-60;
//	  }
	  height = parseInt(height)-80;
	  winParam += ",width=" + width;
	  winParam += ",height=" + height;
	  winParam += ",top=" + top;
	  winParam += ",left=" + left;
	  winParam += ",location=no";
	  
	  try{
		  if(window.external && window.external.IM_OA){
			window.external.IM_OpenNavigation(title,actionUrl.replace(contextPath,""),0,0);
			return;
		  }
	  }catch(e){
		  
	  }
	  
	  return window.open(actionUrl, new Date().getTime()+"", winParam);
	}
	function souSuo(){
		var souSouContent=$("#souSouContent").val();
		openFullWindow("<%=contextPath %>/system/core/base/quicksearch/search_result_new.jsp?souSouContent="+souSouContent,"搜索");
	}
	function doinit(){
		$.addTab("tabs","tabs-content",{title:"工作台",url:contextPath + "/system/frame/default/mainForSimple.jsp",active:true,cache:false});
		getSysMenu();
		renderLoginUserInfo();
		 $("body").on("click",".inactive",function(){
		            if ($(this).siblings('ul:first').css('display') == 'none') {
		                //$(this).parent('li').siblings('li').removeClass('inactives');
		                $(this).addClass('inactives');
		                $(this).siblings('ul:first').slideDown(100);
		                //if ($(this).parents('li').siblings('li').children('ul').css('display') == 'block') {
		                //    $(this).parents('li').siblings('li').children('ul').parent('li').children('h1').removeClass('inactives');
		                //    $(this).parents('li').siblings('li').children('ul').slideUp(100);
		                //}

		                if($(this).hasClass("cole")){
		                    $(this).siblings('ul:first').addClass("ac");
		                }
		            } else {
		                //控制自身变成+号
		                $(this).removeClass('inactives');
		                //控制自身菜单下子菜单隐藏
		                $(this).siblings('ul:first').slideUp(100);
		                //控制自身子菜单变成+号
		                $(this).siblings('ul:first').children('li').children('ul').parent('li').children('h1').addClass('inactives');
		                //控制自身菜单下子菜单隐藏
		                $(this).siblings('ul:first').children('li').children('ul').slideUp(100);

		                //控制同级菜单只保持一个是展开的（-号显示）
		                $(this).siblings('ul:first').children('li').children('h1').removeClass('inactives');

		                if($(this).hasClass("cole")){
		                    $(this).siblings('ul:first').removeClass("ac");
		                }
		            }
		 });
		 
		 $('.ul1').perfectScrollbar();
	}
	function NewPageINfo(title,url){
		$.addTab("tabs","tabs-content",{title:title,url:contextPath + url,active:true,cache:true,closable:true});
	}
	
	//获取当前登陆人基本信息
	function renderLoginUserInfo(){
	   var url=contextPath+"/personManager/getLoginUserInfo.action";
	   var json=tools.requestJsonRs(url);
	   if(json.rtState){
		   var data=json.rtData;
		   loginUserRoleId=data.roleId;
		   $("#userName").text(data.userName);
		   $("#deptName").text(data.deptName);
		   if(data.tx!=0){
			   //height: 50px;width: 50px;position: fixed;right: 136px;top: 14px;background:url('/system/frame/classic/image/images/02.png') round ;border-radius: 50%;
			   $("#txDiv").attr("style","height: 50px;width: 50px;position: fixed;right: 136px;top: 14px;background:url('/attachmentController/downFile.action?id="+data.tx+"') round ;border-radius: 50%;");
		   }
	   }
	}

	
	//返回
	function goBack(){
		  if(loginUserRoleId==11){//总队领导
		    	window.location.href = "<%=contextPath%>/system/frame/leader/leader.jsp";
		    }else{//其他人员
		    	 window.location.href = "<%=contextPath%>/system/frame/html/templates/admin/index.jsp";
		    }
	}
</script>
<style>
.active{
text-color:#000;
color:#000;
}
.tee_tab_pointer_right{ width:18px; height:40px;  background:url(/system/frame/3/style/images/header/nav_left.png) no-repeat; text-indent:-9999px; cursor:pointer;}
.tee_tab_pointer_left{ width:18px; height:40px;  background:url(/system/frame/3/style/images/header/nav_right.png) no-repeat;text-indent:-9999px; cursor:pointer;}
.tee_tab{ float:left; height:40px;line-height:40px; text-align:center; color:#000; padding:0 15px; font-size:14px; cursor:pointer;background:#fff;border-top:1px solid #cdcdcd;border-right:1px solid #cdcdcd;}
.tee_tab i{width:12px; height:10px; position:absolute; right:5px; top:10px; background:url(delete.png) no-repeat; cursor:pointer; z-index:11111111; display:block;}
.tee_tab.tee_tab_select i{ display:block; width:12px; height:10px; position:absolute; right:1px; top:5px; background:url(delete.png) no-repeat; cursor:pointer; z-index:11111111;}
#tabs .tee_tab.tee_tab_select{ background:#fff; position:relative; font-weight:bold;color:rgb(67, 129, 230);border-bottom:1px solid rgb(67, 129, 230)}
</style>
</head>
<body onload="doinit();" >
<div class="body1">
    <div class="nav" style="overflow: hidden">
        <div class="logo">
            <img src="image/icon_xtbg.png" alt="" style="width: 100% ;height: 100%">
        </div>
        <div class="nav1-right" style="float: right">

            <div class="nav-mid" style="">
                <form>
                    <input type="text" placeholder="搜索你想要的内容" id="souSouContent">
                    <a href="javascript:void(0)" onclick="souSuo();"><img src="image/sou.png" alt="" style="margin-top: 20px;margin-left: -42px"></a>
                </form>
            </div>
            <div class="tu-b">
                <ul>
                     <li><a href="javascript:void(0)" onclick="goBack();" ><img src="image/icon_home.png" alt="" class="img" ></a></li>
                    <li><a href="javascript:void(0)" onclick="NewPageINfo('桌面模块设置','<%=contextPath %>/system/core/person/setdescktop/index.jsp')" title="桌面模块设置"><img src="image/shezhi.png" alt="" class="img" ></a></li>
<%--                     <li><a href="javascript:void(0)" onclick="openFullWindow('<%=contextPath %>/system/core/base/weibo/index.jsp','企业社区')" title="企业社区"><img src="image/3.png" alt="" class="img"></a></li>
 --%>                    <li><a href="javascript:void(0)" ><img id="tip_alert" src="image/8.png" alt="" class="img"></a><div class="sign_in_tid" style="display:none">新的消息！</div>
                    </li>
                   <!--  <li><a href="javascript:void(0)"><img class="sign_img" src="image/5.png" alt="" class="img"></a>
                      <div class="signin_out">
								<p id="signInTime" class='sign_time' style="font-weight: bold;font-size: 16px;"></p>
								<p><button id="signInButton" class='sign_btn' onclick="signInFunc();" style='color:#fff;'>登记</button></p>
						</div>
                    </li> -->
					<li><a href="javascript:void(0)"><img src="image/7.png" alt="" class="img group fl"></a>
					<div id='groupContent' style='width:240px;position:fixed;right:-240px;top:77px;bottom:3px;z-index:99999;box-shadow:0 0 15px #a2a2a2;'>
					<iframe style='width:100%;height:100%;' id='right' src="/system/frame/3/right.jsp" frameborder="0"></iframe>
					</div>
					</li>
                    <li style="width: 1px;display: block;position: relative;height: 45px;background: white;top: -21px;right: -225px;"></li>
                </ul>
				<div>
					<div id="txDiv" style="height: 50px;width: 50px;position: fixed;right: 136px;top: 14px;background:url('/system/frame/classic/image/images/02.png') round ;border-radius: 50%;">
						<div style="color: white;letter-spacing: 1px;font-size: 14px;position: relative;top: 5px;right: -70px;width:80px"><span id="userName"></span></div>
						<div style="color: white;letter-spacing: 1px;font-size: 12px;position: relative;top: 14px;right: -69px;width:80px"><span id="deptName"></span></div>
						<div style="position:absolute;top:28px"><img class="dian" src="image/2222.png" alt="" style="height: 9px;width: 13px;position: relative;top: -22px;right: -160px;cursor: pointer;"></div>
						<!-- <div style="color: white;letter-spacing: 1px;font-size: 14px;position: fixed;top: 21px;right: 48px;"><span id="userName"></span></div>
						<div style="color: white;letter-spacing: 1px;font-size: 12px;position: fixed;top: 42px;right: 48px;"><span id="deptName"></span></div>
						<img class="dian" src="image/2222.png" alt="" style="height: 9px;width: 13px;position: fixed;top: 21px;right: 20px;cursor: pointer;"> -->
				    </div>
				<div class="bao" style="background: white;z-index: 100;display: none;cursor: pointer;font-size: 12px;border: 1px solid #cccccc;position: fixed;right: 0;top: 76px;width: 125px;color: grey;">
					<div style="border-bottom: 1px solid #cccccc;text-align: left;height: 35px;line-height: 35px;"  onclick="doLogout();">
					<img src="image/3333.png" alt="" style="padding: 0 10px;">切换账号</div>
					<div style="text-align: left;height: 35px;line-height: 35px;height: 35px;line-height: 35px;" onclick="closeLogin();">
						<img src="image/4444.png" alt="" style="padding: 0 10px;">退出登录</div>
				</div>
            </div>
        </div>
    </div><!--顶部-->
    <div id="ft">
        <div class="header-left" style="position:absolute;width:73px;left:0px;top:78px;bottom:0px;border-top:0px;padding-bottom: 20px">
	        <div class="left-z" style=" opacity: 0; position: fixed; top: 50%; left: 60px; z-index: 1000000;"><img src="imgs/index/left-z.png" alt=""></div>
	        <div class="left-y" style=" opacity: 0; position: fixed; top: 50%;   left: 240px; z-index: 0;"><img src="imgs/index/left-y.png" alt=""></div>
            <div class="ul1" style="width:100%;height:100%">
                <ul class="yiji ul2" id="yijiCanDan">
                </ul>
            </div>
        </div><!--左边--->
        <div style="position: absolute;left:85px;top:80px;bottom:0px;right:0px" id="id">
            <div id="tabs" style="border-left:1px solid #cdcdcd;position: absolute;left:0px;top:0px;height:40px;right:10px;">
            	
            </div>
            <div id="tabs-content" style="position: absolute;left:0px;top:41px;bottom:10px;right:10px;border:1px solid #cdcdcd">
<!--             	<iframe src="ceshizishiying.jsp" frameborder="0" width="100%" height="100%" id="main"></iframe> -->
            </div>
        </div>
    </div>
</div>
<!-- 短消息面板 -->
		<div class="smsbox" id="smsbox" style="display: none;">
			<div class="title">
			<span style="float:left;">消息提醒</span>
			<span class='sms_close_win' style="float:right;font-size:16px;cursor:pointer" onclick="$('#smsbox').hide()">×</span>
			<span style="float:right;cursor:pointer;margin-top:3px;background-image:url(imgs/index/read.png);" class="sms_list smsbox_icon2" onclick="smsDetails()">消息列表</span>
			<span style="float:right;cursor:pointer;margin-top:3px;background-image:url(imgs/index/cancel.png);" class="all_check smsbox_icon1" onclick="smsViewAlls()">全部已阅</span>
			<div style="clear:both"></div>
			</div>
			<div class="content" id="smsBoxContent">
				<div class="smsbox_loadmore" id="loadMore">
					查看更多
				</div>
			</div>
		</div>
<script>
	$(document).ready(function () {
	$('.header-left').prepend($("#ascrail2000"));
	    $('.header-left').bind("mouseenter", function () {
	        $(this).unbind("mouseleave").bind("mouseleave", function () {
	          $('.left-z').animate({
	            opacity: 0
	           }, 300);

	          });
	         $('.left-z').animate({
	           opacity: 1
	          }, 300);
	$('.header-left').prepend($("#ascrail2000"));
	         });

	   });

	$(document).ready(function () {
	    $('.header-left').bind("mouseenter", function () {
	        $(this).unbind("mouseout").bind("mouseleave", function () {
	          $('.left-y').animate({
	            opacity: 0
	          }, 300);

	       });
	          $('.left-y').animate({
	            opacity: 1
	          }, 300);

	       });
	    });
var autoPopSms = "<%=person.getAutoPopSms()%>";
    $(function () {
		$(".dian").click(function(){
			$(".bao").toggle(100);
	});
    	remindCheck();
        $(".left-z,.ul1 ul").click(function () {
            $("#id").css('left', '270px');
            $(".header-left").css('width', '260px');
            $(".menu_text").show();
            $(".ul1 ul li .cole").css('background-color', 'rgb(241,243,255)');
            $(".ul1").css({"width": "210px", "margin": "20px auto 0 auto"});
            $(".header-left .ul1 ul li img").css({
                "width": " 36px",
                "height": "36px",
                "margin-top": "8px",
                "margin-left": "10px",
                "padding-right": "10px"
            });
            $(".ac").show();
	$(".left-y").show();
	$(".left-z").hide();
        });

        $(".left-y").click(function () {
            $("#id").css('left', '80px');
            $(".header-left").css('width', '73px');
            $(".menu_text").hide();
            $(".header-left .ul1 ul li img").css({"width": " 44px", "height": "44px", "margin-left": "14px"});
            $(".ul1 ul li .cole").css('background-color', 'white');
            $(".ul1").css({"width": "73px", "margin": "12px auto 0 auto"});
            $(".ac").hide();
	$(".left-z").show();
	$(".left-y").hide();
        });
      //登记登出
		$(".sign_img").on("click",function(){
			$(".signin_out").slideToggle(50);
		});
		$(".signin_out").on("mouseleave",function(){
			$(".signin_out").slideToggle(50);
		});
		//消息提醒
		var tip_zhezhao = "<div class='tip_zhezhao' style='width:100%;height:100%;z-index: 9999;position: fixed;filter:Alpha(opacity=60);background-color: #000;top: 0;left: 0;opacity: 0.6;'></div>";
		$("#tip_alert").on("click",function(){
			$("body").append(tip_zhezhao);
			$(".smsbox").fadeIn(200);
			smsAlert();
		});
		$("body").on("click",".sms_close_win,.sms_list,.all_check",function(){
			$(".tip_zhezhao").remove();
		});
		//组织架构显示
		$("body").on("click",".group",function(){
			if($("#groupContent").css("right")=="0px"){
				$("#groupContent").animate({right:"-240px"},300);
			}else{
				$("#groupContent").animate({right:"0px"},300);
			}

		});
    })
    function getSysMenu(){
    	   //获取当前登陆人快捷菜单ids
    	   var urls=contextPath+"/personManager/getQuickMenuIds.action";
    	   var json=tools.requestJsonRs(urls);
    	   var quickMenuIds=json.rtData;
    	
    	
    		tools.requestJsonRs(contextPath+"/teeMenuGroup/getPrivSysMenu2.action",{sysId:sysId},true,function(json){
    			var data=json.rtData;
    			if(data!=null){
    				var one=data.one;
    				var two=data.two;
    				var three=data.three;
    				//一级菜单
    				if(one!=null){
    					for(var i=0;i<one.length;i++){
    						var menuId=one[i].menuId;
    						var menuName=one[i].menuName;
    						var menuCode=one[i].menuCode;
    						var icon=one[i].icon;
    						var caiDanFalg=one[i].caiDanFalg;
    						var html="<li>";
    						       if(caiDanFalg){
	    						       html+="<h1 class='inactive active cole' style='height: 50px;line-height: 50px;border-radius:5px;'>";
	    						         html+="<img src='icons/"+icon+"' onerror=\"this.src='icons/xtgl.png'\" alt='' style='float: left'>";

	    						         html+="<span  style='display: none;font-size:16px;font-family:微软雅黑' class='menu_text'>"+menuName+"</span>";
	    						       html+="</h1>";
    						    	   html+="<ul class='' id='one_"+menuId+"'></ul>";
    						       }else{
    						    	   html+="<h1 onclick=\"NewPageINfo('"+menuName+"','"+menuCode+"')\" class='active cole' style='height: 50px;line-height: 50px;border-radius:5px;'>";
      						             html+="<img src='icons/"+icon+"''  onerror=\"this.src='icons/xtgl.png'\" alt='' style='float: left'>";
      						             html+="<span  style='display: none;font-size:16px;font-family:微软雅黑' class='menu_text'>"+menuName+"</span>";
      						           html+="</h1>";
    						       }
    						     html+="</li>"
    						 $("#yijiCanDan").append(html);
    					}
    				}
    				//二级菜单
    				if(two!=null){
    					for(var i=0;i<two.length;i++){
    						var menuId=two[i].menuId;
    						var menuName=two[i].menuName;
    						var menuCode=two[i].menuCode;
    						var icon=two[i].icon;
    						var caiDanFalg=two[i].caiDanFalg;
    						var pmenuId=menuId.substring(0,3);
    						var uuid=two[i].uuid;
    						//判断是否是快捷菜单
    						var isQm=isQuickMenu(quickMenuIds,uuid);
    						
    						var html="<li>";
    						       if(caiDanFalg){
    						           html+="<h1 class='inactive active' style='height: 50px;line-height: 50px;padding-left: 65px ;margin-top: -5px;margin-bottom:-5px'><span class='menu_text'>"+menuName+"</span></h1>";
    						    	   html+="<ul id='two_"+menuId+"'>";

    						    	   html+="</ul>";
    						       }else{
    						    	   if(isQm==0){
    						    		   html+="<div uuid='"+uuid+"'  style='position: relative;left: 35px;float: left;width: 20px;height: 20px;top: 15px;' class='kjfs-img img-001'></div><h1 class='active' onclick=\"NewPageINfo('"+menuName+"','"+menuCode+"')\" style='height: 50px;line-height: 50px;padding-left:65px ;margin-top: -5px;margin-bottom:-5px'><span class='menu_text'>"+menuName+"</span></h1>";
    						    	   }else if(isQm==1){
    						    		   html+="<div uuid='"+uuid+"' style='position: relative;left: 35px;float: left;width: 20px;height: 20px;top: 15px;' class='kjfs-img img-002'></div><h1 class='active' onclick=\"NewPageINfo('"+menuName+"','"+menuCode+"')\" style='height: 50px;line-height: 50px;padding-left:65px ;margin-top: -5px;margin-bottom:-5px'><span class='menu_text'>"+menuName+"</span></h1>";
    						    	   }
    						           
    						       }
    						    html+="</li>";
    						$("#one_"+pmenuId).append(html);
    					}
    				}
    				//三级菜单
    				if(three!=null){
    					for(var i=0;i<three.length;i++){
    						var menuId=three[i].menuId;
    						var menuName=three[i].menuName;
    						var menuCode=three[i].menuCode;
    						var icon=three[i].icon;
    						var caiDanFalg=three[i].caiDanFalg;
    						var pmenuId=menuId.substring(0,6);

    						var uuid=three[i].uuid;
    						//判断是否是快捷菜单
    						var isQm=isQuickMenu(quickMenuIds,uuid);
    						if(isQm==0){
    							var html="<li><div uuid='"+uuid+"' style='position: relative;left: 35px;float: left;width: 20px;height: 20px;top: 15px;' class='kjfs-img img-001'></div><h1 onclick=\"NewPageINfo('"+menuName+"','"+menuCode+"')\" style='height: 50px;line-height: 50px;padding-left: 80px;margin-top: -5px;margin-bottom: -5px'><span class='menu_text'>"+menuName+"</span></h1></li>";
    						}else if(isQm==1){
    							var html="<li><div  uuid='"+uuid+"' style='position: relative;left: 35px;float: left;width: 20px;height: 20px;top: 15px;' class='kjfs-img img-002'></div><h1 onclick=\"NewPageINfo('"+menuName+"','"+menuCode+"')\" style='height: 50px;line-height: 50px;padding-left: 80px;margin-top: -5px;margin-bottom: -5px'><span class='menu_text'>"+menuName+"</span></h1></li>";
    						}
    						
    						$("#two_"+pmenuId).append(html);
    					}
    				}
    			}

				//$(".kjfs-img").addClass('img-001'); // 初始化时添加背景1
				$(".kjfs-img").click(function() {
					var is001 =$(this).hasClass("img-001");
					if(is001){//标为快捷菜单
						var uuid=$(this).attr("uuid");
						var url=contextPath+"/personManager/addQuickMenu.action";
						var json=tools.requestJsonRs(url,{menuId:uuid});
						if(json.rtState){
							$(this).removeClass("img-001");
							$(this).addClass("img-002");	
						}
					}else{//取消快捷菜单
						var uuid=$(this).attr("uuid");
						var url=contextPath+"/personManager/removeQuickMenu.action";
						var json=tools.requestJsonRs(url,{menuId:uuid});
						if(json.rtState){
							 $(this).removeClass("img-002");
							 $(this).addClass("img-001"); 
						}
					}
				});
    		});
    }
    
    
    //判断菜单是否是快捷菜单
    function isQuickMenu(quickMenuIds,uuid){
    	if(quickMenuIds!=null&&quickMenuIds!=""){
    		var qmIds = quickMenuIds.split(","); 
    		if(qmIds!=null&&qmIds.length>0){
    			for(var j=0;j<qmIds.length;j++){
    				if(qmIds[j]==uuid){
    					return 1;
    				}
    			}
    		}else{
    			return 0;
    		}
    		
    	}else{//之前不是快捷菜单
    		return  0;
    	}
    	return 0;
    }
    
</script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.tianee.oa.oaconst.TeeConst" %>
<%@ page import="com.tianee.oa.core.org.bean.TeePerson" %>
<%@ page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page import="com.tianee.webframe.util.date.TeeLunarCalendarUtils" %>
<%@ page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@ page import="com.tianee.webframe.util.auth.TeeAuthUtil"%>
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
	

%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title><%=ieTitle %></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="/header/userheader.jsp" %>

<!-- jQuery库 -->
<script src="<%=contextPath %>/common/zt_webframe/js/jquery-1.11.1.js"></script>
<script type="text/javascript" >
window.UEDITOR_HOME_URL = "<%=contextPath%>/common/ueditor/";
$.browser = {};
$.browser.mozilla = /firefox/.test(navigator.userAgent.toLowerCase());
$.browser.webkit = /webkit/.test(navigator.userAgent.toLowerCase());
$.browser.opera = /opera/.test(navigator.userAgent.toLowerCase());
$.browser.msie = /msie/.test(navigator.userAgent.toLowerCase());
</script>

<!-- 其他工具库类 -->
<script src="<%=contextPath %>/common/js/tools.js"></script>
<script src="<%=contextPath %>/common/js/sys2.0.js"></script>
<script src="<%=contextPath %>/common/js/sysUtil.js"></script>
<script src="<%=contextPath %>/common/js/src/orgselect.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/easyuiTools.js"></script>
<script src="<%=contextPath %>/common/zt_webframe/js/package.js"></script>

<!-- 图片预览器 -->
<script type="text/javascript" src="<%=contextPath%>/common/js/lazyloader.js"></script>
<script type="text/javascript">

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

</script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/common/zt_webframe/css/init1.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/common/zt_webframe/css/package1.css">
<script type="text/javascript" src="<%=contextPath%>/system/frame/3/js/jquery.SuperSlide.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/frame/4/js/index2.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/frame/2/js/index.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/frame/2/js/sms.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jbox-v2.3/jBox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jbox-v2.3/jBox/i18n/jquery.jBox-zh-CN.js"></script>

<link rel="stylesheet" href="css/index.css?v=1">

<script>
function doInit(){
	 queryOnlineUserCount();
	 //获取皮肤
	 getSkinFunc();
	 //初始化桌面
	 //doInitDeskTop();
}
//获取cookie中的皮肤
function getSkinFunc(){
	$(".header").css("background-color","#0099cb");
	$(".section .secLeft .avatar").css("background-color","#014876");
	$(".section .secLeft").css("background-color","#192f47");
	$(".section .default").css("background-color","#192f47");
	$(".currentIdentity").css("background-color","#496290");
	$(".footer_info").css("background-color","#2285c6");
	$(".menu_active_red").css("background-color","#496290");
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
		window.location.href = "/"+contextPath;
});
}


function toDesktop(){
	//$("#mainIframe")[0].src = "<%=contextPath%>/system/frame/default/mainForSimple.jsp";
}



</script>
</head>
<%
String todayLunarStr = TeeLunarCalendarUtils.today();
String TOP_BANNER_TEXT = TeeSysProps.getString("TOP_BANNER_TEXT");
String BOTTOM_STATUS_TEXT = TeeSysProps.getString("BOTTOM_STATUS_TEXT");
String TOP_ATTACHMENT_ID = TeeSysProps.getString("TOP_ATTACHMENT_ID");
%>
<body onload="doInit()">
<!--------------- 系统头部 -------------->
	<div class="header">
	<!---------- logo ---------->
		<div class="logo fl">
			<img src="imgs/index/logo.png"/>
			系统管理员后台
		</div>
		
		<div class="fl clearfix firstMenuContent">
			<ul id = 'firstMenuList' class = 'clearfix fl'>
				<!-- 
			    <img class = 'home' style="cursor:pointer" src="imgs/index/home.png" onclick="toDesktop()" title="桌面"> -->
				<!-- 这里是第一级列表 -->
			</ul>
		</div>
		<div class = 'fl menuListRight'>
			<span class = 'moreApp fl'>更多应用
				<img src="imgs/index/xialasanjiao.png">
				<ul class='moreAppcContent'></ul>
			</span>
			
			<!-- 搜索
			<span class="search "><img src="imgs/index/search.png"></span>
			<input type="text" class='search_input fl' >
			 -->
			
			<ul class = ' clearfix fl'>
				
				<!-- 企业社区
				<li onclick="openFullWindow('/system/core/base/weibo/index.jsp')" title="企业社区"><img src="imgs/index/weibo.png"></li>
				 -->
				 
				<!-- 消息事务
				<li>
					<img class='sms' id='tip_alert' src="imgs/index/alert.png">
					<!--  <p class="top_fot" title="消息事务">消息事务</p> -- >
                	<div class="sign_in_tid" style="display:none">新的消息！</div>
				</li>
				 -->
				
				<!-- 登记
				<li>
					<img class="sign_img" src="imgs/index/sign.png">
					<div class="signin_out">
						<p id="signInTime" class='sign_time' style="font-weight: bold;font-size: 16px;"></p>
						<p><button id="signInButton" class='sign_btn' onclick="signInFunc();" style='color:#fff;'>登记</button></p>
					</div>
				</li>
				 -->
				
				<li onclick="doLogout()"><img src="imgs/index/switch.png"></li>
			</ul>
			
			<!-- IM工具
			<img class = 'group fl' src="imgs/index/group.png">
			<div id='groupContent' style='width:240px;position:fixed;right:-240px;top:60px;bottom:32px;z-index:99999;box-shadow:0 0 15px #a2a2a2;'>
				<iframe style='width:100%;height:100%;' id='right' src="/system/frame/3/right.jsp" frameborder="0"></iframe>
			</div> -->
		</div>

	</div><!-- header结束 -->

	<div class="section">
		<div class="secLeft">
			<div class="avatar">
				<span class='radius'>
					<img class = 'avatarIcon' src="<%=contextPath %>/attachmentController/downFile.action?id=<%=avatar %>" width="40" height="40" onerror="this.src = '<%=request.getContextPath() %>/common/images/default_avatar.gif'">
				</span>
				<span>
				<%
            		if(userName.length()>4){
            			out.print("<span style='font-size:12px'>"+userName+"</span>");
            		}else{
            			out.print(userName);
            		}
            	%>
                </span>
				<img hidden class ='shareIcon' src="imgs/index/share.png">
				<ul hidden class='positionBlock'>
					<li>
						<span class='current_pos'><img src="imgs/index/current_pos.png" alt=""></span>
						<span class='position'>总经理</span>
						<span class='note'>(集团总经办)</span>
					</li>
					<li>
						<span class='current_pos'><img src="imgs/index/current_pos.png" alt=""></span>
						<span class='position'>总经理</span>
						<span class='note'>(集团总经办)</span>
					</li>
				</ul>
			</div>

			<div class="secMenuListContent"><!-- 左侧的二级菜单 -->
				<ul id = 'secMenuList' class="">
					<div class="default">
						<p style="cursor:pointer;" class='home'><img src="imgs/index/xialasanjiao.png">桌面</p>
						<ul class = 'defaultContent' id="desktopDiv">
							<!-- <li onclick="changePage(12)"><img src="imgs/index/page.png">个人门户</li>
							<li><img src="imgs/index/page.png">公司门户</li>
							<li><img src="imgs/index/page.png">管理门户</li> -->
						</ul>
					</div>
						<div class="settingContent">
						<p style="cursor:pointer;" class='setHead'><img src="imgs/index/xialasanjiao.png">设置中心</p>
						<ul class = 'defaultContent'>
							<li>
								<a href="#">
									<img src="imgs/index/page.png">界面设置<img class="menu_hide_show" src="imgs/index/hide.png">
								</a>
								<ul class='setContent_ul'>
									<li onclick="changePage(1)">界面主题</li>
									<li onclick="changePage(2)">收藏夹</li>
									<li onclick="changePage(3)">桌面空间</li>
								</ul>
							</li>
							<li>
								<a href="#">
									<img src="imgs/index/page.png">个人信息<img class="menu_hide_show" src="imgs/index/hide.png">
								</a>
								<ul class='setContent_ul'>
									<li onclick="changePage(4)">个人资料</li>
									<li onclick="changePage(5)">昵称与头像</li>
									<li onclick="changePage(6)">自定义用户组</li>
									<li onclick="changePage(7)">电子签章管理</li>
									<li onclick="changePage(13)">图片签章管理</li>
									<li onclick="changePage(8)">常用语维护</li>
								</ul>
							</li>
							<li>
								<a href="#">
									<img src="imgs/index/page.png">账户安全<img class="menu_hide_show" src="imgs/index/hide.png">
								</a>
								<ul class='setContent_ul'>
									<li onclick="changePage(9)">我的账户</li>
									<li onclick="changePage(10)">修改密码</li>
									<li onclick="changePage(11)">安全日志</li>
								</ul>
							</li>
						</ul>
					</div>
				</ul>


			</div>

		</div>

		<div class="mainContent">
			<iframe id='mainIframe'  frameborder="0" style="box-sizing: border-box;" src="">

			</iframe>
		</div><!-- section结束 -->

		</div>
		<div class="footer">
		<div class="setting">
			<p class='currentIdentity'>
				<img class='setIcon' src="imgs/index/set.png">
				<span>
					<img class = 'themeIcon' src="imgs/index/pageStyle.png">
					<ul class='themeList'>
						<li><span class='theme_red' value="red"></span></li>
						<!-- <li><span class='theme_black' value="black"></span></li> -->
						<li><span class='theme_blue' value="blue"></span></li>
					</ul>
				</span>
<!-- 				<img class='setIcon12' src="imgs/index/set.png"> -->
			</p>
			
		</div>
		
		<div id="scrollDiv_keleyi_com" class="scrollDiv" style="overflow:hidden"> 
				<ul>
						<li><span class='footer_info' id="onlineUserCount"></span>
                        </li>
						<%
			if(!TeeAuthUtil.getInfoString().equals("")){
				%>
				<li><span class='footer_info'><%=TeeAuthUtil.getInfoString() %></span>
				</li>
				<%
			}else{
				%>
				<%
					String sp [] = BOTTOM_STATUS_TEXT.split("\n");
					for(int i=0;i<sp.length;i++){
						%>
						<li><span class='footer_info'><%=sp[i] %></span></li>
						<%
					}
				%>
					<%
					}
				%>
						
				</ul> 
		</div> 
				<script type="text/javascript"> 
				function AutoScroll(obj){ 
				$(obj).find("ul:first").animate({ 
				marginTop:"-32px",background:"none"
				},500,function(){ 
				$(this).css({marginTop:"0px",background:"none"}).find("li:first").appendTo(this); 
				});
				} 
				$(document).ready(function(){ 
				setInterval('AutoScroll("#scrollDiv_keleyi_com")',5000);
				}); 
				</script> 
		
	</div>
	
	<!-- 短消息面板 -->
		<div class="smsbox" id="smsbox" style="display:none">
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
		<div style="right: 5px; bottom: 30px; width: 200px; max-height:500px;overflow-y:auto;overflow-x:hidden; position: absolute;" id="smsInfo">
		</div>


		<!-- 搜索框面板 -->
		<div class="quick_search_div" style="overflow:hidden;">
			<div class="closeBtn">
				<span class="searchTitle">搜索</span>
				<span onclick="$('.quick_search_div').hide()">×</span>
			</div>
			<iframe src="/system/core/base/quicksearch/search_result_1.jsp" frameborder="0" style="height:98%;width:99%"></iframe>
		</div>
		
	<script type="text/javascript">
	$.ajax({
		type:'post',
		url:"/bdadmin/getAdminMenu.action",
		beforeSend: function(XMLHttpRequest){
		},
		success: function(data, textStatus){
			var json = JSON.parse(data);
			renderFirstMenu(json);
			renderSecondMenu(json);
			other();
			setMoreAPP();
			$(".default").show();
		},
		complete: function(XMLHttpRequest, textStatus){
			
		},
		error: function(){
			$.MSg.Alert("提示",'获取文件失败！');
		}
	})

	var windowWidth = $(window).width();
	function renderFirstMenu(data){
		var str = '';
		for(var i =0;i<data.rtData.length;i++){
		if(data.rtData[i].menuId.length == 3){
		str = '<li id="menu-lv1-' +data.rtData[i].menuId + '" class="firstMenu fl menu-lv1-' + data.rtData[i].menuId +'" menuCode=\"'+ data.rtData[i].menuCode +'\" title="'+ data.rtData[i].menuName +'">' +
		'<img src="imgs/icon/2/' + data.rtData[i].icon + '">'+
		data.rtData[i].menuName +
		'</li>';
		$("#firstMenuList").append(str);
		}
		}

		};


	function renderSecondMenu(data){//具有折叠功能二级菜单
		var str2 = '';
		$(".default").hide();
		for(var i = 0,l=data.rtData.length;i<l;i++){
			if(data.rtData[i].menuId.length == 6){
				var parentId = data.rtData[i].menuId.substring(0,6);
				str2 = '<li style="display:none;" id="menu_2_' + data.rtData[i].menuId +'" class="" title="' + data.rtData[i].menuName + '">' +
							'<a id="' + data.rtData[i].menuId + '" menuCode="' + data.rtData[i].menuCode + '"> ' + '<img class="menu_img" src="imgs/icon/2/' + data.rtData[i].icon + '">' + data.rtData[i].menuName +
							'</a>' +
 								'<ul class="thirdMenuList"' + 'class="" style="display:none;">' +
									'<div class=""' + 'menuCode="' + data.rtData[i].menuCode + '"id = "thirdMenu' + parentId + '">'
									'</div>' +
								'</ul>'+
						'</li>';

				$("#secMenuList").append(str2);
			}else if(data.rtData[i].menuId.length == 9){
				var parentId = data.rtData[i].menuId.substring(0,6);
				var str3 = "<p id='menu_3_" + data.rtData[i].menuId + "' menuCode='" + data.rtData[i].menuCode + "' title='" + data.rtData[i].menuName + "'>" + data.rtData[i].menuName + '</p>';

				$("#thirdMenu"+parentId).append(str3);
				var $a = $("#thirdMenu"+parentId).parent().siblings("a");
				if(!$a.hasClass("hasImg")){
					$a.append("<img class='menu_hide_show' src='imgs/index/hide.png'>").addClass("hasImg");
				}

			}
		};
		var menuHead = '<div class="menuHead"><p style="cursor:pointer;" class="setHead"><img src="imgs/index/xialasanjiao.png"><span></span></p></div>'
		$("#secMenuList .settingContent").after(menuHead);
	}

		function other(){

			//一级菜单的点击绑定
			$("#firstMenuList li").on("click",function(){
				$(".default").hide();
				$(".settingContent").hide();
				var menuCode = $(this).attr("menuCode");

				if(menuCode!='' && menuCode!="undefined" && menuCode!=undefined){
					
					$("#mainIframe").attr("src",menuCode);
					$(".menuHead p span").text($(this).text());
					$("#secMenuList>li").hide();
				}else{
					var first_id = $(this).attr("id").substring(9,12);
					for(var i=0,l=$("#secMenuList>li").length;i<l;i++){
					var second_id = $($("#secMenuList>li")[i]).attr("id").substring(7,10);
					if(first_id==second_id){
					$(".menuHead").show();
					$(".menuHead p span").text($(this).text());
					$($("#secMenuList>li")[i]).show();
					}else{
					$($("#secMenuList>li")[i]).hide();
					}
					}
				}
	
			});
			//点击主页
			$(".home").on("click",function(){
				$(".default").show();
				$(".menuHead").hide();
				$(".settingContent").hide();
				$("#secMenuList>li").hide();
			});
			//点击设置
			$(".setIcon").on("click",function(){
				$(".default").hide();
				$(".menuHead").hide();
				$("#secMenuList>li").hide();
				$(".settingContent").show();
			});
			//设置页面的折叠功能
			$(".defaultContent>li").on("click",function(){
				$(this).find(".setContent_ul").slideToggle(100);
				if(!$(this).hasClass("slideDown")){
						$(this).find(".menu_hide_show").attr("src","imgs/index/show.png");
						$(this).addClass("slideDown");
						$(".settingContent .defaultContent .slideDown").css("padding-left","0px!important")
					}else{
						$(this).find(".menu_hide_show").attr("src","imgs/index/hide.png");
						$(this).removeClass("slideDown");
					}
			});
			$(".setContent_ul").on("click",function(e){
				e.stopPropagation();
			});

			//二级菜单的点击折叠
			$('#secMenuList>li').on('click',function(){
				if($(this).find(".menu_hide_show").length!=0){
					$(this).find('.thirdMenuList').slideToggle(100);
					if(!$(this).hasClass("slideDown")){
						$(this).find(".menu_hide_show").attr("src","imgs/index/show.png");
						$(this).addClass("slideDown");
					}else{
						$(this).find(".menu_hide_show").attr("src","imgs/index/hide.png");
						$(this).removeClass("slideDown");
					}
				}
			});
			//阻止下拉菜单中的点击冒泡
			$(".thirdMenuList,.setContent_ul").on("click",function(e){
				e.stopPropagation();
			});
			//桌面互换点击事件
			
			$("body").on("click","#desktopDiv li",function(){
				var skin = getCookie("skinChange");
				if(skin == "blue"||skin==""||skin==null){
					$(".thirdMenuList p").removeClass("menu_active_blue");
					$("#secMenuList li").removeClass("menu_active_blue");
					$(this).addClass("menu_active_blue");
				}else if(skin == "red"){
					$(".thirdMenuList p").removeClass("menu_active_red");
					$("#secMenuList li").removeClass("menu_active_red");
					$(this).addClass("menu_active_red");
				};
				
			});
			//二级三级菜单绑定事件切换iframe
			$("#secMenuList li").on("click",function(){
				if($(this).find("a").attr("menucode")!="" && $(this).find("a").attr("menucode")!="undefined" && $(this).find("a").attr("menucode")!=undefined){
					var src = $(this).find("a").attr("menuCode");
					$("#mainIframe").attr("src",src);
					var skin = getCookie("skinChange");
					if(skin == "blue"||skin==""||skin==null){
						$(".thirdMenuList p").removeClass("menu_active_blue");
						$("#secMenuList li").removeClass("menu_active_blue");
					}else if(skin == "red"){
						$(".thirdMenuList p").removeClass("menu_active_red");
						$("#secMenuList li").removeClass("menu_active_red");
					};
					
					if($(this).find(".menu_hide_show").length==0){
						//$(this).addClass("menu_active");
						if(skin == "blue"||skin==""||skin==null){
							$(this).addClass("menu_active_blue");
							$(this).addClass("menu_active_blue");
						}else if(skin == "red"){
							$(this).addClass("menu_active_red");
							$(this).addClass("menu_active_red");
						};
					}


				}
			})
			$(".thirdMenuList p").on("click",function(){
				var skin = getCookie("skinChange");
				if($(this).attr("menucode")!=""){
					var src = $(this).attr("menuCode");
					$("#mainIframe").attr("src",src);
					if(skin == "blue"||skin==""||skin==null){
						$("#secMenuList li").removeClass("menu_active_blue");
						$(".thirdMenuList p").removeClass("menu_active_blue");
					}else if(skin == "red"){
						$("#secMenuList li").removeClass("menu_active_red");
						$(".thirdMenuList p").removeClass("menu_active_red");
					};
					if(skin == "blue"||skin==""||skin==null){
						$(this).addClass("menu_active_blue");
					}else if(skin == "red"){
						$(this).addClass("menu_active_red");
					};
					
					
					//$(".thirdMenuList p").removeClass("menu_active");
					//$("#secMenuList li").removeClass("menu_active");
					//$(this).addClass("menu_active");
				};
			});
		};
		//设置一级菜单自适应显示
		var win_width = $(window).width();
		$(".firstMenuContent").width(win_width-650);
		$(window).resize(function(){
			var win_width = $(window).width();
			$(".firstMenuContent").width(win_width-650);
			setMoreAPP();
		});
		//更多按钮
		function setMoreAPP(){
			$(".moreAppcContent").html("");
			var f_li = $("#firstMenuList li")
			var le = f_li.length;
			for(var i=0;i<le;i++){
				var offset_top = $(f_li[i]).offset().top;
				if(offset_top != 0){
					var li_copy = $(f_li[i]).clone();
					// console.log(li_copy);
					var img_src = $(li_copy).find("img").attr("src");
					var img_name = img_src.substring(12);
					var newName = "imgs/icon/1/" + img_name;
					// img_src.replace("2","1");
					$(li_copy).find("img").attr("src",newName);
					if(li_copy.length!=0){
						$(li_copy).remove();
					}
					$(".moreAppcContent").append(li_copy);
				}else{
					var className = $(f_li[i]).attr("class");
					var arr = className.split(" ");
					for(var j=0,l=arr.length;j<l;j++){
						var s_0_8 = arr[j].substring(0,8);
						if(s_0_8 == "menu-lv1"){
							className = arr[j];
						}
					}
					$(".moreAppcContent ."+className).remove();
				}
			}

		}
		$(".moreApp").on("click",function(event){
			event.stopPropagation();
			$(".moreAppcContent").slideToggle(100);
			setMoreAPP();
		});
		$(".header").on("click",function(){
			$(".moreAppcContent").slideUp(100);
			$("#groupContent").animate({right:"-240px"},300);
		});
		$(".secLeft").on("click",function(){
			$(".moreAppcContent").slideUp(100);
			$("#groupContent").animate({right:"-240px"},300);
		});
		$(".moreApp").on("click",".moreAppcContent li",function(){
			$(".default").hide();
			$(".settingContent").hide();
			var menuCode = $(this).attr("menuCode");
			/*检查一级目录是否有menuCode*/
			if(menuCode!='' && menuCode!=undefined && menuCode!='undefined'){
				$("#mainIframe").attr("src",menuCode);
				$(".menuHead p span").text($(this).text());
				$(".section .menuHead").show();
				$("#secMenuList>li").hide();
			}else{
				var first_id = $(this).attr("id").substring(9,12);
				for(var i=0,l=$("#secMenuList>li").length;i<l;i++){
				var second_id = $($("#secMenuList>li")[i]).attr("id").substring(7,10);
				if(first_id==second_id){
				$(".menuHead").show();
				$(".menuHead p span").text($(this).text());
				$($("#secMenuList>li")[i]).show();
				}else{
				$($("#secMenuList>li")[i]).hide();
				}
				}
			}

		});

		//抖动功能
		function shake(id){
		    var $panel = $("#"+id);
		    $panel.css({'position':'relative'});
		    for(var i=1; i<=4; i++){
		        $panel.animate({left:5},30);
		        $panel.animate({right:5},30);
		        $panel.animate({left:0},30);
		    }
		};
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
		});
		$("body").on("click",".sms_close_win,.sms_list,.all_check",function(){
			$(".tip_zhezhao").remove();
		});
		//皮肤切换
		$(".themeIcon").click(function(){
			$(".themeList").slideToggle(100);
		});
		$(".themeList").mouseleave(function(){
			$(this).hide();
		});
		$(".themeList li").click(function(){
			var skinColor = $(this).find("span").attr("value");
			switch(skinColor){
				case "red":
					$(".header").css("background-color","#e87676");
					$(".section .secLeft .avatar").css("background-color","#a33b3c");
					$(".section .secLeft").css("background-color","#713e3d");
					$(".section .default").css("background-color","#713e3d");
					$(".currentIdentity").css("background-color","#be6464");
					$(".footer_info").css("background-color","#eb4849");
					
					$(".menu_active_blue").addClass("menu_active_red");
					$(".thirdMenuList p").removeClass("menu_active_blue");
					$("#secMenuList li").removeClass("menu_active_blue");
					setCookie("skinChange" , "red" , 365);
					break;
				case "blue":
					$(".header").css("background-color","#0099cb");
					$(".section .secLeft .avatar").css("background-color","#014876");
					$(".section .secLeft").css("background-color","#192f47");
					$(".section .default").css("background-color","#192f47");
					$(".currentIdentity").css("background-color","#496290");
					$(".footer_info").css("background-color","#2285c6");
					$(".menu_active_red").addClass("menu_active_blue");
					$(".thirdMenuList p").removeClass("menu_active_red");
					$("#secMenuList li").removeClass("menu_active_red");
					setCookie("skinChange" , "blue" , 365);
					break;
			}
		});
		
		
		//菜单鼠标移入上滑
		$("body").on("mouseover","#firstMenuList li",function(){
			var top = $(".firstMenu").offset().top;
			//console.log(top);
			$(this).css("position","relative");
			$(this).stop().animate({top:"-3px"},100);
			//console.log(top);
		});
		$("body").on("mouseleave","#firstMenuList li",function(){
			$(this).css("position","relative");
			var top = $(this).offset().top;
			$(this).stop().animate({top:"0px"},100);
		});
		
		//组织架构显示
		$("body").on("click",".group",function(){
			if($("#groupContent").css("right")=="0px"){
				$("#groupContent").animate({right:"-240px"},300);
			}else{
				$("#groupContent").animate({right:"0px"},300);
			}

		});		
		
        //控制面板界面跳转
		function changePage(sel){
			if(sel==1){
				$("#mainIframe").attr("src",contextPath+"/system/core/person/setdescktop/desktop.jsp");//界面主题
			}else if(sel==2){
				$("#mainIframe").attr("src",contextPath+"/system/core/fav/manage.jsp");//收藏夹
			}else if(sel==3){
				$("#mainIframe").attr("src",contextPath+"/system/core/person/setdescktop/desktopSetting.jsp");//桌面空间
			}else if(sel==4){
				$("#mainIframe").attr("src",contextPath+"/system/core/person/setdescktop/info.jsp");//个人资料
			}else if(sel==5){
				$("#mainIframe").attr("src",contextPath+"/system/core/person/setdescktop/avatar.jsp");//昵称与头像
			}else if(sel==6){
				$("#mainIframe").attr("src",contextPath+"/system/core/dept/usergroup/personalgroup.jsp");//自定义用户组
			}else if(sel==7){
				$("#mainIframe").attr("src",contextPath+"/system/core/person/setdescktop/updateSealPwd.jsp");//印章密码修改
			}else if(sel==8){
				$("#mainIframe").attr("src",contextPath+"/system/core/person/commonword/list.jsp");//常用语维护
			}else if(sel==9){
				$("#mainIframe").attr("src",contextPath+"/system/core/person/setdescktop/mypriv.jsp");//我的账户
			}else if(sel==10){
				$("#mainIframe").attr("src",contextPath+"/system/core/person/setdescktop/password.jsp");//修改密码
			}else if(sel==11){
				$("#mainIframe").attr("src",contextPath+"/system/core/person/setdescktop/securitylog.jsp");//安全日志
			}else if(sel==12){
				$("#mainIframe").attr("src",contextPath+"/system/frame/default/mainForSimple.jsp");//首页--个人门户
			}else if(sel==13){
				$("#mainIframe").attr("src",contextPath+"/system/core/person/setdescktop/updatePicSealPwd.jsp");//签章密码修改
			}
		}

		setCookie("skinChange" , "blue" , 365);
		setCookie("skin_new" , "1" , 365);
	</script>
</body>
</html>
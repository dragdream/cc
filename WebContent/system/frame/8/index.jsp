<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="net.sf.json.JSONObject" %>
<%@ page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page import="com.tianee.webframe.util.auth.TeeAuthUtil"%>
<%@ page import="com.tianee.webframe.util.date.TeeLunarCalendarUtils" %>
<%
	String contextPath = request.getContextPath();
	//IE浏览器窗口标题
	String ieTitle = TeeSysProps.getString("IE_TITLE");
	//获取日期信息
	String todayLunarStr = TeeLunarCalendarUtils.today();
	//转换成json对象
	//JSONObject obj = JSONObject.fromObject(todayLunarStr);
	//日期	eg:2016年8月25日
	//String now_date=obj.getString("date");
	//星期	eg:星期四
	//String week=obj.getString("week");
	//农历	eg:农历丙申(猴)年七月廿三
	//String lunarDate=obj.getString("lunarDate").substring(8, obj.getString("lunarDate").length());
	//顶部大标题文字
	String TOP_BANNER_TEXT = TeeSysProps.getString("TOP_BANNER_TEXT");
	//底部状态栏置中文字
	String BOTTOM_STATUS_TEXT = TeeSysProps.getString("BOTTOM_STATUS_TEXT");
	String TOP_ATTACHMENT_ID = TeeSysProps.getString("TOP_ATTACHMENT_ID");
	String systemImagePath = contextPath+"/common/images";
%>
<!DOCTYPE HTML>
<HTML lang="en">
<head>
<meta content="IE=edge" http-equiv="X-UA-Compatible">
<meta http-equiv="content-type" content="text/html;charset=utf-8">
<%@ include file="/header/userheader.jsp" %>
<title><%=ieTitle %></title>
<!-- <link type="text/css" href="./ico/xtd.ico" rel="shortcut icon"> -->
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/common/bootstrap/css/bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="./css/icomoon.css">
<link rel="stylesheet" type="text/css" href="./css/bootstrap-tour.css">
<link rel="stylesheet" type="text/css" href="./css/select2.css">
<link rel="stylesheet" type="text/css" href="./css/td-bootstrap.css">
<link rel="stylesheet" type="text/css" href="./css/base.css">
<link rel="stylesheet" type="text/css" href="./css/theme.css">
<link rel="stylesheet" type="text/css" href="./skin/0/style.css" id="skin_8">
<link rel="stylesheet" type="text/css" href="./css/sms.css">
<script type="text/javascript" src="./js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/easyuiTools.js"></script>
<!-- JBOX通用UI组件 -->
<script type="text/javascript" src="<%=contextPath%>/common/jbox-v2.3/jBox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jbox-v2.3/jBox/i18n/jquery.jBox-zh-CN.js"></script>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/jbox-v2.3/jBox/Skins/Blue/jbox.css" />
<script type="text/javascript" src="<%=contextPath%>/system/frame/8/js/index.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jquery/portlet/jquery-ui-1.9.2.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jquery/portlet/jquery.portlet.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/frame/8/js/sms.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/frame/8/js/tabs.js"></script>
<!-- 其他工具库类 -->
<script type="text/javascript" src="<%=contextPath %>/common/js/tools.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/sys.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/sysUtil.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/src/orgselect.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/lazyloader.js"></script>
<!-- 图片预览 -->
<script type="text/javascript" src="<%=contextPath%>/common/js/picexplore/jquery.mousewheel.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/picexplore/picexplore.js"></script>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/js/picexplore/picexplore.css">

<script type="text/javascript" src="./js/jquery/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="./js/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript" src="./js/bootstrap/bootstrap-tour.min.js"></script>
<script type="text/javascript" src="./js/jquery/jquery.mousewheel.js"></script>
<script type="text/javascript" src="./js/jquery/jquery.marquee.min.js"></script>
<script type="text/javascript" src="./js/jquery/jquery.json-2.4.min.js"></script>
<script type="text/javascript" src="./js/util.js"></script>
<script type="text/javascript" src="./js/jquery/jquery.nicescroll.js"></script>
<script type="text/javascript" src="./js/jquery/jquery.autocomplete.js"></script>
<script type="text/javascript" src="./js/message-voice.js"></script>
<script type="text/javascript">
	var contextPath = "<%=contextPath%>";
	//用于判断是否自动弹出消息提醒
	var autoPopSms = "<%=person.getAutoPopSms()%>";
	var systemImagePath = "<%=systemImagePath%>";
	$(document).ready(function(){
		//获取消息提醒信息
		remindCheck();
		//判断当前用户是否有默认头像，如果没有，给出默认值
		if(avatar_default=="0"){//0为男
			$("#defaultImg").attr("src","./images/avatars/male_m_offline.png");
			$("#defaultImg2").attr("src","./images/avatars/male_m_offline.png");
		}else if(avatar_default=="1"){//1为女
			$("#defaultImg").attr("src","./images/avatars/female_m_offline.png");
			$("#defaultImg2").attr("src","./images/avatars/male_m_offline.png");
		}
	});
	var preLoadStatus = {
		image : true,
		sidePanel : false,
		app : false,
		weather : false
	};
	var $CONFIG = {};
	/*注销*/
	$CONFIG['url_logout'] = 'logout.jsp';
	$CONFIG['logoutText'] = '轻轻的您走了，正如您轻轻的来……';
	$CONFIG['loginUser'] = '<%=userName %>';
	$CONFIG['loginUserId'] = '<%=userId%>';
	/*皮肤名称*/
	$CONFIG['skins'] = [ "中国红", "天之蓝", "春意绿"];
	$CONFIG['enable_loading'] = 1;
	//弹出消息提醒
	function remindCheck(){
		tools.requestJsonRs(contextPath+"/sms/remindCheck.action",{},true,function(json){
			if(json.rtState){
				if(json.rtData.smsFlag!=0){//弹出短消息
					msgFrame.playSound(2);
					if(autoPopSms=="0"){
						$('#new_sms').show();
					}else{
						smsAlert();
					}
				}
				if(json.rtData.msgFlag!=0){//弹出通讯消息
					msgFrame.playSound(1);
					var offlineMsgJson = tools.requestJsonRs(contextPath+"/messageManage/getOfflineMessages.action");
					if(offlineMsgJson!=null&&offlineMsgJson.rtData.length>0){
						for(var i=0;i<offlineMsgJson.rtData.length;i++){
							socketHandler(offlineMsgJson.rtData[i]);
						}
					}
				}
			}
			setTimeout("remindCheck()",10*1000);//10秒
		});
	}
</script>
</head>
<BODY style="overflow:hidden">
	<DIV id="preOverlay"></DIV>
	<headER id="header">
		<DIV class="header">
			<DIV class="banner_text" style="font-family:;">
			<% 
				if(TeeStringUtil.getString(TOP_BANNER_TEXT).equals("")){
					out.print("协同办公智能管理平台");
				}else{
					out.print(TOP_BANNER_TEXT);
				}
			%>
			</DIV>
			<DIV class="header_right" id="navbar">
				<DIV class="header_right_top">
					<DIV class="header_widget"></DIV>
					<DIV class="header_menu" id="menu_more">
						<SPAN class="menu">更多功能</SPAN>
					</DIV>
					<DIV class="header_nav">
						<UL class="reset" node-type="appParentPanel" id="sidebar_ul">
						</UL>
					</DIV>
				</DIV>
				<DIV class="header_menu_list" node-type="appMenuList">
					<UL class="reset" id="sidebar_more"></UL>
				</DIV>
				<DIV class="header_sub_panel" node-type="appSubPanel" id="secondMenu">
				</DIV>
				<DIV class="header_menu_panel" node-type="appMenuPanel" id="secondMenuDetail"></DIV>
			</DIV>
			<UL id="datetime">
				<!--显示用户名字 -->
				<LI><%=userName%>,您好！欢迎访问<%=TOP_BANNER_TEXT%>！</LI>
			</UL>
			<!-- end top nav -->
			<DIV class="tabs" id="taskbar">
				<DIV class="taskbar_scroll taskbar_scroll_left"
					node-type="taskBarLScroll"></DIV>
				<UL class="taskbar_content" node-type="taskBarContent">
					<LI class="uncloseable active" closeable="false"
						action-data="desktop"><A class="tab" hidefocus="hidefocus"
						href="javascript:;">办公桌面</A></LI>
				</UL>
				<DIV class="taskbar_scroll taskbar_scroll_right"
					node-type="taskBarRScroll"></DIV>
			</DIV>
			<!-- end taskbar -->
		</DIV>
	</headER>
	<!-- 短消息面板 -->
	<div class="smsbox" id="smsbox" style="display:none">
		<div class="title0" id="sms_box_id">
		<span style="float:left;">消息提醒</span>
		<span style="float:right;cursor:pointer" onclick="$('#smsbox').hide()">×</span>
		<span style="float:right;cursor:pointer" class="smsbox_icon2" onclick="smsDetails()">消息列表</span>
		<span style="float:right;cursor:pointer" class="smsbox_icon1" onclick="smsViewAlls()">全部已阅</span>
		<div style="clear:both"></div>
		</div>
		<div class="content" id="smsBoxContent">
			<div class="smsbox_loadmore" id="loadMore">
				
			</div>
		</div>
	</div>
	<DIV class="clearfix containers" id="container">
		<DIV class="c_column1" id="c_funcarea">
			<!--个人信息区-->
			<DIV class="avatar">
				<IMG  id="defaultImg" src="<%=contextPath%>/attachmentController/downFile.action?id=<%=avatar%>&model=person" node-type="avatarImg"/>
				<DIV class="avatar_status T_online_status" node-type="avatarStatus"
					node-data="0"></DIV>
				<DIV class="userinfo_wrap" style="display: none;"
					node-type="myInfoPanel">
					<DIV class="bd">
						<DIV class="bd_pic" node-type="bd_pic">
                            <IMG id="defaultImg2" src="<%=contextPath%>/attachmentController/downFile.action?id=<%=avatar%>&model=person"/>
						</DIV>
						<DIV class="bd_txt" node-type="bd_txt">	
							<SPAN class="bd_stongdesc T_fs14"><%=userName%></SPAN> <SPAN
								class="bd_desc">部门：<%=currUserDeptName %></SPAN> <SPAN class="bd_desc">角色：<%=currUserRoleName %></SPAN>
						</DIV>
					</DIV>
				</DIV>
			</DIV>
			<!--消息提醒区域-->

			<DIV class="msg">
		
				<a class="notifi_box voice" href="javascript:smsAlert();" node-type="notification" >
					<i class="btn-danger" id="new_sms" style="display:none">new</i>
				</a>
			</DIV>
			<!--功能设置区域-->
			<DIV class="toolbar">
				<A class="tb_box skin" href="javascript: void(0);" node-type="switchSkin" node-tips="更换界面风格"></A>
				<A title="控制面板"	class="tb_box set" onclick="P.headTaskBar.createTab('settings', '控制面板', '/system/core/person/setdescktop/')" href="javascript:;"></A>
				<A title="注销" class="tb_box quit" href="javascript: void(0);" node-type="logout" node-tips="注销"></A></A>
			</DIV>
		</DIV>
		<!--主框架-->
		<DIV class="c_column2" id="c_content">
			<DIV class="tabs_panel tabs_panel_noshadow active"
				action-data="tabs_desktop">
				<IFRAME name="tabs_desktop_iframe" src="/system/frame/default/mainForSimple.jsp" border="0"
					frameborder="0" framespacing="0" marginwidth="0" marginheight="0"
					scrolling="auto" allowtransparency="true"
					action-data="desktop_iframe"></IFRAME>
			</DIV>
		</DIV>
		<!--组织架构-->
		<DIV class="c_column3" id="c_orglist">
			<A class="orglist_left_expbtn" id="c_orglist_left" href="javascript: void(0);" node-type="leftExpBtn"><SPAN class="expbtn_click"><EM class="orglist_arrow"></EM></SPAN></A>
			<DIV id="c_orglist_wrapper">
				<!--组织架构列表-->
				<DIV class="orglist_main" node-type="orglist">
					<DIV class="body clearfix">
							<iframe src="./right.jsp" frameborder="no" style="width:198px;height:100%;border:0;overflow: hidden;"></iframe>
					</DIV>
				</DIV>
			</DIV>
		</DIV>
	</DIV>
	<DIV class="footer" id="footer">
		<DIV class="footer_content">
			<span style='display:inline-block;float:left;font-size:12px'>当前登录人：<font><%=userName %></font></span>
			<%
				if(!TeeAuthUtil.getInfoString().equals("")){
					%>
					<span style="font-size:12px"><%=TeeAuthUtil.getInfoString() %></span>
					<%
				}else{
					%>
					<div class="status_text"> 
						<ul>
						<%
							String sp [] = BOTTOM_STATUS_TEXT.split("\n");
							for(int i=0;i<sp.length;i++){
								%>
								<li><%=sp[i] %></li>
								<%
							}
						%>
						</ul> 
						</div>
					<%
				}
			%>
		</DIV>
	</DIV>
	<DIV id="sys_sound" style="height: 0px;"></DIV>
	<script>
		$(function() {
			 getMenuList();
			$('.status_text').marquee({
				auto : true,
				interval : 4000,
				showNum : 1,
				stepLen : 1,
				type : 'vertical'
			});
		});
	</script>
	<script src="./js/theme.js?v=3"></script>
	<script>
		$(function() {
			//初始化页面执行
			P.init();
			//自适应窗口变动
			$(window).resize(function() {
				P.resizeLayout();
			});

			$('body').click(function(e) {
				P.onlineStatus.hide();
				P.logout.hide();
			});
		});
	</script>
	<script src="./js/select2.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		jQuery(function($) {
			$('[rel=tooltip]').tooltip(
							{
								'container' : 'body',
								'template' : '<div class=\"tooltip\ "><div class=\"tooltip-shadow\"><div class=\"tooltip-container\" ><div class=\"tooltip-arrow\"><em><\/em><span><\/span><\/div><div class=\"tooltip-inner\"><\/div><\/div><\/div><\/div>',
								'animation' : true
							});
		});
	</script>
</BODY>
</HTML>

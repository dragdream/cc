<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.tianee.oa.oaconst.TeeConst" %>
<%@ page import="com.tianee.oa.oaconst.TeeModelIdConst" %>
<%@ page import="com.tianee.oa.core.org.bean.TeePerson" %>
<%@ page import="com.tianee.webframe.servlet.TeeResPrivServlet"%>
<%@ page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page import="com.tianee.webframe.util.date.TeeDateUtil"%>
<%@ page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page import="com.tianee.webframe.util.date.TeeLunarCalendarUtils" %>
<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@page import="org.springframework.core.io.ClassPathResource"%>
<%@page import="com.tianee.oa.core.org.service.TeePersonService"%>
<%@ page import="com.tianee.webframe.util.auth.TeeAuthUtil"%>
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


	String GAO_SU_BO_VERSION = TeeStringUtil.getString(TeeSysProps.getString("GAO_SU_BO_VERSION"));
	
	String GSB_OA_CLOUD_LOGIN_URL = TeeSysProps.getString("GSB_OA_CLOUD_LOGIN_URL");//高速波云平台登录地址
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title><%=ieTitle %></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="/header/userheader.jsp" %>
<!-- jQuery库 -->
<script src="<%=contextPath %>/common/easyui/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css"/>



<script type="text/javascript" >
window.UEDITOR_HOME_URL = "<%=contextPath%>/common/ueditor/";
$.browser = {};
$.browser.mozilla = /firefox/.test(navigator.userAgent.toLowerCase());
$.browser.webkit = /webkit/.test(navigator.userAgent.toLowerCase());
$.browser.opera = /opera/.test(navigator.userAgent.toLowerCase());
$.browser.msie = /msie/.test(navigator.userAgent.toLowerCase());
</script>

<!-- Bootstrap通用UI组件 -->
<script src="<%=contextPath %>/common/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="bootstrap-3.3.4-dist/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/common/bootstrap/css/bootstrap-ie7.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/common/bootstrap/css/bootstrap.css"/>
<script type="text/javascript" src="<%=contextPath%>/common/jquery/portlet/jquery-ui-1.9.2.min.js"></script>



<!-- JBOX通用UI组件 -->
<script type="text/javascript" src="<%=contextPath%>/common/jbox-v2.3/jBox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jbox-v2.3/jBox/i18n/jquery.jBox-zh-CN.js"></script>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/jbox-v2.3/jBox/Skins/Blue/jbox.css" />
<script>

</script>
<!-- 其他工具库类 -->
<script src="<%=contextPath %>/common/js/tools.js"></script>
<script src="<%=contextPath %>/common/js/sys.js"></script>
<script src="<%=contextPath %>/common/js/sysUtil.js"></script>
<script src="<%=contextPath %>/common/js/src/orgselect.js"></script>


<script type="text/javascript" src="<%=contextPath%>/common/js/easyuiTools.js"></script>

<!-- jQuery Tooltip -->
<script type="text/javascript" src="<%=contextPath%>/common/tooltip/jquery.tooltip.min.js"></script>
<link rel="stylesheet" href="<%=contextPath %>/common/tooltip/jquery.tooltip.css" type="text/css"/>

<!-- 图片预览器 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/common/js/picexplore/jquery.mousewheel.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/common/js/picexplore/picexplore.js"></script>
<link rel="stylesheet" href="<%=contextPath %>/common/js/picexplore/picexplore.css" type="text/css"/>
<script type="text/javascript" src="<%=contextPath%>/common/js/lazyloader.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/frame/2/js/sms.js?v=1"></script>
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/system/frame/2/js/sms.css">
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
var GAO_SU_BO_VERSION = "<%=GAO_SU_BO_VERSION%>";//是否是高速波云平台版本 1-是 其他-不是
var GSB_OA_CLOUD_LOGIN_URL = "<%=GSB_OA_CLOUD_LOGIN_URL%>";
var xparent;
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
<style>
body {
scrollbar-arrow-color: #4e9be9;  /*图6,三角箭头的颜色*/
scrollbar-face-color: #c0c0c0;  /*图5,立体滚动条的颜色*/
scrollbar-3dlight-color: #dfdcdc;  /*图1,立体滚动条亮边的颜色*/
scrollbar-highlight-color: #dfdcdc;  /*图2,滚动条空白部分的颜色*/
scrollbar-shadow-color: #999;  /*图3,立体滚动条阴影的颜色*/
scrollbar-darkshadow-color: #666;  /*图4,立体滚动条强阴影的颜色*/
scrollbar-track-color: #dfdcdc;  /*图7,立体滚动条背景颜色*/

scrollbar-base-color:#f8f8f8;  /*滚动条的基本颜色*/
Cursor:url(mouse.cur);  /*自定义个性鼠标*/
}

</style>


<link id="skin" rel="stylesheet" type="text/css" href="style/index-purple.css">
<link id="skin" rel="stylesheet" type="text/css" href="style/index-purple<%=GAO_SU_BO_VERSION %>.css">
<link rel="stylesheet" type="text/css" href="style/general.css">

<script type="text/javascript" src="js/index.js"></script>
<script type="text/javascript" src="js/jquery.datePicker-min.js"></script>
<link type="text/css" href="css/datepicker.css" rel="stylesheet" />
<script type="text/javascript" src="js/calendar.js"></script>

<style>
body {
scrollbar-arrow-color: #777777;  /*图6,三角箭头的颜色*/
scrollbar-face-color: #fff;  /*图5,立体滚动条的颜色*/
scrollbar-3dlight-color: red;  /*图1,立体滚动条亮边的颜色*/
scrollbar-highlight-color: #e9e9e9;  /*图2,滚动条空白部分的颜色*/
scrollbar-shadow-color: #c4c4c4;  /*图3,立体滚动条阴影的颜色*/
scrollbar-darkshadow-color: #666;  /*图4,立体滚动条强阴影的颜色*/
scrollbar-track-color: #dfdcdc;  /*图7,立体滚动条背景颜色*/
scrollbar-base-color:#fff;  /*滚动条的基本颜色*/
}
</style>

<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var loginOutText = "<%=loginOutText%>";
var autoPopSms = "<%=person.getAutoPopSms()%>";



function addNewTabs(title,src){
	openFullWindow(src);
}

function doInit(){
	remindCheck();
}

function toDesktop(){
	$("#contentFrame")[0].src = "<%=contextPath%>/system/frame/default/mainForSimple.jsp";
}

/**
*
*注销
*/
function doLogout(){
	var msg = loginOutText + "\n确定要注销吗?";
	if(confirm(msg)){
		var url = contextPath + "/systemAction/doLoginout.action";
		var para = {};
		var jsonObj = tools.requestJsonRs(url,para);
		if(jsonObj){
			var json = jsonObj.rtData;
		}else{
			alert(jsonObj.rtMsg);
		}
		window.location.href = "/"+contextPath;
	}
}

function toSetting(){
	$("#contentFrame")[0].src = contextPath+"/system/core/person/setdescktop";
}

function remindCheck(){
	tools.requestJsonRs(contextPath+"/sms/remindCheck.action",{},true,function(json){
		if(json.rtState){
			if(json.rtData.smsFlag!=0){//弹出短消息
				msgFrame.playSound(2);
				if(autoPopSms=="0"){
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
			}
		}
		setTimeout("remindCheck()",1000*10);//10秒
	});
}

</script>
</head>
<%
String TOP_BANNER_TEXT = TeeSysProps.getString("TOP_BANNER_TEXT");
String BOTTOM_STATUS_TEXT = TeeSysProps.getString("BOTTOM_STATUS_TEXT");
String TOP_ATTACHMENT_ID = TeeSysProps.getString("TOP_ATTACHMENT_ID");
%>
<body onload="doInit()">
<!-- 头部 -->
<div id="header">
	<div class="logo" title="">
	<%
	if(!"".equals(TOP_BANNER_TEXT)){//有标题，则优先显示标题
		%>
		<table style="height:50px;border-collapse:collapse;width:100%">
			<tr>
				<td style="font-size:14px;color:white;font-family:微软雅黑;font-weiht:bold;line-height:16px">
					<%=TOP_BANNER_TEXT %>
				</td>
			</tr>
		</table>
		<%
	}else if(!"".equals(TOP_ATTACHMENT_ID)){//有图片，则显示图片信息
		%>
		<img src="/attachmentController/downFile.action?id=<%=TOP_ATTACHMENT_ID %>"  style="height:40px;margin-top:5px" />
		<%
	}else{
		%>
		<img src="logo.png" style="height:40px;margin-top:5px"/>
		<%
	}
	%>
	</div>
    <div class="header_right">
    	<!-- 搜索 -->
    	<div class="search"><span>搜素</span>
        	<div class="search_content">
				<input name="search" type="text" class="text" value="Serach" />
				<input name="" type="button" class="btn" value="搜索">
            </div>
        </div>
        <div class="sms" >
        	<span title="事务消息">事务消息</span>
        	<div class="sign_in_tid" style="display:none">新的消息！</div>
        </div>
        <div class="home" onclick="toDesktop()"><span title="桌面">回到桌面</span></div>
        <!-- 操作按钮 -->
        <div class="operating_button">
        	<ul>
            	<li class="btn1" title="换肤"><span>换肤</span>
                	<div class="sign_in">
                    	<h2>选着皮肤</h2>
                        <ul>
                        		<li class="purple on"></li>
                                <li class="blue"></li>
                        </ul>
                    </div>
                </li>
                <li class="btn2" title="登记"><span>登记</span>
                	<div class="skin_peeler">
                    	<div class="timer" id="time">
                        	<div class="time_bj"><strong>HOURS</strong><div><img src="images/date/0.png" width="18" height="33"><img src="images/date/1.png" width="20" height="35"></div></div>
                            <div class="time_bj"><strong>MINUTES</strong><div><img src="images/date/0.png" width="18" height="33"><img src="images/date/3.png" width="20" height="35"></div></div>
                            <div class="time_bj"><strong>SECONDS</strong><div><img src="images/date/0.png" width="18" height="33"><img src="images/date/0.png" width="20" height="35"></div></div>
                        </div>
                        <h3><input name="" id="signInButton" type="button" value="登 记"  onclick="signInFunc();"/></h3>
                    </div>
                </li>
                <li class="btn3" title="设置" onclick="toSetting()"><span>设置</span></li>
                <li class="btn4" title="退出" onclick="doLogout()"><span>退出</span></li>
            </ul>
        </div>
        <!-- 用户中心 -->
        <div class="user">
        	<div class="user_avatar"><img src="<%=contextPath %>/attachmentController/downFile.action?id=<%=avatar %>" width="40" height="40" onerror="this.src = '<%=request.getContextPath() %>/common/images/default_avatar.gif'"></div>
            <div class="user_information">
            	<h3 class="on">
            	<%
            		if(userName.length()>4){
            			out.print("<span style='font-size:12px'>"+userName+"</span>");
            		}else{
            			out.print(userName);
            		}
            	%>
            	</h3>
                <div class="user_content">
                	<ul>
                    	<li>部门： <%=currUserDeptName%></li>
                        <li>角色： <%=currUserRoleName%></li>
                    </ul>
                    <ol style="display:none">
                    	<li>未读消息：20条</li>
                        <li>我的下属：5人</li>
                    </ol>
                </div>
            </div>
        </div>
        <div class="message"><span>组织</span>
        	<div id="message_box" style="overflow:hidden;">
                <iframe src="<%=contextPath %>/system/frame/3/right.jsp" width="100%" height="100%" frameborder="0" id="iframe1"></iframe>
            </div>
        </div>
    </div>
    <div class="time" id="timeDiv"></div>
</div>
<!-- 主体 -->
<div id="main">
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="background:#fff">
	<tr>
	<!-- 左侧栏 开始 -->
    <td id="sidebar">
    	<div class="sidebar_nav">
            <ul class="sidebar_nav_ul" id="sidebar_nav_ul">
            </ul>
        </div>
        <!-- 侧栏导航二三级菜单 开始 -->
		<div class="sidebar_box" id="sidebar_box">
            
        </div>
        <!-- 侧栏导航二三级菜单 结束 -->
        <!-- 侧栏导航展开收起 -->
        <div class="sidebar_button">
        	<div class="sidebar_spread"><span title="展开/收起">展开/收起</span></div>
            <div class="sidebar_upward"><span title="向上">向上</span></div>
            <div class="sidebar_down"><span title="向下">向下</span></div>
        </div>
    </td>
    <!-- 左侧栏 结束 -->
    <!-- 主体中心区域 -->
    <td valign="top">
	<div id="main_conetnt" style="overflow:hidden">
		<iframe id="contentFrame" style="width:100%;height:100%;" frameborder="no" src="<%=contextPath%>/system/frame/default/mainForSimple.jsp"></iframe>
    </div>
    </td>
    </tr>
</table>
</div>

<!-- 消息弹出层样式 -->
<div class="remind" id="smsbox" style="display:none">
	<div class="remind_hd">
    	<div class="have_read" title="全部已阅读" onclick="smsViewAlls()">全部已阅读</div>
        <div class="delete" title="关闭" onclick="$('#smsbox').hide()">关闭</div>
        <div class="more" title="更多" onclick="smsDetails()">更多</div>
        <div class="title">消息提醒</div>
    </div>
    <div class="remind_bd">
    	<ul id="smsBoxContent">
            <li class="smsbox_loadmore" id="loadMore">
				点击加载更多
			</li>
        </ul>
    </div>
</div>

<div style="right: 5px; bottom: 30px; width: 200px; max-height:500px;overflow-y:auto;overflow-x:hidden; position: absolute;" id="smsInfo">
</div>

<!-- 搜索框 --> 
<style>
.quick_search_div{
	position:absolute; 
	top:50px; 
	right:20px;
	width:700px; 
	bottom:10px;
	padding:10px 0 20px 0; 
	border-radius:10px 10px 10px 10px; 
	box-shadow: 0 0 15px #BDBDBD; 
	background:#f9f9f9;
	border:1px #dddddd solid; 
	border-left:none; 
	overflow-y:auto; 
	z-index:11111111111;
	display:none;
	overflow:hidden;
}
.quick_search_div .closeBtn{
	text-align:right;
	margin-right:10px;
	margin-bottom:10px;
}
.quick_search_div .closeBtn{
	text-align:right;
	margin-right:10px;
	margin-bottom:10px;
}
.quick_search_div .closeBtn img{
	cursor:pointer;
}
</style>

<div class="quick_search_div" style="overflow:hidden;">
	<div class="closeBtn"><img src="<%=request.getContextPath() %>/system/frame/6/images/close.png" onclick="$('.quick_search_div').hide()"/></div>
	<iframe src="<%=request.getContextPath() %>/system/core/base/quicksearch/search_result_1.jsp" frameborder="0" style="height:98%;width:99%"></iframe>
</div>

<script>
//弹出短消息框
function smsAlert(){
    var url = contextPath+"/sms/popup.action";
    tools.requestJsonRs(url,{},true);
    if(!window.loader){
    	window.loader = new lazyLoader({
			url:contextPath+'/sms/getSmsBoxDatas.action',
			placeHolder:'loadMore',
			contentHolder:'smsBoxContent',
			rowRender:function(rowData){
				var render = [];
				render.push("<li style='cursor:pointer;' onclick=\"openDetail('"+rowData.remindUrl+"','"+rowData.smsSid+"',this)\">");
				render.push("<h3><span class=\"time\"><span>"+(rowData.moduleNoDesc?"["+rowData.moduleNoDesc+"]":"")+"</span>"+rowData.remindTimeDesc+"</span><strong>来自："+(rowData.fromUser==""?"系统":rowData.fromUser)+"的消息</strong></h3>");
				render.push("<p>"+rowData.content+"</p>");
				render.push("</li>");
				
				return render.join("");
			},
			onLoadSuccess:function(){
				$("#smsBoxContent").append($("#loadMore").show());
				$("#smsbox").show();
			},
			onNoData:function(){
				$("#loadMore").hide();
			}
		});
    }else{
    	loader.reload();
    }
    //$("#smsbox").css({top:($(window).height()-$("#smsbox").height())/2,left:($(window).width()-$("#smsbox").width())/2}).show();
    try{
    	var sound = $("#__sound1");
		if(sound.length==0){
			sound = $("<embed id='__sound1' src='"+contextPath+"/system/frame/inc/alert.mp3' autostart=true ></embed>").hide();
			$("body").append(sound);
		}else{
			document.getElementById("__sound1").play();
		}
    }catch(e){
    	
    }
}
</script>
<div class="modal_backdrop_fade_in" style="text-align: center">

</div>

 <div class="top_floor_div" style="text-align: center;display: none;">
 <div style="background-color: #fff"  class="data_div">
 	<form action="" id="cloudForm" name="cloudForm">
 	<div style="margin-top:30px;">
 		<div class="gsb_cloud_logo"></div>
 	</div>
 	
 	<div class="cloud_logo">
 		<span>账号：</span>
 		<input title="云平台账号" class="BigInput " type="text"  id="cloudUserId" name="cloudUserId"  maxlength="100" value="" style="height:35px;line-height: 35px;width:170px;"/>	
 	</div>
 	<div class="cloud_logo">
	 	<span>密码：</span>
	 		<input title="云平台账密码" class="BigInput" type="password" id="cloudPwd" name="cloudPwd" maxlength="100" value="" style="height:35px;line-height: 35px;width:170px;"/>	
	 	</div>
 	<div class="cloud_logo_bottom">
 		  <div onclick="saveAndLogin();" class="cloud_logo_button" style="">登录</div>
 	</div>
 	</form>
 </div>
</div> 
</body>
</html>

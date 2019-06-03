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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
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
	
	String loginOutText = (String) request.getSession().getAttribute("LOGIN_OUT_TEXT") == null ? "" : (String) request.getSession().getAttribute("LOGIN_OUT_TEXT");//退出提示语
	String ieTitle = (String) request.getSession().getAttribute("IE_TITLE") == null ? "" : (String) request.getSession().getAttribute("IE_TITLE");//主界面IEtitle


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


<!-- 主题7库、样式 -->
<link rel="stylesheet" type="text/css" href="css/css.css">
<link rel="stylesheet" type="text/css" href="css/general.css">
<script type="text/javascript" src="js/tabs.js"></script>
<script type="text/javascript" src="js/jquery.SuperSlide.js"></script>
<!-- <link rel="stylesheet" href="bootstrap-3.3.4-dist/css/bootstrap.min.css">
<script src="bootstrap-3.3.4-dist/js/bootstrap.min.js"></script> -->
<script type="text/javascript" src="<%=contextPath%>/system/frame/7/js/index7.js"></script>

<script type="text/javascript">

/** 常量定义 **/
var TDJSCONST = {
  YES: 1,
  NO: 0
};
/** 变量定义 **/
var contextPath = "<%=contextPath %>";
var loginOutText = "<%=loginOutText%>";
var imgPath = "<%=imgPath %>";
var cssPath = "<%=cssPath%>";
var stylePath = "<%=stylePath%>";
var autoPopSms = "<%=person.getAutoPopSms()%>";

var systemImagePath = "<%=systemImagePath%>";
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
</head>
<%
String TOP_BANNER_TEXT = TeeSysProps.getString("TOP_BANNER_TEXT");
String BOTTOM_STATUS_TEXT = TeeSysProps.getString("BOTTOM_STATUS_TEXT");
String TOP_ATTACHMENT_ID = TeeSysProps.getString("TOP_ATTACHMENT_ID");
%>
<body >
<!-- 系统头部 -->
<div id="header">
    <!-- logo -->
	<%
	
	if(!"".equals(TOP_BANNER_TEXT)){//有标题，则优先显示标题
		%>
		<p style="float:left;margin-top:15px;font-size:14px;color:white;padding-left:15px">
			<%=TOP_BANNER_TEXT %>
		</p>
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
    <div class="header_right" >
    	<!-- 搜索 -->
    	<div class="search" title="搜索"><span></span>
        	<div class="search_content">
				<input name="search"  type="text" class="text" value="Serach" />
            </div>
        </div>
        <div class="sms" >
        	<span title="事务消息">事务消息</span>
        	<div class="sign_in_tid" style="display:none">新的消息！</div>
        </div>
        <!-- 操作按钮 -->
        <div class="operating_button">
        	<ul>
                <li class="btn3" title="控制面板" onclick="addTabFunc('控制面板','/system/core/person/setdescktop/');" ><span>设置</span></li>
                <li class="btn4" title="退出" onclick="doLogout();"><span>退出</span></li>
            </ul>
        </div>
        <!-- 用户中心 -->
        <div class="user">
        	<div class="user_avatar"><img src="<%=contextPath %>/attachmentController/downFile.action?id=<%=avatar %>" onerror="this.src = '<%=request.getContextPath() %>/common/images/default_avatar.gif'" width="36" height="36"></div>
            <div class="user_information">
            	<h3 class="on">
            		<%
            		if(userName.length()>4){
            			out.print("<span style='font-size:'>"+userName+"</span>");
            		}else{
            			out.print(userName);
            		}
            	%>
            	</h3>
                <div class="user_content">
                	<ul style="margin:10px;padding:0px">
                    	<li>部门： <%=currUserDeptName%></li>
                        <li>角色：  <%=currUserRoleName%></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="message"><span>组织</span>
        	<div id="message_box" style="overflow:hidden">
               <iframe src="<%=contextPath %>/system/frame/3/right.jsp" width="100%" height="100%" frameborder="0" id="iframe1"></iframe>
            </div>
        </div>
    </div>
   <!--  <div class="logo"><img src="images/logo.png" width="400" height="50"/></div> -->
</div>
<div class="clear"></div>
<!-- 系统主体 -->
<div id="content">
    <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
       <!-- 侧栏导航 开始-->
        <td class="sidebar" valign="top">
        	<div class="sidebar-main">
	            <!-- 导航菜单 -->
	            <div class="sidebar_nav">
	            	<ul class="sidebar_nav_ul" id="sidebar_nav_ul">
	            	</ul>
	            </div>
	             <!-- 二级菜单 -->
	            <div class="sidebar_box" id="twoMenu">
	            	
	            </div>
	            <!-- 上下 -->
	            <div class="sidebar_top">上</div>
	            <div class="sidebar_bottom">下</div>
	            <div class="sidebar_toggle"></div>	
            </div>
        </td>
        <!-- 桌面右侧 -->
        <td class="main_right" valign="top">
        	<!-- 菜单切换 -->
       		<div class="header_nav">
                    <div id="tabs" style=""></div>
            </div>
            <!-- 分屏 -->
            <div class="zm_box">
            	<div id="tabs-content" style="height:100%;" fit="true"></div>
            </div>
            <div class="main_bottom">
            	<div class="main_bottom_r">当前在线人数<span id="onlineUserCount"></span>人</div>
                <div class="main_bottom_c">
	                <div id="scrollDiv_keleyi_com" class="scrollDiv" style="overflow:hidden"> 
					<ul style="margin-left:190px;">
					<%
						String sp [] = BOTTOM_STATUS_TEXT.split("\n");
						for(int i=0;i<sp.length;i++){
							%>
							<li style="font-size:14px;"><%=sp[i] %></li>
							<%
						}
					%>
					</ul> 
					</div> 
					<script type="text/javascript"> 
					function AutoScroll(obj){ 
					$(obj).find("ul:first").animate({ 
					marginTop:"-25px",background:"none"
					},500,function(){ 
					$(this).css({marginTop:"0px",background:"none"}).find("li:first").appendTo(this); 
					});
					} 
					$(document).ready(function(){ 
					setInterval('AutoScroll("#scrollDiv_keleyi_com")',5000);
					}); 
					</script> 
                </div>
            </div>
        </td>
      </tr>
    </table>
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
.smsbox .content{
height:290px;
}
</style>

<div class="quick_search_div">
	<div class="closeBtn"><img src="<%=request.getContextPath() %>/system/frame/7/images/close.png" onclick="$('.quick_search_div').hide()"/></div>
	<iframe src="<%=request.getContextPath() %>/system/core/base/quicksearch/search_result_1.jsp" frameborder="0" style="height:98%;width:99%"></iframe>
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
</html>

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
	
	//第二套风格
	int STYLE_TYPE_INDEX_2 = TeeStringUtil.getInteger( request.getSession().getAttribute("STYLE_TYPE_INDEX_2"), 1);
	String cssPathSecond = contextPath + "/system/frame/2/styles/style" + STYLE_TYPE_INDEX_2 + "/css";
	String imgPathSecond = contextPath + "/system/frame/2/styles/style" + STYLE_TYPE_INDEX_2 + "/img";
	
	
	String loginOutText = (String) request.getSession().getAttribute("LOGIN_OUT_TEXT") == null ? "" : (String) request.getSession().getAttribute("LOGIN_OUT_TEXT");//退出提示语
	String ieTitle = (String) request.getSession().getAttribute("IE_TITLE") == null ? "" : (String) request.getSession().getAttribute("IE_TITLE");//主界面IEtitle
	String secUserMem = (String) request.getSession().getAttribute("SEC_USER_MEM") == null ? "" : (String) request.getSession().getAttribute("SEC_USER_MEM");//是否记忆用户名


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
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/common/bootstrap/css/bootstrap-ie7.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/common/bootstrap/css/bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/system/frame/2/js/sms.css">


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

<!-- 图片预览器 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/common/js/picexplore/jquery.mousewheel.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/common/js/picexplore/picexplore.js"></script>
<link rel="stylesheet" href="<%=contextPath %>/common/js/picexplore/picexplore.css" type="text/css"/>
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
</script>


<link rel="stylesheet" type="text/css" href="style/css/css.css">
<link rel="stylesheet" type="text/css" href="style/css/general.css">
<script type="text/javascript" src="js/jquery.SuperSlide.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/frame/3/js/index2.js"></script>
<script type="text/javascript" src="js/tabs.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jquery/portlet/jquery-ui-1.9.2.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jquery/portlet/jquery.portlet.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/frame/2/js/sms.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/common/js/picexplore/jquery.mousewheel.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/common/js/picexplore/picexplore.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jbox-v2.3/jBox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jbox-v2.3/jBox/i18n/jquery.jBox-zh-CN.js"></script>
<script>
/* 
$(document).ready(function(){
	$.addTab("tabs","tabs-content",{title:"桌面",url:"个人桌面.htm",active:true});
	$.addTab("tabs","tabs-content",{title:"搜狐",url:"http://www.sohu.com",active:false,closable:false});
	$.addTab("tabs","tabs-content",{title:"新浪",url:"http://www.sina.com",active:false,closable:true});
	 $.addTab("tabs","tabs-content",{title:"新浪1",url:"http://www.sina.com",active:false,closable:true});
	$.addTab("tabs","tabs-content",{title:"新浪2",url:"http://www.sina.com",active:false,closable:true});
	$.addTab("tabs","tabs-content",{title:"新浪3",url:"http://www.sina.com",active:false,closable:true});
	$.addTab("tabs","tabs-content",{title:"新浪4",url:"http://www.sina.com",active:false,closable:true}); 


});
 */
 var autoPopSms = "<%=person.getAutoPopSms()%>";
 function doInit(){
	 if(getCookie("className")){
		$("#sidebar").attr("class",getCookie("className"));
	 }
	 remindCheck();
 }
 
 
function remindCheck(){
	tools.requestJsonRs(contextPath+"/sms/remindCheck.action",{},true,function(json){
		if(json.rtState){
			if(json.rtData.smsFlag!=0){//弹出短消息
				if(autoPopSms=="0"){
					$('.sign_in_tid').show();
				}else{
					smsAlert();
				}
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
<%
	String todayLunarStr = TeeLunarCalendarUtils.today();
	String TOP_BANNER_FONT = (String) request.getSession().getAttribute("TOP_BANNER_FONT") == null ? "" : (String) request.getSession().getAttribute("TOP_BANNER_FONT");//顶部字样样式
	String TOP_BANNER_TEXT = (String) request.getSession().getAttribute("TOP_BANNER_TEXT") == null ? "" : (String) request.getSession().getAttribute("TOP_BANNER_TEXT");//顶部文字
	String BOTTOM_STATUS_TEXT = (String) request.getSession().getAttribute("BOTTOM_STATUS_TEXT") == null ? "" : (String) request.getSession().getAttribute("BOTTOM_STATUS_TEXT");//底部文字
	String TOP_ATTACHMENT_ID = TeeStringUtil.getString( request.getSession().getAttribute("TOP_ATTACHMENT_ID"));//顶部图片Id TeeAttachment.sid
%>
<body onload="doInit();">
<!--------------- 系统头部 -------------->
<div id="header">
		<!---------- logo ---------->
		<div class="logo">
			<%
				if(!"".equals(TeeSysProps.getString("THEME_LOGO_TEXT_3"))){
					out.print(TeeSysProps.getString("THEME_LOGO_TEXT_3"));
				}else{
					out.print("<img src=\"logo.png\" />");
				}
			%>
		</div>
        <div class="header_nav">
        		<div id="tabs" style=""></div>
        </div>
        <!---------- 头部右侧 ---------->
        <div class="header_right">
        		<div class="search" title="搜索">
                		<div class="container" >
                        	<input  onkeyup="enterKeywords()" name="search" type="text" placeholder="搜索工作流/人员" id="queryInfo"/>
                            <p >搜索</p>
                            <iframe frameborder=0 src="<%=contextPath %>/system/core/base/quicksearch/search_result.jsp" id="searchDataDiv" style="position:absolute;display:none;border:1px solid gray;height:300px;width:175px;right:0px;top:45px;z-index:100"></iframe>
                        </div>
                </div>
                <div class="sms">
                		<p class="top_fot" title="消息事务">消息事务</p>
                        <div class="sign_in_tid" style="display:none">新的消息！</div>
                </div>
                <div class="sign_in">
                		<p class="top_fot" title="登记">登记</p>
                        <div class="container">
                        		<h2><span id="signInTime" ></span></h2>
                                <h3><input id="signInButton"  type="button" value="登记" onclick="signInFunc();" /></h3>
                        </div>
                </div>
                <div class="skin_peeler">
                		<p class="top_fot" title="更换界面主题">换肤</p>
                        <div class="container">
                        		<h2>更换界面主题</h2>
                                <ul>
                                		<li class="blue on"></li>
                                        <li class="orange"></li>
                                        <li class="green"></li>
                                </ul>
                        </div>
                </div>
                <div class="set_up">
                		<p class="top_fot"  title="控制面板" onclick="addTabFunc('控制面板','/system/core/person/setdescktop/');" >设置</p>
                </div>
                <div class="sign_out">
                		<p class="top_fot"  title="退出" onclick="doLogout();">退出</p>
                </div>
                <div class="structure">
                		<p class="top_fot"  title="组织架构">组织架构</p>
                        <div class="container">
                        	<iframe src="<%=contextPath %>/system/frame/3/right.jsp" width="240px" height="100%" frameborder="1" id="iframe1"></iframe>
                        </div>
                </div>
        </div>
        </div>
</div>
<!--------------- 系统主体 -------------->
<div id="content">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
           <!---------- 侧栏导航 开始---------->
            <td id="sidebar" class="sidebar sidebar_w">
            <div class="start_up">展开收起</div>
                <div class="downward">向下</div>
                <!----- 导航菜单 ----->
                <div class="sidebar_nav">
                		<div class="hd">
                			<ul id="sidebar_ul" class="sidebar_ul">
                            </ul>
                        </div>
                        <div id="bd" class="bd">
                        		
                        		
                        </div>
                        
                </div>
                <div class="upward">向上</div>
            </td>
            <!--------- 桌面右侧 ---------->
            <td class="main_right" valign="top">
                     <!----- 分屏 ----->
                     <div class="zm_box">
                     <div id="tabs-content" style="height:100%;" fit="true"></div>
                    
                     </div>
            </td>
          </tr>
        </table>

</div>
<!--------------- 尾部 -------------->
<div id="footer" style="overflow:hidden">
	<%
		if(!TeeAuthUtil.getInfoString().equals("")){
			%>
			<%=TeeAuthUtil.getInfoString() %>
			<%
		}else{
			%>
			<div id="scrollDiv_keleyi_com" class="scrollDiv" style="overflow:hidden"> 
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
			<%
		}
	%>
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

<div style="right: 5px; bottom: 30px; width: 200px; max-height:500px;overflow-y:auto;overflow-x:hidden; position: absolute;" id="smsInfo">
</div>

<!-- 搜索框 --> 
<style>
.quick_search_div{
	position:absolute; 
	top:70px; 
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

<div class="quick_search_div">
	<div class="closeBtn"><img src="<%=request.getContextPath() %>/system/frame/6/images/close.png" onclick="$('.quick_search_div').hide()"/></div>
	<iframe src="<%=request.getContextPath() %>/system/core/base/quicksearch/search_result_1.jsp" frameborder="0" style="height:98%;width:99%"></iframe>
</div>

</body>
</html>

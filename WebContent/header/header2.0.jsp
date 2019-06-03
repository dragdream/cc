<%@page import="com.tianee.webframe.util.servlet.TeeCookieUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ page import="java.util.*" %>
<%@ page import="com.tianee.oa.oaconst.TeeConst" %>
<%@ page import="com.tianee.oa.core.org.bean.TeePerson" %>
<%@ page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page import="com.tianee.webframe.util.date.TeeDateUtil"%>
<%@ page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@ page import="com.tianee.oa.core.org.service.TeePersonService"%>
<%@ page import="com.tianee.oa.oaconst.TeeModelIdConst" %>
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
String secUserMem = TeeSysProps.getString("SEC_USER_MEM");

Cookie cookie = TeeCookieUtils.getCookie(request, "skin_new");
String skinNew = "1";
if(cookie!=null){
	skinNew = cookie.getValue();
}
%>

<!-- zt_webframe框架引入 jquery -->
<script src="<%=contextPath %>/common/jquery-easyui-1.6.11/jquery.min.js"></script>

<!-- zt_webframe框架引入 核心库 -->
<script src="<%=contextPath %>/common/zt_webframe/js/package.js"></script>
<script src="<%=contextPath %>/common/js/sys2.0.js?v=2"></script>
<script src="<%=contextPath %>/common/js/sysUtil.js"></script>
<script src="<%=contextPath %>/common/js/src/orgselect.js"></script>
<!--

//-->
<!-- zt_webframe框架引入 css样式 -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/common/zt_webframe/css/init<%=skinNew %>.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/common/zt_webframe/css/package<%=skinNew %>.css">

<script src="<%=contextPath %>/common/js/tools2.0.js?v=1"></script>
<script src="<%=contextPath %>/common/js/TeeMenu.js"></script>

<script type="text/javascript">
/** 变量定义 **/
var contextPath = "<%=contextPath %>";
var systemImagePath = contextPath+"/common/images";
var uploadFlashUrl = "<%=contextPath %>/common/swfupload/swfupload.swf";
var commonUploadUrl = "<%=contextPath %>/attachmentController/commonUpload.action;jsessionid=<%=session.getId()%>";
var xparent;
var stylePath = "<%=stylePath%>";
if(window.dialogArguments){
	xparent = window.dialogArguments;
}else if(window.opener){
	xparent = opener;
}else{
	xparent = window;
}
window.UEDITOR_HOME_URL = "<%=contextPath%>/common/ueditor/";
$.browser = {};
$.browser.mozilla = /firefox/.test(navigator.userAgent.toLowerCase());
$.browser.webkit = /webkit/.test(navigator.userAgent.toLowerCase());
$.browser.opera = /opera/.test(navigator.userAgent.toLowerCase());
$.browser.msie = /msie/.test(navigator.userAgent.toLowerCase());
</script>

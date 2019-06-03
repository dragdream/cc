<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.tianee.oa.oaconst.TeeConst" %>
<%@ page import="com.tianee.oa.core.org.bean.TeePerson" %>
<%@ page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page import="com.tianee.webframe.util.date.TeeDateUtil"%>
<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@page import="org.springframework.core.io.ClassPathResource"%>
 
<%
	String contextPath = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ contextPath + "/";
	
	String systemImagePath = contextPath+"/common/images";
	String mobilePath = contextPath + "/system/mobile";
	
	String ten_lang_msg_1 = "暂无更多信息";
    String ten_lang_msg_2 = "加载数据中...";
    String ten_lang_msg_3 = "页面加载错误";
    String ten_lang_msg_4 = "上拉刷新...";
    String ten_lang_msg_5 = "下拉刷新...";
    String ten_lang_msg_6 = "释放立即刷新...";
    String ten_lang_msg_7 = "上拉加载更多...";
    String ten_lang_msg_8 = "下拉加载更多...";
    String ten_lang_msg_9 = "释放加载更多...";
    String ten_lang_msg_10 = "已全部加载完毕";
    String ten_lang_msg_11 = "读取附件中...";
    String userAgent = request.getHeader("user-agent");
%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8" />
<!-- <meta name="viewport" content="width=460, height=920, user-scalable=yes, initial-scale=2.5, maximum-scale=5.0, minimun-scale=1.0">
 -->
 
	<meta name="viewport" content="width=device-width, minimum-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black" />
	<meta name="format-detection" content="telephone=no" /> 
<title>登录</title>
<script type="text/javascript" src="<%=contextPath %>/system/mobile/js/zepto.min.js"></script> 
<script type="text/javascript" src="<%=contextPath %>/system/mobile/js/tools.js?v=2"></script> 
<script type="text/javascript" src="<%=contextPath %>/common/js/lazyloader.js"></script>
<script type="text/javascript" src="<%=contextPath %>/mobile/js/iscroll.js"></script>
<script src="<%=request.getContextPath() %>/system/mobile/js/api.js?v=2"></script>
<script type="text/javascript">
	var ten_lang = {};
		ten_lang.pda = {
            msg_1:'<%=ten_lang_msg_1%>',
            msg_2:'<%=ten_lang_msg_2%>',
            msg_3:'<%=ten_lang_msg_3%>',
            msg_4:'<%=ten_lang_msg_4%>',
            msg_5:'<%=ten_lang_msg_5%>',
            msg_6:'<%=ten_lang_msg_6%>',
            msg_7:'<%=ten_lang_msg_7%>',
            msg_8:'<%=ten_lang_msg_8%>',
            msg_9:'<%=ten_lang_msg_9%>',
            msg_10:'<%=ten_lang_msg_10%>',
            msg_11:'<%=ten_lang_msg_11%>'
         };
         
   var C_VER = "";
   var contextPath = "<%=contextPath %>";
   var mobilePath ="<%=mobilePath%>";
   var systemImagePath = "<%=systemImagePath%>";
   var userAgent = "<%=userAgent%>";
</script>
<script>
window.alert = function(name){
    var iframe = document.createElement("IFRAME");
    iframe.style.display="none";
    iframe.setAttribute("src", 'blank');
    document.documentElement.appendChild(iframe);
    window.frames[0].window.alert(name);
    iframe.parentNode.removeChild(iframe);
};
window.confirm = function (message) {
    var iframe = document.createElement("IFRAME");
    iframe.style.display = "none";
    iframe.setAttribute("src", 'blank');
    document.documentElement.appendChild(iframe);
    var alertFrame = window.frames[0];
    var result = alertFrame.window.confirm(message);
    iframe.parentNode.removeChild(iframe);
    return result;
};
</script>
</head>
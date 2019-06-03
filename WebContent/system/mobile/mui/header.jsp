<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.tianee.oa.oaconst.TeeConst" %>
<%@ page import="com.tianee.oa.core.org.bean.TeePerson" %>
<%@ page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page import="com.tianee.webframe.util.date.TeeDateUtil"%>
<%@ page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@ page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page import="com.alibaba.dingtalk.openapi.demo.auth.AuthHelper"%>
<%
	String contextPath = request.getContextPath();
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<script type="text/javascript" src="<%=request.getContextPath() %>/system/mobile/mui/js/mui.min.js?v=2"></script>
<link href="<%=request.getContextPath() %>/system/mobile/mui/css/mui.listpicker.css?v=1" rel="stylesheet" />
<link href="<%=request.getContextPath() %>/system/mobile/mui/css/mui.dtpicker.css?v=1" rel="stylesheet" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/system/mobile/mui/css/mui.min.css?v=1">
<link rel="stylesheet" href="<%=request.getContextPath() %>/system/mobile/mui/css/app.css?v=2">
<link rel="stylesheet" href="<%=request.getContextPath() %>/common/layer_mobile/need/layer.css">
<script src="<%=request.getContextPath() %>/system/mobile/mui/js/mui.listpicker.js?v=1"></script>
<script src="<%=request.getContextPath() %>/system/mobile/mui/js/mui.dtpicker.js?v=1"></script>
<script src="<%=request.getContextPath() %>/system/mobile/js/api.js?v=279"></script>
<script src="<%=request.getContextPath() %>/system/mobile/js/orgselect.js?v=12"></script>
<script src="<%=request.getContextPath() %>/system/mobile/js/util.js?v=9"></script>
<script src="<%=request.getContextPath() %>/common/layer_mobile/layer.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/jquery-min.js"></script>
<%
String userAgent = request.getHeader("user-agent");
String signature = null;

if(userAgent.contains("dingtalk-win")){//钉钉客户端
	String nonceStr = "abcdefg";
	long timeStamp = System.currentTimeMillis()/1000;
	String url = request.getRequestURL().toString();
	if(!"".equals(request.getQueryString()) && request.getQueryString()!=null){
		url+="?"+request.getQueryString();
	}
	String ticket = TeeSysProps.getString("DING_JSAPI_TICKET");
	signature = AuthHelper.sign(ticket, nonceStr, timeStamp, url);
	%>
	<script src="http://g.alicdn.com/dingding/dingtalk-pc-api/2.4.0/index.js"></script>
	<script>
	DingTalkPC.config({
		agentId: window.DING_APPID,
	    corpId: '<%=TeeSysProps.getString("DD_CORPID")%>',
	    timeStamp: <%=timeStamp%>,
	    nonceStr: '<%=nonceStr%>',
	    signature: '<%=signature%>',
	    jsApiList: ['device.notification.alert','device.notification.toast','biz.navigation.setTitle','biz.util.uploadImage','biz.util.openModal','biz.util.openSlidePanel','biz.util.downloadFile']
	});
	
	DingTalkPC.ready(function(obj){
		
	});
	
	DingTalkPC.error(function(error){
		console.log(error);
	});
	
	</script>
	<%
}else if(userAgent.contains("DingTalk")){//引入钉钉JS
	String nonceStr = "abcdefg";
	long timeStamp = System.currentTimeMillis()/1000;
	String url = request.getRequestURL().toString();
	if(!"".equals(request.getQueryString()) && request.getQueryString()!=null){
		url+="?"+request.getQueryString();
	}
	String ticket = TeeSysProps.getString("DING_JSAPI_TICKET");
	signature = AuthHelper.sign(ticket, nonceStr, timeStamp, url);
	%>
	<script src="http://g.alicdn.com/ilw/ding/0.5.1/scripts/dingtalk.js"></script>
	<script>
	dd.config({
	    appId: window.DING_APPID,
	    corpId: '<%=TeeSysProps.getString("DD_CORPID")%>',
	    timeStamp: <%=timeStamp%>,
	    nonceStr: '<%=nonceStr%>',
	    signature: '<%=signature%>',
	    jsApiList: ['device.notification.alert', 'device.notification.confirm','device.notification.toast','biz.util.scan','biz.navigation.setTitle','biz.util.open','biz.util.uploadImage','biz.util.uploadImageFromCamera','device.notification.showPreloader','device.notification.hidePreloader']
	});
	
	dd.ready(function(){
		document.addEventListener('backbutton', function(e) {
			dd.biz.navigation.close({
				onSuccess : function(result) {
					/*result结构
					{}
					*/
				},
				onFail : function(err) {}
			});
		});

		dd.biz.navigation.setLeft({
			show: false,//控制按钮显示， true 显示， false 隐藏， 默认true
			control: true,//是否控制点击事件，true 控制，false 不控制， 默认false
			showIcon: true,//是否显示icon，true 显示， false 不显示，默认true； 注：具体UI以客户端为准
			text: '',//控制显示文本，空字符串表示显示默认文本
			onSuccess : function(result) {
				dd.biz.navigation.close({
					onSuccess : function(result) {
						/*result结构
						{}
						*/
					},
					onFail : function(err) {}
				});
			},
			onFail : function(err) {}
		});
	});
	
	dd.error(function(error){
		//alert(error.message);
	});
	
	</script>
	<%
}else if(userAgent.contains("MicroMessenger")){
	String nonceStr = "abcdefg";
	long timeStamp = System.currentTimeMillis()/1000;
	String url = request.getRequestURI();
	url = (request.getProtocol().toLowerCase().contains("https")?"https":"http")+"://"+request.getServerName()+url;
	
	if(!"".equals(request.getQueryString()) && request.getQueryString()!=null){
		url+="?"+request.getQueryString();
	}
	
	String ticket = TeeSysProps.getString("WEIXIN_JSAPI_TICKET");
	signature = AuthHelper.sign(ticket, nonceStr, timeStamp, url);
	%>
	<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script>
	wx.config({
	    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: '<%=TeeSysProps.getString("WEIXIN_CORPID")%>', // 必填，企业号的唯一标识，此处填写企业号corpid
	    timestamp: <%=timeStamp%>, // 必填，生成签名的时间戳
	    nonceStr: '<%=nonceStr%>', // 必填，生成签名的随机串
	    signature: '<%=signature%>',// 必填，签名，见附录1
	    jsApiList: ['scanQRCode','chooseImage','uploadImage','hideOptionMenu','hideAllNonBaseMenuItem'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	
	wx.ready(function(){
	    wx.hideOptionMenu();
	    wx.hideAllNonBaseMenuItem();
	});
	
	wx.error(function(res){
// 		alert(JSON.stringify(res));
	});
	</script>
	<%
}
%>
<script>
var contextPath = "<%=request.getContextPath() %>";
var systemImagePath = "<%=contextPath+"/common/images"%>";
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
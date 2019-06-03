<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.tianee.oa.oaconst.TeeConst" %>
<%@ page import="com.tianee.oa.core.org.bean.TeePerson" %>
<%@ page import="com.tianee.webframe.servlet.TeeResPrivServlet"%>
<%@ page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@page import="com.tianee.webframe.util.global.*"%>
<%@page import="org.springframework.core.io.ClassPathResource"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.alibaba.dingtalk.openapi.demo.auth.AuthHelper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<title>原始表单</title>
<head>
<%
	String contextPath = request.getContextPath();

%>

<%
	int runId = TeeStringUtil.getInteger(request.getParameter("runId"),0);
	int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"),0);
	String runIdSearch = TeeStringUtil.getString(request.getParameter("runIdSearch"));
	String view = TeeStringUtil.getString(request.getParameter("view"));
	String runName = TeeStringUtil.getString(request.getParameter("runName"));
	String flowTypeId = TeeStringUtil.getString(request.getParameter("flowTypeId"));
	TeePerson loginPerson = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
%>
<meta content="width=device-width, initial-scale=1.0, user-scalable=1" name="viewport" />
<!-- jQuery库 -->
<script src="<%=contextPath %>/common/easyui/jquery.min.js"></script>
<script src="<%=contextPath %>/common/js/grayscale.js"></script>

<!-- 其他工具库类 -->
<script src="<%=contextPath%>/common/js/md5.js"></script>
<script src="<%=contextPath %>/common/js/tools.js"></script>
<script src="<%=request.getContextPath() %>/system/mobile/js/api.js?v=261"></script>
<link rel="stylesheet" href="<%=request.getContextPath() %>/common/layer_mobile/need/layer.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/images/workflow/index.css">
<script src="<%=request.getContextPath() %>/common/layer_mobile/layer.js"></script>
<%
String userAgent = request.getHeader("user-agent");
String signature = null;
if(userAgent.contains("DingTalk")){//引入钉钉JS
	String nonceStr = "abcdefg";
	long timeStamp = System.currentTimeMillis()/1000;
	String url = request.getRequestURL().toString();
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
	    jsApiList: ['device.notification.alert', 'device.notification.confirm','device.notification.toast','biz.util.scan','biz.navigation.setTitle','biz.util.open']
	});
	
	dd.ready(function(){
		
	});
	
	dd.error(function(error){
		//alert(error.message);
	});
	
	</script>
	<%
}else if(userAgent.contains("MicroMessenger")){
	String nonceStr = "abcdefg";
	long timeStamp = System.currentTimeMillis()/1000;
	String url = request.getRequestURL().toString();
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
	    jsApiList: ['scanQRCode'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	
	wx.ready(function(){
		
	});
	
	wx.error(function(res){
		
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

<style>
#这是浮动div组建 用的div样式 2013-10-07 zhp
.attach_name:link,.attach_name:hover,.attach_name:active,.attach_name:visited{color:#0D3A73;}
.attach_div{width:110px;border:#cccccc 1px solid;position:absolute;padding:0px;z-index:10001;background:#FFFFFF;}
.attach_div a{display:block !important;padding:0px 10px;height:25px;line-height:25px;text-decoration:none;color:#393939;}
.attach_div a:hover{background:#5d99da;color:#fff;text-decoration:none;}
p,span,a,div,td,tr,h1,h2,h3,h4,h5,h6,ul,li,ol
{
	font-size:14px;
	margin:0px;
	padding:0px;
}
table{
border-collapse:collapse;
font-size:12px;
}
#tbody_feedback p,span,a,div,td,tr,h1,h2,h3,h4,h5,h6,ul,li,ol,table{
font-size:12px;
}
#viewinfo p,span,a,div,td,tr,h1,h2,h3,h4,h5,h6,ul,li,ol,table{
font-size:12px;
}
#attach p,span,a,div,td,tr,h1,h2,h3,h4,h5,h6,ul,li,ol,table{
font-size:12px;
}
#feedback p,span,a,div,td,tr,h1,h2,h3,h4,h5,h6,ul,li,ol,table{
font-size:12px;
}
#graph p,span,a,div,td,tr,h1,h2,h3,h4,h5,h6,ul,li,ol,table{
font-size:12px;
}
</style>

<style type="text/css" media="all">
<%

    //水印
    int waterMark=TeeSysProps.getInt("WATER_MARK");
	if(waterMark==1){
		%>
		body{
			background-image:url('/systemAction/generateWaterMark.action');
		}
		<%
	}
%>
</style>

<script>
var userId = <%=loginPerson.getUuid()%>;
var deptId = <%=loginPerson.getDept().getUuid()%>;
var roleId = <%=loginPerson.getUserRole().getUuid()%>;
var contextPath = "<%=contextPath %>";
var runIdSearch = "<%=runIdSearch%>";
var systemImagePath = "<%=contextPath%>/common/images";
var runId = <%=runId%>;
var frpSid = <%=frpSid%>;
var view = "<%=view%>";
var runName = "<%=runName%>";
var stores = [];
var storeDatas = [];
var sealSignDatas = [];
var sealSignValideDatas = [];
var sealSignPos = [];

var ctrlSignArray = [];//控件签章data数组
var ctrlRandArray = [];//控件随机数数组
var ctrlRandPngArray = [];
var ctrlRandPngPos = [];
var ctrlRandArray4Pic = [];//控件随机数数组
var ctrlRandPngArray4Pic = [];

var mobileStores = [];
var mobileMD5Datas = [];
var mobileSealSignDatas = [];

var h5HwStores = [];//h5手写签批数组
var h5HwArray = [];//h5手写签批数组

var mobileHwStores = [];//移动签批数据数组
var mobileHwArray = [];//移动签批数据数组

var userAgent = "<%=userAgent%>";
function goBack(){
	if(userAgent.indexOf("DingTalk")!=-1 || userAgent.indexOf("MicroMessenger")!=-1){
		history.go(-1);
	}else{
		CloseWindow();
	}
	history.go(-1);
}


//地图预览
function  previewPosition(spanId){
		//获取坐标点
		var points=document.getElementById(spanId).nextSibling.nextSibling.value;
		var lng=points.split(",")[0];
		var lat=points.split(",")[1];
		
		var url=contextPath+"/system/mobile/phone/workflow/prcs/mapPreview.jsp?lng="+lng+"&lat="+lat;
		
		layer.open({
			  type: 1
			  ,content: "<iframe style='position:fixed;top:0px;left:0px;width:100%;height:100%' src='"+url+"'></iframe>"
			  ,anim: 'up'
			  ,style: 'position:fixed; left:0; top:0; width:100%; height:100%; border: none; -webkit-animation-duration: .5s; animation-duration: .5s;'
			});
	}
</script>
<script src="print.js?v=1026"></script>
</head>
<body onload="doInit()" style="margin:0px">
<center>
	<button class="btn btn-default" onclick="goBack()">返回</button>
	<div id="docinfo" style="display:none;margin-top:20px;">
		
	</div>
</center>
<div id="css"></div>
<div id="content">
	<div id="form" style="display:none" class="wf"></div>
</div>
<script>
	if(isNaN(Number(view))){
		view = tools.decode64(view.replace(/@/,"="));
	}
</script>
</body>
</html>
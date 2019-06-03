<%@ page import="java.util.*" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.tianee.oa.oaconst.TeeConst" %>
<%@ page import="com.tianee.oa.core.org.bean.TeePerson" %>
<%@ page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page import="com.tianee.webframe.util.date.TeeDateUtil"%>
<%@ page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@ page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page import="com.alibaba.dingtalk.openapi.demo.auth.AuthHelper"%>
<%@ page import="com.tianee.oa.oaconst.TeeModuleConst"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tianee.oa.core.org.bean.TeePerson" %>
<%
String contextPath = request.getContextPath();
String basePath = request.getScheme() + "://"
		+ request.getServerName() + ":" + request.getServerPort()
		+ contextPath + "/";
String mobilePath = contextPath + "/system/mobile";

TeePerson loginUser = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
%>
<!DOCTYPE HTML>
<html>
<head>
<title>工作办理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<link href="<%=mobilePath%>/phone/style/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/system/mobile/mui/css/app.css?v=2">
<%-- <script type="text/javascript" src="<%=contextPath %>/common/jquery/jquery-min.js"></script> --%>
<%@ include file="/header/kinggrid-mobile.jsp" %>
<script type="text/javascript" src="<%=mobilePath %>/phone/js/index.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/workflowUtils.js?v=6"></script>
<script type="text/javascript" src="<%=contextPath %>/system/mobile/js/tools.js?v=4"></script> 
<script type="text/javascript" src="<%=contextPath %>/common/js/md5.js"></script> 
<script type="text/javascript" src="<%=contextPath %>/common/js/grayscale.js"></script> 
<script type="text/javascript" src="form.js?version=2018072503"></script>
<link rel="stylesheet" href="<%=request.getContextPath() %>/common/layer_mobile/need/layer.css">
<script src="<%=request.getContextPath() %>/common/layer_mobile/layer.js"></script>
<!-- 区域联动js -->
<script type="text/javascript" src="/common/js/address_cascade.js"></script>
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
	var DING_APPID = "<%=TeeModuleConst.MODULE_SORT_DD_APP_ID.get("006")%>";
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
		alert(error.message);
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

<script src="<%=request.getContextPath() %>/system/mobile/js/api.js?v=265"></script>
<script>
var  contextPath = "<%=contextPath %>";
var mobilePath ="<%=mobilePath%>";
var userId = <%=loginUser.getUuid()%>;
var topFlag = 0;
var attachOtherPriv = 0;
var attachPriv=0;//flowProcess的附件权限控制值
var attachPrivLock=0;//flowType的附件区域控件
var userAgent = "<%=request.getHeader("user-agent")%>";
</script>
<%
	String runId = request.getParameter("runId");
	String flowId = request.getParameter("flowId");
	String frpSid = request.getParameter("frpSid");
	String flowTypeId = TeeStringUtil.getString(request.getParameter("flowTypeId"));
%>
<script type="text/javascript">
var runId = "<%=runId%>";
var runName;
var flowId = "<%=flowId%>";
var frpSid = "<%=frpSid%>";
var flowTypeId = "<%=flowTypeId%>";
var delegate = "0";
var archivesPriv="0";//归档权限
var feedback;
var formValidModel;
var signatures = [];//签章数组
var signJson = {};//需要验章的 字段
var signArray = new Array();//盖了章的 data
var noSignArray =  new Array();//没有盖章的 data

var mobileSignJson = {};//需要验章的 字段
var mobileSignArray = new Array();//盖了章的 data

var mobileHwStores = [];//移动签批数据数组
var mobileHwArray = [];//移动签批数据数组

var h5HwStores = [];//h5手写签批数组
var h5HwArray = [];//h5手写签批数组

var ctrlSignArray = [];//控件签章data数组
var ctrlRandArray = [];//控件随机数数组
var rand = new Date().getTime()*parseInt((Math.random()*10000+"").split("\\.")[0]);



//地理位置
function selectPosition(data,extra,positionType,radius){
	 if(positionType==2){//自动定位
		 var url=contextPath+"/system/mobile/phone/workflow/prcs/map.jsp?data="+data+"&extra="+extra+"&positionType="+positionType;
		 layer.open({
			  type: 1
			  ,content: "<iframe style='position:fixed;top:0px;left:0px;width:100%;height:100%' src='"+url+"'></iframe>"
			  ,anim: 'up'
			  ,style: 'position:fixed; left:0; top:0; width:100%; height:100%; border: none; -webkit-animation-duration: .5s; animation-duration: .5s;'
			}); 
	 }else if(positionType==1){//获取当前位置
		 var url=contextPath+"/system/mobile/phone/workflow/prcs/map1.jsp?data="+data+"&extra="+extra+"&positionType="+positionType+"&radius="+radius;
		 layer.open({
			  type: 1
			  ,content: "<iframe style='position:fixed;top:0px;left:0px;width:100%;height:100%' src='"+url+"'></iframe>"
			  ,anim: 'up'
			  ,style: 'position:fixed; left:0; top:0; width:100%; height:100%; border: none; -webkit-animation-duration: .5s; animation-duration: .5s;'
			});
	 }
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
<style>
p{
font-size:16px;
padding:5px;
}
p input{
border:1px solid gray;
}
.readonly{
background:#e2e2e2;
}
.title{
font-size:16px;
font-weight:bold;
background:#428bca;
padding:10px;
color:white;
}
textarea{
border:1px solid gray;
}
.img{
border:1px solid gray;
border-radius:10px;
padding:5px;
}
.item{
	border-bottom:1px solid #e2e2e2;
	font-size:14px;
	padding-top:10px;
	padding-bottom:10px;
}

.fixed_div{
      position:fixed;
      right:0px;
      bottom:0px;
      left:0px;
      background:white;
      border-top:1px solid #cdcdcd;
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
<style type="text/css">
.buzhou{
		width: 100%;
		height:auto;
		margin: 0 auto;
		font-size:14px;
		
}
.buzhou_head{
	width: 100%;
	border-bottom: 1px solid #f0f0f0;
	color: #333;
	font-size:14px;
	font-weight:bold;
	line-height:20px;
}
ul li{
	list-style: none;
	color: #717171;
	font-size: 14px;
}
</style>

</head>
<body onload="doInit();" style="overflow-x:hidden;">
<div id="content">
<div style='padding:5px;margin-top:5px;border-top:1px solid #e2e2e2;border-bottom:1px solid #e2e2e2;color:#428bca;font-weight:bold;'>
	<p style="font-size:14px" id="lcjb">流程级别：
	     <select id="level" class="BigSelect" disabled>
			<option value="1">普通</option>
			<option value="2">紧急</option>
			<option value="3">加急</option>
		</select></p>
	<p style="font-size:14px" id="gzmc">工作名称：<span id="title"></span></p>
	<p style="font-size:14px" id="lcfqr">流程发起人：<span id="beginUser"></span></p>
	<p style="font-size:14px" id="kssj">开始时间：<span id="beginTime"></span></p>
	<p style="font-size:14px" id="dqbz">当前步骤：<span id="prcsDesc"></span></p>
	<p style="font-size:14px" id="ysbd">原始表单：<img onclick="window.location = '/system/mobile/phone/workflow/print.jsp?runId=<%=runId%>&view=5&frpSid=<%=frpSid%>';" src="/common/images/workflow/application-document.png"></a></p>
	<p id="docDiv" style="display:none;font-size:14px"></p>
	<p id="docEditDiv" style="display:none;font-size:14px"></p>
	<p id="aipDiv" style="display:none;font-size:14px"></p>
	<p id="aipEditDiv" style="display:none;font-size:14px"></p>
	<p style="font-size:14px" id="xgzy">相关资源：<img onclick="OpenWindow('相关资源','/system/mobile/phone/workflow/relatedResource.jsp?runId=<%=runId%>')" src="/common/images/workflow/application-document.png"></a></p>
	<p style="font-size:14px;display: none;" id="qpd">签批单：
	   <select id="templateId" name="templateId" onchange="changeTemplate();">
	      <option value="0">请选择签批模板</option>
	   </select>
	</p>
</div>
<div class="title">表单</div>
<div id="form" style='padding:5px;margin-top:5px;border-top:1px solid #e2e2e2;border-bottom:1px solid #e2e2e2;'></div>
<div id="ggfj">
   <div class="title">公共附件</div>
	<div id="pulicAttachments" style="padding:10px;"></div>
	<div id="publicAttachUploadContainer" style="padding:10px;display:none;"></div>
	<div id="publicAttachUploadButtons" style="padding:10px;display:none;">
		<button class="btn btn-primary" onclick="doUploadPublicAttach()">上传附件</button>
	</div>
</div>

<div class="title">会签意见</div>
<p id="feedbackDiv"></p>
<p id="feedbackForm" style="display:none">
	点击录制语音：<img style="cursor:pointer" title="点击录制语音" onclick="RecordVoice('FEEDBACK_VOICE')" src="/common/images/workflow/voice.png"/>
	<input type="hidden" id="FEEDBACK_VOICE" name="voiceId" value=""/>
	<br/>
	<textarea id="feedbackTextarea" name="feedbackTextarea" ontouchstart="quickFillData(this)" style="height:100px;width:99%"></textarea>
<!-- 	<div id="publicFeedbackUploadContainer" style="padding:10px;"></div> -->
<!-- 	<div style="padding:10px;"> -->
<!-- 		<button class="btn btn-primary" onclick="doUploadFeedbackAttach()">上传图片</button> -->
<!-- 	</div> -->
</p>


<!-- 流程办理步骤 -->
<div class="title" style="margin-top: 10px;">流程步骤</div>
     <div id="tbody" style="background: none;">
     
     </div>
</div>
</div>
<div id="operDiv" style="text-align:center;padding:10px;" class="fixed_div">
	<span id="prcsEventDefSpan"></span>
	<button class="btn btn-default" onclick="goBack();">返回</button>
	<button id="turnBtn" class="btn btn-primary" onclick="turnNext();" style="display:none">转交</button>
	<button class="btn btn-success" onclick="save(1,1)" id="saveBtn">保存</button>
	<button id="backToBtn" class="btn btn-primary" style="display:none;" onclick="backTo()">回退</button>
	<button id="backToOtherBtn" class="btn btn-primary" style="display:none;" onclick="backToOther()">回退</button>
	<button id="backToFixedBtn" class="btn btn-primary" style="display:none;" onclick="backToFixed()">回退</button>
	<button id="delegateBtn" class="btn btn-primary btn-sm" style="display:none;" onclick="doDelegate()">委托</button>
	<button id="archivesBtn" class="btn btn-success" style="display:none;" onclick="archives()">归档</button>
	<div style="margin-top:5px;">
		<button id="docSendBtn" style="display:none" class="btn btn-danger"  onclick="window.location='docSendPage.jsp?runId=<%=runId%>&flowId=<%=flowId%>&frpSid=<%=frpSid%>';">公文分发</button>
		<button id="docViewBtn" style="display:none" class="btn btn-info" onclick="window.location='docViewPage.jsp?runId=<%=runId%>&flowId=<%=flowId%>&frpSid=<%=frpSid%>';">公文传阅</button>
	</div>
</div>
<br/><br/><br/><br/><br/><br/>
<iframe id="dataFetchFrm" style="border:0px solid gray;display:none;position:fixed;background:white;left:0px;top:0px;height:100%;width:100%" src=""></iframe>
<iframe id="mobileSealFrm" style="border:0px solid gray;display:none;position:fixed;background:white;left:0px;top:0px;bottom:0px;right:0px" src=""></iframe>
<iframe id="h5SealFrm" style="border-top:0px solid blue;border-bottom:0px solid blue;display:none;position:fixed;background:white;left:0px;top:0px;height:100%;width:100%" src=""></iframe>

<script>
var UE = {
		getEditor:function(){
			//alert();
		}
};

</script>
</body>
</html>
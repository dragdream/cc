<%@page import="com.tianee.oa.oaconst.TeeConst"%>
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
<title>工作转交</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<link href="<%=mobilePath%>/phone/style/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=contextPath %>/common/jquery/jquery-min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/mobile/js/tools.js"></script> 
<script type="text/javascript" src="<%=contextPath%>/common/js/prototype.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/mobile/js/mui.min.js"></script> 
<script src="<%=request.getContextPath() %>/system/mobile/js/api.js?v=2"></script>
<script src="<%=request.getContextPath() %>/system/mobile/js/orgselect.js?v=3"></script>
<script>
var  contextPath = "<%=contextPath %>";
var mobilePath ="<%=mobilePath%>";
var userId = <%=loginUser.getUuid()%>;
</script>
<%
	String runId = request.getParameter("runId");
	String flowId = request.getParameter("flowId");
	String frpSid = request.getParameter("frpSid");
	String prcsEvent = request.getParameter("prcsEvent");
	prcsEvent=prcsEvent==null?"":prcsEvent;
%>
<script type="text/javascript">
var runId = "<%=runId%>";
var flowId = "<%=flowId%>";
var frpSid = "<%=frpSid%>";
var userAgent = "<%=request.getHeader("user-agent")%>";
function doInit(){
	if(window.external){
		window.external.setTitle("转交");
	}
	wfCluster.init();
	document.body.onclick=function(){
		$('#opFlagOptsPanel').remove();
	}
}


var prcsEvent = '<%=prcsEvent%>';

var allNextPrcsNodes = [];
var parallelPrcsNodes = [];
var disabledPrcsNodes = [];
var parallelChildNodes = {};
var prcsNodeInfos = [];
var prcsNodePrefix = '#prcsTo';
var nextPrcsContent;
var parallelPrcsContent;
var childFlowNodeInfos = [];
var diabledChildFlowNodes = [];

var parentWindowObj;
var viewPriv;
var error = false;
var autoTurn = 0;
function doInit(){
	wfCluster.init();
	
	document.body.onclick=function(){
		$('#opFlagOptsPanel').remove();
	}
}






//选人控件
function selectSingleUser(a,b,c,d){
	var iframe;
	if($("#selectUserIframe").length){
		iframe = $("#selectUserIframe");
	}else{
		iframe = $("<iframe id='selectUserIframe' src='' frameborder=0 ></iframe>");
		$("body").append(iframe);
	}
	
	$("#selectUserIframe").css({
		position: "fixed",
		left:0,
		top:0,
		right:0,
		bottom:0,
		width: '100%',
		height :'100%',
		zIndex:100000000000000
	}).show();
	var url = '/system/mobile/userselect/selectPageWorkflow.jsp?inputName='+b+'&hiddenUserId='+a+'&single=true'+'&prcsId='+c+'&frpSid='+d;
	$("#selectUserIframe").attr("src",url);
}

function selectUser(a,b,c,d){
	var iframe;
	if($("#selectUserIframe").length){
		iframe = $("#selectUserIframe");
	}else{
		iframe = $("<iframe id='selectUserIframe' src='' frameborder=0 ></iframe>");
		$("body").append(iframe);
	}
	
	$("#selectUserIframe").css({
		position: "fixed",
		left:0,
		top:0,
		right:0,
		bottom:0,
		width: '100%',
		height :'100%',
		zIndex:100000000000000
	}).show();
	var url = '/system/mobile/userselect/selectPageWorkflow.jsp?inputName='+b+'&hiddenUserId='+a+'&single=false'+'&prcsId='+c+'&frpSid='+d;
	$("#selectUserIframe").attr("src",url);
}






</script>
<script type="text/javascript" src="turnFixedNext.js?version=18"></script>
<style>
.trun_prcs_box {
	float: left;
	margin-right: 10px;
	padding: 5px;
	cursor: pointer;
	margin-bottom: 10px;
}

.trun_parallel_prcs_box {
	float: left;
	margin-right: 10px;
	padding: 5px;
	cursor: pointer;
	margin-bottom: 10px;
}

.trun_prcs_disabled_box {
	color: gray;
}

.opFlagStyle {
	color: blue;
	cursor: pointer;
}

.opFlagOptsPanel {
	width: 100px;
	position: absolute;
	background: white;
	border: 1px solid green;
}

.opFlagOptsPanel div {
	padding: 5px;
	text-align: center;
	cursor: pointer;
}

.opFlagOptsPanel div:hover {
	background: #e5f1fa;
}

.prcsOpts_tb {
	width: 100%;
	border-collapse: collapse;
	text-align: left;
	margin-bottom: 3px;
}

.prcsOpts_tb td {
	font-size: 12px;
}

.prcsOpts_header {
	background-color: #428bca;
	color: white;
	padding: 10px;
}

.prcsOpts_opUser {
	
}

.prcsOpts_prcsUser {
	
}

textarea {
	border: 1px solid gray;
}

div,p,span,td,h1,h2,h3,h4,b {
	font-size: 16px;
}

h2  {
	 margin: 0;
	 padding: 10px 0;
	 font-size: 14px;
	 
}
</style>
<link rel="stylesheet" href="<%=request.getContextPath() %>/system/mobile/mui/css/app.css?v=2">
</head>
<body onload="doInit();">
<center id="center">
<table class="TableBlock" width="99%" align="center">
	<tr>
    <td colspan="2"  class="TableData">
    	<div class="prcsOpts_header"><b >步骤选择</b></div>
    </td>
   </tr>
   <tr>
    <td id="nextPrcsContent"   colspan="2"  class="TableData">
    	
    </td>
   </tr>
   <tr>
    <td id="parallelPrcsContent"   colspan="2"  class="TableData" style="display:none">
    	
    </td>
   </tr>
   <tr>
    <td id="prcsOpts"  style="font-size:12px" colspan="2"  class="TableData">
    	
    </td>
   </tr>
   <tr>
    <td id="childFlow" style="text-align:left"  colspan="2"  class="TableData">
    	
    </td>
   </tr>
   <tr>
    <td id="passing" style="display:none"  colspan="2"  class="TableData">
    <b>选择流程传阅人</b>
    <table style="border:0px" style="font-size:12px">
    	<tr>
    		<td style="border:0px;font-size:12px">传阅人：</td>
    		<td style="border:0px;font-size:12px">
    			<textarea id="viewPersonDesc" class="BigTextarea" readonly style="width:98%;height:80px"></textarea>
		    	<input id="viewPerson" type="hidden" name="viewPerson"/>
		    	<input id="viewPersonFilter" type="hidden" name="viewPersonFilter"/>
		    	&nbsp;<a href="javascript:void(0)" onclick="wfCluster.selectViewPerson()">选择</a>&nbsp;<a href="javascript:void(0)" onclick="clearData('viewPerson','viewPersonDesc')">清空</a>
    		</td>
    	</tr>
    </table>
    </td>
   </tr>
   <tr>
    <td colspan="2" style="text-align:left;">
    	<div class="prcsOpts_header"><b >事物提醒</b></div>
    	<div>
			下一步办理人：
			<input clazz="nextPrcsAlert" id="nextPrcsAlert1" type="checkbox" value="1"/><label for="nextPrcsAlert1"><img align=absMiddle src="<%=contextPath %>/common/images/workflow/sms.png" title="内部消息"/></label>
			<input clazz="nextPrcsAlert" id="nextPrcsAlert2" type="checkbox" value="2" /><label for="nextPrcsAlert2"><img align=absMiddle src="<%=contextPath %>/common/images/workflow/mobile_sms.png"  title="手机短信"/></label>
			<input clazz="nextPrcsAlert" id="nextPrcsAlert3" type="checkbox" value="4" /><label for="nextPrcsAlert3"><img align=absMiddle src="<%=contextPath %>/common/images/workflow/email.png"  title="邮件提醒"/></label>
			<br/>
			流程发起人：
			<input clazz="beginUserAlert" id="beginUserAlert1" type="checkbox" value="1" /><label for="beginUserAlert1"><img align=absMiddle src="<%=contextPath %>/common/images/workflow/sms.png"  title="内部消息"/></label>
			<input clazz="beginUserAlert" id="beginUserAlert2" type="checkbox" value="2" /><label for="beginUserAlert2"><img align=absMiddle src="<%=contextPath %>/common/images/workflow/mobile_sms.png"  title="手机短信"/></label>
			<input clazz="beginUserAlert" id="beginUserAlert3" type="checkbox" value="4" /><label for="beginUserAlert3"><img align=absMiddle src="<%=contextPath %>/common/images/workflow/email.png"  title="邮件提醒"/></label>
			<br/>
			全部经办人：
			<input clazz="allPrcsUserAlert" id="allPrcsUserAlert1" type="checkbox" value="1" /><label for="allPrcsUserAlert1"><img align=absMiddle src="<%=contextPath %>/common/images/workflow/sms.png" title="内部消息"/></label>
			<input clazz="allPrcsUserAlert" id="allPrcsUserAlert2" type="checkbox" value="2" /><label for="allPrcsUserAlert2"><img align=absMiddle src="<%=contextPath %>/common/images/workflow/mobile_sms.png"  title="手机短信"/></label>
			<input clazz="allPrcsUserAlert" id="allPrcsUserAlert3" type="checkbox" value="4" /><label for="allPrcsUserAlert3"><img align=absMiddle src="<%=contextPath %>/common/images/workflow/email.png"  title="邮件提醒"/></label>
		</div>
		<DIV style="margin-top: 5px;display:none" ><b>提醒内容：</b>
			<input id="msg" class="BigInput" value="" style="width:500px" type="text" />
		</DIV>
    </td>
   </tr>
</table>
<button class="btn btn-default" onclick="history.go(-1)">返回</button>
<button class="btn btn-success" id="turnNextBtn" onclick="tuenNext()">转交</button>
</center>
<iframe id="dataFetchFrm" style="border:4px solid gray;display:none;position:absolute;background:white" src=""></iframe>
</body>
</html>
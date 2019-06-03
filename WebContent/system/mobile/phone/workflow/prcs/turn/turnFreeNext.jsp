<%@page import="com.tianee.oa.oaconst.TeeConst"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tianee.oa.core.org.bean.TeePerson" %>
<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
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
<title>流程转交</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<link href="<%=mobilePath%>/phone/style/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=contextPath %>/common/jquery/jquery-min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/mobile/js/tools.js"></script> 
	<script type="text/javascript"
	src="<%=contextPath%>/common/js/prototype.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/system/mobile/js/mui.min.js"></script> 
	<script src="<%=request.getContextPath() %>/system/mobile/js/api.js?v=2"></script>
<script>
var  contextPath = "<%=contextPath %>";
var mobilePath ="<%=mobilePath%>";
var userId = <%=loginUser.getUuid()%>;
</script>
<%
	int runId = TeeStringUtil.getInteger(request.getParameter("runId"),0);
	int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"),0);
	int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"),0);
	String runName =request.getParameter("runName");
	String prcsId =request.getParameter("prcsId");
%>
<script type="text/javascript" src="<%=contextPath%>/common/js/TeeMenu.js"></script>
<script type="text/javascript">
var runId = <%=runId%>;
var flowId = <%=flowId%>;
var frpSid = <%=frpSid%>;
var runName = '<%=runName%>';
var userAgent = "<%=request.getHeader("user-agent")%>";
var prcsId = 0;

var maxPrc = 0;
/**
 * 控制字段
 */
var fieldCtrlArray = null;
/**
 * 由于是自由流程 所以 选择人员不过滤
 */
function doInit(){
	window.history.forward(1);
	if(window.external){
		window.external.setTitle("转交");
	}
		initPrcsInfo();
		document.body.onclick=function(){
			$('#opFlagOptsPanel').remove();
		}
}

</script>
<script type="text/javascript" src="turnFreeNext.js?version=5"></script>
<style>
textarea{
border:1px solid gray;
}
div,p,span,td,h1,h2,h3,h4,b{
font-size:16px;
}
.prcsOpts_header{
	background-color:#428bca;
	color:white;
	padding:10px;
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
</style>
</head>
<body onload="doInit()">
<form name="turnForm" id="turnForm" method="post" action="">
<table id="prcsTable" class="TableBlock" width="99%">
   <tbody id="prcsListTbody">
   </tbody>
    <tbody id="preListTbody"></tbody>
   <tr class="TableControl" height="30" id="previewDiv" >
      <td colspan="2">
      <span id="addBtn" >
       <input type="button" value="+ 增加下一个预设步骤"  onClick="addPre()" title="预设更多后续步骤的经办人和主办人" name="button" class="btn btn-default">
       </span>
       <span id="delBtn" style="display:none" >
       <input type="button" value="- 删除最后一个预设步骤"  onClick="delPre()" title="删除最后一个预设步骤" name="button" class="btn btn-default">
      </span>
      </td>
    </tr>
 	   <tr >
      <td colspan="2" >
      <div style="margin-bottom:5px;font-weight:bold" class="prcsOpts_header">消息提醒</div>
		<div>
			下一步办理人：
			<input clazz="nextPrcsAlert" id="nextPrcsAlert1" checked type="checkbox" value="1"/><label for="nextPrcsAlert1"><img align=absMiddle src="<%=contextPath %>/common/images/workflow/sms.png" title="内部消息"/></label>
			<input clazz="nextPrcsAlert" id="nextPrcsAlert2" type="checkbox"  value="2"/><label for="nextPrcsAlert2"><img align=absMiddle src="<%=contextPath %>/common/images/workflow/mobile_sms.png"  title="手机短信"/></label>
			<input clazz="nextPrcsAlert" id="nextPrcsAlert3" type="checkbox"  value="4"/><label for="nextPrcsAlert3"><img align=absMiddle src="<%=contextPath %>/common/images/workflow/email.png"  title="邮件提醒"/></label>
			<br/>
			流程发起人：
			<input clazz="beginUserAlert" id="beginUserAlert1" type="checkbox"  value="1"/><label for="beginUserAlert1"><img align=absMiddle src="<%=contextPath %>/common/images/workflow/sms.png"  title="内部消息"/></label>
			<input clazz="beginUserAlert" id="beginUserAlert2" type="checkbox"  value="2"/><label for="beginUserAlert2"><img align=absMiddle src="<%=contextPath %>/common/images/workflow/mobile_sms.png"  title="手机短信"/></label>
			<input clazz="beginUserAlert" id="beginUserAlert3" type="checkbox"  value="4"/><label for="beginUserAlert3"><img align=absMiddle src="<%=contextPath %>/common/images/workflow/email.png"  title="邮件提醒"/></label>
			<br/>
			全部经办人：
			<input clazz="allPrcsUserAlert" id="allPrcsUserAlert1" type="checkbox"  value="1"/><label for="allPrcsUserAlert1"><img align=absMiddle src="<%=contextPath %>/common/images/workflow/sms.png" title="内部消息"/></label>
			<input clazz="allPrcsUserAlert" id="allPrcsUserAlert2" type="checkbox"  value="2"/><label for="allPrcsUserAlert2"><img align=absMiddle src="<%=contextPath %>/common/images/workflow/mobile_sms.png"  title="手机短信"/></label>
			<input clazz="allPrcsUserAlert" id="allPrcsUserAlert3" type="checkbox"  value="4"/><label for="allPrcsUserAlert3"><img align=absMiddle src="<%=contextPath %>/common/images/workflow/email.png"  title="邮件提醒"/></label>
		</div>
     </td>
    </tr>
</table>
</form>
<center>
<button class="btn btn-success" onclick="turnNext()">转交</button>
</center>
<div id="attachDiv" style="overflow: hidden"></div>
<iframe id="dataFetchFrm" style="border:4px solid gray;display:none;position:absolute;background:white" src=""></iframe>
</body>
</html>
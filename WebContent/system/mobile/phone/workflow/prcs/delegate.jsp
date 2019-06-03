<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"),0);
%>
	<%
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"),
				0);
		int flowId = TeeStringUtil.getInteger(request
				.getParameter("flowId"), 0);
	%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/system/mobile/mui/header.jsp" %>
<title>流程委托</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<script type="text/javascript">
var frpSid = <%=frpSid%>;
var userAgent = "<%=request.getHeader("user-agent")%>";
function doInit(){
	var url = contextPath+"/workflowManage/getDelegateHandlerData.action";
	
	$.ajax({
		  type: 'POST',
		  url: url,
		  data: {frpSid:frpSid},
		  timeout: 6000,
		  success: function(data){
			  var json = eval('(' + data + ')');
			  var data = json.rtData.prcsModel;
				window.RANDOM_USER_FILTER = json.rtData.userFilters;
				$("#title").html(data.runName);
				$("#prcsDesc").html(data.prcsName);
				
		  },
		  error: function(xhr, type){
		    
		  }
		});
}


function commit(){
	var delegate = $("#delegate");
	if(delegate.val()==""){
		Alert("请选择委托人");
		return;
	}

	var url = contextPath+"/workflowManage/flowRunDelegate.action";
	$("button").attr("disabled","");
	$.ajax({
		  type: 'POST',
		  url: url,
		  data: {frpSid:frpSid,delegateTo:delegate.val()},
		  timeout: 6000,
		  success: function(data){
			  var json = eval('(' + data + ')');
			  if(json.rtState){
					if(userAgent.indexOf("DingTalk")!=-1 || userAgent.indexOf("MicroMessenger")!=-1){
						window.location = "../index.jsp";
					}else{
						CloseWindow();
					}
				}else{
					Alert(json.rtMsg);
				}
			  $("button").removeAttr("disabled");
		  },
		  error: function(xhr, type){
		    
		  }
		});
	
}

</script>
<style>

</style>
</head>
<body onload="doInit()" style="font-size:12px">
<div style='padding:5px;margin-top:5px;border-top:1px solid #e2e2e2;border-bottom:1px solid #e2e2e2;color:#428bca;font-weight:bold;background:white'>
	<p>工作名称：<span id="title"></span></p>
	<p>当前步骤：<span id="prcsDesc"></span></p>
	<p>委托给：
			<input type="text" id="delegateDesc" readonly placeholder="选择委托人" onclick="selectSingleUser('delegate','delegateDesc')"/>
			<input type="hidden" id="delegate" />
	</p>
</div>
<center>
	<button class="btn btn-default"  onclick="window.location = 'form.jsp?runId=<%=runId%>&flowId=<%=flowId%>&frpSid=<%=frpSid%>';">返回</button>
	<button class="btn btn-primary"  onclick="commit()">确定</button>
</center>
</body>
</html>

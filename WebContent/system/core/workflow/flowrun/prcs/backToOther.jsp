<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String host = request.getServerName() + ":" + request.getServerPort() + request.getContextPath() ; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<%@ include file="/header/header.jsp" %>
	<%
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"),
				0);
		int frpSid = TeeStringUtil.getInteger(request
				.getParameter("frpSid"), 0);
		int flowId = TeeStringUtil.getInteger(request
				.getParameter("flowId"), 0);
		String isNew = TeeStringUtil.getString(request
				.getParameter("isNew"), "");
	%>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>回退</title>
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/images/workflow/index.css">
	<script type="text/javascript" src="<%=contextPath%>/common/js/workflowUtils.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/TeeMenu.js"></script>
	<script>window.UEDITOR_HOME_URL = "<%=contextPath%>/common/ueditor/";</script>
	<script type="text/javascript" charset="utf-8" src="<%=contextPath%>/common/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=contextPath%>/common/ueditor/ueditor.all.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=contextPath%>/common/ueditor/lang/zh-cn/zh-cn.js"></script>
	
<script>
var runId = <%=runId%>;
var frpSid = <%=frpSid%>;
var flowId = <%=flowId%>;

function doInit(){
	var url = contextPath+"/flowRun/getPrePrcsList.action";
	var json = tools.requestJsonRs(url,{flowId:flowId,frpSid:frpSid});
	if(json.rtState){
		var list = json.rtData;
		var html = "";
		for(var i=0;i<list.length;i++){
			html+="<input type='radio' value='"+list[i].frpSid+"' "+(i==0?"checked":"")+" clazz='prcsBox' name='prcsId' id='prcs"+list[i].frpSid+"'/><label for='prcs"+list[i].frpSid+"' style='font-weight:normal;'>"+list[i].prcsName+"</label><br/>";
		}
		$("#prcsList").html(html);
	}else{
		messageMsg(json.rtMsg,"container","error");
	}
}

function commit(){
	var prcsTo = $("input[name=prcsId]:checked").val();
	
	//是否进行手机短信提醒
	var isPhoneRemind="";
	if($("#isPhoneRemind").attr("checked")=="checked"){
		isPhoneRemind="1";	
	}else{
		isPhoneRemind="0";
	}
	
	var url = contextPath+"/flowRun/backToOther.action";
	var json = tools.requestJsonRs(url,{runId:runId,flowId:flowId,frpSid:frpSid,prcsTo:prcsTo,content:$("#content").val(),isPhoneRemind:isPhoneRemind});
	if(json.rtState){
		return true;
	}else{
		alert(json.rtMsg);
		return false;
	}
}

</script>
</head>
<body onload="doInit()" style="margin:0px;padding:0px;font-size:12px;">
<div id="container" style="padding:5px">
	<!-- <b>回退步骤：</b> -->
	<div id="prcsList"></div>
	<br/>
	<b>填写回退意见：</b>
	<div> 
		<textarea id="content" name="content" style="height:90px;width:320px;padding:5px;" class="BigTextarea"></textarea>
	</div>
	<input  type="checkbox" name="isPhoneRemind" id="isPhoneRemind"/>&nbsp;&nbsp;&nbsp;&nbsp;是否进行短信提醒
</div>
</body>
</html>
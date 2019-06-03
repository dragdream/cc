<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"),0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<title>流程委托</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="<%=contextPath%>/common/js/src/orgselect.js"></script>
<script type="text/javascript">
var frpSid = <%=frpSid%>;

function doInit(){
	var url = contextPath+"/workflowManage/getDelegateHandlerData.action";
	var json = tools.requestJsonRs(url,{frpSid:frpSid});
	if(json.rtState){
		var data = json.rtData.prcsModel;
		var userFilters = json.rtData.userFilters;
		$("#runId").html(data.runId);
		$("#runName").html(data.runName);
		$("#prcsName").html(data.prcsName);
		
		$("#opt").click(function(){
			selectSingleUser(['delegate','delegateDesc'],'','',userFilters,'');
		});
	}else{
		messageMsg(json.rtMsg,"content","error");
		window.commit = function(){};
	}
}


function commit(){
	var delegate = $("#delegate");
	var delegateDesc = $("#delegateDesc");
	if(delegate.val()==""){
		$.MsgBox.Alert_auto("请选择被委托人！");
		return;
	}

	var url = contextPath+"/workflowManage/flowRunDelegate.action";
	var json = tools.requestJsonRs(url,{frpSid:frpSid,delegateTo:delegate.val()});
	if(json.rtState){
		$.MsgBox.Alert_auto(json.rtMsg);
		if(parent.pageHandler){
			parent.pageHandler();//处理调用父页面的处理方法
		}
		$.MsgBox.Alert_auto(true);
		return true;
	}else{
		$.MsgBox.Alert_auto(json.rtMsg);
		return false;
	}
}

</script>
<style>

</style>
</head>
<body onload="doInit()" style="font-size:12px;background-color: #f2f2f2;padding-top: 10px;">
<center id="content">
<table class="TableBlock" width="100%" align="center">
   <tr>
		<td style="text-indent: 10px;">流水号：</td><td class="TableData" id="runId"></td>
   </tr>
   <tr>
		<td style="text-indent: 10px;">工作名称/文号：</td>	<td class="TableData" id="runName"></td>
   </tr>
   <tr>
		<td style="text-indent: 10px;">当前步骤：</td><td class="TableData" id="prcsName"></td>
   </tr>
   <tr>
		<td style="text-indent: 10px;">委托给：</td>	
		<td class="TableData">
			<!-- <button id="opt" class="btn-win-white">委托给</button> -->
			<input type="text" id="delegateDesc" class="BigInput readonly" readonly style="width:150px;height: 25px;font-family: Microsoft YaHei;"/>
			<input type="hidden" id="delegate" class="BigInput readonly" readonly style="width:80px;"/>&nbsp;
		    <span class='addSpan'>
			     <img style="cursor: pointer;" src="<%=contextPath %>/common/zt_webframe/imgs/gzl/gzjk/add.png" id="opt" value="选择"/>
	        </span>	
		
		
		
		</td>
	</tr>
	<tr>
		
	
		
	
	</tr>
</table>
</center>
</body>
</html>

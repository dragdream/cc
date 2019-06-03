<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<%@ include file="/header/header.jsp" %>
	<%
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"),0);
		int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"),0);
		int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"),0);
		String isNew = TeeStringUtil.getString(request.getParameter("isNew"),"");
	%>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>流程办理</title>
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css">
	<script type="text/javascript" src="<%=contextPath%>/common/js/tools.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/sys.js"></script>
	<script>
	var runId = <%=runId%>;
	var frpSid = <%=frpSid%>;
	var flowId = <%=flowId%>;
	var isNew = '<%=isNew%>';
	var runName;

	var contextPath = "<%=contextPath%>";
	function saveFlowRunData(){
		parent.saveFlowRunData();
	}

	function doInit(){
		var url = contextPath+"/flowRun/getHandlerOptPrivData.action";
		var json = tools.requestJsonRs(url,{runId:runId,frpSid:frpSid});
		if(json.rtState){
			if(json.rtData.runName){
				runName = json.rtData.runName;
				$("#runName").attr("value",json.rtData.runName);
				}
			if(json.rtData.turnState && json.rtData.turnState == 1){
				$("<input type=\"button\" class=\"SmallButtonC\" value=\"转交\" onClick=\"turnNext()\" />").appendTo($("#operate"));
				}else if(json.rtData.turnState && json.rtData.turnState == 2){//countersignHandler
			    	$("<input type=\"button\" class=\"SmallButtonC\" value=\"会签完毕\" />").appendTo($("#operate")).bind("click",
					    function(){
			    			turnNext();
				    	});
			    }
			if(json.rtData.turnBack && json.rtData.turnBack == 1){
				$("<input type=\"button\" class=\"SmallButtonC\" value=\"回退\" onClick=\"turnBack()\" />").appendTo($("#operate"));
			}
			if(json.rtData.turnEnd && json.rtData.turnEnd == 1){
				$("<input type=\"button\" class=\"SmallButtonC\" value=\"结束\" />").appendTo($("#operate")).bind("click",
					function(){
					var tempurl = contextPath+"/flowRun/turnEndHandler.action";
					var json = tools.requestJsonRs(tempurl,{runId:runId,frpSid:frpSid});
						if(json.rtState){
							if(isNew == '1'){
									window.parent.close();
								}else{
									window.parent.location = contextPath + "/system/core/workflow/flowrun/list/index.jsp";
							}
						}else{
							alert(json.rtMsg);
							}
					});
			}
		    
		}else{
			//alert(json.rtMsg);
		}
	}
	 /**
     *转交下一步
     */
	function turnNext(){
		//保存表单
		saveFlowRunData();
		
		var url = contextPath+"/flowRun/preTurnHandlerData.action";
		var para = "runId="+runId+"&frpSid="+frpSid+"&flowId="+flowId;
		var json = tools.requestJsonRs(url,{runId:runId,frpSid:frpSid,flowId:flowId});
		if(json.rtState){
			var opCode = json.rtData.opCode;
			if(opCode=="1"){
				parent.window.location = contextPath + "/system/core/workflow/flowrun/prcs/turn/turnFixedNext.jsp?"+para;
			}else if(opCode=="2"){
				parent.window.location = contextPath + "/system/core/workflow/flowrun/prcs/turn/turnFreeNext.jsp?"+para;
			}else{
				parent.CloseWindow();
			}
		}else{
			alert(json.rtMsg);
		}
		//var url = contextPath + "/system/core/workflow/flowrun/prcs/turn/turnFreeNext.jsp?"+para;
		//dialogChangesize(url,700,360);
		//window.parent.location = url;
	}
	/**
     *结束流程
     */
	function turnEnd(){
		alert("结束");
	}
    /**
     *回退
     */
    function turnBack(){
    	alert("回退");
    }
	
	
	
	

	
	</script>
</head>
<body onload="doInit();" style="overflow:hidden">
<div class="workflow_bar1">工作办理</div>
<div style="padding:7px;background:#EEEFF0;">
	<div style="float:left">
		流程名称：<input value="关于xxxxxxx的" id="runName" style="width:200px;background:none;border:0px;border-bottom:1px solid red"/>
	</div>
	<div style="float:right" id="operate">
		<input type="button" class="SmallButtonC" value="保存" onclick="saveFlowRunData()" />
		<input type="button" class="SmallButtonC" value="保存并返回" onclick="saveFlowRunData()" />
	</div>
	<div style="clear:both"></div>
</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tianee.webframe.util.global.TeeSysProps"%>
<!DOCTYPE HTML>
<html>
<head>
<title>新建工作-工作名称</title>
<script>
var DING_APPID = "<%=TeeSysProps.getString("DING_WORKFLOW_APPID")%>";
var flowId = "<%=request.getParameter("flowId")%>";
var sortId = "<%=request.getParameter("sortId")%>";
var runNamePriv = "<%=request.getParameter("runNamePriv")%>";

function doInit(){
	  if(runNamePriv==0){
		  
	  }else if(runNamePriv==1){
		  $("#mainDiv").show();
	  }else if(runNamePriv==2){
		  $("#preDiv").show();
	  }else if(runNamePriv==3){
		  $("#sufDiv").show();
	  }else if(runNamePriv==4){
		  $("#preDiv").show();
		  $("#sufDiv").show();
	  }
}

function commit(){
	
	$("button").attr("disabled","");
	$.ajax({
		  type: 'POST',
		  url: contextPath+"/flowRun/createNewWork.action",
		  data: {fType:flowId,runName:$("#runName").val(),preName:$("#preName").val(),sufName:$("#sufName").val()},
		  timeout: 6000,
		  success: function(data){
			  var json = eval('(' + data + ')');
			  var runId = json.rtData.runId;
			  var frpSid = json.rtData.frpSid;
			  if(json.rtState){
				  if(sortId=="null"){
					  window.location = "prcs/form.jsp?runId="+runId+"&frpSid="+frpSid+"&flowId="+flowId+"&flowTypeId="+flowId;
				  }else{
					  window.location = "prcs/form.jsp?runId="+runId+"&frpSid="+frpSid+"&flowId="+flowId;
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

function goBack(){
	if(sortId=="null"){
		window.location = 'index.jsp?flowTypeId='+flowId;
	}else{
		window.location = 'cr_flow_list.jsp?sortId=<%=request.getParameter("sortId")%>';
	}
	
}
</script>
<%@ include file="/system/mobile/mui/header.jsp" %>
</head>
<body onload="doInit()">
<div id="muiContent" class="mui-content">
	<div class="mui-input-group" id="preDiv" style="display:none">
		<div class="mui-input-row">
			<label>名称前缀</label>
		</div>
		<div class="mui-input-row">
			<input type="text" id="preName" name="preName" placeholder="请输入名称前缀，允许为空" />
		</div>
	</div>
	<div class="mui-input-group" id="mainDiv" style="display:none">
		<div class="mui-input-row">
			<label>工作名称</label>
		</div>
		<div class="mui-input-row">
			<input type="text" id="runName" name="runName" placeholder="不填写则按照默认工作名称命名" />
		</div>
	</div>
	<div class="mui-input-group" id="sufDiv" style="display:none">
		<div class="mui-input-row">
			<label>名称后缀</label>
		</div>
		<div class="mui-input-row">
			<input type="text" id="sufName" name="sufName" placeholder="请输入名称后缀，允许为空" />
		</div>
	</div>
	<br/>
	<center>
		<button class="btn btn-primary" onclick="goBack()">返回</button>
		<button class="btn btn-primary" onclick="commit()">确定</button>
	</center>
</div>
</body>
</html>
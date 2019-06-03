<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%String host = request.getServerName() + ":" + request.getServerPort() + request.getContextPath() ; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/system/mobile/mui/header.jsp" %>
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
	<title>回退上一步</title>
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/images/workflow/index.css">
	
<script>
var runId = <%=runId%>;
var frpSid = <%=frpSid%>;
var flowId = <%=flowId%>;
var error = false;
var userAgent = "<%=request.getHeader("user-agent")%>";
function doInit(){
	var url = contextPath+"/flowRun/getPreReachablePrcsList.action";
	$.ajax({
		  type: 'POST',
		  url: url,
		  data: {flowId:flowId,frpSid:frpSid},
		  timeout: 6000,
		  success: function(data){
			  var json = eval('(' + data + ')');
			  var list = json.rtData;
				var html = "";
				for(var i=0;i<list.length;i++){
					html+="<input type='radio' value='"+list[i].frpSid+"' "+(i==0?"checked":"")+" clazz='prcsBox' name='prcsId' id='prcs"+list[i].frpSid+"'/><label for='prcs"+list[i].frpSid+"' style='font-weight:normal;'>"+list[i].prcsName+"</label><br/>";
				}
				$("#prcsList").html(html);
				
		  },
		  error: function(xhr, type){
		    window.error = true;
		  }
		});
}

function commit(){
	if(window.error){
		return;
	}
	var prcsTo = $("input[name=prcsId]:checked").val();
	
	var url = contextPath+"/flowRun/backToOther.action";
	
	$("button").attr("disabled","");
	$.ajax({
		  type: 'POST',
		  url: url,
		  data: {runId:runId,flowId:flowId,frpSid:frpSid,prcsTo:prcsTo,content:$("#content").val()},
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
</head>
<body onload="doInit()" style="margin:0px;padding:0px;font-size:12px">
<div id="container" style="padding:5px">
	<b>回退步骤：</b>
	<div id="prcsList"></div>
	<br/>
	<b>填写回退意见：</b>
	<div> 
		<textarea id="content" name="content" style="height:90px;width:320px;padding:5px;" class="BigTextarea"></textarea>
	</div>
</div>

<center>
	<button class="btn btn-default"  onclick="window.location = 'form.jsp?runId=<%=runId%>&flowId=<%=flowId%>&frpSid=<%=frpSid%>';">返回</button>
	<button class="btn btn-primary"  onclick="commit()">确定</button>
</center>
</body>
</html>
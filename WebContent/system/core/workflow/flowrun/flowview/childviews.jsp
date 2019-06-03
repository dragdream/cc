<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%
	int flowPrcsId = TeeStringUtil.getInteger(request.getParameter("flowPrcsId"),0);
	int runId = TeeStringUtil.getInteger(request.getParameter("runId"),0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<script>
var runId = "<%=runId%>";
var flowPrcsId = "<%=flowPrcsId%>";
var childRunList = new Array();
window.onload=function(){
	var url = contextPath+"/flowInfoChar/getFlowRunChildData.action";
	var json = tools.requestJsonRs(url,{runId:runId});
	if(json.rtState){
		childRunList = json.rtData;
	}else{
		messageMsg(json.rtMsg,"content","error");
		return;
	}

	var html = "";
	for(var i=0;i<childRunList.length;i++){
		if(flowPrcsId==childRunList[i].pFlowPrcsId || flowPrcsId=="0"){
			html+="<tr>";
				html+="<td style='width:50px' align=right>";
				html+="<img src='<%=request.getContextPath() %>/common/images/workflow/icon-flow.png' />";
				html+="</td>";
				html+="<td>";
				if(childRunList[i].recType=="1"){//未接收
					html+="<span style='color:red'>[未接收]</span>";
				}else{//已接收
					html+="<span style='color:green'>[已接收]</span>";
				}
				html+="<b><a href='javascript:void(0)' onclick='topopen("+childRunList[i].runId+","+childRunList[i].flowId+")'>"+childRunList[i].runName+"</a></b>";
				html+="<br/>";
				html+="<span>发起人：<b style='color:blue'>"+childRunList[i].beginUserName+"</b>&nbsp;&nbsp;发起时间："+childRunList[i].beginTime+"</span>";
				html+="<br/>";
				html+="</td>";
			html+="</tr>";
		}
	}
	if(html==""){
		messageMsg("无子流程数据","content","info");
		return;
	}

	$("#tb").html(html);
	
}

function topopen(runId,flowId){
	var url = contextPath+"/system/core/workflow/flowrun/flowview/index.jsp?runId="+runId+"&flowId="+flowId;
	top.bsWindow(url,"查看子流程",{width:"600",height:"320"});
}

</script>
<style>
#tb{
	border-collapse:collapse;
}
#tb td{
	padding:5px;
	font-size:12px;
}
</style>
</head>
<body>
<div id="content">
	<table id="tb">
	</table>
</div>
</body>
</html>
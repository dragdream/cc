<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%
	int runId = TeeStringUtil.getInteger(request.getParameter("runId"),0);
	String thread_local_archives = TeeStringUtil.getString(request.getParameter("thread_local_archives"));
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/js/tabs/demo.css"/>
<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css"/>
<script type="text/javascript" src="<%=contextPath%>/common/js/tools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/easyuiTools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sys.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/tabs/tabs.js"></script>
<script>

var runId = <%=runId%>;
var thread_local_archives = "<%=thread_local_archives%>";
function doInit(){
	var url = contextPath + "/flowRunLog/getFlowRunLogs.action";
	var json = tools.requestJsonRs(url,{runId:runId,thread_local_archives:thread_local_archives});
	var list = json.rtData;
	if(list.length==0){
		messageMsg("未找到流程日志数据","container","info");
		return;
	}
	
	var info = list[0];
	var type = info.flowPrcs!=0?1:2;

	//拼写表头
	var header = "<tr style='background:#fafafa;'>";
	header+="<td style='font-weight:bold;'>步骤序号</td>";
	if(type==1){//固定流程才有步骤名称
		header+="<td style='font-weight:bold;'>步骤名称</td>";
	}
	header+="<td style='font-weight:bold;'>办理人员</td>";
	header+="<td style='font-weight:bold;'>创建时间</td>";
	header+="<td style='font-weight:bold;'>内容</td>";
	header+="</tr>";
	$("#thead").html(header);

	var bodyRender = "";
	for(var i=0;i<list.length;i++){
		var info = list[i];
		bodyRender+="<tr>";
		bodyRender+="<td>"+info.prcsId+"</td>";
		if(type==1){//固定流程才有步骤名称
			bodyRender+="<td>"+(info.prcsName==null?"":info.prcsName)+"</td>";
		}
		bodyRender+="<td>"+info.userName+"</td>";
		bodyRender+="<td>"+info.timeDesc+"</td>";
		bodyRender+="<td style='text-align:left'>"+info.content+"</td>";
		bodyRender+="</tr>";
	}

	$("#tbody").html(bodyRender);
}

</script>
<style>
td{
border:1px solid #e2e2e2;
padding:5px;
}
</style>
</head>
<body onload="doInit()" style="margin:10px">
<div id="container">
<center>
	<table style="width:90%;font-size:12px;border:1px solid #e2e2e2;border-collapse:collapse;">
		<thead id="thead">
			
		</thead>
		<tbody id="tbody">
			
		</tbody>
	</table>
</center>
</div>
</body>
</html>
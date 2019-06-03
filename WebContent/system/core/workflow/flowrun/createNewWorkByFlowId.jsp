<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.tianee.oa.core.org.service.TeePersonService" %>
<%
	int flowTypeId = TeeStringUtil.getInteger(request.getParameter("flowTypeId"),0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<%@ include file="/header/header.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>新建工作</title>
	<script type="text/javascript" src="<%=contextPath %>/common/services/TeeFlowTypeService.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/common/services/TeeFlowSortService.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/common/services/TeeFormSortService.js"></script>
	<script>
		var contextPath = "<%=contextPath%>";
		var flowTypeId = <%=flowTypeId%>;
		var sortId;
		function doInit(){
			initExtraData();
		}

		//初始化控件数据信息
		function initExtraData(){
			var flowTypeService = new TeeFlowTypeService();
			var flowType = flowTypeService.get(flowTypeId);
			if(!flowType){
				messageMsg("该流程信息不存在","body","info");
				return;
			}
			
			$("#f0").attr("src",contextPath+"/system/core/workflow/flowrun/flowview/flowview.jsp?flowId="+flowTypeId);
		}

		function createNewWork(){
			if(window.confirm("确认创建该工作吗？")){
				var url = contextPath + "/flowRun/createNewWork.action";
				var json = tools.requestJsonRs(url, {
					fType : flowTypeId
				});
				if (json.rtState) {
					window.openFullWindow(contextPath
							+ "/system/core/workflow/flowrun/prcs/index.jsp?runId="
							+ json.rtData.runId + "&frpSid=" + json.rtData.frpSid
							+ "&flowId=" + flowTypeId + "&isNew=1", "流程办理");
				} else {
					alert(json.rtMsg);
				}
			}
		}
	</script>
</head>
<body id="body" onload="doInit()" style="margin:0px;padding:0px;">
	<div style="font-family:微软雅黑;position:absolute;left:0px;right:0px;top:0px;height:50px;background:#f0f0f0;line-height:50px;padding-left:10px;padding-right:10px">
		&nbsp;<button class="btn btn-primary" onclick="createNewWork()">创建工作</button>
	</div>
	<div style="position:absolute;left:0px;right:0px;bottom:0px;top:50px;">
		<iframe id="f0" style="width:100%;height:100%" frameborder="0"></iframe>
	</div>
	
</body>

</html>
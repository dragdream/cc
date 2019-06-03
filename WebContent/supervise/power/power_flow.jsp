<%@page import="net.sf.json.JSONObject"%>
<%@page import="java.util.*"%>
<%@page import="com.tianee.oa.core.workflow.flowrun.bean.FlowRunToken"%>
<%@page import="com.tianee.webframe.util.auth.TeeFunctionControl"%>
<%TeeFunctionControl.distinguishTheVersion(request,response);%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    
<!-- 平台通用样式 -->
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<script type="text/javascript" src="<%=contextPath%>/common/js/workflowUtils.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/power/js/power_flow.js"></script>
<script type="text/javascript">
<% 
String tokenInfo = "";
JSONObject json = new JSONObject();
FlowRunToken token = null;

if(TeeStringUtil.getString(request.getParameter("token"), "") != "") {
    token = new FlowRunToken(request);

    json.put("flowPath", token.getVars().get("FLOW_PATH"));
    json.put("primaryId", token.getVars().get("PRIMARY_ID"));
    json.put("runId", token.getRunId());
    json.put("frpSid", token.getFrpSid());
    json.put("flowId", token.getFlowId());
    json.put("goBack", token.getGoBack());
    json.put("prcsId", token.getPrcsId());
    json.put("isFinished", token.isFinished());
    
    if (token.isPrcsUser()) {
        if(token.isFinished()) {
            json.put("readOnly", true);
        } else {
            json.put("readOnly", false);
        }
    } else {
        json.put("readOnly", true);
    }
    
    tokenInfo = json.toString();
}
%>
</script>

</head>
<body onload="doInit()">
    <input type="hidden" id="power_flow_token" value='<%=tokenInfo %>' />
    <iframe id="power_flow_frame" src="" style="width:100%;height:100%;" frameborder=0></iframe>
</body>
</html>
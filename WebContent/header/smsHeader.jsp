<%@page import="com.tianee.webframe.util.date.TeeDateUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.tianee.oa.core.general.model.TeeSmsModel" %>
<%@ page import="com.tianee.webframe.util.str.TeeJsonUtil" %>
<%@ page import="com.tianee.webframe.util.global.TeeSysProps" %>
<%@ page import="com.tianee.oa.core.org.bean.TeeDepartment" %>
<%@ page import="com.tianee.oa.core.org.bean.TeeUserRole" %>

<%@ include file="/header/header.jsp" %>
<%
	String smsRefresh = TeeSysProps.getString("$SMS_REFRESH_TIME");
	if (smsRefresh == null || "".equals(smsRefresh.trim())) {
	  smsRefresh = "10";
	}
	String smsCallCount = TeeSysProps.getString("$SMS_VIOCE_MAX");
	if (smsCallCount == null || "".equals(smsCallCount.trim())) {
	  smsCallCount = "3";
	}
	String smsVoiceSpace = TeeSysProps.getString("$SMS_VOICE_SPACE");
	if (smsVoiceSpace == null || "".equals(smsVoiceSpace.trim())) {
	  smsVoiceSpace = "3";
	}
	TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);

	TeeDepartment dept = person.getDept();
	TeeUserRole userRole = person.getUserRole();
	String deptNameDesc = "";
	String roleNameDesc = "";
	if(dept != null){
		deptNameDesc = dept.getDeptName();
	}
	if(userRole != null){
		roleNameDesc = userRole.getRoleName();
	}
	long loginTime = person.getDynamicInfo().getOnline();
	long loginTimeL = loginTime * 1000;
	String onlineTime = TeeDateUtil.getTimeMilisecondDesc(loginTimeL);
	
	String userNameDesc = person.getUserName();
	String userInfo = "姓名: " +  userNameDesc
	+ "\n部门: " + deptNameDesc
	+ "\n角色: " + roleNameDesc
	+ "\n在线时长: " + onlineTime
	;
%>
<script type="text/javascript">
var bSmsPriv = true;
var userId = "<%=person.getUserId()%>";
var userName = "<%=person.getUserName()%>";
</script>

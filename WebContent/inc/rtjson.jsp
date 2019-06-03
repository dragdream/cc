<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.tianee.oa.oaconst.TeeActionKeys"%>
<%
	Boolean rtState = (Boolean)request.getAttribute(TeeActionKeys.RET_STATE);
String rtMsg = (String)request.getAttribute(TeeActionKeys.RET_MSRG);
String rtData = (String)request.getAttribute(TeeActionKeys.RET_DATA);
if (rtMsg == null) {
	rtMsg = "";
}else {
	rtMsg = rtMsg.replace("\"", "\\\"");
}
if (rtData == null) {
  rtData = "\"\"";
}
%>
{"rtState":<%=rtState %>, "rtMsg":"<%=rtMsg %>", "rtData":<%=rtData %>}
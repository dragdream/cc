<%@page import="com.tianee.oa.oaconst.TeeConst"%>
<%@page import="com.tianee.oa.core.org.bean.TeePerson"%>
<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@page import="com.tianee.webframe.util.servlet.TeeCookieUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	

<link rel="stylesheet" href="<%=request.getContextPath() %>/common/kinggrid/dialog/artDialog/ui-dialog.css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/common/kinggrid/core/kinggrid.plus.css">

<script type="text/javascript" src="<%=request.getContextPath() %>/common/kinggrid/core/kinggrid.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/common/kinggrid/core/kinggrid.plus.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/common/kinggrid/dialog/artDialog/dialog.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/common/kinggrid/signature.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/common/kinggrid/signature.pc.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/common/kinggrid/password.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/common/kinggrid/signature_pad.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/common/kinggrid/jquery.timer.dev.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/common/kinggrid/jquery.qrcode.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/common/kinggrid/qrcode.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/common/kinggrid/jsQR.js"></script>

<%
	String kinggridServerUrl = (request.getProtocol().toLowerCase().contains("https")?"https":"http")+"://"+request.getServerName()+":"+request.getServerPort()+"/iSignatureHTML5";
%>
<script>
var kinggridServerUrl = "<%=kinggridServerUrl%>";
var openKinggrid = <%=TeeSysProps.getString("WEBSIGN_OPEN")%>;
var kinggridVer = <%=TeeSysProps.getString("WEBSIGN_VER")%>;
var keySn = "<%=((TeePerson)session.getAttribute(TeeConst.LOGIN_USER)).getBkground()%>";
</script>
<%@page import="com.tianee.webframe.util.servlet.TeeCookieUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- zt_webframe框架引入 核心库 -->
<script type="text/javascript" src = '<%=request.getContextPath() %>/common/jquery-easyui-1.6.11/jquery.easyui.min.js'></script>
<script type="text/javascript" src = '<%=request.getContextPath() %>/common/zt_webframe/js/jquery.datagrid.extend.js'></script>
<script type="text/javascript" src = '<%=request.getContextPath() %>/common/jquery-easyui-1.6.11/locale/easyui-lang-zh_CN.js'></script>

<%
Cookie __cookie = TeeCookieUtils.getCookie(request, "skin_new");
String __skinNew = "1";
if(__cookie!=null){
	__skinNew = __cookie.getValue();
}
%>
<!-- zt_webframe框架引入 css样式 -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/common/jquery-easyui-1.6.11/themes/metro/easyui.css">

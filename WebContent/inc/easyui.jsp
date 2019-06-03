<%@ page language="java" pageEncoding="UTF-8"%>
<%

String contextPath1 = request.getContextPath();
String basePath1 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+contextPath1+"/";
%>
<link id="easyuiTheme" href="<%=contextPath1%>/common/easyui/themes/gray/easyui.css" rel="stylesheet" type="text/css" media="screen">
<link href="<%=contextPath1%>/common/easyui/themes/icon.css" rel="stylesheet" type="text/css" media="screen">
<script src="<%=contextPath1%>/common/jquery/jquery-min.js" charset="UTF-8" type="text/javascript"></script>
<script src="<%=contextPath1%>/common/easyui/jquery.easyui.min.js" charset="UTF-8" type="text/javascript"></script>
<script src="<%=contextPath1%>/common/easyui/locale/easyui-lang-zh_CN.js" charset="UTF-8" type="text/javascript"></script>

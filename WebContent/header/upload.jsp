<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tianee.webframe.util.global.TeeSysProps"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/common/swfupload/css/default.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/jquery.form.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/swfupload/swfupload.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/swfupload/swfupload.queue.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/swfupload/fileprogress.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/swfupload/handlers.js?v=1"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/upload.js?v=4"></script>

<script>
var UPLOAD_ATTACH_LIMIT_GLOBAL = "<%=TeeSysProps.getString("UPLOAD_ATTACH_LIMIT")%>";
var GLOBAL_ATTACH_TYPE="<%=TeeStringUtil.getString(TeeSysProps.getString("GLOBAL_ATTACH_TYPE")) %>";
</script>
<%@page import="com.tianee.oa.oaconst.TeeConst"%>
<%@page import="com.tianee.webframe.util.date.TeeDateUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tianee.oa.core.org.bean.TeePerson" %>
<%@page import="com.tianee.oa.core.org.service.TeePersonService"%>
<%@page import="java.util.Date"%>
<%@page import="com.tianee.webframe.util.date.TeeDateUtil"%>
<%@ page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@page import="com.tianee.oa.oaconst.TeeConst"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>PDF在线阅读</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header.jsp"%>
<%

TeePerson loginUser = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
int docId = TeeStringUtil.getInteger(request.getParameter("docId"),0);
%>
<script type="text/javascript">
//全局对象
var docId = <%=docId%>
function doInit(){
	if(!isIE()){
		window.location = contextPath+"/attachmentController/downFile4Pdf.action?id="+docId;
	}else{
		window.location = contextPath+"/system/core/aip/pdflookup.jsp?id="+docId;
	}
}

function isIE() { //ie?  
    if (!!window.ActiveXObject || "ActiveXObject" in window)  
        return true;  
    else  
        return false;  
} 


</script>

</head>
<body onload="doInit()">

</body>
</html>


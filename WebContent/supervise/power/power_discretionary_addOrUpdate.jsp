<%@page import="net.sf.json.JSONObject"%>
<%@page import="java.util.*"%>
<%@page import="com.tianee.oa.core.workflow.flowrun.bean.FlowRunToken"%>
<%@page import="com.tianee.webframe.util.auth.TeeFunctionControl"%>
<%TeeFunctionControl.distinguishTheVersion(request,response);%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/upload.jsp" %>

    <script type="text/javascript"
            src="<%=contextPath%>/supervise/power/js/power_discretionary_addOrUpdate.js"></script>
    <script type="text/javascript"
            src="<%=contextPath%>/supervise/common/js/common.js"></script>

<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/power/css/power.css" />

<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />

<title>职权管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body onload="doInit()" style="width: 100%; height: 100%;">
    <form id="discretionaryForm">
        <input type="hidden" id="id" name="id" value="<c:out value='${discretionary.id}'/>"/>
        <input type="hidden" id="powerId" name="powerId" value="<c:out value='${discretionary.powerId}'/>"/>
        <input type="hidden" id="createDateStr" name="createDateStr" value="<c:out value='${discretionary.createDateStr}'/>"/>
        <table class="none_Table" style="width: 100%; background: #fff;">
            <tr class="common-line-height"></tr>
            <tr>
                <td style="text-indent: 10px; width: 80px;">违法事实：</td>
                <td >
                    <textarea class="common-textarea" rows="7" id="illegalFact" name="illegalFact" style="width: 98%;" ><c:out value='${discretionary.illegalFact}'/></textarea>
                </td>
            </tr>
            <tr class="common-line-height"></tr>
            <tr>
                <td style="text-indent: 10px; width: 80px;">裁量标准：</td>
                <td >
                    <textarea class="common-textarea" rows="7" id="punishStandard" name="punishStandard" style="width: 98%;" ><c:out value='${discretionary.punishStandard}'/></textarea>
                </td>
            </tr>
        </table>
    </form>
    
</body>
</html>
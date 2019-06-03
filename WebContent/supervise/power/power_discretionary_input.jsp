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
            src="<%=contextPath%>/supervise/power/js/power_discretionary_input.js"></script>
    <script type="text/javascript"
            src="<%=contextPath%>/supervise/common/js/common.js"></script>

<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/power/css/power.css" />

<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />

<title>职权管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body onload="doInit()" style="width: 100%; height: 100%;">
    <input type="hidden" id="powerId" name="powerId" value="<c:out value='${powerId}' />"/>
    <div id="toolbar">
        <div class="fr">
            <!-- <button class="easyui-linkbutton" onclick="doCreate();"><i class="fa fa-plus"></i> 新建</button> 
            &nbsp;&nbsp;-->
        </div>
        <div class="" style="padding-top: 5px; padding-bottom: 5px">
            <!-- form表单 -->
            <form id="searchForm">
                <table class="none_table" style="width: 100%;">
                    <tr>
                        <td style="text-indent: 10px; width: 80px;">违法事实：</td>
                        <td style="width: 270px;">
                            <input class="easyui-textbox" id="illegalFact" style="width: 100%;" />
                        </td>
                        <td style="text-indent: 10px; width: 80px;">裁量标准：</td>
                        <td style="width: 270px;">
                            <input type="text" name="name" id="punishStandard" class="easyui-textbox" style="width: 100%;" />
                        </td>
                        <td style="text-align: right;">
                            <a class="easyui-linkbutton" onclick="doSearch()"><i class="fa fa-search"></i> 查询</a>
                            <a class="easyui-linkbutton" onclick="doAdd()"><i class="fa fa-plus"></i> 新增</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <table id="discretionary_datagrid" fit="true"></table>
</body>
</html>
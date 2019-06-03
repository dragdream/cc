<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="java.util.*,java.text.SimpleDateFormat"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/upload.jsp"%>

<script type="text/javascript" src="<%=contextPath%>/supervise/adminCoercion/js/measure_base_input.js"></script>

<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>


<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/power/css/power.css" />

<title>行政强制行为</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body onload="doInit()">
    <div id="measureInput_table_Div" class="easyui-panel"
        style="width: 100%; height: 95%; padding-right: 10px; padding-left: 10px; padding-top: 5px;margin-top:5px;">
        <input type="hidden" id="caseSourceId" value="<c:out value='${basicInfo.caseSourceId}'/>"/>
        <input type="hidden" id="caseSourceType" value="<c:out value='${basicInfo.caseSourceType}'/>"/>
        <input type="hidden" id="subjectId" value="<c:out value='${basicInfo.subjectId}'/>"/>
        <input type="hidden" id="departmentId" value="<c:out value='${basicInfo.departmentId}'/>"/>
        <input type="hidden" id="coercionCaseId" value="<c:out value='${basicInfo.id}'/>"/>
        <div id="toolbar" class="titlebar clearfix">
                <div id="outwarp">
            <div class="fl left">
                <img id="img1" class='title_img' src="<%=contextPath%>/common/zt_webframe/imgs/xzbg/clgl/clsqcx.png" />
                <span class="title">行政强制措施</span>
            </div>
            <div class=" fr">
                <button class="easyui-linkbutton" onclick="doOpenNewMeasurePage()">
                    <i class="fa fa-plus"></i>&nbsp;&nbsp;采取新行政强制措施
                </button>
                &nbsp;&nbsp;
            </div>
        </div>
            <span class="basic_border"></span> 
        </div>
        <table id="measureInput_datagrid" fit="true"></table>
    </div>
</body>
</html>
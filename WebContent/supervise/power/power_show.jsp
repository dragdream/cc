<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>

<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/power/css/power.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<script type="text/javascript" src="<%=contextPath%>/common/jquery/jquery.blockui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/power/js/power_show.js"></script>
<title>职权管理</title>

</head>
<body onload="doInit()">
    <div class="easyui-tabs" style="width:100%;height:100%;">
        <%-- <div title="调整信息">
            <table class="TableBlock" style="width: 100%; background: #fff;">
                <tr >
                    <td class="power-table-label">调整形式：</td>
                    <td colspan="3">${power.optTypeName}</td>
                </tr>
                <c:forEach items="${power.optArray}" var="optArray" varStatus="status">
                    <c:if test="${status.count == 1}">
                        <tr>
                            <td rowspan='${power.optCount}' class="power-table-label">原始职权：</td>
                            <c:if test="${power.optType!='10'}">
                                <td colspan='3'>
                                    <a href="javascript: void(0)" onclick="openShowFormalPower('${optArray.powerFormalId}');"><c:if test="${power.optType=='40'}">${status.count}：</c:if>${optArray.powerFormalName}</a>
                                </td>
                            </c:if>
                            <c:if test="${power.optType=='10'}"><td colspan='3'>无原始职权</td></c:if>
                        </tr>
                    </c:if>
                    <c:if test="${status.count > 1}">
                        <tr>
                            <td colspan='3'>
                                <a href="javascript: void(0)" onclick="openShowFormalPower('${optArray.powerFormalId}');"><c:if test="${power.optType=='40'}">${status.count}：</c:if>${optArray.powerFormalName}</a>
                            </td>
                        </tr>
                    </c:if>
                    
                </c:forEach>
            </table>
        </div> --%>
        <div title="基础信息">
            <input type="hidden" id="id" name="id" value='<c:out value="${power.id}"/>' />
            <input type="hidden" id="flowsheetArray" name="flowsheetArray" value='<c:out value="${power.flowsheetArray}"/>' />
            <table class="TableBlock" style="width: 100%; background: #fff;">
                <tr class="common-tr-border">
                    <td class="power-table-label">职权名称：</td>
                    <td colspan="3"><c:out value="${power.name}"/></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="power-table-label">职权类型：</td>
                    <td style="width: 350px;"><c:out value="${power.powerType}"/></td>
                    <td class="power-table-label">职权分类：</td>
                    <td style="width: 350px;"><c:out value="${power.powerDetail}"/></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="power-table-label">职权领域：</td>
                    <td style="width: 350px;"><c:out value="${power.powerMold}"/></td>
                    <td class="power-table-label">管理主体：</td>
                    <td style="width: 350px;"><c:out value="${power.subjectName}"/></td>
                </tr>
<!--                 <tr class="common-tr-border">
                    
                    <td class="power-table-label"></td>
                    <td style="width: 350px;"></td>
                </tr> -->
                
<%--                 <c:forEach items="${power.levelArray}" var="levelArray">
                    <tr class="common-tr-border">
                        <td class="power-table-label">职权层级：</td>
                        <td colspan="3">${levelArray.powerStage}</td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="power-table-label">${levelArray.powerStage}划分标准：</td>
                        <td colspan="3">${levelArray.remark}</td>
                    </tr>
                </c:forEach> --%>
            </table>
            <table id="flowsheetTable" class="TableBlock" style="width: 100%; background: #fff;">
            </table>
        </div>
        <div title="依据信息" style="padding:10px">
            <table id="gistGrid" fit="true"></table>
        </div>
        <div title="执法主体" style="padding:10px">
            <table id="subjectGrid"  fit="true"></table>
        </div>
    </div>
</body>
</html>
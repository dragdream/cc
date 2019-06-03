<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/upload.jsp"%>
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/adminCoercion/css/adminCoercion.css" />

<title>申请法院强制执行</title>
<!-- （lrn） -->
</head>
<body onload="doInit()" style="padding-top:10px">
    <div id="court_perform_press_panel" class="easyui-panel" title="催告" style="width: 100%; overflow: auto; ">
        <input type="hidden" id="caseSourceId" value="${param.caseSourceId}"/>
        <input type="hidden" id="caseSourceType" value="${param.caseSourceType}"/>
        <input type="hidden" id="subjectId" value="${param.subjectId}"/>
        <input type="hidden" id="departmentId" value="${param.departmentId}"/>
        <input type="hidden" id="coercionCaseId" value="${param.id}"/>
        <input type="hidden" id="courtPerformId" value="${param.courtPerformId}"/>
        <input type="hidden" id="caseCode" value="${param.caseCode}"/>
        <form role="form" id="court_perform_press_form" name="form1" enctype="multipart/form-data" method="post" data-options="novalidate:true">
            <table class="TableBlock" style="width: 95%; background: #fff; table-layout:fixed;">
                <tr class="common-tr-border">
                    <td class="insp-td-right0">原决定书文号：</td>
                    <td class="insp-td-left0"><input class="easyui-textbox" name="punishCodeBefore" id="punishCodeBefore"
                       <%--  value='<c:out value="${performInfo.punishCodeBefore}"/>'  --%>style="width: 100%;" /></td>
                    <td class="insp-td-right0">原决定书日期：</td>
                    <td class="insp-td-left0"><input class="easyui-datebox" name="punishDateBeforeStr" id="punishDateBeforeStr"
                        data-options="validType:'date', novalidate:true"
                        <c:if test="${performInfo.punishDateBeforeStr != 'null'}">
                         value='<c:out value="${performInfo.punishDateBeforeStr}"/>'
                        </c:if>
                        style="width: 100%;" /></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="insp-td-right0">催告书送达日期<span class="required">*</span>：</td>
                    <td class="insp-td-left0"><input style="width: 100%;" class="easyui-datebox"
                        name="pressSendDateStr" id="pressSendDateStr"
                        data-options="required:true, validType:'date', novalidate:true, missingMessage:'请选择催告书送达日期'"
                        <c:if test="${performInfo.pressSendDateStr != 'null'}">
                        value='<c:out value="${performInfo.pressSendDateStr}"/>'
                        </c:if>/></td>
                    <td class="insp-td-right0">送达方式：</td>
                    <td class="insp-td-left0">
                        <input class="easyui-textbox sendType-select"
                        name="pressSendType" id="pressSendType" 
                        value='<c:out value="${performInfo.pressSendType}"/>' style="width: 100%;" /></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="TableData" style="text-align: right; width:  200px;" nowrap>
                        &nbsp;&nbsp;<input class="easyui-checkbox isSecondPress" 
                        <c:if test="${performInfo.isSecondPress == 1}"> checked </c:if>
                        name="isSecondPress" id="isSecondPress" value="1" style="padding-right: 0px;"/>&nbsp;
                    </td>
                    <td class="insp-td-left0">二次催告</td>
                    <td></td>
                    <td></td>
                </tr>
                <tr class="secondPress-info-tr" style="display: none;">
                    <td class="insp-td-right0">二次催告书送达日期：</td>
                    <td class="insp-td-left0"><input class="easyui-datebox" name="secondPressDateStr" id="secondPressDateStr"
                        data-options="validType:'date', novalidate:true,"
                        <c:if test="${performInfo.pressSendDateStr != 'null'}">
                        value='<c:out value="${performInfo.secondPressDateStr}"/>'
                        </c:if> style="width: 100%;" /></td>

                    <td class="insp-td-right0">送达方式：</td>
                    <td class="insp-td-left0">
                    <input style="width: 100%;"
                        class="easyui-textbox sendType-select" name="secondPressType"  id="secondPressType" 
                        value='<c:out value="${performInfo.secondPressType}"/>' /></td>
                </tr>
            </table>
        </form>
    </div>
    <br />
    <div id="court_perform_apply_panel" class="easyui-panel" title="申请与批准" style="width: 100%; overflow: auto;">
        <input type="hidden" id="courtPerformId" value="<c:out value='${performInfo.id}'/>" />
        <form role="form" id="court_perform_Apply_form" name="form1" enctype="multipart/form-data" method="post" data-options="novalidate:true">
            <table class="TableBlock" style="width: 95%; background: #fff; table-layout:fixed;">
                <tr class="common-tr-border">
                    <td class="insp-td-right0" >申请法院强制执行日期：</td>
                    <td class="insp-td-left0"><input class="easyui-datebox" name="applyDateStr" id="applyDateStr"
                        data-options="validType:'date', novalidate:true,"
                        <c:if test="${performInfo.applyDateStr != 'null'}"> 
                         value='<c:out value="${performInfo.applyDateStr}"/>'
                        </c:if>  style="width: 100%;" /></td>
                    <td class="insp-td-right0" >批准日期<span class="required">*</span>：</td>
                    <td class="insp-td-left0"><input class="easyui-datebox" name="approveDateStr"
                        id="approveDateStr" data-options="required:true, validType:'date', novalidate:true, missingMessage:'请选择批准日期'"
                        <c:if test="${performInfo.approveDateStr != 'null'}">
                         value='<c:out value="${performInfo.approveDateStr}"/>'
                        </c:if> style="width: 100%;" /></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="insp-td-right0">申请法院强制执行方式：</td>
                    <td colspan="3">
                        <input class="easyui-textbox enforceType-select"
                        name="performType" id="performType"
                        value='<c:out value="${performInfo.performType}"/>' style="width: 100%;" />
                    </td>
                </tr>
                <tr class="common-tr-border" >
                    <td class="insp-td-right0">执行标的情况：</td>
                    <td colspan="3" height="80px">
                        <input class="easyui-textbox" name="enforceElementCondition" id="enforceElementCondition"
                        data-options="multiline:true" 
                        value='<c:out value="${performInfo.enforceElementCondition}"/>' style="width: 100%; height: 70px;">
                    </td>
                </tr>
            </table>
        </form>
    </div>
</body>
<script type="text/javascript" src="<%=contextPath%>/supervise/adminCoercion/coercionSearch/js/coercionSearch_seeInfo.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/adminCoercion/js/perform_court_input.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
</html>
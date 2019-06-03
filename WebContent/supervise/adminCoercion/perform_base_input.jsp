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

<script type="text/javascript" src="<%=contextPath%>/supervise/adminCoercion/js/perform_base_input.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>


<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/power/css/power.css" />

<title>行政强制行为</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body onload="performBaseInit()">
    <div class="easyui-tabs" style="width: 100%; height: 95%; padding-right: 10px; padding-left: 10px; padding-top: 5px">
        <input type="hidden" id="caseSourceId" value="<c:out value='${basicInfo.caseSourceId}'/>" /> 
        <input type="hidden" id="caseSourceType" value="<c:out value='${basicInfo.caseSourceType}'/>" />
        <input type="hidden" id="subjectId" value="<c:out value='${basicInfo.subjectId}'/>" />
        <input type="hidden" id="departmentId" value="<c:out value='${basicInfo.departmentId}'/>"/>
        <input type="hidden" id="coercionCaseId" value="<c:out value='${basicInfo.id}'/>" />
        <div title="行政机关强制执行" style="padding: 10px">
            <div id="toolbar" class="titlebar clearfix">
                    <div id="outwarp">
                <div class="fl left"></div>
                <div class=" fr">
                    <button class="easyui-linkbutton" onclick="doOpenNewPerformPage()">
                        <i class="fa fa-plus"></i>&nbsp;&nbsp;填报新行政强制执行方式
                    </button>
                    &nbsp;&nbsp;
                </div>
            </div>
                <span class="basic_border"></span>
            </div>
            <table id=performsInput_datagrid fit="true"></table>
        </div>
        <div title="申请法院强制执行" style="padding: 10px">
        	<div id="toolbar" class="titlebar clearfix">
                <div class="fl left"></div>
                <div class=" fr">
                    &nbsp;&nbsp;
                </div>
                <span class="basic_border"></span>
         	</div>
            <div id="court_perform_press_panel" class="easyui-panel" title="催告" style="width: 100%; overflow: auto;">
                <form role="form" id="court_perform_press_form" name="form1" enctype="multipart/form-data" method="post">
                    <table class="TableBlock" style="width: 100%; background: #fff;">
                        <tr>
                            <td class="power-table-label" style="width: 200px;">原决定书文号：</td>
                            <td style="width: 160px;"><input class="easyui-textbox" name="punishCodeBefore"
                                value='<c:out value="${performInfo.punishCodeBefore}"/>' style="width: 90%;" /></td>
                            <td class="power-table-label" style="width: 200px;">原决定书日期：</td>
                            <td style="width: 160px;"><input class="easyui-datebox" name="punishDateBeforeStr"
                                <c:if test="${performInfo.punishDateBeforeStr != 'null'}">
                                 value='<c:out value="${performInfo.punishDateBeforeStr}"/>'
                                </c:if>
                                style="width: 90%;" /></td>
                        </tr>
                        <tr>
                            <td class="power-table-label">催告书送达日期：</td>
                            <td style="width: 160px;"><input style="width: 90%;" class="easyui-datebox"
                                name="pressSendDateStr" 
                                <c:if test="${performInfo.pressSendDateStr != 'null'}">
                                value='<c:out value="${performInfo.pressSendDateStr}"/>'
                                </c:if>/></td>
                            <td class="power-table-label">送达方式：</td>
                            <td style="width: 160px;">
                                <input class="easyui-textbox sendType-select"
                                name="pressSendType" 
                                value='<c:out value="${performInfo.pressSendType}"/>' style="width: 90%;" /></td>
                        </tr>
                        <tr>
                            <td class="TableData" style="text-align: right; width:  200px;" nowrap>
                                &nbsp;&nbsp;<input class="easyui-checkbox isSecondPress" 
                                <c:if test="${performInfo.isSecondPress == 1}"> checked </c:if>
                                name="isSecondPress" value="1" style="padding-right: 0px;"/>&nbsp;
                            </td>
                            <td style="width: 160px;">二次催告</td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr class="secondPress-info-tr" style="display: none;">
                            <td class="power-table-label">二次催告书送达日期：</td>
                            <td style="width: 160px;"><input class="easyui-datebox" name="secondPressDateStr"
                                <c:if test="${performInfo.pressSendDateStr != 'null'}">
                                value='<c:out value="${performInfo.secondPressDateStr}"/>'
                                </c:if> style="width: 90%;" /></td>

                            <td class="power-table-label">送达方式：</td>
                            <td style="width: 160px;">
                            <input style="width: 90%;"
                                class="easyui-textbox sendType-select" name="secondPressType" 
                                value='<c:out value="${performInfo.secondPressType}"/>' /></td>
                        </tr>
                    </table>
                </form>
            </div>
            <br />
            <div id="court_perform_apply_panel" class="easyui-panel" title="申请与批准" style="width: 100%; overflow: auto;">
                <input type="hidden" id="courtPerformId" value="<c:out value='${performInfo.id}'/>" />
                <form role="form" id="court_perform_Apply_form" name="form1" enctype="multipart/form-data" method="post">
                    <table class="TableBlock" style="width: 100%; background: #fff;">
                        <tr>
                            <td class="power-table-label" style="width: 200px;">申请法院强制执行日期：</td>
                            <td style="width: 160px;"><input class="easyui-datebox" name="applyDateStr"
                                <c:if test="${performInfo.applyDateStr != 'null'}">
                                 value='<c:out value="${performInfo.applyDateStr}"/>'
                                </c:if> style="width: 90%;" /></td>
                            <td class="power-table-label" style="width: 200px;">批准日期：</td>
                            <td style="width: 160px;"><input class="easyui-datebox" name="approveDateStr"
                                <c:if test="${performInfo.approveDateStr != 'null'}">
                                 value='<c:out value="${performInfo.approveDateStr}"/>'
                                </c:if> style="width: 90%;" /></td>
                        </tr>
                        <tr>
                            <td class="power-table-label">申请法院强制执行方式：</td>
                            <td colspan="3">
                                <input class="easyui-textbox enforceType-select"
                                name="performType"
                                value='<c:out value="${performInfo.performType}"/>' style="width: 97%;" /></td>
                        </tr>
                        <tr >
                            <td class="power-table-label">执行标的情况：</td>
                            <td colspan="3">
                                <input class="easyui-textbox" name="enforceElementCondition"
                                data-options="multiline:true" 
                                value='<c:out value="${performInfo.enforceElementCondition}"/>' style="width: 97%; height: 60px;"></td>
                        </tr>
                    </table>
                    <br />
                    <div style="text-align: center; padding-top: 10px;">
                        <button type="button" class="easyui-linkbutton" title="保存" onclick="saveCourtPerform()">
                            <span style="padding-right: 2px; width: 40px;"><i class="fa fa-save"></i> 保存</span>
                        </button>
                    </div>
                </form>
            </div>
            
        </div>
    </div>
</body>
</html>
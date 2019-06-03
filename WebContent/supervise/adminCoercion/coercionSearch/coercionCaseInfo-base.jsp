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

<script type="text/javascript"
    src="<%=contextPath%>/supervise/adminCoercion/coercionSearch/js/coercionSearch_seeInfo.js"></script>

<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/pureText-lookInfo.css"/>
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<link rel="stylesheet" type="text/css" href="/supervise/caseManager/commonCase/css/caseLook.css" />
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/power/css/power.css" />
<link rel="stylesheet" type="text/css" href="/supervise/adminCoercion/css/adminCoercion.css" />

<title>行政强制行为</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body onload="doInit()">
    <div id="measureInput_table_Div"
        style="width: 100%; height: 100%; padding-right: 10px; padding-left: 10px;">
        <input type="hidden" id="coercionCaseId" value="<c:out value='${baseInfo.id}'/>" />
        <input type="hidden" id="caseSourceId" value="<c:out value='${baseInfo.caseSourceId}'/>" />
        <input type="hidden" id="pageType" value="<c:out value='${pageType}'/>" />
        <input type="hidden" id="parentPage" value="<c:out value='${parentPage}'/>" />
        <div id="coercion_manage_page" class="easyui-tabs"
            style="width: 100%; height: 90%; padding-right: 10px; padding-left: 10px; ">
            <div title="案件信息" style="padding: 10px">
                <div class="easyui-panel" title="基础信息" style="width: 100%;" align="center" id="baseDiv">
                    <table class="TableBlock_page lookInfo-lowHeight-table" style="width:100%;table-layout: fixed;word-wrap: break-word; word-break: break-all;">
                        <tr class="common-tr-border">
                            <td class="case-common-filing-look-td-class1 font-bold-label-td">案件编号：</td>
                            <td class="case-common-filing-look-td-class2" id="caseCode"></td>
                            <td class="case-common-filing-look-td-class1 font-bold-label-td">执法主体：</td>
                            <td class="case-common-filing-look-td-class2" id="subjectName"></td>
                            <td class="case-common-filing-look-td-class3"></td>
                        </tr>
                        <tr class="common-tr-border">
                            <td class="case-common-filing-look-td-class1 font-bold-label-td">案件来源：</td>
                            <td class="case-common-filing-look-td-class2" id="caseSourceValue">
                            </td>
                            <td class="case-common-filing-look-td-class1 font-bold-label-td">来源日期：</td>
                            <td class="case-common-filing-look-td-class2" id="sourceDateStr">
                            </td>
                            <td class="case-common-filing-look-td-class3"></td>
                        </tr>
                        <tr class="common-tr-border">
                            <td class="case-common-filing-look-td-class1 font-bold-label-td">立案批准日期：</td>
                            <td class="case-common-filing-look-td-class2" id="filingDateStr">
                            </td>
                            <td class="case-common-filing-look-td-class1 font-bold-label-td"></td>
                            <td class="case-common-filing-look-td-class2"></td>
                            <td class="case-common-filing-look-td-class3"></td>
                        </tr>
                        <tr class="common-tr-border">
                            <td class="case-common-filing-look-td-class1 font-bold-label-td">案由（事由）：</td>
                            <td colspan="3" id="name">
                            </td>
                            <td class="case-common-filing-look-td-class3"></td>
                        </tr>
                        <tr class="common-tr-border">
                            <td class="case-common-filing-look-td-class1 font-bold-label-td">案情介绍：</td>
                            <td colspan="3" id="caseIntroduce">
                            </td>
                            <td class="case-common-filing-look-td-class3"></td>
                        </tr>
                        <tr class="common-tr-border">
                            <td class="case-common-filing-look-td-class1 font-bold-label-td">立案呈批表：</td>
                            <td class="" colspan="3" id="filingApprovalDocumentName"></td>
                            <td class="case-common-filing-look-td-class3"></td>
                        </tr>
                    </table>
                </div>
                <br />
                <%-- 立案-当事人 --%>
                <div class="easyui-panel" title="当事人" style="width: 100%;" align="center">
                    <table class="TableBlock_page lookInfo-lowHeight-table" style="width: 100%;">
                        <tr class="common-tr-border">
                            <td class="case-common-filing-look-td-class1 font-bold-label-td">当事人类型：</td>
                            <td class="case-common-filing-look-td-class2" id="partyTypeValue">
                            </td>
                            <td class="case-common-filing-look-td-class1 font-bold-label-td">当事人名称：</td>
                            <td class="case-common-filing-look-td-class2" id="partyName">
                            </td>
                            <td class="case-common-filing-look-td-class3"></td>
                        </tr>
                        <tr class="common-tr-border">
                            <td class="case-common-filing-look-td-class1 font-bold-label-td">证件类型：</td>
                            <td class="case-common-filing-look-td-class2" id="cardTypeValue"></td>
                            <td class="case-common-filing-look-td-class1 font-bold-label-td">证件号码：</td>
                            <td class="case-common-filing-look-td-class2" id="cardCode">
                            </td>
                            <td class="case-common-filing-look-td-class3"></td>
                        </tr>
                        <tr class="common-tr-border">
                            <td class="case-common-filing-look-td-class1 font-bold-label-td">联系电话：</td>
                            <td class="case-common-filing-look-td-class2" id="contactPhone"></td>
                            <td class="case-common-filing-look-td-class1 font-bold-label-td">住所（住址）：</td>
                            <td class="case-common-filing-look-td-class2" id="address"></td>
                            <td class="case-common-filing-look-td-class3"></td>
                        </tr>
                        <tr class="common-tr-border" id="adressTr" style="display: none;">
                            <td class="case-common-filing-look-td-class1 font-bold-label-td">注册地址：</td>
                            <td class="case-common-filing-look-td-class2" id="registerAddress"></td>
                            <td class="case-common-filing-look-td-class1 font-bold-label-td">经营地址：</td>
                            <td class="case-common-filing-look-td-class2" id="businessAddress"></td>
                            <td class="case-common-filing-look-td-class3"></td>
                        </tr>
                    </table>
                </div>
                <br/>
                <%-- 立案-执法人员 --%>
                <div class="easyui-panel" title="执法人员" style="width: 100%; height: 220px;">
                    <table fit="true" style="width: 99%;" id="common_case_add_person_datagrid"></table>
                </div>
            </div>
            <div title="强制措施" style="padding: 10px;" id="coercion_manage_page">
                <div fit="true" class="easyui-panel" style="width: 100%; height: 220px" align="center">
                    <table fit="true" style="width: 100%;" id="measureSee_datagrid">
                    </table>
                </div>
            </div>
            <div title="行政机关执行" style="padding: 10px;" id="coercion_manage_page">
                <div fit="true" class="easyui-panel" style="width: 100%; height: 220px" align="center">
                    <table fit="true" style="width: 100%;" id="self_performSee_datagrid">
                    </table>
                </div>
            </div>
            <div title="申请法院强制执行" style="padding: 10px;" id="coercion_manage_page">
                    <%-- <div class="easyui-panel" title="催告" style="width: 95%; height: auto;" align="center">
                        <table class="TableBlock" style="width: 95%; background: #fff;">
                            <tr class="none-border-tr">
                                <td class="seeInfo-perform-td-label">原决定书文号:</td>
                                <td class="perform-td-content-md"><c:out value="${performInfo.punishCodeBefore}" /></td>
                                <td class="seeInfo-perform-td-label">原决定书日期:</td>
                                <td class="perform-td-content-md"><c:if
                                        test="${performInfo.punishDateBeforeStr != null && performInfo.punishDateBeforeStr != 'null'}">
                                        <c:out value="${performInfo.punishDateBeforeStr}" />
                                    </c:if></td>
                            </tr>
                            <tr class="none-border-tr">
                                <td class="seeInfo-perform-td-label">催告书送达日期:</td>
                                <td class="perform-td-content-md"><c:if
                                        test="${performInfo.pressSendDateStr != null && performInfo.pressSendDateStr != 'null'}">
                                        <c:out value="${performInfo.pressSendDateStr}" />
                                    </c:if></td>
                                <td class="seeInfo-perform-td-label">送达方式:</td>
                                <td class="perform-td-content-md"><c:out value="${performInfo.pressSendType}" /></td>
                            </tr>
                            <tr class="none-border-tr"
                                <c:if test="${performInfo.isSecondPress != 1}"> style="display: none;" </c:if>>
                                <td class="seeInfo-perform-td-label">二次催告书送达日期:</td>
                                <td class="perform-td-content-md"><c:if
                                        test="${performInfo.secondPressDateStr != null && performInfo.secondPressDateStr != 'null'}">
                                        <c:out value="${performInfo.secondPressDateStr}" />
                                    </c:if></td>
                                <td class="seeInfo-perform-td-label">送达方式:</td>
                                <td class="perform-td-content-md"><c:out value="${performInfo.secondPressType}" /></td>
                            </tr>
                        </table>
                    </div>
                    <br />
                    <div class="easyui-panel" title="申请法院强制执行" style="width: 95%; height: auto;" align="center">
                        <table class="TableBlock" style="width: 95%; background: #fff;">
                            <tr class="none-border-tr">
                                <td class="seeInfo-perform-td-label">申请法院强制执行日期:</td>
                                <td class="perform-td-content-md"><c:if
                                        test="${performInfo.applyDateStr != null && performInfo.applyDateStr != 'null'}">
                                        <c:out value="${performInfo.applyDateStr}" />
                                    </c:if></td>
                                <td class="seeInfo-perform-td-label">批准日期:</td>
                                <td class="perform-td-content-md"><c:if
                                        test="${performInfo.approveDateStr != null && performInfo.approveDateStr != 'null'}">
                                        <c:out value="${performInfo.approveDateStr}" />
                                    </c:if></td>
                            </tr>
                            <tr class="none-border-tr">
                                <td class="seeInfo-perform-td-label">申请法院强制执行方式:</td>
                                <td colspan="3" class="perform-td-content-md"><c:out
                                        value="${performInfo.performType}" /></td>
                            </tr>
                            <tr class="none-border-tr">
                                <td class="seeInfo-perform-td-label">执行标的情况:</td>
                                <td colspan="3" class="perform-td-content-md"><c:out
                                        value="${performInfo.enforceElementCondition}" /></td>
                            </tr>
                        </table>
                    </div> --%>
                    
                <div id="court_perform_press_panel" class="easyui-panel" title="催告" style="width: 100%; overflow: auto; ">
        <form role="form" id="court_perform_press_form" name="form1" enctype="multipart/form-data" method="post" data-options="novalidate:true">
            <table class="TableBlock lookInfo-lowHeight-table" style="width: 95%; background: #fff; table-layout:fixed;">
                <tr class="common-tr-border">
                    <td class="insp-td-right0 font-bold-label-td">原决定书文号：</td>
                    <td class="insp-td-left0" id="punishCodeBefore"></td>
                    <td class="insp-td-right0 font-bold-label-td">原决定书日期：</td>
                    <td class="insp-td-left0" id="punishDateBeforeStr"></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="insp-td-right0 font-bold-label-td">催告书送达日期：</td>
                    <td class="insp-td-left0" id="pressSendDateStr"></td>
                    <td class="insp-td-right0 font-bold-label-td">送达方式：</td>
                    <td class="insp-td-left0" id="pressSendType" ></td>
                </tr>
                <!-- <tr class="common-tr-border">
                    <td class="TableData" style="text-align: right; width:  200px;" nowrap>
                        &nbsp;&nbsp;<input class="easyui-checkbox isSecondPress" disabled="disabled"
                        name="isSecondPress" id="isSecondPress" value="1" style="padding-right: 0px;"/>&nbsp;
                    </td>
                    <td class="insp-td-left0">二次催告</td>
                    <td></td>
                    <td></td>
                </tr> -->
                <tr class="secondPress-info-tr font-bold-label-td" style="display: none;">
                    <td class="insp-td-right0 font-bold-label-td">二次催告书送达日期：</td>
                    <td class="insp-td-left0" id="secondPressDateStr" ></td>

                    <td class="insp-td-right0 font-bold-label-td">送达方式：</td>
                    <td class="insp-td-left0" id="secondPressType" ></td>
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
                    <td class="insp-td-right0 font-bold-label-td" >申请法院强制执行日期：</td>
                    <td class="insp-td-left0" id="applyDateStr" ></td>
                    <td class="insp-td-right0 font-bold-label-td" >批准日期：</td>
                    <td class="insp-td-left0" id="approveDateStr" /></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="insp-td-right0 font-bold-label-td">申请法院强制执行方式：</td>
                    <td colspan="3" id="performType" > </td>
                </tr>
                <tr class="common-tr-border" >
                    <td class="insp-td-right0 font-bold-label-td">执行标的情况：</td>
                    <td colspan="3" id="enforceElementCondition" >
                    </td>
                </tr>
            </table>
        </form>
    </div>
            </div>
            
        </div>
        <div id="simpleBtn" align="center" class="iframeBtns clearfix" style="border-bottom-right-radius: 4px;border-bottom-left-radius: 4px;text-align: center;width:100%;padding: 10px 0;background-color:#fff;float: right;">
            <button class="easyui-linkbutton" title="返回" onclick="back();">
                <span style="padding-right: 2px; width: 40px;"><i class="fa fa-reply"></i> 返回</span>
            </button>
        </div>
    </div>
</body>
</html>
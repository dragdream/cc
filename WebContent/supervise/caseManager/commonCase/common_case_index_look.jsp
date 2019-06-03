<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<link rel="stylesheet" type="text/css" href="/supervise/caseManager/commonCase/css/caseLook.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/pureText-lookInfo.css" />
<title>一般案件查看</title>
</head>
<body onload="doIndexLookInit()" style="padding-right: 10px; padding-left: 10px; padding-top: 5px;">
    <c:if test="${param.editFlag != 4 }">
        <div id="common_case_index_look_div" class="easyui-tabs" style="width:100%; height:91%; padding-top: 5px; padding-bottom: 5px;">
        </c:if>
        <c:if test="${param.editFlag eq 4 }">
        <div id="common_case_index_look_div" class="easyui-tabs" style="width:100%; height:100%; padding-top: 5px; padding-bottom: 5px;">
        </c:if>
    
        <input type="hidden" id="caseId" value="${param.caseId}"/>
        <div title="立案" style="padding:10px;">
            <%-- 立案-基础信息 --%>
            <div class="easyui-panel" title="基础信息" style="width: 100%;" align="center" id="baseDiv">
                <table class="TableBlock_page lookInfo-lowHeight-table" style="width:100%;table-layout: fixed;word-wrap: break-word; word-break: break-all;">
                    <tr class="common-tr-border">
                        <td class="case-common-filing-look-td-class1 font-bold-label-td">案件编号：</td>
                        <td class="case-common-filing-look-td-class2" name="caseCode" id="caseCode"></td>
                        <td class="case-common-filing-look-td-class1 font-bold-label-td">执法主体：</td>
                        <td class="case-common-filing-look-td-class2" name="subjectName" id="subjectName"></td>
                        <td class="case-common-filing-look-td-class3"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-filing-look-td-class1 font-bold-label-td">案件来源：</td>
                        <td class="case-common-filing-look-td-class2" name="caseSourceValue" id="caseSourceValue">
                        </td>
                        <td class="case-common-filing-look-td-class1 font-bold-label-td">来源日期：</td>
                        <td class="case-common-filing-look-td-class2"  name="sourceDateStr" id="sourceDateStr">
                        </td>
                        <td class="case-common-filing-look-td-class3"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-filing-look-td-class1 font-bold-label-td">立案批准日期：</td>
                        <td class="case-common-filing-look-td-class2" name="filingDateStr" id="filingDateStr">
                        </td>
                        <td class="case-common-filing-look-td-class1 font-bold-label-td"></td>
                        <td class="case-common-filing-look-td-class2"></td>
                        <td class="case-common-filing-look-td-class3"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-filing-look-td-class1 font-bold-label-td">案由（事由）：</td>
                        <td colspan="3" name="name" id="name">
                        </td>
                        <td class="case-common-filing-look-td-class3"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-filing-look-td-class1 font-bold-label-td">案情介绍：</td>
                        <td colspan="3" name="caseIntroduce" id="caseIntroduce">
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
                        <td class="case-common-filing-look-td-class2" name="partyTypeValue" id="partyTypeValue">
                        </td>
                        <td class="case-common-filing-look-td-class1 font-bold-label-td">当事人名称：</td>
                        <td class="case-common-filing-look-td-class2" name="partyName" id="partyName">
                        </td>
                        <td class="case-common-filing-look-td-class3"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-filing-look-td-class1 font-bold-label-td">证件类型：</td>
                        <td class="case-common-filing-look-td-class2" name="cardTypeValue" id="cardTypeValue"></td>
                        <td class="case-common-filing-look-td-class1 font-bold-label-td">证件号码：</td>
                        <td class="case-common-filing-look-td-class2" name="cardCode" id="cardCode">
                        </td>
                        <td class="case-common-filing-look-td-class3"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-filing-look-td-class1 font-bold-label-td">联系电话：</td>
                        <td class="case-common-filing-look-td-class2" name="contactPhone" id="contactPhone"></td>
                        <td class="case-common-filing-look-td-class1 font-bold-label-td">住所（住址）：</td>
                        <td class="case-common-filing-look-td-class2" name="address" id="address"></td>
                        <td class="case-common-filing-look-td-class3"></td>
                    </tr>
                    <tr class="common-tr-border" id="adressTr" style="display: none;">
                        <td class="case-common-filing-look-td-class1 font-bold-label-td">注册地址：</td>
                        <td class="case-common-filing-look-td-class2" name="registerAddress" id="registerAddress"></td>
                        <td class="case-common-filing-look-td-class1 font-bold-label-td">经营地址：</td>
                        <td class="case-common-filing-look-td-class2" name="businessAddress" id="businessAddress"></td>
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
        <div title="调查取证" style="padding:10px;">
            <%-- 调查取证-是否强制措施 --%>
            <div class="easyui-panel case-common-panel-body tabs-header" style="width: 99%; border-style: none;" align="center">
                <table class="TableBlock_page lookInfo-lowHeight-table" fit="true" style="width: 100%;">
                    <tr class="common-tr-border">
                        <td class="case-common-research-look-td-class2 font-bold-label-td">是否采取强制措施：</td>
                        <td class="case-common-research-look-td-class6" id="isForce"></td>
                        <td class="case-common-research-look-td-class7" colspan="3"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-research-look-td-class2 font-bold-label-td">调查终结日期：</td>
                        <td class="case-common-research-look-td-class6" id="surveyEndDateStr"></td>
                        <td class="case-common-research-look-td-class7" colspan="3"></td>
                    </tr>
                </table>
            </div>
            <%-- 调查取证-调查信息 --%>
            <div class="easyui-panel" title="调查信息" align="center" style="width: 100%; height:220px;">
                <table fit="true" style="width: 100%;" id="survey_information"></table>
            </div>
            <br />
            <%-- 调查取证-证据信息 --%>
            <div class="easyui-panel" title="证据信息" align="center" style="width: 100%; height:220px;">
                <table fit="true" style="width: 100%;" id="illegal_information"></table>
            </div>
            <br />
            <%-- 调查取证-事先告知信息 --%>
            <div class="easyui-panel case-common-panel-body" title="事先告知信息" style="width: 100%;" align="center">
                <table class="TableBlock_page lookInfo-lowHeight-table" fit="true" style="width: 100%;">
                    <tr class="common-tr-border">
                        <td class="case-common-research-look-td-class2 font-bold-label-td">告知日期：</td>
                        <td class="case-common-research-look-td-class4" name="punishHearingSendDateStr" id="punishHearingSendDateStr" ></td>
                        <td class="case-common-research-look-td-class2 font-bold-label-td">告知方式：</td>
                        <td class="case-common-research-look-td-class4" name="punishHearingSendWayValue" id="punishHearingSendWayValue"></td>
                        <td class="case-common-research-look-td-class7" colspan="1"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-research-look-td-class2 font-bold-label-td">告知处罚种类：</td>
                        <td class="case-common-research-look-td-class6" name="informingPunishTypeValue" id="informingPunishTypeValue" colspan="3"></td>
                        <td class="case-common-research-look-td-class7"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-research-look-td-class2 font-bold-label-td">权利告知：</td>
                        <td class="case-common-research-look-td-class6" name="rightInformValue" id="rightInformValue" colspan="3"></td>
                        <td class="case-common-research-look-td-class7"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-research-look-td-class2 font-bold-label-td">事先告知书：</td>
                        <td class="TableData" colspan="3" id="informingbookDocumentPath">
                        </td>
                        <td class="case-common-research-look-td-class7"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-research-look-td-class2 font-bold-label-td">送达日期：</td>
                        <td class="case-common-research-look-td-class4" name="informingbookDeliveryDateStr" id="informingbookDeliveryDateStr"></td>
                        <td class="case-common-research-look-td-class2 font-bold-label-td">送达方式：</td>
                        <td class="case-common-research-look-td-class4" name="informingbookWayValue" id="informingbookWayValue"></td>
                        <td class="case-common-research-look-td-class7"></td>
                    </tr>
                    <tr class="common-tr-border" id="isOrderCorrect_tr">
                        <td class="case-common-research-look-td-class2 font-bold-label-td">送达回证：</td>
                        <td class="TableData" colspan="3" id="backHoldDocumentPath"></td>
                        <td class="case-common-research-look-td-class7"></td>
                    </tr>
                </table>
                <br />
            </div>
        </div>
        <div title="审查决定" style="padding:10px;">
            <%-- 审查决定-审查信息 --%>
            <div class="easyui-panel" title="审查信息" style="width: 100%;" align="center">
                <table class="TableBlock_page lookInfo-lowHeight-table" fit="true" style="width: 100%;table-layout: fixed;">
                    <tbody>
                        <tr class="common-tr-border">
                            <td class="case-common-correct-look-td-class1 font-bold-label-td">是否涉刑案件：</td>
                            <td class="case-common-correct-look-td-class2" id="isCriminal"></td>
                            <td class="case-common-correct-look-td-class3"></td>
                            <td class="case-common-correct-look-td-class4"></td>
                            <td class="case-common-correct-look-td-class6"></td>
                            <td class="case-common-correct-look-td-class7"></td>
                            <td class="case-common-correct-look-td-class5"></td>
                        </tr>
                        <tr class="common-tr-border">
                            <td class="case-common-correct-look-td-class1 font-bold-label-td">是否重大案件：</td>
                            <td class="case-common-correct-look-td-class2" id="isMajorCase"></td>
                            <td class="case-common-correct-look-td-class3"></td>
                            <td class="case-common-correct-look-td-class4"></td>
                            <td class="case-common-correct-look-td-class6"></td>
                            <td class="case-common-correct-look-td-class7"></td>
                            <td class="case-common-correct-look-td-class5"></td>
                        </tr>
                        <tr class="common-tr-border" id="isLegalReviewTr">
                            <td class="case-common-correct-look-td-class1 font-bold-label-td">是否法制审核：</td>
                            <td class="case-common-correct-look-td-class2" id="isLegalReview"></td>
                            <td class="case-common-correct-look-td-class3 font-bold-label-td" >
                                <label class="case-handle-date" style="display: none;">法制审核日期：</label>
                            </td>
                            <td class="case-common-correct-look-td-class4">
                                <div class="case-handle-date" style="display: none;" name="legalReviewDateStr" id="legalReviewDateStr">
                                </div>
                            </td>
                            <td class="case-common-correct-look-td-class6 font-bold-label-td case-handle-date">
                                <label class="case-handle-date" style="display: none;">法制审核意见：</label>
                            </td>
                            <td class="case-common-correct-look-td-class7">
                                <div class="case-handle-date" style="display: none;" name="legalExaminaSuggest" id="legalExaminaSuggest">
                                </div>
                            </td>
                            <td class="case-common-correct-look-td-class5"></td>
                        </tr>
                        <tr class="common-tr-border" id="isCollectiveDiscussionTr">
                            <td class="case-common-correct-look-td-class1 font-bold-label-td">是否集体讨论：</td>
                            <td class="case-common-correct-look-td-class2" id="isCollectiveDiscussion"></td>
                            <td class="case-common-correct-look-td-class3 font-bold-label-td">
                                <label class="case-handle-date" style="display: none;">集体讨论日期：</label>
                            </td>
                            <td class="case-common-correct-look-td-class4">
                                <div class="case-handle-date" style="display: none;" name="collectiveDiscussionDateStr" id="collectiveDiscussionDateStr" >
                                </div>
                            </td>
                            <td class="case-common-correct-look-td-class6 font-bold-label-td">
                                <label class="case-handle-date" style="display: none;">集体讨论结论：</label>
                            </td>
                            <td class="case-common-correct-look-td-class7">
                                <div class="case-handle-date" style="display: none;" name="collectiveDiscussionResult" id="collectiveDiscussionResult" >
                                </div>
                            </td>
                            <td class="case-common-correct-look-td-class5"></td>
                        </tr>
                        <tr class="common-tr-border">
                            <td class="case-common-correct-look-td-class1 font-bold-label-td">是否听证：</td>
                            <td class="case-common-correct-look-td-class2" id="isHearing"></td>
                            <td class="case-common-correct-look-td-class3"></td>
                            <td class="case-common-correct-look-td-class4"></td>
                            <td class="case-common-correct-look-td-class6"></td>
                            <td class="case-common-correct-look-td-class7"></td>
                            <td class="case-common-correct-look-td-class5"></td>
                        </tr>
                    </tbody>
                    <tbody id="isHearingTr" style="display: none;">
                        <tr class="common-tr-border">
                            <td class="case-common-correct-look-td-class1 font-bold-label-td">申请听证日期：</td>
                            <td class="case-common-correct-look-td-class2" name="hearingApplyDateStr" id="hearingApplyDateStr">
                            </td>
                            <td class="case-common-correct-look-td-class3 font-bold-label-td">听证通知送达日期：</td>
                            <td class="case-common-correct-look-td-class4" name="hearingInformDateStr" id="hearingInformDateStr" >
                            </td>
                            <td class="case-common-correct-look-td-class6 font-bold-label-td">听证公告发布日期：</td>
                            <td class="case-common-correct-look-td-class7" name="hearingNoticeDateStr" id="hearingNoticeDateStr" >
                            </td>
                            <td class="case-common-correct-look-td-class5"></td>
                        </tr>
                        <tr class="common-tr-border">
                            <td class="case-common-correct-look-td-class1 font-bold-label-td">听证举行日期：</td>
                            <td class="case-common-correct-look-td-class2" name="hearingHoldDateStr" id="hearingHoldDateStr" >
                            </td>
                            <td class="case-common-correct-look-td-class3 font-bold-label-td">听证方式：</td>
                            <td class="case-common-correct-look-td-class4" name="hearingWayValue" id="hearingWayValue" >
                            </td>
                            <td class="case-common-correct-look-td-class6 font-bold-label-td">听证结论：</td>
                            <td class="case-common-correct-look-td-class7" name="hearingConclusionValue" id="hearingConclusionValue" >
                            </td>
                            <td class="case-common-correct-look-td-class5"></td>
                        </tr>
                        <tr class="common-tr-border">
                            <td class="case-common-correct-look-td-class1 font-bold-label-td">主持人：</td>
                            <td class="case-common-correct-look-td-class2" name="hearingHost" id="hearingHost" >
                            </td>
                            <td class="case-common-correct-look-td-class3 font-bold-label-td">听证人：</td>
                            <td class="" colspan="3" name="hearingPerson" id="hearingPerson" >
                            </td>
                            <td class="case-common-correct-look-td-class5"></td>
                        </tr>
                        <tr class="common-tr-border">
                            <td class="case-common-correct-look-td-class1 font-bold-label-td">记录人：</td>
                            <td class="case-common-correct-look-td-class2" name="hearingRecorder" id="hearingRecorder" >
                            </td>
                            <td class="case-common-correct-look-td-class3 font-bold-label-td">参加人：</td>
                            <td class="" colspan="3" name="hearingParticipants" id="hearingParticipants" >
                            </td>
                            <td class="case-common-correct-look-td-class5"></td>
                        </tr>
                        <tr class="common-tr-border">
                            <td class="case-common-correct-look-td-class1 font-bold-label-td">听证地点：</td>
                            <td class="" colspan="5" name="hearingAddress" id="hearingAddress" >
                            </td>
                            <td class="case-common-correct-look-td-class5"></td>
                        </tr>
                        <tr class="common-tr-border">
                            <td class="case-common-correct-look-td-class1 font-bold-label-td">听证结果/报告：</td>
                            <td class="" colspan="5" name="hearingResult" id="hearingResult" >
                            </td>
                            <td class="case-common-correct-look-td-class5"></td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <br />
            <%-- 审查决定-决定信息 --%>
            <div class="easyui-panel" title="决定信息" style="width: 100%;" align="center">
                <table class="TableBlock_page lookInfo-lowHeight-table" fit="true" style="width: 100%;table-layout: fixed;">
                    <tr class="common-tr-border">
                        <td class="case-common-correct-look-td-class8 font-bold-label-td">处罚决定：</td>
                        <td class="case-common-correct-look-td-class9" id="isPunishment"></td>
                        <td class="case-common-correct-look-td-class10"></td>
                        <td class="case-common-correct-look-td-class11"></td>
                        <td class="case-common-correct-look-td-class12"></td>
                        <td class="case-common-correct-look-td-class13"></td>
                        <td class="case-common-correct-look-td-class14"></td>
                        <td class="case-common-correct-look-td-class15"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-correct-look-td-class8 font-bold-label-td">决定日期：</td>
                        <td class="case-common-correct-look-td-class9" name="punishmentDateStr" id="punishmentDateStr">
                        </td>
                        <td class="case-common-correct-look-td-class10"></td>
                        <td class="case-common-correct-look-td-class11 font-bold-label-td">决定书文号：</td>
                        <td class="case-common-correct-look-td-class12" name="punishmentCode" id="punishmentCode">
                        </td>
                        <td class="case-common-correct-look-td-class13"></td>
                        <td class="case-common-correct-look-td-class14"></td>
                        <td class="case-common-correct-look-td-class15"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-correct-look-td-class8 font-bold-label-td">决定书：</td>
                        <td colspan="6" class="" id="punishmentDocumentName"></td>
                        <td class="case-common-correct-look-td-class15"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-correct-look-td-class8 font-bold-label-td">决定书送达日期：</td>
                        <td class="case-common-correct-look-td-class9" name="punishmentSendDateStr" id="punishmentSendDateStr">
                        </td>
                        <td class="case-common-correct-look-td-class10"></td>
                        <td class="case-common-correct-look-td-class11 font-bold-label-td">决定书送达方式：</td>
                        <td class="case-common-correct-look-td-class12" name="punishmentSendWayValue" id="punishmentSendWayValue">
                        </td>
                        <td class="case-common-correct-look-td-class13"></td>
                        <td class="case-common-correct-look-td-class14"></td>
                        <td class="case-common-correct-look-td-class15"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-correct-look-td-class8 font-bold-label-td">送达回证：</td>
                        <td colspan="6" class="" id="sendProofDocumentName"></td>
                        <td class="case-common-correct-look-td-class15"></td>
                    </tr>
                    <tbody id="common_case_is_punishment_div" style="display: none;">
                        <tr class="common-tr-border" id="">
                            <td class="case-common-correct-look-td-class8 font-bold-label-td">处罚决定种类：</td>
                            <td class="" colspan="6" style="padding-top: 0px!important;">
                                <table style="width:100%;border-spacing: 0;">
                                    <%-- 警告 --%>
                                    <tr class="common-tr-border" id="isWarn_tr">
                                        <td class="case-common-correct-look-td-class16">警告</td>
                                        <td class="case-common-correct-look-td-class17"></td>
                                        <td class="case-common-correct-look-td-class18"></td>
                                        <td class="case-common-correct-look-td-class19"></td>
                                        <td class="case-common-correct-look-td-class20"></td>
                                        <td class="case-common-correct-look-td-class21"></td>
                                    </tr>
                                    <%-- 罚款 --%>
                                    <tr class="common-tr-border" id="isFine_tr">
                                        <td class="case-common-correct-look-td-class16">罚款</td>
                                        <td class="case-common-correct-look-td-class17">
                                            <span class="text-input-punish" style="" name="fineSum" id="fineSum">
                                            </span> 元
                                        </td>
                                        <td class="case-common-correct-look-td-class18"></td>
                                        <td class="case-common-correct-look-td-class19"></td>
                                        <td class="case-common-correct-look-td-class20"></td>
                                        <td class="case-common-correct-look-td-class21"></td>
                                    </tr>
                                    <%-- 没收违法所得、没收非法财物 --%>
                                    <tr class="common-tr-border" id="isConfiscate_tr">
                                        <td class="case-common-correct-look-td-class16">没收违法所得、没收非法财物</td>
                                        <td class="case-common-correct-look-td-class17" id="confiscateMoneyTd">违法所得：
                                            <span class="text-input-punish" style="" name="confiscateMoney" id="confiscateMoney">
                                            </span> 元
                                        </td>
                                        <td class="case-common-correct-look-td-class18" id="confiscateProMonTd">非法财物：
                                            <span class="text-input-punish" style="" name="confiscateProMon" id="confiscateProMon">
                                            </span> 元
                                        </td>
                                        <td class="case-common-correct-look-td-class19"></td>
                                        <td class="case-common-correct-look-td-class20"></td>
                                        <td class="case-common-correct-look-td-class21"></td>
                                    </tr>
                                    <%-- 责令停产停业 --%>
                                    <tr class="common-tr-border" id="isOrderClosure_tr">
                                        <td class="case-common-correct-look-td-class16">责令停产停业</td>
                                        <td class="case-common-correct-look-td-class17"></td>
                                        <td class="case-common-correct-look-td-class18"></td>
                                        <td class="case-common-correct-look-td-class19"></td>
                                        <td class="case-common-correct-look-td-class20"></td>
                                        <td class="case-common-correct-look-td-class21"></td>
                                    </tr>
                                    <%-- 暂扣或者吊销许可证、暂扣或者吊销执照 --%>
                                    <tr class="common-tr-border" id="isTdLicense_tr">
                                        <td class="case-common-correct-look-td-class16">暂扣或者吊销许可证、暂扣或者吊销执照</td>
                                        <td class="case-common-correct-look-td-class17">
                                            <div class="text-input-punish-choose" id="isTdOrRevokeLicenseTd" style="">
                                            </div>
                                        </td>
                                        <td class="case-common-correct-look-td-class18 font-bold-label-td">
                                            <label class="text-input-punish" style="">暂扣期限：</label>
                                        </td>
                                        <td class="case-common-correct-look-td-class19">
                                            <span class="text-input-punish" style="" name="withholdingStartdateStr" id="withholdingStartdateStr" >
                                            </span>
                                        </td>
                                        <td class="case-common-correct-look-td-class20 font-bold-label-td">
                                            <label class="text-input-punish" style="">至：</label>
                                        </td>
                                        <td class="case-common-correct-look-td-class21">
                                            <span class="text-input-punish" style="" name="withholdingEnddateStr" id="withholdingEnddateStr">
                                            </span>
                                        </td>
                                    </tr>
                                    <%-- 拘留 --%>
                                    <tr class="common-tr-border" id="isDetain_tr">
                                        <td class="case-common-correct-look-td-class16">拘留</td>
                                        <td class="case-common-correct-look-td-class17">
                                            <span class="text-input-punish" style="" name="detainDays" id="detainDays">
                                            </span> 天
                                        </td>
                                        <td class="case-common-correct-look-td-class18 font-bold-label-td">
                                            <label class="text-input-punish" style="">拘留期限：</label>
                                        </td>
                                        <td class="case-common-correct-look-td-class19">
                                            <div class="text-input-punish" style="" name="detainStartdateStr" id="detainStartdateStr" >
                                            </div>
                                        </td>
                                        <td class="case-common-correct-look-td-class20 font-bold-label-td">
                                            <label class="text-input-punish" style="">至：</label>
                                        </td>
                                        <td class="case-common-correct-look-td-class21">
                                            <div class="text-input-punish" style="" name="detainEnddateStr" id="detainEnddateStr" >
                                            </div>
                                        </td>
                                    </tr>
                                    <%-- 其他 --%>
                                    <tr class="common-tr-border" id="isOther_tr">
                                        <td class="case-common-correct-look-td-class16" >其他</td>
                                        <td class="" colspan="5">
                                            <div class="text-input-punish" style="" name="otherDetailContent" id="otherDetailContent">
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                            <td class="case-common-correct-look-td-class15"></td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <%-- 审查决定-违法行为及依据 --%>
            <div id="common_case_is_punishment_punish_div" style="display: none;">
                <br/>
                <div id="common_case_illegal_hand_datagrid_div" class="easyui-panel" title="违法行为" style="width: 100%; height:220px" align="center">
                    <table fit="true" style="width: 99%;" id="common_case_illegal_hand_datagrid"></table>
                </div>
                <br />
                <div id="common_case_illegal_gist_datagrid_div" class="easyui-panel" title="违法依据" style="width: 100%; height:220px" align="center">
                    <table fit="true" style="width: 99%;" id="common_case_illegal_gist_datagrid"></table>
                </div>
                <br/>
                <div id="common_case_punish_datagrid_div" class="easyui-panel" title="处罚依据" style="width: 100%; height:220px;" align="center">
                    <table fit="true" style="width: 99%;" align="center" id="common_case_punish_datagrid"></table>
                </div>
            </div>
            <%-- 审查决定-撤销立案 --%>
            <div id="common_case_revoke_div" style="display: none;">
                <br/>
                <div class="easyui-panel case-common-panel-body" title="撤销立案" style="width: 100%;" align="center">
                    <table class="TableBlock_page lookInfo-lowHeight-table" fit="true" style="width: 99%;" align="center" >
                        <tr class="common-tr-border" id="isCollectiveDiscussion_td">
                            <td class="case-common-correct-look-td-class1 font-bold-label-td">撤销立案批准人：</td>
                            <td class="case-common-correct-look-td-class2">
                                <span id="revoke_approvePerson_span"></span>
                            </td>
                            <td class="case-common-correct-look-td-class3 font-bold-label-td">撤销立案批准日期： </td>
                            <td class="case-common-correct-look-td-class4">
                                <span id="revoke_approveDateStr_span"></span>
                            </td>
                            <td class="case-common-correct-look-td-class6 font-bold-label-td">撤销立案日期：</td>
                            <td class="case-common-correct-look-td-class7">
                                <span id="revoke_revokeRegisterDateStr_span"></span>
                            </td>
                            <td class="case-common-correct-look-td-class5"></td>
                        </tr>
                        <tr class="common-tr-border">
                            <td class="case-common-correct-look-td-class1 font-bold-label-td">撤销立案原因：</td>
                            <td class="" colspan="5">
                                <span id="revoke_revokeRegisterReason_span"></span>
                            </td>
                            <td class="case-common-td-class17"></td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <div title="处罚执行" style="padding:10px;">
            <%-- 处罚执行-是否执行处罚 --%>
            <div class="easyui-panel tabs-header-border" style="width: 100%;border:none;" align="center">
                <table class="TableBlock_page lookInfo-lowHeight-table" fit="true" style="width: 100%;">
                    <tr class="common-tr-border common-tr-present">
                        <td class="case-common-present-look-td-class6 font-bold-label-td">是否执行处罚决定：</td>
                        <td class="case-common-present-look-td-class7 yesOrNo" id="isPunishDecisionExecute"></td>
                        <td class="case-common-present-look-td-class6" ></td>
                        <td class="case-common-present-look-td-class7" ></td>
                        <td class="case-common-present-look-td-class8" ></td>
                        <td class="case-common-present-look-td-class9" ></td>
                        <td class="case-common-present-look-td-class10" ></td>
                    </tr>
                    <tr class="common-tr-border common-tr-present" style="display: none;" id="punishDecisionExecute_tr"><!-- id="isPunishDecisionExecut_td" -->
                        <td class="case-common-present-look-td-class6 font-bold-label-td">执行完成日期：</td>
                        <td class="case-common-present-look-td-class7" name="punishDecisionFinishDateStr" id="punishDecisionFinishDateStr"></td>
                        <td class="case-common-present-look-td-class6 font-bold-label-td" >执行方式：</td>
                        <td class="case-common-present-look-td-class7" name="punishDecisionExecuteWayValue" id="punishDecisionExecuteWayValue" ></td> 
                        <td class="case-common-present-look-td-class8" ></td>
                        <td class="case-common-present-look-td-class9" ></td>
                        <td class="case-common-present-look-td-class10" ></td>
                    </tr>
                    <tr class="common-tr-border common-tr-present" style="display: none;" id="stagesExection_tr">
                        <td class="case-common-present-look-td-class6 font-bold-label-td">是否分期执行：</td>
                        <td class="case-common-present-look-td-class7" colspan="3" id="isStagedExection"></td>
                        <td class="case-common-present-look-td-class6" ></td>
                        <td class="case-common-present-look-td-class7" ></td>
                        <td class="case-common-present-look-td-class8" ></td>
                        <td class="case-common-present-look-td-class9" ></td>
                        <td class="case-common-present-look-td-class10" ></td>
                    </tr>
                    <tr class="common-tr-border common-tr-present" style="display: none;" id="delayedExection_tr">
                        <td class="case-common-present-look-td-class6 font-bold-label-td">是否延期执行：</td>
                        <td class="case-common-present-look-td-class7" colspan="3" id="isDelayedExection"></td>
                        <td class="case-common-present-look-td-class6" ></td>
                        <td class="case-common-present-look-td-class7" ></td>
                        <td class="case-common-present-look-td-class8" ></td>
                        <td class="case-common-present-look-td-class9" ></td>
                        <td class="case-common-present-look-td-class10" ></td>
                    </tr> 
                </table>
            </div>
            <%-- 处罚执行-撤销原处罚决定 --%>
            <div id="common_case_revoke_punish_div" style="display: none;">
                <br/>
                <div class="easyui-panel" title="撤销原处罚决定" style="width: 100%;" align="center">
                    <table class="TableBlock_page lookInfo-lowHeight-table" fit="true" style="width: 99%;" align="center">
                        <tr class="common-tr-border">
                            <td class="case-common-present-look-td-class6 font-bold-label-td">决定类型：</td>
                            <td class="case-common-present-look-td-class7">
                                <span id="revokePunishType_span"></span>
                            </td>
                            <td class="case-common-present-look-td-class6 font-bold-label-td">决定批准人：</td>
                            <td class="case-common-present-look-td-class7">
                                <span id="revokePunish_approvePerson_span"></span>
                            </td>
                            <td class="case-common-present-look-td-class8" ></td>
                            <td class="case-common-present-look-td-class9" ></td>
                            <td class="case-common-present-look-td-class10" ></td>
                        </tr>
                        <tr class="common-tr-border">
                            <td class="case-common-present-look-td-class6 font-bold-label-td">决定批准日期：</td>
                            <td class="case-common-present-look-td-class7">
                                <span id="revokePunish_approveDateStr_span"></span>
                            </td>
                            <td class="case-common-present-look-td-class6 font-bold-label-td">决定日期：</td>
                            <td class="case-common-present-look-td-class7">
                                <span id="revokePunishmentDateStr_span"></span>
                            </td>
                            <td class="case-common-present-look-td-class8" ></td>
                            <td class="case-common-present-look-td-class9" ></td>
                            <td class="case-common-present-look-td-class10" ></td>
                        </tr>
                        <tr class="common-tr-border">
                            <td class="case-common-present-look-td-class6 font-bold-label-td">决定原因：</td>
                            <td class="" colspan="5">
                                <span id="revokePunishmentReason_span"></span>
                            </td>
                            <td class="case-common-present-look-td-class10" ></td>
                        </tr>
                    </table>
                </div>
            </div>
            <%-- 处罚执行-终结 --%>
            <div id="common_case_end_div" style="display: none;">
                <br/>
                <div class="easyui-panel" title="终结" style="width: 100%;" align="center">
                    <table class="TableBlock_page lookInfo-lowHeight-table" fit="true" style="width: 99%;" align="center">
                        <tr class="common-tr-border">
                            <td class="case-common-present-look-td-class1 font-bold-label-td">批准人：</td>
                            <td class="case-common-present-look-td-class2">
                                <span id="end_approvePerson_span"></span>
                            </td>
                            <td class="case-common-present-look-td-class1 font-bold-label-td">批准日期：</td>
                            <td class="case-common-present-look-td-class2">
                                <span id="end_approveDateStr_span"></span>
                            </td>
                            <td class="case-common-present-look-td-class3 font-bold-label-td">终结日期：</td>
                            <td class="case-common-present-look-td-class4">
                                <span id="endDateStr_span"></span>
                            </td>
                            <td class="case-common-present-look-td-class5"></td>
                        </tr>
                        <tr class="common-tr-border">
                            <td class="case-common-present-look-td-class1 font-bold-label-td">终结原因：</td>
                            <td class="" colspan="5">
                                <span id="endReason_span"></span>
                            </td>
                            <td class="case-common-present-look-td-class5" ></td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <div title="结案" style="padding:10px;">
            <div class="easyui-panel tabs-header-border"  style="width: 100%;border:none;" align="center">
                <table class="TableBlock_page lookInfo-lowHeight-table" style="width:100%; " id="common_case_execute_div" >
                     <tr class="common-tr-border common-tr-execute">
                        <td class="case-common-execute-look-td-class1 font-bold-label-td">结案日期：</td>
                        <td class="case-common-execute-look-td-class2" name="closedDateStr" id="closedDateStr"></td>
                        <td class="case-common-execute-look-td-class1 font-bold-label-td">结案类型：</td>
                        <td class="case-common-execute-look-td-class2" name="closedStateValue" id="closedStateValue"></td>
                        <td class="case-common-execute-look-td-class5"></td>
                    </tr> 
                    <tr class="common-tr-border common-tr-execute" style="display: none;" id="closedCaseInfo_tr">
                        <td class="case-common-execute-look-td-class1 font-bold-label-td"><span id="stateName"></span>原因：</td>
                        <td class="" colspan="3" name="closedCaseReason" id="closedCaseReason" ></td>
                        <td class="case-common-execute-look-td-class5"></td>
                    </tr>
                    <tr class="common-tr-border common-tr-execute" style="display: none;" id="transferOrgan_tr">
                        <td class="case-common-execute-look-td-class1 font-bold-label-td">接收部门：</td>
                        <td class="" colspan="3" name="transferOrgan" id="transferOrgan" ></td>
                        <td class="case-common-execute-look-td-class5"></td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
    <c:if test="${param.editFlag != 4 }">
    <div id="simpleBtn" align="center" class="iframeBtns clearfix" style="border-bottom-right-radius: 4px;border-bottom-left-radius: 4px;text-align: center;width:100%;padding: 8px 0;background-color:#fff;float: right;">
        <button class="btn-alert-blue" style="" onclick="javascript:history.go(-1);">返回</button>
    </div>
    </c:if>
    <script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/caseManager/commonCase/js/common_case_index_look.js"></script>
</body>
</html>
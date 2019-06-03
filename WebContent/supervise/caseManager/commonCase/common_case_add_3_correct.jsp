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
    <%@ include file="/header/validator2.0.jsp" %>
    <%@ include file="/header/upload.jsp" %>
    <link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="/supervise/caseManager/commonCase/css/caseAdd.css" />
    <link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
</head>
<body onload="doInitCorrect()" style="padding-right: 10px; padding-left: 10px; padding-top: 5px">
    <form method="post" id="common_case_add_3_correct_form" style="width: 100%;" class="easyui-form" data-options="novalidate:true">
        <input type="hidden" id="comm_case_add_correct_caseId" value="${caseId}"/>
        <input type="hidden" id="common_case_add_correct_editFlag" value="${editFlag}"/>
        <input type="hidden" id="common_case_add_correct_isNext" value="${isNext}"/>
        <input type="hidden" id="common_case_add_correct_modelId" value="${modelId}"/>
        <input type="hidden" id="common_case_add_correct_subjectId" value="${cbModel.subjectId}"/>
        <input type="hidden" id="common_case_add_correct_departmentId" value="${cbModel.departmentId}"/>
        <input type="hidden" id="common_case_add_correct_powerJsonStr" value="${cbModel.powerJsonStr}"/>
        <input type="hidden" id="common_case_add_correct_caseName" value="${cbModel.name}"/>
        <input type="hidden" id="common_case_add_correct_registerCode" value="${cbModel.caseCode}"/>
        <input type="hidden" id="common_case_add_correct_revokeJsonStr" value='${cbModel.revokeJsonStr}'/>
        <div class="easyui-panel" title="审查信息" style="width: 100%;" align="center">
            <table class="TableBlock_page" fit="true" style="width: 100%;table-layout: fixed;">
                <tr class="common-tr-border">
                    <td class="case-common-correct-td-class1">是否涉刑案件<span class="required">*</span>：</td>
                    <td class="case-common-correct-td-class2" id="isCriminalTd"></td>
                    <td class="case-common-correct-td-class3"></td>
                    <td class="case-common-correct-td-class4"></td>
                    <td class="case-common-correct-td-class6"></td>
                    <td class="case-common-correct-td-class7"></td>
                    <td class="case-common-correct-td-class5"></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-common-correct-td-class1">是否重大案件<span class="required">*</span>：</td>
                    <td class="case-common-correct-td-class2" id="isMajorCaseTd"></td>
                    <td class="case-common-correct-td-class3"></td>
                    <td class="case-common-correct-td-class4"></td>
                    <td class="case-common-correct-td-class6"></td>
                    <td class="case-common-correct-td-class7"></td>
                    <td class="case-common-correct-td-class5"></td>
                </tr>
                <tr class="common-tr-border" id="isLegalReviewTr">
                    <td class="case-common-correct-td-class1">是否法制审核<span class="required">*</span>：</td>
                    <td class="case-common-correct-td-class2" id="isLegalReviewTd"></td>
                    <td class="case-common-correct-td-class3" >
                        <label class="case-handle-date">法制审核日期<span class="required">*</span>：</label>
                    </td>
                    <td class="case-common-correct-td-class4">
                        <div class="case-handle-date">
                            <input name="legalReviewDateStr" id="legalReviewDateStr" 
                                class="easyui-datebox" style="width:100%;height:30px;max-width: 250px;" data-options="validType:'date'"/>
                        </div>
                    </td>
                    <td class="case-common-correct-td-class6 case-handle-date">
                        <label class="case-handle-date">法制审核意见<span class="required">*</span>：</label>
                    </td>
                    <td class="case-common-correct-td-class7">
                        <div class="case-handle-date">
                            <input class="easyui-textbox" name="legalExaminaSuggest" id="legalExaminaSuggest"
                                data-options="validType:'length[0,150]'" style="width: 100%; height:30px;max-width: 250px;" />
                        </div>
                    </td>
                    <td class="case-common-correct-td-class5"></td>
                </tr>
                <tr class="common-tr-border" id="isCollectiveDiscussionTr">
                    <td class="case-common-correct-td-class1">是否集体讨论<span class="required">*</span>：</td>
                    <td class="case-common-correct-td-class2" id="isCollectiveDiscussionTd"></td>
                    <td class="case-common-correct-td-class3">
                        <label class="case-handle-date">集体讨论日期<span class="required">*</span>：</label>
                    </td>
                    <td class="case-common-correct-td-class4">
                        <div class="case-handle-date">
                            <input name="collectiveDiscussionDateStr" id="collectiveDiscussionDateStr" 
                                class="easyui-datebox" style="width:100%;height:30px;max-width: 250px;" data-options="validType:'date'"/>
                        </div>
                    </td>
                    <td class="case-common-correct-td-class6">
                        <label class="case-handle-date">集体讨论结论<span class="required">*</span>：</label>
                    </td>
                    <td class="case-common-correct-td-class7">
                        <div class="case-handle-date">
                            <input class="easyui-textbox" name="collectiveDiscussionResult" id="collectiveDiscussionResult" 
                                data-options="validType:'length[0,150]'" style="width: 100%; height:30px;max-width: 250px;" />
                        </div>
                    </td>
                    <td class="case-common-correct-td-class5"></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-common-correct-td-class1">是否听证<span class="required">*</span>：</td>
                    <td class="case-common-correct-td-class2" id="isHearingTd"></td>
                    <td class="case-common-correct-td-class3"></td>
                    <td class="case-common-correct-td-class4"></td>
                    <td class="case-common-correct-td-class6"></td>
                    <td class="case-common-correct-td-class7"></td>
                    <td class="case-common-correct-td-class5"></td>
                </tr>
                <tbody id="isHearingTr" >
                    <tr class="common-tr-border">
                        <td class="case-common-correct-td-class1">申请听证日期&nbsp;：</td>
                        <td class="case-common-correct-td-class2">
                            <input name="hearingApplyDateStr" id="hearingApplyDateStr" 
                                class="easyui-datebox" style="width:100%;height:30px;max-width: 250px;" data-options="validType:'date'"/>
                        </td>
                        <td class="case-common-correct-td-class3">听证通知送达日期&nbsp;：</td>
                        <td class="case-common-correct-td-class4">
                            <input name="hearingInformDateStr" id="hearingInformDateStr" 
                                class="easyui-datebox" style="width:100%;height:30px;max-width: 250px;" data-options="validType:'date'"/>
                        </td>
                        <td class="case-common-correct-td-class6">听证公告发布日期&nbsp;：</td>
                        <td class="case-common-correct-td-class7">
                            <input name="hearingNoticeDateStr" id="hearingNoticeDateStr" 
                                class="easyui-datebox" style="width:100%;height:30px;max-width: 250px;" data-options="validType:'date'"/>
                        </td>
                        <td class="case-common-correct-td-class5"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-correct-td-class1">听证举行日期<span class="required">*</span>：</td>
                        <td class="case-common-correct-td-class2">
                            <input name="hearingHoldDateStr" id="hearingHoldDateStr" 
                                class="easyui-datebox" style="width:100%;height:30px;max-width: 250px;" data-options="validType:'date'"/>
                        </td>
                        <td class="case-common-correct-td-class3">听证方式<span class="required">*</span>：</td>
                        <td class="case-common-correct-td-class4">
                            <input name="hearingWay" id="hearingWay" 
                                class="easyui-combobox" style="width:100%;height:30px;max-width: 250px;" data-options=""/>
                        </td>
                        <td class="case-common-correct-td-class6">听证结论&nbsp;：</td>
                        <td class="case-common-correct-td-class7">
                            <input name="hearingConclusion" id="hearingConclusion" 
                                class="easyui-combobox" style="width:100%;height:30px;max-width: 250px;" data-options=""/>
                        </td>
                        <td class="case-common-correct-td-class5"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-correct-td-class1">主持人&nbsp;：</td>
                        <td class="case-common-correct-td-class2">
                            <input name="hearingHost" id="hearingHost" 
                                class="easyui-textbox" style="width:100%;height:30px;max-width: 250px;" data-options="validType:'length[0,50]'"/>
                        </td>
                        <td class="case-common-correct-td-class3">听证人&nbsp;：</td>
                        <td class="" colspan="3">
                            <input name="hearingPerson" id="hearingPerson" 
                                class="easyui-textbox" style="width:100%;height:30px;" data-options="validType:'length[0,200]'"/>
                        </td>
                        <td class="case-common-correct-td-class5"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-correct-td-class1">记录人&nbsp;：</td>
                        <td class="case-common-correct-td-class2">
                            <input name="hearingRecorder" id="hearingRecorder" 
                                class="easyui-textbox" style="width:100%;height:30px;max-width: 250px;" data-options="validType:'length[0,50]'"/>
                        </td>
                        <td class="case-common-correct-td-class3">参加人&nbsp;：</td>
                        <td class="" colspan="3">
                            <input name="hearingParticipants" id="hearingParticipants" 
                                class="easyui-textbox" style="width:100%;height:30px;" data-options="validType:'length[0,400]'"/>
                        </td>
                        <td class="case-common-correct-td-class5"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-correct-td-class1">听证地点&nbsp;：</td>
                        <td class="" colspan="5">
                            <input name="hearingAddress" id="hearingAddress" 
                                class="easyui-textbox" style="width:100%;height:30px;" data-options="validType:'length[0,100]'"/>
                        </td>
                        <td class="case-common-correct-td-class5"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-correct-td-class1">听证结果/报告&nbsp;：</td>
                        <td class="" colspan="5">
                            <input name="hearingResult" id="hearingResult" 
                                class="easyui-textbox" style="width:100%;height:80px;" data-options="validType:'length[0,2000]', multiline:true"/>
                        </td>
                        <td class="case-common-correct-td-class5"></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <br />
        <div class="easyui-panel" title="决定信息" style="width: 100%;" align="center">
            <table class="TableBlock_page" fit="true" style="width: 100%;table-layout: fixed;">
                <tr class="common-tr-border">
                    <td class="case-common-correct-td-class8">处罚决定<span class="required">*</span>：</td>
                    <td class="case-common-correct-td-class9" id="isPunishmentTd"></td>
                    <td class="case-common-correct-td-class10"></td>
                    <td class="case-common-correct-td-class11"></td>
                    <td class="case-common-correct-td-class12"></td>
                    <td class="case-common-correct-td-class13"></td>
                    <td class="case-common-correct-td-class14"></td>
                    <td class="case-common-correct-td-class15"></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-common-correct-td-class8">决定日期<span class="required">*</span>：</td>
                    <td class="case-common-correct-td-class9">
                        <input  name="punishmentDateStr" id="punishmentDateStr" data-options="validType:'date',required:true, novalidate:true, missingMessage:'请选择决定日期'" 
                            class="easyui-datebox" style="width:65%;height:30px;max-width: 250px;"/>
                    </td>
                    <td class="case-common-correct-td-class10"></td>
                    <td class="case-common-correct-td-class11">决定书文号<span class="required">*</span>：</td>
                    <td class="case-common-correct-td-class12">
                        <input style=" width: 100%; height: 30px;max-width: 250px;" class="easyui-textbox" 
                            name="punishmentCode" id="punishmentCode" data-options="validType:'length[0,50]',required:true, novalidate:true, missingMessage:'请填写决定书文号'"/>
                    </td>
                    <td class="case-common-correct-td-class13"></td>
                    <td class="case-common-correct-td-class14"></td>
                    <td class="case-common-correct-td-class15"></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-common-correct-td-class8">决定书&nbsp;：</td>
                    <td class="case-common-correct-td-class9">
                        <div id="fileContainerPunish" style="display: none;"></div> 
                        <a id="uploadHolderPunish" class="add_swfupload" href="javascript: void(0);">
                            <img src="<%=systemImagePath%>/upload/batch_upload.png" />上传附件
                        </a> 
                        <input id="attachmentSidStrPunish" name="attachmentSidStrPunish" type="hidden" />
                    </td>
                    <td  colspan="5" class="" style="white-space: pre-line;" id="punishmentDocument"></td>
                    <td class="case-common-correct-td-class15"></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-common-correct-td-class8">决定书送达日期<span class="required">*</span>：</td>
                    <td class="case-common-correct-td-class9">
                        <input name="punishmentSendDateStr" id="punishmentSendDateStr" data-options="validType:'date',required:true, novalidate:true, missingMessage:'请选择决定书送达日期'" 
                            class="easyui-datebox" style="width:65%;height:30px;max-width: 250px;"/>
                    </td>
                    <td class="case-common-correct-td-class10"></td>
                    <td class="case-common-correct-td-class11" style="text-indent: -15px;">决定书送达方式<span class="required">*</span>：</td>
                    <td class="case-common-correct-td-class12">
                        <select name="punishmentSendWay" id="punishmentSendWay" class="easyui-combobox" data-options="required:true, novalidate:true, missingMessage:'请选择决定书送达方式'" 
                            style="width: 100%; height: 30px;max-width: 250px;">
                        </select>
                    </td>
                    <td class="case-common-correct-td-class13"></td>
                    <td class="case-common-correct-td-class14"></td>
                    <td class="case-common-correct-td-class15"></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-common-correct-td-class8">送达回证&nbsp;：</td>
                    <td class="case-common-correct-td-class9">
                        <div id="fileContainerSendProof" style="display: none;"></div> 
                        <a id="uploadHolderSendProof" class="add_swfupload" href="javascript: void(0);">
                            <img src="<%=systemImagePath%>/upload/batch_upload.png" />上传附件
                        </a> 
                        <input id="attachmentSidStrSendProof" name="attachmentSidStrSendProof" type="hidden" />
                    </td>
                    <td  colspan="5" class="" style="white-space: pre-line;" id="sendProofDocument"></td>
                    <td class="case-common-correct-td-class15"></td>
                </tr>
                <tbody id="common_case_is_punishment_div" style="display: none;">
                    <%-- 警告 --%>
                    <tr class="common-tr-border" id="isWarn_tr">
                        <td class="case-common-correct-td-class8">处罚决定种类<span class="required">*</span>：</td>
                        <td class="case-common-correct-td-class9">
                            <input class="easyui-checkbox" name="isWarn" id="isWarn"/>
                        </td>
                        <td class="case-common-correct-td-class10"></td>
                        <td class="case-common-correct-td-class11"></td>
                        <td class="case-common-correct-td-class12"></td>
                        <td class="case-common-correct-td-class13"></td>
                        <td class="case-common-correct-td-class14"></td>
                        <td class="case-common-correct-td-class15"></td>
                    </tr>
                    <%-- 罚款 --%>
                    <tr class="common-tr-border" id="isFine_td">
                        <td class="case-common-correct-td-class8"></td>
                        <td class="case-common-correct-td-class9">
                            <input class="easyui-checkbox" name="isFine" id="isFine"/>
                        </td>
                        <td class="case-common-correct-td-class10">
                            <div class="text-input-punish" style="">
                                <input style=" width: 80%; height: 30px;max-width: 100px;" class="easyui-textbox" 
                                    name="fineSum" id="fineSum" data-options="disabled: true,validType:['decimal','length[0,11]']"/> 元
                            </div>
                        </td>
                        <td class="case-common-correct-td-class11"></td>
                        <td class="case-common-correct-td-class12"></td>
                        <td class="case-common-correct-td-class13"></td>
                        <td class="case-common-correct-td-class14"></td>
                        <td class="case-common-correct-td-class15"></td>
                    </tr>
                    <%-- 没收违法所得、没收非法财物 --%>
                    <tr class="common-tr-border" id="isConfiscate_td">
                        <td class="case-common-correct-td-class8"></td>
                        <td class="case-common-correct-td-class9">
                            <input class="easyui-checkbox" name="isConfiscate" id="isConfiscate"/>
                        </td>
                        <td class="case-common-correct-td-class10">
                            <div class="text-input-punish" style="">
                                <input style=" width: 80%; height: 30px;max-width: 100px;" class="easyui-textbox" 
                                    name="confiscateMoney" id="confiscateMoney" data-options="disabled: true,validType:['decimal','length[0,11]'],prompt:'违法所得'"/> 元
                            </div>
                        </td>
                        <td class="case-common-correct-td-class11">
                            <div class="text-input-punish" style="">
                                 <input style=" width: 80%; height: 30px;max-width: 100px;" class="easyui-textbox" 
                                    name="confiscateProMon" id="confiscateProMon" data-options="disabled: true,validType:['decimal','length[0,11]'],prompt:'非法财物'"/> 元
                            </div>
                        </td>
                        <td class="case-common-correct-td-class12"></td>
                        <td class="case-common-correct-td-class13"></td>
                        <td class="case-common-correct-td-class14"></td>
                        <td class="case-common-correct-td-class15"></td>
                    </tr>
                    <%-- 责令停产停业 --%>
                    <tr class="common-tr-border">
                        <td class="case-common-correct-td-class8"></td>
                        <td class="case-common-correct-td-class9">
                            <input class="easyui-checkbox" name="isOrderClosure" id="isOrderClosure"/>
                        </td>
                        <td class="case-common-correct-td-class10"></td>
                        <td class="case-common-correct-td-class11"></td>
                        <td class="case-common-correct-td-class12"></td>
                        <td class="case-common-correct-td-class13"></td>
                        <td class="case-common-correct-td-class14"></td>
                        <td class="case-common-correct-td-class15"></td>
                    </tr>
                    <%-- 暂扣或吊销许可证或营业执照 --%>
                    <tr class="common-tr-border" id="isTdLicense_td">
                        <td class="case-common-correct-td-class8"></td>
                        <td class="case-common-correct-td-class9">
                            <input class="easyui-checkbox" name="isTdLicense" id="isTdLicense"/>
                        </td>
                        <td class="case-common-correct-td-class10">
                            <div class="text-input-punish-choose" id="isTdOrRevokeLicenseTd" style=""></div>
                        </td>
                        <td class="case-common-correct-td-class11">
                            <label class="text-input-punish" style="">暂扣期限<span class="required">*</span>：</label>
                        </td>
                        <td class="case-common-correct-td-class12">
                            <div class="text-input-punish" style="">
                                <input  name="withholdingStartdateStr" id="withholdingStartdateStr" 
                                    class="easyui-datebox" style="width:100%;height:30px;max-width: 250px;" data-options="validType:'date'"/>
                            </div>
                        </td>
                        <td class="case-common-correct-td-class13">
                            <label class="text-input-punish" style="">至<span class="required">*</span>：</label>
                        </td>
                        <td class="case-common-correct-td-class14">
                            <div class="text-input-punish" style="">
                                <input  name="withholdingEnddateStr" id="withholdingEnddateStr" 
                                    class="easyui-datebox" style="width:100%;height:30px;max-width: 250px;" data-options="validType:'date'"/>
                            </div>
                        </td>
                        <td class="case-common-correct-td-class15"></td>
                    </tr>
                    <%-- 拘留 --%>
                    <tr class="common-tr-border" id="isDetain_td">
                        <td class="case-common-correct-td-class8"></td>
                        <td class="case-common-correct-td-class9">
                            <input class="easyui-checkbox" name="isDetain" id="isDetain"/>
                        </td>
                        <td class="case-common-correct-td-class10">
                            <div class="text-input-punish" style="">
                                <input style=" width: 80%; height: 30px;max-width: 100px;" class="easyui-textbox" 
                                    name="detainDays" id="detainDays" data-options="disabled: true,validType:['number', 'length[0,2]']"/> 天
                            </div>
                        </td>
                        <td class="case-common-correct-td-class11">
                            <label class="text-input-punish" style="">拘留期限<span class="required">*</span>：</label>
                        </td>
                        <td class="case-common-correct-td-class12">
                            <div class="text-input-punish" style="">
                                <input  name="detainStartdateStr" id="detainStartdateStr" 
                                    class="easyui-datebox" style="width:100%;height:30px;max-width: 250px;" data-options="validType:'date'"/>
                            </div>
                        </td>
                        <td class="case-common-correct-td-class13">
                            <label class="text-input-punish" style="">至<span class="required">*</span>：</label>
                        </td>
                        <td class="case-common-correct-td-class14">
                            <div class="text-input-punish" style="">
                                <input  name="detainEnddateStr" id="detainEnddateStr" 
                                    class="easyui-datebox" style="width:100%;height:30px;max-width: 250px;" data-options="validType:'date'"/>
                            </div>
                        </td>
                        <td class="case-common-correct-td-class15"></td>
                    </tr>
                    <%-- 其他 --%>
                    <tr class="common-tr-border" id="isOther_td">
                        <td class="case-common-correct-td-class8"></td>
                        <td class="case-common-correct-td-class9" >
                            <input class="easyui-checkbox" name="isOther" id="isOther"/>
                        </td>
                        <td class="" colspan="5">
                            <div class="text-input-punish" style="">
                                <input class="easyui-textbox case-textarea" name="otherDetailContent" id="otherDetailContent"
                                    data-options="validType:['length[0,500]'],  disabled:true" 
                                    style="width:100%; height:30px" />
                            </div>
                        </td>
                        <td class="case-common-correct-td-class15"></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <br/>
        <div id="common_case_is_punishment_punish_div" style="display: none;">
            <div id="common_case_illegal_hand_datagrid_div" class="easyui-panel" title="违法行为" style="width: 100%; height:220px;" align="center" data-options="tools:'#common_case_add_illegal_hand'">
                <div id="common_case_add_illegal_hand">
                    <a href="javascript:void(0);" class="icon-add" onclick="commonFindPower()" style=""><i class="fa fa-plus"></i></a>
                </div>
                <table fit="true" style="width: 100%;" id="common_case_illegal_hand_datagrid">
                </table>
            </div>
            <br />
            <div id="common_case_illegal_gist_datagrid_div" class="easyui-panel" title="违法依据" style="width: 100%; height:220px;" align="center">
                <table fit="true" style="width: 100%;" id="common_case_illegal_gist_datagrid"> 
                </table>
            </div>
            <br/>
            <div id="common_case_punish_datagrid_div" class="easyui-panel" title="处罚依据" style="width: 100%; height:220px; margin-bottom: 10px;" align="center">
                <table fit="true" style="width: 100%;" align="center" id="common_case_punish_datagrid"> 
                </table>
            </div>
        </div>
    </form>
    <script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/caseManager/commonCase/js/case.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/caseManager/commonCase/js/common_case_add_3_correct.js"></script>
</body>
</html>
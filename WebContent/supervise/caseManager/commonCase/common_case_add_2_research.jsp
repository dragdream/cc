<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="java.util.*,java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/validator2.0.jsp"%>
<%@ include file="/header/upload.jsp" %>
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/caseManager/commonCase/css/caseAdd.css" />
<!-- <link rel="stylesheet" type="text/css" href="/supervise/caseManager/commonCase/css/case.css" /> -->
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
</head>
<body onload="doInitResearch()" style="padding-right: 10px; padding-left: 10px; padding-top: 5px">
    <form method="post" id="common_case_add_2_research_form" style="width: 100%;" class="easyui-form"
        data-options="novalidate:true">
        <input type="hidden" id="comm_case_add_research_caseId" value="${caseId}" /> 
        <input type="hidden" id="common_case_add_research_editFlag" value="${editFlag}" /> 
        <input type="hidden" id="common_case_add_research_isNext" value="${isNext}" /> 
        <input type="hidden" id="common_case_add_research_modelId" value="${modelId}" /> 
        <input type="hidden" id="common_case_add_research_subjectId" value="${cbModel.subjectId}" /> 
        <input type="hidden" id="common_case_add_research_departmentId" value="${cbModel.departmentId}" /> 
        <input type="hidden" id="common_case_add_research_caseName" value="${cbModel.name}" />
        <input type="hidden" id="common_case_add_research_isForce" value="${cbModel.isForce}"/>
        <input type="hidden" id="common_case_add_research_informPunishType" value="${cbModel.informingPunishType}"/>
        <input type="hidden" id="common_case_add_research_powerNotice" value="${cbModel.rightInform}"/>
        
        <div class="easyui-panel case-common-panel-body tabs-header" style="width: 99%; border-style: none"
            align="center">
            <table class="TableBlock_page" fit="true" style="width: 100%;">
                <tr class="common-tr-border">
                    <td class="case-common-td-research2">是否采取强制措施&nbsp;：</td>
                    <td class="case-common-td-research11" id="isForceTd"></td>
                    <td class="case-common-td-research2"></td>
                    <td class="case-common-td-research11"></td>
                    <td class="case-common-td-research21"></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-common-td-research2">调查终结日期<span class="required">*</span>：</td>
                    <td class="case-common-td-research11">
                        <input name="surveyEndDateStr" id="surveyEndDateStr" value="" class="easyui-datebox"
                            style="width: 100%; height: 30px;max-width: 250px;" data-options="required:true, validType:'date', novalidate:true, missingMessage:'请选择调查终结日期' " />
                    </td>
                    <td class="case-common-td-research2"></td>
                    <td class="case-common-td-research11"></td>
                    <td class="case-common-td-research21"></td>
                </tr>
            </table>
        </div>
        <div class="easyui-panel case-common-panel-body1" title="调查信息" align="center"
            data-options="tools:'#common_case_add_survey'">
            <div id="common_case_add_survey">
                <a href="javascript:void(0);" class="icon-add" onclick="addSurveyInfo()"><i
                    class="fa fa-plus "></i></a>
            </div>
            <table fit="true" style="width: 100%;" id="survey_information">
            </table>
        </div>
        <br />
        <div class="easyui-panel case-common-panel-body1" title="证据信息" align="center"
            data-options="tools:'#common_case_add_illegal'">
            <div id="common_case_add_illegal">
                <a href="javascript:void(0);" class="icon-add" onclick="addIllegal()"><i
                    class="fa fa-plus "></i></a>
            </div>
            <table fit="true" style="width: 100%;" id="illegal_information">
            </table>
        </div>
        <br />
        <div class="easyui-panel case-common-panel-body" title="事先告知信息" style="width: 99%;" align="center">
            <table class="TableBlock_page" fit="true" style="width: 100%;">
                <tr class="common-tr-border">
                    <td class="case-common-td-research2">告知日期<span class="required">*</span>：
                    </td>
                    <td class="case-common-td-research11">
                        <input name="punishHearingSendDateStr"
                            id="punishHearingSendDateStr" value="${cbModel.punishHearingSendDateStr}" class="easyui-datebox"
                            style="width: 100%; height: 30px;"
                            data-options="required:true, validType:'date', novalidate:true, missingMessage:'请选择告知日期' " />
                    </td>
                    <td class="case-common-td-research2">告知方式<span class="required">*</span>：
                    </td>
                    <td class="case-common-td-research11">
                        <input name="punishHearingSendWay"
                        id="punishHearingSendWay" class="easyui-combobox" value="${cbModel.punishHearingSendWay}"
                        style="width: 100%; height: 30px;"
                        data-options="required:true, novalidate:true, missingMessage:'请选择告知方式' "/>
                    </td>
                    
                    <td class="case-common-td-research21" colspan="1"></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-common-td-research2" style="vertical-align: top;">告知处罚种类<span class="required">*</span>：
                    </td>
                    <td class="case-common-td-research19" id="informPunishType" colspan="3" ></td>
                    <td class="case-common-td-research21"></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-common-td-research2">权利告知&nbsp;：</td>
                    <td class="case-common-td-research19" nowrap id="powerNotice" colspan="3"></td>
                    <td class="case-common-td-research21"></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-common-td-research2">事先告知书&nbsp;：</td>
                    <td class="case-common-td-research11 TableData" style="width: 180px;">
                            <div id="fileContainerInformingbook" style="display: none;"></div> 
                            <a id="uploadHolderInformingbook" class="add_swfupload" href="javascript: void(0);">
                                <img src="<%=systemImagePath%>/upload/batch_upload.png" />上传附件
                            </a> 
                            <input id="attachmentSidStrInformingbook" name="attachmentSidStrInformingbook" type="hidden" />
                        </td>
                        <td id="informingbookDocument" colspan="2"></td>
                </tr>
                <tr class="common-tr-border">
                    
                    <td class="case-common-td-research2">送达日期<span class="required">*</span>：
                    </td>
                    <td class="case-common-td-research11"><input name="informingbookDeliveryDateStr"
                        id="informingbookDeliveryDateStr" value="${cbModel.informingbookDeliveryDateStr}"
                        class="easyui-datebox" style="width: 100%; height: 30px;"
                        data-options="required:true, validType:'date', novalidate:true, missingMessage:'请选择送达日期' " />
                    </td>
                    <td class="case-common-td-research2">送达方式<span class="required">*</span>：
                    </td>
                    <td class="case-common-td-research11">
                        <input name="informingbookWay" id="informingbookWay"
                        class="easyui-combobox" value="${cbModel.informingbookWay}" style="width: 100%; height: 30px;"
                        data-options="required:true, novalidate:true, missingMessage:'请选择告知方式' " />
                    </td>
                    
                    <td class="case-common-td-research21"></td>
                </tr>
                <tr class="common-tr-border" id="isOrderCorrect_tr">
                    <td class="case-common-td-research2">送达回证&nbsp;：</td>
                    <td class="case-common-td-research11 TableData" style="width: 180px;">
                            <div id="fileContainerBackHold" style="display: none;"></div> 
                            <a id="uploadHolderBackHold" class="add_swfupload" href="javascript: void(0);">
                                <img src="<%=systemImagePath%>/upload/batch_upload.png" />上传附件
                            </a> 
                            <input id="attachmentSidStrBackHold" name="attachmentSidStrBackHold" type="hidden" />
                        </td>
                        <td id="backHoldDocument" colspan="2"></td>
                    <td class="case-common-td-research21"></td>
                </tr>
            </table>
            <br />
        </div>
        <br />
    </form>
    <script type="text/javascript" src="<%=contextPath%>/supervise/caseManager/commonCase/js/case.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/caseManager/commonCase/js/common_case_add_2_research.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/common/js/md5.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
</body>
</html>
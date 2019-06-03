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
    <link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="/supervise/caseManager/commonCase/css/caseAdd.css" />
    <link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
</head>
<body onload="doInitPresent()" style="padding-right: 10px; padding-left: 10px; padding-top: 5px">
    <form method="post" id="common_case_add_4_present_form" class="easyui-form" data-options="novalidate:true">
        <input type="hidden" id="comm_case_add_present_caseId" value="${caseId}"/>
        <input type="hidden" id="common_case_add_present_editFlag" value="${editFlag}"/>
        <input type="hidden" id="common_case_add_present_isNext" value="${isNext}"/>
        <input type="hidden" id="common_case_add_present_modelId" value="${modelId}"/>
        <input type="hidden" id="common_case_add_present_subjectId" value="${cbModel.subjectId}"/>
        <input type="hidden" id="common_case_add_present_departmentId" value="${cbModel.departmentId}"/>
        <input type="hidden" id="common_case_add_present_caseName" value="${cbModel.name}"/>
        <input type="hidden" id="common_case_add_present_revokePunishJsonStr" value='${cbModel.revokePunishJsonStr}'/>
        <input type="hidden" id="common_case_add_present_endJsonStr" value='${cbModel.endJsonStr}'/>
        <input type="hidden" id="common_case_add_present_caseCode" value='${cbModel.caseCode}'/>
        <input type="hidden" id="common_isPunishDecisionExecute" value='${cbModel.isPunishDecisionExecute}'/>
        <input type="hidden" id="common_isStagedExection" value='${cbModel.isStagedExection}'/>
        <input type="hidden" id="common_isDelayedExection" value='${cbModel.isDelayedExection}'/>
        <div class="easyui-panel tabs-header-border" style="width: 100%;border:none;" align="center">
            <table class="TableBlock_page" fit="true" style="width: 100%;">
                <tr class="common-tr-border">
                    <td class="case-common-td-research3">是否执行处罚决定<span class="required">*</span>：</td>
                    <td class="case-common-td-research11 yesOrNo" id="isPunishDecisionExecuteTd"> </td>
                    <td class="case-common-td-research1" ></td>
                    <td class="case-common-td-research11" ></td>
                    <td class="case-common-td-research21" ></td>
                </tr>
                <tr class="common-tr-border" style="display: none;" id="punishDecisionExecute_tr"><!-- id="isPunishDecisionExecut_td" -->
                    <td class="case-common-td-research3">执行完成日期<span class="required">*</span>：</td>
                    <td class="case-common-td-research11">
                        <input type="text" name="punishDecisionFinishDateStr" id="punishDecisionFinishDateStr" value="${cbModel.punishDecisionFinishDateStr}"
                            class="easyui-datebox" style="width: 100%; height: 30px;" data-options=""/>
                    </td>
                    <td class="case-common-td-research1" >执行方式<span class="required">*</span>：</td>
                    <td class="case-common-td-research11" >
                        <input type="text" name="punishDecisionExecuteWay" id="punishDecisionExecuteWay" value="${cbModel.punishDecisionExecuteWay}"
                        class="easyui-combobox" style="width: 100%; height: 30px;" data-options=""/>
                    </td> 
                    <td class="case-common-td-research21"></td>
                </tr>
                <tr class="common-tr-border" style="display: none;" id="stagesExection_tr">
                    <td class="case-common-td-research3">是否分期执行&nbsp;：</td>
                    <td class="case-common-td-research18" colspan="3" id="isStagedExectionTd"></td>
                    <td class="case-common-td-research21" ></td>
                </tr>
                <tr class="common-tr-border" style="display: none;" id="delayedExection_tr">
                    <td class="case-common-td-research3">是否延期执行&nbsp;：</td>
                    <td class="case-common-td-research18" colspan="3" id="isDelayedExectionTd"></td>
                    <td class="case-common-td-research21" ></td>
                </tr> 
            </table>
        </div>
    </form>
    <script type="text/javascript" src="<%=contextPath%>/supervise/caseManager/commonCase/js/case.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/caseManager/commonCase/js/common_case_add_4_present.js"></script>
</body>
</html>
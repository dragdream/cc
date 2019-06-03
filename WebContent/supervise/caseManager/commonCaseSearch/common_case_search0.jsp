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
<link rel="stylesheet" type="text/css" href="/supervise/caseManager/commonCaseSearch/css/case.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<style>
.case-common-td-class2{
    text-align: left!important; 
    width: 200px!important;
    height: 40px!important;
}
.datebox-calendar-inner {
    height: 180px;
}
.textbox-label{
	width: auto;
}
.textbox-label-a label{
	margin-right: 20px;
}
</style>
</head>
<body onload="doInit()" style="padding-right: 10px; padding-left: 10px; padding-top: 5px;">
    <form method="post" id="common_case_search_form" class="easyui-form" style="width: 100%;" data-options="novalidate:true">
        <input type="hidden" id="comm_case_add_filing_caseId" value="${caseId}"/>
        <input type="hidden" id="common_case_add_filing_editFlag" value="${editFlag}"/>
        <input type="hidden" id="common_case_add_filing_isNext" value="${isNext}"/>
        <input type="hidden" id="common_case_add_filing_modelId" value="${modelId}"/>
        <input type="hidden" id="common_case_add_filing_subjectId" value="${param.subjectId}"/>
        <div class="easyui-panel" style="width: 99%;" align="center">
            <table class="TableBlock_page" fit="true" style="width: 1100px;">
                <tr class="common-tr-border" style="height: 20px;"></tr>
                <tr class="common-tr-border">
                    <td class="case-common-td-class1"></td>
                    <td class="case-common-td-class1">行政区划：</td>
                    <td class="case-common-td-class2" id="administrativeDivision_td">
                        <select name="administrativeDivision" id="administrativeDivision" class="easyui-textbox"
                            style="width: 98%; height: 30px;" data-options="required:true, novalidate:true, missingMessage:'请选择所属地区' ">
                        </select>
                    </td>
                    <td class="case-common-td-class1">执法机关：</td>
                    <td class="case-common-td-class2" >
                        <%-- <input  type="hidden" name="departmentId" id="departmentId" value="${param.departmentId}"/> --%>
                        <input name="departmentId" id="departmentId" class="easyui-textbox"
                            style="width: 98%; height: 30px;" data-options="required:true, novalidate:true, missingMessage:'请选择所属部门' "/>
                    </td>
                    <td class="case-common-td-class1"><!-- 是否公开： --></td>
                    <td class="case-common-td-class3" nowrap id="citizenSex_td">
                        <%-- <input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="38" class="easyui-checkbox" 
                            name="citizenSex" id="citizenSex1" value="1" <c:if test="${param.citizenSex == 1}">data-options="checked: true"</c:if> label="是" />
                        <input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="38" class="easyui-checkbox"
                            name="citizenSex" id="citizenSex2" value="2" <c:if test="${param.citizenSex == 2}">data-options="checked: true"</c:if> label="否"/> --%>
                    </td>
                    <td class="case-common-td-class1"></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-common-td-class1"></td>
                    <td class="case-common-td-class1">所属领域：</td>
                    <td class="case-common-td-class2" id="orgSys_td">
                        <input name="orgSys" id="orgSys" class="easyui-textbox"
                            style="width: 98%; height: 30px;" data-options="required:true, novalidate:true, missingMessage:'请选择所属领域' "/>
                    </td>
                    <td class="case-common-td-class1">执法主体：</td>
                    <td class="case-common-td-class2" id="subjectId_td">
                        <input name="subjectId" id="subjectId" class="easyui-textbox"
                            style="width: 98%; height: 30px;" data-options="required:true, novalidate:true, missingMessage:'请选择执法主体' "/>
                    </td>
                    <td class="case-common-td-class1">是否委托办案：</td>
                    <td class="case-common-td-class3" nowrap id="citizenSex_td">
                        <input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="38" class="easyui-checkbox" 
                            name="isDepute" id="isDepute1" value="1" <c:if test="${param.isDepute == '1'}">data-options="checked: true"</c:if> label="是" />
                        <input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="38" class="easyui-checkbox"
                            name="isDepute" id="isDepute2" value="0" <c:if test="${param.isDepute == '0'}">data-options="checked: true"</c:if> label="否"/>
                    </td>
                    <td class="case-common-td-class1"></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-common-td-class1"></td>
                    <td class="case-common-td-class1">当事人类型：</td>
                    <td id="common_case_part_type_td" class="case-common-td-class2 textbox-label-a" colspan="5"></td>
                    <td class="case-common-td-class1"></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-common-td-class1"></td>
                    <td class="case-common-td-class1">案件来源：</td>
                    <td class="case-common-td-class2 textbox-label-a" id="common_case_source_td" colspan="5"></td>
                    <td class="case-common-td-class1"></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-common-td-class1"></td>
                    <td class="case-common-td-class1">办理状态：</td>
                    <td class="case-common-td-class2 textbox-label-a" id="common_case_current_state_td" colspan="5">
                    </td>
                    <td class="case-common-td-class1"></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-common-td-class1"></td>
                    <td class="case-common-td-class1">结案状态：</td>
                    <td class="case-common-td-class2 textbox-label-a" id="common_case_closed_state_td" colspan="5">
                    </td>
                    <td class="case-common-td-class1"></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-common-td-class1"></td>
                    <td class="case-common-td-class1">提交状态：</td>
                    <td class="case-common-td-class3" nowrap id="isSubmit_td">
                        <input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="60" class="easyui-checkbox" 
                            name="isSubmit" id="isSubmit1" value="1" <c:if test="${param.isSubmit == '1'}">data-options="checked: true"</c:if> label="已提交" />
                        <input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="60" class="easyui-checkbox"
                            name="isSubmit" id="isSubmit2" value="0" <c:if test="${param.isSubmit == '0'}">data-options="checked: true"</c:if> label="未提交"/>
                    </td>
                    <td class="case-common-td-class1">入库日期：</td>
                    <td class="case-common-td-class3" colspan="3">
                        <input name="begincreateDate" id="beginfilingDate" value="${param.begincreateDate}"
                            class="easyui-datebox" style="width:45%;height:30px;" data-options="validType:'date',required:true, missingMessage:'请选择呈报立案日期' "/>
                        &nbsp;-&nbsp;
                        <input name="endcreateDate" id="endfilingDate" value="${param.endcreateDate}"
                            class="easyui-datebox" style="width:45%;height:30px;" data-options="validType:'date',required:true, missingMessage:'请选择呈报立案日期' "/>
                    </td>
                    <td class="case-common-td-class1"></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-common-td-class1"></td>
                    <td class="case-common-td-class1">是否重大案件：</td>
                    <td class="case-common-td-class3" nowrap id="isMajorCase_td">
                        <input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="38" class="easyui-checkbox" 
                            name="isMajorCase" id="isMajorCase1" value="1" <c:if test="${param.isMajorCase == '1'}">data-options="checked: true"</c:if> label="是" />
                        <input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="38" class="easyui-checkbox"
                            name="isMajorCase" id="isMajorCase2" value="0" <c:if test="${param.isMajorCase == '0'}">data-options="checked: true"</c:if> label="否"/>
                    </td>
                    <td class="case-common-td-class1">是否涉刑：</td>
                    <td class="case-common-td-class3" nowrap id="a_td">
                        <input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="38" class="easyui-checkbox" 
                            name="isCriminal" id="isCriminal1" value="1" <c:if test="${param.isCriminal == '1'}">data-options="checked: true"</c:if> label="是" />
                        <input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="38" class="easyui-checkbox"
                            name="isCriminal" id="isCriminal2" value="0" <c:if test="${param.isCriminal == '0'}">data-options="checked: true"</c:if> label="否"/>
                    </td>
                    <td class="case-common-td-class1">是否法制审核：</td>
                    <td class="case-common-td-class3" nowrap id="isFilingLegalReview_td">
                        <input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="38" class="easyui-checkbox" 
                            name="isFilingLegalReview" id="isFilingLegalReview1" value="1" <c:if test="${param.isFilingLegalReview == '1'}">data-options="checked: true"</c:if> label="是" />
                        <input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="38" class="easyui-checkbox"
                            name="isFilingLegalReview" id="isFilingLegalReview2" value="0" <c:if test="${param.isFilingLegalReview == '0'}">data-options="checked: true"</c:if> label="否"/>
                    </td>
                    <td class="case-common-td-class1"></td>
                </tr>
                <tr class="common-tr-border" style="height: 20px;"></tr>
            </table>
        </div>
        <br/>
        <div style="text-align: center; padding-top: 10px;">
                <button class="easyui-linkbutton" title="查询" onclick="doFilingSave();return false;" id="btn">
                    <span style="padding-right: 2px; width: 40px;"><i class="fa fa-search"></i> 查 询</span>
                </button>
                <button class="easyui-linkbutton" title="重置" onclick="commonCaseRefresh()" id="btn">
                    <span style="padding-right: 2px; width: 40px;"><i class="fa fa-refresh"></i> 重 置</span>
                </button>
        </div>
    </form>
    <script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/caseManager/commonCaseSearch/js/common_case_search0.js"></script>
</body>
</html>
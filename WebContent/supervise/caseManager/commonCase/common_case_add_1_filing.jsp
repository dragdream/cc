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
<body onload="doInitFiling()" style="padding-right: 10px; padding-left: 10px; padding-top: 5px;">
    <form method="post" id="common_case_add_1_filing_form" class="easyui-form" style="width: 100%;" data-options="novalidate:true">
        <input type="hidden" id="comm_case_add_filing_caseId" value="${caseId}"/>
        <input type="hidden" id="common_case_add_filing_editFlag" value="${editFlag}"/>
        <input type="hidden" id="common_case_add_filing_isNext" value="${isNext}"/>
        <input type="hidden" id="common_case_add_filing_modelId" value="${modelId}"/>
        <input type="hidden" id="common_case_add_filing_subjectId" value="${cbModel.subjectId}"/>
        <div class="easyui-panel" title="基础信息" style="width: 100%;" align="center" id="baseDiv">
            <table class="TableBlock_page" style="width:100%;table-layout: fixed;word-wrap: break-word; word-break: break-all;">
                <tr class="common-tr-border">
                    <td class="case-common-filing-td-class1">案件编号<span class="required">*</span>：</td>
                    <td class="case-common-filing-td-class2">
                        <input name="caseCode" id="caseCode" class="easyui-textbox" 
                            style="width: 100%; height: 30px; max-width: 250px;" data-options="required:true, validType:'length[0,200]', novalidate:true, missingMessage:'请填写案件编号' "/>
                    </td>
                    <td class="case-common-filing-td-class1">执法主体<span class="required">*</span>：</td>
                    <td class="case-common-filing-td-class2">
                        <select name="subjectId" id="subjectId" class="easyui-combobox" 
                            style="width: 100%; height: 30px; max-width: 250px;" data-options="required:true, novalidate:true, missingMessage:'请选择执法主体' ">
                        </select>
                    </td>
                    <td class="case-common-filing-td-class3"></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-common-filing-td-class1">案件来源<span class="required">*</span>：</td>
                    <td class="case-common-filing-td-class2">
                        <input name="caseSource" id="caseSource" class="easyui-combobox" 
                            style="width: 100%; height: 30px; max-width: 250px;" data-options="required:true, novalidate:true, missingMessage:'请选择案件来源' "/>
                    </td>
                    <td class="case-common-filing-td-class1">来源日期<span class="required">*</span>：</td>
                    <td class="case-common-filing-td-class2">
                        <select name="sourceDateStr" id="sourceDateStr" class="easyui-datebox" 
                            style="width: 100%; height: 30px; max-width: 250px;" data-options="validType:'date',required:true, novalidate:true, missingMessage:'请选择来源日期' ">
                        </select>
                    </td>
                    <td class="case-common-filing-td-class3"></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-common-filing-td-class1">立案批准日期<span class="required">*</span>：</td>
                    <td class="case-common-filing-td-class2">
                        <input name="filingDateStr" id="filingDateStr" class="easyui-datebox" 
                            style="width: 100%; height: 30px; max-width: 250px;" data-options="validType:'date',required:true, novalidate:true, missingMessage:'请选择立案批准日期' "/>
                    </td>
                    <td class="case-common-filing-td-class1"></td>
                    <td class="case-common-filing-td-class2"></td>
                    <td class="case-common-filing-td-class3"></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-common-filing-td-class1">案由（事由）<span class="required">*</span>：</td>
                    <td colspan="3" nowrap="nowrap">
                        <input class="easyui-textbox" name="name" id="name" data-options="required:true, novalidate:true, validType:'length[0,200]', missingMessage:'请填写事由（案由）' "
                            style="width: 100%;height:30px;"/>
                    </td>
                    <td class="case-common-filing-td-class3"></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-common-filing-td-class1">案情介绍&nbsp;：</td>
                    <td colspan="3" nowrap="nowrap">
                        <input class="easyui-textbox" name="caseIntroduce" id="caseIntroduce" data-options="novalidate:true, validType:'length[0,2000]', missingMessage:'请填写案情介绍' ,multiline:true"
                            style="width: 100%;height:80px;"/>
                    </td>
                    <td class="case-common-filing-td-class3"></td>
                </tr>
                
                <tr class="common-tr-border">
                    <td class="case-common-filing-td-class1">立案呈批表&nbsp;：</td>
                    <td class="case-common-filing-td-class2">
                        <div id="fileContainerIntroduce" style="display: none;"></div> 
                        <a id="uploadHolderIntroduce" class="add_swfupload" href="javascript: void(0);">
                            <img src="<%=systemImagePath%>/upload/batch_upload.png" />上传附件
                        </a> 
                        <input id="attachmentSidStrIntroduce" name="attachmentSidStrIntroduce" type="hidden" />
                    </td>
                    <td colspan="2" id="filingApprovalDocument"></td>
                    <td class="case-common-filing-td-class3"></td>
                </tr>
                
            </table>
        </div>
        <br />
        <div class="easyui-panel" title="当事人" style="width: 100%;" align="center">
            <table class="TableBlock_page" style="width: 100%;">
                <tr class="common-tr-border">
                    <td class="case-common-filing-td-class1">当事人类型<span class="required">*</span>：</td>
                    <td class="case-common-filing-td-class2">
                        <input name="partyType" id="partyType" class="easyui-combobox" 
                            style="width: 100%; height: 30px; max-width: 250px;" data-options="required:true, novalidate:true, missingMessage:'请选择当事人类型' "/>
                    </td>
                    <td class="case-common-filing-td-class1">当事人名称<span class="required">*</span>：</td>
                    <td class="case-common-filing-td-class2">
                        <select name="partyName" id="partyName" class="easyui-textbox" 
                            style="width: 100%; height: 30px; max-width: 250px;" data-options="validType:'length[0,200]', required:true, novalidate:true, missingMessage:'请填写当事人名称' ">
                        </select>
                    </td>
                    <td class="case-common-filing-td-class3"></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-common-filing-td-class1">证件类型<span class="required">*</span>：</td>
                    <td class="case-common-filing-td-class2">
                        <input name="cardType" id="cardType" class="easyui-combobox" 
                            style="width: 100%; height: 30px; max-width: 250px;" data-options="required:true, novalidate:true, missingMessage:'请选择证件类型' "/>
                    </td>
                    <td class="case-common-filing-td-class1">证件号码<span class="required">*</span>：</td>
                    <td class="case-common-filing-td-class2">
                        <select name="cardCode" id="cardCode" class="easyui-textbox" 
                            style="width: 100%; height: 30px; max-width: 250px;" data-options="required:true, novalidate:true, missingMessage:'请填写证件号码' ">
                        </select>
                    </td>
                    <td class="case-common-filing-td-class3"></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-common-filing-td-class1">联系电话&nbsp;：</td>
                    <td class="case-common-filing-td-class2">
                        <input name="contactPhone" id="contactPhone" class="easyui-textbox" 
                            style="width: 100%; height: 30px; max-width: 250px;" data-options="validType:'length[0,50]', novalidate:true "/>
                    </td>
                    <td class="case-common-filing-td-class1">住所（住址）&nbsp;：</td>
                    <td class="case-common-filing-td-class2">
                        <input name="address" id="address" class="easyui-textbox" 
                            style="width: 100%; height: 30px; max-width: 250px;" data-options="validType:'length[0,200]', novalidate:true, prompt:'具体到街道（乡镇）、小区、楼门牌号' " />
                    </td>
                    <td class="case-common-filing-td-class3"></td>
                </tr>
                <tr class="common-tr-border" id="adressTr" style="display: none;">
                    <td class="case-common-filing-td-class1">注册地址<span class="required">*</span>：</td>
                    <td class="case-common-filing-td-class2">
                        <input name="registerAddress" id="registerAddress" class="easyui-textbox" 
                            style="width: 100%; height: 30px; max-width: 250px;" data-options="validType:'length[0,200]', novalidate:true "/>
                    </td>
                    <td class="case-common-filing-td-class1">经营地址<span class="required">*</span>：</td>
                    <td class="case-common-filing-td-class2">
                        <input name="businessAddress" id="businessAddress" class="easyui-textbox" 
                            style="width: 100%; height: 30px; max-width: 250px;" data-options="validType:'length[0,200]', novalidate:true " />
                    </td>
                    <td class="case-common-filing-td-class3"></td>
                </tr>
            </table>
        </div>
        <br/>
        <div class="easyui-panel case-common-panel-body1" title="执法人员" style="width: 100%; margin-bottom: 10px; height: 220px!important;" id="common_case_add_person_div"
            align="center" data-options="tools:'#common_case_add_person'">
            <div id="common_case_add_person">
                <c:if test="${editFlag != '3'}">
                    <a href="javascript:void(0);" class="icon-add" onclick="commonFindPerson();" style=""><i class="fa fa-plus"></i></a>
                </c:if>
            </div>
            <table fit="true" style="width: 99%;" id="common_case_add_person_datagrid">
            </table>
        </div>
        <%-- <div style="text-align: center; padding-top: 10px;">
            <c:if test="${editFlag == 1 || editFlag == 2}">
                <button class="easyui-linkbutton" title="保存" onclick="doFilingSave();return false;" id="btn">
                    <span style="padding-right: 2px; width: 40px;"><i class="fa fa-save"></i> 保存</span>
                </button>
            </c:if>
        </div> --%>
    </form>
    <script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/caseManager/commonCase/js/case.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/caseManager/commonCase/js/common_case_add_1_filing.js"></script>
</body>
</html>
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
<body onload="initIllegal()" style="padding-right: 10px; padding-left: 10px; padding-top: 5px">
    <form method="post" id="common_case_illegal" style="width: 100%;" class="easyui-form" data-options="novalidate:true">
        <input type="hidden" id="id" name="id" value="${cSurveyInfo.id}"/>
        <input type="hidden" id="isEdit" name="isEdit" value="${param.isEdit }"/>
        <input type="hidden" id="caseId" name="caseId" value="${param.caseId }"/>
        <input type="hidden" id="createTimeStr" name="createTimeStr" value=""/>
        <div class="easyui-panel case-common-panel-body tabs-header-border" style="width: 99%;border: none;" align="center" >
            <table class="TableBlock_page" fit="true" style="width: 100%; margin-top:20px;margin-bottom: 65px">
                <tr class="common-tr-border">
                    <td class="case-common-illegal-td-class1">证据类型<span class="required">*</span>：</td>
                    <td class="case-common-illegal-td-class2">
                        <input  name="illegalEvidenceType" id="illegalEvidenceType" value=""
                            class="easyui-combobox" style="width:100%;height:30px;" data-options="required:true, novalidate:true, missingMessage:'请选择证据类型'"/>
                    </td> 
                    <td class="case-common-illegal-td-class1">证据来源<span class="required">*</span>：</td>
                    <td class="case-common-illegal-td-class2">
                        <input  name="illegalSource" id="illegalSource" value=""
                            class="easyui-combobox" style="width:100%;height:30px;" data-options="novalidate:true, required:true, missingMessage:'请选择证据来源'"/>
                    </td>
                    <td class="case-common-illegal-td-class3"></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-common-illegal-td-class1">取证人<span class="required">*</span>：</td>
                    <td class="case-common-illegal-td-class2">
                        <input  name="illegalPerson" id="illegalPerson" value=""
                            class="easyui-textbox" style="width:100%;height:30px;" data-options="validType:'length[0,100]', required:true, novalidate:true, missingMessage:'请填写取证人'"/>
                    </td> 
                    <td class="case-common-illegal-td-class1">取证日期<span class="required">*</span>：</td>
                    <td class="case-common-illegal-td-class2">
                        <input  name="illegalDateStr" id="illegalDateStr" value=""
                            class="easyui-datebox" style="width:100%;height:30px;" data-options="novalidate:true, required:true, validType:'date', missingMessage:'请选择取证日期'"/>
                    </td>
                    <td class="case-common-illegal-td-class3"></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-common-illegal-td-class1" valign="top">取证地点&nbsp;：</td>
                    <td class="" colspan="3">
                        <input class="easyui-textbox case-textarea" name="address" id="address" 
                            data-options="validType:'length[0,100]'" style="width: 100%; " value="" />
                    </td>
                    <td class="case-common-illegal-td-class3"></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-common-illegal-td-class1">证据材料&nbsp;：</td>
                    <td class="case-common-illegal-td-class2">
                        <div id="fileContainerIllegal" style="display: none;"></div> 
                        <a id="uploadHolderIllegal" class="add_swfupload" href="javascript: void(0);">
                            <img src="<%=systemImagePath%>/upload/batch_upload.png" />上传附件
                        </a> 
                        <input id="attachmentSidStrIllegal" name="attachmentSidStrIllegal" type="hidden" />
                    </td>
                    <td  colspan="2" class="" style="white-space: pre-line;" id="illegalDocument"></td>
                    <td class="case-common-illegal-td-class3"></td>
                </tr>
            </table>
        </div>
    </form>
    <script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/caseManager/commonCase/js/case.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/caseManager/commonCase/js/common_case_add_2_research_illegal.js"></script>
</body>
</html>
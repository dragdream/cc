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
    <link rel="stylesheet" type="text/css" href="/supervise/caseManager/commonCase/css/case.css" />
    <link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
</head>
<body onload="doInitEnd()" style="padding-right: 0px; padding-left: 0px; padding-top: 5px">
    <form method="post" id="common_case_add_end_form" class="easyui-form" data-options="novalidate:true">
        <input type="hidden" id="comm_case_add_end_caseId" value="${caseId}"/>
        <input type="hidden" id="common_case_add_end_editFlag" value="${editFlag}"/>
        <input type="hidden" id="common_case_add_end_isNext" value="${isNext}"/>
        <div class="easyui-panel" style="width:100%;height:410px; padding-top: 0px; padding-bottom: 5px;border-color: #fff;" align="center">
            <table class="TableBlock_page" fit="true" style="width: 100%;" >
                <tr>
                    <td class="case-common-td-class21">案件名称&nbsp;：</td>
                    <td class="case-common-td-class26">${end.caseName}</td>
                    <td class="case-common-td-class34"></td>
                </tr>
                <tr>
                    <td class="case-common-td-class21">案件编号&nbsp;：</td>
                    <td class="case-common-td-class26">${end.caseCode}</td>
                    <td class="case-common-td-class34"></td>
                </tr>
                <tr>
                    <td class="case-common-td-class21">批准人<span class="required">*</span>：</td>
                    <td class="case-common-td-class26">
                        <input style="width:100%;height:30px;max-width: 250px;" class="easyui-textbox" name="approvePerson"
                             id="approvePerson" value="${end.approvePerson}" data-options="required:true, novalidate:true, missingMessage:'请填写案件终结批准人' "/>
                    </td>
                    <td class="case-common-td-class34"></td>
                </tr>
                <tr>
                    <td class="case-common-td-class21">批准日期<span class="required">*</span>：</td>
                    <td class="case-common-td-class26">
                        <input type="text" name="approveDateStr" id="approveDateStr" value="${end.approveDateStr}"
                            class="easyui-datebox" style="width:100%;height:30px;max-width: 250px;" data-options="required:true, novalidate:true, missingMessage:'请选择案件终结批准日期' "/>
                    </td>
                    <td class="case-common-td-class34"></td>
                </tr>
                <tr>
                    <td class="case-common-td-class21">终结日期<span class="required">*</span>：</td>
                    <td class="case-common-td-class26">
                        <input type="text" name="endDateStr" id="endDateStr" value="${end.endDateStr}"
                            class="easyui-datebox" style="width:100%;height:30px;max-width: 250px;" data-options="required:true, novalidate:true, missingMessage:'请选择案件终结日期' " />
                    </td>
                    <td class="case-common-td-class34"></td>
                </tr>
                <tr>
                    <td class="case-common-td-class21">终结原因<span class="required">*</span>：</td>
                    <td class="case-common-td-class26">
                        <input class="easyui-textbox case-textarea" name="endReason" id="endReason" value="${end.endReason}"
                            data-options="multiline:true, required:true, novalidate:true, missingMessage:'请填写案件终结原因'" 
                            style="width: 98%; height:60px"/>
                    </td>
                    <td class="case-common-td-class34"></td>
                </tr>
            </table>
        </div>
    </form>
    <script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/caseManager/commonCase/js/common_case_addEnd.js"></script>
</body>
</html>
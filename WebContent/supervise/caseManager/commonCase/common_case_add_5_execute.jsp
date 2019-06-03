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
<body onload="doInitExecute()" style="padding-right: 10px; padding-left: 10px; padding-top: 5px">
    <form method="post" id="common_case_add_5_execute_form" class="easyui-form" data-options="novalidate:true">
        <input type="hidden" id="comm_case_add_execute_caseId" value="${caseId}"/>
        <input type="hidden" id="common_case_add_execute_editFlag" value="${editFlag}"/>
        <input type="hidden" id="common_case_add_execute_isNext" value="${isNext}"/>
        <input type="hidden" id="common_case_add_execute_modelId" value="${modelId}"/>
        <input type="hidden" id="common_case_add_execute_closedState" value="${cbModel.closedState}"/>
        <div class="easyui-panel tabs-header-border"  style="width: 100%;border:none;" align="center">
            <table class="TableBlock_page" style="width:100%; " >
                <tr class="common-tr-border">
                    <td class="case-common-td-research3">结案日期<span class="required">*</span>：</td>
                    <td class="case-common-td-research18" style="border:0px;cellpadding:0px;padding:0;>">
                        <table class="TableBlock_page" style="border:0px;width: 100%;">
                            <tr class="common-tr-border">
                                <td class="case-common-td-research14">
                                    <input name="closedDateStr" id="closedDateStr" value="${cbModel.closedDateStr}"
                                        class=" easyui-datebox"  style="width:100%;height: 30px;" data-options="validType:'date',required:true, novalidate:true, missingMessage:'请选择结案日期' "/>
                                </td>
                                <td class="case-common-td-research4">结案类型<span class="required">*</span>：
                                </td>
                                <td class="case-common-td-research14">
                                    <input class=" easyui-combobox" id="closedState" value="${cbModel.closedState}"
                                        name="closedState" style="width:100%;height: 30px;"  data-options="required:true, novalidate:true, missingMessage:'请选择结案类型' "/>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td class="case-common-td-research21"></td>
                </tr> 
                <tr class="common-tr-border" style="display: none;" id="closedCaseInfo_tr">
                    <td class="case-common-td-research3"><span id="stateName"></span>原因<span class="required">*</span>：</td>
                    <td class="case-common-td-research18">
                        <input class="easyui-textbox case-textarea" name="closedCaseReason" id="closedCaseReason" value="${cbModel.closedCaseReason}"
                            data-options="validType:'length[0,300]'," style="width: 100%;height:30px"/>
                    </td>
                    <td class="case-common-td-research21"></td>
                </tr>
                <tr class="common-tr-border" style="display: none;" id="transferOrgan_tr">
                    <td class="case-common-td-research3">接收部门<span class="required">*</span>：</td>
                    <td class="case-common-td-research18" >
                        <input class="easyui-textbox case-textarea" name="transferOrgan" id="transferOrgan" value="${cbModel.transferOrgan}"
                            data-options="validType:'length[0,60]'" style="width: 100%; height:30px"/>
                    </td>
                    <td class="case-common-td-research21"></td>
                </tr>
            </table>
        </div>
    </form>
    <script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/caseManager/commonCase/js/common_case_add_5_execute.js"></script>
</body>
</html>
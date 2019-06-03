<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/validator2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/caseManager/simpleCase/css/case.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
</head>
<body onload="doInitFiling()" style="padding: 10px; padding-left: 10px; padding-top: 5px">
    <form method="post" id="common_case_add_1_filing_form" style="width: 100%;" class="easyui-form" data-options="novalidate:true">
        <div align="center">
        <input type="hidden" id="modelId" name="modelId" value="${param.modelId }"/>
        <input type="hidden" id="editFlag" name="editFlag" value="${param.editFlag }"/>
        <input type="hidden" id="caseId" name="caseId" value="${param.caseId }"/>
        <div class="easyui-panel" title="基础信息" style="width: 100%;" align="center">
            <table class="TableBlock_page" style="width: 100%;">
                <tr class="common-tr-border">
                    <td class="case-simple-td-class1">案件编号<span class="required">*</span>：</td>
                    <td class="case-simple-td-class2">
                        <input name="caseCode" id="caseCode" class="easyui-textbox" 
                            style="width: 100%; height: 30px; max-width: 250px;" data-options="required:true, validType:'length[0,200]', novalidate:true, missingMessage:'请输入案件编号' "/>
                    </td>
                    <td class="case-simple-td-class1">执法主体<span class="required">*</span>：</td>
                    <td class="case-simple-td-class2">
                        <select name="subjectId" id="subjectId" class="easyui-combobox" 
                            style="width: 100%; height: 30px; max-width: 250px;" data-options="required:true, novalidate:true, missingMessage:'请选择执法主体' ">
                        </select>
                    </td>
                    <td class="case-simple-td-class3"></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-simple-td-class1">案件来源<span class="required">*</span>：</td>
                    <td class="case-simple-td-class2">
                        <input name="caseSource" id="caseSource" class="easyui-combobox" 
                            style="width: 100%; height: 30px; max-width: 250px;" data-options="required:true, novalidate:true, missingMessage:'请选择案件来源' "/>
                    </td>
                    <td class="case-simple-td-class1">来源日期<span class="required">*</span>：</td>
                    <td class="case-simple-td-class2">
                        <select name="sourceDateStr" id="sourceDateStr" class="easyui-datebox" 
                            style="width: 100%; height: 30px; max-width: 250px;" data-options="validType:'date',required:true, novalidate:true, missingMessage:'请选择来源日期' ">
                        </select>
                    </td>
                    <td class="case-simple-td-class3"></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-simple-td-class1">案由（事由）<span class="required">*</span>：</td>
                    <td colspan="3" nowrap="nowrap" id="name_td">
                        <input class="easyui-textbox" name="name" id="name" data-options="required:true, novalidate:true, validType:'length[0,200]', missingMessage:'请输入事由（案由）' "
                            style="width: 100%;height:30px;"/>
                    </td>
                    <td class="case-simple-td-class3"></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-simple-td-class1">立案批准日期<span class="required">*</span>：</td>
                    <td class="case-simple-td-class2">
                        <input name="filingDateStr" id="filingDateStr" class="easyui-datebox" 
                            style="width: 100%; height: 30px; max-width: 250px;" data-options="validType:'date',required:true, novalidate:true, missingMessage:'请选择立案批准日期' "/>
                    </td>
                    <td class="case-simple-td-class1"></td>
                    <td class="case-simple-td-class2"></td>
                    <td class="case-simple-td-class3"></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-simple-td-class1">处罚决定日期<span class="required">*</span>：</td>
                    <td class="case-simple-td-class2">
                        <input name="punishmentDateStr" id="punishmentDateStr" class="easyui-datebox" 
                            style="width: 100%; height: 30px; max-width: 250px;" data-options="validType:'date',required:true, novalidate:true, missingMessage:'请选择处罚决定日期' "/>
                    </td>
                    <td class="case-simple-td-class1">处罚决定书文号<span class="required">*</span>：</td>
                    <td class="case-simple-td-class2">
                        <select name="punishmentCode" id="punishmentCode" class="easyui-textbox" 
                            style="width: 100%; height: 30px; max-width: 250px;" data-options="validType:'length[0,50]', required:true, novalidate:true, missingMessage:'请输入处罚决定书文号' ">
                        </select>
                    </td>
                    <td class="case-simple-td-class3"></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-simple-td-class1">处罚决定种类<span class="required">*</span>：</td>
                    <td class="case-simple-td-class2">
                        <span id="punishDecisionTypeSpan" style="height: 30px;"></span>
                        <span id="fineSumSpan" style="display: none;">
                            <input name="fineSum" id="fineSum" class="easyui-textbox" 
                                style="width: 80px; height: 30px;" />
                            &nbsp;元
                        </span>
                    </td>
                    <td class="case-simple-td-class1"></td>
                    <td class="case-simple-td-class2"></td>
                    <td class="case-simple-td-class3"></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-simple-td-class1">送达日期<span class="required">*</span>：</td>
                    <td class="case-simple-td-class2">
                        <input name="sendDateStr" id="sendDateStr" class="easyui-datebox" 
                            style="width: 100%; height: 30px; max-width: 250px;" data-options="validType:'date',required:true, novalidate:true, missingMessage:'请选择送达日期' "/>
                    </td>
                    <td class="case-simple-td-class1">送达方式<span class="required">*</span>：</td>
                    <td class="case-simple-td-class2">
                        <select name="sendWay" id="sendWay" class="easyui-combobox" 
                            style="width: 100%; height: 30px; max-width: 250px;" data-options="required:true, novalidate:true, missingMessage:'请选择送达方式' ">
                        </select>
                    </td>
                    <td class="case-simple-td-class3"></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-simple-td-class1">执行日期<span class="required">*</span>：</td>
                    <td class="case-simple-td-class2">
                        <input name="punishDecisionExecutDateStr" id="punishDecisionExecutDateStr" class="easyui-datebox" 
                            style="width: 100%; height: 30px; max-width: 250px;" data-options="validType:'date',required:true, novalidate:true, missingMessage:'请选择执行日期' "/>
                    </td>
                    <td class="case-simple-td-class1">结案日期<span class="required">*</span>：</td>
                    <td class="case-simple-td-class2">
                        <select name="closedDateStr" id="closedDateStr" class="easyui-datebox" 
                            style="width: 100%; height: 30px; max-width: 250px;" data-options="validType:'date',required:true, novalidate:true, missingMessage:'请选择结案日期' ">
                        </select>
                    </td>
                    <td class="case-simple-td-class3"></td>
                </tr>
            </table>
        </div>
        <br />
        <div class="easyui-panel" title="当事人" style="width: 100%;" align="center">
            <table class="TableBlock_page" style="width: 100%;">
                <tr class="common-tr-border">
                    <td class="case-simple-td-class1">当事人类型<span class="required">*</span>：</td>
                    <td class="case-simple-td-class2">
                        <input name="partyType" id="partyType" class="easyui-combobox" 
                            style="width: 100%; height: 30px; max-width: 250px;" data-options="required:true, novalidate:true, missingMessage:'请选择当事人类型' "/>
                    </td>
                    <td class="case-simple-td-class1">当事人名称<span class="required">*</span>：</td>
                    <td class="case-simple-td-class2">
                        <select name="partyName" id="partyName" class="easyui-textbox" 
                            style="width: 100%; height: 30px; max-width: 250px;" data-options="validType:'length[0,200]', required:true, novalidate:true, missingMessage:'请输入当事人名称' ">
                        </select>
                    </td>
                    <td class="case-simple-td-class3"></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-simple-td-class1">证件类型<span class="required">*</span>：</td>
                    <td class="case-simple-td-class2">
                        <input name="cardType" id="cardType" class="easyui-combobox" 
                            style="width: 100%; height: 30px; max-width: 250px;" data-options="required:true, novalidate:true, missingMessage:'请选择证件类型' "/>
                    </td>
                    <td class="case-simple-td-class1">证件号码<span class="required">*</span>：</td>
                    <td class="case-simple-td-class2">
                        <select name="cardCode" id="cardCode" class="easyui-textbox" 
                            style="width: 100%; height: 30px; max-width: 250px;" data-options="required:true, novalidate:true, missingMessage:'请输入证件号码' ">
                        </select>
                    </td>
                    <td class="case-simple-td-class3"></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-simple-td-class1">联系电话&nbsp;：</td>
                    <td class="case-simple-td-class2">
                        <input name="contactPhone" id="contactPhone" class="easyui-textbox" 
                            style="width: 100%; height: 30px; max-width: 250px;" data-options="validType:'length[0,50]', novalidate:true "/>
                    </td>
                    <td class="case-simple-td-class1">住所（住址）&nbsp;：</td>
                    <td class="case-simple-td-class2">
                        <input name="address" id="address" class="easyui-textbox" 
                            style="width: 100%; height: 30px; max-width: 250px;" data-options="validType:'length[0,200]', novalidate:true, prompt:'具体到街道（乡镇）、小区、楼门牌号' " />
                    </td>
                    <td class="case-simple-td-class3"></td>
                </tr>
                <tr class="common-tr-border" id="adressTr" style="display: none;">
                    <td class="case-simple-td-class1">注册地址<span class="required">*</span>：</td>
                    <td class="case-simple-td-class2">
                        <input name="registerAddress" id="registerAddress" class="easyui-textbox" 
                            style="width: 100%; height: 30px; max-width: 250px;" data-options="validType:'length[0,200]', novalidate:true "/>
                    </td>
                    <td class="case-simple-td-class1">经营地址<span class="required">*</span>：</td>
                    <td class="case-simple-td-class2">
                        <input name="businessAddress" id="businessAddress" class="easyui-textbox" 
                            style="width: 100%; height: 30px; max-width: 250px;" data-options="validType:'length[0,200]', novalidate:true " />
                    </td>
                    <td class="case-simple-td-class3"></td>
                </tr>
            </table>
        </div>
        <br />
        <div class="easyui-panel" title="执法人员" style="width: 100%; height:230px;margin-bottom: 10px;" align="center" data-options="tools:'#common_case_add_illegal_hand'">
            <div id="common_case_add_illegal_hand">
                <a href="###" class="icon-add" onclick="doAddGistLawDetail()" style=""><i class="fa fa-plus"></i></a>
            </div>    
             <table id="case_simple_add_1_datagrid" fit="false" style="height: 180px"></table>
        </div>
        </div>
    </form>
    <%-- <div style="text-align: center; padding-top: 10px;">
            <a class="easyui-linkbutton" title="下一步" onclick="nextPage()" id="nextPage" >
                <span style="padding-right: 2px; width: 40px;"><i class="fa fa-angle-right"></i> 下一步</span>
            </a>
            controlTabs('common_case_add_tabs');
        </div> --%>
    <script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/caseManager/commonCase/js/case.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/caseManager/simpleCase/js/case_simple_add_1_filing.js"></script>
</body>
</html>
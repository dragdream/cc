<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ include file="/header/header2.0.jsp"%>
    <%@ include file="/header/easyui2.0.jsp"%>
    <%@ include file="/header/validator2.0.jsp" %>
    <%@ include file="/header/upload.jsp" %>
    <link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="/supervise/caseManager/simpleCase/css/case.css" />
    <link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
    <link rel="stylesheet" type="text/css" href="/supervise/common/css/pureText-lookInfo.css" />
</head>
<body onload="doInit()" style="padding-right: 10px; padding-left: 10px; padding-top: 5px">
    <input type="hidden" id="id" name="id" value="${param.id }" />
    <div style="width:100%;height:100%;">
    <c:if test="${param.editFlag != 4 }">
        <div class="easyui-tabs" style="height:91%;" >
        </c:if>
        <c:if test="${param.editFlag eq 4 }">
        <div class="easyui-tabs" style="height:100%;" >
        </c:if>
            <div title="基本信息" style="padding: 10px;">
                <div class="easyui-panel" title="基础信息" style="width: 100%;padding: 10px;" align="center">
                    <table class="TableBlock_page lookInfo-lowHeight-table" fit="true" style="width: 100%;">
                        <tr class="common-tr-border">
                            <td class="case-simple-td-class1 font-bold-label-td">案件编号：</td>
                            <td class="case-simple-td-class2" name="caseCode" id="caseCode">
                            </td>
                            <td class="case-simple-td-class1 font-bold-label-td">执法主体：</td>
                            <td class="case-simple-td-class2" name="subjectName" id="subjectName">
                            </td>
                        </tr>
                        <tr class="common-tr-border">
                            <td class="case-simple-td-class1 font-bold-label-td">案件来源：</td>
                            <td class="case-simple-td-class2" name="caseSourceValue" id="caseSourceValue">
                            </td>
                            <td class="case-simple-td-class1 font-bold-label-td">来源日期：</td>
                            <td class="case-simple-td-class2" name="sourceDateStr" id="sourceDateStr">
                            </td>
                        </tr>
                        <tr class="common-tr-border">
                            <td class="case-simple-td-class1 font-bold-label-td">案由（事由）：</td>
                            <td colspan="3" name="name" id="name">
                            </td>
                            <td class="case-simple-td-class3"></td>
                        </tr>
                        <tr class="common-tr-border">
                            <td class="case-simple-td-class1 font-bold-label-td">立案批准日期：</td>
                            <td class="case-simple-td-class2" name="filingDateStr" id="filingDateStr">
                            </td>
                            <td class="case-simple-td-class1 font-bold-label-td"></td>
                            <td class="case-simple-td-class2"></td>
                            <td class="case-simple-td-class3"></td>
                        </tr>
                        <tr class="common-tr-border">
                            <td class="case-simple-td-class1 font-bold-label-td">处罚决定日期：</td>
                            <td class="case-simple-td-class2" name="punishmentDateStr" id="punishmentDateStr">
                            </td>
                            <td class="case-simple-td-class1 font-bold-label-td">处罚决定书文号：</td>
                            <td class="case-simple-td-class2" name="punishmentCode" id="punishmentCode">
                            </td>
                            <td class="case-simple-td-class3"></td>
                        </tr>
                        <tr class="common-tr-border">
                            <td class="case-simple-td-class1 font-bold-label-td">处罚决定种类：</td>
                            <td class="case-simple-td-class2" id="punishDecisionTypeValue" name="punishDecisionTypeValue">
                            </td>
                            <td class="case-simple-td-class1 font-bold-label-td"></td>
                            <td class="case-simple-td-class2"></td>
                            <td class="case-simple-td-class3"></td>
                        </tr>
                        <tr class="common-tr-border">
                            <td class="case-simple-td-class1 font-bold-label-td">送达日期：</td>
                            <td class="case-simple-td-class2" name="sendDateStr" id="sendDateStr">
                            </td>
                            <td class="case-simple-td-class1 font-bold-label-td">送达方式：</td>
                            <td class="case-simple-td-class2" name="sendWayValue" id="sendWayValue">
                            </td>
                            <td class="case-simple-td-class3"></td>
                        </tr>
                        <tr class="common-tr-border">
                            <td class="case-simple-td-class1 font-bold-label-td">执行日期：</td>
                            <td class="case-simple-td-class2" name="punishDecisionExecutDateStr" id="punishDecisionExecutDateStr">
                            </td>
                            <td class="case-simple-td-class1 font-bold-label-td">结案日期：</td>
                            <td class="case-simple-td-class2" name="closedDateStr" id="closedDateStr">
                            </td>
                            <td class="case-simple-td-class3"></td>
                        </tr>
                    </table>
                </div>
                <br />
                <div class="easyui-panel" title="当事人" style="width: 100%;" align="center">
                    <table class="TableBlock_page lookInfo-lowHeight-table" fit="true" style="width: 100%;">
                        <tr class="common-tr-border">
                            <td class="case-simple-td-class1 font-bold-label-td">当事人类型：</td>
                            <td class="case-simple-td-class2" name="partyTypeValue" id="partyTypeValue">
                            </td>
                            <td class="case-simple-td-class1 font-bold-label-td">当事人名称：</td>
                            <td class="case-simple-td-class2" name="partyName" id="partyName">
                            </td>
                            <td class="case-simple-td-class3"></td>
                        </tr>
                        <tr class="common-tr-border">
                            <td class="case-simple-td-class1 font-bold-label-td">证件类型：</td>
                            <td class="case-simple-td-class2" name="cardTypeValue" id="cardTypeValue">
                            </td>
                            <td class="case-simple-td-class1 font-bold-label-td">证件号码：</td>
                            <td class="case-simple-td-class2" name="cardCode" id="cardCode">
                            </td>
                            <td class="case-simple-td-class3"></td>
                        </tr>
                        <tr class="common-tr-border">
                            <td class="case-simple-td-class1 font-bold-label-td">联系电话：</td>
                            <td class="case-simple-td-class2" name="contactPhone" id="contactPhone">
                            </td>
                            <td class="case-simple-td-class1 font-bold-label-td">住所（住址）：</td>
                            <td class="case-simple-td-class2" name="address" id="address">
                            </td>
                            <td class="case-simple-td-class3"></td>
                        </tr>
                        <tr class="common-tr-border" id="adressTr" style="display: none;">
                            <td class="case-simple-td-class1 font-bold-label-td">注册地址：</td>
                            <td class="case-simple-td-class2" name="registerAddress" id="registerAddress"></td>
                            <td class="case-simple-td-class1 font-bold-label-td">经营地址：</td>
                            <td class="case-simple-td-class2" name="businessAddress" id="businessAddress"></td>
                            <td class="case-simple-td-class3"></td>
                        </tr>
                    </table>
                </div>
                <br />
                <div class="easyui-panel" title="执法人员" style="width: 100%; height:230px" align="center">
                     <table id="case_simple_add_1_datagrid" fit="false" style="height: 180px"></table>
                </div>
            </div>
            <div id="bodyDiv" title="违法行为及依据" style="padding: 10px;">
                <div class="easyui-panel" title="违法行为" style="width: 100%; height: 220px" align="center">
                    <table id="case_simple_add_power_datagrid" fit="true" style="width: 100%;"></table>
                </div>
                <br/>
                <div class="easyui-panel" title="违法依据" style="width: 100%; height: 220px" align="center">
                    <table id="case_simple_gist_datagrid" fit="true" style="height: 200px;"></table>
                </div>
                <br />
                <div class="easyui-panel" title="处罚依据" style="width: 100%; height: 220px" align="center">
                    <table id="case_simple_punish_datagrid" fit="true" style="height: 200px"></table>
                </div>
            </div>
        </div>
        <%-- 返回按钮 --%>
        <c:if test="${param.editFlag != 4 }">
        <div id="simpleBtn" align="center" class="iframeBtns clearfix" style="border-bottom-right-radius: 4px;border-bottom-left-radius: 4px;text-align: center;width:100%;padding: 8px 0;background-color:#fff;float: right;">
            <button class="btn-alert-blue" style="" onclick="javascript:history.go(-1);">返回</button>
        </div>
        </c:if>
    </div>
    <script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/caseManager/simpleCase/js/case_simple_look.js"></script>
</body>
</html>
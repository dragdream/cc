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
<style>
.batch-add-manual-td-class1{
    width: 16% !important;
    height: 40px !important;
    text-align: right;
}
.batch-add-manual-td-class2{
    width: 15% !important;
    height: 40px !important;
    text-align: left;
}
.batch-add-manual-td-class3{
    width: 7% !important;
    height: 40px !important;
    text-align: left;
}
.batch-add-manual-td-class4{
    width: 16% !important;
    height: 40px !important;
    text-align: right;
}
.batch-add-manual-td-class5{
    width: 13% !important;
    height: 40px !important;
    text-align: left;
}
.batch-add-manual-td-class6{
    width: 4.5% !important;
    height: 40px !important;
    text-align: center;
    padding-left: 10px;
}
#batch_add_manual_form table{
    width: 100%;
    table-layout: fixed;
}
</style>
</head>
<body onload="doInit()" style="padding-right: 10px; padding-left: 10px; padding-top: 5px;">
    <div id="toolbar" class="titlebar clearfix">
        <div id="outwarp">
            <div class="fl left">
                <img id="img1" class='title_img' src="<%=contextPath%>/common/zt_webframe/imgs/xzbg/clgl/clsqcx.png" />
                <span class="title">案卷列表</span>
            </div>
        </div>
        <span class="basic_border"></span>
        <div class="easyui-panel" style="padding-top: 7px; padding-bottom: 7px; border: none;">
            <!-- form表单 -->
            <form id="batch_add_manual_form">
                <input type="hidden" id="area" name="area" value=""/>
                <input type="hidden" id="caseType" name="caseType" value="${param.caseType }"/>
                <input type="hidden" id="missionId" name = "missionId" value="${param.missionId }"/>
                <table>
                    <tr class="common-tr-border">
                        <td class="batch-add-manual-td-class1">所属区域：</td>
                        <td class="batch-add-manual-td-class2"></td>
                        <td class="batch-add-manual-td-class1">执法领域：</td>
                        <td class="batch-add-manual-td-class2">
                            <input type="text" id="orgSys" name="orgSys" class="easyui-combobox"
                                style="height: 30px; width: 100%; max-width: 250px;"/>
                        </td>
                        <td class="batch-add-manual-td-class1">案卷类型：</td>
                        <td class="batch-add-manual-td-class2"></td>
                        <td class="batch-add-manual-td-class3">
                            <a class="easyui-linkbutton" onclick="caseSearch()"><i class="fa fa-search"></i> 查 询</a>
                        </td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="batch-add-manual-td-class1">案卷名称：</td>
                        <td class="batch-add-manual-td-class2">
                            <input type="text" id="departmentId" name="departmentId" class="easyui-textbox"
                                style="height: 30px; width: 100%; max-width: 250px;"/>
                        </td>
                        <td class="batch-add-manual-td-class1">所属部门名称：</td>
                        <td class="batch-add-manual-td-class2">
                            <input type="text" id="departmentId" name="departmentId" class="easyui-combobox"
                                style="height: 30px; width: 100%; max-width: 250px;"/>
                        </td>
                        <td class="batch-add-manual-td-class1"></td>
                        <td class="batch-add-manual-td-class2"></td>
                        <td class="batch-add-manual-td-class3"></td>
                    </tr>
                </table>
                <%-- 行政处罚 --%>
                <table id="punishTable">
                    <tr class="common-tr-border">
                        <td class="batch-add-manual-td-class4">立案日期：</td>
                        <td class="batch-add-manual-td-class5">
                            <input type="text" id="beginfilingDateStr" name='beginfilingDateStr' class="easyui-datebox"
                                style="height:30px; width:100%; max-width: 250px;"/>
                        </td>
                        <td class="batch-add-manual-td-class6">至</td>
                        <td class="batch-add-manual-td-class5">
                            <input type="text" id="endfilingDateStr" name='endfilingDateStr' class="easyui-datebox"
                                style="height:30px; width:100%; max-width: 250px;"/>
                        </td>
                        <td class="batch-add-manual-td-class4">处罚决定日期：</td>
                        <td class="batch-add-manual-td-class5">
                            <input type="text" id="beginpunishmentDateStr" name='beginpunishmentDateStr' class="easyui-datebox"
                                style="height:30px; width:100%; max-width: 250px;"/>
                        </td>
                        <td class="batch-add-manual-td-class6">至</td>
                        <td class="batch-add-manual-td-class5">
                            <input type="text" id="endpunishmentDateStr" name='endpunishmentDateStr' class="easyui-datebox"
                                style="height:30px; width:100%; max-width: 250px;"/>
                        </td>
                        <td class="batch-add-manual-td-class3"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="batch-add-manual-td-class4">执行完成日期：</td>
                        <td class="batch-add-manual-td-class5">
                            <input type="text" id="beginpunishDecisionFinishDateStr" name='beginpunishDecisionFinishDateStr' class="easyui-datebox"
                                style="height:30px; width:100%; max-width: 250px;"/>
                        </td>
                        <td class="batch-add-manual-td-class6">至</td>
                        <td class="batch-add-manual-td-class5">
                            <input type="text" id="endpunishDecisionFinishDateStr" name='endpunishDecisionFinishDateStr' class="easyui-datebox"
                                style="height:30px; width:100%; max-width: 250px;"/>
                        </td>
                        <td class="batch-add-manual-td-class4">结案日期：</td>
                        <td class="batch-add-manual-td-class5">
                            <input type="text" id="beginclosedDateStr" name='beginclosedDateStr' class="easyui-datebox"
                                style="height:30px; width:100%; max-width: 250px;"/>
                        </td>
                        <td class="batch-add-manual-td-class6">至</td>
                        <td class="batch-add-manual-td-class5">
                            <input type="text" id="endclosedDateStr" name='endclosedDateStr' class="easyui-datebox"
                                style="height:30px; width:100%; max-width: 250px;"/>
                        </td>
                        <td class="batch-add-manual-td-class3"></td>
                    </tr>
                </table>
                <%-- 行政检查 --%>
                <table  id="inspectionTable">
                    <tr class="common-tr-border">
                        <td class="batch-add-manual-td-class4">检查日期：</td>
                        <td class="batch-add-manual-td-class5">
                            <input type="text" id="beginfilingDateStr" name='beginfilingDateStr' class="easyui-datebox"
                                style="height:30px; width:100%; max-width: 250px;"/>
                        </td>
                        <td class="batch-add-manual-td-class6">至</td>
                        <td class="batch-add-manual-td-class5">
                            <input type="text" id="endfilingDateStr" name='endfilingDateStr' class="easyui-datebox"
                                style="height:30px; width:100%; max-width: 250px;"/>
                        </td>
                        <td class="batch-add-manual-td-class4"></td>
                        <td class="batch-add-manual-td-class5"></td>
                        <td class="batch-add-manual-td-class6"></td>
                        <td class="batch-add-manual-td-class5"></td>
                        <td class="batch-add-manual-td-class3"></td>
                    </tr>
                </table>
                <%-- 行政强制 --%>
                <table  id="coercionTable">
                    <tr class="common-tr-border">
                        <td class="batch-add-manual-td-class4">强制措施实施日期：</td>
                        <td class="batch-add-manual-td-class5">
                            <input type="text" id="beginfilingDateStr" name='beginfilingDateStr' class="easyui-datebox"
                                style="height:30px; width:100%; max-width: 250px;"/>
                        </td>
                        <td class="batch-add-manual-td-class6">至</td>
                        <td class="batch-add-manual-td-class5">
                            <input type="text" id="endfilingDateStr" name='endfilingDateStr' class="easyui-datebox"
                                style="height:30px; width:100%; max-width: 250px;"/>
                        </td>
                        <td class="batch-add-manual-td-class4">行政机关执行完成日期：</td>
                        <td class="batch-add-manual-td-class5">
                            <input type="text" id="beginpunishmentDateStr" name='beginpunishmentDateStr' class="easyui-datebox"
                                style="height:30px; width:100%; max-width: 250px;"/>
                        </td>
                        <td class="batch-add-manual-td-class6">至</td>
                        <td class="batch-add-manual-td-class5">
                            <input type="text" id="endpunishmentDateStr" name='endpunishmentDateStr' class="easyui-datebox"
                                style="height:30px; width:100%; max-width: 250px;"/>
                        </td>
                        <td class="batch-add-manual-td-class3"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="batch-add-manual-td-class4">申请法院强制执行日期：</td>
                        <td class="batch-add-manual-td-class5">
                            <input type="text" id="beginpunishDecisionFinishDateStr" name='beginpunishDecisionFinishDateStr' class="easyui-datebox"
                                style="height:30px; width:100%; max-width: 250px;"/>
                        </td>
                        <td class="batch-add-manual-td-class6">至</td>
                        <td class="batch-add-manual-td-class5">
                            <input type="text" id="endpunishDecisionFinishDateStr" name='endpunishDecisionFinishDateStr' class="easyui-datebox"
                                style="height:30px; width:100%; max-width: 250px;"/>
                        </td>
                        <td class="batch-add-manual-td-class4"></td>
                        <td class="batch-add-manual-td-class5"></td>
                        <td class="batch-add-manual-td-class6"></td>
                        <td class="batch-add-manual-td-class5"></td>
                        <td class="batch-add-manual-td-class3"></td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <table id="case_choose_datagrid" fit="true"></table>
    <script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/caseManager/commonCase/js/case.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/caseCheck/batch/js/batch_add_2manual_choose.js"></script>
</body>
</html>
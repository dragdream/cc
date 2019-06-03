<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="java.util.*,java.text.SimpleDateFormat"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>

<script type="text/javascript" src="<%=contextPath%>/supervise/inspection/inspRecord/js/inspectionRecord_detail.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/power/css/power.css" />
<link rel="stylesheet" type="text/css" href="/supervise/inspection/inspRecord/css/inspection.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/pureText-lookInfo.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body onload="doInit()">
<div class="easyui-panel tabs-header-border" style="width: 99%; overflow: auto;">
    <div id="inspRecordInput_base_panel" title="基础信息" class="easyui-panel" style="width: 100%; overflow: auto;" >
        <input type="hidden" id="recordId" value="${param.id }" />
        <input type="hidden" id="orgSysCtrl" value="${param.orgSys}" />
        <input type="hidden" id="loginSubId" value="${param.subjectId}" />
        <input type="hidden" id="loginDeptId" value="${param.departmentId}" />
        <form role="form" id="inspRecordInput_base_form" name="form1" enctype="multipart/form-data" method="post" data-options="novalidate:true">
            <table class="TableBlock_page lookInfo-lowHeight-table" style="width: 95%; margin: 5px 0px;" >
                <tr class="none-border-tr">
                    <td class="insp-td-right2 font-bold-label-td" >检查单号：</td>
                    <td class="insp-td-left3" id='inspectionNumber'> </td>
                    <td class="insp-td-right2 font-bold-label-td">检查日期：</td>
                    <td class="insp-td-left3" id="inspectionDateStr"></td>
                </tr>
                <tr class="none-border-tr">
                    <td class="insp-td-right2 font-bold-label-td">执法主体：</td>
                    <td class="insp-td-left3" id='subjectName'></td>
                    <td class="insp-td-right2 font-bold-label-td">检查单模版：</td>
                    <td class="insp-td-left14" id="inspListModel">
                </tr>
                <tr class="none-border-tr">
                    <td class="insp-td-right2 font-bold-label-td">检查地点：</td>
                    <td class="insp-td-left10" colspan="3" id='inspectionAddr'></td>
                </tr>
                <tr class="none-border-tr">
                    <td class="insp-td-right2 font-bold-label-td">当事人类型：</td>
                    <td class="insp-td-left3" id="partyType" ></td>
                    <td class="insp-td-right2 font-bold-label-td">当事人名称：</td>
                    <td class="insp-td-left3" id="partyName" ></td>
                </tr>
                <tr class="none-border-tr">
                    <td class="insp-td-right2 font-bold-label-td">证件类型：</td>
                    <td class="insp-td-left3" id="cardType" ></td>
                    <td class="insp-td-right2 font-bold-label-td">证件号码：</td>
                    <td class="insp-td-left3" id="cardCode" ></td>
                </tr>
                <tr class="none-border-tr">
                    <td class="insp-td-right2 font-bold-label-td">联系电话&nbsp;：</td>
                    <td class="insp-td-left3" id="contactPhone" ></td>
                    <td class="insp-td-right2 font-bold-label-td">住所（住址）&nbsp;：</td>
                    <td class="insp-td-left3" id="address"></td>
                </tr>
            </table>
        </form>
    </div>
    <br/>
    <div title="检查项" class="easyui-panel"  style="width: 100%;min-height:130px; height:auto;" id="inspRecordInput_item_panel"  align="center" data-options="tools:'#inspRecordInput_item_panel_hand'">
        <table class="TableBlock" style="width: 100%; margin: 5px 0px;" id="record_items_table"></table>
    </div>
    <br />
    <div id="inspRecordInput_result_panel" title="检查结果" class="easyui-panel" style="width: 100%; overflow: auto;">
        <form role="form" id="inspRecordInput_result_form" name="form1" enctype="multipart/form-data" method="post">
            <table class="TableBlock_page lookInfo-lowHeight-table" style="width: 95%; margin: 5px 0px;">
                <tr class="none-border-tr">
                    <td class="insp-td-right2 font-bold-label-td" >检查结果：</td>
                    <td class="insp-td-left10" id ="isInspectionPass"></td>
                </tr>
                <tr class="none-border-tr">
                    <td class="insp-td-right2 font-bold-label-td">结果说明：</td>
                    <td class="insp-td-left10" id="resultDiscribe"></td>
                </tr>
            </table>
             <br/>
        </form>
    </div>
    <br />
    <div class="easyui-panel" title="执法人员" style="width: 100%; height: 130px!important;" id="insp_add_person_div" align="center" data-options="tools:'#common_case_add_person'">
        <table class="TableBlock" style="width: 100%; margin: 5px 0px;" id="insp_add_person_datagrid"></table>
    </div>
</div>
</body>
</html>
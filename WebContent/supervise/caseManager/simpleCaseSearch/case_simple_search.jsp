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
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<link rel="stylesheet" type="text/css" href="/supervise/caseManager/simpleCaseSearch/css/case.css" />
</head>
<body onload="doInit()" style="padding-right: 10px; padding-left: 10px; padding-top: 5px;">
    <form method="post" id="simple_case_search_form" class="easyui-form" style="width: 100%;" data-options="novalidate:true">
        <%-- 条件页 --%>
        <div style="width: 100%;" align="center" id="simpleCaseSearchDiv">
            <div class="easyui-panel" style="width: 100%;border: none;" align="center">
                <input type="hidden" id="isSubmit" name="isSubmit" value="1" />
                <table class="TableBlock_page" fit="true" style="width: 100%;max-width: 1090px;">
                    <tr class="common-tr-border" style="height: 10px;"></tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1">行政区划：</td>
                        <td class="case-common-td-class2" id="area_td">
                            <select name="area" id="area" class="easyui-textbox"
                                style="width: 100%; height: 30px;max-width: 600px;">
                            </select>
                        </td>
                        <td class="case-common-td-class0"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1">执法机关：</td>
                        <td class="case-common-td-class2">
                            <input name="departmentId" id="departmentId" class="easyui-textbox"
                                style="width: 100%; height: 30px;max-width: 600px;"/>
                        </td>
                        <td class="case-common-td-class0"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1">所属领域：</td>
                        <td class="case-common-td-class2" id="orgSys_td">
                            <input name="orgSys" id="orgSys" class="easyui-textbox"
                                style="width: 100%; height: 30px;max-width: 600px;"/>
                        </td>
                        <td class="case-common-td-class0"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1">执法主体：</td>
                        <td class="case-common-td-class2" id="subjectId_td">
                            <input name="subjectId" id="subjectId" class="easyui-textbox"
                                style="width: 100%; height: 30px;max-width: 600px;"/>
                        </td>
                        <td class="case-common-td-class0"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1">当事人类型：</td>
                        <td class="case-common-td-class2 textbox-label-a" id="common_case_part_type_td" nowrap="nowrap"></td>
                        <td class="case-common-td-class0"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1">当事人名称：</td>
                        <td class="case-common-td-class2 textbox-label-a">
                            <input name="partyName" id="partyName" class="easyui-textbox"
                                style="width: 100%; height: 30px;max-width: 600px;"/>
                        </td>
                        <td class="case-common-td-class0"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1">当事人证件类型：</td>
                        <td class="case-common-td-class2 textbox-label-a" id="common_case_card_type_td" nowrap="nowrap"></td>
                        <td class="case-common-td-class0"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1">执法人员姓名：</td>
                        <td class="case-common-td-class2 textbox-label-a">
                            <input name="officeName" id="officeName" class="easyui-textbox"
                                style="width: 100%; height: 30px;max-width: 600px;"/>
                        </td>
                        <td class="case-common-td-class0"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1">执法证号码：</td>
                        <td class="case-common-td-class2 textbox-label-a">
                            <input name="officeCode" id="officeCode" class="easyui-textbox"
                                style="width: 100%; height: 30px;max-width: 600px;"/>
                        </td>
                        <td class="case-common-td-class0"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1">决定书文号：</td>
                        <td class="case-common-td-class2 textbox-label-a">
                            <input name="punishmentCode" id="punishmentCode" class="easyui-textbox"
                                style="width: 100%; height: 30px;max-width: 600px;"/>
                        </td>
                        <td class="case-common-td-class0"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1">处罚决定日期：</td>
                        <td class="case-common-td-class2">
                            <input name="beginpunishmentDateStr" id="beginpunishmentDateStr" value=""
                                class="easyui-datebox" style="width:45%;height:30px;max-width: 250px;" data-options="validType:'date'"/>
                            &nbsp;-&nbsp;
                            <input name="endpunishmentDateStr" id="endpunishmentDateStr" value=""
                                class="easyui-datebox" style="width:45%;height:30px;max-width: 250px;" data-options="validType:'date'"/>
                        </td>
                        <td class="case-common-td-class0"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1">处罚执行日期：</td>
                        <td class="case-common-td-class2">
                            <input name="beginpunishDecisionExecutDateStr" id="beginpunishDecisionExecutDateStr" value=""
                                class="easyui-datebox" style="width:45%;height:30px;max-width: 250px;" data-options="validType:'date'"/>
                            &nbsp;-&nbsp;
                            <input name="endpunishDecisionExecutDateStr" id="endpunishDecisionExecutDateStr" value=""
                                class="easyui-datebox" style="width:45%;height:30px;max-width: 250px;" data-options="validType:'date'"/>
                        </td>
                        <td class="case-common-td-class0"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1">处罚决定种类：</td>
                        <td class="case-common-td-class2">
                            <span id="isWarnSpan" style="height: 30px;">
                                <input class="easyui-checkbox" name="isWarn" id="isWarn"/>
                            </span>
                            <span id="isFineSpan" style="height: 30px;">
                                <input class="easyui-checkbox" name="isFine" id="isFine"/>
                            </span>
                            <span id="fineSumSpan" style="display: none;">
                                <input name="minfineSum" id="minfineSum" class="easyui-textbox" 
                                    style="width: 80px; height: 30px;" />
                                &nbsp;-&nbsp;
                                <input name="maxfineSum" id="maxfineSum" class="easyui-textbox" 
                                    style="width: 80px; height: 30px;" />
                                &nbsp;（元）
                            </span>
                        </td>
                        <td class="case-common-td-class0"></td>
                    </tr>
                    <%-- <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1">违法行为：</td>
                        <td class="case-common-td-class2 textbox-label-a">
                            <input name="partyName" id="partyName" class="easyui-textbox"
                                style="width: 100%; height: 30px;max-width: 600px;"/>
                        </td>
                        <td class="case-common-td-class0"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1">处罚依据：</td>
                        <td class="case-common-td-class2 textbox-label-a">
                            <input name="partyName" id="partyName" class="easyui-textbox"
                                style="width: 100%; height: 30px;max-width: 600px;"/>
                        </td>
                        <td class="case-common-td-class0"></td>
                    </tr> --%>
                </table>
            </div>
            <div style="text-align: center; width: 100%;height: 55px;"></div>
            <div class="easyui-panel" style="" id="buttonDiv">
                    <button class="easyui-linkbutton" title="查询" onclick="caseSimpleSearch();return false;" id="btn">
                        <span style="padding-right: 2px; width: 40px;"><i class="fa fa-search"></i> 查 询</span>
                    </button>
                    <button class="easyui-linkbutton" title="重置" onclick="caseSimpleRefresh()" id="btn">
                        <span style="padding-right: 2px; width: 40px;"><i class="fa fa-refresh"></i> 重 置</span>
                    </button>
            </div>
        </div>
        <%-- 查询页 --%>
        <div style="width: 100%;height:100%;display: none;" align="center" id="simpleCaseDiv">
            <div id="toolbar" class="titlebar clearfix">
                <div id="outwarp">
                    <div class="fl left">
                        <img id="img1" class='title_img' src="<%=contextPath%>/common/zt_webframe/imgs/xzbg/clgl/clsqcx.png" />
                        <span class="title">简易案件综合查询</span>
                    </div>
                    <div class="fr">
                        <button class="easyui-linkbutton" onclick="exportSimpleCase()"><i class="fa fa-download"></i>&nbsp;导&nbsp;出</button>
                        <button class="easyui-linkbutton" onclick="back()"><i class="fa fa-reply"></i>&nbsp;返&nbsp;回</button>
                    </div>
                </div>
                <span class="basic_border"></span>
                <div class="" style="padding-top: 5px;display:inline-block;">
                    <span style="float:left;height: 26px;line-height: 26px;margin:4px;">&nbsp;已选条件：</span>
                    <span id="conditionDiv"></span>
                </div>
                <div id="optionCom" >
                    <span class="isshow"><i class="optional fa fa-bars fa-lg"></i>可选列  </span> 
                    <span class="tip">可根据需要"隐藏/显示"所选列</span> 
                    <ul class="panList"></ul>
                </div>
            </div>
            <table id="simple_case_index_datagrid" fit="true"></table>
        </div>
    </form>
    <script type="text/javascript" src="<%=contextPath%>/common/js/juicer-min.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/caseManager/commonCase/js/case.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/caseManager/simpleCaseSearch/js/case_simple_search.js"></script>
</body>
</html>
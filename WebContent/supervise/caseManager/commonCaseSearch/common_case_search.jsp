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
<link rel="stylesheet" type="text/css" href="/supervise/caseManager/commonCaseSearch/css/case.css" />
</head>
<body onload="doInit()" style="padding-right: 10px; padding-left: 10px; padding-top: 5px;">
    <form method="post" id="common_case_search_form" class="easyui-form" style="width: 100%;" >
        <%-- 条件页 --%>
        <div style="width: 100%;" align="center" id="commonCaseSearchDiv">
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
                        <td class="case-common-td-class1">是否委托办案：</td>
                        <td class="case-common-td-class2" nowrap id="isDepute_td">
                        </td>
                        <td class="case-common-td-class0"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1">当事人类型：</td>
                        <td class="case-common-td-class2 textbox-label-a" id="common_case_part_type_td"></td>
                        <td class="case-common-td-class0"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1">案件来源：</td>
                        <td class="case-common-td-class2 textbox-label-a" id="common_case_source_td" nowrap="nowrap"></td>
                        <td class="case-common-td-class0"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1">办理状态：</td>
                        <td class="case-common-td-class2 textbox-label-a" id="common_case_current_state_td">
                        </td>
                        <td class="case-common-td-class0"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1">结案状态：</td>
                        <td class="case-common-td-class2 textbox-label-a" id="common_case_closed_state_td">
                        </td>
                        <td class="case-common-td-class0"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1">提交状态：</td>
                        <td class="case-common-td-class2" nowrap id="isSubmit_td">
                        </td>
                        <td class="case-common-td-class0"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1">入库日期：</td>
                        <td class="case-common-td-class2">
                            <input name="begincreateTimeStr" id="begincreateTimeStr" value=""
                                class="easyui-datebox" style="width:45%;height:30px;max-width: 250px;" data-options="validType:'date'"/>
                            &nbsp;-&nbsp;
                            <input name="endcreateTimeStr" id="endcreateTimeStr" value=""
                                class="easyui-datebox" style="width:45%;height:30px;max-width: 250px;" data-options="validType:'date'"/>
                        </td>
                        <td class="case-common-td-class0"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1">是否重大案件：</td>
                        <td class="case-common-td-class2" nowrap id="isMajorCase_td">
                        </td>
                        <td class="case-common-td-class0"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1">是否法制审核：</td>
                        <td class="case-common-td-class2" nowrap id="isLegalReview_td">
                        </td>
                        <td class="case-common-td-class0"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1">是否涉刑：</td>
                        <td class="case-common-td-class2" nowrap id="isCriminal_td">
                        </td>
                        <td class="case-common-td-class0"></td>
                    </tr>
                </table>
            </div>
            <div style="text-align: center; width: 100%;height: 50px;"></div>
            <div class="easyui-panel" style="" id="buttonDiv">
                    <button class="easyui-linkbutton" title="查询" onclick="caseCommonSearch();return false;" id="btn">
                        <span style="padding-right: 2px; width: 40px;"><i class="fa fa-search"></i> 查 询</span>
                    </button>
                    <button class="easyui-linkbutton" title="重置" onclick="caseCommonRefresh()" id="btn">
                        <span style="padding-right: 2px; width: 40px;"><i class="fa fa-refresh"></i> 重 置</span>
                    </button>
            </div>
        </div>
        <%-- 查询页 --%>
        <div style="width: 100%;height:100%;display: none;" align="center" id="commonCaseDiv">
            <div id="toolbar" class="titlebar clearfix">
                <div id="outwarp">
                    <div class="fl left">
                        <img id="img1" class='title_img' src="<%=contextPath%>/common/zt_webframe/imgs/xzbg/clgl/clsqcx.png" />
                        <span class="title">一般案件综合查询</span>
                    </div>
                    <div class="fr">
                        <button class="easyui-linkbutton" onclick="commonCaseSearch()">
                            <span style="padding-right: 2px; width: 30px;"><i class="fa fa-search"></i> 查 询</span>
                        </button>
                        <button class="easyui-linkbutton" onclick="commonCaseRefresh()">
                            <span style="padding-right: 2px; width: 30px;"><i class="fa fa-refresh"></i> 重 置</span>
                        </button>
                        <button class="easyui-linkbutton" onclick="exportCase()">
                            <span style="padding-right: 2px; width: 30px;"><i class="fa fa-download"></i> 导 出</span>
                        </button>
                        <button class="easyui-linkbutton" onclick="back()">
                            <span style="padding-right: 2px; width: 30px;"><i class="fa fa-reply"></i> 返 回</span>
                        </button>
                    </div>
                </div>
                <span class="basic_border"></span>
                <%-- <div class="" style="padding-top: 5px;display:inline-block;">
                    <span style="float:left;height: 26px;line-height: 26px;margin:4px;">&nbsp;已选条件：</span>
                    <span id="conditionDiv"></span>
                </div> --%>
                <div class="" style="padding-top: 5px;">
                    <div class="tagbox" style="margin-top:5px;">
                        <span style="float:left;height: 26px;line-height: 26px;margin: -3px 2px 4px 4px;">&nbsp;已选条件：</span>
                        <span id="showOrHide" style="float:right;margin-right:5px;vertical-align:middle;"><i class="tabshow fa fa-angle-down"></i><span class="showtext">高级筛选</span></span>
                        <div style="display:inline-block;width:85%;">
                            <span id="conditionDiv"></span>
                        </div>
                    </div>
                    <div id="hideTable" style="width:100%;">
                        <table style="width:100%;">
                    <tr class="common-line-height"></tr>
                    <tr>
                        <td style="width: 76px; text-align: right;">案件名称：</td>
                        <td style="width: 140px; text-align: left;">
                            <input type="text" name="name" id="name" class="easyui-textbox" style="width: 140px; height:30px;" />
                        </td>
                        <td style="width: 110px; text-align: right;">立案日期：</td>
                        <td style="width: 110px; text-align: left;">
                            <input type="text" name='beginfilingDateStr' id="beginfilingDateStr"
                                class="easyui-datebox" style="width:110px; height:30px;" data-options="validType:'date'"/>
                        </td>
                        <td style="width: 35px; text-align: center;">至</td>
                        <td style="width: 110px; text-align: left;">
                            <input type="text" name='endfilingDateStr' id="endfilingDateStr"
                                class="easyui-datebox" style="width:110px; height:30px;" data-options="validType:'date'"/>
                        </td>
                        <td style="width: 110px; text-align: right;">结案日期：</td>
                        <td style="width: 110px; text-align: left;">
                            <input type="text" name='beginclosedDateStr' id="beginclosedDateStr"
                                class="easyui-datebox" style="width:110px; height:30px;" data-options="validType:'date'"/>
                        </td>
                        <td style="width: 35px; text-align: center;">至</td>
                        <td style="width: 110px; text-align: left;">
                            <input type="text" name='endclosedDateStr' id="endclosedDateStr"
                                class="easyui-datebox" style="width:110px; height:30px;" data-options="validType:'date'"/>
                        </td>
                    </tr>
                    <tr class="common-line-height"></tr>
                    <tr>
                        <td style="width: 76px; text-align: right;">执法人员：</td>
                        <td style="width: 140px; text-align: left;">
                            <input name="officeName" id="officeName" class="easyui-textbox" style="width: 140px; height:30px;"/>
                        </td>
                        <td style="width: 110px; text-align: right;">执法证号：</td>
                        <td style="width: 255px; text-align: left;" colspan="3">
                            <select name="officeCode" id="officeCode" class="easyui-textbox" style="width: 100%; height:30px;">
                            </select>
                        </td>
                        <td style="width: 110px; text-align: right;">决定书文号：</td>
                        <td style="width: 255px; text-align: left;" colspan="3">
                            <select name="punishmentCode" id="punishmentCode" class="easyui-textbox" style="width: 100%; height:30px;">
                            </select>
                        </td>

                    </tr>
                    <tr class="common-line-height"></tr>

                    <tr>
                        <td style="width: 76px; text-align: right;">处罚决定：</td>
                        <td style="width: 140px; text-align: left;">
                            <select name="punishState" id="punishState" class="easyui-combobox" style="width: 140px; height:30px;">
                            </select>
                        </td>
                        <td style="width: 110px; text-align: right;" id="punishDateName">决定书日期：</td>
                        <td style="width: 110px; text-align: left;">
                            <input type="text" name='beginpunishDateStr' id="beginpunishDateStr"
                                class="easyui-datebox" style="width:110px; height:30px;" data-options="validType:'date'"/>
                        </td>
                        <td style="width: 35px; text-align: center;">至</td>
                        <td style="width: 110px; text-align: left;">
                            <input type="text" name='endpunishDateStr' id="endpunishDateStr"
                                class="easyui-datebox" style="width:110px; height:30px;" data-options="validType:'date'"/>
                        </td>
                        <td style="width: 110px; text-align: right;">职权名称：</td>
                        <td style="width: 255px; text-align: left;" colspan="3">
                            <select name="powerName" id="powerName" class="easyui-textbox" style="width: 100%; height:30px;">
                            </select>
                        </td>
                        <td style="text-align: right; padding-right: 5px; width: 150px;"></td>
                    </tr>
                    <tr class="common-line-height"></tr>
                    <tr>
                        <td style="width: 76px; text-align: right;">处罚执行：</td>
                        <td style="width: 140px; text-align: left;">
                            <select name="punishExecutState" id="punishExecutState" class="easyui-combobox" style="width: 140px; height:30px;">
                            </select>
                        </td>
                        <td style="width: 110px; text-align: right;" id="punishExecutDateName">决定执行日期：</td>
                        <td style="width: 110px; text-align: left;">
                            <input type="text" name='beginpunishExecutDateStr' id="beginpunishExecutDateStr"
                                class="easyui-datebox" style="width:110px; height:30px;" data-options="validType:'date'"/>
                        </td>
                        <td style="width: 35px; text-align: center;">至</td>
                        <td style="width: 110px; text-align: left;">
                            <input type="text" name='endpunishExecutDateStr' id="endpunishExecutDateStr"
                                class="easyui-datebox" style="width:110px; height:30px;" data-options="validType:'date'"/>
                        </td>
                        <td style="width: 110px; text-align: right;">职权编号：</td>
                        <td style="width: 255px; text-align: left;" colspan="3">
                            <input type="text" name='powerCode' id="powerCode"
                                class="easyui-textbox" style="width:100%; height:30px;"/>
                        </td>
                        <td style="text-align: right; padding-right: 5px; width: 150px;"></td>
                    </tr>
                    <tr class="common-line-height"></tr>
                    </table>
                    </div>
                </div>
                <div id="optionCom" >
                    <span class="isshow"><i class="optional fa fa-bars fa-lg"></i>可选列  </span> 
                    <span class="tip">可根据需要"隐藏/显示"所选列</span> 
                    <ul class="panList"></ul>
                </div>
            </div>
            <table id="common_case_index_datagrid" fit="true"></table>
        </div>
    </form>
    <script type="text/javascript" src="<%=contextPath%>/common/js/juicer-min.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/caseManager/commonCase/js/case.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/caseManager/commonCaseSearch/js/common_case_search.js"></script>
</body>
</html>
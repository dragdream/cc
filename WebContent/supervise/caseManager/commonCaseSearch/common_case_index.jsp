<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat" %>
<!DOCTYPE html PUBLIC "-//W3C//Dspan XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/Dspan/xhtml1-transitional.dspan">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/caseManager/commonCase/css/case.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<title>一般案件综合查询</title>
</head>
<body onload="doInitIndex()" style="padding-right: 10px;padding-left: 10px;min-width: 1100px;overflow:auto;padding-top: 5px">
    <div id="toolbar" class="titlebar clearfix">
    <div id="outwarp">
        <div class="fl left">
            <img id="img1" class='title_img' src="<%=contextPath%>/common/zt_webframe/imgs/xzbg/clgl/clsqcx.png" />
                <span class="title">一般案件综合查询</span>
        </div>
        <div class="right fr t_btns">
                <button class="easyui-linkbutton" onclick="commonCaseSearch()">
                <span style="padding-right: 2px; width: 30px;"><i class="fa fa-search"></i> 查 询</span>
                </button>
                <button class="easyui-linkbutton" onclick="commonCaseRefresh()">
                <span style="padding-right: 2px; width: 30px;"><i class="fa fa-refresh"></i> 重 置</span>
                </button>
                <button class="easyui-linkbutton" onclick="exportCase()">
                    <span style="padding-right: 2px; width: 30px;"><i class="fa fa-download"></i> 导 出</span>
                </button>
                <button class="easyui-linkbutton" onclick="commonCaseSubmit(0)">
                    <span style="padding-right: 2px; width: 30px;"><i class="fa fa-reply"></i> 返 回</span>
                </button>
        </div>
        </div>
        <span class="basic_border"></span>

        <div class="" style="padding-top: 5px;">
            <!-- form表单 -->
            <form id="common_case_form">
                <input type="hidden" id="administrativeDivision" name="administrativeDivision" value="${param.administrativeDivision}"/>
                <input type="hidden" id="departmentId" name="departmentId" value="${param.departmentId}"/>
                <input type="hidden" id="orgSys" name="orgSys" value="${param.orgSys}"/>
                <input type="hidden" id="subjectId" name="subjectId" value="${param.subjectId}"/>
                
                <input type="hidden" id="partyType" name="partyType" value="${param.partyType}"/>
                <input type="hidden" id="caseSource" name="caseSource" value="${param.caseSource}"/>
                <input type="hidden" id="currentState" name="currentState" value="${param.currentState}"/>
                <input type="hidden" id="closedState" name="closedState" value="${param.closedState}"/>
                
                <input type="hidden" id="isSubmit" name="isSubmit" value="${param.isSubmit}"/>
                <input type="hidden" id="isDepute" name="isDepute" value="${param.isDepute}"/>
                <input type="hidden" id="isCriminal" name="isCriminal" value="${param.isCriminal}"/>
                <input type="hidden" id="isMajorCase" name="isMajorCase" value="${param.isMajorCase}"/>
                <input type="hidden" id="isFilingLegalReview" name="isFilingLegalReview" value="${param.isFilingLegalReview}"/>
                
                <input type="hidden" id="begincreateDate" name="begincreateDate" value="${param.begincreateDate}"/>
                <input type="hidden" id="endcreateDate" name="endcreateDate" value="${param.endcreateDate}"/>
                <%-- <input type="hidden" id="common_case_index_deptIdExists" value="${deptIdExists}"/>
                <input type="hidden" id="common_case_index_subIdExists" value="${subIdExists}"/>
                <input type="hidden" id="common_case_index_supDeptIdExists" value="${supDeptIdExists}"/>
                <input type="hidden" id="common_case_index_menuGroupStrNames" value='${menuGroupStrNames}'/> --%>
     
     
        <div class="tagbox"  style="margin-top:5px;">
             <span style="float:left;height: 26px;line-height: 26px;margin: -3px 2px 4px 4px;">&nbsp;已选条件：</span>
             <span id="showOrHide" style="float:right;margin-right:5px;vertical-align:middle;"><i class="tabshow fa fa-angle-down"></i><span class="showtext">高级筛选</span></span>
            <div style="display:inline-block;width:85%;">
                <c:if test="${param.partyType != '' }">
                    <span class="tagbox-label" id="partyTypeTag">
                        <span title="${param.partyType0}">当事人类型</span>
                    <a href="javascript:;" onclick="thisRemove('partyType')" class="tagbox-remove"></a></span>
                </c:if>
                <c:if test="${param.caseSource != '' }">
                    <span class="tagbox-label" id="caseSourceTag">
                        <span title="${param.caseSource0}">案件来源</span>
                        <a href="javascript:;" onclick="thisRemove('caseSource')" class="tagbox-remove"></a>
                    </span>
                </c:if>
                <c:if test="${param.currentState != '' }">
                    <span class="tagbox-label" id="currentStateTag">
                        <span title="${param.currentState0}">办理状态</span>
                        <a href="javascript:;" onclick="thisRemove('currentState')" class="tagbox-remove"></a>
                    </span>
                </c:if>
                <c:if test="${param.closedState != '' }">
                    <span class="tagbox-label" id="closedStateTag">
                        <span title="${param.closedState0}">结案状态</span>
                    <a href="javascript:;" onclick="thisRemove('closedState')" class="tagbox-remove"></a></span>
                </c:if>
                <c:if test="${param.isMajorCase == '1' }">
                    <span class="tagbox-label" style="height: 26px; line-height: 26px;" id="isMajorCaseTag">
                    &nbsp;是否重大案件：是
                    <a href="javascript:;" onclick="thisRemove('isMajorCase')" class="tagbox-remove"></a></span>
                </c:if>
                <c:if test="${param.isMajorCase == '0' }">
                    <span class="tagbox-label" style="height: 26px; line-height: 26px;" id="isMajorCaseTag">
                    &nbsp;是否重大案件：否
                    <a href="javascript:;" onclick="thisRemove('isMajorCase')" class="tagbox-remove"></a></span>
                </c:if>
                <c:if test="${param.isCriminal == '1' }">
                    <span class="tagbox-label" style="height: 26px; line-height: 26px;" id="isCriminalTag">
                    &nbsp;是否涉刑：是
                    <a href="javascript:;" onclick="thisRemove('isCriminal')" class="tagbox-remove"></a></span>
                </c:if>
                <c:if test="${param.isCriminal == '0' }">
                    <span class="tagbox-label" style="height: 26px; line-height: 26px;" id="isCriminalTag">
                    &nbsp;是否涉刑：否
                    <a href="javascript:;" onclick="thisRemove('isCriminal')" class="tagbox-remove"></a></span>
                </c:if>
                <c:if test="${param.isFilingLegalReview == '1' }">
                    <span class="tagbox-label" style="height: 26px; line-height: 26px;" id="isFilingLegalReviewTag">
                    &nbsp;是否法制审核：是
                    <a href="javascript:;" onclick="thisRemove('isFilingLegalReview')" class="tagbox-remove"></a></span>
                </c:if>
                <c:if test="${param.isFilingLegalReview eq '0' }">
                    <span class="tagbox-label" style="height: 26px; line-height: 26px;" id="isFilingLegalReviewTag">
                    &nbsp;是否法制审核：否
                    <a href="javascript:;" onclick="thisRemove('isFilingLegalReview')" class="tagbox-remove"></a></span>
                </c:if>
                <c:if test="${param.isDepute eq '1' }">
                    <span class="tagbox-label" style="height: 26px; line-height: 26px;" id="isDeputeTag">
                    &nbsp;是否委托办案：是
                    <a href="javascript:;" onclick="thisRemove('isDepute')" class="tagbox-remove"></a></span>
                </c:if>
                <c:if test="${param.isDepute eq '0' }">
                    <span class="tagbox-label" style="height: 26px; line-height: 26px;" id="isDeputeTag">
                    &nbsp;是否委托办案：否
                    <a href="javascript:;" onclick="thisRemove('isDepute')" class="tagbox-remove"></a></span>
                </c:if>
                <c:if test="${param.isSubmit eq '1' }">
                    <span class="tagbox-label" style="height: 26px; line-height: 26px;" id="isSubmitTag">
                    &nbsp;提交状态：已提交
                    <a href="javascript:;" onclick="thisRemove('isSubmit')" class="tagbox-remove"></a></span>
                </c:if>
                <c:if test="${param.isSubmit eq '0' }">
                    <span class="tagbox-label" style="height: 26px; line-height: 26px;" id="isSubmitTag">
                    &nbsp;提交状态：未提交
                    <a href="javascript:;" onclick="thisRemove('isSubmit')" class="tagbox-remove"></a></span>
                </c:if>
                    <!-- <input class="easyui-tagbox" id="conditionTag" name="conditionTag" value="当事人：公民、法人,办理状态：已立案" style="width:1000px;border:0px solid #fff;" /> -->
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
                            <input type="text" name='beginfilingDate' id="beginfilingDate"
                                class="easyui-datebox" style="width:110px; height:30px;"/>
                        </td>
                        <td style="width: 35px; text-align: center;">至</td>
                        <td style="width: 110px; text-align: left;">
                            <input type="text" name='endfilingDate' id="endfilingDate"
                                class="easyui-datebox" style="width:110px; height:30px;"/>
                        </td>
                        <td style="width: 110px; text-align: right;">结案日期：</td>
                        <td style="width: 110px; text-align: left;">
                            <input type="text" name='beginclosedDate' id="beginclosedDate"
                                class="easyui-datebox" style="width:110px; height:30px;"/>
                        </td>
                        <td style="width: 35px; text-align: center;">至</td>
                        <td style="width: 110px; text-align: left;">
                            <input type="text" name='endclosedDate' id="endclosedDate"
                                class="easyui-datebox" style="width:110px; height:30px;"/>
                        </td>
                       <!--   <td style="text-align: right; padding-right: 5px; width: 150px;">
                            <a class="easyui-linkbutton" onclick="commonCaseSearch()"><i class="fa fa-search"></i> 查 询</a>
                            <a class="easyui-linkbutton" onclick="commonCaseRefresh()"><i class="fa fa-trash-o"></i> 重 置</a>
                        </td> --> 
                    </tr>
                    <tr class="common-line-height"></tr>
                    <tr>
                        <td style="width: 76px; text-align: right;">执法人员：</td>
                        <td style="width: 140px; text-align: left;">
                            <input name="officeName" id="officeName" class="easyui-textbox" style="width: 140px; height:30px;"/>
                        </td>
                        <td style="width: 110px; text-align: right;">执法证号：</td>
                        <td style="width: 255px; text-align: left;" colspan="3">
                            <select name="cardCode" id="cardCode" class="easyui-textbox" style="width: 100%; height:30px;">
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
                            <input type="text" name='beginpunishDate' id="beginpunishDate"
                                class="easyui-datebox" style="width:110px; height:30px;"/>
                        </td>
                        <td style="width: 35px; text-align: center;">至</td>
                        <td style="width: 110px; text-align: left;">
                            <input type="text" name='endpunishDate' id="endpunishDate"
                                class="easyui-datebox" style="width:110px; height:30px;"/>
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
                            <input type="text" name='beginpunishExecutDate' id="beginpunishExecutDate"
                                class="easyui-datebox" style="width:110px; height:30px;"/>
                        </td>
                        <td style="width: 35px; text-align: center;">至</td>
                        <td style="width: 110px; text-align: left;">
                            <input type="text" name='endpunishExecutDate' id="endpunishExecutDate"
                                class="easyui-datebox" style="width:110px; height:30px;"/>
                        </td>
                        <td style="width: 110px; text-align: right;">职权编号：</td>
                        <td style="width: 255px; text-align: left;" colspan="3">
                            <input type="text" name='powerCode' id="powerCode"
                                class="easyui-textbox" style="width:100%; height:30px;"/>
                        </td>
                        <td style="text-align: right; padding-right: 5px; width: 150px;"></td>
                    </tr>
                    <!-- <tr class="common-line-height"></tr>
                    <tr>
                        <td style="width: 76px; text-align: right;">处罚类型：</td>
                        <td  style="text-align: left;" colspan="5">
                            <select name="isSubmit" id="isSubmit" class="easyui-combobox" style="width: 100%; height:30px;">
                            </select>
                        </td>
                        <td style="width: 110px; text-align: right;">处罚金额：</td>
                        <td style="width: 110px; text-align: left;">
                            <input type="text" name='createStartdateStr' id="createStartdateStr"
                                class="easyui-textbox" style="width:110px; height:30px;"/>
                        </td>
                        <td style="width: 35px; text-align: center;">-</td>
                        <td style="width: 110px; text-align: left;">
                            <input type="text" name='createEndDateStr' id="createEndDateStr"
                                class="easyui-textbox" style="width:110px; height:30px;"/>
                        </td>
                        <td style="text-align: right; padding-right: 5px; width: 150px;"></td>
                    </tr> -->
                    <tr class="common-line-height"></tr>
                    </table>
                    </div>
                
            </form>
        </div>
        <div id="optionCom">
            <span class="isshow"><i class="optional fa fa-bars fa-lg"></i>可选列  </span> 
            <span class="tip">可根据需要"隐藏/显示"所选列</span> 
            <ul class="panList"></ul>
        </div>
    </div>
    <table fit="true" style="width: 99%;" id="common_case_index_datagrid">
    </table>
    <script type="text/javascript" src="<%=contextPath%>/common/js/juicer-min.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/caseManager/commonCaseSearch/js/common_case_index.js"></script>
</body>
<iframe id="frame0" style="display:none"></iframe>
</html>
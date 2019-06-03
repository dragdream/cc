<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/pureText-lookInfo.css"/>
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<link rel="stylesheet" type="text/css" href="/supervise/caseManager/commonCase/css/caseLook.css" />
<script type="text/javascript" src="<%=contextPath%>/supervise/adminCoercion/coercionSearch/js/coercionSearch_seeInfo.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/adminCoercion/js/coercion_add.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<title>行政强制管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
</head>
<body onload="doInit()" style="padding:10px 10px">
    <div id="measureInput_table_Div" style="width: 100%; height: 98%; ">
<%--         <input type="hidden" id="coercionCaseId" value="<c:out value='${sessionScope.baseInfo.id}'/>" />  --%>
        <input type="hidden" id="srcCaseId" value="${param.id}"/>
        <input type="hidden" id="isForce" value="${param.isForce}"/>
        <input type="hidden" id="punishDecisionExecuteWay" value="${param.punishDecisionExecuteWay}"/>
        
        <div id="coercion_manage_page" class="easyui-tabs" style="width: 100%; height: 93%; ">
            <div title="案件信息" style="padding: 10px" id="case_base_inportment" >
                <div class="easyui-panel" title="基础信息" style="width: 100%;" align="center" id="baseDiv">
                    <table class="TableBlock_page lookInfo-lowHeight-table" style="width:100%;table-layout: fixed;word-wrap: break-word; word-break: break-all;">
                        <tr class="common-tr-border">
                            <td class="case-common-filing-look-td-class1 font-bold-label-td">案件编号：</td>
                            <td class="case-common-filing-look-td-class2" id="caseCode"></td>
                            <td class="case-common-filing-look-td-class1 font-bold-label-td">执法主体：</td>
                            <td class="case-common-filing-look-td-class2" id="subjectName"></td>
                            <td class="case-common-filing-look-td-class3"></td>
                        </tr>
                        <tr class="common-tr-border">
                            <td class="case-common-filing-look-td-class1 font-bold-label-td">案件来源：</td>
                            <td class="case-common-filing-look-td-class2" id="caseSourceValue">
                            </td>
                            <td class="case-common-filing-look-td-class1 font-bold-label-td">来源日期：</td>
                            <td class="case-common-filing-look-td-class2" id="sourceDateStr">
                            </td>
                            <td class="case-common-filing-look-td-class3"></td>
                        </tr>
                        <tr class="common-tr-border">
                            <td class="case-common-filing-look-td-class1 font-bold-label-td">立案批准日期：</td>
                            <td class="case-common-filing-look-td-class2" id="filingDateStr">
                            </td>
                            <td class="case-common-filing-look-td-class1 font-bold-label-td"></td>
                            <td class="case-common-filing-look-td-class2"></td>
                            <td class="case-common-filing-look-td-class3"></td>
                        </tr>
                        <tr class="common-tr-border">
                            <td class="case-common-filing-look-td-class1 font-bold-label-td">案由（事由）：</td>
                            <td colspan="3" id="name">
                            </td>
                            <td class="case-common-filing-look-td-class3"></td>
                        </tr>
                        <tr class="common-tr-border">
                            <td class="case-common-filing-look-td-class1 font-bold-label-td">案情介绍：</td>
                            <td colspan="3" id="caseIntroduce">
                            </td>
                            <td class="case-common-filing-look-td-class3"></td>
                        </tr>
                        <tr class="common-tr-border">
                            <td class="case-common-filing-look-td-class1 font-bold-label-td">立案呈批表：</td>
                            <td class="" colspan="3" id="filingApprovalDocumentName"></td>
                            <td class="case-common-filing-look-td-class3"></td>
                        </tr>
                    </table>
                </div>
                <br />
                <%-- 立案-当事人 --%>
                <div class="easyui-panel" title="当事人" style="width: 100%;" align="center">
                    <table class="TableBlock_page lookInfo-lowHeight-table" style="width: 100%;">
                        <tr class="common-tr-border">
                            <td class="case-common-filing-look-td-class1 font-bold-label-td">当事人类型：</td>
                            <td class="case-common-filing-look-td-class2" id="partyTypeValue">
                            </td>
                            <td class="case-common-filing-look-td-class1 font-bold-label-td">当事人名称：</td>
                            <td class="case-common-filing-look-td-class2" id="partyName">
                            </td>
                            <td class="case-common-filing-look-td-class3"></td>
                        </tr>
                        <tr class="common-tr-border">
                            <td class="case-common-filing-look-td-class1 font-bold-label-td">证件类型：</td>
                            <td class="case-common-filing-look-td-class2" id="cardTypeValue"></td>
                            <td class="case-common-filing-look-td-class1 font-bold-label-td">证件号码：</td>
                            <td class="case-common-filing-look-td-class2" id="cardCode">
                            </td>
                            <td class="case-common-filing-look-td-class3"></td>
                        </tr>
                        <tr class="common-tr-border">
                            <td class="case-common-filing-look-td-class1 font-bold-label-td">联系电话：</td>
                            <td class="case-common-filing-look-td-class2" id="contactPhone"></td>
                            <td class="case-common-filing-look-td-class1 font-bold-label-td">住所（住址）：</td>
                            <td class="case-common-filing-look-td-class2" id="address"></td>
                            <td class="case-common-filing-look-td-class3"></td>
                        </tr>
                        <tr class="common-tr-border" id="adressTr" style="display: none;">
                            <td class="case-common-filing-look-td-class1 font-bold-label-td">注册地址：</td>
                            <td class="case-common-filing-look-td-class2" id="registerAddress"></td>
                            <td class="case-common-filing-look-td-class1 font-bold-label-td">经营地址：</td>
                            <td class="case-common-filing-look-td-class2" id="businessAddress"></td>
                            <td class="case-common-filing-look-td-class3"></td>
                        </tr>
                    </table>
                </div>
                <br/>
                <%-- 立案-执法人员 --%>
                <div class="easyui-panel" title="执法人员" style="width: 100%; height: 220px;">
                    <table fit="true" style="width: 99%;" id="common_case_add_person_datagrid"></table>
                </div>
            </div>
            <div title="强制措施" style="padding: 10px;" id="case_measure" >
                <!-- <div class="titlebar clearfix">
                    <div id="outwarp">
                        <div class=" fr" >
                              <button class="easyui-linkbutton" onclick="doOpenNewMeasurePage()">
                                   <i class="fa fa-plus"></i>&nbsp;&nbsp;新增
                              </button>
                        </div>
                    </div>
                    <span class="basic_border"></span>  
                </div> --><!-- title="案件信息" style="padding: 10px" id="case_base_inportment" -->
                <div fit="true" title="强制措施" class="easyui-panel case-common-panel-body1" align="center" data-options="tools:'#coercion_add_measure'">
                    <div id="coercion_add_measure">
                        <a href="javascript:void(0);" class="icon-add" onclick="editOpenMeasurePage(null,'新增')"><i
                            class="fa fa-plus "></i></a>
                    </div>
                    <table fit="true" style="width: 100%;" id="measureSee_datagrid">
                    </table>
                </div>
                
            </div>
            <div title="行政机关执行" style="padding: 10px;" id="case_perform">
                <!-- <div class="titlebar clearfix">
                    <div id="outwarp">
                        <div class=" fr">
                            <button  class="easyui-linkbutton" onclick="editOpenPerformPage(null,'新增')">
                                 <i class="fa fa-plus"></i>&nbsp;&nbsp;新增
                            </button> 
                      </div>
                    </div>
                    <span class="basic_border"></span>  
                </div> -->
                <div fit="true" title="行政机关执行" class="easyui-panel"  align="center" data-options="tools:'#coercion_add_perform'">
                    <div id="coercion_add_perform">
                        <a href="javascript:void(0);" class="icon-add" onclick="editOpenPerformPage(null,'新增');"><i
                            class="fa fa-plus "></i></a>
                    </div>
                    <table fit="true" style="width: 100%;" id="self_performSee_datagrid">
                    </table>
                </div>
            </div>
            <div title="申请法院强制执行" style="padding: 10px;" id="case_court_perform">
                <!-- <div class="titlebar clearfix" id="countPerformAdd_button">
                    <div id="outwarp">
                        <div class=" fr">
                            <button  class="easyui-linkbutton" onclick="doOpenCountPerformPage(null,'新增')">
                                 <i class="fa fa-plus"></i>&nbsp;&nbsp;新增
                            </button>
                        </div>
                    </div>
                    <span class="basic_border"></span>  
                </div> -->
                
                <div fit="true" title="申请法院强制执行" class="easyui-panel" align="center" data-options="tools:'#coercion_add_count_perform'">
                    <div id="coercion_add_count_perform">
                        <div id="countPerformAdd_button">
                            <a href="javascript:void(0);" class="icon-add" onclick="doOpenCountPerformPage(null,'新增');"><i
                                class="fa fa-plus "></i></a>
                        </div>
                    </div>
                    <table fit="true" style="width: 100%;" id="court_performSee_datagrid">
                    </table>
                </div>
            </div>
        </div>
        <div id="simpleBtn" align="center" class="iframeBtns clearfix" style="border-bottom-right-radius: 4px;border-bottom-left-radius: 4px;text-align: center;width:100%;padding: 10px 0;background-color:#fff;float: right;">
            <button class="easyui-linkbutton" title="返回" onclick="back();">
                <span style="padding-right: 2px; width: 40px;"><i class="fa fa-reply"></i> 返回</span>
            </button>
        </div>
    </div>
</body>
</html>
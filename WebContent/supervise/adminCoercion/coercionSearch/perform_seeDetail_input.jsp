<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="java.util.*,java.text.SimpleDateFormat"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/upload.jsp"%>

<script type="text/javascript" src="<%=contextPath%>/supervise/adminCoercion/coercionSearch/js/perform_seeDetail_input.js"></script>

<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>


<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/power/css/power.css" />
<link rel="stylesheet" type="text/css" href="/supervise/adminCoercion/css/adminCoercion.css" />
<link rel="stylesheet" type="text/css" href="/supervise/caseManager/commonCase/css/case.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/pureText-lookInfo.css" />
<title>行政强制行为</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body onload="performSelfEdit()">
    <%-- <div style="width: 100%; height: 5%; padding-right: 10px; padding-left: 10px; padding-top: 5px">
        <div id="toolbar_edit" class="titlebar clearfix">
            <div class="fl left">
                <img id="img1" class='title_img' src="<%=contextPath%>/common/zt_webframe/imgs/xzbg/clgl/clsqcx.png" />
                <span class="title">行政强制行为</span>
            </div>
            <div class=" fr">
                <button class="easyui-linkbutton" onclick="doOpenListPage()">
                    <i class="fa fa-mail-reply"></i>&nbsp;&nbsp;返回&nbsp;&nbsp;
                </button>
                &nbsp;&nbsp;
            </div>
        </div>
        <span class="basic_border"></span> 
    </div> --%>
    <div  style="width: 100%; height: 100%; padding-right: 10px; padding-left: 10px; padding-top: 5px" align="center">
        <input type="hidden" id="performId" name="performId" value='<c:out value="${performInfo.id}"/>'/> 
        <input type="hidden" id="performType" name="performType" value='<c:out value="${performInfo.performType}"/>'/> 
        <input type="hidden" id="caseSourceId" name="caseSourceId" value='<c:out value="${baseInfo.caseSourceId}"/>' />
        <input type="hidden" id="caseSourceType" name="caseSourceType" value='<c:out value="${baseInfo.caseSourceType}"/>' />
        <input type="hidden" id="coercionCaseId" name="coercionCaseId" value='<c:out value="${baseInfo.coercionCaseId}"/>' />
        <input type="hidden" id="subjectId" name="subjectId" value='<c:out value="${baseInfo.subjectId}"/>' />
        <input type="hidden" id="departmentId" name="departmentId" value='<c:out value="${baseInfo.departmentId}"/>' />
        <input type="hidden" id="enforceStep" name="enforceStep" value='<c:out value="${performInfo.enforceStep}"/>' />
        <input type="hidden" id="caseCode" name="caseCode" value='<c:out value="${baseInfo.caseCode}"/>' />
    	
        <div class="easyui-tabs" style="width: 100%; height: 100%; padding-right: 10px; padding-left: 10px; padding-top: 5px">
            <div title="执行方式" style="padding: 10px">
                <table class="TableBlock lookInfo-lowHeight-table" style="width: 95%; background: #fff;">
                    <tr class="none-border-tr">
                        <td class="seeInfo-perform-td-label font-bold-label-td">行政强制执行方式:</td>
                        <td class="perform-td-content-md"><c:out value="${performInfo.performTypeStr}"/></td>
                        <td class="seeInfo-perform-td-label font-bold-label-td"></td>
                        <td class="perform-td-content-md"></td>
                    </tr>
                </table>
            </div >
            <div title="催告" style="padding: 10px">
                <table class="TableBlock lookInfo-lowHeight-table" style="width: 95%; background: #fff;">
                    <tr class="none-border-tr">
                        <td class="seeInfo-perform-td-label font-bold-label-td">原决定书文号:</td>
                        <td class="perform-td-content-md"><c:out value="${performInfo.punishCodeBefore}"/></td>
                        <td class="seeInfo-perform-td-label font-bold-label-td">原决定书日期:</td>
                        <td class="perform-td-content-md">
                            <c:if test="${performInfo.punishDateBeforeStr != null && performInfo.punishDateBeforeStr != 'null'}">
                                <c:out value="${performInfo.punishDateBeforeStr}"/>
                            </c:if>
                        </td>
                    </tr>
                    <tr class="none-border-tr" <c:if test="${performInfo.performType != '100'}"> style="display: none;" </c:if>>
                        <td class="seeInfo-perform-td-label font-bold-label-td">原决定缴纳金额:</td>
                        <td class="perform-td-content-md"><c:out value="${performInfo.originalMoney}"/></td>
                        <td class="seeInfo-perform-td-label font-bold-label-td">原决定缴纳截止日期:</td>
                        <td class="perform-td-content-md">
                            <c:if test="${performInfo.originalDateStr != null && performInfo.originalDateStr != 'null'}">
                                <c:out value="${performInfo.originalDateStr}"/>
                            </c:if>
                        </td>
                    </tr>
                    <tr class="none-border-tr" <c:if test="${performInfo.performType != '100'}"> style="display: none;" </c:if>>
                        <td class="seeInfo-perform-td-label font-bold-label-td">原决定缴纳金额:</td>
                        <td class="perform-td-content-md"><c:out value="${performInfo.addFindMoney}"/></td>
                        <td class="seeInfo-perform-td-label font-bold-label-td">原决定缴纳截止日期:</td>
                        <td class="perform-td-content-md">
                            <c:if test="${performInfo.originalDateStr != null && performInfo.originalDateStr != 'null'}">
                                <c:out value="${performInfo.originalDateStr}"/>
                            </c:if>
                        </td>
                    </tr>
                    <tr class="none-border-tr" <c:if test="${performInfo.performType != '100'}"> style="display: none;" </c:if>>
                        <td class="seeInfo-perform-td-label font-bold-label-td">加处罚款或滞纳金额:</td>
                        <td class="perform-td-content-md"><c:out value="${performInfo.originalMoney}"/></td>
                        <td class="seeInfo-perform-td-label font-bold-label-td"></td>
                        <td class="perform-td-content-md"></td>
                    </tr>
                    <tr class="none-border-tr">
                        <td class="seeInfo-perform-td-label font-bold-label-td">催告书送达日期:</td>
                        <td class="perform-td-content-md">
                            <c:if test="${performInfo.pressSendDateStr != null && performInfo.pressSendDateStr != 'null'}">
                                <c:out value="${performInfo.pressSendDateStr}"/>
                            </c:if>
                        </td>
                        <td class="seeInfo-perform-td-label font-bold-label-td">送达方式:</td>
                        <td class="perform-td-content-md"><c:out value="${performInfo.pressSendType}"/></td>
                    </tr>
                    <tr class="none-border-tr" <c:if test="${performInfo.performType != '500'}"> style="display: none;" </c:if>>
                        <td class="seeInfo-perform-td-label font-bold-label-td">二次催告书送达日期:</td>
                        <td class="perform-td-content-md">
                            <c:if test="${performInfo.secondPressDateStr != null && performInfo.secondPressDateStr != 'null'}">
                                <c:out value="${performInfo.secondPressDateStr}"/>
                            </c:if>
                        </td>
                        <td class="seeInfo-perform-td-label font-bold-label-td">送达方式:</td>
                        <td class="perform-td-content-md"><c:out value="${performInfo.secondPressType}"/></td>
                    </tr>
                </table>
            </div>
            <div title="申请与批准" style="padding: 10px">
                <table class="TableBlock" style="width: 95%; background: #fff;">
                    <tr class="none-border-tr">
                        <td class="seeInfo-perform-td-label font-bold-label-td">申请日期:</td>
                        <td class="perform-td-content-md">
                            <c:if test="${performInfo.applyDateStr != null && performInfo.applyDateStr != 'null'}">
                                <c:out value="${performInfo.applyDateStr}"/>
                            </c:if>
                        </td>
                        <td class="seeInfo-perform-td-label font-bold-label-td">批准日期:</td>
                        <td class="perform-td-content-md">
                            <c:if test="${performInfo.approveDateStr != null && performInfo.approveDateStr != 'null'}">
                                <c:out value="${performInfo.approveDateStr}"/>
                            </c:if>
                        </td>
                    </tr>
                    <tr class="none-border-tr">
                        <td class="seeInfo-perform-td-label font-bold-label-td">批准人:</td>
                        <td class="perform-td-content-md"><c:out value="${performInfo.approvePerson}"/></td>
                        <td class="seeInfo-perform-td-label font-bold-label-td"></td>
                        <td class="perform-td-content-md"></td>
                    </tr>
                </table>
                <br/>
                <div class="easyui-panel" title="强制职权" style="width: 95%; height:220px" align="center">
                        <table fit="true" style="width: 100%;" id="perform_power_table"> </table>
                </div>
                <br />
                <div class="easyui-panel" title="强制依据" style="width: 95%; height:220px" align="center">
                    <table fit="true" style="width: 100%;" id="perform_gist_table"></table>
                </div>
            </div>
            <div title="事项管理" style="padding:10px">
                    <table class="TableBlock lookInfo-lowHeight-table" style="width: 95%; background: #fff;">
                        <tr class="none-border-tr">
                            <td class="seeInfo-perform-td-label font-bold-label-td">强制执行决定书文号:</td>
                            <td class="perform-td-content-md"><c:out value="${performInfo.coercionPerformCode}"/></td>
                            <td class="seeInfo-perform-td-label font-bold-label-td">实施强制执行日期:</td>
                            <td class="perform-td-content-md">
                                <c:if test="${performInfo.performEnforceDateStr != null && performInfo.performEnforceDateStr != 'null'}">
                                    <c:out value="${performInfo.performEnforceDateStr}"/>
                                </c:if>
                            </td>
                        </tr>
                        <tr class="none-border-tr">
                            <td class="seeInfo-perform-td-label font-bold-label-td">强制执行决定书送达日期:</td>
                            <td class="perform-td-content-md">
                                <c:if test="${performInfo.decideSendDateStr != null && performInfo.decideSendDateStr != 'null'}">
                                    <c:out value="${performInfo.decideSendDateStr}"/>
                                </c:if>
                            </td>
                            <td class="seeInfo-perform-td-label font-bold-label-td">送达方式:</td>
                            <td class="perform-td-content-md"><c:out value="${performInfo.decideSendType}"/></td>
                        </tr>
                        <tr class="none-border-tr" <c:if test="${performInfo.performType != '200'}"> style="display: none;" </c:if>>
                            <td class="seeInfo-perform-td-label font-bold-label-td">划拨金额:</td>
                            <td class="perform-td-content-md">
                                <c:out value="${performInfo.transDeposit}"/>
                            </td>
                            <td class="seeInfo-perform-td-label font-bold-label-td">划拨金融机构名称:</td>
                            <td class="perform-td-content-md"><c:out value="${performInfo.transOrganization}"/></td>
                        </tr>
                        <tr class="none-border-tr" <c:if test="${performInfo.performType != '200'}"> style="display: none;" </c:if>>
                            <td class="seeInfo-perform-td-label font-bold-label-td">划拨通知书送达日期:</td>
                            <td class="perform-td-content-md">
                                <c:if test="${performInfo.transNoticeDateStr != null && performInfo.transNoticeDateStr != 'null'}">
                                    <c:out value="${performInfo.transNoticeDateStr}"/>
                                </c:if>
                            </td>
                            <td class="seeInfo-perform-td-label font-bold-label-td"></td>
                            <td class="perform-td-content-md"></td>
                        </tr>
                        <tr class="none-border-tr" <c:if test="${performInfo.performType != '300'}"> style="display: none;" </c:if>>
                            <td class="seeInfo-perform-td-label font-bold-label-td">拍卖地点:</td>
                            <td class="perform-td-content-md"><c:out value="${performInfo.auctionAddr}"/></td>
                            <td class="seeInfo-perform-td-label font-bold-label-td">拍卖日期:</td>
                            <td class="perform-td-content-md">
                                <c:if test="${performInfo.auctionDateStr != null && performInfo.auctionDateStr != 'null'}">
                                    <c:out value="${performInfo.auctionDateStr}"/>
                                </c:if>
                            </td>
                        </tr>
                        <tr class="none-border-tr" <c:if test="${performInfo.performType != '300'}"> style="display: none;" </c:if>>
                            <td class="seeInfo-perform-td-label font-bold-label-td">拍卖所得处理:</td>
                            <td colspan="3" class="perform-td-content-md"><c:out value="${performInfo.auctionUse}"/></td>
                        </tr>
                        <tr class="none-border-tr" <c:if test="${performInfo.performType != '500'}"> style="display: none;" </c:if>>
                            <td class="seeInfo-perform-td-label font-bold-label-td">代履行人:</td>
                            <td class="perform-td-content-md"><c:out value="${performInfo.replaceObject}"/></td>
                            <td class="seeInfo-perform-td-label font-bold-label-td">代履行费用:</td>
                            <td class="perform-td-content-md"><c:out value="${performInfo.replaceDeposit}"/></td>
                        </tr>
                        <tr class="none-border-tr" <c:if test="${performInfo.performType != '500'}"> style="display: none;" </c:if>>
                            <td class="seeInfo-perform-td-label font-bold-label-td">监督人:</td>
                            <td class="perform-td-content-md"><c:out value="${performInfo.replaceObject}"/></td>
                            <td class="seeInfo-perform-td-label font-bold-label-td">代履行日期:</td>
                            <td class="perform-td-content-md">
                                <c:if test="${performInfo.replaceEnforceDateStr != null && performInfo.replaceEnforceDateStr != 'null'}">
                                    <c:out value="${performInfo.replaceEnforceDateStr}"/>
                                </c:if>
                            </td>
                        </tr>
                        
                    </table>
                    <br/>
                    <div <c:if test='${performInfo.isAgreementEnforce != 1}'>style ="display: none;"</c:if>>
                    <div class="easyui-panel" title="执行协议" style="width: 100%; height:auto;" align="center">
                        <table class="TableBlock lookInfo-lowHeight-table" style="width: 95%; background: #fff;">
                            <tr class="none-border-tr">
                                <td class="seeInfo-perform-td-label font-bold-label-td">协议签订日期:</td>
                                <td class="perform-td-content-md">
                                    <c:if test="${performInfo.agreeSignDateStr != null && performInfo.agreeSignDateStr != 'null'}">
                                        <c:out value="${performInfo.agreeSignDateStr}"/>
                                    </c:if>
                                </td>
                                <td class="seeInfo-perform-td-label font-bold-label-td"></td>
                                <td class="perform-td-content-md"></td>
                            </tr>
                            <tr class="none-border-tr">
                                <td class="seeInfo-perform-td-label font-bold-label-td">协议内容:</td>
                                <td colspan="3" class="perform-td-content-md"><c:out value="${performInfo.agreeContent}"/></td>
                            </tr>
                            <tr class="none-border-tr">
                                <td class="seeInfo-perform-td-label font-bold-label-td">协议执行情况:</td>
                                <td colspan="3" class="perform-td-content-md"><c:out value="${performInfo.agreeEnforceCondition}"/></td>
                            </tr>
                        </table>
                    </div>
                    <br/>
                    </div>
                    
                    <div <c:if test='${performInfo.isEnforceReturn != 1}'>style ="display: none;"</c:if>>
                    <div class="easyui-panel" title="执行回转" style="width: 100%; height:auto;"align="center">
                        <table class="TableBlock lookInfo-lowHeight-table" style="width: 95%; background: #fff;">
                            <tr class="none-border-tr">
                                <td class="seeInfo-perform-td-label font-bold-label-td">执行回转日期:</td>
                                <td class="perform-td-content-md">
                                    <c:if test="${performInfo.enforceReturnDateStr != null && performInfo.enforceReturnDateStr != 'null'}">
                                        <c:out value="${performInfo.enforceReturnDateStr}"/>
                                    </c:if>
                                </td>
                                <td class="seeInfo-perform-td-label font-bold-label-td"></td>
                                <td class="perform-td-content-md"></td>
                            </tr>
                            <tr class="none-border-tr">
                                <td class="seeInfo-perform-td-label font-bold-label-td">执行回转原因:</td>
                                <td colspan="3" class="perform-td-content-md"><c:out value="${performInfo.enforceReturnReason}"/></td>
                            </tr>
                            <tr class="none-border-tr">
                                <td class="seeInfo-perform-td-label font-bold-label-td">执行回转情况:</td>
                                <td colspan="3" class="perform-td-content-md"><c:out value="${performInfo.enforceReturnContent}"/></td>
                            </tr>
                        </table>
                    </div>
                    <br/>
                    </div>
                    
                    <div <c:if test='${performInfo.isEndEnforce != 1}'>style ="display: none;"</c:if>>
                        <div class="easyui-panel" title="终结执行" style="width: 100%; height:auto;" align="center">
                            <table class="TableBlock lookInfo-lowHeight-table" style="width: 95%; background: #fff;">
                                <tr class="none-border-tr">
                                    <td class="seeInfo-perform-td-label font-bold-label-td">通知书送达日期:</td>
                                    <td class="perform-td-content-md">
                                        <c:if test="${performInfo.endEnforceSendDateStr != null && performInfo.endEnforceSendDateStr != 'null'}">
                                            <c:out value="${performInfo.endEnforceSendDateStr}"/>
                                        </c:if>
                                    </td>
                                    <td class="seeInfo-perform-td-label font-bold-label-td">送达方式:</td>
                                    <td class="perform-td-content-md"><c:out value="${performInfo.endSendType}"/></td>
                                </tr>
                                <tr class="none-border-tr" >
                                    <td class="power-table-label font-bold-label-td">终结原因：</td>
                                    <td colspan="3" class="perform-td-content-md"><c:out value="${performInfo.enforceEndReason}"/></td>
                                </tr>
                            </table>
                        </div>
                        <br/>
                    </div>
                    
                    <div <c:if test='${performInfo.isBreakEnforce != 1}'>style ="display: none;"</c:if>>
                    <div class="easyui-panel" title="中止执行" style="width: 100%; height:auto;"align="center">
                        <table class="TableBlock lookInfo-lowHeight-table" style="width: 95%; background: #fff;">
                            <tr class="none-border-tr">
                                <td class="seeInfo-perform-td-label font-bold-label-td">中止执行日期:</td>
                                <td class="perform-td-content-md">
                                    <c:if test="${performInfo.breakEnforceDateStr != null && performInfo.breakEnforceDateStr != 'null'}">
                                        <c:out value="${performInfo.breakEnforceDateStr}"/>
                                    </c:if>
                                </td>
                                <td class="seeInfo-perform-td-label font-bold-label-td">恢复执行日期:</td>
                                <td class="perform-td-content-md">
                                    <c:if test="${performInfo.relwaseEnforceDateStr != null && performInfo.relwaseEnforceDateStr != 'null'}">
                                        <c:out value="${performInfo.relwaseEnforceDateStr}"/>
                                    </c:if>
                                </td>
                            </tr>
                            <tr class="none-border-tr">
                                <td class="seeInfo-perform-td-label font-bold-label-td">中止原因:</td>
                                <td colspan="3" class="perform-td-content-md"><c:out value="${performInfo.enforceBreakReason}"/></td>
                            </tr>
                        </table>
                    </div>
                   <br/>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
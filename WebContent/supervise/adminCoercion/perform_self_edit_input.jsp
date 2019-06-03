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

<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/power/css/power.css" />
<link rel="stylesheet" type="text/css" href="/supervise/adminCoercion/css/adminCoercion.css" />

<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/adminCoercion/js/perform_self_edit_input.js"></script>

<title>行政强制行为</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body onload="performSelfEdit()">
    <div style="width: 100%; height: 10%; padding-right: 10px; padding-left: 10px; padding-top: 5px">
        <div id="toolbar_edit" class="titlebar clearfix">
            <div id="outwarp">
                <c:if test="${baseInfo.sourcePage == 'perform'}">    
                    <div class="fl left">
                        <img id="img1" class='title_img' src="<%=contextPath%>/common/zt_webframe/imgs/xzbg/clgl/clsqcx.png" />
                        <span class="title">行政强制执行</span>
                    </div>
                </c:if>
                <c:if test="${baseInfo.sourcePage != 'perform'}">
                    <div class=" fr">
                        <button class="easyui-linkbutton" onclick="doOpenListPage()">
                            <i class="fa fa-mail-reply"></i>&nbsp;&nbsp;返回列表&nbsp;&nbsp;
                        </button>
                        &nbsp;&nbsp;
                    </div>
                </c:if>
                
            </div>
            <span class="basic_border"></span> 
        </div>
    </div>
    <div class="easyui-tabs" id="performInput_edit_Div" style="width: 100%; height: 90%; padding-right: 10px; padding-left: 10px; padding-top: 5px">
        <div title="行政强制执行方式" style="padding: 10px">
            <form role="form" id="selfPerformInput_form1" name="form1" enctype="multipart/form-data" method="post">
                <input type="hidden" id="performId" name="performId" value='<c:out value="${performInfo.id}"/>'/> 
                <input type="hidden" id="performType" name="performType" value='<c:out value="${performInfo.performType}"/>'/> 
                <input type="hidden" id="caseSourceId" name="caseSourceId" value='<c:out value="${baseInfo.caseSourceId}"/>' />
                <input type="hidden" id="caseSourceType" name="caseSourceType" value='<c:out value="${baseInfo.caseSourceType}"/>' />
                <input type="hidden" id="coercionCaseId" name="coercionCaseId" value='<c:out value="${baseInfo.coercionCaseId}"/>' />
                <input type="hidden" id="subjectId" name="subjectId" value='<c:out value="${baseInfo.subjectId}"/>' />
                <input type="hidden" id="departmentId" name="departmentId" value='<c:out value="${baseInfo.departmentId}"/>' />
                <input type="hidden" id="enforceStep" name="enforceStep" value='<c:out value="${performInfo.enforceStep}"/>' />
                <input type="hidden" id="caseCode" name="caseCode" value='<c:out value="${baseInfo.caseCode}"/>' />
                <table class="TableBlock" style="width: 99%; background: #fff;">
                    <tr>
                        <td style="padding-left: 15%;">
                            <ul style="width: 500px;" id="performInput_performType">
                                <li><input class="easyui-radiobutton"
                                    <c:if test="${performInfo.performType == '100'}"> checked </c:if> name="performType"
                                    value="100"><span>&nbsp;&nbsp;加处罚款或滞纳金</span></li>
                                <li><input class="easyui-radiobutton"
                                    <c:if test="${performInfo.performType == '200'}"> checked </c:if> name="performType"
                                    value="200"><span>&nbsp;&nbsp;划拨存款、汇款</span></li>
                                <li><input class="easyui-radiobutton"
                                    <c:if test="${performInfo.performType == '300'}"> checked </c:if> name="performType"
                                    value="300"><span>&nbsp;&nbsp;拍卖或者依法处理查封、扣押的场所、设施或者财物</span></li>
                                <li><input class="easyui-radiobutton"
                                    <c:if test="${performInfo.performType == '400'}"> checked </c:if> name="performType"
                                    value="400"><span>&nbsp;&nbsp;排除妨碍、恢复原状</span></li>
                                <li><input class="easyui-radiobutton"
                                    <c:if test="${performInfo.performType == '500'}"> checked </c:if> name="performType"
                                    value="500"><span>&nbsp;&nbsp;代履行</span></li>
                                <li><input class="easyui-radiobutton"
                                    <c:if test="${performInfo.performType == '900'}"> checked </c:if> name="performType"
                                    value="900"><span>&nbsp;&nbsp;其他行政强制措施</span></li>
                            </ul>
                        </td>
                    </tr>
                </table>
                <!-- <div style="text-align: center; padding-top: 10px;">
                    <button type="button" class="easyui-linkbutton" title="保存" onclick="savePerformType()">
                        <span style="padding-right: 2px; width: 40px;"><i class="fa fa-save"></i> 保存</span>
                    </button>
                </div> -->
            </form>
        </div>
        <div title="催告" style="padding: 10px">
            <div id="perform_press_panel" class="easyui-panel" style="width: 99%; overflow: auto;">
                <form role="form" id="performPress_form" name="form1" enctype="multipart/form-data" method="post" data-options="novalidate:true">
                <table id="performSelfEditInput_press_table" class="TableBlock" style="width: 100%; background: #fff;">
                        <tr>
                            <td class="power-table-label" style="width: 200px;">原决定书文号：</td>
                            <td style="width: 160px;"><input class="easyui-textbox" name="punishCodeBefore"
                                value='<c:out value="${performInfo.punishCodeBefore}"/>' style="width: 90%;" /></td>
                            <td class="power-table-label" style="width: 200px;">原决定书日期：</td>
                            <td style="width: 160px;"><input class="easyui-datebox" name="punishDateBeforeStr" id="punishDateBeforeStr"
                                data-options="validType:'date', novalidate:true"
                               <c:if test="${performInfo.punishDateBeforeStr != 'null'}">
                                 value='<c:out value="${performInfo.punishDateBeforeStr}"/>'
                                </c:if> style="width: 90%;" /></td>
                        </tr>
                        <tr class="imposeFine-info-tr" style="display: none;">
                            <td class="power-table-label">原决定缴纳金额：</td>
                            <td style="width: 160px;"><input style="width: 90%;" id="originalMoney" class="easyui-textbox"
                                data-options="validType:'money', novalidate:true,"
                                name="originalMoney" value='<c:out value="${performInfo.originalMoney}"/>' /></td>
                            <td class="power-table-label">原决定缴纳截止日期：</td>
                            <td style="width: 160px;"><input class="easyui-datebox" name="originalDateStr" id="originalDateStr"
                                data-options="validType:'date', novalidate:true,"
                                <c:if test="${performInfo.originalDateStr != 'null'}">
                                value='<c:out value="${performInfo.originalDateStr}"/>'
                                </c:if> style="width: 90%;"/></td>
                        </tr>
                        <tr class="imposeFine-info-tr" style="display: none;">
                            <td class="power-table-label">加处罚款或滞纳金额：</td>
                            <td style="width: 160px;"><input style="width: 90%;" class="easyui-textbox"
                                data-options="validType:'money', novalidate:true,"
                                name="addFindMoney" value='<c:out value="${performInfo.addFindMoney}"/>' /></td>
                        </tr>
                        <tr>
                            <td class="power-table-label">催告书送达日期<span class="required">*</span>：</td>
                            <td style="width: 160px;"><input style="width: 90%;" class="easyui-datebox" id="pressSendDateStr"
                                name="pressSendDateStr" data-options=" required:true, validType:'date', novalidate:true, missingMessage:'请选择催告书送达日期'"
                                <c:if test="${performInfo.pressSendDateStr != 'null'}">
                                value='<c:out value="${performInfo.pressSendDateStr}"/>'
                                </c:if>/></td>
                            <td class="power-table-label">送达方式：</td>
                            <td style="width: 160px;">
                                <input class="easyui-textbox sendType-select"
                                name="pressSendType" 
                                value='<c:out value="${performInfo.pressSendType}"/>' style="width: 90%;" /></td> 
                        </tr>
                        <tr class="replace-info-tr" style="display: none;">
                            <td class="power-table-label">二次催告书送达日期：</td>
                            <td style="width: 160px;"><input class="easyui-datebox" name="secondPressDateStr" id="secondPressDateStr"
                                data-options="validType:'date', novalidate:true,"
                                <c:if test="${performInfo.pressSendDateStr != 'null'}">
                                value='<c:out value="${performInfo.secondPressDateStr}"/>'
                                </c:if> style="width: 90%;" /></td>

                            <td class="power-table-label">送达方式：</td>
                            <td style="width: 160px;"><input style="width: 90%;"
                                class="easyui-textbox sendType-select" name="secondPressType" 
                                value='<c:out value="${performInfo.secondPressType}"/>' /></td>
                        </tr>
                    </table>
                </form>
            </div>
            <!-- <div style="text-align: center; padding-top: 10px;">
                <button type="button" class="easyui-linkbutton" title="保存" onclick="savePerformPress()">
                    <span style="padding-right: 2px; width: 40px;"><i class="fa fa-save"></i> 保存</span>
                </button>
            </div> -->
        </div>
        <div title="申请与批准" style="padding: 10px">
        <div id="perform_apply_panel" class="easyui-panel"  title="基础信息" style="width: 99%; overflow: auto;">
         <form role="form" id="performApply_form" name="form1" enctype="multipart/form-data" method="post" data-options="novalidate:true">
            <table id="performSelfEditInput_apply_table" class="TableBlock" style="width: 100%; background: #fff;">
                <tr>
                    <td class="power-table-label" style="width: 200px;">申请日期：</td>
                    <td style="width: 160px;"><input class="easyui-datebox" name="applyDateStr" id="applyDateStr"
                        data-options="validType:'date', novalidate:true,"
                        <c:if test="${performInfo.applyDateStr != 'null'}">
                            value='<c:out value="${performInfo.applyDateStr}"/>'
                        </c:if>  style="width: 90%;" /></td>
                    <td class="power-table-label">批准日期<span class="required">*</span>：</td>
                    <td style="width: 160px;"><input class="easyui-datebox" name="approveDateStr" id="approveDateStr"
                    data-options="required:true, validType:'date', novalidate:true, missingMessage:'请选择批准日期'"
                        <c:if test="${performInfo.approveDateStr != 'null'}">
                            value='<c:out value="${performInfo.approveDateStr}"/>' 
                        </c:if> style="width: 90%;" /></td>
                </tr>
                <tr>
                    <td class="power-table-label" style="width: 200px;">批准人：</td>
                    <td style="width: 160px;"><input class="easyui-textbox" name="approvePerson"
                        value='<c:out value="${performInfo.approvePerson}"/>' style="width: 90%;" /></td>
                </tr>
            </table>
        </form>
        </div>
        <br/>
         <!-- <div class="easyui-panel" title="强制依据"
                        style="width: 95%; height: 210px;" data-options="tools:'#coercion_powerDetail_Tools'">
                        <table id="coercion_powerDetail_table" fit="true"></table>
            </div>
            <div id="coercion_powerDetail_Tools">
                <a onclick="doAddCoercionDetail()" title="添加强制依据"><i class="fa fa-plus"></i></a> 
                <a onclick="doReduceCoercionDetail()" title="撤销选中依据"><i class="fa fa-minus"></i></a>
            </div> -->
            <div class="easyui-panel" title="强制职权" style="width: 99%; height:220px" align="center" data-options="tools:'#common_case_add_illegal_hand'">
            <div id="common_case_add_illegal_hand">
                <a href="javascript:void(0);" class="icon-add" onclick="coercionFindPower()" ><i class="fa fa-plus"></i></a>
            </div>
                <table fit="true" style="width: 100%;" id="perform_power_table">
                </table>
            </div>
            <br />
            <div class="easyui-panel" title="强制依据" style="width: 99%; height:220px" align="center">
                <table fit="true" style="width: 100%;" id="perform_gist_table"> 
                </table>
            </div>
            <br/>
            <!-- <div style="text-align: center; padding-top: 10px;">
                <button type="button" class="easyui-linkbutton" title="保存" onclick="savePerformApply()">
                    <span style="padding-right: 2px; width: 40px;"><i class="fa fa-save"></i> 保存</span>
                </button>
            </div> -->
        </div>
        <div title="行政强制执行事项管理" style="padding: 10px">
            <div id="perform_enforce_panel" class="easyui-panel"  title="基础执行事项信息" style="width: 99%; overflow: auto;">
                <form role="form" id="performEnforce_form" name="form1" enctype="multipart/form-data" method="post">
                    <table id="performSelfEditInput_enforce_table" class="TableBlock" style="width: 100%; background: #fff;">
                        <tr>
                            <td class="power-table-label" style="width: 200px;">强制执行决定书文号：</td>
                            <td style="width: 160px;">
                                <input class="easyui-textbox" name="coercionPerformCode"
                                value='<c:out value="${performInfo.coercionPerformCode}"/>' style="width: 90%;" /></td>
                            <td class="power-table-label" style="width: 200px;">实施强制执行日期：</td>
                            <td style="width: 160px;">
                                <input class="easyui-datebox" name="performEnforceDateStr" id="performEnforceDateStr"
                                data-options="validType:'date', novalidate:true,"
                                <c:if test="${performInfo.performEnforceDateStr != 'null'}">
                                value='<c:out value="${performInfo.performEnforceDateStr}"/>' 
                                </c:if> style="width: 90%;" 
                                /></td>
                        </tr>
                        <tr>
                            <td class="power-table-label">强制执行决定书送达日期：</td>
                            <td style="width: 160px;">
                            <input class="easyui-datebox" name="decideSendDateStr" id="decideSendDateStr"
                                data-options="validType:'date', novalidate:true,"
                                <c:if test="${performInfo.decideSendDateStr != 'null'}">
                                value='<c:out value="${performInfo.decideSendDateStr}"/>'
                                </c:if> style="width: 90%;" /></td>

                            <td class="power-table-label">送达方式：</td>
                            <td style="width: 160px;"><input style="width: 90%;"
                                class="easyui-textbox sendType-select" name="decideSendType" 
                                value='<c:out value="${performInfo.decideSendType}"/>' /></td>
                        </tr>
                        <tr class="trans-info-tr" style="display: none;">
                            <td class="power-table-label" style="width: 200px;">划拨金额：</td>
                            <td style="width: 160px;">
                                <input class="easyui-textbox" name="transDeposit" 
                                data-options="validType:'money', novalidate:true,"
                                value='<c:out value="${performInfo.transDeposit}"/>'
                                style="width: 90%;" /></td>
                            <td class="power-table-label" style="width: 200px;">划拨金融机构名称：</td>
                            <td style="width: 160px;"><input class="easyui-textbox" name="transOrganization"
                                value='<c:out value="${performInfo.transOrganization}"/>' style="width: 90%;" /></td>
                        </tr>
                        <tr class="trans-info-tr" style="display: none;">
                            <td class="power-table-label" style="width: 200px;">划拨通知书送达日期：</td>
                            <td style="width: 160px;"><input class="easyui-datebox" name="transNoticeDateStr" 
                                id="transNoticeDateStr" data-options="validType:'date', novalidate:true," 
                                <c:if test="${performInfo.transNoticeDateStr != 'null'}">
                                value='<c:out value="${performInfo.transNoticeDateStr}"/>' 
                                </c:if> style="width: 90%;" /></td>
                        </tr>
                        <tr class="auction-info-tr" style="display: none;">
                            <td class="power-table-label" style="width: 200px;">拍卖地点：</td>
                            <td style="width: 160px;">
                                <input class="easyui-textbox" name="auctionAddr"
                                value='<c:out value="${performInfo.auctionAddr}"/>'
                                style="width: 90%;" /></td>
                            <td class="power-table-label" style="width: 200px;">拍卖日期：</td>
                            <td style="width: 160px;">
                            <input class="easyui-datebox" name="auctionDateStr" id="auctionDateStr"
                            data-options="validType:'date', novalidate:true,"
                            <c:if test="${performInfo.auctionDateStr != 'null'}">
                                value='<c:out value="${performInfo.auctionDateStr}"/>'
                                </c:if> style="width: 90%;" /></td>
                        </tr>
                        <tr class="auction-info-tr" style="display: none;">
                            <td class="power-table-label" style="width: 200px;">拍卖所得处理：</td>
                            <td colspan="3" style="width: 600px;">
                            <input class="easyui-textbox" name="auctionUse"
                                value='<c:out value="${performInfo.auctionUse}"/>' style="width: 90%;" /></td>
                        </tr>
                        <tr class="replace-info-tr" style="display: none;">
                            <td class="power-table-label" style="width: 200px;">代履行人：</td>
                            <td style="width: 160px;">
                                <input class="easyui-textbox" name="replaceObject" 
                                value='<c:out value="${performInfo.replaceObject}"/>'
                                style="width: 90%;" /></td>
                            <td class="power-table-label" style="width: 200px;">代履行费用：</td>
                            <td style="width: 160px;">
                                <input class="easyui-textbox" name="replaceDeposit"
                                data-options="validType:'money', novalidate:true,"
                                value='<c:out value="${performInfo.replaceDeposit}"/>'
                                style="width: 90%;" /></td>
                        </tr>
                        <tr class="replace-info-tr" style="display: none;">
                            <td class="power-table-label" style="width: 200px;">监督人：</td>
                            <td style="width: 160px;">
                                <input class="easyui-textbox" name="replaceSupervise"
                                value='<c:out value="${performInfo.replaceSupervise}"/>'
                                style="width: 90%;" /></td>
                            <td class="power-table-label" style="width: 200px;">代履行日期：</td>
                            <td style="width: 160px;">
                                <input class="easyui-datebox" name="replaceEnforceDateStr" 
                                data-options="validType:'date', novalidate:true," id="replaceEnforceDateStr"
                                <c:if test="${performInfo.replaceEnforceDateStr != 'null'}">
                                value='<c:out value="${performInfo.replaceEnforceDateStr}"/>' 
                                </c:if> style="width: 90%;" /></td>
                        </tr>
                    </table>
                </form>
            </div>
            <br/>
            <div id="perform_enforceExtra_panel" class="easyui-panel"  title="其他执行事项信息" style="width: 99%; overflow: auto;">
                <form role="form" id="performEnforceExtra_form" name="form1" enctype="multipart/form-data" method="post">
                    <table id="performSelfEditInput_enforceExtra_table" class="TableBlock" style="width: 100%; background: #fff;">
                        <tr>
                            <td class="TableData" style="text-align: right; width:  200px;" nowrap>
                                &nbsp;&nbsp;<input class="easyui-checkbox isAgreementEnforce" 
                                <c:if test="${performInfo.isAgreementEnforce == 1}"> checked </c:if>
                                name="isAgreementEnforce" value="1" style="padding-right: 0px;"/>&nbsp;
                            </td>
                            <td style="width: 160px;">执行协议</td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr class="agreementEnforce-info-tr" style="display: none;">
                            <td class="power-table-label" style="width: 200px;">协议签订日期：</td>
                            <td style="width: 160px;"><input class="easyui-datebox" name="agreeSignDateStr"
                                data-options="validType:'date', novalidate:true," id="agreeSignDateStr"
                                <c:if test="${performInfo.agreeSignDateStr != 'null'}">
                                value='<c:out value="${performInfo.agreeSignDateStr}"/>' 
                                </c:if> style="width: 90%;" /></td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr class="agreementEnforce-info-tr" style="display: none;">
                            <td class="power-table-label">协议内容：</td>
                            <td colspan="3" style="width: 600px; height: 70px;">
                                <input class="easyui-textbox" name="agreeContent"
                                data-options="multiline:true" 
                                value='<c:out value="${performInfo.agreeContent}"/>' style="width: 90%; height: 60px;"></td>
                        </tr>
                        <tr class="agreementEnforce-info-tr" style="display: none;">
                            <td class="power-table-label">协议执行情况：</td>
                            <td colspan="3" style="width: 600px; height: 70px;">
                                <input class="easyui-textbox" name="agreeEnforceCondition"
                                data-options="multiline:true" 
                                value='<c:out value="${performInfo.agreeEnforceCondition}"/>' style="width: 90%; height: 60px;"></td>
                        </tr>
                        <tr>
                            <td class="TableData" style="text-align: right; width:  200px;" nowrap>
                                &nbsp;&nbsp;<input class="easyui-checkbox isEnforceReturn" 
                                <c:if test="${performInfo.isEnforceReturn == 1}"> checked </c:if>
                                name="isEnforceReturn" value="1" style="padding-right: 0px;"/>&nbsp;
                            </td>
                            <td style="width: 160px;">执行回转</td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr class="enforceReturn-info-tr" style="display: none;">
                            <td class="power-table-label" style="width: 200px;">执行回转日期：</td>
                            <td style="width: 160px;"><input class="easyui-datebox" name="enforceReturnDateStr"
                                data-options="validType:'date', novalidate:true"
                                <c:if test="${performInfo.enforceReturnDateStr != 'null'}">
                                value='<c:out value="${performInfo.enforceReturnDateStr}"/>' 
                                </c:if> style="width: 90%;" /></td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr class="enforceReturn-info-tr" style="display: none;">
                            <td class="power-table-label">执行回转原因：</td>
                            <td colspan="3" style="width: 600px; height: 70px;">
                                <input class="easyui-textbox" name="enforceReturnReason"
                                data-options="multiline:true" 
                                value='<c:out value="${performInfo.enforceReturnReason}"/>' style="width: 98%; height: 60px;"></td>
                        </tr>
                        <tr class="enforceReturn-info-tr" style="display: none;">
                            <td class="power-table-label">执行回转情况：</td>
                            <td colspan="3" style="width: 600px; height: 70px;">
                                <input class="easyui-textbox" name="enforceReturnContent"
                                data-options="multiline:true" 
                                value='<c:out value="${performInfo.enforceReturnContent}"/>' style="width: 98%; height: 60px;"></td>
                        </tr>
                        <tr>
                            <td class="TableData" style="text-align: right; width:  200px;" nowrap>
                                &nbsp;&nbsp;<input class="easyui-checkbox isEndEnforce" 
                                <c:if test="${performInfo.isEndEnforce == 1}"> checked </c:if>
                                name="isEndEnforce" value="1" style="padding-right: 0px;"/>&nbsp;
                            </td>
                            <td style="width: 160px;">终结执行</td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr class="endEnforce-info-tr" style="display: none;">
                            <td class="power-table-label" style="width: 200px;">通知书送达日期：</td>
                            <td style="width: 160px;"><input class="easyui-datebox" name="endEnforceSendDateStr"
                                data-options="validType:'date', novalidate:true,"
                                <c:if test="${performInfo.endEnforceSendDateStr != 'null'}">
                                value='<c:out value="${performInfo.endEnforceSendDateStr}"/>' 
                                </c:if> style="width: 90%;" /></td>
                            <td class="power-table-label">送达方式：</td>
                            <td style="width: 160px;">
                            <input style="width: 90%;" class="easyui-combobox" id="endSendType"
                                name="endSendType"  value="${performInfo.endSendType}" />
                                </td>
                        </tr>
                        <tr class="endEnforce-info-tr" style="display: none;">
                            <td class="power-table-label">终结原因：</td>
                            <td colspan="3" style="width: 600px;">
                                <input style="width: 98%;" class="easyui-combobox"
                                name="enforceEndReason" id="enforceEndReason"
                                value="${performInfo.enforceEndReason}"/></td>
                        </tr>
                        <tr>
                            <td class="TableData" style="text-align: right; width:  200px;" nowrap>
                                &nbsp;&nbsp;<input class="easyui-checkbox isBreakEnforce" 
                                <c:if test="${performInfo.isBreakEnforce == 1}"> checked </c:if>
                                name="isBreakEnforce" value="1" style="padding-right: 0px;"/>&nbsp;
                            </td>
                            <td style="width: 160px;">中止执行</td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr class="breakEnforce-info-tr" style="display: none;">
                            <td class="power-table-label" style="width: 200px;">中止执行日期：</td>
                            <td style="width: 160px;"><input class="easyui-datebox" name="breakEnforceDateStr"
                                data-options="validType:'date', novalidate:true,"
                                <c:if test="${performInfo.breakEnforceDateStr != 'null'}">
                                value='<c:out value="${performInfo.breakEnforceDateStr}"/>' 
                                </c:if> style="width: 90%;" /></td>
                            <td class="power-table-label">恢复执行日期：</td>
                            <td style="width: 160px;"><input class="easyui-datebox" name="relwaseEnforceDateStr"
                                data-options="validType:'date', novalidate:true,"
                                <c:if test="${performInfo.relwaseEnforceDateStr != 'null'}">
                                value='<c:out value="${performInfo.relwaseEnforceDateStr}"/>' 
                                </c:if> style="width: 90%;" /></td>
                        </tr>
                        <tr class="breakEnforce-info-tr" style="display: none;">
                            <td class="power-table-label">中止原因：</td>
                            <td colspan="3" style="width: 600px; height: 70px;">
                                <input class="easyui-textbox" name="enforceBreakReason"
                                data-options="multiline:true"
                                value='<c:out value="${performInfo.enforceBreakReason}"/>' style="width: 90%; height: 60px;"></td>
           
                        </tr>
                    </table>
                    <br />
                </form>
            </div>
<!--             <div style="text-align: center; padding-top: 10px;"> -->
<!--                 <button type="button" class="easyui-linkbutton" title="保存" onclick="savePerformEnforce()"> -->
<!--                     <span style="padding-right: 2px; width: 40px;"><i class="fa fa-save"></i> 保存</span> -->
<!--                 </button> -->
<!--             </div> -->
        </div>
    </div>
</body>
</html>
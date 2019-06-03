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

<link rel="stylesheet" type="text/css" href="/supervise/caseManager/commonCase/css/case.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/power/css/power.css" />
<link rel="stylesheet" type="text/css" href="/supervise/adminCoercion/css/adminCoercion.css" />
<title>行政强制行为</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
</head>
<body onload="measureEditInit()">
    <div id="measureInput_edit_title"
        style="width: 100%; height: 10%; padding-right: 10px; padding-left: 10px; padding-top: 5px">
        <div id="toolbar_edit" class="titlebar clearfix">
                <div id="outwarp">
            <div class="fl left">
                <img id="img1" class='title_img' src="<%=contextPath%>/common/zt_webframe/imgs/xzbg/clgl/clsqcx.png" />
                <span class="title">行政强制措施</span>
            </div>
            <c:if test="${baseInfo.sourcePage != 'measure'}">
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
    <div id="measureInput_edit_Div" class="easyui-tabs"
        style="width: 100%; height: 90%; padding-right: 10px; padding-left: 10px; padding-top: 5px">
        <div title="行政强制措施种类">
            <form role="form" id="measureInput_form1" name="form1" enctype="multipart/form-data" method="post" data-options="novalidate:true">
                <input type="hidden" id="measureId" name="measureId" value='<c:out value="${measureInfo.id}"/>'/> 
                <input type="hidden" id="measureType" name="measureType" value='<c:out value="${measureInfo.measureType}"/>'/> 
                <input type="hidden" id="caseSourceId" name="caseSourceId" value='<c:out value="${baseInfo.caseSourceId}"/>' />
                <input type="hidden" id="caseSourceType" name="caseSourceType" value='<c:out value="${baseInfo.caseSourceType}"/>' />
                <input type="hidden" id="coercionCaseId" name="coercionCaseId" value='<c:out value="${baseInfo.coercionCaseId}"/>' />
                <input type="hidden" id="subjectId" name="subjectId" value='<c:out value="${baseInfo.subjectId}"/>' />
                <input type="hidden" id="departmentId" name="departmentId" value='<c:out value="${baseInfo.departmentId}"/>' />
                <input type="hidden" id="enforceStep" name="enforceStep"  value='<c:out value="${measureInfo.enforceStep}"/>'/>
                <input type="hidden" id="caseCode" name="caseCode"  value='<c:out value="${baseInfo.caseCode}"/>'/>
                <table class="TableBlock" style="width: 100%; background: #fff;">
                    <tr>
                        <td style="padding-left: 15%;">
                            <ul style="width: 500px;" id="measureInput_measureType">
                                <li><input class="easyui-radiobutton" <c:if test="${measureInfo.measureType == 100}"> checked </c:if> name="measureType" value="100"><span>&nbsp;&nbsp;查封场所、设施或者财物</span></li>
                                <li><input class="easyui-radiobutton" <c:if test="${measureInfo.measureType == 200}"> checked </c:if> name="measureType" value="200"><span>&nbsp;&nbsp;扣押财物</span></li>
                                <li><input class="easyui-radiobutton" <c:if test="${measureInfo.measureType == 300}"> checked </c:if> name="measureType" value="300"><span>&nbsp;&nbsp;冻结存款、汇款</span></li>
                                <li><input class="easyui-radiobutton" <c:if test="${measureInfo.measureType == 400}"> checked </c:if> name="measureType" value="400"><span>&nbsp;&nbsp;限制公民人身自由</span></li>
                                <li><input class="easyui-radiobutton" <c:if test="${measureInfo.measureType == 900}"> checked </c:if> name="measureType" value="900"><span>&nbsp;&nbsp;其他行政强制措施</span></li>
                            </ul>

                           
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <div title="申请与批准" style="padding: 10px">
            <div id="coercion_apply_panel" class="easyui-panel" title="基础信息" style="width: 100%; overflow:auto;">
                   <form role="form" id="meaSureApply_form" name="form1" enctype="multipart/form-data" method="post" data-options="novalidate:true">
                   <table id="measureEditInput_apply_table" class="TableBlock" style="width: 100%; background: #fff;">
                            <tr>
                                <td style="width: 200px;" class="power-table-label">申请日期：</td>
                                <td style="width: 200px;"><input class="easyui-datebox" 
                                name="applyDateStr"  data-options="validType:'date', novalidate:true"
                                <c:if test="${performInfo.punishDateBeforeStr != 'null'}">
                                 value='<c:out value="${measureInfo.applyDateStr}"/>'
                                </c:if> style="width: 90%;" /></td>
                                <td style="width: 200px;" class="power-table-label">批准日期<span class="required">*</span>：</td>
                                <td style="width: 200px;"><input class="easyui-datebox"
                                 name="approveDateStr"  data-options="required:true, validType:'date', novalidate:true, missingMessage:'请选择批准日期'"
                                 <c:if test="${performInfo.punishDateBeforeStr != 'null'}">
                                 value='<c:out value="${measureInfo.approveDateStr}"/>'
                                </c:if> style="width: 90%;" /></td>
                            </tr>
                            <tr>
                                <td class="power-table-label">批准人：</td>
                                <td style="width: 200px;"><input style="width: 90%;" class="easyui-textbox"
                                    id="allowPerson" name="approvePerson" value='<c:out value="${measureInfo.approvePerson}"/>'/></td>
                                <td class="power-table-label">强制措施决定书文号：</td>
                                <td style="width: 200px;"><input style="width: 90%;" class="easyui-textbox"
                                    id="measureCode" name="coercionCode" value='<c:out value="${measureInfo.coercionCode}"/>'/></td>
                            </tr>

                            <tr class="decisionSend-info-tr">
                                <td class="power-table-label">强制措施决定书送达日期<span class="required">*</span>：</td>
                                <td style="width: 200px;">
                                <input style="width: 90%;" class="easyui-datebox" 
                                name="cdSendDateStr" data-options="required:true, validType:'date', novalidate:true, missingMessage:'请选择强制措施决定书送达日期'"
                                <%-- <c:if test="${measureInfo.cdSendDateStr != 'null'}"> --%>
                                 value='<c:out value="${measureInfo.cdSendDateStr}"/>'
                                <%-- </c:if> --%> /></td>
                                <td class="power-table-label">送达方式：</td>
                                <td style="width: 200px;">
                                    <input class="easyui-combobox" style="width: 90%;" 
                                    value='<c:out value="${measureInfo.cdSendType}"/>'
                                    id="measureDecision_sendWay" name="cdSendType"
                                    />
                                </td>
                            </tr>
                            <tr>
                                <td class="power-table-label">行政强制措施期限<span class="required">*</span>：</td>
                                <td style="width: 200px;">
                                    <input style="width: 90%;" class="easyui-datebox" 
                                    name="measureDateLimitStartStr" data-options="required:true, validType:'date', novalidate:true, missingMessage:'请选择行政强制措施期限起始日期'"
                                    value='<c:out value="${measureInfo.measureDateLimitStartStr}"/>'/>
                                </td>
                                <td class="power-table-label">至<span class="required">*</span>：</td>
                                <td style="width: 200px;">
                                    <input style="width: 90%;" class="easyui-datebox" 
                                    name="measureDateLimitEndStr" data-options="required:true, validType:'date', novalidate:true, missingMessage:'请选择行政强制措施期限截止日期'"
                                    value='<c:out value="${measureInfo.measureDateLimitEndStr}"/>'/>
                                </td>
                            </tr>
                            <tr>
                                <td class="power-table-label">实施强制措施日期<span class="required">*</span>：</td>
                                <td style="width: 200px;">
                                    <input style="width: 90%;" class="easyui-datebox"
                                    name="measureEnforceDateStr" data-options="required:true, validType:'date', novalidate:true, missingMessage:'请选择实施强制措施日期'"
                                    value='<c:out value="${measureInfo.measureEnforceDateStr}"/>'/>
                                </td>
                                <td></td>
                                <td></td>
                            </tr>
                        <tr class="close-info-tr delay_limit" >
                            <td class="power-table-label">是否延期：</td>
                            <td style="width: 200px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                                <input type="radio" <c:if test="${measureInfo.isDelayLimit == 1}"> checked </c:if> name="isDelayLimit" value="1" />&nbsp;&nbsp;是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <input type="radio" <c:if test="${measureInfo.isDelayLimit == 0}"> checked </c:if> name="isDelayLimit" value="0" />&nbsp;&nbsp;否
                            </td>
                            <td class="power-table-label " >
                                <label class="is_delay_limit" style="display: none;" > 强制措施延长期限<span class="required">*</span>：</label>
                           </td>
                                <td style="width: 200px; " >
                                    <div class="is_delay_limit" style="width: 240px; display: none;" >
                                        <input style="width: 90%;" class="easyui-datebox" 
                                        name="measureDelayDateEndStr" id="measureDelayDateEndStr"
                                        value='<c:out value="${measureInfo.measureDelayDateEndStr}"/>'/>
                                    </div>
                                </td>
                                
                            <%-- <td class="power-table-label is_delay_limit"  >强制措施延长期限：</td>
                            <td style="width: 200px; display: none;" class="is_delay_limit" >
                                <input style="width: 90%; " class="easyui-datebox" 
                                name="measureDelayDateEndStr" 
                                value='<c:out value="${measureInfo.measureDelayDateEndStr}"/>'/>
                            </td> --%>
                        </tr>
                        <tr class="close-info-tr" style="display: none;">
                            <td class="power-table-label">当事人是否在场：</td>
                            <td style="width: 200px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                                <input type="radio"  <c:if test="${measureInfo.isPartyPresent == 1}"> checked </c:if> name="isClosePresence" value="1" />&nbsp;&nbsp;是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <input type="radio" <c:if test="${measureInfo.isPartyPresent == 2}"> checked </c:if> name="isClosePresence" value="2" />&nbsp;&nbsp;否
                            </td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr style="display: none;">
                            <td class="power-table-label">见证人姓名：</td>
                            <td style="width: 200px;"><input style="width: 90%;"
                                class="easyui-textbox close-isPresence-info" 
                                value='<c:out value="${measureInfo.witnessName}"/>'
                                name="closeWitnessName" /></td>
                            <td class="power-table-label">联系方式：</td>
                            <td style="width: 200px;"><input style="width: 90%;"
                                class="easyui-textbox close-isPresence-info" 
                                value='<c:out value="${measureInfo.witnessContactWay}"/>'
                                name="closeWitnessContactWay" /></td>
                        </tr>
                        <tr class="close-info-tr" style="display: none;">
                            <td class="power-table-label">强制措施对象：</td>
                            <td style="width: 200px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                            <input type="checkbox" name="elemType" value="1" />&nbsp;&nbsp;场所&nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="checkbox" name="elemType" value="2" />&nbsp;&nbsp;财物
                            </td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr style="display: none;">
                            <td class="power-table-label">设施、场所名称：</td>
                            <td colspan="3" style="width: 600px;"><input style="width: 90%;" 
                                value='<c:out value="${measureInfo.closeDownPlaceName}"/>'
                                name="closeDownPlaceName"
                                class="easyui-textbox close-place-info" /></td>
                        </tr>
                        <tr style="display: none;">
                            <td class="power-table-label">设施、场所地址：</td>
                            <td colspan="3" style="width: 600px; height: 70px;"><input
                                class="easyui-textbox close-place-info" 
                                value='<c:out value="${measureInfo.closeDownPlaceAddr}"/>'
                                name="closeDownPlaceAddr"
                                data-options="multiline:true" style="width: 90%; height: 60px;"></td>
                        </tr>
                        <tr style="display: none;">
                            <td class="power-table-label">财物名称、数量、金额：</td>
                            <td colspan="3" style="width: 600px; height: 70px;"><input
                                class="easyui-textbox close-property-info" 
                                value='<c:out value="${measureInfo.closeDownPropertyInfo}"/>'
                                name="closeProperty"
                                data-options="multiline:true" style="width: 90%; height: 60px;"></td>
                        </tr>
                        <tr class="close-info-tr" style="display: none;">
                            <td class="power-table-label">查封原因：</td>
                            <td colspan="3" style="width: 600px; height: 70px;">
                            <input class="easyui-textbox" 
                            value='<c:out value="${measureInfo.closeDownReason}"/>' name="closeDownReason" 
                            data-options="multiline:true" style="width: 90%; height: 60px;"></td>
                        </tr>
                        <tr class="detention-info-tr" style="display: none;">
                            <td class="power-table-label">当事人是否在场：</td>
                            <td style="width: 200px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                                <input type="radio" <c:if test="${measureInfo.isDetentionPartyPresent == 1}"> checked </c:if> name="isDetentionPresence" value="1" />&nbsp;&nbsp;是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <input type="radio" <c:if test="${measureInfo.isDetentionPartyPresent == 2}"> checked </c:if> name="isDetentionPresence" value="2" />&nbsp;&nbsp;否
                            </td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr style="display: none;">
                            <td class="power-table-label">见证人姓名：</td>
                            <td style="width: 200px;"><input style="width: 90%;"
                                class="easyui-textbox detention-isPresence-info" 
                                value='<c:out value="${measureInfo.detentionWitnessName}"/>'
                                name="detentionWitnessName" /></td>
                            <td class="power-table-label">联系方式：</td>
                            <td style="width: 200px;"><input style="width: 90%;"
                                class="easyui-textbox detention-isPresence-info" 
                                value='<c:out value="${measureInfo.detentionWitnessContactWay}"/>'
                                name="detentionWitnessContactWay" /></td>
                        </tr>
                        <tr style="display: none;">
                            <td class="power-table-label">财物名称、数量、金额：</td>
                            <td colspan="3" style="width: 600px; height: 70px;"><input
                                class="easyui-textbox detention-property-info"
                                value='<c:out value="${measureInfo.detentionPropertyInfo}"/>'
                                 name="detentionProperty"
                                data-options="multiline:true" style="width: 90%; height: 60px;"></td>
                        </tr>
                        <tr class="detention-info-tr" style="display: none;">
                            <td class="power-table-label">扣押原因：</td>
                            <td colspan="3" style="width: 600px; height: 70px;">
                            <input class="easyui-textbox detention-info"
                            value='<c:out value="${measureInfo.detentionReason}"/>'
                            name="detentionReason"
                            data-options="multiline:true" style="width: 90%; height: 60px;"/>
                            </td>
                        </tr>
                        <tr class="freeze-info-tr" style="display: none;">
                            <td class="power-table-label">冻结金额：</td>
                            <td style="width: 200px;"><input style="width: 90%;" 
                                data-options="validType:'money', novalidate:true,"
                                value='<c:out value="${measureInfo.freezeNumber}"/>'
                                name="freezeNumber"
                                class="easyui-textbox" /></td>
                            <td class="power-table-label">金融机构名称：</td>
                            <td style="width: 200px;"><input style="width: 90%;"
                                value='<c:out value="${measureInfo.freezeOrganization}"/>'
                                name="freezeOrganization"
                                class="easyui-textbox" /></td>
                        </tr>
                        <tr class="freeze-info-tr" style="display: none;">
                            <td class="power-table-label">通知冻结日期：</td>
                            <td style="width: 200px;"><input style="width: 90%;"
                                value='<c:out value="${measureInfo.freezeNoticeDateStr}"/>'
                                name="freezeNoticeDateStr" data-options="validType:'date', novalidate:true,"
                                class="easyui-datebox" /></td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr class="limit-info-tr" style="display: none;">
                            <td class="power-table-label">是否通知家属或所在单位：</td>
                            <td style="width: 200px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                            <input type="radio" <c:if test="${measureInfo.isNoticeFamily == 1}"> checked </c:if> name="isNoticeFamily" value="1" />&nbsp;&nbsp;是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                            <input type="radio" <c:if test="${measureInfo.isNoticeFamily == 2}"> checked </c:if> name="isNoticeFamily" value="2" />&nbsp;&nbsp;否
                            </td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr class="limit-reason-tr" style="display: none;">
                            <td class="power-table-label">未通知原因：</td>
                            <td colspan="3" style="width: 600px; height: 70px;">
                            <input class="easyui-textbox" name="notNoticeReason"
                                value='<c:out value="${measureInfo.notNoticeReason}"/>'
                                data-options="multiline:true" style="width: 90%; height: 60px;"/></td>
                        </tr>
                    </table>
                    <br />
                </form>
            </div>
            <br/>

            <div class="easyui-panel" title="强制职权" style="width: 100%; height:220px" align="center" data-options="tools:'#common_case_add_illegal_hand'">
            <div id="common_case_add_illegal_hand">
                <a href="javascript:void(0);" class="icon-add" onclick="coercionFindPower()" ><i class="fa fa-plus"></i></a>
            </div>
                <table fit="true" style="width: 100%;" id="measure_power_table">
                </table>
            </div>
            <br />
            <div class="easyui-panel" title="强制依据" style="width: 100%; height:220px" align="center">
                <table fit="true" style="width: 100%;" id="measure_gist_table"> 
                </table>
            </div>
            <!-- <div style="text-align: center; padding-top: 10px;">
                <button type="button" class="easyui-linkbutton" title="保存" onclick="saveMeasureApply()">
                    <span style="padding-right: 2px; width: 40px;"><i class="fa fa-save"></i> 保存</span>
                </button>
            </div> -->
        </div>
        <div title="行政强制措施处理决定" style="padding: 10px">
            <form role="form" id="meaSureResult_form" name="form1" enctype="multipart/form-data" method="post" data-options="novalidate:true">
                <table class="TableBlock" style="width: 100%; background: #fff;">
                    <tr>
                        <td style="padding-left: 15%;">
                            <ul style="width: 500px;">
                                <li><input id="measureDealWay_destory" class="easyui-radiobutton"
                                    <c:if test="${measureInfo.measureDealWay == 1}"> checked </c:if>
                                    name="measureDealWay" value="1"><span>&nbsp;&nbsp;予以销毁</span></li>
                                <li><input id="measureDealWay_release" class="easyui-radiobutton"
                                    <c:if test="${measureInfo.measureDealWay == 2}"> checked </c:if>
                                    name="measureDealWay" value="2"><span>&nbsp;&nbsp;予以解除</span></li>
                                <li><input id="measureDealWay_auction" class="easyui-radiobutton"
                                    <c:if test="${measureInfo.measureDealWay == 3}"> checked </c:if>
                                    name="measureDealWay" value="3"><span>&nbsp;&nbsp;予以拍卖，抵交罚款</span></li>
                                <li><input id="measureDealWay_trans" class="easyui-radiobutton"
                                    <c:if test="${measureInfo.measureDealWay == 4}"> checked </c:if>
                                    name="measureDealWay" value="4"><span>&nbsp;&nbsp;移送司法机关</span></li>
                            </ul>
                        </td>
                    </tr>
                </table>
                <table class="TableBlock_page" fit="true" style="width: 100%;">
                    <tr style="border:none!important;">
                    	<td class="power-table-label" style="text-align:right;">强制决定书<span class="required">*</span>：</td>
                        <td class="TableData" style="width: 180px;">
							<a id="uploadHolder2" class="add_swfupload">
								<img src="<%=systemImagePath %>/upload/batch_upload.png"/>上传附件
							</a>
							<input id="attaches" name="attaches"  type="hidden" />
 						 </td>
 						 <td class="power-table-label" ></td>
                         <td style="width: 250px;">
                         	<div id="fileContainer2"></div>
							<div id="renderContainer2"></div>
                            <div id="attachDiv"></div>
                         </td>
                    </tr>
                </table>
                <!-- <div style="text-align: center; padding-top: 10px;">
                    <button type="button" class="easyui-linkbutton" title="保存" onclick="saveMeasureResult()">
                        <span style="padding-right: 2px; width: 40px;"><i class="fa fa-save"></i> 保存</span>
                    </button>
                </div> -->
            </form>
        </div>
    </div>
</body>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/adminCoercion/js/measure_edit_input.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/md5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/supervise/js/supperson_add.js"></script>
</html>
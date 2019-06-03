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
<script type="text/javascript" src="<%=contextPath%>/supervise/adminCoercion/coercionSearch/js/measure_seeDetail_input.js"></script>
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
<body onload="measureEditInit()">
    <input type="hidden" id="measureId" name="measureId" value='<c:out value="${measureInfo.id}"/>'/> 
    <input type="hidden" id="measureType" name="measureType" value='<c:out value="${measureInfo.measureType}"/>'/> 
    <input type="hidden" id="caseSourceId" name="caseSourceId" value='<c:out value="${baseInfo.caseSourceId}"/>' />
    <input type="hidden" id="caseSourceType" name="caseSourceType" value='<c:out value="${baseInfo.caseSourceType}"/>' />
    <input type="hidden" id="coercionCaseId" name="coercionCaseId" value='<c:out value="${baseInfo.coercionCaseId}"/>' />
    <input type="hidden" id="subjectId" name="subjectId" value='<c:out value="${baseInfo.subjectId}"/>' />
    <input type="hidden" id="departmentId" name="departmentId" value='<c:out value="${baseInfo.departmentId}"/>' />
    <input type="hidden" id="enforceStep" name="enforceStep"  value='<c:out value="${measureInfo.enforceStep}"/>'/>
    <input type="hidden" id="caseCode" name="caseCode"  value='<c:out value="${baseInfo.caseCode}"/>'/>
    <div style="width: 100%;height:99%; padding-left: 20px; padding-top: 10px">
        <%-- <div id="measureInput_edit_title" style="width: 100%; height: 8%; padding-right: 15px; padding-top: 5px">
            <div id="toolbar_edit" class="titlebar clearfix">
                <div class="fl left">
                    <img id="img1" class='title_img' src="<%=contextPath%>/common/zt_webframe/imgs/xzbg/clgl/clsqcx.png" />
                    <span class="title">行政强制措施</span>
                </div>
                <div class=" fr">
                    <button class="easyui-linkbutton" onclick="doOpenListPage()">
                        <i class="fa fa-mail-reply"></i>返回
                    </button>
                </div>
            </div>
            <span class="basic_border"></span> 
        </div> --%>
        <div class="easyui-panel" title="基础信息" style="width: 100%;" align="center">
        	<table class="TableBlock lookInfo-lowHeight-table" style="width: 95%; background: #fff;">
        		<tr class="none-border-tr">
        			<td style="width: 200px;" class="power-table-label font-bold-label-td">行政强制措施种类：</td>
        			<td style="width: 200px;"><c:out value="${measureInfo.measureTypeStr}"/></td>
        			<td style="width: 200px;" class="power-table-label"></td>
        			<td style="width: 200px;"></td>
        		</tr>
        	</table>
        	<table class="TableBlock" style="width: 95%; background: #fff;">
        		<tr class="none-border-tr">
        			<td style="width: 200px;" class="power-table-label font-bold-label-td" >申请日期：</td>
        			<td style="width: 200px;">
        				<c:if test="${measureInfo.applyDateStr != null && measureInfo.applyDateStr != 'null'}">
        					<c:out value="${measureInfo.applyDateStr}"/>
        				</c:if>
        			</td>
        			<td style="width: 200px;" class="power-table-label font-bold-label-td">批准日期：</td>
        			<td style="width: 200px;">
        				<c:if test="${measureInfo.applyDateStr != null && measureInfo.applyDateStr != 'null'}">
        					<c:out value="${measureInfo.approveDateStr}"/>
        				</c:if>
        			</td>
        		</tr >
        		<tr class="none-border-tr">
        			<td style="width: 200px;" class="power-table-label font-bold-label-td">批准人：</td>
        			<td style="width: 200px;"><c:out value="${measureInfo.approvePerson}"/></td>
        			<td style="width: 200px;" class="power-table-label font-bold-label-td">强制措施决定书文号：</td>
        			<td style="width: 200px;"><c:out value="${measureInfo.coercionCode}"/></td>
        		</tr>
        		
        		<tr class="none-border-tr">
        			<td style="width: 200px;" class="power-table-label font-bold-label-td">强制措施决定书送达日期：</td>
        			<td style="width: 200px;">
        				<c:if test="${measureInfo.cdSendDateStr != null && measureInfo.cdSendDateStr != 'null'}">
        					<c:out value="${measureInfo.cdSendDateStr}"/>
        				</c:if>
        			</td>
        			<td style="width: 200px;" class="power-table-label font-bold-label-td">送达方式：</td>
        			<td style="width: 200px;"><c:out value="${measureInfo.cdSendTypeStr}"/></td>
        		</tr>
        		<tr class="none-border-tr">
        			<td style="width: 200px;" class="power-table-label font-bold-label-td">行政强制措施期限：</td>
        			<td style="width: 200px;">
        				<c:if test="${measureInfo.measureDateLimitStartStr != null && measureInfo.measureDateLimitStartStr != 'null'}">
        					<c:out value="${measureInfo.measureDateLimitStartStr}"/>起&nbsp;&nbsp;
        				</c:if>
        				<c:if test="${measureInfo.measureDateLimitEndStr != null && measureInfo.measureDateLimitEndStr != 'null'}">
        					&nbsp;&nbsp;至<c:out value="${measureInfo.measureDateLimitEndStr}"/>
        				</c:if>
        			</td>
        			<td style="width: 200px;" class="power-table-label font-bold-label-td"></td>
        			<td style="width: 200px;"></td>
        		</tr>
        		<tr class="none-border-tr">
        			<td style="width: 200px;" class="power-table-label font-bold-label-td">实施强制措施日期：</td>
        			<td style="width: 200px;">
        				<c:if test="${measureInfo.measureEnforceDateStr != null && measureInfo.measureEnforceDateStr != 'null'}">
        					<c:out value="${measureInfo.measureEnforceDateStr}"/>
        				</c:if>
        			</td>
        			<td style="width: 200px;" class="power-table-label"></td>
        			<td style="width: 200px;"></td>
        		</tr>
        		<tr class="none-border-tr close-info-tr" <c:if test="${measureInfo.measureType != '100'}"> style="display: none;" </c:if>>
        			<td class="power-table-label font-bold-label-td">当事人是否在场：</td>
        			<td style="width: 200px;">
        				<c:if test="${measureInfo.isPartyPresent == 1}"> 是 </c:if>
        				<c:if test="${measureInfo.isPartyPresent == 2}"> 否 </c:if>
        			</td>
        			<td></td>
        			<td></td>
        		</tr>
        		<tr class="none-border-tr" <c:if test="${measureInfo.isPartyPresent != 2}">style="display: none;" </c:if>>
        			<td class="power-table-label font-bold-label-td">见证人姓名：</td>
        			<td style="width: 200px;"><c:out value="${measureInfo.witnessName}"/></td>
        			<td class="power-table-label font-bold-label-td">联系方式：</td>
        			<td style="width: 200px;"><c:out value="${measureInfo.witnessContactWay}"/></td>
        		</tr>
        		<tr class="none-border-tr close-info-tr" <c:if test="${measureInfo.measureType != '100'}"> style="display: none;" </c:if>>
        			<td class="power-table-label font-bold-label-td">强制措施对象：</td>
        			<td style="width: 200px;">
        				<c:if test="${measureInfo.measureTargetType == '01'}">财物</c:if>
        				<c:if test="${measureInfo.measureTargetType == '02'}">场所</c:if>
        				<c:if test="${measureInfo.measureTargetType == '03'}">财物、场所</c:if>
        			</td>
        			<td></td>
        			<td></td>
        		</tr>
        		<tr class=" none-border-tr close-info-tr" <c:if test="${measureInfo.measureType != '100' || 
        		(measureInfo.measureTargetType!='01' && measureInfo.measureTargetType!='03')}"> style="display: none;" </c:if>>
        			<td class="power-table-label font-bold-label-td">设施、场所名称：</td>
        			<td colspan="3" style="width: 200px;">
        				<c:out value="${measureInfo.closeDownPlaceName}"/>
        			</td>
        		</tr>
        		<tr class=" none-border-tr close-info-tr" <c:if test="${measureInfo.measureType != '100' || 
        		(measureInfo.measureTargetType!='01' && measureInfo.measureTargetType!='03')}"> style="display: none;" </c:if>>
        			<td class="power-table-label font-bold-label-td">设施、场所地址：</td>
        			<td colspan="3" style="width: 200px;">
        				<c:out value="${measureInfo.closeDownPlaceAddr}"/>
        			</td>
        		</tr>
        		<tr class=" none-border-tr close-info-tr" <c:if test="${measureInfo.measureType != '100' || 
        		(measureInfo.measureTargetType!='02' && measureInfo.measureTargetType!='03')}"> style="display: none;" </c:if>>
        			<td class="power-table-label font-bold-label-td">财物名称、数量、金额：</td>
        			<td colspan="3" style="width: 200px;">
        				<c:out value="${measureInfo.closeDownPropertyInfo}"/>
        			</td>
        		</tr>
        		<tr class="none-border-tr close-info-tr" <c:if test="${measureInfo.measureType != '100'}"> style="display: none;" </c:if>>
        			<td class="power-table-label font-bold-label-td">查封原因：</td>
        			<td colspan="3" style="width: 200px;">
        				<c:out value="${measureInfo.closeDownReason}"/>
        			</td>
        		</tr>
        		<tr class="none-border-tr close-info-tr" <c:if test="${measureInfo.measureType != '200'}"> style="display: none;" </c:if>>
        			<td class="power-table-label font-bold-label-td">当事人是否在场：</td>
        			<td style="width: 200px;">
        				<c:if test="${measureInfo.isDetentionPartyPresent == 1}"> 是 </c:if>
        				<c:if test="${measureInfo.isDetentionPartyPresent == 2}"> 否 </c:if>
        			</td>
        			<td></td>
        			<td></td>
        		</tr>
        		<tr class="none-border-tr detention-info-tr" <c:if test="${measureInfo.isDetentionPartyPresent != 2}">style="display: none;" </c:if>>
        			<td class="power-table-label font-bold-label-td">见证人姓名：</td>
        			<td style="width: 200px;"><c:out value="${measureInfo.detentionWitnessName}"/></td>
        			<td class="power-table-label font-bold-label-td">联系方式：</td>
        			<td style="width: 200px;"><c:out value="${measureInfo.detentionWitnessContactWay}"/></td>
        		</tr>
        		<tr class=" none-border-tr detention-info-tr" <c:if test="${measureInfo.measureType != '200'}"> style="display: none;" </c:if>>
        			<td class="power-table-label font-bold-label-td">财物名称、数量、金额：</td>
        			<td colspan="3" style="width: 200px;">
        				<c:out value="${measureInfo.closeDownPropertyInfo}"/>
        			</td>
        		</tr>
        		<tr class="none-border-tr detention-info-tr" <c:if test="${measureInfo.measureType != '200'}"> style="display: none;" </c:if>>
        			<td class="power-table-label font-bold-label-td">扣押原因：</td>
        			<td colspan="3" style="width: 200px;">
        				<c:out value="${measureInfo.detentionReason}"/>
        			</td>
        		</tr>
        		<tr class="none-border-tr freeze-info-tr" <c:if test="${measureInfo.measureType != '300'}">style="display: none;" </c:if>>
        			<td class="power-table-label font-bold-label-td">冻结金额：</td>
        			<td style="width: 200px;"><c:out value="${measureInfo.freezeNumber}"/></td>
        			<td class="power-table-label font-bold-label-td">金融机构名称：</td>
        			<td style="width: 200px;"><c:out value="${measureInfo.freezeOrganization}"/></td>
        		</tr>
        		<tr class="none-border-tr freeze-info-tr" <c:if test="${measureInfo.measureType != '300'}">style="display: none;" </c:if>>
        			<td class="power-table-label font-bold-label-td">通知冻结日期：</td>
        			<td style="width: 200px;">
        				<c:if test="${performInfo.freezeNoticeDateStr != null && performInfo.freezeNoticeDateStr != 'null'}">
        					<c:out value="${measureInfo.freezeNoticeDateStr}"/>
        				</c:if>
        			</td>
        			<td></td>
        			<td></td>
        			
        		</tr>
        		<tr class="none-border-tr limit-info-tr" <c:if test="${measureInfo.measureType != '400'}">style="display: none;" </c:if>>
        			<td class="power-table-label font-bold-label-td">是否通知家属或所在单位：</td>
        			<td style="width: 200px;">
        				<c:if test="${measureInfo.isNoticeFamily == 1}"> 是 </c:if>
        				<c:if test="${measureInfo.isNoticeFamily == 2}"> 否 </c:if>
        			</td>
        			<td></td>
        			<td></td>
        		</tr>
        		<tr class="none-border-tr detention-info-tr" <c:if test="${measureInfo.measureType != '400' || measureInfo.isNoticeFamily != 2}"> style="display: none;" </c:if>>
        			<td class="power-table-label font-bold-label-td">未通知原因：</td>
        			<td colspan="3" style="width: 200px;">
        				<c:out value="${measureInfo.notNoticeReason}"/>
        			</td>
        		</tr>
        	</table>
        	<table class="TableBlock lookInfo-lowHeight-table" style="width: 95%; background: #fff;">
        		<tr class="none-border-tr">
        			<td style="width: 200px;" class="power-table-label font-bold-label-td">行政强制措施处理决定：</td>
        			<td style="width: 200px;">
        				<c:if test="${measureInfo.measureDealWay == 1}"> 予以销毁 </c:if>
        				<c:if test="${measureInfo.measureDealWay == 2}"> 予以解除 </c:if>
        				<c:if test="${measureInfo.measureDealWay == 3}"> 予以拍卖，抵交罚款</c:if>
        				<c:if test="${measureInfo.measureDealWay == 4}"> 移送司法机关 </c:if>
        			</td>
        			<td style="width: 200px;" class="power-table-label font-bold-label-td"></td>
        			<td style="width: 200px;"></td>
        		</tr>
        	</table>
        </div>
        	<!-- <div class="easyui-panel" title="强制职权" style="width: 95%; height:220px" align="center" >
                <table fit="true" style="width: 100%;" id="measure_power_table"></table>
            </div>
            <br />
            <div class="easyui-panel" title="强制依据" style="width: 95%; height:220px" align="center">
            	<table fit="true" style="width: 100%;" id="measure_gist_table">
            	</table>
            </div> -->
        <br />
        <div class="easyui-panel" title="强制职权" style="width: 100%; height:220px" align="center" >
            <table fit="true" style="width: 100%;" id="measure_power_table">
            </table>
        </div>
        <br />
        <div class="easyui-panel" title="强制依据" style="width: 100%; height:220px" align="center">
            <table fit="true" style="width: 100%;" id="measure_gist_table"> 
            </table>
        </div>
        <br />
        
    </div>
</body>

</html>
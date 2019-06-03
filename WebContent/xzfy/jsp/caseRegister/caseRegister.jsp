<%@ page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page import="java.text.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>登记页面</title>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/validator2.0.jsp"%>
<%@ include file="/header/upload.jsp"%>

<link rel="stylesheet" href="../../css/common.css">
<script src="../../js/base/jquery-1.9.1.min.js"></script>
<script src="../../js/base/juicer-min.js"></script>
<link rel="stylesheet" type="text/css" href="../../css/easyui.css">
<link rel="stylesheet" type="text/css" href="../../css/icon.css">
<link rel="stylesheet" type="text/css" href="../../css/demo.css">
<script type="text/javascript" src="../../js/base/jquery.min.js"></script>
<script type="text/javascript" src="../../js/base/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/caseRegister.css" />
<link rel="stylesheet" href="../../css/common.css">
<link rel="stylesheet" type="text/css" href="css/init1.css" />
<link rel="stylesheet" type="text/css" href="css/icon.css" />
<link rel="stylesheet" type="text/css" href="css/easyui.css" />
<link rel="stylesheet" type="text/css" href="css/demo.css" />
<link rel="stylesheet" type="text/css" href="css/case.css" />
<link rel="stylesheet" type="text/css" href="css/iconfont/iconfont.css" />
<script type="text/javascript"
	src="/common/My97DatePicker/WdatePicker.js"></script>

<style>
#d1 {
	margin-bottom: 40px;
	padding: 10px;
	background-color: #fff;
	margin-top: 10px;
}

.tab {
	width: 100%;
	height: 40px;
}

.tab ul li {
	width: 25%;
	line-height: 40px;
	text-align: center;
	float: left;
	cursor: pointer;
}

#rp_btn {
	position: fixed;
	bottom: 0px;
	z-index: 999;
	background: white;
	width: 100%;
	height: 40px;
	text-align: right;
	padding: 6px 30px 6px;
	border-top: 1px #e6e9ed solid;
	/* margin-right: 10px; */
	/* margin: 0 auto; */
}

#rp_btn input {
	width: 80px;
	height: 28px;
	margin-left: 10px;
	border-radius: 5px;
	font-size: 14px;
	color: #fff;
	/* background-color: #3379b7; */
}

.rp-btn-left {
	float: left;
}

.td-col4-title {
	text-align: right;
	width: 17%;
}

.td-col4-content {
	width: 33%;
}

.div-col4-leftext {
	text-align: left;
	text-indent: 9px;
}

.leftextlable {
	text-align: left;
	text-indent: 9px;
}
</style>

</head>

<body onload="doInit();"style="overflow: auto; padding-left: 10px; padding-right: 10px;">
	<input type="hidden" id="caseId" name="caseId" value="">
	<div class="tab">
		<ul>
			<li onclick="showpage(0);"><span
				class="case-tab case-tab-active">来件\接待信息</span></li>
			<li onclick="showpage(1);"><span class="case-tab">当事人信息</span></li>
			<li onclick="showpage(2);"><span class="case-tab">复议事项</span></li>
			<li onclick="showpage(3);"><span class="case-tab">案件材料</span></li>
		</ul>
	</div>

	<!-- 来件接待信息  -->
	<!-- 当面接待开始 -->
	<div class="content" id="d1">
		<button class="getcase" onclick="caseExtraction();">案件提取</button>
		<div class="fyform">
			<div class="div-col">
				<div class="div-col4 div-col4-title">
					<label class="inputlable apply-lable">申请方式：</label>
				</div>
				<div class="div-col4 div-col4-content">
					<select id="applyType" class="fyselect apply-select">
						<option value="">请选择</option>
						<option value="2">当面接待</option>
						<option value="1">书面来件</option>
					</select>
				</div>
			</div>
		</div>

		<div class="fyform select-one">
			<div class="div-col">
				<div class="div-col4 div-col4-title">
					<label class="inputlable">接待地点：</label>
				</div>
				<div class="div-col4 div-col4-content">
					<input id="place" name="place" value="" type="text" class="fyinput"
						id="" placeholder="" />
				</div>
				<div class="div-col4 div-col4-title">
					<label class="inputlable">接待日期：</label>
				</div>
				<div class="div-col4 div-col4-content">
				<input type="text" id="receptionDate" name="receptionDate" value="" readonly
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
							class="Wdate BigInput" style="width: 100%; height: 100%" />
				</div>
			</div>
			<div class="div-col">
				<div class="div-col4 div-col4-title">
					<label class="inputlable">接待人：</label>
				</div>
				<div class="div-col4 div-col4-content">
					<input id="dealMan1Id" name="dealMan1Id" value="" type="text"
						class="fyinput" placeholder="" />
				</div>
				<div class="div-col4 div-col4-title">
					<label class="inputlable">第二接待人：</label>
				</div>
				<div class="div-col4 div-col4-content">
					<input id="dealMan2Id" name="dealMan2Id" value="" type="text"
						class="fyinput" placeholder="" />
				</div>
			</div>
			<div class="div-col">
				<div class="div-col4 div-col4-title">
					<label class="inputlable">处理结果：</label>
				</div>
				<div class="div-col4 div-col4-content">
					<select id="dealResultCode" name="dealResultCode" value=""
						class="fyselect">
					</select>
				</div>
				<div class="div-col4 div-col4-title">
					<label class="inputlable  control-label">复议请求类型：</label>
				</div>
				<div class="div-col4 div-col4-content">
					<select id="recertionTypeCode" name="recertionTypeCode" value=""
						class="fyselect">
					</select>
				</div>
			</div>

			<div class="recpinfo">
				<p class="case-head-title">被接待人信息</p>
				<div class="edit-table-div">
					<table id="recp-table" class="edit-table"></table>
					<p class="edit-table-add" onclick="recp_insertRow();">添加</p>
				</div>
			</div>

			<div class="div-col">
				<div class="div-col2 div-col2-title">
					<label class="inputlable leftextlable">接待情况：</label>
				</div>
				<div class="div-col2 div-col2-content">
					<textarea id="receptionDetail" name="receptionDetail" value=""
						class="rowinput" rows="3"></textarea>
				</div>
			</div>

			<div class="other-one">
				<div class="div-col4 div-col4-longtitle div-col4-leftext">
					<label class="leftextlable">其他复议机关\法院受理同一复议申请：</label>
				</div>
				<span><td><input type="radio"
						name="isReconsiderTogether" value="1"
						<c:if test="${isReconsiderTogether=='1'}">checked="checked"</c:if> />是</span>
				<span><td><input type="radio"
						name="isReconsiderTogether" value="0"
						<c:if test="${isReconsiderTogether=='0'}">checked="checked"</c:if> />
						否</span>
				<div class="div-col4 div-col4-longtitle">
					<label>复议机关名称：</label>
				</div>
				<select id="reconsiderOrganCode" value=""
					class="reconsiderOrganCode">
				</select>
			</div>
			<div class="other-two">
				<div class="div-col4 div-col4-longtitle div-col4-leftext"
					<label class="leftextlable">接收材料：</label></div>

				<span><td><input type="radio" onclick="showTable();"
						name="isReceiveMaterial" value="1"
						<c:if test="${isReceiveMaterial=='1'}">checked="checked"</c:if> />是</td></span>
				<span><td><input type="radio" onclick="hideTable();"
						name="isReceiveMaterial" value="0"
						<c:if test="${isReceiveMaterial=='0'}">checked="checked"</c:if> />
						否</td></span>
			</div>
			<div class="material-div">
				<div class="edit-table-div ">
					<table id="material-table" class="edit-table"></table>
					<p class="edit-table-add" onclick="material_insertRow();">添加</p>
				</div>
			</div>
		</div>
		<!-- 当面接待结束 -->

		<!-- 书面来件 -->
		<div class="fyform select-two">
			<div class="div-col">
				<div class="div-col4 div-col4-title">
					<label class="inputlable">来信人姓名：</label>
				</div>
				<div class="div-col4 div-col4-content">
					<input type="text" class="fyinput" id="senderName" name="senderName"
						value="" placeholder="" />
				</div>
				<div class="div-col4 div-col4-title">
					<label class="inputlable">来信人电话：</label>
				</div>
				<div class="div-col4 div-col4-content">
					<input type="text" class="fyinput" id="senderPhone" name="senderPhone"
						value="" placeholder="" />
				</div>
			</div>

			<div class="div-col">
				<div class="div-col4 div-col4-title">
					<label class="inputlable">申请日期：</label>
				</div>
				<div class="div-col4 div-col4-content">
					<div class="fyinput">
						<input type="text" id="receiveDate" name="receiveDate" value="" readonly
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
							class="Wdate BigInput" style="width: 100%; height: 100%" />
					</div>
				</div>
				<div class="div-col4 div-col4-title">
					<label class="inputlable">收件类型：</label>
				</div>
				<div class="div-col4 div-col4-content">
					<select id="letterTypeCode" name="letterTypeCode" class="fyselect">
					</select>
				</div>
			</div>

			<div class="div-col">
				<div class="div-col4 div-col4-title">
					<label class="inputlable">来信人通信地址：</label>
				</div>
				<div class="div-col4 div-col4-content">
					<input id="senderAddress" name="senderAddress" value="" type="text"
						class="fyinput" placeholder="" />
				</div>
				<div class="div-col4 div-col4-title">
					<label class="inputlable">来信人邮编：</label>
				</div>
				<div class="div-col4 div-col4-content">
					<input id="senderPostCode" name="senderPostCode" value="" type="text"
						class="fyinput" placeholder="" />
				</div>
			</div>

			<div class="div-col">
				<div class="div-col4 div-col4-title">
					<label class="inputlable">来文编号：</label>
				</div>
				<div class="div-col4 div-col4-content">
					<input id="letterNum" name="letterNum" value="" type="text" class="fyinput"
						placeholder="" />
				</div>
				<div class="div-col4 div-col4-title"></div>
				<div class="div-col4 div-col4-content"></div>
			</div>
		</div>
	</div>
	<!-- 来件接待信息结束 -->

	<!-- 当事人信息  -->
	<div class="content" id="d1">
		<div class="partyDiv">
			<p class="case-head-title">申请人</p>
			<div class="party-body">
				<div>
					<label>申请人类别：</label> <span><input type="radio"
						name="optradio" /> 公民</span> <span><input type="radio"
						name="optradio" /> 法人或其他组织</span> <span><input type="radio"
						name="optradio" /> 个体工商户</span>
				</div>

				<div class="edit-table-div applicant-div">
					<table id="applicant-table" class="edit-table"></table>
					<p class="edit-table-add" onclick="applicant_insertRow();">添加</p>
				</div>
				<hr />
				<!-- 用button控制添加的表格 -->
				<button class="fy-btn blue-btn party-btn"
					onclick="add_otherApplicant();">添加其他申请人</button>
				<div class="edit-table-div applicant-otherApplicant-div">
					<table id="applicant-otherApplicant-table" class="edit-table"></table>
				</div>

				<button class="fy-btn blue-btn party-btn" onclick="add_agent();">添加申请人代理人</button>
				<div class="edit-table-div applicant-agent-div">
					<table id="applicant-agent-table" class="edit-table"></table>
				</div>
			</div>

			<p class="case-head-title">被申请人</p>
			<div class="party-body">
				<div class="edit-table-div applicant-div">
					<table id="respondent-table" class="edit-table"></table>
					<p class="edit-table-add" onclick="respondent_insertRow();">添加</p>
				</div>
				<hr />
				<!--  用button控制添加的表格 -->
				<button class="fy-btn blue-btn party-btn"
					onclick="add_respondent_agent();">添加被申请人代理人</button>
				<div class="edit-table-div respondent-agent-div">
					<table id="respondent-agent-table" class="edit-table"></table>
				</div>
			</div>

			<p class="case-head-title">第三人</p>
			<div class="party-body">
				<div class="edit-table-div applicant-div">
					<table id="thirdParty-table" class="edit-table"></table>
					<p class="edit-table-add" onclick="thirdParty_insertRow();">添加</p>
				</div>
				<hr />
				<!-- 用button控制添加的表格 -->
				<button class="fy-btn blue-btn party-btn"
					onclick="add_thirdParty_agent();">添加第三人代理人</button>
				<div class="edit-table-div thirdParty-agent-div">
					<table id="thirdParty-agent-table" class="edit-table"></table>
				</div>
			</div>
		</div>
	</div>

	<!-- 复议事项  -->
	<div class="content" id="d1">
		<div class="fyform">
			<table class="table-col4">
				<tr class="tr-col4">
					<td class="td-col4-title"><label class="inputlable">案件编号：</label></td>
					<td class="td-col4-content">
						<!-- 案件编号暂时改成输入框 --> 
						<input id="caseNum" name="caseNum" value="" type="text" class="fyinput" placeholder="" />
					<td class="td-col4-title"><label class="inputlable">申请日期：</label></td>
					<td class="td-col4-content">
						<div class="fyinput">
							<input type="text" id="applicationDate" name="applicationDate"
								value="" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
								class="Wdate BigInput" style="width: 100%; height: 100%" />
						</div>
					</td>
				</tr>

				<tr class="tr-col4">
					<td class="td-col4-title"><label class="inputlable">申请事项：</label></td>
					<td class="td-col4-content"><select id="applicationItemCode"
						name="applicationItemCode" value="" class="fyselect">
					</select></td>
					<td class="td-col4-title"><label class="inputlable">行政类别管理：</label></td>
					<td class="td-col4-content">
					<select id="categoryCode"
						name="categoryCode" value="" class="fyselect">
					</select></td>
				</tr>

				<tr class="tr-col4">
					<td class="td-col4-title"><label class="inputlable">行政不作为：</label></td>
					<td class="td-col4-content"><input type="radio"
						name="isNonfeasance" value="1" onclick="showNotPoliticalDo(this)" />是
						<input type="radio" name="isNonfeasance" value="0"
						onclick="showPoliticalDo(this)" />否</td>
					<td class="td-col4-title notpoliticaldo"><label
						class="inputlable">不作为事项：</label></td>
					<td class="td-col4-content notpoliticaldo"><select
						id="nonfeasanceItemCode" name="nonfeasanceItemCode" value=""
						class="fyselect">
					</select></td>
					<td class="td-col4-title politicaldo"><label
						class="inputlable">具体行政行为名称：</label></td>
					<td class="td-col4-content politicaldo">
					<input id="specificAdministrativeName" name="specificAdministrativeName" value="" type="text" class="fyinput" placeholder="" /></td>
				</tr>

				<!-- 行政不作为选项为是 -->
				<tr class="tr-col4 notpoliticaldo">
					<td colspan="3" class="td-col4-title"><label
						class="inputlable">申请人要求被申请人履行该项法定职责日期：</label></td>
					<td colspan="1" class="td-col4-content"><input type="text"
						id="nonfeasanceDate" name="nonfeasanceDate" value="" readonly
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
						class="Wdate BigInput" style="width: 100%; height: 100%;" /></td>
				</tr>

				<!-- 行政不作为选项为否 -->
				<tr class="tr-col4 politicaldo">
					<td class="td-col4-title"><label class="inputlable">具体行政行为文号：</label></td>
					<td class="td-col4-content"><input type="text" class="fyinput"
						id="specificAdministrativeNo" name="specificAdministrativeNo"
						value="" placeholder="" /></td>
					<td class="td-col4-title"><label class="inputlable">具体行政行为日期：</label></td>
					<td class="td-col4-content"><input type="text"
						id="applicationDate" name="applicationDate" value="" readonly
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
						class="Wdate BigInput" style="width: 100%; height: 100%" /></td>
				</tr>

				<tr class="tr-col4 politicaldo">
					<td class="td-col4-title"><label class="inputlable">收到处罚决定书日期：</label></td>
					<td class="td-col4-content">
						<div class="fyinput">
							<input type="text" id="receivedPunishDate"
								name="receivedPunishDate" value="" readonly
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
								class="Wdate BigInput" style="width: 100%; height: 100%" />
					</td>
					</div>
					</td>
					<td class="td-col4-title"><label class="inputlable">得知该具体行为途径：</label></td>
					<td class="td-col4-content"><input id="receivedSpecificWay"
						value="receivedSpecificWay" type="text" class="fyinput" id=""
						placeholder="" /></td>
				</tr>

				<tr>
					<td class="td-col4-title"><label class="inputlable">具体行政行为：</label></td>
					<td colspan="3"><textarea id="specificAdministrativeDetail"
							value="specificAdministrativeDetail" class="rowinput" rows="3"></textarea></td>
				</tr>
				<tr>
					<td class="td-col4-title"><label class="inputlable">行政复议请求：</label></td>
					<td colspan="3"><textarea id="requestForReconsideration"
							value="requestForReconsideration" class="rowinput" rows="3"></textarea></td>
				</tr>

				<!-- 按钮组 -->
				<tr>
					<td class="td-col4-title"><label class="inputlable">行政复议前置：</label></td>
					<td><span class="radio-group"> <input type="radio"
							name="isReview" value="1"
							<c:if test="${isReview=='1'}">checked="checked"</c:if> /><label>是</label>
							<input type="radio" name="isReview" value="0"
							<c:if test="${isReview=='0'}">checked="checked"</c:if> /><label>否</label>
					</span></td>

					<td class="td-col4-title"><label class="inputlable">申请举行听证会：</label></td>
					<td><span class="radio-group"> <input type="radio"
							name="isHoldHearing" value="1"
							<c:if test="${isReview=='1'}">checked="checked"</c:if> /><label>是</label>
							<input type="radio" name="isHoldHearing" value="0"
							<c:if test="${isReview=='0'}">checked="checked"</c:if> /><label>否</label>
					</span></td>
				</tr>
				<tr>
					<td class="td-col4-title"><label class="inputlable">申请赔偿：</label></td>
					<td colspan="3"><span class="radio-group"> <input
							type="radio" name="isCompensation" onclick="showInput(this);"
							value="1"
							<c:if test="${isCompensation=='1'}">checked="checked"</c:if> /><label>是</label>
							<input type="radio" name="isCompensation"
							onclick="hideInput(this);" value="0"
							<c:if test="${isCompensation=='0'}">checked="checked"</c:if> /><label>否</label>
					</span> <span class="li-input"> <label class="lilable">赔偿请求类型：</label>

							<select id="compensationTypeCode" name="compensationTypeCode"
							value="" class="fyselect">
						</select> <label class="lilable">赔偿请求金额：</label> <input type="text"
							class="form-control" id="compensationAmount"
							name="compensationAmount" placeholder=""></span>
				</tr>
				<tr>
					<td class="td-col4-title"><label class="inputlable">申请附带对规范性文件的审查：</label></td>
					<td colspan="3"><span class="radio-group"> <input
							type="radio" name="isDocumentReview" onclick="showInput(this);"
							value="1"
							<c:if test="${isDocumentReview=='1'}">checked="checked"</c:if> /><label>是</label>
							<input type="radio" name="isDocumentReview"
							onclick="hideInput(this);" value="0"
							<c:if test="${isDocumentReview=='0'}">checked="checked"</c:if> /><label>否</label>
					</span> <span class="li-input"> <label class="lilable">规范性文件名称：</label>
							<input type="text" class="form-control" id="documentReviewName"
							name="documentReviewName" placeholder=""></span></td>
				</tr>
			</table>
		</div>
	</div>

	<!-- 案件材料  -->
	<div class="content  materialDiv" id="d1"></div>

	<div id="rp_btn" class="fr">
		<span class="rp-btn-left"> <input id="record"
			class="fy-btn yellow-btn" type="button" value="接待笔录" title="接待笔录"
			onclick="" /> <input id="receipt" class="fy-btn yellow-btn"
			type="button" value="材料收据" title="材料收据" onclick="" /> <input
			id="sheet" class="fy-btn yellow-btn" type="button" value="地址确认单"
			title="地址确认单" onclick="" />
		</span> <input id="back" class="fy-btn blue-btn" type="button" value="上一步"
			title="上一步" onclick="back();" /> <input id="save"
			class="fy-btn green-btn" type="button" value="暂  存" title="暂存"
			onclick="save();" /> <input id="copy" class="fy-btn yellow-btn"
			type="button" value="保存并复制" title="保存并复制" onclick="" /> <input
			id="forward" class="fy-btn blue-btn" type="button" value="下一步"
			title="下一步" onclick="forward();" />
	</div>
	<script src="/xzfy/js/common/common.js"></script>
	<script src="/xzfy/js/caseRegister/registerCommon.js"></script>
	<script src="/xzfy/js/caseRegister/caseRegister.js"></script>
	<script src="/xzfy/js/caseRegister/letterrReception.js"></script>
	<script src="/xzfy/js/caseRegister/clientInfoRegister.js"></script>
	<script src="/xzfy/js/caseRegister/reviewMatters.js"></script>
	<script>
		$('.li-input').hide();
		$('.select-two').hide();

		$('.notpoliticaldo').hide();
		$('.politicaldo').hide();

		function showInput(that) {
			$(that).parent().siblings('.li-input').show();
		}

		function hideInput(that) {
			$(that).parent().siblings('.li-input').hide();
		}

		function showNotPoliticalDo(that) {
			$(that).parents().siblings('.politicaldo').hide();
			$(that).parents().siblings('.notpoliticaldo').show();
		}

		function showPoliticalDo(that) {
			$(that).parents().siblings('.politicaldo').show();
			$(that).parents().siblings('.notpoliticaldo').hide();
		}

		$(".apply-select").change(function() {
			var selected = $(".apply-select").val();
			receptionInit();
			if (selected == 1) {
				$('.select-one').hide();
				$('.select-two').show();
			} else {
				$('.select-one').show();
				$('.select-two').hide();
			}
		})

		$('.case-tab').click(function() {
			$('.case-tab').removeClass('case-tab-active');
			$(this).addClass('case-tab-active');
		})
	</script>
</body>

</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui.jsp"%>
<%@ include file="/header/ztree.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	 
    <link rel="stylesheet" type="text/css" href="/xzfy/css/init1.css" />
    <link rel="stylesheet" type="text/css" href="/xzfy/css/common.css" />
    <link rel="stylesheet" type="text/css" href="/xzfy/css/index.css" />
  
    <script type="text/javascript" src="/xzfy/js/common/common.js"></script>
    <script type="text/javascript" src="/xzfy/js/base/juicer-min.js"></script>
    <script type="text/javascript" src="/common/My97DatePicker/WdatePicker.js"></script>
    <title>登记信息</title>
  
    <style type="text/css">
    html, body, div{
	margin:0;
	padding:0;
	border:0;
	outline:0;
	font-weight:inherit;
	font-style:inherit;
	font-family:inherit;
	vertical-align:baseline;
	-webkit-text-size-adjust:90%;
    overflow: auto;
	}

    </style>
</head>

<body onload="doInit()">
    <div id="reception">
		<div class="lanky-container">
			<div class="lanky-title">接待信息</div>
				<div class="lanky-content">
				<table class="lanky-table" id=" ">
				  <tr>
					  <td class="lanky-item ">申请方式：</td>
					  <td class="lanky-td "><input class="lanky-input " type="text" id="applicationType" name="applicationType" /></td>
					  <td class="lanky-item ">接待日期：</td>
					  <td class="lanky-td "><input class="lanky-input " type="text" id="receptionDate" name="receptionDate" /></td>
					  <td class="lanky-item ">接待人名称：</td>
					  <td class="lanky-td "><input class="lanky-input " type="text" id="dealMan1Id" name="dealMan1Id" /></td>
					  <td class="lanky-item ">第二接待人：</td>
					  <td class="lanky-td "><input class="lanky-input " type="text" id="dealMan2Id" name="dealMan2Id" /></td>
				  </tr>
				  <tr>
					  <td class="lanky-item ">接待地点：</td>
					  <td class="lanky-td " colspan="7 "><input class="lanky-input" type="text" id="place" name="place" /></td>
				  </tr>
				  <tr>
					  <td class="lanky-item ">复议请求：</td>
					  <td class="lanky-td "><input class="lanky-input " type="text" id="recertionTypeCode" name="recertionTypeCode" /></td>
					  <td class="lanky-item ">处理结果：</td>
					  <td class="lanky-td "><input class="lanky-input " type="text" id="dealResultCode" name="dealResultCode" /></td>
					  <td class="lanky-item ">是否接受材料：</td>
					  <td class="lanky-td "><input class="lanky-input " type="text" id="isReceivingMaterial" name="isReceivingMaterial" /></td>
					  
				  </tr>
				  <tr>
					  <td class="lanky-item ">被接待人信息：</td>
					  <td class="lanky-td " colspan="7 ">
					  <textarea class="lanky-input " rows="3 " cols="100 " id="visitorVo"></textarea>
				  	  </td>
				  </tr>
				  <tr>
					  <td class="lanky-item ">接待情况：</td>
					  <td class="lanky-td " colspan="7 ">
					  <textarea class="lanky-input " rows="3 " cols="100 " id="receptionDetail"></textarea>
					  </td>
				  </tr>
				  <tr>
					  <td class="lanky-item ">材料信息：</td>
					  <td class="lanky-td " colspan="7 ">
					  <textarea class="lanky-input " rows="3 " cols="100 " id="listFyMaterial"></textarea>
					  </td>
				  </tr>
				</table>
			</div>
		</div >
    </div>
    
    <div id="letter">
    	<div class="lanky-container">
   			<div class="lanky-title">来件信息</div>
				<div class="lanky-content">
    				<table class="lanky-table" id="">
					<tr>
						<td class="lanky-item">来信人姓名：</td>
						<td class="lanky-td">
							<input class="lanky-input" type="text" id="senderName"  /></td>
						<td class="lanky-item">来行人电话：</td>
						<td class="lanky-td">
							<input class="lanky-input" type="text" id="senderPhone" /></td>
				 		<td class="lanky-item">收件日期：</td>
						<td class="lanky-td">
							<input class="lanky-input" type="text" id="receiveDate"/></td>
						<td class="lanky-item">收件类型：</td>
						<td class="lanky-td">
							<input class="lanky-input" type="text" id="letterType"/></td>
					</tr>
					<tr>
						<td class="lanky-item">来信人邮编：</td>
						<td class="lanky-td">
							<input class="lanky-input" type="text" id="senderPostCode" /></td>
						<td class="lanky-item">来件编号：</td>
						<td class="lanky-td">
							<input class="lanky-input" type="text" id="senderNo" />
						</td>
					</tr>
					<tr>
						<td class="lanky-item">来信人通信地址：</td>
						<td class="lanky-td" colspan="7">
							<input class="lanky-input" type="text" id="senderAddress" />
						</td>
					</tr>
				</table>
			</div>
		</div>
    </div>
    
    <div id="clientInfo">
		<div class="lanky-container">
			<div class="lanky-title">当事人信息</div>
				<div class="lanky-content">
					<table class="lanky-table" id=" ">
			    <tr>
				    <td class="lanky-item proposer">申请人类型：</td>
				    <td class="lanky-td ">
				    	<input class="lanky-input " type="text" id="applicantType" />
				    </td>
				    <td class="lanky-item ">申请人总数：</td>
				    <td class="lanky-td ">
				    	<input class="lanky-input " type="text" id="total" />
				    </td>
				    <td class="lanky-item ">是否有申请人代理人：</td>
				    <td class="lanky-td ">
					    <input class="lanky-input " type="text" id="isAgent" />
				    </td>
				    <td class="lanky-item ">是否有被申请人代理人：</td>
				    <td class="lanky-td ">
					    <input class="lanky-input " type="text" id="isRespondentAgent" />
				    </td>
				</tr>
			    <tr>
				    <td class="lanky-item ">是否有第三人：</td>
				    <td class="lanky-td ">
				    	<input class="lanky-input " type="text" id="isThirdParty" />
				    </td>
				    <td class="lanky-item ">是否有第三人代理人：</td>
				    <td class="lanky-td ">
				    	<input class="lanky-input " type="text" id="isThirdPartyAgent" />
				    </td>
				   
			   </tr>
			   <tr>     
				    <td class="lanky-item ">申请人信息：</td>
				    <td class="lanky-td " colspan="7 ">
				    <textarea class="lanky-input " rows="3 " cols="100 " id="applicant"></textarea>
				    </td>
			    </tr>
			    <tr>
				    <td class="lanky-item ">其他申请人信息：</td>
				    <td class="lanky-td " colspan="7 ">
				    <textarea class="lanky-input " rows="3 " cols="100 " id="otherApplicant"></textarea>
				    </td>
			    </tr>
			    <tr>
				    <td class="lanky-item ">申请人代理人信息：</td>
				    <td class="lanky-td " colspan="7 ">
				    <textarea class="lanky-input " rows="3 " cols="100 " id="applicantAgent"></textarea>
				    </td>
			    </tr>
			    <tr>
				    <td class="lanky-item ">被申请人信息：</td>
				    <td class="lanky-td " colspan="7 ">
				    <textarea class="lanky-input " rows="3 " cols="100 " id="respondent"></textarea>
				    </td>
			    </tr>
			    <tr>
				    <td class="lanky-item ">被申请人代理人信息：</td>
				    <td class="lanky-td " colspan="7 ">
				    <textarea class="lanky-input " rows="3 " cols="100 " id="respondentAgent"></textarea>
				    </td>
			    </tr>
			    <tr>
				    <td class="lanky-item ">第三人信息：</td>
				    <td class="lanky-td " colspan="7 ">
				    <textarea class="lanky-input " rows="3 " cols="100 " id="thirdParty"></textarea>
				    </td>
			    </tr>
			    <tr>
				    <td class="lanky-item ">第三人代理人信息：</td>
				    <td class="lanky-td " colspan="7 ">
				    <textarea class="lanky-input " rows="3 " cols="100 " id="thirdPartyAgent"></textarea>
				    </td>
			    </tr>
			    
			</table>
		    </div>
		</div >
    </div>
    
    <div id="matters">
		<div class="lanky-container">
			<div class="lanky-title">复议事项</div>
			<div class="lanky-content">
				<table class="lanky-table" id=" ">
			    <tr>
				    <td class="lanky-item ">申请日期：</td>
				    <td class="lanky-td ">
				    	<input class="lanky-input " type="text" id="applicationDate" />
				    </td>
				    <td class="lanky-item ">行政类别管理：</td>
				    <td class="lanky-td ">
			    		<input class="lanky-input " type="text" id="category" />
			   		</td>
				    <td class="lanky-item ">申请事项：</td>
				    <td class="lanky-td ">
				    	<input class="lanky-input " type="text" id="applicationItem" />
				    </td>
				    <td class="lanky-item ">行政不作为：</td>
				    <td class="lanky-td ">
				    	<input class="lanky-input " type="text" id="isNonfeasance" />
				    </td>
			    </tr>
			    
			    <tr id="isNonfeasanceAtra" style="display: none;">
			        <td class="lanky-item ">具体行政行为名称：</td>
				    <td class="lanky-td ">
				    	<input class="lanky-input " type="text" id="specificAdministrativeName"/>
				    </td>
				    <td class="lanky-item ">具体行政行为文号：</td>
				    <td class="lanky-td ">
				   		<input class="lanky-input " type="text" id="specificAdministrativeNo" />
				    </td>
				    <td class="lanky-item ">具体行政行为做出日期：</td>
				    <td class="lanky-td ">
				    	<input class="lanky-input " type="text" id="specificAdministrativeDate" />
				    </td>
				    <td class="lanky-item ">收到处罚决定书日期：</td>
				    <td class="lanky-td ">
				    	<input class="lanky-input " type="text" id="receivedPunishDate" />
				    </td>
			    </tr>
			    <tr id="isNonfeasanceAtrb" style="display: none;">
				    <td class="lanky-item ">得知该具体行为途径：</td>
				    <td class="lanky-td ">
				    	<input class="lanky-input " type="text" id="receivedSpecificWay" />
				    </td>
			    </tr>
			    
			    <tr id="isNonfeasanceBtr" style="display: none;">
			        <td class="lanky-item ">不作为事项：</td>
				    <td class="lanky-td ">
	          			<input class="lanky-input" type="text" id="nonfeasanceItem" />
				    </td>
				    <td class="lanky-item " colspan="3">申请人要求被申请人履行该项法定责任日期：</td>
				    <td class="lanky-td ">
				    	<input class="lanky-input" type="text" id="nonfeasanceDate" />
				    </td>
			    </tr>
			    
			    
			    <tr>
				    <td class="lanky-item ">具体行政行为：</td>
				    <td class="lanky-td " colspan="7 ">
				    	<textarea class="lanky-input " rows="3 " cols="100 " id="specificAdministrativeDetail"></textarea>
				    </td>
			    </tr>
			    <tr>
				    <td class="lanky-item ">行政复议请求：</td>
				    <td class="lanky-td " colspan="7 ">
				    	<textarea class="lanky-input " rows="3 " cols="100 " id="requestForReconsideration"></textarea>
				    </td>
			    </tr>
			    <tr>
				    <td class="lanky-item ">行政复议前置：</td>
				    <td class="lanky-td ">
				    	<input class="lanky-input " type="text" id="isReview" />
				    </td>
				    
				    <td class="lanky-item ">申请举行听证会：</td>
				    <td class="lanky-td ">
					    <input class="lanky-input " type="text" id="isHoldHearing" />
				    </td>
				    <td class="lanky-item ">申请对规范性文件审查：</td>
				    <td class="lanky-td ">
					    <input class="lanky-input " type="text" id="isDocumentReview" />
				    </td>
				    <td class="lanky-item ">规范性文件名称：</td>
				    <td class="lanky-td ">
					    <input class="lanky-input " type="text" id="documentReviewName" />
				    </td>
			    </tr>
			    <tr>
				    <td class="lanky-item ">申请赔偿：</td>
				    <td class="lanky-td ">
					    <input class="lanky-input " type="text" id="isCompensation" />
				    </td>
				    <td class="lanky-item ">赔偿请求类型：</td>
				    <td class="lanky-td ">
				    	<input class="lanky-input " type="text" id="compensationType" />
				    </td>
				    <td class="lanky-item ">赔偿请求金额：</td>
				    <td class="lanky-td ">
				   		<input class="lanky-input " type="text" id="compensationAmount" />
				    </td>
		     </tr>
		    </table>
		    </div>
		    </div >
    </div>
    <script type="text/javascript" src="/xzfy/js/caseAcceptence/acceptcommon.js"></script>
    <script type="text/javascript" src="/xzfy/js/caseAcceptence/caseRegister.js"></script>
    
</body>

</html>
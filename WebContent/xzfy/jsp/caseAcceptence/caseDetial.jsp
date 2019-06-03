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
	<link rel="stylesheet" type="text/css" href="/xzfy/css/case.css" />
	<link rel="stylesheet" type="text/css" href="/xzfy/imgs/caseInfo_icon/iconfont.css" />
    
    <script type="text/javascript" src="/xzfy/js/common/common.js"></script>
    <script type="text/javascript" src="/xzfy/js/base/juicer-min.js"></script>
    <script type="text/javascript" src="/common/My97DatePicker/WdatePicker.js"></script>
    <title>案件详情</title>
    <style type="text/css">
    html, body, div{
	margin:0;
	padding:0;
	border:0;
	outline:0;
	font-weight:inherit;
	font-style:inherit;
	font-size:100%;
	font-family:inherit;
	vertical-align:baseline;
	-webkit-text-size-adjust:90%;
    overflow: auto;
	}

	.iconfont {
		cursor: pointer;
	}
    </style>
</head>

<body onload="doInit()" >
    <div id="toolbar" class = " clearfix" style="margin-top: 5px">
	    <div class="fl" style="position:static">
		    <span class="title" id="title"></span>
	    </div>
	    <div class = "right fr clearfix" id="accept" >
	    </div>
	    <span class="basic_border_grey" style="margin-top: 10px"></span>
   	</div>
    <div id="case-div">
		<div class="lanky-container">
			<div class="lanky-title">当事人信息</div>
			<table class="lanky-table">
				<tr>
					<td class="lanky-item">申请人类型：</td>
					<td class="lanky-td">
						<input class="lanky-input " type="text" id="applicantType"/>
					</td>
					<td class="lanky-item">申请人总数：</td>
					<td class="lanky-td"id="">
					    <input class="lanky-input " type="text" id="totalApplicant"/>
					</td>
					<td class="lanky-item">是否有申请人代理人：</td>
					<td class="lanky-td">
						<input class="lanky-input " type="text" id="isAgent"/>
					</td>
					<td class="lanky-item">是否有被申请人代理人：</td>
					<td class="lanky-td">
						<input class="lanky-input " type="text" id="isRespondent"/>
					</td>
				</tr>
				<tr>
					<td class="lanky-item">是否有第三人：</td>
					<td class="lanky-td">
						<input class="lanky-input " type="text" id="isThird"/>
					</td>
					<td class="lanky-item">是否有第三人代理人：</td>
					<td class="lanky-td">
						<input class="lanky-input " type="text" id="isThirdAgent"/>
					</td>
					
				</tr>
			</table>
		</div>
		<!-- 申请人信息 -->
		<div class="recpinfo">
			<p class="case-head-title">申请人信息：</p>
			<div class="edit-table-div">
				<table id="applicant-table" class="edit-table">
					<tr class="">
						<th class="">序号</th>
						<th class="">姓名</th>
						<th class="">证件类型</th>
						<th class="">证件号</th>
						<th class="">性别</th>
						<th class="">民族</th>
						<th class="">送达地址</th>
						<th class="">邮编</th>
						<th class="">联系电话</th>
						
					</tr>
				</table>
				
			</div>
		</div>
			
		<!-- 其他申请人 -->
		<div class="recpinfo">
			<p class="case-head-title">其他申请人信息：</p>
			<div class="edit-table-div">
				<table id="otherApplicant-table" class="edit-table">
					<tr class="">
						<th class="">序号</th>
						<th class="">姓名</th>
						<th class="">性别</th>
						<th class="">证件类型</th>
						<th class="">证件号</th>
						
					</tr>
				</table>
				
			</div>
		</div>

		<!-- 申请人代理人 -->
		<div class="recpinfo" id="applicantAgent">
			<p class="case-head-title">申请人代理人信息：</p>
			<div class="edit-table-div">
				<table id="applicantAgent-table" class="edit-table">
					<tr class="">
						<th class="">序号</th>
						<th class="">代理人类型</th>
						<th class="">代理人姓名</th>
						<th class="">证件类型</th>
						<th class="">证件号</th>
						<th class="">联系电话</th>
						<th class="">是否授权书</th>
						<th class="">被代理人</th>
						
					</tr>
				</table>
				
			</div>
		</div>

		<!-- 被申请人 -->
		<div class="recpinfo">
			<p class="case-head-title">被申请人：</p>
			<div class="edit-table-div">
				<table id="respondent-table" class="edit-table">
					<tr class="">
						<th class="">序号</th>
						<th class="">被申请人类型</th>
						<th class="">被申请人姓名</th>
						
					</tr>
				</table>
				
			</div>
		</div>
	
		<!-- 被申请人代理人 -->
		<div class="recpinfo" id="respondentAgent">
			<p class="case-head-title">被申请人代理人信息：</p>
			<div class="edit-table-div">
				<table id="respondentAgent-table" class="edit-table">
					<tr class="">
						<th class="">序号</th>
						<th class="">代理人类型</th>
						<th class="">代理人姓名</th>
						<th class="">证件类型</th>
						<th class="">证件号</th>
						<th class="">联系电话</th>
						<th class="">是否授权书</th>
						<th class="">被代理人</th>
					</tr>
				</table>
			</div>
		</div>

	<!-- 第三人 -->
	<div class="recpinfo" id="third">
		<p class="case-head-title">第三人信息：</p>
		<div class="edit-table-div">
			<table id="third-table" class="edit-table">
				<tr class="">
					<th class="">序号</th>
					<th class="">姓名</th>
					<th class="">证件类型</th>
					<th class="">证件号</th>
					<th class="">性别</th>
					<th class="">民族</th>
					<th class="">送达地址</th>
					<th class="">邮编</th>
					<th class="">联系电话</th>
				</tr>
			</table>
		</div>
	</div>
		
		<!-- 第三人代理人 -->
		<div class="recpinfo" id="thirdAgent">
				<p class="case-head-title">第三人代理人信息：</p>
				<div class="edit-table-div">
					<table id="thirdAgent-table" class="edit-table">
						<tr class="">
							<th class="">序号</th>
							<th class="">代理人类型</th>
							<th class="">代理人姓名</th>
							<th class="">证件类型</th>
							<th class="">证件号</th>
							<th class="">联系电话</th>
							<th class="">是否授权书</th>
							<th class="">被代理人</th>
						</tr>
					</table>
				</div>
			</div>

	<!-- 复议事项 -->
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
				    <td class="lanky-item">行政不作为：</td>
				    <td class="lanky-td">
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
	          			<input class="lanky-input " type="text" id="nonfeasanceItem" />
				    </td>
				    <td class="lanky-item " colspan="3">申请人要求被申请人履行该项法定责任日期：</td>
				    <td class="lanky-td ">
				    	<input class="lanky-input " type="text" id="nonfeasanceDate" />
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
	    				<input class="lanky-input " type="text" id="compensationTypeCode" />
				    </td>
				    <td class="lanky-item ">赔偿请求金额：</td>
				    <td class="lanky-td ">
				   		<input class="lanky-input " type="text" id="compensationAmount" />
				    </td>
		     </tr>
		    </table>
		    </div>
		</div>
    </div>
    <script type="text/javascript" src="/xzfy/js/caseAcceptence/acceptcommon.js"></script>
    <script type="text/javascript" src="/xzfy/js/caseAcceptence/caseDetial.js"></script>
    <script type="text/javascript" src="/xzfy/js/caseAcceptence/caseInfo.js"></script>
</body>

</html>
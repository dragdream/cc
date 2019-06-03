<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="/xzfy/css/common/table.css" />
<link rel="stylesheet" type="text/css" href="/xzfy/css/common/supervise.css" />
<!-- 中腾按钮框架 -->
<link rel="stylesheet" type="text/css" href="/common/zt_webframe/css/package1.css" />
<%
	String caseId = request.getParameter("caseId");
	String caseNum = request.getParameter("caseNum");
	String time = request.getParameter("time");
%>

<title>案件信息</title>
<style> 
	textarea.textstyle {
		font-size: 13px;
	    color: #555555;
	    /* border: 1px solid #C0BBB4; */
    	border: 1px solid #cccccc;
    	border-radius: 3px;
	}
</style>

</head>
<body style="padding-left: 10px;padding-right: 10px;">
<!-- 菜单栏 -->

<div class="base_layout_top" style="position:static">
	<span class="" style="padding-top: 4px;">案件编号:<%=caseNum %></span>
	<span style="margin-left: 250px;">当前状态:<span class="title" style="color:red">审理中</span></span>
	<span style="margin-right: 60px;float:right">距立案审查期限届满之日还有 <span class="title" style="color:red"><%=time %></span> 天</span>
</div>
<form  method="post" name="form1" id="form1" >
<input type="hidden" id="caseId" name="caseId" value="<%=caseId %>">
<input type="hidden" id="id" name="id" >
<div class="easyui-panel" title="来信信息" style="width: 100%;margin-bottom:10px" align="center" id="letterDiv">
	<table align="center" width="100%" class="TableBlock_page_page">
				<tr calss="common-tr-border">
					<td nowrap class="case-common-filing-td-class1" >申请方式：</td>
					<td class="case-common-filing-td-class2">
						<input value="" id="applicationTypeLetter" name="applicationTypeLetter" class="BigInput" title="" disabled/>
					</td>
					<td nowrap class="case-common-filing-td-class1">来信人姓名：</td>
					<td class="case-common-filing-td-class2">
						<input value="" id="senderName" name="senderName" class="BigInput" title="" disabled/>
					</td>
					<td nowrap class="case-common-filing-td-class1">来信人电话：</td>
					<td class="case-common-filing-td-class2">
						<input value="" id="senderPhone" name="senderPhone" class="BigInput" title="" disabled/>
					</td>
				</tr>
				<tr calss="common-tr-border">
					<td nowrap class="case-common-filing-td-class1">收件日期：</td>
					<td class="case-common-filing-td-class2">
						<input value="" id="receiveDate" name="receiveDate" class="BigInput" title="" disabled/>
					</td>
					<td nowrap class="case-common-filing-td-class1">收件类型：</td>
					<td nowrap class="case-common-filing-td-class2">
						<input value="" id="" name="" class="BigInput" title="" disabled/>
					</td>
					<td nowrap class="case-common-filing-td-class1">来文编号：</td>
					<td class="case-common-filing-td-class2">
						<input value="" id="letterNum" name="letterNum" class="BigInput" title="" disabled/>
					</td>
				</tr>
				<tr calss="common-tr-border">
					<td nowrap class="case-common-filing-td-class1" >来信人通信地址：</td>
					<td class="case-common-filing-td-class2" colspan="3">
						<textarea class="textstyle" style="height:40px;width:90%;line-height:1.5" data-options="multiline:true" name="senderAddress" id="senderAddress" disabled></textarea>
					</td>
					<!-- 处理结果从关联表中获取 -->
					<td nowrap class="case-common-filing-td-class1">来信人邮编：</td>
					<td class="case-common-filing-td-class2">
						<input value="" id="senderPostCode" name="senderPostCode" class="BigInput" title="" disabled/>
					</td>
					
				</tr>
			</table>
</div>
<div class="easyui-panel" title="接待信息" style="width: 100%;margin-bottom:10px" align="center" id="appliDiv">
	<table align="center" width="100%" class="TableBlock_page">
				<tr calss="common-tr-border">
					<td nowrap class="case-common-filing-td-class1" >申请方式：</td>
					<td class="case-common-filing-td-class2">
						<input value="" id="applicationType" name="applicationType" class="BigInput" title="" disabled/>
					</td>
					<td nowrap class="case-common-filing-td-class1">接待日期：</td>
					<td class="case-common-filing-td-class2">
						<input value="" id="receptionDate" name="receptionDate" class="BigInput" title="" disabled/>
					</td>
					<td nowrap class="case-common-filing-td-class1">接待人姓名：</td>
					<td class="case-common-filing-td-class2">
						<input value="" id="dealMan1Name" name="dealMan1Name" class="BigInput" title="" disabled/>
					</td>
				</tr>
				<tr calss="common-tr-border">
					<td nowrap class="case-common-filing-td-class1">第二接待人：</td>
					<td class="case-common-filing-td-class2">
						<input value="" id="dealMan2Name" name="dealMan2Name" class="BigInput" title="" disabled/>
					</td>
					<td nowrap class="case-common-filing-td-class1">接待地点：</td>
					<td colspan="4" nowrap class="case-common-filing-td-class2">
						<textarea class="textstyle" style="height:40px;width:100%;line-height:1.5" data-options="multiline:true" name="place" id="place" disabled></textarea>
					</td>
					
				</tr>
				<tr calss="common-tr-border">
					<td nowrap class="case-common-filing-td-class1" >复议请求：</td>
					<td class="case-common-filing-td-class2">
						<input value="" id="recertionType" name="recertionType" class="BigInput" title="" disabled/>
					</td>
					<!-- 处理结果从关联表中获取 -->
					<td nowrap class="case-common-filing-td-class1">处理结果：</td>
					<td class="case-common-filing-td-class2">
						<input value="" id="dealResult" name="dealResult" class="BigInput" title="" disabled/>
					</td>
					<td nowrap class="case-common-filing-td-class1">是否接收材料：</td>
					<td class="case-common-filing-td-class2">
						<input disabled type="radio" value="1" name="isReceiveMaterial">是
						<input disabled type="radio" value="0" name="isReceiveMaterial">否
					</td>
				</tr>
				<tr calss="common-tr-border">
					<td nowrap class="case-common-filing-td-class1">被接待人信息：</td>
					<td colspan="7" class="case-common-filing-td-class2">
						<textarea class="textstyle" style="height:50px;width:100%;line-height:1.5" data-options="multiline:true" name="visitorVo" id="visitorVo" disabled></textarea>
					</td>
				</tr>
				<tr calss="common-tr-border">
					<td nowrap class="case-common-filing-td-class1">接待情况：</td>
					<td colspan="7" class="case-common-filing-td-class2">
						<!-- 从关联表中获取 -->
						<textarea class="textstyle" style="height:50px;width:100%;line-height:1.5" data-options="multiline:true" name="receptionDetail" id="receptionDetail" disabled></textarea>
					</td>
				</tr>
				<tr calss="common-tr-border">
					<td nowrap class="case-common-filing-td-class1">材料信息：</td>
					<td colspan="7" class="case-common-filing-td-class2">
						<textarea class="textstyle" style="height:50px;width:100%;line-height:1.5" data-options="multiline:true" name="reconsiderMaterial" id="reconsiderMaterial" disabled></textarea>
					</td>
				</tr>
			</table>
</div>
<div class="easyui-panel" title="当事人信息" style="width: 100%;margin-bottom:10px" align="center" id="baseDiv">
	<table align="center" width="100%" class="TableBlock_page">
				<tr calss="common-tr-border">
					<td nowrap class="case-common-filing-td-class1" >申请人类型：</td>
					<td class="case-common-filing-td-class1">
						<!-- 关联表字段 -->
						<input value="" id="applicantType" name="applicantType" class="BigInput" title="" disabled/>
					</td>
					<td nowrap class="case-common-filing-td-class1">是否有代理人：</td>
					<td class="case-common-filing-td-class2">
						<!-- 关联表字段 -->
						<input type="radio" name="isAgent" value="1" disabled/>是
						<input type="radio" name="isAgent" value="0" disabled/>否
					</td>
					<td nowrap class="case-common-filing-td-class1">是否有第三人：</td>
					<td class="case-common-filing-td-class2">
						<!-- 关联表字段 -->
						<input type="radio" value="1" name="isThirdParty" disabled>是
						<input type="radio" value="0" name="isThirdParty" disabled>否
					</td>
				</tr>
				<tr calss="common-tr-border">
					<td nowrap class="case-common-filing-td-class1">是否有第三人代理人：</td>
					<td class="case-common-filing-td-class2">
						<input type="radio" value="1" name="isThirdPartyAgent" disabled>是
						<input type="radio" value="0" name="isThirdPartyAgent" disabled>否
					</td>
					<td nowrap class="case-common-filing-td-class1">申请总人数：</td>
					<td class="case-common-filing-td-class2">
						<input value="" id="sumApplicant" name="sumApplicant" class="BigInput" title="" disabled/>
					</td>
				</tr>
				<tr calss="common-tr-border">
					<td nowrap class="case-common-filing-td-class1">申请人信息：</td>
					<td colspan="7" class="case-common-filing-td-class2">
						<textarea class="textstyle" style="height:80px;width:100%;line-height:1.5" data-options="multiline:true" name="applicant" id="applicant" disabled></textarea>
					</td>
				</tr>
				<tr calss="common-tr-border">
					<td nowrap class="case-common-filing-td-class1">其他申请人信息：</td>
					<td colspan="7" class="case-common-filing-td-class2">
						<textarea class="textstyle" style="height:50px;width:100%;line-height:1.5" data-options="multiline:true" name="otherApplicant" id="otherApplicant" disabled></textarea>
					</td>
				</tr>
				<tr calss="common-tr-border">
					<td nowrap class="case-common-filing-td-class1">申请人代理人信息：</td>
					<td colspan="7" class="case-common-filing-td-class2">
						<textarea class="textstyle" style="height:50px;width:100%;line-height:1.5" data-options="multiline:true" name="applicantAgent" id="applicantAgent" disabled></textarea>
					</td>
				</tr>
				<tr calss="common-tr-border">
					<td nowrap class="case-common-filing-td-class1">被申请人信息：</td>
					<td colspan="7" class="case-common-filing-td-class2">
						<textarea class="textstyle" style="height:50px;width:100%;line-height:1.5" data-options="multiline:true" name="respondent" id="respondent" disabled></textarea>
					</td>
				</tr>
				<tr calss="common-tr-border">
					<td nowrap class="case-common-filing-td-class1">第三人信息：</td>
					<td colspan="7" class="case-common-filing-td-class2">
						<textarea class="textstyle" style="height:50px;width:100%;line-height:1.5" data-options="multiline:true" name="thirdParty" id="thirdParty" disabled></textarea>
					</td>
				</tr>
			</table>
</div>
<div class="easyui-panel" title="复议事项" style="width: 100%;" align="center" id="baseDiv">
	<table align="center" width="100%" class="TableBlock_page">
		<tr calss="common-tr-border">
			<td nowrap class="case-common-filing-td-class1" >申请日期：</td>
			<td class="case-common-filing-td-class2">
				<input value="" id="applicationDate" name="applicationDate" class="BigInput" title="" disabled/>
			</td>
			<td nowrap class="case-common-filing-td-class1">行政管理类别：</td>
			<td class="case-common-filing-td-class2">
				<input value="" id="category" name="category" class="BigInput"  title="" disabled/>
			</td>
			<td nowrap class="case-common-filing-td-class1" style="width:150px">申请事项：</td>
			<td class="case-common-filing-td-class2">
				<input value="" id="applicationItem" name="applicationItem" class="BigInput" style="width:135px" title="" disabled/>
			</td>
		</tr>
		<tr calss="common-tr-border">
			<td nowrap class="case-common-filing-td-class1">行政不作为：</td>
			<td class="case-common-filing-td-class2">
				<input type="radio" name="isNonfeasance" value="1" disabled/>是
				<input type="radio" name="isNonfeasance" value="0" disabled/>否
			</td>
			<td nowrap class="case-common-filing-td-class1" colspan="4"></td>
		</tr>
		<tr name="noregard" style="display:none">
			<td nowrap class="case-common-filing-td-class1">具体行政行为名称：</td>
			<td class="case-common-filing-td-class2">
				<input value="" id="specificAdministrativeName" name="specificAdministrativeName" class="BigInput"  title="" disabled/>
			</td>
			<td nowrap class="case-common-filing-td-class1" >具体行政行为文号：</td>
			<td class="case-common-filing-td-class2">
				<input value="" id="specificAdministrativeNo" name="specificAdministrativeNo" class="BigInput" title="" disabled/>
			</td>
			<td nowrap class="case-common-filing-td-class1" style="width:160px">具体行政行为做出日期：</td>
			<td class="case-common-filing-td-class2">
				<input value="" id="specificAdministrativeDate" name="specificAdministrativeDate" class="BigInput" style="width:135px" title="" disabled/>
			</td>
		</tr>
		<tr name="noregard" style="display:none">
			<td nowrap class="case-common-filing-td-class1">得知该具体行为途径：</td>
			<td class="case-common-filing-td-class2">
				<input value="" id="receivedSpecificWay" name="receivedSpecificWay" class="BigInput" title="" disabled/>
			</td>
			<td colspan="2" class="case-common-filing-td-class1"></td>
		</tr>
		<tr name="yesregard" style="display:none">
			<td nowrap class="case-common-filing-td-class1">不作为事项：</td>
			<td class="case-common-filing-td-class2">
				<input value="" id="" name="" class="BigInput" title="" disabled/>
			</td>
			<td nowrap colspan="2" class="case-common-filing-td-class1">申请人要求被申请人履行该项法定职责日期：</td>
			<td class="case-common-filing-td-class2">
				<input value="" id="" name="" class="BigInput" style="width:150px" title="" disabled/>
			</td>
		</tr>
		<tr calss="common-tr-border">
			<td nowrap class="case-common-filing-td-class1">具体行政行为：</td>
			<td colspan="5" class="case-common-filing-td-class2">
				<!-- 从关联表中获取 -->
				<textarea class="textstyle" style="height:40px;width:100%;line-height:1.5" data-options="multiline:true" name="specificAdministrativeDetail" id="specificAdministrativeDetail" disabled></textarea>
			</td>
		</tr>
		<tr calss="common-tr-border">
			<td nowrap class="case-common-filing-td-class1">行政复议请求：</td>
			<td colspan="5" class="case-common-filing-td-class2">
				<!-- 从关联表中获取 -->
				<textarea class="textstyle" style="height:40px;width:100%;line-height:1.5" data-options="multiline:true" name="requestForReconsideration" id="requestForReconsideration" disabled></textarea>
			</td>
		</tr>
		<tr calss="common-tr-border">
			<td nowrap class="case-common-filing-td-class1" >行政复议前置：</td>
			<td class="case-common-filing-td-class2">
				<input type="radio" name="isReview" value="1" disabled/>是
				<input type="radio" name="isReview" value="0" disabled/>否
			</td>
			
			<td nowrap class="case-common-filing-td-class1">申请举行听证会：</td>
			<td class="case-common-filing-td-class2">
				<input type="radio" name="isHoldHearing" value="1" disabled/>是
				<input type="radio" name="isHoldHearing" value="0" disabled/>否
			</td>
			<td nowrap class="case-common-filing-td-class1" >申请对规范性文件审查：</td>
			<td class="case-common-filing-td-class2">
				<input type="radio" name="isDocumentReview" value="1" disabled/>是
				<input type="radio" name="isDocumentReview" value="0" disabled/>否
			</td>
		</tr>
		<!-- 赔偿 -->
		<tr calss="common-tr-border">
			<td nowrap class="case-common-filing-td-class1">申请赔偿：</td>
			<td class="case-common-filing-td-class2">
				<input type="radio" name="isCompensation" value="1" disabled/>是
				<input type="radio" name="isCompensation" value="0" disabled/>否
			</td>
			
			<td nowrap class="case-common-filing-td-class1" name="compensate" style="display:none">赔偿请求类型：</td>
			<td class="case-common-filing-td-class2" name="compensate" style="display:none">
				<input value="" id="compensationType" name="compensationType" class="BigInput" title="" disabled/>
			</td>
			<td nowrap class="case-common-filing-td-class1" name="compensate" style="display:none">赔偿请求金额：</td>
			<td class="case-common-filing-td-class2" name="compensate" style="display:none">
				<input value="" id="compensationAmount" name="compensationAmount" class="BigInput" style="width:130px" title="" disabled/>
			</td>
			<td class="case-common-filing-td-class1" colspan="4" id="nocompensate" style="display:none"></td>
		</tr>
		<tr calss="common-tr-border">
			<td nowrap class="case-common-filing-td-class1">规范性文件名称：</td>
			<td class="case-common-filing-td-class2">
				<input value="" id="documentReviewName" name="documentReviewName" class="BigInput" title="" disabled/>
			</td>
			<td colspan="4" nowrap class="case-common-filing-td-class1"></td>
		</tr>
</table>
</div>
</form>

<script type="text/javascript" src="<%=contextPath%>/xzfy/js/jquery.tips.js"></script>
<script type="text/javascript">
var title = "";
var caseId = "<%= caseId%>";
$(function(){
	caseId="10be0f867b10407e90009295889ac860";
	var json = tools.requestJsonRs("/xzfy/caseInfo/getCaseInfo.action", {caseId: caseId,type:0});
	if(json.rtState){
		//判断行政不作为是或否,控制隐藏和显示部分信息项
		var isNonfeasance = json.rtData.caseHandling.isNonfeasance;
		if(isNonfeasance==0){
			var isNonfeasanceArr = document.getElementsByName("noregard");
			for(var i = 0;i<isNonfeasanceArr.length;i++){
				isNonfeasanceArr[i].style="block";
			}
			$("input[name='isNonfeasance'][value=0]").attr("checked",true); 
		}
		if(isNonfeasance==1){
			var isNonfeasanceArr = document.getElementsByName("yesregard");
			for(var i = 0;i<isNonfeasanceArr.length;i++){
				isNonfeasanceArr[i].style="block";
			}
			$("input[name='isNonfeasance'][value=1]").attr("checked",true); 
		}
		//来件信息
		var letter = json.rtData.letter;
		if(letter!=null){
			var senderName = letter.senderName;//来信人姓名
			json.rtData.senderName = senderName;
			var letterNum = letter.letterNum;//来文编号
			json.rtData.letterNum = letterNum;
			var senderPhone = letter.senderPhone;//来信人电话
			json.rtData.senderPhone = senderPhone;
			var receiveDate = letter.receiveDate;//收件日期
			json.rtData.receiveDate = receiveDate;
			var letterType = letter.letterType;//收件类型
			json.rtData.letterType = letterType;
			var letterNum = letter.letterNum;//来文编号
			json.rtData.letterNum = letterNum;
			var senderAddress = letter.senderAddress;//来信人通信地址
			json.rtData.senderAddress = senderAddress;
			var senderPostCode = letter.senderPostCode;//来信人邮编
			json.rtData.senderPostCode = senderPostCode;
		}
		
		//当事人信息  开始给rtData下对象中的控件属性复制
		var clientInfo = json.rtData.clientInfo;//当事人数组
		if(clientInfo!=null){
			var applicantType = clientInfo.applicantType//申请人类型
			json.rtData.applicantType = applicantType;
			var isAgent = clientInfo.isAgent;//申请人是否有代理人
			json.rtData.isAgent = isAgent;
			var isThirdParty =clientInfo.isThirdParty;//是否有第三人
			json.rtData.isThirdParty = isThirdParty;
			var isThirdPartyAgent = clientInfo.isThirdPartyAgent//是否有第三人代理人
			json.rtData.isThirdPartyAgent = isThirdPartyAgent;
			
			var applicant = clientInfo.applicant;//申请人数组
			var sqrtr = "";
			for(var i = 0;i<applicant.length;i++){
				sqrtr+="姓名:"+applicant[i].name+",证件类型:"+applicant[i].certificateType+",证件号:"+applicant[i].certificateId+",性别:"+applicant[i].sex+",民族:"+applicant[i].nation+",送达地址:"+applicant[i].postAddress+",邮编:"+applicant[i].postCode+",联系电话："+applicant[i].phoneNum+"\n";
			}
			$("#applicant").text(sqrtr);
			var applicantAgent = clientInfo.applicantAgent;//申请人代理人数组
			if(applicantAgent!=null){
				var dlrtr = "";
				for(var i = 0;i<applicantAgent.length;i++){
					applicantAgent[i].isAuthorization = applicantAgent[i].isAuthorization==0?"否":"是";
					dlrtr+="代理人类型:"+applicantAgent[i].agentType+",代理人姓名:"+applicantAgent[i].agentName+",证件类型:"+applicantAgent[i].cardType+",证件号码:"+applicantAgent[i].idCard+",联系电话:"+applicantAgent[i].phone+",是否有授权书:"+applicantAgent[i].isAuthorization+"\n";
				}
				$("#applicantAgent").text(dlrtr);
			}
	         
			var respondent = clientInfo.respondent;//被申请人数组
			if(respondent!=null){
				var bsqrtr = "";
				for(var i = 0;i<respondent.length;i++){
					bsqrtr+="被申请人种类:"+respondent[i].respondentType+",被申请人名称:"+respondent[i].respondentName+"\n";
				}
				$("#respondent").text(bsqrtr);
			}
			
			var thirdParty = clientInfo.thirdParty;//第三人数组
			if(thirdParty!=null){
				var dsrtr = "";
				for(var i = 0;i<thirdParty.length;i++){
					dsrtr+=thirdParty[i].thirdPartyName+";"+thirdParty[i].certificateType+";"+thirdParty[i].certificateId+";"+thirdParty[i].sex+";"+thirdParty[i].nation+";"+thirdParty[i].postAddress+";"+thirdParty[i].postcode+";"+thirdParty[i].thirdPartyPhonenum+"\n";
				}
				$("#thirdParty").text(dsrtr);
			}
			
	        var otherApplicant = clientInfo.otherApplicant;//其它申请人数组
	        if(otherApplicant!=null){
	        	var qtsqr = "";
	        	for(var i = 0;i<otherApplicant.length;i++){
	        		qtsqr+="姓名:"+otherApplicant[i].name+",性别:"+otherApplicant[i].sex+",证件类型:"+otherApplicant[i].certificateType+",证件号码:"+otherApplicant[i].certificateId+"\n";
	        	}
	        	$("#otherApplicant").text(qtsqr);
	        }
			
			var sumApplicant = applicant.length+otherApplicant.length;//申请总人数:申请人数组长度+其他申请人数组长度
			json.rtData.sumApplicant = sumApplicant;
			
		}
		
		//接待信息
		var reception = clientInfo.reception;//接待信息
		if(reception!=null){
			var receptionDate = reception.receptionDate;//接待日期
			json.rtData.receptionDate = receptionDate;
			var dealMan1Id = reception.dealMan1Id;//接待人姓名
			json.rtData.dealMan1Id = dealMan1Id;
			var dealMan2Id = reception.dealMan2Id;//第二接待人
			json.rtData.dealMan2Id = dealMan2Id;
			var place = reception.place;//接待地点
			json.rtData.place = place;
			var isReceiveMaterial = reception.isReceiveMaterial//是否接收材料 0 1
			json.rtData.isReceiveMaterial = isReceiveMaterial;
			var recertionTypeCode = reception.recertionTypeCode;//接待类型代码(或者复议请求代码)
			var recertionType = reception.recertionType;
			json.rtData.recertionType = recertionType;
			var  dealResult  = reception.dealResult;//处理结果CODE
			json.rtData.dealResult = dealResult;
			var receptionDetail = reception.receptionDetail;//接待情况
			json.rtData.receptionDetail = receptionDetail;
			//被接待人信息数组
			var visitorVo = reception.visitorVo;
			if(visitorVo!=null){
				var bjdrtr = "";
				for(var i = 0;i<visitorVo.length;i++){
					bjdrtr+="姓名:"+visitorVo[i].visitorName+",性别："+visitorVo[i].sex+",证件类型："+visitorVo[i].cardType+",证件号码："+visitorVo[i].cardNum+",联系电话："+visitorVo[i].phoneNum+"\n";
				}
				$("#visitorVo").text(bjdrtr);
			}
			//材料信息数组(原件/几页/...)
			var reconsiderMaterial = reception.reconsiderMaterial;
			if(reconsiderMaterial!=null){
				var clxx = "";
				for(var i = 0;i<reconsiderMaterial.length;i++){
					clxx+="材料名称:"+reconsiderMaterial[i].fileName+",材料类型:"+reconsiderMaterial[i].fileType+",份数:"+reconsiderMaterial[i].copyNum+",张数:"+reconsiderMaterial[i].pageNum+"\n";
				}
				$("#reconsiderMaterial").text(clxx);
			}
			
		}
		//案件信息
		var caseHandling = json.rtData.caseHandling;
		if(caseHandling!=null){
			var	requestForReconsideration = caseHandling.requestForReconsideration//行政复议请求
			json.rtData.requestForReconsideration = requestForReconsideration;
			var applicationDate = caseHandling.applicationDate;//申请日期
			json.rtData.applicationDate = applicationDate;
			var category = caseHandling.category;//行政管理类别
			json.rtData.category = category;
			var applicationItem = caseHandling.applicationItem //申请事项
			json.rtData.applicationItem = applicationItem;
			var specificAdministrativeName = caseHandling.specificAdministrativeName//具体行政行为名称
			json.rtData.specificAdministrativeName = specificAdministrativeName;
			var specificAdministrativeNo = caseHandling.specificAdministrativeNo//具体行政行为文号
			json.rtData.specificAdministrativeNo = specificAdministrativeNo;
			var specificAdministrativeDate = caseHandling.specificAdministrativeDate//具体行政行为做出日期
			json.rtData.specificAdministrativeDate = specificAdministrativeDate;
			var receivedSpecificWay = caseHandling.receivedSpecificWay; //得知该具体行为途径
			json.rtData.receivedSpecificWay = receivedSpecificWay;
			var inactionMatters	= caseHandling.inactionMatters;//不作为事项
			json.rtData.inactionMatters = inactionMatters;
			var performancedate = caseHandling.performancedate;//申请人要求被申请人履行该项法定职责日期
			json.rtData.performancedate = performancedate;
			var specificAdministrativeDetail = caseHandling.specificAdministrativeDetail;//具体行政行为
			json.rtData.specificAdministrativeDetail = specificAdministrativeDetail;
			var isReview = caseHandling.isReview;//行政复议前置
			json.rtData.isReview = isReview;
			var isHoldHearing = caseHandling.isHoldHearing;//申请举行听证会
			json.rtData.isHoldHearing = isHoldHearing;
			var isCompensation = caseHandling.isCompensation;//申请赔偿
			json.rtData.isCompensation = isCompensation;
			if(isCompensation==1){
				var isCompensationArr = document.getElementsByName("compensate");
				for(var i = 0;i<isCompensationArr.length;i++){
					isCompensationArr[i].style="block";
				}
				$("input[type='radio'][name='isCompensation']").get(0).checked = true;
			}
			if(isCompensation==0){
				$("input[type='radio'][name='isCompensation']").get(1).checked = true;
				//让表格占位的td显示
			}
			var isDocumentReview = caseHandling.isDocumentReview;//申请对规范性文件审查
			json.rtData.isDocumentReview = isDocumentReview;
			var compensationType = caseHandling.compensationType;//赔偿请求类型
			json.rtData.compensationType = compensationType;
			var compensationMoney = caseHandling.compensationMoney;//赔偿请求金额
			json.rtData.compensationMoney = compensationMoney;
			var documentReviewName = caseHandling.documentReviewName;//规范性文件名称
			json.rtData.documentReviewName = documentReviewName;
		}
		
		//来信登记或者接待登记隐藏一个DIV
		if(json.rtData.applicationTypeCode="01"){//来信登记
			//document.getElementById("appliDiv").style.display="none";
			$("#applicationTypeLetter").val(json.rtData.applicationType);
		}else{//当面登记
			//document.getElementById("letterDiv").style.display="none";
		}
		//后台返回对象后绑定到form表单
		bindJsonObj2Cntrl(json.rtData);
	}else{
		$.jBox.tip("请求失败,请联系管理员！", 'info' , {timeout:1500});
	}
});

</script>
</body>
</html>
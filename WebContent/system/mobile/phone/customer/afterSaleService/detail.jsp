<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%
	String sid = request.getParameter("sid");
	String returnFlag = request.getParameter("returnFlag");//1-返回共享页面；其他值-返回我的
%>
<!DOCTYPE HTML>
<html>
<head>
<title>售后详情</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
</head>
<body>
<header id="header" class="mui-bar mui-bar-nav">
	<button class="mui-btn mui-btn-link mui-btn-nav mui-pull-left" onclick="returnBack();">
	    <span class="mui-icon mui-icon-left-nav"></span>返回
	</button>
	<button class="mui-btn mui-btn-link mui-btn-nav mui-pull-right" >
	    
	</button>
	<h1 class="mui-title">售后详情</h1>
</header>
<style>
.app-row{
	margin-left:14px;
	padding-top:10px;
	padding-right:10px;
	padding-bottom:10px;
	border-bottom:1px solid #f0f0f0;
}
.app-row br{
	margin:0px;
}
.app-row-content{
	margin-left:14px;
	padding-top:10px;
	padding-right:10px;
	padding-bottom:10px;
	border-bottom:1px solid #f0f0f0;
	color:gray;
}
.app-row-right{
	padding:8px;
}
</style>
<div id="muiContent" class="mui-content">
	<!-- 售后服务 -->
	<div class="mui-input-group">
		<div class="mui-input-row" style="background: #E0EBF9;font-weight: bold;font-size:14px;" onclick="showDivFunc('div1','span1');">
			<label style="width: 100%;" >
				<span id="span1">-</span>售后服务
			</label>
		</div>
		<div id="div1" style="" >
			<div class="mui-input-row">
				<label>服务编号： </label>
				<p class="app-row-right mui-ellipsis" id="serviceCode"></p>
			</div>
			<div class="mui-input-row">
				<label>客户名称： </label>
				<p class="app-row-right mui-ellipsis" id="customerInfoName"></p>
			</div>
			<div class="mui-input-row">
				<label>客户联系人： </label>
				<p class="app-row-right mui-ellipsis" id="contactUserName" ></p>
			</div>
			<div class="mui-input-row">
				<label>售后服务类型： </label>
				<p class="app-row-right mui-ellipsis" id="serviceTypeName"></p>
			</div>
			<div class="mui-input-row">
				<label>紧急程度： </label>
				<p class="app-row-right mui-ellipsis" id="emergencyDegree"></p>
			</div>
			<div class="mui-input-row">
				<label>受理人： </label>
				<p class="app-row-right mui-ellipsis" id="accteptUserName"></p>
			</div>
			<div class="mui-input-row">
				<label>受理时间： </label>
				<p class="app-row-right mui-ellipsis" id="acceptDatetimeStr"></p>
			</div>
			
			<div >
				<ul class='mui-table-view'>
					<li class="mui-table-view-cell mui-media" onclick=" " >
						<div class="mui-media-body" >
							附件 
							<p class=''>
								<span id="attachments"></span>
							</p>
						</div>
					</li>
				</ul>
			</div>
			
			<div class="mui-input-row">
				<label>问题描述： </label>
				<p class="app-row-right mui-ellipsis" id="questionDesc"></p>
			</div>
			<div class="mui-input-row">
				<label>创建时间： </label>
				<p class="app-row-right mui-ellipsis" id="createTimeStr"></p>
			</div>
			
		</div>
	</div>
	
	<!-- 处理信息 -->
	<div class="mui-input-group">
		<div class="mui-input-row" style="background: #E0EBF9;font-weight: bold;font-size:14px;" onclick="showDivFunc('div2','span2');">
			<label style="width: 100%;">
				<span id="span2">+</span>处理信息
			</label>
		</div>
		<div id="div2" style="display:none;" >
			<div class="mui-input-row">
				<label>处理人： </label>
				<p class="app-row-right mui-ellipsis" id="handleUserName"></p>
			</div>
			<div class="mui-input-row">
				<label>处理时间： </label>
				<p class="app-row-right mui-ellipsis" id="handleDatetimeStr"></p>
			</div>
			<div class="mui-input-row">
				<label>是否完成： </label>
				<p class="app-row-right mui-ellipsis" id="handleStatus"></p>
			</div>
			<div class="mui-input-row">
				<label>处理结果： </label>
				<p class="app-row-right mui-ellipsis" id="handleDesc"></p>
			</div>
			<div class="mui-input-row">
				<label>反馈结果： </label>
				<p class="app-row-right mui-ellipsis" id="feedback"></p>
			</div>
		</div>
	</div>
	
</div>
<script>
$(function(){
	getInfoById('<%=sid%>');
	
});

/**
 * 显示、隐藏div
 */
function showDivFunc(objId,spanObj){
	$("#" +objId).toggle();
	if($("#" + objId).css('display')=='none'){
		$("#" + spanObj).text("+");
	}else{
		$("#" + spanObj).text("-");
	}
}


/**
 * 获取信息
 */
function getInfoById(sid){
	var url =contextPath+"/crmAfterSaleServController/getInfoById.action";
	mui.ajax(url,{
		type:"POST",
		dataType:"JSON",
		data:{sid:sid},
		timeout:10000,
		success:function(text){
			var json = eval("("+text+")");
			if(json.rtState){
				var prc = json.rtData;
				//基本信息
				$("#serviceCode").text(prc.serviceCode);
				$("#customerInfoName").text(prc.customerInfoName);
				$("#contactUserName").text(prc.contactUserName);
				$("#serviceTypeName").text(prc.serviceTypeName);
				$("#emergencyDegree").text(prc.emergencyDegree);
				$("#accteptUserName").text(prc.accteptUserName);
				$("#acceptDatetimeStr").text(prc.acceptDatetimeStr);
				$("#questionDesc").text(prc.questionDesc);
				$("#createTimeStr").text(prc.createTimeStr);
				
				
				//处理信息
				$("#handleUserName").text(prc.handleUserName);
				$("#handleDatetimeStr").text(prc.handleDatetimeStr);
				$("#handleStatus").text(handleStatusFunc(prc.handleStatus));
				$("#handleDesc").text(prc.handleDesc);
				$("#feedback").text(prc.feedback);
				
				var attachs = prc.attacheModels;
				for(var i = 0 ;i<attachs.length;i++){
					var attach = attachs[i];
					var att = "<div style='height:32px;line-height:32px;'><a href='javascript:void(0);' onclick=\"GetFile('"+attach.sid+"','"+attach.fileName+"','"+attach.attachmentName+"')\">"+attach.fileName + "</a></div>";
					$("#attachments").append(att);
				}
				
			}
		},
		error:function(){
			
		}
	});
}
/**
 * 完成情况
 * @param value
 * @returns {String}
 */
function handleStatusFunc(value){
	if(value =="0"){
		value = "未完成";
	}else{
		value = "已完成";
	}
	return value;
}
function returnBack(){
	var returnFlag = '<%=returnFlag%>';
	var url = contextPath + "/system/mobile/phone/customer/afterSaleService/myAfterSaleService.jsp";
	if(returnFlag =='1'){
		url = contextPath + "/system/mobile/phone/customer/afterSaleService/shareAfterSaleService.jsp";
	}
	location.href = url;
}





</script>
</body>
</html>
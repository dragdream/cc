<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%
	String sid = request.getParameter("sid");
	String returnFlag = request.getParameter("returnFlag");//1-返回联系人共享页面；2-返回我的客户；3-返回共享客户；   其他值-返回我的联系人
%>
<!DOCTYPE HTML>
<html>
<head>
<title>联系人详情</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
</head>
<body>
<header id="header" class="mui-bar mui-bar-nav">
	<button class="mui-btn mui-btn-link mui-btn-nav mui-pull-left" onclick="returnBack();">
	    <span class="mui-icon mui-icon-left-nav"></span>返回
	</button>
	<button class="mui-btn mui-btn-link mui-btn-nav mui-pull-right" >
	    
	</button>
	<h1 class="mui-title">联系人详情</h1>
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
	<!-- 基本信息 -->
	<div class="mui-input-group">
		<div class="mui-input-row" style="background: #E0EBF9;font-weight: bold;font-size:14px;" onclick="showDivFunc('div1','span1');">
			<label>
				<span id="span1">-</span>基本信息
			</label>
		</div>
		<div id="div1" style="" >
			<div class="mui-input-row">
				<label>所属客户： </label>
				<p class="app-row-right mui-ellipsis" id="customerName"></p>
			</div>
			<div class="mui-input-row">
				<label>姓名： </label>
				<p class="app-row-right mui-ellipsis" id="name"></p>
			</div>
			<div class="mui-input-row">
				<label>性别： </label>
				<p class="app-row-right mui-ellipsis" id="genderDesc" ></p>
			</div>
			<div class="mui-input-row">
				<label>所属部门： </label>
				<p class="app-row-right mui-ellipsis" id="department"></p>
			</div>
			<div class="mui-input-row">
				<label>重要程度： </label>
				<p class="app-row-right mui-ellipsis" id="importantDesc"></p>
			</div>
			<div class="mui-input-row">
				<label> 出生日期 ： </label>
				<p class="app-row-right mui-ellipsis" id="birthdayDesc"></p>
			</div>
			<div class="mui-input-row">
				<label>状态： </label>
				<p class="app-row-right mui-ellipsis" id="posDesc"></p>
			</div>
			<div class="mui-input-row">
				<label> 简介 ： </label>
				<p class="app-row-right mui-ellipsis" id="brief"></p>
			</div>
		</div>
	</div>
	
	<!-- 联系方式 -->
	<div class="mui-input-group">
		<div class="mui-input-row" style="background: #E0EBF9;font-weight: bold;font-size:14px;" onclick="showDivFunc('div2','span2');">
			<label>
				<span id="span2">+</span>联系方式
			</label>
		</div>
		<div id="div2" style="display:none;" >
			<div class="mui-input-row">
				<label>公司电话： </label>
				<p class="app-row-right mui-ellipsis" id="telephone"></p>
			</div>
			<div class="mui-input-row">
				<label> 分机电话 ： </label>
				<p class="app-row-right mui-ellipsis" id="telephone1"></p>
			</div>
			<div class="mui-input-row">
				<label>移动电话 ： </label>
				<p class="app-row-right mui-ellipsis" id="mobilePhone"></p>
			</div>
			<div class="mui-input-row">
				<label>传真： </label>
				<p class="app-row-right mui-ellipsis" id="fax"></p>
			</div>
			<div class="mui-input-row">
				<label> 邮件 ： </label>
				<p class="app-row-right mui-ellipsis" id="email"></p>
			</div>
			<div class="mui-input-row">
				<label>QQ： </label>
				<p class="app-row-right mui-ellipsis" id="qq"></p>
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
 * 获取话题信息
 */
function getInfoById(sid){
	var url =contextPath+"/TeeCrmContactUserController/getById.action";
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
				$("#customerName").text(prc.customerName);
				$("#name").text(prc.name);
				$("#genderDesc").text(prc.genderDesc);
				$("#department").text(prc.department);
				$("#importantDesc").text(prc.importantDesc);
				$("#birthdayDesc").text(prc.birthdayDesc);
				$("#posDesc").text(prc.posDesc);
				$("#brief").text(prc.brief);
				
				//联系方式
				$("#telephone").html(prc.telephone);
				$("#telephone1").text(prc.telephone1);
				$("#mobilePhone").text(prc.mobilePhone);
				$("#fax").text(prc.fax);
				$("#email").text(prc.email);
				$("#qq").text(prc.qq);
				
				
			}
		},
		error:function(){
			
		}
	});
}


function returnBack(){
	var returnFlag = '<%=returnFlag%>';
	var url = contextPath + "/system/mobile/phone/customer/contactUser/myContactUser.jsp";
	if(returnFlag =='1'){
		url = contextPath + "/system/mobile/phone/customer/contactUser/shareContactUser.jsp";
	}else if(returnFlag =='2'){
		url = contextPath + "/system/mobile/phone/customer/customInfo/myCustomer.jsp";
	}else if(returnFlag =='3'){
		url = contextPath + "/system/mobile/phone/customer/customInfo/shareCustomer.jsp";
	}
	location.href = url;
}





</script>
</body>
</html>
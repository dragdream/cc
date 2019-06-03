<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%
	String sid = request.getParameter("sid");
%>
<!DOCTYPE HTML>
<html>
<head>
<title>竞争对手详情</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
</head>
<body>
<header id="header" class="mui-bar mui-bar-nav">
	<button class="mui-btn mui-btn-link mui-btn-nav mui-pull-left" onclick="returnBack();">
	    <span class="mui-icon mui-icon-left-nav"></span>返回
	</button>
	<button class="mui-btn mui-btn-link mui-btn-nav mui-pull-right" >
	    
	</button>
	<h1 class="mui-title">竞争对手详情</h1>
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
			<label style="width: 100%;" >
				<span id="span1">-</span>基本信息
			</label>
		</div>
		<div id="div1" style="" >
			<div class="mui-input-row">
				<label>公司名称： </label>
				<p class="app-row-right mui-ellipsis" id="company"></p>
			</div>
			<div class="mui-input-row">
				<label>注册资本： </label>
				<p class="app-row-right mui-ellipsis" id="registerCapital"></p>
			</div>
			<div class="mui-input-row">
				<label>公司地址： </label>
				<p class="app-row-right mui-ellipsis" id="companyAddress"></p>
			</div>
			<div class="mui-input-row">
				<label>公司邮箱： </label>
				<p class="app-row-right mui-ellipsis" id="email"></p>
			</div>
			<div class="mui-input-row">
				<label>公司网址： </label>
				<p class="app-row-right mui-ellipsis" id="website"></p>
			</div>
			<div class="mui-input-row">
				<label>联系电话： </label>
				<p class="app-row-right mui-ellipsis" id="telephone"></p>
			</div>
		</div>
	</div>
	
	<!-- 详细信息 -->
	<div class="mui-input-group">
		<div class="mui-input-row" style="background: #E0EBF9;font-weight: bold;font-size:14px;" onclick="showDivFunc('div2','span2');">
			<label style="width: 100%;">
				<span id="span2">+</span>详细信息
			</label>
		</div>
		<div id="div2" style="display:none;" >
			<div class="mui-input-row">
				<label>公司性质： </label>
				<p class="app-row-right mui-ellipsis" id="companyNature"></p>
			</div>
			<div class="mui-input-row">
				<label>公司规模： </label>
				<p class="app-row-right mui-ellipsis" id="companyScaleDesc"></p>
			</div>
			<div class="mui-input-row">
				<label>销售额（万元）</label>
				<p class="app-row-right mui-ellipsis" id="companySales"></p>
			</div>
			<div class="mui-input-row">
				<label>主要产品： </label>
				<p class="app-row-right mui-ellipsis" id="mainProduct"></p>
			</div>
			<div class="mui-input-row">
				<label>成立时间： </label>
				<p class="app-row-right mui-ellipsis" id="companyCreateDate"></p>
			</div>
			<div class="mui-input-row">
				<label>所属区域： </label>
				<p class="app-row-right mui-ellipsis" ><span id="provinceName"></span>&nbsp; <span id="cityName"></span>   </p>
			</div>
			<div class="mui-input-row">
				<label>备注： </label>
				<p class="app-row-right mui-ellipsis" id="remark"></p>
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
	var url =contextPath+"/teeCrmCompetitorController/getById.action";
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
				$("#company").text(prc.company);
				$("#registerCapital").text(prc.registerCapital);
				$("#companyAddress").text(prc.companyAddress);
				$("#email").text(prc.email);
				$("#website").text(prc.website);
				$("#telephone").text(prc.telephone);
				
				//详细信息
				$("#companyNature").text(prc.companyNature);
				$("#companyScaleDesc").text(prc.companyScaleDesc);
				$("#companySales").text(prc.companySales);
				$("#mainProduct").text(prc.mainProduct);
				$("#companyCreateDate").text(prc.companyCreateDate);
				$("#provinceName").text(prc.provinceName);
				$("#cityName").text(prc.cityName);
				$("#remark").text(prc.remark);
				
				var attachs = prc.attachmodels;
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
	var url = contextPath + "/system/mobile/phone/customer/competitor/myCompetitor.jsp";
	location.href = url;
}





</script>
</body>
</html>
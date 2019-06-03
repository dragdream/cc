<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%
	String sid = request.getParameter("sid");
	String returnFlag = request.getParameter("returnFlag");//1-返回共享页面；其他值-返回我的
	String falg = "2";
	if("1".equals(returnFlag)){
		falg = "3";
	}
%>
<!DOCTYPE HTML>
<html>
<head>
<title>客户详情</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
</head>
<body>
<header id="header" class="mui-bar mui-bar-nav">
	<button class="mui-btn mui-btn-link mui-btn-nav mui-pull-left" onclick="returnBack();">
	    <span class="mui-icon mui-icon-left-nav"></span>返回
	</button>
	<button class="mui-btn mui-btn-link mui-btn-nav mui-pull-right" >
	    
	</button>
	<h1 class="mui-title">客户详情</h1>
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
				<label>客户名称： </label>
				<p class="app-row-right mui-ellipsis" id="customerName"></p>
			</div>
			<div class="mui-input-row">
				<label>类型： </label>
				<p class="app-row-right mui-ellipsis" id="customerTypeDesc"></p>
			</div>
			<div class="mui-input-row">
				<label>所属区域： </label>
				<p class="app-row-right mui-ellipsis" ><span id="provinceName" ></span>&nbsp;&nbsp;<span id="cityName" ></span></p>
			</div>
			<div class="mui-input-row">
				<label>所属行业： </label>
				<p class="app-row-right mui-ellipsis" id="industryDesc"></p>
			</div>
			<div class="mui-input-row">
				<label>公司规模： </label>
				<p class="app-row-right mui-ellipsis" id="companyScaleDesc"></p>
			</div>
			<div class="mui-input-row">
				<label> 来源 ： </label>
				<p class="app-row-right mui-ellipsis" id="customerSourceDesc"></p>
			</div>
			<div class="mui-input-row">
				<label>客户性质： </label>
				<p class="app-row-right mui-ellipsis" id="type"></p>
			</div>
			<div class="mui-input-row">
				<label> 单位性质 ： </label>
				<p class="app-row-right mui-ellipsis" id="unitTypeDesc"></p>
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
				<label>公司地址： </label>
				<p class="app-row-right mui-ellipsis" id="companyAddress"></p>
			</div>
			<div class="mui-input-row">
				<label>公司网址： </label>
				<p class="app-row-right mui-ellipsis" id="companyUrl"></p>
			</div>
			<div class="mui-input-row">
				<label>公司电话 ： </label>
				<p class="app-row-right mui-ellipsis" id="companyPhone"></p>
			</div>
			<div class="mui-input-row">
				<label>移动电话： </label>
				<p class="app-row-right mui-ellipsis" id="companyMobile"></p>
			</div>
			<div class="mui-input-row">
				<label>传真： </label>
				<p class="app-row-right mui-ellipsis" id="companyFax"></p>
			</div>
			<div class="mui-input-row">
				<label>邮编： </label>
				<p class="app-row-right mui-ellipsis" id="companyZipCode"></p>
			</div>
			<div class="mui-input-row">
				<label> 邮件 ： </label>
				<p class="app-row-right mui-ellipsis" id="companyEmail"></p>
			</div>
			<div class="mui-input-row">
				<label>QQ： </label>
				<p class="app-row-right mui-ellipsis" id="companyQQ"></p>
			</div>
		</div>
	</div>
	
	<!-- 概要 -->
	<div class="mui-input-group">
		<div class="mui-input-row" style="background: #E0EBF9;font-weight: bold;font-size:14px;" onclick="showDivFunc('div3','span3');">
			<label>
				<span id="span3">+</span>概要
			</label>
		</div>
		<div id="div3" style="display:none;" >
			<div class="mui-input-row">
				<label>关系等级： </label>
				<p class="app-row-right mui-ellipsis" id="relationLevelDesc"></p>
			</div>
			<div class="mui-input-row">
				<label>重要程度： </label>
				<p class="app-row-right mui-ellipsis" id="importantLevelDesc"></p>
			</div>
			<div class="mui-input-row">
				<label>投资来源 ： </label>
				<p class="app-row-right mui-ellipsis" id="sourcesOfInvestmentDesc"></p>
			</div>
			<div class="mui-input-row">
				<label>信用等级： </label>
				<p class="app-row-right mui-ellipsis" id="trustLevelDesc"></p>
			</div>
			<div class="mui-input-row">
				<label>销售市场： </label>
				<p class="app-row-right mui-ellipsis" id="salesMarketDesc"></p>
			</div>
		</div>
	</div>
	
	<!-- 开票资料 -->
	<div class="mui-input-group">
		<div class="mui-input-row" style="background: #E0EBF9;font-weight: bold;font-size:14px;" onclick="showDivFunc('div4','span4');">
			<label>
				<span id="span4">+</span>开票资料
			</label>
		</div>
		<div id="div4" style="display:none;" >
			<div class="mui-input-row">
				<label>单位名称： </label>
				<p class="app-row-right mui-ellipsis" id="billUnitName"></p>
			</div>
			<div class="mui-input-row">
				<label>公司地址： </label>
				<p class="app-row-right mui-ellipsis" id="billUnitAddress"></p>
			</div>
			<div class="mui-input-row">
				<label>银行账号： </label>
				<p class="app-row-right mui-ellipsis" id="bankAccount"></p>
			</div>
			<div class="mui-input-row">
				<label>税号： </label>
				<p class="app-row-right mui-ellipsis" id="taxNo"></p>
			</div>
			<div class="mui-input-row">
				<label>开户银行： </label>
				<p class="app-row-right mui-ellipsis" id="bankName"></p>
			</div>
			<div class="mui-input-row">
				<label>开票联系电话： </label>
				<p class="app-row-right mui-ellipsis" id="billPhone"></p>
			</div>
		</div>
	</div>
	
	
	<!-- 共享与设置 -->
	<div class="mui-input-group">
		<div class="mui-input-row" style="background: #E0EBF9;font-weight: bold;font-size:14px;" onclick="showDivFunc('div5','span5');">
			<label>
				<span id="span5">+</span>共享与设置
			</label>
		</div>
		<div id="div5" style="display:none;" >
			<div class="mui-input-row">
				<label>负责人： </label>
				<p class="app-row-right mui-ellipsis" id="managePersonName"></p>
			</div>
			<div class="mui-input-row">
				<label>共享人员： </label>
				<p class="app-row-right mui-ellipsis" id="sharePersonNames"></p>
			</div>
			<div class="mui-input-row">
				<label>备注： </label>
				<p class="app-row-right mui-ellipsis" id="remark"></p>
			</div>
		</div>
	</div>
	
	<!-- 联系人 -->
	<div class="mui-input-group">
		<div class="mui-input-row" style="background: #E0EBF9;font-weight: bold;font-size:14px;" onclick="showDivFunc('div6','span6');">
			<label>
				<span id="span6">+</span>联系人
			</label>
		</div>
		<div id="div6" style="display:none;" >
			<div id="contactUserList"></div>
		</div>
	</div>
	
	<!-- 跟单记录 -->
	<div class="mui-input-group">
		<div class="mui-input-row" style="background: #E0EBF9;font-weight: bold;font-size:14px;" onclick="showDivFunc('div7','span7');">
			<label>
				<span id="span7">+</span>跟单记录
			</label>
		</div>
		<div id="div7" style="display:none;" >
			<div id="saleFollowList"></div>
		</div>
	</div>
	
	
	
</div>
<script>
$(function(){
	getInfoById('<%=sid%>');
	getContactUserList('<%=sid%>');
	getSaleFollowList('<%=sid%>');
	
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
	var url =contextPath+"/TeeCrmCustomerInfoController/getById.action";
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
				if(prc.type == 1){
					$("#type").text("客户");
				}else if(prc.type == 2){
					$("#type").text("供应商");
				}
				$("#customerName").text(prc.customerName);
				$("#customerTypeDesc").text(prc.customerTypeDesc);
				$("#provinceName").text(prc.provinceName);
				$("#cityName").text(prc.cityName);
				$("#industryDesc").text(prc.industryDesc);
				$("#companyScaleDesc").text(prc.companyScaleDesc);
				$("#customerSourceDesc").text(prc.customerSourceDesc);
				$("#unitTypeDesc").text(prc.unitTypeDesc);
				
				//联系方式
				$("#companyAddress").html(prc.companyAddress);
				$("#companyUrl").text(prc.companyUrl);
				$("#companyPhone").text(prc.companyPhone);
				$("#companyMobile").text(prc.companyMobile);
				$("#companyFax").text(prc.companyFax);
				$("#companyZipCode").text(prc.companyZipCode);
				$("#companyEmail").text(prc.companyEmail);
				$("#companyQQ").text(prc.companyQQ);
				
				//概要
				$("#relationLevelDesc").text(prc.relationLevelDesc);
				$("#importantLevelDesc").text(prc.importantLevelDesc);
				$("#sourcesOfInvestmentDesc").text(prc.sourcesOfInvestmentDesc);
				$("#trustLevelDesc").text(prc.trustLevelDesc);
				$("#salesMarketDesc").text(prc.salesMarketDesc);
				
				//开票资料
				$("#billUnitName").text(prc.billUnitName);
				$("#billUnitAddress").text(prc.billUnitAddress);
				$("#bankAccount").text(prc.bankAccount);
				$("#taxNo").text(prc.taxNo);
				$("#bankName").text(prc.bankName);
				$("#billPhone").text(prc.billPhone);
				
				//共享与设置
				$("#managePersonName").text(prc.managePersonName);
				$("#sharePersonNames").text(prc.sharePersonNames);
				$("#remark").text(prc.remark);
				//$("#").text(prc.content);
				
			}
		},
		error:function(){
			
		}
	});
}



/**
 * 获取联系人
 */
 function getContactUserList(sid){
 	var url =contextPath+"/TeeCrmContactUserController/getContactUserList.action";
 	mui.ajax(url,{
 		type:"POST",
 		dataType:"JSON",
 		data:{customerId:sid},
 		timeout:10000,
 		success:function(text){
 			var json = eval("("+text+")");
 			if(json.rtState){
 				var list = json.rtData;
 				if(list.length>0){
 					 jQuery.each(list,function(i,sysPara){
 						var html ="<div class=\"mui-input-row\" onclick=\"window.location.href ='<%=contextPath%>/system/mobile/phone/customer/contactUser/detail.jsp?returnFlag=<%=falg%>&sid="+ sysPara.sid +"' \"   >"
							+"	<label>" + sysPara.name + "</label>"
							+"	<p class='app-row-right mui-ellipsis' >" + sysPara.telephone + "</p>"
							+"</div>";
 						$("#contactUserList").append(html);
 					});
 				}else{
 					$("#contactUserList").append("<div style='margin-left:14px;'>无相关信息！</div>");
 				}
 			}
 		},
 		error:function(){
 			
 		}
 	});
 }
 

/**
 * 获取跟单记录
 */
 function getSaleFollowList(sid){
 	var url =contextPath+"/TeeCrmSaleFollowController/getSaleFollowList.action";
 	mui.ajax(url,{
 		type:"POST",
 		dataType:"JSON",
 		data:{customerId:sid},
 		timeout:10000,
 		success:function(text){
 			var json = eval("("+text+")");
 			if(json.rtState){
 				var list = json.rtData;
 				if(list.length>0){
 					 jQuery.each(list,function(i,sysPara){
 						var html="<div class=\"mui-input-row\" onclick=\"window.location.href ='<%=contextPath%>/system/mobile/phone/customer/saleFollow/detail.jsp?returnFlag=<%=falg%>&sid="+ sysPara.sid +"' \" >"
							+"	<label>" + sysPara.addPersonName + "</label>"
							+"	<p class='app-row-right mui-ellipsis' >状态：" + sysPara.followResultDesc + "</p>"
							+"</div>";
 						$("#saleFollowList").append(html);
 					});
 				}else{
 					$("#saleFollowList").append("<div style='margin-left:14px;'>无相关信息！</div>");
 				}
 			}
 		},
 		error:function(){
 			
 		}
 	});
 }
 
//返回
 function returnBack(){
 	var returnFlag = '<%=returnFlag%>';
 	var url = contextPath + "/system/mobile/phone/customer/customInfo/myCustomer.jsp";
 	if(returnFlag =='1'){
 		url = contextPath + "/system/mobile/phone/customer/customInfo/shareCustomer.jsp";
 	}
 	location.href = url;
 }




</script>
</body>
</html>
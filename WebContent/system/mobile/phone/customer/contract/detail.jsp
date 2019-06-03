<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%
	String sid = request.getParameter("sid");
	String returnFlag = request.getParameter("returnFlag");//1-返回共享页面；其他值-返回我的
%>
<!DOCTYPE HTML>
<html>
<head>
<title>合同详情</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
</head>
<body>
<header id="header" class="mui-bar mui-bar-nav">
	<button class="mui-btn mui-btn-link mui-btn-nav mui-pull-left" onclick="returnBack();">
	    <span class="mui-icon mui-icon-left-nav"></span>返回
	</button>
	<button class="mui-btn mui-btn-link mui-btn-nav mui-pull-right" >
	    
	</button>
	<h1 class="mui-title">合同详情</h1>
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
				<label>合同编号： </label>
				<p class="app-row-right mui-ellipsis" id="contractNo"></p>
			</div>
			<div class="mui-input-row">
				<label>合同名称： </label>
				<p class="app-row-right mui-ellipsis" id="contractName"></p>
			</div>
			<div class="mui-input-row">
				<label>货币类别： </label>
				<p class="app-row-right mui-ellipsis" id="currencyTypeDesc" ></p>
			</div>
			<div class="mui-input-row">
				<label>合同状态： </label>
				<p class="app-row-right mui-ellipsis" id="contractStatusDesc"></p>
			</div>
			<div class="mui-input-row">
				<label>合同类型： </label>
				<p class="app-row-right mui-ellipsis" id="contractCodeDesc"></p>
			</div>
			<div class="mui-input-row">
				<label>结算方式： </label>
				<p class="app-row-right mui-ellipsis" id="accountsMethodDesc"></p>
			</div>
			<div class="mui-input-row">
				<label>合同签订日期： </label>
				<p class="app-row-right mui-ellipsis" id="contractSignDate"></p>
			</div>
			<div class="mui-input-row">
				<label>合同有效日期： </label>
				<p class="app-row-right mui-ellipsis" id="contractDate"></p>
			</div>
			<div class="mui-input-row">
				<label>成交金额： </label>
				<p class="app-row-right mui-ellipsis" id="contractAmount"></p>
			</div>
		</div>
	</div>
	
	<!-- 产品信息 -->
	<div class="mui-input-group">
		<div class="mui-input-row" style="background: #E0EBF9;font-weight: bold;font-size:14px;" onclick="showDivFunc('div2','span2');">
			<label style="width: 100%;">
				<span id="span2">+</span>产品信息
			</label>
		</div>
		<div id="div2" style="display:none;" >
			<div id="productList"></div>
			
		</div>
	</div>
	
	
	<!-- 关联信息 -->
	<div class="mui-input-group">
		<div class="mui-input-row" style="background: #E0EBF9;font-weight: bold;font-size:14px;" onclick="showDivFunc('div3','span3');">
			<label style="width: 100%;">
				<span id="span3">+</span>关联信息
			</label>
		</div>
		<div id="div3" style="display:none;" >
			<div class="mui-input-row">
				<label>客户名称： </label>
				<p class="app-row-right mui-ellipsis" id="customerInfoName"></p>
			</div>
			<div class="mui-input-row">
				<label>责任人： </label>
				<p class="app-row-right mui-ellipsis" id="responsibleUserName"></p>
			</div>
			<div class="mui-input-row">
				<label>收款银行： </label>
				<p class="app-row-right mui-ellipsis" id="bueBank"></p>
			</div>
			<div class="mui-input-row">
				<label>付款方式： </label>
				<p class="app-row-right mui-ellipsis" id="paymentMethodDesc"></p>
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
	
	
	<!-- 付款信息 -->
	<div class="mui-input-group">
		<div class="mui-input-row" style="background: #E0EBF9;font-weight: bold;font-size:14px;" onclick="showDivFunc('div4','span4');">
			<label style="width: 100%;">
				<span id="span4">+</span>付款信息
			</label>
		</div>
		<div id="div4" style="display:none;" >
			<div id="recvPaymentsList"></div>
			
		</div>
	</div>
	
	
</div>
<script>
$(function(){
	getInfoById('<%=sid%>');
	getProductList('<%=sid%>');
	
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
	var url =contextPath+"/teeCrmContractController/getById.action";
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
				$("#contractNo").text(prc.contractNo);
				$("#contractName").text(prc.contractName);
				$("#currencyTypeDesc").text(prc.currencyTypeDesc);
				$("#contractStatusDesc").text(prc.contractStatusDesc);
				$("#contractCodeDesc").text(prc.contractCodeDesc);
				$("#accountsMethodDesc").text(prc.accountsMethodDesc);
				
				$("#contractSignDate").text(prc.contractSignDateStr);
				$("#contractDate").text(prc.contractStartDateStr + " 至  " + prc.contractEndDateStr);
				
				$("#contractAmount").text(prc.contractAmount);
				
				//关联信息
				$("#customerInfoName").text(prc.customerInfoName);
				$("#responsibleUserName").text(prc.responsibleUserName);
				$("#bueBank").text(prc.bueBank);
				$("#paymentMethodDesc").text(prc.paymentMethodDesc);
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
 * 获取产品信息
 */
 function getProductList(sid){
 	var url =contextPath+"/teeCrmContractController/getById.action";
 	mui.ajax(url,{
 		type:"POST",
 		dataType:"JSON",
 		data:{sid:sid},
 		timeout:10000,
 		success:function(text){
 			var json = eval("("+text+")");
 			if(json.rtState){
 				var list = json.rtData.productItemModel;
 				if(list.length>0){
 					var html = "<ul class='mui-table-view'>";
 					 jQuery.each(list,function(i,sysPara){
 						html+="<li class=\"mui-table-view-cell mui-media\" onclick=\" \" >"
						+"	<div class=\"mui-media-body\" >"
						+		sysPara.productsName + "&nbsp;&nbsp;" + sysPara.productsNumber + "&nbsp;" + sysPara.unitsDesc
						+"		<p class='mui-ellipsis'>"
						+"			<span>单价 ：" + sysPara.price + "&nbsp;元</span>"
						+"		</p>"
						+"		<p class='mui-ellipsis'>"
						+"			<span>合计：" + sysPara.totalAmount + "&nbsp;元</span>"
						+"		</p>"
						+"	</div>"
						+"</li>";
 					});
 					html+="</ul>";
 					$("#productList").append(html);
 				}else{
 					$("#productList").append("<div style='margin-left:14px;'>无相关信息！</div>");
 				}
 				getRecvPaymentsList(json.rtData.recvPaymentModel);
 			}
 		},
 		error:function(){
 			
 		}
 	});
 }

/**
 * 获取合同付款信息
 */
 function getRecvPaymentsList(list){
	 if(list.length>0){
		var html = "<ul class='mui-table-view'>";
		 jQuery.each(list,function(i,sysPara){
			html+="<li class=\"mui-table-view-cell mui-media\" onclick=\" \" >"
			+"	<div class=\"mui-media-body\" >"
			+		"收款人：" + sysPara.managerUserName 
			+"		<p class='mui-ellipsis'>"
			+"			<span>回款金额 ：" + sysPara.recvPayAmount + "&nbsp;元</span>"
			+"		</p>"
			+"		<p class='mui-ellipsis'>"
			+"			<span>预计回款日期：" + sysPara.planRecvDateStr + "</span>"
			+"		</p>"
			+"		<p class='mui-ellipsis'>"
			+"			<span>实际回款日期：" + sysPara.recvDateStr + "</span>"
			+"		</p>"
			+"	</div>"
			+"</li>";
		});
		html+="</ul>";
		$("#recvPaymentsList").append(html);
	}else{
		$("#recvPaymentsList").append("<div style='margin-left:14px;'>无相关信息！</div>");
	}
 }









//返回
function returnBack(){
	var returnFlag = '<%=returnFlag%>';
	var url = contextPath + "/system/mobile/phone/customer/contract/myContract.jsp";
	if(returnFlag =='1'){
		url = contextPath + "/system/mobile/phone/customer/contract/shareContract.jsp";
	}
	location.href = url;
}





</script>
</body>
</html>
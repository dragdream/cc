<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%
	String sid = request.getParameter("sid");
	String returnFlag = request.getParameter("returnFlag");//1-返回共享页面；2-返回我的客户；3-返回共享客户；其他值-返回我的
%>
<!DOCTYPE HTML>
<html>
<head>
<title>跟单详情</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
</head>
<body>
<header id="header" class="mui-bar mui-bar-nav">
	<button class="mui-btn mui-btn-link mui-btn-nav mui-pull-left" onclick="returnBack();">
	    <span class="mui-icon mui-icon-left-nav"></span>返回
	</button>
	<button class="mui-btn mui-btn-link mui-btn-nav mui-pull-right" >
	    
	</button>
	<h1 class="mui-title">跟单详情</h1>
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
	<!-- 本次跟踪信息 -->
	<div class="mui-input-group">
		<div class="mui-input-row" style="background: #E0EBF9;font-weight: bold;font-size:14px;" onclick="showDivFunc('div1','span1');">
			<label style="width: 100%;" >
				<span id="span1">-</span>本次跟踪信息
			</label>
		</div>
		<div id="div1" style="" >
			<div class="mui-input-row">
				<label>跟踪客户： </label>
				<p class="app-row-right mui-ellipsis" id="customerName"></p>
			</div>
			<div class="mui-input-row">
				<label>跟踪方式： </label>
				<p class="app-row-right mui-ellipsis" id="followTypeDesc"></p>
			</div>
			<div class="mui-input-row">
				<label>联系人： </label>
				<p class="app-row-right mui-ellipsis" id="contantsName" ></p>
			</div>
			<div class="mui-input-row">
				<label>跟踪时间： </label>
				<p class="app-row-right mui-ellipsis" id="followDateDesc"></p>
			</div>
			<div class="mui-input-row">
				<label>跟踪状态： </label>
				<p class="app-row-right mui-ellipsis" id="followResultDesc"></p>
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
				<label>跟踪内容： </label>
				<p class="app-row-right mui-ellipsis" id="followContent"></p>
			</div>
		</div>
	</div>
	
	<!-- 下次跟踪信息 -->
	<div class="mui-input-group">
		<div class="mui-input-row" style="background: #E0EBF9;font-weight: bold;font-size:14px;" onclick="showDivFunc('div2','span2');">
			<label style="width: 100%;">
				<span id="span2">+</span>下次跟踪信息
			</label>
		</div>
		<div id="div2" style="display:none;" >
			<div class="mui-input-row">
				<label style="width: 40%;">下次联系人： </label>
				<p class="app-row-right mui-ellipsis" id="nextFollowUserName"></p>
			</div>
			<div class="mui-input-row">
				<label style="width: 45%;"> 下次跟踪时间 ： </label>
				<p class="app-row-right mui-ellipsis" id="nextFollowTimeDesc"></p>
			</div>
			<div class="mui-input-row">
				<label style="width: 45%;">下次跟踪内容 ： </label>
				<p class="app-row-right mui-ellipsis" id="nextFollowContent"></p>
			</div>
			<div class="mui-input-row">
				<label>是否提醒： </label>
				<p class="app-row-right mui-ellipsis" id="isRemind"></p>
			</div>
		</div>
	</div>
	<!-- 产品信息 -->
	<div class="mui-input-group">
		<div class="mui-input-row" style="background: #E0EBF9;font-weight: bold;font-size:14px;" onclick="showDivFunc('div3','span3');">
			<label style="width: 100%;">
				<span id="span3">+</span>产品信息
			</label>
		</div>
		<div id="div3" style="display:none;" >
			<div id="productList"></div>
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
	var url =contextPath+"/TeeCrmSaleFollowController/getByIdForDetail.action";
	mui.ajax(url,{
		type:"POST",
		dataType:"JSON",
		data:{sid:sid},
		timeout:10000,
		success:function(text){
			var json = eval("("+text+")");
			if(json.rtState){
				var prc = json.rtData;
				if(prc.isRemind==1){
					$("#isRemind").html("是");
				}else{
					$("#isRemind").html("否");
				}
				//基本信息
				$("#customerName").text(prc.customerName);
				$("#followTypeDesc").text(prc.followTypeDesc);
				$("#contantsName").text(prc.contantsName);
				$("#followDateDesc").text(prc.followDateDesc);
				$("#followResultDesc").text(prc.followResultDesc);
				$("#followContent").text(prc.followContent);
				
				//下次跟踪信息
				$("#nextFollowUserName").html(prc.nextFollowUserName);
				$("#nextFollowTimeDesc").html(prc.nextFollowTimeDesc);
				$("#nextFollowContent").html(prc.nextFollowContent);
				
			
				var attachs = prc.attacheModels;
				for(var i = 0 ;i<attachs.length;i++){
					var attach = attachs[i];
					var att = "<div style='height:32px;line-height:32px;'><a href='javascript:void(0);' onclick=\"GetFile('"+attach.sid+"','"+attach.fileName+"','"+attach.attachmentName+"')\">"+attach.fileName + "</a></div>";
					$("#attachments").append(att);
				}
				
				
				//产品信息
				//$("#").html(prc.telephone);
				
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
 	var url =contextPath+"/TeeCrmSaleFollowController/getProductItem.action";
 	mui.ajax(url,{
 		type:"POST",
 		dataType:"JSON",
 		data:{saleFollowId:sid},
 		timeout:10000,
 		success:function(text){
 			var json = eval("("+text+")");
 			if(json.rtState){
 				var list = json.rtData;
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
 			}
 		},
 		error:function(){
 			
 		}
 	});
 }








function returnBack(){
	var returnFlag = '<%=returnFlag%>';
	var url = contextPath + "/system/mobile/phone/customer/saleFollow/mySaleFollow.jsp";
	if(returnFlag =='1'){
		url = contextPath + "/system/mobile/phone/customer/saleFollow/shareSaleFollow.jsp";
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
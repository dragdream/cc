<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid")==null?"0":request.getParameter("sid");
	String customerName = TeeStringUtil.getString(request.getParameter("customerName"), null);
	int paybackStatus=TeeStringUtil.getInteger(request.getParameter("paybackStatus"),1);//默认查询待确认
	TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
	
	int loginPersonId = person.getUuid();
%>

<!DOCTYPE HTML>
<html>
<head>
<title>回款信息</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<style>
#topPopover {
	position: fixed;
	top: 16px;
	right: 6px;
	width: 120px;
}
#topPopover .mui-popover-arrow {
	left: auto;
	right: 6px;
}

#topPopover1 {
	position: fixed;
	top: 16px;
	right: 6px;
	width: 120px;
}

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

</style>
</head>

<script type="text/javascript">
var sid = "<%=sid%>";
var customerName = "<%=customerName%>";
var loginPersonId = <%=loginPersonId%>;
var status = <%=paybackStatus%>;
//初始化方法
function doInit(){
   
	if(sid>0){
		getInfoByUuid(sid);
	}

	//返回
 	backBtn.addEventListener("tap",function(){
		window.location = contextPath+'/system/mobile/phone/crm/payback/indexApprove.jsp?paybackStatus='+status;
	}); 

}


var paymentPersonIds = "";
//根据主键获取详情
function getInfoByUuid(sid){
	var url=contextPath+"/TeeCrmPaybackController/getInfoBySid.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{sid:sid},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				var data=json.rtData;
				bindJsonObj2Cntrl(data);
				paymentPersonIds = data.paymentFinancialId;
				var paybackStatus = json.rtData.paybackStatusDesc;
				if(paybackStatus=="已驳回"){
					$("#rejects").show();
				}
				
				var  attachments = data.attachmodels;
				  if(attachments.length > 0){
					  $.each(attachments, function(index, item){  
						  $("#attachList").append("<div ><a href='javascript:void(0);' onclick=\"GetFile('"+item.sid+"','"+item.fileName+"','"+item.attachmentName+"')\">"+item.fileName + "&nbsp;&nbsp;"+"</a><div>");
					  });
				  }
				
				renderOptBtns(paybackStatus);
				
			}
		}
	});
}

//渲染操作
function renderOptBtns(data){
	var str="";
	$("#topPopover1").empty();
	if(loginPersonId==paymentPersonIds){
		if(data == "待回款"){
			$("#topTop").show();
			str='<ul class="mui-table-view">'+
		   '<li class="mui-table-view-cell" onclick="agree();">同意</li>'+
		   '<li class="mui-table-view-cell" onclick="reject();">驳回</li>'+
		   ' </ul>';
		}else{
			$("#topTop").hide();
		}
		}else{
			$("#topTop").hide();
		}
 	$("#topPopover1").append(str);

}

/**
 * 同意
 */
function agree(){
	if(window.confirm("确定回款信息无误？")){
		var url = contextPath+ "/TeeCrmPaybackController/agree.action";
		var param={sid:sid,paybackStatus:2};
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:param,
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
					alert("操作成功！");
					window.location.href = "<%=contextPath%>/system/mobile/phone/crm/payback/indexApprove.jsp?paybackStatus="+status;
				}else {
					alert("操作失败！");
				}
			}
		});	
	}
}

/**
 * 驳回
 */
function reject(){
	window.location.href = "reject.jsp?sid="+sid+"&paybackStatus="+status;
}

</script>
<body onload="doInit()">
	<header class="mui-bar mui-bar-nav">
		<button class="mui-btn mui-btn-link mui-btn-nav mui-pull-left" id="backBtn">
		    <span class="mui-icon mui-icon-left-nav" ></span>返回
		</button>
	    <button class="mui-btn mui-btn-link mui-btn-nav mui-pull-right"  href="#topPopover1" id="topTop" style="display: none;">
	        <span style="padding-right: 10px;"><a href="#topPopover1">操作</a></span>
	    </button> 
	    <h1 class="mui-title">回款详情</h1>
		
	</header>
	<div id="muiContent" class="mui-content">
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>客户名称</label>
		</div>
		<div class="app-row-content" id="customerName">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>销售订单编号</label>
		</div>
		<div class="app-row-content" id="orderNo">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>回款日期</label>
		</div>
		<div class="app-row-content" id="paybackTimeDesc">
			
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>附件</label>
		</div>
		<div class="app-row-content" id="attachList">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>提醒时间</label>
		</div>
		<div class="app-row-content" id="remindTimeDesc" >
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>回款编号</label>
		</div>
		<div class="app-row-content" id='paybackNo'>
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>回款金额</label>
		</div>
		<div class="app-row-content" id='paybackAmount'>
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>回款方式</label>
		</div>
		<div class="app-row-content" id='paybackStyleDesc'>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>备注</label>
		</div>
		<div class="app-row-content" id='remark'>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>负责人</label>
		</div>
		<div class="app-row-content" id='managePersonName'>
			
		</div>
			
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>回款财务</label>
		</div>
		<div class="app-row-content" id='paymentFinancialName'>
		</div>
	</div>
	<div class="mui-input-group" style="display: none;" id="rejects">
		<div class="mui-input-row">
			<label>驳回原因</label>
		</div>
		<div class="app-row-content" id="rejectReason">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>状态</label>
		</div>
		<div class="app-row-content" id='paybackStatusDesc'>
			
		</div>
			
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>创建人</label>
		</div>
		<div class="app-row-content" id='addPersonName'>
			
		</div>
			
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>创建时间</label>
		</div>
		<div class="app-row-content" id='createTimeDesc'>
			
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>最后变化时间</label>
		</div>
		<div class="app-row-content" id='lastEditTimeDesc'>
			
		</div>
	</div>
</div>

    <div id="topPopover1" class="mui-popover">
	</div>

<br/>
</body>
</html>
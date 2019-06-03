<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid")==null?"0":request.getParameter("sid");
	String customerName = TeeStringUtil.getString(request.getParameter("customerName"), null);
	String customerId = request.getParameter("customerId")==null?"0":request.getParameter("customerId");
	int type=TeeStringUtil.getInteger(request.getParameter("type"),1);//默认查询全部
	TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
	
	int loginPersonId = person.getUuid();
%>

<!DOCTYPE HTML>
<html>
<head>
<title>开票申请详情</title>
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
var customerId = "<%=customerId%>";
var type = <%=type%>;
//初始化方法
function doInit(){
   
	if(sid>0){
		getInvoiceInfoBySid(sid);
	}

	//返回
	backBtn.addEventListener("tap",function(){
		window.location.href=contextPath+"/system/mobile/phone/crm/customer/crmInvoice/invoiceList.jsp?type="+type+"&customerId="+customerId+"&customerName="+customerName;
	});

}


var managerPerId = "";
//根据主键获取详情
function getInvoiceInfoBySid(sid){
	var url=contextPath+"/TeeCrmInvoiceController/getInfoBySid.action";
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
				managerPerId = data.managePersonId;
				var invoiceStatus = json.rtData.invoiceStatusDesc;
				if(json.rtData.invoiceStatus==3){
					$("#rejects").show();
				}
				
				var  attachments = data.attachmodels;
				  if(attachments.length > 0){
					  $.each(attachments, function(index, item){  
						  $("#attachList").append("<div ><a href='javascript:void(0);' onclick=\"GetFile('"+item.sid+"','"+item.fileName+"','"+item.attachmentName+"')\">"+item.fileName + "&nbsp;&nbsp;"+"</a><div>");
					  });
				  }
				
				renderOptBtns(invoiceStatus);
				
			}
		}
	});
}

//渲染操作
function renderOptBtns(data){
	var str="";
	$("#topPopover1").empty();
	if(managerPerId==loginPersonId){
		if(data == "待开票"){
			$("#topTop").show();
			str='<ul class="mui-table-view">'+
		   '<li class="mui-table-view-cell" onclick="edit();">编辑</li>'+
		   '<li class="mui-table-view-cell" onclick="deleteById();">删除</li>'+
		   ' </ul>';
		}else if(data =="已开票"){
			$("#topTop").show();
			str='<ul class="mui-table-view">'+
			'<li class="mui-table-view-cell" onclick="deleteById();">删除</li>'+
			' </ul>';
		}else if(data =="已驳回"){
			$("#topTop").show();
			str='<ul class="mui-table-view">'+
			'<li class="mui-table-view-cell" onclick="deleteById();">删除</li>'+
			' </ul>';
		 }
		}else{
			$("#topTop").hide();
		}
 	$("#topPopover1").append(str);

}

//编辑
function edit(){
	window.location = contextPath+"/system/mobile/phone/crm/customer/crmInvoice/addOrUpdate.jsp?customerId="+customerId+"&customerName="+customerName+"&type="+type+"&sid="+sid;
}

/**
 * 删除
 */
function deleteById(){
	if(window.confirm("确定删除此开票信息？删除后不可恢复！")){
		var url = contextPath+ "/TeeCrmInvoiceController/deleteById.action";
		var param={sid:sid};
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:param,
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
					alert("删除成功！");
					window.location.href=contextPath+"/system/mobile/phone/crm/customer/crmInvoice/invoiceList.jsp?type="+type+"&customerId="+customerId+"&customerName="+customerName;
				}else {
					alert("删除失败！");
				}
			}
		});	
	}
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
	    <h1 class="mui-title">开票详情</h1>
		
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
			<label>开票日期</label>
		</div>
		<div class="app-row-content" id="invoiceTimeDesc">
			
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>开票申请编号</label>
		</div>
		<div class="app-row-content" id="invoiceNo" >
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>开票金额（元）</label>
		</div>
		<div class="app-row-content" id='invoiceAmount'>
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>开票类型</label>
		</div>
		<div class="app-row-content" id='invoiceTypeDesc'>
		
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
			<label>发票号码</label>
		</div>
		<div class="app-row-content" id='invoiceNumber'>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>开票财务</label>
		</div>
		<div class="app-row-content" id='invoiceFinancialName'>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>附件</label>
		</div>
		<div class="app-row-content" id="attachList">
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
			<label>联系人</label>
		</div>
		<div class="app-row-content" id='contacts'>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>联系方式</label>
		</div>
		<div class="app-row-content" id='contactNumber'>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>寄送地址</label>
		</div>
		<div class="app-row-content" id='sendAddress'>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>抬头类型</label>
		</div>
		<div class="app-row-content" id='headerTypeDesc'>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>开票抬头</label>
		</div>
		<div class="app-row-content" id='invoiceHeader'>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>电话</label>
		</div>
		<div class="app-row-content" id='telephone'>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>状态</label>
		</div>
		<div class="app-row-content" id='invoiceStatusDesc'>
			
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
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid")==null?"0":request.getParameter("sid");
	String customerName = TeeStringUtil.getString(request.getParameter("customerName"), null);
	int invoiceStatus=TeeStringUtil.getInteger(request.getParameter("invoiceStatus"),1);//默认查询待确认
	TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
	
	int loginPersonId = person.getUuid();
%>

<!DOCTYPE HTML>
<html>
<head>
<title>开票信息</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<style>
#topPopover {
	position: fixed;
	top: 16px;
	right: 6px;
	width: 120px;
	z-index:10000000;
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
var status = <%=invoiceStatus%>;
//初始化方法
function doInit(){
   
	if(sid>0){
		getInvoiceInfoBySid(sid);
	}

	//返回
 	backBtn.addEventListener("tap",function(){
 		
		window.location = contextPath+'/system/mobile/phone/crm/invoice/indexApprove.jsp?invoiceStatus='+status;
	}); 

}


var billingPersonIds = "";
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
				billingPersonIds = data.invoiceFinancialId;
				var invoiceStatus = json.rtData.invoiceStatusDesc;
				if(invoiceStatus=="已驳回"){
					$("#rejects").show();
					$("#rejectReason").show();
				}else{
					$("#rejects").hide();
					$("#rejectReason").hide();
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
	 if(loginPersonId==billingPersonIds){
		 if(data == "待开票"){
			$("#topTop").show();
			str='<ul class="mui-table-view">'+
		   '<li class="mui-table-view-cell" onclick="edit();">编辑</li>'+
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
	if(window.confirm("确定开票信息无误？")){
		var url = contextPath+ "/TeeCrmInvoiceController/agree.action";
		var param={sid:sid,invoiceStatus:2};
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:param,
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
					alert("操作成功！");
					window.location.href = "<%=contextPath%>/system/mobile/phone/crm/invoice/indexApprove.jsp?invoiceStatus="+status;
				}else {
					alert("操作失败！");
				}
			}
		});	
	}
}

/**
 * 拒绝
 */
function reject(){
	window.location.href = "reject.jsp?sid="+sid+"&invoiceStatus="+status;
}



/**
 * 编辑
 */
 function edit(){
	window.location = 'addOrUpdate.jsp?sid='+sid+'&customerName='+customerName+'&isApprove=1&invoiceStatus='+status;
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
			<label><img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" style="vertical-align: middle;">&nbsp;<span style="font-weight: bold;">基本信息</span></label>
		</div>
		<div class="mui-input-row">
			<label>客户名称</label>
			<div  id="customerName" style="line-height: 40px;"></div>
		</div>
		<div class="mui-input-row">
			<label>销售订单编号</label>
			<div  id="orderNo" style="line-height: 40px;"></div>
		</div>
		<div class="mui-input-row">
			<label>开票日期</label>
			<div  id="invoiceTimeDesc" style="line-height: 40px;"></div>
		</div>
		<div class="mui-input-row">
			<label>开票申请编号</label>
			<div  id="invoiceNo" style="line-height: 40px;"></div>
		</div>
		<div class="mui-input-row">
			<label>开票金额（元）</label>
			<div  id='invoiceAmount' style="line-height: 40px;"></div>
		</div>
		<div class="mui-input-row">
			<label>开票类型</label>
			<div  id='invoiceTypeDesc' style="line-height: 40px;"></div>
		</div>
		<div class="mui-input-row">
			<label>备注</label>
		</div>
		<div class="app-row-content" id='remark'>
		</div>
		<div class="mui-input-row">
			<label>负责人</label>
			<div  id='managePersonName' style="line-height: 40px;"></div>
		</div>
		<div class="mui-input-row">
			<label>发票号码</label>
			<div  id='invoiceNumber' style="line-height: 40px;"></div>
		</div>
		<div class="mui-input-row">
			<label>开票财务</label>
			<div  id='invoiceFinancialName' style="line-height: 40px;"></div>
		</div>
		<div class="mui-input-row">
			<label>附件</label>
		</div>
		<div class="app-row-content" id="attachList">
		</div>
	</div>
	
	
	<div class="mui-input-group">
	    <div class="mui-input-row">
			<label><img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" style="vertical-align: middle;">&nbsp;<span style="font-weight: bold;">发票信息</span></label>
		</div>
		<div class="mui-input-row">
			<label>抬头类型</label>
			<div  id='headerTypeDesc' style="line-height: 40px;"></div>
		</div>
		<div class="mui-input-row">
			<label>开票抬头</label>
			<div  id='invoiceHeader' style="line-height: 40px;"></div>
		</div>
		<div class="mui-input-row">
			<label>纳税人识别号</label>
			<div  id='nsrNumber' style="line-height: 40px;"></div>
		</div>
		<div class="mui-input-row">
			<label>开户行账号</label>
			<div  id='khhNumber' style="line-height: 40px;"></div>
		</div>
		<div class="mui-input-row">
			<label>开户行地址</label>
			<div  id='khhAddress' style="line-height: 40px;"></div>
		</div>
		<div class="mui-input-row">
			<label>开户行名称</label>
			<div  id='khhName' style="line-height: 40px;"></div>
		</div>
		<div class="mui-input-row">
			<label>电话</label>
			<div  id='telephone' style="line-height: 40px;"></div>
		</div>
	</div>
	
	
	<div class="mui-input-group">
	    <div class="mui-input-row">
			<label><img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" style="vertical-align: middle;">&nbsp;<span style="font-weight: bold;">寄送信息</span></label>
		</div>
		<div class="mui-input-row">
			<label>联系人</label>
			<div  id='contacts' style="line-height: 40px"></div>
		</div>
		
		<div class="mui-input-row">
			<label>联系方式</label>
			<div id='contactNumber'  style="line-height: 40px"></div>
		</div>
		<div class="mui-input-row">
			<label>寄送地址</label>
			<div  id='sendAddress' ></div>
		</div>
	</div>

	<div class="mui-input-group">
	    <div class="mui-input-row">
			<label><img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" style="vertical-align: middle;">&nbsp;<span style="font-weight: bold;">其他</span></label>
		</div>
		<div class="mui-input-row">
			<label>状态</label>
			<div  id='invoiceStatusDesc' style="line-height: 40px"></div>	
		</div>
		<div class="mui-input-row"  id="rejects" style="display: none;">
			<label>驳回原因</label>
		</div>
		<div class="app-row-content" id="rejectReason" style="display: none;"></div>
		<div class="mui-input-row">
			<label>创建人</label>
			<div  id='addPersonName' style="line-height: 40px"></div>
		</div>
		<div class="mui-input-row">
			<label>创建时间</label>
			<div  id='createTimeDesc' style="line-height: 40px"></div>
		</div>
		<div class="mui-input-row">
			<label>最后变化时间</label>
			<div  id='lastEditTimeDesc' style="line-height: 40px"></div>
		</div>
	</div>
	
	
</div>


<div id="topPopover1" class="mui-popover"></div>

<br/>
</body>
</html>
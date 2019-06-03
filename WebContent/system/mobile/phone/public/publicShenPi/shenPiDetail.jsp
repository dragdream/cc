<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);//车辆Id
int status = TeeStringUtil.getInteger(request.getParameter("status"), 0);
%>
<!DOCTYPE HTML>
<html>
<head>
<title>用品登记</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<script type="text/javascript" src="<%=contextPath %>/common/js/address_cascade.js"></script>

<script>
var status=<%=status%>;
var sid=<%=sid%>;
function doInit(){
	getInfoById(sid);
	//审批不通过
	 $("body").on("tap","#notShenPi",function(){
		 doObtain(sid);
	});
	//审批通过
	 $("body").on("tap","#shenPi",function(){
		 doPass(sid);
	});
	if(status==1){
		$("#shenPi").show();
		$("#notShenPi").show();
	}
}

//同意审批
function doPass(sid){
	window.location.href="selectUser.jsp?sid="+sid+"&status="+status;
}

//不同意审批
function doObtain(sid){
	window.location.href="reason.jsp?sid="+sid+"&status="+status;
}
//
//用品申请信息
function getInfoById(sid){
	var url =   "<%=contextPath%>/officeStockBillController/findStockPublic.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{sid:sid},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			var prc = json.rtData;
				bindJsonObj2Cntrl(prc);
		}
	});
}

</script>
</head>
<body onload="doInit()">
<header id="header" class="mui-bar mui-bar-nav">
	<span class="mui-icon mui-icon-back" onclick="window.location.href='list/indexList.jsp?status=<%=status %>'"></span>
	<h1 class="mui-title">用品审批详情</h1>
	<a class="mui-action-menu mui-icon mui-icon-more mui-pull-right" href="#topPopover"></a>
</header>

<div id="muiContent" class="mui-content">
<form id="form1" name="form1">
	<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>用品名称/编码</label>
		</div>
		<div class="mui-input-row">
		   <input type="text" name="productCode" id="productCode" readonly="readonly"/>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>用品类型/用品库</label>
		</div>
		<div class="mui-input-row">
			<input type="text" id="productName" name="productName" readonly="readonly"/>
		</div>
	</div>
	<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>申请类型</label>
		</div>
		<div class="mui-input-row">
			<input type="text" id="regTypeDesc" name="regTypeDesc" readonly="readonly"/>
		</div>
	</div>
		<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>申请数量/库存量</label>
		</div>
		<div class="mui-input-row">
           	<input type="text" id="regCount" name="regCount" readonly="readonly"/>	
		</div>
	</div>
		<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>登记用户</label>
		</div>
		<div class="mui-input-row">
            <input type="text" name="regUserName" id="regUserName"  readonly="readonly"/>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>登记时间</label>
		</div>
		<div class="mui-input-row">
		   <input type="text" id="regTimeDesc" name="regTimeDesc" readonly="readonly"/>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>调度人员</label>
		</div>
		<div class="mui-input-row">
		   <input type="text" id="operatorName" name="operatorName" readonly="readonly"/>
		</div>
	</div>
	
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>调度开始时间</label>
		</div>
		<div class="mui-input-row">
		   <input type="text" id="operTimeDesc" name="operTimeDesc" readonly="readonly"/>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>调度完成时间</label>
		</div>
		<div class="mui-input-row">
		   <input type="text" id="finishTimeDesc" name="finishTimeDesc" readonly="readonly"/>
		</div>
	</div>
		<div class="mui-input-group">
		<div class="mui-input-row">
			<label>备注</label>
		</div>
		<div class="mui-input-row">
		   <input type="text" id="remark" name="remark" readonly="readonly"/>
		</div>
	</div>
</form>	
</div>
<!--右上角弹出菜单-->
	<div id="topPopover" class="mui-popover">
		<ul class="mui-table-view">
		   <li class="mui-table-view-cell" id="shenPi" style="display: none;">审批通过</li>
		   <li class="mui-table-view-cell" id="notShenPi" style="display: none;">不通过</li>
		</ul>
	</div>

<div id="mapFrameDiv" style="display:none;z-index:10000000;position:fixed;top:0px;bottom:0px;left:0px;right:0px;background:white">
<iframe id="mapFrame" frameborder="no" style="width:100%;height:100%"></iframe>
</div>

</body>
</html>
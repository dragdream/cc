<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script>

function doInit(){
	//加载可用库信息
	var url = contextPath+"/officeDepositoryController/getDeposListWithNoPriv.action";
	var json = tools.requestJsonRs(url);
	var depositories = json.rtData;
	var html = "<option value=\"0\"></option>";
	for(var i=0;i<depositories.length;i++){
		html+="<option value=\""+depositories[i].sid+"\">"+depositories[i].deposName+"</option>";
	}
	$("#deposId").html(html);
}

function catList(deposId){
	var url = contextPath+"/officeCategoryController/getCatListWithNoPriv.action";
	var json = tools.requestJsonRs(url,{deposId:deposId});
	var cats = json.rtData;
	var html = "<option value=\"0\"></option>";
	for(var i=0;i<cats.length;i++){
		html+="<option value=\""+cats[i].sid+"\">"+cats[i].catName+"</option>";
	}
	$("#catId").html(html);
}

function productList(catId){
	var url = contextPath+"/officeProductController/getProductWithPriv.action";
	var json = tools.requestJsonRs(url,{catId:catId,regType:$("#regType")[0].value});
	var rows = json.rtData;
	var html = "<option value=\"0\"></option>";
	for(var i=0;i<rows.length;i++){
		html+="<option value=\""+rows[i].sid+"\">"+rows[i].proName+"/(当前库存"+rows[i].curStock+")</option>";
	}
	$("#productId").html(html);
}

function commit(){

	if(!$("#form1").valid()){
		return;
	}
	
	if($("#deposId")[0].value=="0"){
// 		top.$.jBox.tip("请选择所属用品库","warning");
        $.MsgBox.Alert_auto("请选择所属用品库");
		$("#deposId").focus();
		return;
	}
	if($("#catId")[0].value=="0" || $("#catId")[0].value==""){
// 		top.$.jBox.tip("请选择用品类型","info");
		$.MsgBox.Alert_auto("请选择用品类型");
		$("#catId").focus();
		return;
	}
	if($("#productId")[0].value=="0" || $("#productId")[0].value==""){
// 		top.$.jBox.tip("请选择用品","info");
        $.MsgBox.Alert_auto("请选择用品");
		$("#productId").focus();
		return;
	}

	//判断是否超出库存量
	if($("#regType").val()!="3"){
		var url = contextPath +"/officeStockBillController/checkOutOfStockCount.action";
		var json = tools.requestJsonRs(url,{productId:$("#productId").val(),regCount:$("#regCount").val()});
		if(!json.rtData){
// 			top.$.jBox.tip("申请数量已超出库存量！","error");
			$.MsgBox.Alert_auto("申请数量已超出库存量！");
			return ;
		}
	}
	
	var param = tools.formToJson($("#form1"));
	var url = contextPath+"/officeStockBillController/addStockBill.action";
	var json = tools.requestJsonRs(url,param);
	if(json.rtState){
// 		top.$.jBox.tip(json.rtMsg,"info");
		$.MsgBox.Alert_auto(json.rtMsg);
		return true;
	}
// 	top.$.jBox.tip(json.rtMsg,"error");
	$.MsgBox.Alert_auto(json.rtMsg);
	return false;
}

function regTypeChanged(val){
	productList($("#catId").val());
}

</script>
<style>
.tableFormLine{
    margin-left: 20px;
    margin-top: 5px;
}

</style>
</head>
<body onload="doInit()" style="font-size:12px; background:#f4f4f4;overflow:hidden;">
<form id="form1" name="form1">
	<div class="tableFormLine" style="margin-top: 0px;!important;">
		<b>登记类型：</b>
		<br/>
		<select class="BigSelect" name="regType" id="regType" style="width:300px" onchange="regTypeChanged(this.value)">
			<option value="1">领用</option>
			<option value="2">借用</option>
			<option value="3">归还</option>
		</select>
	</div>
	<div class="tableFormLine">
		<b>用品库：</b>
		<br/>
		<select class="BigSelect" id="deposId" onchange="catList(this.value)" style="width:300px">
			
		</select>
	</div>
	<div class="tableFormLine">
		<b>用品类别：</b>
		<br/>
		<select class="BigSelect" id="catId" onchange="productList(this.value)" style="width:300px">
			
		</select>
	</div>
	<div class="tableFormLine">
		<b>用品：</b>
		<br/>
		<select class="BigSelect" id="productId" name="productId" style="width:300px">
			
		</select>
	</div>
	<div class="tableFormLine">
		<b>申请/归还数量：</b>
		<br/>
		<input type="text" name="regCount" id="regCount" class="BigInput easyui-validatebox" required="true" validType="number[] & integeBetweenLength[1,2147483647]" style="width:290px"/>
	</div>
	<div class="tableFormLine">
		<b>备注：</b>
		<br/>
		<textarea type="text" name="remark" id="remark" class="BigTextarea" style="width:300px;height:60px"></textarea>
	</div>
</form>
</body>
</html>
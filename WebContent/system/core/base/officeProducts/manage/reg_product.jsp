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
<style type="text/css">
.moduleHeader
{
    font-size:14px;
    background: #f4f4f4;
}

</style>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script>

function doInit(){
	//加载可用库信息
	var url = contextPath+"/officeDepositoryController/getDeposListByOperatePriv.action";
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
	var json = tools.requestJsonRs(url,{catId:catId});
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

	
	var param = tools.formToJson($("#form1"));
	var url = contextPath+"/officeStockController/addStockInfo.action";
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

</script>

</head>
<body onload="doInit()" style="font-size:12px">
<form id="form1" name="form1">
	<div class="moduleHeader">
		<p>登记库存</p>
	</div>
	<table class="TableBlock" width="100%">
		<tr class="tableFormLine">
			<td class="TableData">
			<b>登记类型：</b>
			</td>
			<td class="TableData">
				<select class="BigSelect" name="regType" id="regType">
					<option value="4">采购入库</option>
					<option value="5">维护</option>
					<option value="6">报废</option>
				</select>
			</td>
		</tr>
		<tr class="tableFormLine">
			<td class="TableData">
				<b>用品库：</b>
			</td>
			<td class="TableData">
				<select class="BigSelect" id="deposId" onchange="catList(this.value)">
					
				</select>
			</td>
		</tr>
		<tr class="tableFormLine">
			<td class="TableData">	
			<b>用品类别：</b>
			</td>
			<td class="TableData">
			<select class="BigSelect" id="catId" onchange="productList(this.value)">
				
			</select>
			</td>
		</tr>
		<tr class="tableFormLine">
			<td class="TableData">
			<b>用品：</b>
			</td>
			<td class="TableData">
			<select class="BigSelect" id="productId" name="productId">
				
			</select>
			</td>
		</tr>
		<tr class="tableFormLine">
			<td class="TableData">	
				<b>数量&nbsp;<font color="red">*</font>：</b>
				</td>
			<td class="TableData">
			<input type="text" name="regCount" id="regCount" class="BigInput easyui-validatebox" required="true" validType="number[]"/>
			</td>
		</tr>
	</table>
</form>
</body>
</html>
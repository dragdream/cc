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
<style type="text/css">
.TableHeader
{
    font-size:14px;
}

</style>
<script>

function doInit(){
	//加载可用库信息
	var url = contextPath+"/officeDepositoryController/datagrid.action";
	var json = tools.requestJsonRs(url);
	var depositories = json.rows;
	var html = "<option value=\"0\"></option>";
	for(var i=0;i<depositories.length;i++){
		html+="<option value=\""+depositories[i].sid+"\">"+depositories[i].deposName+"</option>";
	}
	$("#depository").html(html);
}

function commit(){
	if(!$("#form1").valid()){
		return ;
	}
	
	if($("#depository")[0].value=="0"){
// 		top.$.jBox.tip("请选择所属用品库","warning");
        $.MsgBox.Alert_auto("请选择所属用品库");
		$("#depository").focus();
		return;
	}
	if($("#categoryId")[0].value=="0" || $("#categoryId")[0].value==""){
// 		top.$.jBox.tip("请选择用品类型","info");
        $.MsgBox.Alert_auto("请选择用品类型");
		$("#categoryId").focus();
		return;
	}

	//获取最高警戒和最低警戒
	var max=$("#maxStock").val();
	var min=$("#minStock").val();
	if(max!=""&&min!=""){
      if(max<=min){
    	  $.MsgBox.Alert_auto("最高警戒库存不能低于最低警戒库存！");
    	  return;
      }	
	}
	
	
	var param = tools.formToJson($("#form1"));
	var url = contextPath+"/officeProductController/addProduct.action";
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

function fetchCategory(obj){
	var url = contextPath+"/officeCategoryController/datagrid.action";
	var json = tools.requestJsonRs(url,{depositoryId:obj.value});
	var datas = json.rows;
	var html = "";
	for(var i=0;i<datas.length;i++){
		html+="<option value=\""+datas[i].sid+"\">"+datas[i].catName+"</option>";
	}
	$("#categoryId").html(html);
}

</script>

</head>
<body onload="doInit()" style="font-size:12px">
<form id="form1" name="form1">
	<table style="width:100%;font-size:12px" class="TableBlock">
		<tr class="TableHeader">
			<td colspan="4">
					用品基础信息
			</td>
		</tr>
		<tr>
			<td class="TableData">
				<b>用品名称<span style="color:red;font-weight:bold;">*</span>：</b>
				</td>
			<td class="TableData">
				<input type="text" required="true" class="easyui-validatebox BigInput" name="proName" id="proName" />
			</td>
			<td class="TableData">
				<b>用品编号：</b>
			</td>
			<td class="TableData">
				<input type="text" class="BigInput" name="proCode" id="proCode" />
			</td>
		</tr>
		<tr>
			<td class="TableData">
				<b>计量单位：</b>
			</td>
			<td class="TableData">
				<input type="text" class="BigInput" name="proUnit" id="proUnit" />
			</td>
			<td class="TableData">
				<b>规格：</b>
			</td>
			<td class="TableData">
				<input type="text" class="BigInput" name="norms" id="norms" />
			</td>
		</tr>
		<tr>
			<td class="TableData">
				<b>供应商：</b>
			</td>
			<td class="TableData">
				<input type="text" class="BigInput" name="proSupplier" id="proSupplier" />
			</td>
			<td class="TableData">
				<b>单价（元）<span style="color:red;font-weight:bold;">*</span>：</b>
			</td>
			<td class="TableData">
				<input type="text" class="BigInput easyui-validatebox" validType="number[]" name="price" id="price" required="true"/>
			</td>
		</tr>
		<tr>
			<td class="TableData">
				<b>最低警戒库存：</b>
				</td>
			<td class="TableData">
				<input type="text" class="BigInput easyui-validatebox" name="minStock" id="minStock"  validType="number[]"/>
			</td>
			<td class="TableData">
				<b>最高警戒库存：</b>
			</td>
			<td class="TableData">
				<input type="text" class="BigInput easyui-validatebox" name="maxStock" id="maxStock"  validType="number[]"/>
			</td>
		</tr>
		<tr>
			<td class="TableData">
				<b>用品库<span style="color:red;font-weight:bold;">*</span>：</b>
			</td>
			<td class="TableData">
				<select class="BigSelect" id="depository" onchange="fetchCategory(this)">
					<option></option>
				</select>
			</td>
			<td class="TableData">
				<b>申请类型<span style="color:red;font-weight:bold;">*</span>：</b>
			</td>
			<td class="TableData">
				<select type="text" class="BigSelect" name="regType" id="regType">
					<option value="1">领用</option>
					<option value="2">借用</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TableData">
		  		<b>用品类别<span style="color:red;font-weight:bold;">*</span>：</b>
		    </td>
			<td class="TableData" colspan="3">
				<select class="BigSelect" id="categoryId" name="categoryId">
					<option></option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TableData">
				<b>用品描述：</b>
		</td>
			<td class="TableData" colspan="3">
				<textarea class="BigTextarea" style="width:500px;height:100px" name="proDesc" id="proDesc"></textarea>
			</td>
		</tr>
		<tr class="TableHeader">
			<td colspan="4">
					权限与管理
			</td>
		</tr>
		<tr>
			<td class="TableData">
				<b>审批权限（用户）：</b>
		</td>
			<td class="TableData" colspan='3'>
				<textarea class="BigTextarea readonly" readonly style="width:320px;height:80px" id="auditorsNames"></textarea>
				<input type="hidden" id="auditorsIds" name="auditorsIds" />
				&nbsp;<a href="javascript:void(0)" onclick="selectUser(['auditorsIds','auditorsNames'])">添加</a>
				&nbsp;<a href="javascript:void(0)" onclick="clearData('auditorsIds','auditorsNames')">清空</a>
			</td>
		</tr>
		<tr>
			<td  class="TableData">
				<b>登记权限（用户）：</b>
			</td>
			<td class="TableData"  colspan='3'>
				<textarea class="BigTextarea readonly" readonly style="width:320px;height:80px" id="regUsersNames"></textarea>
				<input type="hidden" id="regUsersIds" name="regUsersIds" />
				&nbsp;<a href="javascript:void(0)" onclick="selectUser(['regUsersIds','regUsersNames'])">添加</a>
				&nbsp;<a href="javascript:void(0)" onclick="clearData('regUsersIds','regUsersNames')">清空</a>
			</td>
		</tr>
		<tr>
			<td class="TableData">
				<b>登记权限（部门）：</b>
			</td>
			<td class="TableData" colspan='3' >
				<textarea class="BigTextarea readonly" readonly style="width:320px;height:80px" id="regDeptsName"></textarea>
				<input type="hidden" id="regDeptsIds" name="regDeptsIds" />
				&nbsp;<a href="javascript:void(0)" onclick="selectDept(['regDeptsIds','regDeptsName'])">添加</a>
				&nbsp;<a href="javascript:void(0)" onclick="clearData('regDeptsIds','regDeptsName')">清空</a>
			</td>
		</tr>
	</table>
</form>
</body>
</html>
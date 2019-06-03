<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>售后服务查询</title>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/core/afterSaleService/js/saleService.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crm.js"></script>
<script type="text/javascript">
function doInit(){
	getCrmCodeByParentCodeNo("CUSTOMER_SERVICE_TYPE","serviceType");
	getCrmCodeByParentCodeNo("CUSTOMER_SERVICE_EMERGENCY","emergencyDegree");
	//getInfoList();
}

var datagrid;
function getInfoList(){
	var queryParams=tools.formToJson($("#form1"));
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/crmAfterSaleServController/getManageInfoList.action?shareFlag=2",
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		queryParams:queryParams,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
		columns:[[
				{field:'sid',checkbox:true,title:'ID',width:1},
				{field:'serviceCode',title:'服务编号',width:40},
				{field:'customerInfoName',title:'客户名称',width:60},
				{field:'handleStatus',title:'是否完成',width:25,formatter:handleStatusFunc},
				{field:'emergencyDegree',title:'紧急程度',width:25},
				{field:'contactUserName',title:'客户联系人',width:30},
				{field:'acceptDatetimeStr',title:'受理时间',width:55,formatter:function(value, rowData, rowIndex){
					return value;
				}},
				{field:'handleDatetimeStr',title:'处理时间',width:55,formatter:function(value, rowData, rowIndex){
					return value;
				}},
				{field:'questionDesc',title:'问题描述',width:60},
				{field:'2',title:'操作',align:'center',width:50,formatter:function(value, rowData, rowIndex){
				var str = "<a href='#' onclick='showInfoFunc("+rowData.sid+")'>详细信息 </a>";
				return str;
			}}
		]]
	});
}

//根据条件查询
function doSearch(){
	$('#searchDiv').toggle();
	$("#optItem").show();
	$(".datagrid-view").show();
	getInfoList();
}


/**
 * 联系人（选择客户名称后调用该回调函数）
 */
function getContactUser(customerId,customerName){
	var url = contextPath+"/TeeCrmContactUserController/getContactUserList.action";
	var json = tools.requestJsonRs(url,{customerId:customerId});
	if(json.rtState){
		var contactUserList = json.rtData;
		var html = "<option value=''>请选择</option>";
		for(var i=0;i<contactUserList.length;i++){
			html+="<option value=\""+contactUserList[i].sid+"\">"+contactUserList[i].name+"</option>";
		}
		$("#contactUserId").html(html);
	}
}




function toReturn(){
	window.location.reload();
}
</script>
</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px">
	<table id="datagrid" fit="true" ></table>
	<div id="toolbar">
		<form id="form1" name="form1" method="post">
		<div style="margin-top:10px;" id="searchDiv">
			<table align="center" width="80%" class="TableBlock" >
				<tr>
					<td nowrap class="TableData"  width="15%;" >服务编号：</td>
					<td class="TableData"  >
						<input type="text" name="serviceCode" id="serviceCode" size="" class="BigInput  easyui-validatebox"  size="15" maxlength="100">
					</td>
					<td nowrap class="TableData" >是否完成 ：</td>
					<td class="TableData" >
						<select name="handleStatus" id="handleStatus"   class="BigSelect easyui-validatebox"  title="类型可在“CRM管理”->CRM编码设置。">
							<option value="">请选择</option>
							<option value="0">未完成</option>
							<option value="1">已完成</option>
						</select
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData"  width="15%;" >客户名称：</td>
					<td class="TableData" width="35%;" >
						<input class="BigInput" type="hidden" id = "customerInfoId" name='customerInfoId' />
						<input class="BigInput" type="text" id ="customerInfoName" name='customerInfoName' readonly="readonly" />
						<a href="javascript:void(0);" class="orgAdd" onClick="selectCustomerInfo(['customerInfoId','customerInfoName'],'getContactUser')">选择客户</a></a>&nbsp;&nbsp;
						<a href="javascript:void(0);" class="orgClear" onClick="clearData('customerInfoId','customerInfoName')">清空</a>
					</td>
					<td nowrap class="TableData"  width="15%;" >客户联系人 ：</td>
					<td class="TableData" width="60%;" >
						<input type="hidden" id="contactUserName" name="contactUserName">
						<select id="contactUserId" name="contactUserId" class="BigSelect easyui-validatebox" >
							<option value="">请选择</option>
						</select>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData" >售后服务类型 ：</td>
					<td class="TableData" >
						<select name="serviceType" id="serviceType"   class="BigSelect easyui-validatebox"  title="类型可在“CRM管理”->CRM编码设置。">
							<option value="">请选择</option>
						</select
					</td>
					<td nowrap class="TableData"  width="">紧急程度：</td>
					<td class="TableData" width="" >
						<select name="emergencyDegree" id="emergencyDegree"   class="BigSelect easyui-validatebox"  title="类型可在“CRM管理”->CRM编码设置。">
							<option value="">请选择</option>
						</select
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData" width="" >录入日期：</td>
					<td class="TableData"  colspan="3">
						<input type="text" name="createTimeStrMin" id="createTimeStrMin" size="" class="BigInput  easyui-validatebox" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'createTimeStrMax\',{d:0});}',dateFmt:'yyyy-MM-dd'})"  value="">
						至
						<input type="text" name="createTimeStrMax" id="createTimeStrMax" size="" class="BigInput  easyui-validatebox" onClick="WdatePicker({minDate:'#F{$dp.$D(\'createTimeStrMin\',{d:0});}',dateFmt:'yyyy-MM-dd'})" value="">
					</td>
				</tr>
				<tr>
				<tr align="center">
					<td nowrap class="TableData" colspan=4>
						<input type="button"  value="查询" class="btn btn-primary" onclick="doSearch();"/>&nbsp;&nbsp;
						<input type="reset" value="重置" class="btn btn-primary" >
					</td>
				</tr>
			</table>
		</div>
		</form>
		
		<div style="text-align:left;display: none;margin-bottom:10px;margin-top:10px;" id="optItem">
			<input type="button"  value="返回" class="btn btn-primary" onclick="toReturn();"/>&nbsp;&nbsp;
		</div>

	</div>
</body>
</html>
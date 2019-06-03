<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>售后服务管理</title>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/core/afterSaleService/js/saleService.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crm.js"></script>
<script type="text/javascript">
function doInit(){
	getInfoList();
}

var datagrid;
function getInfoList(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/crmAfterSaleServController/getManageInfoList.action",
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:1},
			{field:'serviceCode',title:'服务编号',width:50},
			{field:'customerInfoName',title:'客户名称',width:80},
			{field:'handleStatus',title:'是否完成',width:35,formatter:handleStatusFunc},
			{field:'emergencyDegree',title:'紧急程度',width:35},
			{field:'contactUserName',title:'客户联系人',width:40},
			{field:'acceptDatetimeStr',title:'受理时间',width:55,formatter:function(value, rowData, rowIndex){
				return value;
			}},
			{field:'handleDatetimeStr',title:'处理时间',width:55,formatter:function(value, rowData, rowIndex){
				return value;
			}},
			{field:'questionDesc',title:'问题描述',width:60},
			{field:'createUserName',title:'创建人',width:40},
			{field:'createTimeStr',title:'创建时间',width:70},
			{field:'2',title:'操作',width:70,formatter:function(value, rowData, rowIndex){
				var str = "<a href='#' onclick='showInfoFunc("+rowData.sid+")'>详情 </a>";
				str += "&nbsp;&nbsp;<a href='#' onclick='toAddOrUpdate("+rowData.sid+")'>解决问题</a>";
				str += "&nbsp;&nbsp;<a href='#' onclick='deleteSingleFunc("+rowData.sid+")'>删除</a>";
				return str;
			}}
		]]
	});
}

//根据条件查询
function doSearch(){
	var queryParams=tools.formToJson($("#form1"));
	datagrid.datagrid('options').queryParams=queryParams; 
	datagrid.datagrid("reload");
	$('#searchDiv').modal('hide');
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


</script>
</head>
<body onload="doInit()" style="font-size:12px;">
	<table id="datagrid" fit="true" ></table>
	<div id="toolbar">
		<div style="text-align:left;margin-bottom:10px;margin-top:10px;">
			<button class="btn btn-primary" onclick="toAddOrUpdate(0)">新增售后服务信息</button>&nbsp;&nbsp;
			<button class="btn btn-danger" onclick="batchDeleteFunc()">批量删除</button>&nbsp;&nbsp;
			<button type="button" onclick="" class="btn btn-success" data-toggle="modal" data-target="#searchDiv"><i class="glyphicon glyphicon-search"></i>&nbsp;高级检索</button>
			</div>
	</div>
	<form id="form1" name="form1">
		<div class="modal fade" id="searchDiv" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"  >
			<div class="modal-dialog" style="width: 700px" >
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
						<h4 class="modal-title" id="myModalLabel">查询条件</h4>
					</div>
					<div class="modal-body">
						<table align="center" width="95%" class="TableBlock" >
							<tr>
								<td nowrap class="TableData"  width="15%;" >服务编号：</td>
								<td class="TableData"  width="40%;" >
									<input type="text" name="serviceCode" id="serviceCode" size="10" class="BigInput  easyui-validatebox"   maxlength="100">
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
								<td nowrap class="TableData"  >客户名称：</td>
								<td class="TableData"  >
									<input class="BigInput" type="hidden" id = "customerInfoId" name='customerInfoId' />
									<input class="BigInput" type="text" id ="customerInfoName" name='customerInfoName' readonly="readonly" size="10"/>
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
									<input type="text" name="createTimeStrMin" id="createTimeStrMin" size="10" class="BigInput  easyui-validatebox" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'createTimeStrMax\',{d:0});}',dateFmt:'yyyy-MM-dd'})"  value="">
									至
									<input type="text" name="createTimeStrMax" id="createTimeStrMax" size="10" class="BigInput  easyui-validatebox" onClick="WdatePicker({minDate:'#F{$dp.$D(\'createTimeStrMin\',{d:0});}',dateFmt:'yyyy-MM-dd'})" value="">
								</td>
							</tr>
							<tr>
							<tr align="center">
								<td nowrap class="TableData" colspan=4>
									<input type="button"  value="查询" class="btn btn-primary" onclick="doSearch();"/>&nbsp;&nbsp;
									<input type="reset" value="重置" class="btn btn-primary" onclick="$('#customerInfoId').val('');$('#contactUserId').val('');">
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
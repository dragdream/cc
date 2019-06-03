<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人预算查询</title>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@include file="/header/upload.jsp"%>
<%@ include file="/header/ztree.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/budget/userBudget/js/userBudget.js"></script>

<script type="text/javascript">
function doInit(){
	getInfoList();
}

var datagrid;
function getInfoList(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/userBudgetController/getManageInfoList.action",
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
			{field:'YEAR',title:'目标年度',width:40,sortable:true},
			{field:'USERNAME',title:'人员名称',width:60,sortable:true},
			{field:'AMOUNT',title:'费用金额',width:25,sortable:true},
			{field:'managePersonName',title:'创建人',width:25,sortable:true},
			{field:'2',title:'操作',width:50,align:'center',formatter:function(value, rowData, rowIndex){
				var str = "<a href='#' onclick='showQueryInfoFunc("+rowData.USERID + "," + rowData.YEAR +")'>详情 </a>";
				return str;
			}}
		]]
	});
}

function checkForm(){
	var check = $("#form1").form('validate'); 
	if(!check){
		return false; 
	}
	return true;
}

//根据条件查询
function doSearch(){
	if(checkForm()){
		var queryParams=tools.formToJson($("#form1"));
		datagrid.datagrid('options').queryParams=queryParams; 
		datagrid.datagrid("reload");
		$('#searchDiv').modal('hide');
	}
}


</script>
</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px">
	<table id="datagrid" fit="true" ></table>
	<div id="toolbar">
		<div class="base_layout_top" style="position:static">
			<span class="easyui_h1">个人费用计划</span>
		</div>
		
		<div style="padding:10px">
			<button class="btn btn-default" data-toggle="modal" data-target="#searchDiv"><i class="glyphicon glyphicon-zoom-in"></i>&nbsp;高级查询</button>
		</div>
		<form id="form1" name="form1">
		<div class="modal fade" id="searchDiv" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
						<h4 class="modal-title" id="myModalLabel">查询条件</h4>
					</div>
					<div class="modal-body">
						<table class="none-table">
							<tr>
								<td class="TableData" >人员名称：</td>
								<td class="TableData">
									<input type=hidden name="userId" id="userId" value="">
									<input  name="userName" id="userName" class="BigStatic BigInput" size="10"  readonly value=""></input>
									<span>
										<a href="javascript:void(0);" class="orgAdd" onClick="selectSingleUser(['userId', 'userName']);">添加</a>
										<a href="javascript:void(0);" class="orgClear" onClick="$('#userId').val('');$('#userName').val('');">清空</a>
									</span>
								</td>
							</tr>
							<tr>
								<td class="TableData">目标年度：</td>
								<td class="TableData"  >
									<select id="year" name="year" class="BigSelect easyui-validatebox" >
										<option value="">请选择</option>
										<%
											int currentYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
											int counter = currentYear + 30;
											int defaultYear = 2000;
											for(int i=defaultYear;i<=counter;i++){
										%>
										<option value="<%=i %>"  <%=currentYear==i?"selected":"" %>><%=i %></option>
										<%
											}
										%>
										
									</select>
								</td>
							</tr>
							<tr>
								<td class="TableData">费用金额大于：</td>
								<td class="TableData">
									<input type='text' class="BigInput  easyui-validatebox" id='amountMax' name='amountMax' style="width:180px;" validType='pointTwoNumber[]' maxlength="9" />
								</td>
							</tr>
							<tr>
								<td class="TableData">费用金额小于：</td>
								<td class="TableData">
									<input type='text' class="BigInput  easyui-validatebox" id='amountMin' name='amountMin' style="width:180px;" validType='pointTwoNumber[]' maxlength="9" />
								</td>
							</tr>
							<tr>
								<td colspan='4'>
									<input type="reset" class="btn btn-default" onClick="$('#userId').val('');$('#userName').val('');" value="清空">
									<input type="button" class="btn btn-primary" onclick="doSearch();" value="查询">
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</form>

	</div>
</body>
</html>
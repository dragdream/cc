<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	TeePerson loginPerson = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
	boolean isAdmin = TeePersonService.checkIsAdminPriv(loginPerson);

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>申请预算管理</title>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/budget/regBudget/js/regBudget.js"></script>

<script type="text/javascript">
var isAdmin = <%=isAdmin%>;
function doInit(){
	getInfoList();
	//getDeptList();
	getPersonDeptList('<%=loginPerson.getUuid()%>','opDeptId');
}

var datagrid;
function getInfoList(){
	//获取当前人预算信息
	var json = tools.requestJsonRs(contextPath+"/budgetRegController/getCurRegUserInfoAbout.action");
	var data = json.rtData;
	//渲染个人信息
	var render = [];
	render.push("预算金额：<b>个人</b>："+data["personalRemain"]+"元");
	
	for(var key in data){
		if(key!="personalRemain"){
			render.push("<b>"+key+"</b>："+data[key]+"元");
		}
	}
	
	$("#infos").html(render.join("；"));
	
	
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/budgetRegController/getManageInfoList.action",
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		idField:'uuid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
		columns:[[
			{field:'uuid',checkbox:true,title:'ID',width:10,hidden:true},
			{field:'regType',title:'申请类型',width:10,formatter:regTypeNameFunc},
			{field:'type',title:'记录类型',width:10,formatter:typeFunc},
			{field:'opUserName',title:'申请人名称',width:20},
			{field:'opDeptName',title:'部门名称',width:20},
			{field:'amount',title:'申请金额',width:15},
			{field:'crTimeDesc',title:'申请时间',width:20},
			{field:'reasonDesc',title:'申请原由',width:20},
			{field:'2',title:'操作',width:30,align:'center',formatter:function(value, rowData, rowIndex){
				var str = "<a href='#' onclick=\"showManageInfoFunc('"+ rowData.uuid +"')\">详情 </a>";
				if(isAdmin){
					str += "&nbsp;&nbsp;<a href='#' onclick=\"toAddOrUpdate('"+ rowData.uuid +"')\">编辑</a>";
					str += "&nbsp;&nbsp;<a href='#' onclick=\"deleteSingleFunc('"+rowData.uuid+"')\">删除</a>";
				}
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
			<span class="easyui_h1">费用申请</span>
		</div>
		<div style="padding:10px">
			<button class="btn btn-info" onclick="toAddOrUpdate('')">费用申请</button>&nbsp;
<!-- 			<button class="btn btn-danger" onclick="batchDeleteFunc()" style="display:" >批量删除</button>&nbsp;&nbsp; -->
			<button class="btn btn-default" data-toggle="modal" data-target="#searchDiv"><i class="glyphicon glyphicon-zoom-in"></i>&nbsp;高级查询</button>
			&nbsp;
			<span id="infos"></span>
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
						<table class="TableBlock" width=90%"  style="text-align:left;" align="center">
							<tr >
								<td class="TableData"  width="30%">申请类型：</td>
								<td class="TableData">
									<input type="radio" name="regType" id="regType1" size="" checked="checked" value="1" onclick="regTypeFunc(this.value);"> 
									<label for="regType1">个人预算</label>&nbsp;&nbsp;
									<input type="radio" name="regType" id="regType2" size="" value="2" onclick="regTypeFunc(this.value);"> 
									<label for="regType2">部门预算</label>&nbsp;&nbsp;
									<input type="radio" name="regType" id="regType3" size="" value="" onclick=""> 
									<label for="regType3">全部预算</label>&nbsp;&nbsp;
								</td>
							</tr>
							<tr>
								<td class="TableData" >申请类型：</td>
								<td class="TableData">
									<input type="radio" name="type" id="type1" size="" checked="checked"  value="1" > 
									<label for="type1">预算申请</label>&nbsp;&nbsp;
									<input type="radio" name="type" id="type2" size="" value="2" > 
									<label for="type2">报销</label>&nbsp;&nbsp;
								</td>
							</tr>
							<tr id="personTr">
								<td class="TableData" >人员名称：</td>
								<td class="TableData">
									<%=isAdmin == true? "":loginPerson.getUserName() %>
									<span style="<%=!isAdmin?"display:none":"" %>">
										<input type=hidden name="opUserId" id="opUserId" value="<%=loginPerson.getUuid()%>">
										<input  name="opUserName" id="opUserName" class="BigStatic BigInput" size="20"  readonly value="<%=loginPerson.getUserName()%>"></input>
										<span >
											<a href="javascript:void(0);" class="orgAdd" onClick="selectSingleUser(['opUserId', 'opUserName']);">添加</a>
											<a href="javascript:void(0);" class="orgClear" onClick="$('#opUserId').val('');$('#opUserName').val('');">清空</a>
										</span>
									</span>
								</td>
							</tr>
							<tr id="deptTr" style="display: none;">
								<td class="TableData" >单位部门：</td>
								<td class="TableData">
									<select id="opDeptId" name="opDeptId" class="BigSelect easyui-validatebox">
										<option value="">请选择</option>
										<option value="<%=loginPerson.getDept().getUuid()%>"><%=loginPerson.getDept().getDeptName() %></option>
									</select>
								</td>
							</tr>
							<tr>
								<td class="TableData">费用金额大于：</td>
								<td class="TableData">
									<input type='text' class="BigInput  easyui-validatebox" validType='pointTwoNumber[]' id='amountMax' name='amountMax' style="width:180px;" maxlength="9" />
								</td>
							</tr>
							<tr>
								<td class="TableData">费用金额小于：</td>
								<td class="TableData">
									<input type='text' class="BigInput  easyui-validatebox" validType='pointTwoNumber[]' id='amountMin' name='amountMin' style="width:180px;" maxlength="9" />
								</td>
							</tr>
							<tr>
								<td colspan='4' align='center'>
									<input type="button" onClick="reSetFunc();"  class="btn btn-primary" value="清空">
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
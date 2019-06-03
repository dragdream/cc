<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%
 	String callBackFunc = request.getParameter("callBackFunc") == null ? "" : request.getParameter("callBackFunc")  ;
    callBackFunc = callBackFunc.replace("\"", "\\\"");
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crm.js"></script>
<script>
var callBackFunc = "<%=callBackFunc%>";
//var parentWindowObj = window.dialogArguments;
var xparent;
var datagrid;
var userForm;
var to_id_field ;
var to_name_field ;
function doInit(){
 	var contactsArray = xparent["contactsArray"];
	var roleCntrl = contactsArray[0];
    var roleDescCntrl = contactsArray[1];
    to_id_field = xparent.document.getElementById(roleCntrl);
    to_name_field = xparent.document.getElementById(roleDescCntrl);
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/TeeCrmContactsController/datagrid.action?type=0',
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'2',title:'操作',formatter:function(e,rowData){
				return "<a href='javascript:void(0);' onclick='selectContacts("+rowData.sid+",\""+rowData.contactName+"\")'>选择</a>";
			}},
			{field:'addPersonName',title:'创建人',width:100},
			{field:'customerName',title:'客户名称',width:100},
			{field:'contactName',title:'姓名',width:100},
			{field:'department',title:'部门',width:100},
			{field:'mobilePhone',title:'移动电话',width:100},
			{field:'isKeyPerson',title:'关键决策人',width:100},
			{field:'email',title:'邮箱',width:100},
			{field:'managePersonName',title:'负责人',width:100},
			{field:'createTimeDesc',title:'创建时间',width:100},
		]],
	});
}

function selectContacts(sid,customerName){
	var contactsArray = xparent["contactsArray"];
	to_id_field.value=sid;
	to_name_field.value=customerName;
	arguments.length = 2;//参数调用
	arguments[0] = sid;
	arguments[1] = customerName;
	trigger_callback(callBackFunc,arguments); 
	CloseWindow();
}
function showDetail(sid){
	datagrid.datagrid("unselectAll");
	bsWindow(contextPath+"/system/subsys/crm/core/contactUser/detail.jsp?sid="+sid,"查看详情",{width:"700",height:"300",buttons:[{name:"关闭",classStyle:"btn btn-primary"}],submit:function(v,h){return true;}});
}
function add(){
	var url = contextPath+"/system/subsys/crm/core/contactUser/addOrEditContactUser.jsp";
	location.href=url;
}

function edit(sid){
	datagrid.datagrid("unselectAll");
	var url = contextPath+"/system/subsys/crm/core/contactUser/addOrEditContactUser.jsp?sid="+sid;
	location.href=url;
}

function del(){
	var selections = datagrid.datagrid("getSelections");
	if(selections==null || selections==undefined || selections.length<=0){
		$.jBox.tip("请选择需要删除项目","info");
		return;
	}
	$.jBox.confirm("确认删除选中信息吗","确认",function(v){
		if(v=="ok"){
			var sids="";
			for(var i=0;i<selections.length;i++){
			   sids += selections[i].sid+",";
			}
			var url = contextPath+"/TeeCrmContactUserController/deleteById.action";
			var json = tools.requestJsonRs(url,{sids:sids});
			if(json.rtState){
				$.jBox.tip(json.rtMsg,"success");
			}else{
				$.jBox.tip("删除失败！","error");
			}
			datagrid.datagrid("unselectAll");
			datagrid.datagrid("reload");
		}
	});
}
//根据条件查询
function doSearch(){
	  var queryParams=tools.formToJson($("#form1"));
	  datagrid.datagrid('options').queryParams=queryParams; 
	  datagrid.datagrid("reload");
	  $('#searchDiv').modal('hide');
}

function exportContactUser(){
	var url = contextPath+"/TeeCrmContactUserController/exportContactUser.action";
	document.form1.action=url;
	document.form1.submit();
	datagrid.datagrid("reload");
	$('#searchDiv').modal('hide');
	return true;
}
</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px;padding: 10px;">
	<table id="datagrid" fit="true"></table>
<!-- 	<div id="toolbar">
		<div style="text-align:left;margin-bottom:10px;margin-top: 10px;">
			<button class="btn btn-primary" onclick="add()">添加</button>
			<button class="btn btn-danger" onclick="del()">删除</button>
			<button class="btn btn-primary" data-toggle="modal" data-target="#searchDiv"><i class="glyphicon glyphicon-zoom-in"></i>&nbsp;高级查询</button>
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
		        <table class="SearchTable" style="text-align:left;">
								<tr>
									<td class="SearchTableTitle">所属客户：</td>
									<td colspan='3'>
										<input class="BigInput" type="text" id = "customerName" name='customerName' style="width:180px;"/>
										<input class="BigInput" type="hidden" id = "customerId" name='customerId' style="width:180px;"/>
										<a href="javascript:void(0);" class="orgAdd" onClick="selectCustomerInfo(['customerId','customerName'])">选择客户</a></a>&nbsp;&nbsp;
										<a href="javascript:void(0);" class="orgClear" onClick="clearData('customerId','customerName')">清空</a>
									</td>
								</tr>
								<tr>
									<td class="SearchTableTitle">姓名：</td>
									<td>
										<input type='text' class="BigInput" id='name' name='name' style="width:180px;">
									</td>
									<td class="SearchTableTitle">所属部门：</td>
									<td>
										<input type='text' class="BigInput" id='department' name='department' style="width:180px;"/>
									</td>
								</tr>
								<tr>
									<td class="SearchTableTitle">公司电话：</td>
									<td>
										<input type='text' class="BigInput" id='telephone' name='telephone' style="width:180px;"/>
									</td>
									<td class="SearchTableTitle"> 移动电话：</td>
									<td>
										<input type='text' class="BigInput" id='mobilePhone' name='mobilePhone' style="width:180px;"/>
									</td>
								</tr>
								<tr>
									<td colspan='4' align='right'>
										<input type="reset" class="btn btn-primary" value="清空" onClick="clearData('customerId','customerName');">
										<input type="button" class="btn btn-primary" onclick="doSearch();" value="查询">
										<input type="button" class="btn btn-primary" onclick="exportContactUser();" value="导出">
									</td>
								</tr>
							</table>
		      </div>
		    </div>
		  </div>
		</div>
		</form>
	</div> -->
</body>
</html>
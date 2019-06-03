<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%
	String flowTypeId = request.getParameter("flowTypeId") == null? "0": request.getParameter("flowTypeId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp"%>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>流程权限规则管理</title>
<script type="text/javascript" charset="UTF-8">
	var flowTypeId = '<%=flowTypeId%>';
	var datagrid;
	var userDialog;
	var userForm;
	var passwordInput;
	var userRoleDialog;
	var userRoleForm;
	var title ="";
	$(function() {
		userForm = $('#Form1').form();
	
		datagrid = $('#datagrid').datagrid({
			url : contextPath + '/flowPrivManage/getFlowPrivList.action?sort=sid&flowTypeId=' + flowTypeId,
			toolbar : '#toolbar',
			//iconCls : 'icon-save',
			iconCls:'',
			pagination : true,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
			fit : true,
			fitColumns : false,
			nowrap : false,
			border : false,
			idField : 'sid',
			singleSelect:false,

			frozenColumns : [ [
			     {field:'sid',checkbox:true},{
				field : 'privType',
				title : '权限类型',
				width : 80,
				sortable : true,
				formatter : function(value, rowData, rowIndex) {
					if(value == 1){
						return "管理";
					}else if(value == 2){
						return  "监控";
					}else if(value == 3){
						return "查询";
					}else if(value == 4){
						return "编辑";
					}else if(value == 5){
						return "点评";
					}
					return value;
				}
			} ,{
				field : 'privUsersName',
				title : '授权范围',
				width : 280,
				formatter : function(value, rowData, rowIndex) {
					var privDesc = "";
				
					if(value && value != ''){
						privDesc = "<b>用户</b>:" + value;
					}
					if(rowData.privDeptsName && rowData.privDeptsName != ''){
						if(privDesc != ''){
							privDesc = privDesc + "<br><b>部门</b>:" + rowData.privDeptsName;
						}

					}
					
					if(rowData.privRolesName && rowData.privRolesName != ''){
						if(privDesc != ''){
							privDesc = privDesc + "<br><b>角色</b>:" + rowData.privRolesName;
						}
						
					}
					return privDesc;
				}
			 },{
					field : 'privDeptsName',
					title : '授权范围(部门)',
					width : 1,
					hidden:true

			},{
					field : 'privRolesName',
					title : '授权范围(角色)',
					width : 1,
					hidden:true
			 },{
					field : 'deptPostName',
					title : '授权范围(角色)',
					width : 1,
					hidden:true
			 },{
					field : 'privScope',
					title : '管理范围',
					width : 260,
					formatter : function(value, rowData, rowIndex) {
						if(value == 0){
							return "所有部门";
						}else if(value == 1){
							return  "本部门，包含所有下级部门";
						}else if(value == 2){
							return "本部门";
						}else if(value == 3){//自定义部门
							var privDesc = "";
							if(rowData.deptPostName && rowData.deptPostName != ''){
								privDesc = privDesc + "<b>部门</b>:" + rowData.deptPostName;

							}
							return privDesc;
						}
						return value;
					}
				},	
				{
					field : '_optmanage',
					title : '操作',
					width : 200,
					formatter : function(value, rowData, rowIndex) {
						    return "<a href='javascript:void(0);' onclick='toAddUpdate(\"" +rowData.sid + "\");'> 编辑 </a>";
					}
				}
			] ]
		   ,onLoadSuccess:onLoadSuccessFunc
		});
		
		
	});
	/**
	 * 获取最大记录数
	 */
	function onLoadSuccessFunc(){
		var data=$('#datagrid').datagrid('getData');
		$("#totalPerson").empty();
	}

	/**
	 *打开增加 编辑窗口
	 */
	function append() {
		userForm.form('clear');
		userDialog.dialog('open');
	}
	
function toAddUpdate(sid){
	
	var url = "<%=contextPath%>/system/core/workflow/workmanage/flowPriv/addupdate.jsp?flowTypeId=" + flowTypeId + "&sid=" + sid;
	window.location.href = url;
}
	/**
	 * 批量删除人员
	 */
	function deletePerson() {

		var selections = $('#datagrid').datagrid('getSelections');
		if (selections.length == 0) {
			$.jBox.tip("至少选择一项","error");
			return;
		}

		top.$.jBox.confirm("确定要删除所选中权限类型？","提示",function(v){
			if(v!="ok") return;

			var ids = "";
			for ( var i = 0; i < selections.length; i++) {
				ids += selections[i].sid;
				if (i != selections.length - 1) {
					ids += ",";
				}
			}
			var url = contextPath
					+ "/flowPrivManage/DelByIds.action?ids="
					+ ids;
			var para = {};
			var jsonRs = tools.requestJsonRs(url, para);
			if (jsonRs.rtState) {
				$.jBox.tip(jsonRs.rtMsg,"info");
				$('#datagrid').datagrid('reload');
			} else {
				$.jBox.tip(jsonRs.rtMsg,"error");
			}
		});
	}
</script>
</head>
<body fit="true">
<div id="toolbar" style="padding:5px;">
	<div class="moduleHeader">
		<b>流程权限管理</b>
	</div>
	<button class="btn btn-primary" onclick="toAddUpdate('0')">添加</button>
	<button class="btn btn-danger" onclick="deletePerson()">删除所选记录</button>
</div>
<table id="datagrid"></table>
</body>
</html>
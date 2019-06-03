<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>合同提醒</title>
	<script type="text/javascript" charset="UTF-8">
	$(function() {
		$('#datagrid').datagrid({
			url:contextPath+'/contractRemind/datagrid.action',
			pagination:true,
			singleSelect:true,
			toolbar:'#toolbar',//工具条对象
			border:false,
			idField:'sid',//主键列
			fitColumns : false,
			sortOrder: 'desc',
			striped: true,
			remoteSort: false,
			columns:[[
				{field:'remindContent',title:'提醒内容',width:100},
				{field:'remindTimeDesc',title:'提醒时间',width:200},
				{field:'toUserNames',title:'提醒人员',width:200},
				{field:'contractName',title:'合同名称',width:200},
				{field:'_manage',title:'操作',formatter:function(value,rowData,rowIndex){
					var render = [];
					render.push("<a href='javascript:void(0)' onclick='del("+rowData.sid+")'>删除</a>");
					return render.join("&nbsp;&nbsp;");
				}}
			]]
		});
	});
	
	function edit(sid){
		window.location = "addOrUpdate.jsp?sid="+sid;
	}
	
	function del(sid){
		if(window.confirm("是否删除该合同提醒？")){
			var json = tools.requestJsonRs(contextPath+"/contractRemind/delete.action?sid="+sid,{});
			window.location.reload();
		}
	}
	
	function query(type){
		var para = {type:type};
		$('#datagrid').datagrid("reload",para);
		$('#datagrid').datagrid("unselectAll");
	}
	</script>
</head>
<body fit="true">
<table id="datagrid" fit="true"></table>
<div id="toolbar">
	<div class="base_layout_top" style="position:static">
		<table width="100%">
			<tr>
				<td>
					<span class="easyui_h1">合同提醒</span>
				</td>
				<td align=right>
					<div class="btn-group" data-toggle="buttons">
					  <label class="btn btn-default active" onclick="query(1)">
					    <input type="radio" name="options" id="option1">本月提醒
					  </label>
					  <label class="btn btn-default" onclick="query(2)">
					    <input type="radio" name="options" id="option2">下月提醒
					  </label>
					  <label class="btn btn-default" onclick="query(3)">
					    <input type="radio" name="options" id="option3">全部提醒
					  </label>
					</div>
				</td>
			</tr>
		</table>
	</div>
	<br/>
</div>
</body>
</html>
<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/header/header.jsp" %>
	<%@ include file="/header/easyui.jsp" %>
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	<title></title>
	<script type="text/javascript">
	var datagrid;
	function doInit(){
		datagrid = $('#datagrid').datagrid({
			url:contextPath+'/demo/query.action',
			pagination:true,
			singleSelect:true,
			toolbar:'#toolbar',//工具条对象
			checkbox:false,
			border:false,
			pageSize : 30,
			pageList: [30,60,70,80,90,100],
			fitColumns:true,//列是否进行自动宽度适应
			remoteSort:true,
			columns:[[
				{field:'userName',title:'姓名',sortable:true},
				{field:'passWord',title:'密码'},
				{field:'age',title:'年龄'},
				{field:'gender',title:'性别'},
				{field:'deptName',title:'所属部门'},
				{field:'_oper',title:'操作',formatter:function(data,rows,index){
					var render = ["<a href='#' onclick=\"edit("+rows.sid+")\">编辑</a>","<a href='#' onclick=\"del("+rows.sid+")\">删除</a>"];
					return render.join("&nbsp;&nbsp;");
				}}
			]]
		});
	}
	
	
	function edit(sid){
		window.location = "edit.jsp?sid="+sid;
		
	}
	
	function del(sid){
		if(window.confirm("是否删除该数据？")){
			var json = tools.requestJsonRs(contextPath+"/demo/delete.action?sid="+sid);
			if(json.rtState){
				$('#datagrid').datagrid("reload");
			}
		}
	}
	
	function doSearch(){
		var queryParam = {userName:$("#userName").val(),age:$("#age").val(),sid:0};
		$('#datagrid').datagrid("reload",queryParam);
	}
	
	</script>
</head>
<body onload="doInit()">
<table id="datagrid" fit="true"></table>
<div id="toolbar">
	<div class="base_layout_top" style="position:static">
		<table width="100%">
			<tr>
				<td>
					<span class="easyui_h1"><i class="glyphicon glyphicon-sound-stereo"></i>&nbsp;测试</span>
				</td>
				<td align=right>
					<button class="btn btn-primary" onclick="window.location='add.jsp'">添加</button>&nbsp;
				</td>
			</tr>
		</table>
	</div>
	<br/>
	用户名：<input type="text" id="userName" name="userName" />
	&nbsp;
	年龄：<input type="text" id="age" name="age" value="0"/>
	&nbsp;
	<input type="button" value="检索" onclick="doSearch()" />
</div>
</body>
</html>

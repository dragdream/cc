<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>库存盘点</title>
	<script type="text/javascript" charset="UTF-8">
	$(function() {
		$('#datagrid').datagrid({
			url:contextPath+'/deposCheckController/listCheckRecord.action',
			singleSelect:true,
			toolbar:'#toolbar',//工具条对象
			border:false,
			idField:'sid',//主键列
			fitColumns:true,
			striped: true,
			remoteSort: true,
			columns:[[
				{field:'title',title:'盘点名称',width:100},
				{field:'checkTime',title:'盘点日期',width:100},
				{field:'checkUser',title:'盘点人',width:100},
				{field:'deposName',title:'目标仓库',width:100},
				{field:'_oper',title:'操作',width:200,formatter:function(data,row,index){
					var render = [];
					if(row.flag==1){//可进行盘点
						render.push("<a href='#' onclick='check("+row.sid+")'>盘点</a>");
					}else{//锁定，只可查看
						render.push("<a href='#' onclick='view("+row.sid+")'>查看</a>");
					}
					render.push("<a href='javascript:void(0)' onclick='del("+row.sid+")'>删除</a>");
					return render.join("&nbsp;&nbsp;");
				}}
			]]
		});
	});
	
	function create(){
		window.location = "addCheckRecord.jsp";
	}
	
	
	function del(sid){
		if(window.confirm("确认要删除该盘点记录吗？")){
			var json = tools.requestJsonRs(contextPath+"/deposCheckController/delCheckRecord.action",{sid:sid});
			window.location.reload();
		}
	}
	
	function check(sid){
		window.location = "check_item_list.jsp?sid="+sid;
	}
	
	function view(sid){
		window.location = "check_item_list_view.jsp?sid="+sid;
	}
	</script>
</head>
<body style="overflow:hidden">
<div id="toolbar">
	<div class="base_layout_top" style="position:static">
		<span class="easyui_h1">库存盘点</span>
	</div>
	<div style="padding:10px;">
		<button class="btn btn-primary" onclick="create()">新建盘库记录</button>
	</div>
</div>
<table id="datagrid" fit="true"></table>
</body>
</html>
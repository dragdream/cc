<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<%@ include file="/header/header.jsp" %>
	<%@ include file="/header/easyui.jsp" %>
	<%
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
	%>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>我的计划</title>
	<script>
	var runId = <%=runId%>;
	function doInit(){
		initDataGrid();
	}

	function initDataGrid(){
		$('#datagrid').datagrid({
			url:contextPath+'/runRel/listSchedule.action',
			pagination:true,
			singleSelect:false,
			toolbar:'#toolbar',//工具条对象
			checkbox:true,
			border:false,
			idField:'uuid',//主键列
			fitColumns : false,
			sortOrder: 'desc',
			striped: true,
			pageList:[50,60,70,80,90,100,120,140,160],
			columns:[[
				{field:'uuid',checkbox:true},
				{field:'title',title:'计划名称'}
			]]
		});
	}
	
	function query(){
		$('#datagrid').datagrid("reload",{title:$("#title").val()});
	}
	
	function add(){
		var selections = $('#datagrid').datagrid('getSelections');
		if(selections.length==0){
			alert("至少选择一项");
			return ;
		}
		var arr = [];
		for(var i=0;i<selections.length;i++){
			arr.push(selections[i].uuid);
		}
		
		var json = tools.requestJsonRs(contextPath+"/runRel/addScheduleRel.action",{ids:arr.join(","),runId:runId});
		window.dialogArguments.$("#datagrid").datagrid("reload");
		CloseWindow();
	}

	</script>
</head>
<body onload="doInit()">
	<table id="datagrid" fit="true"></table>
	
	<!-- 声明工具条 -->
	<div id="toolbar" style="padding:5px">
		<div class="moduleHeader">
			<b><i class="glyphicon glyphicon-briefcase"></i>&nbsp;我的计划</b>
		</div>
		计划名称：<input type="text" class="BigInput" id="title"/>
		&nbsp;&nbsp;<button class="btn btn-default" onclick="query()">查询</button>
		&nbsp;&nbsp;<button class="btn btn-primary" onclick="add()">添加</button>
		
	</div>
</body>

</html>
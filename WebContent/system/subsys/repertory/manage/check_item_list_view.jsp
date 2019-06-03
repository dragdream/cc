<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%
	String recordSid = request.getParameter("sid");
%>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>库存盘点详情</title>
	<script type="text/javascript" charset="UTF-8">
	$(function() {
		search();
	});
	
	function search(){
		$('#datagrid').datagrid({
			url:contextPath+'/deposCheckController/listCheckItem.action?recordSid=<%=recordSid%>&rows=1000000',
			singleSelect:true,
			toolbar:'#toolbar',//工具条对象
			border:false,
			idField:'sid',//主键列
			fitColumns:true,
			queryParams:{words:$("#words").val()},
			striped: true,
			remoteSort: true,
			columns:[[
				{field:'proNo',title:'编号',width:100},
				{field:'proName',title:'品名',width:100},
				{field:'type',title:'分类',width:100},
				{field:'proModel',title:'规格',width:100},
				{field:'units',title:'单位',width:100},
				{field:'originalCount',title:'盘点前数量',width:100},
				{field:'manualCount',title:'盘点后数量',width:100,formatter:function(data,row,index){
					var render = [];
					if(row.manualCount>row.originalCount){
						render.push("<span style='color:green'>"+data+"&nbsp;↑</span>");
					}else if(row.manualCount<row.originalCount){
						render.push("<span style='color:red'>"+data+"&nbsp;↓</span>");
					}
					return render.join("");
				}},
				{field:'delta',title:'差额',width:100,formatter:function(data,row,index){
					return row.manualCount-row.originalCount;
				}}
			]]
		});
	}
	
	</script>
</head>
<body style="overflow:hidden">
<div id="toolbar">
	<div class="base_layout_top" style="position:static">
		<span class="easyui_h1">库存盘点详情</span>
	</div>
	<div style="padding:10px">
		<button class="btn btn-default" onclick="window.location='check_record_list.jsp'">返回</button>
		&nbsp;&nbsp;&nbsp;&nbsp;
		产品编号/名称/分类查询：
		<input type="text" class="BigInput" id="words" name="words" style="width:230px" placeholder="请输入产品编号/产品名称/分类"/>
		<button class="btn btn-info" onclick="search()">查询</button>
	</div>
</div>
<table id="datagrid" fit="true"></table>
</body>
</html>
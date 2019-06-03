<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%
	String proId = request.getParameter("proId");
	String reposId = request.getParameter("reposId");
	String time1 = request.getParameter("time1");
	String time2 = request.getParameter("time2");
%>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>库存详细</title>
	<script type="text/javascript" charset="UTF-8">
	$(function() {
		var year = new Date().getFullYear();
		var month = new Date().getMonth()+1;
		var firstdate = year + '-' + (month<10?"0"+month:month) + '-01';  
        var day = new Date(year,month,0);   
        var lastdate = year + '-' + (month<10?"0"+month:month) + '-' + day.getDate();
		var startTime = firstdate;
		var endTime = lastdate;
		
		$("#time2").val(endTime);
		refresh();
		
	});
	
	function refresh(){
		var para = {time1:$("#time1").val(),time2:$("#time2").val(),proId:<%=proId%>,deposId:<%=reposId%>};
		
		$('#datagrid').datagrid({
			url:contextPath+'/repDepository/detailList.action',
			singleSelect:true,
			toolbar:'#toolbar',//工具条对象
			queryParams:para,
			border:false,
			idField:'sid',//主键列
			fitColumns:false,
			striped: true,
			remoteSort: true,
			view:groupview,
			groupField:"type",
			groupFormatter:function(value,rows){
				
				return (value==1?"入库记录":"出库记录") + ' 共' + rows.length + '项';
			},
			columns:[[
				{field:'billNo',title:'单据号',width:200,formatter:function(data,rowData){
					return "<a href='javascript:void(0)' onclick='view("+rowData.runId+")'>"+data+"</a>";
				}},
				{field:'type',title:'类型',width:100,formatter:function(data){
					return data==1?"入库":"出库";
				}},
				{field:'time',title:'出入库时间',width:100,formatter:function(data){
					return getFormatDateStr(data,"yyyy-MM-dd");
				}},
// 				{field:'proName',title:'执行人',width:100},
				{field:'proName',title:'商品名称',width:100},
				{field:'count',title:'数量',width:100},
				{field:'price',title:'单价',width:100},
				{field:'sum',title:'总金额',width:100}
			]]
		});
	}
	
	function view(runId){
		openFullWindow(contextPath+"/system/core/workflow/flowrun/print/index.jsp?runId="+runId+"&view=1");
	}
	</script>
</head>
<body style="overflow:hidden">
<div id="toolbar">
	<div class="base_layout_top" style="position:static">
		<span class="easyui_h1">库存详细</span>
	</div>
	<div style="padding:10px">
		<button class="btn btn-default" onclick="window.location='list.jsp?time1=<%=time1%>&time2=<%=time2%>&deposId=<%=reposId%>'">返回</button>
		&nbsp;&nbsp;
		统计时段：
		<input type="text" class="BigInput Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="time1" name="time1"/>
		&nbsp;&nbsp;
		<input type="text" class="BigInput Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="time2" name="time2"/>
		&nbsp;&nbsp;
		<button class="btn btn-info" onclick="refresh()">查询</button>
	</div>
</div>
<table id="datagrid" fit="true"></table>
</body>
</html>
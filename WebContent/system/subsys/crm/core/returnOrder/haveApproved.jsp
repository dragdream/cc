<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>已确认退货单列表</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crm.js"></script>
<script>
var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/TeeCrmReturnOrderController/datagrid.action?returnOrderStatus=2',  //退货单列表
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		checkbox:false,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
					{field:'sid',checkbox:true,title:'ID',width:100},
					{field:'returnOrderNo',title:'退货单号',width:80,formatter:function(value,rowData,rowIndex){
						return "<a href=\"#\" onclick=\"showDetail("+rowData.sid+",'"+rowData.customerName+"')\">"+rowData.returnOrderNo+"</a>";
					}   
					},
					{field:'customerName',title:'客户名称',width:80},
					{field:'orderNo',title:'订单编号',width:80},
					{field:'returnTimeDesc',title:'退单日期 ',align: 'center',width:120},
					{field:'returnOrderStatusDesc',title:'退货单状态',align: 'center',width:80},
					{field:'returnReason',title:'退货原因',width:80},
					{field:'managePersonName',title:'负责人',align: 'center',width:60},
					{field:'createTimeDesc',title:'创建时间 ',align: 'center',width:120},
					{field:'addPersonName',title:'创建人',align: 'center',width:100},
					{field:'2',title:'操作',align: 'center',width:80,formatter:function(value,rowData,rowIndex){
						return "<a href=\"#\" onclick=\"showDetail("+rowData.sid+",'"+rowData.customerName+"')\">详情</a>&nbsp;&nbsp;";
					}},
				]]
	});
	
	
}

//查看订单详情
function showDetail(sid,customerName){
	var url = contextPath+"/system/subsys/crm/core/returnOrder/approveReturnOrderDetial.jsp?sid="+sid+"&customerName="+customerName;
	openFullWindow(url);
	//location.href=url;
}



</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px;padding-left: 10px;padding-right: 10px;">
	<table id="datagrid" fit="true"></table>
	<div class="topbar clearfix" id="toolbar">
</div>
 
</body>
</html>
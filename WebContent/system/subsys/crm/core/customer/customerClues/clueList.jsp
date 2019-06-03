<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%
    String customerId = request.getParameter("customerId")==null?"0":request.getParameter("customerId");
    String customerName = TeeStringUtil.getString(request.getParameter("customerName"), null);
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>线索列表</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crm.js"></script>
<script>
var datagrid;
var cusId = "<%=customerId%>";
var customerId = "<%=customerId%>";
var customerName = "<%=customerName%>";
function doInit(){
	getCrmCodeByParentCodeNo("CLUE_SOURCE","clueSource"); //线索来源
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/TeeCrmClueController/datagrid.action?cusId='+cusId,  //线索列表
		pagination:true,
		//singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'name',title:'姓名',width:80,formatter:function(value,rowData,rowIndex){
				return "<a href=\"#\" onclick=\"showDetail("+rowData.sid+",'"+rowData.name+"')\">"+rowData.name+"</a>";
			}   
			},
			{field:'companyName',title:'公司',width:80},
			{field:'managePersonName',title:'负责人',align: 'center',width:100},
			{field:'clueSourceDesc',title:'来源',align: 'center',width:100},
			{field:'telephone',title:'电话',align: 'center',width:80},
			{field:'culeDetail',title:'销售线索详情',align: 'center',width:120},
			{field:'clueStatusDesc',title:'状态',align: 'center',width:60},
			{field:'addPersonName',title:'创建人',align: 'center',width:100},
			{field:'createTimeDesc',title:'创建时间',align: 'center',width:120},
			{field:'2',title:'操作',align: 'center',width:100,formatter:function(value,rowData,rowIndex){
				return "<a href=\"#\" onclick=\"showDetail("+rowData.sid+")\">查看</a>&nbsp;";
			}},
		]]
	});
}

//查看线索详情
function showDetail(sid){
	var url = contextPath+"/system/subsys/crm/core/customer/customerClues/clueInfo.jsp?sid="+sid+"&customerId="+cusId;
	location.href=url;
}

</script>

</head>
<body onload="doInit();" style="overflow:hidden;font-size:12px;padding-left: 10px;padding-right: 10px;">
	<table id="datagrid" fit="true"></table>
	<div class="topbar clearfix" id="toolbar">
</div>

</body>
</html>
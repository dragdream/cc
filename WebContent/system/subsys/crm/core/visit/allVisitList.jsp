<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    String type = TeeStringUtil.getString(request.getParameter("type"), null);
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>拜访记录列表</title>
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
var type = <%=type%>;
function doInit(){
	query();
}

//查询
function query(params){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/TeeCrmVisitController/datagrid.action?type='+type,  //拜访记录列表
		pagination:true,
		singleSelect:true,
		queryParams:params,
		toolbar:'#toolbar',//工具条对象
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		checkbox:false,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'visitName',title:'拜访名称',width:120,formatter:function(value,rowData,rowIndex){
				return "<a href=\"#\" onclick=\"showDetail("+rowData.sid+",'"+rowData.customerName+"')\">"+rowData.visitName+"</a>";
			}   
			},
			{field:'visitTopicDesc',title:'拜访主题',width:80},
			{field:'customerName',title:'客户名称',width:80},
			{field:'visitTimeDesc',title:'计划日期',align: 'center',width:100},
			{field:'visitStatusDesc',title:'拜访状态',align: 'center',width:80},
			{field:'visitEndTimeDesc',title:'拜访完成日期',align: 'center',width:100},
			{field:'managePersonName',title:'负责人',align: 'center',width:60},
			{field:'createTimeDesc',title:'创建时间 ',align: 'center',width:120},
			{field:'createUserName',title:'创建人',align: 'center',width:100},
			{field:'2',title:'操作',align: 'center',width:80,formatter:function(value,rowData,rowIndex){
				return "<a href=\"#\" onclick=\"showDetail("+rowData.sid+",'"+rowData.customerName+"')\">详情</a>&nbsp;&nbsp;<a href=\"#\" onclick=\"deleteById("+rowData.sid+")\">删除</a>";
			}},
		]]
	});
	
}

//查看拜访详情
function showDetail(sid,customerName){
	var url = contextPath+"/system/subsys/crm/core/visit/visitDetial.jsp?sid="+sid+"&customerName="+customerName;
	openFullWindow(url);
	//location.href=url;
}

/**
 * 删除
 */
function deleteById(sid){
	$.MsgBox.Confirm("提示","确定删除此拜访记录？",function(){
		var url = contextPath+ "/TeeCrmVisitController/delById.action?sid="+sid;
		var json = tools.requestJsonRs(url, {sid:sid});
		if (json.rtState) {
			$.MsgBox.Alert_auto(json.rtMsg,function(){
				$.MsgBox.Alert_auto("删除成功！");
				datagrid.datagrid("unselectAll");
				datagrid.datagrid('reload');
			});
			
		} else {
			$.MsgBox.Alert_auto(json.rtMsg);
		}
	
	});
}

</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px;padding-left: 10px;padding-right: 10px;">
	<table id="datagrid" fit="true"></table>
	<div class="topbar clearfix" id="toolbar">
</div>
 
</body>
</html>
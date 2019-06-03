<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background-color: #f2f2f2">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>审批中</title>
</head>
<script>
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/projectController/getProjectListByStatus.action?status=2",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			/* {field:'sid',checkbox:true,title:'ID',width:100}, */
			{field:'projectName',title:'项目名称',width:100},
			{field:'projectNum',title:'项目编码',width:100},
			{field:'endTime',title:'计划结束日期',width:100},
			{field:'managerName',title:'项目负责人',width:100},
			{field:'projectMemberNames',title:'项目成员',width:400},
			{field:'progress',title:'进度',width:100,formatter:function(value,rowData,rowIndex){
				return rowData.progress+"%";
			}},
			{field:'opt_',title:'操作',width:120,formatter:function(value,rowData,rowIndex){
				return "<a href=\"#\" onclick=\"detail('"+rowData.uuid+"')\">详情</a>&nbsp;&nbsp;<a href=\"#\" onclick=\"revoke('"+rowData.uuid+"')\">撤回</a>";
			}}
		]]
	});
	
}

//详情
function detail(uuid){
	var url=contextPath+"/system/subsys/project/projectdetail/info.jsp?uuid="+uuid;
	openFullWindow(url);
}
//撤回
function  revoke(uuid){
	$.MsgBox.Confirm ("提示", "是否确认撤回该项目？", function(){
		 var url=contextPath+"/projectController/changeStatus.action";
			var param={uuid:uuid,status:1};
			var json=tools.requestJsonRs(url,param);
			
			if(json.rtState){
				$.MsgBox.Alert_auto("撤回成功！");
				datagrid.datagrid('reload');
			} 
	  });
	
}





</script>
<body onload="doInit()">
	<table id="datagrid" fit="true"></table>
</body>
</html>
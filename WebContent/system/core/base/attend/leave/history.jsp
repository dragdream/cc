<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<title>请假历史记录</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="<%=contextPath%>/system/core/base/attend/js/attend.js"></script>

<script type="text/javascript">
var datagrid;
function  doOnload(){
	queryLeave();
}
/**
 *查询管理
 */
function queryLeave(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/attendLeaveManage/getLeave.action?status=2",
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
			{field:'createTimeStr',title:'申请时间',width:120},
			{field:'leaderName',title:'审批人员',width:100},
			{field:'startTimeStr',title:'开始时间',width:100},
			{field:'endTimeStr',title:'结束时间',width:100},
			{field:'annualLeave',title:'请假天数',width:100},
			{field:'leaveTypeDesc',title:'请假类型',width:100},
			{field:'outDesc',title:'请假原因',width:200,formatter:function(value,rowData,rowIndex){
				var leaveDesc = rowData.leaveDesc.length>20?rowData.leaveDesc.substring(0,20)+"...":rowData.leaveDesc;
				var str="<a href='javascript:void(0);' onclick='attendLeaveInfo(" + rowData.sid + ")' title='"+ rowData.leaveDesc +"'>" + leaveDesc  + "</a>";
		        return str;	
			}},
		]]});
}

</script>
</head>
<body class=""  onload="doOnload();">
    <body class="" style="overflow:hidden" onload="doOnload();">
   <div id="toolbar" class = "toolbar clearfix">
     <div class="right fl setHeight">
     </div> 
   </div>
   <table id="datagrid" fit="true"></table>

</body>
</body>

</html>


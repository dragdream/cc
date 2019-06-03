<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<title>加班历史记录</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="<%=contextPath%>/system/core/base/attend/js/attend.js"></script>

<script type="text/javascript">
var datagrid;
function  doOnload(){
	queryOvertime();
}
/**
 *查询管理
 */
function queryOvertime(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/attendOvertimeManage/getOvertime.action?status=1",
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
			{field:'createTimeDesc',title:'申请时间',width:120},
			{field:'leaderName',title:'审批人员',width:100},
			{field:'startTimeDesc',title:'开始时间',width:100},
			{field:'endTimeDesc',title:'结束时间',width:100},
			{field:'overHours',title:'加班时长（小时）',width:100},
			{field:'overtimeDesc',title:'加班内容',width:200,formatter:function(value,rowData,rowIndex){
				var overtimeDesc = rowData.overtimeDesc.length>20?rowData.overtimeDesc.substring(0,20)+"...":rowData.overtimeDesc;
				var str="<a href='javascript:void(0);' onclick='attendOvertimeInfo(" + rowData.sid + ")' title='"+rowData.overtimeDesc+"'>" +overtimeDesc  + "</a>";
		        return str;	
			}},
		]]});
}

</script>
</head>
<body class="" style="overflow:hidden" onload="doOnload();">
   <div id="toolbar" class = "toolbar clearfix">
     <div class="right fl setHeight">
     </div> 
   </div>
   <table id="datagrid" fit="true"></table>

</body>

</html>


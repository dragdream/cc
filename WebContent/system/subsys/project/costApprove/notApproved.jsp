<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>未批准</title>
</head>

<script>
//初始化
var datagrid;
function  doInit(){
	var url=contextPath+"/projectCostController/getApproveListByStatus.action?status=2";
	datagrid = $('#datagrid').datagrid({
		url:url,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'projectName',title:'项目名称',width:120,formatter:function(value,rowData,rowIndex){
                return "<a href=\"#\" onclick=\"projectDetail('"+rowData.projectId+"')\">"+rowData.projectName+"</a>";
			}},
			{field:'costTypeName',title:'费用类型',width:80},
			{field:'createrName',title:'申请人',width:80},
			{field:'createTimeStr',title:'申请时间',width:100},
			{field:'amount',title:'申请金额',width:80},
			{field:'description',title:'费用说明',width:200},
			{field:'refusedReason',title:'拒绝原因',width:200},
			
		]]
	});
}

//项目详情
function projectDetail(uuid){
    var url=contextPath+"/system/subsys/project/projectdetail/index.jsp?uuid="+uuid;	
    openFullWindow(url);
}

</script>
<body onload="doInit()">
   <table id="datagrid" fit="true"></table>
</body>
</html>
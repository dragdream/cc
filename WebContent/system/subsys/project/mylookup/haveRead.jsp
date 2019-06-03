<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>已阅</title>
</head>
<script type="text/javascript">
var  datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/projectCopyController/getMyLookUpByReadFlag.action?readFlag=1",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			/* {field:'sid',checkbox:true,title:'ID',width:100}, */
			{field:'projectName',title:'项目名称',width:100,formatter:function(value,rowData,rowIndex){
				
				return "<a href='#' onclick=\"detail('"+rowData.projectId+"')\">"+rowData.projectName+"</a>";
			}},
			{field:'projectNum',title:'项目编码',width:100},
			{field:'projectManagerName',title:'项目负责人',width:100},
			{field:'projectMemberNames',title:'项目成员',width:200},
			{field:'fromUserName',title:'抄送人',width:100},
			{field:'createTimeStr',title:'抄送时间',width:100},
			
		]]
	});
	
}


//点击详情
function detail(projectId){
		
	//跳转到详情页面
	openFullWindow(contextPath+"/system/subsys/project/projectdetail/index.jsp?uuid="+projectId);
		
}
</script>


<body onload="doInit();" style="padding-left: 10px;padding-right: 10px">
   <table id="datagrid" fit="true"></table>
</body>
</html>
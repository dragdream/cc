<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//当前登陆人
  TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>处理中</title>
</head>
<script>
var datagrid ;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/supervisionController/getMySupListByStatus.action?option=clz",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'supName',title:'督办事项',width:100},
			{field:'typeName',title:'所属分类',width:100},
			{field:'leaderName',title:'责任领导',width:100},
			{field:'managerName',title:'主办人',width:100},
			{field:'assistNames',title:'协办人',width:200},
			{field:'endTimeStr',title:'截止时间',width:100},
			{field:'status',title:'状态描述',width:100,formatter:function(value,rowData,rowIndex){
			   var status=rowData.status;
			   var desc="";
			   if(status==1){
				   desc="进行中";
			   }else if(status==2){
				   desc="暂停申请中";
			   }else if(status==4){
				   desc="恢复申请中";
			   }else if(status==5){
				   desc="办结申请中";
			   }
			   return desc;
			}},
			{field:'opt',title:'操作',width:100,formatter:function(value,rowData,rowIndex){
			    var opt="<a href=\"#\" onclick=\"handle("+rowData.sid+")\">办理</a>";
			    return opt;
			}},
		]]
	});
	
}




//办理
function handle(sid){
	var url=contextPath+"/system/subsys/supervise/handle/index.jsp?sid="+sid;
	openFullWindow(url);
}

</script>
<body onload="doInit()">
   <table id="datagrid" fit="true"></table>
</body>
</html>
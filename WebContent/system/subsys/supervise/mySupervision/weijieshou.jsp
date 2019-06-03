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
<title>未接收</title>
</head>
<script>
var loginUserUuid=<%=loginUser.getUuid()%>;
var datagrid ;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/supervisionController/getMySupListByStatus.action?option=wjs",
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
			{field:'assistNames',title:'协办人',width:150},
			{field:'endTimeStr',title:'截止时间',width:120},
			{field:'opt',title:'操作',width:120,formatter:function(value,rowData,rowIndex){
			    var opt="<a href=\"#\" onclick=\"handle("+rowData.sid+")\">查看</a>";
			    if(loginUserUuid==rowData.managerId){
			    	opt+="&nbsp;&nbsp;&nbsp<a href=\"#\" onclick=\"receive("+rowData.sid+")\">签收</a>";
			    }
			    return opt;
			}},
		]]
	});
	
}


//签收
function receive(sid){
	$.MsgBox.Confirm ("提示", "是否确认签收该任务？", function(){
		  var url = contextPath + "/supervisionController/receive.action";
			var para = {sid:sid};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){
				
				$.MsgBox.Alert_auto("签收成功！");
				datagrid.datagrid("reload");
			}   
	  });
}


//查看

function handle(sid){
	var url=contextPath+"/system/subsys/supervise/handle/index.jsp?sid="+sid;
	openFullWindow(url);
}
</script>
<body onload="doInit()">
   <table id="datagrid" fit="true"></table>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//任务主键
  int  supId=TeeStringUtil.getInteger(request.getParameter("supId"),0);
//当前登陆人
  TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>催办记录</title>
</head>
<script>
var supId=<%=supId%>;
var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/supUrgeController/getUrgeListBySupId.action?supId="+supId,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'createrName',title:'发送人',width:100},
			{field:'createTimeStr',title:'发送时间',width:100},
			{field:'isIncludeChildren',title:'是否提醒下级任务',width:100,formatter:function(value,rowData,rowIndex){
				var isIncludeChildren=rowData.isIncludeChildren;
				var desc="";
				if(isIncludeChildren==1){
					desc="是";
				}else{
					desc="否";
				}
				return desc;
			}},
			{field:'content',title:'催办内容',width:200},
	]]	
	});
	
}

</script>
<body onload="doInit()">
 <table id="datagrid" fit="true"></table>
</body>
</html>
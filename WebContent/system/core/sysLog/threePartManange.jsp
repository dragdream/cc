<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
   //1=登录访问日志   2=访问实时日志   3=异常日志  4=管理员访问日志  5=管理员登录日志  6=管理员操作日志
   int type=TeeStringUtil.getInteger(request.getParameter("type"),0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<title>日志列表</title>
<script type="text/javascript">
var type=<%=type %>;
var datagrid ;
//初始化
function doInit(){
	//渲染日志表
	getSysLogTable();
	
	query();
}


//查询
function  query(){
	//获取日志表
	var para={};
	para["sysLogTable"]=$("#sysLogTable").val();
	para["type"]=type;	
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/sysLogManage/getThreePartSysLogs.action",
		queryParams:para,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'uuid',checkbox:true,title:'ID',width:100},
			{field:'userName',title:'用户名称',width:100},
			{field:'ip',title:'ip地址',width:120},
			{field:'type',title:'日志类型',width:150},
			{field:'timeDesc',title:'时间',width:200},
			{field:'remark',title:'描述',width:200},
		]]
	});
	
	
}



//渲染日志归档日期 
function getSysLogTable(){
	var url =contextPath+"/sysLogManage/getSysLogTable.action";
	var json = tools.requestJsonRs(url);
	var html = "<option value=\"0\">默认表(当月表)</option>";
	if(json.rtState){
		var list=json.rtData;
		for(var i=0;i<list.length;i++){
			html+="<option value=\""+list[i].tableName+"\">"+list[i].tableName+"</option>";
		}
	}
	$("#sysLogTable").html(html);
}

</script>
</head>
<body  onload="doInit();" style="font-size:12px;">
	<div id="toolbar" class = "topbar clearfix">
		<div class="fl" style="position:static;">
			日志表：
			<select id="sysLogTable" name="sysLogTable" class="BigSelect" onchange="query();">
			</select>
		</div>
	</div>
	<table id="datagrid" fit="true"></table>
</body>
</html>
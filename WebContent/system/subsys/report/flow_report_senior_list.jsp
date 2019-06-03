<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	TeePerson loginPerson = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script>

function doInit(){
	var json = tools.requestJsonRs(contextPath+"/seniorReportCat/datagrid.action",{rows:10000,page:1});
	var render = [];
	for(var i=0;i<json.rows.length;i++){
		render.push("<option value='"+json.rows[i].sid+"'>"+json.rows[i].name+"</option>");
	}
	$("#catId").append(render.join(""));
	
	
	$('#datagrid').datagrid({
		url:contextPath+'/seniorReport/datagridViews.action',
		pagination:true,
		singleSelect:true,
		striped: true,
		border: false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		fitColumns:false,//列是否进行自动宽度适应
		pageSize:30,
		columns:[[
			{field:'tplName',title:'报表名称',width:150},
			{field:'catName',title:'分类',width:150},
			{field:'_manage',title:'操作',width:100,formatter:function(value,rowData,rowIndex){
				var render = [];
				render.push("<a href='javascript:void(0)' onclick=\"view('"+rowData.uuid+"')\">查看</a>");
// 				render.push("<a href='javascript:void(0)' onclick='del("+rowData.sid+")'>删除</a>");
				return render.join("&nbsp;/&nbsp;");
			}}
		]]
	});
}

function view(uuid){
	openFullWindow(contextPath+"/system/subsys/report/flow_report_senior_show.jsp?reportId="+uuid);
}

function search(){
	$('#datagrid').datagrid("reload",{tplName:$("#tplName").val(),catId:$("#catId").val()});
}

</script>
</head>
<body onload="doInit()" >
<div id="toolbar">
	<div class="base_layout_top" style="position:static">
		<span class="easyui_h1"><img src="<%=contextPath %>/system/frame/3/icons/4.png" />&nbsp;&nbsp;通用报表</span>
	</div>
	<div style="padding:10px">
		分类：<select id="catId" name="catId" class="BigSelect" onchange="search()">
			<option value="0"></option>
		</select>
		&nbsp;&nbsp;
		报表名称：<input type="text" class="BigInput" name="tplName" id="tplName"/>&nbsp;&nbsp;<button class="btn btn-one" onclick="search()">查询</button>
	</div>
</div>
<table id="datagrid" fit="true"></table>
</body>
</html>
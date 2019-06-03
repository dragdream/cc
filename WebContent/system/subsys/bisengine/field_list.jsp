<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp" %>
<%
	int tableId = TeeStringUtil.getInteger(request.getParameter("tableId"),0);
	int catId = TeeStringUtil.getInteger(request.getParameter("catId"),0);
%>
<title>业务字段管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
var datagrid;
var tableId = <%=tableId%>
var catId = <%=catId%>;
function doInit(){

	//获取业务表信息
	var url = contextPath+"/bisTable/getModelById.action?sid="+tableId;
	var json = tools.requestJsonRs(url,{});
	$("#tableInfo").html(json.rtData.tableName+"("+json.rtData.tableDesc+")");
	
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/bisTableField/datagrid.action?bisTableId='+tableId,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		pageList: [50,60,70,80,90,100],
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'fieldName',title:'字段标识',width:100,formatter:function(e,rowData){
				if(rowData.primaryKeyFlag==1){
					return "<span style='color:red'>"+e+"</span>";
				}
				return e;
			}},
			{field:'fieldDesc',title:'字段描述',width:100},
			{field:'alias',title:'别名',width:100},
			{field:'fieldType',title:'字段类型',width:100},
			{field:'fieldTypeExt',title:'字段类型扩展'},
			{field:'2',title:'操作',formatter:function(e,rowData){
				var html = [];
				html.push("<a href='#' onclick='edit("+rowData.sid+")'>编辑</a>");
				html.push("&nbsp;&nbsp;<a href='javascript:void(0)' onclick='del("+rowData.sid+")'>删除</a>");
				html.push("<br/><a href='javascript:void(0)' onclick='createIndex("+rowData.sid+")'>创建索引</a>");
				html.push("&nbsp;&nbsp;<a href='javascript:void(0)' onclick='cancelIndex("+rowData.sid+")'>取消索引</a>");
				html.push("&nbsp;&nbsp;<a href='javascript:void(0)' onclick='changeField("+rowData.sid+")'>生成</a>");
				return html.join("");
			}}
		]]
	});
}

function add(){
	window.location = "field_addOrUpdate.jsp?tableId="+tableId+"&catId="+catId;
}

function edit(sid){
	window.location = "field_addOrUpdate.jsp?tableId="+tableId+"&sid="+sid+"&catId="+catId;
}

function createIndex(sid){
	if(window.confirm("是否要在该字段上创建索引？")){
		tools.requestJsonRs(contextPath+"/bisTableField/createIndex.action",{sid:sid});
		window.location.reload();
	}
}

function cancelIndex(sid){
	if(window.confirm("是否要取消该字段上的索引？")){
		tools.requestJsonRs(contextPath+"/bisTableField/cancelIndex.action",{sid:sid});
		window.location.reload();
	}
}

function changeField(sid){
	if(window.confirm("是否要重新生成该字段定义？")){
		tools.requestJsonRs(contextPath+"/bisTableField/changeField.action",{sid:sid});
		window.location.reload();
	}
}

function del(sid){
	$.jBox.confirm("确认要删除该字段吗？","确认",function(v){
		if(v=="ok"){
			var url = contextPath+"/bisTableField/deleteBisTableField.action";
			var json = tools.requestJsonRs(url,{sid:sid});
			if(json.rtState){
				datagrid.datagrid("reload");
			}else{
				alert(json.rtMsg);
			}
		}
	});
}

function manage(sid){
	
}
</script>
</head>
<body onload="doInit()">
<table id="datagrid" fit="true"></table>
<div id="toolbar">
	<div class="moduleHeader">
		<b><i class="glyphicon glyphicon-sound-stereo"></i>&nbsp;业务字段管理&nbsp;<span id="tableInfo"></span></b>
	</div>
	<div style="text-align:left;">
		<button class="btn btn-primary" onclick="add()">添加字段</button>
		&nbsp;
		<button class="btn btn-default" onclick="window.location='table_list.jsp?catId=<%=catId %>'">返回</button>
	</div>
</div>
</body>
</html>

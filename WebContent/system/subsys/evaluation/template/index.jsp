<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script>
var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/TeeEvalTemplateController/datagrid.action',
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'subject',title:'主题',width:80},
			{field:'evalTypeName',title:'类型',width:100},
			{field:'createUserName',title:'创建人',width:100},
			{field:'createTimeDesc',title:'创建时间',width:100},
			{field:'2',title:'操作',formatter:function(e,rowData){
				return "<a href='#' onclick='edit("+rowData.sid+")'>编辑</a>";
			}}
		]]
	});
}

function add(){
	location.href=contextPath+'/system/subsys/evaluation/template/addOrEidtTemplate.jsp';
}

function edit(sid){
	location.href=contextPath+'/system/subsys/evaluation/template/addOrEidtTemplate.jsp?sid='+sid;
}

function del(){
	var selections = datagrid.datagrid("getSelections");
	if(selections==null || selections==undefined || selections.length<=0){
		top.$.jBox.tip("请选择需要删除项目","info");
		return;
	}
	top.$.jBox.confirm("确认删除选中信息吗","确认",function(v){
		if(v=="ok"){
			var sids="";
			for(var i=0;i<selections.length;i++){
				 sids += selections[i].sid+",";
			}
			var url = contextPath+"/TeeEvalTemplateController/deleteById.action";
			var json = tools.requestJsonRs(url,{sids:sids});
			if(json.rtState){
				top.$.jBox.tip(json.rtMsg,"success");
			}else{
				top.$.jBox.tip(json.rtMsg,"error");
			}
			datagrid.datagrid("unselectAll");
			datagrid.datagrid("reload");
		}
	});
}
</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar">
		<div class="moduleHeader">
			<b><i class="glyphicon glyphicon-sound-stereo"></i>&nbsp;考核模板管理</b>
		</div>
		<div style="text-align:left;margin-bottom:5px;">
			<button class="btn btn-primary" onclick="add()">添加</button>
			<button class="btn btn-danger" onclick="del()">删除</button>
		</div>
	</div>
</body>
</html>
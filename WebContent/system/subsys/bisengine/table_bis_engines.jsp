<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp" %>
<% 
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"),0);
	int catId = TeeStringUtil.getInteger(request.getParameter("catId"),0);
%>
<title>引擎规则配置</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
.readonly{
background:#e2e2e2;
}
</style>
<script>
var sid = <%=sid%>;
var catId = <%=catId%>;
var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/bisTableEngine/datagrid.action?bisTableId='+sid,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'flowName',title:'绑定流程',width:50,formatter:function(data,rowData){
					return "<a href=\"table_bisconfig.jsp?sid="+sid+"&catId="+catId+"&tableEngineId="+rowData.sid+"\">"+rowData.flowName+"</a>";
				}
			},
			{field:'remark',title:'规则描述',width:100
			},
			{field:'status',title:'状态',width:50,formatter:function(data,rowData){
					if(data==1){
						return "<span style='color:green'>开启</span>";
					}else{
						return "<span style='color:red' >关闭</span>";
					}
				}
			},
			{field:'_oper',title:'管理',width:50,formatter:function(data,rowData){
					return "<a  href='javascript:void(0)' onclick='setStatus("+rowData.sid+",1)'>开启</a>&nbsp;<a href='javascript:void(0)' onclick='setStatus("+rowData.sid+",0)'>关闭</a>"
					+"&nbsp;<a href='javascript:void(0)' onclick='del("+rowData.sid+")'>删除</a>";
				}
			}
		]]
	});
}

function add(){
	if(window.confirm("是否要生成引擎规则项？")){
		var json = tools.requestJsonRs(contextPath+"/bisTableEngine/addBisTableEngine.action",{bisTableId:sid});
		window.location.reload();
	}
}

function setStatus(sid,status){
	tools.requestJsonRs(contextPath+"/bisTableEngine/setStatus.action",{sid:sid,status:status});
	window.location.reload();
}

function del(sid){
	if(window.confirm("是否删除该引擎规则？")){
		tools.requestJsonRs(contextPath+"/bisTableEngine/delBisTableEngine.action",{sid:sid});
		window.location.reload();
	}
}
</script>
</head>
<body style="margin:0px" onload="doInit()" >
<table id="datagrid" fit="true"></table>
<div id="toolbar">
	<div class="moduleHeader">
		<b><i class="glyphicon glyphicon-sound-stereo"></i>&nbsp;业务引擎规则列表</b>
	</div>
	<div style="text-align:left;">
		<button class="btn btn-primary" onclick="add()">添加</button>
	</div>
</div>
</body>
</html>

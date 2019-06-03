<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp" %>
<%
	int catId = TeeStringUtil.getInteger(request.getParameter("catId"),0);
%>
<title>业务表管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
var datagrid;
var catId = <%=catId%>
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/bisTable/datagrid.action?bisCatId='+catId,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'tableName',title:'业务表标识',width:100},
			{field:'tableDesc',title:'业务表描述',width:100},
			{field:'alias',title:'别名',width:100},
			{field:'sortNo',title:'排序号',width:100},
			{field:'gen',title:'是否已生成',width:100,formatter:function(e,rowData){
				if(e==0){
					return "未生成";
				}else{
					return "已生成";
				}
			}},
			{field:'dbType',title:'数据库类型',width:100},
			{field:'dataSource',title:'数据源类型',width:100,formatter:function(e,rowData){
				if(e==1){
					return "内部";
				}else{
					return "外部";
				}
			}},
			{field:'2',title:'操作',formatter:function(e,rowData){
				var html = [];
				html.push("<a href='#' onclick='edit("+rowData.sid+")'>编辑</a>");
				html.push("&nbsp;&nbsp;<a href='javascript:void(0)' onclick='gen("+rowData.sid+")'>生成</a>");
				html.push("&nbsp;&nbsp;<a href='javascript:void(0)' onclick='del("+rowData.sid+")'>删除</a>");
				html.push("&nbsp;&nbsp;<a href='javascript:void(0)' onclick='exportXml("+rowData.sid+")'>导出</a>");
				html.push("<br/>");
				html.push("<a href='#' onclick='manage("+rowData.sid+")'>字段设计</a>");
				html.push("&nbsp;&nbsp;<a href='#' onclick='config("+rowData.sid+")'>引擎配置</a>");
// 				html.push("&nbsp;&nbsp;<a href='javascript:void(0)' onclick='data("+rowData.sid+")'>数据获取</a>");
				return html.join("");
			}}
		]]
	});
}

function exportXml(sid){
	$("#frame").attr("src",contextPath+"/bisTable/export.action?sid="+sid);
}

function add(){
	window.location = "table_addOrUpdate.jsp?catId="+catId;
}

function edit(sid){
	window.location = "table_addOrUpdate.jsp?sid="+sid+"&catId="+catId;
}

function config(sid){
	//window.location = "table_bisconfig.jsp?sid="+sid+"&catId="+catId;
	window.location = "table_bis_engines.jsp?sid="+sid+"&catId="+catId;
}

function data(sid){
	window.location = "data_fetch_list.jsp?bisTableId="+sid+"&catId="+catId;
}

function del(sid){
	$.jBox.confirm("确认要删除该业务表？<br/>提示：如果删除则会清除该表的所有数据。","确认",function(v){
		if(v=="ok"){
			var url = contextPath+"/bisTable/deleteBisTable.action";
			var json = tools.requestJsonRs(url,{sid:sid});
			if(json.rtState){
				datagrid.datagrid("reload");
			}else{
				alert(json.rtMsg);
			}
		}
	});
}


function gen(sid){
	$.jBox.confirm("确认要生成业务数据表结构吗？","确认",function(v){
		if(v=="ok"){
			var url = contextPath+"/bisTable/generate.action";
			var json = tools.requestJsonRs(url,{tableId:sid});
			if(json.rtState){
				datagrid.datagrid("reload");
			}else{
				alert(json.rtMsg);
			}
		}
	});
}

function manage(tableId){
	window.location = "field_list.jsp?tableId="+tableId+"&catId="+catId;
}

function doImport(obj){
	if(document.getElementById("file").value.indexOf(".xml")==-1){
		alert("仅能上传xml后缀名模板文件！");
		return false;
	}
	$("#uploadBtn").attr("value","上传中").attr("disabled","");
	return true;
}

function uploadSuccess(){
	alert("导入成功！");
	window.location.reload();
}
</script>
</head>
<body onload="doInit()">
<table id="datagrid" fit="true"></table>
<div id="toolbar">
	<div class="base_layout_top" style="position:static;">
		<span class="easyui_h1">业务表管理</span>
	</div>
	<div style="padding:10px;">
		<button class="btn btn-primary" onclick="add()">创建</button>
		<button class="btn btn-warning" onclick="$('#uploadDiv').modal('show')">导入业务表</button>
	</div>
</div>


<form id="uploadForm" onsubmit="return doImport()" target="frame" action="<%=contextPath %>/bisTable/import.action?catId=<%=catId %>" name="uploadForm" method="post" enctype="multipart/form-data" >
<div class="modal fade" id="uploadDiv">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title">业务表导入</h4>
      </div>
      <div class="modal-body">
        <!-- 导入专用 -->
        	<span style="color:red">1.导入的格式为*.xml，且必须是兼容本系统的业务表文件格式。</span><br/><br/>
			<input style="border:0px" type="file" name="file" id="file"/>
			<iframe id="frame" name="frame" style="display:none" src="" ></iframe>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="submit" class="btn btn-primary" id="uploadBtn" >上传</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</form>
</body>
</html>

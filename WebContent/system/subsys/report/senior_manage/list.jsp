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
		url:contextPath+'/seniorReport/datagrid.action',
		pagination:true,
		singleSelect:false,
		striped: true,
		border: false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		fitColumns:false,//列是否进行自动宽度适应
		pageSize:30,
		columns:[[
			{field:'sid',checkbox:true},
			{field:'_sid',title:'报表ID',width:250 ,align:'center',formatter:function(value,rowData,rowIndex){
				return rowData.uuid;
			}},
			{field:'tplName',title:'报表名称',width:150},
			{field:'status',title:'状态',width:50,formatter:function(value,rowData,rowIndex){
				if(value==0){
					return "<span style='color:red'>禁用</span>";
				}
				return "<span style='color:green'>启用</span>";
			}},
			{field:'catName',title:'分类',width:150},
			{field:'_manage',title:'操作',width:100,formatter:function(value,rowData,rowIndex){
				var render = [];
				if(rowData.resId!=0){
					render.push("<a href='#' onclick=\"edit('"+rowData.uuid+"',2)\">编辑</a>");
				}else{
					render.push("<a href='#' onclick=\"edit('"+rowData.uuid+"',1)\">编辑</a>");
				}
				render.push("<a href='#' onclick=\"exportXml('"+rowData.uuid+"')\">导出</a>");
				render.push("<a href='javascript:void(0)' onclick=\"del('"+rowData.uuid+"')\">删除</a>");
				return render.join("&nbsp;&nbsp;");
			}}
		]]
	});
}

function add(){
	//window.location = "addOrUpdate.jsp";
	$("#createDiv").modal("show");
}

function doCreate(){
	if($("#bbType").val()==1){
		openFullWindow(contextPath+"/system/subsys/report/senior_manage/addOrUpdate.jsp");
	}else{
		openFullWindow(contextPath+"/system/subsys/report/senior_manage/addOrUpdate_gez.jsp");
	}
	$("#createDiv").modal("hide");
}

function edit(id,type){
	//window.location = "addOrUpdate.jsp?id="+id;
	if(type==2){
		openFullWindow(contextPath+"/system/subsys/report/senior_manage/addOrUpdate_gez.jsp?id="+id);
	}else{
		openFullWindow(contextPath+"/system/subsys/report/senior_manage/addOrUpdate.jsp?id="+id);
	}
}

function del(id){
	if(window.confirm("是否要删除该模版？")){
		var json = tools.requestJsonRs(contextPath+"/seniorReport/delReport.action",{uuid:id});
		window.location.reload();
	}
}

function open0(){
	var selections = $('#datagrid').datagrid('getSelections');
	if(selections.length==0){
		alert("至少选择一项");
		return ;
	}
	if(confirm("确认批量开启报表？")){
		var ids = "";
		for(var i=0;i<selections.length;i++){
			ids+=("'"+selections[i].uuid+"'");
			if(i!=selections.length-1){
				ids+=",";
			}
		}
		tools.requestJsonRs(contextPath+"/seniorReport/changeStatus.action",{status:1,ids:ids});
		$("#datagrid").datagrid("reload");
		$("#datagrid").datagrid("unselectAll");
	}
}

function close0(){
	var selections = $('#datagrid').datagrid('getSelections');
	if(selections.length==0){
		alert("至少选择一项");
		return ;
	}
	if(confirm("确认批量禁用报表吗？")){
		var ids = "";
		for(var i=0;i<selections.length;i++){
			ids+=("'"+selections[i].uuid+"'");
			if(i!=selections.length-1){
				ids+=",";
			}
		}
		tools.requestJsonRs(contextPath+"/seniorReport/changeStatus.action",{status:0,ids:ids});
		$("#datagrid").datagrid("reload");
		$("#datagrid").datagrid("unselectAll");
	}
}

function batchSettings(){
	var selections = $('#datagrid').datagrid('getSelections');
	if(selections.length==0){
		alert("至少选择一项");
		return ;
	}
	$("#settingsDiv").modal("show");
}

function doBatchSettings(){
	var selections = $('#datagrid').datagrid('getSelections');
	var ids = "";
	for(var i=0;i<selections.length;i++){
		ids+=("'"+selections[i].uuid+"'");
		if(i!=selections.length-1){
			ids+=",";
		}
	}
	tools.requestJsonRs(contextPath+"/seniorReport/batchSettings.action",{status:0,ids:ids,userPrivs:$("#userPrivs").val(),deptPrivs:$("#deptPrivs").val()});
	$("#settingsDiv").modal("hide");
}

function search(){
	$('#datagrid').datagrid("reload",{tplName:$("#tplName").val(),catId:$("#catId").val()});
}

function exportXml(uuid){
	$("#frame0").attr("src",contextPath+"/seniorReport/export.action?uuid="+uuid);
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
<body onload="doInit()" >
<div id="toolbar">
	<div style="padding:10px">
		分类：<select id="catId" name="catId" class="BigSelect" onchange="search()">
			<option value="0"></option>
		</select>
		&nbsp;&nbsp;
		报表名称：<input type="text" class="BigInput" name="tplName" id="tplName"/>
		<button class="btn btn-one" onclick="search()">查询</button>
		<button class="btn btn-primary" onclick="add()">添加</button>
		<input id="importBtn" type="button" class="btn btn-default" value="导入" onclick="$('#uploadDiv').modal('show')"/>
		<button class="btn btn-info" onclick="batchSettings()">批量授权</button>
		<button class="btn btn-success" onclick="open0()">开启</button>
		<button class="btn btn-danger" onclick="close0()">禁用</button>
	</div>
</div>
<table id="datagrid" fit="true"></table>

<div class="modal fade" id="settingsDiv" style="display:none;">
  <div class="modal-dialog"  style="width:600px">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title">批量授权</h4>
      </div>
      <div class="modal-body" style="font-size:12px;">
        <form id="form1" name="form1" method="post">
			<table  style="width:100%;font-size:12px" >
					<tr>
						<td>
							授权人员：
						</td>
						<td>
							<input type="hidden" id="userPrivs"/>
							<textarea readonly class="BigTextarea" id="_userPrivs" style="height:50px;width:300px;"></textarea>
							<a href="javascript:void(0)" onclick="selectUser(['userPrivs','_userPrivs'])">选择</a>
							<a href="javascript:void(0)" onclick="clearData('userPrivs','_userPrivs')">清空</a>
						</td>
					</tr>
					<tr>
						<td>
							授权部门：
						</td>
						<td>
							<input type="hidden" id="deptPrivs"/>
							<textarea readonly class="BigTextarea" id="_deptPrivs" style="height:50px;width:300px;"></textarea>
							<a href="javascript:void(0)" onclick="selectDept(['deptPrivs','_deptPrivs'])">选择</a>
							<a href="javascript:void(0)" onclick="clearData('deptPrivs','_deptPrivs')">清空</a>
						</td>
					</tr>
			</table>
		</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" onclick="doBatchSettings();">确定</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<iframe style="display:none" id="frame0" name="frame0"></iframe>

<form id="uploadForm" onsubmit="return doImport()" target="frame0" action="<%=contextPath %>/seniorReport/import.action" name="uploadForm" method="post" enctype="multipart/form-data" >
<div class="modal fade" id="uploadDiv">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title">报表导入</h4>
      </div>
      <div class="modal-body">
        <!-- 导入专用 -->
        	<span style="color:red">1.导入的格式为*.xml，且必须是兼容本系统的报表文件格式。</span><br/><br/>
			<input style="border:0px" type="file" name="file" id="file"/>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="submit" class="btn btn-primary" id="uploadBtn" >上传</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</form>


<div class="modal fade" id="createDiv">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title">创建报表</h4>
      </div>
      <div class="modal-body">
      	报表类型：
        <select class="BigSelect" id="bbType">
        	<option value="1">通用报表</option>
        	<option value="2">Gez报表</option>
        </select>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="submit" class="btn btn-primary" onclick="doCreate()" >确定</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

</body>
</html>
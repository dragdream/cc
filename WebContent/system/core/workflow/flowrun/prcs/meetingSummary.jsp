<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String host = request.getServerName() + ":" + request.getServerPort() + request.getContextPath() ; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<%@ include file="/header/header.jsp" %>
	<%@ include file="/header/easyui.jsp" %>
	<%
		int flowId =TeeStringUtil.getInteger(request.getParameter("flowId"), 0);
		String path = request.getContextPath();
	%>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>数据选择</title>
<script>
var id = <%=flowId%>;
var contextPath = '<%=path%>';
var xparent;
if(window.dialogArguments){
	xparent = window.dialogArguments;
}else if(window.opener){
	xparent = opener;
}else{
	xparent = window;
}
function doInit(){
	//根据flowId获取流程数据
	var opts = [{
		title : '流水号',
		field : 'runId',
		width:200
	},{
		field : 'runName',
		title : '发文标题',
		width:600,
		formatter:function(value,rowData,rowIndex){
			var url = contextPath+"/system/core/workflow/flowrun/print/index.jsp?runId="+rowData.runId+"&view=1";
			return "<a href='javascript:void(0)' onclick=\"openFullWindow('"+url+"','工作详情')\">"+rowData.runName+"</a>";
		}
	}];
	var para = {flowId:id};
	$('#datagrid').datagrid({
		url:contextPath+"/flowRun/getFlowRunList.action",
		queryParams:para,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		//idField:'formId',//主键列
		fitColumns:false,//列是否进行自动宽度适应
		columns:[opts]
	}); 
	
}

function selectIt(){
    var selected = $('#datagrid').datagrid('getSelected');
	if(selected==null){
		alert("请选择一项数据");
		return;
	}
	var bid = selected.runId;
	var sid = xparent.runId;
	var url = contextPath+"/flowRun/copyBodyAttachement.action";
	var json = tools.requestJsonRs(url,{bodyRunId:bid,summaryRunId:sid});
	if(json.rtState){
		CloseWindow();
		xparent.parent.$("#saveBtn").click()
	}else{
		json.rtMsg();
	} 
}

function search(){
	var tmp = {runId:$("#runId").val(),flowId:id,runName:$("#runName").val()}
	$('#datagrid').datagrid("reload",tmp);
}

</script>
</head>
<body onload="doInit()">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar">
		<div class="base_layout_top" style="position:static;">
			<table style="width:100%">
				<tr>
					<td>
						<span class="easyui_h1">流程数据选择</span>
					</td>
					<td style="text-align:right">
						<button type="button" class="btn btn-info"  onclick="selectIt()">确定</button>
						&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</div>
		<div style="padding:10px">
			<form id="form1">
			    流水号：<input type="text" name="runId" id="runId" class="BigInput" />
			 &nbsp;&nbsp;
			   发文标题：<input type="text" name="runName" id="runName"  class="BigInput"/>
			   
			   <input type="button"  class="btn btn-default"  value="查询" onclick="search();" />
			</form>
		</div>
	</div>
</body>

</html>
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
<title>添加业务表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
.readonly{
background:#e2e2e2;
}
</style>
<script>
var sid = <%=sid%>;
var catId = <%=catId%>;
function doInit(){
	var url = contextPath+"/bisDataSource/datagrid.action";
	var json = tools.requestJsonRs(url,{});
	var render = [];
	var rows = json.rows;
	for(var i=0;i<rows.length;i++){
		render.push("<option value="+rows[i].sid+">"+rows[i].dsName+"</option>");
	}
	$("#dataSource").html(render.join(""));
	
	if(sid!=0){
		var url = contextPath+"/bisTable/getModelById.action";
		var json = tools.requestJsonRs(url,{sid:sid});
		bindJsonObj2Cntrl(json.rtData);
		$("#bisPrefix").hide();
		$("#tableName").attr("readonly","readonly").addClass("readonly");
	}
	
}

function commit(){
	if(!$("#form1").form("validate")){
		return false;
	}
	
	var url;
	if(sid==0){
		url = contextPath+"/bisTable/addBisTable.action";
	}else{
		url = contextPath+"/bisTable/updateBisTable.action";
	}

	var params = tools.formToJson($("#form1"));
	if(sid==0){
		params["tableName"] = params["tableName"];
	}
	var json = tools.requestJsonRs(url,params);
	if(json.rtState){
		alert(json.rtMsg);
		history.go(-1);
	}else{
		alert(json.rtMsg);
	}
}

</script>
</head>
<body style="padding:5px" onload="doInit()" >
<div class="moduleHeader">
	<b>
	<%
		if(sid==0){
			out.print("添加");
		}else{
			out.print("编辑");
		}
	%>业务表</b>
</div>
<center>
<form id="form1">
	<table class="TableBlock" style="width:470px">
		<tr>
			<td class="TableData">表标识（英文）：</td>
			<td class="TableData" align="left">
				<span id="bisPrefix"></span>&nbsp;<input type="text" class="BigInput easyui-validatebox" required="true" validType="letter[]" name="tableName" value="bis_" id="tableName"/>
			</td>
		</tr>
		<tr>
			<td class="TableData">表描述（中文）：</td>
			<td class="TableData" align="left">
				<input type="text" class="BigInput easyui-validatebox"  name="tableDesc" required="true" validType="chinese[]"/>
			</td>
		</tr>
		<tr>
			<td class="TableData">表别名（英文）：</td>
			<td class="TableData" align="left">
				<input type="text" class="BigInput easyui-validatebox" required="true" validType="letter[]" name="alias"/>
			</td>
		</tr>
		<tr style="display:none">
			<td class="TableData">实体类路径：</td>
			<td class="TableData" align="left">
				<input type="text" class="BigInput easyui-validatebox" name="entityClass"/>
			</td>
		</tr>
		<tr>
			<td class="TableData">排序号：</td>
			<td class="TableData" align="left">
				<input type="text" style="width:30px" class="BigInput easyui-validatebox" required="true" validType="number[]" name="sortNo" id="sortNo"/>
			</td>
		</tr>
		<tr>
			<td class="TableData">数据源：</td>
			<td class="TableData" align="left">
				<select id="dataSource" name="dataSource" class="BigSelect">
					
				</select>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<button type="button" class="btn btn-primary" onclick="commit()">提交</button>
				&nbsp;&nbsp;
				<button type="button" class="btn btn-default" onclick="window.location='table_list.jsp?catId=<%=catId %>'">返回</button>
			</td>
		</tr>
	</table>
	<input type="hidden" name="sid" value="<%=sid %>" />
	<input type="hidden" name="bisCatId" id="bisCatId" value="<%=catId %>" />
	<input type="hidden" name="gen" id="gen"/>
</form>
</center>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/common/codemirror/codemirror.css">
	<script src="<%=contextPath %>/common/codemirror/codemirror.js"></script>
	<script src="<%=contextPath %>/common/codemirror/javascript.js"></script>
<%
	String sid = TeeStringUtil.getString(request.getParameter("sid"));
	String bisViewId = TeeStringUtil.getString(request.getParameter("bisViewId"));
%>
<title>视图项管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
/* BASICS */
 
.CodeMirror {
  /* Set height, width, borders, and global font properties here */
    font-family: monospace;
    height: 150px;
    width:350px;
    border: 0px solid #ccc; /*add by jackqqxu*/
    font-family: Monaco, Menlo, Consolas, 'COURIER NEW', monospace;/*add by jackqqxu*/
    font-size: 12px;
    border:1px solid #e2e2e2;
}
.CodeMirror-scroll {
  /* Set scrolling behaviour here */
  overflow: auto;
}
</style>
<script>
var sid = "<%=sid%>";
var bisViewId = "<%=bisViewId%>";

function doInit(){
	if(sid!=""){//
		var json = tools.requestJsonRs(contextPath+"/bisView/getBisViewListItem.action?sid="+sid);
		bindJsonObj2Cntrl(json.rtData);
	}
	initEditor();
}

function initEditor() {
	window.editor = CodeMirror.fromTextArea(document.getElementById('formatterScript'), {
	    mode: "javascript",
	    indentWithTabs: true,
	    smartIndent: true,
	    lineNumbers: true,
	    matchBrackets : true,
	    autofocus: true,
	    extraKeys: {"Ctrl-Space": "autocomplete"},
	    hintOptions: {tables: {
	      users: {name: null, score: null, birthDate: null},
	      countries: {name: null, population: null, size: null}
	    }}
	  });
}

function commit(){
	var param = tools.formToJson($("#form"));
	param["formatterScript"] = editor.getValue();
	if(sid!=""){
		var json = tools.requestJsonRs(contextPath+"/bisView/updateBisViewListItem.action",param);
		return json;
	}else{
		var json = tools.requestJsonRs(contextPath+"/bisView/addBisViewListItem.action",param);
		return json;
	}
}

function goBack(){
	window.location = "data_fetch_list.jsp?bisTableId="+bisTableId;
}
</script>
</head>
<body onload="doInit()">
<center id="form">
<table class="TableBlock" style="width:100%">
	<tr>
		<td class="TableData" style="width:120px">字段显示标题：</td>
		<td class="TableData">
			<input type="text" id="title" name="title" class="BigInput "/>
		</td>
	</tr>
	<tr>
		<td class="TableData">字段别名：</td>
		<td class="TableData">
			<input type="text" id="field" name="field" class="BigInput"/>
		</td>
	</tr>
	<tr>
		<td class="TableData">查询字段：</td>
		<td class="TableData">
			<input type="text" id="searchField" name="searchField" class="BigInput"/>
		</td>
	</tr>
	<tr>
		<td class="TableData">排序号：</td>
		<td class="TableData">
			<input type="text" id="orderNo" name="orderNo" class="BigInput"/>
		</td>
	</tr>
	<tr>
		<td class="TableData">显示宽度：</td>
		<td class="TableData">
			<input type="text" id="width" name="width" class="BigInput" value="100"/>
		</td>
	</tr>
	<tr>
		<td class="TableData">是否作为查询字段：</td>
		<td class="TableData">
			<select class="BigSelect" id="isSearch" name="isSearch">
				<option value="0">否</option>
				<option value="1">是</option>
			</select>
		</td>
	</tr>
	<tr>
		<td class="TableData" style="width:100px">查询字段类型：</td>
		<td class="TableData">
			<select name="fieldType" id="fieldType" class="BigSelect" onchange="">
				<option value="TEXT">文本类型</option>
				<option value="NUMBER">数字类型</option>
				<option value="DATE" >日期时间类型</option>
			</select>
		</td>
	</tr>
	<tr>
		<td class="TableData">数据模型（填写系统编码）：</td>
		<td class="TableData">
			<input type="text" id="model" name="model" class="BigInput"/>
		</td>
	</tr>
	<tr>
		<td class="TableData">格式化脚本：</td>
		<td class="TableData">
			<div>行字段数据：data</div>
			<div>行对象：rowData</div>
			<div>行索引：index</div>
			<textarea type="text" id="formatterScript" name="formatterScript" class="BigInput"></textarea>
		</td>
	</tr>
	
</table>
<input type="hidden" id="bisViewId" name="bisViewId" value="<%=bisViewId%>"/>
<input type="hidden" id="sid" name="sid" value="<%=sid%>"/>
</center>
</body>
</html>

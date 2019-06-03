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
	
	var tables = parent.tables;
	for(var i = 0;i<tables.length;i++){
		var json = tools.requestJsonRs(contextPath+"/bisTableField/datagrid.action?page=1&rows=1000000&bisTableId="+tables[i].sid);
		var list = json.rows;
		for(var j=0;j<list.length;j++){
			$("#selectField").append("<option value=\""+list[j].fieldDesc+","+list[j].fieldName+","+tables[i].name+"_"+tables[i].index+"."+list[j].fieldName+","+list[j].fieldType+"\">"+tables[i].desc+"-"+list[j].fieldDesc+"("+tables[i].name+"_"+tables[i].index+"."+list[j].fieldName+")"+"</option>");
		}
	}
	
	
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

function change(obj){
	var fieldDesc = obj.value.split(",")[0];
	var fieldNameAll = obj.value.split(",")[2];
	var fieldType = obj.value.split(",")[3];
	
	$("#title").val(fieldDesc);
	$("#field").val(fieldDesc);
	$("#searchField").val(fieldNameAll);
	if(fieldType=="TEXT" || fieldType=="CHAR" || fieldType=="VARCHAR"){
		$("#fieldType").val("TEXT");
	}else if(fieldType=="NUMBER"){
		$("#fieldType").val("NUMBER");
	}else if(fieldType=="DATE"){
		$("#fieldType").val("DATE");
	}else if(fieldType=="DATETIME"){
		$("#fieldType").val("DATETIME");
	}
	
}

function change111(obj){
	var v = obj.value;
	v = v.replace("field",$("#searchField").val());
	$("#searchFieldWrap").val(v);
}
</script>
</head>
<body onload="doInit()">
<center id="form">
<table class="TableBlock" style="width:100%">
	<tr>
		<td class="TableData" style="width:120px">字段选择：</td>
		<td class="TableData">
			<select id="selectField" class="BigSelect" onchange="change(this)">
				<option value="">请选择</option>
			</select>
		</td>
	</tr>
	<tr>
		<td class="TableData" style="width:120px">字段显示：</td>
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
			<input type="text" id="searchField" readonly name="searchField" style="width:300px" class="BigInput readonly"/>
		</td>
	</tr>
	<tr>
		<td class="TableData">日期字段显示格式化：</td>
		<td class="TableData">
			<input type="text" id="searchFieldWrap" style="width:300px" readonly name="searchFieldWrap" class="BigInput readonly"/>
			<br/>
			<select id="" class="BigSelect" onchange="change111(this)">
				<option value="">可选项</option>
				<option value="[DATE2CHAR_Y_M_D|field]">例：2012-02-02</option>
				<option value="[DATE2CHAR_Y_M|field]">例：2012-02</option>
				<option value="[DATE2CHAR_Y|field]">例：2012</option>
				<option value="[DATE2CHAR_M|field]">例：01（月份）</option>
			</select>
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
	<tr>
		<td class="TableData" style="width:100px">字段类型：</td>
		<td class="TableData">
			<select name="fieldType" id="fieldType" class="BigSelect" onchange="">
				<option value="TEXT">文本类型</option>
				<option value="NUMBER">数字类型</option>
				<option value="DATE">日期类型</option>
				<option value="DATETIME">日期时间类型</option>
			</select>
		</td>
	</tr>
</table>
<input type="hidden" id="bisViewId" name="bisViewId" value="<%=bisViewId%>"/>
<input type="hidden" id="sid" name="sid" value="<%=sid%>"/>
</center>
</body>
</html>

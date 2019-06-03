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
	<script src="<%=contextPath %>/common/codemirror/sql.js"></script>
<%
	int bisTableId = TeeStringUtil.getInteger(request.getParameter("bisTableId"),0);
	String identity = TeeStringUtil.getString(request.getParameter("identity"));
%>
<title>数据获取</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
/* BASICS */
 
.CodeMirror {
  /* Set height, width, borders, and global font properties here */
    font-family: monospace;
    height: 400px;
    width:500px;
    border: 0px solid #ccc; /*add by jackqqxu*/
    font-family: Monaco, Menlo, Consolas, 'COURIER NEW', monospace;/*add by jackqqxu*/
    font-size: 12px;
}
.CodeMirror-scroll {
  /* Set scrolling behaviour here */
  overflow: auto;
  width:500px;
}
</style>
<script>
var bisTableId = <%=bisTableId%>
var identity = "<%=identity%>";

function doInit(){
	if(identity!=""){//
		var json = tools.requestJsonRs(contextPath+"/bisDataFetch/get.action?identity="+identity);
		bindJsonObj2Cntrl(json.rtData);
	}
	initEditor();
}

function initEditor() {
	window.editor = CodeMirror.fromTextArea(document.getElementById('sql'), {
	    mode: "text/x-mysql",
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
	param["sql"] = editor.getValue();
	if(identity!=""){
		var json = tools.requestJsonRs(contextPath+"/bisDataFetch/update.action",param);
		if(json.rtState){
			goBack();
		}else{
			alert(json.rtMsg);
		}
	}else{
		var json = tools.requestJsonRs(contextPath+"/bisDataFetch/add.action",param);
		if(json.rtState){
			goBack();
		}else{
			alert(json.rtMsg);
		}
	}
}

function goBack(){
	window.location = "data_fetch_list.jsp?bisTableId="+bisTableId;
}
</script>
</head>
<body onload="doInit()">
<div class="moduleHeader">
	<b>数据获取规则管理</b>
</div>
<center id="form">
<table class="TableBlock" style="width:600px">
	<tr>
		<td class="TableData" style="width:100px">唯一标识：</td>
		<td class="TableData">
			<input type="text" id="identity" name="identity" class="BigInput" style="width:300px"/>
		</td>
	</tr>
	<tr>
		<td class="TableData">显示字段：</td>
		<td class="TableData">
			<textarea id="fieldsShow" name="fieldsShow" class="BigTextarea" style="height:80px;width:460px"></textarea>
			<br/>
			显示字段是从SQL中提取出来的别名集合，用逗号分隔：
			<br/>
			例如：标题,正文,日期
		</td>
	</tr>
	<tr>
		<td class="TableData">SQL：</td>
		<td class="TableData">
			<textarea id="sql" name="sql" class="BigTextarea" style="height:300px;width:460px"></textarea>
			<br/>
			SQL语句特殊变量说明：
			<br/>
			$userId = 当前用户ID<br/>
			$userSid = 当前用户主键ID<br/>
			$userName = 当前用户名<br/>
			$deptId = 部门ID<br/>
			$roleId = 角色ID<br/>
			@XXXX = 传入的参数值
		</td>
	</tr>
	<tr>
		<td class="TableData">特殊函数段说明：</td>
		<td class="TableData">
			[DATE2CHAR_Y_M_D|field] => 将field日期字段转换成格式为2012-02-02的字符串格式<br/>
			[DATE2CHAR_Y_M|field] => 将field日期字段转换成格式为2012-02的字符串格式<br/>
			[DATE2CHAR_Y|field] => 将field日期字段转换成格式为2012的字符串格式<br/>
			[DATE2CHAR_M|field] => 将field日期字段转换成格式为01的字符串格式<br/>
			[GET_DAY_OF_MONTH_STR] => 返回本月日期格式串  2014-05-01,2014-05-02,...,2014-05-31<br/>
			[GET_DAY_OF_MONTH_STR,2014,5] => 返回2014年5月的日期格式串  2014-05-01,2014-05-02,...,2014-05-31<br/><br/>
			[GET_MONTH_OF_YEAR_STR] => 返回本年的月份格式串  2014-01,2014-02,...,2014-12<br/>
			[GET_MONTH_OF_YEAR_STR,2014] => 返回2014年的月份格式串  2014-01,2014-02,...,2014-12<br/>
			[DATE2CHAR_Y_M|field] => 将field日期字段转换成格式为2012-02的字符串格式<br/>
			[GET_LONG_MONTH_BETWEEN_STR|field|1] => 返回本年度一月份的LONG整型区间   (field&lt;xxx and field&gt;xxx)<br/>
			[GET_LONG_MONTH_FIRST_STR|1] => 返回本年度一月份第一天的LONG整型 <br/>
			[GET_LONG_MONTH_LAST_STR|1] => 返回本年度一月份最后一天的LONG整型 <br/>
		</td>
	</tr>
	<tr>
		<td class="TableData">备注：</td>
		<td class="TableData">
			<textarea id="remark" name="remark" class="BigTextarea" style="height:80px;width:460px"></textarea>
		</td>
	</tr>
	<tr>
		<td class="TableData" colspan="2">
		<center>
			<button class="btn btn-primary" type="button" onclick="commit()">确定</button>
			&nbsp;
			<button class="btn btn-default" type="button" onclick="goBack()">返回</button>
		</center>
		</td>
	</tr>
	<input type="hidden" name="bisTableId" value="<%=bisTableId %>"/>
</table>
</center>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%
	int formId = TeeStringUtil.getInteger(request.getParameter("formId"),0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
	<title>JAVASCRIPT脚本</title>
	<meta charset="utf-8">
	<link rel="stylesheet" type="text/css" href="<%=contextPath %>/common/codemirror/codemirror.css">
	<script src="<%=contextPath %>/common/codemirror/codemirror.js"></script>
	<script src="<%=contextPath %>/common/codemirror/javascript.js"></script>
	<style>
		
	</style>
	<script>
	var formId = <%=formId%>;
	function doInit(){
		var url = contextPath+"/flowForm/getScript.action";
		var json = tools.requestJsonRs(url,{formId:formId});
		if(json.rtState){
			$("#script").attr("value",json.rtData);
			initEditor();
		}else{
			alert(json.rtMsg);
		}
	}
	
	function initEditor() {
		window.editor = CodeMirror.fromTextArea(document.getElementById("script"), {
			value: "",
		  mode: "javascript",
		  styleActiveLine: true,
		  lineNumbers: true,
		  lineWrapping: false,
		  tabindex: 4,
		  indentUnit: 4
		});
	}

	function commit(){
		var url = contextPath+"/flowForm/updateScript.action";
		var json = tools.requestJsonRs(url,{formId:formId,script:window.editor.getValue()});
		return true;
	}
	</script>
</head>
<body style="margin:0px;" onload="doInit()">
	<textarea id="script" style="width:100%;height:100%"></textarea>
</body>
</html>
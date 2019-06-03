<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%
	int formId = TeeStringUtil.getInteger(request.getParameter("formId"),0);
%>
<html>
<head>
<%@ include file="/header/header.jsp" %>
	<title>CSS样式表</title>
	<meta charset="utf-8">
	<link rel="stylesheet" type="text/css" href="<%=contextPath %>/common/codemirror/codemirror.css">
	<script src="<%=contextPath %>/common/codemirror/codemirror.js"></script>
	<script src="<%=contextPath %>/common/codemirror/css.js"></script>
	<style>
		.CodeMirror{
		  height:100%;
		  color:black;
		} 
	</style>
	<script>
	var formId = <%=formId%>;
	function doInit(){
		var url = contextPath+"/flowForm/getStyle.action";
		var json = tools.requestJsonRs(url,{formId:formId});
		if(json.rtState){
			$("#css").attr("value",json.rtData);
			initEditor();
		}else{
			alert(json.rtMsg);
		}
		
	}
	
	function initEditor() {
		window.editor = CodeMirror.fromTextArea(document.getElementById("css"), {
			value: "",
		  mode: "css",
		  styleActiveLine: true,
		  lineNumbers: true,
		  lineWrapping: true,
		  tabindex: 4,
		  indentUnit: 4
		});
	}

	function getCssContent(){
		return editor.getValue();
	}

	function commit(){
		var url = contextPath+"/flowForm/updateStyle.action";
		var json = tools.requestJsonRs(url,{formId:formId,css:editor.getValue()});
		return true;
	}
	</script>
</head>
<body style="margin:0px" onload="doInit()">
	<textarea id="css"></textarea>
</body>
</html>
<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%
	String diaryId = request.getParameter("diaryId");
%>
<script src="<%=contextPath %>/common/ckeditor/ckeditor.js"></script>
<script src="<%=contextPath %>/common/ueditor/ueditor.config.js"></script>
<script src="<%=contextPath %>/common/ueditor/ueditor.all.min.js"></script>
<style>
</style>
<script>
var diaryId = <%=diaryId%>;
var editor;
var uEditorObj;
function doInit(){
	uEditorObj = UE.getEditor('content');//获取编辑器对象
	uEditorObj.ready(function(){//初始化方法
	uEditorObj.setHeight(200);
	/* CKEDITOR.replace('editor1',{
		width : 'auto',
		height:$(document).height()-120
	});
	CKEDITOR.on('instanceReady', function (e) {
		editor = e.editor;
	}); */
	});
}

function commit(){
	var url = contextPath+"/diaryController/addReply.action";
	if(uEditorObj.getContent()=="" || uEditorObj.getContent()==null || uEditorObj.getContent()=="null"){
		//alert(uEditorObj.getContent());
		parent.$.MsgBox.Alert_auto("回复内容不能为空！");
		return false;
	}
	var json = tools.requestJsonRs(url,{diaryId:diaryId,content:uEditorObj.getContent()});
	return json.rtState;
}
</script>
</head>
<body onload="doInit();">
	<textarea id="content" name="content"></textarea>
</body>
</html>
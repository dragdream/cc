<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String channelId = request.getParameter("channelId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp" %>
<%@ include file="/header/upload.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/TeeMenu.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/layout/layout.js"></script>
<script src="<%=contextPath %>/common/ckeditor/ckeditor.js"></script>
<script>
var editor;
var channelId = <%=channelId%>;

function doInit(){
	$("#layout").layout({auto:true});
	
	editor = CKEDITOR.replace('content');		
	editor.on( 'instanceReady', function(event){		
		var editor = event.editor;
		editor.resize( editor.container.getStyle( 'width' ), CKEDITOR.document.getById( 'cke_'+'content' ).getParent().$.offsetHeight );
		$(window).resize(function(){
			editor.resize( editor.container.getStyle( 'width' ), CKEDITOR.document.getById( 'cke_'+'content' ).getParent().$.offsetHeight );
		});
		
		var json = tools.requestJsonRs(contextPath+"/cmsChannel/getChannelInfo.action",{sid:channelId});
		if(json.rtState){
			editor.setData(json.rtData.htmlContent);
		}
		
		
	}, null,null,9999);
}

function save(){
	
	var param = tools.formToJson($("#form"));
	param["htmlContent"] = editor.getData();
	param["sid"] = channelId;
	
	tools.requestJsonRs(contextPath+"/cmsChannel/updateChannelHtmlContent.action",param,true,function(json){
		alert("保存成功");
	});
}

</script>
</head>
<body onload="doInit();" style="margin:0px;overflow:hidden;background:#f0f0f0;font-size:12px;">
<div id="layout">
<form id="form" enctype="multipart/form-data" method="post">
	<div layout="south" height="40" style="padding:4px">
		<center>
			<button class="btn btn-success" type="button" onclick="save(1)">保存</button>
			<button class="btn btn-default" type="button" onclick="window.close();">关闭</button>
		</center>
	</div>
	<div layout="center">
		<textarea style="" id="content" name="htmlContent" class="BigTextarea" ></textarea>
	</div>
	
	<input type="hidden" name="chnlId" value="<%=channelId %>" />
</form>
</div>
</body>
</html>
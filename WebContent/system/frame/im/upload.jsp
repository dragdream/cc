<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<head>
	<%@ include file="/header/header.jsp" %>
	<%@ include file="/header/upload.jsp" %>
	<title>上传demo</title>
	<style>
	.progressWrapper {
		width: 100%;
		overflow: hidden;
	}
	</style>
	<script type="text/javascript">
	
	var swfUploadObj;
	window.onload = function() {
		//多附件快速上传
		swfUploadObj = new TeeSWFUpload({
			fileContainer:"fileContainer2",//文件列表容器
			renderContainer:"renderContainer2",//渲染容器
			uploadHolder:"uploadHolder2",//上传按钮放置容器
			valuesHolder:"valuesHolder2",//附件主键返回值容器，是个input
			quickUpload:true,//快速上传
			showUploadBtn:false,//不显示上传按钮
			queueComplele:function(){//队列上传成功回调函数，可有可无
				
			},
			uploadSuccess:function(fileInfo){
				window.external.IM_SEND_FILE_MSG(fileInfo.fileName,fileInfo.sid+"");
			},
			renderFiles:true,//渲染附件
			post_params:{model:"message"}//后台传入值，model为模块标志
			});

     };
	</script>
</head>
<body>
<div style="position:absolute;left:0px;right:0px;top:0px;height:20px;">
	<a id="uploadHolder2" class="add_swfupload" style="font-size:12px">
		<img src="<%=systemImagePath %>/upload/batch_upload.png"/>&nbsp;选择文件
	</a>
</div>
<div style="position:absolute;left:0px;right:0px;top:20px;bottom:0px;">
	<table style="width:100%;font-size:12px;margin-top:8px">
		<tr>
			<td style="border:1px solid gray;background:#f0f0f0;text-align:center"><b>传输列表</b></td>
		</tr>
		<tr>
			<td style="border:1px solid gray">
				<div id="fileContainer2" style="font-size:12px"></div>
			</td>
		</tr>
	</table>
	<div id="renderContainer2" style="font-size:12px"></div>
	<input id="valuesHolder2" type="hidden"/>
</div>
</body>
</html>

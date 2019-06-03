<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid");
%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/header/header.jsp" %>
	<%@ include file="/header/upload.jsp" %>
	<title>图片上传</title>
	<script type="text/javascript">
	var sid = "<%=sid%>";
	var editor = window.dialogArguments;
	var swfUploadObj;
		window.onload = function() {
			//多附件快速上传
			swfUploadObj = new TeeSWFUpload({
				fileContainer:"fileContainer2",//文件列表容器
				renderContainer:"renderContainer2",//渲染容器
				uploadHolder:"uploadHolder2",//上传按钮放置容器
				valuesHolder:"valuesHolder2",//附件主键返回值容器，是个input
				namesHolder:"namesHolder",//附件主键返回值容器，是个input
				quickUpload:true,//快速上传
				showUploadBtn:false,//不显示上传按钮
				queueComplele:function(){//队列上传成功回调函数，可有可无
					
				},
				renderFiles:true,//渲染附件
				file_types:"*.jpg;*.png;*.gif;*.bmp",
				post_params:{model:"imgbase",modelId:"0"}//后台传入值，model为模块标志
				});
	     };
	     
	function commit(){
		var attachIds = valuesHolder2.value;
		if(attachIds==""){
			alert("请上传图片！");
			return false;
		}
		var  url = contextPath+"/teeImgBaseController/dealAttach.action?attachIds="+attachIds+"&sid="+sid;
		var json = tools.requestJsonRs(url);
		return json;
	}
	  
	function isImg(ext){
		var imgArray=[".jpg",".png",".gif",".bmp"];
		if(imgArray.toString().indexOf(ext) > -1) {
			return true;
		}
		return false;
	}
	</script>
</head>
<body style="margin:10px;overflow:hidden;font-size:12px">
<a id="uploadHolder2" class="add_swfupload">
	<img src="<%=systemImagePath %>/upload/batch_upload.png"/>快速上传
</a>
&nbsp;&nbsp;
<a href="javascript:void(0)" onclick="commit()">
	确定
</a>
<br/>
<div id="fileContainer2"></div>
<div id="renderContainer2"></div>
<input id="valuesHolder2" type="text" style="display:none"/>
<input id="namesHolder" type="text" style="display:none"/>
</body>
</html>

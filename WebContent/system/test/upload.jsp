<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/header/header.jsp" %>
	<%@ include file="/header/upload.jsp" %>
	<title>上传demo</title>
	<script type="text/javascript">
	
	var swfUploadObj;
		window.onload = function() {
			$("#single").bingUpload({uploadSuccess:function(fileInfo){
				
			},queueComplele:function(){
				
			},model:"tmp",html:"<br/><input type='checkbox' />提醒所有有权限人员"});
			
			//多附件SWF上传组件
			swfUploadObj = new TeeSWFUpload({
				fileContainer:"fileContainer",//文件列表容器
				uploadHolder:"uploadHolder",//上传按钮放置容器
				showUploadBtn:false,//不显示上传按钮
				valuesHolder:"valuesHolder",//附件主键返回值容器，是个input
				queueComplele:function(){//队列上传成功回调函数，可有可无
					
				},
				renderFiles:true,//渲染附件
				post_params:{model:"workFlow"}//后台传入值，model为模块标志
				});

			
			//多附件简单上传组件，随表单提交
			new TeeSimpleUpload({
				fileContainer:"fileContainer1",//文件列表容器
				uploadHolder:"uploadHolder1",//上传按钮放置容器
				valuesHolder:"valuesHolder1",//附件主键返回值容器，是个input
				ext:["exe","pdf"],//附件主键返回值容器，是个input
				form:"form"//随form表单提交
				});

			//单附件简单上传组件
			new TeeSingleUpload({
				uploadBtn:"uploadBtn",
				callback:function(json){//回调函数，json.rtData
					
				},
				post_params:{model:"workFlow"}//后台传入值，model为模块标志
			});
			
			//多附件快速上传
			new TeeSWFUpload({
				fileContainer:"fileContainer2",//文件列表容器
				renderContainer:"renderContainer2",//渲染容器
				uploadHolder:"uploadHolder2",//上传按钮放置容器
				valuesHolder:"valuesHolder2",//附件主键返回值容器，是个input
				quickUpload:true,//快速上传
				showUploadBtn:false,//不显示上传按钮
				queueComplele:function(){//队列上传成功回调函数，可有可无
					
				},
				renderFiles:true,//渲染附件
				post_params:{model:"workFlow"}//后台传入值，model为模块标志
				});

	     };


	     //提交测试！！！！！
	     function test(){
			$("#form").doUpload({
				url:"业务的controller地址",
				success:function(json){
					
				},
				post_params:{model:"workFlow",xx:'xx'}
			});
		 }

	     function doUploadTest(){
	    	 swfUploadObj.doUpload();
	 	}
	</script>
</head>
<body>
<h1 title="testTitle">多附件SWF上传组件</h1>
<div id="fileContainer"></div>
<a id="uploadHolder" class="add_swfupload">
	<img src="<%=systemImagePath %>/upload/batch_upload.png"/>批量上传
</a>
<input id="valuesHolder" type="text"/>
<button onclick="doUploadTest()">绑定swf上传事件</button>

<button id="single">独立上传蒙板</button>

<form id="form" method="post" enctype="multipart/form-data">
	<h1>多附件简单上传组件</h1>
	<div id="fileContainer1"></div>
	<a id="uploadHolder1" class="add_swfupload">附件上传</a>
	<input id="valuesHolder1" type="text"/>
</form>
<input type="button" id="wocao" value="上传" onclick="test()"/>

<h1>单附件简单上传组件</h1>
<input id="uploadBtn" type="button" value="上传"/>


<h1 title="testTitle">多附件SWF快速上传组件</h1>
<div id="fileContainer2"></div>
<div id="renderContainer2"></div>
<a id="uploadHolder2" class="add_swfupload">
	<img src="<%=systemImagePath %>/upload/batch_upload.png"/>快速上传
</a>
<input id="valuesHolder2" type="text"/>

<input id="t1" type="text"/>
<input id="t2" type="text"/>
<br/>
<a href="javascript:void(0)" onclick="selectSingleUser(['t1','t2'],'14')">用户单选</a>
<br/>
<a href="javascript:void(0)" onclick="selectUser(['t1','t2'],'14')">用户多选</a>
<br/>
<a href="javascript:void(0)" onclick="selectSingleDept(['t1','t2'],'14')">部门单选</a>
<br/>
<a href="javascript:void(0)" onclick="selectDept(['t1','t2'],'14')">部门多选</a>
<br/>
<a href="javascript:void(0)" onclick="selectSingleRole(['t1','t2'],'14')">角色单选</a>
<br/>
<a href="javascript:void(0)" onclick="selectRole(['t1','t2'],'14')">角色多选</a>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header/header.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>Portal管理</title>
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css">
	<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="jquery.form.min.js"></script>
	<script type="text/javascript" charset="UTF-8">

	$(function(){



	});
	function submitForm(){
		 $(document.forms[0]).ajaxSubmit({
	            url: '<%=request.getContextPath() %>/uploadfile.action?method=upload',
	            iframe: true,
	            data: "{name=1}",
	            success: function(res) {
	            alert(res.rtMsg);
	                  // ... my success function (never getting here in IE)
	                  },
               error: function(arg1, arg2, ex) {
	                  // ... my error function (never getting here in IE)
	            },
	            dataType: 'json'});


			
	}
	
	</script>
<style>
.addfile {
	left: -5px;
	width: 65px;
	filter: alpha(opacity =   0);
	position: absolute;
	cursor: pointer
}
</style>
</head>
<body class="easyui-layout" fit="true">
<form method="post"  enctype="multipart/form-data" action="<%=request.getContextPath() %>/attachmentController/upload.action" id="userForm" name="userForm">  


<a >上传<input type="file" name="file" id="file" class="addfile" /></a>

<input type="text" name="disName"  value="妞i"/>

<input type="submit" value="form表单上传"/>  
</form>  
<input  type="button"   onclick="submitForm()" value="ajax上传"/>
</body>
</html>
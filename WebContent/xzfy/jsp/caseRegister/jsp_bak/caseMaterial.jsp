<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/validator2.0.jsp"%>
<%@ include file="/header/upload.jsp"%>
<script src="../../js/base/jquery.1.11.3.min.js"></script>
<style>
body {
	background: #fff;
	padding: 5px 10px 0 10px;
}

.material {
	display: inline-block;
	height: 100%;
	width: 100%;
	list-style: none;
}

.material li {
	display: inline-block;
	width: 100%;
	height: 8%;
	margin-bottom: 0.5%;
	background: #F8F8F8;
	list-style: none;
	position: relative;
}

.material>li>button {
	height: 80%;
	position: absolute;
	right: 10px;
	border: none;
	/* margin: 1%; */
	width: 80px;
	background: #51A0FD;
	color: #fff;
	border-radius: 5px;
	top: 10%;
}

.material>li>.material-title {
	display: inline-block;
	width: 20%;
	height: 100%;
	background: #EDF6FF;
	font-size: 14px;
	line-height: 38px;
	text-align: center;
}

.matertial-content {
	color: red;
	margin-left: 20px;
}
</style>
</head>
<body>
	<ul class="material">
		<li>
			<span class="material-title">申请书</span>
			<span class="matertial-content">未上传</span>
			<button class="btn-win-white" type="button" onclick="application_btn()">上 传</button>
		</li>
		<li><span class="material-title">收件等登记材料</span><span
			class="matertial-content"></span>
			<button>上 传</button></li>
		<li><span class="material-title">申请人提交的其他材料</span><span
			class="matertial-content">未上传</span>
			<button>上 传</button></li>
		<li><span class="material-title">阅卷笔录、阅卷意见及材料</span><span
			class="matertial-content">未上传</span>
			<button>上 传</button></li>
		<li><span class="material-title">阅卷笔录、阅卷意见及材料</span><span
			class="matertial-content"></span>
			<button>上 传</button></li>
		<li><span class="material-title">被申请人答复书</span><span
			class="matertial-content"></span>
			<button>上 传</button></li>
		<li><span class="material-title">被申请人证据材料</span><span
			class="matertial-content">未上传</span>
			<button>上 传</button></li>
		<li><span class="material-title">被申请人补充材料</span><span
			class="matertial-content"></span>
			<button>上 传</button></li>
		<li><span class="material-title">第三人有关材料</span><span
			class="matertial-content"></span>
			<button>上 传</button></li>
		<li><span class="material-title">复议机构制作获取的材料</span><span
			class="matertial-content"></span>
			<button>上 传</button></li>
		<li><span class="material-title">审批表</span><span
			class="matertial-content"></span>
			<button>上 传</button></li>
	</ul>
	<script src="../../../xzfy/js/caseRegister/case_material.js"></script>
</body>
</html>
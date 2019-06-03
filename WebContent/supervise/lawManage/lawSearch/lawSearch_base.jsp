<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/ztree.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />

<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/lawManage/lawSearch/js/law_index.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
</head>
<body onload="doInit()" style="padding-right: 10px;padding-left: 10px;padding-top: 5px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar" class="titlebar clearfix">
			<div id="outwarp">
		<div class="fl left">
			<img id="img1" class='title_img'
				src="<%=contextPath%>/common/zt_webframe/imgs/gzl/gzcx/icon_gongzuochaxun.png"/>
			<span class="title">法律法规检索</span>
		</div>
		<div class="fr">
		</div>
	</div>
		<span class="basic_border"></span>
		<div class="" style="padding-top: 5px; padding-bottom: 5px">
		<div>
			法律法规名称：<input type="text" id='name' name='name' class="easyui-textbox"  />
			&nbsp;&nbsp;时效性：
			<select name="timeliness" id="timeliness" data-options="editable:false" class="easyui-combobox" style="width: 145px"> 
				<option value="">全部</option>
				<option value="01">现行有效</option>
				<option value="02">失效</option>
			</select>
			&nbsp;&nbsp;效力级别：
			<select name='submitlawLevel' id='submitlawLevel' data-options="editable:false" class="easyui-combobox" style="width: 145px" >
				<option value="">全部</option>
			</select>
				&nbsp;&nbsp;<button  class="easyui-linkbutton" onclick="search()" style="text-align:right"><i class="fa fa-search"></i>&nbsp;查&nbsp;询</button>
			<!-- <span class="fr">
			</span> -->
			</div>
		</div>
	</div>
	</body>
</html>
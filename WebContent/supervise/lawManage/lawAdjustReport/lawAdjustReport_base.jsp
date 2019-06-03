<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
</head>
<body onload="doInit()" style="padding-right: 10px;padding-left: 10px;padding-top: 5px">
	<div id="toolbar" class="titlebar clearfix">
			<div id="outwarp">
		<div class="fl left">
			<img id="img1" class='title_img'
				src="<%=contextPath%>/common/zt_webframe/imgs/gzl/gzcx/icon_gongzuochaxun.png"/>
			<span class="title">法律法规入库</span>
		</div>
		<div class="fr">
			<button class="easyui-linkbutton" onclick="openNewReport()"><i class="fa fa-plus"></i>&nbsp;申请调整</button>
			&nbsp;&nbsp;
			<button class="easyui-linkbutton" onclick="submitReport()"><i class="fa fa-check"></i>&nbsp;提交审核</button>
		</div>
	</div>
		<span class="basic_border"></span> 
		<div class="" style="padding-top: 5px; padding-bottom: 5px">
		<div>
			调整类型：
			<select name='controlType' id='controlType' class="easyui-combobox" data-options="editable:false,panelHeight:'auto'" style="width: 145px" >
				<option value="">全部</option>
				<option value="01">新颁</option>
				<option value="02">修订</option>
				<option value="03">废止</option>
			</select>
			&nbsp;&nbsp;法律法规名称：<input type="text" id='lawName' name='lawName' class="easyui-textbox" data-options="validType:'length[0,200]'" />
			&nbsp;&nbsp;发布机关：<input type="text" id='organ' name='organ' class="easyui-textbox" data-options="validType:'length[0,100]'" />
			&nbsp;&nbsp;效力级别：
			<select name='submitlawLevel' id='submitlawLevel' class="easyui-combobox" style="width: 145px" >
				<option value="">全部</option>
			</select>
            &nbsp;&nbsp;
                <button  class="easyui-linkbutton" onclick="search()" style="text-align:right"><i class="fa fa-search"></i>&nbsp;查&nbsp;询</button>
			</div>
		</div>
	</div>

	<table id="datagrid" fit="true"></table>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/lawManage/lawAdjustReport/js/lawAdjustReport_base.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
</body>
</html>
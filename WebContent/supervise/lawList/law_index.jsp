<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/ztree.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/lawList/js/law_index.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
</head>
<body onload="doInit()" style="padding-right: 10px;padding-left: 10px;padding-top: 5px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar" class="titlebar clearfix">
			<div id="outwarp">
		<div class="fl left">
			<img id="img1" class='title_img'
				src="<%=contextPath%>/common/zt_webframe/imgs/gzl/gzcx/icon_gongzuochaxun.png">
			<span class="title">法律法规基础数据管理</span>
		</div>
		<div class="fr">
			<button class="easyui-linkbutton" onclick="add()"><i class="fa fa-plus"></i>新法</button>
			&nbsp;&nbsp;
			<button class="easyui-linkbutton" onclick="revise()"><i class="fa fa-pencil"></i>修订</button>
			&nbsp;&nbsp;
			<button class="easyui-linkbutton" onclick="abolish()"><i class="fa fa-minus"></i>废止</button>
			&nbsp;&nbsp;
			<button class="easyui-linkbutton" onclick="editn()"><i class="fa fa-search-plus"></i>内容管理</button>
			&nbsp;&nbsp;
			<button class="easyui-linkbutton" onclick="auditing()"><i class="fa fa-check"></i>审核</button>
			&nbsp;&nbsp;
			<button class="easyui-linkbutton" onclick="RemoveAuditing()"><i class="fa fa-mail-reply"></i>取消审核</button>
			&nbsp;&nbsp;
			<button class="easyui-linkbutton" onclick="legalDownloads()"><i class="fa fa-download"></i>法律下载</button>
			&nbsp;&nbsp;
			<button class="easyui-linkbutton" onclick="templateDownload()"><i class="fa fa-download"></i>模板下载</button>
			&nbsp;&nbsp;
			<button class="easyui-linkbutton" onclick="importLaw()"><i class="fa fa-upload"></i>导入</button>
			&nbsp;&nbsp;
			<button class="easyui-linkbutton" onclick="blockUp()"><i class="fa fa-minus"></i>停用</button>
		</div>
	</div>
		<span class="basic_border"></span>
		<div class="" style="padding-top: 5px; padding-bottom: 5px">
		<div>
			法律标题：<input type="text" id='name' name='name' class="easyui-textbox"  />
			时效性：
			<select name="timeliness" id="timeliness" class="easyui-combobox" style="width: 145px"> 
				<option value="">全部</option>
				<option value="1">现行有效</option>
				<option value="2">失效</option>
			</select>
							
			法律类别：
			<select name='submitlawLevel' id='submitlawLevel' class="easyui-combobox" style="width: 145px" >
				<option value="">全部</option>
			</select>
			<span class="fr">
						<button  class="easyui-linkbutton" onclick="search()" style="text-align:right"><i class="fa fa-search"></i>查询</button>
			</span>
			审核状态：
			<select name="examine" id="examine" class="easyui-combobox" style="width: 145px"> 
				<option value="">全部</option>
				<option value="0">待审核</option>
				<option value="1">已审核</option>
			</select>
			
			</div>
		</div>
	</div>
	</body>
</html>
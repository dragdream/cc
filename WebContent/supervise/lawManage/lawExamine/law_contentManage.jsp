<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>
<%
    String id = request.getParameter("id");
			String lawName = request.getParameter("lawName");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/userheader.jsp"%>
<%@ include file="/header/ztree.jsp"%>
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/supervise/lawManage/css/lawManage.css" />

<script type="text/javascript">
var datagrid;
var lawId = '<%=id%>';
var lawName = '<%=lawName%>';
</script>
</head>
<body onload="doInit()" style="font-size: 12px; padding-left: 10px; padding-right: 10px;">
    <div id="toolbar" class="titlebar topbar clearfix">
        <div class="fl left">
            <img id="img1" class='title_img' src="<%=contextPath%>/common/zt_webframe/imgs/gzl/gzcx/icon_gongzuochaxun.png" />
            <span class="title"><%=lawName%></span>
        </div>
        <div class="right fr t_btns">
            <button class="easyui-linkbutton" onclick="add()">
                <i class="fa fa-plus"></i>&nbsp;新&nbsp;增
            </button>
            &nbsp;&nbsp;
                <button class="easyui-linkbutton" onclick="excelImportLaw()">
                    <i class="fa fa-pencil"></i>&nbsp;excel导入
                </button>
            &nbsp;&nbsp;
            <button class="easyui-linkbutton" onclick="importLaw()">
                <i class="fa fa-upload"></i>&nbsp;导&nbsp;入
            </button>
            &nbsp;&nbsp;
            <button class="easyui-linkbutton" onclick="backMainPage()">
                <i class="fa fa-mail-reply"></i>&nbsp;返&nbsp;回
            </button>
        </div>
        <div style="clear: both"></div>
    </div>

    <table id="datagrid" fit="true"></table>
    <script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/common/js/md5.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/lawManage/lawExamine/js/law_contentManage.js"></script>

</body>
</html>
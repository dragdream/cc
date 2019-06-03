<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
</head>
<body onload="doInit()" style="padding-right: 10px; padding-left: 10px; padding-top: 5px">
    <div id="toolbar" class="titlebar clearfix">
        <div id="outwarp">
            <div class="fl left">
                <img id="img1" class='title_img' src="<%=contextPath%>/common/zt_webframe/imgs/gzl/gzcx/icon_gongzuochaxun.png" />
                <span class="title">法律法规审核</span>
            </div>
            <div class="fr">
            </div>
        </div>
        <span class="basic_border"></span>
        <div class="" style="padding-top: 5px; padding-bottom: 5px">
            <div>
                <span> 法律法规名称：</span><input type="text" id=lawName name='lawName' class="easyui-textbox" /> 
                <span>&nbsp;&nbsp;效力级别：</span>
                <select name='submitlawLevel' id='submitlawLevel' class="easyui-combobox" style="width: 145px">
                    <option value="">全部</option>
                </select>
                &nbsp;&nbsp;
                <button class="easyui-linkbutton" onclick="search()" style="text-align: right">
                    <i class="fa fa-search"></i>&nbsp;查&nbsp;询
                </button>
            </div>
        </div>
    </div>

    <table id="datagrid" fit="true"></table>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/lawManage/lawExamine/js/lawAdjustExamine_base.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
</body>
</html>
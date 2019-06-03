<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui.jsp"%>
<%@ include file="/header/ztree.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" type="text/css" href="/xzfy/css/init1.css" />
    <link rel="stylesheet" type="text/css" href="/xzfy/css/caseStep.css" />
    <link rel="stylesheet" type="text/css" href="/xzfy/css/iconfont/caseProgress/iconfont.css" />
    
    <script type="text/javascript" src="/xzfy/js/common/common.js"></script>
    <script type="text/javascript" src="/common/My97DatePicker/WdatePicker.js"></script>
    <title>案件受理</title>
</head>

<body onload="doInit()">
    <div class="content" id="d1">
        <div class="stepheader">
            <span class="step undo" id="caseRegister" onclick="change('1')"><span class="iconfont">&#xe6b9;&nbsp;</span>案件登记</span>
            <span class="room"><span class="stick left-stick"></span></span>
            
            <span class="step undo" id="caseAccept" onclick="change('2')"><span class="iconfont">&#xe69b;&nbsp;</span>立案管理</span>
            <span class="room"><span class="stick right-stick"></span></span>
            
            <span class="step undo" id="caseTrial" onclick="change('3')"><span class="iconfont">&#xe674;&nbsp;</span>案件审理</span>
            <span class="room"><span class="stick right-stick"></span></span>
            
            <span class="step undo" id="caseClose" onclick="change('4')"><span class="iconfont">&#xe861;&nbsp;</span>结案管理</span>
            <span class="room"><span class="stick right-stick"></span></span>
            
            <span class="step undo" id="caseAricive" onclick="change('5')"><span class="iconfont">&#xe861;&nbsp;</span>归档管理</span>
        </div>
        <div class="stepcover" id="stepcover">
            <span class="tabline"></span>
            <span class="room"></span>
            <span class="tabline"></span>
            <span class="room"></span>
            <span class="tabline"></span>
            <span class="room"></span>
            <span class="tabline"></span>
            <span class="room"></span>
            <span class="tabline"></span>
        </div>
        <div class="stepcontainer" id="content">
            
        </div>
    </div>
    <script type="text/javascript" src="/xzfy/js/caseAcceptence/acceptcommon.js"></script>
    <script type="text/javascript" src="/xzfy/js/caseAcceptence/caseProgress.js"></script>
</body>
</html>
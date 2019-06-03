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
    <link rel="stylesheet" href="/xzfy/css/init1.css" />
    <script type="text/javascript" src="/xzfy/js/common/common.js"></script>
    <script type="text/javascript" src="/xzfy/js/base/juicer-min.js"></script>
    
    <title>登记信息</title>
    <style rel="stylesheet" type="text/css">
    .main-left {
        width: 100px;
        display: inline-block;
    }
    .main-right {
        width: 90%;
        height: 95%;
        display: inline-block;
        vertical-align: top;
    }
    .lanky-tab {
        width: 73px;
        height: 35px;
        margin-top: 14px;
        border: solid 1px burlywood;
    }
    .head {
        width: 100%;
        height: 51px;
        line-height: 51px;
        font-size: 18px;
        background-color: #F7F7F7;
    }
    .content {
        width: 100%;
    }
    .lanky-star {
        color: red;
        font-weight: bold;
    }
    .lanky-tr {
        height: 35px;
        font-size: 18px;
        color: #343434;
        background-color: #F7F7F7;
    }
    .lanky-th {
        line-height: 35px;
        font-size: 18px;
        color: #3376C3;
    }
    .lanky-item {
        width: 10%;
        margin: 0 auto;
        text-indent: 10px;
        vertical-align: middle;
        text-align: right;
    }

    .lanky-td {
        width: 15%;
        text-align: left;
    }
    .lanky-info {
        border: 1px solid #cfcfcf;
        background-color: #F7F7F7;
    }
    .lanky-p {
        height: 70px;
    }
    table {
        width: 100%;
    }
    table td,
    table th {
        border: none;
    }
    table {
        /* border-top: 1px solid #cfcfcf;
        border-left: 1px solid #cfcfcf; */
        border: 1px solid #cfcfcf;
        border-collapse: separate;
        border-spacing: 0px 10px;
    }
    table td {
        height: 30px;
        line-height: 30px;
        font-size: 15px;
        text-indent: 5px;
    }
    p {
        word-wrap: break-word;
        overflow: auto
    }
    input {
        vertical-align: inherit;
    }
    .lanky-input {
        height: 99%;
        width: 96%;
        vertical-align: inherit;
    }
    </style>
</head>

<body onload="doInit()">
    <div class="main-left">
        <ul>
            <li class="lanky-tab">登记信息</li>
            <li class="lanky-tab">案件信息</li>
            <li class="lanky-tab">立案受理</li>
            <li class="lanky-tab">不予受理</li>
            <li class="lanky-tab">补正</li>
            <li class="lanky-tab">告知</li>
            <li class="lanky-tab">转送</li>
            <li class="lanky-tab">其他</li>
            <li class="lanky-tab">案卷管理</li>
            <li class="lanky-tab">归档管理</li>
            <li class="lanky-tab">办理进度</li>
        </ul>
    </div>
    <div class="main-right">
        <div class="head">
            <span>案件编号:</span>
        </div>
        <div class="content reg-info" id="register-div"></div>
        <div class="content" id="case-div"></div>
        <div class="content" id="accept-div"></div>
        <div class="content" id="refuse-div"></div>
        <div class="content" id="correction-div"></div>
        <div class="content" id="inform-div"></div>
        <div class="content" id="forward-div"></div>
        <div class="content" id="other-div"></div>
        <div class="content" id="file-div"></div>
        <div class="content" id="arichive-div"></div>
        <div class="content" id="flow-div"></div>
    </div>
    <script type="text/javascript" src="/xzfy/js/caseRegister/caseTpl.js"></script>
    <script type="text/javascript" src="/xzfy/js/caseRegister/registerInfo.js"></script>
    
</body>

</html>
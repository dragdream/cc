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
    <link rel="stylesheet" type="text/css" href="/xzfy/css/index.css" />
    <link rel="stylesheet" type="text/css" href="/xzfy/css/iconfont.css" />
    <link rel="stylesheet" type="text/css" href="/xzfy/css/common.css" />
    <link rel="stylesheet" type="text/css" href="/xzfy/imgs/acceptence_icon/iconfont.css" />
    <script type="text/javascript" src="/xzfy/js/common/common.js"></script>
    <script type="text/javascript" src="/xzfy/js/base/juicer-min.js"></script>
    <script type="text/javascript" src="/common/My97DatePicker/WdatePicker.js"></script>
    <style>
        .iconfont {
            vertical-align: bottom;
        }
    </style>
    <title>案件受理</title>
</head>

<body onload="doInit()">
    <div class="main-left">
        <ul>
            <li class="lanky-tab actived-status">登记信息
                <img src="../../imgs/5.png" class='status-img' />
            </li>
            <li class="lanky-tab default-status">案件信息
                <img src="../../imgs/2.png" class='status-img' />
            </li>
            <li class="lanky-tab">立案受理
                <img src="../../imgs/3.png" class='status-img' />
            </li>
            <li class="lanky-tab visited-status">不予受理
                <img src="../../imgs/1.png" class='status-img' />
            </li>
            <li class="lanky-tab">补正
                <img src="../../imgs/3.png" class='status-img' />
            </li>
            <li class="lanky-tab">告知
                <img src="../../imgs/3.png" class='status-img' />
            </li>
            <li class="lanky-tab">转送
                <img src="../../imgs/3.png" class='status-img'/>
            </li>
            <li class="lanky-tab">其他
                <img src="../../imgs/3.png" class='status-img'/>
            </li>
            <li class="lanky-tab">案卷管理
                <img src="../../imgs/3.png" class='status-img'/>
            </li>
            <li class="lanky-tab">归档管理
                <img src="../../imgs/3.png" class='status-img'/>
            </li>
            <li class="lanky-tab">办理进度
                <img src="../../imgs/3.png" class='status-img'/>
            </li>
        </ul>
    </div>
    <div class="main-right">
        <div class='module-top'>
            <div class='case-desc'>
                <div class='desc-left'>
                    <span class="iconfont">&#xe68a;</span>
                    <span>案件编号：<span class='case-identifier' id="caseNum"></span>
                    </span>
                    <span class="iconfont">&#xe680;</span>
                    <span>当前状态：<span class='case-status'>受理中</span></span>
                </div>
                <div class='desc-right'>
                    <span>距期限届满还有<span class='deadlind' id="time"></span></span>
                </div>
            </div>
        </div>
        <iframe id="myframe" width="100%" frameBorder="0" src="" scrolling="no"></iframe>
    </div>
    <script type="text/javascript" src="/xzfy/js/caseAcceptence/acceptcommon.js"></script>
    <script type="text/javascript" src="/xzfy/js/caseAcceptence/acceptence.js"></script>
</body>
</html>
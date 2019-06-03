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
    <link rel="stylesheet" type="text/css" href="/xzfy/css/common.css" />
    
    <script type="text/javascript" src="/xzfy/js/common/common.js"></script>
    <script type="text/javascript" src="/xzfy/js/base/juicer-min.js"></script>
    <script type="text/javascript" src="/common/My97DatePicker/WdatePicker.js"></script>
<%
	String caseId = request.getParameter("caseId");
%>
    <title>案件审理</title>
</head>

<body onload="doInit()">
    <div class="main-left">
        <ul>
            <li class="lanky-tab">登记信息</li>
            <li class="lanky-tab">案件信息</li>
            <li class="lanky-tab">立案受理</li>
            <li class="lanky-tab actived">调查取证</li>
            <li class="lanky-tab">复议决定</li>
            <li class="lanky-tab">辅助操作</li>
            <li class="lanky-tab">案卷管理</li>
            <li class="lanky-tab">归档管理</li>
            <li class="lanky-tab">办理进度</li>
        </ul>
    </div>
    <div class="main-right">
        <!-- <div class='module-top'>
            <div class='case-desc'>
                <div class='desc-left'>
                    <span>icon</span>
                    <span>案件编号：<span class='case-identifier'>深府行复【2019】0001 字</span>
                    </span>
                    <span>icon</span>
                    <span>当前状态：<span class='case-status'>受理中</span></span>
                </div>
                <div class='desc-right'>
                    <span>距立案审查期限届满还有<span class='deadlind'>19</span>天</span>
                </div>
            </div>
        </div> -->
        <input type="hidden" id="caseId" name="caseId" value="<%=caseId %>">
        <iframe id="myframe" width="100%" height="700px" frameBorder="0" src="" scrolling=""></iframe>
    </div>
    <script type="text/javascript" src="/xzfy/js/caseTrial/common/common.js"></script>
</body>
</html>
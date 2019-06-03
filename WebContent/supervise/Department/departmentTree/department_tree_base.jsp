<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>

    <script type="text/javascript"
            src="<%=contextPath%>/supervise/Department/js/adminDivision_manage_base.js"></script>
    <script type="text/javascript"
            src="<%=contextPath%>/supervise/Department/departmentTree/js/department_tree_base.js"></script>
            
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />

<title>执法机关结构树形图</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body onload="doInit()" style="padding-right: 10px;padding-left: 10px;padding-top: 5px">
    <div id="toolbar" class="titlebar clearfix">
            <div id="outwarp">
        <div class="fl left">
            <img id="img1" class='title_img'
                src="<%=contextPath%>/common/zt_webframe/imgs/xzbg/clgl/clsqcx.png" />
                <span class="title">执法机关结构树形图</span>
        </div>
        <div class=" fr">
         <!--    <button class="easyui-linkbutton" onclick="doOpenInputPage()">
                <i class="fa fa-plus"></i>行政强制措施填报</button>
            &nbsp;&nbsp;
            <button class="easyui-linkbutton" onclick="doOpenPerformPage()">
                <i class="fa fa-plus"></i>行政强制执行填报</button> -->
            &nbsp;&nbsp;
        </div>
    </div>
        <span class="basic_border"></span> 
    </div>
    <ul class="easyui-tree" id="testTree"></ul>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/supervise/inspection/inspList/js/insp_list_base.js"></script>
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />

</head>
<body onload="doInit()" style=" padding-left: 10px; padding-right: 10px; padding-top: 5px">
    <div id="toolbar" class="titlebar clearfix">
    <div id="outwarp">
        <div class="fl left">
            <img id="img1" class='title_img'
                src="<%=contextPath%>/common/zt_webframe/imgs/gzl/gzcx/icon_gongzuochaxun.png">
            <span class="title">检查事项维护</span>
        </div>
        <div class="fr">
            <button class="easyui-linkbutton" onclick="doOpenInputPage(null,'新增')"><i class="fa fa-plus"></i>&nbsp;新增</button>
<!--             <button class="easyui-linkbutton" onclick="inspListSubmit()"><i class="fa fa-check"></i>&nbsp;提交</button> -->
            <!-- <button class="easyui-linkbutton" onclick="inspListDel()"><i class="fa fa-trash-o"></i>&nbsp;删 除</button> -->
<!--             &nbsp;&nbsp; -->
         </div>
        </div>
        <span class="basic_border"></span>
        <div class="" style="padding-top: 5px; padding-bottom: 5px">
        <div>
            事项名称：<input type="text" id='inspListName' name='inspListName' class="easyui-textbox"  />
            <button  class="easyui-linkbutton" onclick="searchList()" style="text-align:right"><i class="fa fa-search"></i>查询</button>
            </div>
        </div>
    </div>
    <table id="datagrid" fit="true"></table>
</body>
</html>
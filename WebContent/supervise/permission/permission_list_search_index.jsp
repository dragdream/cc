<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@ page trimDirectiveWhitespaces="true" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ include file="/header/header2.0.jsp"%>
    <%@ include file="/header/easyui2.0.jsp"%>
    <link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
    <title>许可办件查询</title>
</head>
<body onload="doInitIndex()" style="padding-right: 10px;padding-left: 10px;padding-top: 5px; min-width: 1130px;">
    <div id="toolbar" class="titlebar clearfix">
       <div id="outwarp">
        <div class="fl left">
            <img id="img1" class='title_img' src="<%=contextPath%>/common/zt_webframe/imgs/xzbg/clgl/clsqcx.png" />
            <span class="title">许可办件查询</span>
        </div>
        </div>
        <span class="basic_border"></span>
        
        <div class="" style="padding-top: 7px; padding-bottom: 7px">
            <!-- form表单 -->
            <form id="permission_list_form">
                &nbsp;&nbsp;办理事项：&nbsp;
                <select name="itemId" id="itemId" class="easyui-textbox" style="width: 220px; height:30px;"
                    data-options="validType:'length[0,200]'">
                </select>
                &nbsp;&nbsp;行政相对人类型：&nbsp;
                <select name="partyType" id="partyType" class="easyui-combobox" style="width: 100px; height:30px;"
                    data-options="" >
                </select>
                &nbsp;&nbsp;许可决定文书号：&nbsp;
                <input name="decisionCode" id="decisionCode" class="easyui-textbox" style="width: 150px; height:30px;"
                    data-options="validType:'length[0,100]'" />
                &nbsp;&nbsp;审核类型：&nbsp;
                <select name="permissionType" id="permissionType" class="easyui-combobox" style="width: 100px; height:30px;"
                    data-options="" >
                </select>
                &nbsp;&nbsp;
                <a class="easyui-linkbutton" onclick="permissionListSearch()"><i class="fa fa-search"></i> 查 询</a>
            </form>
        </div>
    </div>
    <table id="permission_list_index_datagrid" fit="true"></table>
    <script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/permission/js/permission.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/permission/js/permission_list_search_index.js"></script>
</body>
</html>
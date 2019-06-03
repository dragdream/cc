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
    <title>许可事项维护</title>
</head>
<body onload="doInitIndex()" style="padding-right: 10px;padding-left: 10px;padding-top: 5px; min-width: 1130px;">
    <div id="toolbar" class="titlebar clearfix">
            <div id="outwarp">
        <div class="fl left">
            <img id="img1" class='title_img' src="<%=contextPath%>/common/zt_webframe/imgs/xzbg/clgl/clsqcx.png" />
            <span class="title">许可事项维护</span>
        </div>
        <!-- 右上角按钮 -->
        <div class="right fr t_btns">
            <button class="easyui-linkbutton" onclick="permissionItemAdd()">
                <span style="padding-right: 2px; width: 30px;"><i class="fa fa-plus"></i> 新 增</span>
            </button>
            <button class="easyui-linkbutton" onclick="permissionItemBatchDelete()">
                <span style="padding-right: 2px; width: 30px;"><i class="fa fa-trash-o"></i> 删 除</span>
            </button>
            &nbsp;&nbsp;
        </div>
    </div>
        <span class="basic_border"></span>
       
        <div class="" style="padding-top: 7px; padding-bottom: 7px">
            <!-- form表单 -->
            <form id="permission_item_form">
                <%-- &nbsp;&nbsp;事项编号：&nbsp;
                <input name="xkSxbm" id="xkSxbm" class="easyui-textbox" style="width: 150px; height:30px;"
                    data-options="validType:'length[0,64]'"/> --%>
                &nbsp;&nbsp;事项名称：&nbsp;
                <input name="name" id="name" class="easyui-textbox" style="width: 150px; height:30px;"
                    data-options="validType:'length[0,200]'"/>
                &nbsp;&nbsp;办件类型：&nbsp;
                <select name="listType" id="listType" class="easyui-combobox" style="width: 100px; height:30px;"
                    data-options="">
                </select>
                &nbsp;&nbsp;服务对象：&nbsp;
                <select name="partyType" id="partyType" class="easyui-combobox" style="width: 100px; height:30px;"
                    data-options="">
                </select>
                &nbsp;&nbsp;办理形式：&nbsp;
                <select name="handleWay" id="handleWay" class="easyui-combobox" style="width: 100px; height:30px;"
                    data-options="">
                </select>
                <!-- &nbsp;&nbsp;许可主体：&nbsp;
                <select name="subjectId" id="subjectId" class="easyui-combobox" style="width: 220px; height:30px;"
                    data-options="validType:'length[0,300]'">
                </select> -->
                &nbsp;&nbsp;
                <a class="easyui-linkbutton" onclick="permissionItemSearch()"><i class="fa fa-search"></i> 查 询</a>
            </form>
        </div>
    </div>
    <table id="permission_item_index_datagrid" fit="true"></table>
    <script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/permission/js/permission.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/permission/js/permission_item_index.js"></script>
</body>
</html>
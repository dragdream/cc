<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/ztree.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/Department/js/department_search_index.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
</head>
<body onload="doInit()" style="padding-right: 10px;padding-left: 10px;padding-top: 5px">
    
    <table id="datagrid" fit="true"></table>
    <div id="toolbar" class="titlebar clearfix">
            <div id="outwarp">
        <div class="fl left">
            <img id="img1" class='title_img'
                src="<%=contextPath%>/common/zt_webframe/imgs/gzl/gzcx/icon_gongzuochaxun.png" />
            <span class="title">执法机关查询</span>
        </div>
        <div class="fr"></div>
    </div>
        <form id= "form2" name = "form2" >
        <span class="basic_border"></span>
        <div class="div1" style="padding-top: 5px; padding-bottom: 5px">
                              机关名称：<input type="text" prompt="机关全称或简称" id='name' name='name' class="easyui-textbox"  />
            &nbsp;&nbsp;机关性质：
            <select  name="nature" id="nature" data-options="editable:false,panelHeight:'auto'" class="easyui-combobox" style="width: 145px"> 
            <option value="">全部</option>
            </select>
            &nbsp;&nbsp;是否垂管：
            <select size="1" name='isManubrium' id='isManubrium' class="easyui-combobox" style="width: 145px" panelMaxHeight="150px" data-options="editable:false,panelHeight:'auto'">
                            <option value="">全部</option>
                            <option value="0">否</option>
                            <option value="1">是</option>
            </select>
<!--             &nbsp;&nbsp;审核状态： -->
<!--             <select size="1" name='isExamine' id='isExamine' class="easyui-combobox" style="width: 145px" panelMaxHeight="150px" data-options="editable:false,panelHeight:'auto'"> -->
<!--                             <option value="">全部</option> -->
<!--                             <option value="0">未审核</option> -->
<!--                             <option value="1">已审核</option> -->
<!--             </select> -->
            &nbsp;&nbsp;<button type="button" class="easyui-linkbutton" onclick="queryDepartment()" style="text-align:right"><i class="fa fa-search"></i>&nbsp查&nbsp询</button>
        </div>
        </form>
    </div>
    
</body>
</html>
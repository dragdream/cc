<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String id = request.getParameter("id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/ztree.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/subject/js/organization_search_index.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />

</head>
<body onload="doInit()" style="padding-right: 10px;padding-left: 10px;padding-top: 5px">
    <table id="datagrid" fit="true"></table>
    <div id="toolbar" class="titlebar clearfix">
            <div id="outwarp">
            <div class="fl left">
            <img id="img1" class='title_img'
                src="<%=contextPath%>/common/zt_webframe/imgs/gzl/gzcx/icon_gongzuochaxun.png"/>
            <span class="title">受委托组织查询</span>
        </div>
        <div class="fr">
            <!-- <button class="easyui-linkbutton" onclick="add()"><i class="fa fa-plus"></i>&nbsp新&nbsp增</button>
            &nbsp;&nbsp;
            <button class="easyui-linkbutton" onclick="dels()"><i class="fa fa-trash-o"></i>&nbsp删&nbsp除</button>
            &nbsp;&nbsp; -->
        </div>
    </div>
        <span class="basic_border"></span> 
        <div class="" style="padding-top: 5px; padding-bottom: 5px">
        <div>
            	受委托组织名称：
            <input  id='subName' name='subName' class="easyui-textbox"  />
            &nbsp;&nbsp; 受委托组织性质：
            <input name="entrustNature" id="entrustNature" class="easyui-combobox" data-options="editable:false,panelHeight:'auto'"/> 
            &nbsp;&nbsp; 统一社会信用代码：
            <input  id='code' name='code' class="easyui-textbox"  />
            <!-- 委托主体：
            <input  id='parentId' name='parentId' class="easyui-textbox"  /> -->
            <input type="hidden" id="isDepute" name="isDepute" value="1"/>
            <!-- <span class="fr"> -->&nbsp;&nbsp;
                        <button  class="easyui-linkbutton" onclick="queryS()" style="text-align:right"><i class="fa fa-search"></i>&nbsp;查&nbsp;询</button>&nbsp;&nbsp;&nbsp;
            <!-- </span> -->
            </div>
            
        </div>
    </div>
    
</body>
</html>
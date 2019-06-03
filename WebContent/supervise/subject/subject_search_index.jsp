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
<script type="text/javascript" src="<%=contextPath%>/supervise/subject/js/subject_search_index.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
</head>
<body onload="doInit()" style="padding-right: 10px;padding-left: 10px;padding-top: 5px">
    <table id="datagrid" fit="true"></table>
    <div id="toolbar" class="titlebar clearfix">
        <div id="outwarp">
        <div class="fl left">
            <img id="img1" class='title_img'
                src="<%=contextPath%>/common/zt_webframe/imgs/gzl/gzcx/icon_gongzuochaxun.png"/>
            <span class="title">执法主体查询</span>
        </div>
        <div class="fr" style="height:30px;">
        </div>
        </div>
        <span class="basic_border"></span> 
        <div class="" style="padding-top: 5px; padding-bottom: 5px">
        <div>
            	主体名称：<input type="text" id='subName' name='subName' class="easyui-textbox"  />
            &nbsp;&nbsp;所属领域：
            <input name="orgSys" id="orgSys" prompt="输入关键字后自动搜索" class="easyui-combobox" style="width: 190px"/> 
            &nbsp;&nbsp;主体类别：
            <select name="nature" id="nature" data-options="editable:false,panelHeight:'auto'" class="easyui-combobox" style="width: 160px"> 
            <option value="">全部</option>
            </select>
            <!-- 主体层级：
            <select name='subLevel' id='subLevel' class="easyui-combobox" style="width: 145px" >
                            <option value="">所有状态</option>
            </select> -->
<!--             &nbsp;&nbsp;审核状态： -->
<!--                 <select name='examine' id='examine' class="easyui-combobox" style="width: 160px" panelMaxHeight="150px" data-options="editable:false,panelHeight:'auto'" > -->
<!--                                 <option value="">全部</option> -->
<!--                                 <option value="0">未审核</option> -->
<!--                                 <option value="1">已审核</option> -->
<!--                 </select> -->
                <input type="hidden" id="isDepute" name="isDepute" value="0"/>
            <!-- <span class="fr"> -->&nbsp;&nbsp;
                        <button  class="easyui-linkbutton" onclick="queryS()" style="text-align:right"><i class="fa fa-search"></i>&nbsp;查&nbsp;询</button>
            <!-- </span> -->
            
            </div>
        </div>
    </div>
    
</body>
</html>
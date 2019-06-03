<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>

    <script type="text/javascript"
            src="<%=contextPath%>/supervise/power/js/power_discretionary.js"></script>

    <link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="/supervise/power/css/power.css" />
    
    <link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />

<title>自由裁量</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

</head>
<body style="padding-right: 10px; padding-left: 10px; padding-top: 5px"
    onload="doInit();">
    <div id="toolbar" class="titlebar clearfix">
        <div class="fl left">
            <img id="img1" class='title_img'
                src="<%=contextPath%>/common/zt_webframe/imgs/xzbg/clgl/clsqcx.png" />
                <span class="title">自由裁量</span>
        </div>
        <div class="fr">
            <!-- <button class="easyui-linkbutton" onclick="doCreate();"><i class="fa fa-plus"></i> 新建</button> 
            &nbsp;&nbsp;-->
        </div>
        <span class="basic_border"></span> 
        <div class="" style="padding-top: 5px; padding-bottom: 5px">
            <!-- form表单 -->
            <form id="searchForm">
                <table class="none_table" style="width: 100%;">
                    <tr>
                        <td style="text-align: right; width: 90px;">职权编号：</td>
                        <td style="width: 100px;">
                            <input class="easyui-textbox" id="powerCode" style="width: 100px;" data-options="validType:'length[0,10]' "/>
                        </td>
                        <td style="text-align: right; width: 90px;">职权名称：</td>
                        <td style="width: 220px;">
                            <input type="text" name="name" id="powerName" class="easyui-textbox" style="width: 100%;" data-options="validType:'length[0,200]' "/>
                        </td>
                        <td style="text-align: right; width: 90px;">自由裁量：</td>
                        <td style="width: 100px;">
                            <select name="isDeiscretionary" id="isDeiscretionary" class="easyui-combobox" style="width: 100px;"
                                data-options="panelHeight: 'auto'">
                                <option value="-1">请选择</option>
                                <option value="01">包含</option>
                                <option value="02">不包含</option>
                            </select>
                        </td>
                        <td style="text-align: right; width: 90px;">职权分类：</td>
                        <td style="width: 150px;">
                            <input name="powerDetail" id="powerDetail" class="easyui-combobox" data-options="multiple:true" style="width: 150px;" />
                        </td>
                        <td style="text-align: right;">
                            <a class="easyui-linkbutton" onclick="doSearch()"><i class="fa fa-search"></i> 查询</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <table id="appreciation_datagrid" fit="true"></table>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>

<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<link rel="stylesheet" type="text/css" href="/supervise/power/css/power.css" />

</head>
<body style="padding-right: 10px; padding-left: 10px; padding-top: 5px"
    onload="doInit();">
    <div id="toolbar" class="titlebar clearfix">
            <div id="outwarp">
        <div class="fl left">
            <img id="img1" class='title_img' src="<%=contextPath%>/common/zt_webframe/imgs/xzbg/clgl/clsqcx.png" />
                <span class="title">行政职权审核</span>
        </div>
        <div class="fr">
            <button class="easyui-linkbutton" onclick="doAuditingGroup()"><i class="fa fa-check"></i> 审核通过</button>
        </div>
    </div>
        <span class="basic_border"></span>
        <div class="" style="padding-top: 5px; padding-bottom: 5px">
            <!-- form表单 -->
            <form id="searchForm">
                <table class="none_table" style="width: 100%;">
                    <tr>
                        <!-- <td style="text-align: right; width: 90px;">职权编号：</td>
                        <td style="width: 220px;">
                            <input class="easyui-textbox" id="powerCode" style="width: 100%;" />
                        </td> -->
                        <!-- <td style="text-align: right; width: 90px;">调整方式：</td>
                        <td style="width: 220px;">
                            <input type="text" name="operationType" id="operationType" class="easyui-textbox" style="width: 100%;" />
                        </td> -->
                        <td style="text-align: right; width: 90px;">职权状态：</td>
                        <td style="width: 120px;">
                            <select name="currentState" id="currentState" class="easyui-combobox" style="width: 120px;" data-options="panelHeight:'auto'">
                                <option value="20">待审核</option>
                                <option value="90">审核通过</option>
                                <option value="99">审核未通过</option>
                            </select>
                        </td>
                        <td style="text-align: right; width: 90px;">职权名称：</td>
                        <td style="width: 200px;">
                            <input type="text" name="name" id="powerName" class="easyui-textbox" style="width: 100%;"
                                data-options="validType:'length[0,200]' " />
                        </td>
                        
                        <td style="text-align: right; width: 90px;">职权类型：</td>
                        <td style="width: 100px;">
                            <input name="powerType" id="powerType" class="easyui-combobox" style="width: 100px;" />
                        </td>
                        <td style="text-align: right; width: 90px;">职权分类：</td>
                        <td >
                            <input name="powerDetail" id="powerDetail" class="easyui-combobox" data-options="multiple:true" style="width: 150px;" />
                            <a class="easyui-linkbutton" onclick="doSearch()"><i class="fa fa-search"></i> 查询</a>
                        </td>
                        <td style="text-align: right;">
                            
                        </td>
                    </tr>
                    <!-- <tr class="power-line-height"></tr>
                    <tr>
                        <td style="text-align: right; width: 90px;">职权类型：</td>
                        <td style="width: 220px;">
                            <input name="powerType" id="powerType" class="easyui-combobox" style="width: 100%;" />
                        </td>
                        <td style="text-align: right; width: 90px;">职权分类：</td>
                        <td style="width: 220px;">
                            <input name="powerDetail" id="powerDetail" class="easyui-combobox" data-options="multiple:true" style="width: 100%;" />
                            
                        </td>
                        <td style="text-align: right; width: 90px;"></td>
                        <td style="width: 220px;">
                            <select name="currentState" id="currentState" class="easyui-combobox" style="width: 100%;">
                                
                            </select>
                        </td>
                        <td style=""></td>
                    </tr> -->
                </table>
            </form>
        </div>
    </div>
    <table id="datagrid" fit="true"></table>
    <script type="text/javascript" src="<%=contextPath%>/supervise/power/js/power_auditing.js"></script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>

    <script type="text/javascript"
            src="<%=contextPath%>/supervise/power/js/power_schedule.js"></script>

    <link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="/supervise/power/css/power.css" />

<title>进度跟踪</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

</head>
<body style="padding-right: 10px; padding-left: 10px; padding-top: 5px"
    onload="doInit();">
    <div id="toolbar" class="titlebar clearfix">
        <div class="fl left">
            <img id="img1" class='title_img'
                src="<%=contextPath%>/common/zt_webframe/imgs/xzbg/clgl/clsqcx.png" />
                <span class="title">进度跟踪</span>
        </div>
        <div class="fr">
            
        </div>
        <span class="basic_border"></span>
        <div class="" style="padding-top: 5px; padding-bottom: 5px">
            <!-- form表单 -->
            <form id="searchForm">
                <table class="none_table" style="width: 100%;">
                    <tr>
                        <td style="text-align: right; width: 90px;">批次编号：</td>
                        <td style="width: 220px;">
                            <input class="easyui-textbox" id="batchCode" style="width: 100%;" data-options="validType:'length[0,20]' "/>
                        </td>
                        <td style="text-align: right; width: 90px;">批次状态：</td>
                        <td style="width: 220px;">
                            <input class="easyui-combobox" id="currentStatus" style="width: 100%;" />
                        </td>
                        <td style="text-align: right;">
                            <a class="easyui-linkbutton" onclick="doSearch()"><i class="fa fa-search"></i> 查询</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <table id="datagrid" fit="true"></table>
</body>
</html>
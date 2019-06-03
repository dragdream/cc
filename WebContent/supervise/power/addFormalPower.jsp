<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>

<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/power/js/addFormalPower.js"></script>


<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />

<title>职权查询</title>
</head>
<body onload="doInit()" fit="true">
    <div id="toolbar" class="clearfix">
        <div class="left fl setHeight">
            <form id="selectFormalPower_form">
                <table style="width: 100%">
                    <tr>
                        <td style="text-indent: 10px; width: 80px;">职权编号：</td>
                        <td style="width: 150px;">
                            <input class="easyui-textbox" id="search_powerCode" name="search_powerCode" style="width: 100%;" />
                        </td>
                        <td style="text-indent: 10px; width: 80px;">职权名称：</td>
                        <td style="width: 150px;">
                            <input type="text" name="search_powerName" id="search_powerName" class="easyui-textbox" style="width: 100%;" />
                        </td>
                        <td style="text-indent: 10px; width: 80px;">职权类型：</td>
                        <td style="width: 100px;">
                            <select name="search_powerType" id="search_powerType" class="easyui-combobox" style="width: 100px;">
                                
                            </select>
                        </td>
                        <td class="text-right">
                            <a class="easyui-linkbutton" onclick="doSearchFormalPower();"><i class="fa fa-search"></i> 查询</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <table id="selectFormalPowerTable" fit="true"></table>
</body>
</html>
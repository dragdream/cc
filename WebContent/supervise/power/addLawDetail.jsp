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
<script type="text/javascript" src="<%=contextPath%>/supervise/power/js/addLawDetail.js"></script>

<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />

<title>依据查询</title>
</head>
<body onload="doInit()" fit="true">
    <div id="toolbar" class="clearfix">
        <div class="left fl setHeight">
            <form id="selectLawDetail_form">
                <table style="width: 100%">
                    <tr>
                        <td style="text-indent: 10px; width: 80px;"><span class="fr">依据名称：</span></td>
                        <td >
                            <input class="easyui-textbox" type="text" id="lawName" name="lawName" />
                        </td>
                        <td style="text-indent: 10px; width: 30px;"><span class="fr">条：</span></td>
                        <td >
                            <input style="width: 50px;" class="easyui-textbox" type="text" id="detailStrip" name="detailStrip" />
                        </td>
                        <td style="text-indent: 10px; width: 30px;"><span class="fr">款：</span></td>
                        <td >
                            <input style="width: 50px;" class="easyui-textbox" type="text" id="detailFund" name="detailFund" />
                        </td>
                        <td style="text-indent: 10px; width: 30px;"><span class="fr">项：</span></td>
                        <td >
                            <input style="width: 50px;" class="easyui-textbox" type="text" id="item" name="item" />
                        </td>
                        <td style="text-indent: 10px; width: 80px;"><span class="fr">依据内容：</span></td>
                        <td >
                            <input style="width: 270px;" class="easyui-textbox" type="text" id="content" name="content" />
                        </td>
                        <td class="text-right">
                            <a class="easyui-linkbutton" onclick="doSearchLawDetail();"><i class="fa fa-search"></i> 查询</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <table id="selectLawDetailTable" fit="true"></table>
</body>
</html>
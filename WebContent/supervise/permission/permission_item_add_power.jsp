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
<%@ include file="/header/validator2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/caseManager/commonCase/css/case.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
</head>
<body onload="doAddPowerInit()" fit="true">
    <div id="toolbar" class="clearfix">
        <div class="left fl setHeight">
            <form id="selectFormalPower_form">
                <input type="hidden" id="actSubject" value="${param.actSubject }"/>
                <input type="hidden" id="powerType" value="${param.powerType }"/>
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
                        <td class="text-right">
                            <a class="easyui-linkbutton" onclick="doSearchAddPower();"><i class="fa fa-search"></i> 查询</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <table id="select_permission_item_add_power_table" fit="true"></table>
    <script type="text/javascript" src="<%=contextPath%>/supervise/permission/js/permission_item_add_power.js"></script>
    
</body>
</html>
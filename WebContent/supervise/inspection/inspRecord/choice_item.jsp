<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="java.util.*,java.text.SimpleDateFormat"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>

<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/inspection/inspRecord/js/choice_item.js"></script>

<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />

<title>选择本次检查内容</title>
</head>
<body onload="doInit()" fit="true">
    <div id="toolbar" class="clearfix">
        <div class="left fl setHeight">
        <input type="hidden" id="inspListId" value="<c:out value='${basicInfo.id}'/>"/>
        <form id="selectInspItem_form">
            <table style="width: 100%">
                <tr>
                    <td style="text-indent: 10px; width: 120px;"><span class="fr">检查项名称：</span></td>
                    <td >
                        <input class="easyui-textbox" type="text" id="inspItemName" name="inspItemName" />
                    </td>
                    <td class="text-right">
                        <a class="easyui-linkbutton" onclick="searchItems();"><i class="fa fa-search"></i> 查询</a>
                    </td>
                </tr>
            </table>
        </form>
        </div>
    </div>
    <table id="record_items_table" fit="true"></table>
</body>
</html>
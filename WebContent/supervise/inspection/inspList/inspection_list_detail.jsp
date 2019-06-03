<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="java.util.*,java.text.SimpleDateFormat"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>

<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/power/css/power.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/pureText-lookInfo.css" />
<link rel="stylesheet" type="text/css" href="/supervise/inspection/inspRecord/css/inspection.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>

<body onload="doInit()">
    <div id="inspectionItem_input_panel" class="easyui-panel tabs-header-border" style="width: 96%; overflow: auto;">
        <input id="listId" name="listId" type="hidden" value="${param.id }"/>
        <form role="form" id="form1" name="form1" enctype="multipart/form-data" method="post" >
            <table class="TableBlock_page lookInfo-lowHeight-table" style="width: 95%; margin: 5px 0px;">
                <tr class="none-border-tr">
                    <td class="insp-td-right3 font-bold-label-td">模版名称：</td>
                    <td class="insp-td-left8" colspan="3" id='listName'></td>
                </tr>
                <tr class="none-border-tr">
                    <td class="insp-td-right3 font-bold-label-td">适用层级：</td>
                    <td class="insp-td-left2" id="applyHierarchyStr"></td>
                    <td class="insp-td-right3 font-bold-label-td" >检查分类：</td>
                    <td class="insp-td-left2" id="listClassifyStr"></td>
                </tr>
                <tr class="none-border-tr">
                    <td class="insp-td-right3 font-bold-label-td">所属领域：</td>
                    <td class="insp-td-left2" id="orgSysName"></td>
                    <td class="insp-td-right3 font-bold-label-td">模版状态：</td>
                    <td class="insp-td-left2" id="currentState"></td>
                </tr>
                <tr class="none-border-tr">
                    <td class="insp-td-right3 font-bold-label-td" valign="top">检查模块：</td>
                    <td class="insp-td-left9 " colspan="3" id="moduleIdsStr"></td>
                </tr>
            </table>
        </form>
    </div>
</body>
<script type="text/javascript" src="<%=contextPath%>/supervise/inspection/inspList/js/inspection_list_detail.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
</html>
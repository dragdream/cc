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

<script type="text/javascript" src="<%=contextPath%>/supervise/inspection/inspItem/js/inspection_item_detail.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/pureText-lookInfo.css" />
<link rel="stylesheet" type="text/css" href="/supervise/power/css/power.css" />
<link rel="stylesheet" type="text/css" href="/supervise/inspection/inspRecord/css/inspection.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body onload="doInit()">
    <div id="inspectionItem_detail_panel" class="easyui-panel tabs-header-border" style="width: 96%; overflow: auto;">
        <input type="hidden" id="itemId" value="<c:out value='${param.id}'/>" />
        <%-- <input type="hidden" id="orgSysCtrl" value="<c:out value='${basicInfo.orgSys}'/>" />
        <input type="hidden" id="loginSubId" value="<c:out value='${basicInfo.loginSubId}'/>" />
        <input type="hidden" id="loginDeptId" value="<c:out value='${basicInfo.loginDeptId}'/>" />
        <input type="hidden" id="ctrlType" value="<c:out value='${basicInfo.ctrlType}'/>" /> --%>
        <form role="form" id="inspection_item_detail_form" name="form1" enctype="multipart/form-data" method="post" data-options="novalidate:true">
            <table class="TableBlock_page lookInfo-lowHeight-table" style="width: 98%; margin: 5px 0px;">
                <tr class="none-border-tr">
                    <td class="power-table-label font-bold-label-td" style="width: 120px;">创建单位：</td>
                    <td colspan="2" id="createOrganizationName" style="width: 200px;">
                    </td>
                </tr>
                <tr class="none-border-tr">
                    <td class="power-table-label font-bold-label-td" style="width: 120px;">创建时间：</td>
                    <td colspan="2" id="createTimeStr" style="width: 200px;" >
                    </td>
                </tr>
                <tr class="none-border-tr">
                    <td class="power-table-label font-bold-label-td" style="width: 120px;">所属领域：</td>
                    <td colspan="2" id="orgSysName" style="width: 200px;">
                    </td>
                </tr>
                <tr class="none-border-tr">
                    <td class="power-table-label font-bold-label-td" style="width: 120px;">检查模块：</td>
                    <td colspan="2" id="moduleName" style="width: 200px;"></td>
                </tr>
                <tr class="none-border-tr">
                    <td class="power-table-label font-bold-label-td" style="width: 120px;">检查项名称：</td>
                    <td colspan="2" id="itemName" style="width: 200px;"></td>
                </tr>
                <tr>
                <%-- <tr class="none-border-tr">
                    <td ><input id="orgSysList" Type="hidden" value="${orgSysLists}" /></td>
                </tr> --%>
            </table>
            <!--         <div style="text-align: center; padding-top: 10px;">
            <button type="button" class="easyui-linkbutton" title="保存" onclick="saveCourtPerform()">
                <span style="padding-right: 2px; width: 40px;"><i class="fa fa-save"></i> 保存</span>
            </button>
        </div> -->
        </form>
    </div>
</body>
</html>
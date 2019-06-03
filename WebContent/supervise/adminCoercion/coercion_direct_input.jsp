<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/upload.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/supervise/adminCoercion/js/coercion_direct_input.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>


<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/power/css/power.css" />
<link rel="stylesheet" type="text/css" href="/supervise/adminCoercion/css/adminCoercion.css" />
<title>行政强制案件填报</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body onload="doInit();" style="padding: 5px; background-color: #fff;">
    <div >
        <form role="form" id="form1" name="form1" enctype="multipart/form-data" method="post" class="easyui-form" data-options="novalidate:true">
            <input type="hidden" id="id" name="id" value="<c:out value = '${baseInfo.id}'/>" />
            <input type="hidden" id="adminDivisionCode" name="adminDivisionCode" value="<c:out value = '${baseInfo.adminDivisionCode}'/>" />
            <table class="TableBlock" style="width: 100%; background: #fff;">
                <tr style="border: none !important;">
                    <td class="power-table-label" style="text-align: right;">
                        请确认数据来源
                    </td>
                    <td style="width: 300px;">
                        <input name="caseSourceType" id="caseSourceType" class="easyui-combobox" 
                        data-options="required:true, missingMessage:'请确认'" 
                        style="width: 89%;" />
                    </td>
                    <td></td>
                    <td></td>
                </tr>
            </table>
        </form>
    </div>
</body>
</html>
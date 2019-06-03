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
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<link rel="stylesheet" type="text/css" href="/supervise/caseCheck/batch/css/batch.css" />
<style type="">
.batch-add-auto-td-class1{
    width: 30% !important;
    height: 40px !important;
    text-align: right;
}
.batch-add-auto-td-class2{
    width: 40% !important;
    height: 40px !important;
    text-align: left;
}
.batch-add-auto-td-class3{
    width: 30% !important;
    height: 40px !important;
    text-align: left;
}
caption{
    font-size: 18px;
    margin: 10px;
}
</style>
</head>
<body onload="doInit()" style="padding-right: 10px; padding-left: 10px; padding-top: 5px;">
    <form method="post" id="batch_add_auto_form" class="easyui-form" style="width: 100%;">
        <input type="hidden" id="batchId" name="batchId" value="${batchId}"/>
        <div class="easyui-panel" style="width: 100%; border: none;" align="center">
            <table class="TableBlock_page" style="width:99%;table-layout: fixed;word-wrap: break-word; word-break: break-all;">
                <caption >按总数抽查：</caption>
                <tr class="common-tr-border">
                    <td class="batch-add-auto-td-class1">抽取数量<span class="required">*</span>：</td>
                    <td class="batch-add-auto-td-class2">
                        <input name="caseNum" id="caseNum" class="easyui-textbox" 
                            style="width: 100%; height: 30px; max-width: 100px;" />
                    </td>
                    <td class="batch-add-auto-td-class3">
                        <span style="color: #ddd;">请输入抽卷数</span>
                    </td>
                </tr>
            </table>
        </div>
    </form>
    <script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/caseManager/commonCase/js/case.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/caseCheck/batch/js/batch_add_2auto_choose.js"></script>
</body>
</html>
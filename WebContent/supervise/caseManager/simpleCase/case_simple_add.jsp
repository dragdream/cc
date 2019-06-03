<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/validator2.0.jsp" %>
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/caseManager/commonCase/css/case.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<title>填报</title>
</head>
<body onload="doInitAdd()" style="padding-right: 10px;padding-left: 10px;padding-top: 5px">
    <div id="common_case_add_tabs" class="easyui-tabs" style="width:100%; height:91%; padding-top: 5px;" data-options="{onSelect:EasyuiTabSwitchBugFunc}">
            <input type="hidden" id="common_case_modelId" value="${modelId }"/>
            <input type="hidden" id="common_case_id" value="${caseId}"/>
   			<input type="hidden" id="common_case_editFlag" value="${param.editFlag}"/>
    </div>
    <div id="simpleBtn" align="center" class="iframeBtns clearfix" style="border-bottom-right-radius: 4px;border-bottom-left-radius: 4px;text-align: center;width:100%;padding: 8px 0;background-color:#fff;float: right;">
        <button  class="btn-alert-blue" id="btn" style="margin-right:14px;letter-spacing: 0; text-indent: 0;" onclick="nextPage('0');">下一步</button>
        <button class="btn-alert-blue" style="" onclick="back()">返回</button>
        <button class="easyui-linkbutton" title="保存" onclick="nextPage('0');">
            <span style="padding-right: 2px; width: 40px;"><i class="fa fa-save"></i> 下一步</span>
        </button>
        &nbsp;&nbsp;
        <button class="easyui-linkbutton" title="返回" onclick="back();">
            <span style="padding-right: 2px; width: 40px;"><i class="fa fa-reply"></i> 返回</span>
        </button>
    </div>
    <script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/caseManager/simpleCase/js/case_simple_add.js"></script>
</body>
</html>
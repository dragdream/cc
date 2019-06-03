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
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/caseManager/commonCase/css/case.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<title>填报</title>
</head>
<body onload="doInitAdd()" style="padding-right: 10px;padding-left: 10px;padding-top: 5px">
    <input type="hidden" id="common_case_id" value="${id}"/>
    <input type="hidden" id="common_case_editFlag" value="${editFlag}"/>
    <input type="hidden" id="common_case_isNext" value="${isNext}"/>
    <input type="hidden" id="common_case_modelId" value="${modelId}"/>
    <div id="common_case_add_tabs" class="easyui-tabs" style="width:100%; height:91%; padding-top: 5px; padding-bottom: 5px; " >
        
    </div>
    <%-- <div id="backBtn" style="border-color: #fff #fff #ddd #fff;padding: 2px;">
            <a href="javascript:void(0);" onclick="back()" style="position: relative;top: 30%;"><i class="fa fa-reply"></i></a>
    </div> --%>
    <div id="simpleBtn" align="center" class="iframeBtns clearfix" style="border-bottom-right-radius: 4px;border-bottom-left-radius: 4px;text-align: center;width:100%;padding: 8px 0;background-color:#fff;float: right;">
        <button class="easyui-linkbutton" title="保存" onclick="doSave('0');">
            <span style="padding-right: 2px; width: 40px;"><i class="fa fa-save"></i> 保存</span>
        </button>
        &nbsp;&nbsp;
        <button class="easyui-linkbutton" title="返回" onclick="back();">
            <span style="padding-right: 2px; width: 40px;"><i class="fa fa-reply"></i> 返回</span>
        </button>
    </div>
    <script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/caseManager/commonCase/js/common_case_add.js"></script>
</body>
</html>
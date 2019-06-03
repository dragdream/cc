<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/validator2.0.jsp"%>
    <link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="/supervise/caseManager/commonCase/css/case.css" />
    <link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
</head>
<body onload="doInitExecute()" style="padding-right: 10px; padding-left: 10px; padding-top: 10px">
    <form method="post" id="common_case_add_5_execute_form">
        <input type="hidden" id="modelId" name="modelId" value="${param.modelId }"/>
        <input type="hidden" id="editFlag" name="editFlag" value="${param.editFlag }"/>
        <input type="hidden" id="caseId" name="caseId" value="${param.caseId }"/>
        <div class="easyui-panel" title="违法行为" style="width: 100%; height: 220px" align="center"
            data-options="tools:'#common_case_add_illegal_hand'" id="case_simple_add_power_datagrid_div">
            <div id="common_case_add_illegal_hand">
                <a href="javascript:void(0);" class="icon-add" onclick="doAddGistLawDetail()" style=""><i class="fa fa-plus"></i></a>
            </div>
            <table id="case_simple_add_power_datagrid" fit="true" style="width: 100%;"></table>
        </div>
        <br/>
        <div class="easyui-panel" title="违法依据" style="width: 100%; height: 220px" align="center" id="case_simple_gist_datagrid_div">
            <table id="case_simple_gist_datagrid" fit="true" style="width: 100%;height: 200px;"></table>
        </div>
        <br />
        <div class="easyui-panel" title="处罚依据" style="width: 100%; height: 220px;margin-bottom: 10px;" align="center" id="case_simple_punish_datagrid_div">
            <table id="case_simple_punish_datagrid" fit="true" style="width: 100%;height: 200px"></table>
        </div>
        <%-- <div style="text-align: center; padding-top: 10px;">
            <a id="doSave" class="easyui-linkbutton" title="保存"
                onclick="doSave(0)"> <span style="padding-right: 2px; width: 40px;"><i class="fa fa-save"></i> 保存</span>
            </a> <a id="doSaveSumit" class="easyui-linkbutton" title="保存并提交" onclick="doSave(1)"> 
            <span style="padding-right: 2px; width: 90px;"><i class="fa fa-save"></i> 保存并提交</span>
            </a>
        </div> --%>
    </form>
    <script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/caseManager/simpleCase/js/case_simple_add_5_execute.js"></script>
</body>
</html>
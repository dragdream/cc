<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/analysis/css/analysis.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<title>行政处罚统计</title>
</head>
<body onload="doInit()" style="padding-right: 10px;padding-left: 10px;padding-top: 5px">
    <div id="toolbar" class="titlebar clearfix">
        <div id="outwarp">
            <div class="fl left">
                <img id="img1" class='title_img' src="<%=contextPath%>/common/zt_webframe/imgs/xzbg/clgl/clsqcx.png" />
                    <span class="title">行政处罚统计</span>
            </div>
        </div>
        <span class="basic_border"></span>
        <div class="" style="padding-top: 7px; padding-bottom: 7px">
            <!-- form表单 -->
            <form id="search_form">
                <input type="hidden" id="isSubmit" name="isSubmit" value='1'/>
                <input type="hidden" id="common_case_search_index_menuGroupStrNames" name="menuNames" value='${menuGroupStrNames}'/>
                &nbsp;入库日期：
                <input type="text" name='begincreateTimeStr' id="begincreateTimeStr"
                    class="easyui-datebox" style="width:110px; height:30px;" data-options="validType:'date'"/>
                &nbsp;-&nbsp;
                <input type="text" name='endcreateTimeStr' id="endcreateTimeStr"
                    class="easyui-datebox" style="width:110px; height:30px;" data-options="validType:'date'"/>
                <%-- &nbsp;&nbsp;处罚决定书文号：
                <input name="punishmentCode" id="punishmentCode" class="easyui-textbox" style="width: 120px; height:30px;"
                    data-options="validType:'length[0,50]'" />
                &nbsp;&nbsp;结案状态：
                <select name="closedState" id="closedState" class="easyui-combobox" style="width: 120px; height:30px;"
                    data-options="" >
                </select> --%>
                &nbsp;&nbsp;
                <a class="easyui-linkbutton" onclick="query()"><i class="fa fa-search"></i> 查 询</a>
                &nbsp;
                <a class="easyui-linkbutton" onclick="exportExcel()"><i class="fa fa-download"></i> 导 出</a>
            </form>
        </div>
    </div>
    <table id="case_stage_datagrid" fit="true"></table>
    <iframe id="frame0" style="display:none"></iframe>
    <script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/caseManager/commonCase/js/case.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/analysis/js/caseStage.js"></script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/ztree.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/supervise/common/css/supervise.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/supervise/caseCheck/rules/css/rules.css" />
<script type="text/javascript">
</script>
</head>
<body onload="doInit()" style="padding-right: 10px; padding-left: 10px; padding-top: 5px">
<div id="toolbar" class="titlebar clearfix">
    <div id="outwarp">
        <div class="fl left">
            <img id="img1" class='title_img' src="<%=contextPath%>/common/zt_webframe/imgs/xzbg/clgl/clsqcx.png"/> <span class="title">评查细则管理</span>
        </div>
        <div class="fr">
            <button class="easyui-linkbutton" onclick="addVersion()">
                <i class="fa fa-plus"></i>&nbsp;新增版本
            </button>
            &nbsp;&nbsp;
            <button class="easyui-linkbutton" onclick="addRateRules()">
                <i class="fa fa-plus"></i>&nbsp;新增评分细则
            </button>
            &nbsp;&nbsp;
            <button class="easyui-linkbutton" onclick="expRateRules()">
                <i class="fa fa-download"></i>&nbsp;导出评分细则
            </button>
            &nbsp;&nbsp;
            <button class="easyui-linkbutton" onclick="templateDownload()">
                <i class="fa fa-download"></i>&nbsp;模板下载
            </button>
        </div>
    </div>
    <span class="basic_border"></span>
    <div id="rules_search_div" class="" style="padding-top: 5px; padding-bottom: 5px">
        <!-- form表单 -->
        <form id="rules_search_form">
            <div class="left">
                <span>评分版本：</span>
                <select id="rulesVersion" name="rulesVersion" class="easyui-combobox" style="width:200px;" panelMaxHeight="150px">
                </select>
                <span>适用范围：</span>
                <select id="rulesSuitRange" name="rulesSuitRange" class="easyui-combobox" style="width:200px;" panelMaxHeight="150px">
                </select>
                <span>评查项目：</span>
                <input id="subject" name="subject" class="easyui-textbox" style="width:200px;"/>
            </div>
            <div class="left">
                <span>一级指标：</span>
                <select id="firstLevel" class="easyui-combobox" style="width:200px;" panelMaxHeight="150px">
                </select>
                <span>二级指标：</span>
                <select  id="secLevel" class="easyui-combobox" style="width:200px;" panelMaxHeight="150px">
                </select>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <a class="easyui-linkbutton" onclick="rulesSearch()"><i class="fa fa-search"></i> 查询</a>
                </span>
            </div>
        </form>
    </div>
</div>
<table id="rules_table" fit="true"></table>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/caseCheck/rules/js/rules_index.js"></script>
</body>
</html>
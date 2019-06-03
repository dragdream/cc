<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String id = request.getParameter("id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/ztree.jsp"%>
<%@ include file="/header/upload.jsp"%>

<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/gray/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/supervise/lawManage/css/lawManage.css" />
<script type="text/javascript">
var id = '<%=id%>';
</script>
</head>
<body onload="doInit()">
    <form  id= "form1" class="easyui-form" style="width:100%;height:100%;">
    <div id="toolbar" class="titlebar clearfix">
        <div style="padding: 5px 5px; margin: 5px 5px;">
            <input type="hidden" id="lawAdjustId" value="<%=id%>" />
                <div style="padding: 5px 0px;">
                    <div style="width:15%;float:left;text-align:right;">
                        <span>内容格式<span class="required">*</span>：&nbsp;</span>
                    </div>
                    <div style="width:84%;float:left;" id="contentType_radio_group">
                        <input class="law-content-type" name="contentType" value="A" label="A：编、章、节、条" /> 
                        <input class="law-content-type" name="contentType" value="B" label="B：1、1.1、1.1.1" /> 
                        <input class="law-content-type" name="contentType" value="C" label="C：一、二、三、四" /> 
                        <input class="law-content-type" name="contentType" value="D" label="D：整部" />
                    </div>
                </div>
                <br/>
                <div style="padding: 5px 0px;">
                    <div style="width:15%;float:left;text-align:right;">
                        <span>法律法规<span class="required">*</span>：&nbsp;</span>
                    </div>
                    <div id="attachDiv" style="width:75%px;float:left;"></div>
                    <div id="fileContainer2" style="width:100px;float:left;"></div>
                    <div id="renderContainer2" style="width:100px;float:left;"></div>
                    <div style="width:9%;float:left;">
                        <a id="uploadHolder2" class="add_swfupload"><img src="<%=systemImagePath%>/upload/batch_upload.png" />替换原文</a>
                        <input id="attaches" name="attaches" type="hidden" />
                    </div>
                    <div style="clear:both"></div>
                </div>
                <div style="padding: 5px 0px;text-align:center;">
                    <button class="easyui-linkbutton center" id="lawManageBtn" onclick="splitLawWordFile()">
                        <i class="fa fa-share-alt"></i>&nbsp;拆分
                    </button>
                </div>
            
        </div>
    </div>
    <table id="lawSplit_result_table" fit="true" ></table>
    <br />
</form>
    <script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/lawManage/lawExamine/js/law_importPage.js"></script>
</body>
</html>
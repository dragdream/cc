<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>

<script type="text/javascript" src="<%=contextPath%>/supervise/system/sysDepartment/js/sysDepartment_index.js"></script>
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<title>监督业务管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>

<body onload="doInit()" style="padding-right: 10px;padding-left: 10px;padding-top: 5px">
    <div id="toolbar" class="titlebar clearfix">
        <div class="fl left">
            <img id="img1" class='title_img'
                src="<%=contextPath%>/common/zt_webframe/imgs/xzbg/clgl/clsqcx.png" />
                <span class="title">监督业务管理</span>
        </div>
        <div class="right fr t_btns">
        </div>
        <span class="basic_border"></span> 
        <div class="" style="padding-top: 5px; padding-bottom: 5px">
            <form id="form1">
                <table class="none_table" style="width: 100%;">
                    <tr>
                        <td style="text-indent: 10px; width: 80px;">机构名称</td>
                        <td style="width: 110px;">
                            <input class="easyui-textbox" name="deptName" id="deptName" style="width: 98%;" />
                        </td>
                        <td style="text-indent: 10px; width: 105px;">执法部门名称</td>
                        <td style="width: 110px;">
                            <input class="easyui-textbox" name="businessDeptName" id="businessDeptName" style="width: 98%;" />
                        </td>
                    </tr>
                    <tr>
                        <td style="text-indent: 10px; width: 105px;">监督部门名称</td>
                        <td style="width: 110px;">
                            <input class="easyui-textbox" name="businessSupDeptName" id="businessSupDeptName" style="width: 98%;" />
                        </td>
                        <td style="text-indent: 10px; width: 80px;">主体名称</td>
                        <td style="width: 110px;">
                            <input class="easyui-textbox" name="businessSubjectName" id="businessSubjectName" style="width: 98%;" />
                        </td>
                        <td style="text-align: right;">
                            <a class="easyui-linkbutton" onclick="doSearch()"><i class="fa fa-search"></i>&nbsp查&nbsp询</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <table id="datagrid" fit="true"></table>
</body>
</html>
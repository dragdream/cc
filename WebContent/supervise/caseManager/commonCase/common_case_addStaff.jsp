<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
    <link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="/supervise/caseManager/commonCase/css/case.css" />
    <link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
    <title>添加执法人员</title>
</head>
<body onload="doInitAddPerson()" fit="true">
    <div id="toolbar" class="clearfix">
        <div class="left fl setHeight">
            <form id="selectFormalPerson_form">
                <input type="hidden" id="common_case_addPerson_subjectId" value="${subjectId}"/>
                <table style="width: 100%">
                    <tr>
                        <td style="text-indent: 10px; width: 80px;">执法人员：</td>
                        <td style="width: 150px;">
                            <input class="easyui-textbox" id="common_case_addPerson_name" name="common_case_addPerson_name" style="width: 100%;" />
                        </td>
                        <td style="text-indent: 10px; width: 100px;">执法证号：</td>
                        <td style="width: 150px;">
                            <input class="easyui-textbox" name="common_case_addPerson_code" id="common_case_addPerson_code" class="easyui-textbox" style="width: 100%;" />
                        </td>
                        <td class="text-right">
                            <a class="easyui-linkbutton" onclick="doSearchAddStaff();"><i class="fa fa-search"></i> 查询</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <table id="select_common_case_addPerson_table" fit="true"></table>
    <script type="text/javascript" src="<%=contextPath%>/supervise/caseManager/commonCase/js/common_case_addStaff.js"></script>
</body>
</html>
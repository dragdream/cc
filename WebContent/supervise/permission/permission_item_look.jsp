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
    <link rel="stylesheet" type="text/css" href="/supervise/common/css/pureText-lookInfo.css" />
    <link rel="stylesheet" type="text/css" href="/supervise/permission/css/permission.css" />
    <title>查看</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/common/js/md5.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
</head>
<body  onload="doInit();">
    <form role="form" id= "form1" name = "form1"  enctype="multipart/form-data" method="post" class="easyui-form" data-options="novalidate:true">
        <div class="easyui-tabs" style="width:100%;height:500px;">
        <div id="permission_item_div" title="基本信息" >
            <input type="hidden" id="id" name = "id" value="${param.id }"/>
            <table class="TableBlock_page lookInfo-lowHeight-table" frame="void" rules="none" fit="true" >
                <tr class="tr-title1"></tr>
                <tr>
                    <td class="td-title1 font-bold-label-td" >事项名称：</td>
                    <td class="td-title2" id="name" name="name">
                    </td>
                    <td class="td-title1 font-bold-label-td" >许可主体：</td>
                    <td class="td-title2" id="subjectName" name="subjectName">
                    </td>
                    <td class="td-title4"></td>
                </tr>
                <tr>
                    <td class="td-title1 font-bold-label-td">办件类型：</td>
                    <td class="td-title2" id="listTypeValue" name="listTypeValue">
                    </td>
                    <td class="td-title1 font-bold-label-td"></td>
                    <td class="td-title2"></td>
                    <td class="td-title4"></td>
                </tr>
                <tr>
                    <td class="td-title1 font-bold-label-td">法定办结时限：</td>
                    <td class="td-title2">
                        <span name="statutoryTimeLimit" id="statutoryTimeLimit"></span>
                    </td>
                    <td class="td-title1 font-bold-label-td">承诺办结时限：</td>
                    <td class="td-title2">
                        <span id="promisedTimeLimit" name="promisedTimeLimit"></span>
                    </td>
                    <td class="td-title4"></td>
                </tr>
                <tr>
                    <td class="td-title1 font-bold-label-td">服务对象：</td>
                    <td class="td-title2" id="partyTypeValue" name="partyTypeValue">
                    </td>
                    <td class="td-title1 font-bold-label-td">办理形式：</td>
                    <td class="td-title2" id="handleWayValue" name="handleWayValue">
                    </td>
                    <td class="td-title4"></td>
                </tr>
                <tr>
                    <td class="td-title1 font-bold-label-td">申请材料：</td>
                    <td colspan="3" name="applicationMaterials" id="applicationMaterials">
                    </td>
                    <td class="td-title4"></td>
                </tr>
                <tr class="tr-title1"></tr>
                <tr>
                    <td class="td-title1 font-bold-label-td">是否收费：</td>
                    <td class="td-title2" name="isCollectFees" id="isCollectFees">
                    </td>
                    <td class="td-title1 font-bold-label-td"></td>
                    <td class="td-title2"></td>
                    <td class="td-title4"></td>
                </tr>
                <tbody id="isCollectFees_tbody" style="display: none;">
                <tr>
                    <td class="td-title1 font-bold-label-td">收费标准：</td>
                    <td colspan="3" name="collectFeesRates" id="collectFeesRates">
                    </td>
                    <td class="td-title4"></td>
                </tr>
                </tbody>
            </table>
        </div>
        <div id="power" title="关联职权及依据" style="padding:10px">
            
            <div id="setting_panel" class="easyui-panel" title="许可职权" style="width:100%; height:210px;" >
                <table id="powerDatagrid" fit="true"></table>
            </div>
            
            <br />
            <div id="gist_panel" class="easyui-panel" title="设定依据" style="width:100%;height:210px;" >
                <table id="settingDatagrid" fit="true"></table>
            </div>
        </div>
        </div>
    </form>
    <script type="text/javascript" src="<%=contextPath%>/supervise/permission/js/permission_item_look.js"></script>
</body>
</html>
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
<link rel="stylesheet" type="text/css" href="/supervise/permission/permissionSearch/css/permission.css" />
</head>
<body onload="doInit()" style="padding-right: 10px; padding-left: 10px; padding-top: 5px;">
    <form method="post" id="permission_list_search_form" class="easyui-form" style="width: 100%;" data-options="novalidate:true">
        <%-- 条件页 --%>
        <div style="width: 100%;" align="center" id="permissionSearchDiv">
            <div class="easyui-panel" title="基本信息" style="width: 100%;" align="center">
                <table class="TableBlock_page" fit="true" style="width: 990px;" align="center">
                    <tr class="common-tr-border" style="height: 10px;"></tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1"></td>
                        <td class="case-common-td-class1">办理主体：</td>
                        <td class="case-common-td-class2" id="subjectId_td">
                            <select name="subjectId" id="subjectId" class="easyui-textbox"
                                style="width: 98%; height: 30px;" data-options="required:true, novalidate:true, missingMessage:'请选择所属地区' ">
                            </select>
                        </td>
                        <td class="case-common-td-class1"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1"></td>
                        <td class="case-common-td-class1">许可事项：</td>
                        <td class="case-common-td-class2" id="itemId_td">
                            <select name="itemId" id="itemId" class="easyui-textbox"
                                style="width: 98%; height: 30px;" data-options="required:true, novalidate:true, missingMessage:'请选择所属地区' ">
                            </select>
                        </td>
                        <td class="case-common-td-class1"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1"></td>
                        <td class="case-common-td-class1">审核类型：</td>
                        <td class="case-common-td-class2" id="permissionType_td">
                        </td>
                        <td class="case-common-td-class1"></td>
                    </tr>
                    <!-- <tr class="common-tr-border">
                        <td class="case-common-td-class1"></td>
                        <td class="case-common-td-class1">办件类型：</td>
                        <td class="case-common-td-class2" id="xkBjlx_td">
                        </td>
                        <td class="case-common-td-class1"></td>
                    </tr> -->
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1"></td>
                        <td class="case-common-td-class1">决定书文号：</td>
                        <td class="case-common-td-class2" id="decisionCode_td">
                            <input name="decisionCode" id="decisionCode" class="easyui-textbox"
                                style="width: 98%; height: 30px;" data-options="required:true, novalidate:true, missingMessage:'请选择所属地区' "/>
                        </td>
                        <td class="case-common-td-class1"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1"></td>
                        <td class="case-common-td-class1">许可证书编号：</td>
                        <td class="case-common-td-class2" id="certificateCode_td">
                            <input name="certificateCode" id="certificateCode" class="easyui-textbox"
                                style="width: 98%; height: 30px;" data-options="required:true, novalidate:true, missingMessage:'请选择所属地区' "/>
                        </td>
                        <td class="case-common-td-class1"></td>
                    </tr>
                    <!-- <tr class="common-tr-border">
                        <td class="case-common-td-class1"></td>
                        <td class="case-common-td-class1">当前状态：</td>
                        <td class="case-common-td-class2" id="xkZt_td">
                        </td>
                        <td class="case-common-td-class1"></td>
                    </tr> -->
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1"></td>
                        <td class="case-common-td-class1">有效截止日期：</td>
                        <td class="case-common-td-class2" id="endValidDate_td">
                            <input name="endValidDateStr" id="endValidDateStr"
                            class="easyui-datebox" style="width:250px;height:30px;" data-options="validType:'date'"/>
                        &nbsp;&nbsp;&nbsp;至&nbsp;
                        <input name="endEndValidDateStr" id="endEndValidDateStr"
                            class="easyui-datebox" style="width:250px;height:30px;" data-options="validType:'date'"/>
                        </td>
                        <td class="case-common-td-class1"></td>
                    </tr>
                    <tr class="common-tr-border" style="height: 10px;"></tr>
                </table>
            </div>
            <br />
            <div class="easyui-panel" title="行政相对人信息" style="width: 100%;" align="center">
                <table class="TableBlock_page" fit="true" style="width: 990px;">
                    <tr class="common-tr-border" style="height: 10px;"></tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1"></td>
                        <td class="case-common-td-class1">相对人类型：</td>
                        <td class="case-common-td-class2" id="partyType_td">
                        </td>
                        <td class="case-common-td-class1"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1"></td>
                        <td class="case-common-td-class1">相对人名称：</td>
                        <td class="case-common-td-class2" id="partyName_td">
                            <input name="partyName" id="partyName" class="easyui-textbox"
                                style="width: 98%; height: 30px;" data-options="required:true, novalidate:true, missingMessage:'请选择所属地区' "/>
                        </td>
                        <td class="case-common-td-class1"></td>
                    </tr>
                    <tr class="common-tr-border" style="height: 10px;"></tr>
                </table>
            </div>
            <br />
            <div style="text-align: center; width: 100%;height: 50px;"></div>
            <div class="easyui-panel" style="" id="buttonDiv">
                    <button class="easyui-linkbutton" title="查询" onclick="permissionSearch();return false;" id="btn">
                        <span style="padding-right: 2px; width: 40px;"><i class="fa fa-search"></i> 查 询</span>
                    </button>
                    <button class="easyui-linkbutton" title="重置" onclick="permissionRefresh()" id="btn">
                        <span style="padding-right: 2px; width: 40px;"><i class="fa fa-refresh"></i> 重 置</span>
                    </button>
            </div>
            <!-- <div style="text-align: center; padding-top: 10px;">
                    <button class="easyui-linkbutton" title="查询" onclick="permissionSearch();return false;" id="btn">
                        <span style="padding-right: 2px; width: 40px;"><i class="fa fa-search"></i> 查 询</span>
                    </button>
                    <button class="easyui-linkbutton" title="重置" onclick="permissionRefresh()" id="btn">
                        <span style="padding-right: 2px; width: 40px;"><i class="fa fa-refresh"></i> 重 置</span>
                    </button>
            </div> -->
            <br/>
        </div>
        <%-- 查询页 --%>
        <div style="width: 100%;height:100%" align="center" id="permissionListDiv">
            <div id="toolbar" class="titlebar clearfix">
                <div id="outwarp">
                    <div class="fl left">
                        <img id="img1" class='title_img' src="<%=contextPath%>/common/zt_webframe/imgs/xzbg/clgl/clsqcx.png" />
                        <span class="title">许可办件综合查询</span>
                    </div>
                    <div class="fr">
                        <button class="easyui-linkbutton" onclick="exportPermissionList()"><i class="fa fa-download"></i>&nbsp;导&nbsp;出</button>
                        <button class="easyui-linkbutton" onclick="back()"><i class="fa fa-reply"></i>&nbsp;返&nbsp;回</button>
                    </div>
                </div>
                <span class="basic_border"></span>
                <div class="" style="padding-top: 5px;display:inline-block;">
                    <span style="float:left;height: 26px;line-height: 26px;margin:4px;">&nbsp;已选条件：</span>
                    <span id="conditionDiv"></span>
                </div>
                <div id="optionCom" >
                    <span class="isshow"><i class="optional fa fa-bars fa-lg"></i>可选列  </span> 
                    <span class="tip">可根据需要"隐藏/显示"所选列</span> 
                    <ul class="panList"></ul>
                </div>
            </div>
            <table id="permission_list_index_datagrid" fit="true"></table>
        </div>
    </form>
    <script type="text/javascript" src="<%=contextPath%>/common/js/juicer-min.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/permission/permissionSearch/js/permission_list_search.js"></script>
</body>
</html>
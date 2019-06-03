<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="java.util.*,java.text.SimpleDateFormat"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>

<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/pureText-lookInfo.css" />
<link rel="stylesheet" type="text/css" href="/supervise/inspection/inspRecord/css/inspection.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />

<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
</head>
<body onload="doInit()">
    <div id="inspectionItem_input_panel" style="width: 100%; padding-top: 10px">
        <input type="hidden" id="listId" value="${param.id }"/>
        <input type="hidden" id="loginSubId" value="${param.loginSubId}"/>
        <input type="hidden" id="loginDeptId" value="${param.loginDeptId}"/> 
        <input type="hidden" id="orgSys" value="${param.orgSys}" />
<%--         <input type="hidden" id="listId" value="<c:out value='${ilModel.id}'/>" /> --%>
<%--         <input type="hidden" id="orgSys" value="<c:out value='${oiModel.orgSysCode}'/>" /> --%>
<%--         <input type="hidden" id="levelCode" value="<c:out value='${oiModel.levelCode}'/>" /> --%>
<%--         <input type="hidden" id="loginSubId" value="<c:out value='${oiModel.subjectId}'/>" /> --%>
<%--         <input type="hidden" id="loginDeptId" value="<c:out value='${oiModel.departId}'/>" /> --%>
<%--         <input type="hidden" id="applyHierarchy" value="<c:out value='${ilModel.applyHierarchy}'/>" /> --%>
        <%-- <input type="hidden" id="ctrlType" value="<c:out value='${basicInfo.ctrlType}'/>" />  --%>
<%--         <input type="hidden" id="isSpotCheck" value="<c:out value='${ilModel.isSpotCheck}'/>" />  --%>
        <div class="easyui-tabs" style="width: 100%; padding-right: 10px; padding-left: 10px; padding-top: 5px">
            <div id="" title="基础信息">
                <form role="form" id="inspection_list_input_form" name="form1" enctype="multipart/form-data" method="post" data-options="novalidate:true">
                    <table class="TableBlock_page lookInfo-lowHeight-table" style="table-layout: fixed; width: 95%; margin: 5px 0px; ">
                        <tr class="none-border-tr">
                            <td class="insp-td-right3 font-bold-label-td font-bold-label-td" >统一社会信用代码：</td>
                            <td class="insp-td-left2" id='code'></td>
                            <td class="insp-td-right2 font-bold-label-td font-bold-label-td" >行使层级：</td>
                            <td class="insp-td-left2" id="applyHierarchyStr"></td>
                            <td class="insp-td-left0"></td>
                        </tr>
                        <tr class="none-border-tr">
                            <td class="insp-td-right3 font-bold-label-td">事项名称：</td>
                            <td class="insp-td-left2" id="listName"></td>
                            <td class="insp-td-right2 font-bold-label-td">实施清单编码：</td>
                            <td class="insp-td-left2" id="inspListCode"></td>
                            <td class="insp-td-left0"></td>
                        </tr>
                        <tr class="none-border-tr">
                            <td class="insp-td-right3 font-bold-label-td">检查类别：</td>
                            <td class="insp-td-left2" id="inspType"></td>
                            <td class="insp-td-right2 font-bold-label-td">检查方式：</td>
                            <td class="insp-td-left2" id="inspWay"></td>
                            <td class="insp-td-left0"></td>
                        </tr>
                        <tr class="none-border-tr">
                            <td class="insp-td-right3 font-bold-label-td">是否纳入双随机：</td>
                            <td class="insp-td-left2" id="isDoubleRandom"></td>
                            <td class="insp-td-right2 font-bold-label-td">是否抽查：</td>
                            <td class="insp-td-left2" id="isSpotCheck"></td>
                            <td class="insp-td-left0"></td>
                        </tr>
                        <tr class="none-border-tr" style="display: none" id="doubleRandomCascade">
                            <td class="insp-td-right3 font-bold-label-td">检查比例：</td>
                            <td class="insp-td-left2" id="inspProportion"></td>
                            <td class="insp-td-right2 font-bold-label-td">检查频次：</td>
                            <td class="insp-td-left2" id="inspFrequency"></td>
                            <td class="insp-td-left0"></td>
                        </tr>
                        <tr class="none-border-tr">
                            <td class="insp-td-right3 font-bold-label-td">是否协同：</td>
                            <td class="insp-td-left2" id="isSynergy"></td>
                            <td class="insp-td-right2 font-bold-label-td synergy-cascade"  style="display: none" >协同相关机构：</td>
                            <td class="insp-td-left2 synergy-cascade" style="display: none" id="synergyOrgan"></td>
                            <td class="insp-td-left0"></td>
                        </tr>
                        <tr class="none-border-tr">
                            <td class="insp-td-right3 font-bold-label-td">事项状态：</td>
                            <td class="insp-td-left2" id="currentState"></td>
                            <td class="insp-td-right2 font-bold-label-td">事项版本：</td>
                            <td class="insp-td-left2" id="version" ></td>
                            <td class="insp-td-left0"></td>
                        </tr>
                        <tr class="none-border-tr">
                            <td class="insp-td-right3 font-bold-label-td">计划生效时间：</td>
                            <td class="insp-td-left2" id="planEffectDateStr"></td>
                            <td class="insp-td-right2 font-bold-label-td">计划取消时间：</td>
                            <td class="insp-td-left2" id="planCancelDateStr" ></td>
                            <td class="insp-td-left0"></td>
                        </tr>
                        <tr class="none-border-tr">
                            <td class="insp-td-right3 font-bold-label-td">监管抽检内容：</td>
                            <td class="insp-td-left7" colspan="3" id="superviseCheckContent"></td>
                            <td class="insp-td-left0"></td>
                        </tr>
                        <tr class="none-border-tr">
                            <td class="insp-td-right3 font-bold-label-td">办理流程：</td>
                            <td class="insp-td-left7" colspan="3" id="handleProcess"></td>
                            <td class="insp-td-left0"></td>
                        </tr>
                    </table>
                </form>
            </div>
            <div id="insp_list_item" title="检查项" >
                <div class="easyui-panel" style="width: 100%; height:440px" title="检查项" align="center" data-options="tools:'#insp_item_add'">
                    <table fit="true" id="inspItemDatagrid" ></table>
                </div>
            </div>
            <div id="insp_list_power" title="行政职权">
                <div id="setting_panel" class="easyui-panel" title="检查职权" style="width:100%; height:210px;" data-options="tools:'#settingTools'" >
                    <table id="powerDatagrid" fit="true"></table>
                </div>
                <br />
                <div id="gist_panel" class="easyui-panel" title="设定依据" style="width:100%;height:210px;" >
                    <table id="settingDatagrid" fit="true"></table>
                </div>
            </div>
        </div>
    </div>
    <script>
    </script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/inspection/inspList/js/insp_list_detail.js"></script>
</body>
</html>
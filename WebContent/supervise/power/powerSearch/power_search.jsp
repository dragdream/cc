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
<link rel="stylesheet" type="text/css" href="/supervise/power/powerSearch/css/power.css" />
</head>
<body onload="doInit()" style="padding-right: 10px; padding-left: 10px; padding-top: 5px;">
    <form method="post" id="power_search_form" class="easyui-form" style="width: 100%;" data-options="novalidate:true">
        <%-- 条件页 --%>
        <div style="width: 100%;" align="center" id="powerSearchDiv">
            <div class="easyui-panel" title="基本信息" style="width: 100%;" align="center">
                <table class="TableBlock_page" fit="true" style="width: 990px;" align="center">
                    <tr class="common-tr-border" style="height: 10px;"></tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1">职权名称：</td>
                        <td class="case-common-td-class2" id="name_td">
                            <input name="name" id="name" class="easyui-textbox"
                                style="width: 100%; height: 30px;max-width: 600px;" data-options="required:true, novalidate:true, missingMessage:'请选择所属地区' "/>
                        </td>
                        <td class="case-common-td-class0"></td>
                    </tr>
                    <%-- <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1">职权层级：</td>
                        <td class="case-common-td-class2" id="powerLevel_td">
                        </td>
                        <td class="case-common-td-class0"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1">行政区划：</td>
                        <td class="case-common-td-class2" id="area_td">
                            <select name="area" id="area" class="easyui-textbox"
                                style="width: 100%; height: 30px;max-width: 600px;">
                            </select>
                        </td>
                        <td class="case-common-td-class0"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1">职权领域：</td>
                        <td class="case-common-td-class2" id="powerMold_td">
                            <input name="powerMold" id="powerMold" class="easyui-textbox"
                                style="width: 100%; height: 30px;max-width: 600px;"/>
                        </td>
                        <td class="case-common-td-class0"></td>
                    </tr> --%>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1">执法主体：</td>
                        <td class="case-common-td-class2" id="subjectId_td">
                            <select name="subjectId" id="subjectId" class="easyui-textbox"
                                style="width: 100%; height: 30px;max-width: 600px;" data-options="required:true, novalidate:true, missingMessage:'请选择所属地区' ">
                            </select>
                        </td>
                        <td class="case-common-td-class0"></td>
                    </tr>
                    <%-- <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1">实施主体：</td>
                        <td class="case-common-td-class2" id="powerSubjectId_td">
                            <select name="powerSubjectId" id="powerSubjectId" class="easyui-textbox"
                                style="width: 100%; height: 30px;max-width: 600px;" data-options="required:true, novalidate:true, missingMessage:'请选择所属地区' ">
                            </select>
                        </td>
                        <td class="case-common-td-class0"></td>
                    </tr> --%>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1">是否涉刑：</td>
                        <td class="case-common-td-class2" nowrap id="isCriminal_td">
                        </td>
                        <td class="case-common-td-class0"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1">入库日期：</td>
                        <td class="case-common-td-class2">
                            <input name="begincreateDateStr" id="begincreateDateStr" value=""
                                class="easyui-datebox" style="width:45%;height:30px;max-width: 250px;" data-options="validType:'date'"/>
                            &nbsp;-&nbsp;
                            <input name="endcreateDateStr" id="endcreateDateStr" value=""
                                class="easyui-datebox" style="width:45%;height:30px;max-width: 250px;" data-options="validType:'date'"/>
                        </td>
                        <td class="case-common-td-class0"></td>
                    </tr>
                    <tr class="common-tr-border" style="height: 10px;"></tr>
                </table>
            </div>
            <br />
            <div class="easyui-panel" title="职权类型" style="width: 100%;" align="center">
                <table class="TableBlock_page" fit="true" style="width: 990px;">
                    <tr class="common-tr-border" style="height: 10px;"></tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1 power-area-top">行政许可：</td>
                        <td class="case-common-td-class2" id="permissionPowerType_td">
                        </td>
                        <td class="case-common-td-class0"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1 power-area-top">行政处罚：</td>
                        <td class="case-common-td-class2" id="punishPowerType_td">
                        </td>
                        <td class="case-common-td-class0"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1 power-area-top">行政强制：</td>
                        <td class="case-common-td-class2" id="coercionPowerType_td">
                        </td>
                        <td class="case-common-td-class0"></td>
                    </tr>
                    <tr class="common-tr-border" style="height: 10px;"></tr>
                </table>
            </div>
            <br />
            <%-- <div class="easyui-panel" title="法律依据" style="width: 100%;" align="center">
                <table class="TableBlock_page" fit="true" style="width: 990px;">
                    <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1">法律法规类别：</td>
                        <td class="case-common-td-class2" id="lawType_td">
                        </td>
                        <td class="case-common-td-class0"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1">发布机关：</td>
                        <td class="case-common-td-class2">
                            <input name="departmentId" id="departmentId" class="easyui-textbox"
                                style="width: 100%; height: 30px;max-width: 600px;"/>
                        </td>
                        <td class="case-common-td-class0"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1">法律法规名称：</td>
                        <td class="case-common-td-class2" id="lawsName_td">
                            <input name="lawsName" id="lawsName" class="easyui-textbox"
                                style="width: 100%; height: 30px;max-width: 600px;" data-options="required:true, novalidate:true, missingMessage:'请选择所属地区' "/>
                        </td>
                        <td class="case-common-td-class0"></td>
                    </tr>
                    <tr class="common-tr-border" style="height: 10px;"></tr>
                </table>
            </div>
            <br /> --%>
            <%-- <div class="easyui-panel" title="执行情况" style="width: 100%;" align="center">
                <table class="TableBlock_page" fit="true" style="width: 990px;">
                    <tr class="common-tr-border" style="height: 10px;"></tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1">本年度案件数量：</td>
                        <td class="case-common-td-class2">
                            <span id="caseNum_td"></span>
                            <span id="caseNumAuto_span" style="display: none;">
                                <input name="mincaseNum" id="mincaseNum" class="easyui-textbox" 
                                    style="width: 80px; height: 30px;" />
                                &nbsp;-&nbsp;
                                <input name="maxcaseNum" id="maxcaseNum" class="easyui-textbox" 
                                    style="width: 80px; height: 30px;" />
                            </span>
                        </td>
                        <td class="case-common-td-class0"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class0"></td>
                        <td class="case-common-td-class1">上年度案件数量：</td>
                        <td class="case-common-td-class2">
                            <span id="caseNumBefore_td"></span>
                            <span id="caseNumAutoBefore_span" style="display: none;">
                                <input name="mincaseNumBefore" id="mincaseNumBefore" class="easyui-textbox" 
                                    style="width: 80px; height: 30px;" />
                                &nbsp;-&nbsp;
                                <input name="maxcaseNumBefore" id="maxcaseNumBefore" class="easyui-textbox" 
                                    style="width: 80px; height: 30px;" />
                            </span>
                        </td>
                        <td class="case-common-td-class0"></td>
                    </tr>
                    <tr class="common-tr-border" style="height: 10px;"></tr>
                </table>
            </div> --%>
            <div style="text-align: center; width: 100%;height: 50px;"></div>
            <div class="easyui-panel" style="" id="buttonDiv">
                    <button class="easyui-linkbutton" title="查询" onclick="powerSearch();return false;" id="btn">
                        <span style="padding-right: 2px; width: 40px;"><i class="fa fa-search"></i> 查 询</span>
                    </button>
                    <button class="easyui-linkbutton" title="重置" onclick="powerRefresh()" id="btn">
                        <span style="padding-right: 2px; width: 40px;"><i class="fa fa-refresh"></i> 重 置</span>
                    </button>
            </div>
        </div>
        <%-- 查询页 --%>
        <div style="width: 100%;height:100%" align="center" id="powerListDiv">
            <div id="toolbar" class="titlebar clearfix">
                <div id="outwarp">
                    <div class="fl left">
                        <img id="img1" class='title_img' src="<%=contextPath%>/common/zt_webframe/imgs/xzbg/clgl/clsqcx.png" />
                        <span class="title">行政职权综合查询</span>
                    </div>
                    <div class="fr">
                        <button class="easyui-linkbutton" onclick="exportPowerList()"><i class="fa fa-download"></i>&nbsp;导&nbsp;出</button>
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
            <table id="power_search_datagrid" fit="true"></table>
        </div>
    </form>
    <script type="text/javascript" src="<%=contextPath%>/common/js/juicer-min.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/power/powerSearch/js/power_search.js"></script>
</body>
</html>
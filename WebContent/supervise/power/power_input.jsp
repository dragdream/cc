<%@page import="net.sf.json.JSONObject"%>
<%@page import="java.util.*"%>
<%@page import="com.tianee.oa.core.workflow.flowrun.bean.FlowRunToken"%>
<%@page import="com.tianee.webframe.util.auth.TeeFunctionControl"%>
<%TeeFunctionControl.distinguishTheVersion(request,response);%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/upload.jsp" %>

    <script type="text/javascript"
            src="<%=contextPath%>/supervise/power/js/power_input.js"></script>
    <script type="text/javascript"
            src="<%=contextPath%>/supervise/common/js/common.js"></script>

<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/power/css/power.css" />

<title>职权管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body onload="doInit()">
    <div id="power_tabs" class="easyui-tabs" <c:if test="${type=='10' or isNext==1}">data-options="selected:'基础信息'"</c:if> style="width:100%;height:500px;">
        <div title="基础信息" >
            <form role="form" id= "power_form" name = "form1" enctype="multipart/form-data" method="post">
                <input type="hidden" id="id" name="id" value='<c:out value="${id}"/>' />
                <input type="hidden" id="type" name="type" value="<c:out value="${type}"/>" />
                <input type="hidden" id="editFlag" name="editFlag" value="<c:out value="${editFlag}"/>" />
                <input type="hidden" id="isNext" name="isNext" value="<c:out value="${isNext}"/>" />
                <input type="hidden" id="currentState" name="currentState" value="" />
                <input type="hidden" id="powerFormalId" name="powerFormalId" value="<c:out value="${powerFormalId}"/>" />
                <input type="hidden" id="createDateStr" name="createDateStr" value="" />
                <input type="hidden" id="updateDateStr" name="updateDateStr" value="" />
                <table class="none_Table" style="width: 100%; background: #fff;frame: void;">
                    <tr class="power-input-high-tr">
                        <td class="power-table-label">职权名称<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td colspan="3">
                            <input class="easyui-textbox tb" id="name" name="name" style="width: 96%;"
                                data-options="required:true, novalidate:true,validType:'length[2,200]',missingMessage:'请填写职权名称'" />
                        </td>
                    </tr>
                    <tr class="power-input-high-tr" style="padding-top:5px!important;">
                        <td class="power-table-label">职权类型<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style="width: 300px;">
                            <select class="easyui-combobox tb" data-options="required:true, novalidate:true, missingMessage:'请选择职权类型' "  id="powerType" name="powerType" style="width: 90%;">
                            </select>
                        </td>
                        <td class="power-table-label">职权分类<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style=" width: 300px;">
                            <input name="powerDetail" id="powerDetail" class="easyui-combobox"
                                 data-options="required:true, novalidate:true, missingMessage:'请选择职权分类' ,multiple:true" style="width: 90%;" />
                        </td>
                    </tr>
                    <tr class="power-input-high-tr">
                        <td class="power-table-label">职权层级 ：</td>
                        <td style=" width: 300px;">
                            <input class="easyui-combobox" id="powerLevel" name="powerLevel"
                                data-options="multiple:true" style="width: 90%;" />
                        </td>
                        <td class="power-table-label">职权领域 ：</td>
                        <td>
                            <input class="easyui-combobox" id="powerMold" name="powerMold" style="width: 90%;" />
                        </td>
                    </tr>
                    <tr class="power-input-high-tr">
    <!--                     <td class="power-table-label">法定主体 ：</td>
                        <td>
                            <input class="easyui-textbox"  id="subjectDesc" name="subjectDesc" style="width: 90%;"
                                data-options="validType:'length[0, 100]', novalidate:true, missingMessage:'请填写法定主体'" />
                        </td> -->
                        <td class="power-table-label">管理主体<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style=" width: 300px;">
                            <input name="subjectId" id="subjectId" class="easyui-combobox" style="width: 90%;"
                                data-options="request: true, novalidate:true,missingMessage:'请填写管理主体'"/>
                        </td>
                    </tr>
                </table>
<!--                 <table id="power_level_table" class="none_Table" style="width: 100%; background: #fff;">
                    
                </table> -->
<!--                 <table class="none_Table" style="width: 100%; background: #fff;">
                    <tr>
                        <td class="power-table-label">添加流程图 ：</td>
                        <td style=" width: 300px;">
                            <div id="fileContainer" style="display: none;"></div>
                            <a id="uploadHolder" class="add_swfupload" href="javascript: void(0);">上传附件</a>
                            <input id="attachmentSidStr" name="attachmentSidStr" type="hidden"/>
                        </td>
                        <td class="power-table-label"></td>
                        <td style=" width: 300px;">
                            
                        </td>
                    </tr>
                    <tr class="power-line-height"></tr>
                </table>
                <table id="power_flowsheet_table" class="none_Table" style="width: 100%; background: #fff;">
                    
                </table> -->
            </form>
        </div>
        <div title="依据信息" style="padding:10px">
            <div id="setting_panel" class="easyui-panel" title="设定依据" style="width:100%;height:210px;" data-options="tools:'#settingTools'" >
                <table id="settingDatagrid" fit="true"></table>
            </div>
            <div id="settingTools">
                <a onclick="doAddSettingLawDetail()" id="settingDatagrid_add_btn" title="添加设定依据"><i class="fa fa-plus"></i></a>
            </div>
            <div id="gist_panel" class="easyui-panel" title="违法依据" style="width:100%;height:210px;" data-options="tools:'#gistTools'" >
                <table id="gistDatagrid" fit="true"></table>
            </div>
            <div id="gistTools">
                <a onclick="doAddGistLawDetail()" id="gistDatagrid_add_btn" title="添加违法依据"><i class="fa fa-plus"></i> </a>
            </div>
            <br />
            <div id="punish_panel" class="easyui-panel" title="处罚依据" style="width:100%;height:210px;" data-options="tools:'#punishTools'" >
                <table id="punishDatagrid" fit="true"></table>
            </div>
            <div id="punishTools">
                <a onclick="doAddPunishLawDetail()" id="punishDatagrid_add_btn" title="添加处罚依据"><i class="fa fa-plus"></i></a>
            </div>
        </div>
        <div title="实施主体" style="padding:10px">
            <div class="easyui-panel" style="width:100%;height:100%;padding:5px;" data-options="border:false">
                <div class="easyui-layout" data-options="fit:true,border:false">
                    <div data-options="region:'west',border:false" style="width:45%;">
                        <div class="easyui-tabs" id="subject_tabs" style="width: 100%; height: 100%;">
                            <div title="本系统主体"  style="width: 100%; height: 100%;">
                                <div id="subject_toolbar">
                                    <input class="easyui-textbox" id="subject_name_query" />
                                    <a class="easyui-linkbutton" onclick="searchSubject();"><i class="fa fa-search"></i> 查询</a>
                                </div>
                                <table id="subject_datagrid"  data-options="border:false" fit="true"></table>
                            </div>
                            <div title="其他系统主体" style="width: 100%; height: 100%;">
                                <div id="other_subject_toolbar">
                                    <input class="easyui-textbox" id="other_subject_name_query" />
                                    <a class="easyui-linkbutton" onclick="searchOtherSubject();"><i class="fa fa-search"></i> 查询</a>
                                </div>
                                <table id="other_subject_datagrid" fit="true"></table>
                            </div>
                            <div title="受委托组织" style="width: 100%; height: 100%;">
                                <div id="depute_toolbar">
                                    <input class="easyui-textbox" id="depute_name_query" />
                                    <a class="easyui-linkbutton" onclick="searchDepute();"><i class="fa fa-search"></i> 查询</a>
                                </div>
                                <table id="depute_datagrid" fit="true"></table>
                            </div>
                        </div>
                    </div>
                    <div data-options="region:'east',border:false" style="width:45%;">
                        <div class="easyui-tabs" style="width: 100%; height: 100%;">
                            <div title="已选择主体" style="width: 100%; height: 100%;padding:5px;">
                                <table id="selected_datagrid" fit="true"></table>
                            </div>
                        </div>
                    </div>
                    <div data-options="region:'center',border:false" style="width:10%;">
                        <div style="text-align: center; margin-top: 180px;">
                            <a class="easyui-linkbutton" id="selectedSubject_add_btn" style="width: 80%; height: 20px;'" onclick="selectedSubject();"><i class="fa fa-arrow-right"></i> </a>
                            
                        </div>
                        <div style="text-align: center; margin-top: 15px;">
                            <a class="easyui-linkbutton" id="removeSubject_add_btn" style="width: 80%; height: 20px;" onclick="removeSubject();"><i class="fa fa-arrow-left"></i> </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
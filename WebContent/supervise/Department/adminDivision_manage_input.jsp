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
<%@ include file="/header/upload.jsp"%>

<script type="text/javascript" src="<%=contextPath%>/supervise/Department/js/adminDivision_manage_input.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>

<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/power/css/power.css" />
<link rel="stylesheet" type="text/css" href="/supervise/Department/css/adminDivisionManage.css" />

<title>填报</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>

<body onload="doInit();" style="padding: 10px; background-color: #fff;">
    <div title="新增">
        <form role="form" id="adminDivisionEdit_form" name="form1" enctype="multipart/form-data" method="post" class="easyui-form"
            data-options="novalidate:true">
        <div class="easyui-panel" style="width: 100%;" data-options="border:false;" align="center">
            <input type="hidden" id="id" name="id" value="<c:out value='${baseInfo.id}'/>" />
            <input type="hidden" id="baseLevelCode" name="baseLevelCode" value="<c:out value='${baseInfo.baseLevelCode}'/>" />
            <input type="hidden" id="editInitLevel" name="editInitLevel" value="<c:out value='${baseInfo.levelCode}'/>" />
            <input type="hidden" id="baseAdminDivisionCode" name="baseAdminDivisionCode" value="<c:out value='${baseInfo.baseAdminDivisionCode}'/>" />
            <input type="hidden" id="editInitProvCode" name="editInitProvCode" value="<c:out value='${baseInfo.provincialCode}'/>" />
            <input type="hidden" id="editInitCityCode" name="editInitCityCode" value="<c:out value='${baseInfo.cityCode}'/>" />
            <input type="hidden" id="editInitDistCode" name="editInitDistCode" value="<c:out value='${baseInfo.districtCode}'/>" />
            <table class="TableBlock" id="admindivisionInfo_table" style="width: 99%;padding-left:5%; background: #fff;" rules="none">
                <tr>
                    <td style="width: 140px;" class="power-table-label">层级<span style="color:red;font-weight:bold;">*</span>：&nbsp;&nbsp;</td>
                    <td style="width: 200px;">
                        <select class="easyui-combobox" id="levelCode" name="levelCode" style="width: 90%" data-options="novalidate:true,required:true, missingMessage:'请选择层级',editable:false,panelHeight:'auto'"></select>
                    </td>
                    <td style="width: 140px;" class="power-table-label"><span class="editArea-provincial">所属省（直辖市）<span style="color:red;font-weight:bold;">*</span>：&nbsp;&nbsp;</span></td>
                    <td style="width: 200px;">
                        <div class="editArea-provincial selfCtrl-div">
                        <select class="easyui-combobox" id="provincialCode" name="provincialCode" style="width: 90%" ></select>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td style="width: 140px;" class="power-table-label"><span class="editArea-city">所属市（州）<span style="color:red;font-weight:bold;">*</span>：&nbsp;&nbsp;</span></td>
                    <td style="width: 200px;">
                        <div class="editArea-city selfCtrl-div">
                        <select class="easyui-combobox" id="cityCode" name="cityCode" style="width: 90%" ></select>
                        </div>
                    </td>
                    <td style="width: 140px;" class="power-table-label"><span class="editArea-district">所属区（县）<span style="color:red;font-weight:bold;">*</span>：&nbsp;&nbsp;</span></td>
                    <td style="width: 200px;">
                        <div class="editArea-district selfCtrl-div">
                        <select class="easyui-combobox" id="districtCode" name="districtCode" style="width: 90%"></select>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td style="width: 140px;" class="power-table-label">区划名称<span style="color:red;font-weight:bold;">*</span>：&nbsp;&nbsp;</td>
                    <td style="width: 200px;">
                        <input class="easyui-textbox"
                        id="adminDivisionName" 
                        name="adminDivisionName" 
                        style="width: 90%;"
                        value="<c:out value='${baseInfo.adminDivisionName}'/>"/>
                    </td>
                    <td style="width: 140px;" class="power-table-label">区划代码<span style="color:red;font-weight:bold;">*</span>：&nbsp;&nbsp;</td>
                    <td style="width: 200px;">
                        <input class="easyui-textbox"
                        id="adminDivisionCode"
                        name="adminDivisionCode"
                        style="width: 90%;"
                        value="<c:out value='${baseInfo.adminDivisionCode}'/>"/>
                    </td>
                </tr>
<!--                 <tr> -->
<!--                     <td style="width: 140px;" class="power-table-label">是否具备政府机构<span style="color:red;font-weight:bold;">*</span>：&nbsp;&nbsp;</td> -->
<!--                     <td style="width: 200px;"> -->
<!--                         <input type="radio" name="isHaveGov"  id="isHaveGov_yes"/> -->
<!--                         <input type="radio" name="isHaveGov"  id="isHaveGov_no" /> -->
<!--                     </td> -->
<!--                 </tr> -->
            </table>
        </div>
        </form>
    </div>
</body>
</html>
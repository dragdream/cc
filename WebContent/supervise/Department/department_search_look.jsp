<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String id = request.getParameter("id")==null?"0":request.getParameter("id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="/supervise/caseManager/commonCaseSearch/css/case.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/pureText-lookInfo.css" />
</head>
<body  onload="doInit();" style="padding: 10px;background-color: #fff;">
    <form role="form" id= "form1" name = "form1"  enctype="multipart/form-data" method="post" class="easyui-form" data-options="novalidate:true">
        <div class="easyui-panel" title="基础信息" style="width: 100%;" align="center">
                <input type="hidden" id="id" name = "id" value="${param.id }"/>
                <table class="TableBlock_page lookInfo-lowHeight-table" frame="void" style="width: 100%; background: #fff;" rules="none">
                    <tr style="border:none!important;">
                        <td class="power-table-label font-bold-label-td" style="text-align:right;">机关全称：</td>
                        <td colspan="3" class="power-table-label" style="text-align:left;" id="name" name="name">
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                    	<td class="power-table-label font-bold-label-td" style="text-align:right;width: 150px;">机关简称：</td>
                        <td class="power-table-label" style="text-align:left;width: 270px;" id="simpleName" name="simpleName">
                        </td>
                    	<td class="power-table-label font-bold-label-td" style="text-align:right;width: 150px;">统一社会信用码：</td>
                        <td class="power-table-label" style="text-align:left;" id="departmentCode" name="departmentCode">
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label font-bold-label-td" style="text-align:right;width: 150px;">成立日期：</td>
                        <td class="power-table-label" style="text-align:left;width: 270px;" id="establishDateStr" name="establishDateStr">
                        </td>
                        <td class="power-table-label font-bold-label-td" style="text-align:right;width: 150px;">法定代表人：</td>
                        <td class="power-table-label" style="text-align:left;" id="representative" name="representative">
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                  		<td class="power-table-label font-bold-label-td" style="text-align:right;width: 150px;">行政区划：</td>
                        <td class="power-table-label" style="text-align:left;width: 270px;" id="administrativeDivision" name="administrativeDivision">
                        </td>
                        <td class="power-table-label font-bold-label-td" style="text-align:right;width: 150px;">机关层级：</td>
                        <td class="power-table-label" style="text-align:left;" id="deptLevel" name="deptLevel">
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label font-bold-label-td" style="text-align:right;width: 150px;">机关性质：</td>
                        <td class="power-table-label" style="text-align:left;width: 270px;" id="nature" name="nature">
                        </td>
                        <td class="power-table-label font-bold-label-td" style="text-align:right;width: 150px;">所属领域：</td>
                        <td class="power-table-label" style="text-align:left;" id="orgSys" name="orgSys">
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label font-bold-label-td" style="text-align:right;width: 150px;">监督机关：</td>
                        <td class="power-table-label" style="text-align:left;width: 270px;" id="superviceDepartmentId" name="superviceDepartmentId">
                        </td>
                        <td class="power-table-label font-bold-label-td" style="text-align:right;width: 150px;">是否垂管：</td>
                        <td class="power-table-label" style="text-align:left;" id="isManubrium" name="isManubrium">
                        </td>
                    </tr>
                    <tr style="border:none!important;display:none" id="chuiguan1">
                    	<td id="chuiguan" class="power-table-label font-bold-label-td" style="text-align:right;width: 150px;">垂管部门：</td>
                        <td class="power-table-label" style="text-align:left;width: 270px;" id="droopId" name="droopId">
                        </td>
                        <td class="power-table-label font-bold-label-td" style="text-align:right;width: 150px;">垂管层级：</td>
                        <td class="power-table-label" style="text-align:left;" id="manubriumLevel" name="manubriumLevel">
                        </td>
                    </tr>
             </table>
        </div>
        <br />
        <div class="easyui-panel" title="内设监督机构" style="width: 100%;" align="center">
        	<table class="TableBlock" frame="void" style="width: 100%; background: #fff;" rules="none">
                    <tr style="border:none!important;">
                        <td class="power-table-label font-bold-label-td" style="text-align:right;width: 150px;">机构名称：</td>
                        <td class="power-table-label" style="text-align:left;width: 270px;" id="innerSupOrgName" name="innerSupOrgName">
                        </td>
                        <td class="power-table-label font-bold-label-td" style="text-align:right;width: 150px;">机构级别：</td>
                        <td class="power-table-label" style="text-align:left;" id="innerSupOrgLevel" name="innerSupOrgLevel">
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label font-bold-label-td" style="text-align:right;width: 150px;">编制数：</td>
                        <td class="power-table-label" style="text-align:left;width: 270px;" id="innerSupOrgPostNum" name="innerSupOrgPostNum">
                        </td>
                        <td class="power-table-label font-bold-label-td" style="text-align:right;width: 150px;">负责人：</td>
                        <td class="power-table-label" style="text-align:left;" id="innerSupOrgLoader" name="innerSupOrgLoader">
                        </td>
                    </tr>
             </table>
        </div>
        <br />
        <div class="easyui-panel" title="联系信息" style="width: 100%;" align="center">
        	<table class="TableBlock" frame="void" style="width: 100%; background: #fff;" rules="none">
        		<tr style="border:none!important;">
       				<td class="power-table-label font-bold-label-td" style="text-align:right;width: 150px;">邮政编码：</td>
                    <td class="power-table-label" style="text-align:left;width: 270px;" id="postCode" name="postCode">
                    </td>
                    <td class="power-table-label font-bold-label-td" style="text-align:right;width: 150px;">传&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;真：</td>
                    <td class="power-table-label" style="text-align:left;" id="fax" name="fax">
                    </td>
                </tr>
                <tr style="border:none!important;">
                    <td class="power-table-label font-bold-label-td" style="text-align:right;width: 150px;">联系电话：</td>
                    <td class="power-table-label" style="text-align:left;width: 270px;" id="phone" name="phone">
                    </td>
                    <td class="power-table-label font-bold-label-td" style="text-align:right;width: 150px;">电子邮箱：</td>
                    <td class="power-table-label" style="text-align:left;" id="mail" name="mail">
                    </td>
                </tr>
                <tr style="border:none!important;">
                    <td class="power-table-label font-bold-label-td" style="text-align:right;width: 150px;">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址：</td>
                    <td class="power-table-label" colspan="3" style="text-align:left;" id="address" name="address">
                    </td>
                </tr>
        	</table>
        </div>
    </form>
<script type="text/javascript" src="<%=contextPath%>/supervise/Department/js/department_search_look.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/md5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
</body>
</html>
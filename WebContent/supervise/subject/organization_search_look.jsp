<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/pureText-lookInfo.css" />
</head>
<body  onload="doInit();" style="padding: 10px;background-color: #fff;">
            <form role="form" id= "form1" name = "form1"  enctype="multipart/form-data" method="post" class="easyui-form" data-options="novalidate:true">
                <input type="hidden" id="id" name = "id" value="${param.id }"/>
                <div class="easyui-panel" data-options="fit:true,border:false">
                <table class="TableBlock_page lookInfo-lowHeight-table" style="width: 100%; background: #fff;">
                    <tr style="border:none!important;">
                        <td class="power-table-label font-bold-label-td" style="text-align:right;" nowrap>受委托组织名称：</td>
                        <td colspan="3" class="power-table-label" style="text-align:left;" id="subName" name="subName">
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                    	<td class="power-table-label font-bold-label-td" style="text-align:right;" nowrap>统一社会信用代码：</td>
                        <td class="power-table-label" style="text-align:left;width: 260px;" id="code" name="code">
                        </td>
                        <td class="power-table-label font-bold-label-td" style="text-align:right;">受委托组织性质：</td>
                        <td style=" width: 260px;" align="center" valign="middle" id="entrustNature">
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                    	<td class="power-table-label font-bold-label-td" style="text-align:right;">委托主体：</td>
                        <td class="power-table-label" style="text-align:left;width: 260px;" id="parentId" name="parentId">
                        </td>
                        <td class="power-table-label font-bold-label-td" style="text-align:right;">委托方式：</td>
                        <td class="power-table-label" style="text-align:left;width: 260px;" id="orgMode" name="orgMode">
                       	</td>
                        
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label font-bold-label-td" style="text-align:right;">委托期限（起）：</td>
                        <td class="power-table-label" style="text-align:left;width: 260px;" id="termBeginStr" name="termBeginStr">
                       	</td>
                        <td class="power-table-label font-bold-label-td" style="text-align:right;" nowrap>委托期限（止）：</td>
                        <td class="power-table-label" style="text-align:left;width: 260px;" id="termEndStr" name="termEndStr">
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label font-bold-label-td" style="text-align:right;">法定代表人：</td>
                        <td class="power-table-label" style="text-align:left;width: 260px;" id="representative" name="representative">
                       	</td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label font-bold-label-td" style="text-align:right;">联系电话：</td>
                        <td class="power-table-label" style="text-align:left;width: 260px;" id="telephone" name="telephone">
                        </td>
                        <td class="power-table-label font-bold-label-td" style="text-align:right;">传&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;真：</td>
                        <td class="power-table-label" style="text-align:left;width: 260px;" id="fax" name="fax">
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                   	 	<td class="power-table-label font-bold-label-td" style="text-align:right;">电子邮箱：</td>
                        <td class="power-table-label" style="text-align:left;width: 260px;" id="mail" name="mail">
                        </td>
                        <td class="power-table-label font-bold-label-td" style="text-align:right;">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编：</td>
                        <td class="power-table-label" style="text-align:left;width: 260px;" id="postCode" name="postCode">
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label font-bold-label-td" style="text-align:right;">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址：</td>
                        <td class="power-table-label" colspan="3" style="text-align:left;width: 260px;" id="address" name="address">
                         </td>
                    </tr>
                    </table>
                    </div>
            </form>
<script type="text/javascript" src="<%=contextPath%>/supervise/subject/js/organization_search_look.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/md5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
</body>
</html>
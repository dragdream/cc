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
<link rel="stylesheet" type="text/css" href="/supervise/common/css/pureText-lookInfo.css" />
<script type="text/javascript">
var id = '<%=id%>';
</script>

</head>
<body  onload="doInit();" style="padding: 10px;background-color: #fff;">
            <form role="form" id= "form1" name = "form1" style="width:100%;height:100%;" enctype="multipart/form-data" method="post" class="easyui-form" data-options="novalidate:true">
                <input type="hidden" id="id" name = "id" value="${param.id}"/>
                <div class="easyui-panel" data-options="fit:true,border:false" style="width:100%;height:100%;">
                <table class="TableBlock_page lookInfo-lowHeight-table" style="width: 100%;height:100%;border:none;background: #fff;">
                    <tr style="border:none!important;">
                        <td class="power-table-label font-bold-label-td" style="width: 100px;text-align:right;">机关全称：</td>
                        <td colspan="3" id="name" name="name"></td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label font-bold-label-td" style="width: 100px;text-align:right;">机关简称：</td>
                        <td style="width: 200px;" name="simpleName" id="simpleName">
                        </td>
                        <td class="power-table-label font-bold-label-td" style="width: 140px;text-align:right;">统一社会信用代码：</td>
                        <td style="width: 200px;" name="departmentCode" id="departmentCode">
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label font-bold-label-td" style="width: 100px;text-align:right;">行政区划：</td>
                        <td style=" width: 200px;" name="administrativeDivision" id="administrativeDivision">
                        </td>
                        <td class="power-table-label font-bold-label-td" style="width: 100px;text-align:right;">所属层级：</td>
                        <td style=" width: 200px;" id="deptLevel" name="deptLevel">
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                    	<td class="power-table-label font-bold-label-td" style="width: 100px;text-align:right;">机关性质：</td>
                        <td style=" width: 200px;" name="nature" id="nature">
                        </td>
                        <td class="power-table-label font-bold-label-td" style="width: 100px;text-align:right;">法定代表人：</td>
                        <td style="width: 200px;" id="representative" name="representative">
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label font-bold-label-td" style="width: 100px;text-align:right;">邮政编码：</td>
                        <td style="width: 200px;" id="postCode" name="postCode">
                        </td>
                        <td class="power-table-label font-bold-label-td" style="width: 100px;text-align:right;">电子邮箱：</td>
                        <td style="width: 200px;" id="mail" name="mail">
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                    	<td class="power-table-label font-bold-label-td" style="width: 100px;text-align:right;">传&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;真：</td>
                        <td style="width: 200px;" id="fax" name="fax">
                        </td>
                        <td class="power-table-label font-bold-label-td" style="width: 100px;text-align:right;">联系电话：</td>
                        <td style="width: 200px;"  id="phone" name="phone" >
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                    	<td class="power-table-label font-bold-label-td" style="width: 100px;text-align:right;">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址：</td>
                        <td colspan="3" name="address" id="address">
                        </td>
                    </tr>
                    </table>
                </table>
                </div>
            </form>

<script type="text/javascript" src="<%=contextPath%>/supervise/supervise/js/supervise_search_look.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/md5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
</body>
</html>
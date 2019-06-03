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
<%@ include file="/header/upload.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/pureText-lookInfo.css" />
<style>
.none-border-tr {
	border: none !important;
}
</style>
<script type="text/javascript">
var id = '<%=id%>';
</script>
</head>
<body  onload="doInit();" style="padding: 10px;background-color: #fff;">
            <form role="form" id= "form1" name = "form1"  enctype="multipart/form-data" method="post" class="easyui-form" data-options="novalidate:true">
                <input type="hidden" id="id" name = "id" value="${param.id}"/>
                <div class="easyui-panel" data-options="fit:true,border:false">
                <table class="TableBlock_page lookInfo-lowHeight-table" style="width: 100%;background: #fff;">
                    <tr style="border:none!important;">
                        <td class="power-table-label font-bold-label-td" style="width: 100px;text-align:right;">姓名：</td>
                        <td style="width: 200px;" name="name" id="name">
                        </td>
                        <td class="power-table-label font-bold-label-td" style="width: 100px;text-align:right;">身份证号：</td>
                        <td style=" width: 200px;" name="personId" id="personId">
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label font-bold-label-td" style="width: 100px;text-align:right;">性别：</td>
                        <td style="width: 200px;" name="sex" id="sex">
                        </td>
                        <td class="power-table-label font-bold-label-td" style="width: 100px;text-align:right;" nowrap>出生日期：</td>
                        <td style=" width: 200px;" id='birthStr'  name='birthStr'>
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label font-bold-label-td" style="width: 100px;text-align:right;">民族：</td>
                        <td style=" width: 200px;" name="nation" id="nation">
		    			</td>
		    			<td class="power-table-label font-bold-label-td" style="width: 100px;text-align:right;">所属机构：</td>
                        <td style="width: 200px;" id="departmentName" name="departmentName">
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                    	<td class="power-table-label font-bold-label-td" style="width: 100px;text-align:right;">政治面貌：</td>
                        <td style="width: 200px;" name="politive" id="politive">
                        </td>
                        <td class="power-table-label font-bold-label-td" style="width: 100px;text-align:right;">职级：</td>
                        <td style=" width: 200px;" name="jobClass" id="jobClass">
		    			</td>
                    </tr>
                    <tr style="border:none!important;">
                    	<td class="power-table-label font-bold-label-td" style="width: 100px;text-align:right;">最高学历：</td>
                        <td style="width: 200px;" name="education" id="education">
                        </td>
                        <td class="power-table-label font-bold-label-td" style="width: 100px;text-align:right;" nowrap>公职律师：</td>
                        <td style="width: 200px;" name="isLawyer" id="isLawyer">
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                    	<td class="power-table-label font-bold-label-td" style="width: 100px;text-align:right;">联系方式：</td>
                        <td style="width: 200px;" name="telephone" id="telephone">
                        </td>
                    	<td class="power-table-label font-bold-label-td" style="width: 100px;text-align:right;" nowrap>执法证：</td>
                        <td style="width: 200px;" name="isGetcode" id="isGetcode">
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                    	<td class="power-table-label font-bold-label-td" style="width: 100px;text-align:right;">照片：</td>
                        <td colspan="3">
                         	<div id="fileContainer2"></div>
							<div id="renderContainer2" style="display:none"></div>
                             <div id="attachDiv"></div>
                         </td>
                    </tr>
                    </table>
            		</div>
            </form>
<script type="text/javascript" src="<%=contextPath%>/supervise/supervise/js/supperson_search_look.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/md5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
</body>
</html>
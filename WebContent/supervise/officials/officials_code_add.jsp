<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 	String index = request.getParameter("awardDept")==null?"0":request.getParameter("awardDept");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<link rel="stylesheet" type="text/css" href="/supervise/caseManager/commonCaseSearch/css/case.css" />
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<!-- <script type="text/javascript"> -->
<%-- var ids = '<%=ids%>'; --%>
<!-- </script> -->
<style type="text/css">
.datebox-calendar-inner {
    height: 180px;
}
</style>
</head>
<body  onload="doInit();" style="padding: 10px;background-color: #fff;">
    <form role="form" id= "form1" name = "form1"  enctype="multipart/form-data" method="post" class="easyui-form" data-options="novalidate:true">
                <input type="hidden" id="id" name = "id" value="<c:out value ='${paramCode.id}'/>"/>
                <input type="hidden" id="index" name = "index" value= "<c:out value ='${paramCode.index}'/>"/>
                <table class="TableBlock" style="width: 100%; background: #fff;">
	       		    <tr style="border:none!important;">
	       		    	<td class="power-table-label" style="text-align:right;">证件类型<span style="color:red;font-weight:bold;">*</span>：</td>
	                    <td style="width: 300px;">
	                        <input name="codeType" id="codeType" prompt="请选择" value= "<c:out value ='${paramCode.codeType}'/>" data-options="novalidate:true,required:true, missingMessage:'请选择证件类型',editable:false,validType:'length[0,50]'"  class="easyui-textbox"  style="width: 100%;" />
	                    </td>
	                    <td class="power-table-label" style="text-align:right;">证件编号<span style="color:red;font-weight:bold;">*</span>：</td>
	                    <td style="width: 300px;">
	                        <input name="code" id="code" value="<c:out value ='${paramCode.code}'/>" data-options="novalidate:true,required:true, missingMessage:'请输入证件编号',validType:'length[0,50]'"  class="easyui-textbox"  style="width: 100%;" />
	                    </td>
	                </tr>
	                <tr style="border:none!important;">
	                	<td class="power-table-label" style="text-align:right;">发证日期<span style="color:red;font-weight:bold;">*</span>：</td>
	                    <td style="width: 300px;">
	                    	<input  class="easyui-datebox" id='codeDateStr'  name='codeDateStr' value="<c:out value ='${paramCode.codeDateStr}'/>" style="width: 100%;" data-options="novalidate:true,required:true, missingMessage:'请选择发证日期',editable:false,panelHeight:'auto'" />
	                    </td>
	                    <td class="power-table-label" style="text-align:right;">颁发机关<span style="color:red;font-weight:bold;">*</span>：</td>
	                    <td style="width: 300px;">
	                        <input name="awardDept" id="awardDept" value= "<c:out value ='${paramCode.awardDept}'/>" data-options="novalidate:true,required:true, missingMessage:'请输入颁发机关',validType:'length[0,200]'"  class="easyui-textbox"  style="width: 100%;" />
	                    </td>
	                </tr>
	                <tr style="border:none!important;">
	                    <td class="power-table-label" style="text-align:right;">有限期（起）<span style="color:red;font-weight:bold;">*</span>：</td>
	                    <td style="width: 300px;">
	                    	<input  class="easyui-datebox" id='codeBeginStr'  name='codeBeginStr' value= "<c:out value ='${paramCode.codeBeginStr}'/>" style="width: 100%;" data-options="novalidate:true,required:true, missingMessage:'请选择有效期（起）',editable:false,panelHeight:'auto'" />
	                    </td>
	                    <td class="power-table-label" style="text-align:right;">有限期（止）<span style="color:red;font-weight:bold;">*</span>：</td>
	                    <td style=" width: 300px;">
	                    	<input  class="easyui-datebox" id='codeEndStr'  name='codeEndStr' value= "<c:out value ='${paramCode.codeEndStr}'/>" style="width: 100%;" data-options="novalidate:true,required:true, missingMessage:'请选择有效期（止）',editable:false,panelHeight:'auto'" />
	               		</td>
	                </tr>
        		</table>
    </form>
<script type="text/javascript" src="<%=contextPath%>/supervise/officials/js/officials_add_code.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/md5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
</body>
</html>
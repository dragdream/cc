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
<link rel="stylesheet" type="text/css" href="/supervise/caseManager/commonCaseSearch/css/case.css" />
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript">
var id = '<%=id%>';
</script>
<style type="text/css">
.datebox-calendar-inner {
    height: 180px;
}
</style>
</head>
<body  onload="doInit();" style="padding: 10px;background-color: #fff;">
	<div class="easyui-layout" data-options="fit:true,border:false">
		<form role="form" id= "form1" name = "form1"  enctype="multipart/form-data" method="post" class="easyui-form" data-options="novalidate:true">
			<table class="TableBlock" frame="void" style="width: 100%; background: #fff;" rules="none">
	            <tr style="border:none!important;">
	            	<td class="power-table-label" style="text-align:right;width: 80px;">姓名<span style="color:red;font-weight:bold;">*</span>：&nbsp;&nbsp;</td>
	               	<td>
	                	<input class="easyui-textbox" id="name" style="width: 200px" name="name" data-options="required:true, missingMessage:'请输入姓名'"  />
	               	</td>
	               	<td class="power-table-label" style="text-align:right;width: 80px;">身份证号<span style="color:red;font-weight:bold;">*</span>：&nbsp;&nbsp;</td>
	               	<td style="width: 200px;">
	                    <input name="personIdcard" id="personIdcard" style="width: 200px;" class="easyui-textbox" data-options="novalidate:true,required:true, missingMessage:'请输入身份证号'"  />
	               	</td>
	           	</tr>
	           	<tr style="border:none!important;">
	           		<td class="power-table-label" style="text-align:right;width: 80px;">性别<span style="color:red;font-weight:bold;">*</span>：&nbsp;&nbsp;</td>
	               	<td>
	                	<input class="easyui-textbox" id="sex" style="width: 200px" prompt="请先输入身份证号" name="sex" data-options="editable:false,required:true, missingMessage:'请先输入身份证号'"  />
	               	</td>
	               	<td class="power-table-label" style="text-align:right;width: 80px;">所属机关<span style="color:red;font-weight:bold;">*</span>：&nbsp;&nbsp;</td>
	               	<td style="width: 200px;">
	                    <input name="organizationName" id="organizationName" style="width: 200px;" class="easyui-textbox" data-options="editable:false,required:true, missingMessage:'请输入所属机关'"  />
	               	</td>
	           	</tr>
	           	<tr style="border:none!important;">
	           		<td class="power-table-label" style="text-align:right;width: 80px;">人员类型<span style="color:red;font-weight:bold;">*</span>：&nbsp;&nbsp;</td>
	               	<td>新增评查人员</td>
	               	<td class="power-table-label" style="text-align:right;width: 80px;">联系电话&nbsp;&nbsp;：&nbsp;&nbsp;</td>
	               	<td style="width: 200px;">
	                    <input name="phoneNumber" id="phoneNumber" style="width: 200px;" class="easyui-textbox" />
	               	</td>
	           	</tr>
        	</table>
		</form>
	</div>
	<script>
           $(function () {
               $('#name').textbox('textbox').attr('maxlength', 60);
               $('#personIdcard').textbox('textbox').attr('maxlength', 18);
               $('#phoneNumber').textbox('textbox').attr('maxlength', 15);
           });
    </script><script type="text/javascript" src="<%=contextPath%>/supervise/caseCheck/person/js/person_addOther.js"></script>
<%-- <script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script> --%>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/md5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>

</body>
</html>
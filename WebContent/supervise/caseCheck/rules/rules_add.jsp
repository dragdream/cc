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
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/supervise/caseCheck/rules/css/rules.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body  onload="doInit();" style="padding: 10px;background-color: #fff;">
	<div class="easyui-layout" data-options="fit:true,border:false">
		<form id= "form1" name = "form1" role="form"  enctype="multipart/form-data" method="post" class="easyui-form" data-options="novalidate:true">
			<table class="TableBlock" frame="void" style="width: 100%; background: #fff;" rules="none">
	            <tr style="border:none!important;">
	            	<td class="power-table-label">评分版本&nbsp;：&nbsp;&nbsp;</td>
	               	<input id="id" name="id" value="${param.id}" type="hidden"/>
	               	<input id="versionId" name="versionId" value="${param.vId}" type="hidden"/>
	               	<td>
	               		<span>${param.vName}</span>
	               	</td>
	               	<td class="power-table-label">适用范围<span style="color:red;font-weight:bold;">*</span>：&nbsp;&nbsp;</td>
	               	<td>
						<select id="checkType" name="checkType" class="easyui-combobox" style="width: 200px" panelMaxHeight="150px"></select>
	               	</td>
	           	</tr>
	           	<tr style="border:none!important;">
	           		<td class="power-table-label">编号<span style="color:red;font-weight:bold;">*</span>：&nbsp;&nbsp;</td>
	               	<td>
	                	<input id="code" name="code" class="easyui-textbox"  style="width: 200px" prompt="请先输入编号" data-options="required:true, missingMessage:'请先输入编号'"  />
	               	</td>
	                <td class="power-table-label">一级指标<span style="color:red;font-weight:bold;">*</span>：&nbsp;&nbsp;</td>
	                <td>
						<select id="classoneId" name="classoneId" class="easyui-combobox" style="width: 200px" panelMaxHeight="150px"></select>
    				</td>
                  </tr>
	           	<tr style="border:none!important;">
	           		<td class="power-table-label">二级指标<span style="color:red;font-weight:bold;">*</span>：&nbsp;&nbsp;</td>
	                <td>
         				<select id="classtwoId" name="classtwoId" class="easyui-combobox" style="width: 200px" panelMaxHeight="150px"></select>
    				</td>
	           		<td class="power-table-label">评查项类型<span style="color:red;font-weight:bold;">*</span>：&nbsp;&nbsp;</td>
	               	<td>
						<select id="checkSubjectType" name="checkSubjectType" class="easyui-combobox" style="width: 200px" panelMaxHeight="150px" prompt="请选择"></select>
	               	</td>
	           	</tr>
	           	<tr style="border:none!important;">
	            	<td class="power-table-label">评查项目<span style="color:red;font-weight:bold;">*</span>：&nbsp;&nbsp;</td>
	               	<td>
	                	<input id="checkSubject" name="checkSubject" class="easyui-textbox" style="width: 200px" data-options="required:true, missingMessage:'请输入项目名称'"  />
	               	</td>
	               	<td class="power-table-label">扣分分值<span style="color:red;font-weight:bold;">*</span>：&nbsp;&nbsp;</td>
	               	<td style="width: 200px;">
	                    <input id="deductscore" name="deductscore" class="easyui-numberbox" style="width: 200px;" data-options="novalidate:true,required:true, missingMessage:'请输入扣分分值'"  />
	               	</td>
	           	</tr>
	           	<tr style="border:none!important;">
	            	<td class="power-table-label">评分标准&nbsp;：&nbsp;&nbsp;</td>
	               	<td colspan="3">
	                	<input id="basisDescribe" name="basisDescribe" class="easyui-textbox" style="width: 100%;height:80px" data-options="multiline:true"  />
	               	</td>
	           	</tr>
        	</table>
		</form>
	</div>
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/md5.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/supervise/caseCheck/rules/js/rules_add.js"></script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<script type="text/javascript"src="<%=contextPath%>/supervise/inspection/inspModule/js/inspection_add.js"></script>
<script type="text/javascript"src="<%=contextPath%>/supervise/common/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/power/css/power.css" />
<link rel="stylesheet" type="text/css" href="/supervise/inspection/inspRecord/css/inspection.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>

<body onload="doInit();"
	style="padding: 10px; ">
	<div class="easyui-panel tabs-header-border" style="width: 100%; overflow: auto;">
	<input type="hidden" id="insp_id" value="<c:out value='${inspInfo.id}'/>" />
	<input type="hidden" id="insp_orgSys" value="<c:out value='${inspInfo.orgSys}'/>" />
		<form role="form" id="form1" name="form1" enctype="multipart/form-data" method="post" data-options="novalidate:true">
			<%-- <input type="hidden" id="id" name = "id" value="<c:out value='${power.id}' />"/> --%>
			<table class="TableBlock" style="width: 95%; background: #fff;">
				<tr class="none-border-tr">
					<td class="insp-td-right3" >模块名称<span class="required">*</span>：</td>
                    <td class="insp-td-left9"><input name="moduleName"
					id="moduleName" class="easyui-textbox" value='<c:out value="${inspInfo.moduleName}"/>'
					data-options="validType:'length[0,60]', novalidate:true,required:true, missingMessage:'请输入模块名称' "
					style="width: 95%;" /></td>
				</tr>
				<tr class="none-border-tr">
					<td class="insp-td-right3">所属领域<span class="required">*</span>：</td>
					<td class="insp-td-left9"><input name="orgSys" id="orgSys" class="easyui-combobox"
						data-options="novalidate:true,validType:'length[0,300]',required:true, missingMessage:'请选择执法系统'"
						style="width: 95%;" /></td>
				</tr>
				
			</table>
		</form>
	</div>
</body>
</html>
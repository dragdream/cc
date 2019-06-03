<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%

    String model = TeeAttachmentModelKeys.humanDoc;
    String ids=request.getParameter("ids");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<%@ include file="/header/upload.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hr.js"></script>
<style>
	td{
		line-height:28px;
		min-height:28px;
	}
	.ztree{
		background:white;
		border:1px solid gray;
	}
</style>
<script>
var ids="<%=ids%>";
function doInit(){
	getInsurances();
}
function getInsurances(){
	var url = contextPath+"/salaryManage/datagridInsurances.action";
	var json = tools.requestJsonRs(url,{});
	var list = json.rows;
	for(var i=0;i<list.length;i++){
		$("#insuranceId").append("<option value='"+list[i].sid+"'>"+list[i].insuranceName+"</option>");
	}
}

function commit(){
	var insuranceId=$("#insuranceId").val();
	var url = contextPath+"/humanDocController/getInsuranceAll.action";
	var json = tools.requestJsonRs(url,{ids:ids,insuranceId:insuranceId});
	return json.rtState;
}

</script>

</head>
<body onload="doInit();" style="font-size:12px;padding-left: 10px;padding-right: 10px;">
<div class="topbar clearfix" id="toolbar">
   <div class="fl" style="position:static;">
		
	</div>
</div>

<form id="form1" name="form1">
	<table style="width:100%;font-size:12px" class='TableBlock_page'>
		<tr class='TableData' align="left">
			<td class="TableData" width="150" style="text-indent: 15px;">
				保险套账：
			</td>
			<td>
				<select style="height: 23px;" class="BigSelect" id="insuranceId" name="insuranceId" >
					<option value="0"></option>
				</select>
			</td>
		</tr>
		
	</table>
</form>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id")==null?"0":request.getParameter("id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/ztree.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<script type="text/javascript">
var id = '<%=id%>';
console.log(id);
</script>
</head>
<body onload="doInit()" style="padding-right: 10px;padding-left: 10px;padding-top: 5px">
	<div class="easyui-panel" style="width: 100%;" data-options="fit:true,border:false">
        <form id="form1">
        	<table class="TableBlock" style="width: 100%; background: #fff;">
        		<tr>
        			<td>序列</td>
        			<td>批次编号</td>
        			<td>姓名</td>
        			<td>案卷名称</td>
        		</tr>
        		<tr>
        			<td>1</td>
        			<td>123123C</td>
        			<td>欢欢</td>
        			<td>案卷名称</td>
        		</tr>
        		<tr>
        			<td>2</td>
        			<td>324234</td>
        			<td>欢欢</td>
        			<td>案卷名称</td>
        		</tr>
        	</table>
        </form>
	</div>
	<script>
           $(function () {
           $('#revokeReasonOther').textbox('textbox').attr('maxlength', 500);
           });
         </script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/caseCheck/person/js/person_check.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
</body>
</html>
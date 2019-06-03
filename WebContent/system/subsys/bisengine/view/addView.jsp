<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>创建视图</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath %>/common/My97DatePicker/WdatePicker.js"></script>
<style>
</style>
<script>
function add(){
	if($("#identity").val()==""){
		$.MsgBox.Alert_auto("视图标识不能为空");
		return;
	}
	if($("#viewName").val()==""){
		$.MsgBox.Alert_auto("视图名称不能为空");
		return;
	}	
    var json = tools.requestJsonRs(contextPath+"/bisView/createBisView.action",{name:$("#viewName").val(),identity:$("#identity").val(),type:$("#type").val()});
    if($("#type").val()=="1"){
    	parent.window.location = "view_mgr_senior.jsp?identity="+$("#identity").val();
    }else{
    	parent.window.location = "view_mgr.jsp?identity="+$("#identity").val();
    }
}
</script>
</head>
<body onload="doInit()" style="font-size:12px;padding-left: 10px;padding-right: 10px;background-color: #f2f2f2;">

<form action="" method="post" id="form1">
	<table class="TableBlock" style="width:100%;" >
		
		<tr>
			<td class="TableData" style="text-indent: 15px;">
				  视图标识：
			</td>
			<td class="TableData">
				<input type='text' id='identity' name='identity' style="height: 23px;width: 250px" />
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent: 15px;">
				   视图名称：
			</td>
			<td class="TableData">
				<input type='text' id='viewName' name='viewName' style="height: 23px;width: 250px" />
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent: 15px;">
				 视图类型：
			</td>
			<td class="TableData">
				<select style="height: 23px;width: 150px" id="type">
         	      <option value="1">设计模式</option>
         	      <option value="2">SQL开发模式</option>
                </select>
			</td>
		</tr>
	</table>
</form>
</body>
</html>
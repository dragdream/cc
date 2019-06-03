<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>
<%
	String typeName = request.getParameter("typeName");
	String sid = request.getParameter("sid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp"%>
<%@ include file="/header/upload.jsp" %>
<title>图书类别修改</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>

<style type="text/css">
</style>

<script type="text/javascript">

var typeName = '<%=typeName%>';
var sid = '<%=sid%>';

function doInit(){
	$("#typeName").val(typeName);
}

function doSave(){
	if(sid!=""){
		var url = "<%=contextPath%>/bookManage/updateType.action?sid="+sid;
		var para =  tools.formToJson($("#form1")) ;
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			alert(jsonRs.rtMsg);
			location.href="<%=contextPath%>/bookManage/bookTypeList.action";
		}
	}
}

function toReturn(){
	location.href="<%=contextPath%>/bookManage/bookTypeList.action";
}
</script>

</head>
<body onload="doInit();">
<form action=""  method="post" name="form1" id="form1">
<br>
	<table class="none-table">
	  <tr>
	      <td nowrap class="TableData"> 类别名称：<font style='color:red'>*</font></td>
	      <td class="TableData" colspan="3">
	          <input type="text" name="typeName" id="typeName" size="40" maxlength="300" class="BigInput easyui-validatebox" value="" required="true">
	      </td>
	  </tr>
     <tr>
        <td nowrap class="TableData" colspan="4">
		    <input id="button" type="button" value="确定" onclick="doSave();" class="btn btn-primary"/>
		    <input id="button" type="button" value="返回" onclick="toReturn();" class="btn btn-default"/>
	    </td>
     </tr>
    </table>
<br>
</form>
</body>
</html>

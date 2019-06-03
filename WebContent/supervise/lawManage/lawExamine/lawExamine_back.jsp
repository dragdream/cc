<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="java.util.*,java.text.SimpleDateFormat"%>
	<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/supervise/common/css/supervise.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/supervise/lawManage/css/lawManage.css" />
<script type="text/javascript">
var id = '<%=id%>';
</script>
</head>
<body onload="doInit()" style="padding-right: 10px;padding-left: 10px;padding-top: 5px">
	<div>
    		<form role="form" id= "form1" name = "form1"  enctype="multipart/form-data" method="post" class="easyui-form" data-options="novalidate:true">
            	<input type="hidden" id="id" name = "id" value=""/>
            	退回原因<span style="color:red;font-weight:bold;">*</span>：<input name="backReason" id="backReason" class="easyui-textbox" style="width: 80%;" data-options="novalidate:true,required:true, missingMessage:'请输入退回原因',panelHeight:'auto'" />
            </form>
        </div>
        <script>
           $(function () {
           $('#backReason').textbox('textbox').attr('maxlength', 500);
           });
         </script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/lawManage/lawExamine/js/lawAdjustExamine_back.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
</body>
</html>
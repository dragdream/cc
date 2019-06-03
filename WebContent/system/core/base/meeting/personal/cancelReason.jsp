
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp"%>
<title>会议撤销</title>

<link href='<%=contextPath %>/common/fullcalendar/fullcalendar/fullcalendar.css' rel='stylesheet' />
<link href='<%=contextPath %>/common/fullcalendar/fullcalendar/fullcalendar.print.css' rel='stylesheet' media='print' />
<link  rel='stylesheet' href='<%=contextPath %>/system/core/base/calendar/css/calendar.css'type="text/css" />



<script type="text/javascript">
/**
 * 校验
 */
function checkFrom(){
	 var check = $("#form1").form('validate'); 
	 if(!check){
		 return false; 
	 }else{
		 return true;
	 }
	 
}

</script>
</head>
<body class="" topmargin="5" style="overflow:hidden;">
     <form id="form1">
     <table>
       <tr>
          <td class="TableData">撤销理由&nbsp;<font color="red">*</font>：</td>
          <td class="TableData">
            <textarea rows="10" cols="70" id="reason" name="reason" class="BigTextarea easyui-validatebox" required="true"></textarea>
          </td>
       </tr>
     </table>
     </form>
</body>

</html>
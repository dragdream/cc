<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String meetingId = request.getParameter("meetingId");//会议Id
	String attendFlag = request.getParameter("attendFlag");//状态

%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>确认参会情况</title>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script type="text/javascript">

function checkForm(){
	var check = $("#form1").form('validate'); 
	if(!check){
		return false; 
	}
	return true;
}

function doSaveOrUpdate(callback){
	if(checkForm()){
	    var url = "<%=contextPath %>/TeeMeetingAttendConfirmController/updateAttendFlag.action";
	    var para =  tools.formToJson($("#form1"));
	    var jsonRs = tools.requestJsonRs(url,para);
	    if(jsonRs.rtState){
	      callback();
	    }else{
	      alert(jsonRs.rtMsg);
	    }
	}
}




</script>

</head>
<body>
<form action="" method="post" name="form1" id="form1">
<input type="hidden" name="meetingId" id="meetingId" value="<%=meetingId%>">
<input type="hidden" name="attendFlag" id="attendFlag" value="<%=attendFlag%>">
<table align="center" width="96%" class="TableBlock" >
	<tr>
		<td nowrap class="TableData"  width="">缺席说明：<font color="red" >*</font></td>
		<td class="TableData" width="">
			<textarea rows="8" cols="85" id="remark" name="remark" maxlength="150" class="BigTextarea easyui-validatebox" required="true" ></textarea>
		</td>
	</tr>
</table>
</form>

</body>
</html>
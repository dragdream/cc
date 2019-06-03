<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String parentId=request.getParameter("parentId");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script>
function commit(){
	if($("#form1").valid() && checkForm()){
		var param = tools.formToJson($("#form1"));
		var url = contextPath+"/TeeAttendConfigController/addHoliday.action";
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
// 			top.$.jBox.tip(json.rtMsg,"success");
			parent.$.MsgBox.Alert_auto(json.rtMsg);
			return true;
		}else{
// 			top.$.jBox.tip(json.rtMsg,"error");
			parent.$.MsgBox.Alert_auto(json.rtMsg);
		}
		return false;
	}
}

function checkForm(){
	 if($("#startTimeDesc").val()==null || $("#startTimeDesc").val()=="" ||$("#startTimeDesc").val()=="null"){
		 $.MsgBox.Alert_auto("补假日期不能为空！");
		  return false;
	 }
	return true;
}

function doInit(){
	
}
</script>

</head>
<body onload='doInit()' style="background:#f4f4f4;">
<form id="form1" name="form1">
<input type="hidden" id="parentId" name="parentId" value="<%=parentId %>" />
	<table  style="width:100%;font-size:12px" class='TableBlock'>
		<tr class='TableData'>
			<td>
				<b>补假名称：</b>
			</td>
			<td>
				<input type="text"   id="holidayName" name="holidayName" required="true" maxlength="50" class="easyui-validatebox BigInput"/>
			</td>
		</tr>
		<tr class='TableData' style="border-bottom:none;">
			<td>
				<b>补假日期：</b>
			</td>
			<td>
				<input type="text" id='startTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='startTimeDesc' class="Wdate BigInput" />
			</td>
		</tr>
	</table>
</form>
</body>
</html>
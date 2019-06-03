<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid");
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
var sid = "<%=sid%>";
function commit(){
	if($("#form1").valid() && checkForm()){
		var param = tools.formToJson($("#form1"));
		var url = contextPath+"/TeeAttendConfigController/updateHoliday.action";
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
// 			top.$.jBox.tip(json.rtMsg,"info");
			parent.$.MsgBox.Alert_auto(json.rtMsg);
			return true;
		}
// 		top.$.jBox.tip(json.rtMsg,"error");
		parent.$.MsgBox.Alert_auto(json.rtMsg);
		return false;
	}
}
function checkForm(){
	 if($("#startTimeDesc").val()==null || $("#startTimeDesc").val()=="" ||$("#startTimeDesc").val()=="null"){
		 $.MsgBox.Alert_auto("开始时间不能为空！");
		  return false;
	 }
	 if($("#endTimeDesc").val()==null || $("#endTimeDesc").val()=="" ||$("#endTimeDesc").val()=="null" ){
		 $.MsgBox.Alert_auto("结束时间不能为空！");
		  return false;
	 }
	 if($("#startTimeDesc").val() && $("#endTimeDesc").val()){
		    if(document.getElementById("startTimeDesc").value > document.getElementById("endTimeDesc").value){
		    	$.MsgBox.Alert_auto("开始时间不能大于结束时间！");
		      return false;
			 }
	 }
	return true;
}


function doInit(){
	if(sid!="" && sid!=null && sid!="null"){
		var url = "<%=contextPath%>/TeeAttendConfigController/getHolidayById.action?sid="+sid;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			bindJsonObj2Cntrl(json.rtData);
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		}
	}
}
</script>

</head>
<body onload='doInit()' style="background:#f4f4f4;">
<form id="form1" name="form1">
	<table  style="width:100%;font-size:12px" class='TableBlock'>
		<tr class='TableData'>
			<td>
				<b>节日名称：</b>
			</td>
			<td>
				<input type="text"   id="holidayName" name="holidayName" required="true" class="easyui-validatebox BigInput"/>
				<input type="hidden"   id="sid" name="sid" value="<%=sid%>"/>
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				<b>开始日期：</b>
			</td>
			<td>
				<input type="text" id='startTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='startTimeDesc' class="Wdate BigInput" />
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				<b>结束日期</b>
			</td>
			<td>
				<input type="text" id='endTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='endTimeDesc' class="Wdate BigInput" />
			</td>
		</tr>
	</table>
</form>
</body>
</html>
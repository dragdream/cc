<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp"%>
<%@include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/validator2.0.jsp" %>
<%@include file="/header/upload.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<style>
.diaryTable {
	border-collapse: collapse;
}

.diaryTable td {
	border: 1px solid #e2e2e2;
	font-size: 14px;
	padding: 5px;
}
</style>
<script>
function doInit(){
		var url = contextPath+"/TeeAttendConfigController/getAttendTimes.action";
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			bindJsonObj2Cntrl(json.rtData);
		}else{
// 			top.$.jBox.tip(json.rtMsg,"error");
			$.MsgBox.Alert_auto(json.rtMsg);
		}
}

function commit(){
	if($("#form1").valid() && checkForm()){
		var param = tools.formToJson($("#form1"));
		var url = contextPath+"/TeeAttendConfigController/saveAttendTimes.action";
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
// 			top.$.jBox.tip(json.rtMsg,"info");
            parent.$.MsgBox.Alert_auto(json.rtMsg);
			location.reload();
		}else{
// 			top.$.jBox.tip(json.rtMsg,"error");
			parent.$.MsgBox.Alert_auto(json.rtMsg);
		}
	}
}

function checkForm(){
	return true;
}
</script>
<style>
#timesTips{
	color:#ffa800;
	font-weight:bold;
	margin-left:5px;
}
.time_info{
	line-height:30px;
	font-weight:bold;
}
.info img,.info span{
	vertical-align:middle;
}
table{
	border-collapse: collapse;
/*     border: 1px solid #f2f2f2; */
    width:100%;
}
table tr{
	line-height:35px;
	border-bottom:1px solid #f2f2f2;
}
table tr td:first-child{
	text-indent:10px;
}
table tr:first-child td{
	/*font-weight:bold;*/
}
table tr:first-child{
	/* background-color: #f8f8f8;  */
}
</style>

</head>
<body onload="doInit()" style="overflow-y:auto;overflow-x:hidden;padding-top:25px;">
<div id="time_info">
<form method="post" name="form1" id="form1"  enctype="multipart/form-data">
	<table class="none-table" style="width:60%;font-size:12px;margin:0 auto;">
		<tr>
			<td class="TableData">上班登记时间段设置：</td>
			<td class="TableData">
				提前<input type="text" id="workonBeforeMin" name="workonBeforeMin" class="BigInput easyui-validatebox" validType='integeZero[]' maxlength="5" value="0" style="width:60px;">分钟允许登记
				<br/>延后<input type="text" id="workonAfterMin" name="workonAfterMin" class="BigInput easyui-validatebox" validType='integeZero[]' maxlength="5" value="0" style="width:60px;">分钟允许登记
			</td>
		</tr>
		<tr>
			<td class="TableData">下班登记时间段设置：</td>
			<td class="TableData">
				提前<input type="text" id="workoutBeforeMin" name="workoutBeforeMin" class="BigInput easyui-validatebox" validType='integeZero[]' maxlength="5" value="0" style="width:60px;">分钟允许登记
				<br/>延后<input type="text" id="workoutAfterMin" name="workoutAfterMin" class="BigInput easyui-validatebox" validType='integeZero[]' maxlength="5" value="0" style="width:60px;">分钟允许登记
			</td>
		</tr>
		<tr style="border-bottom:none;">
			<td colspan="2" class="TableData" align="center">
					<input type="button" class="btn-win-white fr" onClick="commit()" value="保存设置"/>
			</td>
		</tr>
	</table>
</form>
</div>
</body>
</html>
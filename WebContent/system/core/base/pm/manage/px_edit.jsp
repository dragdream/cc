<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

	String sid = request.getParameter("sid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script>
var sid='<%=sid%>';
function doInit(){
	var url = contextPath+"/TeeHumanTrainController/getModelById.action?sid="+sid;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		bindJsonObj2Cntrl(json.rtData);
	}else{
		alert(json.rtMsg);
	}
}

function commit(){
	if($("#form1").form("validate") && checkForm()){
		var param = tools.formToJson($("#form1"));
		var url = contextPath+"/TeeHumanTrainController/updateHumanTrain.action";
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
			top.$.jBox.tip(json.rtMsg,"info");
			return true;
		}
		top.$.jBox.tip(json.rtMsg,"error");
		return false;
	}
}

function checkForm(){
	  if($("#startTimeDesc").val() && $("#endTimeDesc").val()){
		    if(document.getElementById("startTimeDesc").value > document.getElementById("endTimeDesc").value){
		      alert("开始时间不能大于结束时间！");
		      //$("#validTimeDesc").focus();
		      //$("#validTimeDesc").select();
		      return false;
			 }
	  }
	return true;
}
</script>

</head>
<body onload="doInit()" >
<form id="form1" name="form1">
	<table  style="width:100%;font-size:12px" class="TableBlock">
		<tr class='TableData'>
			<td>
				<b>培训主题：</b>
			</td>
			<td>
				<input type="text"  id="traSubject" name="traSubject" required="true" class="easyui-validatebox BigInput"/>
			</td>
			<td>
				<b>培训费用：</b>
			</td>
			<td>
				<input type="text" class="BigInput easyui-validatebox" validType='intege[]' id="traPays" name="traPays"/>
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				<b>开始日期：</b>
			</td>
			<td>
				<input type="text" id='startTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='startTimeDesc' class="Wdate BigInput" />
			</td>
			<td>
				<b>结束日期：</b>
			</td>
			<td>
				<input type="text" id='endTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='endTimeDesc' class="Wdate BigInput" />
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				<b>培训类型：</b>
			</td>
			<td>
				<input type="text" class="BigInput" id="traType" name="traType" />
			</td>
			<td>
				<b>培训证书：</b>
			</td>
			<td>
				<input type="text" class="BigInput" id="traCert" name="traCert"/>
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				<b>培训地点：</b>
			</td>
			<td colspan="3">
				<textarea style="width:425px;height:100px" class="BigTextarea" id="traPosition" name="traPosition"></textarea>
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				<b>培训内容：</b>
			</td>
			<td colspan="3">
				<textarea style="width:425px;height:100px" class="BigTextarea" id="traContent" name="traContent"></textarea>
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				<b>备注：</b>
			</td>
			<td colspan="3">
				<textarea style="width:425px;height:100px" class="BigTextarea" id="remark" name="remark"></textarea>
			</td>
		</tr>
	</table>
		<input type="hidden" class="BigInput" id="humanDocSid" name="humanDocSid" />
	<input type="hidden" class="BigInput" id="sid" name="sid" value="<%=sid%>"/>
	</form>
</body>
</html>
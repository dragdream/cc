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
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hr.js"></script>
<script>
var sid='<%=sid%>';
function doInit(){
	getHrCodeByParentCodeNo("PM_CERT_TYPE" , "certType");
	getHrCodeByParentCodeNo("PM_CERT_ATTR" , "certAttr");
	var url = contextPath+"/TeeHumanCertController/getModelById.action?sid="+sid;
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
		var url = contextPath+"/TeeHumanCertController/updateHumanCert.action";
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
	  if($("#validTimeDesc").val() && $("#endTimeDesc").val()){
		    if(document.getElementById("validTimeDesc").value > document.getElementById("endTimeDesc").value){
		      alert("生效时间不能大于结束时间！");
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
				<b>证照编号：</b>
			</td>
			<td>
				<input type="text" id="certCode" name="certCode" required="true" class="easyui-validatebox BigInput"/>
			</td>
			<td>
				<b>证照类型：</b>
			</td>
			<td>
				<select class="BigSelect" id="certType" name="certType">
				</select>
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				<b>证照名称：</b>
			</td>
			<td>
				<input type="text" class="BigInput" id="certName" name="certName"/>
			</td>
			<td>
				<b>取证日期：</b>
			</td>
			<td>
				<input type="text" id='getTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='getTimeDesc' class="Wdate BigInput" />
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				<b>生效日期：</b>
			</td>
			<td>
				<input type="text" id='validTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='validTimeDesc' class="Wdate BigInput" />
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
				<b>证件属性：</b>
			</td>
			<td colspan="3">
				<select class="BigSelect" id="certAttr" name="certAttr">
				</select>
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				<b>发证机构：</b>
			</td>
			<td colspan="3">
				<textarea style="width:425px;height:100px" class="BigTextarea" id="certOrg" name="certOrg"></textarea>
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				<b>备注：</b>
			</td>
			<td colspan="3">
				<textarea style="width:425px;height:100px" class="BigTextarea" id="remark" name="remark" ></textarea>
			</td>
		</tr>
	</table>
		<input type="hidden" class="BigInput" id="humanDocSid" name="humanDocSid" />
	<input type="hidden" class="BigInput" id="sid" name="sid" value="<%=sid%>"/>
	</form>
</body>
</html>
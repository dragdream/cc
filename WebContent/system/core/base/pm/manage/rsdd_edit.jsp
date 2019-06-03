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
	getHrCodeByParentCodeNo("PM_SHIFT_TYPE" ,"sType");
	var url = contextPath+"/TeeHumanShiftController/getModelById.action?sid="+sid;
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
		var url = contextPath+"/TeeHumanShiftController/updateHumanShift.action";
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
	  if($("#sTime1Desc").val() && $("#sTime2Desc").val()){
		    if(document.getElementById("sTime1Desc").value > document.getElementById("sTime2Desc").value){
		      alert("调动时间不能大于生效时间！");
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
				<b>调动类型：</b>
			</td>
			<td>
				<select class="BigSelect" id="sType" name="sType" >
				</select>
			</td>
			<td>
				<b>调动原因：</b>
			</td>
			<td>
				<input type="text" class="BigInput" id="sCause" name="sCause"/>
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				<b>调动日期：</b>
			</td>
			<td>
				<input type="text" id='sTime1Desc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='sTime1Desc' class="Wdate BigInput" />
			</td>
			<td>
				<b>调动生效日期：</b>
			</td>
			<td>
				<input type="text" id='sTime2Desc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='sTime2Desc' class="Wdate BigInput" />
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				<b>调动前单位：</b>
			</td>
			<td>
				<input type="text" class="BigInput" id="sFirstCompany" name="sFirstCompany"/>
			</td>
			<td>
				<b>调动后单位：</b>
			</td>
			<td>
				<input type="text" class="BigInput" id="sLastCompany" name="sLastCompany"/>
			</td>
		</tr>
			<tr class='TableData'>
			<td>
				<b>调动前职务：</b>
			</td>
			<td>
				<input type="text" class="BigInput" id="sFirstPost"  name="sFirstPost"/>
			</td>
			<td>
				<b>调动后职务：</b>
			</td>
			<td>
				<input type="text" class="BigInput" id="sLastPost" name="sLastPost"/>
			</td>
		</tr>
				<tr class='TableData'>
			<td>
				<b>调动前部门：</b>
			</td>
			<td>
				<input type="text" class="BigInput" id="sFirstDept" name="sFirstDept"/>
			</td>
			<td>
				<b>调动后部门：</b>
			</td>
			<td>
				<input type="text" class="BigInput" id="sLastDept" name="sLastDept"/>
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				<b>调动手续办理：</b>
			</td>
			<td colspan="3">
				<textarea style="width:425px;height:100px" class="BigTextarea" id="detail" name="detail"></textarea>
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
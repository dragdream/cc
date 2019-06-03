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
	getHrCodeByParentCodeNo("PM_REHAB_TYPE" , "rehabType");
	var url = contextPath+"/TeeHumanRehabController/getModelById.action?sid="+sid;
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
		if(confirm("是否恢复关联OA用户登录状态为允许登录？")){
			param["isDel"]=1;
		}else{
			param["isDel"]=0;
		}
		var url = contextPath+"/TeeHumanRehabController/updateHumanRehab.action";
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
	  if($("#regTimeDesc").val() && $("#planTimeDesc").val() && $("#realTimeDesc").val()){
		    if(document.getElementById("regTimeDesc").value > document.getElementById("planTimeDesc").value){
		      alert("申请时间不能大于拟复职时间！");
		      return false;
			 }
		    if(document.getElementById("regTimeDesc").value > document.getElementById("realTimeDesc").value){
			    alert("申请日期不能大于实际复职日期");
			    return false;
			    
		    }
		    if(document.getElementById("planTimeDesc").value > document.getElementById("realTimeDesc").value){
			    alert("拟复职日期不能大于实际复职日期");
			    return false;
			    
		    }
	  }
	  if($("#regTimeDesc").val() && $("#planTimeDesc").val()){
		    if(document.getElementById("regTimeDesc").value > document.getElementById("planTimeDesc").value){
		      alert("申请时间不能大于拟复职时间！");
		      return false;
			 }
	  }

	  if($("#planTimeDesc").val() && $("#realTimeDesc").val()){
		    if(document.getElementById("planTimeDesc").value > document.getElementById("realTimeDesc").value){
			    alert("拟复职日期不能大于实际复职日期");
			    return false;
			    
		    }
	  }
	  if($("#regTimeDesc").val() && $("#realTimeDesc").val()){
		    if(document.getElementById("regTimeDesc").value > document.getElementById("realTimeDesc").value){
			    alert("申请日期不能大于实际复职日期");
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
				<b>担任职务：</b>
			</td>
			<td>
				<input type="text" id="pos" name="pos" required="true" class="easyui-validatebox BigInput"/>
			</td>
			<td>
				<b>复职类型：</b>
			</td>
			<td>
				<select class="BigSelect" id="rehabType" name="rehabType" >
				</select>
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				<b>申请日期：</b>
			</td>
			<td>
				<input type="text" id='regTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='regTimeDesc' class="Wdate BigInput" />
			</td>
			<td>
				<b>拟复职日期：</b>
			</td>
			<td>
				<input type="text" id='planTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='planTimeDesc' class="Wdate BigInput" />
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				<b>实际复职日期：</b>
			</td>
			<td>
				<input type="text" id='realTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='realTimeDesc' class="Wdate BigInput" />
			</td>
			<td>
				<b>工资恢复日期：</b>
			</td>
			<td>
				<input type="text" id='payTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='payTimeDesc' class="Wdate BigInput" />
			</td>
		</tr>
			<tr class='TableData'>
			<td>
				<b>复职部门：</b>
			</td>
			<td colspan="3">
				<input type="text" class="BigInput" id="rehabDept" name="rehabDept"/>
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				<b>复职手续办理：</b>
			</td>
			<td colspan="3">
				<textarea style="width:425px;height:100px" class="BigTextarea" id="rehabDetail" name="rehabDetail"></textarea>
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
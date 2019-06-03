<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid");
	String humanDocSid=request.getParameter("humanDocSid");
	String personName = request.getParameter("personName");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script>
var sid='<%=sid%>';
var humanDocSid='<%=humanDocSid%>';
var personName='<%=personName%>';
function doInit(){
	var url = contextPath+"/TeeHumanExperienceController/getModelById.action?sid="+sid;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		bindJsonObj2Cntrl(json.rtData);
	}else{
		$.MsgBox.Alert_auto(json.rtMsg);
	}
}

function commit(callback){
	if(checkForm() && $("#form1").valid()){
		var param = tools.formToJson($("#form1"));
		var url = contextPath+"/TeeHumanExperienceController/updateHumanExperience.action";
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
			callback(json.rtState);
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		} 
	}
}

function checkForm(){
	if($("#pos").val()=="" || $("#pos").val()=="null" || $("#pos").val()==null){
		$.MsgBox.Alert_auto("请输入担任职务！");
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
</script>
<style>
html{
   background-color: #f2f2f2;
}
	td{
		line-height:28px;
		min-height:28px;
	}
</style>
</head>
<body onload="doInit();" style="padding-left: 10px;padding-right: 10px;background-color: #f2f2f2;">
<div class="topbar clearfix" id="toolbar">
   <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/base/pm/img/icon_dabj.png">
		<span class="title">新增/编辑  <%= personName %> 的工作经历信息</span>
	</div>
</div>
<form id="form1" name="form1">
	 <table class="TableBlock" width="100%" align="center">
	    <tr>
		   <td class=TableHeader colSpan=2 noWrap>
		       <img src="<%=contextPath %>/common/zt_webframe/imgs/rsdagl/dagl/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		       <B style="color: #0050aa">基本信息</B>
		   </td>
	     </tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				担任职务<span style="color:red;font-weight:bold;">*</span>：
			</td>
			<td>
				<input style="height: 23px;width: 350px;" type="text"  id="pos" name="pos" required class="BigInput"/>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				所在部门：
			</td>
			<td>
				<input style="height: 23px;width: 350px;" type="text" class="BigInput" id="dept" name="dept" />
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				证明人：
			</td>
			<td>
				<input style="height: 23px;width: 350px;" type="text" class="BigInput" id="prover" name="prover"/>
			</td>
	    </tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				行业类别：
			</td>
			<td>
				<input style="height: 23px;width: 350px;" type="text" class="BigInput" id="tradeType" name="tradeType" />
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				开始日期：
			</td>
			<td>
				<input style="height: 23px;width: 200px;" type="text" id='startTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='startTimeDesc' class="Wdate BigInput" />
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				结束日期：
			</td>
			<td>
				<input style="height: 23px;width: 200px;" type="text" id='endTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='endTimeDesc' class="Wdate BigInput" />
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">s
				工作单位：
			</td>
			<td>
				<input style="height: 23px;width: 350px;" type="text" class="BigInput" id="workAt" name="workAt" />
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				联系方式：
			</td>
			<td>
				<input style="height: 23px;width: 350px;" type="text" class="BigInput" id="contact" name="contact" />
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				工作内容：
			</td>
			<td>
				<textarea style="width:425px;height:100px" class="BigTextarea" id="content" name="content" ></textarea>
			</td>
	    </tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				离职原因：
			</td>
			<td>
				<textarea style="width:425px;height:100px" class="BigTextarea" id="leaveCause" name="leaveCause"></textarea>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				备注：
			</td>
			<td>
				<textarea style="width:425px;height:100px" class="BigTextarea" id="remark" name="remark"></textarea>
			</td>
		</tr>
	</table>
		<input type="hidden" class="BigInput" id="humanDocSid" name="humanDocSid" />
	<input type="hidden" class="BigInput" id="sid" name="sid" value="<%=sid%>"/>
</form>
</body>
</html>
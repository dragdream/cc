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
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hr.js"></script>
<script>
var sid='<%=sid%>';
var humanDocSid='<%=humanDocSid%>';
var personName='<%=personName%>';
function doInit(){
	getHrCodeByParentCodeNo("PM_SHIFT_TYPE" ,"sType");
	var url = contextPath+"/TeeHumanShiftController/getModelById.action?sid="+sid;
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
		var url = contextPath+"/TeeHumanShiftController/updateHumanShift.action";
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
			callback(json.rtState);
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		} 
	}
}

function checkForm(){
	  if($("#sTime1Desc").val() && $("#sTime2Desc").val()){
		    if(document.getElementById("sTime1Desc").value > document.getElementById("sTime2Desc").value){
		    	$.MsgBox.Alert_auto("调动时间不能大于生效时间！");
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
		<span class="title">新增/编辑  <%= personName %> 的人事调动</span>
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
				调动类型：
			</td>
			<td>
				<select style="height: 23px;width: 200px;" class="BigSelect" id="sType" name="sType" >
				</select>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				调动原因：
			</td>
			<td>
				<input style="height: 23px;width: 350px;"  type="text" class="BigInput" id="sCause" name="sCause"/>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				调动日期：
			</td>
			<td>
				<input style="height: 23px;width: 200px;" type="text" id='sTime1Desc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='sTime1Desc' class="Wdate BigInput" />
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				调动生效日期：
			</td>
			<td>
				<input style="height: 23px;width: 200px;" type="text" id='sTime2Desc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='sTime2Desc' class="Wdate BigInput" />
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				调动前单位：
			</td>
			<td>
				<input style="height: 23px;width: 350px;" type="text" class="BigInput" id="sFirstCompany" name="sFirstCompany"/>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				调动后单位：
			</td>
			<td>
				<input style="height: 23px;width: 350px;" type="text" class="BigInput" id="sLastCompany" name="sLastCompany"/>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				调动前职务：
			</td>
			<td>
				<input style="height: 23px;width: 350px;" type="text" class="BigInput" id="sFirstPost"  name="sFirstPost"/>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				调动后职务：
			</td>
			<td>
				<input style="height: 23px;width: 350px;" type="text" class="BigInput" id="sLastPost" name="sLastPost"/>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				调动前部门：
			</td>
			<td>
				<input style="height: 23px;width: 350px;" type="text" class="BigInput" id="sFirstDept" name="sFirstDept"/>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				调动后部门：
			</td>
			<td>
				<input style="height: 23px;width: 350px;" type="text" class="BigInput" id="sLastDept" name="sLastDept"/>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				调动手续办理：
			</td>
			<td>
				<textarea style="width:425px;height:100px" class="BigTextarea" id="detail" name="detail"></textarea>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				备注：
			</td>
			<td>
				<textarea style="width:425px;height:100px" class="BigTextarea" id="remark" name="remark" ></textarea>
			</td>
		</tr>
	</table>
		<input type="hidden" class="BigInput" id="humanDocSid" name="humanDocSid" />
	<input type="hidden" class="BigInput" id="sid" name="sid" value="<%=sid%>"/>
	</form>
</body>
</html>
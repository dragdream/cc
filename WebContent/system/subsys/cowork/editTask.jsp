<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	String taskId = request.getParameter("taskId");
	String from = request.getParameter("from");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp" %>
<%@include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/TeeMenu.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/layout/layout.js"></script>
<style>
table td{
vertical-align:top;
font-size:12px;
}
</style>
<script>
var taskId = <%=taskId%>;
var from = "<%=from%>";
function doInit(){
	$("#layout").layout({auto:true});
	var json = tools.requestJsonRs(contextPath+"/coWork/getTaskInfo.action?taskId="+taskId);
	
	if(json.rtState){
		bindJsonObj2Cntrl(json.rtData);
		if(json.rtData.parentTaskId!=0){
			$("#parentTaskBlock").show();
		}
	}
}

function commit(){
	if($("#form").form("validate")){
		var param = tools.formToJson($("form"));
		param["sid"] = taskId;
		var json = 	tools.requestJsonRs(contextPath+"/coWork/editTask.action",param);
		if(json.rtState){
			goback();
		}else{
			$.jBox.tip(json.rtMsg,"error");
		}
	}
	
}

function goback(){
	if(from=="list"){
		window.location = contextPath+"/system/subsys/cowork/list.jsp";
	}else if(from=="detail"){
		window.location = contextPath+"/system/subsys/cowork/detail.jsp?taskId="+taskId;
	}
}

</script>
</head>
<body onload="doInit();" style="margin:0px;overflow:hidden">
<div id="layout">
	<div layout="north" height="85">
		<div class="moduleHeader">
			<div>
				<b><i class="glyphicon glyphicon-list-alt"></i>&nbsp;修改任务</b>
			</div>
		</div>
		<div style='text-align:center;'>
			<button class="btn btn-default" type="button" onclick="goback()"><i class="glyphicon glyphicon-repeat"></i>&nbsp;返回</button>
			<button class="btn btn-default" type="button" onclick="commit()"><i class="glyphicon glyphicon-floppy-disk"></i>&nbsp;修改</button>
		</div>
	</div>
	<div layout="center" style="padding:10px;overflow:auto">
	<form id="form">
		<table class="TableBlock" style="font-size:12px;margin:0 auto;">
			<tr class="TableHeader">
					<td colspan="2">
						任务基本信息
					</td>
			</tr>
			<tr>
				<td class="TableData" align="right">
					任务标题<span style="color:red">*</span>：
				</td>
				<td class="TableData">
					<input type="text" class="BigInput easyui-validatebox" required id="taskTitle" name="taskTitle" style="width:500px;"/>
				</td>
			</tr>
			<tr style="display:none" id="parentTaskBlock">
				<td class="TableData" align="right">父任务：</td>
				<td class="TableData">
					<input type="hidden" class="BigInput" name="parentTaskId" id="parentTaskId" readonly/>
					<input class="BigInput" name="parentTaskName" id="parentTaskName" readonly/>
<!-- 					<select class="BigSelect"> -->
<!-- 						<option></option> -->
<!-- 					</select> -->
				</td>
			</tr>
			<tr>
				<td class="TableData" align="right">评估工时（例如10天，2个月等，单位自填）：</td>
				<td class="TableData">
					<input type="text" class="BigInput" id="rangeTimes" name="rangeTimes"/>
				</td>
			</tr>
			<tr>
				<td class="TableData" align="right">添加时间<span style="color:red">*</span>：</td>
				<td class="TableData">
					<input type="text" class="BigInput easyui-validatebox Wdate" required onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTimeDesc\',{d:0});}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="startTimeDesc" name="startTimeDesc"/>
				</td>
			</tr>
			<tr>
				<td class="TableData" align="right">完成时间<span style="color:red">*</span>：</td>
				<td class="TableData">
					<input type="text" class="BigInput easyui-validatebox Wdate" required onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTimeDesc\',{d:0});}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="endTimeDesc" name="endTimeDesc"/>
				</td>
			</tr>
			<!-- <tr>
				<td align="right">审批人：</td>
				<td>
					<input type="hidden" name="auditorId" id="auditorId" />
					<input class="BigInput" name="auditorName" id="auditorName" readonly/>
					<a href="javascript:void(0)" onclick="selectSingleUser(['auditorId','auditorName'])">添加</a>
				</td>
			</tr> -->
			<tr>
				<td class="TableData" align="right">负责人<span style="color:red">*</span>：</td>
				<td class="TableData">
					<input type="hidden" name="chargerId" id="chargerId"/>
					<input class="BigInput easyui-validatebox readonly" required name="chargerName"  id="chargerName" readonly/>
					<a href="javascript:void(0)" onclick="selectSingleUser(['chargerId','chargerName'])">添加</a>&nbsp;&nbsp;
					<a href="javascript:void(0);" class="orgClear" onClick="clearData('chargerId','chargerName')">清空</a>
				</td>
			</tr>
			<tr>
				<td class="TableData" align="right">参与人：</td>
				<td class="TableData">
					<input type="hidden" name="joinerIds" id="joinerIds"/>
					<textarea class="BigTextarea readonly" name="joinerNames"  id="joinerNames" style="height:60px;width:550px" readonly></textarea>
					<a href="javascript:void(0)" onclick="selectUser(['joinerIds','joinerNames'])">添加</a>&nbsp;&nbsp;
					<a href="javascript:void(0);" class="orgClear" onClick="clearData('joinerIds','joinerNames')">清空</a>
				</td>
			</tr>
			<tr>
				<td class="TableData" align="right">任务内容：</td>
				<td class="TableData">
					<textarea class="BigTextarea" id="content" name="content" style="height:160px;width:550px"></textarea>
				</td>
			</tr>
			<tr>
				<td class="TableData" align="right">奖罚标准：</td>
				<td class="TableData">
					<textarea class="BigTextarea" id="standard" name="standard" style="height:120px;width:550px"></textarea>
				</td>
			</tr>
			<tr>
				<td class="TableData" align="right">领导批示：</td>
				<td class="TableData">
					<textarea class="BigTextarea" id="leaderRemark" name="leaderRemark" style="height:120px;width:550px"></textarea>
				</td>
			</tr>
		</table>
		</form>
	</div>
</div>
</div>
</body>
</html>
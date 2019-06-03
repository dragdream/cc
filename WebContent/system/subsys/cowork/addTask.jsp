<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String parentTaskId = request.getParameter("parentTaskId");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp" %>
<%@include file="/header/easyui2.0.jsp" %>
<%@include file="/header/validator2.0.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/TeeMenu.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/layout/layout.js"></script>
<style>
table td{
vertical-align:top;
padding:5px;
}
</style>
<script>
var parentTaskId = <%=parentTaskId%>;
function doInit(){
	$("#layout").layout({auto:true});
	if(parentTaskId!=null){
		$("#parentTaskDiv").show();
		//获取父任务名称与id
		var json = tools.requestJsonRs(contextPath+"/coWork/getTaskInfo.action",{taskId:parentTaskId});
		$("#parentTaskId").attr("value",json.rtData.sid);
		$("#parentTaskName").attr("value",json.rtData.taskTitle);
	}
}

function commit(){
	if($("#form").valid()){
		var param = tools.formToJson($("form"));
		var json = 	tools.requestJsonRs(contextPath+"/coWork/addTask.action",param);
		if(json.rtState){
			$.MsgBox.Confirm("提示","已成功布置一个任务，是否转到详细任务页面？",function(){
// 				if(v=="ok"){
					window.location = contextPath+"/system/subsys/cowork/detail.jsp?taskId="+json.rtData;
// 				}else{
// 					window.location.reload();
// 				}
			});
		}else{
// 			$.jBox.tip(json.rtMsg,"error");
			$.MsgBox.Alert_auto(json.rtMsg);
		}
	}
	
}

function goback(){
	window.location = contextPath+"/system/subsys/cowork/list.jsp";
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
.TableData input{
    height:25px;
}
</style>
</head>
<body onload="doInit();" style="overflow-y:auto;overflow-x:hidden;padding-left: 10px;padding-right: 10px">
<div id="time_info">
    <table border="0" width="100%" cellspacing="0" cellpadding="3"
		class="small">
		<tr style="font-size:16px;border-bottom: 2px solid #b0deff;">
			<td><span> 创建任务</span>
			</td> 
			<td class="right fr clearfix">
			<button class="btn-win-white fl" type="button" onclick="commit()"><i class="glyphicon glyphicon-floppy-disk"></i>&nbsp;保存</button>
			<button class="btn-win-white fl" type="button" onclick="goback()"><i class="glyphicon glyphicon-repeat"></i>&nbsp;返回</button>
			</td>
		</tr>
	</table>
	
	<div layout="center" style="padding:10px;overflow:auto">
	<form id="form">
		<table class="none-table" style="font-size:12px;margin:0 auto;">
			<tr class="TableHeader" style="font-size:14px;">
					<td colspan="2">
						任务基本信息
					</td>
			</tr>
			<tr>
				<td class="TableData" align="right">
					任务标题<span style="color:red">*</span>：
				</td>
				<td class="TableData">
					<input type="text" class="BigInput easyui-validatebox" required name="taskTitle" style="width:500px;height:25px;"/>
				</td>
			</tr>
			<tr id="parentTaskDiv" style="display:none">
				<td class="TableData" align="right">父任务：</td>
				<td class="TableData">
					<type type="hidden" class="BigInput" name="parentTaskId" id="parentTaskId"/>
					<input class="BigInput readonly" name="parentTaskName" id="parentTaskName" readonly/>
<!-- 					<select class="BigSelect"> -->
<!-- 						<option></option> -->
<!-- 					</select> -->
				</td>
			</tr>
			<tr>
				<td class="TableData" align="right">评估工时（例如10天，2个月等，单位自填）：</td>
				<td class="TableData">
					<input type="text" class="BigInput easyui-validatebox" validType ='number[]' name="rangeTimes" style="height:25px;"/>
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
		<!-- 	<tr>
				<td align="right">审批人：</td>
				<td>
					<input type="hidden" name="auditorId" id="auditorId" />
					<input class="BigInput readonly" name="auditorName" id="auditorName" readonly/>
					<a href="javascript:void(0)" onclick="selectSingleUser(['auditorId','auditorName'])">添加</a>&nbsp;&nbsp;
					<a href="javascript:void(0);" class="orgClear" onClick="clearData('auditorId','auditorName')">清空</a>
				</td>
			</tr> -->
			<tr>
				<td class="TableData" align="right">负责人<span style="color:red">*</span>：</td>
				<td class="TableData">
					<input type="hidden" name="chargerId" id="chargerId"/>
					<input class="BigInput  readonly easyui-validatebox" required name="chargerName"  id="chargerName" readonly/>
					<a href="javascript:void(0)" onclick="selectSingleUser(['chargerId','chargerName'])">添加</a>&nbsp;&nbsp;
					<a href="javascript:void(0);" class="orgClear" onClick="clearData('chargerId','chargerName')">清空</a>
					
				</td>
			</tr>
			<tr>
				<td class="TableData" align="right">参与人：</td>
				<td class="TableData">
					<input type="hidden" name="joinerIds" id="joinerIds"/>
					<textarea class="BigTextarea  readonly" name="joinerNames"  id="joinerNames" style="height:60px;width:550px" readonly></textarea>
					<a href="javascript:void(0)" onclick="selectUser(['joinerIds','joinerNames'])">添加</a>&nbsp;&nbsp;
					<a href="javascript:void(0);" class="orgClear" onClick="clearData('joinerIds','joinerNames')">清空</a>
				</td>
			</tr>
			<tr>
				<td class="TableData" align="right">任务内容：</td>
				<td class="TableData">
					<textarea class="BigTextarea" name="content" style="height:160px;width:550px"></textarea>
				</td>
			</tr>
			<tr>
				<td class="TableData" align="right">奖惩标准：</td>
				<td class="TableData">
					<textarea class="BigTextarea" name="standard" style="height:120px;width:550px"></textarea>
				</td>
			</tr>
			<tr style="border-bottom:none;">
				<td class="TableData" align="right">备注批示：</td>
				<td class="TableData">
					<textarea class="BigTextarea"  name="leaderRemark" style="height:120px;width:550px"></textarea>
				</td>
			</tr>
		</table>
		</form>
	</div>
</div>
</div>
</body>
</html>
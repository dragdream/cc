<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%
	String sid = request.getParameter("sid");
	String contractId = request.getParameter("contractId");
%>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>提醒设置</title>
	<script type="text/javascript" charset="UTF-8">
	var sid = "<%=sid%>";
	
	$(function() {
		if(sid!="null"){
			var json = tools.requestJsonRs(contextPath+"/contractRemind/get.action",{sid:sid});
			bindJsonObj2Cntrl(json.rtData);
		}
		
	});
	
	function check(){
		if($("#form1").form("validate")){
			return true;
		}
		return false;
	}
	
	function commit(){
		if(!check()){
			return;
		}
		
		var para = tools.formToJson($("#form1"));
		
		var url = "";
		if(sid!="null"){
			url = contextPath+"/contractRemind/update.action";
		}else{
			url = contextPath+"/contractRemind/add.action";
		}
		
		var json = tools.requestJsonRs(url,para);
		alert(json.rtMsg);
		return json.rtState;
	}
	</script>
</head>
<body >
<center>
	<table id="form1" class="TableBlock" style="font-size:12px;width:500px">
		<input type="hidden" name="sid" id="sid" value="<%=sid==null?"0":sid %>" />
		<input type="hidden" name="contractId" id="contractId" class="BigInput" value="<%=contractId==null?"0":contractId %>"/>
		<tr>
			<td class="TableData">提醒内容：</td>
			<td class="TableData">
				<textarea type="text" name="remindContent" id="remindContent" class="BigTextarea easyui-validatebox" required style="height:100px;width:350px"></textarea>
			</td>
		</tr>
		<tr>
			<td class="TableData">提醒时间：</td>
			<td class="TableData">
				<input type="text" name="remindTimeDesc" id="remindTimeDesc" class="Wdate BigInput" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"/>
			</td>
		</tr>
		<tr>
			<td class="TableData">相关人员：</td>
			<td class="TableData">
				<input type="hidden" name="toUserIds" id="toUserIds"/>
				<textarea class="BigTextarea"  name="toUserNames" readonly id="toUserNames" style="width:250px;height:50px"></textarea>
				<a href="javascript:void(0)" onclick="selectUser(['toUserIds','toUserNames'])">选择</a>
				&nbsp;
				<a href="javascript:void(0)" onclick="clearData('toUserIds','toUserNames')">清空</a>
			</td>
		</tr>
	</table>
</center>
</body>
</html>
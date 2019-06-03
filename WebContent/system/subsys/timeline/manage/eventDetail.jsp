<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid")==null?"0":request.getParameter("sid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<style>
	td{
		line-height:28px;
		min-height:28px;
	}
.ztree{
		background:white;
		border:1px solid #f0f0f0;
	}
</style>

<script>
var sid = "<%=sid%>";
function doInit(){
	if(sid!="" && sid!=null && sid!="null"){
		var url = "<%=contextPath%>/TeeTimelineEventController/getById.action?uuid="+sid;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			bindJsonObj2Cntrl(json.rtData);
			/**
			*处理附件
			*/
			var  attachmodels = json.rtData.attacheModels;
			for(var i=0;i<attachmodels.length;i++){
				var fileItem = tools.getAttachElement(attachmodels[i]);
				$("#attachments").append(fileItem);
			}
		}
	}
}
</script>
</head>
<body onload="doInit();" style="padding: 10px;background-color: #f2f2f2">
<form id="form1" name="form1">
	<table class='TableBlock' width="100%">
		<tr>
			<td class="TableData" style="text-indent: 10px;width:100px;">
				<b>标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题：</b>
			</td>
			<td class="TableData">
				<span id="title" name="title" ></span>
			</td>
		</tr>
		<tr> 
			<td class="TableData" style="text-indent: 10px;">
				<b>开始时间：</b>
				</td>
			<td class="TableData">
				<span  id='startTimeDesc'  name='startTimeDesc' ></span>
			</td>
		</tr >
		<tr>
			<td class="TableData" style="text-indent: 10px;">
				<b>结束时间：</b>
				</td>
			<td class="TableData">
				<span  id='endTimeDesc'  name='endTimeDesc' ></span>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent: 10px;">
				<b>内&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;容：</b>
				</td>
			<td class="TableData">
				<span  id="content" name="content"  >
				</span>
			</td>
		</tr >
		<tr style="display:none">
			<td class="TableData" style="text-indent: 10px;">
				<b>附件上传：</b>
			</td>
			<td class="TableData">
		       	<span id="attachments"></span>
		    </td>
		</tr>
	</table>
	<br/>
	<div id="control" style='margin:0 auto;text-align:center; height:28px;line-height:28px;width:"90%"'>
		<input id="uuid" name="uuid" type='hidden'value="<%=sid %>"/>
	</div>
</form>
</body>
</html>
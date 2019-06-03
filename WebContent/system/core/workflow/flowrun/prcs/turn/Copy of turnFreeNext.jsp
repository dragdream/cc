<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/header/header.jsp"%>
<%
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"),0);
		int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"),0);
		int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"),0);
		String runName =request.getParameter("runName");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>转交工作</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="<%=cssPath%>/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/common/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/common/easyui/themes/gray/easyui.css">
<link rel="stylesheet"
	href="<%=contextPath%>/common/jquery/ztree/css/demo.css"
	type="text/css">
<link rel="stylesheet"
	href="<%=contextPath%>/common/jquery/ztree/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css">


<script type="text/javascript"
	src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/common/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/common/jquery/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/common/js/src/teeValidagteBox.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/tools.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/common/js/ZTreeSync.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/common/js/easyuiTools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sys.js"></script>

<script type="text/javascript"
	src="<%=contextPath%>/system/core/person/js/person.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/common/js/src/orgselect.js"></script>




<script type="text/javascript">
var runId = <%=runId%>;
var flowId = <%=flowId%>;
var frpSid = <%=frpSid%>;
var runName = '<%=runName%>';

var parentWindowObj;
/**
 * 由于是自由流程 所以 选择人员不过滤
 */
function doInit(){
	//parentWindowObj = window.dialogArguments;
		$("#runName").html(runName);
		$("#runId").html(runId);
		$("#currUserId").html(userId);
}

function turnNextPrcs(){
	
	var opUser = $('#opUser').val();
	if(!opUser){
		alert("请选择主办人!");
		return ;
	}
	var url = contextPath+"/flowRun/turnNextHandler.action";
	var para = {};
	para['runId'] =  runId;
	para['flowId'] =  flowId;
	para['frpSid'] =  frpSid;
	para['opUser'] = $('#opUser').val();
	para['prcsUser'] = $('#prcsUser').val();
	var json = tools.requestJsonRs(url,para);
	if(json.rtState){
		alert(json.rtMsg);
		CloseWindow();
		window.location = contextPath +"/system/core/workflow/flowrun/list/index.jsp";
		
	}
}

</script>
<style>
</style>
</head>
<body onload="doInit()">
<center>
<br />
<table width="100%" class="small" border="0" cellSpacing="0"
	cellPadding="3">
	<TBODY>
		<TR>
			<TD class=Big><IMG align=absMiddle
				src="<%=imgPath%>/green_arrow.gif"><SPAN class=big3>
			转交下一步骤</SPAN></TD>
		</TR>
	</TBODY>
</table>
<br />
<table width="95%" class="TableTop">
	<TBODY>
		<TR>
			<TD class=left></TD>
			<TD class=center><IMG align="absMiddle"
				src="<%=imgPath%>/workflow.gif" complete="complete" /> 流水号 -<span id="runId"></span>&nbsp;&nbsp;
			<span id="runName"></span> &nbsp;&nbsp;</TD>
			<TD class=right></TD>
		</TR>
	</TBODY>
</table>


<table width="95%" class="TableList no-top-border" border="0">
	<TBODY>
		<TR height=25>
			<TD class="TableContent" colSpan=2>
			<DIV style="float: left">&nbsp;当前步骤为第<span id="currPrcsId"></span>步骤</DIV>
			<DIV style="width: 100%"><FONT color="red"><span id="currUserId"></span>(办理中)</FONT></DIV>
			</TD>
		</TR>

		<TR class="TableData">
			<TD colSpan="2">
			<DIV class="TableHeader PrcsName">选择办理人员</DIV>
			<DIV style="LINE-HEIGHT: 18px; MARGIN-LEFT: 5px">
			<table>
				<tr>
					<td nowrap class="TableData">主办人：</td>
					<td nowrap class="TableData"><input type="hidden"
						name="opUser" id="opUser" value=""> <textarea cols="15"
						name="opUserDesc" id="opUserDesc" rows="1"
						style="overflow-y: auto;" class="SmallStatic" wrap="yes" readonly></textarea>
					<a href="javascript:void(0);" class="orgAdd"
						onClick="selectUser(['opUser', 'opUserDesc'])">添加</a> <a
						href="javascript:void(0);" class="orgClear"
						onClick="clearData('opUser', 'opUserDesc')">清空</a></td>
				</tr>
				<tr>
					<td nowrap class="TableData">经办人：</td>
					<td nowrap class="TableData">
					<input type="hidden" name="prcsUser" id="prcsUser" value="">
						 <textarea cols="35" name="prcsUserDesc" id="prcsUserDesc" rows="2"
						 style="overflow-y: auto;" class="SmallStatic" wrap="yes" readonly></textarea>
					<a href="javascript:void(0);" class="orgAdd"
						onClick="selectUser(['prcsUser', 'prcsUserDesc'])">添加</a> <a
						href="javascript:void(0);" class="orgClear"
						onClick="clearData('prcsUser', 'prcsUserDesc')">清空</a></td>
				</tr>
			</table>
			</DIV>
			</TD>
		</TR>
		<TR class="TableContent">
			<TD colSpan="2">
			<DIV style="margin-bottom: 5px; font-weight: bold">向以下人员发送事务提醒消息</DIV>
			<span style="margin-right: 5px"><B>下一步骤</B>： 
				<input type="checkbox" />
				<img alt="" align="absMiddle" src="<%=imgPath%>/sms.gif"> 
				<input type="checkbox" />
				<img align="absMiddle" src="<%=imgPath%>/webmail.gif" > 
			</span>
			
			<span style="margin-right: 5px"><B>办理人</B>： 
				<input type="checkbox" />
				<img alt="" align="absMiddle" src="<%=imgPath%>/sms.gif"> 
			</span>
			<DIV>
			<DIV style="margin-top: 5px">提醒内容：
				<input class="SmallInput" value="您有新的工作需要办理，流水号：3，工作名称/文号：收文流程(2013-09-08 16:31:48)" size="70" type="text" />
			</DIV>
			</DIV>
			</TD>
		</TR>
		<TR class=TableControl>
			<TD style="text-align: right; width: 65%; border-right: medium none" nowrap align=middle>
				 <input class="BigButtonB"  value="确认转交" type="button" onclick="turnNextPrcs()" >&nbsp;&nbsp; 
				 <input class="BigButtonB" onclick="" value="继续办理" type="button">&nbsp;&nbsp;
				 <input class="BigButtonC" onclick="" value="取消转交并返回 " type="button" />&nbsp;&nbsp;
			</TD>
			<TD style="text-align: left; border-left: medium none">
			</TD>
		</TR>
	</TBODY>
</table>
</center>
</body>
</html>

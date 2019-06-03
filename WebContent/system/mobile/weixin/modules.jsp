<%@page import="com.tianee.oa.subsys.weixin.ParamesAPI.AccessToken"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String modelType = TeeStringUtil.getString(request.getParameter("modelType"), "email");
%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/header/header.jsp"%>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>微信应用模块设置</title>

<script type="text/javascript" charset="UTF-8">

function doInit() {
	var json = tools.requestJsonRs(contextPath
			+ "/weixin/getAppParam.action" , {modelType:'<%=modelType%>'});
	bindJsonObj2Cntrl(json.rtData);
}

function save() {
	var json = tools
			.requestJsonRs(contextPath + "/weixin/saveAppParam.action",
					tools.formToJson($("#form")));
	window.location.reload();
}

function initAppMenu(){
	var json = tools.requestJsonRs(contextPath + "/weixin/initAppMenu.action",
			tools.formToJson($("#form")));
	if(json.rtState){
		alert("初始化成功！");
	}else{
		alert(json.rtMsg);
	}
}
</script>
</head>
<body onload="doInit();" id="form">
	<fieldset>
		<legend>
			<h5 style="font-size: 14px; font-weight: bold; font-family: 微软雅黑">
				<%if(modelType.equals("021")){ %>
						公告通知
				<%}else if(modelType.equals("006")){ %>
						工作流
				<%}else if(modelType.equals("022")){ %>
						日程安排
				<%}else if(modelType.equals("018")){ %>
						工作日志
				<%}else if(modelType.equals("020")){ %>
						新闻管理
				<%}else if(modelType.equals("043")){ %>
						计划管理
				<%}else if(modelType.equals("035")){ %>
						任务管理
				<%}else if(modelType.equals("044")){ %>
						客户管理
				<%}else if(modelType.equals("050")){ %>
						讨论区
				<%}else if(modelType.equals("report")){ %>
						报表管理
				<%}else if(modelType.equals("search")){ %>
						查询
				<%}else if(modelType.equals("024")){ %>
						公共网盘
				<%}else if(modelType.equals("025")){ %>
						个人网盘
				<%}else{ %>
						电子邮件
				<%} %>
	
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="help2/index.jsp" target="_blank">点击查看帮助文档</a> <br />
			</h5>
		</legend>
	
		<table style="font-size: 12px;">
			<tr>
				<td style="width: 80px"></td>
				<td style="vertical-align: middle;height:50px;">
					<%if(modelType.equals("021")){ %>
						<img alt="" src="../icons/notify.png" style="height: 70px; width: 70px;" /> 
					<%}else if(modelType.equals("006")){ %>
						<img alt="" src="../icons/work_flow.png" style="height: 70px; width: 70px;" /> 
					<%}else if(modelType.equals("020")){ %>
						<img alt="" src="../icons/news.png" style="height: 70px; width: 70px;" /> 
					<%}else if(modelType.equals("022")){ %>
						<img alt="" src="../icons/calendar.png" style="height: 70px; width: 70px;" /> 
				<%}else if(modelType.equals("018")){ %>
						<img alt="" src="../icons/diary.png" style="height: 70px; width: 70px;" /> 
				<%}else if(modelType.equals("043")){ %>
						<img alt="" src="../icons/schedule.png" style="height: 70px; width: 70px;" /> 
				<%}else if(modelType.equals("035")){ %>
						<img alt="" src="../icons/task.png" style="height: 70px; width: 70px;" /> 
				<%}else if(modelType.equals("044")){ %>
						<img alt="" src="../icons/customer.png" style="height: 70px; width: 70px;" /> 
				<%}else if(modelType.equals("050")){ %>
						<img alt="" src="../icons/topic.png" style="height: 70px; width: 70px;" /> 
				<%}else if(modelType.equals("024")){ %>
						<img alt="" src="../icons/public_file.png" style="height: 70px; width: 70px;" /> 
				<%}else if(modelType.equals("025")){ %>
						<img alt="" src="../icons/personal_file.png" style="height: 70px; width: 70px;" /> 
				<%}else if(modelType.equals("019")){ %>
						<img alt="" src="../icons/email.png" style="height: 70px; width: 70px;" /> 
				<%} %>
					请保存此图标作为应用logo
					</td>
			</tr>
			<tr>
				<td style="width: 80px">应用ID：</td>
				<td style="vertical-align: middle;height:50px;">
					<input type="text" class="BigInput " id="WEIXIN_APPID" name="WEIXIN_APPID" />
				</td>
			</tr>
			<tr>
				<td style="width: 80px">Secret：</td>
				<td style="vertical-align: middle;height:50px;">
					<input type="text" style="width: 450px;" class="BigInput " id="WEIXIN_APPSECRET" name="WEIXIN_APPSECRET" />
				</td>
			</tr>
			<tr>
				<td style="width: 80px">微信单点地址：</td>
				<td style="vertical-align: middle;height:50px;">
					<input type="text" style="width: 450px;" class="BigInput " id="WEIXIN_MENU_URL" disabled="disabled" name="WEIXIN_MENU_URL" />
				</td>
			</tr>
			<tr>
				<td style="width: 80px">URL：</td>
				<td style="vertical-align: middle;height:50px;">
					<input type="text" style="width: 450px;" class="BigInput" value=""  disabled="disabled" id="WEIXIN_APP_URL" name="WEIXIN_APP_URL"  />
				</td>
			</tr>
			<tr>
				<td style="width: 80px">Token：</td>
				<td style="vertical-align: middle;height:50px;">
					kUt0rkMMSE
				</td>
			</tr>
			<tr>
				<td style="width: 80px">EncodingAESKey：</td>
				<td style="vertical-align: middle;height:50px;">
					eIRvdsAodEJBeB2I4aTAOxmUaLxEuv8wdrUPzfhV35e
				</td>
			</tr>
			<tr>
			<td style="width: 80px"></td>
				<td style="vertical-align: middle;height:50px;" >
					<input type="hidden" name="modelType" value="<%=modelType %>">
						<button type="button" class="btn" onclick="save()">保存</button>
<!-- 						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
<!-- 						<button type="button" class="btn btn-primary" onclick="initAppMenu()">初始化菜单</button>&nbsp;&nbsp;点击初始化菜单，可以自动创建微信企业号应用对应的菜单 -->
				</td>
			</tr>
		</table>
	</fieldset>
	<br />
</body>
</html>
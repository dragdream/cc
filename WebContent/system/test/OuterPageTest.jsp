<%@page import="com.tianee.oa.core.workflow.flowrun.bean.FlowRunToken"%>
<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
	<%@ include file="/header/header2.0.jsp" %>
	<script src="<%=contextPath %>/common/js/workflowUtils.js"></script>
	<title>业务引擎测试</title>
	<script type="text/javascript">
	<%	
		//从token中获取流程数据
		FlowRunToken token = new FlowRunToken(request);
	
		//可以获取到之前发起流程时，所带入的业务表主键，然后就各种使用吧！！
		String primaryId = token.getVars().get("PRIMARY_ID");
	%>
	//从token中获取到四个重要数据
	var runId = "<%=token.getRunId()%>";
	var frpSid = "<%=token.getFrpSid()%>";
	var flowId = "<%=token.getFlowId()%>";
	var goBack = "<%=token.getGoBack()%>";
	
	//创建工作流工具对象
	var workFlowUtil = new WorkFlowUtil(runId,frpSid,flowId,goBack);
	
	function turnNext(){
		//第一步：先将业务表数据保存至数据库中
		//…………………具体保存的逻辑…………………
		
		//第二步：调用turnNext转交方法！
		workFlowUtil.turnNext("转交",function(rt){
			if(rt==true){
				alert("转交成功");
				window.close();
			}else{
				alert("转交失败");
			}
			
		});
	}
	
	function backTo(){
		//第一步：先将业务表数据保存至数据库中
		//…………………具体保存的逻辑…………………
		
		//第二步：调用turnNext转交方法！
		workFlowUtil.backTo(function(rt){
			if(rt==true){
				alert("回退成功");
				window.close();
			}else{
				alert("回退失败");
			}
		});
	}
	</script>
</head>
<body onload="doInit()" style="padding:10px">
	<%
		
		//如果当前节点没有办理完  并且  当前节点的办理人为当前登陆人时，可进行表单的填写或办理
		if(!token.isFinished() && token.isPrcsUser()){
			%>
			<p>姓名：<input type="" name=""  /></p>
			<p>性别：<input type="" name=""  /></p>
			<p>年龄：<input type="" name=""  /></p>
			<p>出生日期：<input type="" name=""  /></p>
			<p>
				<button onclick="turnNext()">转交</button>
				<button onclick="backTo()">回退</button>
			</p>
			<%
		}else{//否则没有权限办理，只能查看
			%>
			<p>姓名：杨冰</p>
			<p>性别：男</p>
			<p>年龄：27</p>
			<p>出生日期：1990-03-01</p>
			<%
		}
	%>
</body>
</html>

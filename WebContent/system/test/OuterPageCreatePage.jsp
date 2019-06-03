<%@page import="com.tianee.oa.core.workflow.flowrun.bean.FlowRunToken"%>
<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
	<%@ include file="/header/header2.0.jsp" %>
	<%	
		//从token中获取流程数据
		FlowRunToken token = new FlowRunToken(request);
	
		//可以获取到之前发起流程时，所带入的业务表主键，然后就各种使用吧！！
		String primaryId = token.getVars().get("PRIMARY_ID");
	%>
	<title></title>
	<script type="text/javascript">
	
	var runId=0;
	var frpSid;
	var flowId;
	var primaryId = "";
	
	function save(){
		//先保存业务表中的数据
		//var json = 
		//	tools.requestJsonRs(contextPath+"/这个是业务表负责保存数据的action",
		//			{name:name.value,sex:sex.value,age:age.value,birthday:birthday.value});
		//var parimayKey = json.rtData;//此处是业务action保存数据成功后，返回回来的业务表主键
		var parimayKey = 12;
		
		//发起流程的ajax请求接口
		//其中fType为流程定义ID，runName为默认发起流程时的工作名称
		//比如要把上面生成的业务表主键带入流程中，那么就以流程变量的形式传入  规则为  VAR_ + 自定义参数名
		var runJson = tools.requestJsonRs(contextPath + "/flowRun/createNewWork.action",
				{fType : "4031",runName:"默认工作名称","VAR_PRIMARY_ID":parimayKey,runId:runId});
		
		//如果发起成功，则跳转到对应的流程办理页面
		if (runJson.rtState) {
			
			runId = runJson.rtData.runId;
			frpSid = runJson.rtData.frpSid;
			flowId = runJson.rtData.flowId;
			
// 			//具体处理方式可以按实际要求处理，跳与不跳都可以
// 			window.openFullWindow(contextPath
// 					+ "/flowRun/toUrl.action?runId="
// 					+ runJson.rtData.runId + "&frpSid=" + runJson.rtData.frpSid
// 					+ "&flowId=" + runJson.rtData.flowId + "&isNew=1", "流程办理");


			//创建工作流工具对象
			var workFlowUtil = new WorkFlowUtil(runId,frpSid,flowId,0);
			workFlowUtil.turnNext("转交",function(rt){
				if(rt==true){
					alert("转交成功");
					window.close();
				}else{
					alert("转交失败");
				}
				
			});
			
			
		} else {
			alert(runJson.rtMsg);
		}
	}
	</script>
</head>
<body>
	<div class="title">
		<div class="titleName">表单--流程分离（创建页面）</div>
		<div class="btns">
			<input class="btn_default" onclick="save();" type="button" value="新建(点击这里看效果)">
			<input class="btn_default btn_logo" type="button" value="打印">
			<input class="btn_default" type="button" value="保存">
			<input class="btn_default" type="button" value="取消">
		</div>
	</div>
	<div class="content">
		<table class="tableBlock">
			<tr>
				<td>文件类别号</td>
				<td>
					<input class="tableInput" type="text">
					<span class='unNull'>*</span>
				</td>
			</tr>
			<tr>
				<td>登记时间</td>
				<td colspan="3">
					<input class="tableInput" type="text">
					<span class='unNull'>*</span>
				</td>
			</tr>
			<tr>
				<td>来文文号</td>
				<td>
					<input class="tableInput" type="text">
				</td>
			</tr>
			<tr>
				<td>拟处理意见</td>
				<td>
					<textarea class="tableTextarea" name="" id="" cols="15" rows="3"></textarea>
				</td>
			</tr>
			<tr>
				<td>文件种类</td>
				<td>
					<select class="tableSelect" name="" id="">
						<option value="">国务院文件</option>
						<option value="">值班快报</option>
						<option value="">部委文件</option>
						<option value="">工作情况交流</option>
						<option value="">业务通讯</option>
						<option value="">政务情况交流</option>
						<option value="">中央纪委文件</option>
						<option value="">昨日要情</option>
						<option value="">北京市委市政府文件</option>
						<option value="">中共中央办公厅文件</option>
						<option value="">中办快报</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>成文日期</td>
				<td>
					<input class="tableInput" type="text">
				</td>
				<td>来文份数</td>
				<td>
					<input class="tableInput" type="text">
				</td>
				<td>登记人</td>
				<td>
					<input class="tableInput" type="text">
				</td>
			</tr>
		</table>
	</div>
</body>
</html>

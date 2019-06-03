<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<% 
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"),0);
%>
<title>管理数据源</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
var sid = <%=sid%>;

function doInit(){
	if(sid!=0){
		var url = contextPath+"/bisDataSource/get.action";
		var json = tools.requestJsonRs(url,{sid:sid});
		if(json.rtData.dataSource==2){//外部数据源
			$("#testConnectBtn").show();
			$("#driverClassTr").show();
			$("#driverUrlTr").show();
			$("#driverUsernameTr").show();
			$("#driverPwdTr").show();
		}
		bindJsonObj2Cntrl(json.rtData);
	}else{
		$("#configModel").val("initialPoolSize=20\nmaxPoolSize=100\nminPoolSize=20\nacquireIncrement=1\nmaxIdleTime=60\nidleConnectionTestPeriod=120\ntestConnectionOnCheckin=true");
	}
}

function commit(){
	if(!$("#form1").validate()){
		return false;
	}
	
	var url;
	if(sid==0){
		url = contextPath+"/bisDataSource/add.action";
	}else{
		url = contextPath+"/bisDataSource/update.action";
	}

	var params = tools.formToJson($("#form1"));
	var json = tools.requestJsonRs(url,params);
	if(json.rtState){
		$.MsgBox.Alert_auto(json.rtMsg,function(){
			window.location = "list.jsp";
		});
		
	}else{
		$.MsgBox.Alert_auto(json.rtMsg);
	}
}

//连接测试
function testConnect(){
	var param = tools.formToJson($("#form1"));
	var url=contextPath+"/bisDataSource/testConn.action";
    var json=tools.requestJsonRs(url,param);
    if(json.rtState){
		$.MsgBox.Alert_auto("连接成功！");
	}else{
		$.MsgBox.Alert_auto(json.rtMsg);
	}
}

//改变数据源类型调用的方法
function changeType(){
	var dataSource=$("#dataSource").val();
	if(dataSource==1){//内部数据源
		$("#testConnectBtn").hide();
		$("#driverClassTr").hide();
		$("#driverUrlTr").hide();
		$("#driverUsernameTr").hide();
		$("#driverPwdTr").hide();
	}else{//外部数据源
		$("#testConnectBtn").show();
		$("#driverClassTr").show();
		$("#driverUrlTr").show();
		$("#driverUsernameTr").show();
		$("#driverPwdTr").show();
	}
}
</script>
</head>
<body style="font-size:12px;padding-left: 10px;padding-right: 10px" onload="doInit()" >


<div id="toolbar" class = "topbar clearfix">
   <div class="fl" style="position:static;">
		<span class="title">
          <b>
	     <%
		  if(sid==0){
			out.print("添加");
		  }else{
			out.print("编辑");
		  }
	     %>数据源</b>
        </span>
	</div>
	<div class = "right fr clearfix">
	    <button id="testConnectBtn" type="button" class="btn-win-white" onclick="testConnect()"  style="display: none;">连接测试</button>
	    <button type="button" class="btn-win-white" onclick="commit()">提交</button>
	    <button type="button" class="btn-win-white" onclick="window.location = 'list.jsp'">返回</button>
	</div>
</div>
<form id="form1">
	<table class="TableBlock_page" style="">
		<tr>
			<td class="TableData" style="text-indent: 15px">数据源名称：</td>
			<td class="TableData">
				<input type="text" id="dsName" name="dsName" class="BigInput"/>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent: 15px">数据库类型：</td>
			<td class="TableData">
				<select id="dbType" name="dbType" class="BigSelect">
					<option value="MYSQL">MYSQL</option>
					<option value="SQLSERVER">SQLSERVER</option>
					<option value="ORACLE">ORACLE</option>
					<option value="DAMENG">DAMENG</option>
					<option value="KINGBASE">KINGBASE</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent: 15px">数据源类型：</td>
			<td class="TableData">
				<select id="dataSource" name="dataSource" class="BigSelect" onchange="changeType()">
					<option value="1">内部</option>
					<option value="2">外部</option>
				</select>
			</td>
		</tr>
		<tr id="driverClassTr" style="display: none">
			<td class="TableData" style="text-indent: 15px">连接驱动类：</td>
			<td class="TableData">
				<input type="text" id="driverClass" name="driverClass" class="BigInput"/>
			</td>
		</tr>
		<tr id="driverUrlTr" style="display: none">
			<td class="TableData" style="text-indent: 15px">驱动连接字符串：</td>
			<td class="TableData">
				<input type="text" id="driverUrl" name="driverUrl" class="BigInput"/>
			</td>
		</tr>
		<tr id="driverUsernameTr" style="display: none">
			<td class="TableData" style="text-indent: 15px">驱动连接用户名：</td>
			<td class="TableData">
				<input type="text" id="driverUsername" name="driverUsername" class="BigInput"/>
			</td>
		</tr>
		<tr id="driverPwdTr" style="display: none">
			<td class="TableData" style="text-indent: 15px">驱动连接密码：</td>
			<td class="TableData">
				<input type="text" id="driverPwd" name="driverPwd" class="BigInput"/>
			</td>
		</tr>
		<tr style="display:none">
			<td class="TableData" style="text-indent: 15px">其他配置项：</td>
			<td class="TableData">
				<textarea type="text" id="configModel" name="configModel" class="BigTextarea" style="height:100px;width:250px;"></textarea>
			</td>
		</tr>
	</table>
	<input type="hidden" name="sid" value="<%=sid %>" />
</form>
</body>
</html>

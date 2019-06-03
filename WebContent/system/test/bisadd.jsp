<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/header/header.jsp" %>
	<%@ include file="/header/easyui.jsp" %>
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	<title>业务引擎测试</title>
	<script type="text/javascript">
	
	function doInit(){
		
	}
	
	function commit(){
		var param = tools.formToJson($("#form"));
		param["bisTableName"] = "bis_test";
		var json = tools.requestJsonRs(contextPath+"/bisDataFetch/dfsave.action",param);
		if(json.rtState){
			window.location = "bistest.jsp";
		}else{
			alert(json.rtMsg);
		}
	}
	</script>
</head>
<body onload="doInit()" style="padding:10px">
	<form id="form">
	姓名：<input type="text" class="BigInput" id="NAME" name="B|NAME|STRING"/>
	<br/>
	年龄：<input type="text" class="BigInput" id="AGE" name="B|AGE|INT"/>
	<br/>
	出生年月：<input type="text" class="BigInput Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="BIRTHDAY" name="B|BIRTHDAY|DATE"/>
	<br/>
	<button class="btn btn-primary" type="button" onclick="commit()">提交</button>
	<button class="btn btn-primary" type="button" onclick="history.go(-1)">返回</button>
	</form>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int userId= TeeStringUtil.getInteger(request.getParameter("userId"), 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@ include file="/header/header.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>用户信息查看</title>
	
	<script type="text/javascript" charset="UTF-8">
	var contextPath = '<%=contextPath%>';
	var userId ='<%=userId%>';
	window.onload=function(){
		
		var url = contextPath+"/user/getUserModel.action";
		var json = tools.requestJsonRs(url,{userId:userId});
		if(json.rtState){
			//alert(json.rtData.deptName);
			bindJsonObj2Cntrl(json.rtData);
			$("#userName").html(json.rtData.userName);
			$("#gender").html(json.rtData.gender);
			$("#age").html(json.rtData.age);
			$("#department").html(json.rtData.deptName);
		}
		
	}
	</script>
</head>
<body>
<table id="datagrid"></table>
<div id="toolbar">
	<div class="panel panel-default">
	  <div class="panel-heading">
	    <h3 class="panel-title">用户名：</h3>
	  </div>
	  <div class="panel-body" style="">
	    <div class="" name="userName" id="userName" style="padding:10px;"></div>
	  </div>
	  
	  <div class="panel-heading">
	    <h3 class="panel-title">性别：</h3>
	  </div>
	  <div class="panel-body" style="">
	    <div class="" name="gender" id="gender" style="padding:10px;"></div>
	  </div>
	  
	  <div class="panel-heading">
	    <h3 class="panel-title">年龄：</h3>
	  </div>
	  <div class="panel-body" style="">
	    <div class="" name="age" id="age" style="padding:10px;"></div>
	  </div>
	  
	  <div class="panel-heading">
	    <h3 class="panel-title">所属部门：</h3>
	  </div>
	  <div class="panel-body" style="">
	    <div class="" name="department" id="department" style="padding:10px;"></div>
	  </div>
	</div>
	
</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%
	String userName = TeeStringUtil.getString(request.getParameter("userName"));
	String pwd = TeeStringUtil.getString(request.getParameter("pwd"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

<%@ include file="/header/header.jsp"%>
<title><%=ieTitle%></title>
<script type="text/javascript">
/**
 * 用户名密码登录

 */
function loginNamePass() {
  	if($('#userName').val() == ''){
   		 $('#msrgOut').html("没有传递用户名");
   		 return;
  	}
  	var url = "<%=contextPath%>/systemAction/doLoginIn.action";
	var para =  tools.formToJson($("#loginForm")) ;
	var json = tools.requestJsonRs(url,para);
  	if(json.rtState){
  		var data = json.rtData;
	    var theme = data.theme;
	    var themeIndex = 1;
	    var index = 1;
	    if(theme.length == 2){
	    	themeIndex = theme.substring(0,1);
	    	index = theme.substring(1,2);
	    }
	    if(themeIndex == 2){//第二套
	    	  window.location.href = "<%=contextPath%>/system/frame/2/index.jsp";
	    }else{
	    	  window.location.href = "<%=contextPath%>/system/frame/default/index.jsp";
	    }
  	}else{
   		 $('#msrgOut').html(json.rtMsg);
  	}
}

function doInit(){
  loginNamePass();
}
</script>
</head>
<body onload="doInit()">
<table align="center" class="MessageBox" width="300">
  <tr>
    <td id="msrgOut" class="msg info">
     &nbsp;正在登录，请稍候...    
    </td>
  </tr>
</table>
<%
	if (!TeeUtility.isNullorEmpty(userName)) {
%>
<form method=post action="" id='loginForm' name="loginForm">
  <input type="hidden" name="userName" id="userName" value="<%=userName %>"></input>
  <input type="hidden" name="pwd" id="pwd" value="<%=pwd %>"></input>
</form>
<%
}
%>
</body>
</html>

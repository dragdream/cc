<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%

	int checkValidType = TeeStringUtil.getInteger(request.getParameter("checkValidType") , 0);
%>
<html>
<head>
<%@ include file="/header/header2.0.jsp" %>
<title>ERROR</title>
<script>
/* 页面访问失败提醒界面原因
* 默认-未登录或已登录超时
* 1-无权限访问
* 
*
*/
var checkValidTypeArray = ["您未登录或登录超时，请重新登录" , "暂无权限访问,请与系统管理员联系！"];
var checkValidType = "<%=checkValidType%>";
function doInit(){
	messageMsg(checkValidTypeArray[checkValidType],"content","info",400);
	document.title = checkValidTypeArray[checkValidType];
	if(checkValidType != 0){
		$("#reLoginButton").hide();
	}
}

/**
 * 获取最顶层窗口
 */
var topWin= (function (p,c){
    while(p!=c){
        c = p;   
        p = p.parent;
    }
    return c;
})(window.parent,window);


function toReLogin(){
	topWin.location = '/<%=contextPath%>';
}
</script>
</head>

<body onload="doInit()">

<center>
<div id="content" style="padding-top:30px;">

</div>
</center>

<center id='reLoginButton' style="padding:10px"><button class="btn-win-white" onClick="toReLogin();">返回</button></center>

</body>
</html>

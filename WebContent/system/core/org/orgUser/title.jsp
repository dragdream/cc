<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    //校验资源权限 参数1:菜单资源权限编码  ;；  参数2：是否检查 true-需要检查 false-不需要检查  ; 参数5：扩展字段
    //TeeResPrivServlet.checkResPriv("001001", true ,request ,response ,"");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<title>组织机构</title>
<link href="<%=cssPath %>/style.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
function toOrgUser(type){
	var url = "<%=contextPath%>/system/core/org/orgUser/orgOnlineUser.jsp";
	if(type == '1'){
		url = "<%=contextPath%>/system/core/org/orgUser/orgAllUser.jsp";
	}
	parent.orgUser.location = url;
}
</script>
</head>
<body style="text-align:center">
<div class="btn-group" data-toggle="buttons">
  <label class="btn btn-default active" onclick="toOrgUser('0')">
    <input type="radio" name="options" id="option1">在线人员
  </label>
  <label class="btn btn-default" onclick="toOrgUser('1')">
    <input type="radio" name="options" id="option2">全部人员
  </label>
</div>
</body>
</html>
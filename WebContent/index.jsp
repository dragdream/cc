<%@ page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%
if("1".equals(TeeSysProps.getString("IS_GOV"))){
	request.getRequestDispatcher("/login_gov.jsp").forward(request, response);
}else{
	request.getRequestDispatcher("/login.jsp").forward(request, response);
}
%>
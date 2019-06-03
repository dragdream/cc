<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	
%>
<script src="<%=contextPath %>/common/easyui/jquery.min.js"></script>
<script src="<%=contextPath %>/common/js/tools.js"></script>
<script tyoe="text/javascript">
var contextPath='<%=contextPath%>';
var url = contextPath + "/systemAction/doLoginout.action";
var para = {};
var jsonObj = tools.requestJsonRs(url,para);
if(jsonObj){
	window.location.href = "/"+contextPath;
}
</script>
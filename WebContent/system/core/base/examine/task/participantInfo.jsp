<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%
	int groupId = TeeStringUtil.getInteger(request.getParameter("groupId"), 0);//指标集Id
	int taskId = TeeStringUtil.getInteger(request.getParameter("taskId"), 0);//考核任务Id
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header.jsp" %>
<title >考核指标集明细 </title>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/examine/js/examine.js"></script>
<script type="text/javascript">


var taskId = <%=taskId%>;
var groupId = <%=groupId%>;
function doInit(){
	var url =   "<%=contextPath%>/TeeExamineTaskManage/selectParticipantById.action";
	var para = {sid: taskId};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		
		var prcs = jsonObj.rtData;
		
		var table = "<table class='TableList' width='90%' align='center'>"
			+ "<tr class='TableHeader'><td>姓名</td>   <td>部门</td> <td>角色</td></tr>";
		$(prcs).each(function(i,item){
			 table =  table + '<tr class="TableData">'
			   + '<td ">'
			   + item.userName
			   +  '</td>'
			   + '<td align="left" id="item_score_' + item.sid + '">'
			   + item.deptName
			   + ' </td>'
			   + ' <td align="left" id="itemDesc_' + item.sid + '">'
			   + item.userRoleName
			   +' </td>'
			   + '</tr>';
			   //validType:validTypeStr
		});
		table = table + "</table>";
		$("#info").append(table);
	} else {
		alert(jsonObj.rtMsg);
	}
}


</script>

</head>

<body onload="doInit();">
<div id="info">

</div>

</body>
</html>



<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%
	int taskId = TeeStringUtil.getInteger(request.getParameter("taskId"), 0);//考核任务Id
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header.jsp" %>

<title >考核 </title>
<script type="text/javascript">
var taskId = <%=taskId%>;
function doInit(){

	var url =   "<%=contextPath%>/TeeExamineTaskManage/getById.action";
	var para = {sid:taskId};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		$("#tbody").empty();
		var prc = jsonObj.rtData;
		var participantIds = prc.participantIds;
		var participantNames = prc.participantNames;
		var participantList = prc.participantList;
		if(participantList.length > 0){
			/* var participantIdsArr = participantIds.split(",");
		
			var participantNamesArra = participantNames.split(","); */
			$(participantList).each(function(i,item){
				if(item){
					var userId = item.userId;
					var userName = item.userName;
					var isExamine = item.isExamine;
					var img = "";
					if(isExamine == '1'){
						img = "<img src='<%=stylePath%>/imgs/correct.gif' />" ;
					}
					var temp = "<a href='#' onclick='toExamine(" + userId  + ",this);'>" + userName + " <a/>";
					var tempStr = '<tr class="TableData" >'
					   + '<td width="140" align="center">'
					   + temp
					   + img
					   +  '</td>'
			
					   + '</tr>';
					   $("#tbody").append(tempStr);
					   //validType:validTypeStr
				}
				
			});
		}
		
	} else {
		alert(jsonObj.rtMsg);
	}
}

function toExamine(userId , obj){
	var userName = $(obj).html();
	parent.center.location = "<%=contextPath%>/system/core/base/examine/manage/examine.jsp?userId=" + userId + "&taskId=" + taskId + "&userName=" + encodeURIComponent(userName);
}
</script>

</head>

<body onload="doInit();">
<table border="0" width="100%" cellspacing="0" cellpadding="3"   style="margin:5px 0px;"  class="small">
  <tr>
    <td class="Big3"><span class="big3"></span>
    </td>
  </tr>
</table>

<div style="margin:0 auto;">
	<form name="form1" id="form1" method="post">
		<table class="TableList" width="85%" align="center">
			<tr class="TableHeader" align="center">
			    <td width="350" >被考核人列表</td>
			  
			</tr>
			<tbody id="tbody">
				
			</tbody>
		
		</table>
	
	</form>
</div>

</body>
</html>



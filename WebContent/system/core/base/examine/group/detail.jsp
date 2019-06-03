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
<%@ include file="/header/easyui.jsp" %>
<title >考核指标集明细 </title>
<script type="text/javascript">


var taskId = <%=taskId%>;
var groupId = <%=groupId%>;
function doInit(){

	var url =   "<%=contextPath%>/TeeExamineItemManage/getAllByGroupId.action";
	var para = {groupId:groupId};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		$("#tbody").empty();
		var prcs = jsonObj.rtData;
		$(prcs).each(function(i,item){
			
			var temp = item.itemName  ;
			var tempStr = '<tr class="TableData">'
			   + '<td ">'
			   + temp
			   +  '</td>'
			   + '<td align="left" id="item_score_' + item.sid + '">'
			   + item.itemMin +" ~ " + item.itemMax 
			   + ' </td>'
			   + ' <td align="left" id="itemDesc_' + item.sid + '">'
			   + item.itemDesc
			   +' </td>'
			   + '</tr>';
			   $("#tbody").append(tempStr);
			   //validType:validTypeStr
		});
		
	} else {
		alert(jsonObj.rtMsg);
	}
}


</script>

</head>

<body onload="doInit();">
<div style="margin:0 auto;">
	<form name="form1" id="form1" method="post">
		<table class="TableList" width="95%" align="center">
			<tr class="TableHeader" align="center">
			    <td width="120">考核项目</td>
			    <td width="80">分值范围</td>
			    <td width=>分值说明</td>
			</tr>
			<tbody id="tbody">
				
			</tbody>
		</table>
	
	</form>
</div>

</body>
</html>



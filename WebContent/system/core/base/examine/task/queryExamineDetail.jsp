<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
    
<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header.jsp" %>

<title >考核查阅</title>
<script type="text/javascript">
var sid = <%=sid%>;
function doOnload(){
	var url =   "<%=contextPath%>/TeeExamineTaskManage/queryExamineDetail.action";
	var para = {sid:sid} ;
	var jsonObj = tools.requestJsonRs(url, para);

	if (jsonObj.rtState) {
	
		var itemList = jsonObj.rtData.itemList;
		var prcs = jsonObj.rtData.data;
	
		if(prcs.length > 0){
			var table = "<table class='TableList' width='100%' align='center'>"
				+ "<tr class='TableHeader' ><td width='100'>姓名</td>   <td width='100'>部门</td> <td width='100'>角色</td>";
				
				$(itemList).each(function(i,item){
					table = table + "<td>" + item.itemName+ "</td>";
				});
				table = table  + "<td>总分</td> </tr>";
			$(prcs).each(function(i,item){
				//var userId = item.userId;
				var prc = item;
		
				 table =  table + '<tr class="TableData">'
				   + '<td ">'
				   + prc.userName
				   +  '</td>'
				   + '<td align="left" >'
				   + prc.deptName
				   + ' </td>'
				   + ' <td align="left" >'
				   + prc.userRoleName
				   +' </td>';
				   var total =0;
				$(itemList).each(function(j,itemDesc){
					var score = prc[itemDesc.itemId + ""];
					table = table + "<td>" + score + "</td>";
					total = prc.ALL;
				});
				table = table   + ' <td align="left" >'
				   + total
				   +' </td>'
				   + '</tr>';
			});
			table = table + "</table>";
			$("#info").append(table);
		}
	}else{
		alert(jsonObj.rtMsg);
	} 
}

</script>

</head>

<body onload="doOnload();">

<div id="info">
		

</div>
</body>
</html>



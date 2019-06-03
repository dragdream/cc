<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String sid = request.getParameter("sid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background-color: #f2f2f2">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>投票情况</title>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript">
var sid = '<%=sid%>';
function doInit(){
	getAttachSeriesTable(sid);
}

/* 获取数据列表 */
function getAttachSeriesTable(id){
	var url = "<%=contextPath%>/voteManage/showVoteDetail.action";
	var para = {sid:id};
	var jsonObj = tools.requestJsonRs(url,para);
	if (jsonObj.rtState) {
		var prcs = jsonObj.rtData;
		//alert(prc[0].modelName + ">>" + prc[0].modelCount);
		var counter = 0;
		if(prcs.length>0){
			$("#tableList").append("<table id='tbody'  style='border:#dddddd 2px solid;' width='100%' class='TableBlock_page'>"
					+"<tr class='TableHeader' style='background-color:#e8ecf9' >"
					+"<td style='text-indent:5px'>序号</td>"
					+"<td >姓名</td>"
					+"<td >所属部门</td>"
					+"<td >角色</td>"
					+"<td >投票状态</td>"
					+"<td >投票时间</td>"
		  		+ "</tr>"); 
			jQuery.each(prcs,function(i,sysPara){
				counter++;
				var voteState = "<font color='red'>未投票</font>";
				if(sysPara.voteState =='1'){
					voteState = "<font color=' #007500 '>已投票</font>";
						
				}
			$("#tbody").append("<tr class='TableData' align='center'>"
								+"<td style='text-indent:5px'>" + counter + "</td>"
								+"<td >" + sysPara.userName+ "</td>"
								+"<td >" + sysPara.deptName + "</td>"
								+"<td >" + sysPara.roleName + "</td>"
								+"<td >" + voteState + "</td>"
								+"<td >" + sysPara.voteTime + "</td>"
					  		+ "</tr>"); 
			});
			$("#tableList").append("</table>");
		}else{
			messageMsg("无相关数据！","tableList","info");
		}
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}



</script>
</head>
<body onload="doInit();" style="padding-top: 10px;background-color: #f2f2f2">

<center>
<div id="tableList"></div>
</center>





</body>
</html>
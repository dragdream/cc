<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
int folderSid = TeeStringUtil.getInteger(request.getParameter("folderSid"), 0);
String sid = request.getParameter("sid");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background-color:#f2f2f2">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>签阅情况</title>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript">
var sid = '<%=sid%>';
function doInit(){
	getInfoTable(sid);
}

/* 获取数据列表 */
function getInfoTable(id){
	var url = "<%=contextPath%>/TeeFileNetdiskReaderController/showSignReadDetail.action";
	var para = {sid:id};
	var jsonObj = tools.requestJsonRs(url,para);
	if (jsonObj.rtState) {
		var prcs = jsonObj.rtData;
		//alert(prc[0].modelName + ">>" + prc[0].modelCount);
		var counter = 0;
		if(prcs.length>0){
			$("#tableList").append("<table id='tbody'  style='border:#dddddd 2px solid;' width='100%' class='TableBlock_page'>"
					+"<tr class='TableHeader' style='background-color:#e8ecf9'>"
					+"<td style='text-indent:5px'>序号</td>"
					+"<td >姓名</td>"
					+"<td >所属部门</td>"
					+"<td >角色</td>"
					+"<td >签阅状态</td>"
					+"<td >签阅时间</td>"
		  		+ "</tr>"); 
			jQuery.each(prcs,function(i,sysPara){
				counter++;
				var signState = "<font color='red'>未签阅</font>";
				if(sysPara.signState =='1'){
					signState = "<font color=' #007500 '>已签阅</font>";
				}
			$("#tbody").append("<tr class='TableData'>"
								+"<td style='text-indent:5px' >" + counter + "</td>"
								+"<td >" + sysPara.userName+ "</td>"
								+"<td >" + sysPara.deptName + "</td>"
								+"<td >" + sysPara.roleName + "</td>"
								+"<td >" + signState + "</td>"
								+"<td >" + sysPara.signTime + "</td>"
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


function toReturn(){ 
	window.location.href = "<%=contextPath%>/system/core/base/fileNetdisk/fileManage/fileManage.jsp?sid=<%=folderSid%>";
}
</script>
</head>
<body onload="doInit();" style="padding-top: 10px;background-color: #f2f2f2">

<div style="padding: 10px;">
   <div id="tableList"></div>
</div>
</body>
</html>
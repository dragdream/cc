<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id") == null ? "0" : request.getParameter("id") ;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background-color:#f2f2f2">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header2.0.jsp"%>
<title>查看阅读情况</title>
<script type="text/javascript">
var id = <%=id%>;
function doInit(){
	getInfoTable(id);
}


/* 获取数据列表 */
function getInfoTable(id){
	var url = "<%=contextPath%>/teeNotifyController/getNotifyInfo.action";
	var para = {id:id};
	var jsonObj = tools.requestJsonRs(url,para);
	if (jsonObj.rtState) {
		var prcs = jsonObj.rtData;
		//alert(prc[0].modelName + ">>" + prc[0].modelCount);
		var counter = 0;
		if(prcs.length>0){
			$("#tableList").append("<table id='tbody' style='border:#dddddd 2px solid;' width='100%' class='TableBlock_page'>"
					+"<tr class='TableHeader' style='background-color:#e8ecf9' >"
					+"<td width='50px' style='text-indent:5px'>序号</td>"
					+"<td width='110px'>姓名</td>"
					+"<td width='110px'>所属部门</td>"
					+"<td width='110px'>角色</td>"
					+"<td width='110px'>阅读状态</td>"
					+"<td width='160px'>阅读时间</td>"
		  		+ "</tr>"); 
			jQuery.each(prcs,function(i,sysPara){
				counter++;
				var voteState = "<font color='red'>未阅读</font>";
				if(sysPara.isRead =='1'){
					voteState = "<font color=' #007500 '>已阅读</font>";
						
				}
			$("#tbody").append("<tr class='TableData' align='center'>"
								+"<td style='text-indent:5px'>" + counter + "</td>"
								+"<td >" + sysPara.userName+ "</td>"
								+"<td >" + sysPara.deptName + "</td>"
								+"<td >" + sysPara.roleName + "</td>"
								+"<td >" + voteState + "</td>"
								+"<td >" + sysPara.readTime1 + "</td>"
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




function clearNotifyInfo(){
	window.parent.$.MsgBox.Confirm ("提示","确定要清空阅读详情吗？", function(){
		var url = "<%=contextPath%>/teeNotifyController/clearNotifyInfo.action";
    	var jsonRs = tools.requestJsonRs(url,{"id":id});
    	if(jsonRs.rtState){
    		$.MsgBox.Alert_auto("清空成功!");
    		location.reload();
    	}else{
    		$.MsgBox.Alert_auto(jsonRs.rtMsg);
    	}
	});
}
</script>
</head>

<body onload="doInit()" style="padding-top: 10px;background-color: #f2f2f2">
	<!-- <center>
		<button class='btn btn-info' name="" onclick="clearNotifyInfo();">清空查阅详情</button>
	</center> -->
	<div style="padding: 10px;">
		<div id="tableList"></div>
	</div>
</body>
</html>
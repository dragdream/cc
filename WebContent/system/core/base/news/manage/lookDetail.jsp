<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%

	String id = request.getParameter("id") == null ? "0" : request.getParameter("id") ;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp" %>
<title>查看阅读情况</title>
<script type="text/javascript">
var id = <%=id%>;
function doInit(){
	var url = "<%=contextPath %>/teeNewsController/getNewsLookDatail.action";
	var para = {id:id};
	//var para =  tools.formToJson($("#form1")) ;
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		var prcs = jsonRs.rtData;
		//alert(prc[0].modelName + ">>" + prc[0].modelCount);
		var counter = 0;
		if(prcs.length>0){
			$("#tableList").append("<table id='tbody' style='border:#dddddd 2px solid;' width='100%' class='TableBlock_page'>"
					+"<tr class='TableHeader' style='background-color:#e8ecf9' >"
					+"<td width='110px'>阅读情况</td>"
					+"<td width='110px'>人员</td>"
					+"<td width='110px'>部门</td>"
					+"<td width='110px'>角色</td>"
		  		+ "</tr>"); 
			jQuery.each(prcs,function(i,sysPara){
				counter++;
				var voteState = "<font color='red'>未阅读</font>";
				if(sysPara.isRead =='1'){
					voteState = "<font color=' #007500 '>已阅读</font>";
						
				} 
			$("#tbody").append("<tr class='TableData' align='center'>"
								+"<td style='text-indent:5px'>" + voteState + "</td>"
								+"<td >" + sysPara.userName + "</td>"
								+"<td >" + sysPara.deptName + "</td>"
								+"<td >" + sysPara.roleName + "</td>"
					  		+ "</tr>"); 
			});
			$("#tableList").append("</table>");
		}else{
			messageMsg("无相关数据！","tableList","info");
		}
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
}
/**
 * 清空阅读情况
 */
<%-- function clearReadInfo(){
	var url = "<%=contextPath %>/teeNewsController/clearReadInfo.action?id="+id;
	var jsonRs = tools.requestJsonRs(url);
	if(jsonRs.rtState){
		$.MsgBox.Alert_auto("清空成功");
		doInit();
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
} --%>
function clearReadInfo(){
	window.parent.$.MsgBox.Confirm ("提示","确定要清空阅读详情吗？", function(){
		var url = "<%=contextPath %>/teeNewsController/clearReadInfo.action?id="+id;
    	var jsonRs = tools.requestJsonRs(url,{"id":id});
    	if(jsonRs.rtState){
    		$.MsgBox.Alert_auto("清空成功!");
    		//doInit();
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
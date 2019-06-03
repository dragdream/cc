<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int aipId = TeeStringUtil.getInteger(request.getParameter("aipId"), 0);
	int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@ include file="/header/header.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>盖章规则</title>
	
	<script type="text/javascript" charset="UTF-8">
	var contextPath = '<%=contextPath%>';
	var aipId = "<%=aipId%>";
	var frpSid = "<%=frpSid%>";
	
	window.onload=function(){
		getModelList();
	}
	
	 //获取套红的列表
	function getModelList(){
		var url = contextPath+"/flowRun/getSealRulesByPrcs.action?frpSid="+frpSid+"&aipId="+aipId;
		var json = tools.requestJsonRs(url,{});
		var list = json.rtData;
		var arr = [];
		for(var i=0;i<list.length;i++){
			arr.push("<div style='padding:5px;border-bottom:1px dotted gray;cursor:pointer;' class='hov' onclick='xparent.addRemoteSeal("+list[i].sealId+");CloseWindow();'>"+list[i].sealName+"</div>");
		}
		$("#content").html(arr.join(""));
		
	}
	 
	</script>
<style>
.hov:hover{
background:#9dc2db;
}
</style>
</head>
<body>
<div style="text-align:center;padding:10px;background:#428bca;color:white">
	<b>盖章规则列表</b>
</div>
<div id="content" style="font-size:12px;text-align:center">
	
</div>
</body>
</html>
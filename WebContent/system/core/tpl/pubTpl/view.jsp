<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@ include file="/header/header.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>公共模版查看</title>
	
	<script type="text/javascript" charset="UTF-8">
	var contextPath = '<%=contextPath%>';
	var sid = "<%=sid%>";
	window.onload=function(){
		var url = contextPath+"/pubTemplate/getTemplate.action";
		var json = tools.requestJsonRs(url,{sid:sid});
		if(json.rtState){
			bindJsonObj2Cntrl(json.rtData);
			$("#tplContent").html(json.rtData.tplContent);
		}
		
	}
	</script>
</head>
<body style="padding: 10px">
<div id="toolbar">
	<div class="panel panel-default">
	  <div class="panel-heading">
	    <h3 class="panel-title">模版内容：</h3>
	  </div>
	  <div class="panel-body" style="">
	    <div class="" name="tplContent" id="tplContent" style="padding:10px;"></div>
	  </div>
	</div>
	
</div>
</body>
</html>
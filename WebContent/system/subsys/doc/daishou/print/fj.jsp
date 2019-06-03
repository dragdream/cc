<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tianee.oa.core.org.bean.TeePerson"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="/header/header2.0.jsp"%>
<title>附件</title>
</head>
<script>
function doInit(){
	if(parent.data.attachList){
		for(var i=0;i<parent.data.attachList.length;i++){
			var att = parent.data.attachList[i];
			att.priv = 1+2;
			var attach = tools.getAttachElement(att,{});
			$("#attachDiv").append(attach);
		}
		if(parent.data.attachList.length==0){
			messageMsg("暂无附件","attachDiv","info");
		}
	}
	
}


</script>


<body onload="doInit()">
   <div id="attachDiv" style="padding: 20px;"></div>
</body>
</html>
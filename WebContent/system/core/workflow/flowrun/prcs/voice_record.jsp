<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<%@ include file="/header/header.jsp" %>
	<%
		String runId = request.getParameter("runId");
		String itemId = request.getParameter("itemId");
		String model = request.getParameter("model");
		String modelId = request.getParameter("modelId");
	%>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>语音录制</title>
<script>

var runId = "<%=runId%>";
var itemId =  "<%=itemId%>";
var model =  "<%=model%>";
var modelId =  "<%=modelId%>";

function doInit(){
	
}

function  f_getURL(){
    var url = "/voiceUploadController/upload.action";
    return url;
}
function f_getMAX(){
    return 120;//最长录音时间
}
function f_getMIN(){
    return 1; //最小录音时间
}
function f_complete(filename){
	
	window.close();
}
</script>
</head>
<body onload="doInit()" style="margin:0px">
	<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0" width="808" height="140" id="as_js" align="middle">
         <param name="allowScriptAccess" value="sameDomain" />
         <param name="movie" value="/common/mp3recorder/bbsRecorder.swf" />
         <param name="quality" value="high" />
         <param name="wmode" value="transparent">
         <param name="bgcolor" value="#cccccc" />
         <embed src="/common/mp3recorder/bbsRecorder.swf" vmode="transparent" quality="high" bgcolor="#cccccc" width="808" height="140" name="as-js" align="middle" allowScriptAccess="always" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" />
  </object>
</body>

</html>
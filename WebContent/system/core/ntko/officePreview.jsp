<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
   //原附件id
   int attachId=TeeStringUtil.getInteger(request.getParameter("attachId"),0);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<title>快速预览</title>
<script>
  var attId="<%=attachId %>";
  function doInit(){
	  //转换
	  var url=contextPath+"/officeSwitchController/insertSwitchTask.action";
	  var json=tools.requestJsonRs(url,{attId:attId});
	  if(json.rtState){
		   setInterval("myInterval()",1000);//1000为1秒钟   
	  }
  }
  
  //定时器
  function myInterval(){
	  var url=contextPath+"/officeSwitchController/getTaskByAttachId.action";
	  var json=tools.requestJsonRs(url,{attId:attId});
	  if(json.rtState){
		  var data=json.rtData;
		  if(data.flag==0||data.flag==1){//为转换  转换中
			  messageMsg("转换中，请稍后...","mess","info"); 
		  }else if(data.flag==-1){//换换失败
			  messageMsg("转换失败！","mess","info"); 
		  }else{//转换成功
			  window.location.href = contextPath +  "/attachmentController/textPreview.action?attachmentId="+data.htmlAttId+"&attachmentName="+data.htmlAttName+"&ext="+"html";
		  }
	  }
   }
</script>
</head>
<body onload="doInit();"  >
    <div id="mess" style="margin-top: 20px">
    </div>
</body>
</html>
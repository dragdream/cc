<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header1.0.jsp"%>
<%@ include file="/header/upload.jsp" %>
<%
	String bisKey = request.getParameter("bisKey");
    String operate=request.getParameter("operate");
    int pkid=TeeStringUtil.getInteger(request.getParameter("pkid"), 0);
%>
<title>编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
label{
	font-weight:normal;
}
body{
	background-image:url('preview-bg.png');
}
.form{
	width:800px;
	margin:0 auto;
	-webkit-box-shadow: 0 0 5px rgba(0, 0, 0, 0.4);
	box-shadow: 0 0 5px rgba(0, 0, 0, 0.4);
	background:#fff;
	margin:15px auto 15px;
	border-radius:4px;
	padding:20px;
}
</style>
<script>
var bisKey='<%=bisKey%>';
var operate='<%=operate%>';
var pkid=<%=pkid%>;
//初始化
function doInit(){
	//动态加载页面信息
	var url=contextPath+"/businessModelController/view.action";
	var json = tools.requestJsonRs(url,{bisKey:bisKey,pkId:pkid});
	var result=json.rtData;	
	$("#form1").append(result[0]);
	
	var attList=result[1];
	
	$(".attach").each(function(i,obj){
		
		var att = {priv:1+2,fileName:attList[i].fileName,ext:attList[i].ext,sid:attList[i].sid};
		var attach = tools.getAttachElement(att,{});
		$(obj).append(attach);
	});
	
	
}

//返回按钮
function back(){
	
	window.location = "bis.jsp?bisKey="+bisKey;
}


</script>
<body onload="doInit();" id="myBody" >
	 <form id="form1" class="form"></form>
	<div align="center">	
			
			<button type="button" class="btn btn-default"
			onclick="javascript:back();">返回</button>
	</div>
</body>
</html>
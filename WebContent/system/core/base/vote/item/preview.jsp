<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<title>系统界面设置</title>
<%
	//String json = request.getParameter("json");
	//System.out.println(json);
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/colorPicker/css/colorPicker.css"/>
<style type="text/css">

</style>
<script type="text/javascript" src="<%=contextPath %>/common/colorPicker/colorPicker.js"></script>
<script type="text/javascript">
var json = "{json}";
function doInit(){
	//var json = $.cookies.get(uuid);
	//if(json!=null){
	//	$.cookies.set(uuid,null);
	//
	alert(json);
	//json =  eval('('+json+')');
	var html = "<table>";
	for(var i = 0;i<json.vote.length;i++){
		var content = "";
		if(json.vote[i].ifContent==1){
			content = "说明："+json.vote[i].content+"，";
		}
		var required = "";
		if(json.vote[i].required==1){
			required = "必填，";
		}
		var minNum = "";
		if(json.vote[i].minNum!=0&&json.vote[i].minNum!=""&&json.vote[i].minNum!="0"){
			minNum = "最小可选："+json.vote[i].minNum+"，";
		}
		var maxNum = "";
		if(json.vote[i].maxNum!=0&&json.vote[i].maxNum!=""&&json.vote[i].minNum!="0"){
			maxNum = "最大可选："+json.vote[i].maxNum+"，";
		}
		var appendix = content + required + minNum + maxNum;
		if(appendix!=""){
		    if(appendix.charAt(appendix.length-1)=="，"){
		    	appendix = appendix.substring(0,appendix.length-1);
			}
			appendix = "<font color='red'>（"+appendix+"）</font>";
		}
		var order = i+1+"、";
		html += "<tr><td><strong>"+order+json.vote[i].voteSubject+"</strong>"+appendix+"</td></tr>";
		var textHtml = json.vote[i].textHtml;
		textHtml = textHtml.replace(/glyphicon glyphicon-remove/g,"");
		textHtml = textHtml.replace(/display:/g,"display:none;");
		html += "<tr><td>"+textHtml+"</td></tr>";
	}	
	html+="</table>";
	$("#preview").html(html);
}
 
</script>
</head>
<body style="margin:5px 0 0 1px;" onload="doInit();">
	<div id="preview">
		
	</div>
</body>
</html>
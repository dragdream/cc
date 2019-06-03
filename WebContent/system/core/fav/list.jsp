<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>收藏夹</title>
	<%@ include file="/header/header.jsp" %>
<script type="text/javascript">
function doInit(){
	var url = contextPath+"/favoriteRecord/list.action";
	var json = tools.requestJsonRs(url,{});
	var list = json.rtData;
	var html = [];
	html.push("<ul style='list-style:none;padding:0px;margin:0px;overflow:hidden'>");
	for(var i=0;i<list.length;i++){
		var item = list[i];
		html.push("<li style='white-space:nowrap'><img style='cursor:pointer' onclick='del("+item.sid+",this)' src='"+systemImagePath+"/upload/remove.png' /><a style='color:black' title='"+item.title+"' nowrap target='_blank' href='"+contextPath+"/"+item.url+"'>"+item.title+"</a></li>");
	}
	html.push("</ul>");
	$("#content").append(html.join(""));
}

function del(sid,obj){
	var url = contextPath+"/favoriteRecord/deleteFavoriteRecord.action";
	var json = tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		$(obj).parent().remove();
	}
}
</script>
 
</head>
<body id="content" onload="doInit()" style="margin:5px;font-size:12px;">
	
</body>
</html>
 
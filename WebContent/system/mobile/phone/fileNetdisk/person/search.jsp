<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%
	int folderSid = TeeStringUtil.getInteger(request.getParameter("folderSid"), 0);
%>
<!DOCTYPE HTML>
<html>
<head>
<title>文件查询</title>
<script>
var DING_APPID = "<%=TeeSysProps.getString("DING_PERSONDISK_APPID")%>";
</script>
<%@ include file="/system/mobile/mui/header.jsp" %>
<style>

</style>
</head>
<body>
	<header class="mui-bar mui-bar-nav">
		<button id="preBtn" class="mui-btn mui-btn-link mui-btn-nav mui-pull-left" onclick="returnBack();">
		    <span class="mui-icon mui-icon-left-nav"></span>返回
		</button>
		<h1 class="mui-title" >文件查询</h1>
	</header>
	<div class="mui-input-group" style="position:fixed;top:40px;left:0px;right:0px;height:40px;">
		<div class="mui-input-row">
			<input id="keyWords" type="text" placeholder="输入文件名或文件备注"  oninput="doSearch()" />
		</div>
	</div>
	<div class="mui-content" id="content" style="margin:0px;padding:0px;position:fixed;top:95px;left:0px;right:0px;bottom:0px;overflow:auto">
		<ul class="mui-table-view" id="dataList" style="margin:0px;padding:0px;">
			
		</ul>
	</div>
<script>
$(function(){
	doSearch();
});

/**
 * 获取分页数据
 */
function doSearch(){
	var url = contextPath+"/mobileFileNetdiskController/searchPersonFiles.action";
	
	mui.ajax(url,{
		type:"post",
		dataType:"json",
		data:{keyWords:$("#keyWords").val()},
		timeout:10000,
		success:function(json){
			var rows = json.rows;
			var render = [];
			for(var i=0;i<rows.length;i++){
				var data = rows[i];
				render.push("<li class=\"mui-table-view-cell mui-media\" onclick=\"GetFile('"+data.attachSid+"','"+data.attachSid+"_"+data.fileName+"','"+data.attachSid+"_"+data.fileName+"')\">");
				render.push("<img class=\"mui-media-object mui-pull-left\" src='../file_64.png' style='height:30px'/>");
				
				render.push("<div class=\"mui-media-body\">");
				render.push(data.fileName);
				render.push("</div>");
				render.push("</li>");
			}
			$("#dataList").html(render.join(""));
			
			if(render.length==0){
				$("#dataList").html("<br/><center style='font-size:14px'>没有找到指定文件</center>");
			}
		},
		error:function(){
			
		}
	});	
}

//详情界面
function toFolder(sid){
	var  url = contextPath + "index.jsp?folderSid=" + sid;
	window.location.href = url;
}


//返回
function returnBack(){
	history.go(-1);
}

</script>
</body>
</html>
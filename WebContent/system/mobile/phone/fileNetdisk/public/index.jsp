<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%
	int folderSid = TeeStringUtil.getInteger(request.getParameter("folderSid"), 0);
%>
<!DOCTYPE HTML>
<html>
<head>
<title>公共网盘</title>
<script>
var DING_APPID = "<%=TeeSysProps.getString("DING_PUBDISK_APPID")%>";
var folderSid = <%=folderSid%>;
</script>
<%@ include file="/system/mobile/mui/header.jsp" %>
<style>

</style>
</head>
<body>
	<header class="mui-bar mui-bar-nav">
		<button id="preBtn" style="display:none" class="mui-btn mui-btn-link mui-btn-nav mui-pull-left" onclick="returnBack();">
		    <span class="mui-icon mui-icon-left-nav"></span>上一级目录
		</button>
		<h1 class="mui-title" id="gml" style="display:none">根目录</h1>
		<button class="mui-btn mui-btn-link mui-btn-nav mui-pull-right" onclick="window.location = 'search.jsp';">
		    <span class="mui-icon mui-icon-search"></span>查询
		</button>
	</header>
	<div class="mui-content" id="content">
		<div class="mui-scroll">
			<!--数据列表-->
			<ul class="mui-table-view" id="dataList">
				
			</ul>
		</div>
	</div>
<script>
$(function(){
	getPageList();
});

/**
 * 获取分页数据
 */
function getPageList(){
	var url = contextPath+"/mobileFileNetdiskController/getPublicFilePage.action?folderSid="+folderSid+"&page=1&rows=100000";
	if(folderSid > 0){
		$("#preBtn").show();
		$("#gml").hide();
	}else{
		$("#preBtn").hide();
		$("#gml").show();
	}
	
	
	mui.ajax(url,{
		type:"post",
		dataType:"json",
		data:{},
		timeout:10000,
		success:function(json){
			var rows = json.rows;
			var render = [];
			for(var i=0;i<rows.length;i++){
				var data = rows[i];
				if(data.filetype==0){//文件夹
					render.push("<li class=\"mui-table-view-cell mui-media\" onclick=\"toFolder("+data.sid+")\">");
					render.push("<img class=\"mui-media-object mui-pull-left\" src='../folder_64.png' style='height:30px'/>");
				}else{//文件
					render.push("<li class=\"mui-table-view-cell mui-media\" onclick=\"GetFile('"+data.attachSid+"','"+data.attachSid+"_"+data.fileName+"','"+data.attachSid+"_"+data.fileName+"')\">");
					render.push("<img class=\"mui-media-object mui-pull-left\" src='../file_64.png' style='height:30px'/>");
				}
				
				render.push("<div class=\"mui-media-body\">");
				render.push(data.fileName);
				render.push("</div>");
				render.push("</li>");
			}
			$("#dataList").html(render.join(""));
			if(render.length==0){
				$("#content").html("<br/><center style='font-size:14px'>暂无可显示的文件</center>");
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
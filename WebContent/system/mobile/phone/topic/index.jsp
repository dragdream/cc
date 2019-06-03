<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tianee.webframe.util.global.TeeSysProps"%>
<!DOCTYPE HTML>
<html>
<head>
<title>论坛板块</title>
<script>
var DING_APPID = "<%=TeeSysProps.getString("DING_TOPIC_APPID")%>";
</script>
<%@ include file="/system/mobile/mui/header.jsp" %>
<script type="text/javascript" src="<%=contextPath %>/system/mobile/superRaytinpaginationjs/dist/pagination-with-styles.js?v=1"></script>
<style>
.reply{
color:gray;font-size:12px;
}
.reply br{
margin:0px;
}
.mui-icon{
font-size:12px;
}
.tt{
font-size:16px;
}
</style>
</head>
<body>
<div class="mui-content" id="topicList">
<center>
	<p class="app-tip">无板块信息！</p>
</center>
<ul class="mui-table-view" style="display: none;">
	<li class="mui-table-view-cell mui-media" onclick="window.location = 'topics.jsp';">
		<a href="#">
			<div class="mui-media-body">
				<span class="tt">学习园地</span>
				<p class='mui-ellipsis'>主题5123&nbsp;&nbsp;&nbsp;&nbsp;文章1002</p>
				<p class='mui-ellipsis'>最后发表：平凡的人生，平凡的日子平常过</p>
			</div>
		</a>
	</li>
</ul>
</div>


<script >

	(function() {
		getToPicList();
	})();
	
	function getToPicList(){
		//return;
		var url = contextPath+"/TeeTopicSectionController/getManageInfoList.action";
		mui.ajax(url,{
			type:"get",
			dataType:"html",
			data:{},
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				var list = json.rows;
				if(list.length>0){
					document.getElementById("topicList").innerHTML="";
					var html = "<ul class='mui-table-view'>";
					 jQuery.each(list,function(i,sysPara){
						 html+="<li class=\"mui-table-view-cell mui-media\" onclick=\"showInfoFunc('" + sysPara.uuid + "');\" >"
								+"<a href=\"#\">"
								+"	<div class=\"mui-media-body\" >"
								+"		<span class='tt'>"+sysPara.sectionName+"</span>"
								+"		<p class='mui-ellipsis'>主题：" + sysPara.topicCount + "&nbsp;&nbsp;&nbsp;&nbsp;文章：" + sysPara.topicReplyCount + "</p>"
								+"		<p class='mui-ellipsis'>最后发表：" + sysPara.lastTopicSubject + "</p>"
								+"	</div>"
								+"</a>"
								+"</li>";
					 });
					 html+="</ul>";
					document.getElementById("topicList").innerHTML=html;
				}
				
			},
			error:function(){
				
			}
		});
	}
	
	//进入话题界面
	function showInfoFunc(sid){
		var  url = contextPath + "/system/mobile/phone/topic/topics.jsp?uuid=" + sid;
		window.location.href = url;
	}

</script>
</body>
</html>
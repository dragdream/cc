<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String uuid = request.getParameter("uuid");

%>

<!DOCTYPE HTML>
<html>
<head>
<title>讨论区</title>
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
.avatar{
border-radius:25px;
height:30px;
width:30px;
margin-right:10px;
}
.u1{
font-size:12px;
margin:0px;
padding:0px;
}
.topic-title{
font-size:16px;
}
.topic-right{
text-align:right;
}
</style>
</head>
<body>
<header class="mui-bar mui-bar-nav">
	<button class="mui-btn mui-btn-link mui-btn-nav mui-pull-left" onclick="window.location = 'index.jsp';">
	    <span class="mui-icon mui-icon-left-nav"></span>
	    返回
	  </button>
	  <button class="mui-btn mui-btn-link mui-btn-nav mui-pull-right"  onclick="publishFunc();">
	    发表
	    <span class="mui-icon mui-icon-right-nav"></span>
	  </button>
	  <h1 class="mui-title" id="topicSectionName"></h1>
</header>
<div class="mui-content" id="topicList">
<center>
	<p class="app-tip">无相关信息！</p>
</center>
<ul class="mui-table-view" style="display: none;">
	<li class="mui-table-view-cell mui-media" onclick="OpenWindow('帖子详情','/system/mobile/phone/topic/detail.jsp')">
		<a href="#">
			<div class="mui-media-body">
				<table>
					<tr>
						<td>
							<img class="avatar" src="<%=contextPath %>/attachmentController/downFile.action?sid=1" onerror="errorAvatar(this)"/>
						</td>
						<td>
							<p class="u1">王小明</p>
							<p class="u1">2015-12-32 20:15:23</p>
						</td>
					</tr>
				</table>
				<div class="topic-title">平凡的人生，平凡的日子平常过</div>
				<span class="mui-icon mui-icon-chatboxes mui-pull-right">5</span>
			</div>
		</a>
	</li>
</ul>
</div>
<div id="pagination-demo1" class="app-pagination"></div>
<script>
	
$(function(){
	getTopicById('<%=uuid%>');
	getPageList('<%=uuid%>');
});

/**
 * 获取分页数据
 */
function getPageList(uuid){
	$('#pagination-demo1').pagination({
		dataSource: contextPath+'/TeeTopicController/getManageInfoList.action?topicSectionId=' + uuid,
		pageSize: 20,
		callback: function(data){
			//Alert(data.rows);
			var list = data.rows;
			if(list.length>0){
				document.getElementById("topicList").innerHTML="";
				var html = "<ul class='mui-table-view'>";
				 jQuery.each(list,function(i,sysPara){
					var avatar = sysPara.avatar;
					if(avatar==null || avatar=="" || avatar=="0"){
						avatar = systemImagePath+"/default_avatar.gif";
					}else{
						avatar = contextPath+"/attachmentController/downFile.action?id="+avatar+"&model=person";
					}
					html+="<li class=\"mui-table-view-cell mui-media\" onclick=\"showInfoFunc('" + sysPara.uuid + "');\" >"
							+"<a href=\"#\">"
							+"	<div class=\"mui-media-body\" >"
							+"		<table> "
							+"			<tr> "
							+"				<td> "
							+"					<img class='avatar' src='" + avatar + "' onerror='errorAvatar(this)'/>"
							+"				</td> "
							+"				<td>"
							+"					<p class='u1'>" + sysPara.crUserName + "</p>"
							+"					<p class='u1'>" + sysPara.crTimeStr + "</p>"
							+"				</td> "
							+"			</tr> "
							+"		</table> "
							+"		<div class='topic-title mui-ellipsis' style='font-size:14px;'>" + sysPara.subject + "</div>"
							+"		<span class='mui-icon mui-icon-chatboxes mui-pull-right'>&nbsp;" + sysPara.replyAmount + "/" + sysPara.clickAccount + "</span>"
							+"	</div>"
							+"</a>"
							+"</li>";
				});
				html+="</ul>";
				document.getElementById("topicList").innerHTML=html;
			}
		}
	});
}

function errorAvatar(obj){
	obj.src = contextPath+"/common/images/default_avatar.gif";
}



/**
 * 获取话题信息
 */
function getTopicById(uuid){
	var url =contextPath+"/TeeTopicSectionController/getInfoById.action";
	mui.ajax(url,{
		type:"POST",
		dataType:"JSON",
		data:{uuid:uuid},
		timeout:10000,
		success:function(text){
			var json = eval("("+text+")");
			if(json.rtState){
				$("#topicSectionName").text(json.rtData.sectionName);
			}
		},
		error:function(){
			
		}
	});
}

//进入话题界面
function showInfoFunc(sid){
	var  url = contextPath + "/system/mobile/phone/topic/detail.jsp?topicSectionId=<%=uuid%>&uuid=" + sid;
	window.location.href = url;
}
	
//发表
function publishFunc(){
	var  url = contextPath + "/system/mobile/phone/topic/publish.jsp?topicSectionId=<%=uuid%>";
	window.location.href = url;
}
	
</script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String topicSectionId = request.getParameter("topicSectionId");

%>

<!DOCTYPE HTML>
<html>
<head>
<title>发表</title>
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
margin-right:10px;
}
.u1{
font-size:12px;
}
.topic-title{
font-size:16px;
}
.topic-right{
text-align:right;
}
.img{
border:1px solid gray;
border-radius:10px;
padding:5px;
}
</style>
</head>
<body>
<header class="mui-bar mui-bar-nav">
	<button class="mui-btn mui-btn-link mui-btn-nav mui-pull-left" onclick="returnBackFunc();">
	    <span class="mui-icon mui-icon-left-nav"></span>
	    返回
	  </button>
	  <button class="mui-btn mui-btn-link mui-btn-nav mui-pull-right" onclick="doSubmit();">
	    确定
	    <span class="mui-icon mui-icon-right-nav"></span>
	  </button>
	  <h1 class="mui-title">发表</h1>
</header>
<div class="mui-content">
	<div class="mui-input-group">
		<div class="mui-input-row">
			<input type="text" placeholder="标题（必填）" name="subject" id="subject">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row"  style="height:inherit">
			<textarea name="content" id='content' rows="5" placeholder="说两句吧"></textarea>
		</div>
	</div>
	<div style="padding:10px" id="picDiv">
		
	</div>
	<div style="padding:10px">
		<button type="button" class="mui-btn mui-btn-primary" onclick="doTakePhoto()">上传图片</button>
	</div>
</div>
<script>
	
$(function(){

});

function removeImg(obj){
	$(obj).parent().remove();
}

function doTakePhoto(){
	TakePhoto(function(files){
		$("<p class='img' path=\""+files[0].path+"\">"+files[0].name+"&nbsp;&nbsp;<img src='/common/images/upload/remove.png' onclick='removeImg(this)' /></p>").appendTo($("#picDiv"));
	});
}


function checkForm(){
	if(!$("#subject").val()){
		Alert("请输入标题！");
		return false; 
	}
	return true;
}
function doSubmit(){
	if(checkForm()){
		var attachIds = [];
		$("#picDiv p").each(function(i,obj){
			attachIds.push($(obj).attr("path"));
		});
		
		UploadPhoto(function(files){
			var subject= document.getElementById("subject").value;
			var content= document.getElementById("content").value;
			var url = contextPath+"/TeeTopicController/addOrUpdate.action";
			attachIds = [];
			for(var i=0;i<files.length;i++){
				attachIds.push(files[i].id);
			}
			
			mui.ajax(url,{
				type:"POST",
				dataType:"JSON",
				data:{topicSectionId:'<%=topicSectionId%>',content:content,subject:subject,attacheIds:attachIds.join(",")},
				timeout:10000,
				success:function(data){
					returnBackFunc('<%=topicSectionId%>');
				},
				error:function(){
					
				}
			});
		},attachIds,"topic","0");
	}
}


//返回话题界面
function returnBackFunc(sid){
	var  url = contextPath + "/system/mobile/phone/topic/topics.jsp?uuid=<%=topicSectionId%>";
	window.location.href = url;
}

</script>
</body>
</html>
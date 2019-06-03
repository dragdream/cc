<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
  String sid = request.getParameter("sid");
  String type = request.getParameter("type");
  String type2 = request.getParameter("type2");
  TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
  int userId=loginUser.getUuid();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta charset="utf-8">
  <title>微博评论</title>
  <%@ include file="/system/mobile/mui/header.jsp" %>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
  <meta name="apple-mobile-web-app-capable" content="yes">
  <meta name="apple-mobile-web-app-status-bar-style" content="black">
  <link rel="stylesheet" href="css/mui.css">
  <style>
    html,body {
	   background-color: #e1e1e1;
	}
	*{
	  margin:0;
	  padding:0;
	  font-family: "Microsoft Yahei";
	}
	li{
	   list-style: none;
	}
	.hide{
	   display: none;
	}
	.footerBar{
	   position: fixed;
	   bottom: 0;
	   left: 0;
	   right: 0;
	   height:40px;
	   z-index: 1;
	   background: #e2e2e2;
	}

	.footerBar .repostWrap{
		width: 100px;
		vertical-align: top;
	}
	.footerBar ul{
		text-align:center;
		padding:10px 0;
		color: #999;
	}
	.footerBar li{
		display: inline-block;
		width: 30%;
		color: #333;
	}
	.footerBar li img{
		width: 100%;
	}
	.mui-content{
		background-color: inherit;
		margin-bottom:41px;
	}
	.blogAuthor{
		color: #008bff;
	}
	.mui-card{
		margin:0;
	}
	.mui-card-content{
		padding: 10px;
	}
	.mui-card-header>img:first-child{
		overflow: hidden;
		border-radius: 50%;
	}
	.mui-card{
		background-color: #eeeeee;
	}
	.mui-card-footer .mui-card-link{
		background-position: left center;
		background-repeat: no-repeat;
	}
	.blogStar{
		width:15px;
		height: 15px;
		display: inline-block;
		float: right;
		margin-right: 10px;
		position: absolute;
		right: 0;
		top: 20px;
	}
	.repost{
		background-image: url(images/repost.png);
	}
	.comment{
		background-image: url(images/repost.png);
	}
	.star{
		background-image: url(images/repost.png);
	}
	.mui-card-header.mui-card-media{
		position: relative;
		overflow: hidden;
    }
	.mui-card-header.mui-card-media .mui-media-body p{
		line-height: 20px;
	}
	.bottomList{
		background-color: #eee;
	}
	ul.listWrap{
		margin: 0 10px;
		border-bottom: 1px solid #aeaeae;
		margin-top: 20px;
		text-indent: 5px;
		overflow: hidden;
	}
	.listWrap li{
	    float: left;
		color: #7b7f88;
		margin-right: 14px;
		line-height: 35px;
		font-size: 14px;
	}
	.listWrap li.active{
		color: #333;
	}
	.listWrap li:last-child{
		float: right;
	}
	.listWrap li.active{
		color: #000;
	}
	.commentContent{
		color: #333;
		margin-top: 10px;
		line-height: 25px;
	}
	.comentReply{
		padding:5px;
		background-color: #e1e1e1;
		color: #333;
		font-size: 13px;
	}
	.replyName{
		color: #008bff;
	}
	.allReply{
		margin-top: 15px;
		line-height: 25px;
		color: #008bff;
	}
	.replyTime{
		color: #999;
	}
	.commentList .operAndInfo{
		margin-top: 10px;
	}
	.replyOper{
		float: right;
	}
	.replyOper li{
		float: left;
		margin:0 5px;
		vertical-align: middle;
		color: #999;
		margin: 0 10px;
	}
	.replyOper li img{
		width: 15px;
		height: 15px;
		display: inline-block;
		vertical-align: top;
	}
	span.border{
		background-image: linear-gradient(to bottom, rgba(0, 0, 0, 0), rgba(0, 0, 0, 0.6), rgba(0, 0, 0, 0));
		width: 1px;
        height: 20px;
	    display: inline-block;
	    vertical-align: text-bottom;
	}
	.mui-card-header.mui-card-media .mui-media-body p.starer{
	    line-height: 33px;
		font-size: 16px;
	}
	.repostContent{
		color: #333;
		line-height: 25px;
	}
	.repostContent .operAndInfo{
		margin-top: 5px;

	}
	.repostInputWrap {
		position: fixed;
		left:0;
		right: 0;
		bottom: 0;
		height: 160px;
		background-color: #eaebed;
		padding: 5px;
		z-index: 3;
	}
	.repostInput{
		width: 90%;
		height: 120px;
		font-size: 12px;
		background-color: #eeeeee;
		padding:5px;
		margin-bottom: 0;
	}
	#shadow{
		position: fixed;
		left: 0;
		right: 0;
		top: 0;
		bottom: 0;
		background-color: rgba(0,0,0,0.3);
		font-size:12px;
		z-index: 2;
		cursor: pointer;
	}
	.fullScreen{
		float: right;
		width: 20px;
		margin-right: 7px;
		margin-top: 7px;
	}

	#repost{
		width: 15px;
		height: 15px;
		vertical-align: middle;
	}
	.repostInputWrap label.repostLabel{
		font-size:14px;
		vertical-align: middle;
	}
	.repostInputWrap ul{
		padding:5px 0;
	}
	.repostInputWrap li{
		/* float: left; */
		margin-left: 10px;
		display: inline-block;
		margin: 0 10px;
		width: 20px;
		height: 20px;
	}
	.repostInputWrap li img{
		width: 100%;
	}
	.repostInputWrap .repostWrap{
		width: 100px;
		vertical-align: top;
	}
	.btn{
		float:right;
	}
  .commentBtn {
	width: 64px;
	height: 28px;
	line-height: 28px;
	text-align: center;
	color: #fff;
	font-size:14px;
	float: right;
	border-radius: 5px;
	border:none;
	outline: none;
	cursor: pointer;
	background-color: #ff7a35;
	top:-10px;
}
.commentInput{
	width: 310px;
	height:45px;
	border:1px solid #ccc;
	outline: none;
}

.emojiBox{
	position: fixed;
	left: 0;
	right: 0;
	bottom: -120px;
	height: 120px;
	z-index: 3;
	cursor:pointer;
}
.emojiBox{
	background-color: #eaebed;
	overflow: auto;
}
.emojiBox .emoji {
    float: left;
    width: 2em;
    padding: 0.2em;
    border: 1px solid #eaebed;

}
.emojiBox .emoji img{
	vertical-align: middle;
	width:100%;
}


.pictureLi{
	width: 28%;
	max-height: 100px;
	margin:5px;
	display: inline-block;
	position: relative;
	overflow: hidden;
}
.deleteIcon{
	position: absolute;
	display: block;
	width: 40px;
	height: 40px;
	line-height: 54px;
	text-align: left;
	text-indent: 4px;
	border-radius: 50%;
	background-color: #7c7c80;
	color: #fff;
	right: -20px;
	top: -20px;
	font-size: 20px;
}
.pictureLi img{
	width: 100%;
}

  </style>
  <script type="text/javascript">
  var sid="<%=sid%>";
  var type="<%=type%>";
  var userId=<%=userId%>;
  var blHuifu=0;
  var huiFuUserId=0;
  var pingLunId=0;
  var imgsId="";
  var imgNum=0;
  var type2=<%=type2%>;
  function doInit(){
	findWeibInfo();
	if(type==1){//转发
		$(".allRepost").addClass("active");
		$(".repostList").removeClass("hide");
		$(".repostInput").attr("placeholder","输入转发理由");
		$(".repostWrap").hide();
	}else if(type==2){//评论
		$(".allComment").addClass("active");
		$(".commentList").removeClass("hide");
		$(".pictureList").hide();//图片
	}else{
		$(".allStar").addClass("active");
		$(".starList").removeClass("hide");
	}
	if(type2==1){
		$("#shadow").hide();
		$(".repostInputWrap").hide();
	}
	//转发
    $("body").on("tap",".allRepost",function(){
    	$(".allRepost").attr("class","allRepost active");
    	$(".allComment").attr("class","allComment");
    	$(".allStar").attr("class","allStar");
    	
    	$(".repostList").attr("class","repostList");
    	$(".commentList").attr("class","commentList hide");
    	$(".starList").attr("class","starList hide");
	});
	//评论
	$("body").on("tap",".allComment",function(){
    	$(".allComment").attr("class","allComment active");
		$(".allRepost").attr("class","allRepost");
    	$(".allStar").attr("class","allStar");
    	
    	$(".commentList").attr("class","commentList");
    	$(".repostList").attr("class","repostList hide");
    	$(".starList").attr("class","starList hide");
	});
	//点赞
	 $("body").on("tap",".allStar",function(){
	    $(".allComment").attr("class","allComment");
		$(".allRepost").attr("class","allRepost");
	    $(".allStar").attr("class","allStar active");
	    	
	    $(".commentList").attr("class","commentList hide");
	    $(".repostList").attr("class","repostList hide");
	    $(".starList").attr("class","starList");
	}); 
	//收藏（取消收藏）
	 $("body").on("tap","#blogStar",function(){
		 findcollectPulish();
	}); 
	//点赞（取消点赞）
	 $("body").on("tap",".dianZanImg",function(){
		 findDianZanPulish();
	}); 
	
	//mui-btn
	/*  $("body").on("tap",".mui-btn",function(){
		 window.location.replace('index.jsp'); 
	});  */
	//显示转发页面
	 $("body").on("tap",".zhuanFaImg",function(){
		 $("#shadow").show();
		 $("body").css("overflow","hidden");
		 $(".repostInputWrap").show();
		 $(".repostInput").attr("placeholder","输入转发理由");
		 $(".repostWrap").hide();//是否转发
		 $(".pictureList").show();//图片
		 toggleEmoji("hide");
		 type=1; 
	});
	//显示评论页面
	 $("body").on("tap",".pinglunImg",function(){
		 $("#shadow").show();
		 $("body").css("overflow","hidden");
		 $(".repostInputWrap").show();
		 $(".repostInput").attr("placeholder","写评论");
		 $(".repostWrap").show();//是否转发
		 $(".pictureList").hide();//图片
		 toggleEmoji("hide");
		 type=2;
	});
	//显示回复评论对话框
	 $("body").on("tap",".commentImg",function(){
		 pingLunId=$(this).parent().prev().val();
		 huiFuUserId=0;
		 $("#shadow").show();
		 $("body").css("overflow","hidden");
		 $(".repostInputWrap").show();
		 $(".repostInput").attr("placeholder","回复:");
		 $(".repostWrap").hide();//是否转发
		 $(".pictureList").hide();//图片
		 toggleEmoji("hide");
		// $(".repostWrapList").html("<li><img src='images/emoji.png'></li>");
		 blHuifu=1;
	});
	//提交转发和评论信息
	$("body").on("tap",".btn-comment ",function(e){
		if(blHuifu==0){
			if(type==1){
				relay();
				window.location.replace("blogContent.jsp?type2=1&type=1&sid="+sid); 
			}else if(type==2){
				addReply();
				if($('#repost').is(":checked")){
					relay();
				}
				window.location.replace("blogContent.jsp?type2=1&type=2&sid="+sid); 
			}
		}else if(blHuifu==1){
			huiFuPinglunAndPerson(pingLunId,huiFuUserId);
			window.location.replace("blogContent.jsp?type2=1&type=2&sid="+sid); 
		}else{
			huiFuPinglunAndPerson(pingLunId,huiFuUserId);
			window.location.replace("blogContent.jsp?type2=1&type=2&sid="+sid); 
		}

	});
	//隐藏阴影
	 $("body").on("tap","#shadow",function(){
		 $("#shadow").hide();
		 $("body").css("overflow","auto");
		 $(".repostInputWrap").hide();
		 toggleEmoji("hide");
	}); 
	//显示回复评论对话框huiFuPingLunContent
	 $("body").on("tap",".huiFuPingLunContent",function(){
		huiFuUserId=$(this).prev().val();
		pingLunId=$(this).parent().next().val();
		var userNameHF=$(this).children(":first").html();
		$("#shadow").show();
		$(".repostInputWrap").show();
		$(".repostInput").attr("placeholder","回复:"+userNameHF);
		$(".repostWrap").hide();//是否转发
		$(".pictureList").hide();//图片
		toggleEmoji("hide");
		
		blHuifu=2;
	}); 
	//评论点赞
	 $("body").on("tap",".addDianZan",function(){
		var pingLunSid=$(this).parent().prev().val();//评论id
		 $.ajax({
			  type: 'POST',
			  url: "<%=contextPath%>/TeeWeibDianZaiController/addDianZan2.action",
			  data: {sid:pingLunSid},
			  timeout: 10000,
			  success: function(json){
				  //json = eval("("+json+")");
				   findWeibInfo();
				  //window.location.href="blogContent.jsp?type=2&sid="+sid;
			  },
			  error: function(xhr, type){
			    //alert('服务器请求超时!');
			  }
		});
		
	});
	//评论取消点赞
	 $("body").on("tap",".delDianZan",function(){
		 var pingLunSid=$(this).parent().prev().val();//评论id
		 $.ajax({
			  type: 'POST',
			  url: "<%=contextPath%>/TeeWeibDianZaiController/deleteDianZan2.action",
			  data: {sid:pingLunSid},
			  timeout: 10000,
			  success: function(json){
				  //json = eval("("+json+")");
				  findWeibInfo();
			  },
			  error: function(xhr, type){
			    //alert('服务器请求超时!');
			  }
		});
		
	});
	 
	//删除图片deleteIcon
	  $("body").on("tap",".deleteIcon",function(){
		if(window.confirm("确定删除吗?")){
			$(this).parent().remove();
			imgNum--;
			pictureLists();
		}
	});
	
	//回复评论
	 $("body").on("tap",".commentBtn",function(){
		 var pingLunSid=$(this).parent().prev().prev().val();//评论id
		 var content=$(this).prev().val();
		 if(content!=null && content!=""){
			 while(content.indexOf('\n') >= 0){
					content = content.replace('\n','<br/>');
			}
			 var personId=0;
			 $.ajax({
				  type: 'POST',
				  url: "<%=contextPath%>/TeeWeibReplyController/addReply.action",
				  data: {infoId:pingLunSid,content:content,personId:personId},
				  timeout: 10000,
				  success: function(json){
					  //json = eval("("+json+")");
					  findWeibInfo();
				  },
				  error: function(xhr, type){
				    //alert('服务器请求超时!');
				  }
			});
	    }
    });
	
	//评论全部的回复
	 $("body").on("tap",".allReply",function(){
		 var pingLunSid=$(this).prev().val();
		 var arrDiv=$(this).prev().prev();
		 var Object=$(this);
		 $.ajax({
			type: 'POST',
			url: "<%=contextPath%>/TeeWeibReplyController/findReplyAll.action",
		    data: {plId:pingLunSid},
			timeout: 10000,
			success: function(json){
				json = eval("("+json+")");
				var data=json.rtData;
				if(data!=null && data.length>0){
					var html="";
					for(var i=0;i<data.length;i++){
					   html+="<input type='hidden' value='"+data[i].userId+"'/>";
					   html+="<li class='huiFuPingLunContent' ontouchstart='gtouchstart("+data[i].sid+","+data[i].userId+")' ontouchmove='gtouchmove()' ontouchend='gtouchend()'>";
 		                 html+="<span class='replyName'>"+data[i].userName+"</span>";
 		                if(data[i].personName!=null && data[i].personName!=""){
    		            	html+="<span class='replyName'> 回复 "+data[i].personName+"</span>";
    		             }
 		                 html+=":"+data[i].content;
					   html+="</li>";
					}
					$(arrDiv).html(html);
					$(Object).hide();
				}
			},
			error: function(xhr, type){
				 //alert('服务器请求超时!');
			}
	   });
    });
	
	 
	//删除评论
	 $("body").on("tap",".delPingLunContent",function(){
		if(window.confirm("确定删除吗?")){
			 var pingLunSid=$(this).parent().prev().val();//评论id
			 $.ajax({
				type: 'POST',
				url: "<%=contextPath%>/teeWeibCommentController/deletePingLun.action",
				data: {sid:pingLunSid},
				timeout: 10000,
				success: function(json){
						  //json = eval("("+json+")");
					window.location.replace("blogContent.jsp?type2=1&type=2&sid="+sid); 
			    },
				error: function(xhr, type){
					    //alert('服务器请求超时!');
				}
			});
		 
		}
    });
  //话题
  $(".otherMes3").click(function(event) {
	var content=$(".repostInput").val();
	$(".repostInput").val(content+"#在这里输入你想要说的话题#");
	var length = content.length;
	setSelectionRange($(".repostInput")[0],length + 1,length+13);
  });
  //给话题添加链接
  $("body").on("tap","a",function(){
      var content=$(this).html();
      content=content.substring(1,content.length-1);
      while(content.indexOf('\n') >= 0){
			  content = content.replace('\n','<br/>');
		 }
      open("topicList.jsp?content="+content); 
  });
 //显示删除回复按钮（长按事件）
}
	function check1(){
		var relay=$(".repostInput").val();
		if(relay==null || relay==""){
			return false;
		}
		return true;
	}
	
	//回复评论
	function huiFuPinglunAndPerson(pingLunSid,personId){
		 var content=$(".repostInput").val();
		 if(check1()){
			 while(content.indexOf('\n') >= 0){
					content = content.replace('\n','<br/>');
			}
			 $.ajax({
				  type: 'POST',
				  url: "<%=contextPath%>/TeeWeibReplyController/addReply.action",
				  data: {infoId:pingLunSid,content:content,personId:personId},
				  timeout: 10000,
				  success: function(json){
					  //json = eval("("+json+")");
					  findWeibInfo();
				  },
				  error: function(xhr, type){
				    //alert('服务器请求超时!');
				  }
			});
	    }
	}
	//转发
	function relay(){
	   var relay=$(".repostInput").val();
	   if(check1()){
			while(relay.indexOf('\n') >= 0){
				 relay = relay.replace('\n','<br/>');
			}
		   //var picIdStr=$("#imgInput").val();
			var pictureLis=$(".pictureLi");
			  var picIdStry="";
			  var picIdStr="";
			  for(var i=0;i<pictureLis.length;i++){
				 var picId= $(pictureLis[i]).attr("id");
				  picId=picId.substring(3,picId.length);
		    	  picIdStr+=picId+",";
				  picIdStry+=$(pictureLis[i]).children(":first").val()+",";
			  }
		   $.ajax({
				  type: 'POST',
				  url: "<%=contextPath%>/TeeWeibRelayController/addRelay.action",
				  data: {infoId:sid,relay:relay,img:picIdStr,imgy:picIdStry},
				  timeout: 10000,
				  success: function(json){
					  //json = eval("("+json+")");
				  },
				  error: function(xhr, type){
				    alert('服务器请求超时!');
				  }
			});
		}
	}
	
	//评论
	function addReply(){
		var content=$(".repostInput").val();
		if(content!=null && content!=""){
			while(content.indexOf('\n') >= 0){
				content = content.replace('\n','<br/>');
			}
	   $.ajax({
			  type: 'POST',
			  url: "<%=contextPath%>/teeWeibCommentController/addComment.action",
			  data: {infoId:sid,content:content},
			  timeout: 10000,
			  success: function(json){
				  //json = eval("("+json+")");
			  },
			  error: function(xhr, type){
			    //alert('服务器请求超时!');
			  }
		});
	}
}
  //判断是否已经收藏
  function findcollectPulish(){
	  $.ajax({
		  type: 'POST',
		  url: "<%=contextPath%>/TeeWeibConllectController/findCollect.action",
		  data: {sid:sid},
		  timeout: 10000,
		  success: function(json){
			  json = eval("("+json+")");
			  var data=json.rtData;
			  if(data.conllect){
				  deleteCollectPublish(sid);
				  $("#blogStar").attr("src","images/star.png");
			  }else{
				  addCollectPublish(sid);
				  $("#blogStar").attr("src","images/stared.png");
			  }
		  },
		  error: function(xhr, type){
		    //alert('服务器请求超时!');
		  }
		});
  }
	//收藏
	function addCollectPublish(sid){
		$.ajax({
			  type: 'POST',
			  url: "<%=contextPath%>/TeeWeibConllectController/addCollect.action",
			  data: {sid:sid},
			  timeout: 10000,
			  success: function(json){
				  //json = eval("("+json+")");
			  },
			  error: function(xhr, type){
			    //alert('服务器请求超时!');
			  }
			});
	}
	//取消收藏
	function deleteCollectPublish(sid){
		$.ajax({
			  type: 'POST',
			  url: "<%=contextPath%>/TeeWeibConllectController/deleteCollect.action",
			  data: {sid:sid},
			  timeout: 10000,
			  success: function(json){
				 // json = eval("("+json+")");
			
			  },
			  error: function(xhr, type){
			    //alert('服务器请求超时!');
			  }
			});
	}
	  //判断是否已经点赞
	  function findDianZanPulish(){
		  $.ajax({
			  type: 'POST',
			  url: "<%=contextPath%>/TeeWeibDianZaiController/findDianZan.action",
			  data: {sid:sid},
			  timeout: 10000,
			  success: function(json){
				  json = eval("("+json+")");
				  var data=json.rtData;
				  if(data.dianzai){
					  deleteDianZanPublish(sid);
				  }else{
					  addDianZanPublish(sid);
				  }
				  window.location.replace("blogContent.jsp?type2=1&type=3&sid="+sid); 
			  },
			  error: function(xhr, type){
			    //alert('服务器请求超时!');
			  }
			});
	  }
	//点赞（微博）
	function addDianZanPublish(sid){
		$.ajax({
			  type: 'POST',
			  url: "<%=contextPath%>/TeeWeibDianZaiController/addDianZan.action",
			  data: {sid:sid},
			  timeout: 10000,
			  async:false,
			  success: function(json){
				 // json = eval("("+json+")");
			  },
			  error: function(xhr, type){
			    //alert('服务器请求超时!');
			  }
			});
	}
	//取消点赞（微博）
	function deleteDianZanPublish(sid){
		$.ajax({
			  type: 'POST',
			  url: "<%=contextPath%>/TeeWeibDianZaiController/deleteDianZan.action",
			  data: {sid:sid},
			  timeout: 10000,
			  async:false,
			  success: function(json){
				  //json = eval("("+json+")");
			  },
			  error: function(xhr, type){
			    //alert('服务器请求超时!');
			  }
			});
	}
  //获取微博信息
  function findWeibInfo(){
	  $.ajax({
		  type: 'POST',
		  url: "<%=contextPath%>/TeeWeibPublishController/findWeibInfo.action",
		  data: {sid:sid},
		  timeout: 10000,
		  success: function(json){
			 json = eval("("+json+")");
		     var data=json.rtData;
		     $(".mui-card-content").html(data.content);
		     $("#weibAuthor").html(data.userName);
		     $("#weibCreatime").html(data.createTime);
		     if(data.avatar>0){
			     $("#avatar").attr("src","<%=contextPath %>/attachmentController/downFile.action?id="+data.avatar);
		     }
		     if(data.collect){
			     $("#blogStar").attr("src","images/stared.png");
		     }
		    /*  if(data.dianzan){
		    	 $(".dianZanImg").attr("src","images/ups_active.png");
		     } */
		     $(".allRepost").html("转发  "+data.number);
		     $(".allComment").html("评论  "+data.num);
		     $(".allStar").html("赞  "+data.count);
		     var ctList=data.ctList;//评论
		     if(ctList!=null && ctList.length>0){
		         var html="";
		    	 for(var i=0;i<ctList.length;i++){
		    		 html+="<div class='mui-card'>";
		    		    html+="<div class='mui-card-header mui-card-media'>";
		    		    if(ctList[i].avatar>0){
			    		    html+="<img src='<%=contextPath %>/attachmentController/downFile.action?id="+ctList[i].avatar+"'/>";
		    		    }else{
		    		    	html+="<img src='dist/images/replyAvatarDemo.png'/>";
		    		    }
		    		       html+="<div class='mui-media-body'>";
		    		          html+="<p class='blogAuthor'>"+ctList[i].userName+"</p>";
		    		          html+="<p class='commentContent'>"+ctList[i].content+"</p>";
		    		       var replyList=ctList[i].replyList;
		    		       if(replyList!=null && replyList.length>0){
		    		          html+="<div class='comentReply'>";
		    		            html+="<ul class='comentReplyList'>";
		    		         for(var j=0;j<replyList.length;j++){
		  					       html+="<input type='hidden' value='"+replyList[j].userId+"'/>";
		    		               html+="<li class='huiFuPingLunContent' ontouchstart='gtouchstart("+replyList[j].sid+","+replyList[j].userId+")' ontouchmove='gtouchmove()' ontouchend='gtouchend()'>";
			    		             html+="<span class='replyName'>"+replyList[j].userName+"</span>";
			    		             if(replyList[j].personName!=null && replyList[j].personName!=""){
			    		            	html+="<span class='replyName'> 回复 "+replyList[j].personName+"</span>";
			    		             }
			    		             html+=":"+replyList[j].content;
								   html+="</li>";
		    		            }
							    html+="</ul>";
							    html+="<input type='hidden' value='"+ctList[i].sid+"'/>"; 
							    html+="<p class='allReply'>共"+ctList[i].hfNum+"条回复></p>";
							  html+="</div>";
		    		       }
							  html+="<div class='operAndInfo'>";
							     html+="<span class='replyTime'>"+ctList[i].creTime+"</span>";
							     html+="<input type='hidden' value='"+ctList[i].sid+"'/>";
							     html+="<ul class='replyOper'>";
							      /*  html+="<li><img src='images/repost1.png'></li>"; */
							      if(ctList[i].userId==userId){
								      html+="<li class='delPingLunContent'>删除</li>";
							      }
							       html+="<li class='commentImg'><img src='images/comment1.png'></li>";
							       if(ctList[i].pDianzan){
								     html+="<li class='delDianZan'><img src='images/ups_active.png'>"+ctList[i].plNum+"</li>";
							       }else{
							         html+="<li class='addDianZan'><img src='images/up1.png'>"+ctList[i].plNum+"</li>";
							       }
								 html+="</ul>";
								 html+="<div class='commentInputBtn hide'>";
								   html+="<textarea cols='30' class='commentInput' rows='10'></textarea>";
								   html+="<input type='button' class='commentBtn' value='回复'/>";
								 html+="</div>";
						      html+="</div>";
						   html+="</div>";
						html+="</div>";
					  html+="</div>";
		    	 }
		    	 $(".commentList").html(html);
		     }
		     var dzList=data.dzList;//点赞
		     if(dzList!=null && dzList.length>0){
		    	 var html="";
		    	 for(var i=0;i<dzList.length;i++){
		    		 html+="<div class='mui-card'>";
		    		   html+="<div class='mui-card-header mui-card-media'>";
		    		   if(dzList[i].avatar>0){
			    		  html+="<img src='<%=contextPath %>/attachmentController/downFile.action?id="+dzList[i].avatar+"' />";
		    		   }else{
		    		      html+="<img src='dist/images/replyAvatarDemo.png'/>";
		    		   }
		    		     html+="<div class='mui-media-body'>";
		    		       html+="<p class='starer'>"+dzList[i].userName+"</p>";
		    		     html+="</div>";
		    		   html+="</div>";
		    		 html+="</div>";
		    	 }
		    	 $(".starList").html(html);
		     }
		     var rlList=data.rlList;//转发
		     if(rlList!=null && rlList.length>0){
		    	 var html="";
		    	 for(var i=0;i<rlList.length;i++){
		    		 html+="<div class='mui-card'>";
		    		    html+="<div class='mui-card-header mui-card-media'>";
		    		    if(rlList[i].avatar>0){
			    		    html+="<img src='<%=contextPath %>/attachmentController/downFile.action?id="+rlList[i].avatar+"' />";
		    		    }else{
		    		    	html+="<img src='dist/images/replyAvatarDemo.png'/>";
		    		    }
		    		       html+="<div class='mui-media-body'>";
		    		         html+="<p class='blogAuthor'>"+rlList[i].userName+"</p>";
		    		         html+="<p class='repostContent'>"+rlList[i].userName+"转发微博</p>";
		    		         html+="<div class='operAndInfo'>";
		    		            html+="<span class='replyTime'>"+rlList[i].creTime+"</span>";
						     //hmtl+="</div>";
						   html+="</div>";
					    html+="</div>";
					 html+="</div>";
		    	 }
		    	 $(".repostList").html(html);
		     }
		  },
		  error: function(xhr, type){
		    //alert('服务器请求超时!');
		  }
		});
  }
  </script>
</head>
<body onload="doInit();">
<header id="header" class="mui-bar mui-bar-nav">
	<h1 class="mui-title">微博正文</h1>
	</header>
	<div class="footerBar">
	   <ul>
		  <li class="zhuanFaImg">
			转发
		  </li><span class="border"></span>
		  <li class="pinglunImg">
			评论
		  </li><span class="border"></span>
		 <li class='dianZanImg'>
			赞
		</li>
	</ul>
	</div>
		<div class="mui-content">
			<div class="mui-card">
				<div class="mui-card-header mui-card-media">
					<img src="images/avatar.png" id="avatar"/>
					<div class="mui-media-body">
						<p class="blogAuthor" id="weibAuthor"></p>
						<p class="blogTime" id="weibCreatime"></p>
					</div>
					<img class="blogStar" src="images/star.png" id="blogStar">
				</div>
				<div class="mui-card-content" >
					内容
				</div>
			</div>
			<div class="bottomList">
				<ul class="listWrap">
					<li class="allRepost"></li>
					<li class="allComment"> </li>
					<li class="allStar"></li>
				</ul>
				<!-- 转发 -->
				<div class="repostList hide">
			
				</div>
				<!-- 评论 -->
				<div class="commentList hide">
				
				</div>
				<!-- 点赞 -->
				<div class="starList hide">
				
				</div>
			</div>
		</div>
		<div id="shadow"></div>
		<div class="repostInputWrap">
			<textarea name="" class="repostInput" id="" placeholder="写评论"></textarea>
			<img onclick="fullScreenToggle(this)" class="fullScreen" src="images/fullscreen.png">
			<input type="button" value="发送" class="btn btn-comment">
			<ul class='repostWrapList'>
				<li class="repostWrap">
					<input id="repost" type="checkbox">
					<label for="repost" class="repostLabel">同时转发</label>
				</li>
				<li class="pictureList">
				    <input type="hidden" id="imgInput" value=""/>
					<img src="images/picture.png">
				</li>
				<li class="emojiBtn">
					<img src="images/emoji.png">
				</li>
			</ul>
		</div>
		<ul class="emojiBox pictureBox" id="avaterUl">
	
        </ul>
		<script type="text/javascript">
		 
			mui.init({
				swipeBack:true //启用右滑关闭功能
			});
			creatNav();
			function creatNav(){
				var header = document.getElementById("header");
				 //左侧图标+文字按钮
				var leftbtn = document.createElement('button');
				leftbtn.className = 'mui-action-back mui-btn mui-btn-blue mui-btn-link mui-btn-nav mui-pull-left';
				var span = document.createElement('span');
				span.className = 'mui-icon mui-icon-left-nav';
				leftbtn.appendChild(span);
				//var text = document.createTextNode('取消');
				//leftbtn.appendChild(text);
				//remove('.mui-pull-left');
				header.appendChild(leftbtn);
				$(leftbtn).click(function(event){
					window.location.href="index.jsp";
				});
			}

			 //删除原先存在的节点
			function remove(selector) {
				var elem = header.querySelector(selector);
				if (elem) {
					header.removeChild(elem);
				}
			}
			function fullScreenToggle(dom){
				var _this = dom;
				if(!$(_this).hasClass("open")){
					$(".repostInputWrap").css({
						top:"45px",
						height:"auto"
					});
					$(".repostInput").css({
						height:"90%"
					});
					$(_this).addClass("open");
				}else{
					$(".repostInputWrap").css({
						top:"auto",
						height:"160px"
					});
					$(".repostInput").css({
						height:"120px"
					});
					$(_this).removeClass("open");
				}
				
			}
			function setSelectionRange(input, selectionStart, selectionEnd) {
				  if (input.setSelectionRange) {
				    input.focus();
				    input.setSelectionRange(selectionStart, selectionEnd);
				  }
				  else if (input.createTextRange) {
				    var range = input.createTextRange();
				    range.collapse(true);
				    range.moveEnd('character', selectionEnd);
				    range.moveStart('character', selectionStart);
				    range.select();
				  }
				}

				function setCaretToPos (input, pos) {
				  setSelectionRange(input, pos, pos);
				}
				var timeOutEvent=0;//定时器   
				//开始按   
				function gtouchstart(longPressId,preUserId){
				    timeOutEvent = setTimeout("longPress("+longPressId+","+preUserId+")",500);//这里设置定时器，定义长按500毫秒触发长按事件，时间可以自己改，个人感觉500毫秒非常合适   
				    return false;   
				};   
				//手释放，如果在500毫秒内就释放，则取消长按事件，此时可以执行onclick应该执行的事件   
				function gtouchend(){   
				    clearTimeout(timeOutEvent);//清除定时器   
				    if(timeOutEvent!=0){   
				        //这里写要执行的内容（尤如onclick事件）   
				    	huiFuUserId=$(this).prev().val();
						pingLunId=$(this).parent().next().val();
						var userNameHF=$(this).children(":first").html();
						$("#shadow").show();
						$(".repostInputWrap").show();
						$(".repostInput").attr("placeholder","回复:"+userNameHF);
						 $(".repostWrap").hide();//是否转发
						 $(".pictureList").hide();//图片
						 toggleEmoji("hide");
						//$(".repostWrapList").html("<li class='emojiBtn'><img src='images/emoji.png'></li>");
						blHuifu=2;
				    }   
				    return false;   
				};   
				//如果手指有移动，则取消所有事件，此时说明用户只是要移动而不是长按   
				function gtouchmove(){   
				    clearTimeout(timeOutEvent);//清除定时器   
				    timeOutEvent = 0;   
				      
				};   
				   
				//真正长按后应该执行的内容   
				function longPress(longPressId,preUserId){   
				    timeOutEvent = 0;
				    //执行长按要执行的内容，如弹出菜单
				    if(userId==preUserId){
				    	  if(window.confirm("确定删除吗?")){
						    	$.ajax({
									  type: 'POST',
									  url: "<%=contextPath%>/TeeWeibReplyController/deleteReply.action",
									  data: {sid:longPressId},
									  timeout: 10000,
									  async:false,
									  success: function(json){
										  //json = eval("("+json+")");
										  window.location.reload();
									  },
									  error: function(xhr, type){
									    //alert('服务器请求超时!');
									  }
									});
						    }
				    }else{
				    	alert("您没有删除的权限！！！");
				    }
				}   
				
				renderAvatar();
				function renderAvatar(){
					var temp = [];
					for(var i=1; i<=90; i++){
						var labFace = '[emo_'+(i-1)+']';
						temp.push('<li class="emoji" avatarName="[emo_'+(i - 1)+']"><img src="'+'dist/arclist/'+"emo_"+(i-1)+'.png"/></li>');
					}
					$(".emojiBox").append(temp.join(""));
				}
				
				/*点击显示表情*/
				$(".emojiBtn").on("tap",toggleEmoji);
				function toggleEmoji(commend){
					var repostInputWrapBottom = 0;
					var emojiBoxBottom = 0;
					$(".pictureLi").hide();
					$(".emoji").show();
					if(commend && commend=="hide"){
						repostInputWrapBottom = 0;
						emojiBoxBottom = "-120px";
						$(this).removeClass('open');
						$(".repostInputWrap").css({
							bottom: repostInputWrapBottom
						});
						$(".emojiBox").css({
							bottom: emojiBoxBottom
						});
						return;
					}
					if($(".emojiBtn").hasClass('open')){
						repostInputWrapBottom = 0;
						emojiBoxBottom = "-120px";
						$(this).removeClass('open');
					}else{
						repostInputWrapBottom = "120px";
						emojiBoxBottom = "0";
						$(this).addClass('open');
					}
					$(".repostInputWrap").css({
						bottom: repostInputWrapBottom
					});
					$(".emojiBox").css({
						bottom: emojiBoxBottom
					});
				}
				/*点击表情插入到文本框中*/
				$(".emojiBox .emoji").on("tap",function(){
					var name = $(this).attr("avatarName");
					$(".repostInput").val($(".repostInput").val() + name);
					toggleEmoji("hide");
				});
				
				
					 //删除原先存在的节点
			function remove(selector) {
				var elem = header.querySelector(selector);
				if (elem) {
					header.removeChild(elem);
				}
			}
	function pictureLists(){
			$(".pictureLi").show();
			$(".emoji").hide();
			var repostInputWrapBottom = 0;
			var emojiBoxBottom = 0;
			if($(".pictureLi").length==0){
				repostInputWrapBottom = 0;
				emojiBoxBottom = "-120px";
				$(this).removeClass('open');
			}
			/* if($(".emojiBtn").hasClass('open')){
				repostInputWrapBottom = 0;
				emojiBoxBottom = "-120px";
				$(this).removeClass('open');
			} */
			else{
				repostInputWrapBottom = "120px";
				emojiBoxBottom = "0";
				$(this).addClass('open');
			}
			$(".repostInputWrap").css({
				bottom: repostInputWrapBottom
			});
			$(".emojiBox").css({
				bottom: emojiBoxBottom
			});
		}
			$(".pictureList").on("tap",function(){
				if($(".pictureLi").length<9){
					if($(".repostInput").val()==null || $(".repostInput").val()==""){
						$(".repostInput").val("分享图片");
					}
					var children= $(".pictureBox").children();
					doTakePhotob();
				}else{
					alert("您最多只能上传9张图片");
				}
			});
			function doTakePhotob(){
				TakePhoto(function(files){
				    if(files.length!=0){
						var attachIds = [];
						for(var i=0;i<files.length;i++){
							imgNum++;
							if(imgNum<=9){
							  attachIds.push(files[i].path);
							}
						}
						UploadPhoto(function(files0){
							for(var j=0;j<files0.length;j++){
								var filId=suoLuePic(files0[j].id);
								var html="<li class='pictureLi' id='pic"+filId+"'><input type='hidden' value='"+files0[j].id+"'/><img src='<%=contextPath%>/attachmentController/downFile.action?id="+filId+"'><span class='deleteIcon'>×</span></li>";
								imgsId+=filId+",";
							    $(".pictureBox").prepend(html);
							}
							$("#imgInput").val(imgsId);
							pictureLists();
						},attachIds,"imgupload","");
					}
				},1,9); 
			}
			//缩略图
			function suoLuePic(sid){
				var data=0;
				$.ajax({
					  type: 'POST',
					  url: "<%=contextPath%>/attachmentController/picZoom.action",
					  data: {attachId:sid},
					  timeout: 10000,
					  async:false,
					  success: function(json){
						  json = eval("("+json+")");
						  data=json.rtData;
					  },
					  error: function(xhr, type){
					    //alert('服务器请求超时!');
					  }
					});
				return data;
			}
		</script>
</body>
</html>
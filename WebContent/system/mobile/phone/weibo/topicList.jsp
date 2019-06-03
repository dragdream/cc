<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
  String content=request.getParameter("content");
  TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
  int userId=loginUser.getUuid();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta charset="utf-8">
		<title>首页</title>
		<%@ include file="/system/mobile/mui/header.jsp" %>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <meta name="apple-mobile-web-app-capable" content="yes">
	    <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <link rel="stylesheet" href="css/mui.css">
		<style>
			html,
			body {
				background-color: #e1e1e1;
			}
			*{
				margin:0;
				padding:0;
				font-family: "Helvetica Neue,Helvetica,Roboto,Segoe UI,Arial,sans-serif";
			}
			li{
				list-style: none;
			}
			#textInput{
				position:fixed;
				top:44px;
				left: 0;
				right: 0;
				bottom: 40px;
				margin-bottom: 0;
				border:none;
				font-size: 15px;
			}
			.mui-content{
				background-color: #e1e1e1;
			}
			.footerBar{
				position: fixed;
				bottom: 0;
				left: 0;
				right: 0;
				height:40px;
			}
			.caret-down{
				border-top:7px solid #fff;
				border-left: 3px solid transparent;
				border-right: 3px solid transparent;
				border-bottom: 6px solid transparent;
				display: inline-block;
				margin-right: 10px;
				vertical-align: text-bottom;
			}
			.mui-card{
				margin:0;
				margin-top: 20px;
				background-color: #eeeeee;
			}
			.mui-card-content{
				color: #333;
			}
			.mui-card-content p{
				margin:0;
				padding:10px;
				color: #333;
			}
			.mui-card-header>img:first-child{
				overflow: hidden;
				border-radius: 50%;
			}
			.mui-card-footer .mui-card-link{
				background-position: left center;
				background-repeat: no-repeat;
			}
			.imgContent{
				padding:10px;
				width: 70%;
			}
			.imgContent img{
				width: 33%;
			}
			.mui-content{
				padding-bottom: 40px;
			}
			.mui-content>.mui-card:first-child{
				margin-top: 0;
			}
			.mui-bar-nav~.mui-content{
				padding-top: 0;
			}
			.referenceBlog{
				background-color:#e1e1e1;
			}
			.mui-card-footer{
				padding:0;
			}
			.mui-card-footer ul{
				width: 100%;
				display: flex;
				justify-content: space-around;
			}
			.mui-card-footer ul li{
				display: inline-block;
				width: 24%;
				height: 20px;
				text-align: center;
			}
			.mui-card-footer ul li img{
				width: 20px;
				display: inline-block;
				vertical-align: text-bottom;
				margin-right: 3px;
			}

			.group-header{
				overflow: hidden;
			}
			.group-header .title{
				float: left;
				color: #999999;
			}
			.group-header .edit{
				float: right;
				color: #f88310;
			}
			.topBar .group .group-item.newItem{
				border:1px dashed #aeaeae;
				background-color: transparent;
			}
			.remove{
				position: absolute;
				right: -5px;
				top: -5px;
				width: 15px;
				height: 15px;
				display: inline-block;
				border-radius: 50%;
				background-color: rgba(0,0,0,0.3);
			}
			.remove:after{
				content:"x";
				font-size: 12px;
				text-align: center;
				line-height: 5px;
				color: #fff;
				position: relative;
				top: -10px;
			}
			.add{
				position: absolute;
				right: -5px;
				top: -5px;
				width: 15px;
				height: 15px;
				display: inline-block;
				border-radius: 50%;
				background-color: #f57902;
			}
			.add:after{
				content:"+";
				font-size: 12px;
				text-align: center;
				line-height: 5px;
				color: #fff;
				position: relative;
				top: -10px;
			}
			.topBar{
				width: 100%;
				height: 200px;
				background: linear-gradient(#2989cc, #80c5ed); /* 标准的语法 */
				padding-top: 40px;
			}
			.topicImg{
				width: 120px;
				margin-left: 15px;
				margin-top: 20px;
				float: left;
			}
			.topicInfoWrap{
				margin-left:150px;
				color: #fff;
				margin-top: 20px;
			}
			.topicInfoWrap span{
				margin:0 5px;
			}
			.topicInfoWrap span:first{
				margin-left: 0;
			}
			.topicInfoWrap p.topicName{
				font-size: 20px;
				color: #fff;
				margin-top: 32px;
			}
			.topicInfoWrap p.topicInfo{
				font-size: 16px;
				color: #fff;
				margin-top: 50px;
			}
			.sortBar{
				overflow: hidden;
				background-color: #e1e1e1;
				padding:0 10px;
			}
			.spanLeft{
				float: left;
				line-height: 30px;
				color: #666666;
				font-size: 14px;
			}
			.spanRight{
				float:right;
				line-height: 30px;
				color: #666666;
				font-size: 14px;
			}
			.footerBar{
				position: fixed;
				bottom: -1px;
				left: 0;
				right: 0;
				height:40px;
				z-index: 3;
				background: #e2e2e2;
			}

			.footerBar .repostWrap{
				width: 100px;
				vertical-align: top;
			}
			.footerBar ul{
				text-align:center;
				padding:5px 0;
				color: #999;
				display: flex;
				justify-content: space-around;
			}
			.footerBar li{
				display: inline-block;
				width: 50%;
				color: #333;
				text-align: center;
			}
			.footerBar li span{
				display: inline-block;
				padding-left: 25px;
				background-repeat: no-repeat;
				background-position: left;
				background-size: 20px;
				line-height: 25px;
			}
			.footerBar li span.follow{
				background-image: url(images/follow.png);
				color: #f76a21;
			}
			.footerBar li span.compose{
				background-image: url(images/compose.png);
			}
			.footerBar li img{
				width: 100%;
			}
			.mui-bar-nav{
				box-shadow: none;
			}
		</style>
		<script type="text/javascript">
		 var page = 1;
		 var pageSize=5;
		 var content="<%=content%>";
		 var userId=<%=userId%>;
		 function doInit(){
			 query();
			 readTopicCount();//阅读次数
			 countTopic();//关注话题数量
			 loginUserGzTopic();//判断是否关注
			 //关注
			 $("body").on("tap","#guanZhuTap",function(){
				 $.ajax({
					  type: 'POST',
					  url: "<%=contextPath%>/TeeWeibGuanZhuController/addGzTopic.action",
					  data: {topicName:content},
					  timeout: 10000,
					  success: function(json){
						 //json = eval("("+json+")");
						  loginUserGzTopic();
						  countTopic();
					  },
					  error: function(xhr, type){
					    //alert('服务器请求超时!');
					  }
					});
			 });
			//取消关注
			 $("body").on("tap","#quxiaoGzTap",function(){
				 $.ajax({
					  type: 'POST',
					  url: "<%=contextPath%>/TeeWeibGuanZhuController/deleteGzTopic.action",
					  data: {topicName:content},
					  timeout: 10000,
					  success: function(json){
						 //json = eval("("+json+")");
						  loginUserGzTopic();
						  countTopic();
					  },
					  error: function(xhr, type){
					    //alert('服务器请求超时!');
					  }
					});
			 });
			 //取消点赞
			  $("body").on("tap",".deleteDianZai",function(){
				  var sid=$(this).parent().prev().val();
				  $.ajax({
					  type: 'POST',
					  url: "<%=contextPath%>/TeeWeibDianZaiController/deleteDianZan.action",
					  data: {sid:sid},
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
			 });
			  //点赞
			  $("body").on("tap",".addDianZai",function(){
				  var sid=$(this).parent().prev().val();
				  $.ajax({
					  type: 'POST',
					  url: "<%=contextPath%>/TeeWeibDianZaiController/addDianZan.action",
					  data: {sid:sid},
					  timeout: 10000,
					  async:false,
					  success: function(json){
						 // json = eval("("+json+")");
						  window.location.reload();
					  },
					  error: function(xhr, type){
					    //alert('服务器请求超时!');
					  }
					});
			 });
			  //转发
			  $("body").on("tap",".zhuanFa",function(){
				  var sid=$(this).parent().prev().val();
				  window.open("blogContent.jsp?type=1&sid="+sid);
			 });
			  //评论
			  $("body").on("tap",".pingLun",function(){
				  var sid=$(this).parent().prev().val();
				  window.open("blogContent.jsp?type=2&sid="+sid);
			 });
			  //发微博
			  $("body").on("tap",".compose",function(){
				  window.open("sendPublish.jsp");
			 });
			 $("body").on("tap","a",function(){
			         var content=$(this).html();
			         content=content.substring(1,content.length-1);
			         while(content.indexOf('\n') >= 0){
						  content = content.replace('\n','<br/>');
					 }
			         open("topicList.jsp?content="+content); 
			     });
			   //删除微博deletePublishById
				  $("body").on("tap",".deletePublishById",function(){
					  if(window.confirm("确定删除吗?")){
						  var weibId=$(this).prev().val();
						  $.ajax({
							  type: 'POST',
							  url: "<%=contextPath%>/TeeWeibPublishController/deletePublishById.action",
							  data: {sid:weibId},
							  timeout: 10000,
							  success: function(json){
								  //json = eval("("+json+")");
								  window.location.reload();
							  },
							  error: function(xhr, type){
							    //alert('服务器请求超时!');
							  }
						});
					  }
				 });
				  //查看更多
				  $("body").on("tap",".chaKanMore",function(){
					  pageSize+=5;
					  query();
				 });
				  //预览图片
				  $("body").on("tap",".yunLanPicture",function(){
					  var strArr= $(this).prev().prev().val();
					  strArr= strArr.substring(0,strArr.length-1);
					  var picId= $(this).prev().val();
					  PicExplore(strArr,"",picId);
				 });
		 }
		 function query(){
			 $.ajax({
				  type: 'POST',
				  url: "<%=contextPath%>/TeeWeibPublishController/findTopicAll.action",
				  data: {content:content,page:1,pageSize:pageSize},
				  timeout: 10000,
				  success: function(json){
					 json = eval("("+json+")");
					 var count=json.total;
					 $("#taoLunNum").html("讨论"+count);
					 var render = [];
					 for(var i=0;i<json.rows.length;i++){
						var item = json.rows[i];
						render.push("<div class='mui-card' style=' background-color: #fff;'>");
						if(item.userId==userId){
							render.push("<input type='hidden' value='"+item.sid+"'/>");
							render.push("<div class='deletePublishById' style='float:right;'><img src='images/del.png' style='width:20px;'/></div>");
						}
						render.push("<div class='mui-card-header mui-card-media' style='width: 90%;'>");
						if(item.avatar==0){
							render.push("<img src='dist/images/replyAvatarDemo.png'/>");
						}else{
							render.push("<img src='<%=contextPath %>/attachmentController/downFile.action?id="+item.avatar+"' />");
						}

						render.push("<div class='mui-media-body'>");
						render.push("<p class='blogAuthor'>"+item.userName+"</p>");
						render.push("<p class='blogTime'>"+item.createTime+"</p>");
						render.push("</div>");
						render.push("</div>");
						render.push("<div class='mui-card-content'>");	
						render.push("<p>"+item.content+"</p>");		
						//render.push("<div class='imgContent'>");	
						if(item.img!=null && item.img!=""){
							render.push("<div class=\"imgContent\">");
							var str=item.img;
							var imgy=item.imgy;
							var imgArr=str.split(",");
							var imgyArr=imgy.split(",");
							for(var j=0;j<imgArr.length-1;j++){
								render.push("<input type='hidden' value='"+item.imgy+"'/>");
								render.push("<input type='hidden' value='"+imgyArr[j]+"'/>");
							  render.push("<img class='yunLanPicture' src='<%=contextPath %>/attachmentController/downFile.action?id="+imgArr[j]+"' alt=''>");
						   }
							render.push("</div>");
						}
						//render.push("<img src='images/demoImg.jpg' alt=''>");
						//render.push("</div>");
						render.push("</div>");
						render.push("<div class='mui-card-footer'>");
						render.push("<input type='hidden' value='"+item.sid+"'/>");
						render.push("<ul>");
						render.push("<li class='zhuanFa'><img src='images/repost1.png'>"+item.number+"</li>");
						render.push("<li class='pingLun'><img src='images/comment1.png'>"+item.num+"</li>");
						if(item.dianzan){//已经点赞
					    	render.push("<li class='deleteDianZai'><img src='images/ups_active.png'>"+item.count+"</li>");
						}else{
					    	render.push("<li class='addDianZai'><img src='images/up1.png'>"+item.count+"</li>");
						}
						//render.push("<li><img src='images/up1.png'>"+item.count+"</li>");
						render.push("</ul>");
					    render.push("</div></div>");
					}
					 if(json.rows.length==json.total){
						 render.push("<div style='width:100%;height:36px;margin-top: 10px;text-align: center;'>没有更多了</div>");
					 }else{
						render.push("<div class='chaKanMore' style='width:100%;height:36px;margin-top: 10px;text-align: center;'>查看更多></div>");
					 }
					$(".mui-content").html(render.join(""));
				
				  },
				  error: function(xhr, type){
				    //alert('服务器请求超时!');
				  }
				});
		 }
		//阅读此话题的次数
		 function readTopicCount(){
			 $.ajax({
				  type: 'POST',
				  url: "<%=contextPath%>/TeeWeibPublishController/readTopicCount.action",
				  data: {content:content},
				  timeout: 10000,
				  success: function(json){
					 json = eval("("+json+")");
					 var data=json.rtData;
				 	 if(data!=null){
				 		$("#readCount").html("阅读"+data);
				 	 }
				  },
				  error: function(xhr, type){
				    //alert('服务器请求超时!');
				  }
				});
		 }
		//关注话题（数量）
		 function countTopic(){
			 $.ajax({
				  type: 'POST',
				  url: "<%=contextPath%>/TeeWeibGuanZhuController/countTopic.action",
				  data: {topicName:content},
				  timeout: 10000,
				  success: function(json){
					 json = eval("("+json+")");
					 var data=json.total;
					 $("#guangZhuNum").html("关注"+data);
				  },
				  error: function(xhr, type){
				    //alert('服务器请求超时!');
				  }
				});
		 }
		//判断当前登录人是否已经关注此话题
		 function loginUserGzTopic(){
			 $.ajax({
				  type: 'POST',
				  url: "<%=contextPath%>/TeeWeibGuanZhuController/loginUserGzTopic.action",
				  data: {topicName:content},
				  timeout: 10000,
				  success: function(json){
					 json = eval("("+json+")");
					 var data=json.rtData;
					 if(data){
						 $("#quxiaoGzTap").show();
						 $("#guanZhuTap").hide();
					 }else{
						 $("#guanZhuTap").show();
						 $("#quxiaoGzTap").hide();
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
		<div class="topBar">
			<img class="topicImg" src="images/topicImg.png" alt="">
			<div class="topicInfoWrap">
				<p class="topicName">#<%=content%>#</p>
				<p class="topicInfo">
					<span id="readCount">阅读0</span>
					<span id="guangZhuNum">关注0</span>
					<span id="taoLunNum">讨论0</span>
				</p>
			</div>
		</div>
		<div class="sortBar">
			 <span class="spanLeft">时间排序</span>
			<span class="spanRight"></span>
		</div>
		<header id="header" class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">话题</h1>
		</header>
		<div class="footerBar">
			<ul>
				<li>
					<span style="display: none" class="follow" id="guanZhuTap">关注</span>
					<span style="display: none" class="follow" id="quxiaoGzTap">取消关注</span>
				</li><span class="border"></span>
				<li >
					<span  class="compose">发微博</span>
				</li>
			</ul>
		</div>
		<div class="mui-content">
		
		</div>
        
		<script type="text/javascript">
		   /* 
			mui.init({
				swipeBack:true //启用右滑关闭功能
			}); */
			
			
			mui.init({
				pullRefresh: {
					container: '#pullrefresh',
					down: {
						callback: pulldownRefresh
					},
					up: {
						contentrefresh: '正在加载...',
						callback: pullupRefresh
					}
				}
			});
			
			
			/**
			 * 下拉刷新具体业务实现
			 */
			function pulldownRefresh() {
				var url = "<%=contextPath%>/TeeWeibPublishController/findCollectAll2.action";
				mui.ajax(url,{
					type:"post",
					dataType:"html",
					data:{meter:meter,deptId:deptId},
					timeout:10000,
					success:function(json){
						mui('#pullrefresh').pullRefresh().endPulldownToRefresh(false);
						json = eval("("+json+")");
						var render = [];
						for(var i=0;i<json.rows.length;i++){
							var item = json.rows[i];
							render.push("<div class=\"mui-card\">");
							render.push("<div class=\"mui-card-header mui-card-media\">");
							render.push("<img src=\"images/avatar.png\" />");
							render.push("<div class=\"mui-media-body\">");
							render.push("<p class=\"blogAuthor\">"+item.userName+"</p>");
							render.push("<p class=\"blogTime\">"+item.createTime+"</p>");
							render.push("</div></div>");
							render.push("<div class=\"mui-card-content\" >");
							render.push("<p>"+item.content+"</p>");
							render.push("<div class=\"imgContent\">");
							render.push("<img src=\"images/demoImg.jpg\" alt=\"\">");
							render.push("</div></div>");
							render.push("<div class=\"mui-card-footer\">");
							render.push("<ul>");
							render.push("<li><img src=\"images/star.png\">11</li>");
							render.push("<li><img src=\"images/repost1.png\">22</li>");
							render.push("<li><img src=\"images/comment1.png\">33</li>");
							render.push("<li><img src=\"images/up1.png\">44</li>");
							render.push("</ul>");
							render.push("</div></div>");
						}
						$("#list").html(render.join(""));
						
						 $(".mui-media").each(function(i,obj){
							obj.removeEventListener("tap",detail);
							obj.addEventListener("tap",detail);
						});
					},
					error:function(){
						
					}
				});
				
			}
			
			/**
			 * 上拉加载具体业务实现
			 */
			function pullupRefresh() {
				var url = "<%=contextPath%>/TeeWeibPublishController/findTopicAll2.action";
				mui.ajax(url,{
					type:"post",
					dataType:"html",
					data:{content:content},
					timeout:10000,
					success:function(json){
						mui('#pullrefresh').pullRefresh().endPulldownToRefresh(false);
						json = eval("("+json+")");
						var render = [];
						for(var i=0;i<json.rows.length;i++){
							var item = json.rows[i];
							render.push("<div class='mui-card'>");
							render.push("<div class='mui-card-header mui-card-media'>");
							render.push("<img src='images/avatar.png'/>");
							render.push("<div class='mui-media-body'>");
							render.push("<p class='blogAuthor'>"+item.userName+"</p>");
							render.push("<p class='blogTime'>"+item.createTime+"</p>");
							render.push("</div>");
							render.push("</div>");
							render.push("<div class='mui-card-content'>");	
							render.push("<p>"+item.content+"<a href=''>#"+content+"#</a></p>");		
							render.push("<div class='imgContent'>");	
							render.push("<img src='images/demoImg.jpg' alt=''>");
							render.push("</div>");
							render.push("</div>");
							render.push("<div class='mui-card-footer'>");
							render.push("<ul>");
							render.push("<li><img src='images/repost1.png'>"+item.number+"</li>");
							render.push("<li><img src='images/comment1.png'>"+item.num+"</li>");
							alert(item.dianzan);
							if(item.dianzan){//已经点赞
						    	render.push("<li><img src='images/ups_active.png'>"+item.count+"</li>");
							}else{
						    	render.push("<li><img src='images/up1.png'>"+item.count+"</li>");
							}
							render.push("</ul>");
						    render.push("</div></div>");
						}
						$(".mui-content").html(render.join(""));
						
					$(".mui-media").each(function(i,obj){
							obj.removeEventListener("tap",detail);
							obj.addEventListener("tap",detail);
						});
					},
					error:function(){
						
					}
				});
			}
			
			
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

				remove('.mui-pull-left');
				header.appendChild(leftbtn);
			}

			 //删除原先存在的节点
			function remove(selector) {
				var elem = header.querySelector(selector);
				if (elem) {
					header.removeChild(elem);
				}
			}
		</script>

	</body>

</html>
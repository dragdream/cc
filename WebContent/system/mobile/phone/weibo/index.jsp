<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
  TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
  int deptId=loginUser.getDept().getUuid();
  int userId=loginUser.getUuid();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="utf-8">
		<title>首页</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
        <%@ include file="/system/mobile/mui/header.jsp" %>
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
				display:inline-block;
			}
			.imgContent img{
				width: 33%;
			}
			.mui-content>.mui-card:first-child{
				margin-top: 0;
			}
			.referenceBlog{
				background-color:#e1e1e1;
			}
			.mui-card-footer{
				padding:0;
			}
			.mui-card-footer ul{
				width: 100%;
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

			.topBar{
				position: fixed;
				top: 44px;
				left: 0;
				right: 0;
				z-index: 3;
				background: #eee;
				box-shadow: 0 0 5px rgba(0,0,0,0.6);
				padding:10px 0;
			}
			.topBar .group{
				font-size: 14px;
				padding:0 10px;
				line-height: 31px;
			}

			.topBar .group .group-content{
				display: block;
			}
			.topBar .group .group-item{
				font-size: 14px;
				width: 21%;
				background-color: #e2e2e2;
				justify-content: center;
				margin: 5px 0;
				position: relative;
				display: inline-block;
				text-align: center;
				margin: 0.25rem;
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
  </style>
  <script type="text/javascript">
   var page = 1;
   var pageSize=5;
   var meter=0;
   var deptId=<%=deptId%>;
   var userId=<%=userId%>;
  function doInit(){
	  pullupRefresh();
	  //收藏
	  $("body").on("tap",".addCollect",function(){
		  var sid=$(this).parent().attr("id");
		  addCollectPublish(sid);
	 });
	  //取消收藏
	  $("body").on("tap",".delCollect",function(){
		  var sid=$(this).parent().attr("id");
		  deleteCollectPublish(sid);
	 });
	  //取消点赞
	  $("body").on("tap",".delDianZan",function(){
		  var sid=$(this).parent().attr("id");
		  deleteDianZanPublish(sid);
	 });
	  //点赞
	  $("body").on("tap",".addDianZan",function(){
		  var sid=$(this).parent().attr("id");
		  addDianZanPublish(sid);
	 });
	  //转发
	  $("body").on("tap",".zhuanFa",function(){
		  var sid=$(this).parent().attr("id");
		  window.open("blogContent.jsp?type=1&sid="+sid);
	 });
	  //评论
	  $("body").on("tap",".pingLun",function(){
		  var sid=$(this).parent().attr("id");
		  window.open("blogContent.jsp?type=2&sid="+sid);
	 });
	  //发微博
	  $("body").on("tap",".sendPublish",function(){
		  window.open("sendPublish.jsp");
	 });
	  //
	  //查看更多
	  $("body").on("tap",".chaKanMore",function(){
		  pageSize+=5;
		  query(meter);
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
    $("body").on("tap","a",function(){
         var content=$(this).html();
         content=content.substring(1,content.length-1);
         while(content.indexOf('\n') >= 0){
			  content = content.replace('\n','<br/>');
		 }
         open("topicList.jsp?content="+content); 
     });
    $("body").on("tap",".yunLanPicture",function(){
		  var strArr= $(this).prev().prev().val();
		  strArr= strArr.substring(0,strArr.length-1);
		  var picId= $(this).prev().val();
		  PicExplore(strArr,"",picId);
	 });
  }
  function query(args){
	  meter=args;
	  pullupRefresh();
	  $(".group-item").css("background-color","#e2e2e2");
	  if(meter==0){
		  $(".allDepartment").html("首页");
		  $("#show").css("background-color","#03a9f4");
	  }else if(meter==1){
		  $(".allDepartment").html("我的收藏");
		  $("#collect").css("background-color","#03a9f4");
	  }
	  else if(meter==2){
		  $(".allDepartment").html("广场");
		  $("#guangC").css("background-color","#03a9f4");
	  }
	  else if(meter==3){
		  $(".allDepartment").html("我的部门");
		  $("#myDept").css("background-color","#03a9f4");
	  }
	  $(".group-content").hide();
	  $(".topBar").hide();
  }
  //选择
  function choose(){
	 if($(".group-content").is(":hidden")){
		 $(".group-content").show();
		 $(".topBar").show();
	 }else{
		 $(".group-content").hide();
		 $(".topBar").hide();
	 }
  }
  //跳转微博页面（发微博）
  
  </script>
</head>
	<body onload="doInit();">
		<div class="topBar" style="display: none;">
			<div class="group">
				<div class="group-header" >
					<!-- <span class="title">默认分组</span> -->
					<!-- <span class="edit">编辑/完成</span> -->
				</div>
				<div class="group-content" style="display: none;">
					<div class="group-item" onclick="query(0)" id="show" style="background-color:#03a9f4">首页</div>
					<div class="group-item" onclick="query(1);" id="collect">我的收藏</div>
					<div class="group-item" onclick="query(2);" id="guangC">广场</div>
					<div class="group-item" onclick="query(3);" id="myDept">我的部门</div>
				</div>
			</div>
		</div>

		<header id="header" class="mui-bar mui-bar-nav">
			<!-- <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a> -->
			<div class="mui-title">
				<span class="homePage">首页 <span class="caret-down" onclick="choose();"></span></span>
				<span class="allDepartment">首页</span>
			</div>
		</header>
		<!--下拉刷新容器-->
		<div id="pullrefresh" class="mui-content mui-scroll-wrapper">
			<div class="mui-scroll">
				<!--数据列表-->
				<ul class="mui-table-view" id="list" style="    background-color: #eee;">
					
				</ul>
			</div>
		</div>
		<script type="text/javascript">
			/**
			 * 上拉加载具体业务实现
			 */
			 mui.init({  
	              pullRefresh: {  
	                  container: '#pullrefresh',  
	                  down: {  
	                      callback: pullupRefresh  
	                  },  
	              }  
	          });  
			function pullupRefresh() {
				var url = "<%=contextPath%>/TeeWeibPublishController/findCollectAll.action";
				$.ajax(url,{
					type:"post",
					dataType:"html",
					data:{meter:meter,deptId:deptId,page:1,rows:pageSize},
					timeout:10000,
					success:function(json){
						mui('#pullrefresh').pullRefresh().endPulldownToRefresh(false);
						json = eval("("+json+")");
						var render = [];
						for(var i=0;i<json.rows.length;i++){
							var item = json.rows[i];
							render.push("<div class=\"mui-card\" style='background-color: #fff;'>");
							if(item.userId==userId){
								render.push("<input type='hidden' value='"+item.sid+"'/>");
								render.push("<div class='deletePublishById' style='float:right;'><img src='images/del.png' style='width:14px;'/></div>");
							}
							render.push("<div class=\"mui-card-header mui-card-media\" style='width: 90%;'>");
							if(item.avatar==0){
								render.push("<img src='dist/images/replyAvatarDemo.png'/>");
							}else{
								render.push("<img src='<%=contextPath %>/attachmentController/downFile.action?id="+item.avatar+"' />");
							}
							render.push("<div class=\"mui-media-body\">");
							render.push("<p class=\"blogAuthor\">"+item.userName+"</p>");
							render.push("<p class=\"blogTime\">"+item.createTime+"</p>");
							render.push("</div></div>");
							render.push("<div class=\"mui-card-content\" >");
							render.push("<p>"+item.content+"</p>");
							if(item.zfCotent!=null && item.zfCotent!=""){
								render.push("<p>@"+item.zfCotent+"</p>");
							}
							if(item.img!=null && item.img!=""){
								render.push("<div class=\"imgContent\">");
								var str=item.img;
								var imgy=item.imgy;
								var imgArr=str.split(",");
								var imgyArr=imgy.split(",");
								for(var j=0;j<imgArr.length-1;j++){
									render.push("<input type='hidden' value='"+item.imgy+"'/>");
									render.push("<input type='hidden' value='"+imgyArr[j]+"'/>");
								  render.push("<img  class='yunLanPicture' src='<%=contextPath %>/attachmentController/downFile.action?id="+imgArr[j]+"' alt=''>");
							   }
								render.push("</div>");
							}
							render.push("</div>");
							render.push("<div class=\"mui-card-footer\">");
							render.push("<ul id='"+item.sid+"'>");
							if(item.collect){
							  render.push("<li class='delCollect'><img src=\"images/stared.png\"></li>");
							}else{
							  render.push("<li class='addCollect'><img src=\"images/star.png\"></li>");
							}
							render.push("<li class='zhuanFa'><img src=\"images/repost1.png\">"+item.number+"</li>");
							render.push("<li class='pingLun'><img src=\"images/comment1.png\">"+item.num+"</li>");
							if(item.dianzan){
							  render.push("<li class='delDianZan'><img src=\"images/ups_active.png\">"+item.count+"</li>");
							}else{
							  render.push("<li class='addDianZan'><img src=\"images/up1.png\">"+item.count+"</li>");
							}
							render.push("</ul>");
							render.push("</div></div>");
						}
						if(json.rows.length==json.total){
						    render.push("<div style='width:100%;height:36px;margin-top: 10px;text-align: center;font-size:14px'>没有更多了</div>");
						}else{
							render.push("<div class='chaKanMore' style='width:100%;height:36px;margin-top: 10px;text-align: center;'>查看更多></div>");
						}
						$("#list").html(render.join(""));
					},
					error:function(){
						
					}
				});
			}
			
			creatNav();
			function creatNav(){
				var header = document.getElementById("header");
				 //左侧图标+文字按钮
				/* var leftbtn = document.createElement('button');
				leftbtn.className = 'mui-action-back mui-btn mui-btn-blue mui-btn-link mui-btn-nav mui-pull-left';
				var span = document.createElement('span');
				span.className = 'mui-icon mui-icon-left-nav';
				leftbtn.appendChild(span);
				var text = document.createTextNode('取消');
				//leftbtn.appendChild(text);
				leftbtn.onclick = function () {  
					CloseWindow();
                };
				remove('.mui-pull-left');
				header.appendChild(leftbtn); */
				
				//右侧文字按钮
				var rightbtn = document.createElement('button');
				rightbtn.className = 'mui-btn mui-btn-blue mui-btn-link mui-pull-right sendPublish';
				rightbtn.innerText = '发微博';
				remove('.mui-pull-right');
				header.appendChild(rightbtn);
			}

			 //删除原先存在的节点
			function remove(selector) {
				var elem = header.querySelector(selector);
				if (elem) {
					header.removeChild(elem);
				}
			}
			 function detail(){
				var sid = this.getAttribute("sid");
				var readType = this.getAttribute("readType");
				if(userAgent.indexOf("DingTalk")==-1 && userAgent.indexOf("MicroMessenger")==-1){//原生
					OpenWindow('',contextPath+"/system/mobile/phone/news/newsInfo.jsp?sid="+sid+"&isLooked="+readType);
				}else{
					window.location = "newsInfo.jsp?sid="+sid+"&isLooked="+readType;
				}
				
			}
			mui.ready(function() {
				mui('#pullrefresh').pullRefresh().pullupLoading();
			});
			//收藏
			function addCollectPublish(sid){
				$.ajax({
					  type: 'POST',
					  url: "<%=contextPath%>/TeeWeibConllectController/addCollect.action",
					  data: {sid:sid},
					  timeout: 10000,
					  success: function(json){
						  //json = eval("("+json+")");
					     pullupRefresh();
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
						  pullupRefresh();
						 // json = eval("("+json+")");
					
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
						  pullupRefresh();
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
						  pullupRefresh();
						  //json = eval("("+json+")");
					  },
					  error: function(xhr, type){
					    //alert('服务器请求超时!');
					  }
					});
			}
			//显示转发页面
			function ZhuanFaPage(sid){
				window.open("blogContent.jsp?type=1&sid="+sid);
			}
			//显示评论页面
			function pingLunPage(sid){
				window.open("blogContent.jsp?type=2&sid="+sid);
			}
		</script>

	</body>

</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="utf-8">
		<title>首页（所有评论）</title>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
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
				margin-top: 10px;
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
				width: 48%;
			}
			.imgContent img{
				width: 100%;
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
			.mui-card-footer:before, .mui-card-header:after{
				background-color: transparent;
			}
			.resouceBlog .leftImg {
				width: 25%;
			}
			.mui-card-content{
				padding:0 10px;
				padding-bottom: 10px;

			}
			.resouceBlog {
				overflow: hidden;
			}
			.resouceBlog .leftImg{
				float: left;
			}
			.resouceBlog .leftImg img{
				width: 100%;
			}
			.resouceBlog .blogContent{
				width: 100%;
				padding-left: 27%;
				background-color: #e1e1e1;
			}
			.mui-card-content p{
				padding: 0;
				line-height: 25px;
				width: 100%;
				overflow: hidden;
				text-overflow: ellipsis;
				white-space: nowrap;
			}
			.lastRepost{

			}
			.rootTitle{
				color: #999999;
				width: 98%;
				margin:0 auto;
				line-height: 30px;
				font-size: 12px;
				border-bottom: 1px solid #bfbfbf;
			}
			.replyBtn{
				border:1px solid #c9c9c9;
				border-radius: 5px;
				width:46px;
				height:25px;
				line-height: 25px;
				text-align: center;
				font-size: 14px;
				color: #888888;
				position: absolute;
			    right: 10px;
			    top: 10px;
			}
		</style>
	</head>

	<body>
		<header id="header" class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">所有评论</h1>
		</header>
		<div class="mui-content">

			<div class="mui-card">
				<p class="rootTitle">系统管理员评论了系统管理员</p>
				<div class="mui-card-header mui-card-media">
					<img src="images/avatar.png" />
					<div class="mui-media-body">
						<p class="blogAuthor">系统管理员</p>
						<p class="blogTime">2015-02-02 12:30</p>
						<input type="buttn" value="回复" class="replyBtn">
					</div>
				</div>
				<div class="mui-card-content" >
					<p>赞了这条微博</p>
					<div class="resouceBlog">
						<div class="leftImg">
							<img src="images/demoImg.jpg" alt="">
						</div>
						<div class="blogContent">
							<p>@系统管理员</p>
							<p>#美丽动人</p>
							<p>总有一番美景能打动你,赶快加入我们吧！赶快加入我们吧！</p>
						</div>
					</div>
				</div>
			</div>
			<div class="mui-card">
				<div class="mui-card-header mui-card-media">
					<img src="images/avatar.png" />
					<div class="mui-media-body">
						<p class="blogAuthor">系统管理员</p>
						<p class="blogTime">2015-02-02 12:30</p>
						<input type="buttn" value="回复" class="replyBtn">
					</div>
				</div>
				<div class="mui-card-content" >
					<p>赞了这条微博</p>
					<div class="resouceBlog">
						<p class="lastRepost"><a href="">@系统管理员</a>: 来呀，出发吧！</p>
						<div class="leftImg">
							<img src="images/demoImg.jpg" alt="">
						</div>
						<div class="blogContent">
							<p>@系统管理员</p>
							<p>#美丽动人</p>
							<p>总有一番美景能打动你,赶快加入我们吧！</p>
						</div>
					</div>
				</div>
			</div>
			<div class="mui-card">
				<div class="mui-card-header mui-card-media">
					<img src="images/avatar.png" />
					<div class="mui-media-body">
						<p class="blogAuthor">系统管理员</p>
						<p class="blogTime">2015-02-02 12:30</p>
						<input type="buttn" value="回复" class="replyBtn">
					</div>
				</div>
				<div class="mui-card-content" >
					<p>赞了这条微博</p>
					<div class="resouceBlog">
						<p class="lastRepost"><a href="">@系统管理员</a>: 来呀，出发吧！</p>
						<div class="leftImg">
							<img src="images/demoImg.jpg" alt="">
						</div>
						<div class="blogContent">
							<p>@系统管理员</p>
							<p>#美丽动人</p>
							<p>总有一番美景能打动你,赶快加入我们吧！</p>
						</div>
					</div>
				</div>
			</div>
			<div class="mui-card">
				<div class="mui-card-header mui-card-media">
					<img src="images/avatar.png" />
					<div class="mui-media-body">
						<p class="blogAuthor">系统管理员</p>
						<p class="blogTime">2015-02-02 12:30</p>
						<input type="buttn" value="回复" class="replyBtn">
					</div>
				</div>
				<div class="mui-card-content" >
					<p>赞了这条微博</p>
					<div class="resouceBlog">
						<p class="lastRepost"><a href="">@系统管理员</a>: 来呀，出发吧！</p>
						<div class="leftImg">
							<img src="images/demoImg.jpg" alt="">
						</div>
						<div class="blogContent">
							<p>@系统管理员</p>
							<p>#美丽动人</p>
							<p>总有一番美景能打动你,赶快加入我们吧！</p>
						</div>
					</div>
				</div>
			</div>
			<div class="mui-card">
				<div class="mui-card-header mui-card-media">
					<img src="images/avatar.png" />
					<div class="mui-media-body">
						<p class="blogAuthor">系统管理员</p>
						<p class="blogTime">2015-02-02 12:30</p>
						<input type="buttn" value="回复" class="replyBtn">
					</div>
				</div>
				<div class="mui-card-content" >
					<p>赞了这条微博</p>
					<div class="resouceBlog">
						<p class="lastRepost"><a href="">@系统管理员</a>: 来呀，出发吧！</p>
						<div class="leftImg">
							<img src="images/demoImg.jpg" alt="">
						</div>
						<div class="blogContent">
							<p>@系统管理员</p>
							<p>#美丽动人</p>
							<p>总有一番美景能打动你,赶快加入我们吧！</p>
						</div>
					</div>
				</div>
			</div>
		</div>

		<script src="js/mui.min.js"></script>
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
				var text = document.createTextNode('消息');
				leftbtn.appendChild(text);

				//右侧文字按钮
				var rightbtn = document.createElement('button');
				rightbtn.className = 'mui-btn mui-btn-blue mui-btn-link mui-pull-right';
				rightbtn.innerText = "回复";
				remove('.mui-pull-right');
				header.appendChild(rightbtn);

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
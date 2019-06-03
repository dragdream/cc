<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="utf-8">
		<title>发微博（评论）</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">

		<link rel="stylesheet" href="css/mui.css">
		<style>
			html,
			body {
				background-color: #fff;
			}
			*{
				margin:0;
				padding:0;
				font-family: "Microsoft Yahei";
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
			.footerBar{
				position: fixed;
				bottom: 0;
				left: 0;
				right: 0;
				height:40px;
			}

			.footerBar .repostWrap{
				width: 100px;
				vertical-align: top;
			}
			#repost{
				width: 15px;
				height: 15px;
				vertical-align: middle;
			}
			label.repostLabel{
				font-size:14px;
				vertical-align: middle;
			}
			.footerBar ul{
				padding:10px 0;
			}
			.footerBar li{
				/* float: left; */
				margin-left: 10px;
				display: inline-block;
				margin: 0 10px;
				width: 20px;
				height: 20px;
			}
			.footerBar li img{
				width: 100%;
			}
		</style>
	</head>

	<body>
		<header id="header" class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">发评论</h1>
		</header>
		<div class="footerBar">
			<ul>
				<li class="repostWrap">
					<input id="repost" type="checkbox">
					<label for="repost" class="repostLabel">同时转发</label>
				</li>
				<li>
					<img src="images/picture.png">
				</li>
				<li>
					<img src="images/link.png">
				</li>
				<li>
					<img src="images/topic.png">
				</li>
				<li>
					<img src="images/emoji.png">
				</li>
			</ul>
		</div>
		<div class="mui-content">
			<textarea name="" id="textInput" placeholder="写评论"></textarea>
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
				var text = document.createTextNode('取消');
				leftbtn.appendChild(text);

				remove('.mui-pull-left');
				header.appendChild(leftbtn);

				 //右侧文字按钮
				var rightbtn = document.createElement('button');
				rightbtn.className = 'mui-btn mui-btn-blue mui-btn-link mui-pull-right';
				rightbtn.innerText = "回复";
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
		</script>

	</body>

</html>
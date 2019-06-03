<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/KinSlideshow/jquery.KinSlideshow-1.2.1.min.js"></script>
<script src="<%=contextPath %>/common/jquery/portlet/jquery-ui-1.9.2.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/lazyloader.js"></script>
<link rel="stylesheet" href="css/onee.css">
<link rel="stylesheet" href="css/resett.css">
<script src="<%=contextPath %>/common/ueditor/ueditor.config.js"></script>
<script src="<%=contextPath %>/common/ueditor/ueditor.all.min.js"></script>
<title>主界面</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style>
.main {
	width: 100%;
	height: 100%;
	margin: 5px;
}
.main-up {
	width: 100%;
	height: 38%;
}
.main-up > div {
	height: 100%;
	margin-bottom: 10px;
	display: inline-block;
}
.main-down {
	width: 100%;
	height: 59%;
}
.info {
	width: 30%;
	float: left;
	border: 1px solid #d1d1d1;
	border-radius:5px;
}
.notice {
	width: 40%;
	margin: 0 10px;
	border:1px solid #d1d1d1;
	border-radius:5px;
	float: left;
}
.noticeDate{
    width: 25%;
    text-align:center;
	float: right;
}
.pie {
	width: 26%;
	border:1px solid #d1d1d1;
	border-radius:5px;
	float: left;
}
.main-down > div {
	/* height: 100%; */
	display: inline-block;
} 
.moheader{
    width:100%;
    height:35px;
	line-height: 35px;
    display:inline-block;
	text-align: left;
	text-indent: 10px;
    border-bottom:1px solid #e8e8e8;
    background:#fbfbfb;
	color: #2d4c98;
}
.infoheader {
	text-align: center;
	text-indent: 0;
}
.downleft {
	width: 69%;
}
.downright {
	width: 30%;
}
.info div {
	width: 100%;
	float: left;
}
.infocontent li {
	height: 22px;
	margin: 0 5px;
}
.infocontent li :first-child{
	padding-left: 20px;
	line-height: 22px;
	font-size: 14px;
	float: left;
}
.infocontent li :last-child{
	padding-right: 20px;
	line-height: 22px;
	font-size: 14px;
	float: right;
	color: #6989FF;
}
.noticeUl li{
    list-style-type: square;
    margin-left: 27px;
    height: 25px;
    text-indent: -5px;
    line-height:25px;
    cursor:pointer;
}
.enter {
	height: 25%;
	position: relative;
	border:1px solid #d1d1d1;
	border-radius:5px;
	margin-bottom: 5px;
}
.enter div {
	position: absolute;
	top: 18px;
	left: 20px;
	width: 30px;
	height:30px;
	line-height: 18px;
	font-size: 15px;
	color: #2d4c98;
}
.enter ul {
	height: 70px;
	margin-left: 70px;
	display: table;
}
.enter ul li {
	width: 70px;
	height:70px;
	line-height: 70px;
	float: left;
	text-align: center;
}
.enter ul li span {
	margin-top:10px;
	display: block;
}
.enter ul li img {
	width: 35px;
	height:35px;
	line-height: normal;
}
.enter ul li p{
	line-height: normal;
}
.list {
	height: 70%;
	background: #6989FF;
	border-bottom:1px solid #e8e8e8;
}
.data {
	border-bottom:1px solid #e8e8e8;
}
</style>

</head>
<body>
	<div class="main">
		<div class="main-up">
			<div class="info">
				<p class="moheader infoheader">您好，李莹莹！您所在复议机关：深圳市人民政府</p>
				<ul class="infocontent">
					<li><span>预警</span><span>5</span></li>
					<li><span>预警</span><span>5</span></li>
					<li><span>预警</span><span>5</span></li>
					<li><span>预警</span><span>5</span></li>
					<li><span>预警</span><span>5</span></li>
					<li><span>预警</span><span>5</span></li>
					<li><span>预警</span><span>5</span></li>
				</ul>
			</div>
			<div class="notice">
					<p class="moheader">通知公告</p>
					<ul class="noticeUl" >
					   <li>【置顶】抓住规范行为的牛鼻子123123541345<span class="noticeDate">2019-02-10</span></li>
					   <li>【置顶】抓住规范行为的牛鼻子123123541345<span class="noticeDate">2019-02-10</span></li>
					   <li>【置顶】抓住规范行为的牛鼻子123123541345<span class="noticeDate">2019-02-10</span></li>
					   <li>【置顶】抓住规范行为的牛鼻子123123541345<span class="noticeDate">2019-02-10</span></li>
					   <li>【置顶】抓住规范行为的牛鼻子123123541345<span class="noticeDate">2019-02-10</span></li>
					   <li>【置顶】抓住规范行为的牛鼻子123123541345<span class="noticeDate">2019-02-10</span></li>
					</ul>
				</div>
			<div class="pie">
				<p class="moheader">受理情况统计</p>
			</div>
		</div>
		<div class="main-down">
			<div class="downleft">
				<div class="enter">
					<div>常用功能</div>
					<ul>
						<li><span><img src="./images/home_red.png" alt=""></span><p>案件登记</p></li>
						<li><span><img src="./images/home_yellow.png" alt=""></span><p>案件填报</p></li>
						<li><span><img src="./images/home_blue.png" alt=""></span><p>案件提取</p></li>
						<li><span><img src="./images/home_green.png" alt=""></span><p>案件提取</p></li>
						<li><span><img src="./images/home_plus.png" alt=""></span></li>
					</ul>
				</div>
				<div class="list">
					<p class="moheader">待办事项</p>
				</div>
			</div>
			<div class="downright">
				<div class="data"></div>
			</div>
		</div>
</body>
</html>

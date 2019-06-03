<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String inputName = request.getParameter("inputName");
	String hiddenUserId = request.getParameter("hiddenUserId");
	String single = request.getParameter("single");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>常用人</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">

		<link rel="stylesheet" href="css/mui.css">
		<style>
			html,
			body {
				background-color: #ffffff;
			}
			*{
				margin:0;
				padding:0;
				font-family: "Microsoft Yahei";
			}
			li{
				list-style: none;
			}
			p{
				margin-bottom: 0;
			}
			.hide{
				display: none;
			}
			.sortable-ghost {
				opacity: .2;
			}
			/*.sortable-drag {
				background: #daf4ff;
			}*/
			.mui-content {
    			background-color: #fff;
				padding-bottom: 44px;
				width: 100vw;
				height: 100vh;
    		}
			.footerBar{
				position: fixed;
				bottom: 0;
				left: 0;
				right: 0;
				height:44px;
				z-index: 2;
				background: #e1e1e1;
			}
			.mui-input-group .mui-input-row{
				height: auto;
			}
			.name{
				float: left;
				margin-left: 10px;
			}
			.depart{
				margin-left: 20px;
				float: left;
			}
			.name1{
				font-size: 14px;
				margin-bottom: 10px;
				max-width: 7em;
			    white-space: nowrap;
			    text-overflow: ellipsis;
			    overflow: hidden;
			}
			.name2{
				font-size: 14px;
				color: #999;
				max-width: 7em;
			    white-space: nowrap;
			    text-overflow: ellipsis;
			    overflow: hidden;
			}
			.depart1{
				font-size: 14px;
				color: #666666;
				margin-bottom: 10px;
			}
			.depart2{
				font-size: 14px;
				color: #999999;
			}
			.mui-checkbox input[type=checkbox], .mui-radio input[type=radio]{
				top: 18px;
			}
			.mui-card{
				margin: 0;
				box-shadow: none;
				/*border-bottom: 1px solid #eaeaea;*/
			}
			.mui-content>.mui-card:first-child {
			    margin-top: 0;
			    margin-bottom: 0;
			}
			.allPageBtn{
				background-image: url(images/pageList.png);
				background-repeat: no-repeat;
				background-position: left center;
				padding-left: 25px;
				line-height: 25px;
				background-size: 22px;
			}
			.allPageList{
				position: fixed;
				bottom: 50px;
				left: 20px;
				padding:5px;
				background-color: #fff;
				border-radius: 5px;
				border:1px solid #aeaeae;
			}
			.allPageList:before{
				content:"";
				border-top: 12px solid #aeaeae;
				border-left: 12px solid transparent;
				border-right: 12px solid transparent;
				position: absolute;
				position: absolute;
				bottom: -12px;
				left: 35px;
			}
			.allPageList:after{
				content:"";
				border-top: 12px solid #fff;
				border-left: 12px solid transparent;
				border-right: 12px solid transparent;
				position: absolute;
				bottom: -11px;
				left: 35px;
			}
			.allPageList li{
				line-height: 35px;
				border-top: 1px solid #AEAEAE;
				text-align: center;
			}
			.allPageList li a{
				display: block;
				padding:0 10px;
			}
			.allPageList li:first-child{
				border-top:none;
			}
			.footerList{
				display: flex;
				justify-content:space-around;
				padding:5px 0;
			}
			.footerList li{
				float: left;
				line-height: 34px;
    			font-size: 16px;
			}
			button.selectedPerson{
				background-color: #e1e1e1;
				border:1px solid #aeaeae;
				background-image: url(images/selected.png);
				background-repeat: no-repeat;
				background-position: 5px center;
				padding-left: 35px;
				background-size: 25px;
			}
			input[type=button].confirmBtn{
				background-color: #e1e1e1;
				border:1px solid #aeaeae;
				background-image: url(images/confirm_btn.png);
				background-repeat: no-repeat;
				background-position: 5px center;
				padding-left: 35px;
				background-size: 25px;
			}
			.searchBar{
				padding:5px 0;
				background-color: #d9d8d8;
			}
			input[type='text'].searchInput{
				width: 90%;
			    display: block;
			    margin: 0 auto;
			    padding: 0;
			    border-radius: 7px;
			    height: 30px;
			    font-size: 16px;
			    padding-left: 35px;
			    background-image: url(images/search.png);
			    background-position: 10px center;
			    background-repeat: no-repeat;
			    background-size: 20px;
			}
			.mui-input-row{
				position: relative;
			}
			.movePosition{
				width:30px;
				height: 30px;
				position: absolute;
				right: 10px;
				top: 15px;
				background-image: url(images/move.png);
				background-repeat: no-repeat;
				background-size: 30px;
				background-position: center;
			}
			.removeItem{
				background-color: #f00;
				color: #fff;
				width: 20px;
				height: 20px;
				position: absolute;
				top: 22px;
				left: 30px;
				border-radius: 50%;
				text-align: center;
				line-height: 20px;
				font-size: 35px;
			}
			.frequentGroupWrap{
				padding:10px;
			}
			.allPersonPageWrap{
				position: relative;
				height: 100%;
			}
			.searchBar{
				position: absolute;
				top: 0;
				left: 0;
				right: 0;
				height:40px;
				z-index: 1;
			}
			.searchContent{
				padding-top: 40px;
				width: 100%;
				height: 100%;
				overflow: auto;
			}
			.groupHeader{
			    overflow: hidden;
			}
			.GroupToggle{
				font-size: 20px;
			    font-weight: bold;
			    color: #017afd;
			    line-height: 25px;
			    float: left;
			}
			.groupName{
				margin-left: 20px;
			    line-height: 30px;
			}
			.groupBody {
				margin-left: 15px;
			}
			.groupBody .mui-checkbox.mui-left label, .mui-radio.mui-left label{
				padding-left:45px;
			}

			.organizationWrap{
				padding:10px;
			}
			.organizationHeader{
			    position: relative;
			}
			.selectedCount{
				display: inline-block;
				color: #969696;
				position: absolute;
				right: 35px;
				top: 5px;
				font-size: 14px;
			}
			.organizationName{
				font-size: 16px;
				padding-left: 35px;
			    background-image: url(images/orgTitle.png);
			    background-repeat: no-repeat;
			    background-position: 5px center;
			    background-size: 25px;
			    line-height: 30px;
				font-size: 14px;
				margin: 5px 0;
			}
			.organizationToggle{
				background-repeat: no-repeat;
				background-position: center;
				position: absolute;
				top: 5px;
				right: 15px;
				color: #969696;
				transition: all 0.5s;
			}
			.organizationToggle.opened{
				transform: rotateZ(90deg);
			}
			.organizationToggle.open{
				background-image: url(images/right.png);
			}
			.organizationToggle.close{
				background-image: url(images/down.png);
			}
			.organizationWrap .mui-checkbox input[type=checkbox], .mui-radio input[type=radio]{
				top:0px;
			}
			.organizationWrap .mui-input-group .mui-input-row{
				height: 40px;
			}
			.organizationWrap .mui-input-row{
				height:40px;
			}
			.organizationWrap .groupBody .mui-checkbox.mui-left label, .mui-radio.mui-left label{
				padding:0;
				padding-left: 45px;
			}
			.organizationWrap .groupBody .name,.organizationWrap .groupBody .depart{
				line-height: 30px;
			}

			.mui-scroll-wrapper{
				overflow: auto;
			}
			.mui-slider .mui-slider-group
			{
				width: 100%;
				height: 100%;
			}
			.mui-card .sortable-ghost {
				opacity: 0.4;
				background-color: #F4E2C9;
			}
			.selectedPersonWrap .movePosition{
			    -webkit-touch-callout:none;  /*系统默认菜单被禁用*/
			    -webkit-user-select:none; /*webkit浏览器*/
			    -khtml-user-select:none; /*早期浏览器*/
			    -moz-user-select:none;/*火狐*/
			    -ms-user-select:none; /*IE10*/
			    user-select:none;
			    -webkit-touch-callout: none; /* IOS 禁止长按链接与图片弹出菜单  */
			}
			.groupBody{
				display: none;
			}
			.getMore{
				line-height: 30px;
				text-align: center;
			}
			.tip{
				line-height: 25px;
				text-align: center;
				font: 20px;
				margin-top: 20px;
			}
		</style>
	</head>

	<body>
		<header id="header" class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">组织架构</h1>
		</header>
		<div class="footerBar">
			<ul class="footerList">
				<li class="allPageBtn">组织架构</li>
				<li>
					<a  class="mui-control-item" href="#item3mobile"><button class="selectedPerson">已选(<span class="selectedNum">0</span>)</button></a>
				</li>
				<li>
					<input class="confirmBtn" type="button" value="确定">
				</li>
			</ul>
			<ul class="hide allPageList">
				<!-- <li class="frequentContacts">
					<a class="mui-control-item" href="#item1mobile">常用人</a>
				</li> -->
				<li class="allPersonPage">
					<a class="mui-control-item" href="#item2mobile">所有人</a>
				</li>
				<li class="frequentGroup">
					<a class="mui-control-item" href="#item4mobile">常用组</a>
				</li>
				<li class="myDepart">
					<a class="mui-control-item" href="#item6mobile">同部门</a>
				</li>
				<li class="organization">
					<a class="mui-control-item" href="#item5mobile">组织架构</a>
				</li>
			</ul>
		</div>
		<div class="mui-content mui-slider">
			<div class="mui-slider-group ">
				<!-- 常用人 -->
				<!-- <div id="item1mobile" class="mui-slider-item mui-control-content mui-active">
					<div id="scroll1" class="mui-scroll-wrapper">
						<div class="mui-scroll">
							<div class="frequentContactsWrap">
								<div class="mui-card">
									<form class="mui-input-group">
										<div class="mui-input-row mui-checkbox mui-left">
											<label>
												<div class="name">
													<div class="name1">徐双玉</div>
													<div class="name2">中腾科技</div>
												</div>
												<div class="depart">
													<div class="depart1">系统管理员</div>
													<div class="depart2">系统管理部</div>
												</div>
											</label>
											<input class="leftCheckbox " name="checkbox" value="person" type="checkbox" >
										</div>
										<div class="mui-input-row mui-checkbox mui-left">
											<label>
												<div class="name">
													<div class="name1">徐双玉</div>
													<div class="name2">中腾科技</div>
												</div>
												<div class="depart">
													<div class="depart1">系统管理员</div>
													<div class="depart2">系统管理部</div>
												</div>
											</label>
											<input class="leftCheckbox " name="checkbox" value="person" type="checkbox" >
										</div>
										<div class="mui-input-row mui-checkbox mui-left">
											<label>
												<div class="name">
													<div class="name1">徐双玉</div>
													<div class="name2">中腾科技</div>
												</div>
												<div class="depart">
													<div class="depart1">系统管理员</div>
													<div class="depart2">系统管理部</div>
												</div>
											</label>
											<input class="leftCheckbox " name="checkbox" value="person" type="checkbox" >
										</div>
										<div class="mui-input-row mui-checkbox mui-left">
											<label>
												<div class="name">
													<div class="name1">徐双玉</div>
													<div class="name2">中腾科技</div>
												</div>
												<div class="depart">
													<div class="depart1">系统管理员</div>
													<div class="depart2">系统管理部</div>
												</div>
											</label>
											<input class="leftCheckbox " name="checkbox" value="person" type="checkbox" >
										</div>
										<div class="mui-input-row mui-checkbox mui-left">
											<label>
												<div class="name">
													<div class="name1">徐双玉</div>
													<div class="name2">中腾科技</div>
												</div>
												<div class="depart">
													<div class="depart1">系统管理员</div>
													<div class="depart2">系统管理部</div>
												</div>
											</label>
											<input class="leftCheckbox " name="checkbox" value="person" type="checkbox" >
										</div>
										<div class="mui-input-row mui-checkbox mui-left">
											<label>
												<div class="name">
													<div class="name1">徐双玉</div>
													<div class="name2">中腾科技</div>
												</div>
												<div class="depart">
													<div class="depart1">系统管理员</div>
													<div class="depart2">系统管理部</div>
												</div>
											</label>
											<input class="leftCheckbox " name="checkbox" value="person" type="checkbox" >
										</div>

										<div class="mui-input-row mui-checkbox mui-left">
											<label>
												<div class="name">
													<div class="name1">徐双玉</div>
													<div class="name2">中腾科技</div>
												</div>
												<div class="depart">
													<div class="depart1">系统管理员</div>
													<div class="depart2">系统管理部</div>
												</div>
											</label>
											<input class="leftCheckbox " name="checkbox" value="person" type="checkbox" >
										</div>
										<div class="mui-input-row mui-checkbox mui-left">
											<label>
												<div class="name">
													<div class="name1">徐双玉</div>
													<div class="name2">中腾科技</div>
												</div>
												<div class="depart">
													<div class="depart1">系统管理员</div>
													<div class="depart2">系统管理部</div>
												</div>
											</label>
											<input class="leftCheckbox " name="checkbox" value="person" type="checkbox" >
										</div>
										<div class="mui-input-row mui-checkbox mui-left">
											<label>
												<div class="name">
													<div class="name1">徐双玉</div>
													<div class="name2">中腾科技</div>
												</div>
												<div class="depart">
													<div class="depart1">系统管理员</div>
													<div class="depart2">系统管理部</div>
												</div>
											</label>
											<input class="leftCheckbox " name="checkbox" value="person" type="checkbox" >
										</div>
										<div class="mui-input-row mui-checkbox mui-left">
											<label>
												<div class="name">
													<div class="name1">徐双玉</div>
													<div class="name2">中腾科技</div>
												</div>
												<div class="depart">
													<div class="depart1">系统管理员</div>
													<div class="depart2">系统管理部</div>
												</div>
											</label>
											<input class="leftCheckbox " name="checkbox" value="person" type="checkbox" >
										</div>
										<div class="mui-input-row mui-checkbox mui-left">
											<label>
												<div class="name">
													<div class="name1">徐双玉</div>
													<div class="name2">中腾科技</div>
												</div>
												<div class="depart">
													<div class="depart1">系统管理员</div>
													<div class="depart2">系统管理部</div>
												</div>
											</label>
											<input class="leftCheckbox " name="checkbox" value="person" type="checkbox" >
										</div>
										<div class="mui-input-row mui-checkbox mui-left">
											<label>
												<div class="name">
													<div class="name1">徐双玉</div>
													<div class="name2">中腾科技</div>
												</div>
												<div class="depart">
													<div class="depart1">系统管理员</div>
													<div class="depart2">系统管理部</div>
												</div>
											</label>
											<input class="leftCheckbox " name="checkbox" value="person" type="checkbox" >
										</div>
										<div class="mui-input-row mui-checkbox mui-left">
											<label>
												<div class="name">
													<div class="name1">徐双玉</div>
													<div class="name2">中腾科技</div>
												</div>
												<div class="depart">
													<div class="depart1">系统管理员</div>
													<div class="depart2">系统管理部</div>
												</div>
											</label>
											<input class="leftCheckbox " name="checkbox" value="person" type="checkbox" >
										</div>
										<div class="mui-input-row mui-checkbox mui-left">
											<label>
												<div class="name">
													<div class="name1">徐双玉</div>
													<div class="name2">中腾科技</div>
												</div>
												<div class="depart">
													<div class="depart1">系统管理员</div>
													<div class="depart2">系统管理部</div>
												</div>
											</label>
											<input class="leftCheckbox " name="checkbox" value="person" type="checkbox" >
										</div>
										<div class="mui-input-row mui-checkbox mui-left">
											<label>
												<div class="name">
													<div class="name1">徐双玉</div>
													<div class="name2">中腾科技</div>
												</div>
												<div class="depart">
													<div class="depart1">系统管理员</div>
													<div class="depart2">系统管理部</div>
												</div>
											</label>
											<input class="leftCheckbox " name="checkbox" value="person" type="checkbox" >
										</div>
										<div class="mui-input-row mui-checkbox mui-left">
											<label>
												<div class="name">
													<div class="name1">徐双玉</div>
													<div class="name2">中腾科技</div>
												</div>
												<div class="depart">
													<div class="depart1">系统管理员</div>
													<div class="depart2">系统管理部</div>
												</div>
											</label>
											<input class="leftCheckbox " name="checkbox" value="person" type="checkbox" >
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div> -->
				<!-- 组织架构 -->
				<div id="item5mobile" class="mui-slider-item mui-control-content mui-active">
					<div id="scroll5" class="mui-scroll-wrapper">
						<div class="mui-scroll">
							<div class="hide mui-loading">
								<div class="mui-spinner">
								</div>
							</div>
							<div class="organizationWrap"></div>
						</div>
					</div>
				</div>
				<!-- 所有人 -->
				<div id="item2mobile" class="mui-slider-item mui-control-content">
					<div id="scroll2" class="mui-scroll-wrapper">
						<div class="allPersonPageWrap mui-scroll">
							<div class="searchBar">
								<input class="searchInput" type="text" placeholder="请输入姓名、手机号、首字母查找">
							</div>
							<div class="searchContent">
								<div class="hide mui-loading">
									<div class="mui-spinner">
									</div>
								</div>
								<div class="mui-card">
									<form class="mui-input-group"></form>
								</div>
							</div>

						</div>

					</div>

				</div>
				<!-- 已选-->
				<div id="item3mobile" class="mui-slider-item mui-control-content">
					<div id="scroll3" class="mui-scroll-wrapper">
						<div class="mui-scroll">
							<div class="hide mui-loading">
								<div class="mui-spinner">
								</div>
							</div>
							<div class="selectedPersonWrap">
								<div class="mui-card">
									<form class="mui-input-group">
										<p class="tip">无选择项</p>
									</form>
								</div>
							</div>
						</div>
					</div>

				</div>
				<!-- 常用组 -->
				<div id="item4mobile" class="mui-slider-item mui-control-content">
					<div id="scroll4" class="mui-scroll-wrapper">
						<div class="mui-scroll">
							<div class="hide mui-loading">
								<div class="mui-spinner">
								</div>
							</div>
							<div class="frequentGroupWrap" id="frequentGroupWrap"></div>
						</div>
					</div>

				</div>

				<!-- 同部门 -->
				<div id="item6mobile" class="mui-slider-item mui-control-content">
					<div id="scroll1" class="mui-scroll-wrapper">
						<div class="mui-scroll">
							<div class="sameDeptWrap">
								<div class="mui-card">
									<form class="mui-input-group"></form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<script src="https://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
		<script src="js/mui.min.js"></script>
		<script type="text/javascript" src="js/Sortable.min.js"></script>
		<script type="text/javascript">
			var INPUTNAME = "<%= inputName %>";
			var HIDDENUSERID = "<%= hiddenUserId %>";
			var selectedId = $("#"+HIDDENUSERID,parent.document).val();
			var SELECTED = selectedId ? selectedId.split(",") : [];
			var timer;
			var single = "<%= single %>";
			if(INPUTNAME==HIDDENUSERID){
				$('#'+HIDDENUSERID, window.parent.document).val("");
				$('#'+INPUTNAME, window.parent.document).val("");
			}

			mui.init({
				swipeBack: false
			});
			creatNav();
			function creatNav(){
				var header = document.getElementById("header");
				 //左侧图标+文字按钮
				var leftbtn = document.createElement('button');
				leftbtn.className = 'mui-btn mui-btn-blue mui-btn-link mui-btn-nav mui-pull-left';
				var span = document.createElement('span');
				span.className = 'mui-icon mui-icon-left-nav';
				leftbtn.appendChild(span);
				var text = document.createTextNode('取消');
				leftbtn.appendChild(text);
				leftbtn.addEventListener("tap",function(){
					setTimeout(function(){
						$('#selectUserIframe', window.parent.document).hide();
					},300);
				});

				remove('.mui-pull-left');
				header.appendChild(leftbtn);
			}

			 //删除原先存在的导航栏节点
			function remove(selector) {
				var elem = header.querySelector(selector);
				if (elem) {
					header.removeChild(elem);
				}
			}
			(function($) {
				/*左右滚动显示滚动条*/
				$('.mui-scroll-wrapper').scroll({
					indicators: false //是否显示滚动条
				});
				getOrgDepAll();
			})(mui);
			$(function(){

				selectedCallback(SELECTED.toString());
				/*点击空白弹出框消失*/
				$(document).on("tap",function(){
					$(".allPageList").hide();
				})
				/*点击按钮显示切换页*/
				$(".allPageBtn").on("tap",function(e){
					$(".allPageList").toggle();
					e.stopPropagation();
				})
				/*常用组和组织机构  点击切换子菜单*/
				$(".frequentGroupWrap,.organizationWrap").on("tap",".groupHeader",function(){
					//$(this).siblings('.groupBody').slideToggle("fast");
					var showNext = $(this).children(".organizationToggle");//下拉图标
					if(showNext.length){
						showNext.toggleClass('opened');
					}
					var showIcon = $(this).children(".GroupToggle");//下拉图标
					if(showIcon.length){
						if(showIcon.hasClass('open')){
							showIcon.removeClass('open');
							showIcon.text("+");
						}else{
							showIcon.addClass('open');
							showIcon.text("-");
						}
					}
				});
				$(".frequentGroupWrap").on("tap",".groupHeader",function(){
					var id = $(this).attr("gpId");
					renderFrequentGroupContent(id);
				});
				$(".organizationWrap").on("tap",".groupHeader",function(){
					var id = $(this).attr("gpId");
					renderOrgDepPerson(id);
				});
				/*已选人员*/
				$(".selectedPerson").on("tap",function(){
					$("#header").find(".mui-title").text("已选人员");
				});

				/*已选移除人员*/
				$(".selectedPersonWrap").on("tap",".removeItem",function(){
					var that = $(this);
					var uuid = that.closest('.mui-input-row').attr("uuid");
					SELECTED.splice($.inArray(uuid,SELECTED),1);
					that.closest('.mui-input-row').remove();
					if($(".selectedPersonWrap").find('.mui-input-row').length == 0){
						$(".selectedPersonWrap").find(".tip").show();
					}
					var curSelected = $(".selectedNum").text();
					$(".mui-input-row[uuid="+uuid+"]").find(".leftCheckbox").removeAttr('checked');
					setSelectedNumber();
				});
				/*点击确定执行回调*/
				$(".confirmBtn").on("tap",function(){
					var selected = $(".selectedPersonWrap").find(".mui-input-row");
					var uuidArr = [];
					var userNamesArr = [];
					for(var i=0,l=selected.length;i<l;i++){
						var uuid = $(selected[i]).attr("uuid");
						var userName = $(selected[i]).find('.name1').text();
						uuidArr.push(uuid);
						userNamesArr.push(userName);
					}
					$('#'+HIDDENUSERID, window.parent.document).val(uuidArr.join(","));
					$('#'+INPUTNAME, window.parent.document).val(userNamesArr.join(","));
					setTimeout(function(){
						$('#selectUserIframe', window.parent.document).hide();
					},300);
				});
				/* 拖动排序*/
				var el = $('.selectedPersonWrap .mui-input-group')[0];
				new Sortable(el, {
				    group: { name: "mui-input-row", pull: true, put: true},
				    store: null, //   @see Store
				    handle: ".movePosition", // 点击目标元素约束开始
				    draggable: ".mui-input-row",   // 指定那些选项需要排序
				    ghostClass: "sortable-ghost",
				    chosenClass: "sortable-chosen",
				    animation: 150,
				    onStart: function (/**Event*/evt) { // 拖拽
				        var itemEl = evt.item;
				    },

				    onEnd: function (/**Event*/evt) { // 拖拽
				        var itemEl = evt.item;
				    },

				    onAdd: function (/**Event*/evt){
				        var itemEl = evt.item;
				    },

				    onUpdate: function (/**Event*/evt){
				        var itemEl = evt.item; // 当前拖拽的html元素
				    },

				    onRemove: function (/**Event*/evt){
				        var itemEl = evt.item;
				    }
				});
				/*长按禁止显示系统菜单*/
				$(".selectedPersonWrap").on("touchstart ",".movePosition",function(e){
					e.preventDefault();
				});
				/*滑动切换标题*/
				document.querySelector('.mui-slider').addEventListener('slide', function(event) {
				  //注意slideNumber是从0开始的；
				  var index = event.detail.slideNumber;
				 //  if(index === 0){
					// 	$("#header").find(".mui-title").text("常用人");
					// }else
				    if(index === 0){
						$("#header").find(".mui-title").text("组织架构");
						$(".footerList").find(".allPageBtn").text("组织架构");
					}else if(index === 1){
						$("#header").find(".mui-title").text("所有人");
						$(".footerList").find(".allPageBtn").text("所有人");
					}else if(index === 2){
						$("#header").find(".mui-title").text("已选人员");
						$(".footerList").find(".allPageBtn").text("已选人员");
					}else if(index === 3){
						$("#header").find(".mui-title").text("常用组");
						$(".footerList").find(".allPageBtn").text("常用组");
						renderFrequentGroupWrap();
					}else  if(index === 4){
						$("#header").find(".mui-title").text("同部门");
						$(".footerList").find(".allPageBtn").text("同部门");
						renderSameDept();
					}
				});

				$(document).on("change","input.leftCheckbox",function(){
					var item = {};
					item.userName = $(this).attr("userName");
					item.orgName = $(this).attr("orgName");
					item.uuid = $(this).attr("uuid");
					item.userRoleStrName = $(this).attr("userRoleStrName");
					item.deptIdName = $(this).attr("deptIdName");

					if($(this).prop("checked") ){//选中添加
						if(single == "true"){
							$(".selectedPersonWrap").find(".removeItem").trigger('tap');
						}
						renderSelectedPerson(item);

						$(".mui-input-row[uuid="+item.uuid+"]").find(".leftCheckbox").prop("checked","checked");
					}else{//取消选中
						$(".selectedPersonWrap .mui-input-row[uuid="+item.uuid+"]").remove();//移除已选组里的人员
						SELECTED.splice($.inArray(item.uuid,SELECTED),1);
						$(".mui-input-row[uuid="+item.uuid+"]").find(".leftCheckbox").removeAttr('checked');
						setSelectedNumber();
					}
				});

				$(".searchInput").on("keyup",function(){
					var that = this;
					timer = new Date().getTime();
					setTimeout(function(){
			            //0.5秒后比较当前时间和最后一次搜索框值改变时的时间的差值
			            //只有大于等于setTimeout的间隔才调取接口
			            if(new Date().getTime() - timer >= 500){
			            	var keyValue = $(that).val();
			            	if(keyValue == ""){
			            		return;
			            	}
			                renderAllPeople(keyValue);
			            }
			        },500)
				})

			})




			/**
			 * 渲染已选
			 */
			 function renderSelectedPerson(item) {
			 	if($(".selectedPersonWrap .mui-input-row[uuid="+item.uuid+"]").length > 0){
			 		return;
			 	}
			 	var html = [];
			 	html.push('<div uuid="' + item.uuid + '" class="mui-input-row mui-checkbox mui-left"><div class="removeItem">-</div><label for="AllPeople-'+item.uuid+'"><div class="name">');
				html.push('<div class="name1">'+item.userName+'</div>');
				html.push('<div class="name2">'+item.orgName +'</div>');
				html.push('</div><div class="depart">');
				html.push('<div class="depart1">'+item.userRoleStrName +'</div>');
				html.push('<div class="depart2">'+ item.deptIdName +'</div>');
				html.push('</div></label>');
				html.push('<div class="movePosition"></div>');
				html.push('</div>');
				$(".selectedPersonWrap").find(".tip").hide();
				$(".selectedPersonWrap").find(".mui-input-group").append(html.join(""));
				SELECTED.push(parseInt(item.uuid));
				setSelectedNumber();
			 }

			 /**
			  * 获取选中人员的个数
			  */
			 function setSelectedNumber(){
			 	var len = $(".selectedPersonWrap").find(".mui-input-row").length;
			 	$(".selectedNum").text(len);
			 	return len;
			 }
			/**
			 * 渲染所有人
			 * @param      {<type>}  value   The value
			 */
			function renderAllPeople(value){
				var loadingBox = $('<div class="mui-loading"><div class="mui-spinner"></div></div>');
				$(".searchContent").find(".mui-input-group").html(loadingBox);

				var keyWord = value ? value : '';
				$.ajax({
					url: "/orgSelectManager/getSelectUserByUserIdOrUserName.action",
					type: "post",
					data:{
						user : keyWord
					},
					dataType:"json",
					success:function(data, textStatus){
						if(data.rtState){
							var rtData = data.rtData;
							for(var i = 0,l = rtData.length;i<l;i++){
								var html = [];
								html.push('<div uuid="' + rtData[i].uuid + '" class="mui-input-row mui-checkbox mui-left"><label for="AllPeople-'+rtData[i].uuid+'"><div class="name">');
								html.push('<div class="name1">'+rtData[i].userName+'</div>');
								html.push('<div class="name2">'+rtData[i].orgName +'</div>');
								html.push('</div><div class="depart">');
								html.push('<div class="depart1">'+rtData[i].userRoleStrName +'</div>');
								html.push('<div class="depart2">'+ rtData[i].deptIdName +'</div>');
								html.push('</div></label>');
								html.push('<input class="leftCheckbox" '+ ($.inArray(rtData[i].uuid, SELECTED) > -1  ? 'checked="checked"' : "") +' uuid="'+rtData[i].uuid+'" userName="'+rtData[i].userName+'" orgName="'+rtData[i].orgName+'" userRoleStrName="'+rtData[i].userRoleStrName+'" deptIdName="'+rtData[i].deptIdName+'"  id="AllPeople-'+rtData[i].uuid+'" name="checkbox" value="person" type="checkbox" >');
								html.push('</div>');
								$(".searchContent").find(".mui-input-group").append(html.join(""));
							}
							$(".searchContent").find(".mui-loading").remove();
						}
					},
					error:function(XMLHttpRequest, textStatus, errorThrown){
						if(textStatus){
							console.log(textStatus);
						}
					}
				})
			}
			/**
			 * 同部门
			 */
			function renderSameDept(){
				if($(".sameDeptWrap").find(".mui-input-group").children().length){
					return;
				}
				var loadingBox = $('<div class="mui-loading"><div class="mui-spinner"></div></div>');
				$(".sameDeptWrap").find(".mui-input-group").html(loadingBox);
				$.ajax({
					url: "/orgSelectManager/getSelectUserByCurrentDept.action",
					type: "post",
					data:{
					},
					dataType:"json",
					success:function(data, textStatus){
						if(data.rtState){
							var rtData = data.rtData;
							var html = [];
							for(var i = 0,l = rtData.length;i<l;i++){
								html.push('<div uuid="' + rtData[i].uuid + '" class="mui-input-row mui-checkbox mui-left"><label for="SameDept-'+rtData[i].uuid+'"><div class="name">');
								html.push('<div class="name1">'+rtData[i].userName+'</div>');
								html.push('<div class="name2">'+rtData[i].orgName +'</div>');
								html.push('</div><div class="depart">');
								html.push('<div class="depart1">'+rtData[i].userRoleStrName +'</div>');
								html.push('<div class="depart2">'+ rtData[i].deptIdName +'</div>');
								html.push('</div></label>');
								html.push('<input '+($.inArray(rtData[i].uuid, SELECTED)  > -1  ? 'checked="checked"' : "" )+' class="leftCheckbox" uuid="'+rtData[i].uuid+'" userName="'+rtData[i].userName+'" orgName="'+rtData[i].orgName+'" userRoleStrName="'+rtData[i].userRoleStrName+'" deptIdName="'+rtData[i].deptIdName+'"  id="AllPeople-'+rtData[i].uuid+'" id="SameDept-'+rtData[i].uuid+'" name="checkbox" value="person" type="checkbox" >');
								html.push('</div>');
								$(".sameDeptWrap").find(".mui-input-group").append(html.join(""));
							}
							$(".sameDeptWrap").find(".mui-loading").remove();
						}
					},
					error:function(XMLHttpRequest, textStatus, errorThrown){
						if(textStatus){
							console.log(textStatus);
						}
					}
				})
			}

			/*数据渲染
			页面加载质只渲染常用人
			*/
			function renderFrequentPerson(){

			}
			/**
			 * 获取常用组的所有组框架
			 * @return {[type]} [description]
			 */
			function renderFrequentGroupWrap(){
				if($("#frequentGroupWrap").children().length){
					return;
				}
				$.ajax({
					url: "/userGroup/getPublicAndPersonalUserGroup.action",
					type: "post",
					data:{

					},
					dataType:"json",
					success:function(data, textStatus){
						if(data.rtState){
							var rtData = data.rtData;
							var html = [];
							for(var i = 0,l = rtData.length;i<l;i++){
								html.push('<div class="group">');
									html.push('<div gpId="' + rtData[i].uuid +'" class="groupHeader FrequentGroup-G'+rtData[i].uuid+'">');
										html.push('<p class="GroupToggle">+</p>');
										html.push('<p class="groupName">' + rtData[i].groupName + '</p>');
									html.push('</div>');
									html.push('<div class="groupBody"></div>');
								html.push('</div>');
							}
							$("#frequentGroupWrap").html(html.join(""));
						}
					},
					error:function(XMLHttpRequest, textStatus, errorThrown){
						if(textStatus){
							console.log(textStatus);
						}
					}
				})
			}
			/**
			 * 获取常用组内部人员
			 */
			function renderFrequentGroupContent (groupId) {
				if($(".FrequentGroup-G"+groupId).siblings(".groupBody").children().length){
					$(".FrequentGroup-G"+groupId).siblings(".groupBody").slideToggle("fast");
					return;
				}
				var loadingBox = $('<div class="mui-loading"><div class="mui-spinner"></div></div>');
				$(".FrequentGroup-G"+groupId).siblings(".groupBody").append(loadingBox);
				$(".FrequentGroup-G"+groupId).siblings(".groupBody").show();
				$.ajax({
					url: "/orgSelectManager/getPersonByUserGroup.action",
					type: "post",
					data:{
						userGroupId : groupId
					},
					dataType:"json",
					success:function(data, textStatus){
						if(data.rtState){
							var rtData = data.rtData;
							var html = [];
							for(var i = 0,l=rtData.length;i<l;i++){
								html.push('<div uuid="' + rtData[i].uuid + '" class="mui-input-row mui-checkbox mui-left"><label for="FrequentGroupContent-'+rtData[i].uuid+'"><div class="name">');
								html.push('<div class="name1">'+rtData[i].userName+'</div>');
								html.push('<div class="name2">'+rtData[i].orgName +'</div>');
								html.push('</div><div class="depart">');
								html.push('<div class="depart1">'+rtData[i].userRoleStrName +'</div>');
								html.push('<div class="depart2">'+ rtData[i].deptIdName +'</div>');
								html.push('</div></label>');
								html.push('<input '+ ($.inArray(rtData[i].uuid, SELECTED) > -1 ? 'checked="checked"' : "") +' class="leftCheckbox" uuid="'+rtData[i].uuid+'" userName="'+rtData[i].userName+'" orgName="'+rtData[i].orgName+'" userRoleStrName="'+rtData[i].userRoleStrName+'" deptIdName="'+rtData[i].deptIdName+'"  id="AllPeople-'+rtData[i].uuid+'" id="FrequentGroupContent-'+rtData[i].uuid+'" name="checkbox" value="person" type="checkbox" >');
								html.push('</div>');
							}
							$(".FrequentGroup-G"+groupId).siblings(".groupBody").append(html.join(""));
							$(".FrequentGroup-G"+groupId).siblings(".groupBody").find(".mui-loading").remove();
							$(".FrequentGroup-G"+groupId).siblings(".groupBody").slideDown("fast");
						}
					},
					error:function(XMLHttpRequest, textStatus, errorThrown){
						if(textStatus){
							console.log(textStatus);
						}
					}
				})
			}

			/**
			 * 获取组织架构所有的部门目录
			 */
			function getOrgDepAll(){
				if($(".organizationWrap").children().length > 0){
					return;
				}
				$.ajax({
					url: "/orgSelectManager/getSelectDeptTreeAll.action",
					type: "post",
					data:{

					},
					dataType:"json",
					success:function(data, textStatus){
						if(data.rtState){
							var rtData = data.rtData;
							var treeData = toTreeData(rtData,"id","pId","child");
							var html = renderOrgDep(treeData)
							$(".organizationWrap").html(html);
						}
					},
					error:function(XMLHttpRequest, textStatus, errorThrown){
						if(textStatus){
							console.log(textStatus);
						}
					}
				})
			}

			/*组织机构渲染人员*/
			function renderOrgDepPerson(deptId){
				if($(".organization-G" + deptId).hasClass('rendered')){
					$(".organization-G"+deptId).siblings(".groupBody").slideToggle("fast");
					return;
				}
				var loadingBox = $('<div class="mui-loading"><div class="mui-spinner"></div></div>');
				$(".organization-G"+deptId).siblings(".groupBody").append(loadingBox);
				$(".organization-G"+deptId).siblings(".groupBody").show();
				$.ajax({
					url: "/orgSelectManager/getSelectUserByDept.action",
					type: "post",
					data:{
						deptId: deptId
					},
					dataType:"json",
					success:function(data, textStatus){
						if(data.rtState){
							var rtData = data.rtData;
							var html = [];
							for(var i = 0,l=rtData.length;i<l;i++){
								html.push('<div uuid="' + rtData[i].uuid + '" class="mui-input-row mui-checkbox mui-left"><label for="OrgDepPerson-'+rtData[i].uuid+'"><div class="name">');
								html.push('<div class="name1">'+rtData[i].userName+'</div>');
								html.push('<div class="name2">'+rtData[i].orgName +'</div>');
								html.push('</div><div class="depart">');
								html.push('<div class="depart1">'+rtData[i].userRoleStrName +'</div>');
								html.push('<div class="depart2">'+ rtData[i].deptIdName +'</div>');
								html.push('</div></label>');
								html.push('<input '+ ($.inArray(rtData[i].uuid, SELECTED) > -1 ? 'checked="checked"' : "") +' class="leftCheckbox" uuid="'+rtData[i].uuid+'" userName="'+rtData[i].userName+'" orgName="'+rtData[i].orgName+'" userRoleStrName="'+rtData[i].userRoleStrName+'" deptIdName="'+rtData[i].deptIdName+'"  id="AllPeople-'+rtData[i].uuid+'" id="OrgDepPerson-'+rtData[i].uuid+'" name="checkbox" value="person" type="checkbox" >');
								html.push('</div>');
							}
						$(".organization-G" + deptId).siblings(".groupBody").prepend(html.join(""));
						$(".organization-G"+deptId).addClass('rendered');
						$(".organization-G"+deptId).siblings(".groupBody").find(".mui-loading").remove();
						$(".organization-G"+deptId).siblings(".groupBody").slideDown("fast");
						}
					},
					error:function(XMLHttpRequest, textStatus, errorThrown){
						if(textStatus){
							console.log(textStatus);
						}
					}
				})
			}

			/**
			 * 一维数组转化为树
			 */
			var toTreeData = function (jsonData, id, pid, child) {
				var tree = [];
		        var hash = {};
		        var newTree = [];
		        for (var i = 0; i < jsonData.length; i++) {
		            hash[jsonData[i][id]] = jsonData[i];
		        }
		        for (var j = 0; j < jsonData.length; j++) {
		            var aVal = jsonData[j],
		                hashVP = hash[aVal[pid]];
		            if (hashVP) {
		                !hashVP[child] && (hashVP[child] = []);
		                hashVP[child].push(aVal);
		            } else {
		                tree.push(aVal);
		            }
		        }
		        return tree;
		    };
		    /**
		     *  递归渲染组织架构的部门结构
		     *
		     * @param      {Array}  treeData  The tree data
		     * @return     {Array}   { description_of_the_return_value }
		     *
		     */
		    function renderOrgDep(treeData){
		    	var html = [];
		    	for(var i = 0,l = treeData.length;i<l;i++){
		    		html.push('<div class="group">');
		    			html.push('<div gpId="' + treeData[i].id +'" class="organizationHeader groupHeader organization-G'+treeData[i].id+'">');
		    				html.push('<div class="organizationName">' + treeData[i].name + '</div>');
			    			// html.push('<div class="selectedCount">');
			    			// 	html.push('<span class="selectedNumber">2</span>/<span class="totalNumber">3</span>');
			    			// html.push('</div>');
		    				html.push('<span class="organizationToggle">></span>');
		    			html.push('</div>');
		    			html.push('<div class="groupBody">');
		    			if( "undefined" != typeof(treeData[i].child) ){
		    				html.push(renderOrgDep(treeData[i].child));
		    			}
		    			html.push('</div>');
		    		html.push('</div>');
		    	}
		    	return html.join("");
		    }

		    /**
		     * 已选人员回填
		     */
		    function selectedCallback(selected){
		    	$.ajax({
		    		url: "/personManager/getPersonListByUuids.action",
		    		type: "post",
		    		data:{
		    			uuid: selected
		    		},
		    		dataType:"json",
		    		success:function(data, textStatus){
		    			if(data.rtState){
							var rtData = data.rtData;
							for(var i = 0,l=rtData.length;i<l;i++){
								renderSelectedPerson(rtData[i]);
							}
						}
		    		},
		    		error:function(XMLHttpRequest, textStatus, errorThrown){
		    			if(textStatus){
		    				console.log(textStatus);
		    			}
		    		}
		    	})
		    }
		</script>

	</body>

</html>
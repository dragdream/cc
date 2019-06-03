<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html" charset="utf-8" />
	<title>我的关注</title>
	<%@ include file="/header/header2.0.jsp"%>
	<link rel="stylesheet" href="dist/css/blog1.css">
	<style>
	     html{
	         background: #dbdbdb
	     }
		.myFollow{
			width: 600px;
			margin:0 auto;
			padding:10px 15px;
			background-color: #fff;
			padding-bottom: 20px;
		}
		.followHeader{
			border-bottom: 1px solid rgba(211,211,211);
			padding-bottom:10px;
		}
		.followSearch{
			float: right;
			padding:2px 5px;
			padding-right: 20px;
			background-image: url(dist/images/searchIcon.png);
			background-repeat: no-repeat;
			background-position: right center;
			outline: none;
			border:1px solid #aaa;
		}
		.othersFollow{
			float: right;
			font-size: 14px;
			margin-left: 10px;
			color: #0099d0;
		}
		.followBody{
			padding:10px 0;
		}
		.followItem {
			padding:10px 0;
			border-top: 1px solid rgba(211,211,211);
		}
		.followItem:first-child{
			border-top: none;
		}
		.followItem .followAvatar{
			float:left;
			width: 48px;
			height: 48px;
			border-radius: 50%;
			overflow: hidden;
		}
		.followItem .info{
			margin-left:10px;
			display: inline-block;
			font-size: 14px;
		}
		.followItem .info .role{
			margin-top: 10px;
		}
		.btns{
			float: right;
			position: relative;
			margin-top:10px;
		}
		.btns input{
			outline: none;
		}
		.btns input.followBtn{
			background-color: #fff;
			border:1px solid #aaa;
			padding:5px 5px;
			padding-left: 25px;
			color: #aaa;
			background-image: url(dist/images/followEathoter.png);
			background-repeat: no-repeat;
			background-position: 5px center;
			cursor: pointer;
		}
		.btns input.toFollowed{
			background-color: #fff;
			border:1px solid #aaa;
			padding:5px 10px;
			cursor: pointer;
			color: #aaa;
		}
		.btns input.moreBtn{
			background-color: #fff;
			border:1px solid #aaa;
			padding:5px 10px;
			cursor: pointer;
			color: #aaa;
			margin-left: 10px;
		}
		.btns ul.moreList{
			position: absolute;
			top: 35px;
			right: 0;
			background-color: #e8e6e6;
			padding:0 10px;
			display: none;
		}
		.btns ul.moreList li{
			color: #8a8686;
			padding:5px 8px;
			font-size: 14px;
			cursor: pointer;
		}
		.followPersonName{
			color: #0099d0;
			margin-right: 10px;
		}
		.btns ul.moreList li:hover{
			background-color: #eae8e8;
		}
	</style>
	<script type="text/javascript">
	
	function doInit(){
		fansList();
		$(".btns .moreBtn").on("click",function(e){
			$(this).siblings('.moreList').toggle();
			e.stopPropagation();
		});

		$(document).on("click",function(){
			$('.moreList').hide();
		});
		
	}
	function fansList(){
		var url = "<%=contextPath%>/TeeWeibGuanZhuController/findFansAll.action";
		var json = tools.requestJsonRs(url);
		var rows=json.rows;
		if(rows!=null){
			var html="";
			for(var i=0;i<rows.length;i++){
				html+="<div class='followItem'>";
				  if(rows[i].avatar>0){
					   html+="<img src='<%=contextPath %>/attachmentController/downFile.action?id="+rows[i].avatar+"' style='width:48px;height:48px;'>";
				   }else{
					   html+="<img src='dist/images/blog.png' class='followAvatar'>";
				   }
				  html+="<div class='info'>";
				    html+="<p><a class='followPersonName' href='javascript:void(0)'>"+rows[i].userName+"</a><span class='depart'>"+rows[i].deptIdName+"</span></p>";
				    html+="<p class='role'>"+rows[i].userRoleStrName+"</p>";
				  html+="</div>";
				  html+="<div class='btns'>";
				     if(rows[i].fowllows){
				       html+="<input class='followBtn' type='button' value='互相关注'>";
				     }
				     html+="<input type='button' value='更多' class='moreBtn'>";
				     html+="<ul class='moreList' style='width: 45px;box-shadow: 0 0 10px #999;background-color:white;z-index: 1;'>";
				       if(!rows[i].fowllows){
				         html+="<li onclick='addGuanZhu("+rows[i].uuid+")'>关注</li>";
				       }
				       html+="<li onclick='deleteFans("+rows[i].uuid+")'>删除</li>";
				     html+="</ul>";
				  html+="</div>";
				     //html+="<input class='toFollowed' type='button' value='关注'>";
				  html+="</div>";
				html+="</div>";
			}
			$(".followBody").html(html);
			$("#fansNumber").html(json.total);
		}
	}
	//关注
	function addGuanZhu(sid){
		var url = "<%=contextPath%>/TeeWeibGuanZhuController/addGuanZhu.action";
		var json = tools.requestJsonRs(url,{personId:sid});
		if(json.rtState){
			//alert("关注成功");
			window.location.reload();
		}else{
			//alert("关注失败");
		}
	}
	//删除粉丝
	function deleteFans(sid){
		var url = "<%=contextPath%>/TeeWeibGuanZhuController/deleteFans.action";
		var json = tools.requestJsonRs(url,{sid:sid});
		if(json.rtState){
			//alert("删除成功");
			window.location.reload();
		}else{
			//alert("删除失败");
		}
	}
	</script>
</head>
<body  onload="doInit();">
	
	<div class="main">
		<div class="myFollow">
			<div class="group">
				<div class="followHeader">
					<span>粉丝</span>
					<span id="fansNumber"></span>
					<a class="othersFollow" href="follows.jsp">我的关注</a>
					<input class="followSearch" type="text" placeholder="输入昵称或备注" style="display: none;">
				</div>
				<div class="followBody">
				
				</div>
			</div>
		</div>
	</div>
	<script src="https://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
<script>
	$(function(){
		$(".allDepart .name").click(function(event) {
			$(".allDepartWrap").toggleClass('hide');
			event.stopPropagation();
			event.cancelBubble = true;
		});
		$(".searchDep").click(function(event) {
			$(".searchDepInput").toggleClass('hide');
			$(".searchResult").toggleClass('hide');
		});
		//点击其他位置隐藏
		$("body").click(function(event) {
			$(".allDepartWrap").addClass('hide');
		});
		$(".allDepartWrap").click(function(event) {
			event.stopPropagation();
			event.cancelBubble = true;
		});
		$(".share").click(function(event) {
			$("html").css("overflow","hidden");
			$(".bodyWrap").removeClass('hide');
		});
		$(".repost .close").click(function(event) {
			$("html").css("overflow","auto");
			$(".bodyWrap").addClass('hide');
		});
	});
</script>
</body>
</html>
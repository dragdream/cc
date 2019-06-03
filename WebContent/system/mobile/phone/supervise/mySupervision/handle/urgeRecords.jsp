<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
  //任务主键
  int  supId=TeeStringUtil.getInteger(request.getParameter("supId"),0);
  //当前登陆人
  TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
  
  String option=TeeStringUtil.getString(request.getParameter("option"),"wjs");
%>

<!DOCTYPE HTML>
<html>
<head>
<title>催办记录</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<style>
#topPopover {
	position: fixed;
	top: 16px;
	right: 6px;
	width: 120px;
}
#topPopover .mui-popover-arrow {
	left: auto;
	right: 6px;
}
</style>
</head>
<body>
	<header class="mui-bar mui-bar-nav">
		<span class="mui-icon mui-icon-back" id="backBtn"></span>
		<h1 class="mui-title">催办记录</h1>
	</header>
	<!-- 底部选项卡 -->
	<nav class="mui-bar mui-bar-tab ">
			<a id="b1" class="mui-tab-item" href="#" onclick="" >基本详情</a>
			<a id="b2" class="mui-tab-item " href="#" onclick="">办理情况</a>
			<a class="mui-tab-item  mui-active" href="#Popover_2" style="font-weight:bold">申请记录</a>
    </nav>
	<!--下拉刷新容器-->
	<div id="pullrefresh" class="mui-content mui-scroll-wrapper">
		<div class="mui-scroll">
			<!--数据列表-->
			<ul class="mui-table-view" id="list">
				
			</ul>
		</div>
	</div>
	
	
	<div id="Popover_2" class="mui-popover">
			<ul class="mui-table-view">
				<li id="b3" class="mui-table-view-cell">催办申请记录</li>
				<li id="b4" class="mui-table-view-cell">办结申请记录</li>
				<li id="b5" class="mui-table-view-cell">暂停恢复申请记录</li>
			</ul>
	</div>
	<script>
	var option="<%=option%>";

	var supId=<%=supId%>;
	var loginUserUuid=<%=loginUser.getUuid()%>;
	var page = 1;
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
		page = 1;
		var url=contextPath +"/supUrgeController/getUrgeListBySupId.action?supId="+supId;		
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:{state:-1,rows:20,page:page++},
			timeout:10000,
			success:function(json){
				mui('#pullrefresh').pullRefresh().endPulldownToRefresh(false);
				json = eval("("+json+")");
				var render = [];
				for(var i=0;i<json.rows.length;i++){
					var item = json.rows[i];

					  render.push("<li class=\"mui-table-view-cell mui-media\"   sid='"+item.sid+"' >");
						
	                    render.push("<div class=\"mui-media-body mui-slider-handle\">");
						render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
						render.push("<span>"+item.content.substr(0,10)+"..."+"</span></p>");
						render.push("<p style='font-size:12px'>发送人："+item.createrName+"</p>");
						render.push("<p style='font-size:12px'>发送时间："+item.createTimeStr+"</p>");
						render.push("</div>");
						
						
						render.push("</li>");
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
		var url=contextPath +"/supUrgeController/getUrgeListBySupId.action?supId="+supId;		
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:{state:-1,rows:20,page:page++},
			timeout:10000,
			success:function(json){
				mui('#pullrefresh').pullRefresh().endPullupToRefresh(false);
				json = eval("("+json+")");
				var render = [];
				for(var i=0;i<json.rows.length;i++){
					var item = json.rows[i];
					
					render.push("<li class=\"mui-table-view-cell mui-media\"   sid='"+item.sid+"' >");
						
                    render.push("<div class=\"mui-media-body mui-slider-handle\">");
					render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
					render.push("<span>"+item.content.substr(0,10)+"..."+"</span></p>");
					render.push("<p style='font-size:12px'>发送人："+item.createrName+"</p>");
					render.push("<p style='font-size:12px'>发送时间："+item.createTimeStr+"</p>");
					render.push("</div>");
					
					
					render.push("</li>");
				}
				$("#list").append(render.join(""));
				
				$(".mui-media").each(function(i,obj){
					obj.removeEventListener("tap",detail);
					obj.addEventListener("tap",detail);
				});
				
			},
			error:function(){
				
			}
		});
		
	}
	
	
	
	//任务详情
	function detail(){
		window.event.cancelBubble=true;
		var sid = this.getAttribute("sid");
		window.location = "urgeDetail.jsp?sid="+sid+"&&supId="+supId+"&&option="+option;
	}
	
	

	mui.ready(function() {
		mui('#pullrefresh').pullRefresh().pullupLoading();
		
		backBtn.addEventListener("tap",function(){
			window.location = '../index.jsp?option='+option;
		});//返回
		
		//基本详情
		b1.addEventListener("tap",function(){
			window.location='index.jsp?sid=<%=supId%>'+'&&option='+option;
		});
		
		//办理情况
		b2.addEventListener("tap",function(){
			window.location='feedBackRecords.jsp?supId=<%=supId%>'+'&&option='+option;
		});
		
		//催办记录
		b3.addEventListener("tap",function(){
			window.location='urgeRecords.jsp?supId=<%=supId%>'+'&&option='+option;
		});
		
		//办结申请记录
		b4.addEventListener("tap",function(){
			window.location='endApplyRecords.jsp?supId=<%=supId%>'+'&&option='+option;
		});
		//暂停恢复申请记录
		b5.addEventListener("tap",function(){
			window.location='pauseRecoverApplyRecords.jsp?supId=<%=supId%>'+'&&option='+option;
		});
		
	});
	</script>
</body>
</html>
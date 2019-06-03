<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
   //任务主键
   int taskId=TeeStringUtil.getInteger(request.getParameter("taskId"),0);
 
%>

<!DOCTYPE HTML>
<html>
<head>
<title>我的任务</title>
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
<body onload="doInit()">
	<header class="mui-bar mui-bar-nav">
		<span class="mui-icon mui-icon-back" id="backBtn"></span>
		<h1 class="mui-title">项目问题</h1>
		<span class="mui-icon mui-icon-plusempty mui-pull-right" id="addBtn" style="display: none"></span>
	</header>
	<!--下拉刷新容器-->
	<div id="pullrefresh" class="mui-content mui-scroll-wrapper">
		<div class="mui-scroll">
			<!--数据列表-->
			<ul class="mui-table-view" id="list">
				
			</ul>
		</div>
	</div>
	
	
	<nav class="mui-bar mui-bar-tab" id="footerDiv" >
		<a id="a1" class="mui-tab-item mui-active" >
			<!-- <span class="mui-icon mui-icon-compose"></span> -->
			<span class="mui-tab-label" >详情</span>
		</a>
		<a id="a2" class="mui-tab-item mui-active" >
			<!-- <span class="mui-icon mui-icon-search"></span> -->
			<span class="mui-tab-label" >汇报</span>
		</a>
		<a id="a3" class="mui-tab-item mui-active" style="font-weight:bold">
			<!-- <span class="mui-icon mui-icon-search"></span> -->
			<span class="mui-tab-label" >问题</span>
		</a>
</nav>
	<script>
	var taskId=<%=taskId%>;
	var page = 1;
	//初始化方法
	function doInit(){
		isEnd();
		
		//详情
		a1.addEventListener("tap",function(){
			window.location = 'taskDetail.jsp?sid='+taskId;
		});
		
		//汇报
		a2.addEventListener("tap",function(){
			window.location = 'report.jsp?taskId='+taskId;
		});
	}
	
	
	//根据任务主键  判断任务状态  从而判断是否可以新增问题
	function isEnd(){
		var url=contextPath+"/taskController/getInfoBySid.action";
		mui.ajax(url,{
			type:"POST",
			dataType:"JSON",
			data:{sid:taskId},
			timeout:10000,
			async:false,
			success:function(text){
				var json = eval("("+text+")");
				if(json.rtState){	
					var status=json.rtData.status;
					if(status==0){//办理中
						$("#addBtn").show();	
					}
				}else{
					alert("数据获取失败！");
				}
			}
		});
	}
	
	
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
		var url=contextPath + "/projectQuestionController/getQuestionListByTaskId.action?taskId="+taskId;
		
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
					render.push("<li class=\"mui-table-view-cell mui-media\"  sid='"+item.sid+"' >");
					
					render.push("<div class=\"mui-media-body\">");
					render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px;margin-right:25px'>");
					
					render.push("<span>问题名称："+item.questionName+"</span></p>");
					render.push("<span>处理人："+item.operatorName+"</span></p>");
					var statusDesc="";
					if(item.status==0){
						statusDesc="待处理";
					}else{
						statusDesc="已处理";
					}
					render.push("<p style='font-size:12px;margin-top:5px'>问题状态："+statusDesc+"</p>");
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
		var url=contextPath + "/projectQuestionController/getQuestionListByTaskId.action?taskId="+taskId;
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
					render.push("<li class=\"mui-table-view-cell mui-media\"  sid='"+item.sid+"' >");
					
					render.push("<div class=\"mui-media-body\">");
					render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px;margin-right:25px'>");
					
					render.push("<span>问题名称："+item.questionName+"</span></p>");
					render.push("<span>处理人："+item.operatorName+"</span></p>");
					var statusDesc="";
					if(item.status==0){
						statusDesc="待处理";
					}else{
						statusDesc="已处理";
					}
					render.push("<p style='font-size:12px;margin-top:5px'>问题状态："+statusDesc+"</p>");
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
	
	
	
	//问题详情
	function detail(){
		var sid = this.getAttribute("sid");
		window.location = "questionDetail.jsp?sid="+sid;
	}
	
	
	
	
	mui.ready(function() {
		mui('#pullrefresh').pullRefresh().pullupLoading();
		
		backBtn.addEventListener("tap",function(){
			window.location.href = "index.jsp";
		});//返回
		
		addBtn.addEventListener("tap",function(){
			window.location.href = "addQuestion.jsp?taskId="+taskId;
		});//新建
		
	});
	</script>
</body>
</html>
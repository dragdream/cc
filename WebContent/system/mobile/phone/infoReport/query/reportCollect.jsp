<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
   //任务模板主键
   int taskTemplateId=TeeStringUtil.getInteger(request.getParameter("taskTemplateId"),0);
   //任务模板名称
   String taskTemplateName=TeeStringUtil.getString(request.getParameter("taskTemplateName"));
%>

<!DOCTYPE HTML>
<html>
<head>
<title>上报汇总</title>
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
		<h1 class="mui-title"><%=taskTemplateName %>--上报汇总</h1>
	</header>
	<!--下拉刷新容器-->
	<div id="pullrefresh" class="mui-content mui-scroll-wrapper">
		<div class="mui-scroll">
			<!--数据列表-->
			<ul class="mui-table-view" id="list">
				
			</ul>
		</div>
	</div>
	
	
	
	<script>
	var taskTemplateId=<%=taskTemplateId  %>;
	var taskTemplateName="<%=taskTemplateName %>";
	
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
		var url=contextPath + "/TeeTaskPubRecordController/getRecordListByTaskTemplateId.action?taskTemplateId="+taskTemplateId;
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:{rows:20,page:page++},
			timeout:10000,
			success:function(json){
				mui('#pullrefresh').pullRefresh().endPulldownToRefresh(false);
				json = eval("("+json+")");
				var render = [];
				for(var i=0;i<json.rows.length;i++){
					var item = json.rows[i];
					render.push("<li class=\"mui-table-view-cell mui-media\"    recordId='"+item.recordId+"' reportTime='"+item.reportTime+"' >");
					
					render.push("<div class=\"mui-media-body mui-slider-handle\">");
					render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
					render.push("<span>"+item.reportTime+"</span></p>");
					render.push("<p style='font-size:12px'>应报："+item.sumNum+"</p>");
					render.push("<p style='font-size:12px'>已报："+item.ybNum+"</p>");
					render.push("<p style='font-size:12px'>未报："+item.wbNum+"</p>");
					render.push("<p style='font-size:12px'>上报率："+item.rate+"</p>");
					render.push("</div>");
					
					
					
					render.push("</li>");
				}
				$("#list").html(render.join(""));
				
				
				//查看
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
		var url=contextPath + "/TeeTaskPubRecordController/getRecordListByTaskTemplateId.action?taskTemplateId="+taskTemplateId;
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:{rows:20,page:page++},
			timeout:10000,
			success:function(json){
				mui('#pullrefresh').pullRefresh().endPullupToRefresh(false);
				json = eval("("+json+")");
				var render = [];
				for(var i=0;i<json.rows.length;i++){
					var item = json.rows[i];
                    render.push("<li class=\"mui-table-view-cell mui-media\"    recordId='"+item.recordId+"' reportTime='"+item.reportTime+"' >");
					
					render.push("<div class=\"mui-media-body mui-slider-handle\">");
					render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
					render.push("<span>"+item.reportTime+"</span></p>");
					render.push("<p style='font-size:12px'>应报："+item.sumNum+"</p>");
					render.push("<p style='font-size:12px'>已报："+item.ybNum+"</p>");
					render.push("<p style='font-size:12px'>未报："+item.wbNum+"</p>");
					render.push("<p style='font-size:12px'>上报率："+item.rate+"</p>");
					render.push("</div>");
					
					
					render.push("</li>");
				}
				$("#list").append(render.join(""));
				


				//查看
				$(".mui-media").each(function(i,obj){
					obj.removeEventListener("tap",detail);
					obj.addEventListener("tap",detail);
				});
				
			},
			error:function(){
				
			}
		});
		
	}
	
	
	
	//详情
	function detail(){
		var recordId = this.getAttribute("recordId");
		var reportTime = this.getAttribute("reportTime");
		//跳转到已报列表
	    window.location = "detail.jsp?taskTemplateName="+taskTemplateName+"&taskTemplateId="+taskTemplateId+"&taskPubRecordId="+recordId+"&reportTime="+reportTime;
		
	}
	
	
	
	
	
	mui.ready(function() {
		mui('#pullrefresh').pullRefresh().pullupLoading();
		
		backBtn.addEventListener("tap",function(){
			window.location.href = "index.jsp";
		});//返回

	});
	</script>
</body>
</html>
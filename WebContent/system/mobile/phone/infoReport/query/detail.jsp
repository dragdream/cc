<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	int taskPubRecordId=TeeStringUtil.getInteger(request.getParameter("taskPubRecordId"),0);
	String reportTime=TeeStringUtil.getString(request.getParameter("reportTime"));
	String taskTemplateName=TeeStringUtil.getString(request.getParameter("taskTemplateName"));
	int taskTemplateId=TeeStringUtil.getInteger(request.getParameter("taskTemplateId"),0);
%>

<!DOCTYPE HTML>
<html>
<head>
<title>已报详情</title>
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
		<h1 class="mui-title"><%=taskTemplateName %>(<%=reportTime %>)--已报详情</h1>
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
	var taskPubRecordId=<%=taskPubRecordId %>;//任务发布记录主键
	var taskTemplateId=<%=taskTemplateId %>;//任务模板Id
	var taskTemplateName="<%=taskTemplateName %>"; //任务模板名称
	var reportTime="<%=reportTime %>";
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
		var url=contextPath + "/TeeTaskPubRecordItemController/getRepDataListByRecordId.action?taskPubRecordId="+taskPubRecordId+"&taskTemplateId="+taskTemplateId;
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
					render.push("<li class=\"mui-table-view-cell mui-media\"  createUserId='"+item.CREATE_USER_ID+"' taskPubRecordItemId="+item.RECORD_ITEM_ID+"  >");
					
					render.push("<div class=\"mui-media-body mui-slider-handle\">");
					render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
					render.push("<span>"+item.CREATE_USER_NAME+"</span></p>");
					render.push("<p style='font-size:12px'>所属部门："+item.CREATE_USER_DEPT_NAME+"</p>");
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
		var url=contextPath + "/TeeTaskPubRecordItemController/getRepDataListByRecordId.action?taskPubRecordId="+taskPubRecordId+"&taskTemplateId="+taskTemplateId;
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
                    render.push("<li class=\"mui-table-view-cell mui-media\" createUserId='"+item.CREATE_USER_ID+"' taskPubRecordItemId="+item.RECORD_ITEM_ID+"     >");
					
					render.push("<div class=\"mui-media-body mui-slider-handle\">");
					render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
					render.push("<span>"+item.CREATE_USER_NAME+"</span></p>");
					render.push("<p style='font-size:12px'>所属部门："+item.CREATE_USER_DEPT_NAME+"</p>");
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
		var createUserId = this.getAttribute("createUserId");
		var taskPubRecordItemId = this.getAttribute("taskPubRecordItemId");
		
		
		var url="info.jsp?taskPubRecordItemId="+taskPubRecordItemId+"&taskTemplateName="+taskTemplateName+"&taskTemplateId="+taskTemplateId+"&createUserId="+createUserId+"&reportTime="+reportTime+"&taskPubRecordId="+taskPubRecordId;
		//跳转到上报汇总页面
	    window.location = url;
		
	}
	
	
	
	
	
	mui.ready(function() {
		
		mui('#pullrefresh').pullRefresh().pullupLoading();
		
		backBtn.addEventListener("tap",function(){
			window.location.href = "reportCollect.jsp?taskTemplateId="+taskTemplateId+"&taskTemplateName="+taskTemplateName;
		});//返回

	});
	</script>
</body>
</html>
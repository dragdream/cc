<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
   int flag=TeeStringUtil.getInteger(request.getParameter("flag"),0);//默认办理中
   String  flagDesc="";
   if(flag==0){
	   flagDesc="待上报";  
   }else if(flag==1){
	   flagDesc="已上报";  
   }
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
<body>
	<header class="mui-bar mui-bar-nav">
		<span class="mui-icon mui-icon-back" id="backBtn"></span>
		<h1 class="mui-title">我的上报（<%=flagDesc %>）</h1>
		<a class="mui-action-menu mui-icon mui-icon-more mui-pull-right" href="#topPopover"></a>
	</header>
	<!--下拉刷新容器-->
	<div id="pullrefresh" class="mui-content mui-scroll-wrapper">
		<div class="mui-scroll">
			<!--数据列表-->
			<ul class="mui-table-view" id="list">
				
			</ul>
		</div>
	</div>
	
	<!--右上角弹出菜单-->
	<div id="topPopover" class="mui-popover">
		<ul class="mui-table-view">
		    <li class="mui-table-view-cell" onclick="window.location = 'index.jsp?flag=0';">未上报</li>
		    <li class="mui-table-view-cell" onclick="window.location = 'index.jsp?flag=1';">已上报</li>
		  </ul>
	</div>
	
	<script>
	var flagDesc="<%=flagDesc %>";
	var flag=<%=flag %>;
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
		var url=contextPath + "/TeeTaskPubRecordItemController/getMyReport.action?flag="+flag;
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
					render.push("<li class=\"mui-table-view-cell mui-media\"   pubRecordItemId='"+item.sid+"'  pubRecordId='"+item.taskPubRecordId+"' taskTemplateId='"+item.taskTemplateId+"' taskTemplateName='"+item.taskTemplateName+"' >");
					
					render.push("<div class=\"mui-media-body mui-slider-handle\">");
					render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
					render.push("<span>"+item.taskTemplateName+"&nbsp;("+item.taskTypeDesc+"："+item.pc+")</span></p>");
					render.push("<p style='font-size:12px'>发布类型："+item.pubTypeDesc+"</p>");
					render.push("<p style='font-size:12px'>发布时间："+item.createTimeStr+"</p>");
					render.push("</div>");
					
					/* render.push("<div class='mui-slider-right mui-disabled'>");
					var managerId=item.managerId;
					if(flag==0){ //汇报
						render.push("<a class='mui-btn mui-btn-blue hb' pubRecordItemId='"+item.sid+"'  pubRecordId='"+item.taskPubRecordId+"' taskTemplateId='"+item.taskTemplateId+"' taskTemplateName='"+item.taskTemplateName+"' >汇报</a>");   
					}else if(flag==1){
						render.push("<a class='mui-btn mui-btn-blue xq' pubRecordItemId='"+item.sid+"' pubRecordId='"+item.taskPubRecordId+"' taskTemplateId='"+item.taskTemplateId+"' taskTemplateName='"+item.taskTemplateName+"' >详情</a>");   
					}
					render.push(" </div>"); */
					
					
					render.push("</li>");
				}
				$("#list").html(render.join(""));
				
			/* 	//汇报
				$(".hb").each(function(i,obj){
					obj.removeEventListener("tap",report);
					obj.addEventListener("tap",report);
				});
				
				//详情
				$(".xq").each(function(i,obj){
					obj.removeEventListener("tap",detail);
					obj.addEventListener("tap",detail);
				}); */
				
				if(flag==0){//汇报
					$(".mui-media").each(function(i,obj){
						obj.removeEventListener("tap",report);
						obj.addEventListener("tap",report);
					});
				}else if(flag==1){
					$(".mui-media").each(function(i,obj){
						obj.removeEventListener("tap",detail);
						obj.addEventListener("tap",detail);
					});
				}
				
			},
			error:function(){
				
			}
		});
		
	}
	
	/**
	 * 上拉加载具体业务实现
	 */
	function pullupRefresh() {
		var url=contextPath + "/TeeTaskPubRecordItemController/getMyReport.action?flag="+flag;
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
					render.push("<li class=\"mui-table-view-cell mui-media\"  pubRecordItemId='"+item.sid+"'  pubRecordId='"+item.taskPubRecordId+"' taskTemplateId='"+item.taskTemplateId+"' taskTemplateName='"+item.taskTemplateName+"' >");
					
					render.push("<div class=\"mui-media-body mui-slider-handle\">");
					render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
					render.push("<span>"+item.taskTemplateName+"&nbsp;("+item.taskTypeDesc+"："+item.pc+")</span></p>");
					render.push("<p style='font-size:12px'>发布类型："+item.pubTypeDesc+"</p>");
					render.push("<p style='font-size:12px'>发布时间："+item.createTimeStr+"</p>");
					render.push("</div>");
					
					
                   /*  render.push("<div class='mui-slider-right mui-disabled'>");
					var managerId=item.managerId;
					if(flag==0){ //汇报
						render.push("<a class='mui-btn mui-btn-blue hb' pubRecordItemId='"+item.sid+"'  pubRecordId='"+item.taskPubRecordId+"' taskTemplateId='"+item.taskTemplateId+"' taskTemplateName='"+item.taskTemplateName+"' >汇报</a>");   
					}else if(flag==1){
						render.push("<a class='mui-btn mui-btn-blue xq' pubRecordItemId='"+item.sid+"' pubRecordId='"+item.taskPubRecordId+"' taskTemplateId='"+item.taskTemplateId+"' taskTemplateName='"+item.taskTemplateName+"' >详情</a>");   
					}
					render.push(" </div>"); */
					
					
					render.push("</li>");
				}
				$("#list").append(render.join(""));
				
				/* //汇报
				$(".hb").each(function(i,obj){
					obj.removeEventListener("tap",report);
					obj.addEventListener("tap",report);
				});
				
				//详情
				$(".xq").each(function(i,obj){
					obj.removeEventListener("tap",detail);
					obj.addEventListener("tap",detail);
				}); */
				if(flag==0){//汇报
					$(".mui-media").each(function(i,obj){
						obj.removeEventListener("tap",report);
						obj.addEventListener("tap",report);
					});
				}else if(flag==1){
					$(".mui-media").each(function(i,obj){
						obj.removeEventListener("tap",detail);
						obj.addEventListener("tap",detail);
					});
				}
			},
			error:function(){
				
			}
		});
		
	}
	
	
	
	//详情
	function detail(){
		var pubRecordItemId = this.getAttribute("pubRecordItemId");
		var pubRecordId = this.getAttribute("pubRecordId");
		var taskTemplateId = this.getAttribute("taskTemplateId");
		var taskTemplateName = this.getAttribute("taskTemplateName");
		
		window.location = "detail.jsp?taskPubRecordItemId="+pubRecordItemId+"&taskTemplateName="+taskTemplateName+"&taskTemplateId="+taskTemplateId;
		
	}
	
	
	
	//汇报
	function report(){
		var pubRecordItemId = this.getAttribute("pubRecordItemId");
		var pubRecordId = this.getAttribute("pubRecordId");
		var taskTemplateId = this.getAttribute("taskTemplateId");
		var taskTemplateName = this.getAttribute("taskTemplateName");
		
		//跳转到汇报页面 
		window.location = "report.jsp?pubRecordItemId="+pubRecordItemId+"&pubRecordId="+pubRecordId+"&taskTemplateId="+taskTemplateId+"&taskTemplateName="+taskTemplateName;
	}
	
	
	
	mui.ready(function() {
		mui('#pullrefresh').pullRefresh().pullupLoading();
		
		backBtn.addEventListener("tap",function(){
			window.location.href = "../index.jsp";
		});//返回

	});
	</script>
</body>
</html>
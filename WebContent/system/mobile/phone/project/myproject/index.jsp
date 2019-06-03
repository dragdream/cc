<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
   int status=TeeStringUtil.getInteger(request.getParameter("status"),3);//默认办理中
   String  statusDesc="";
   if(status==1){
	   statusDesc="立项中";  
   }else if(status==2){
	   statusDesc="审批中";  
   }else if(status==3){
	   statusDesc="办理中";  
   }else if(status==4){
	   statusDesc="挂起中";  
   }else if(status==5){
	   statusDesc="已办结";  
   }
%>

<!DOCTYPE HTML>
<html>
<head>
<title>项目管理</title>
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
		<h1 class="mui-title">我的项目（<%=statusDesc %>）</h1>
		<a class="mui-action-menu mui-icon mui-icon-more mui-pull-right" href="#topPopover"></a>
		<span class="mui-icon mui-icon-plusempty mui-pull-right" id="addBtn"></span>
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
		    <li class="mui-table-view-cell" onclick="window.location = 'index.jsp?status=1';">立项中</li>
		    <li class="mui-table-view-cell" onclick="window.location = 'index.jsp?status=2';">审批中</li>
		    <li class="mui-table-view-cell" onclick="window.location = 'index.jsp?status=3';">办理中</li>
		    <li class="mui-table-view-cell" onclick="window.location = 'index.jsp?status=4';">挂起中</li>
		    <li class="mui-table-view-cell" onclick="window.location = 'index.jsp?status=5';">已办结</li>
		  </ul>
	</div>
	
	<script>
	var status=<%=status%>;
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
		var url="";
		if(status==1||status==2){//立项中  审批中
			url =contextPath+"/projectController/getProjectListByStatus.action?status="+status;
		}else if(status==3||status==5){
			url=contextPath + "/projectController/getMyProjectListByStatus.action?status="+status;
		}else if(status==4){//挂起中
			url=contextPath +"/projectController/getHangProject.action?status="+status;	
		}
		
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
					//var readType = item.readType;
					render.push("<li class=\"mui-table-view-cell mui-media\"   uuid='"+item.uuid+"' >");
					
					render.push("<div class=\"mui-media-body\">");
					render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
					/* if(readType=="0"){
						render.push("<img src='readflag.png' style='height:15px'/>");
					} */
					render.push("<span>"+item.projectName+"</span></p>");
					render.push("<p style='font-size:12px'>"+item.projectNum+"</p>");
					render.push("<p style='font-size:12px'>"+item.beginTime+"&nbsp;~&nbsp;"+item.endTime+"</p>");
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
		var url="";
		if(status==1||status==2){//立项中  审批中
			url =contextPath+"/projectController/getProjectListByStatus.action?status="+status;
		}else if(status==3||status==5){
			url=contextPath + "/projectController/getMyProjectListByStatus.action?status="+status;
		}else if(status==4){//挂起中
			url=contextPath +"/projectController/getHangProject.action?status="+status;	
		}
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
					//var readType = item.readType;
					render.push("<li class=\"mui-table-view-cell mui-media\"  uuid='"+item.uuid+"' >");
					
					render.push("<div class=\"mui-media-body\">");
					render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
					/* if(readType=="0"){
						render.push("<img src='readflag.png' style='height:15px'/>");
					} */
					render.push("<span>"+item.projectName+"</span></p>");
					render.push("<p style='font-size:12px'>"+item.projectNum+"</p>");
					render.push("<p style='font-size:12px'>"+item.beginTime+"&nbsp;~&nbsp;"+item.endTime+"</p>");
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
	
	
	
	//项目详情
	function detail(){
		var uuid = this.getAttribute("uuid");
		window.location = "projectDetail.jsp?uuid="+uuid+"&&status="+status;
	}
	
	
	
	
	mui.ready(function() {
		mui('#pullrefresh').pullRefresh().pullupLoading();
		
		backBtn.addEventListener("tap",function(){
			window.location.href = "../index.jsp";
		});//返回
		
		addBtn.addEventListener("tap",function(){
			window.location.href = "addOrUpdate.jsp";
		});//新建
		
	});
	</script>
</body>
</html>
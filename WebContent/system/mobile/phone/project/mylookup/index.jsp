<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
   int readFlag=TeeStringUtil.getInteger(request.getParameter("readFlag"),0);//查阅状态
   String  statusDesc="";
   if(readFlag==0){
	   statusDesc="待阅";  
   }else if(readFlag==1){
	   statusDesc="已阅";  
   }
%>

<!DOCTYPE HTML>
<html>
<head>
<title>项目查阅</title>
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
		<h1 class="mui-title">项目查阅（<%=statusDesc %>）</h1>
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
		    <li class="mui-table-view-cell" onclick="window.location = 'index.jsp?readFlag=0';">待阅</li>
		    <li class="mui-table-view-cell" onclick="window.location = 'index.jsp?readFlag=1';">已阅</li>
		  </ul>
	</div>
	
	<script>
	var readFlag=<%=readFlag%>;//阅读标记
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
		var url=contextPath + "/projectCopyController/getMyLookUpByReadFlag.action?readFlag="+readFlag;
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
					render.push("<li class=\"mui-table-view-cell mui-media\"   projectId='"+item.projectId+"' >");
					
					render.push("<div class=\"mui-media-body\">");
					render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
					
					render.push("<span>"+item.projectName+"</span></p>");
					render.push("<p style='font-size:12px'>"+item.projectNum+"</p>");
					render.push("<p style='font-size:12px'>"+item.projectBeginTimeStr+"&nbsp;~&nbsp;"+item.projectEndTimeStr+"</p>");
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
		var url=contextPath + "/projectCopyController/getMyLookUpByReadFlag.action?readFlag="+readFlag;
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
					render.push("<li class=\"mui-table-view-cell mui-media\"   projectId='"+item.projectId+"' sid="+item.sid+" >");
					
					render.push("<div class=\"mui-media-body\">");
					render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
					
					render.push("<span>"+item.projectName+"</span></p>");
					render.push("<p style='font-size:12px'>"+item.projectNum+"</p>");
					render.push("<p style='font-size:12px'>"+item.projectBeginTimeStr+"&nbsp;~&nbsp;"+item.projectEndTimeStr+"</p>");
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
		var projectId = this.getAttribute("projectId");
		var sid = this.getAttribute("sid");
		if(readFlag==0){//待阅
			var url=contextPath+"/projectCopyController/changeReadFlag.action";	
			mui.ajax(url,{
				type:"post",
				dataType:"html",
				data:{sid:sid},
				timeout:10000,
				success:function(json){
					json = eval("("+json+")");
					if(json.rtState){
						window.location = "projectDetail.jsp?uuid="+projectId;
					}
				}
			});
		}else{
			window.location = "projectDetail.jsp?uuid="+projectId;
		}
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
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
   int type=TeeStringUtil.getInteger(request.getParameter("type"),1);//默认查询全部
   String  typeDesc="";
   if(type==1){
	   typeDesc="全部";  
   }else if(type==2){
	   typeDesc="我负责的";  
   }else if(type==3){
	   typeDesc="我下属负责的";  
   }
%>

<!DOCTYPE HTML>
<html>
<head>
<title>线索</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<style>
#topPopover {
	position: fixed;
	top: 16px;
	right: 6px;
	width: 130px;
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
		<h1 class="mui-title">线索（<%=typeDesc %>）</h1>
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
		    <li class="mui-table-view-cell" onclick="window.location = 'index.jsp?type=1';">全部</li>
		    <li class="mui-table-view-cell" onclick="window.location = 'index.jsp?type=2';">我负责的</li>
		    <li class="mui-table-view-cell" onclick="window.location = 'index.jsp?type=3';">我下属负责的</li>
		  </ul>
	</div>
	
	<script>
	var type=<%=type%>;
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
		if(type!=0||type!=null){
			url = contextPath+'/TeeCrmClueController/datagrid.action?type='+type;
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
					render.push("<li class=\"mui-table-view-cell mui-media\"   uuid='"+item.sid+"' clueName='"+item.name+"' >");
					
					render.push("<div class=\"mui-media-body\">");
					render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
				
					render.push("<span>"+item.companyName+"</span></p>");
					render.push("<p style='font-size:12px'><span>联系人："+item.name+"<span><span style='padding-left:15px;'>状态："+item.clueStatusDesc+"</span><span style='padding-left:15px;'>来源："+item.clueSourceDesc+"</span></p>");
					render.push("<p style='font-size:12px'>"+item.createTimeDesc+"</p>");
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
		if(type!=0||type!=null){
			url = contextPath+'/TeeCrmClueController/datagrid.action?type='+type;
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
					render.push("<li class=\"mui-table-view-cell mui-media\"  uuid='"+item.sid+"'>");
					
					render.push("<div class=\"mui-media-body\">");
					render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
				
					render.push("<span>"+item.companyName+"</span></p>");
					render.push("<p style='font-size:12px'><span>联系人："+item.name+"<span><span style='padding-left:15px;'>状态："+item.clueStatusDesc+"</span><span style='padding-left:15px;'>来源："+item.clueSourceDesc+"</span></p>");
					render.push("<p style='font-size:12px'>"+item.createTimeDesc+"</p>");
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
	
	
	
	//线索详情
	function detail(){
		var uuid = this.getAttribute("uuid");
		window.location = "/system/mobile/phone/crm/clue/clueInfo.jsp?sid="+uuid+"&type="+type;
	}
	
	
	
	
	mui.ready(function() {
		mui('#pullrefresh').pullRefresh().pullupLoading();
		
		backBtn.addEventListener("tap",function(){
			window.location.href = contextPath+"/system/mobile/phone/crm/index.jsp?type="+type;
		});//返回
		
		addBtn.addEventListener("tap",function(){
			window.location.href = "addOrUpdate.jsp?type="+type;
		});//新建
		
	});
	</script>
</body>
</html>
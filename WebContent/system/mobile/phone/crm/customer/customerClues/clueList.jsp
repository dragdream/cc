<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
   int type=TeeStringUtil.getInteger(request.getParameter("type"),1);//默认查询全部
   String customerId = request.getParameter("customerId")==null?"0":request.getParameter("customerId");//客户主键
%>

<!DOCTYPE HTML>
<html>
<head>
<title>线索列表</title>
<%@ include file="/system/mobile/mui/header.jsp" %>

</head>
<body>
<header class="mui-bar mui-bar-nav">
	<span class="mui-icon mui-icon-back" id="backBtn"></span>
	<h1 class="mui-title">线索</h1>
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
var type=<%=type%>;
var cusId = "<%=customerId%>";
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
			url = contextPath+'/TeeCrmClueController/datagrid.action?cusId='+cusId;
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
				
					render.push("<span>"+item.name+"</span></p>");
					render.push("<p style='font-size:12px'><span>"+item.managePersonName+"<span><span style='padding-left:10px;'>"+item.clueStatusDesc+"</span></p>");
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
			url = contextPath+'/TeeCrmClueController/datagrid.action?cusId='+cusId;
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
				
					render.push("<span>"+item.name+"</span></p>");
					render.push("<p style='font-size:12px'><span>"+item.managePersonName+"<span><span style='padding-left:10px;'>"+item.clueStatusDesc+"</span></p>");
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
		window.location = contextPath+"/system/mobile/phone/crm/customer/customerClues/clueInfo.jsp?sid="+uuid+"&type="+type+"&customerId="+cusId;
	}
	
	
	
	
	mui.ready(function() {
		mui('#pullrefresh').pullRefresh().pullupLoading();
		
		backBtn.addEventListener("tap",function(){
			window.location.href = contextPath+"/system/mobile/phone/crm/customer/customerInfo.jsp?type="+type+"&sid="+cusId;
		});//返回
		
	});
	</script>
</body>
</html>
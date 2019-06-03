<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String customerId = request.getParameter("customerId")==null?"0":request.getParameter("customerId");
	String customerName = TeeStringUtil.getString(request.getParameter("customerName"), null);
	int type=TeeStringUtil.getInteger(request.getParameter("type"),1);//默认查询全部
%>

<!DOCTYPE HTML>
<html>
<head>
<title>回款列表</title>
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
		<h1 class="mui-title">回款列表</h1>
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
	
	
	<script>
	var cusId=<%=customerId%>;
	var customerName="<%=customerName%>";
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
			url = contextPath+'/TeeCrmPaybackController/datagrid.action?cusId='+cusId; //回款列表
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
					render.push("<li class=\"mui-table-view-cell mui-media\"   uuid='"+item.sid+"' customerName='"+item.customerName+"' >");
					render.push("<div class=\"mui-media-body\">");
					render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
					render.push("<span>"+item.paybackNo+"</span></p>");
					render.push("<p style='font-size:12px'>"+item.managePersonName+"<span style='padding-left:15px;'>"+item.paybackStatusDesc+"</span></p>");
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
			url = contextPath+'/TeeCrmPaybackController/datagrid.action?cusId='+cusId; //回款列表
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
					render.push("<li class=\"mui-table-view-cell mui-media\"   uuid='"+item.sid+"' customerName='"+item.customerName+"' >");
					render.push("<div class=\"mui-media-body\">");
					render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
					render.push("<span>"+item.paybackNo+"</span></p>");
					render.push("<p style='font-size:12px'>"+item.managePersonName+"<span style='padding-left:15px;'>"+item.paybackStatusDesc+"</span></p>");
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
	
	
	
	//详情
	function detail(){
		var sid = this.getAttribute("uuid");
		var customerName = this.getAttribute("customerName");
		window.location = contextPath+"/system/mobile/phone/crm/customer/crmPayback/paybackDetail.jsp?sid="+sid+"&customerId="+cusId+"&customerName="+customerName+"&type="+type;
	}
	
	
	
	
	mui.ready(function() {
		mui('#pullrefresh').pullRefresh().pullupLoading();
		
		backBtn.addEventListener("tap",function(){
			window.location.href = contextPath+"/system/mobile/phone/crm/customer/customerInfo.jsp?type="+type+"&sid="+cusId+"&customerName="+customerName;
		});//返回
		
		addBtn.addEventListener("tap",function(){
			window.location.href = contextPath+"/system/mobile/phone/crm/customer/crmPayback/addOrUpdate.jsp?customerId="+cusId+"&customerName="+customerName+"&type="+type;
		});//新建
		
	});
	</script>
</body>
</html>
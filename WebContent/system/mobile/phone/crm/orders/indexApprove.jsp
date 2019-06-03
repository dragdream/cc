<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
   int orderStatus=TeeStringUtil.getInteger(request.getParameter("orderStatus"),1);//默认查询待確認
   String  typeDesc="";
   if(orderStatus==1){
	   typeDesc="待确认";  
   }else if(orderStatus==2){
	   typeDesc="已确认";  
   }else if(orderStatus==3){
	   typeDesc="已驳回";  
   }
%>
<!DOCTYPE HTML>
<html>
<head>
<title>订单审批</title>
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
		<h1 class="mui-title">订单确认（<%=typeDesc %>）</h1>
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
		    <li class="mui-table-view-cell" onclick="window.location = 'indexApprove.jsp?orderStatus=1';">待确认</li>
		    <li class="mui-table-view-cell" onclick="window.location = 'indexApprove.jsp?orderStatus=2';">已确认</li>
		    <li class="mui-table-view-cell" onclick="window.location = 'indexApprove.jsp?orderStatus=3';">已驳回</li>
		  </ul>
	</div>
	
	<script>
	var orderStatus=<%=orderStatus%>;
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
		if(orderStatus!=0||orderStatus!=null){
			url = contextPath+'/TeeCrmOrderController/datagrid.action?orderStatus='+orderStatus;//开票确认列表
		}
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:{state:-1,rows:20,page:page++},
			timeout:10000,
			async:false,
			success:function(json){
				mui('#pullrefresh').pullRefresh().endPulldownToRefresh(false);
				json = eval("("+json+")");
				var render = [];
				for(var i=0;i<json.rows.length;i++){
					var item = json.rows[i];
					render.push("<li class=\"mui-table-view-cell mui-media\"   uuid='"+item.sid+"' customerName='"+item.customerName+"' >");
					render.push("<div class=\"mui-media-body\">");
					render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
					render.push("<span>"+item.orderNo+"</span></p>");
					render.push("<p style='font-size:12px'>所属客户："+item.customerName+"<span style='padding-left:15px;'>"+item.orderStatusDesc+"</span></p>");
					render.push("<p style='font-size:12px'>创建时间："+item.createTimeDesc+"</p>");
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
		if(orderStatus!=0||orderStatus!=null){
			url = contextPath+'/TeeCrmOrderController/datagrid.action?orderStatus='+orderStatus;
		}
		
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:{state:-1,rows:20,page:page++},
			timeout:10000,
			async:false,
			success:function(json){
				mui('#pullrefresh').pullRefresh().endPullupToRefresh(false);
				json = eval("("+json+")");
				var render = [];
				for(var i=0;i<json.rows.length;i++){
					var item = json.rows[i];
					render.push("<li class=\"mui-table-view-cell mui-media\"   uuid='"+item.sid+"' customerName='"+item.customerName+"' >");
					render.push("<div class=\"mui-media-body\">");
					render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
					render.push("<span>"+item.orderNo+"</span></p>");
					render.push("<p style='font-size:12px'>所属客户："+item.customerName+"<span style='padding-left:15px;'>"+item.orderStatusDesc+"</span></p>");
					render.push("<p style='font-size:12px'>创建时间："+item.createTimeDesc+"</p>");
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
		window.location = contextPath+"/system/mobile/phone/crm/orders/approveOrderInfo.jsp?sid="+sid+"&customerName="+customerName+"&orderStatus="+orderStatus;
	}
	
	
	
	
	mui.ready(function() {
		mui('#pullrefresh').pullRefresh().pullupLoading();
		
		backBtn.addEventListener("tap",function(){
			window.location.href = contextPath+"/system/mobile/phone/crm/index.jsp";
		});//返回
		
	});
	</script>
</body>
</html>
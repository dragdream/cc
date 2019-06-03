<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>   
<%
   String option=TeeStringUtil.getString(request.getParameter("option"),"wjs");//默认办理中
   String  statusDesc="";
   if("wjs".equals(option)){
	   statusDesc="未接收";  
   }else if("clz".equals(option)){
	   statusDesc="处理中";  
   }else if("yzt".equals(option)){
	   statusDesc="已暂停";  
   }else if("ybj".equals(option)){
	   statusDesc="已办结";  
   }
 //当前登陆人
   TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
%>

<!DOCTYPE HTML>
<html>
<head>
<title>督办任务</title>
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
		<h1 class="mui-title">督办任务（<%=statusDesc %>）</h1>
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
		    <li class="mui-table-view-cell" onclick="window.location = 'index.jsp?option=wjs';">未接收</li>
		    <li class="mui-table-view-cell" onclick="window.location = 'index.jsp?option=clz';">处理中</li>
		    <li class="mui-table-view-cell" onclick="window.location = 'index.jsp?option=yzt';">已暂停</li>
		    <li class="mui-table-view-cell" onclick="window.location = 'index.jsp?option=ybj';">已办结</li>
		  </ul>
	</div>
	
	<script>
	var option="<%=option%>";
	var loginUserUuid=<%=loginUser.getUuid()%>;
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
		var url=contextPath + "/supervisionController/getMySupListByStatus.action?option="+option;
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
					render.push("<li class=\"mui-table-view-cell mui-media\"   sid='"+item.sid+"' >");
					
					render.push("<div class=\"mui-media-body mui-slider-handle\">");
					render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
					
					render.push("<span>"+item.supName+"</span></p>");
					render.push("<p style='font-size:12px'>责任领导："+item.leaderName+"</p>");
					render.push("<p style='font-size:12px'>主办人员："+item.managerName+"</p>");
					render.push("<p style='font-size:12px'>截止时间："+item.endTimeStr+"</p>");
					render.push("</div>");
					
					render.push("<div class='mui-slider-right mui-disabled'>");
					
					var managerId=item.managerId;
					if(option=="wjs"){//未签收
						if(loginUserUuid==managerId){
							render.push("<a class='mui-btn mui-btn-blue qs' sid='"+item.sid+"' >签收</a>");   
					    }
					}
					render.push(" </div>");
					render.push("</li>");
				}
				$("#list").html(render.join(""));
				
				$(".mui-media").each(function(i,obj){
					obj.removeEventListener("tap",detail);
					obj.addEventListener("tap",detail);
				});
				
				//签收
				$(".qs").each(function(i,obj){
					obj.removeEventListener("tap",receive);
					obj.addEventListener("tap",receive);
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
		var url=contextPath + "/supervisionController/getMySupListByStatus.action?option="+option;
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
                    render.push("<li class=\"mui-table-view-cell mui-media\"   sid='"+item.sid+"' >");
					
					render.push("<div class=\"mui-media-body mui-slider-handle\">");
					render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
					
					render.push("<span>"+item.supName+"</span></p>");
					render.push("<p style='font-size:12px'>责任领导："+item.leaderName+"</p>");
					render.push("<p style='font-size:12px'>主办人员："+item.managerName+"</p>");
					render.push("<p style='font-size:12px'>截止时间："+item.endTimeStr+"</p>");
					render.push("</div>");
					
                    render.push("<div class='mui-slider-right mui-disabled'>");
					var managerId=item.managerId;
					if(option=="wjs"){ //未签收
						if(loginUserUuid==managerId){
							render.push("<a class='mui-btn mui-btn-blue qs' sid='"+item.sid+"' >签收</a>");   
					    }
					}
					render.push(" </div>");
					render.push("</li>");
				}
				$("#list").append(render.join(""));
				
				$(".mui-media").each(function(i,obj){
					obj.removeEventListener("tap",detail);
					obj.addEventListener("tap",detail);
				});
				
				//签收
				$(".qs").each(function(i,obj){
					obj.removeEventListener("tap",receive);
					obj.addEventListener("tap",receive);
				});
			},
			error:function(){
				
			}
		});
		
	}
	
	
	
	//督办任务详情
	function detail(){
		window.event.cancelBubble=true;
		var sid = this.getAttribute("sid");
		window.location = "handle/index.jsp?sid="+sid+"&&option="+option;
	}
	
	//签收任务
	function receive(){
		window.event.cancelBubble=true;
		var sid = this.getAttribute("sid");
		if(window.confirm("是否确认签收该任务？")){
			 var url = contextPath + "/supervisionController/receive.action";
			 var para = {sid:sid};
			mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:para,
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
					alert("签收成功！");
					window.location.reload();
				}
			}
		});	
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
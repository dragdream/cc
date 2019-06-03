<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
  //任务主键
  int  supId=TeeStringUtil.getInteger(request.getParameter("supId"),0);
  TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
  String typeName=TeeStringUtil.getString(request.getParameter("typeName"));
  int typeSid=TeeStringUtil.getInteger(request.getParameter("typeSid"), 0);
%>

<!DOCTYPE HTML>
<html>
<head>
<title>办理情况</title>
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
<body onload="doInit()">
	<header class="mui-bar mui-bar-nav">
		<span class="mui-icon mui-icon-back" id="backBtn"></span>
		<h1 class="mui-title">办理情况</h1>
	</header>
	<!-- 底部选项卡 -->
	<nav class="mui-bar mui-bar-tab ">
			<a id="b1" class="mui-tab-item" href="#" onclick="" >基本详情</a>
			<a id="b2" class="mui-tab-item mui-active" href="#" onclick="" style="font-weight:bold">办理情况</a>
			<a class="mui-tab-item" href="#Popover_2">申请记录</a>
    </nav>
	<!--下拉刷新容器-->
	<div id="pullrefresh" class="mui-content mui-scroll-wrapper">
		<div class="mui-scroll">
			<!--数据列表-->
			<ul class="mui-table-view" id="list">
				
			</ul>
		</div>
	</div>
	
	<div id="Popover_2" class="mui-popover">
			<ul class="mui-table-view">
				<li id="b3" class="mui-table-view-cell">催办申请记录</li>
				<li id="b4" class="mui-table-view-cell">办结申请记录</li>
				<li id="b5" class="mui-table-view-cell">暂停恢复申请记录</li>
			</ul>
	</div>
	
	<br/><br/><br/>
	<script>
	var typeName="<%=typeName%>";
	var typeSid=<%=typeSid%>;
	var supId=<%=supId%>;
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
		var url=contextPath + "/supFeedBackController/getFeedBackListBySupId.action?supId="+supId;		
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
						render.push("<span>"+item.title+"</span></p>");
						render.push("<p style='font-size:12px'>办理人："+item.createrName+"</p>");
						render.push("<p style='font-size:12px'>办理时间："+item.createTimeStr+"</p>");
						render.push("</div>");
						
						
                        render.push("<div class='mui-slider-right mui-disabled'>");
						
						var createrId=item.createrId;
						if(createrId==loginUserUuid){//当前登陆人 是创建人 
							render.push("<a class='mui-btn mui-btn-blue' sid='"+item.sid+"' >修改</a><a class='mui-btn mui-btn-red' sid='"+item.sid+"'>删除</a>");   
						}
						render.push("<a class='mui-btn mui-btn-warning' sid='"+item.sid+"'>回复</a>"); 
						render.push(" </div>");
						
						
						
						render.push("</li>");
				}
				$("#list").html(render.join(""));
				
				$(".mui-media").each(function(i,obj){
					obj.removeEventListener("tap",detail);
					obj.addEventListener("tap",detail);
				});
				
				$(".mui-btn-blue").each(function(i,obj){
					obj.removeEventListener("tap",update);
					obj.addEventListener("tap",update);
				});
				
				
				$(".mui-btn-warning").each(function(i,obj){
					obj.removeEventListener("tap",reply);
					obj.addEventListener("tap",reply);
				});
				
				$(".mui-btn-red").each(function(i,obj){
					obj.removeEventListener("tap",del);
					obj.addEventListener("tap",del);
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
		var url=contextPath + "/supFeedBackController/getFeedBackListBySupId.action?supId="+supId;		
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
					render.push("<span>"+item.title+"</span></p>");
					render.push("<p style='font-size:12px'>办理人："+item.createrName+"</p>");
					render.push("<p style='font-size:12px'>办理时间："+item.createTimeStr+"</p>");
					render.push("</div>");
					
					
                    render.push("<div class='mui-slider-right mui-disabled'>");
					
					var createrId=item.createrId;
					if(createrId==loginUserUuid){//当前登陆人 是创建人 
						render.push("<a class='mui-btn mui-btn-blue' sid='"+item.sid+"' >修改</a><a class='mui-btn mui-btn-red' sid='"+item.sid+"'>删除</a>");   
					}
					render.push("<a class='mui-btn mui-btn-warning' sid='"+item.sid+"'>回复</a>"); 
					render.push(" </div>");
					
					
					
					render.push("</li>");
				}
				$("#list").append(render.join(""));
				
				$(".mui-media").each(function(i,obj){
					obj.removeEventListener("tap",detail);
					obj.addEventListener("tap",detail);
				});
				
				$(".mui-btn-blue").each(function(i,obj){
					obj.removeEventListener("tap",update);
					obj.addEventListener("tap",update);
				});
				
				$(".mui-btn-red").each(function(i,obj){
					obj.removeEventListener("tap",del);
					obj.addEventListener("tap",del);
				});
				
				$(".mui-btn-warning").each(function(i,obj){
					obj.removeEventListener("tap",reply);
					obj.addEventListener("tap",reply);
				});
			},
			error:function(){
				
			}
		});
		
	}
	
	
	
	//反馈详情
	function detail(){
		var sid = this.getAttribute("sid");
		window.location = "fbDetail.jsp?sid="+sid+"&&supId="+supId+"&&typeName="+typeName+"&&typeSid="+typeSid;
	}
	
	//删除反馈
	function del(){
		window.event.cancelBubble=true;
		var sid = this.getAttribute("sid");
		if(window.confirm("是否确认删除该反馈信息？")){
			var url = contextPath + "/supFeedBackController/delBySid.action";
			var param={sid:sid};
			mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:param,
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
					alert("删除成功！");
					window.location.reload();
				}
			}
		});	
	  }
	}
	
	
	//回复反馈
	function reply(){
		window.event.cancelBubble=true;
		var sid = this.getAttribute("sid");
		window.location="addfbReply.jsp?fbId="+sid+"&&supId="+supId+"&&typeName="+typeName+"&&typeSid="+typeSid;
	}
	
	
	//修改反馈
	function update(){
		window.event.cancelBubble=true;
		var sid = this.getAttribute("sid");
		window.location="doFeedBack.jsp?sid="+sid+"&&supId="+supId+"&&typeName="+typeName+"&&typeSid="+typeSid;
	}
	
	mui.ready(function() {
		mui('#pullrefresh').pullRefresh().pullupLoading();
		
		backBtn.addEventListener("tap",function(){
			window.location = '../list.jsp?typeName='+typeName+"&&typeSid="+typeSid;
		});//返回
		
		
		
	});
	
	function doInit(){
		//基本详情
		b1.addEventListener("tap",function(){
			window.location='index.jsp?sid=<%=supId%>';
		});
		//办理情况
		b2.addEventListener("tap",function(){
			window.location='feedBackRecords.jsp?supId=<%=supId%>'+'&&typeName='+typeName+'&&typeSid='+typeSid;
		});
		
		//催办记录
		b3.addEventListener("tap",function(){
			window.location='urgeRecords.jsp?supId=<%=supId%>'+'&&typeName='+typeName+'&&typeSid='+typeSid;
		});
		
		//办结申请记录
		b4.addEventListener("tap",function(){
			window.location='endApplyRecords.jsp?supId=<%=supId%>'+'&&typeName='+typeName+'&&typeSid='+typeSid;
		});
		//暂停恢复申请记录
		b5.addEventListener("tap",function(){
			window.location='pauseRecoverApplyRecords.jsp?supId=<%=supId%>'+'&&typeName='+typeName+'&&typeSid='+typeSid;
		});
	}
	</script>
</body>
</html>
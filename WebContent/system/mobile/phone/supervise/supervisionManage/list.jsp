<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
   String typeName=TeeStringUtil.getString(request.getParameter("typeName"));//分类名称
   int typeSid=TeeStringUtil.getInteger(request.getParameter("typeSid"),0);//分类主键
 //当前登陆人
   TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
%>

<!DOCTYPE HTML>
<html>
<head>
<title>督办管理</title>
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
		<h1 class="mui-title"><%=typeName %></h1>
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
	var typeName="<%=typeName%>";
	var typeSid=<%=typeSid%>;
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
		var url=contextPath +"/supervisionController/getSupervisionListByTypeId.action?typeId="+typeSid;		
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
					
					var status=item.status;
					var desc="";
					if(status==0){
						desc="未发布";
					}else if(status==1){
						desc="办理中";
					}else if(status==2){
						desc="暂停申请中";
					}else if(status==3){
						desc="暂停中";
					}else if(status==4){
						desc="恢复申请中";
					}else if(status==5){
						desc="办结申请中";
					}else if(status==6){
						desc="已办结";
					}else if(status==7){
						desc="待接收";
					}
				
					  render.push("<li class=\"mui-table-view-cell mui-media\"   sid='"+item.sid+"' >");
						
	                    render.push("<div class=\"mui-media-body mui-slider-handle\">");
						render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
						render.push("<span>"+item.supName+"</span></p>");
						render.push("<p style='font-size:12px'>工作状态："+desc+"</p>");
						render.push("<p style='font-size:12px'>截止日期："+item.endTimeStr+"</p>");
						render.push("</div>");
						
						render.push("<div class='mui-slider-right mui-disabled'>");
						
						var createrId=item.createrId;
						if(createrId==loginUserUuid){//当前登陆人 是创建人 
							if(status==0){
								render.push("<a class='mui-btn mui-btn-blue' sid='"+item.sid+"' >修改</a><a class='mui-btn mui-btn-red' sid='"+item.sid+"'>删除</a>");   
							}else if(status==1||status==2||status==3||status==4||status==5||status==7){
								render.push("<a class='mui-btn mui-btn-red' sid='"+item.sid+"' >删除</a>");   
							}else if(status==6){
								render.push("<a class='mui-btn mui-btn-red' sid='"+item.sid+"'>删除</a>"); 
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
				$(".mui-btn-red").each(function(i,obj){
					obj.removeEventListener("tap",del);
					obj.addEventListener("tap",del);
				});
				
				$(".mui-btn-blue").each(function(i,obj){
					obj.removeEventListener("tap",update);
					obj.addEventListener("tap",update);
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
		var url=contextPath +"/supervisionController/getSupervisionListByTypeId.action?typeId="+typeSid;		
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
					
					var status=item.status;
					var desc="";
					if(status==0){
						desc="未发布";
					}else if(status==1){
						desc="办理中";
					}else if(status==2){
						desc="暂停申请中";
					}else if(status==3){
						desc="暂停中";
					}else if(status==4){
						desc="恢复申请中";
					}else if(status==5){
						desc="办结申请中";
					}else if(status==6){
						desc="已办结";
					}else if(status==7){
						desc="待接收";
					}
                    render.push("<li class=\"mui-table-view-cell mui-media\"   sid='"+item.sid+"' >");
					
                    render.push("<div class=\"mui-media-body mui-slider-handle\">");
					render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
					render.push("<span>"+item.supName+"</span></p>");
					render.push("<p style='font-size:12px'>工作状态："+desc+"</p>");
					render.push("<p style='font-size:12px'>截止日期："+item.endTimeStr+"</p>");
					render.push("</div>");
					
					render.push("<div class='mui-slider-right mui-disabled'>");
					
					var createrId=item.createrId;
					if(createrId==loginUserUuid){//当前登陆人 是创建人 
						if(status==0){
							render.push("<a class='mui-btn mui-btn-blue' sid='"+item.sid+"' >修改</a><a class='mui-btn mui-btn-red' sid='"+item.sid+"'>删除</a>");   
						}else if(status==1||status==2||status==3||status==4||status==5||status==7){
							render.push("<a class='mui-btn mui-btn-red' sid='"+item.sid+"'>删除</a>");   
						}else if(status==6){
							render.push("<a class='mui-btn mui-btn-red' sid='"+item.sid+"'>删除</a>"); 
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
				
				$(".mui-btn-red").each(function(i,obj){
					obj.removeEventListener("tap",del);
					obj.addEventListener("tap",del);
				});
				
				$(".mui-btn-blue").each(function(i,obj){
					obj.removeEventListener("tap",update);
					obj.addEventListener("tap",update);
				});
			},
			error:function(){
				
			}
		});
		
	}
	
	
	
	//任务详情
	function detail(){
		window.event.cancelBubble=true;
		var sid = this.getAttribute("sid");
		window.location = "detail/index.jsp?sid="+sid;
	}
	
	//删除任务
	function del(){
		window.event.cancelBubble=true;
		var sid = this.getAttribute("sid");
		if(window.confirm("是否确认刪除该任务？")){
			var url=contextPath+"/supervisionController/delBySid.action";
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
	
	
	//修改任务
	function update(){
		window.event.cancelBubble=true;
		var sid = this.getAttribute("sid");
		window.location="addOrUpdate.jsp?sid="+sid;
	}
	
	mui.ready(function() {
		mui('#pullrefresh').pullRefresh().pullupLoading();
		
		backBtn.addEventListener("tap",function(){
			window.location.href = "index.jsp";
		});//返回
		
		addBtn.addEventListener("tap",function(){
			window.location.href = "addOrUpdate.jsp";
		});//新建
		
	});
	</script>
</body>
</html>
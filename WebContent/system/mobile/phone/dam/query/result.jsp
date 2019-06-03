<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
  String param1=TeeStringUtil.getString(request.getParameter("param"));
  String param = param1.replace("\"", "\\\"");
%>

<!DOCTYPE HTML>
<html>
<head>
<title>档案查询</title>
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
		<h1 class="mui-title">档案查询</h1>
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
	var param="<%=param %>";
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
		var param_ = eval("("+param+")");
		param_["page"] = page++;
		param_["rows"] = 20;
		var url=contextPath + "/TeeDamFilesController/queryAllArchivedFiles.action";
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:param_,
			timeout:10000,
			success:function(json){
				mui('#pullrefresh').pullRefresh().endPulldownToRefresh(false);
				json = eval("("+json+")");
				var render = [];
				for(var i=0;i<json.rows.length;i++){
					var item = json.rows[i];
					render.push("<li class=\"mui-table-view-cell mui-media\"     >");
					
					render.push("<div class=\"mui-media-body mui-slider-handle\">");
					render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
					render.push("<span>"+item.title+"</span></p>");
					render.push("<p style='font-size:12px'>文件编号："+item.number+"</p>");
					render.push("<p style='font-size:12px'>发/来文单位："+item.unit+"</p>");
					render.push("</div>");
					
					render.push("<div class='mui-slider-right mui-disabled'>");
					render.push("<a class='mui-btn mui-btn-blue ck'   sid='"+item.sid+"' >查看</a>");   
					var viewFlag=item.viewFlag;
					if(viewFlag==0){ //借閲
						render.push("<a class='mui-btn mui-btn-warning jy' sid='"+item.sid+"' >借阅</a>");   
					}
					render.push(" </div>");
					
					
					render.push("</li>");
				}
				$("#list").html(render.join(""));
				
				
				//查看
				$(".ck").each(function(i,obj){
					obj.removeEventListener("tap",detail);
					obj.addEventListener("tap",detail);
				});
				
				//查看
				$(".jy").each(function(i,obj){
					obj.removeEventListener("tap",borrow);
					obj.addEventListener("tap",borrow);
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
		var param_ = eval("("+param+")");
		param_["page"] = page++;
		param_["rows"] = 20;
		
		var url=contextPath + "/TeeDamFilesController/queryAllArchivedFiles.action";
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:param_,
			timeout:10000,
			success:function(json){
				mui('#pullrefresh').pullRefresh().endPullupToRefresh(false);
				json = eval("("+json+")");
				var render = [];
				for(var i=0;i<json.rows.length;i++){
					var item = json.rows[i];
                    render.push("<li class=\"mui-table-view-cell mui-media\"     >");
					
					render.push("<div class=\"mui-media-body mui-slider-handle\">");
					render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
					render.push("<span>"+item.title+"</span></p>");
					render.push("<p style='font-size:12px'>文件编号："+item.number+"</p>");
					render.push("<p style='font-size:12px'>发/来文单位："+item.unit+"</p>");
					render.push("</div>");
					
					
					render.push("<div class='mui-slider-right mui-disabled'>");
					render.push("<a class='mui-btn mui-btn-blue ck'   sid='"+item.sid+"' >查看</a>");   
					var viewFlag=item.viewFlag;
					if(viewFlag==0){ //借閲
						render.push("<a class='mui-btn mui-btn-warning jy' sid='"+item.sid+"' >借阅</a>");   
					}
					render.push(" </div>");
					
					
					
					render.push("</li>");
				}
				$("#list").append(render.join(""));
				


				//查看
				$(".ck").each(function(i,obj){
					obj.removeEventListener("tap",detail);
					obj.addEventListener("tap",detail);
				});
				
				//查看
				$(".jy").each(function(i,obj){
					obj.removeEventListener("tap",borrow);
					obj.addEventListener("tap",borrow);
				});
				
			},
			error:function(){
				
			}
		});
		
	}
	
	
	
	//详情
	function detail(){
		var sid = this.getAttribute("sid");
		//跳转详情页面
	    window.location = "detail.jsp?sid="+sid;
	}
	
	
	//借阅
	function borrow(){
		var sid = this.getAttribute("sid");
		var btnArray = ['确定', '取消'];
		mui.confirm('是否确认借阅该档案？', '提示', btnArray, function(e) {
			if (e.index == 0) {//是
				var url = contextPath + "/TeeFileBorrowController/borrow.action";
				mui.ajax(url,{
					type:"post",
					dataType:"html",
					data:{fileId:sid},
					timeout:10000,
					success:function(json){
						json = eval("("+json+")");
						if(json.rtState){
							window.location="result.jsp?param="+param;
						}
					}
				});

			}
		});		
	}
	
	
	mui.ready(function() {
		mui('#pullrefresh').pullRefresh().pullupLoading();
		
		backBtn.addEventListener("tap",function(){
			window.location.href = "index.jsp";
		});//返回

	});
	</script>
</body>
</html>
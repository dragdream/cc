<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<%
    String param=TeeStringUtil.getString(request.getParameter("param"));
    param = param.replace("\"", "\\\"");
    
    //如果是search代表查询
    String type=TeeStringUtil.getString(request.getParameter("type"));
%>
<html>
<head>
<title>我的借阅</title>
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
		<a id="searchBtn" class="mui-icon mui-icon-search mui-pull-right" style="color: #999;"></a>
		<h1 class="mui-title">我的借阅</h1>
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
	var type="<%=type %>";
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
		var param_ = {};
		if(type=="search"){//查询
			param_ = eval("("+param+")");
			param_["page"] = page++;
			param_["rows"] = 20;
		}else{
			param_ = {};
			param_["mj"]=" ";
			param_["hj"]=" ";
			param_["page"] = page++;
			param_["rows"] = 20;
		}
		
		
		var url=contextPath + "/TeeFileBorrowController/getMyBorrow.action";
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:param_ ,
			timeout:10000,
			success:function(json){
				mui('#pullrefresh').pullRefresh().endPulldownToRefresh(false);
				json = eval("("+json+")");
				var render = [];
				for(var i=0;i<json.rows.length;i++){
					var item = json.rows[i];
					var approveFlag=item.approveFlag;
					var returnFlag=item.returnFlag;
					var statusDesc="";
					if(approveFlag==0){
						statusDesc= "待审批";
					}else if(approveFlag==1){//已批准
						if(returnFlag==0){
							statusDesc= "已批准";
						}else if(returnFlag==1){
							statusDesc= "归还中";
						}else if(returnFlag==2){
							statusDesc= "已归还";
						}
					}else if(approveFlag==2){//未批准
						statusDesc= "未批准";
					}
					
					
					render.push("<li class=\"mui-table-view-cell mui-media\"     >");
					
					render.push("<div class=\"mui-media-body mui-slider-handle\">");
					render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
					render.push("<span>"+item.fileTitle+"</span></p>");
					render.push("<p style='font-size:12px'>文件编号："+item.fileNumber+"</p>");
					render.push("<p style='font-size:12px'>发/来文单位："+item.fileUnit+"</p>");
					render.push("<p style='font-size:12px'>借阅状态："+statusDesc+"</p>");
					render.push("<p style='font-size:12px'>借阅时间："+item.viewTimeStr+"</p>");
					render.push("</div>");
					
					render.push("<div class='mui-slider-right mui-disabled'>");
					if(approveFlag==0){//待审批
						render.push("<a class='mui-btn mui-btn-blue ck1'   sid='"+item.sid+"' >查看</a>");
						render.push("<a class='mui-btn mui-btn-danger sc'   sid='"+item.sid+"' >删除</a>");
					}else if(approveFlag==1){//已批准
						if(returnFlag==0){//已批准未归还
							render.push("<a class='mui-btn mui-btn-blue ck2'   sid='"+item.sid+"' >查看</a>");
							render.push("<a class='mui-btn mui-btn-warning gh'   sid='"+item.sid+"' >归还</a>");
						}else if(returnFlag==1){//归还中
							
						}else if(returnFlag==2){//已归还
							
						}
					}else if(approveFlag==2){//未批准
						render.push("<a class='mui-btn mui-btn-danger sc'   sid='"+item.sid+"' >删除</a>");
					}
					render.push(" </div>");
					
					
					render.push("</li>");
				}
				$("#list").html(render.join(""));
				
				
				//查看
				$(".ck1").each(function(i,obj){
					obj.removeEventListener("tap",basicDetail);
					obj.addEventListener("tap",basicDetail);
				});
				//查看
				$(".ck2").each(function(i,obj){
					obj.removeEventListener("tap",detail);
					obj.addEventListener("tap",detail);
				});
				
				//归还
				$(".gh").each(function(i,obj){
					obj.removeEventListener("tap",returnBack);
					obj.addEventListener("tap",returnBack);
				});
				//删除
				$(".sc").each(function(i,obj){
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
		var param_={};
		if(type=="search"){//查询
			param_ = eval("("+param+")");
			param_["page"] = page++;
			param_["rows"] = 20;
		}else{
			param_ ={};
			param_["mj"]=" ";
			param_["hj"]=" ";
			param_["page"] = page++;
			param_["rows"] = 20;
		}
		var url=contextPath + "/TeeFileBorrowController/getMyBorrow.action";
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
					var approveFlag=item.approveFlag;
					var returnFlag=item.returnFlag;
					var statusDesc="";
					if(approveFlag==0){
						statusDesc= "待审批";
					}else if(approveFlag==1){//已批准
						if(returnFlag==0){
							statusDesc= "已批准";
						}else if(returnFlag==1){
							statusDesc= "归还中";
						}else if(returnFlag==2){
							statusDesc= "已归还";
						}
					}else if(approveFlag==2){//未批准
						statusDesc= "未批准";
					}
					
					
					render.push("<li class=\"mui-table-view-cell mui-media\"     >");
					
					render.push("<div class=\"mui-media-body mui-slider-handle\">");
					render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
					render.push("<span>"+item.fileTitle+"</span></p>");
					render.push("<p style='font-size:12px'>文件编号："+item.fileNumber+"</p>");
					render.push("<p style='font-size:12px'>发/来文单位："+item.fileUnit+"</p>");
					render.push("<p style='font-size:12px'>借阅状态："+statusDesc+"</p>");
					render.push("<p style='font-size:12px'>借阅时间："+item.viewTimeStr+"</p>");
					render.push("</div>");
					
					render.push("<div class='mui-slider-right mui-disabled'>");
					if(approveFlag==0){//待审批
						render.push("<a class='mui-btn mui-btn-blue ck1'   fileId='"+item.fileId+"' >查看</a>");
						render.push("<a class='mui-btn mui-btn-danger sc'   sid='"+item.sid+"' >删除</a>");
					}else if(approveFlag==1){//已批准
						if(returnFlag==0){//已批准未归还
							render.push("<a class='mui-btn mui-btn-blue ck2'   fileId='"+item.fileId+"' >查看</a>");
							render.push("<a class='mui-btn mui-btn-warning gh'   sid='"+item.sid+"' >归还</a>");
						}else if(returnFlag==1){//归还中
							
						}else if(returnFlag==2){//已归还
							
						}
					}else if(approveFlag==2){//未批准
						render.push("<a class='mui-btn mui-btn-danger sc'   sid='"+item.sid+"' >删除</a>");
					}
					render.push(" </div>");
					
					
					render.push("</li>");
				}
				$("#list").append(render.join(""));
				
				
				//查看
				$(".ck1").each(function(i,obj){
					obj.removeEventListener("tap",basicDetail);
					obj.addEventListener("tap",basicDetail);
				});
				//查看
				$(".ck2").each(function(i,obj){
					obj.removeEventListener("tap",detail);
					obj.addEventListener("tap",detail);
				});
				
				//归还
				$(".gh").each(function(i,obj){
					obj.removeEventListener("tap",returnBack);
					obj.addEventListener("tap",returnBack);
				});
				//删除
				$(".sc").each(function(i,obj){
					obj.removeEventListener("tap",del);
					obj.addEventListener("tap",del);
				});
				
				
			},
			error:function(){
				
			}
		});
		
	}
	
	
	
	//简单详情
	function basicDetail(){
		var sid = this.getAttribute("fileId");
		//跳转详情页面
	    window.location = "basicDetail.jsp?sid="+sid;
	}
	
	
	//复杂详情
	function detail(){
		var sid = this.getAttribute("fileId");
		//跳转详情页面
	    window.location = "detail.jsp?sid="+sid;
	}
	
	//归还
	function returnBack(){
		var sid = this.getAttribute("sid");
		var btnArray = ['确定', '取消'];
		mui.confirm('是否确认归还该档案文件？', '提示', btnArray, function(e) {
			if (e.index == 0) {//是
				 var url = contextPath + "/TeeFileBorrowController/giveBack.action";
				mui.ajax(url,{
					type:"post",
					dataType:"html",
					data:{sid:sid},
					timeout:10000,
					success:function(json){
						json = eval("("+json+")");
						if(json.rtState){
							window.location="index.jsp";
						}
					}
				});

			}
		});		
	}
	
	
	
	//刪除
	function del(){
		var sid = this.getAttribute("sid");
		var btnArray = ['确定', '取消'];
		mui.confirm('是否确认删除该借阅记录？', '提示', btnArray, function(e) {
			if (e.index == 0) {//是
				var url = contextPath + "/TeeFileBorrowController/delBySid.action";
				mui.ajax(url,{
					type:"post",
					dataType:"html",
					data:{sid:sid},
					timeout:10000,
					success:function(json){
						json = eval("("+json+")");
						if(json.rtState){
							window.location="index.jsp";
						}
					}
				});

			}
		});		
	}
	
	mui.ready(function() {
		mui('#pullrefresh').pullRefresh().pullupLoading();
		
		
		backBtn.addEventListener("tap",function(){
			window.location.href = "../index.jsp";
		});//返回
		
		
		searchBtn.addEventListener("tap",function(){
			window.location.href = "search.jsp";
		});//返回

	});
	
	
	
	
	</script>
</body>
</html>
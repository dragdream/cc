<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
  //任务主键
  int  supId=TeeStringUtil.getInteger(request.getParameter("supId"),0);
  //当前登陆人
  TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);

  String typeName=TeeStringUtil.getString(request.getParameter("typeName"));
  int typeSid=TeeStringUtil.getInteger(request.getParameter("typeSid"), 0);
%>

<!DOCTYPE HTML>
<html>
<head>
<title>暂停恢复申请记录</title>
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
		<h1 class="mui-title">暂停恢复申请记录</h1>
	</header>
	<!-- 底部选项卡 -->
	<nav class="mui-bar mui-bar-tab ">
			<a id="b1" class="mui-tab-item" href="#" onclick="" >基本详情</a>
			<a id="b2" class="mui-tab-item " href="#" onclick="">办理情况</a>
			<a class="mui-tab-item  mui-active" href="#Popover_2" style="font-weight:bold">申请记录</a>
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
	<script>
	var typeName="<%=typeName%>";
	var typeSid=<%=typeSid%>;
	var supId=<%=supId%>;
	var loginUserUuid=<%=loginUser.getUuid()%>;
	var page = 1;
	var isLead=0;
	
	
	function doInit(){
		isLeader();
	}
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
	
	
	
	//判断当前登陆人  是不是任务的责任领导
	function isLeader(){
		var url=contextPath+"/supervisionController/getStatusAndRole.action";
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			async:false,
			data:{sid:supId},
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
					var data=json.rtData;
					isLead=	data.isLeader;	
				}
			}
		});
	}
	/**
	 * 下拉刷新具体业务实现
	 */
	function pulldownRefresh() {
		page = 1;
		var url=contextPath +"/supervisionApplyController/getPauseOrRecoverApplyListBySupId.action?supId="+supId;		
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
                    var type=item.type;
                    var typeDesc="";
    				if(type==1){
    					typeDesc="暂停申请";
    			    }else if(type==2){
    			    	typeDesc="恢复申请";
    			    }
	
    				var status=item.status;
    				var statusDesc="";
    				   if(status==0){
    					   statusDesc="待审批";
    				   }else if(status==1){
    					   statusDesc="已同意";
    				   }else if(status==2){
    					   statusDesc="已拒绝";
    				   }
    				   
    				 
					  render.push("<li class=\"mui-table-view-cell mui-media\"   sid='"+item.sid+"' >");
						
	                    render.push("<div class=\"mui-media-body mui-slider-handle\">");
						render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
						render.push("<span>"+item.content.substr(0,10)+"..."+"</span></p>");
						render.push("<p style='font-size:12px'>申请类型："+typeDesc+"</p>");
						render.push("<p style='font-size:12px'>申请状态："+statusDesc+"</p>");
						render.push("<p style='font-size:12px'>申请时间："+item.createTimeStr+"</p>");
						render.push("</div>");
						
						
						render.push("<div class='mui-slider-right mui-disabled'>");
 
					    if(status==0){
							if(isLead==1){
								render.push("<a class='mui-btn mui-btn-blue' sid='"+item.sid+"'>同意</a>");
								render.push("<a class='mui-btn mui-btn-red' sid='"+item.sid+"'>拒绝</a>");
							}	
						}
					    render.push("</div>");
					    
						render.push("</li>");
				}
				$("#list").html(render.join(""));
				
				$(".mui-media").each(function(i,obj){
					obj.removeEventListener("tap",detail);
					obj.addEventListener("tap",detail);
				});
				//拒绝
				$(".mui-btn-red").each(function(i,obj){
					obj.removeEventListener("tap",refuse);
					obj.addEventListener("tap",refuse);
				});
				
				//同意
				$(".mui-btn-blue").each(function(i,obj){
					obj.removeEventListener("tap",agree);
					obj.addEventListener("tap",agree);
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
		
		var url=contextPath +"/supervisionApplyController/getPauseOrRecoverApplyListBySupId.action?supId="+supId;		
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:{state:-1,rows:20,page:page++},
			timeout:10000,
			success:function(json){
				
				json = eval("("+json+")");
				var render = [];
				for(var i=0;i<json.rows.length;i++){
					var item = json.rows[i];
                    var type=item.type;
                    var typeDesc="";
    				if(type==1){
    					typeDesc="暂停申请";
    			    }else if(type==2){
    			    	typeDesc="恢复申请";
    			    }
	
    				var status=item.status;
    				var statusDesc="";
    				   if(status==0){
    					   statusDesc="待审批";
    				   }else if(status==1){
    					   statusDesc="已同意";
    				   }else if(status==2){
    					   statusDesc="已拒绝";
    				   }
    				   
    				 
					  	render.push("<li class=\"mui-table-view-cell mui-media\"   sid='"+item.sid+"' >");
						
	                    render.push("<div class=\"mui-media-body mui-slider-handle\">");
						render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
						render.push("<span>"+item.content.substr(0,10)+"..."+"</span></p>");
						render.push("<p style='font-size:12px'>申请类型："+typeDesc+"</p>");
						render.push("<p style='font-size:12px'>申请状态："+statusDesc+"</p>");
						render.push("<p style='font-size:12px'>申请时间："+item.createTimeStr+"</p>");
						render.push("</div>");
					    
						
						
						render.push("<div class='mui-slider-right mui-disabled'>");
					  	if(status==0){
							if(isLead==1){
								render.push("<a class='mui-btn mui-btn-blue' sid='"+item.sid+"'>同意</a>");
								render.push("<a class='mui-btn mui-btn-red' sid='"+item.sid+"'>拒绝</a>");
							}	
						}
					    render.push("</div>");
					    
						render.push("</li>");
				}
				$("#list").append(render.join(""));
				
				$(".mui-media").each(function(i,obj){
					obj.removeEventListener("tap",detail);
					obj.addEventListener("tap",detail);
				});
				//拒绝
				$(".mui-btn-red").each(function(i,obj){
					obj.removeEventListener("tap",refuse);
					obj.addEventListener("tap",refuse);
				});
				
				//同意
				$(".mui-btn-blue").each(function(i,obj){
					obj.removeEventListener("tap",agree);
					obj.addEventListener("tap",agree);
				});
				
				mui('#pullrefresh').pullRefresh().endPullupToRefresh(false);
			},
			error:function(){
				
			}
		});
		
	}
	
	
	
	//申请详情
	function detail(){
		window.event.cancelBubble=true;
		var sid = this.getAttribute("sid");
		window.location = "applyDetail.jsp?sid="+sid;
	}
	
	

	mui.ready(function() {
		mui('#pullrefresh').pullRefresh().pullupLoading();
		
		backBtn.addEventListener("tap",function(){
			window.location = '../list.jsp?typeName='+typeName+"&&typeSid="+typeSid;
		});//返回
		
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
	});
	
	
	//同意
	function agree(){
		window.event.cancelBubble=true;
		var sid = this.getAttribute("sid");
		if(window.confirm("是否确认同意该申请？")){
			var url = contextPath + "/supervisionApplyController/approve.action";
			var para = {sid:sid,status:1};
			mui.ajax(url,{
				type:"post",
				dataType:"html",
				data:para,
				timeout:10000,
				success:function(json){
					json = eval("("+json+")");
					if(json.rtState){
						alert("已同意！");
						window.location.reload();
					}
				}
			});	
		}
	}
	
	
	//拒绝
	function refuse(){
		window.event.cancelBubble=true;
		var sid = this.getAttribute("sid");
		if(window.confirm("是否确认拒绝该申请？")){
			mui.prompt('请输入拒绝原因？','拒绝理由',"",function(e){
			    if(e.index == 1){
			        //mui.toast(e.value);
			        var url = contextPath + "/supervisionApplyController/approve.action";
			  	    var para = {sid:sid,status:2,reason:e.value};
			  	    mui.ajax(url,{
						type:"post",
						dataType:"html",
						data:para,
						timeout:10000,
						success:function(json){
							json = eval("("+json+")");
							if(json.rtState){
								alert("已拒绝！");
								window.location.reload();
							}
						}
					 });	
			    }else{
			        mui.toast('您取消了输入');
			    }
			});
		}
	}
	
	</script>
</body>
</html>
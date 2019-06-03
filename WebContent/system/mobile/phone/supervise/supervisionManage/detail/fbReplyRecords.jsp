<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
  //反馈情况的主键
  int fbId=TeeStringUtil.getInteger(request.getParameter("fbId"), 0);
  TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
  //任务主键
  int supId=TeeStringUtil.getInteger(request.getParameter("supId"), 0);

  String typeName=TeeStringUtil.getString(request.getParameter("typeName"));
  int typeSid=TeeStringUtil.getInteger(request.getParameter("typeSid"),0);
%>

<!DOCTYPE HTML>
<html>
<head>
<title>回复列表</title>
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
		<h1 class="mui-title">回复列表</h1>
	</header>
	<!--下拉刷新容器-->
	<div id="pullrefresh" class="mui-content mui-scroll-wrapper">
		<div class="mui-scroll">
			<!--数据列表-->
			<ul class="mui-table-view" id="list">
				
			</ul>
		</div>
	</div>
	
	<nav class="mui-bar mui-bar-tab" id="footerDiv"  >
		<a id="a1" class="mui-tab-item mui-active">
			<span class="mui-tab-label" >基本详情</span>
		</a>
		<a id="a2" class="mui-tab-item mui-active" style="font-weight:bold">
			<span class="mui-tab-label" >回复列表</span>
		</a>
    </nav>


	<script>
	var typeName="<%=typeName%>";
	var typeSid=<%=typeSid%>;
	var supId=<%=supId%>;
	var fbId=<%=fbId %>;
	var loginUserUuid=<%=loginUser.getUuid()%>;
	
	/**
	 * 获取回复列表
	 */
	function doInit() {
		var url=contextPath+"/supFeedBackReplyController/getReplyListByFbId.action";		
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:{fbId:fbId},
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				var render = [];
				for(var i=0;i<json.rtData.length;i++){
					var item = json.rtData[i];
					
				      
					    render.push("<li class=\"mui-table-view-cell mui-media\"   sid='"+item.sid+"' >");
						
	                    render.push("<div class=\"mui-media-body mui-slider-handle\">");
						render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
						render.push("<span>"+item.content.substr(0,10)+"..."+"</span></p>");
						render.push("<p style='font-size:12px'>回复人员："+item.createrName+"</p>");
						render.push("<p style='font-size:12px'>回复时间："+item.createTimeStr+"</p>");
						render.push("</div>");
						
						render.push("<div class='mui-slider-right mui-disabled'>");
						
						var createrId=item.createrId;
						if(createrId==loginUserUuid){//当前登陆人 是创建人 
							
							render.push("<a class='mui-btn mui-btn-red' sid='"+item.sid+"'>删除</a>"); 
							
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
				
			},
			error:function(){
				
			}
		});
		
	}
	

	
	
	//回复详情
	function detail(){
		window.event.cancelBubble=true;
		var sid = this.getAttribute("sid");
		window.location = "fbReplyDetail.jsp?sid="+sid+"&&typeName="+typeName+"&&typeSid="+typeSid+"&&supId="+supId+"&&fbId="+fbId;
	}
	
	
	//删除任务
	function del(){
		window.event.cancelBubble=true;
		var sid = this.getAttribute("sid");
		if(window.confirm("是否确认刪除该回复？")){
		    var url = contextPath + "/supFeedBackReplyController/delBySid.action";
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
	
	

	
	mui.ready(function() {
		
		backBtn.addEventListener("tap",function(){
			window.location = "feedBackRecords.jsp?supId="+supId+"&&typeName="+typeName+"&&typeSid="+typeSid;
		});//返回
		
		
		//基本详情
		a1.addEventListener("tap",function(){
			window.location = "fbDetail.jsp?sid="+fbId+"&&supId="+supId+"&&typeName="+typeName+"&&typeSid="+typeSid;
		});
		
	});
	</script>
</body>
</html>
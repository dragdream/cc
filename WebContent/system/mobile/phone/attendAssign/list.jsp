<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
   int status=TeeStringUtil.getInteger(request.getParameter("status"),1);//默认今日
   String  statusDesc="";
   String startTimeStr=TeeStringUtil.getString(request.getParameter("startTimeStr"));
   String endTimeStr=TeeStringUtil.getString(request.getParameter("endTimeStr"));
   
   if(status==1){
	   statusDesc="今日";  
   }else if(status==2){
	   statusDesc="昨日";  
   }else if(status==3){
	   statusDesc="本周";  
   }else if(status==4){
	   statusDesc="本月";  
   }else if(status==5){
	   statusDesc=startTimeStr+"~"+endTimeStr;  
   }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/system/mobile/mui/header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>外勤轨迹</title>
</head>


<body>
   <header class="mui-bar mui-bar-nav">
		<span class="mui-icon mui-icon-back" id="backBtn"></span>
		<h1 class="mui-title">筛选（<%=statusDesc %>）</h1>
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
		    <li class="mui-table-view-cell" onclick="window.location = 'list.jsp?status=1';">今日</li>
		    <li class="mui-table-view-cell" onclick="window.location = 'list.jsp?status=2';">昨日</li>
		    <li class="mui-table-view-cell" onclick="window.location = 'list.jsp?status=3';">本周</li>
		    <li class="mui-table-view-cell" onclick="window.location = 'list.jsp?status=4';">本月</li>
		    <li class="mui-table-view-cell" onclick="window.location = 'selectDate.jsp?status=5';">指定时间范围</li>
		  </ul>
	</div>
	
	
	
	<script>
	var status=<%=status%>;
	var endTimeStr="<%=endTimeStr %>";
	var startTimeStr="<%=startTimeStr %>";
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
		var url=contextPath+"/TeeAttendAssignController/getListByStatus.action?status="+status+"&endTimeStr="+endTimeStr+"&startTimeStr="+startTimeStr;
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
					var attachIds=item.attachIds;
				    var attachNames="";
				    
					render.push("<li class=\"mui-table-view-cell mui-media\"   sid='"+item.sid+"' >");
					
					render.push("<div class=\"mui-media-body\">");
					render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
					render.push("<span>上报地点："+item.address+"</span></p>");
					render.push("<p style='font-size:12px'>上报时间："+item.createTimeStr+"</p>");
					render.push("<p style='font-size:12px'>备注："+item.remark+"</p>");
					if(attachIds!=""){
						var attIdArray=attachIds.split(",");
						render.push("<p style='font-size:12px'>");
						render.push("<div>");
						
                        for(var n=0;n<attIdArray.length;n++){
                        	attachNames+="图片"+attIdArray[n];
    						if(n!=attIdArray.length-1){
    							attachNames+=",";
    						}
						}
						for(var n=0;n<attIdArray.length;n++){
							
							render.push("<img class=\"pic\" src='/attachmentController/downFile.action?id="+attIdArray[n]+"'  style=\"width:60px;height:60px;margin-right:15px \" attachIds='"+attachIds+"'  attachNames='"+attachNames+"' attachId="+attIdArray[n]+"  />");
							
						}
						render.push("</div>");
						render.push("</p>");
					}
				
					render.push("</div>");
					render.push("</li>");
				}
				$("#list").html(render.join(""));
				$(".pic").each(function(i,obj){
					obj.removeEventListener("tap",view);
					obj.addEventListener("tap",view);
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
		var url=contextPath+"/TeeAttendAssignController/getListByStatus.action?status="+status+"&endTimeStr="+endTimeStr+"&startTimeStr="+startTimeStr;
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
					var attachIds=item.attachIds;
					var attachNames="";
                    render.push("<li class=\"mui-table-view-cell mui-media\"   sid='"+item.sid+"' >");
					
					render.push("<div class=\"mui-media-body\">");
					render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
					render.push("<span>上报地点："+item.address+"</span></p>");
					render.push("<p style='font-size:12px'>上报时间："+item.createTimeStr+"</p>");
					render.push("<p style='font-size:12px'>备注："+item.remark+"</p>");
					if(attachIds!=""){
						var attIdArray=attachIds.split(",");
						render.push("<p style='font-size:12px'>");
						render.push("<div>");
						
                        for(var n=0;n<attIdArray.length;n++){
                        	attachNames+="图片"+attIdArray[n];
    						if(n!=attIdArray.length-1){
    							attachNames+=",";
    						}
						}
						for(var n=0;n<attIdArray.length;n++){
							
							render.push("<img class=\"pic\" src='/attachmentController/downFile.action?id="+attIdArray[n]+"'  style=\"width:60px;height:60px;margin-right:15px \" attachIds='"+attachIds+"'  attachNames='"+attachNames+"' attachId="+attIdArray[n]+"  />");
							
						}
						render.push("</div>");
						render.push("</p>");
					}
					
					render.push("</div>");
					render.push("</li>");
				}
				$("#list").append(render.join(""));
				
				$(".pic").each(function(i,obj){
					obj.removeEventListener("tap",view);
					obj.addEventListener("tap",view);
				});
			},
			error:function(){
				
			}
		});
		
	}

	
	//图片详情
	function view(){
		var attachNames = this.getAttribute("attachNames");
		var attachId = this.getAttribute("attachId");
		var attachIds = this.getAttribute("attachIds");
		PicExplore(attachIds.split(","),attachNames.split(","),attachId);
	}
	
	
	mui.ready(function() {
		mui('#pullrefresh').pullRefresh().pullupLoading();
		
		backBtn.addEventListener("tap",function(){
			window.location.href = "report.jsp";
		});//返回
		
		
	});
	</script>
</body>
</html>
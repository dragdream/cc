<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
   int diskId=TeeStringUtil.getInteger(request.getParameter("diskId"),0);
   String diskName=TeeStringUtil.getString(request.getParameter("diskName"));//文件夹名称
   String projectId=TeeStringUtil.getString(request.getParameter("projectId"));//项目主键
%>

<!DOCTYPE HTML>
<html>
<head>
<title>项目文档 </title>
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
		<h1 class="mui-title"><%=diskName %></h1>
		
	</header>
	<!--下拉刷新容器-->
	<div id="pullrefresh" class="mui-content ">
		<div class="">
			<!--数据列表-->
			<ul class="mui-table-view" id="list">
				
			</ul>
		</div>
	</div>
	
	
	
	<script>
	var diskId=<%=diskId%>;
	var projectId="<%=projectId%>";
	var page = 1;
	function doInit(){
		//GetFile('591','icon_xmsp.png','e25578f3e5e54677a118063196b455f8.png');
		var url=contextPath + "/projectFileController/getFileListByDiskId.action?diskId="+diskId+"&&projectId="+projectId;
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			//data:{state:-1,rows:20,page:page++},
			timeout:10000,
			success:function(json){
				mui('#pullrefresh').pullRefresh().endPulldownToRefresh(false);
				json = eval("("+json+")");
				var render = [];
				for(var i=0;i<json.rows.length;i++){
					var item = json.rows[i];
					var fileExt=item.fileExt;
					var src="../imgs/icon_txt.png";
					if(fileExt=="png"||fileExt=="jpg"||fileExt=="jpeg"){//图片
						src="../imgs/icon_pic.png";
					}else if(fileExt=="doc"||fileExt=="docx"){//word文档
						src="../imgs/icon_word.png";
					}else if(fileExt=="ppt"||fileExt=="pptx"){//ppt
						src="../imgs/icon_ppt.png";
					}else if(fileExt=="xls"||fileExt=="xlsx"){//excel文件
						src="../imgs/icon_excel.png";
					}else if(fileExt=="rar"||fileExt=="zip"){//压缩包
						src="../imgs/icon_rar.png";
					}else{
						src="../imgs/icon_txt.png";
					}
					
					render.push("<li class=\"mui-table-view-cell mui-media\"  attachId="+item.attchId+" fileName='"+item.fileName+"'  attachName='"+item.attchName+"'   >");
					
					render.push("<img class=\"mui-media-object mui-pull-left\" src='"+src+"'>");
			        render.push("<div class=\"mui-media-body\">"); 
			        render.push(item.fileName); 
			        render.push("<p class='mui-ellipsis'>"+item.createrName+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+item.createTimeStr+"</p>");        
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

	
	
	//项目详情
	function detail(){
		var attachId= this.getAttribute("attachId");
		var fileName=this.getAttribute("fileName");
		var attachName=this.getAttribute("attachName");
		GetFile(attachId,fileName,attachName);
	}
	
	
	
	
	mui.ready(function() {
		
		backBtn.addEventListener("tap",function(){
             history.go(-1);
		});//返回
		
		
		
	});
	</script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid");
	String thumbFilePath = request.getParameter("thumbFilePath");
	String rootId = request.getParameter("rootId");
	String folder = request.getParameter("folder");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<!-- include CSS always before including js -->
<link type="text/css" rel="stylesheet" href="css/tn3.css"></link>
<!-- include tn3 plugin -->

<%@ include file="/system/mobile/mui/header.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<script type="text/javascript" src="js/iscroll.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/system/mobile/js/tools.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>图片预览</title>
<script>
var sid = "<%=sid%>";
var firstThumbFilePath = "<%=thumbFilePath%>";
var index=0;
var curPage=0;
var contextPath = "<%=request.getContextPath()%>";
var rootId = "<%=rootId%>";
var folder = "<%=folder%>";
function doInit(){
	//getPictureList(sid);
	getPictureList2(sid);
	
	 $("body").on("touchstart", function (e) {
         e.preventDefault();
         startX = e.originalEvent.changedTouches[0].pageX,
             startY = e.originalEvent.changedTouches[0].pageY;
     });
     $("body").on("touchmove", function (e) {
         e.preventDefault();
         moveEndX = e.originalEvent.changedTouches[0].pageX,
             moveEndY = e.originalEvent.changedTouches[0].pageY,
             X = moveEndX - startX,
             Y = moveEndY - startY;

         if (Math.abs(X) > Math.abs(Y) && X > 0) {
             getPictureList(sid);
         }
         else if (Math.abs(X) > Math.abs(Y) && X < 0) {
        	 getPictureList1(sid);
         }
         else if (Math.abs(Y) > Math.abs(X) && Y > 0 && canSlide) {
             scrollUp();
         }
         else if (Math.abs(Y) > Math.abs(X) && Y < 0 && canSlide) {
             scrollDown();
         }
         else {
            
         }
     });
     $("body").on("tap","img",function(){
		  window.location.href="items.jsp?sid="+sid+"&rootId="+rootId+"&folder="+folder;
	});
}

function getPictureList2(sid){
	var url = contextPath+"/teeImgBaseController/getPictureList.action";
	var jsonRs = tools.requestJsonRs(url,{id:sid});
	var html ="";
	var data = jsonRs.rtData;
	var thumbFilePath = contextPath+"/teeImgBaseController/loadImage.action?filePath="+firstThumbFilePath;
	html+="<img style='width:100%;' src=\""+thumbFilePath+"\" />";
	$("#content").html(html);
}

function getPictureList1(sid){
	var url = contextPath+"/teeImgBaseController/getPictureList.action";
	var jsonRs = tools.requestJsonRs(url,{id:sid});
	var html ="";
	var data = jsonRs.rtData;
	var j=data.length-1;
	var thumbFilePath="";
	for(var i= 0;i<data.length;i++){
		if(firstThumbFilePath==data[i].thumbFilePath){
			if(i+1<data.length-1){
				j=i+1;
			}
			firstThumbFilePath=data[j].thumbFilePath;
	        thumbFilePath = contextPath+"/teeImgBaseController/loadImage.action?filePath="+data[j].thumbFilePath;
			break;
		}
		
	}
	$("#content").html("<img src=\""+thumbFilePath+"\" />");
}

function getPictureList(sid){
	var url = contextPath+"/teeImgBaseController/getPictureList.action";
	var jsonRs = tools.requestJsonRs(url,{id:sid});
	var html ="";
	var data = jsonRs.rtData;
	var j=0;
	var thumbFilePath="";
	for(var i= 0;i<data.length;i++){
		if(firstThumbFilePath==data[i].thumbFilePath){
			if(i-1>0){
				j=i-1;
			}
		    firstThumbFilePath=data[j].thumbFilePath;
	        thumbFilePath = contextPath+"/teeImgBaseController/loadImage.action?filePath="+data[j].thumbFilePath;
			break;
		}
	}
	$("#content").html("<img src=\""+thumbFilePath+"\" />");
}
</script>
</head>
<body style="margin:0px;background:black" onload="doInit()">
<!-- 代码 开始 -->
<div id="content" style="margin-top:20%;width:100%;">
   <div class="tn3 album">
	    <ol id="tn3ol">
	    	
	    </ol>
	</div>
</div>
</body>
</html>
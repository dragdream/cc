<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid");
	String thumbFilePath = request.getParameter("thumbFilePath");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<!-- include CSS always before including js -->
<link type="text/css" rel="stylesheet" href="css/tn3.css"></link>
<!-- include tn3 plugin -->

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.tn3lite.min.js"></script>
<script src="<%=request.getContextPath() %>/common/js/tools.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>图片预览</title>
<script>
var sid = "<%=sid%>";
var firstThumbFilePath = "<%=thumbFilePath%>";
var index=0;
var curPage=0;
var contextPath = "<%=request.getContextPath()%>";

function doInit(){
	getPictureList(sid);
	var tn1 = $('.mygallery').tn3({
		skinDir:"skins",
		imageClick:"fullscreen",
		image:{
		maxZoom:1,
		crop:true,
		clickEvent:"dblclick",
		transitions:[{
		type:"blinds"
		},{
		type:"grid"
		},{
		type:"grid",
		duration:460,
		easing:"easeInQuad",
		gridX:1,
		gridY:8,
		// flat, diagonal, circle, random
		sort:"random",
		sortReverse:false,
		diagonalStart:"bl",
		// fade, scale
		method:"scale",
		partDuration:360,
		partEasing:"easeOutSine",
		partDirection:"left"
		}]
		}
		});
	
}

function getPictureList(sid){
	var url = contextPath+"/teeImgBaseController/getPictureList.action";
	var jsonRs = tools.requestJsonRs(url,{"id":sid});
	var html ="";
	var data = jsonRs.rtData;
	for(var i= 0;i<data.length;i++){
		var thumbFilePath = contextPath+"/teeImgBaseController/loadImage.action?filePath="+data[i].thumbFilePath;
		var filePath = contextPath+"/teeImgBaseController/loadImage.action?filePath="+data[i].filePath;
		html+="<li>";
		html+="<h4>"+data[i].fileName+"</h4>";
		if(data[i].thumbFilePath==firstThumbFilePath){
			index = i;
			curPage = Math.floor(i/11);
		}
		html+="<h4>"+data[i].fileName+"</h4>";
		html+="<a href=\""+filePath+"\"><img src=\""+thumbFilePath+"\" /></a>";
		html+="</li>";
	}
	$("#tn3ol").html(html);
}
</script>
</head>
<body style="margin:0px;background:black" onload="doInit()">
<!-- 代码 开始 -->
<div id="content">
    <div class="mygallery clearfix">
	<div class="tn3 album">
	    <ol id="tn3ol">
	    	
	    </ol>
	</div>
    </div>
</div>
</body>
</html>
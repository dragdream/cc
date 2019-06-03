<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% 
	int id = TeeStringUtil.getInteger(request.getParameter("id"),0);
    String pics=TeeStringUtil.getString(request.getParameter("pics"));
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>

<meta charset="utf-8" />

<title>图片预览</title>

<style>

/* 代码整理：jQuery插件库 www.jq22.com */

*{ margin:0; padding:0;}

body {background: #333;}

#pageContent {width: 980px;	height: 500px;overflow: hidden;position:relative;margin:50px auto;}

#imgContainer {width: 980px;height: 500px;}

#positionButtonDiv {background: rgb(58, 56, 63);background: rgba(58, 56, 63, 0.8);border: solid 1px #100000;color: #fff;padding: 8px;text-align: left;position: absolute;right:35px;top:35px;}

#positionButtonDiv .positionButtonSpan img {float: right;border: 0;}

.positionMapClass area {cursor: pointer;}

.zoomButton {border: 0;	cursor: pointer;}

.zoomableContainer {background-image: url("images/transparent.png");}

</style>

</head>

<body>



<!--代码部分begin-->

<div id="pageContent">
    
    
	<div id="imgContainer">

    	<img id="imageFullScreen" src="<%=contextPath+"/attachmentController/downFile.action?id="+id %>"/>

    </div>

	<div id="positionButtonDiv">

		<p><span> <img id="zoomInButton" class="zoomButton" src="images/zoomIn.png" title="zoom in" alt="zoom in" /> <img id="zoomOutButton" class="zoomButton" src="images/zoomOut.png" title="zoom out" alt="zoom out" /> </span> </p>

		<p>

        <span class="positionButtonSpan">

			<map name="positionMap" class="positionMapClass">

				<area id="topPositionMap" shape="rect" coords="20,0,40,20" title="move up" alt="move up"/>

                <area id="leftPositionMap" shape="rect" coords="0,20,20,40" title="move left" alt="move left"/>

				<area id="rightPositionMap" shape="rect" coords="40,20,60,40" title="move right" alt="move right"/>

				<area id="bottomPositionMap" shape="rect" coords="20,40,40,60" title="move bottom" alt="move bottom"/>

			</map>

			<img src="images/position.png" usemap="#positionMap" />

         </span>

         </p>

	</div>
    
    
</div>


<script src="js/e-smart-zoom-jquery.min.js"></script>

<!--[if lt IE 9]>

    <script src="js/html5shiv.min.js"></script>

<![endif]-->

<script>
var  id=<%=id %>;//当前的id
var  pics="<%=pics %>";//所有图片的id串

    $(document).ready(function() {        

        $('#imageFullScreen').smartZoom({'containerClass':'zoomableContainer'});        

        $('#topPositionMap,#leftPositionMap,#rightPositionMap,#bottomPositionMap').bind("click", moveButtonClickHandler);

        $('#zoomInButton,#zoomOutButton').bind("click", zoomButtonClickHandler);

        

        function zoomButtonClickHandler(e){

            var scaleToAdd = 0.8;

            if(e.target.id == 'zoomOutButton')

                scaleToAdd = -scaleToAdd;

            $('#imageFullScreen').smartZoom('zoom', scaleToAdd);

        }        

        function moveButtonClickHandler(e){

            var pixelsToMoveOnX = 0;

            var pixelsToMoveOnY = 0;

    

            switch(e.target.id){

                case "leftPositionMap":

                    pixelsToMoveOnX = 50;	

                break;

                case "rightPositionMap":

                    pixelsToMoveOnX = -50;

                break;

                case "topPositionMap":

                    pixelsToMoveOnY = 50;	

                break;

                case "bottomPositionMap":

                    pixelsToMoveOnY = -50;	

                break;

            }

            $('#imageFullScreen').smartZoom('pan', pixelsToMoveOnX, pixelsToMoveOnY);

        } 

    });

//上一页
function previous(){
	var picArray=pics.split(",");
	var curIndex=0; 
	for(var i=0;i<picArray.length;i++){
		if(picArray[i]==id){//匹配当前的位置
			curIndex=i;
		    break;
		}
	}
	if(curIndex==0){
		alert("当前已是第一张！");
	}else{
		window.location.href="picExplore.jsp?id="+picArray[curIndex-1]+"&&pics="+pics;
	}
}

//下一页
function next(){
	var picArray=pics.split(",");
	var maxIndex=picArray.length-1;
	var curIndex=0; 
	for(var i=0;i<picArray.length;i++){
		if(picArray[i]==id){//匹配当前的位置
			curIndex=i;
		    break;
		}
	}
	if(curIndex==maxIndex){
		alert("当前已是最后一张！");
	}else{
		window.location.href="picExplore.jsp?id="+picArray[curIndex+1]+"&&pics="+pics;
	}
	
}
</script>

<!--代码部分end-->


<!-- 上一页  下一页翻页 -->
<div style="text-align:center;left:0px;right:0px;bottom:0px;height:30px;position: absolute;z-index: 10000000000000000000000000000">
   <img alt="" src="images/previous.png" onclick="previous();">
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   <img alt="" src="images/next.png" onclick="next();">
</div>
</body>

</html>

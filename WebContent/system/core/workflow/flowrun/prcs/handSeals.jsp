<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String itemId = request.getParameter("itemId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<!--[if lt IE 9]>
<script type="text/javascript" src="<%=contextPath %>/common/jSignature/libs/flashcanvas.js"></script>
<![endif]-->
<script src="<%=contextPath %>/common/easyui/jquery.min.js"></script>
<script src="<%=contextPath %>/common/jSignature/libs/modernizr.js"></script>
<script src="<%=contextPath %>/common/jSignature/src/jSignature.js"></script>
<script src="<%=contextPath %>/common/jSignature/src/plugins/jSignature.CompressorBase30.js"></script>
<script src="<%=contextPath %>/common/jSignature/src/plugins/jSignature.CompressorSVG.js"></script>
<script src="<%=contextPath %>/common/jSignature/src/plugins/jSignature.UndoButton.js?v=2018072607"></script> 

<style>
#signature {
	border: 1px dotted black;
}
</style>
<script>
var itemId = "<%=itemId%>";
var contextPath = "<%=contextPath %>";

var xparent;
if(window.dialogArguments){
	xparent = window.dialogArguments;
}else if(window.opener){
	xparent = opener;
}else{
	xparent = window;
}

function doInit(){
	var itemObj = xparent.$("#"+itemId);
	var width = parseInt(itemObj.attr("w"));
	var height = parseInt(itemObj.attr("h"));
	
	
	var screenWidth = parseInt($(window).width());
	var screenHeight = parseInt($(window).height());
	
// 	var delta = width/height;
	
// 	if(screenWidth<screenHeight){//如果是竖屏，则将原高度增加为一半
// 		height = screenHeight/2;
// 	}else{//如果是横屏，则宽度设置为高度
// 		height = screenHeight/3*2;
// 	}
// 	width = delta*height;
	
	window.sigdiv = $("#signature").jSignature({
		'UndoButton':true,
		'background-color':null,
		'decor-color':null,
		'width':screenWidth,
		'height':screenHeight-30,
		'lineWidth':itemObj.attr("weight"),
		color:itemObj.attr("color")});
}


function addSeal(){
	var svgXml = window.sigdiv.jSignature("getData", "image/svg+xml")+"";
	svgXml = svgXml.substring(14,svgXml.length);
	var json = tools.requestJsonRs(contextPath+"/svg2base64/convert.action",{svg:svgXml});
	xparent.doAddH5Seal(itemId,"data:image/png;base64,"+json.rtData);
	CloseWindow();
}


function closeView(){
	CloseWindow();
}

/**
 * 统一关闭窗体方法
 */
function CloseWindow(){
	 try{
		 if(window.external && window.external.IM_OA){
			 window.external.IM_Close();
		 }else{
			window.close();
		 }
	 }catch(e){
		 window.close();
	 }
}
</script>
</head>
<body onload="doInit()" style="margin:0px;padding:0px;overflow:hidden">
<div style="position:absolute;left:0px;right:0px;top:0px;height:30px;padding-left:10px;line-height:25px">
<button onclick="addSeal()">确定</button>
<button onclick="closeView()">关闭</button>
</div>
<div id="container" style="position:absolute;left:0px;right:0px;top:30px;bottom:0px;overflow:hidden">
	<div id="signature"></div>
</div>
</body>
</html>
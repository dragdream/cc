<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">

<!-- jQuery库 -->
<script src="/common/easyui/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="/common/styles/style1/css/style.css"/>

<script type="text/javascript" >
window.UEDITOR_HOME_URL = "/common/ueditor/";
$.browser = {};
$.browser.mozilla = /firefox/.test(navigator.userAgent.toLowerCase());
$.browser.webkit = /webkit/.test(navigator.userAgent.toLowerCase());
$.browser.opera = /opera/.test(navigator.userAgent.toLowerCase());
$.browser.msie = /msie/.test(navigator.userAgent.toLowerCase());
</script>

<!-- Bootstrap通用UI组件 -->
<script src="/common/bootstrap/js/bootstrap.min.js"></script>

<link rel="stylesheet" type="text/css" href="/common/bootstrap/css/bootstrap-ie7.css"/>
<link rel="stylesheet" type="text/css" href="/common/bootstrap/css/bootstrap.css"/>




<!-- JBOX通用UI组件 -->
<script type="text/javascript" src="/common/jbox-v2.3/jBox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="/common/jbox-v2.3/jBox/i18n/jquery.jBox-zh-CN.js"></script>
<link rel="stylesheet" type="text/css" href="/common/jbox-v2.3/jBox/Skins/Blue/jbox.css" />
<script>

</script>
<!-- 其他工具库类 -->
<script src="/common/js/tools.js"></script>
<script src="/common/js/sys.js"></script>
<script src="/common/js/sysUtil.js"></script>
<script src="/common/js/src/orgselect.js"></script>
<script type="text/javascript" src="/common/js/easyuiTools.js"></script>

<!-- jQuery Tooltip -->
<script type="text/javascript" src="/common/tooltip/jquery.tooltip.min.js"></script>
<link rel="stylesheet" href="/common/tooltip/jquery.tooltip.css" type="text/css"/>

<!-- 图片预览器 -->
<script type="text/javascript" src="/common/js/picexplore/jquery.mousewheel.js"></script>
<script type="text/javascript" src="/common/js/picexplore/picexplore.js"></script>
<link rel="stylesheet" href="/common/js/picexplore/picexplore.css" type="text/css"/>

<script type="text/javascript">

/** 常量定义 **/
var TDJSCONST = {
  YES: 1,
  NO: 0
};
/** 变量定义 **/
var contextPath = "";
var imgPath = "/common/styles/style1/img";
var cssPath = "/common/styles/style1/css";
var stylePath = "/common/styles";

var cssPathSecond = "/system/frame/2/styles/style1/css";
var imgPathSecond = "/system/frame/2/styles/style1/img";
var loginOutText = "感谢您使用，再见！";
var uploadFlashUrl = "/common/swfupload/swfupload.swf";
var commonUploadUrl = "/attachmentController/commonUpload.action";
var systemImagePath = "/common/images";
var xparent;
if(window.dialogArguments){
	xparent = window.dialogArguments;
}else if(window.opener){
	xparent = opener;
}else{
	xparent = window;
}
function parseNumber(value, defValue) {
  if (isNaN(value)) {
    return defValue;
  }
  return value * 1;
}


</script>
<style>
body {
scrollbar-arrow-color: #777777;  /*图6,三角箭头的颜色*/
scrollbar-face-color: #fff;  /*图5,立体滚动条的颜色*/
scrollbar-3dlight-color: red;  /*图1,立体滚动条亮边的颜色*/
scrollbar-highlight-color: #e9e9e9;  /*图2,滚动条空白部分的颜色*/
scrollbar-shadow-color: #c4c4c4;  /*图3,立体滚动条阴影的颜色*/
scrollbar-darkshadow-color: #666;  /*图4,立体滚动条强阴影的颜色*/
scrollbar-track-color: #dfdcdc;  /*图7,立体滚动条背景颜色*/
scrollbar-base-color:#fff;  /*滚动条的基本颜色*/
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="/common/js/tabs/tabs.js"></script>
	<!-- jQuery 布局器 -->
<script type="text/javascript" src="/common/jqueryui/jquery.layout-latest.js"></script>
	
<script>

function doInit(){
	//$("#layout").layout({auto:true});
	$.addTab("tabs","tabs-content",[{title:"内部应用",url:contextPath+"/system/mobile/app.jsp",active:true},
	                                {title:"微信应用",url:contextPath+"/system/mobile/weixin.jsp",active:false},
	                                {title:"钉钉应用",url:contextPath+"/system/mobile/ding.jsp",active:false}
									]);
}

</script>

</head>
<body onload="doInit()" style="padding:5px 0px 0px 1px;">
<div id="tabs" class="base_layout_top"></div>
<div id="tabs-content" class="base_layout_center"></div>

</body>
</html>
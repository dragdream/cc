<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page import="java.util.*" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.tianee.oa.oaconst.TeeConst" %>
<%@ page import="com.tianee.oa.oaconst.TeeModelIdConst" %>
<%@ page import="com.tianee.oa.core.org.bean.TeePerson" %>
<%@ page import="com.tianee.webframe.servlet.TeeResPrivServlet"%>
<%@ page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page import="com.tianee.webframe.util.date.TeeDateUtil"%>
<%@ page import="com.tianee.webframe.util.global.TeeSysProps"%>

<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@page import="org.springframework.core.io.ClassPathResource"%>
<%@page import="com.tianee.oa.core.org.service.TeePersonService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%
	String contextPath = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ contextPath + "/";
	//获取主题的索引号
	int styleIndex = 1;
	Integer styleInSession = (Integer) request.getSession().getAttribute("STYLE_TYPE_INDEX");
	if (styleInSession != null) {
		styleIndex = styleInSession;
	}
	String stylePath = contextPath + "/common/styles";
	String imgPath = stylePath + "/style" + styleIndex + "/img";
	String cssPath = stylePath + "/style" + styleIndex + "/css";
	String systemImagePath = contextPath+"/common/images";

	//第二套风格
	int STYLE_TYPE_INDEX_2 = TeeStringUtil.getInteger( request.getSession().getAttribute("STYLE_TYPE_INDEX_2"), 1);
	String cssPathSecond = contextPath + "/system/frame/2/styles/style" + STYLE_TYPE_INDEX_2 + "/css";
	String imgPathSecond = contextPath + "/system/frame/2/styles/style" + STYLE_TYPE_INDEX_2 + "/img";


	String loginOutText = (String) request.getSession().getAttribute("LOGIN_OUT_TEXT") == null ? "" : (String) request.getSession().getAttribute("LOGIN_OUT_TEXT");//退出提示语
	String ieTitle = (String) request.getSession().getAttribute("IE_TITLE") == null ? "" : (String) request.getSession().getAttribute("IE_TITLE");//主界面IEtitle
	String secUserMem = (String) request.getSession().getAttribute("SEC_USER_MEM") == null ? "" : (String) request.getSession().getAttribute("SEC_USER_MEM");//是否记忆用户名



%>
<!-- jQuery库 -->
<script src="<%=contextPath %>/common/jquery/jquery-1.11.3.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css"/>

<script type="text/javascript" >
window.UEDITOR_HOME_URL = "<%=contextPath%>/common/ueditor/";
$.browser = {};
$.browser.mozilla = /firefox/.test(navigator.userAgent.toLowerCase());
$.browser.webkit = /webkit/.test(navigator.userAgent.toLowerCase());
$.browser.opera = /opera/.test(navigator.userAgent.toLowerCase());
$.browser.msie = /msie/.test(navigator.userAgent.toLowerCase());
</script>

<!-- 其他工具库类 -->
<script src="<%=contextPath %>/common/js/tools2.0.js"></script>
<script src="<%=contextPath %>/common/js/sys2.0.js"></script>
<script src="<%=contextPath %>/common/js/sysUtil.js"></script>
<script src="<%=contextPath %>/common/js/src/orgselect.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/easyuiTools.js"></script>


<!-- 图片预览器 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/TeeMenu.js"></script>
<script type="text/javascript">

/** 常量定义 **/
var TDJSCONST = {
  YES: 1,
  NO: 0
};
/** 变量定义 **/
var contextPath = "<%=contextPath %>";
var imgPath = "<%=imgPath %>";
var cssPath = "<%=cssPath%>";
var stylePath = "<%=stylePath%>";

var cssPathSecond = "<%=cssPathSecond%>";
var imgPathSecond = "<%=imgPathSecond%>";
var loginOutText = "<%=loginOutText%>";
var uploadFlashUrl = "<%=contextPath %>/common/swfupload/swfupload.swf";
var commonUploadUrl = "<%=contextPath %>/attachmentController/commonUpload.action";
var systemImagePath = "<%=systemImagePath%>";
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
<script type="text/javascript" src="<%=contextPath%>/common/js/lazyloader.js"></script>
<%@ include file="/header/upload.jsp" %>
<title>查询结果</title>
<script type="text/javascript">
var word = "";
var type = 1;

function doSearchWork(){
	word = $("#word").val();

	tools.requestJsonRs(contextPath+"/quickSearch/quickSearchCount.action",{word:word},true,function(countData){
		$("#task").html(countData.task);
		$("#schedule").html(countData.schedule);
		$("#doc").html(countData.doc);
		$("#customer").html(countData.customer);
		$("#workflow").html(countData.workflow);
		$("#user").html(countData.user);

		$("#list-content").html("");
		loadLazyLoader();

	});
}

$(document).ready(function(){
	$('#word').bind('keypress',function(event){
        if(event.keyCode == "13")
        {
        	doSearchWork();
        }
    });
});

function changeTypeSearch(type,obj){
	$(".search_ul li").removeClass("on");
	$(obj).addClass("on");
	window.type = type;
	$("#list-content").html("");
	loadLazyLoader();
}

function loadLazyLoader(){
	window.loader = null;
	window.loader = new lazyLoader({
		url:contextPath+'/quickSearch/quickSearchList.action',
		placeHolder:'loadMore',
		queryParam:{word:word,type:type},
		contentHolder:'list-content',
		pageSize:30,
		rowRender:function(rowData){
			var render = [];
			if(window.type==1){//任务
				render.push("<div class='list-item' onclick=\"openFullWindow('<%=request.getContextPath() %>/system/subsys/cowork/detail.jsp?taskId="+rowData.sid+"')\">"+rowData.taskTitle+"</div>");
			}else if(window.type==2){//计划
				render.push("<div class='list-item' onclick=\"openFullWindow('<%=request.getContextPath() %>/system/subsys/schedule/manage/detail.jsp?scheduleId="+rowData.uuid+"')\">"+rowData.title+"</div>");
			}else if(window.type==3){//文档
				render.push("<div class='list-item' fileName=\""+rowData.fileName+"\" ext=\""+rowData.ext+"\" attachId=\""+rowData.attachId+"\">"+rowData.fileName+"</div>");
			}else if(window.type==4){//客户
				render.push("<div class='list-item' onclick=\"openFullWindow('<%=request.getContextPath() %>/system/subsys/crm/core/customInfo/detail.jsp?sid="+rowData.sid+"')\">"+rowData.customerName+"</div>");
			}else if(window.type==5){//流程
				render.push("<div class='list-item' onclick=\"openFullWindow('<%=request.getContextPath() %>/system/core/workflow/flowrun/print/index.jsp?view=3&runId="+rowData.runId+"')\"><span style='color:green'>["+rowData.runId+"]&nbsp;</span>"+rowData.runName+"</div>");
			}else if(window.type==6){//用户
				render.push("<div class='list-item' onclick=\"openFullWindow('<%=request.getContextPath() %>/system/core/person/userinfo.jsp?uuid="+rowData.uuid+"')\">"+rowData.userName+"</div>");
			}
			$("#loadMore").show();
			return render.join("");
		},
		onLoadSuccess:function(){
			if(window.type==3){//文档类型，支持下载和转储
				$(".list-item").each(function(i,obj){
					var att = {priv:1+2,fileName:obj.getAttribute("fileName"),ext:obj.getAttribute("ext"),sid:obj.getAttribute("attachId")};
					var attach = tools.getAttachElement(att,{});
					$(obj).html("").append(attach);
				});
			}
		},
		onNoData:function(){
			$("#loadMore").hide();
		}
	});
}


</script>
<style>
body{
	font-family:微软雅黑;
}
.search_ul{
	margin:0px;
	padding:0px;
	list-style:none;
	margin-top:10px;
}
.search_ul li{
	float:left;
	font-size:12px;
	margin-left:10px;
	display:block;
	color:#888;
	min-width:50px;
	height:25px;
	text-align:center;
	cursor:pointer;
}
.search_ul li:hover{
	color:#69b1df;
}
.search_ul li.on{
	color:#69b1df;
	border-bottom:2px solid #69b1df;
}
.list-content{
	clear:both;
	position:absolute;
	top:80px;
	left:10px;
	right:10px;
	bottom:10px;
}
.list-item{
	padding:10px;
	border-bottom:1px solid #efefef;
	color:#000;
	font-size:12px;
	background:white;
	cursor:pointer;
}
.list-item:hover{
	background:#f9f9f9;
}
.search_btn{
	background-color: #6a9ee8;
	border:none;
	outline: none;
	color: #fff;
	text-align: center;
	font-size: 16px;
	width: 70px;
	height: 30px;
	line-height: 28px;
	cursor: pointer;
	font-family: "微软雅黑";
}
.input-sm {
    padding: 5px 10px;
    font-size: 12px;
    line-height: 18px;
}
.form-control{
	width: 90%;
    color: #555;
    background-color: #fff;
    background-image: none;
    border: 1px solid #ccc;
    font-family: "微软雅黑";
}

</style>
</head>
<body style="margin:10px;background:transparent">
<input type="text" id="word" class="form-control input-sm" placeholder="请输入检索关键字" onkeyup="doSearchWork()"/>
<ul class="search_ul">
	<li class="on" onclick="changeTypeSearch(1,this)">任务(<span id="task">0</span>)</li>
	<li onclick="changeTypeSearch(2,this)">计划(<span id="schedule">0</span>)</li>
	<li onclick="changeTypeSearch(3,this)">文档(<span id="doc">0</span>)</li>
	<li onclick="changeTypeSearch(4,this)">客户(<span id="customer">0</span>)</li>
	<li onclick="changeTypeSearch(5,this)">流程(<span id="workflow">0</span>)</li>
	<li onclick="changeTypeSearch(6,this)">用户(<span id="user">0</span>)</li>
</ul>
<div class="list-content">
	<div id="list-content"></div>
	<br/>
	<div id="loadMore" style="font-size:12px;text-align:center;display:none;cursor:pointer">点击加载更多</div>
</div>
</body>
</html>
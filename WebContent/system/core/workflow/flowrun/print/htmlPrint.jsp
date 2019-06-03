<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.tianee.oa.oaconst.TeeConst" %>
<%@ page import="com.tianee.oa.core.org.bean.TeePerson" %>
<%@ page import="com.tianee.webframe.servlet.TeeResPrivServlet"%>
<%@ page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@page import="com.tianee.webframe.util.global.*"%>
<%@page import="org.springframework.core.io.ClassPathResource"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%
	String contextPath = request.getContextPath();
	String thread_local_archives = TeeStringUtil.getString(request
		.getParameter("thread_local_archives"), "");
%>

<%
	int runId = TeeStringUtil.getInteger(request.getParameter("runId"),0);
	int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"),0);
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"),0);
	String view = TeeStringUtil.getString(request.getParameter("view"));
	TeePerson loginPerson = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- jQuery库 -->
<script src="<%=contextPath %>/common/easyui/jquery.min.js"></script>
<script src="<%=contextPath %>/common/js/grayscale.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jQuery.print.js"></script>
<script type="text/javascript" >
window.UEDITOR_HOME_URL = "<%=contextPath%>/common/ueditor/";
$.browser = {};
$.browser.mozilla = /firefox/.test(navigator.userAgent.toLowerCase());
$.browser.webkit = /webkit/.test(navigator.userAgent.toLowerCase());
$.browser.opera = /opera/.test(navigator.userAgent.toLowerCase());
$.browser.msie = /msie/.test(navigator.userAgent.toLowerCase());
</script>

<!-- JBOX通用UI组件 -->
<script type="text/javascript" src="<%=contextPath%>/common/jbox-v2.3/jBox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jbox-v2.3/jBox/i18n/jquery.jBox-zh-CN.js"></script>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/jbox-v2.3/jBox/Skins/Blue/jbox.css" />

<!-- 其他工具库类 -->
<script src="<%=contextPath%>/common/js/md5.js"></script>
<script src="<%=contextPath %>/common/js/tools.js"></script>
<script src="<%=contextPath %>/common/js/sys2.0.js"></script>
<script src="<%=contextPath %>/common/js/src/orgselect.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/easyuiTools.js"></script>

<!-- jQuery Tooltip -->
<script type="text/javascript" src="<%=contextPath%>/common/tooltip/jquery.tooltip.min.js"></script>
<link rel="stylesheet" href="<%=contextPath %>/common/tooltip/jquery.tooltip.css" type="text/css"/>

<!-- jQuery 布局器 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/common/jqueryui/jquery.layout-latest.js"></script>
<script src="<%=contextPath %>/common/ckeditor/ckeditor.js"></script>

<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/images/workflow/index.css">
	<script type="text/javascript" src="<%=contextPath%>/common/js/workflowUtils.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/TeeMenu.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/ckeditor/contents.css">


<style>
#这是浮动div组建 用的div样式 2013-10-07 zhp
.attach_name:link,.attach_name:hover,.attach_name:active,.attach_name:visited{color:#0D3A73;}
.attach_div{width:110px;border:#cccccc 1px solid;position:absolute;padding:0px;z-index:10001;background:#FFFFFF;}
.attach_div a{display:block !important;padding:0px 10px;height:25px;line-height:25px;text-decoration:none;color:#393939;}
.attach_div a:hover{background:#5d99da;color:#fff;text-decoration:none;}
p,span,a,div,td,tr,h1,h2,h3,h4,h5,h6,ul,li,ol
{
	font-size:14px;
	margin:0px;
	padding:0px;
}
table{
border-collapse:collapse;
font-size:12px;
}
#tbody_feedback p,span,a,div,td,tr,h1,h2,h3,h4,h5,h6,ul,li,ol,table{
font-size:12px;
}
#viewinfo p,span,a,div,td,tr,h1,h2,h3,h4,h5,h6,ul,li,ol,table{
font-size:12px;
}
#attach p,span,a,div,td,tr,h1,h2,h3,h4,h5,h6,ul,li,ol,table{
font-size:12px;
}
#feedback p,span,a,div,td,tr,h1,h2,h3,h4,h5,h6,ul,li,ol,table{
font-size:12px;
}
#graph p,span,a,div,td,tr,h1,h2,h3,h4,h5,h6,ul,li,ol,table{
font-size:12px;
}
</style>
<script type="text/javascript">
var sid = <%=sid%>;
var userId = <%=loginPerson.getUuid()%>;
var deptId = <%=loginPerson.getDept().getUuid()%>;
var roleId = <%=loginPerson.getUserRole().getUuid()%>;
var contextPath = "<%=contextPath %>";
var systemImagePath = "<%=contextPath%>/common/images";
var runId = <%=runId%>;
var frpSid = <%=frpSid%>;
var view = "<%=view%>";
var thread_local_archives = "<%=thread_local_archives%>";
var stores = [];
var storeDatas = [];
var sealSignDatas = [];
var sealSignValideDatas = [];
var sealSignPos = [];

var ctrlSignArray = [];//控件签章data数组
var ctrlRandArray = [];//控件随机数数组
var ctrlRandArray4Pic = [];//控件随机数数组
var ctrlRandPngArray = [];
var ctrlRandPngArray4Pic = [];
var ctrlRandPngPos = [];

var mobileStores = [];
var mobileMD5Datas = [];
var mobileSealSignDatas = [];

var mobileHwStores = [];//移动签批数据数组
var mobileHwArray = [];//移动签批数据数组

var h5HwStores = [];//h5手写签批数组
var h5HwArray = [];//h5手写签批数组

function doInit(){
	var url=contextPath+"/teeHtmlPrintTemplateController/printExplore.action";
	var json=tools.requestJsonRs(url,{sid:sid,runId:runId,thread_local_archives:thread_local_archives});
	if(json.rtState){
	    var data=json.rtData;
	    $("#bd").html(data);
	}
}



function printit(){
	$("#bd").print({
    	globalStyles: true,
    	mediaPrint: false,
    	stylesheet: null,
    	noPrintSelector: ".no-print",
    	iframe: true,
    	append: null,
    	prepend: null,
    	manuallyCopyFormValues: true,
    	deferred: $.Deferred(),
    	timeout: 750,
    	title: null,
    	doctype: '<!doctype html>'
    }); 

} 


//打印預覽
function printexplore(){
	openFullWindow(window.location.href);
}
</script>
</head>
<body onload="doInit()">
   <div id="bd">
   
   </div>
</body>
</html>
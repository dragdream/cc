<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
   String contextPath = request.getContextPath();
   String runIds=TeeStringUtil.getString(request.getParameter("runIds"));

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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


<script language="javascript" src="js/jquery.jqprint-0.3.js"></script>
<script src="http://code.jquery.com/jquery-migrate-1.1.0.js"></script>
<link href="css/print.css" rel="stylesheet" media="print">
<title>批量打印表单</title>
<style>
@page {
 size: A4;
 margin: 0;
}
@media print {
 .page {
  margin: 0;
  border: initial;
  border-radius: initial;
  width: initial;
  min-height: initial;
  box-shadow: initial;
  background: initial;
  page-break-after: always;
 }
 
.w3cbbs { page-break-after:always;}
body {
 margin: 0;
 padding: 0;
 background-color: #FAFAFA;
 font: 12pt "Tahoma";
}
* {
 box-sizing: border-box;
 -moz-box-sizing: border-box;
}
.page {
 width: 21cm;
 height: 29.7cm;
 margin: 0 auto;
 border: 1px #D3D3D3 solid;
 border-radius: 5px;
 background: white;

}
.subpage {
 height: 100%;
}
}


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

</style>

<script type="text/javascript">
var contextPath = "<%=contextPath %>";


var runIds="<%=runIds %>";
function doInit(){
	var runIdArray=runIds.split(",");
	var html=[];
	for(var i=0;i<runIdArray.length;i++){
		var url = contextPath+"/flowRun/getFormPrintData.action";
		var json = tools.requestJsonRs(url,{runId:runIdArray[i],view:1});
		if(json.rtState){
			var form = json.rtData.form;
			html.push("<div class='page w3cbbs'>");
			html.push("<div class='subpage'>");
			html.push(form);
			html.push("</div>");
			html.push("</div>");
		}
	}
	
	$("#bd").append(html.join(""));
	
	
	
	
	$(".book").jqprint({debug: true,importCSS: true, printContainer: true, operaSupport: true});
}


</script>
</head>
<body onload="doInit()">
    <div class="book" id="bd">
    </div>
</body>
</html>
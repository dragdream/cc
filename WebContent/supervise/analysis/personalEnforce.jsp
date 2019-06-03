<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

Cookie cookie66 = TeeCookieUtils.getCookie(request, "skin_new");
String skinNew66 = "1";
if(cookie66!=null){
    skinNew66 = cookie66.getValue();
}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header2.0.jsp" %>
<script src="<%=contextPath %>/common/jquery/portlet/jquery-ui-1.9.2.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/KinSlideshow/jquery.KinSlideshow-1.2.1.min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/seniorreport.js"></script>
<script src="<%=contextPath %>/common/jquery/portlet/jquery-ui-1.9.2.min.js"></script>
<script src="<%=contextPath %>/common/highcharts/js/highcharts.src.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/highcharts/js/jquery.highchartsTable.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/highcharts/js/modules/exporting.src.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/highcharts/js/modules/data.src.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/highcharts/js/highcharts-more.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/highcharts/js/modules/funnel.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/lazyloader.js"></script>

<link rel="stylesheet" type="text/css" href="<%=contextPath %>/system/frame/6/css/datepicker.css"/>
<script type="text/javascript" src="<%=contextPath%>/system/frame/6/js/jquery.datePicker-min.js"></script>

<title>主界面</title>
<!-- base_fornt_workDesk.png -->
<link href="css/main<%=skinNew66 %>.css" type="text/css" rel="stylesheet" />
</head>

<body style="background:#fff; width: 99%; height: 98%;" onload="doInit()">
<img src="<%=contextPath%>/supervise/analysis/personalEnforce.png" alt="" style="z-index:9999999;position:absolute;left:1%;top:3%;height:96%;width:97%;"/>
<div id="divOut" style="display: none; position: fixed; background-color: ; z-index:1000000; height:220px; width:150px; overflow:; right: -134px; bottom:35px; ">

    </div>


</body>
</html>

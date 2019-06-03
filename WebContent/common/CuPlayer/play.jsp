<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>正在播放 <%=request.getParameter("fileName") %></title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link rel="shortcut icon" href="/favicon.ico" >
<style type="text/css">
body {margin:0px;background:#000000; }
</style>
</head>

<body>
<!-- 我爱播放器(52player.com)/代码开始 -->
<script type="text/javascript" src="images/swfobject.js"></script>
<center>
<div class="video" id="CuPlayer">
<strong style="color:#ffffff">提示：您的Flash Player版本过低进行网页播放器升级</a>！</strong></div>
<script type="text/javascript">
var so = new SWFObject("CuPlayerMiniV4.swf","CuPlayerV4","800","600","9","#000000");
so.addParam("allowfullscreen","true");
so.addParam("allowscriptaccess","always");
so.addParam("wmode","opaque");
so.addParam("quality","high");
so.addParam("salign","lt");
so.addVariable("CuPlayerSetFile","CuPlayerSetFile.xml"); //播放器配置文件地址,例SetFile.xml、SetFile.asp、SetFile.php、SetFile.aspx
so.addVariable("CuPlayerWidth","800"); 
so.addVariable("CuPlayerHeight","600"); 
so.addVariable("CuPlayerAutoPlay","no"); 
so.addVariable("CuPlayerImage","images/start.jpg");
so.addVariable("CuPlayerPosition","bottom-right"); 
so.addVariable("CuPlayerFile","<%=request.getContextPath()%>/attachmentController/downFile.action?id=<%=request.getParameter("id")%>"); 
so.write("CuPlayer");
</script>
</center>
</body>
</html>


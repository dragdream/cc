<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

<title>Tenee办公自动化智能管理平台</title>
<script type="text/javascript">
var contextPath  = "<%=request.getContextPath() %>";
</script>
<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="js/ui/jquery-ui-1.8.17.custom.min.js"></script>
<script type="text/javascript" src="js/ui/t9/t9.all.min.js"></script>
<script type="text/javascript" src="js/ui/jquery.ux.borderlayout.js"></script>
<link rel="stylesheet" type="text/css" href="css/ui/jqueryUI/base/jquery.ui.all.css"/>
<script type="text/javascript" src="js/index.js"></script>

<link rel="stylesheet" type="text/css" href="css/index.css"/>
</head>
<body>
  <div class="ui-layout-north" id="northContainer">
    <div class="banner_bg">
    </div>
  </div>
  <div class="ui-layout-west" id="westContainer">
    <div class="left-menu"><div></div></div>
  </div>
  <div class="ui-layout-center" id="mainContainer">
    <iframe id="workspace" name="workspace" src="about:blank" frameborder="0">
    </iframe>
  </div>
  <div class="ui-layout-south" id="southContainer">
    <div class="status-content"></div>
  </div>
</body>
</html>
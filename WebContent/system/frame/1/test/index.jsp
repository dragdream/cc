<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title>科目</title>
<script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../js/ui/jquery-ui-1.8.17.custom.min.js"></script>
<script type="text/javascript" src="../js/ui/jquery-ui-patch.js"></script>
<link rel="stylesheet" type="text/css" href="../css/ui/jqueryUI/base/jquery.ui.all.css"/>
<link rel="stylesheet" type="text/css" href="../css/style.css"/>
<link rel="stylesheet" href="flora.tabs.css">
<script type="text/javascript">
$(function() {
  $("body").pageTabs({
    iframeTabs: [{
      title: "测试页面1",
      src: "list.jsp"
    }, {
      title: "测试页面2",
      src: "list.jsp"
    }]
  });
});
</script>
<style>
.ui-tabs-panel > iframe {
  width: 100%;
  height: 100%;
  border: none;
}
</style>
</head>
<body>
</body>
</html>
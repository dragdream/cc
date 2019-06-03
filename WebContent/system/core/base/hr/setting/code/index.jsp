<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<title>HR代码管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">

function doInit() {
  doFramesetScroll("navigateFrame", "contentFrame", 580, "<%=contextPath %>/system/core/base/hr/setting/code/blank.jsp");
}
window.onload = doInit;

/**
 * 处理frameset,左边是菜单,右边是功能区,菜单不随着功能区滚动的问题
 * @param menuId
 * @param mainId
 * @param minHeight
 * @param url
 * @return
 */
function doFramesetScroll(menuId, mainId, minHeight, url) {
  try {
    var main = document.getElementById(mainId);
    var menu = document.getElementById(menuId);
    menu.scrolling = "no";
    menu.contentWindow.document.body.style.height = "auto";
    menu.contentWindow.document.attachEvent && menu.contentWindow.document.attachEvent("onmousewheel", function(e) {
      var top = (main.contentWindow.document.documentElement || main.contentWindow.document.body).scrollTop;
      if (e.wheelDelta >= 120) {
        main.contentWindow.scrollTo(0, top - 50);
      }
      else {
        main.contentWindow.scrollTo(0, top + 50);
      }
    });
    main.onload = main.onreadystatechange = function() {
      if (!this.readyState || this.readyState == "complete") {
        menu.contentWindow.scrollTo(0, 0);
        main.contentWindow.document.body.style.minHeight = parseInt(minHeight-160) + "px";
        if (main.contentWindow.attachEvent) {
          main.contentWindow.attachEvent("onscroll", function(e) {
            menu.contentWindow.scrollTo(0, main.contentWindow.document.documentElement.scrollTop);
            return false;
          });
        }
        else if (main.contentWindow.addEventListener) {
          main.contentWindow.addEventListener("scroll", function(e) {
            menu.contentWindow.scrollTo(0, main.contentWindow.document.documentElement.scrollTop);
            return false;
          }, false);
        }
      }
    };
    main.src = url;
  } catch (e) {
    
  } finally {
    
  }
}
</script>
</head>
<frameset cols="400,*" frameborder=no framespacing="0">	
	<frame name="navigateFrame" id="navigateFrame" scrolling="auto"  src="<%=contextPath %>/system/core/base/hr/setting/code/mainmanage.jsp" />
	<frame name="contentFrame" id="contentFrame"  scrolling="auto" src="about:blank" />	
</frameset>
</html>
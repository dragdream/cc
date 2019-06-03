<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.tianee.oa.core.general.model.TeeSmsModel" %>
<%@ page import="com.tianee.webframe.util.str.TeeJsonUtil" %>
<%@ page import="com.tianee.webframe.util.date.TeeLunarCalendarUtils" %>
<%@ page import="com.tianee.webframe.util.date.TeeWeather" %>
<%@ page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page import="com.tianee.webframe.util.date.*"%>
<%@ page import="com.tianee.webframe.util.auth.TeeAuthUtil"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/header/smsHeader.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<link rel="stylesheet" type="text/css" href="css/sms.css">
	<link rel="stylesheet" type="text/css" href="css/cmp-all.css">
	<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style_without_tab.css">
	<link rel="stylesheet" type="text/css" href="<%=cssPath%>/theme.css">
	<link rel="stylesheet" type="text/css" href="<%=cssPath%>/index1.css" />
	<link rel="stylesheet" type="text/css" href="<%=cssPath%>/menu.css">
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/system/frame/default/css/query.css">
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/system/frame/default/css/index1.css">
	<link  href="<%=contextPath%>/common/jquery/portlet/jquery.portlet.css?v=1.1.2" type="text/css" rel="stylesheet" />
	<link  href="<%=contextPath%>/common/jquery/portlet/jquery-ui-1.9.2.custom.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="<%=contextPath %>/common/jquery/ux/jquery.ux.tee.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/common/jquery/ux/jquery.ux.container.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/common/jquery/ux/jquery.ux.panel.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/common/jquery/ux/jquery.ux.window.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/tabs/tabs.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/system/frame/ispirit/js/sms.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/system/frame/default/js/index.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/jquery/portlet/jquery-ui-1.9.2.min.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/jquery/portlet/jquery.portlet.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/TeeMenu.js"></script> 
	<script type="text/javascript" src="<%=contextPath%>/system/core/org/orgUser/orgUser.js"></script>	
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/system/frame/2/styles/style1/css/sms.css">
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/fullcalendar/fullcalendar/fullcalendar.css">
	<script type="text/javascript" src="<%=contextPath%>/system/frame/2/js/sms.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/bootstrap/js/bootstrap_typeahead.js"></script>	
<script type="text/javascript" charset="UTF-8">
function doInit(){
	getMenuList();
	//点击菜单
	jQuery('ul.nav li.menuItem a').bind('click', function(){
		//alert(jQuery(this).attr('class').indexOf('active'));
		
		/* if(MENU_EXPAND_SINGLE == '1'){//是否同时可以展示多个菜单 0 -多个； 1- 只有一个
			jQuery(this).parent().siblings().removeClass('active open');
		}else{
			jQuery(this).parent().siblings().removeClass('active open');
		} */
		jQuery(this).parent().siblings().removeClass('active open');
		if(jQuery(this).parent().attr('class').indexOf('active') < 0 ){
			jQuery(this).parent().addClass('active open');
		}else{
			jQuery(this).parent().removeClass('active open');
		}
	});
}

/**
 * 转转页面
 */
function toSrcUrl(menuName,menuCode , isHide){
	
	if(menuCode && menuCode != ''){
		//addTabs(menuName,contextPath + menuCode);
		window.external.IM_OpenNavigation(menuName,contextPath + menuCode,1024,700);
		if(isHide){
			hideMenuInfo();//隐藏菜单
		}
	}

}
/**
 * 获取菜单,初始化
 */
function  getMenuList(){
	var url = contextPath + "/teeMenuGroup/getPrivSysMenu.action";
	var  MENU_EXPAND_SINGLE = "0";//是否同时可以展示多个菜单 0 -多个； 1- 只有一个
	var jsonObj = tools.requestJsonRs(url);
	if(jsonObj.rtState){
		var json = jsonObj.rtData;
		jQuery.each(json, function(i, sysMenu) {
			var menuId = sysMenu.menuId;
		    var menuName = sysMenu.menuName;
		    var  menuCode = sysMenu.menuCode ;
		    var menuIcon = sysMenu.icon;
		    if(!menuIcon && menuIcon == ''){
		    	menuIcon = "glyphicon glyphicon-list";
		    }
			if(menuId.length == 3){//主菜单
				$("#menuNav").append(" <li id='menu-lv1-"+menuId+"' class='menuItem'>" +
						"<a href='javascript:void(0);'>" +
						"<i class='" + menuIcon + "'></i>" + 
					    "<span class='menu-text'  style='font-size:14px;font-family:'>" +menuName+ "</span>" + 
					    "<b class='arrow icon-angle-down'></b>" 
					    +"</a></li>");
			}else if(menuId.length == 6){//二级菜单
				var parentMenuId = menuId.substring(0,3);
				//alert($("#menu-lv1-" + parentMenuId)[0])
				if($("#menu-lv1-" + parentMenuId + " ul")[0]){
					$("#menu-ul-lv2-" + parentMenuId).append(
							"<li class='menuItem'  id='menu-lv2-"+menuId+"'>" +
							"<a href='javascript:void(0);'>" +
							"<i class='icon-double-angle-right'></i>"+
							"<i class='glyphicon glyphicon-plus' style='opacity:0;filter:alpha(0);'></i>&nbsp;"+
			            	 "<span style='font-family:'>"+menuName +"</span>"+ 
			            	"</a>" + 
							"</li>" 
							);
				}else{
					$("#menu-lv1-" + parentMenuId).append("<ul class='submenu'  id='menu-ul-lv2-"+parentMenuId+"'>" +
							"<li class='menuItem' id='menu-lv2-"+menuId+"'>" +
							"<a href='javascript:void(0);'>" +
							"<i class='icon-double-angle-right'></i>"+
							"<i class='glyphicon glyphicon-plus' style='opacity:0;filter:alpha(0);'></i>&nbsp;"+
							"<span style='font-family:'>"+menuName +"</span>"+ 
			            	"</a>" + 
							"</li>" + 
							"</ul>");
				}
				
					
				$("#menu-lv2-" + menuId).children('a').bind("click",function(){
					 toSrcUrl(menuName,menuCode);
				});
			}else if(menuId.length == 9){//三级菜单
				var parentMenuId = menuId.substring(0,6);
				$("#menu-lv2-"  + parentMenuId+" .glyphicon-plus").css({opacity:1});//更改二级菜单样式
				if($("#menu-lv2-" + parentMenuId + " ul")[0]){
					$("#menu-ul-lv3-" + parentMenuId).append(
							"<li class='menuItem'  id='menu-lv3-"+menuId+"'>" +
							"<a href='javascript:void(0);'>" +
							"<i class='icon-leaf'></i>&nbsp;&nbsp;"+
							"<span style='font-family:;font-size:12px'>"+menuName +"</span>"+ 
			            	"</a>" + 
							"</li>" 
							);
				}else{
					$("#menu-lv2-" + parentMenuId).append("<ul class='submenu' id='menu-ul-lv3-"+parentMenuId+"'>" +
							"<li class='menuItem'  id='menu-lv3-"+menuId+"'>" +
							"<a href='javascript:void(0);'>" +
							"<i class='icon-leaf'></i>&nbsp;&nbsp;"+
							"<span style='font-family:;font-size:12px'>"+menuName +"</span>"+ 
			            	"</a>" + 
							"</li>" + 
							"</ul>");
				}
				
				$("#menu-lv3-" + menuId).children('a').bind("click",function(){
					 toSrcUrl(menuName,menuCode);
				});
			}
		});
		MENU_EXPAND_SINGLE = jsonObj.rtMsg;
	}
	return MENU_EXPAND_SINGLE;
}

</script>
</head>
<body onload="doInit()">
<div class="menu-wrapper">
	<div class="menu-scroller">
       <ul class="nav nav-list" id="menuNav">
       </ul>
	</div>
</div>
</body>
</html>
		        
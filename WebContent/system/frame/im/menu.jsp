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
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
	<script type="text/javascript" src="<%=contextPath%>/system/frame/1/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/frame/1/js/ui/jquery-ui-1.8.17.custom.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/frame/1/js/ui/t9/t9.all.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/frame/1/js/ui/jquery.ux.borderlayout.js"></script>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/system/frame/1/css/ui/jqueryUI/base/jquery.ui.all.css"/>
<script type="text/javascript" src="<%=contextPath%>/system/frame/1/js/index.js"></script>

<link rel="stylesheet" type="text/css" href="<%=contextPath%>/system/frame/1/css/index.css"/>
<script type="text/javascript" charset="UTF-8">
function doInit(){
	
}

var isClickMenuInfo = false;
var menuInfoId = "menuInfoId";
var start_menu = "start_menu";
/**
* 获取菜单信息
*/
function getMenuInfo(){
	var test = false;
	if(!isClickMenuInfo){
		$("#closeMenu").append('<i class="glyphicon glyphicon-remove"/>');
		$("#closeMenu").click(function(){
			$("#" + menuInfoId).hide();
		});
 

        var menuInfo = getMenuList();
		 $(document).on('click', function(e) {
			   // e.target ;// 判断e.target是不是属于菜单下的东西
			/* var a = false;
			var ss = $("#" + menuInfoId).find("*");//.filter(e.target);
			
			 for(var i =0 ;i<ss.length ; i++){
				 if(e.target == ss[i]){
					 a= true;
					 break;
				 }
			 } */
			
		 });
		
     
	}
}
/**
 * 转转页面
 */
function toSrcUrl(menuName,menuCode , isHide){
	
	if(menuCode && menuCode != ''){
		//addTabs(menuName,contextPath + menuCode);
		window.external.IM_OpenNavigation(menuName,menuCode,1024,700);
// 		if(isHide){
// 			hideMenuInfo();//隐藏菜单
// 		}
	}

}

var level1 = [];
var level2 = [];
var level3 = [];
/**
 * 初始化主菜单
 * @param el
 * @return
 */
function initMenu(el) {
	var data = [];
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
				var obj = {
					    seqId:menuId,
					    expand:0,
					    id:menuId,
					    text:menuName,
					    icon:contextPath+"/system/frame/3/icons/"+menuIcon,
					    leaf:0,
					    url: menuCode,
					    children:[]};
				level1.push(obj);
				data.push(obj);
			}else if(menuId.length == 6){//二级菜单
				var parentMenuId = menuId.substring(0,3);
				for(var i=0;i<level1.length;i++){
					if(level1[i].id==parentMenuId){
						var obj = {
						        seqId:menuId,
						        expand:0,
						        id:menuId,
						        text:menuName,
						        leaf:0,
						        url: menuCode,
						        children:[]};
						level1[i].children.push(obj);
						level2.push(obj);
						break;
					}
				}
			}else if(menuId.length == 9){//三级菜单
				var parentMenuId = menuId.substring(0,6);
				for(var i=0;i<level2.length;i++){
					if(level2[i].id==parentMenuId){
						var obj = {
						        seqId:menuId,
						        expand:0,
						        id:menuId,
						        text:menuName,
						        leaf:0,
						        url: menuCode,
						        children:[]};
						level2[i].children.push(obj);
						level3.push(obj);
						break;
					}
				}
			}
		});
		MENU_EXPAND_SINGLE = jsonObj.rtMsg;
	}
	
	for(var i=0;i<level1.length;i++){
		if(level1[i].children.length==0){
			level1[i].leaf = 1;
		}
	}
	for(var i=0;i<level2.length;i++){
		if(level2[i].children.length==0){
			level2[i].leaf = 1;
		}
	}
	for(var i=0;i<level3.length;i++){
		if(level3[i].children.length==0){
			level3[i].leaf = 1;
		}
	}
	
	new T9.Menu({
	    id: "menu",
	    classes: ['menu-lv1', 'menu-lv2', 'menu-lv3'],
	    data: data,
	    openUrl: function (node) {
	      //dispParts(node.url, node.openFlag);
	      window.external.IM_OpenNavigation(node.text,node.url,1024,700);
	    },
	    el: el,
	    expandType: 0,
	    //isLazyLoad: true,
	    lazyLoadData: function (menu) {
	      return {};
	    },
	    liClass: [null, 'menu-close', null],
	    selClass: 'menu-selected',
	    expClass: ['menu-selected', 'menu-expand']
	  });
}
</script>
</head>
<body onload="doInit()" style="overflow: auto;">
<div class="left-menu" style=""><div>
</body>
</html>
		        
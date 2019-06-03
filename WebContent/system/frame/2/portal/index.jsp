<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<title>个人桌面</title>

<link rel="stylesheet" type="text/css" href="<%=cssPathSecond%>/personal.css"/>
<style>
table {
  border-collapse: collapse;
  padding: 0;
  margin: 0;
}
html, body {
  height: 100%;
}

body {
  position: relative;
  text-align: center;
  margin: 0;
  padding: 0;
}

.slidebox-container {
  margin: 0;
  padding: 0;
  overflow: hidden;
}

#controller {
  height: 26px;
  left: 0;
  position: absolute;
  top: 30px;
  z-index: 3;
  width: 100%;
  text-align: center;
}
</style>

<!-- jQuery库 -->
<script type="text/javascript" src="<%=contextPath %>/system/frame/2/jquery/jquery.ui.core.js"></script> 
<script type="text/javascript" src="<%=contextPath %>/system/frame/2/jquery/jquery.ui.widget.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/frame/2/jquery/jquery.ui.mouse.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/frame/2/jquery/jquery.ui.draggable.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/frame/2/jquery/jquery.ui.droppable.js"></script>
 <script type="text/javascript" src="<%=contextPath %>/system/frame/2/jquery/jquery.ui.resizable.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/frame/2/jquery/jquery.ui.selectable.js"></script>  
<script type="text/javascript" src="<%=contextPath %>/system/frame/2/jquery/jquery.ui.sortable.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/frame/2/jquery/ux/jquery.ux.slidebox.js"></script>

<script type="text/javascript">
var modules;
var slidebox;
/** 变量定义 **/
var contextPath = "<%=contextPath %>";
var imgPath = "<%=imgPath %>";
var cssPath = "<%=cssPath%>";
var stylePath = "<%=stylePath%>";
var screenCount = 0;//屏幕数量
$(function() {
	
	
 	 modules = [
				[{icon:"default.png",menuCode:"/core/funcs/org",menuName:"单位管理",uuid:"600402"}],
 	            [{icon:"default.png",menuCode:"/core/funcs/org",menuName:"单位管理",uuid:"600402"},
 	      		 {icon:"default.png",menuCode:"/core/funcs/autorunmgr/index.jsp",menuName:"后台管理",id:"6034"}]]
; 
 	
 	var url = contextPath + "/systemAction/getPostChildMenuToFrame2.action";
	var para =  {type:'1'};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var data = jsonObj.rtData;
		modules = jsonObj.rtData.selectSysMenuModelList;
		if(modules.length == 0){
			modules = [[]];
		}
		screenCount = modules.length;
		 slidebox = $("#container").slideBox({
	          control: ".controller-c",
	          count: modules.length,
	          cancel: ".block",
	          listeners: {
	            afterScroll: function(i) {
	            },
	            beforeScroll: function(i) {
	               jQuery(".background").animate({
	                  left: - i * 70   
	               }, "normal");
	            }
	          }
	        });
		 
		 initScreens();
	}
  initTrash();
  
  //去掉父级菜单显示
  $("body").click(function(){
	  parent.hideMenuInfo();
  });
  
  
});

//初始化
function initScreens() {
  $("#container .screen").append("<ul class='blocks' style='margin-top:20px;' ></ul><div style='clear: both;'></div>");
  $.each(modules, function(i, e) {
    $.each(e, function(j, m) {
      addModule(i, m);
    })
    var s = slidebox.getScreen(i); 
    var ul = s.find("ul"); 
    var click;
		ul.sortable({
		  revert: true,
		  //delay: 200,
		  //distance: 10,               //延迟拖拽事件(鼠标移动十像素),便于操作性
		  tolerance: 'pointer',       //通过鼠标的位置计算拖动的位置*重要属性*
		  connectWith: ".screen ul",
		  scroll: false,
		  stop: function(e, ui) {
		    setTimeout(function() {
          $(".block.remove").remove();
          $("#trash").hide();
          //ui.item[0].onclick = click;
          serializeSlide();
					ui.item.removeAttr("clickdisabled");
		    }, 0);
		  },
		  start: function(e, ui) {
				$("#trash").show();
				ui.item.attr("clickdisabled", true);
		  }
	  });
  });
}

function addScreen() {
  slidebox.addScreen();
  slidebox.scroll(slidebox.getCount() - 1);
  var newScreen = slidebox.getScreen(slidebox.getCount() - 1);
  var ul = $("<ul class='blocks'></ul>");
  newScreen.append(ul);
  ul.sortable({
    revert: true,
    //delay: 200,
    //distance: 10,               //延迟拖拽事件(鼠标移动十像素),便于操作性
    tolerance: 'pointer',       //通过鼠标的位置计算拖动的位置*重要属性*
    connectWith: ".screen ul",
    scroll: false,
    stop: function(e, ui) {
      setTimeout(function() {
            $(".block.remove").remove();
            $("#trash").hide();
            //ui.item.click(openUrl);
            serializeSlide();
      }, 0);
    },
    start: function(e, ui) {
       $("#trash").show();
       //ui.item.unbind("click");
    }
  });
}

//添加模型
function addModule(screen, module) {
  var el = slidebox.getScreen(screen).find("ul.blocks");
  var _id = module.uuid;
  var iconTemp  =  module.icon;
  var icon =  "default.png";
  if(iconTemp && iconTemp != ''){
	  icon = iconTemp;
  }
  var li = $("<li class=\"block\"></li>");
  var img = $("<div class='img'><p><img src='" + cssPathSecond + "/images/app_icons/" + icon+ "' /></p></div>");
  var divT = $("<div class=\"count\" align=''></div>");
  li.attr("id", "block_" + module.uuid);
  li.attr("title", module.menuName);
  li.attr("index", module.uuid);
  divT.attr("id", "count_" + module.uuid);
  if(module.count > 0){
     //divT.addClass("count" + module.count);   
  }
 // var a = $("<span class=\"icon-text\" href=\"javascript: void(0)\"></span>"); 
  var div = $("<div  class=\"icon-text\" ></div>").text(module.menuName); 
/*   var span = $("<span  class=\"span-icon-text\" ></span>").text(module.menuName);  */
  li.append(img.append(divT)).append(div); 
  el.append(li);
  var url  = "";
  if((module.menuCode).startWith("/oaop")){
	   url = module.menuCode;
  }
  else{
	   url = new RegExp("^\/").test(module.menuCode) ?   module.menuCode : module.menuCode;
  }
  //点击触发事件
  li.click(function() {
    if (li.attr("clickdisabled")) {
      return;
    }
    parent.toSrcUrl(module.menuName,url);
    //parent.toSrcUrl("用户管理","/system/core/person/index.jsp");
  });
}

String.prototype.startWith = function(str){
	if(str==null||str==""||this.length==0||str.length>this.length)
	  return false;
	if(this.substr(0,str.length)==str)
		return true;
	else
	  return false;
	return true;
}
/**
 * 点击应用盒子
 */
function openAppBox(){
	var title = "桌面应用设置";
	var index = 0;
	var url = contextPath + "/system/frame/2/portal/setting.jsp?index=" + index ;
	bsWindow(url ,title,{width:"800",height:"360",buttons:
		[
		
	 	 //{name:"关闭",classStyle:"btn btn-primary"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			var isOk = cw.doSaveOrUpdate();
			return isOk;
		}else if(v=="关闭"){
			return true;
		}
	}});
}

/**
 * 消息
 */
function portalMessage(msg){
  if(!msg) return;
  msgObj = $("#portalSettingMsg");
  msgObj.html(msg).show();
  setTimeout(function(){msgObj.empty().hide()},5000);
}

/**
 * 改变时触发时间
 */
function serializeSlide() {

  var s = "[";
  jQuery("#container .screen").each(function(i, e) {
	 var ss = "";
     jQuery(this).find("li.block").each(function(j, el) {
        if(!$(el).attr("index")) return true;
        ss += $(el).attr("index");
        ss += ",";
     });
     if(ss != ""){
    	 ss = ss.substring(0,ss.length - 1);
     }
     
     s = s + "{data:\"" + ss + "\"},";
  });
  if (s.length) {
     s = s.replace(/\|$/, "");   
  }
  if(s != "["){
	  s = s.substring(0,s.length - 1);
  }
  s = s + "]";
  //alert(s)
 // return ;
	var url = contextPath + "/personManager/updatePersonMenuParamSet.action";
	var para =  {menuParamSet: s};
	var jsonObj = tools.requestJsonRs(url, para);
	return jsonObj.rtState;
}

/**
 * 初始化垃圾箱 ，移动、移出等事件
 */
function initTrash() {
  $("#trash").droppable({
     over: function() {
        $("#trash").addClass("hover");
     },
     out: function() {
        $("#trash").removeClass("hover");
     },
     drop: function(event, ui) {
        ui.draggable.addClass("remove").hide();
        delModule(ui.draggable.attr("index"));
        $(".ui-sortable-placeholder").animate({
           width: "0"
        }, "normal", function() {
        });
        $("#trash").removeClass("hover");
     }
  });   
}

/**
 * 删除对象
 */
function delModule(el){
  var pObj = jQuery("#container .screen ul li.block");
  pObj.each(function(){
     var index = jQuery(this).attr("index");
     if(el == index){
        jQuery(this).remove();
        var flag = serializeSlide();
     }
  });
}

function doInit(){
	//处理应用盒子
	//$("a").removeClass("btn");
	//$("#openAppBox").show();
}
</script>

</head>
<body onload="doInit()">
  <div id="trash"></div>
  <div id="container"></div>
  <div id="controller"> 
    <table align="center" cellpadding="0" cellspacing="0">
       <tr>
          <td class="controller-l"></td>
          <td class="controller-c"></td>
          <td class="controller-r">
            <a id="openAppBox" title="打开应用盒子" onclick="openAppBox()" href="javascript: void(0)"  class="cfg"></a>
          </td>
       </tr>
    </table>
	</div>
	<div class="background" style="left: 0px;"></div>
</body>

</html>

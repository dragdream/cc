<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>

<%
	int index = TeeStringUtil.getInteger(request.getParameter("index"), 0);//当前所在索引位置

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp"%>
<title>桌面应用设置</title>
<link rel="stylesheet" type="text/css" href="<%=cssPathSecond %>/personal.css"/>
<link rel="stylesheet" type="text/css" href="<%=cssPathSecond %>/appPage.css"/>
<link rel="stylesheet" type="text/css" href="<%=cssPathSecond%>/appbox.css"/>
<script type="text/javascript" src="<%=contextPath %>/system/frame/2/jquery/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/frame/2/jquery/jquery.ui.core.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/frame/2/jquery/jquery.ui.widget.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/frame/2/jquery/jquery.ui.mouse.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/frame/2/jquery/jquery.ui.draggable.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/frame/2/jquery/jquery.ui.droppable.js"></script>
 <script type="text/javascript" src="<%=contextPath %>/system/frame/2/jquery/jquery.ui.resizable.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/frame/2/jquery/jquery.ui.selectable.js"></script>  
<script type="text/javascript" src="<%=contextPath %>/system/frame/2/jquery/jquery.ui.sortable.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/frame/2/jquery/ux/jquery.ux.slidebox.js"></script>
<style type="text/css">
</style>



<script type="text/javascript">

var index = <%=index%>;

//所有未选中的
var notSelectSysMenuModelList = new Array();
//所有一级菜单
var parentMenuList = new Array();
function doInit()
{
	var url = contextPath + "/systemAction/getPostChildMenuToFrame2.action";
	var para =  tools.formToJson($("#form1")) ;
	
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var data = jsonObj.rtData;
		parentMenuList= data.parentMenuList;
		notSelectSysMenuModelList = jsonObj.rtData.notSelectSysMenuModelList;
		$.each(parentMenuList, function(i, m) {
			//$('#menuParent').append('<li><a title="'+m.menuName+'"  href="javascript:getChildMenu(\''+m.menuId+'\',' + m.uuid + ');" id="'+m.uuid+'" ' +(i == 0 ? 'class="current"' : '' )+'> '+m.menuName+'</a></li>');
			$('#menuParent').append("<a class='list-group-item ' id='"+m.uuid+"' href='javascript:void(0)'  title='"+m.menuName+"'  >" + m.menuName + "</a>");
			if(i == 0){
				$("#" + m.uuid).addClass("active");
				getChildMenu(m.menuId );
			}
			$("#" + m.uuid).click(function (){
				getChildMenu(m.menuId , m.uuid);
			});
		});
		
		
	}else{
		alert(jsonObj.rtMsg);
	}
}

/**
 * 获取叶子菜单
 */
function getChildMenu(parentMenuId , parentID){
	$('#menuChild').empty();
	if(parentID){
		$("a.active").removeClass("active");
		$("#" + parentID).addClass("active");
	}
	
	
	$.each(notSelectSysMenuModelList, function(j, m) {
		var menuId = m.menuId.substring(0,3);
        if(!parent.$("#block_"+m.uuid).length > 0 && parentMenuId == menuId && m.menuId.length != 3 ){//不是父节点，且未在桌面的
        	 var icon = "51.png";
        	 if(m.icon && m.icon != ''){
        		 icon= m.icon;
        	 }
	         var a = $('<span title="'+m.menuName+'" href="javascript:void(0);" id="'+m.uuid+'" style="cursor:pointer"></span>').append('<img width="48" height="48"  src="' + contextPath + '/system/frame/7/icons/' +icon+'"><br/>'+m.menuName);
	         if(m.menuCode!=""){
	        	 $('#menuChild').append($("<li  style='float:left;width:50px;height:80px;margin-right:30px;margin-bottom:10px;text-align:center;'></li>").append(a));
	         }
	         a.click(function() {
	           parent.addModule(parent.slidebox.getCursor(), m);
	           $(a).parent().remove();
	           parent.serializeSlide();
	         });
        }
      });
}

/**
 * 模块应用设置
 */
function setMemu(){
	$("#app_menu_setting").show();
	$("#app_screen_setting").hide();
}

function setScreen(){
	$("#app_menu_setting").hide();
	$("#app_screen_setting").show();
	 $("#screenPageDom ul").empty();
	  var screenCount = parent.slidebox.getCount();
	  for (var i = 0; i < screenCount; i++) {
	      addScreen(i);
	  }
	  $("#screenPageDom ul").append('<li title="添加屏幕" class="no-draggable-holder" id="btnAddScreen" style=""></li>');
	  
	  //鼠标滑过屏幕样式
	  $("#screenPageDom ul li.minscreenceil'").live('mouseenter', function() {
	     $(this).css({"font-size":"60px"});
	     if($('span.closebtn', this).length <= 0)
	        $(this).append("<span class='closebtn' title='移除此屏'></span>");//'移除此屏'
	     $('span.closebtn', this).show();
	  });
	  
	  $("#screenPageDom ul li.minscreenceil").live('mouseleave', function() {
	     $(this).css({"font-size":""});
	     $('span.closebtn', this).hide();
	  });
	  
	  //删除屏幕
	  $("#screenPageDom ul li.minscreenceil span").live("click",function(){
	     if(confirm("删除桌面，将删除桌面应用模块，确定要删除吗？")) {
	        var currentDom = $(this).parent("li");
	        parent.slidebox.removeScreen(currentDom.index("li.minscreenceil"));
	        //保存数据
	        var flag = parent.serializeSlide();
	        if(flag)
	        {
	           parent.portalMessage("桌面删除成功！");
	           
	           currentDom.remove();
	           reSortMinScreen();
	        }
	     }   
	  });
	  
	  $("#screenPageDom ul").sortable({
	    cursor: 'move', 
	    tolerance: 'pointer',
	    cancel: '#btnAddScreen',
	    stop: function(){
	       var arrScreen = new Array();
	       $(this).find("li").each(function(){
	          arrScreen.push($(this).attr("index"));
	       });
	       parent.slidebox.sortScreen(arrScreen);
	       $(this).find("li").each(function(i){
	          $(this).attr("index",i);
	       });
	       var flag = parent.serializeSlide();
	       if(flag)   
	         parent.portalMessage("桌面顺序已设置成功！");
	    }
	  });

		//添加屏幕
		$("#btnAddScreen").live("click",function(){
		  parent.addScreen();
			
			var screenlist = $("#screenPageDom ul");
			var _max = 0;
			screenlist.find("li.minscreenceil").each(function(){
			   _max = _max > parseInt($(this).attr("index")) ? _max : parseInt($(this).attr("index"));      
			});
			screenlist.find("#btnAddScreen").remove();
			screenlist.append("<li class='minscreenceil' index='"+ (_max+1) +"'>"+(_max+2)+"</li><li id='btnAddScreen' class='no-draggable-holder' title='添加屏幕'></li>");
			var flag = parent.serializeSlide();
			if(flag) parent.portalMessage("屏幕添加成功！");
		});
}



	var screens = [];

	function addScreen(index) {
	  $("#screenPageDom ul").append('<li index="' + index
	      + '" class="minscreenceil" style="">' + (index + 1)
	      + '<span title="移除此屏" class="closebtn" style="display: none;"></span></li>');
	}


	function reSortMinScreen(){
	  $("#screenPageDom ul li.minscreenceil").each(function(i){
	     $(this).text(i+1);
	     $(this).attr("index",i);      
	  });      
	}
</script>

</head>
<body onload="doInit();">
<center>
	<button type="button" class="btn btn-default"  onclick="setMemu()">模块设置</button>
	<button type="button" class="btn btn-default" onclick="setScreen()">分屏设置</button>
</center>
<div id="app_menu_setting" style="">
	    <div id="app_cate_list" >
			<div class="list-group"  id="menuParent" style="position:absolute;left:0px;top:40px;bottom:0px;width:150px;overflow-y:auto"></div>
		</div>
		<ul id="menuChild" style="position:absolute;left:170px;top:40px;bottom:0px;right:0px;padding:0px;overflow-y:auto;list-style:none"></ul>
</div>	

<!-- 分屏设置 -->
<div id="app_screen_setting" style="margin-top:10px;overflow-y:auto;height:320px;">
	 <div id="screenPageDom"  >
    <div id="portalSettingMsg"></div>
    <ul>
      
    </ul>
    <div style="clear: both"></div>
  </div>
</div>	
</body>

</html>

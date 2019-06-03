<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/header/header.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<link href="<%=cssPath%>/bootstrap.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="css/sms.css">
	<link rel="stylesheet" type="text/css" href="css/cmp-all.css">
	<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css">
	<link href="<%=cssPath%>/index1.css" rel="stylesheet" type="text/css" />
	
	<link rel="stylesheet" href="<%=contextPath %>/common/jquery/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<style type="text/css">
	/* 处理tab鼠标移上去样式 */
	.nav-tabs > li > a:hover {
 	     border-color: #0053ff; 
 		 background-color: #0053ff;
	}
	</style>
	<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/bootstrap/js/bootstrap.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/common/js/ZTreeSync.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/tools.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>

	<script type="text/javascript" src="<%=contextPath%>/common/js/TeeMenu.js"></script> 
	<script type="text/javascript" src="<%=contextPath%>/system/core/org/orgUser/orgUser.js"></script>	

</head>
<body style=";overflow-x:hidden; overflow-y:auto;height: 100%;">
<script type="text/javascript" charset="UTF-8">
$(function(){
	var MENU_EXPAND_SINGLE = getMenuList();
	//触发A点击事件
	$('#teemenu ul li a').click(function(even){
	    if($(this).parent().children("ul").is(':hidden')){//上级（LI）下面存在UL标签且为隐藏的 ，都展开，否则反之
	    	if(MENU_EXPAND_SINGLE == '1'){//是否同时可以展示多个菜单 0 -多个； 1- 只有一个
	    		if($(this).parent().siblings("li").children("ul").not(':hidden')){//上级(LI)兄弟节点的下级节点且是UL不是隐藏的
					$(this).parent().siblings("li").children("ul").hide();//隐藏上级(LI)兄弟节点的下级节点且是UL
			}	
	    	}
			
			$(this).parent().children("ul").show();//显示
	     }else{
	        $(this) .parent().children("ul").hide();//隐藏	
	     }  
	}).css('cursor','pointer');
   // .click();
	$("#teemenu ul li ul").hide();//隐藏下级所有节点
	
	
	$('#menuTable a:first').tab('show'); // Select first tab
	initTabClick();
 });
/**
 * 新建tab页
 */
function addMenuTab(index,url,menuName){
	//window.parent.document.getElementById("portlet").innerHTML = "";
	//window.parent.document.getElementById("portlet").style.display="none";
	var srcUrl = "<%=contextPath%>/system/core/menu/index.jsp";
	if(url){
		srcUrl = "<%=contextPath %>" + url;
	}
	if(index == '2'){
		srcUrl = "http://www.baidu.com";
		srcUrl = "<%=contextPath %>/system/core/menuGroup/manageGroup.jsp"; 
		parent.addTabs("菜单组管理",srcUrl);
	 }else  if(index == '3'){
			srcUrl = "<%=contextPath %>/system/core/menuGroup/setMuiltGroupPriv.jsp"; 
			parent.addTabs("添加/删除菜单",srcUrl);
		 }
	 else  if(index == '10'){
			srcUrl = "<%=contextPath %>/system/core/sysCode/index.jsp"; 
			parent.addTabs("系统编码",srcUrl);
		 }
	else if(index == '1'){
		 
		 if(!menuName){
			 menuName = "菜单管理"; 
		 }
		 parent.addTabs(menuName,srcUrl);
	 }else{
		 parent.addTabs(menuName,srcUrl);
	 } 
}
/**
 * 转转页面
 */
function toSrcUrl(menuName,menuCode){
	if(menuCode && menuCode != ''){
		addMenuTab('1',menuCode,menuName);
	}

}
/**
 * 获取菜单 ,才人员菜单
 */
function  getMenuList(){
	var url = "<%=contextPath %>/teeMenuGroup/getPrivSysMenu.action";
	var  MENU_EXPAND_SINGLE = "0";//是否同时可以展示多个菜单 0 -多个； 1- 只有一个
	var jsonObj = tools.requestJsonRs(url);
	if(jsonObj.rtState){
		var json = jsonObj.rtData;
		jQuery.each(json, function(i, sysMenu) {
			var menuId = sysMenu.menuId;
		    var menuName = sysMenu.menuName;
		    var  menuCode = sysMenu.menuCode ;
			if(menuId.length == 3){//主菜单
				$("#menu-lv1-ul").append(" <li id='menu-lv1-"+menuId+"'><a><span>"+ menuName + "</span></a></li>");
			}else if(menuId.length == 6){//二级菜单
				var parentMenuId = menuId.substring(0,3);
				$("#menu-lv1-" + parentMenuId).append("<ul  class='menu-lv2' >  <li id='menu-lv2-"+menuId+"'>"
				   +"<a ><span>"+ menuName + "</span></a></li></ul>");
				$("#menu-lv2-" + menuId).children('a').bind("click",function(){
					 toSrcUrl(menuName,menuCode);
				});
			}else if(menuId.length == 9){//三级菜单
				var parentMenuId = menuId.substring(0,6);
				$("#menu-lv2-"  + parentMenuId).addClass("menu-expand");//更改二级菜单样式
				$("#menu-lv2-" + parentMenuId).append("<ul  class='menu-lv3' >  <li id='menu-lv3-"+menuId+"'>"
				   +"<a><span>"+ menuName + "</span></a></li></ul>");
				$("#menu-lv3-" + menuId).children('a').bind("click",function(){
					 toSrcUrl(menuName,menuCode);
				});
			}
		});
		MENU_EXPAND_SINGLE = jsonObj.rtMsg;
	}
	return MENU_EXPAND_SINGLE;
}

/**
 * 初始化Tab实践
 */
function initTabClick(){
	$('#menuTable a').click(function (e) {
		setTabColor($(this));
	});
	
	$('#orgTable a').click(function (e) {
		onlineUser();
		setTabColor($(this));
	
	});
	
}

/**
 * 重新设置Tab颜色
 */
function setTabColor(ew){
	 $(".main-nav-tabs a").each(function(el){
		if(ew.html() !=  $(this).html()){
			$(this).css("color","#fff");
		}else{
			$(this).css("color","#000");
		}
		
	}); 
}

function showHide(){
	
	//.children("ul").not(':hidden')){//上
	if($("#tab-content").is(":hidden")){
	
		$("#tab-content").show();
	}else{
		alert("dd")
		$("#tab-content").hide();
	}

}
	


</script>
<body>
  <div  style="background-color:#E5E5E5; " >
	 <div class="teemenutop" >
		 <ul class="nav nav-tabs main-nav-tabs" style="border-bottom:0px;">
		  <li id="menuTable"><a href="#home" data-toggle="tab" style="padding:8px 13px 8px 13px;" >功能菜单</a></li>
		  <li id="orgTable" ><a href="#profile"  data-toggle="tab" style="color:#fff;padding:8px 13px 8px 13px;" >组织机构</a></li>
	<!-- 	  <li><a href="#messages" data-toggle="tab">Messages</a></li>
		  <li ><a href="#settings" data-toggle="tab">Settings</a></li> -->
		</ul>
		
	</div>
	
	<div class="tab-content" id="tab-content" >
	  	<div class="tab-pane fade in active" id="home">
		     <div  id="teemenu" class="teemenu"  >
		    	<ul class="menu-lv1" id="menu-lv1-ul">
		       		<li><a>个人事务</a>
		        <ul  class="menu-lv2" >
		            <li><a href="javascript:addMenuTab(4,'/system/core/base/message/index.jsp','消息管理');">消息管理</a>
		            <li><a href="javascript:addMenuTab(15,'/system/core/address/private/address/index.jsp','个人通讯簿');">内部短信</a>
		        </ul>
		       		</li>
		      <li><a>工作流程</a>
		         <ul  class="menu-lv2" >
		            <li><a href="javascript:addMenuTab(4,'/system/core/workflow/flowrun/createNewWork.jsp','新建流程');">新建流程</a>        
		            <li><a href="javascript:addMenuTab(5,'/system/core/workflow/flowrun/list/index.jsp','我的流程');">我的流程</a>        
		            <li><a href="javascript:addMenuTab(5,'/system/core/workflow/workmanage/workquery/index.jsp','流程查询');">流程查询</a>
		            <li><a href="javascript:addMenuTab(6,'/system/core/workflow/workmanage/flowRule/index.jsp','流程委托');">流程委托</a>
		            <li><a href="javascript:addMenuTab(5,'/system/core/workflow/workmanage/flowPriv/monitor.jsp','流程监控');">流程监控</a>
		            <li><a href="javascript:addMenuTab(5,'/system/core/workflow/workmanage/workdestroy/index.jsp','流程销毁');">流程销毁</a>
		         </ul>
		       </li>
		       <li><a>短信</a>
		         <ul  class="menu-lv2" >
		            <li><a href="javascript:addMenuTab(4,'/system/core/sms/index.jsp','内部短信');">内部短信</a>
		         </ul>
		       </li>
		      <li><a>系统管理</a>
<!--		        二级菜单开始-->
		         <ul  class="menu-lv2" >
		            <li class="menu-expand"><a>组织机构管理</a>
<!--		            	 三级菜单开始-->
				         <ul  class="menu-lv3" >
				         	<li><a href="javascript:addMenuTab(4,'/system/core/org/index.jsp','单位管理');">单位管理</a></li>
				            <li><a href="javascript:addMenuTab(5,'/system/core/dept/index.jsp','部门管理');">部门管理</a></li>
				            <li><a href="javascript:addMenuTab(5,'/system/core/person/index.jsp','用户管理');">用户管理</a></li>
				            <li><a href="javascript:addMenuTab(5,'/system/core/org/role/index.jsp','角色管理');">角色管理</a></li>
				            <li><a href="javascript:addMenuTab(5,'/system/core/menuGroup/index.jsp','权限组管理');">权限组管理</a></li>
				          
				         </ul>
<!--				         三级菜单结束-->
		       		</li>
		       		<li class="menu-expand"><a>工作流设置</a>
<!--		            	 三级菜单开始-->
				         <ul  class="menu-lv3" >
				         	<li><a href="javascript:addMenuTab(4,'/system/core/workflow/flowtype/manage/index.jsp','流程管理');">流程设置</a>
				            <li><a href="javascript:addMenuTab(5,'/system/core/workflow/flowform/manage/index.jsp','表单设置');">表单设置</a>
				            <li><a href="javascript:addMenuTab(5,'/system/core/workflow/flowsort/manage/index.jsp','流程分类设置');">流程分类设置</a>
				            <li><a href="javascript:addMenuTab(5,'/system/core/workflow/formsort/manage/index.jsp','表单类型设置');">表单类型设置</a>
				         </ul>
<!--				         三级菜单结束-->
		       		</li>
		       		<li><a href="javascript:addMenuTab('5','/system/core/system/sysPorlet/index.jsp','桌面模块管理');">桌面模块管理</a>
		       		</li>
		       		<li ><a href="javascript:addMenuTab(1);">菜单管理</a>
		       		</li>
		       		<li><a href="javascript:addMenuTab(10);">系统编码</a>
		       	    <li><a href="javascript:addMenuTab('5','/system/core/system/security/index.jsp','系统安全与设置');">系统安全与设置</a>
		       		<li><a href="javascript:addMenuTab('5','/system/core/system/interface/index.jsp','界面设置');">界面设置</a>
		     		<li><a href="javascript:addMenuTab('5','/system/core/person/setdescktop/index.jsp','控制面板');">控制面板</a>
					<li><a href="javascript:addMenuTab('5','/system/core/system/seal/index.jsp','印章管理');">印章管理</a>
					<li><a href="javascript:addMenuTab('5','/system/core/system/ipRule/index.jsp','访问控制');">访问控制</a>
					<li><a href="javascript:addMenuTab('5','/system/core/system/resourcemanager/index.jsp','资源管理');">资源管理</a>
		         </ul>
<!--		        二级菜单结束-->
		       </li> 
		    </ul>
	     </div>
      </div>
    
      <div class="tab-pane fade" id="profile" style="background-color: #fff;">
     	  <div  id="teemenu" class="teemenu"  style="padding:10px 0px 2px 0px;" align="center">
     		 <input type="button" id="" value="在线人员" class="btn btn-primary" onclick="onlineUser();">
     	 	 <input type="button" id="" value="全部人员" class="btn btn-primary" onclick="allUser();">
			<br>
     		<ul id="orgUserZtree" class="ztree" style="border:0px;width:180px;height:auto;padding-top:5px;"></ul> 
     	  </div>
     	 
       </div>
    </div>
 </div>
</body>
</html>

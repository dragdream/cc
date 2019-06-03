<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.tianee.oa.core.partthree.util.TeePartThreeUtil" %>

<%
   TeePerson  loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
   boolean hasPriv=TeePartThreeUtil.checkHasPriv(loginUser, "SYS_MENU");
   if(!hasPriv){//没有权限
	   response.sendRedirect("/system/core/system/partthree/error.jsp");
   }
   
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
html{
	padding:5px 0px 0px 5px;
}
</style>
<!-- jQuery 布局器 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/common/jqueryui/jquery.layout-latest.js"></script>

<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script>

function doInit(){
	$("#layout").layout({auto:true});
	$("#group").group();
	changePage(1);
	getMenuList();
}
function changePage(sel , sid){
	if(sel==1){
		$("#frame0").attr("src",contextPath+"/system/core/menu/blank.jsp");//外出
	}else if(sel==2){
		$("#frame0").attr("src",contextPath +"/system/core/menu/addupdate.jsp?sid=" + sid);//请假
	}
}
function getMenuList(){
	
	//添加例子一
	var url = "<%=contextPath %>/sysMenu/getSysMenu.action";
	var jsonObj = tools.requestJsonRs(url);
	if(jsonObj.rtState){
		var json = jsonObj.rtData;
		//for(var i =0;i<json.length;i++){
		jQuery.each(json, function(i, sysMenu) {
			//var sysMenu = json[i];
		 	$("#menuHeader").before("<tr class='TableData'>"
					//+"<td nowrap align='center'>" + sysMenu.uuid + "</td>"
					+"<td nowrap align='left'><b>" + sysMenu.menuId + "&nbsp;&nbsp;" + sysMenu.menuName  + "</b></td>"
					+"<td nowrap align='center'>"
					 +"<a href='javascript:toMainMenu(\"" + sysMenu.uuid+ "\")'>修改</a>"
					 +"&nbsp;&nbsp;<a id='menu-a-" + sysMenu.uuid+ "' href='javascript:void(0);'>下一级</a>"
					 +"&nbsp;&nbsp;<a href='javascript:deleteSysMenu(\"" + sysMenu.uuid + "\",\"" + sysMenu.menuId+ "\")'>删除</a>"
					 +"</td>"
		  	+ "</tr>");
		 	$("#menu-a-" + sysMenu.uuid).bind("click",function(){
		 		toChild(sysMenu.uuid,sysMenu.menuId,sysMenu.menuName);
			});
		});

	//}

	}else{
		alert(jsonObj.rtMsg);
	}
}
/**
 * 删除
 */
function deleteSysMenu(uuid,menuId){
	
	if(confirm("确认要删除此主菜单！删除后将删除所有下级菜单")){
		var url = "<%=contextPath %>/sysMenu/delMenu.action";
		var jsonRs = tools.requestJsonRs(url,{uuid:uuid,menuId:menuId});
		if(jsonRs.rtState){
			top.$.jBox.tip("删除成功！",'info',{timeout:1000});
			
			window.location.reload();
		}else{
			alert(jsonRs.rtMsg);
		}
		
	}
	
}
/**
 * 新增或删除路径
 */
function toMainMenu(uuid){
	var url = "<%=contextPath%>/system/core/menu/addupdate.jsp?uuid=" + uuid;
	$("#frame0").attr("src",url);	 
}
/**
 * 去下级菜单管理
 */
function toChild(uuid,menuId,menuName){
	var url = "<%=contextPath%>/system/core/menu/childMenu.jsp?pMenuId=" + menuId + "&uuid=" + uuid + "&menuName=" + encodeURIComponent(menuName);
	$("#frame0").attr("src",url);
}

/**
 * 跳转设置菜单页面
 */
function toSetMenu(type){
	var url = "";
	if(type == '1'){
		url = "<%=contextPath%>/system/core/menu/menuModulePriv.jsp";
	}else if(type == '2'){
		url = "<%=contextPath%>/system/core/menuPriv/index.jsp";
	}else{
		url = "<%=contextPath%>/system/core/menu/menupara.jsp";
	}
	$("#frame0").attr("src",url);

}
</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px;">
<div id="layout" >
	<div layout="west" width="340" style="overflow-y:auto;overflow-x:hidden;">
		<br>
		<div id="group" class="list-group">
		     <table border="0" width="100%" cellspacing="0" cellpadding="3" class="small" align="center" style="margin:10px 0px 0px 10px ;">
			    <tr>
			      <td class="Big"><span class="Big3">主菜单</span></td>
			    </tr>
			  </table><br>
			
			 
			   <table class="TableBlock" width="80%" align="center" >
			      <tbody id="tbody">
			        <tr  class="TableHeader">
			         <td colspan="2" onclick="toMainMenu('')" style="cursor: pointer;"> 新增主菜单</td>
			        </tr>
			       <!--  <tr style="background: none repeat scroll 0 0 #f7f8bd" >
			      		<td width="40" align="center">UUId</td>
			      		<td nowrap align="center" >菜单编号</td>
			     	    <td nowrap align="center" >菜单名称</td>
			      		<td nowrap align="center">操作</td>
			       </tr>
			        -->
			       <tr id='menuHeader'  class="TableHeader">
			         <td colspan="2" onclick="toSetMenu('')" style="cursor:pointer ;">菜单设置</td>
			        </tr>
			        <!--  <tr id='menuHeader'  class="TableHeader">
			         <td colspan="2" onclick="toSetMenu('1')" style="cursor: pointer;">菜单模块权限编码设置</td>
			        </tr> -->
			         <tr id='menuHeader'  class="TableHeader">
			         <td colspan="2" onclick="toSetMenu('2')" style="cursor: pointer;">菜单模块权限设置</td>
			        </tr>
			      </tbody>
			   </table>
			<br>
		</div>
	</div>
	<div layout="center" style="padding-left:10px">
		<iframe id="frame0" frameborder=0 style="width:100%;height:100%"></iframe>
	</div>
</div>
</body>
</html>
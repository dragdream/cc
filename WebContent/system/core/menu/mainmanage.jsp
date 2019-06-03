<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<title>菜单功能</title>
<link href="<%=cssPath %>/style.css" rel="stylesheet" type="text/css" />
<link  rel="stylesheet" type="text/css" href="<%=cssPath %>/stylebootstrap.css" />
<script type="text/javascript" src="<%=contextPath %>/common/jquery/jquery-min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/tools.js"></script>
<script type="text/javascript">
function doInit(){
	
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
			alert("删除成功！");
			
			var parent = window.parent;
			parent.location.reload();
		}else{
			alert(jsonRs.rtMsg);
		}
		
	}
	
}
/**
 * 新增或删除路径
 */
function toMainMenu(uuid){
	var parent = window.parent.contentFrame;
	parent.location = "<%=contextPath%>/system/core/menu/addupdate.jsp?uuid=" + uuid;
		 
}
/**
 * 去下级菜单管理
 */
function toChild(uuid,menuId,menuName){
	var parent = window.parent.contentFrame;
	parent.location = "<%=contextPath%>/system/core/menu/childMenu.jsp?pMenuId=" + menuId + "&uuid=" + uuid + "&menuName=" + encodeURIComponent(menuName);
}

/**
 * 跳转设置菜单页面
 */
function toSetMenu(type){
	var parent = window.parent.contentFrame;
	if(type == '1'){
		parent.location = "<%=contextPath%>/system/core/menu/menuModulePriv.jsp";
	}else if(type == '2'){
		parent.location = "<%=contextPath%>/system/core/menuPriv/index.jsp";
	}else{
		parent.location = "<%=contextPath%>/system/core/menu/menupara.jsp";
	}

}
</script>

</head>
<body onload="doInit()" style="overflow-x: hidden" >
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

</body>
</html>
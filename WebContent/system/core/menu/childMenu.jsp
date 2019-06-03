<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%
	String pMenuId = request.getParameter("pMenuId") == null ? "" : request.getParameter("pMenuId");
	String Puuid = request.getParameter("uuid") == null ? "" : request.getParameter("uuid");
	String menuName = request.getParameter("menuName") == null ? "" : request.getParameter("menuName");
	menuName = menuName.replace("\'", "\\\'");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<title>子菜单设置</title>
<link href="<%=cssPath %>/style.css" rel="stylesheet" type="text/css" />
<link  rel="stylesheet" type="text/css" href="<%=cssPath %>/stylebootstrap.css" />
<script type="text/javascript" src="<%=contextPath %>/common/jquery/jquery-min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/tools.js"></script>
<script type="text/javascript">
var menuId = '<%=pMenuId%>';
var uuid = "<%=Puuid%>";
var pMenuName = "<%=menuName%>";
function doInit(){
	//添加例子一
	var url = "<%=contextPath %>/sysMenu/getSysMenuByParent.action?menuId=" + menuId;
	var jsonObj = tools.requestJsonRs(url);
	if(jsonObj.rtState){
		var json = jsonObj.rtData;
		//for(var i =0;i<json.length;i++){
		jQuery.each(json, function(i, sysMenu) {
			var menuId = sysMenu.menuId;
			var icon = sysMenu.icon;
			var ICON =  "menu/default.gif";
			if(icon &&  icon != "" ){
				ICON = "menu/" + icon;
			}
			if(menuId){
				var level = (menuId.length-3)/3;
				var paddingLeft  = 0;
				if(level>1){
					paddingLeft = (level-1) * 25;
				}
				menuId =  "<span style='padding-left:" +paddingLeft+ "px;margin-left:5px;'></span>"
				<%-- //	+ "<img src='<%=imgPath%>/" + ICON+ "' align='absmiddle'/>" --%>
					+ menuId.substring(menuId.length - 3,menuId.length);
			}
			//var sysMenu = json[i];
		 	$("#tbody").append("<tr class='TableData'>"
					//+"<td nowrap align='center'>" + sysMenu.uuid + "</td>"
					+"<td nowrap align=''>" 
			
					+ menuId+ "</td>"
					+"<td nowrap align=''>" + sysMenu.menuName + "</td>"
					//+"<td nowrap align='center'>" + sysMenu.menuCode + "</td>"
					//+"<td nowrap align='center'>" + sysMenu.openFlag + "</td>"
					+"<td nowrap align=''>"
					 +"<a href='javascript:void(0);' id='menu-child-a-" + sysMenu.uuid + "'>修改</a>"
					 +"&nbsp;&nbsp;<a href='javascript:deleteSysMenu(\"" + sysMenu.uuid+ "\",\"" + sysMenu.menuId +  "\")'>删除</a>"
					 +"&nbsp;&nbsp;<a href='javascript:addMenuButton(\"" + sysMenu.uuid+ "\",\"" + sysMenu.menuId +  "\")'>按钮编辑</a>"
					 +"</td>"
		  	+ "</tr>"); 
		 	$("#menu-child-a-" + sysMenu.uuid).bind("click",function(){
		 		toMenu(sysMenu.uuid,sysMenu.menuId);
			});
		});

	//}

	}else{
		alert(jsonObj.rtMsg);
	}
}


function deleteSysMenu(uuid,menuId){
	if(confirm("确认要删除此主菜单！删除后将删除所有下级菜单")){
		var url = "<%=contextPath %>/sysMenu/delMenu.action";
		var jsonRs = tools.requestJsonRs(url,{uuid:uuid,menuId:menuId});
		if(jsonRs.rtState){
			alert("删除成功！");
			
			//var parent = window.parent;
			window.location.reload();
		}else{
			alert(jsonRs.rtMsg);
		}
		
	}
	
}

function toMenu(uuid,menuId){
	var url = "<%=contextPath%>/system/core/menu/addupdatechild.jsp?uuid=" + uuid + "&menuId=" + menuId;
	window.parent.$("#frame0").attr("src",url);	 
}

function toAddMenu(){
	var url = "<%=contextPath%>/system/core/menu/addupdatechild1.jsp?pMenuId=" + menuId + "&Puuid=" + uuid + "&menuName=" + encodeURIComponent(pMenuName);;
	window.parent.$("#frame0").attr("src",url);
}

function addMenuButton(uuid,menuId){
    var url = "<%=contextPath%>/system/core/menu/menuButton.jsp?uuid=" + uuid + "&menuId=" + menuId;
    window.parent.$("#frame0").attr("src",url);  
}


</script>

</head>
<body onload="doInit()">
   <table border="0" width="95%" cellspacing="0" cellpadding="3" class="small" align="center">
    <tr>
      <td class="Big"><span class="Big3">子菜单设置</span></td>
    </tr>
  </table><br>

 
   <table class="TableList" width="60%" align="center">
      <tbody id="tbody">
        <tr>
         <td colspan="3" class="TableHeader" id="mainMenuName">
         <%=menuName %>
        </td>
        </tr>
        <tr>
        <td colspan="3" class="" align="center">
          <img src="<%=imgPath%>/notify_new.gif" align="absmiddle"><span class="big3">
         <a href="javascript:toAddMenu();">新增菜单</a></td>
        </tr>
      
        <tr class="TableHeader">
     <!--  		<td width="40" align="center">UUId</td> -->
      		<td nowrap align="center">菜单编号</td>
     	    <td nowrap align="center">菜单名称</td>
      		<td nowrap align="center">操作</td>
       </tr>
      </tbody>
   </table>

</body>
</html>
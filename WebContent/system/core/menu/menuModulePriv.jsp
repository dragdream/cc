<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<title>菜单模块权限编号设置</title>


<script type="text/javascript">

function doInit(){
	//添加例子一
	var url = "<%=contextPath %>/sysMenu/getAllSysMenu.action" ;
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
				var level = (menuId.length)/3;
				var paddingLeft  = 0;
				if(level>1){
					paddingLeft = (level-1) * 40;
				}
				menuId =  "<span style='padding-left:" +paddingLeft+ "px;margin-left:5px;'></span>"
					+ menuId;
			}
			//var sysMenu = json[i];
		 	$("#tbody").append("<tr class='TableData'>"
					//+"<td nowrap align='center'>" + sysMenu.uuid + "</td>"
					+"<td nowrap align=''>" 
			
					+ menuId+ "</td>"
					+"<td nowrap align=''>" + sysMenu.menuName + "</td>"
					+"<td nowrap align=''>" 
					+"<input type='text' id='menuModuleNo_" + sysMenu.uuid + "' value='" + sysMenu.menuModuleNo  +"' maxlength='30'/>"
					+ "</td>"
					//+"<td nowrap align='center'>" + sysMenu.menuCode + "</td>"
					//+"<td nowrap align='center'>" + sysMenu.openFlag + "</td>"
					+"<td nowrap align=''>"
					 +"<a href='javascript:void(0);' id='menu-child-a-" + sysMenu.uuid + "'>保存</a>"
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

/**
 * 设置
 */
function toMenu(id){
	var menuModuleNo = $("#menuModuleNo_" + id).val();
	var url = "<%=contextPath %>/sysMenu/updateMenuModuleNo.action" ;
	var para = {menuModuleNo:menuModuleNo , uuid:id};
	var jsonObj = tools.requestJsonRs(url , para);
	if(jsonObj.rtState){
		 $.jBox.tip('保存成功！','info',{timeout:500});
	}else{
		alert(jsonObj.rtMsg);
	}
}
</script>

</head>
<body onload="doInit()">
   <table border="0" width="95%" cellspacing="0" cellpadding="3" class="small" align="center">
    <tr>
      <td class="Big"><span class="Big3">菜单模块权限编号设置</span></td>
    </tr>
  </table><br>

 
   <table class="TableList" width="500" align="center">
      <tbody id="tbody">
   
        <tr class="TableHeader">
     <!--  		<td width="40" align="center">UUId</td> -->
      		<td nowrap align="center" width='160' nowrap> 菜单编号</td>
     	    <td nowrap align="center" nowrap>菜单名称</td>
     	    <td nowrap align="center" nowrap>菜单模块权限编号</td>
      		<td nowrap align="center" nowrap>操作</td>
       </tr>
      </tbody>
   </table>

</body>
</html>
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
		 	$("#tbody").append("<tr class='TableData'>"
					+"<td nowrap align='center'>" + sysMenu.uuid + "</td>"
					+"<td nowrap align='center'>" + sysMenu.menuId + "</td>"
					+"<td nowrap align='center'>" + sysMenu.menuName + "</td>"
					+"<td nowrap align='center'>" + sysMenu.menuCode + "</td>"
					+"<td nowrap align='center'>" + sysMenu.openFlag + "</td>"
					+"<td nowrap align='center'>"
					 +"<a href='javascript:updateSysMenu(\"" + sysMenu.uuid+ "\")'>修改</a>"
					 +"&nbsp;&nbsp;<a href='javascript:deleteSysMenu(\"" + sysMenu.uuid + "\",\"" + sysMenu.menuId+ "\")'>删除</a>"
					 +"</td>"
		  	+ "</tr>"); 
			
		});

	//}

	}else{
		alert(jsonObj.rtMsg);
	}
}

function updateSysMenu(uuid){
    window.location.href = "<%=contextPath %>/system/core/menu/addupdate.jsp?uuid=" + uuid;
}

function deleteSysMenu(uuid,menuId){
	if(confirm("确认要删除此主菜单！删除后将删除所有下级菜单")){
		var url = "<%=contextPath %>/sysMenu/delMenu.action";
		var jsonRs = tools.requestJsonRs(url,{uuid:uuid,menuId:menuId});
		if(jsonRs.rtState){
			alert("删除成功！");
	        window.location.reload();
		}else{
			alert(jsonRs.rtMsg);
		}
	}
	
}
</script>

</head>
<body onload="doInit()">
<div></div>
   <table border="0" width="100%" cellspacing="0" cellpadding="3" class="small" align="center">
    <tr>
      <td class="Big"><img src="<%=imgPath%>/infofind.gif" align="absmiddle"><span class="big3">菜单查询</span></td>
    </tr>
  </table><br>

   <table class="TableList" width="80%" align="center">
      <tbody id="tbody">
        <tr class="TableHeader">
      		<td width="40" align="center">UUId</td>
      		<td nowrap align="center">菜单编号</td>
     	    <td nowrap align="center">菜单名称</td>
      		<td nowrap align="center">菜单路径</td>
      		<td nowrap align="center">菜单状态</td>
      		<td nowrap align="center">操作</td>
       </tr>
      </tbody>
   </table>

</body>
</html>
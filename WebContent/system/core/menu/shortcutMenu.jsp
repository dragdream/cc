<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<title>界面主题</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>




<script type="text/javascript">

function doInit(){
	
	var url = "<%=contextPath %>/shortcutMenuController/getMenuInfoByPerson.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonRs = tools.requestJsonRs(url,para);
		//alert(jsonRs);
		if(jsonRs.rtState){
			var data = jsonRs.rtData;
			var selectSysMenuModelList = data.selectSysMenuModelList;
			var notSelectSysMenuModelList = data.notSelectSysMenuModelList;
			jQuery.each(selectSysMenuModelList, function(i, sysMenu) {
				var menuName = sysMenu.menuName;
				var menuId = sysMenu.menuName;
				var menuCode = sysMenu.menuCode;
				var li = "<li class='menuItem'  id='menu-lv3-"+menuId+"'>" +
				"<a href='javascript:void(0);' style='padding-top:3px;padding-bottom:3px;'>" +
				"<i class='icon-leaf'></i>&nbsp;&nbsp;"+
				"<span class='menu-text'  style='font-family:;font-size:12px'>"+menuName +"</span>"+ 
            	"</a>" + 
				"</li>" ;
				$("#menuNav").append(li);
				$("#menu-lv3-" + menuId).children('a').bind("click",function(){
					 parent.toSrcUrl(menuName,menuCode);
				});
			});
			
			
		}else{
			alert("查询错误");
			return;
		}
}




</script>

</head>
<body onload="doInit()">
<ul class="nav nav-list" id="menuNav" >
					          	 
 </ul>
	
</body>
</html>
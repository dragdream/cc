<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
body{
	font-size:12px;
}
.c{
	width:150px;
	float:left;
	margin-left:5px;
	margin-right:5px;
	padding:5px;
}
.cc{
	margin-left:10px;
	padding:2px;
}
td{
	border:1px solid #66ccff;
	background:#f0f0f0;
}
</style>
<script>
var sysId = "<%=request.getParameter("sysId") == null ? "" : request.getParameter("sysId")%>";
function doInit(){
	$.MsgBox.Loading();
	var width = 0;
	var json = tools.requestJsonRs(contextPath+"/sysMenu/getSysMenuBySysId.action?sysId="+sysId);
	//遍历出所有的一级菜单
	var list = json.rtData;
	for(var i=0;i<list.length;i++){
		var item = list[i];
		if(item.menuId.length==3){
			$("<td class='c' nowrap ><input type='checkbox' checked='checked' disabled='disabled' id='" +item.menuId+ "'/><b style='font-size:14px'>"+item.menuName+"</b><div id='pc"+item.menuId+"' class='cc'></div></td>").appendTo($("#tr"));
			width+=190;
		}else if(item.menuId.length==6){
			var parentId = item.menuId.substring(0,3);
			$("<div class='cc'><input type='checkbox' checked='checked' disabled='disabled' id='"+item.menuId+"' lv1='" +parentId+ "'/>"+item.menuName+"<div id='pc"+item.menuId+"' class='cc'></div></div>").appendTo($("#pc"+parentId));
		}else if(item.menuId.length==9){
			var parentId = item.menuId.substring(0,6);
			var lv1 = item.menuId.substring(0,3);
			$("<div class='cc'><input type='checkbox' checked='checked' disabled='disabled' id='"+item.menuId+"' lv1='"+lv1+"' lv2='"+parentId+"'/>"+item.menuName+"<div id='pc"+item.menuId+"' class='cc'></div></div>").appendTo($("#pc"+parentId));
		}
	}
	$("#tr").css({width:width});
	$.MsgBox.CloseLoading();
}


function click0(obj){

}

</script>

</head>
<body onload="doInit()" style="overflow:hidden">
<div id="toolbar" class="topbar clearfix" style="position:absolute;top:0px;left:0px;right:0px;height:20px">
   <div class="fl">
   	&nbsp;&nbsp;<button type="button" class="btn-win-white" onclick="location='index.jsp'">返回</button>
   	&nbsp;&nbsp;&nbsp;&nbsp;
   </div>
</div>
<div style="position:absolute;top:60px;left:0px;right:0px;bottom:0px;overflow:auto">
	<div id="tr">
		
	</div>
</div>
</body>
</html>
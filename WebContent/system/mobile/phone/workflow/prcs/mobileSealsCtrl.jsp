<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	String itemId = request.getParameter("itemId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<script src="<%=contextPath %>/common/easyui/jquery.min.js"></script>
<script src="<%=contextPath %>/common/js/sys.js"></script>
<script src="<%=contextPath %>/common/js/tools.js"></script>
<script src="<%=contextPath %>/common/js/sysUtil.js"></script>
<script>
var itemId = "<%=itemId%>";
var list;
var contextPath = "<%=contextPath %>";
function doInit(){
	tools.requestJsonRs(contextPath+'/mobileSeal/myMobileSealsWithDataForMobile.action',{},true,function(json){
		list = json.rows;
		var render = [];
		for(var i=0;i<list.length;i++){
			render.push("<div onclick=\"addSeal("+i+")\">");
			render.push("<p><img src=\""+list[i].sealData+"\" height='100px'></p>");
			render.push("<p>"+list[i].sealName+"</p>");
			render.push("</div>");
		}
		$("#container").html(render.join(""));
	});
	
// 	$('#datagrid').datagrid({
// 		url:contextPath+'/mobileSeal/myMobileSealsWithData.action',
// 		pagination:true,
// 		singleSelect:true,
// 		striped: true,
// 		border: false,
// 		toolbar:'#toolbar',//工具条对象
// 		checkbox:true,
// 		fitColumns:true,//列是否进行自动宽度适应
// 		columns:[[
// 			{field:'sealName',title:'签章名称',width:150},
// 			{field:'userName',title:'签章使用人',width:150},
// 			{field:'deviceNo',title:'绑定设备号',width:150},
// 			{field:'sealData',title:'签章预览',width:150,formatter:function(value,rowData,rowIndex){
// 				return "<img src=\""+value+"\" style='height:50px;cursor:pointer' onclick=\"sealView("+rowIndex+")\"/>";
// 			}},
// 			{field:'_manage',title:'操作',width:50,formatter:function(value,rowData,rowIndex){
// 				var render = [];
// 				render.push("<a href='javascript:void(0)' onclick=\"$('#enterPwd').modal('show');window.rowIndex="+rowIndex+";\">盖章</a>");
// 				return render.join("&nbsp;/&nbsp;");
// 			}}
// 		]]
// 	});
}


function addSeal(index){
	var data = list[index];
	var value = window.prompt("请输入签章密码：", "");
	if(data.pwd==value){ //签章密码输入正确
		parent.doAddMobileSealCtrl(itemId,data.sealData);
		parent.layer.closeAll();
	}else{//签章密码输入错误
		alert("密码输入错误！");
	}
	
	
}


function sealView(rowIndex){
	var rows = $('#datagrid').datagrid('getRows');
	$("#sealImg").attr("src",rows[rowIndex]["sealData"]);
	$("#sealViewDiv").modal("show");
}

</script>
</head>
<body onload="doInit()" >
<div style="position:absolute;left:0px;right:0px;top:0px;height:30px">
<button onclick="parent.layer.closeAll();">关闭</button>
</div>
<div id="container" style="text-align:center;position:absolute;left:0px;right:0px;top:30px;bottom:0px;">
</div>
</body>
</html>
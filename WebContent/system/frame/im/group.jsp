<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/header/header.jsp" %>
<style>
.btn01{
	cursor:pointer;
}
.btn01:hover{
	background:#f7f7f7;
}
</style>
<script type="text/javascript" charset="UTF-8">
function doInit(){
	tools.requestJsonRs(contextPath+"/messageGroupManage/getMyGroup.action",{},true,function(json){
		var list = json.rtData;
		var render = [];
		for(var i=0;i<list.length;i++){
			render.push("<tr class='btn01'>");
			render.push("<td onclick=\"window.external.IM_OpenGroup('"+list[i].sid+"','"+list[i].groupName+"')\">&nbsp;&nbsp;<img src='style/imgs/group.png' />&nbsp;&nbsp;"+list[i].groupName+"</td>");
			render.push("</tr>");
		}
		$("#tbody").append(render.join(""));
	});
}
</script>
<style>
.button{
display:block;
float:left;
cursor:pointer;
margin-left:5px;
padding-left:10px;
padding-right:10px;
padding-top:4px;
padding-bottom:4px;
color:#3997d0;
text-decoration:none;
}
.button:hover{
color:white;
background:#5b7fcb;
}
</style>
</head>
<body onload="doInit()" style="margin:0px;padding:0px;">
	<div style="position:absolute;top:0px;left:0px;right:0px;height:30px;background:#f0f0f0;border-bottom:1px solid #cdcdcd">
		<div style="font-size:12px;margin-right:20px;margin-top:3px;">
			<p class="button" onclick="window.external.IM_OpenNavigation('群组管理','/system/core/base/message/group/index.jsp',800,600)">管理</p>
			<p class="button" onclick="window.location.reload();">刷新</p>
		</div>
	</div>
	<div style="position:absolute;top:30px;left:0px;right:0px;bottom:0px;overflow:auto">
		<table class="TableList" width="100%" align="center" >
		      <tbody id="tbody">
		   		
		      
		      </tbody>
		   </table>
	</div>
</body>
</html>
		        
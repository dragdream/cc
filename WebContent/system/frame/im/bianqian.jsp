<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/header/header.jsp" %>
<script type="text/javascript" charset="UTF-8">
function doInit(){
	tools.requestJsonRs(contextPath+"/noteManage/selectPersonalNote.action",{},true,function(json){
		var list = json.rtData;
		var render = [];
		for(var i=0;i<list.length;i++){
			render.push("<div class='outline outline_"+list[i].color+"' onclick='window.external.IM_OpenNote("+list[i].sid+");'>");
			render.push("<div>");
			render.push(list[i].content.substring(0,50).replace(/\r\n/gi,"<br/>")+"…");
			render.push("</div>");
			render.push("<table class='tb'>");
			render.push("<tr>");
			render.push("<td>"+list[i].createTimeStr+"</td>");
			render.push("<td class='tb_op'>");
			render.push("<img src='/common/images/other/5.png' onclick=\"Email("+list[i].sid+")\"/>");
			render.push("&nbsp;&nbsp;");
			render.push("<img src='/common/images/other/8.png' onclick='window.event.cancelBubble = true;del("+list[i].sid+")'/>");
			render.push("</td>");
			render.push("</tr>");
			render.push("</table>");
			render.push("</div>");
		}
		
		$("#warpcontent").html(render.join(""));
		
	});
}


function Email(sid){
	window.external.IM_OpenNavigation("邮件","/system/core/email/send.jsp?noteId="+sid,1000,700);
	window.event.cancelBubble = true;
}

function exp(){
	$("#frame0").attr("src",contextPath+"/noteManage/export.action");
}

function del(sid){
	if(window.confirm("是否删除该便签？")){
		tools.requestJsonRs(contextPath+"/noteManage/del.action",{sid:sid});
		window.location.reload();
	}
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
.tb{
width:100%;
font-size:12px;
color:#999999;
margin-top:10px;
}
.tb_op{
text-align:right;
display:none;
}
.outline{
cursor:pointer;
margin:10px;
border:1px solid #e6ddde;
border-left:4px solid #e6ddde;
background:#ebe7e6;
font-size:12px;
padding:5px;
border-radius:5px;
}
.outline:hover{
	background:#f5f5f5;
}
.outline:hover .tb_op{
	display:block;
}
.outline_1{
margin:10px;
border:1px solid #f34b74;
border-left:4px solid #f34b74;
background:#feb9be;
font-size:12px;
padding:5px;
border-radius:5px;
}
.outline_2{
margin:10px;
border:1px solid #c1e28b;
border-left:4px solid #c1e28b;
background:#d2f8bd;
font-size:12px;
padding:5px;
border-radius:5px;
}
.outline_3{
margin:10px;
border:1px solid #ebdb83;
border-left:4px solid #ebdb83;
background:#fefacb;
font-size:12px;
padding:5px;
border-radius:5px;
}
.outline_4{
margin:10px;
border:1px solid #a5cfe5;
border-left:4px solid #a5cfe5;
background:#bbe0fd;
font-size:12px;
padding:5px;
border-radius:5px;
}
.outline_5{
margin:10px;
border:1px solid #e6ddde;
border-left:4px solid #e6ddde;
background:#ebe7e6;
font-size:12px;
padding:5px;
border-radius:5px;
}
</style>
</head>
<body onload="doInit()">
	<div style="position:absolute;top:0px;left:0px;right:0px;height:30px;background:#f0f0f0;border-bottom:1px solid #cdcdcd">
		<div style="font-size:12px;margin-right:20px;margin-top:3px;">
			<p class="button" onclick="window.external.IM_OpenNote(0);">新建</p>
			<p class="button" onclick="exp()">导出</p>
			<p class="button" onclick="window.location.reload();">刷新</p>
		</div>
	</div>
	<div id="warpcontent" style="position:absolute;top:30px;left:0px;right:0px;bottom:0px;overflow:auto">
	</div>
	<iframe id="frame0" style="display:none"></iframe>
</body>
</html>
		        
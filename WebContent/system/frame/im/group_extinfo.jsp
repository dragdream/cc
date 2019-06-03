<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/header/header.jsp" %>
<style>
.btn01{
	background:#056db4;
	color:white;
	font-size:12px;
	cursor:pointer;
	font-weight:bold;
}
.btn01:hover{
	background:#f5f5f5;
	color:#000;
}
.btn01select{
	background:#f5f5f5;
	color:#000;
}

.btn02{
	cursor:pointer;
	font-size:12px;
	padding:5px;
}
.btn02:hover{
	background:#f7f7f7;
}
</style>
<script type="text/javascript" charset="UTF-8">
function doInit(){
	tools.requestJsonRs(contextPath+"/messageGroupManage/selectById.action",{id:<%=request.getParameter("id")%>},true,function(json){
		var data = json.rtData;
		$("#info1").html(data.groupNotify);
		$("#info2").html("<b>群组主题：</b><br/>"+data.groupSubject+"<br/><b>群主简介：</b><br/>"+data.groupIntroduction);
		
		//渲染群组人员
		var toId = data.toId.split(",");
		var toUserId = data.toUserId.split(",");
		var toUserName = data.toName.split(",");
		var render = [];
		for(var i=0;i<toId.length;i++){
			render.push("<div class='btn02' onclick=\"window.external.IM_OpenDialog('"+toUserId[i]+"','"+toUserName[i]+"')\">"+toUserName[i]+"</div>");
		}
		
// 		var list = json.rtData;
// 		var render = [];
// 		for(var i=0;i<list.length;i++){
// 			render.push("<tr class='btn01'>");
// 			render.push("<td onclick=\"window.external.IM_Open('"+list[i].sid+"','"+list[i].groupName+"')\">"+list[i].groupName+"</td>");
// 			render.push("</tr>");
// 		}
		$("#div0").append(render.join(""));
	});
}

function change(obj,sel){
	$(".btn01").removeClass("btn01select");
	if(sel==1){
		$("#info1").show();
		$("#info2").hide();
	}else if(sel==2){
		$("#info1").hide();
		$("#info2").show();
	}
	$(obj).addClass("btn01select");
}
</script>
</head>
<body onload="doInit()" style="margin:0px;overflow:hidden">
	<div style="position:absolute;top:0px;left:0px;right:0px;height:30px;">
		<table style="border-collapse:collapse;width:100%;border:0px;height:100%;text-align:center;">
			<tr style="font-size:12px;font-family:微软雅黑">
				<td class="btn01 btn01select" onclick="change(this,1)">群组公告</td>
				<td class="btn01" onclick="change(this,2)">群组信息</td>
			</tr>
			</table>
	</div>
	<div style="position:absolute;top:30px;left:0px;right:0px;height:130px;font-size:12px">
		<div id="info1" style="padding:5px;">
			
		</div>
		<div id="info2" style="padding:5px;display:none">
			
		</div>
	</div>
	<div style="position:absolute;font-size:12px;font-weight:bold;line-height:25px;top:160px;left:0px;right:0px;height:25px;background:#f0f0f0;border-top:1px solid #cdcdcd;border-bottom:1px solid #cdcdcd">
		&nbsp;&nbsp;<img src="style/imgs/group.png" />&nbsp;&nbsp;群组成员
	</div>
	<div id="div0" style="position:absolute;font-size:12px;top:185px;left:0px;right:0px;bottom:0px;overflow:auto">
		
	</div>
</body>
</html>
		        
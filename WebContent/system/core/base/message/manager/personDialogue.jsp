<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String personId = request.getParameter("personId") == null ? "0" : request.getParameter("personId") ;
	String personUserId = request.getParameter("userId") == null ? "" : request.getParameter("userId") ;
	String personName = request.getParameter("personName") == null ? "" : request.getParameter("personName") ;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/js/lazyloader.js"></script>
<title>对话记录管理</title>
<script type="text/javascript" charset="UTF-8">
var loader;
var personId = "<%=personId%>";
var personName = "<%=personName%>";
var personUserId= "<%=personUserId%>";
var firstLoad = true;
var firstHeight;
function doInit(){
	loader = new lazyLoader({
		url:contextPath + '/messageManage/getMessageListByPersonId.action?sort=sendTime&personId=' + encodeURI(personId)  + "&userId=" + encodeURI(personUserId),
		placeHolder:'loadMore',
		pageSize:20,
		contentHolder:'smsBoxContent',
		rowRender:function(rowData){
			var render = [];
			if(rowData.fromId==personUserId){//如果是接收方
				render.push("<div><div style='color:#007eff;margin-bottom:5px;font-size:12px;'>" + rowData.fromIdName + "&nbsp;" + rowData.sendTimeDesc + "</div><div style='margin-bottom:10px;font-size:12px;text-indent:20px;'>" + msgReceiveFilter(rowData.content) + "</div></div>");
			}else{//否则是发送
				render.push("<div><div style='color:#ff515d;margin-bottom:5px;font-size:12px;'>" + rowData.fromIdName + "&nbsp;" + rowData.sendTimeDesc + "</div><div style='margin-bottom:10px;font-size:12px;text-indent:20px;'>" + msgReceiveFilter(rowData.content) + "</div></div>");
			}
			var firstDiv = $("#smsBoxContent").find("div:first");
			if(firstDiv.length==0){
				
				$("#smsBoxContent").append(render.join(""));
			}else{
				firstDiv.before(render.join(""));
				$("#smsBoxContent").find("center").hide();
			}
			
			return "";
		},
		onLoadSuccess:function(){
			$("#loadMore").html("查看更多历史记录");
			if(firstLoad){
				$(document).scrollTop(1000000000);
			}else{
				$(document).scrollTop($("#smsBoxContent").height()-firstHeight);
			}
			firstHeight = $("#smsBoxContent").height();
			firstLoad = false;
		},
		onNoData:function(){
			$("#loadMore").hide();
			var render = ["<br/><br/><center style='font-size:12px'>无更多与该人员的对话消息<hr style='margin-top: 20px;border: 0;border-top: 1px solid #eeeeee;'/></center>"];
			var firstDiv = $("#smsBoxContent").find("div:first");
			if(firstDiv.length==0){
				$("#smsBoxContent").append(render.join(""));
			}else{
				firstDiv.before(render.join(""));
			}
		}
	});
}

function msgReceiveFilter(content){
	//处理文件
	var regexp = /\[#FILE:[^\]]+:[0-9]+\]/gi;
	content = content.replace(/</gi,"&lt;").replace(/>/gi,"&gt;").replace(/\n\r/gi,"<br/>");
	content = content.replace(/\n/gi,"<br/>");
	var newContent = content;
	var imgBlock;
	while(imgBlock=regexp.exec(content)){
		imgBlock+="";
		var sp = imgBlock.split(":");
		newContent = newContent.replace(imgBlock,"<span style=\"cursor:pointer;color:red\" onclick=\"downloadFile('" + sp[2].replace("]","") + "')\">" + sp[1] + "</span>");
	}
	
	//处理图片
	regexp = /\[#IMG:[^\]]+:[0-9]+\]/gi;
	while(imgBlock=regexp.exec(content)){
		imgBlock+="";
		var sp = imgBlock.split(":");
		var sid = sp[2].replace("]","");
		newContent = newContent.replace(imgBlock,"<img src=\""+contextPath+"/attachmentController/downFile.action?id="+sid+"\" style='max-width:800px;cursor:pointer' onclick=\"ExplorePic(this,'"+sid+"')\"/>");
	}
	
	//处理录音
	regexp = /\[#VOICE:[^\]]+:[0-9]+:[0-9]+\]/gi;
	while(imgBlock=regexp.exec(content)){
		imgBlock+="";
		var sp = imgBlock.split(":");
		var sid = sp[3].replace("]","");
		newContent = newContent.replace(imgBlock,"语音消息：<video src=\""+contextPath+"/attachmentController/downFile.action?id="+sid+"\" style='cursor:pointer' width='20px' height='20px' poster=\"/common/images/workflow/voice_play.png\" onclick=\"this.play()\"></video>"+sp[2]+"s");
	}
	
	return newContent;
}

function downloadFile(sid){
	var downloadFrame = $("#DOWN_LOAD_FRAME___");
	if(downloadFrame.length==0){
		downloadFrame = $("<iframe style='display:none'></iframe>");
		$("body").append(downloadFrame);
	}
	downloadFrame.attr("src",contextPath+"/attachmentController/downFile.action?id="+sid);
}

function ExplorePic(obj,sid){
	top.$.picExplore({src:contextPath+"/attachmentController/downFile.action?id="+sid});
}
</script>
</head>
<body onload="doInit()" style="padding:10px;">
<!-- 短消息面板 -->
<div id="smsbox" style="border: 1px solid #f2f2f2;background-color: #f9fcfe;height: auto;">
	<div id="loadMore" style="font-size:12px;padding:10px;color:blue;cursor:pointer" onclick="this.innerHTML='加载中，请稍候……'">加载中，请稍候……</div>
	<div id="smsBoxContent" style="padding-left: 30px;">
	</div>
</div>
</body>
</html>
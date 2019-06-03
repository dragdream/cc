<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/layout/layout.js"></script>
<script>
var path = "";
function doInit(){
	var json =  tools.requestJsonRs(contextPath+"/attachmentController/getRemoteFolder.action",{path:""});
	render(json.rtData);
}

function render(list){
	var r = [];
	if(list && list!=null){
		for(var i in list){
			r.push("<p onclick=\"enter('"+list[i]+"')\">"+list[i]+"</p>");
		}
	}else{
		
	}
	$("#files").html(r.join(""));
}

function enter(p){
	path = p;
	$("#pathDesc").html(path);
	var json =  tools.requestJsonRs(contextPath+"/attachmentController/getRemoteFolder.action",{path:p});
	render(json.rtData);
}

function back(){
	var sp = path.split("/");
	//判断到根磁盘目录
	if(sp.length==2 && sp[1]==""){//根目录 c:/    /
		path = "";
		$("#pathDesc").html(path);
		var json =  tools.requestJsonRs(contextPath+"/attachmentController/getRemoteFolder.action",{path:""});
		render(json.rtData);
	}else if(sp.length==2 && sp[1]!=""){
		path = sp[0]+"/";
		$("#pathDesc").html(sp[0]+"/");
		var json =  tools.requestJsonRs(contextPath+"/attachmentController/getRemoteFolder.action",{path:sp[0]+"/"});
		render(json.rtData);
	}else{
		path = "";
		for(var i=0;i<sp.length-1;i++){
			path+=sp[i];
			if(i!=sp.length-2){
				path+="/";
			}
		}
		
		$("#pathDesc").html(path);
		var json =  tools.requestJsonRs(contextPath+"/attachmentController/getRemoteFolder.action",{path:path});
		render(json.rtData);
	}
}

function ok(){
	xparent.$("#imgDir").val(path);
	window.close();
}
</script>
<style>
p{
margin:0px;
padding:2px;
cursor:pointer;
}
p:hover{
background:#f0f0f0;
}
</style>
</head>
<body onload="doInit();" style="margin:10px;font-size:12px">
<div  style="margin-bottom:10px;height:20px;">选择路径：<span id="pathDesc"></span>&nbsp;&nbsp;<img src="back.gif" onclick="back()" style="width:15px;"/></div>
<div id="files" style="border:1px solid gray;width:500px;height:200px;overflow:auto"></div>
<br/>
<button class="btn btn-default" onclick="ok()">确认</button>
</body>
</html>
<%@page import="com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String cusId = request.getParameter("cusId")==null?"0":request.getParameter("cusId");
	
%>
<!DOCTYPE HTML>
<html>
<head>
<title>商机选择</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<style>
	.mui-media.active{
		background-color:rgb(55, 148, 243);
		color:#fff;
	}
	.mui-media:hover{
		background-color:rgb(55, 148, 243);
		color:#fff;
	}
	.mui-media *{
		color:inherit;
	}
</style>
</head>
<body onload="doInit()">
<header class="mui-bar mui-bar-nav">
	<button id='closeBtn' class="mui-btn mui-btn-link mui-btn-nav mui-pull-left" onclick="doSelect()">
	    <span class="mui-icon mui-icon-checkmarkempty"></span>
	</button>
	<h1 class="mui-title" >选择商机</h1>
	<button id='closeBtn' class="mui-btn mui-btn-link mui-btn-nav mui-pull-right" onclick="closeWin()">
	    <span class="mui-icon mui-icon-closeempty"></span>
	</button>
</header>
<div class="mui-input-group" style="position:fixed;top:40px;left:0px;right:0px;height:40px;">
	<div class="mui-input-row">
		<input id="searchBox" type="text" placeholder="请输入商机名称搜索"  oninput="doSearch()" />
	</div>
</div>
<div class="mui-content" style="margin:0px;padding:0px;position:fixed;top:95px;left:0px;right:5px;bottom:0px;overflow:auto">
	<ul class="mui-table-view" id="list" style="margin:0px;padding:0px;">
			
	</ul>
</div>





<script>


var cusId = "<%=cusId%>";
var chanceName,sid;
//初始化数据
function doInit(){
	var url = contextPath+'/crmChancesController/selectChances.action';//商机列表
	
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{cusId:cusId},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			var render = [];
			if(json.rtState){
				var prcs = json.rtData;
				for ( var i = 0; i < prcs.length; i++) {
					render.push("<li class=\"mui-table-view-cell mui-media\"   sid='"+prcs[i].sid+"' chanceName='"+prcs[i].chanceName+"' >");
					render.push("<div class=\"mui-media-body\">");
					render.push("<p style='font-size:12px'><span style='padding-left:10px;'>"+prcs[i].chanceName+"</span></p>");
					render.push("</div>");
					render.push("</li>");
				}
				$("#list").html(render.join(""));
				
				$(".mui-media").each(function(i,obj){
					obj.removeEventListener("tap",preSelect);
					obj.addEventListener("tap",preSelect);
				});
			}else{
				alert("查询失败！");
			}
			
		
		},
		
	});
	
}

//模糊查询
function doSearch(){
	var name = $("#searchBox").val();
	$("#list").html("");//清空面板
	if(name==""){
		doInit();
		return;
	}
	
	var url = contextPath+'/crmChancesController/selectChances.action';//商机列表
	
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{cusId:cusId,chanceName:name},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			var render = [];
			if(json.rtState){
				var prcs = json.rtData;
				for ( var i = 0; i < prcs.length; i++) {
					render.push("<li class=\"mui-table-view-cell mui-media\"   sid='"+prcs[i].sid+"' chanceName='"+prcs[i].chanceName+"' >");
					render.push("<div class=\"mui-media-body\">");
					render.push("<p style='font-size:12px'><span style='padding-left:10px;'>"+prcs[i].chanceName+"</span></p>");
					render.push("</div>");
					render.push("</li>");
				}
				$("#list").html(render.join(""));
				
				$(".mui-media").each(function(i,obj){
					obj.removeEventListener("tap",preSelect);
					obj.addEventListener("tap",preSelect);
				});
			}else{
				alert("查询失败！");
			}
			
		
		},
		
	});
}

/**
 * 商机选择回填
 */
function doSelect(){
	if(!sid || !chanceName){
		alert("您还没有选择！");
		return;
	}
	/* window.top.document.getElementById('iframe2').style.display='none';  */ 
	window.parent.selectChancesCallBackFunc(sid,chanceName);
	window.parent.operPage();
	/* window.parent.document.getElementById("shadow").style.display='none'; */
}

function preSelect(){
	 sid = this.getAttribute("sid");
	 chanceName = this.getAttribute("chanceName"); 
	// window.parent.document.getElementById("shadow").style.display='none';
}

//关闭iframe
function closeWin(){
/* 	document.getElementById("closeBtn").onclick=function(){
        window.top.document.getElementById("iframe2").style.display="none";
    }; */
   /*  window.top.document.getElementById('iframe3').style.display='none';   */
    window.parent.operPage();
	
}
 



</script>


</body>
</html>
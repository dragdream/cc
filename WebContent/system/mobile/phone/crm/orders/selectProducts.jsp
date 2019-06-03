<%@page import="com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>产品查询</title>
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
</style>
</head>
<body onload="doInit()">
<header class="mui-bar mui-bar-nav">
	<button class="mui-btn mui-btn-link mui-btn-nav mui-pull-left" onclick="doSelect()">
	    <span class="mui-icon mui-icon-checkmarkempty"></span>
	</button>
	<h1 class="mui-title" >选择产品</h1>
	<button class="mui-btn mui-btn-link mui-btn-nav mui-pull-right" onclick="closeWin()">
	    <span class="mui-icon mui-icon-closeempty"></span>
	</button>
</header>
<div class="mui-input-group" style="position:fixed;top:40px;left:0px;right:0px;height:40px;">
	<div class="mui-input-row">
		<input id="searchBox" type="text" placeholder="请输入产品名称搜索"  oninput="doSearch()" />
	</div>
</div>
<div class="mui-content" style="margin:0px;padding:0px;position:fixed;top:95px;left:0px;right:5px;bottom:0px;overflow:auto">
	<ul class="mui-table-view" id="list" style="margin:0px;padding:0px;">
			
	</ul>
</div>





<script>

//初始化数据
function doInit(){
	
	var url = contextPath+'/crmProductsController/getManageInfoList.action';//产品列表
	
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			var render = [];
			for(var i=0;i<json.rows.length;i++){
				var item = json.rows[i];
				render.push("<li class=\"mui-table-view-cell mui-media\"   sid='"+item.sid+"' >");
				render.push("<div class=\"mui-media-body\">");
				render.push("<p style='font-size:12px'><input type=\"checkbox\" name='selPro' id='selPro' value='"+item.sid+"'><span style='padding-left:20px;'>"+item.productsName+"</span></p>");
				render.push("</div>");
				render.push("</li>");
			}
			$("#list").html(render.join(""));
			$(".mui-media").each(function(i,obj){
				obj.removeEventListener("tap",preSelect);
				obj.addEventListener("tap",preSelect);
			});
		
		},
		
	});
	
}

function preSelect(){
	var chk = $(this).find("input[type='checkbox']");
	if(chk.is(":checked")){
		chk.attr("checked",false);
	}else{
		chk.attr("checked",true);
	}
}

//模糊查询
function doSearch(){
	var name = $("#searchBox").val();
	$("#list").html("");//清空面板
	if(name==""){
		doInit();
		return;
	}
	
	var url = contextPath+'/crmProductsController/getManageInfoList.action';//产品列表
	
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{productsName:name},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			var render = [];
			for(var i=0;i<json.rows.length;i++){
				var item = json.rows[i];
				render.push("<li class=\"mui-table-view-cell mui-media\"   sid='"+item.sid+"' >");
				render.push("<div class=\"mui-media-body\">");
				render.push("<p style='font-size:12px'><input type=\"checkbox\" name='selPro' id='selPro' value='"+item.sid+"'><span style='padding-left:20px;'>"+item.productsName+"</span></p>");
				render.push("</div>");
				render.push("</li>");
			}
			$("#list").html(render.join(""));
			
		
		},
		
	});
}

/**
 * 产品选择回填
 */
function doSelect(){
	var obj=document.getElementsByName('selPro');
	var s=''; 
	
	for(var i=0; i<obj.length; i++){ 
		if(obj[i].checked) s+=obj[i].value+','; //如果选中，将value添加到变量s中 
	} 
	
	if(s.length<=0){
		alert("您还没有选择！");
		return;
	}
	
	/* window.top.document.getElementById('iframe1').style.display='none';   */
	window.parent.selectProductCallBackFunc(s);
	window.parent.operPage();
	/* window.parent.document.getElementById("shadow").style.display='none'; */
	
}

//关闭iframe
function closeWin(){
    /* window.top.document.getElementById('iframe1').style.display='none';  
    window.parent.document.getElementById('shadow').style.display='none';  */ 
	 window.parent.operPage();
	
}
 



</script>


</body>
</html>
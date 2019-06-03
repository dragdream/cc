<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%
	String sid = request.getParameter("sid")==null?"0":request.getParameter("sid");
 %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/system/mobile/mui/header.jsp" %>
<script type="text/javascript" charset="UTF-8">
var sid="<%=sid%>";
function doInit(){
	findbianqian();
	 $("body").on("tap","#divColor div",function(){
		var divColor=$(this).css("background-color");
		$("body").css("background-color", divColor);
		$("textarea").css("background-color", divColor);
		$("#divColor").hide();
	 });
	 $("body").on("tap","#picColor",function(){
			if($("#divColor").is(":hidden")){
				$("#divColor").show();
			}else{
				$("#divColor").hide();
			}
	});
	 //保存addOrUpdate
	 $("body").on("tap","#addOrUpdate",function(){
			if(check()){
				var content=$("#content").val();
				var bodyColor=$("body").css("background-color");
				var url = contextPath+"/noteManage/addOrUpdate.action";
				mui.ajax(url,{
					type:"post",
					dataType:"html",
					data:{content:content,color:bodyColor,sid:sid},
					timeout:10000,
					success:function(json){
						//json = eval("("+json+")");
						window.location.href="bianqian.jsp";
					}
				});
			}else{
				alert("便签内容不能为空");
			}
	});
}
function check(){
	var content=$("#content").val();
	if(content==null || content==""){
		return false;
	}
	return true;
}
function findbianqian(){
	var url = contextPath+"/noteManage/getById.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{sid:sid},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			var data=json.rtData;
			$("body").css("background-color", data.color);
			$("textarea").css("background-color", data.color);
			$("textarea").val(data.content);
		}
	});
}

</script>

</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px;background-color: #feb9be;">
	<header class="mui-bar mui-bar-nav">
			<span class="mui-icon mui-icon-back" id="backBtn" onclick="window.location.href='bianqian.jsp'"></span>
			<h1 class="mui-title">编辑便签</h1>
			<div style="float:right;margin-top: 10px;" id="picColor">颜色</div>
	</header>
	<div style="margin-top:15%;">
	   <textarea id="content" style="border:0;overflow:hidden;overflow:auto;height: 300px;background-color: #feb9be;" ></textarea>
	</div>
	<div id="divColor" style="width: 337px;height: 69px;background-color: white;position: fixed;top: 50px;right:2px;display: none">
	   <div style="width:60px;height:60px;background-color: #feb9be;float:left;margin:5px 3px;"></div>
	   <div style="width:60px;height:60px;background-color: #d2f8bd;float:left;margin:5px 3px;"></div>
	   <div style="width:60px;height:60px;background-color: #fefacb;float:left;margin:5px 3px;"></div>
	   <div style="width:60px;height:60px;background-color: #bbe0fd;float:left;margin:5px 3px;"></div>
	   <div style="width:60px;height:60px;background-color: #ebe7e6;float:left;margin:5px 3px;"></div>
	</div>
	<div style="width: 95%;height: 55px;background-color: white;position: fixed; bottom: 1px;text-align: center;opacity: 0.5;">
	  <input id="addOrUpdate" type="button" style="width: 100%;height: 55px;" value="保存"/>
	</div>
</body>
</html>
		        
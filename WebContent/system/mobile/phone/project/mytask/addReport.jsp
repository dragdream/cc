<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
   //任务主键
   int taskId=TeeStringUtil.getInteger(request.getParameter("taskId"), 0);
%>
<!DOCTYPE HTML>
<html>
<head>
<title>我的任务</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<script type="text/javascript" src="<%=contextPath %>/common/js/address_cascade.js"></script>
<script src="/common/rangesliderjquery/js/ion.rangeSlider.js"></script> 
<link rel="stylesheet" href="/common/rangesliderjquery/css/normalize.min.css" />
<link rel="stylesheet" href="/common/rangesliderjquery/css/ion.rangeSlider.css" />
<link rel="stylesheet" href="/common/rangesliderjquery/css/ion.rangeSlider.skinNice.css" id="styleSrc"/>
</head>
<body onload="doInit()">
<header id="header" class="mui-bar mui-bar-nav">
	
	<span class="mui-icon mui-icon-back" id="backBtn"></span>
		<span class="mui-icon mui-icon-checkmarkempty mui-pull-right " onclick="save()"></span>
	<h1 class="mui-title">新增汇报</h1>
	
</header>

<div id="muiContent" class="mui-content">
<form id="form1" name="form1">
	
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>汇报内容</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		  <textarea rows="6" style="width: 550px" name="content" id="content" placeholder="进度详情描述" ></textarea>
		</div>
	</div>
	
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>完成百分比</label>
		</div>
		<div class="mui-input-row" style="height:inherit;padding:15px">
		   <input type="text" id="range" />
           
		</div>
	</div>
</form>	
</div>


<script>
var taskId=<%=taskId%>;
function doInit(){
	//获取任务
	var url=contextPath+"/taskController/getInfoBySid.action";
	//var json=tools.requestJsonRs(url,{sid:taskId});
	
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{sid:taskId},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				var data=json.rtData;
				$("#range").ionRangeSlider({
					min: 0,
					max: 100,
					from:data.progress,
					prettify:0,
					type: 'single',//设置类型
					step: 1,
					prefix: "",//设置数值前缀
					postfix: "%",//设置数值后缀
					prettify: true,
					hasGrid: true
				});
			}
		}
	});

}


mui.ready(function() {
	
	backBtn.addEventListener("tap",function(){
		history.go(-1);
	});//返回
	
	
});






//新增汇报
function save(){
		//获取任务汇报描述
		var content=$("#content").val();
		//获取任务进度
		var progress=$("#range").val();
		
		if(progress==100){
			if(window.confirm("您将要汇报的进度为100%，是否确认结束任务？")){
				var url=contextPath+"/taskReportController/addReport.action";
				mui.ajax(url,{
					type:"post",
					dataType:"html",
					data:{taskId:taskId,content:content,progress:progress},
					timeout:10000,
					success:function(json){
						json = eval("("+json+")");
						if(json.rtState){
							history.go(-1);
						}
					}
				});
			}
		}else{
			var url=contextPath+"/taskReportController/addReport.action";
			
			mui.ajax(url,{
				type:"post",
				dataType:"html",
				data:{taskId:taskId,content:content,progress:progress},
				timeout:10000,
				success:function(json){
					json = eval("("+json+")");
					if(json.rtState){
						history.go(-1);
					}	
				}
			});	
		}
}
</script>


</body>
</html>
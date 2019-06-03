<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
   //任务主键
   int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
%>

<!DOCTYPE HTML>
<html>
<head>
<title>任务办理</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<style>
#topPopover {
	position: fixed;
	top: 16px;
	right: 6px;
	width: 120px;
}
#topPopover .mui-popover-arrow {
	left: auto;
	right: 6px;
}

.app-row{
	margin-left:14px;
	padding-top:10px;
	padding-right:10px;
	padding-bottom:10px;
	border-bottom:1px solid #f0f0f0;
}
.app-row br{
	margin:0px;
}
.app-row-content{
	margin-left:14px;
	padding-top:10px;
	padding-right:10px;
	padding-bottom:10px;
	border-bottom:1px solid #f0f0f0;
	color:gray;
}

</style>
</head>

<script type="text/javascript">
//项目主键
var sid=<%=sid %>;
//初始化方法
function doInit(){
	getInfoBySid();
	
	//汇报
	a2.addEventListener("tap",function(){
		window.location = 'report.jsp?taskId='+sid;
	});
	
	//问题
	a3.addEventListener("tap",function(){
		window.location = 'question.jsp?taskId='+sid;
	});
	
	//返回
	backBtn.addEventListener("tap",function(){
		window.location = 'index.jsp';
	});
}


//获取详情
function getInfoBySid(){
	var url=contextPath+"/taskController/getInfoBySid.action";
	mui.ajax(url,{
		type:"POST",
		dataType:"JSON",
		data:{sid:sid},
		timeout:10000,
		async:false,
		success:function(text){
			var json = eval("("+text+")");
			if(json.rtState){	
				bindJsonObj2Cntrl(json.rtData);
				$("#progress").html(json.rtData.progress+"%");
			}else{
				alert("数据获取失败！");
			}
		}
	});

}




</script>
<body onload="doInit()">
	<header class="mui-bar mui-bar-nav">
		<span class="mui-icon mui-icon-back" id="backBtn" onclick=""></span>
		<h1 class="mui-title">任务详情</h1>
	</header>
	<div id="muiContent" class="mui-content">
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>任务序号</label>
		</div>
		<div class="app-row-content" id="taskNo" >
		</div>	
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>任务名称</label>
		</div>
		<div class="app-row-content" id="taskName">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>任务级别</label>
		</div>
		<div class="app-row-content" id="taskLevel">
			
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>负责人</label>
		</div>
		<div class="app-row-content" id="managerName" >
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>上级任务</label>
		</div>
		<div class="app-row-content" id="higherTaskName">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>前置任务</label>
		</div>
		<div class="app-row-content" id='preTaskName'>
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>计划开始时间</label>
		</div>
		<div class="app-row-content" id='beginTimeStr'>
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>计划结束时间</label>
		</div>
		<div class="app-row-content" id='endTimeStr'>
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>任务进度</label>
		</div>
		<div class="app-row-content" id='progress'>
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>任务描述</label>
		</div>
		<div class="app-row-content" id='description'>
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>备注</label>
		</div>
		<div class="app-row-content" id='remark'>
		
		</div>
	</div>
</div>

<nav class="mui-bar mui-bar-tab" id="footerDiv"  >
		<a id="a1" class="mui-tab-item mui-active" style="font-weight:bold">
			<!-- <span class="mui-icon mui-icon-compose"></span> -->
			<span class="mui-tab-label" >详情</span>
		</a>
		<a id="a2" class="mui-tab-item mui-active" onclick="">
			<!-- <span class="mui-icon mui-icon-search"></span> -->
			<span class="mui-tab-label" >汇报</span>
		</a>
		<a id="a3" class="mui-tab-item mui-active" onclick="">
			<!-- <span class="mui-icon mui-icon-search"></span> -->
			<span class="mui-tab-label" >问题</span>
		</a>
</nav>
<br/><br/><br/>	
</body>
</html>
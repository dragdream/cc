<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% 
	String taskId = request.getParameter("taskId");
	String fromTask = request.getParameter("fromTask");
	TeePerson loginPerson = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
%>
<!DOCTYPE HTML>
<html>
<head>
<title>任务管理</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<script type="text/javascript" src="script.js"></script>
</head>
<body>
<header id="header" class="mui-bar mui-bar-nav">
	<button class="mui-btn mui-btn-link mui-btn-nav mui-pull-left" onclick="history.go(-1)">
	    <span class="mui-icon mui-icon-left-nav"></span>返回
	</button>
	<h1 class="mui-title">任务督办</h1>
</header>

<style>
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
<div id="muiContent" class="mui-content">
	<div class="mui-input-group">
			<div class="mui-table-view-cell mui-media">
				<div class="mui-media-object mui-pull-right" >
				<span id='status'></span>
				</div>
				<div class="mui-media-body">
					<span id="taskTitle"></span>
					<p class='mui-ellipsis'><span id='chargerName'></span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="read"></span></p>
				</div>
			</div>
	</div>
	<div class="mui-input-group" id="redoDiv">
		<div class="mui-input-row">
			<label>延期日期：</label>
		</div>
		<div class="app-row-content">
			<input type="text" id="delayTime" name="delayTime" placeholder="选择时间" />
		</div>
	</div>
	<div class="mui-input-group" id="redoDiv">
		<div class="mui-input-row">
			<label>延期原因：</label>
		</div>
		<div class="app-row-content">
			<textarea id="delayTextarea" name='delayTextarea' style='border:1px solid #fff000;' placeholder="请填写延期原因"></textarea>
			<br/><br/>
			<button class="mui-btn mui-btn-primary" onclick="delaySubmit(<%=taskId%>)">确定延期</button>
		</div>
	</div>
</div>


<script>
	var taskId = <%=taskId%>;
	(function($) {
		var date = new Date();
		document.getElementById("delayTime").value=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
		delayTime.addEventListener('tap', function() {
			/*
			 * 首次显示时实例化组件
			 * 示例为了简洁，将 options 放在了按钮的 dom 上
			 * 也可以直接通过代码声明 optinos 用于实例化 DtPicker
			 */
			var picker = new $.DtPicker({type:"date"});
			picker.show(function(rs) {
				/*
				 * rs.value 拼合后的 value
				 * rs.text 拼合后的 text
				 * rs.y 年，可以通过 rs.y.vaue 和 rs.y.text 获取值和文本
				 * rs.m 月，用法同年
				 * rs.d 日，用法同年
				 * rs.h 时，用法同年
				 * rs.i 分（minutes 的第二个字母），用法同年
				 */
				//result.innerText = '选择结果: ' + rs.text;
				delayTime.value = rs.text;
				/* 
				 * 返回 false 可以阻止选择框的关闭
				 * return false;
				 */
				
				/*
				 * 释放组件资源，释放后将将不能再操作组件
				 * 通常情况下，不需要示放组件，new DtPicker(options) 后，可以一直使用。
				 * 当前示例，因为内容较多，如不进行资原释放，在某些设备上会较慢。
				 * 所以每次用完便立即调用 dispose 进行释放，下次用时再创建新实实。
				 */
				picker.dispose(); 
			});
		}, false);
		getTaskInfo();
	})(mui);
	
	function getTaskInfo(){
		var url = contextPath+"/coWork/getTaskInfo.action";
		mui.ajax(url,{
			type:"POST",
			dataType:"JSON",
			data:{taskId:taskId},
			timeout:10000,
			success:function(text){
				var json = eval("("+text+")");
				if(json.rtState){
					document.getElementById("taskTitle").innerHTML = json.rtData.taskTitle;
					document.getElementById("chargerName").innerHTML = "负责人："+json.rtData.chargerName;
					//渲染当前状态
					if(json.rtData.status==0){
						status="<span class=\"mui-badge mui-badge-primary\">等待接收</span>";
					}else if(json.rtData.status==1){
						status="<span class=\"mui-badge mui-badge-primary\">等待审批</span>";
					}else if(json.rtData.status==2){
						status="<span class=\"mui-badge mui-badge-danger\">审批不通过</span>";
					}else if(json.rtData.status==3){
						status="<span class=\"mui-badge mui-badge-danger\">拒绝接收</span>";
					}else if(json.rtData.status==4){
						status="<span class=\"mui-badge mui-badge-success\">进行中</span>";
					}else if(json.rtData.status==5){
						status="<span class=\"mui-badge mui-badge-purple\">提交审核</span>";
					}else if(json.rtData.status==6){
						status="<span class=\"mui-badge mui-badge-danger\">审核不通过</span>";
					}else if(json.rtData.status==7){
						status="<span class=\"mui-badge  mui-badge-danger\">任务撤销</span>";
					}else if(json.rtData.status==8){
						status="<span class=\"mui-badge mui-badge-warning\">已完成</span>";
					}else if(json.rtData.status==9){
						status="<span class=\"mui-badge mui-badge-danger\">任务失败</span>";
					}
					document.getElementById("status").innerHTML = status;
				}
			},
			error:function(){
				
			}
		});
	}
	
</script>
</body>
</html>
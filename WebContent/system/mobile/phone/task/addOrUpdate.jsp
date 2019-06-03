<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String taskId = request.getParameter("taskId");
	String parentTaskId = request.getParameter("parentTaskId");
%>
<!DOCTYPE HTML>
<html>
<head>
<title>任务管理</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
</head>
<body>
<header id="header" class="mui-bar mui-bar-nav">
	<button class="mui-btn mui-btn-link mui-btn-nav mui-pull-left" onclick="window.location = 'index.jsp'">
	    <span class="mui-icon mui-icon-left-nav"></span>返回
	</button>
	<button class="mui-btn mui-btn-link mui-btn-nav mui-pull-right" onclick='save()'>
	    保存
	</button>
	<%
		if(null==taskId || taskId.equals("0") || taskId.equals("")){
	%>
	<h1 class="mui-title">新建任务</h1>
	<%
		}else{
			
	%>
	<h1 class="mui-title">编辑任务</h1>
	<%
		}
	%>
</header>
<div id="muiContent" class="mui-content">
	<div class="mui-input-group">
		<div class="mui-input-row">
			<input type="text" placeholder="任务标题" name="taskTitle" id="taskTitle">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>添加时间</label>
		</div>
		<div class="mui-input-row">
			<input type="text" id="startTimeDesc" name="startTimeDesc" placeholder="选择添加时间" />
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>完成时间</label>
		</div>
		<div class="mui-input-row">
			<input type="text" id="endTimeDesc" name="endTimeDesc" placeholder="选择完成时间" />
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>负责人</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
			<input type="text" id="chargerName" readonly placeholder="选择负责人" />
			<input type="hidden" id=chargerId />
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>参与人</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
			<textarea name="joinerNames" id='joinerNames' rows="5" placeholder="请选择参与人"></textarea>
			<input type="hidden" id=joinerIds />
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>任务内容</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
			<textarea name="content" id='content' rows="5" placeholder="请填写任务内容"></textarea>
		</div>
	</div>
	<input type="hidden" id="parentTaskId" />
</div>


<script>
	var taskId = "<%=taskId%>";
	var parentTaskId = "<%=parentTaskId%>";
	(function($) {
		var date = new Date();
		document.getElementById("startTimeDesc").value=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+" "+date.getHours()+":"+date.getMinutes();
		startTimeDesc.addEventListener('tap', function() {
			var picker = new $.DtPicker({type:"datetime"});
			picker.show(function(rs) {
				startTimeDesc.value = rs.text;
				picker.dispose(); 
			});
		}, false);
		endTimeDesc.addEventListener('tap', function() {
			var picker = new $.DtPicker({type:"datetime"});
			picker.show(function(rs) {
				endTimeDesc.value = rs.text;
				picker.dispose(); 
			});
		}, false);
		
		chargerName.addEventListener('tap', function() {
			selectSingleUser("chargerId","chargerName");
		}, false);
		joinerNames.addEventListener('tap', function() {
			selectUser("joinerIds","joinerNames");
		}, false);
		
		if(taskId!="" && taskId!="null"){
			 getTaskInfo();
		}
		if(parentTaskId!="" && parentTaskId!="null"){
			document.getElementById("parentTaskId").value = parentTaskId;
		}
		
	})(mui);
	
	
	function save(){
		var taskTitle = document.getElementById("taskTitle").value;
		var startTimeDesc = document.getElementById("startTimeDesc").value;
		var endTimeDesc = document.getElementById("endTimeDesc").value;
		var chargerName = document.getElementById("chargerName").value;
		var chargerId = document.getElementById("chargerId").value;
		var joinerIds = document.getElementById("joinerIds").value;
		var joinerNames = document.getElementById("joinerNames").value;
		var content = document.getElementById("content").value;
		var parentTaskId = document.getElementById("parentTaskId").value;
		if(chargerId==""){
			Alert("负责人不能为空！");
			$("#chargerName").focus();
			return;
		}
		var url = contextPath+"/coWork/addTask.action";
		if(taskId!="" && taskId!="null"){
			 url = contextPath+"/coWork/editTask.action?sid="+taskId;
		}
		mui.ajax(url,{
			type:"POST",
			dataType:"JSON",
			data:{taskTitle:taskTitle,startTimeDesc:startTimeDesc,endTimeDesc:endTimeDesc,chargerId:chargerId,
				chargerName:chargerName,joinerIds:joinerIds,joinerNames:joinerNames,content:content,parentTaskId:parentTaskId},
			timeout:10000,
			success:function(data){
				Alert("保存成功！");
				window.location = 'index.jsp';
			},
			error:function(){
				
			}
		});
	}
	
	
	
	
	function getTaskInfo(){
		var url = contextPath+"/coWork/getTaskInfo.action";
		mui.ajax(url,{
			type:"GET",
			dataType:"JSON",
			data:{taskId:taskId},
			timeout:10000,
			success:function(text){
				var json = eval("("+text+")");
				if(json.rtState){
					document.getElementById("taskTitle").value = json.rtData.taskTitle;
					document.getElementById("startTimeDesc").value = json.rtData.startTimeDesc;
					document.getElementById("endTimeDesc").value = json.rtData.endTimeDesc;
					document.getElementById("chargerName").value = json.rtData.chargerName;
					document.getElementById("chargerId").value = json.rtData.chargerId;
					document.getElementById("joinerIds").value = json.rtData.joinerIds;
					document.getElementById("joinerNames").value = json.rtData.joinerNames;
					document.getElementById("content").value = json.rtData.content;
					document.getElementById("parentTaskId").value = json.rtData.parentTaskId;
				}
			},
			error:function(){
				
			}
		});
	}
</script>
</body>
</html>
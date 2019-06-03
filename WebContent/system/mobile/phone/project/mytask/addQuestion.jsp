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
</head>
<body onload="doInit()">
<header id="header" class="mui-bar mui-bar-nav">
	
	<span class="mui-icon mui-icon-back" id="backBtn"></span>
		<span class="mui-icon mui-icon-checkmarkempty mui-pull-right " onclick="save()"></span>
	<h1 class="mui-title">新增问题</h1>
	
</header>

<div id="muiContent" class="mui-content">
<form id="form1" name="form1">
	
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>问题名称</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		  <input type="text" name="questionName" id="questionName"  placeholder="问题名称"  />
		</div>
	</div>
	
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>优先级</label>
		</div>
		<div class="mui-input-row" style="height:inherit;">
		  <select id="questionLevel" name="questionLevel">
                  <option value="低">低</option>
                  <option value="普通">普通</option>
                  <option value="高">高</option>
                  <option value="非常高">非常高</option>
          </select>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>负责人</label>
		</div>
		<div class="mui-input-row" style="height:inherit;">
		   <input name="operatorId" id="operatorId" type="hidden"/>
		   <input class="BigInput readonly" type="text" id="operatorName" name="operatorName" placeholder="负责人"   readonly/>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>问题描述</label>
		</div>
		<div class="mui-input-row" style="height:inherit;">
		   <textarea rows="6" cols="60" id="questionDesc" name="questionDesc" placeholder="问题描述" ></textarea>
		</div>
	</div>
</form>	
</div>


<script>
var taskId=<%=taskId%>;
//初始化
function doInit(){
	//选择项目负责人
	operatorName.addEventListener('tap', function() {
		selectSingleUser("operatorId","operatorName");
	}, false);
}
//验证
function check(){
	var questionName=$("#questionName").val();
	var operatorName=$("#operatorName").val();
	var questionDesc=$("#questionDesc").val();
	if(questionName==""||questionName==null){
		alert("请填写问题名称！");
		return false;
	}
	if(questionDesc==""||questionDesc==null){
		alert("请填写问题描述！");
		return false;
	}
	if(operatorName==""||operatorName==null){
		alert("请选择负责人！");
		return false;
	}
	return true;
}

//保存
function save(){
	if(check()){
		var url=contextPath+"/projectQuestionController/addQuestion.action";
		var param=formToJson("#form1");
		param["taskId"]=taskId;
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:param,
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
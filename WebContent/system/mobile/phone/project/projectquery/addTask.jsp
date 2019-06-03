<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
   String projectId=TeeStringUtil.getString(request.getParameter("projectId"),"");//项目主键
   int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);//任务主键
%>
<!DOCTYPE HTML>
<html>
<head>
<title>创建任务</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
</head>
<body onload="doInit()">
<header id="header" class="mui-bar mui-bar-nav">
	
	<span class="mui-icon mui-icon-back" id="backBtn" onclick="history.go(-1)"></span>
	<span class="mui-icon mui-icon-checkmarkempty mui-pull-right " onclick="commit()"></span>
	
	<h1 class="mui-title">创建任务</h1>
	
</header>

<div id="muiContent" class="mui-content">
    <input type="hidden" name="sid" id="sid" value="<%=sid %>" />
    <input type="hidden" name="projectId" id="projectId" value="<%=projectId %>"/>
	<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>任务序号</label>
		</div>
		<div class="mui-input-row">
			<input type="text" placeholder="任务序号" name="taskNo" id="taskNo"/>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>任务名称</label>
		</div>
		<div class="mui-input-row">
			<input type="text" placeholder="任务名称" name="taskName" id="taskName">
		</div>
	</div>
	<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>任务级别</label>
		</div>
		<div class="mui-input-row">
			<select id="taskLevel" name="taskLevel">
               <option value="次要">次要</option>
               <option value="一般">一般</option>
               <option value="重要">重要</option>
               <option value="非常重要">非常重要</option>
            </select>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>负责人</label>
		</div>
		<div class="mui-input-row">
			<select id="managerId" name="managerId">
               <option value="0">请选择负责人</option>
            </select>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>上级任务</label>
		</div>
		<div class="mui-input-row">
			<select id="higherTaskId" name="higherTaskId">
               <option value="0">请选择上级任务</option>
            </select>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>前置任务</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
			<select id="preTaskId" name="preTaskId">
               <option value="0">请选择前置任务</option>
            </select>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>计划开始时间</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		   <input type="text" id='beginTimeStr' name='beginTimeStr' placeholder="计划开始时间"  />
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>计划结束时间</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		   <input type="text" id='endTimeStr' name='endTimeStr'  placeholder="计划结束时间" />
		   <input type="hidden" id='days' name='days'  class="BigInput" style="width: 100px" />
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>任务描述</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
			<textarea rows="6" style="width: 550px" name="description" id="description" placeholder="任务描述"></textarea>
		</div>
	</div>
	
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>备注</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		  <textarea rows="6" style="width: 550px" name="remark" id="remark" placeholder="备注" ></textarea>
		</div>
	</div>
</div>


<script>
//项目主键
var projectId="<%=projectId%>"; 
//当前任务 主键  当新增任务的时候  为0
var sid=<%=sid%>;
function doInit(){
	initManager(projectId);
	initOtherTasks();

	//开始时间
	beginTimeStr.addEventListener('tap', function() {
		var picker = new mui.DtPicker({type:"date"});
		picker.show(function(rs) {
		beginTimeStr.value = rs.text;	
		picker.dispose(); 
		});
	}, false);

	
	endTimeStr.addEventListener('tap', function() {
		var picker = new mui.DtPicker({type:"date"});
		picker.show(function(rs) {
		endTimeStr.value = rs.text;	
		picker.dispose(); 
		});
	}, false);
	
	
}


//初始化任务负责人
function initManager(projectId) {
	var url = contextPath + "/projectController/getManagerOrMember.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{uuid : projectId},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if (json.rtState) {
				var data = json.rtData;
				for (var i = 0; i < data.length; i++) {
					$("#managerId").append(
							"<option value="+data[i].uuid+">" + data[i].userName
									+ "</option>");
				}
			} else {
				alert("数据获取失败！");
			}
		}
	});
}

//初始化  上级任务  前置任务的下拉列表
function initOtherTasks() {
	var url = contextPath+ "/taskController/getOtherTasksByProjectId.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{projectId : projectId,sid : sid},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if (json.rtState) {
				data = json.rtData;
				for (var i = 0; i < data.length; i++) {
					$("#higherTaskId").append(
					"<option value="+data[i].sid+">" + data[i].taskName
					+ "</option>");
					$("#preTaskId").append(
					"<option value="+data[i].sid+">" + data[i].taskName
					+ "</option>");
				}
			}
		}
	});
}

//保存
function commit() {
	//先给天数赋值
	getDays();
	if (check()) {
		var url = contextPath + "/taskController/addOrUpdate.action";
		var param = formToJson("#muiContent");
		param["flowTypeIds"]="";
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:param,
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if (json.rtState) {
					alert("任务创建成功！");
					window.location.href=contextPath+"/system/mobile/phone/project/projectquery/projectDetail.jsp?uuid="+projectId;
				}
			}
		});

	}
}

//获取天数
function getDays() {
	var startTime = document.getElementById("beginTimeStr").value;
	var endTime = document.getElementById("endTimeStr").value;
	if (startTime != null && endTime != null) {
		var days = DateDiff(startTime, endTime);
		$("#days").val(days + 1);
	}
}

function DateDiff(sDate1, sDate2) {
	var iDays;
	sDate1 = sDate1.replace(/-/g, "/");
	var aDate = new Date(sDate1);
	sDate2 = sDate2.replace(/-/g, "/");
	var oDate = new Date(sDate2);
	iDays = parseInt(Math.abs(oDate - aDate) / 1000 / 60 / 60 / 24); //把相差的毫秒數轉抽象為天數
	return iDays;
}

//验证
function check() {
	var taskNo = $("#taskNo").val();
    var taskName = $("#taskName").val();
	var managerId = $("#managerId").val();
	var beginTimeStr = $("#beginTimeStr").val();
	var endTimeStr = $("#endTimeStr").val();
	if (taskNo == null || taskNo == "") {
		alert("请填写任务序号！");
		return false;
	}
	if (taskName == null || taskName == "") {
		alert("请填写任务名称！");
		return false;
	}
	if (managerId == null || managerId == "" || managerId == 0) {
		alert("请选择任务负责人！");
		return false;
	}
	if (beginTimeStr == null || beginTimeStr == "") {
		alert("请选择开始时间！");
		return false;
	}
	if (endTimeStr == null || endTimeStr == "") {
		alert("请选择结束时间！");
		return false;
	}
	
	//判断开始时间不能小于结束时间

	var date1 = new Date(beginTimeStr.replace("//-/g", "//"));
	var date2 = new Date(endTimeStr.replace("//-/g", "//"));
	if(date1>date2){
	    alert("开始时间不能大于结束时间！");
	    return false;
	}
	
	return true;
}
</script>


</body>
</html>
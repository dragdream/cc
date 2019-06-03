
function edit(taskId){
	window.location = "addOrUpdate.jsp?taskId="+taskId;
}

function del(taskId){
	//获取任务事件数据
	var url = contextPath+"/coWork/deleteTask.action";
	mui.ajax(url,{
		type:"POST",
		dataType:"JSON",
		data:{taskId:taskId},
		timeout:10000,
		success:function(text){
			var json = eval("("+text+")");
			if(json.rtState){
				Alert("删除成功！");
				window.location  = "index.jsp";
			}
		},
		error:function(){
			Alert("异常错误！");
		}
	});
}

function redo(taskId){
	window.location = "redo.jsp?taskId="+taskId;
}

function redoSubmit(taskId){
	if($("#redoTextarea").val()==""){
		Alert("撤销原因不能为空！");
		$("#redoTextarea").focus();
		return;
	}
	var url = contextPath+"/coWork/redo.action";
	mui.ajax(url,{
		type:"POST",
		dataType:"JSON",
		data:{taskId:taskId,remark:$("#redoTextarea").val()},
		timeout:10000,
		success:function(text){
			var json = eval("("+text+")");
			if(json.rtState){
				Alert("撤销成功！");
				window.location="detail.jsp?taskId="+taskId;
			}
		},
		error:function(){
			Alert("异常错误！");
		}
	});
}


function urge(taskId){
	window.location = "urge.jsp?taskId="+taskId;
}


function urgeSubmit(taskId){
	if($("#urgeTextarea").val()==""){
		Alert("督办内容不能为空！");
		$("#urgeTextarea").focus();
		return;
	}
	var url = contextPath+"/coWork/urge.action";
	mui.ajax(url,{
		type:"POST",
		dataType:"JSON",
		data:{taskId:taskId,remark:$("#urgeTextarea").val()},
		timeout:10000,
		success:function(text){
			var json = eval("("+text+")");
			if(json.rtState){
				Alert("督办成功！");
				window.location="detail.jsp?taskId="+taskId;
			}
		},
		error:function(){
			Alert("异常错误！");
		}
	});
}
function failed(taskId){
	window.location = "failed.jsp?taskId="+taskId;
}


function failedSubmit(taskId){
	if($("#failedTextarea").val()==""){
		Alert("失败原因不能为空！");
		$("#failedTextarea").focus();
		return;
	}
	var url = contextPath+"/coWork/failed.action";
	mui.ajax(url,{
		type:"POST",
		dataType:"JSON",
		data:{taskId:taskId,remark:$("#failedTextarea").val()},
		timeout:10000,
		success:function(text){
			var json = eval("("+text+")");
			if(json.rtState){
				Alert("任务已失败！");
				window.location="detail.jsp?taskId="+taskId;
			}
		},
		error:function(){
			Alert("异常错误！");
		}
	});
}

function receive(taskId){
	var url = contextPath+"/coWork/receive.action";
	mui.ajax(url,{
		type:"POST",
		dataType:"JSON",
		data:{taskId:taskId},
		timeout:10000,
		success:function(text){
			var json = eval("("+text+")");
			if(json.rtState){
				Alert("任务已接收！");
				window.location.reload();
			}
		},
		error:function(){
			Alert("异常错误！");
		}
	});
}

function noReceive(taskId){
	var url = contextPath+"/coWork/noReceive.action";
	mui.ajax(url,{
		type:"POST",
		dataType:"JSON",
		data:{taskId:taskId},
		timeout:10000,
		success:function(text){
			var json = eval("("+text+")");
			if(json.rtState){
				Alert("任务已拒收！");
				window.location.reload();
			}
		},
		error:function(){
			Alert("异常错误！");
		}
	});
}

function pass(taskId){
	var url = contextPath+"/coWork/pass.action";
	mui.ajax(url,{
		type:"POST",
		dataType:"JSON",
		data:{taskId:taskId},
		timeout:10000,
		success:function(text){
			var json = eval("("+text+")");
			if(json.rtState){
				Alert("任务已通过！");
				window.location.reload();
			}
		},
		error:function(){
			Alert("异常错误！");
		}
	});
}

function noPass(taskId){
	var url = contextPath+"/coWork/noPass.action";
	mui.ajax(url,{
		type:"POST",
		dataType:"JSON",
		data:{taskId:taskId},
		timeout:10000,
		success:function(text){
			var json = eval("("+text+")");
			if(json.rtState){
				Alert("任务不通过！");
				window.location.reload();
			}
		},
		error:function(){
			Alert("异常错误！");
		}
	});
}

function pass1(taskId){
	var url = contextPath+"/coWork/pass1.action";
	mui.ajax(url,{
		type:"POST",
		dataType:"JSON",
		data:{taskId:taskId},
		timeout:10000,
		success:function(text){
			var json = eval("("+text+")");
			if(json.rtState){
				Alert("任务审核已通过！");
				window.location.reload();
			}
		},
		error:function(){
			Alert("异常错误！");
		}
	});
}

function noPass1(taskId){
	var url = contextPath+"/coWork/noPass1.action";
	mui.ajax(url,{
		type:"POST",
		dataType:"JSON",
		data:{taskId:taskId},
		timeout:10000,
		success:function(text){
			var json = eval("("+text+")");
			if(json.rtState){
				Alert("任务审核不通过！");
				window.location.reload();
			}
		},
		error:function(){
			Alert("异常错误！");
		}
	});
}



function report(taskId){
	window.location = "report.jsp?taskId="+taskId;
}


function reportSubmit(taskId){
	var progress = $("#progress").val();
	var val = -1;
	try{
		val = parseInt(progress);
	}catch(e){}
	
	if(val==-1){
		Alert("请填写进度百分比,0-100之间");
		$("#progress").focus();
		return ;
	}else if(isNaN(val)){
		Alert("请填写进度百分比,0-100之间");
		$("#progress").focus();
		return ;
	}else if(val<0 || val>100){
		Alert("请填写进度百分比,0-100之间");
		$("#progress").focus();
		return ;
	}
	
	if($("#reportTextarea").val()==""){
		Alert("督办内容不能为空！");
		$("#reportTextarea").focus();
		return;
	}
	var url = contextPath+"/coWork/report.action";
	mui.ajax(url,{
		type:"POST",
		dataType:"JSON",
		data:{taskId:taskId,remark:$("#reportTextarea").val(),progress:$("#progress").val()},
		timeout:10000,
		success:function(text){
			var json = eval("("+text+")");
			if(json.rtState){
				Alert("进度汇报成功！");
				window.location="detail.jsp?taskId="+taskId;
			}
		},
		error:function(){
			Alert("异常错误！");
		}
	});
}

function finish(taskId){
	window.location = "finish.jsp?taskId="+taskId;
}


function finishSubmit(taskId){
	var score = $("#score").val();
	var val = -1;
	try{
		val = parseInt(score);
	}catch(e){}
	
	if(val==-1){
		Alert("请填写任务评分,0-100之间");
		$("#score").focus();
		return ;
	}else if(isNaN(val)){
		Alert("请填写任务评分,0-100之间");
		$("#score").focus();
		return ;
	}else if(val<0 || val>100){
		Alert("请填写任务评分,0-100之间");
		$("#score").focus();
		return ;
	}
	if($("#finishTextarea").val()==""){
		Alert("任务不能为空！");
		$("#finishTextarea").focus();
		return;
	}
	var url = contextPath+"/coWork/finish.action";
	mui.ajax(url,{
		type:"POST",
		dataType:"JSON",
		data:{taskId:taskId,remark:$("#finishTextarea").val(),score:val,relTimes:$("#relTimes").val()},
		timeout:10000,
		success:function(text){
			var json = eval("("+text+")");
			if(json.rtState){
				Alert("任务已完成！");
				window.location="detail.jsp?taskId="+taskId;
			}
		},
		error:function(){
			Alert("异常错误！");
		}
	});
}

function delay(taskId){
	window.location = "delay.jsp?taskId="+taskId;
}


function delaySubmit(taskId){
	if($("#delayTextarea").val()==""){
		Alert("督办内容不能为空！");
		$("#delayTextarea").focus();
		return;
	}
	var url = contextPath+"/coWork/delay.action";
	mui.ajax(url,{
		type:"POST",
		dataType:"JSON",
		data:{taskId:taskId,remark:$("#delayTextarea").val(),delayTime:$("#delayTime").val()},
		timeout:10000,
		success:function(text){
			var json = eval("("+text+")");
			if(json.rtState){
				Alert("延期成功！");
				window.location="detail.jsp?taskId="+taskId;
			}
		},
		error:function(){
			Alert("异常错误！");
		}
	});
}
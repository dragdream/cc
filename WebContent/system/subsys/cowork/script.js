
function edit(taskId){
	window.location = contextPath+"/system/subsys/cowork/editTask.jsp?taskId="+taskId;
}

function del(taskId){
	$.MsgBox.Confirm("提示","确认要删除该任务吗？",function(){
//		if(v=="ok"){
			var json = tools.requestJsonRs(contextPath+"/coWork/deleteTask.action?taskId="+taskId);
			if(json.rtState){
				if(datagrid){
					datagrid.datagrid("reload");
					$.MsgBox.Alert_auto("删除成功");
				}else{
					window.location = contextPath+"/system/subsys/cowork/list.jsp";
				}
			}
//		}
	});
}

function redo(taskId){
	$.MsgBox.Confirm("提示","确认要撤销该任务吗？",function(){
//		if(v=="ok"){
		bsWindow($("#redoDiv").html(),"撤销任务",{width:"410",height:"200",buttons:
			 [{name:"确定",classStyle:"btn-alert-blue"},{name:"关闭",classStyle:"btn-alert-gray"}]
		,submit:function(v,h,f,d){
				if(v=="确定"){
					var param = {taskId:taskId,remark:f.find("[fid=redoTextarea]:first").val()};
					var json = tools.requestJsonRs(contextPath+"/coWork/redo.action",param);
					if(json.rtState){
						$.MsgBox.Alert_auto("撤销成功");
						if(datagrid){
							d.remove();
							datagrid.datagrid("reload");
						}else{
							window.location.reload();
						}
					}
				}else if(v=="关闭"){
					return true;
				}
			}},"html");
//		}
	});
}

function detail(taskId){
	window.location = contextPath+"/system/subsys/cowork/detail.jsp?taskId="+taskId;
}

function urge(taskId){
	$.MsgBox.Confirm("提示","确认要督办该任务吗？",function(){
//		if(v=="ok"){
		bsWindow($("#urgeDiv").html(),"督办任务",{width:"410",height:"200",buttons:
			 [{name:"确定",classStyle:"btn-alert-blue"},{name:"关闭",classStyle:"btn-alert-gray"}]
		,submit:function(v,h,f,d){
				if(v=="确定"){
					var param = {taskId:taskId,remark:f.find("[fid=urgeTextarea]:first").val()};
					var json = tools.requestJsonRs(contextPath+"/coWork/urge.action",param);
					if(json.rtState){
						$.MsgBox.Alert_auto("督办成功");
						if(datagrid){
							d.remove();
							datagrid.datagrid("reload");
						}else{
							window.location.reload();
						}
					}
				}else if(v=="关闭"){
					return true;
				}
			}},"html");
//		}
	});
}

function failed(taskId){
	$.MsgBox.Confirm("提示","确认要作废该任务吗？",function(){
//		if(v=="ok"){
		bsWindow($("#failedDiv").html(),"任务失败",{width:"410",height:"200",buttons:
			 [{name:"确定",classStyle:"btn-alert-blue"},{name:"关闭",classStyle:"btn-alert-gray"}]
		,submit:function(v,h,f,d){
				if(v=="确定"){
					var param = {taskId:taskId,remark:f.find("[fid=failedTextarea]:first").val()};
					var json = tools.requestJsonRs(contextPath+"/coWork/failed.action",param);
					if(json.rtState){
						$.MsgBox.Alert_auto("操作成功");
						if(datagrid){
							d.remove();
							datagrid.datagrid("reload");
						}else{
							window.location.reload();
						}
					}
				}else if(v=="关闭"){
					return true;
				}
			}},"html");
//		}
	});
}


function receive(taskId){
	$.MsgBox.Confirm("提示","确认接收该任务吗？",function(){
//		if(v=="ok"){
			var param = {taskId:taskId};
			var json = tools.requestJsonRs(contextPath+"/coWork/receive.action",param);
			if(json.rtState){
				if(datagrid){
					datagrid.datagrid("reload");
					$.MsgBox.Alert_auto("接收成功");
				}else{
					window.location.reload();
				}
			}
//		}
	});
}

function noReceive(taskId){
	$.MsgBox.Confirm("提示","确认不接收该任务吗？",function(){
//		if(v=="ok"){
			var param = {taskId:taskId};
			var json = tools.requestJsonRs(contextPath+"/coWork/noReceive.action",param);
			if(json.rtState){
				if(datagrid){
					datagrid.datagrid("reload");
					$.MsgBox.Alert_auto("操作成功");
				}else{
					window.location.reload();
				}
			}
//		}
	});
}

function pass(taskId){
	$.MsgBox.Confirm("提示","确认通过该任务的审批吗？",function(){
//		if(v=="ok"){
			var param = {taskId:taskId};
			var json = tools.requestJsonRs(contextPath+"/coWork/pass.action",param);
			if(json.rtState){
				if(datagrid){
					datagrid.datagrid("reload");
					$.MsgBox.Alert_auto("操作成功");
				}else{
					window.location.reload();
				}
			}
//		}
	});
}

function noPass(taskId){
	$.MsgBox.Confirm("提示","确认不通过该任务的审批吗？",function(){
//		if(v=="ok"){
			var param = {taskId:taskId};
			var json = tools.requestJsonRs(contextPath+"/coWork/noPass.action",param);
			if(json.rtState){
				if(datagrid){
					datagrid.datagrid("reload");
					$.MsgBox.Alert_auto("操作成功");
				}else{
					window.location.reload();
				}
			}
//		}
	});
}

function pass1(taskId){
	$.MsgBox.Confirm("提示","确认通过该任务的审核吗？",function(){
//		if(v=="ok"){
			var param = {taskId:taskId};
			var json = tools.requestJsonRs(contextPath+"/coWork/pass1.action",param);
			if(json.rtState){
				if(datagrid){
					datagrid.datagrid("reload");
					$.MsgBox.Alert_auto("操作成功");
				}else{
					window.location.reload();
				}
			}
//		}
	});
}

function noPass1(taskId){
	$.MsgBox.Confirm("提示","确认不通过该任务的审核吗？",function(){
//		if(v=="ok"){
			var param = {taskId:taskId};
			var json = tools.requestJsonRs(contextPath+"/coWork/noPass1.action",param);
			if(json.rtState){
				if(datagrid){
					datagrid.datagrid("reload");
					$.MsgBox.Alert_auto("操作成功");
				}else{
					window.location.reload();
				}
			}
//		}
	});
}

function report(taskId){
	bsWindow($("#reportDiv").html(),"汇报进度",{width:"410",height:"300",buttons:
		 [{name:"确定",classStyle:"btn-alert-blue"},{name:"关闭",classStyle:"btn-alert-gray"}]
	,submit:function(v,h,f,d){
		if(v=="确定"){
			var progress = f.find("[fid=progress]:first").val();
			var val = -1;
			try{
				val = parseInt(progress);
			}catch(e){}
			
			if(val==-1){
				$.MsgBox.Alert_auto("请填写进度百分比,0-100之间");
				return false;
			}else if(isNaN(val)){
				$.MsgBox.Alert_auto("请填写进度百分比,0-100之间");
				return false;
			}else if(val<0 || val>100){
				$.MsgBox.Alert_auto("请填写进度百分比,0-100之间");
				return false;
			}
			var param = {taskId:taskId,remark:f.find("[fid=reportTextarea]:first").val(),progress:f.find("[fid=progress]:first").val()};
			var json = tools.requestJsonRs(contextPath+"/coWork/report.action",param);
			if(json.rtState){
				$.MsgBox.Alert_auto("操作成功");
				if(datagrid){
					d.remove();
					datagrid.datagrid("reload");
				}else{
					window.location.reload();
				}
			}
		}else if(v=="关闭"){
			return true;
		}
	}},"html");
}

function finish(taskId){
	var json = tools.requestJsonRs(contextPath+"/coWork/hasExistsUnfinishedChildTask.action",{taskId:taskId});
	if(json.rtData==true){
		if(!$.MsgBox.Confirm("提示","该任务存在未完成的子任务，确认要完成该任务吗？",function(){
			return false;
		}));
	}
	
	
	bsWindow($("#finishDiv").html(),"完成任务",{width:"410",height:"200",buttons:
		 [{name:"确定",classStyle:"btn-alert-blue"},{name:"关闭",classStyle:"btn-alert-gray"}]
	,submit:function(v,h,f,d){
		if(v=="确定"){
			var progress = f.find("[fid=score]:first").val();
			var val = -1;
			try{
				val = parseInt(progress);
			}catch(e){}
			
			if(val==-1){
				$.MsgBox.Alert_auto("请填写任务评分,0-100之间");
				return false;
			}else if(isNaN(val)){
				$.MsgBox.Alert_auto("请填写任务评分,0-100之间");
				return false;
			}
			var param = {taskId:taskId,remark:f.find("[fid=finishTextarea]:first").val(),score:val,relTimes:f.find("[fid=relTimes]:first").val()};
			var json = tools.requestJsonRs(contextPath+"/coWork/finish.action",param);
			if(json.rtState){
				$.MsgBox.Alert_auto("操作成功");
				if(datagrid){
					d.remove();
					datagrid.datagrid("reload");
				}else{
					window.location.reload();
				}
			}
		}else if(v=="关闭"){
			return true;
		}
	}},"html");
}

function delay(taskId){
	bsWindow($("#delayDiv").html(),"任务延期",{width:"410",height:"250",buttons:
		 [{name:"确定",classStyle:"btn-alert-blue"},{name:"关闭",classStyle:"btn-alert-gray"}]
	,submit:function(v,h,f,d){
		if(v=="确定"){
			if(f.find("[fid=delayTime]:first").val()==""){
				$.MsgBox.Alert_auto("请填写延期日期");
				return false;
			}
			var param = {taskId:taskId,remark:f.find("[fid=delayTextarea]:first").val(),delayTime:f.find("[fid=delayTime]:first").val()};
			var json = tools.requestJsonRs(contextPath+"/coWork/delay.action",param);
			if(json.rtState){
				$.MsgBox.Alert_auto("操作成功");
				if(datagrid){
					d.remove();
					datagrid.datagrid("reload");
				}else{
					window.location.reload();
				}
			}
		}else if(v=="关闭"){
			return true;
		}
	}},"html");
}
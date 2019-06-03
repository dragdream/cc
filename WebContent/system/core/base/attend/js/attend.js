
/*考勤外出*/

/**
 * 删除外出记录
 * @param id  id
 * @param obj  删除A对象
 */
function deleteAttendOut(id,obj){
	$.MsgBox.Confirm ("提示", "是否删除此外出记录，删除后不可恢复！", function(){
		var url = contextPath + "/attendOutManage/deleteById.action";
		var para =  {sid:id} ;
		var jsonObj = tools.requestJsonRs(url,para);
		if (jsonObj.rtState) {
			$.MsgBox.Alert_auto("删除成功！");
			datagrid.datagrid('reload');
			
		}else{
			$.MsgBox.Alert_auto(jsonObj.rtMsg);
		}
		
	});
	
}
/**
 * 删除加班记录
 * @param id  id
 * @param obj  删除A对象
 */
function deleteAttendOvertime(id,obj){
	 $.MsgBox.Confirm ("提示", "是否删除此加班记录，删除后不可恢复！", function(){
		  var url = contextPath + "/attendOvertimeManage/deleteById.action";
		  var para =  {sid:id} ;
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){
				$.MsgBox.Alert_auto("删除成功！");
					datagrid.datagrid('reload');	
				
			}else{
				$.MsgBox.Alert_auto(json.rtMsg);
			}
		  
		  
	  });
	
}


/**
 * 获取审批人员
 */
function getRuleApprovUser(id){
	var url = contextPath + "/attendLeaderRuleManage/selectRuleLeaderPerson.action";
	var para =  {} ;
	var jsonObj = tools.requestJsonRs(url,para);
	if(jsonObj.rtState){
		var data = jsonObj.rtData;
		var userIds =data.userIds;
		var userNames = data.userNames;
		var userIdArray = userIds.split(",");
		var userNameArray = userNames.split(",");
		var options = "";
		for(var i = 0;i<userIdArray.length; i++){
			if(userIdArray[i] != ""){
				options = options +  "<option value='" + userIdArray[i] + "'>" + userNameArray[i] + "</option>";
			}
		}
		$("#" + id).append(options);
	}else{
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}


/**
 * 查看外出详情
 * @param id
 */
function attendOutInfo(id){
	var url = contextPath + "/system/core/base/attend/out/detail.jsp?id=" + id;
	bsWindow(url ,"外出详情",{width:"600",height:"240",buttons:
		[
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			return true;
		}else if(v=="关闭"){
			return true;
		}
	}});
}
/**
 * 查看加班详情
 * @param id
 */
function attendOvertimeInfo(id){
	var url = contextPath + "/system/core/base/attend/overtime/detail.jsp?id=" + id;
	bsWindow(url ,"加班详情",{width:"600",height:"220",buttons:
		[
		 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
	,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			return true;
		}else if(v=="关闭"){
			return true;
		}
	}});
}



/*考勤请假*/
/**
 * 删除请假记录
 * @param id  id
 * @param obj  删除A对象
 */
function deleteAttendLeave(id,obj){
	 $.MsgBox.Confirm ("提示", "是否删除此请假记录，删除后不可恢复！", function(){
		  var url = contextPath +"/attendLeaveManage/deleteById.action";
		  var para =  {sid:id} ;
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){
				$.MsgBox.Alert_auto("删除成功！");
				datagrid.datagrid('reload');
				
			}else{
				$.MsgBox.Alert_auto(json.rtMsg);
			}
		  	  
	  });
}

/**
 * 查看请假详情
 * @param id
 */
function attendLeaveInfo(id){
	var url = contextPath + "/system/core/base/attend/leave/detail.jsp?id=" + id;
	bsWindow(url ,"请假详情",{width:"600",height:"280",buttons:
		[
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			return true;
		}else if(v=="关闭"){
			return true;
		}
	}});
}


/*考勤出差*/
/**
 * 删除出差信息
 */
function deleteAttendEvection(id,obj){	
	 $.MsgBox.Confirm ("提示", "是否删除此出差记录，删除后不可恢复！", function(){
		  var url = contextPath + "/attendEvectionManage/deleteById.action";
		  var para =  {sid:id} ;
		  var json = tools.requestJsonRs(url,para);
		  if(json.rtState){
			 $.MsgBox.Alert_auto("删除成功！");
			 datagrid.datagrid('reload');
			 
		  }else{
			 $.MsgBox.Alert_auto(json.rtMsg);
		  }   
	  });
	
}

/**
 * 查看出差详情
 * @param id
 */
function attendEvectionInfo(id){
	var url = contextPath + "/system/core/base/attend/evection/detail.jsp?id=" + id;
    bsWindow(url ,"出差详情",{width:"600",height:"240",buttons:
        [
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			return true;
		}else if(v=="关闭"){
			return true;
		}
	}});
}

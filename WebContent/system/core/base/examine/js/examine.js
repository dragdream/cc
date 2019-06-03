/**
 * 获取有权限的考核指标集
 */
function  getPostExamine(id){
	var url = contextPath + "/TeeExamineGroupManage/getPostExamine.action";
	var jsonObj = tools.requestJsonRs(url, null);
	if (jsonObj.rtState) {
		var prcs = jsonObj.rtData;
	
		if(id && $("#" + id)[0]){//存在此对象
			var options = "";
			for ( var i = 0; i < prcs.length; i++) {
				options = options + "<option value='"+prcs[i].sid+"'>" + prcs[i].examineName + "</option>";
			}
			$("#" + id).append(options);
		}
		return prcs;
	} else {
		alert(jsonObj.rtMsg);
	}
}




/**
 * 查看自评信息
 */
function toSelfInfo(sid , groupId){
	window.location.href = contextPath + "/system/core/base/examine/self/selfinfo.jsp?taskId=" + sid  + "&groupId=" + groupId;
}

/**
 * 跳转至自评
 */
function toSelf(sid , groupId){
	window.location.href = contextPath +"/system/core/base/examine/self/add.jsp?taskId=" + sid  + "&groupId=" + groupId;
}

/**
 * 考核查看自评信息
 * @param sid
 * @param userId
 */
function getSelfData(sid , groupId ){
	var title = "查看自评信息";
	var  url = contextPath + "/system/core/base/examine/self/detail.jsp?sid=" + sid + "&groupId=" + groupId;
	bsWindow(url ,title,{width:"700",height:"320",buttons:
		[
	 	 {name:"关闭",classStyle:"btn btn-primary"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			//return ;
		}else if(v=="关闭"){
			return true;
		}
	}});
}
/**
 * 考核详情
 * @param sid
 * @param userId
 */
function toExamineDetail(taskId){
	var title = "查考核详情";
	var  url = contextPath + "/TeeExamineTaskManage/queryExamineInfo.action?sid=" + taskId ;
	bsWindow(url ,title,{width:"700",height:"320",buttons:
		[
	 	 {name:"关闭",classStyle:"btn btn-primary"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			//return ;
		}else if(v=="关闭"){
			return true;
		}
	}});
}


/**
 * 获取考核指标明细
 */
function getExamineGroupInfo(groupId){
	var title = "考核指标集明细";
	var  url = contextPath + "/system/core/base/examine/group/detail.jsp?groupId=" + groupId;
	bsWindow(url ,title,{width:"700",height:"320",buttons:
		[
	 	 {name:"关闭",classStyle:"btn btn-primary"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			//return ;
		}else if(v=="关闭"){
			return true;
		}
	}});
}


/**
 * 获取被考核人 明细
 */
function getExamineTaskParticipantInfo(taskId){
	var title = "被考核 人员清单";
	var  url = contextPath + "/system/core/base/examine/task/participantInfo.jsp?taskId=" + taskId;
	bsWindow(url ,title,{width:"500",height:"320",buttons:
		[
	 	 {name:"关闭",classStyle:"btn btn-primary"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			//return ;
		}else if(v=="关闭"){
			return true;
		}
	}});
}


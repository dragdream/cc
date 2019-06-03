/**
 * 详情信息
 */
function showInfoFunc(sid){
  var title = "培训计划详细信息";
  var url = contextPath + "/system/core/base/hr/training/plan/trainingPlanDetail.jsp?sid=" + sid;
  bsWindow(url ,title,{width:"850",height:"360",buttons:
		[
	 	 {name:"关闭",classStyle:"btn btn-primary"}
		 ]
		,submit:function(v,h){
		if(v=="关闭"){
			return true;
		}
	}});
}


function planStatusFunc(ids){
	var str = "";
	if(ids == "0"){
		str = "待审批";
	}else if(ids == "1"){
		str = "<font color='green'>已批准</font>";
	}else if(ids == "2"){
		str = "<font color='red'>未批准</font>";
	}
	return str;
}

/**
 * 获取所有培训计划列表
 * @param selectId  select选择框
 */
function getPlanList(selectId){
	var url = contextPath + "/trainingPlanController/getAllPlan.action";
	var para = [];
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var data = jsonObj.rtData;
		if($("#" + selectId)[0]){
			var str = "";
			for(var i =0; i < data.length ; i++ ){
				str = str + "<option value='"+ data[i].sid+"'>" + data[i].planName+ "</option>";
			}
			$("#" + selectId).append(str);
		}
		return data;
	} else {
		alert(jsonObj.rtMsg);
	}
}
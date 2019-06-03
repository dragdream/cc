/**
 * 获取选中值
 */
function getSelectItem(){
	var selections = $('#datagrid').datagrid('getSelections');
	var ids = "";
	alert(selections.length);
	for(var i=0;i<selections.length;i++){
		ids+=selections[i].uuid;
		if(i!=selections.length-1){
			ids+=",";
		}
	}
	return ids;
}

/**
 * 单个删除维护信息
 */
function deleteSingleFunc(sid){
	deleteObjFunc(sid);
}
/**
 * 批量删除
 */
function batchDeleteFunc(){
	var ids = getSelectItem();
	if(ids.length==0){
		alert("至少选择一项");
		return ;
	}
	deleteObjFunc(ids);
}
/**
 * 删除维护信息
 */
function deleteObjFunc(userId,year){
  $.jBox.confirm("确定要删除所选中记录？","确认",function(v){
		if(v=="ok"){
			var url = contextPath + "/userBudgetController/deleteObjById.action";
			var para = {userId:userId,year:year};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){
				$.jBox.tip("删除成功！", "info", {timeout: 1800});
				datagrid.datagrid('reload');
			}
		}
	});
}



/**
 * 新建编辑信息
 */
function toAddOrUpdate(userId,year,save){
	var  url = contextPath + "/system/subsys/budget/userBudget/addOrUpdateUserBudget.jsp?userId=" + userId + "&year=" + year + "&save=" + save;
	window.location.href = url;
}

/**
 * 查询页详情
 * @param userId
 * @param year
 */
function showQueryInfoFunc(userId,year){
	showInfoFunc(userId,year,1);
}
/**
 * 管理页详情
 * @param userId
 * @param year
 */
function showManageInfoFunc(userId,year){
	showInfoFunc(userId,year,0);
}

/**
 * 详情信息
 * @param userId
 * @param year
 * @param optFlag 0-返回管理页面；1-返回查询页面
 */
function showInfoFunc(userId,year,optFlag){
	var url = contextPath + "/system/subsys/budget/userBudget/userBudgetDetail.jsp?userId=" + userId + "&year=" + year + "&optFlag=" + optFlag;
	location.href = url;
}





//季度合计
function jiDuHeJiFunc(objId1,objId2,objId3,heJiId){
	var objId1Obj = $("#"+objId1);
	var objId2Obj = $("#"+objId2);
	var objId3Obj = $("#"+objId3);
	
	var heJiIdObj = $("#"+heJiId);
	
	var total = 0;
	if(isNumber(objId1Obj.val())){
		total +=Number(objId1Obj.val()); 
	}
	if(isNumber(objId2Obj.val())){
		total +=Number(objId2Obj.val()); 
	}
	if(isNumber(objId3Obj.val())){
		total +=Number(objId3Obj.val()); 
	}
	heJiIdObj.text(Number(total)); 
}

//年合计
function nianHeJiFunc(objId1,objId2,objId3,objId4,heJiId){
	var objId1Obj = $("#"+objId1);
	var objId2Obj = $("#"+objId2);
	var objId3Obj = $("#"+objId3);
	var objId4Obj = $("#"+objId4);
	
	var heJiIdObj = $("#"+heJiId);
	
	var total = 0;
	
	if(isNumber(objId1Obj.text())){
		total +=Number(objId1Obj.text()); 
	}
	if(isNumber(objId2Obj.text())){
		total +=Number(objId2Obj.text()); 
	}
	if(isNumber(objId3Obj.text())){
		total +=Number(objId3Obj.text()); 
	}
	if(isNumber(objId4Obj.text())){
		total +=Number(objId4Obj.text()); 
	}
	heJiIdObj.text(Number(total)); 
}







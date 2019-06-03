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
function deleteObjFunc(deptId,year){
  $.jBox.confirm("确定要删除所选中记录？","确认",function(v){
		if(v=="ok"){
			var url = contextPath + "/deptBudgetController/deleteObjById.action";
			var para = {deptId:deptId,year:year};
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
function toAddOrUpdate(deptId,year,save){
	var  url = contextPath + "/system/subsys/budget/deptBudget/addOrUpdateDeptBudget.jsp?deptId=" + deptId + "&year=" + year + "&save=" + save;
	window.location.href = url;
}

/**
 * 管理页详情
 * @param deptId
 * @param year
 */
function showQueryInfoFunc(deptId,year){
	showInfoFunc(deptId,year,1);
}
/**
 * 查询页详情
 * @param deptId
 * @param year
 */
function showManageInfoFunc(deptId,year){
	showInfoFunc(deptId,year,0);
}

/**
 * 详情信息
 * @param deptId
 * @param year
 * @param optFlag 0-返回管理页面；1-返回查询页面
 */
function showInfoFunc(deptId,year,optFlag){
	var url = contextPath + "/system/subsys/budget/deptBudget/deptBudgetDetail.jsp?deptId=" + deptId + "&year=" + year + "&optFlag=" + optFlag;
	location.href = url;
}

/**
 * 获取部门下拉列表
 */
function getDeptList(){
	var url =  contextPath+"/deptManager/getDeptTreeAll.action";
	window.deptIdProxy = ZTreeTool.comboCtrl($("#deptId"),{url:url});
}



function reSetFunc(){
	$("#year").val('');
	$("#amountMax").val('');
	$("#amountMin").val('');
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
	return;
	
	if(isNumber(objId1Obj.val()) && !isNumber(objId2Obj.val()) && !isNumber(objId3Obj.val())){
		heJiIdObj.text(Number(objId1Obj.val())); 
	}else if(isNumber(objId2Obj.val()) && !isNumber(objId1Obj.val()) && !isNumber(objId3Obj.val())){
		heJiIdObj.text(Number(objId2Obj.val())); 
	}else if(isNumber(objId3Obj.val()) && !isNumber(objId1Obj.val()) && !isNumber(objId2Obj.val())){
		heJiIdObj.text(Number(objId3Obj.val())); 
		
	}else if(isNumber(objId1Obj.val()) && isNumber(objId2Obj.val()) && !isNumber(objId3Obj.val())){
		heJiIdObj.text(Number(objId1Obj.val())+ Number(objId2Obj.val())); 
		
	}else if(isNumber(objId1Obj.val()) && isNumber(objId3Obj.val()) && !isNumber(objId2Obj.val())){
		heJiIdObj.text(Number(objId1Obj.val())+ Number(objId3Obj.val())); 
	}else if(isNumber(objId2Obj.val()) && isNumber(objId3Obj.val()) && !isNumber(objId1Obj.val())){
		heJiIdObj.text(Number(objId2Obj.val())+ Number(objId3Obj.val())); 
	}else if(isNumber(objId1Obj.val()) && isNumber(objId2Obj.val()) && isNumber(objId3Obj.val())){
		heJiIdObj.text(Number(objId1Obj.val())+ Number(objId2Obj.val())+ Number(objId3Obj.val())); 
	}else{
		heJiIdObj.text(""); 
	}
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







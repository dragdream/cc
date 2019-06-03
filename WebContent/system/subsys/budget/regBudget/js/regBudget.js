/**
 * 获取选中值
 */
function getSelectItem(){
	var selections = $('#datagrid').datagrid('getSelections');
	var ids = "";
	for(var i=0;i<selections.length;i++){
		ids+= "'" + selections[i].uuid + "'";
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
	if(sid){
		sid = "'" + sid + "'";
	}
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
function deleteObjFunc(ids){
 $.jBox.confirm("确定要删除所选中记录？","确认",function(v){
		if(v=="ok"){
			var url = contextPath + "/budgetRegController/deleteObjById.action";
			var para = {sids:ids};
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
function toAddOrUpdate(uuid){
	if(uuid!=""){
		var  url = contextPath + "/system/subsys/budget/regBudget/addOrUpdateRegBudget.jsp?uuid=" + uuid;
		window.location.href = url;
	}else{
		if(isOpenBisEngine(4)){
			$.jBox.confirm("请选择申请流程", "提示", function(v,h,f){
				if(v==1){
					createNewWork(44);
				}else{
					createNewWork(34);
				}
			}, { buttons: { '预算申请': 1, '费用报销': 2} });
			return;
		}
		var  url = contextPath + "/system/subsys/budget/regBudget/addOrUpdateRegBudget.jsp?uuid=" + uuid;
		window.location.href = url;
	}
}

/**
 * 申请类型
 * @param value
 * @param rowData
 * @param rowIndex
 * @returns {String}
 */
function regTypeNameFunc(value, rowData, rowIndex){
	var str = "";
	if(value =='1'){
		str = "个人预算";
	}else if(value =='2'){
		str = "部门预算";
	}
	return str;
}
/**
 * 记录类型
 * @param value
 * @param rowData
 * @param rowIndex
 * @returns {String}
 */
function typeFunc(value, rowData, rowIndex){
	var str = "";
	if(value =='1'){
		str = "预算申请";
	}else if(value =='2'){
		str = "报销";
	}
	return str;
}


/**
 * 管理页详情
 * @param deptId
 * @param year
 */
function showQueryInfoFunc(deptId){
	showInfoFunc(deptId,1);
}
/**
 * 查询页详情
 * @param deptId
 * @param year
 */
function showManageInfoFunc(deptId){
	showInfoFunc(deptId,0);
}

/**
 * 详情信息
 * @param deptId
 * @param year
 * @param optFlag 0-返回管理页面；1-返回查询页面
 */
function showInfoFunc(uuid,queryOptFlag){
	var url = contextPath + "/system/subsys/budget/regBudget/regBudgetDetail.jsp?uuid=" + uuid + "&queryOptFlag=" + queryOptFlag;
	location.href = url;
}

/**
 * 获取部门下拉列表
 */
function getDeptList(){
	var url =  contextPath+"/deptManager/getDeptTreeAll.action";
	window.deptIdProxy = ZTreeTool.comboCtrl($("#opDeptId"),{url:url});
}


function regTypeFunc(str){
	if(str =='1'){
		$("#deptTr").hide();
		$("#personTr").show();
	}else {
		$("#personTr").hide();
		$("#deptTr").show();
	}
}


function reSetFunc(){
	$("#amountMax").val('');
	$("#amountMin").val('');
}




/**
 * 获取辅助部门
 */
function getPersonDeptList(uuid,inputIdName){
	var url = contextPath + "/personManager/getPersonById.action";
	var para = {uuid:uuid};
	var json = tools.requestJsonRs(url,para);
	if(json.rtState){
		var objStr = document.getElementById(inputIdName);
		var prcs = json.rtData;
		
		objStr.length=0;
		
		//部门
		var deptIdStr = prcs.deptId;
		var deptIdNameStr = prcs.deptIdName;
		var option = new Option(deptIdNameStr,deptIdStr);
		objStr.options.add(option);
		
		//辅助部门
		var deptIdOtherStr = prcs.deptIdOtherStr;
		var deptIdOtherStrNameStr = prcs.deptIdOtherStrName;
		
		var deptIdOtherStrs= deptIdOtherStr.split(",");
		var deptIdOtherStrNameStrs= deptIdOtherStrNameStr.split(",");
		if(deptIdOtherStrs.length>0){
			for(var i = 0;i<deptIdOtherStrs.length;i++){
				if(deptIdOtherStrs[i]){
					var option = new Option(deptIdOtherStrNameStrs[i],deptIdOtherStrs[i]);
					objStr.options.add(option);
				}
			}
		}
	}
}


/**
 * 获取部门当月预算剩余金额
 * @param deptId
 * @returns {Number}
 */
function getDeptBudgetCost(deptId){
	var url = contextPath + "/deptBudgetController/getDeptBudgetCost.action";
	var para =  {deptId:deptId};
	var returnPrc=0;
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		returnPrc = jsonRs.rtData.returnAmount;
	}else{
		alert(jsonRs.rtMsg);
	}
	return returnPrc;
}

/**
 * 获取个人当月预算剩余金额
 * @param userId
 * @returns {Number}
 */
function getUserBudgetCost(userId){
	var url = contextPath + "/userBudgetController/getUserBudgetCost.action";
	var para = {userId:userId};
	var returnPrc=0;
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		returnPrc = jsonRs.rtData.returnAmount;
	}else{
		alert(jsonRs.rtMsg);
	}
	return returnPrc;
}










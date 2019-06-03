/**
 * 获取选中值
 */
function getSelectItem(){
	var selections = $('#datagrid').datagrid('getSelections');
	var ids = "";
	for(var i=0;i<selections.length;i++){
		ids+=selections[i].sid;
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
function deleteObjFunc(ids){
  $.jBox.confirm("确定要删除所选中记录？","确认",function(v){
		if(v=="ok"){
			var url = contextPath + "/crmAfterSaleServController/deleteObjById.action";
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
 * 完成情况
 * @param value
 * @returns {String}
 */
function handleStatusFunc(value){
	if(value =="0"){
		value = "未完成";
	}else{
		value = "已完成";
	}
	return value;
}





/**
 * 新建编辑信息
 */
function toAddOrUpdate(sid){
	var  url = contextPath + "/system/subsys/crm/core/afterSaleService/addOrUpdateSaleService.jsp?sid=" + sid;
	window.location.href = url;
}



/**
 * 详情信息
 */
function showInfoFunc(sid){
	var url = contextPath + "/system/subsys/crm/core/afterSaleService/afterSaleServiceDetail.jsp?sid=" + sid;
	location.href = url;
}



















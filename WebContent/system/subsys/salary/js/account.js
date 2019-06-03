/**
 * 获取所有 工资账套
 */
function getAllAccount(accountId){	
	var url = contextPath+"/teeSalAccountController/getAllAccount.action";
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		var prcs = json.rtData;
		if(accountId && $("#" + accountId)[0]){
			var options = "";
			for ( var i = 0; i < prcs.length; i++) {
				options = options + "<option value='"+prcs[i].sid+"'>" + prcs[i].accountName + "</option>";
			}
			$("#" + accountId).append(options);
		}
		return prcs;
	}else{
		alert(json.rtMsg);
		return;
	}
}
/**
 * 获取工资账套 byId
 */
function getAccountById(sid){	
	var url = contextPath+"/teeSalAccountController/getById.action";
	var json = tools.requestJsonRs(url, {sid:sid});
	if(json.rtState){
		return json.rtData;
	}else{
		alert(json.rtMsg);
		return;
	}
}

/**
 * 获取所有工资项目  by 账套ID
 */
function getItemListByAccountId(accountId){	
	var url = contextPath+"/teeSalItemController/getItemListByAccountId.action";
	var json = tools.requestJsonRs(url,{accountId:accountId});
	if(json.rtState){
		return json.rtData;
	}else{
		alert(json.rtMsg);
		return;
	}
}
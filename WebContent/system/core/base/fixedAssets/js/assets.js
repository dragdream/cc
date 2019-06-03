/**
 * 获取领用、返库记录
 * @param assetsId  固定资产类型
 * @param optType  操作类型
 * @returns
 */
function getAssetsRecordByAssetsId(assetsId , optType){
	var url =contextPath+"/teeFixedAssetsRecordController/selectByAssetsId.action";
	var param = {fixedAssetsId:assetsId , optType: optType};
	var json = tools.requestJsonRs(url , param);
	if(json.rtState){
		var prcs = json.rtData;
		return prcs;
	}else{
		alert(json.rtMsg);
	}
}
/**
 * 领用详情
 * @param sid   领用记录Id
 * @param assetsId  固定资产Id
 */
function appDetails(sid , assetsId){
	var title = "固定资产领用详情";
	var url = contextPath + "/system/core/base/fixedAssets/record/app/details.jsp?fixedAssetsId=" + assetsId + "&sid=" + sid;
	bsWindow(url ,title,{width:"700",height:"250",buttons:
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
 * 归还详情
 * @param sid   记录Id
 * @param assetsId  固定资产Id
 */
function backDetails(sid , assetsId){
	var title = "固定资产归还详情";
	var url = contextPath + "/system/core/base/fixedAssets/record/back/details.jsp?fixedAssetsId=" + assetsId + "&sid=" + sid;
	bsWindow(url ,title,{width:"700",height:"250",buttons:
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
 * 维修详情
 * @param sid   记录Id
 * @param assetsId  固定资产Id
 */
function repairDetails(sid , assetsId){
	var title = "固定资产维修详情";
	var url = contextPath + "/system/core/base/fixedAssets/record/repair/details.jsp?fixedAssetsId=" + assetsId + "&sid=" + sid;
	bsWindow(url ,title,{width:"700",height:"280",buttons:
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
 * 报废详情
 * @param sid   记录Id
 * @param assetsId  固定资产Id
 */
function discardDetails(sid , assetsId){
	var title = "固定资产报废详情";
	var url = contextPath + "/system/core/base/fixedAssets/record/discard/details.jsp?fixedAssetsId=" + assetsId + "&sid=" + sid;
	bsWindow(url ,title,{width:"700",height:"280",buttons:
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
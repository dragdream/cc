function TeeFlowTypeService(){
	/**
	 * 通过分类ID获取流程集合
	 */
	this.getFlowListBySort=function(sortId){
		var url = contextPath+"/flowType/getFlowListBySort.action";
		var json = tools.requestJsonRs(url,{sortId:sortId});
		if(json.rtState){
			return json.rtData;
		}
		alert(json.rtMsg);
		return undefined;
	}
	
	/**
	 * 获取流程数据
	 */
	this.get=function(flowTypeId){
		var url = contextPath+"/flowType/get.action";
		var json = tools.requestJsonRs(url,{flowTypeId:flowTypeId});
		if(json.rtState){
			return json.rtData;
		}
		return undefined;
	}
	
	/**
	 * 保存对象
	 */
	this.save=function(params){
		var url = contextPath+"/flowType/save.action";
		var json = tools.requestJsonRs(url,params);
		if(json.rtState){
			return true;
		}
		alert(json.rtMsg);
		return false;
	}
	
	 /**
	  * 更新
	  */
	this.update=function(params){
		var url = contextPath+"/flowType/update.action";
		var json = tools.requestJsonRs(url,params);
		if(json.rtState){
			return true;
		}
		alert(json.rtMsg);
		return false;
	}
}
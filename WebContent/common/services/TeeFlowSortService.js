function TeeFlowSortService(){
	
	/**
	 * 批量删除流程分类
	 */
	this.deleteBatch=function(sortIds){
		var url = contextPath+"/flowSort/deleteBatch.action";
		var json = tools.requestJsonRs(url,{sortIds:sortIds});
		if(json.rtState){
			return true;
		}
		alert(json.rtMsg);
		return false;
	};
	
	/**
	 * 通过分类ID获取流程集合
	 */
	this.doSortOrder=function(type,flowSortId){
		var url = contextPath+"/flowSort/doSortOrder.action";
		var json = tools.requestJsonRs(url,{type:type,flowSortId:flowSortId});
		if(json.rtState){
			return true;
		}
		alert(json.rtMsg);
		return false;
	};
	
	/**
	 * 获取流程数据
	 */
	this.get=function(flowSortId){
		var url = contextPath+"/flowSort/get.action";
		var json = tools.requestJsonRs(url,{flowSortId:flowSortId});
		if(json.rtState){
			return json.rtData;
		}
		alert(json.rtMsg);
		return undefined;
	};
	
	/**
	 * 保存对象
	 */
	this.save=function(params){
		var url = contextPath+"/flowSort/save.action";
		var json = tools.requestJsonRs(url,params);
		if(json.rtState){
			return true;
		}
		alert(json.rtMsg);
		return false;
	};
	
	 /**
	  * 更新
	  */
	this.update=function(params){
		var url = contextPath+"/flowSort/update.action";
		var json = tools.requestJsonRs(url,params);
		if(json.rtState){
			return true;
		}
		alert(json.rtMsg);
		return false;
	};
	  
	  /**
	  * 删除
	  */
	this.del=function(params){
		var url = contextPath+"/flowSort/delete.action";
		var json = tools.requestJsonRs(url,params);
		if(json.rtState){
			return true;
		}
		alert(json.rtMsg);
		return false;
	};
	
	/**
	 * 获取集合对象
	 */
	this.getList=function(){
		var url = contextPath+"/flowSort/getList.action";
		var json = tools.requestJsonRs(url,{});
		if(json.rtState){
			return json.rtData;
		}
		alert(json.rtMsg);
		return undefined;
	}
}
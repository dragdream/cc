function TeeFormSortService(){ 
	/**
	 * 获取表单分类列表
	 */
	this.getSortList=function(){
		var url = contextPath + "/formSort/getSortList.action";
		var json = tools.requestJsonRs(url,{});
		if(json.rtState){
			return json.rtData;
		}
		alert(json.rtMsg);
		return undefined;
	};
	
	 /**
	  * 保存表单分类
	  */
	this.save=function(params){
		var url = contextPath + "/formSort/save.action";
		var json = tools.requestJsonRs(url,params);
		if(json.rtState){
			return true;
		}
		alert(json.rtMsg);
		return false;
	};
	
	/**
	 * 更新表单分类
	 */
	this.update=function(params){
		var url = contextPath + "/formSort/update.action";
		var json = tools.requestJsonRs(url,params);
		if(json.rtState){
			return true;
		}
		alert(json.rtMsg);
		return false;
	};
	
	/**
	 * 删除实体
	 */
	this.del=function(params){
		var url = contextPath + "/formSort/delete.action";
		var json = tools.requestJsonRs(url,params);
		if(json.rtState){
			return true;
		}
		alert(json.rtMsg);
		return false;
	};
	
	/**
	 * 批量删除
	 */
	this.deleteBatch=function(sortIds){
		var url = contextPath + "/formSort/deleteBatch.action";
		var json = tools.requestJsonRs(url,{sortIds:sortIds});
		if(json.rtState){
			return true;
		}
		alert(json.rtMsg);
		return false;
	};
	
	/**
	 * 获取实体
	 */
	this.get=function(formSortId){
		var url = contextPath + "/formSort/get.action";
		var json = tools.requestJsonRs(url,{formSortId:formSortId});
		if(json.rtState){
			return json.rtData;
		}
		alert(json.rtMsg);
		return undefined;
	};
	
	/**
	 * 排序方法
	 */
	this.doSortOrder=function(type,formSortId){
		var url = contextPath + "/formSort/doSortOrder.action";
		var json = tools.requestJsonRs(url,{type:type,formSortId:formSortId});
		if(json.rtState){
			return true;
		}
		alert(json.rtMsg);
		return false;
	};
	
	/**
	 * 获取表单分类模型集合
	 */
	this.getSelectCtrlDataSource=function(){
		var url = contextPath + "/formSort/getSelectCtrlDataSource.action";
		var json = tools.requestJsonRs(url,{});
		if(json.rtState){
			return json.rtData;
		}
		alert(json.rtMsg);
		return undefined;
	};
}
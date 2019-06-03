/**
 * 获取审批已同意的招聘计划
 */
function getRecruitPlan(){
	
	var  url = contextPath + "/system/core/base/hr/recruit/plan/queryPlan.jsp";
	bsWindow(url ,"选择招聘计划",{width:"500",height:"300",buttons:
		[
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			
		}else if(v=="关闭"){
			return true;
		}
	}});
}

/*
 * 选择人才库
 */
function getHrPool(){
	
	var  url = contextPath + "/system/core/base/hr/pool/selectPool.jsp";
	bsWindow(url ,"选择应聘者",{width:"500",height:"300",buttons:
		[
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			
		}else if(v=="关闭"){
			return true;
		}
	}});
}


/**
 * 查询人才库信息 --绑定对象
 * @return
 * retArray:表单文本框数组 
 * @param callBackPara 回调方法参数 
 */
var hrPoolsArray = null;
function queryHrPools(retArray ,callBackFunc) {
	hrPoolsArray = retArray;
	var url = contextPath + "/system/core/base/hr/pool/queryHrPools.jsp";
    if(callBackFunc){
	   url += "?callBackFunc=" + callBackFunc;
    }
    dialogChangesize(url, 900, 500);
}
/**
 * 执行回掉函数
 */
function trigger_callback(type, args ){
    if(typeof xparent == 'object' && typeof xparent[type] == 'function'){
    	xparent[type].apply(this, args );
        CloseWindow();
    }
}

/**
 * 查询招聘需求信息 --绑定对象
 * @return
 * retArray:表单文本框数组 
 * @param callBackPara 回调方法参数 
 */
var hrRecruitRequArray = null;
function queryHrRecruitRequ(retArray ,callBackFunc) {
	hrRecruitRequArray = retArray;
	var url = contextPath + "/system/core/base/hr/recruit/requirements/queryHrRequitRequ.jsp";
    if(callBackFunc){
	   url += "?callBackFunc=" + callBackFunc;
    }
    dialogChangesize(url, 900, 500);
}
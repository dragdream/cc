var userRetNameArray = null;
var deptRetNameArray = null;
var roleRetNameArray = null;
var userExternalRetNameArray = null;
var sysMenuNameArray = null;
var sysModuleArray = null;
var objSelectType = null ;//1-select Multiple 其它为 input
var meetingEquipment = null;



/**
 * 选择人员
 * @return
 * retArray:表单文本框数组 retArray[2]为是否为多选框处理
 * moduleId:模块
 * privNoFlag:是否需要处理 1-不需要处理 默认为处理
 * filter:查询人员过滤器，JSON串，可扩展条件
 *   @param callBackPara 回调方法参数 
 *   @param confirmCallBack 点击确认回掉函数
 */
function selectUser(retArray , moduleId,privNoFlag ,filter,callBackPara,confirmCallBack) {
  userRetNameArray = retArray;
  objSelectType  = retArray[2] || "";
  var url = contextPath + "/system/core/orgselect/selectMultiUser.jsp?objSelectType=" + objSelectType;
  if (moduleId) {
    url += "&moduleId=" + moduleId;
    if (!privNoFlag) {
      privNoFlag = 0;
    }
 
  }
  if (privNoFlag) {
	  url += "&privNoFlag=" + privNoFlag;
  }

  if(filter){
	  url += "&userFilter=" + filter;
  }
  
  if(callBackPara){
	  url += "&callBackPara=" + callBackPara;
  }
  
  if(confirmCallBack){
	  url += "&confirmCallBack=" + confirmCallBack;
  }
 
  dialogChangesize(url, 760, 400);
}


/**
 * 选择人员  ----  工作流
 * @return
 * retArray:表单文本框数组 retArray[2]为是否为多选框处理
 * moduleId:模块
 * privNoFlag:是否需要处理 1-不需要处理 默认为处理
 * filter:查询人员过滤器，JSON串，可扩展条件
 *   @param callBackPara 回调方法参数 
 */
function selectUserWorkFlow(retArray , moduleId,privNoFlag ,filter,callBackPara) {
  userRetNameArray = retArray;
  
  objSelectType  = retArray[2] || "";
  var url = contextPath + "/system/core/orgselect/selectMultiUserWorkFlow.jsp?objSelectType=" + objSelectType;
  if (moduleId) {
    url += "&moduleId=" + moduleId;
    if (!privNoFlag) {
      privNoFlag = 0;
    }
 
  }
  if (privNoFlag) {
	  url += "&privNoFlag=" + privNoFlag;
  }
 
  if(filter){
	  window.RANDOM_USER_FILTER = filter;
  }else{
	  window.RANDOM_USER_FILTER = "0";
  }
  
  if(callBackPara){
	  url += "&callBackPara=" + callBackPara;
  }
 
  dialogChangesize(url, 760, 400);
}
/***
 * 单个选人 
 * @param retArray  表单属性数组
 * @param moduleId 	模块Id
 * @param privNoFlag  //是否需要处理 1-需要处理权限  默认为不带权限
 * @param filter  过滤条件查询
 * @param callBackPara 回调方法参数 
 */
function selectSingleUser(retArray,moduleId,privNoFlag,filter,callBackPara) {
	 userRetNameArray = retArray;
	 objSelectType  = retArray[2] || "";
	 var url = contextPath + "/system/core/orgselect/selectSingleUser.jsp?isSingle=true&objSelectType=" + objSelectType;
	 if (moduleId) {
	    url += "&moduleId=" + moduleId;
	    if (!privNoFlag) {
	      privNoFlag = 0;
	    }
	   
	 }
	 
	 if (privNoFlag) {
		  url += "&privNoFlag=" + privNoFlag;
	  }

	  if(filter){
		  url += "&userFilter=" + filter;
	  }
	  if(callBackPara){
		
		  url += "&callBackPara=" +  tools.parseJson2String(callBackPara);
	  }
	  dialogChangesize(url, 560, 400);
}


/***
 * 单个选人  -----工作流
 * @param retArray  表单属性数组
 * @param moduleId 	模块Id
 * @param privNoFlag  //是否需要处理 1-需要处理权限  默认为不带权限
 * @param filter  过滤条件查询
 * @param callBackPara 回调方法参数 
 */
function selectSingleUserWorkFlow(retArray,moduleId,privNoFlag,filter,callBackPara) {
	 userRetNameArray = retArray;
	 objSelectType  = retArray[2] || "";
	 var url = contextPath + "/system/core/orgselect/selectSingleUserWorkFlow.jsp?isSingle=true&objSelectType=" + objSelectType;
	 if (moduleId) {
	    url += "&moduleId=" + moduleId;
	    if (!privNoFlag) {
	      privNoFlag = 0;
	    }
	 }
	 
	 if (privNoFlag) {
		  url += "&privNoFlag=" + privNoFlag;
	  }

	  if(filter){
		  window.RANDOM_USER_FILTER = filter;
	  }else{
		  window.RANDOM_USER_FILTER = "0";
	  }
	  if(callBackPara){
		
		  url += "&callBackPara=" +  tools.parseJson2String(callBackPara);
	  }
	  dialogChangesize(url, 560, 400);
}

/**
 * 选择部门
 * @return
 */
function selectDept(retArray , moduleId,privNoFlag , noAllDept) {
  deptRetNameArray = retArray;
  objSelectType  = retArray[2] || "";
  var url = contextPath + "/system/core/orgselect/selectMultiDept.jsp?objSelectType=" + objSelectType;
  var has = false;
  if (moduleId) {
    url += "&moduleId=" + moduleId ;
  }
  if (privNoFlag) {
    url += "&privNoFlag=" + privNoFlag ;
  }
  if (noAllDept) {
    url += "&noAllDept=" + noAllDept ;
  }
  dialogChangesize(url, 560, 400);
}


/**
 * 选择单个部门
 * @return
 */
function selectSingleDept(retArray , moduleId,privNoFlag , noAllDept) {
  deptRetNameArray = retArray;
  objSelectType  = retArray[2] || "";
  var url = contextPath + "/system/core/orgselect/selectMultiDept.jsp?objSelectType=" + objSelectType + "&isSingle=1";
  var has = false;
  if (moduleId) {
    url += "&moduleId=" + moduleId ;
  }
  if (privNoFlag) {
    url += "&privNoFlag=" + privNoFlag ;
  }
  if (noAllDept) {
    url += "&noAllDept=" + noAllDept ;
  }
  dialogChangesize(url, 560, 400);
}

/**
 * 选择多个角色
 * @param retArray  表单属性数组
 * @param moduleId 模块Id
 * @param privNoFlag   //是否控制权限（暂不考虑，个人权限设置）
 *        privNoFlag = 0;全体部门、全体角色  
 *        privNoFlag = 1; //自己的管理范围、全体角色 
 *        privNoFlag = 2; //自己的管理范围、低角色
 *        privNoFlag = 3; //自己的管理范围、低角色，OA管理员除外的用户
 * @param  privOp  操作权限 1-需要控制，获取角色  其它-不控制，全部获取
 * @return
 */
function selectRole(retArray, moduleId,privNoFlag , privOp) {
  roleRetNameArray = retArray;
  objSelectType = retArray[2] || "";
  var url =contextPath + "/system/core/orgselect/selectMultiRole2.jsp?objSelectType=" + objSelectType ;
  if (moduleId) {
    url += "&moduleId=" + moduleId;
    if (!privNoFlag) {
      privNoFlag = 0;
    }
    url += "&privNoFlag=" + privNoFlag ;
  }
  if (privOp) {
	  url += "&privOp=" + privOp ;
  }
  //dialogChangesize(url , 280, 400);
  dialogChangesize(url, 560, 400);
}
/**
 * 选择单个
 * @param retArray
 * @param moduleId
 * @param privNoFlag
 * @param privOp
 * @returns
 */
function selectSingleRole(retArray, moduleId,privNoFlag , privOp) {
	  roleRetNameArray = retArray;
	  objSelectType = retArray[2] || "";
	  var url =contextPath + "/system/core/orgselect/selectMultiRole.jsp?objSelectType=" + objSelectType + "&isSingle=1";
	  if (moduleId) {
	    url += "&moduleId=" + moduleId;
	    if (!privNoFlag) {
	      privNoFlag = 0;
	    }
	    url += "&privNoFlag=" + privNoFlag ;
	  }
	  if (privOp) {
		  url += "&privOp=" + privOp ;
	  }
	  dialogChangesize(url , 280, 400);
	
}


/**
 * 选择菜单组
 * @return
 */
function selectMenuGroup(retArray, moduleId,privNoFlag , privOp) {
  roleRetNameArray = retArray;
  objSelectType = retArray[2] || "";
 
  var url =contextPath + "/system/core/orgselect/selectMultiMenuGroup.jsp?objSelectType=" + objSelectType;
  var has = false;
  if (moduleId) {
    url += "?moduleId=" + moduleId;
    if (!privNoFlag) {
      privNoFlag = 0;
    }
    url += "&privNoFlag=" + privNoFlag + "&objSelectType=" + objSelectType;
    has = true;
  }
  if (privOp) {
    if (has) {
      url += "&privOp=" + privOp + "&objSelectType=" + objSelectType;
    } else {
      url += "?privOp=" + privOp+ "&objSelectType=" + objSelectType;
    }
  }
  dialogChangesize(url , 280, 400);
}


/**
 * 选择菜单
 * @return
 * retArray:表单文本框数组 retArray[2]为是否为多选框处理
 * moduleId:模块
 * privNoFlag:是否需要处理 1-不需要处理 默认为处理
 * filter:查询人员过滤器，JSON串，可扩展条件
 *   @param callBackPara 回调方法参数 
 */
function selectSysMenu(retArray , moduleId,privNoFlag ,filter,callBackPara) {
	sysMenuNameArray = retArray;
  
  objSelectType  = retArray[2] || "";
  var url = contextPath + "/system/core/orgselect/selectMultiSysMenu.jsp?objSelectType=" + objSelectType;
  if (moduleId) {
    url += "&moduleId=" + moduleId;
    if (!privNoFlag) {
      privNoFlag = 0;
    }
 
  }
  if (privNoFlag) {
	  url += "&privNoFlag=" + privNoFlag;
  }
 
  if(filter){
	  url += "&userFilter=" + filter;
  }
  
  if(callBackPara){
	  url += "&callBackPara=" + callBackPara;
  }
 
  dialogChangesize(url, 280, 400);
}


/**
 * 选择模块
 * @return
 * retArray:表单文本框数组 retArray[2]为是否为多选框处理
 * moduleId:模块
 * privNoFlag:是否需要处理 1-不需要处理 默认为处理
 * filter:查询人员过滤器，JSON串，可扩展条件
 *   @param callBackPara 回调方法参数 
 */
function selectSysModule(retArray , moduleId,callBackPara) {
	sysModuleArray = retArray;
  
    objSelectType  = retArray[2] || "";
    var url = contextPath + "/system/core/orgselect/selectSysModule.jsp?objSelectType=" + objSelectType;
    if (moduleId) {
	    url += "&moduleId=" + moduleId;
	    if (!privNoFlag) {
	      privNoFlag = 0;
	    }
    }
    if(callBackPara){
    	url += "&callBackPara=" + callBackPara;
    }
    dialogChangesize(url, 290, 450);
}


/**
 * 选择离职人员
 * @return
 */
function selectUserExternalSelect(retArray) {
  userExternalRetNameArray = retArray;
  openDialogResize(contextPath + "/core/funcs/orgselect/MultiUserExternalSelect.jsp", 520, 400);
}


/**
 * 清空选人框数据
 * @param idStr
 * @param idNameStr
 * @param objSelectType
 */
function clearData(idStr,idNameStr,objSelectType){
	to_id_field = document.getElementById( idStr);
	if(objSelectType && objSelectType == '1'){
		jQuery(to_id_field).empty();

	}else{
		to_id_field.value = "";
		jQuery("#" + idNameStr).val("");
	}

}


/**
 * 选择模块
 * @return
 * retArray:表单文本框数组 retArray[2]为是否为多选框处理
 * moduleId:模块
 * privNoFlag:是否需要处理 1-不需要处理 默认为处理
 * filter:查询人员过滤器，JSON串，可扩展条件
 *   @param callBackPara 回调方法参数 
 */
function selectMeetingEquipment(retArray , moduleId,callBackPara) {
	meetingEquipment = retArray;
  
    objSelectType  = retArray[2] || "";
    var url = contextPath + "/system/core/orgselect/selectMeetingEquipment.jsp?objSelectType=" + objSelectType;
    if (moduleId) {
	    url += "&moduleId=" + moduleId;
	    if (!privNoFlag) {
	      privNoFlag = 0;
	    }
    }
    if(callBackPara){
    	url += "&callBackPara=" + callBackPara;
    }
    dialogChangesize(url, 290, 450);
}

/**
 * 根据系统参数名称 获取系统参数信息  
 *  支持获取多个参数   获取多个时，以逗号分隔即可 
 * @param paramNames  以逗号分隔参数名称字符串
 * return 返回参数 数组对象 [{paraName:'TYPE_XXXX' , paraValue:'111'}]
 */
function getSysParamByNames(paramNames){
	var url =   contextPath + "/sysPara/getSysParaList.action";
	var para = {paraNames:paramNames};
	var jsonObj = tools.requestJsonRs(url ,para);
	if(jsonObj.rtState){
		return jsonObj.rtData;
	}else{
		alert(jsonObj.rtMsg);
	}
}

/**
 * 保存或者更新系统参数  
 *  单个保存或者更新
 * @param param  {paraName:'TYPE_XXXX' , paraValue:'111'}
 * 
 */
function getAddOrUpdateParam(param){
	var url = contextPath + "/sysPara/addOrUpdateSysPara.action";
	var jsonRs = tools.requestJsonRs(url,param);
	if(jsonRs.rtState){
		top.$.jBox.tip(jsonRs.rtMsg,'info' , {timeout:1500});
	}else{
		alert(jsonRs.rtMsg);
	}
}


/**
 * 根据人员Id  获取人员信息
 * 
 * @param uuid   支持获取多个人员 ， 以逗号分隔
 * @returns 返回Map   属性为sid和userName
 */
function getPersonNameAndUuidByUuids(uuid){
	var url =   contextPath + "/personManager/getPersonNameAndUuidByUuids.action";
	var para = {uuid:uuid};
	var jsonObj = tools.requestJsonRs(url ,para);
	if(jsonObj.rtState){
		return jsonObj.rtData;
	}else{
		alert(jsonObj.rtMsg);
	}
}


/**
 * 根据人员Id  获取人员信息
 * 
 * @param uuid   支持获取多个人员 ， 以逗号分隔
 * @returns 返回人员数组 对象
 */
function getPersonListByUuids(uuid){
	var url =   contextPath + "/personManager/getPersonListByUuids.action";
	var para = {uuid:uuid};
	var jsonObj = tools.requestJsonRs(url ,para);
	if(jsonObj.rtState){
		return jsonObj.rtData;
	}else{
		alert(jsonObj.rtMsg);
	}
}



/**
 * 根据主类编号  获取子集代码列表
 * 
 * @param codeNo 系统代码编号  主类编码
 * @param codeSelectId 对象Id
 * @returns 返回人员数组 对象 [{codeNo:'' , codeName:''}]
 */
function getSysCodeByParentCodeNo(codeNo , codeSelectId ){
	var url =   contextPath + "/sysCode/getSysCodeByParentCodeNo.action";
	var para = {codeNo:codeNo};
	var jsonObj = tools.requestJsonRs(url ,para);
	if(jsonObj.rtState){
		var prcs = jsonObj.rtData;
		if(codeSelectId && $("#" + codeSelectId)[0]){//存在此对象
			var options = "";
			for ( var i = 0; i < prcs.length; i++) {
				options = options + "<option value='"+prcs[i].codeNo+"'>" + prcs[i].codeName + "</option>";
			}
			$("#" + codeSelectId).append(options);
		}
		return prcs;
	}else{
		alert(jsonObj.rtMsg);
	}
}

/**
 * 根据父编码号和子编码号，获取子编码名称
 * @param parentCodeNo
 * @param codeNo
 * @returns
 */
function getSysCodeNameByParentCodeNo(parentCodeNo , codeNo){
	var url =   contextPath + "/sysCode/getSysCodeNameByParentCodeNo.action";
	var para = {parentCodeNo:parentCodeNo,codeNo:codeNo};
	var jsonObj = tools.requestJsonRs(url ,para);
	if(jsonObj.rtState){
		return jsonObj.rtData;
	}else{
		return "";
	}
}

/**
 * 判断当前模块是否有手机提醒权限，同时判读当前登陆人是否有权限发送手机短信
 * @param selectId 对象Id
 * @param moduleNo 模块编号
 * @param loginUserId 登陆人用户id
 */
function showPhoneSmsForModule(selectId,moduleNo,loginUserId){
	var url = contextPath +"/TeeSmsPhonePrivController/getPhonePriv.action";
	var jsonRs = tools.requestJsonRs(url);
	if(jsonRs.rtState){
		var typePriv = jsonRs.rtData.typePriv;
		var remindPriv = jsonRs.rtData.smsRemindPriv;
		if(isExist(typePriv,moduleNo) && isExist(remindPriv,loginUserId)){
			var phoneSms=$("<input>",{
				id:"phoneSms",
				name:"phoneSms",
				type:"checkbox"
			});
			var title=$("<span>").text("手机短信提醒");
			$("#" + selectId).append(phoneSms).append(title);
			return true;
		}
	}
	return false;
}

/**
 * 发送手机短信
 * @param toUserId
 * @param smsContent
 */

function sendPhoneSms(toUserId,smsContent,sendTime){
	var url = contextPath +"/TeeSmsPhonePrivController/getPhonePriv.action";
	var jsonRs = tools.requestJsonRs(url);
	var hasRemindPriv="";
	if(jsonRs.rtState){
		var remindPriv = jsonRs.rtData.remindPriv;//判断是否有被提醒权限
		if(toUserId!=null && toUserId!=""){
			var toUserIds=toUserId.split(",");
			if(toUserIds.length>0){
				for(var i=0;i<toUserIds.length;i++){
					if(isExist(remindPriv,toUserIds[i])){
						hasRemindPriv+=toUserIds[i]+",";
					}
				}
			}
		}
		
	}
	//判断是否选中，发送手机短信
	if($("#phoneSms").attr("checked")=="checked"){
		var sendUrl = contextPath +"/TeeSmsSendPhoneController/sendPhoneSms.action?hasRemindPriv="+hasRemindPriv+"&smsContent="+smsContent+"&sendTime="+sendTime;
		var json = tools.requestJsonRs(encodeURI(sendUrl));
		if(json.rtState){
			alert(json.rtMsg);
		}
	}
}

/**
 * 判断ids中是否存在id
 * @param ids
 * @param id
 * @returns {Boolean}
 */
function isExist(ids,id){
	var strs=ids.split(",");
	for(var i=0;i<strs.length;i++){
		if(strs[i]==id){
			return true;
		}
	}
	return false;
}



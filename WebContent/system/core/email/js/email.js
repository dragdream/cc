
function checkForm(type){//type==0  草稿   type==1 发送
	if(type==1){
		if($("#pubType").val()==0){//指定人员
			if(!$("#userListIds").val() && !$("#externalInput").val()){
				$.MsgBox.Alert_auto("收件人和外部邮箱不能全为空！");
				$("#userListNames").focus();		
				return false;
			}
		}
	}
	
	if(!$("#subject").val()){
		$.MsgBox.Alert_auto("主题不能为空！");
		$("#subject").focus();
		return false;
	}
	return true;
}





/**
 * 根据mail Id阅读 邮件
 * @param sid
 */
function readEmailByMailId(sid,mailType){
	var url = contextPath + "/system/core/email/readEmailByMailId.jsp?sid=" + sid + "&mailType=" + mailType;
	location.href = url;
}
/**
 * 根据mailBody Id阅读 邮件
 * @param sid
 * @param isSendBox 是否为已发送 1-是；其他值否
 */
function readEmailByMailBodyId(sid,isSendBox){
	if(!isSendBox){
		isSendBox = 0;
	}
	var url = contextPath + "/system/core/email/readEmailBody.jsp?sid=" + sid + "&isSendBox=" + isSendBox;
	location.href = url;
}


/**
 * 单个删除维护信息
 */
function deleteSingleFunc(sid){
	deleteObjFunc(sid);
}
/**
 * 批量彻底删除
 */
function batchDestroyMails(){
	var ids = getSelectItem();
	if(ids.length==0){
		$.MsgBox.Alert_auto("未选中任何邮件！");
		return ;
	}
	deleteObjFunc(ids);
}
/**
 * 彻底删除
 */
function deleteObjFunc(ids){
  $.MsgBox.Confirm ("提示", "彻底删除后邮件将无法恢复，您确定要删除吗？", function(){
	  var url = contextPath + "/mail/destroyMails.action";
		var para = {mailIds:ids};
		var json = tools.requestJsonRs(url,para);
		if(json.rtState){					
			$.MsgBox.Alert_auto("删除成功！");
			datagrid.datagrid('reload');
			//刷新左侧列表邮件主界面
			refreshEmailMainFunc();
			
			
		}   
  });

}


/**
 * 批量删除草稿、已发送邮件
 * @param optFlag 1-草稿；2-已发送邮件
 */
function batchDestroyEmailDraftBox(optFlag){
	var ids = getSelectItem();
	if(ids.length==0){
		$.MsgBox.Alert_auto("未选中任何邮件！");
		return ;
	}
	deleteEmailDraftBox(ids,optFlag);
}

/**
 * 彻底删除草稿
 * optFlag 删除标识 1-草稿；2-已发件
 */
function deleteEmailDraftBox(ids,optFlag){
	var text = "删除后草稿将无法恢复，您确定要删除吗？";
	if(optFlag==2){
		text = "删除后邮件将无法恢复，您确定要删除吗？";
	}
	
	$.MsgBox.Confirm ("提示", text, function(){
		var url = contextPath + "/mail/delSingleMailBody.action";
		var para =  {id:ids,value:optFlag};
		var json = tools.requestJsonRs(url,para);
		if(json.rtState){	
			
			$.MsgBox.Alert_auto("已将邮件成功删除！");
			datagrid.datagrid('reload');
			//刷新左侧列表邮件主界面
			refreshEmailMainFunc();
			
		}
	});

}




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
 * 删除邮件（假删除）
 */
function delEmailById(){
	var ids = getSelectItem();
	if(ids.length==0){
		$.MsgBox.Alert_auto("未选中任何邮件！");
		return ;
	}else{
		$.MsgBox.Confirm ("提示", "是否确认删除？", function(){
			var url = contextPath + "/mail/delMail.action";
			var para = {mailIds:ids};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){					
				$.MsgBox.Alert_auto("删除成功！");
				datagrid.datagrid('reload');
				//刷新左侧列表邮件主界面
				refreshEmailMainFunc();
				
			}	
		});
		
	}
}





/**
 * 查看详情 ,根据mail 的sid
 * @param sid
 */
function getEmailInfoById(sid){
	var url = contextPath + "/emailController/getEmailDetailById.action";
	var para = {sid : sid};
	return jsonObj = tools.requestJsonRs(url, para);
	/*var obj = "";
	if (jsonObj.rtState) {
		bj = jsonObj.rtData;
	} else {
		alert(jsonObj.rtMsg);
	}
	return obj;*/
}

/**
 * 查看详情，根据mailBody 的sid 
 * @param sid
 */
function getEmailDetailByMailBodyId(sid){
	var url = contextPath + "/emailController/getEmailDetailByMailBodyId.action";
	var para = {sid : sid};
	return jsonObj = tools.requestJsonRs(url, para);
}
/**
 * 根据mailBody 的sid获取mail的sid
 * @param sid
 * @returns {Number}
 */
function getMailSidByMailBodySid(sid){
	var mailSid = 0;
	if(sid){
		var jsonObj = getEmailDetailByMailBodyId(sid);
		if (jsonObj.rtState) {
			var prc = jsonObj.rtData;
			if(prc){
				mailSid = prc.sid;
			}
		}else{
			$.MsgBox.Alert_auto(jsonObj.rtMsg);
		}
	}
	return mailSid;
}



/**
 * 发送邮件方式
 * @param flag 0-回复;1-回复全部；2-转发；3-编辑
 * @param sid
 * @param isEditFlag 是否为编辑，0-否；1-是(根据mailBody 的sid 草稿-编辑邮件);2-已发邮件再次编辑发送
 */
function sendEmailOpt(flag,sid,isEditFlag){
	var url = contextPath + "/system/core/email/send.jsp?optFlag=" + flag + "&sid=" + sid + "&isEditFlag=" + isEditFlag;
	location.href = url;
}

/**
 * 设置邮件阅读状态
 * @param flag 0-设置为"未阅读"；1-设置为"已阅读"
 */
function setEmailReadFlag(readFlag){
	var ids = getSelectItem();
	if(ids.length==0){
		$.MsgBox.Alert_auto("未选中任何邮件！");
		return ;
	}
	var url = contextPath + "/emailController/setEmailReadFlagById.action";
	var para = {sid:ids,readFlag:readFlag};
	var jsonRs = tools.requestJsonRs(url, para);
	if (jsonRs.rtState) {		
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
		datagrid.datagrid('reload');
	} else {
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
}

/**
 * 根据sid设置邮件阅读状态
 * @sid email的sid 
 * @param flag 0-设置为"未阅读"；1-设置为"已阅读"
 */
function setEmailReadFlagBySid(ids,readFlag){
	var url = contextPath + "/emailController/setEmailReadFlagById.action";
	var para = {sid:ids,readFlag:readFlag};
	var jsonRs = tools.requestJsonRs(url, para);
	if (jsonRs.rtState) {
	} else {
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
}

/**
 * (收邮件/收件箱)全部标记已读
 */
function setAllEmailReadFlag(){
	var url = contextPath + "/emailController/setAllEmailReadFlag.action";
	var jsonRs = tools.requestJsonRs(url);
	if (jsonRs.rtState) {
		var optFlag = jsonRs.rtData.optFlag;
		
		if(optFlag ==1){			
			$.MsgBox.Alert_auto("已标记为已读邮件！");
			datagrid.datagrid('reload');
		}else{
			$.MsgBox.Alert_auto("文件夹内没有未读邮件！");
		}
	} else {
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
}

/**
 * (自定义邮箱)全部标记已读
 */
function setAllEmailReadFlagCustom(mailBoxSid){
	var url = contextPath + "/emailController/setAllEmailReadFlagCustom.action";
	var jsonRs = tools.requestJsonRs(url,{mailBoxSid:mailBoxSid});
	if (jsonRs.rtState){
		var optFlag = jsonRs.rtData.optFlag;
		if(optFlag ==1){			
			$.MsgBox.Alert_auto("已标记为已读邮件！");
			datagrid.datagrid('reload');
		}else{
			$.MsgBox.Alert_auto("文件夹内没有未读邮件！");
		}
	} else {
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
}


/**
 * 获取文件夹分类
 */
function getEmailBoxList(){
	var url = contextPath + "/emailController/getEmailBoxList.action";
	var jsonRs = tools.requestJsonRs(url);
	if (jsonRs.rtState) {
		var prc = jsonRs.rtData;
		var ulStr = "";
		var mailBoxList = eval(prc.mailBoxList);
		jQuery.each(mailBoxList,function(i,prcs){
			var boxName = prcs.boxName;
			var mailCount = prcs.mailCount;
			var mailBoxSId = prcs.sid;
			
			if(boxName.length>10){
				boxName = boxName.substring(0,10) + "……";
			}
			
			ulStr  += "<li onclick='moveMailFunc(" + mailBoxSId + ")'><a href='javascript:void(0);' >" + boxName + "</a></li>";
		});
		ulStr  += "<li class='divider'></li>";
		ulStr  += "<li onclick='$(this).modal();' class='modal-menu-test'><a href='javascript:void(0);'  >新建邮件箱</a></li>";
		
		$("#mailBoxList").append(ulStr);
	} else {
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
}


/**
 * 新建邮件箱
 */
function newFolderFunc(){
  createFolderFunc("新建邮件箱");
  $("#boxInput").focus();
}
/*更新邮件箱
 * 
 */ 
function updateFolderFunc(sid){
	/*createFolderFunc("编辑邮件箱");*/
	$("#boxInput").focus();
	var url = contextPath + "/mailBoxController/getEmailBoxInfoById.action";
	var jsonRs = tools.requestJsonRs(url,{sid:sid});
	if (jsonRs.rtState) {
		var prc = jsonRs.rtData;
		$("#boxInput").val(prc.boxName);
		$("#boxNo").val(prc.boxNo);
		$("#sid").val(prc.sid);
	}
}
/**
 * 删除邮件箱
 * @param sid
 */
function deleteEmailBox(sid){
	$.MsgBox.Confirm ("提示", "确认删除此邮件箱，删除后将不可恢复？", function(){
		var url = contextPath + "/mailBoxController/deleEmailBoxById.action";
		var jsonRs = tools.requestJsonRs(url,{sid:sid});
		if (jsonRs.rtState) {
			var prc = jsonRs.rtData;			
			
			$.MsgBox.Alert_auto("删除成功！");
			window.location.reload();
			//刷新左侧列表邮件主界面
			refreshEmailMainFunc();
			
		}else{
			$.MsgBox.Alert_auto("删除错误！");
		}
	});
	
}

/* 新建文件夹 */
function createFolderFunc(titleStr){
	$("#myModal").modal("show");
//  var html = "<br><form method='post' name='mailBoxForm' id='mailBoxForm'><table class='TableBlock' width='80%' align='center'>"
//           +   "<tr class='TableLine2'>"
//           +     "<td>邮件箱名称</td>"
//           +     "<td><input type='text' name='boxInput' id='boxInput' class='easyui-validatebox BigInput' size='35' maxlength='100'></td>"
//        
//           +   "</tr>"
//           +   "<tr class='TableLine2'>"
//           +     "<td>序号</td>"
//           +     "<td><input type='text' name='boxNo' id='boxNo' class='easyui-validatebox BigInput' size='35' maxlength='8'></td>"
//           +   "</tr>"
//           +   "<tr class='TableControl'>"
//           +     "<td colspan='2' align='center'>"
//           +   "<input type='hidden' name='sid' id='sid'>"
//           +       "<input type='button' value='保存' class='btn btn-primary' onclick='submitNewFolder();'>&nbsp;&nbsp;"
//           +       "<input type='button' value='关闭' class='btn btn-primary' onclick='javascript:$.jBox.close();'>&nbsp;&nbsp;"
//           +     "</td>"
//           +   "</tr>"
//           + "</table></form>";
//  
//  $.jBox(html, { title: titleStr, width:500,height:200,buttons:{} });
}


/**
 * 提交新建文件夹信息
 * @param sid
 */
function submitNewFolder(sid){
  if(!$("#boxInput").val()){
	$.MsgBox.Alert_auto("请输入名称！");
	$("#boxInput").focus();
    return false;
  }
  if(isValidateFilePath($("#boxInput").val())){
	$.MsgBox.Alert_auto("名称不能包含有以下字符/\:*<>?\"|");
	$("#boxInput").focus();
    return false;
  }
  if(!$("#boxNo").val()){
	    $.MsgBox.Alert("提示", "请输入序号，且为整数类型！",function(){
	    	$("#boxNo").focus();
	    });
	    return false;
   }
  var integeZero = /^[-+]?[1-9][0-9]*$/;
  if(!integeZero.test($("#boxNo").val())){
	    $.MsgBox.Alert_auto("请输入序号，且为整数类型！");
	    $("#boxNo").focus();	    		    
	    return false;
 }
  
  var url = contextPath + "/mail/saveOrUpdateMailBox.action";
  var para =  tools.formToJson($("#mailBoxForm"));
  var jsonRs = tools.requestJsonRs(url,para);
  if(jsonRs.rtState){
    var prc = jsonRs.rtData; 
    $.MsgBox.Alert_auto("保存成功！");
    refreshEmailMainFunc();//刷新主界面  
    window.location.reload();
  }else{
    $.MsgBox.Alert_auto(jsonRs.rtMsg);
  }
}

/**
 * 移动邮件
 * @param boxId
 */
function moveMailFunc(boxId){
	var ids = getSelectItem();
	if(ids.length==0){
	    $.MsgBox.Alert_auto("未选中任何邮件！");
		return ;
	}
	$.MsgBox.Confirm ("提示", "是否确认移动邮件？", function(){
		var url = contextPath + "/mail/moveMail.action";
		var para = {mailIds:ids,boxId:boxId};
		var jsonRs = tools.requestJsonRs(url, para);
		if(jsonRs.rtState){
			var prc = jsonRs.rtData;		   
			
			$.MsgBox.Alert_auto("已将邮件成功移动！");
				//window.location.reload();
				datagrid.datagrid('reload');
				//刷新左侧列表邮件主界面
				refreshEmailMainFunc();
			
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
	
	});

}


/**
 * 批量删除草稿、已发送邮件
 * @param optFlag 1-草稿；2-已发送邮件
 */
function batchMoveToReceive(){
	var ids = getSelectItem();
	if(ids.length==0){
		$.MsgBox.Alert_auto("未选中任何邮件！");
		return ;
	}
	moveToReceive(ids);
}

/**
 * 根据mail id将邮件移动至收件箱
 * @param sid
 */
function moveToReceive(sid){
	if(sid){
		var url = contextPath + "/mail/moveReceive.action";
		var para = {mailIds:sid};
		var jsonRs = tools.requestJsonRs(url, para);
		if(jsonRs.rtState){	
			
			$.MsgBox.Alert_auto("已将邮件成功移动！");
				//window.location.reload();
				datagrid.datagrid('reload');
				//刷新左侧列表邮件主界面
				refreshEmailMainFunc();
			
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
	}
}



/**
 * 获取自定义邮箱信息 ,根据mailBox 的sid
 * @param sid
 */
function getEmailBoxInfoById(sid){
	var url = contextPath + "/mailBoxController/getEmailBoxInfoById.action";
	var para = {sid : sid};
	return jsonObj = tools.requestJsonRs(url, para);
	/*var obj = "";
	if (jsonObj.rtState) {
		bj = jsonObj.rtData;
	} else {
		alert(jsonObj.rtMsg);
	}
	return obj;*/
}


/**
 * 设置外部邮箱
 * @param 
 */
function setWebMail(){
	
	var url = contextPath + "/system/core/email/webMailManage.jsp";
	//location.href = url;
	document.getElementById("frame0").src =url;
	
	return;
	var url = contextPath + "/mail/setWebMailIndex.action";
	
 	top.bsWindow(url ,"设置外部邮箱",{width:"600",height:"320",buttons:
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
 * 管理邮件箱
 */
function setEmail(){
	var url = contextPath + "/system/core/email/setEmailManager.jsp";
	document.getElementById("frame0").src =url;
}

//刷新左侧列表邮件主界面
function refreshEmailMainFunc(){
	if(parent.refreshEmailMain){
		parent.refreshEmailMain();
	}
	
}



/**
 * 检查用户是否存爱
 */
function check_user(value)
{
	if(value=="")
	{
		return;	
	}	     
	$("#user_id_msg").html("<img src='" +stylePath + "/imgs/loading_16.gif' align='absMiddle'> 检查中，请稍候……");
	chekUser(value);
}
function chekUser(value){
	var uuid = $("#uuid").val();
	var url = contextPath +  "/personManager/checkUserExist.action";
	var para = {uuid:uuid,userId : value};
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		//return jsonRs.rtState;
		$("#user_id_msg").html("<img src='" + stylePath + "/imgs/correct.gif' align='absMiddle'>");
	}else{
		//alert(jsonRs.rtMsg);
		 $("#user_id_msg").html("<img src='" + stylePath + "/imgs/error.gif' align='absMiddle'> 该用户名已存在");
		 document.form1.userId.focus();
	}
}
/**
 * 获取角色列表
 * @param userRoleStr 表单属性
 * @param  privOp  角色权限控制  1-权限控制，低角色  其它不空，获取所有角色列表
 */
function selectUserPrivList(userRoleStr,privOp){
	if(!privOp){
		privOp = "";
	}
	var url = contextPath +  "/userRoleController/selectUserPrivList.action";
	var para = {privOp:privOp};
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		var dataList = jsonRs.rtData;
		var selects = document.getElementById(userRoleStr);
		for(var i = 0; i < dataList.length; i++){
		    var prc = dataList[i];
		    var option = document.createElement("option"); 
		    option.value = prc.uuid; 
		    option.innerHTML = prc.roleName; 
		   /* if(prc.value == ){
		      option.selected = true;
		    }*/
		    selects.appendChild(option);
		 }
	}else{
		alert(jsonRs.rtMsg);
	}
}
/**
 * 控制辅助角色显示
 */
function select_priv(){
	if($("#priv").is(":hidden")){
		$("#priv").show();
	}else{
		$("#priv").hide();
	}
}
/**
 * 控制辅助部门显示
 */
function selectDeptOther(){
	if($("#dept_other").is(":hidden")){
		$("#dept_other").show();
	}else{
		$("#dept_other").hide();
	}
}
/**
 * 控制管理范围
 */
function selectPostDept(){
	var postPriv = $("#postPriv").val();
	if(postPriv == '2'){
		$("#post_dept").show();
	}else{
		$("#post_dept").hide();
	}
}

/**
 * 弹出新增或者编辑人员界面
 * @param personUuid
 */
function toAddUpdatePerson(personUuid){
	var url = contextPath +  "/system/core/person/addupdate.jsp?uuid=" + personUuid ;
	//openWindow(url,"addupdatePerson",870,540);
	window.parent.changePage(url);
}
/**
 * 清空密码
 * @param uuid
 */
function clearPassword(uuid){
	if(confirm("确定要清空密码？")){
		var url = contextPath +  "/personManager/clearPassword.action?uuids=" + uuid;
		var para = {};
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
		//	alert(jsonRs.rtMsg);
			$.messager.show({
				msg : '清除密码成功！',
				title : '提示'
			});
			doQuery();
		}else{
			alert(jsonRs.rtMsg);
		}
	}
}


/**
 * 获取密码规则并设置
 * @param pass1 ： 第一个密码
 * @param pass2 ：第二个密码
 */
function getCheckPassPara(pass1,pass2){
	var url = contextPath + "/sysPara/getSysParaList.action";
	var paraNames = "SEC_PASS_MIN,SEC_PASS_MAX,SEC_PASS_SAFE,SEC_PASS_SAFE_SC";
	var para =  {paraNames:paraNames} ;
	var SEC_PASS_MIN = "";
	var SEC_PASS_MAX = "";
	var SEC_PASS_SAFE = "0";
	var SEC_PASS_SAFE_SC = "0";
	var passMessage = "";
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		var dataList = jsonRs.rtData;
		for(var i = 0;i<dataList.length;i++){
			var data = dataList[i]; 
			if(data.paraName == 'SEC_PASS_MIN' && data.paraValue != ""){
				SEC_PASS_MIN = data.paraValue;
			}else if(data.paraName == 'SEC_PASS_MAX' && data.paraValue != ""){
				SEC_PASS_MAX = data.paraValue;
			}else if(data.paraName == 'SEC_PASS_SAFE'){
				SEC_PASS_SAFE = data.paraValue;
			}else if(data.paraName == 'SEC_PASS_SAFE_SC'){
				SEC_PASS_SAFE_SC = data.paraValue;
			}
		}
	}else{
		alert(jsonRs.rtMsg);
		return;
		
	}
	passMessage = SEC_PASS_MIN + "-" + SEC_PASS_MAX + "位" ;
	if(SEC_PASS_MIN == "" && SEC_PASS_MAX == "" ){
		passMessage = "无限制";
	}
	
	var validTypeStr = "length["+SEC_PASS_MIN+"," + SEC_PASS_MAX +  "]";
	if(SEC_PASS_SAFE == '1' ){
		passMessage = passMessage + ",必须同时包含字母和数字 ";
		validTypeStr = validTypeStr + "&letter_and_intege[]";
	}
	if(SEC_PASS_SAFE_SC == '1' ){
		passMessage = passMessage + ",必须同时包含特殊字符串 ";
		validTypeStr = validTypeStr + "&special_character[]";
	}
	
	$("#" + pass1).after( "<span>&nbsp;" +passMessage  + "</span>");
	$("#" + pass2).after( "<span>&nbsp;" +passMessage  + "</span>");
	//if(SEC_PASS_MIN != '0' ||  SEC_PASS_MIN != '' || SEC_PASS_MAX != '' ||  SEC_PASS_MAX != '0'){
	if (SEC_PASS_MIN == "" && SEC_PASS_MAX == ""){//为控制
		$('#' + pass2).validatebox({ 
			required:false ,
			validType: "equalTo[$('#"+pass1+"')]"
		}); 
	}else if (SEC_PASS_MIN != '0'  ||  SEC_PASS_MAX != '0'){
		$('#' + pass1).validatebox({ 
			required:true ,
			validType:validTypeStr
		});   
		$('#' + pass2).validatebox({ 
			required:true ,
			validType:validTypeStr + "&equalTo[$('#"+pass1+"')]"
		}); 
	}else{
		if(SEC_PASS_MIN == '0'){//等于0
			$('#' + pass1).validatebox({ 
				validType:validTypeStr
			}); 
			$('#' + pass2).validatebox({ 
				validType:validTypeStr
			}); 
		}
		
		$('#' + pass2).validatebox({ 
			required:false ,
			validType: "equalTo[$('#"+pass1+"')]"
		}); 
	}

}
/**
 * 按模块权限
 */
function module_priv(){
	var personUuid  = $("#uuid").val();
	var userName  = $("#userName").val();
	var url = contextPath +  "/system/core/person/modulepriv/index.jsp?personUuid=" + personUuid + "&personName=" + encodeURIComponent(userName) ;
	/*openWindow(url,"setmodulpriv",600,440);
	
	var url = contextPath + "/system/core/base/attend/out/detail.jsp?id=" + id;*/
	top.bsWindow(url ,"按模块设置权限",{width:"800",height:"400",buttons:
		[
		/* {name:"保存",classStyle:"btn btn-primary"}	,
	 	 {name:"关闭",classStyle:"btn btn-primary"}*/
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			return true;
		}else if(v=="关闭"){
			return true;
		}
	}});
}



/**
 * 根据人员Id获取人员信息
 * @param personId
 * @returns
 */
function getPersonInfo(personId){
	var url = contextPath + "/personManager/getPersonById.action";
	var para = {uuid:personId};
	var jsonObj = tools.requestJsonRs(url,para);
	if(jsonObj.rtState){
		return jsonObj.rtData;
	}else{
		alert(jsonObj.rtMsg);
	}
	
}
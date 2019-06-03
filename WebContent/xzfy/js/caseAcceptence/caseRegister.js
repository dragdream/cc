//案件详情接口类型
var type ="0";
function doInit(){
	//案件ID
	var caseId = getQueryString("caseId");
	//请求参数
    var param = {
    	caseId:caseId,
    	type:type
    };
	var json = tools.requestJsonRs(CASEINFO_URL,param);
	if( json.rtState == true ){
		//获取登记信息返回数据
		var data = json.rtData;
		//案件类型(来件/接待)
    	var applicationType = data.applicationTypeCode;
    	
    	$("input").attr("disabled", "disabled");
		$("textarea").attr("disabled", "disabled");
		$("select").attr("disabled", "disabled");
    	
    	//来件  01
		if( applicationType == "01"){
			$("#reception").hide();
			$("#letter").show();
			//绑定数据
			bindJsonObj2Cntrl(data.letter);
		}
		//接待
		else{
			$("#reception").show();
			$("#letter").hide();
			//绑定数据
			bindJsonObj2Cntrl(data.reception);
			//接待数据封装
			receptionData(data.reception);
		}
		//当事人信息封装
		clientInfoData(data.clientInfo);
		//复议事项数据封装
		bindJsonObj2Cntrl(data.caseHandling);
		mattersData(data.caseHandling);
		
	}
	else{
		$.MsgBox.Alert_auto("查询失败,请联系管理员！");
	}
}

//接待数据封装
function receptionData(data){
    //是否接受材料
	$("#isReceivingMaterial").val(data.isReceivingMaterial == 1?'是':'否');
	//被接待人信息 
	var list = data.visitorVo;
	var val = "";
	if( list!=null){
		for(var i=0;i<list.length;i++){
			var it = list[i];
			val = val + "姓名："+it.visitorName+",证件类型："+it.cardType+",证件号："+it.cardNum+",性别："+it.sexCode+",联系电话："+it.postCode+";";
		}
		$("#visitorVo").val(val);
	}
	
	//材料信息
	list = data.listFyMaterial;
	val = "";
	if( list != null){
		for(var i=0;i<list.length;i++){
			var it = list[i];
			val = val + "材料名称："+it.fileName+",材料类型："+it.caseTypeCode+",份数："+it.copyNum+",张数："+it.pageNum+";";
		}
		$("#listFyMaterial").val(val);
	}
	
	
}
//来件数据封装
function letterData(data){
	
}

//当事人数据封装
function clientInfoData(data){
	//
	$("#applicantType").val(data.applicant[0].applicantType);
	var total = data.applicant.length + data.otherApplicant.length;
	$("#total").val(total);
	$("#isAgent").val( data.isAgent == 1 ?'是':'否');
	$("#isRespondentAgent").val( data.isThirdParty == 1 ?'是':'否');
	$("#isThirdParty").val( data.isThirdParty == 1 ?'是':'否');
	$("#isThirdPartyAgent").val( data.isThirdPartyAgent == 1 ?'是':'否');
	
	//申请人列表
	var list = data.applicant;
	var val = "";
	if( list!=null){
		for(var i=0;i<list.length;i++){
			var it = list[i];
			val = val + "姓名："+it.name+",证件类型："+it.certificateType+",证件号："+it.certificateId+",性别："+it.sexCode 
			    + ",民族："+ it.nationCode + ",送达地址："+ it.postAddress+",邮编："+it.postCode+",联系电话："+it.phoneNum+";";
		}
		$("#applicant").val(val);
	}
	
	//其他申请人
	list = data.otherApplicant;
	val = "";
	if( list != null){
		for(var i=0;i<list.length;i++){
			var it = list[i];
			val = val + "姓名："+it.name+",性别："+it.sexCode+",证件类型："+it.certificateType+",证件号："+it.certificateId+";";
		}
		$("#otherApplicant").val(val);
	}
	
	//申请人代理人
	list = data.applicantAgent;
	val = "";
	if( list != null){
		for(var i=0;i<list.length;i++){
			var it = list[i];
			val = val + "代理人类型："+it.agentTypeCode+",代理人姓名："+it.agentName+",证件类型："+it.cardType+",证件号："+it.idCard
			    +",联系电话：" +it.phone+",是否授权书："+it.isAuthorization+",被代理人："+it.agentParent+";";
		}
		$("#applicantAgent").val(val);
	}
	
	//被申请人
	list = data.respondent;
	val = "";
	if( list != null){
		for(var i=0;i<list.length;i++){
			var it = list[i];
			val = val + "被申请人类型："+it.respondentType+",被申请人姓名："+it.respondentName+";";
		}
		$("#respondent").val(val);
	}
	
	//被申请人代理人
	list = data.respondentAgent;
	val = "";
	if( list != null){
		for(var i=0;i<list.length;i++){
			var it = list[i];
			val = val + "代理人类型："+it.agentTypeCode+",代理人姓名："+it.agentName+",证件类型："+it.cardType+",证件号："+it.idCard
			    +",联系电话：" +it.phone+",是否授权书："+it.isAuthorization+",被代理人："+it.agentParent+";";
		}
		$("#respondentAgent").val(val);
	}
	
	//第三人
	list = data.thirdParty;
	val = "";
	if( list!=null){
		for(var i=0;i<list.length;i++){
			var it = list[i];
			val = val + "姓名："+it.name+",证件类型："+it.certificateTypeCode+",证件号："+it.ceritificateId+",性别："+it.sexCode 
			    + ",民族："+ it.nationCode + ",送达地址："+ it.postAddress+",邮编："+it.postCode+",联系电话："+it.phoneNum+";";
		}
		$("#thirdParty").val(val);
	}
	
	//第三人代理人
	list = data.thirdPartyAgent;
	val = "";
	if( list != null){
		for(var i=0;i<list.length;i++){
			var it = list[i];
			val = val + "代理人类型："+it.agentTypeCode+",代理人姓名："+it.agentName+",证件类型："+it.cardType+",证件号："+it.idCard
			    +",联系电话：" +it.phone+",是否授权书："+it.isAuthorization+",被代理人："+it.agentParent+";";
		}
		$("#thirdPartyAgent").val(val);
	}
}

//复议事项数据封装
function mattersData(data){
	
	$("#isReview").val( data.isReview == 1 ?'是':'否');
	$("#isNonfeasance").val( data.isNonfeasance == 1 ?'是':'否');
	$("#isHoldHearing").val( data.isHoldHearing == 1 ?'是':'否');
	$("#isCompensation").val( data.isCompensation == 1 ?'是':'否');
	$("#isDocumentReview").val( data.isDocumentReview == 1 ?'是':'否');
	
	var val = data.isNonfeasance;
	if( val ==1){
		$("#isNonfeasanceAtra").show();
		$("#isNonfeasanceAtrb").show();
		$("#isNonfeasanceBtr").hide();
	}
	else {
		$("#isNonfeasanceAtra").hide();
		$("#isNonfeasanceAtrb").hide();
		$("#isNonfeasanceBtr").show();
	}
}

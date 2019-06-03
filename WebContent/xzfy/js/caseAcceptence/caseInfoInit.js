
// 当事人- 证件类型
var certificateTypeJson = "";
// 当事人- 性别
var sexJson = "";
// 当事人- 民族
var nationJson = "";
// 当事人-代理人类型
var agentJson = "";
// 当事人-被申请人类型
var respondentTypeJson = "";

//案件详情接口类型
var type ="1";

//案件ID
var caseId="";

//案件信息-div
function doInit(){
	
	caseId = getQueryString("caseId");

  	//请求参数
    var param = {
      	caseId:caseId,
      	type:type
    };
  	var json = tools.requestJsonRs(CASEINFO_URL,param);
  	if( json.rtState == true ){
  		//获取案件信息返回数据
  		var data = json.rtData;
  		//案件状态
        caseStatus = data.caseHandling[0].caseStatusCode;
  		
     	// 初始化下拉框
  		initCaseOptions(data);
  		
  		//当事人信息
  		clientInfoData(data.clientInfo);
  		
  		//绑定复议事项数据
        bindJsonObj2Cntrl(data.caseHandling[0]);
  		//复议事项
        mattersData(data.caseHandling[0]);
  		
  		//更新index
  		updateIndex();

 		// 案件审理
//  		if(caseStatus == "03" ){
//  			$("#case-div").append(juicer(case_c_tpl, data));
//  		} 
//  		// 案件结案 ,归档,归档完成
//		else if(caseStatus == "04" ||
//				caseStatus == "05" ||
//  				caseStatus == "06" ){
//  			$("#case-div").append(juicer(case_c_tpl, data));
//  			$("#case-div").append(juicer(case_d_tpl, data));
//  		}
  	}
  	else{
  		$.MsgBox.Alert_auto("查询失败,请联系管理员！");
  	}
}



//初始化下拉选择框
function initCaseOptions(data){
	
	//当事人-申请人类型
	var url = DICT_URL;
	var param = {type:"APPLICANT_TYPE_CODE"};
	var json = tools.requestJsonRs(url,param);
	var html = createSelectHtml(json,data.clientInfo.applicantTypeCode);
	$("#applicantType").html(html);
	
	// 当事人- 证件类型
	param = {type:"CERTIFICATE_TYPE_CODE"};
	certificateTypeJson = tools.requestJsonRs(url,param);

	// 当事人- 性别
	param = {type:"SEX_CODE"};
	sexJson = tools.requestJsonRs(url,param);

	// 当事人- 民族
	param = {type:"NATION_CODE"};
	nationJson = tools.requestJsonRs(url,param);

	// 当事人-代理人类型
	param = {type:"AGENT_TYPE"};
	agentJson = tools.requestJsonRs(url,param);
	
	// 当事人-被申请人类型
	param = {type:"RESPONDENT_TYPE_CODE"};
	respondentTypeJson = tools.requestJsonRs(url,param);
	
	
	//复议事项-行政类别管理
	param = {type:"CATEGORY_CODE"};
	json = tools.requestJsonRs(url,param);
	html = createSelectHtml(json,data.caseHandling[0].categoryCode);
	$("#category").html(html);
	
	//复议事项-申请事项
	param = {type:"APPLICATION_ITEM_CODE"};
	json = tools.requestJsonRs(url,param);
	html = createSelectHtml(json,data.caseHandling[0].applicationItemCode);
	$("#applicationItem").html(html);
	
	//复议事项-赔偿类型
	param = {type:"COMPENSATION_TYPE_CODE"};
	json = tools.requestJsonRs(url,param);
	html = createSelectHtml(json,data.caseHandling[0].compensationTypeCode);
	$("#compensationTypeCode").html(html);
	
	//不作为事项
	param = {type:"NO_NFEASANCE_CODE"};
	json = tools.requestJsonRs(url,param);
	html = createSelectHtml(json,data.caseHandling[0].nonfeasanceItemCode);
	$("#nonfeasanceItem").html(html);
}

//当事人设置选中
function clientInfoData(data){
	//申请人代理人
	var isAgentVal = data.isAgent == 1 ?'isAgentA':'isAgentB';
	$("#"+isAgentVal).prop("checked","checked");
	agent(data.isAgent);
	//被申请人代理人
	var isRespondentVal = data.isRespondentAgent == 1 ?'isRespondentA':'isRespondentB';
	$("#"+isRespondentVal).prop("checked","checked");
	respondentA(data.isRespondentAgent);
	// 第三人
	var isThirdVal = data.isThirdParty == 1 ?'isThirdA':'isThirdB';
	$("#"+isThirdVal).prop("checked","checked");
	third(data.isThirdParty);
	// 第三人代理人
	var isThirdAgentVal = data.isThirdPartyAgent == 1 ?'isThirdAgentA':'isThirdAgentB';
	$("#"+isThirdAgentVal).prop("checked","checked");
	thirdAgent(data.isThirdPartyAgent);
	
	//当事人数据填充
	clientInfoPeople(data);
}
//当事人数据填充
function clientInfoPeople(data){
	//申请人
	var list = data.applicant;
	var object = $("#applicant-table").children("tbody");
	for(var i=0;i<list.length;i++){
    	$("#applicant-table").append('<tr class="edit-table-tr">' +
   	        '<td class="indexTd" data-value="'+list[i].id+'"></td>' +
   	        '<td class=""><input type="text" name="applicant.name" value="'+list[i].name+'"/></td>' +
   	        '<td class=""><select class="certificateType" name="applicant.certificateTypeCode">' + createSelectHtml(certificateTypeJson,list[i].certificateTypeCode) + '</select></td>' +
   	        '<td class=""><input type="text" name="applicant.certificateId" value="'+list[i].certificateId+'"/></td>' +
   	        '<td class=""><select class="sex" name="applicant.sexCode">'+ createSelectHtml(sexJson,list[i].sexCode) +'</select></td>' +
   	        '<td class=""><select class="nation" name="applicant.nationCode">'+ createSelectHtml(nationJson,list[i].nationCode) +'</select></td>' +
   	        '<td class=""><input type="text" name="applicant.postAddress" value="'+list[i].postAddress+'"/></td>' +
   	        '<td class=""><input type="text" name="applicant.postCode" value="'+list[i].postCode+'"/></td>' +
   	        '<td class=""><input type="text" name="applicant.phoneNum" value="'+list[i].phoneNum+'"/></td>' +
   	        //'<td class=""><a><input id="deleteUL" class="deleteBtn" type="button" value="删除" title="删除" onclick="deleteApplicantRow(this);" /></a></td>' +
   	        '<td class="funcTd"><i class="iconfont icon-shanchu" onclick="deleteApplicantRow(this);"></i></td>' +
   	        '</tr>'
   	    );
	}
	//其他申请人
	list = data.otherApplicant;
	object = $("#otherApplicant-table").children("tbody");
	for(var i=0;i<list.length;i++){
		$("#otherApplicant-table").append('<tr class="edit-table-tr">' +
        '<td class="indexTd" data-value="'+list[i].id+'"></td>' +
        '<td class=""><input type="text" value="'+list[i].name+'"/></td>' +
        '<td class=""><select class="sex">'+ createSelectHtml(sexJson,list[i].sexCode) +'</select></td>' +
        '<td class=""><select class="certificateType">' + createSelectHtml(certificateTypeJson,list[i].certificateTypeCode) + '</select></td>' +
        '<td class=""><input type="text" value="'+list[i].certificateId+'"/></td>' +
        //'<td class=""><a><input id="deleteUL" class="deleteBtn" type="button" value="删除" title="删除" onclick="deleteOtherRow(this);" /></a></td>' +
        '<td class="funcTd"><i class="iconfont icon-shanchu" onclick="deleteOtherRow(this);"></i></td>' +
		'</tr>');
	}	
	//申请人代理人
	list = data.applicantAgent;
	object = $("#applicantAgent-table").children("tbody");
	for(var i=0;i<list.length;i++){
		$("#applicantAgent-table").append('<tr class="edit-table-tr">' +
	    '<td class="indexTd" data-value="'+list[i].id+'"></td>' +
	    '<td class=""><select class="agent">' + createSelectHtml(agentJson,list[i].agentTypeCode) + '</select></td>' +
	    '<td class=""><input type="text" value="'+list[i].agentName+'"/></td>' +
	    '<td class=""><select class="certificateType">' + createSelectHtml(certificateTypeJson,list[i].cardTypeCode) + '</select></td>' +
	    '<td class=""><input type="text" value="'+list[i].idCard+'"/></td>' +
	    '<td class=""><input type="text" value="'+list[i].phone+'"/></td>' +
	    '<td class=""><select class="">'+
	          '<option value="1" '+ (list[i].isAuthorization==1?'selected ="selected"':'') +'>是</option>' +
		      '<option value="0" '+ (list[i].isAuthorization==0?'selected ="selected"':'') +'>否</option></select></td>' +
		  '<td class="">' +
		      '<select class="respondentchoose" onclick="chooseApplicant(this);"><option>'+list[i].agentParent+'</option></select></td>' +
	    //'<td class=""><a><input id="deleteUL" class="deleteBtn" type="button" value="删除" title="删除" onclick="deleteApplicantAgentRow(this);" /></a></td>' +
		      '<td class="funcTd"><i class="iconfont icon-shanchu" onclick="deleteApplicantAgentRow(this);"></i></td>' +
		'</tr>');
	}
    
	//被申请人
	list = data.respondent;
	object = $("#respondent-table").children("tbody");
	for(var i=0;i<list.length;i++){
	    $("#respondent-table").append('<tr class="edit-table-tr">' +
	    '<td class="indexTd" data-value="'+list[i].id+'"></td>' +
	    '<td class=""><select class="respondentType">' + createSelectHtml(respondentTypeJson,list[i].respondentTypeCode) + '</select></td>' +
	    '<td class=""><input type="text" value="'+list[i].respondentName+'"/></td>' +
	    //'<td class=""><a><input id="deleteUL" class="deleteBtn" type="button" value="删除" title="删除" onclick="deleteRespondentRow(this);" /></a></td>' +
	    '<td class="funcTd"><i class="iconfont icon-shanchu" onclick="deleteRespondentRow(this);"></i></td>' +
	    '</tr>');
	}
	//被申请人代理人
	list = data.respondentAgent;
	object = $("#respondentAgent-table").children("tbody");
	for(var i=0;i<list.length;i++){
		$("#respondentAgent-table").append('<tr class="edit-table-tr">' +
	    '<td class="indexTd" data-value="'+list[i].id+'"></td>' +
	    '<td class=""><select class="agent">' + createSelectHtml(agentJson,list[i].agentTypeCode) + '</select></td>' +
	    '<td class=""><input type="text" value="'+list[i].agentName+'"/></td>' +
	    '<td class=""><select class="certificateType">' + createSelectHtml(certificateTypeJson,list[i].cardTypeCode) + '</select></td>' +
	    '<td class=""><input type="text" value="'+list[i].idCard+'"/></td>' +
	    '<td class=""><input type="text" value="'+list[i].phone+'"/></td>' +
	    '<td class=""><select class="">'+
	          '<option value="1" '+ (list[i].isAuthorization==1?'selected ="selected"':'') +'>是</option>' +
		      '<option value="0" '+ (list[i].isAuthorization==0?'selected ="selected"':'') +'>否</option></select></td>' +
		  '<td class="">' +
		      '<select class="respondentchoose" onclick="chooseRespondent(this);"><option>'+list[i].agentParent+'</option></select></td>' +
	    //'<td class=""><a><input id="deleteUL" class="deleteBtn" type="button" value="删除" title="删除" onclick="deleteRespondentAgentRow(this);" /></a></td>' +
		'<td class="funcTd"><i class="iconfont icon-shanchu" onclick="deleteRespondentAgentRow(this);"></i></td>' +
		'</tr>');
	}
	
	//第三人
	list = data.thirdParty;
	object = $("#third-table").children("tbody");
	for(var i=0;i<list.length;i++){
    	$("#third-table").append('<tr class="edit-table-tr">' +
   	        '<td class="indexTd" data-value="'+list[i].id+'"></td>' +
   	        '<td class=""><input type="text" name="applicant.name" value="'+list[i].thirdPartyName+'"/></td>' +
   	        '<td class=""><select class="certificateType" name="applicant.certificateTypeCode">' + createSelectHtml(certificateTypeJson,list[i].certificateTypeCode) + '</select></td>' +
   	        '<td class=""><input type="text" name="applicant.ceritificateId" value="'+list[i].certificateId+'"/></td>' +
   	        '<td class=""><select class="sex" name="applicant.sexCode">'+ createSelectHtml(sexJson,list[i].sexCode) +'</select></td>' +
   	        '<td class=""><select class="nation" name="applicant.nationCode">'+ createSelectHtml(nationJson,list[i].nationCode) +'</select></td>' +
   	        '<td class=""><input type="text" name="applicant.postAddress" value="'+list[i].postAddress+'"/></td>' +
   	        '<td class=""><input type="text" name="applicant.postCode" value="'+list[i].postcode+'"/></td>' +
   	        '<td class=""><input type="text" name="applicant.phoneNum" value="'+list[i].phoneNum+'"/></td>' +
   	        //'<td class=""><a><input id="deleteUL" class="deleteBtn" type="button" value="删除" title="删除" onclick="deleteThirdRow(this);" /></a></td>' +
   	        '<td class="funcTd"><i class="iconfont icon-shanchu" onclick="deleteThirdRow(this);"></i></td>' +
   	        '</tr>'
   	    );
	}
	//第三人代理人
	list = data.thirdPartyAgent;
	object = $("#thirdAgent-table").children("tbody");
	for(var i=0;i<list.length;i++){
		$("#thirdAgent-table").append('<tr class="edit-table-tr">' +
	    '<td class="indexTd" data-value="'+list[i].id+'"></td>' +
	    '<td class=""><select class="agent">' + createSelectHtml(agentJson,list[i].agentTypeCode) + '</select></td>' +
	    '<td class=""><input type="text" value="'+list[i].agentName+'"/></td>' +
	    '<td class=""><select class="certificateType">' + createSelectHtml(certificateTypeJson,list[i].cardTypeCode) + '</select></td>' +
	    '<td class=""><input type="text" value="'+list[i].idCard+'"/></td>' +
	    '<td class=""><input type="text" value="'+list[i].phone+'"/></td>' +
	    '<td class=""><select class="">'+
	          '<option value="1" '+ (list[i].isAuthorization==1?'selected ="selected"':'') +'>是</option>' +
		      '<option value="0" '+ (list[i].isAuthorization==0?'selected ="selected"':'') +'>否</option></select></td>' +
		  '<td class="">' +
		      '<select class="respondentchoose" onclick="choosethird(this);"><option>'+list[i].agentParent+'</option></select></td>' +
	    //'<td class=""><a><input id="deleteUL" class="deleteBtn" type="button" value="删除" title="删除" onclick="deleteThirdAgentRow(this);" /></a></td>' +
		'<td class="funcTd"><i class="iconfont icon-shanchu" onclick="deleteThirdAgentRow(this);"></i></td>' +
		'</tr>');
	}
}

//复议事项设置选中
function mattersData(data){
	var isReviewVal = data.isReview == 1 ?'isReviewA':'isReviewB';
	$("#"+isReviewVal).prop("checked","checked");
	var isNonfeasanceVal = data.isNonfeasance == 1 ?'isNonfeasanceA':'isNonfeasanceB';
	$("#"+isNonfeasanceVal).prop("checked",'checked');
	var isHoldHearingVal = data.isHoldHearing == 1 ?'isHoldHearingA':'isHoldHearingB';
	$("#"+isHoldHearingVal).prop("checked",'checked');
	var isCompensationVal = data.isCompensation == 1 ?'isCompensationA':'isCompensationB';
	$("#"+isCompensationVal).prop("checked",'checked');
	var isDocumentReviewVal = data.isDocumentReview == 1 ?'isDocumentReviewA':'isDocumentReviewB';
	$("#"+isDocumentReviewVal).prop("checked",'checked');
	
	docCheck(data.isDocumentReview);
	payCheck(data.isCompensation);
	
	//行政不作为
	nonfeasanceCheck(data.isNonfeasance);
	
}

//申请人，其他申请人，申请人代理人，被申请人,被申请人代理人，第三人，第三人代理人
function updateIndex(){
	updateApplicantIndex();
	updateOtherIndex();
	updateApplicantAgentIndex();
	updateRespondentIndex()
	updateRespondentAgentIndex();
	updateThirdIndex();
	updateThirdAgentIndex();
}
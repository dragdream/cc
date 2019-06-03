
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
  		
        $("input").attr("disabled", "disabled");
		$("textarea").attr("disabled", "disabled");
		$("select").attr("disabled", "disabled");
        
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


//当事人设置选中
function clientInfoData(data){
	//申请人代理人
	var isAgentVal = data.isAgent == 1 ?'是':'否';
	$("#isAgent").val(isAgentVal)
	agent(data.isAgent);
	//被申请人代理人
	var isRespondentVal = data.isRespondent == 1 ?'是':'否';
	$("#isRespondent").val(isRespondentVal);
	respondentA(data.isRespondent);
	// 第三人
	var isThirdVal = data.isThirdParty == 1 ?'是':'否';
	$("#isThird").val(isThirdVal);
	third(data.isThirdParty);
	// 第三人代理人
	var isThirdAgentVal = data.isThirdPartyAgent == 1 ?'是':'否';
	$("#isThirdAgent").val(isThirdAgentVal);
	thirdAgent(data.isThirdPartyAgent);
	
	
	//当事人数据填充
	clientInfoPeople(data);
}
//当事人数据填充
function clientInfoPeople(data){
	
	$("#totalApplicant").val(data.applicant.length+data.otherApplicant.length);
	//申请人
	var list = data.applicant;
	var object = $("#applicant-table").children("tbody");
	for(var i=0;i<list.length;i++){
    	$("#applicant-table").append('<tr class="edit-table-tr">' +
   	        '<td class="indexTd"></td>' +
   	        '<td class="">'+list[i].name+'</td>' +
   	        '<td class="">' + list[i].certificateType + '</td>' +
   	        '<td class="">'+list[i].certificateId+'</td>' +
   	        '<td class="">'+ list[i].sex +'</td>' +
   	        '<td class="">'+ list[i].nation +'</td>' +
   	        '<td class="">'+list[i].postAddress+'</td>' +
   	        '<td class="">'+list[i].postCode+'</td>' +
   	        '<td class="">'+list[i].phoneNum+'</td>' +
   	        '</tr>'
   	    );
	}
	//其他申请人
	list = data.otherApplicant;
	object = $("#otherApplicant-table").children("tbody");
	for(var i=0;i<list.length;i++){
		$("#otherApplicant-table").append('<tr class="edit-table-tr">' +
        '<td class="indexTd"></td>' +
        '<td class="">'+list[i].name+'</td>' +
        '<td class="">'+list[i].sex+'</td>' +
        '<td class="">'+list[i].certificateType+'</td>' +
        '<td class="">'+list[i].certificateId+'</td>' +
        '</tr>');
	}	
	//申请人代理人
	list = data.applicantAgent;
	object = $("#applicantAgent-table").children("tbody");
	for(var i=0;i<list.length;i++){
		$("#applicantAgent-table").append('<tr class="edit-table-tr">' +
	    '<td class="indexTd"></td>' +
	    '<td class="">'+list[i].agentType+'</td>' +
	    '<td class="">'+list[i].agentName+'</td>' +
	    '<td class="">'+list[i].cardType+'</td>' +
	    '<td class="">'+list[i].idCard+'</td>' +
	    '<td class="">'+list[i].phone+'</td>' +
	    '<td class="">'+ (list[i].isAuthorization==1?"是":"否")+'</td>' +
		'<td class="">'+list[i].agentParent+'</td>' +
	    '</tr>');
	}
    
	//被申请人
	list = data.respondent;
	object = $("#respondent-table").children("tbody");
	for(var i=0;i<list.length;i++){
	    $("#respondent-table").append('<tr class="edit-table-tr">' +
	    '<td class="indexTd"></td>' +
	    '<td class="">'+list[i].respondentType+'</td>' +
	    '<td class="">'+list[i].respondentName+'</td>' +
	    '</tr>');
	}
	//被申请人代理人
	list = data.respondentAgent;
	object = $("#respondentAgent-table").children("tbody");
	for(var i=0;i<list.length;i++){
		$("#respondentAgent-table").append('<tr class="edit-table-tr">' +
	    '<td class="indexTd"></td>' +
	    '<td class="">'+list[i].agentType+'</td>' +
	    '<td class="">'+list[i].agentName+'</td>' +
	    '<td class="">'+list[i].cardType+'</td>' +
	    '<td class="">'+list[i].idCard+'</td>' +
	    '<td class="">'+list[i].phone+'</td>' +
	    '<td class="">'+(list[i].isAuthorization==1?"是":"否")+'</td>' +
		'<td class="">'+list[i].agentParent+'</td>' +
	    '</tr>');
	}
	
	//第三人
	list = data.thirdParty;
	object = $("#third-table").children("tbody");
	for(var i=0;i<list.length;i++){
    	$("#third-table").append('<tr class="edit-table-tr">' +
   	        '<td class="indexTd"></td>' +
   	        '<td class="">'+list[i].thirdPartyName+'</td>' +
   	        '<td class="">'+list[i].certificateType+'</td>' +
   	        '<td class="">'+list[i].certificateId+'</td>' +
   	        '<td class="">'+list[i].sex+'</td>' +
   	        '<td class="">'+list[i].nation+'</td>' +
   	        '<td class="">'+list[i].postAddress+'</td>' +
   	        '<td class="">'+list[i].postcode+'</td>' +
   	        '<td class="">'+list[i].phoneNum+'</td>' +
   	        '</tr>'
   	    );
	}
	//第三人代理人
	list = data.thirdPartyAgent;
	object = $("#thirdAgent-table").children("tbody");
	for(var i=0;i<list.length;i++){
		$("#thirdAgent-table").append('<tr class="edit-table-tr">' +
	    '<td class="indexTd"></td>' +
	    '<td class="">'+list[i].agentType+'</td>' +
	    '<td class="">'+list[i].agentName+'</td>' +
	    '<td class="">'+list[i].cardType+'</td>' +
	    '<td class="">'+list[i].idCard+'</td>' +
	    '<td class="">'+list[i].phone+'</td>' +
	    '<td class="">'+ (list[i].isAuthorization==1?"是":"否") +'</td>' +
		'<td class="">'+list[i].agentParent+'</td>' +
	    '</tr>');
	}
}

//复议事项设置选中
function mattersData(data){
	var isReviewVal = data.isReview == 1 ?'是':'否';
	$("#isReview").val(isReviewVal);
	var isNonfeasanceVal = data.isNonfeasance == 1 ?'是':'否';
	$("#isNonfeasance").val(isNonfeasanceVal);
	var isHoldHearingVal = data.isHoldHearing == 1 ?'是':'否';
	$("#isHoldHearing").val(isHoldHearingVal);
	var isCompensationVal = data.isCompensation == 1 ?'是':'否';
	$("#isCompensation").val(isCompensationVal);
	var isDocumentReviewVal = data.isDocumentReview == 1 ?'是':'否';
	$("#isDocumentReview").val(isDocumentReviewVal);
	
	if(data.isDocumentReview == 0){
		$("#documentReviewName").val("");
	}
	if(data.isCompensation == 0){
		$("#compensationTypeCode").val("");
		$("#compensationAmount").val("");
	}
	else{
		$("#compensationTypeCode").val(data.compensationType);
	}
	
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


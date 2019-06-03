// 申请人信息登记/修改
var APPLICANT_URL = "/xzfy/caseManager/addOrUpdateFyApplicant.action";

//根据caseId查询申请人信息
var GET_APPLICANT_URL = "/xzfy/caseManager/getFyApplicant.action";

//根据caseId查询被申请人信息
var GET_RESPONDENT_URL = "/xzfy/caseManager/getFyRespondent.action";

//根据caseId查询第三人信息
var GET_THIRDPARTY_URL = "/xzfy/caseManager/getFyThirdParty.action";

//var caseId = $("#caseId").val();
var caseId = '10be0f867b10407e90009295889ac860';

// 加载初始化数据
function clientInit() {
	searchApplicant();
	searchRespondent();
}

// 根据caseId查询申请人信息
function searchApplicant() {
	$.ajax({
		type : 'GET',
		dataType : "json",
		url : GET_APPLICANT_URL,
		data : {
			caseId : caseId
		},
		success : function(res) {
			var json = res.rtData;
			var applicantData = json.fyApplicantList;
			console.log(applicantData);
			applicant_listdata = {
				isShowColumn : applicantData
			}
			applicant_agent_listdata = {
				isShowColumn : applicantData
			}
			$("#applicant-table").append(juicer(applicant_tpl, applicant_listdata));
			$("#other_applicant-table").append(juicer(other_applicant_tpl, other_applicant_listdata));
			$("#applicant-agent-table").append(juicer(applicant_agent_tpl, applicant_agent_listdata));
			bindJsonObj2Cntrl(json);
		},
		error : function() {
			$.MsgBox.Alert_auto("查询失败,请联系管理员！");
		}
	});
}

// 根据caseId查询被申请人信息
function searchRespondent() {
	$.ajax({
		type : 'GET',
		dataType : "json",
		url : GET_RESPONDENT_URL,
		data : {
			caseId : caseId
		},
		success : function(res) {
			var json = res.rtData;
			var applicantData = json.fyRespondentList;
			console.log(applicantData);
			respondent_listdata = {
				isShowColumn : applicantData
			}
			$("#respondent-table").append(juicer(respondent_tpl, respondent_listdata));
			bindJsonObj2Cntrl(json);
		},
		error : function() {
			$.MsgBox.Alert_auto("查询失败,请联系管理员！");
		}
	});

}


//根据caseId查询第三人信息
function searchThirdParty() {
	$.ajax({
		type : 'GET',
		dataType : "json",
		url : GET_THIRDPARTY_URL,
		data : {
			caseId : caseId
		},
		success : function(res) {
			var json = res.rtData;
			var applicantData = json.fyThirdPartyList;
			console.log(applicantData);
			thirdParty_listdata = {
				isShowColumn : applicantData
			}
			$("#thirdParty-table").append(juicer(thirdParty_tpl, thirdParty_listdata));
			bindJsonObj2Cntrl(json);
		},
		error : function() {
			$.MsgBox.Alert_auto("查询失败,请联系管理员！");
		}
	});
}




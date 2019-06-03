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

// 加载初始化数据
function clientInit() {

	// 当事人-申请人类型
	var url = DICT_URL;
	var param = {
		type : "APPLICANT_TYPE_CODE"
	};
	var json = tools.requestJsonRs(url, param);

	// 当事人- 证件类型
	param = {
		type : "CERTIFICATE_TYPE_CODE"
	};
	certificateTypeJson = tools.requestJsonRs(url, param);

	// 当事人- 性别
	param = {
		type : "SEX_CODE"
	};
	sexJson = tools.requestJsonRs(url, param);

	// 当事人- 民族
	param = {
		type : "NATION_CODE"
	};
	nationJson = tools.requestJsonRs(url, param);

	// 当事人-代理人类型
	param = {
		type : "AGENT_TYPE_CODE"
	};
	agentJson = tools.requestJsonRs(url, param);

	// 当事人-被申请人类型
	param = {
		type : "RESPONDENT_TYPE_CODE"
	};
	respondentTypeJson = tools.requestJsonRs(url, param);
	searchClientInfo();
	updateIndex();
}

// 根据caseId查询当事人信息
function searchClientInfo() {
	var caseId = $('#caseId').val(); // 案件id
	$.ajax({
		type : 'GET',
		dataType : "json",
		url : GET_CLIENTINFO_URL,
		data : {
			caseId : caseId
		},
		success : function(res) {
			$("#caseId").val(caseId);
			var json = res.rtData;
			var applicant = json.applicant;
			var otherApplicant = json.otherApplicant;
			var applicantAgent = json.applicantAgent;
			var respondent = json.respondent;
			var respondentAgent = json.respondentAgent;
			var thirdParty = json.thirdParty;
			var thirdPartyAgent = json.thirdPartyAgent;
			applicant_listdata = {
				isShowColumn : applicant
			};
			other_applicant_listdata = {
				isShowColumn : otherApplicant
			};
			applicant_agent_listdata = {
				isShowColumn : applicantAgent
			};
			respondent_listdata = {
				isShowColumn : respondent
			};
			respondent_agent_listdata = {
				isShowColumn : respondentAgent
			};
			thirdParty_listdata = {
				isShowColumn : thirdParty
			};
			thirdParty_agent_listdata = {
				isShowColumn : thirdPartyAgent
			};
			$("#applicant-table").empty();
			$("#applicant-table").append(
					juicer(applicant_tpl, applicant_listdata));
			
			for (var i = 0; i < applicant_listdata.isShowColumn.length; i++) {
				$("#certificateTypeCode")
						.html(
								createSelectHtml(
										certificateTypeJson,
										applicant_listdata.isShowColumn[i].certificateTypeCode));
				$("#sexCode")
						.html(
								createSelectHtml(sexJson,applicant_listdata.isShowColumn[i].sexCode));
			}
			$("#applicant-otherApplicant-table").empty();
			$("#applicant-otherApplicant-table").append(
					juicer(other_applicant_tpl,
							other_applicant_listdata));
			for (var i = 0; i < other_applicant_listdata.isShowColumn.length; i++) {
				$("#other_certificateTypeCode")
						.html(
								createSelectHtml(
										certificateTypeJson,
										other_applicant_listdata.isShowColumn[i].certificateTypeCode));
				$("#other_sexCode")
						.html(
								createSelectHtml(
										sexJson,
										other_applicant_listdata.isShowColumn[i].sexCode));
			}
			$("#applicant-agent-table").empty();
			$("#applicant-agent-table").append(
					juicer(applicant_agent_tpl,
							applicant_agent_listdata));
//			for (var i = 0; i < applicant_agent_listdata.isShowColumn.length; i++) {
//				$("#applicant_agentTypeCode")
//						.html(createSelectHtml(agentJson,applicant_agent_listdata.isShowColumn[i].agentTypeCode));
//			}
			$("#respondent-table").empty();
			$("#respondent-table").append(
					juicer(respondent_tpl, respondent_listdata));
			$("#respondent-agent-table").empty();
			$("#respondent-agent-table").append(
					juicer(respondent_agent_tpl,
							respondent_agent_listdata));
			for (var i = 0; i < respondent_agent_listdata.isShowColumn.length; i++) {
				$("#respondent_agentTypeCode")
						.html(
								createSelectHtml(
										agentJson,
										respondent_agent_listdata.isShowColumn[i].agentTypeCode));
			}
			$("#thirdParty-table").empty();
			$("#thirdParty-table").append(
					juicer(thirdParty_tpl, thirdParty_listdata));
			for (var i = 0; i < thirdParty_listdata.isShowColumn.length; i++) {
				$("#thirdParty_certificateTypeCode")
						.html(
								createSelectHtml(
										certificateTypeJson,
										thirdParty_listdata.isShowColumn[i].certificateTypeCode));
				$("#thirdParty_sexCode")
						.html(
								createSelectHtml(
										sexJson,
										thirdParty_listdata.isShowColumn[i].sexCode));
			}
			$("#thirdParty-agent-table").empty();
			$("#thirdParty-agent-table").append(
					juicer(thirdParty_agent_tpl,
							thirdParty_agent_listdata));
			bindJsonObj2Cntrl(json);
			for (var i = 0; i < thirdParty_agent_listdata.isShowColumn.length; i++) {
				$("#thirdParty_agentTypeCode")
						.html(
								createSelectHtml(
										agentJson,
										thirdParty_agent_listdata.isShowColumn[i].agentTypeCode));
			}

		},
		error : function() {

		}
	});
}

// 当事人信息登记/或修改
function saveOrUpdataClientInfo() {
	// 申请人信息登记/修改
	saveOrUpdataApplicant();
	// 被申请人信息登记/修改
	saveOrUpdataRespondent();
	// 第三人信息登记/修改
	saveOrUpdataThirdParty();
}

// 申请人信息登记/修改
function saveOrUpdataApplicant() {
	var caseId = $('#caseId').val(); // 案件id
	var appliantData = {
		"operationType" : "01",// 操作类型 "01"为登记/"02"为填报
		"caseId" : caseId, // 案件ID
		"applicantList" : getApplicantValues(), // 申请人
		"otherApplicantList" : getOtherApplicantValues(), // 其他申请人
		"agentList" : getApplicantAgentValues(), // 代理人
	};
	$.ajax({
		type : "POST",
		dataType : "json",
		async : false,
		contentType : 'application/json;charset=utf-8',
		url : APPLICANT_URL,
		data : JSON.stringify(appliantData),
		success : function(res) {
			$("#caseId").val(res.rtData.caseId);
		},
		error : function() {
		}
	});
}

// 被申请人信息登记/修改
function saveOrUpdataRespondent() {
	var caseId = $('#caseId').val(); // 案件id
	var appliantData = {
		"operationType" : "01",// 操作类型 "01"为登记/"02"为填报
		"caseId" : caseId,
		"respondentList" : getRespondentValues(), // 被申请人
		"agentList" : getAgentValues("respondent"), // 代理人
	};
	$.ajax({
		type : "POST",
		dataType : "json",
		contentType : 'application/json;charset=utf-8',
		url : RESPONDENT_URL,
		data : JSON.stringify(appliantData),
		success : function(res) {
			$("#caseId").val(res.rtData.caseId);
		},
		error : function() {
		}
	});
}

// 第三人信息登记/修改
function saveOrUpdataThirdParty() {
	var caseId = $('#caseId').val(); // 案件id
	var appliantData = {
		"operationType" : "01",// 操作类型 "01"为登记/"02"为填报
		"caseId" : caseId,
		"thirdPartyList" : getThirdPartyValues(), // 被申请人
		"agentList" : getAgentValues("thirdParty"), // 代理人
	};
	$.ajax({
		type : "POST",
		dataType : "json",
		contentType : 'application/json;charset=utf-8',
		url : THIRDPARTY_URL,
		data : JSON.stringify(appliantData),
		success : function(res) {
			$("#caseId").val(res.rtData.caseId);
		},
		error : function() {
		}
	});
}

// 获取申请人信息数据
function getApplicantValues() {

	var arry = new Array()
	var length = $("#applicant-table")[0].rows.length - 1;
	var object = $("#applicant-table").children("tbody");
	for (var i = 0; i < length; i++) {
		var json = {};
		json["id"] = object.children("tr:eq(" + (i + 1) + ")").children(
				"td:eq(1)").find("input").val();
		json["caseId"] = $('#caseId').val();
		json["name"] = object.children("tr:eq(" + (i + 1) + ")").children(
				"td:eq(2)").find("input").val();
		json["certificateTypeCode"] = object.children("tr:eq(" + (i + 1) + ")")
				.children("td:eq(3)").find("select").val();
		json["certificateType"] = object.children("tr:eq(" + (i + 1) + ")")
				.children("td:eq(3)").find("option:selected").text();
		json["certificateId"] = object.children("tr:eq(" + (i + 1) + ")")
				.children("td:eq(4)").find("input").val();
		json["sexCode"] = object.children("tr:eq(" + (i + 1) + ")").children(
				"td:eq(5)").find("select").val();
		json["sex"] = object.children("tr:eq(" + (i + 1) + ")").children(
				"td:eq(5)").find("option:selected").text();
		json["postAddress"] = object.children("tr:eq(" + (i + 1) + ")")
				.children("td:eq(6)").find("input").val();
		json["postCode"] = object.children("tr:eq(" + (i + 1) + ")").children(
				"td:eq(7)").find("input").val();
		json["phoneNum"] = object.children("tr:eq(" + (i + 1) + ")").children(
				"td:eq(8)").find("input").val();
		arry.push(json);
	}
	return arry;
}

// 获取其他申请人信息数据
function getOtherApplicantValues() {
	var arry = new Array()
	var length = $("#applicant-otherApplicant-table")[0].rows.length - 1;
	var object = $("#applicant-otherApplicant-table").children("tbody");
	for (var i = 0; i < length; i++) {
		var json = {};
		json["caseId"] = $('#caseId').val();
		json["id"] = object.children("tr:eq(" + (i + 1) + ")").children(
				"td:eq(1)").find("input").val();
		json["name"] = object.children("tr:eq(" + (i + 1) + ")").children(
				"td:eq(2)").find("input").val();
		json["sexCode"] = object.children("tr:eq(" + (i + 1) + ")").children(
				"td:eq(3)").find("select").val();
		json["sex"] = object.children("tr:eq(" + (i + 1) + ")")
		.children("td:eq(3)").find("option:selected").text();
		json["certificateTypeCode"] = object.children("tr:eq(" + (i + 1) + ")")
				.children("td:eq(4)").find("select").val();
		json["certificateType"] = object.children("tr:eq(" + (i + 1) + ")")
		.children("td:eq(4)").find("option:selected").text();
		json["certificateId"] = object.children("tr:eq(" + (i + 1) + ")")
				.children("td:eq(5)").find("input").val();
		arry.push(json);
	}
	return arry;
}

// 被申请人信息数据
function getRespondentValues() {
	var arry = new Array()
	var length = $("#respondent-table")[0].rows.length - 1;
	var object = $("#respondent-table").children("tbody");
	for (var i = 0; i < length; i++) {
		var json = {};
		json["caseId"] = $('#caseId').val();
		json["id"] = object.children("tr:eq(" + (i + 1) + ")").children(
				"td:eq(1)").find("input").val();
		json["respondentType"] = object.children("tr:eq(" + (i + 1) + ")")
				.children("td:eq(2)").find("input").val();
		json["respondentName"] = object.children("tr:eq(" + (i + 1) + ")")
				.children("td:eq(3)").find("input").val();
		arry.push(json);
	}
	return arry;
}

// 第三人信息数据
function getThirdPartyValues() {
	var arry = new Array()
	var length = $("#thirdParty-table")[0].rows.length - 1;
	var object = $("#thirdParty-table").children("tbody");
	for (var i = 0; i < length; i++) {
		var json = {};
		json["caseId"] = $('#caseId').val();
		json["id"] = object.children("tr:eq(" + (i + 1) + ")").children(
				"td:eq(1)").find("input").val();
		json["thirdPartyName"] = object.children("tr:eq(" + (i + 1) + ")")
				.children("td:eq(2)").find("input").val();
		json["certificateTypeCode"] = object.children("tr:eq(" + (i + 1) + ")")
				.children("td:eq(3)").find("select").val();
		json["certificateType"] = object.children("tr:eq(" + (i + 1) + ")")
				.children("td:eq(3)").find("option:selected").text();
		json["certificateId"] = object.children("tr:eq(" + (i + 1) + ")")
				.children("td:eq(4)").find("input").val();
		json["sexCode"] = object.children("tr:eq(" + (i + 1) + ")").children(
				"td:eq(5)").find("select").val();
		json["sex"] = object.children("tr:eq(" + (i + 1) + ")").children(
				"td:eq(5)").find("option:selected").text();
		json["nationCode"] = object.children("tr:eq(" + (i + 1) + ")")
				.children("td:eq(6)").find("input").val();
		json["nation"] = object.children("tr:eq(" + (i + 1) + ")").children(
				"td:eq(6)").find("option:selected").text();
		json["postAddress"] = object.children("tr:eq(" + (i + 1) + ")")
				.children("td:eq(7)").find("input").val();
		json["postcode"] = object.children("tr:eq(" + (i + 1) + ")").children(
				"td:eq(8)").find("input").val();
		arry.push(json);
	}
	return arry;
}

// 获取代理人信息数据
function getAgentValues(agentType) {
	var arry = new Array()
	if (agentType == "applicant") {
		var length = $("#applicant-agent-table")[0].rows.length - 1;
		var object = $("#applicant-agent-table").children("tbody");
	} else if (agentType == "respondent") {
		var length = $("#respondent-agent-table")[0].rows.length - 1;
		var object = $("#respondent-agent-table").children("tbody");
	} else if (agentType == "thirdParty") {
		var length = $("#thirdParty-agent-table")[0].rows.length - 1;
		var object = $("#thirdParty-agent-table").children("tbody");
	}
	for (var i = 0; i < length; i++) {
		var json = {};
		json["caseId"] = $('#caseId').val();
		json["type"] = "1";
		json["id"] = object.children("tr:eq(" + (i + 1) + ")").children(
				"td:eq(1)").find("input").val();
		json["agentName"] = object.children("tr:eq(" + (i + 1) + ")").children(
				"td:eq(2)").find("input").val();
		json["agentTypeCode"] = object.children("tr:eq(" + (i + 1) + ")")
				.children("td:eq(3)").find("select").val();
		json["agentType"] = object.children("tr:eq(" + (i + 1) + ")").children(
				"td:eq(3)").find("option:selected").text();
		json["phone"] = object.children("tr:eq(" + (i + 1) + ")").children(
				"td:eq(4)").find("input").val();
		json["isAuthorization"] = object.children("tr:eq(" + (i + 1) + ")")
				.children("td:eq(5)").find("input").val();
		json["agentParent"] = object.children("tr:eq(" + (i + 1) + ")")
				.children("td:eq(6)").find("input").val();
		arry.push(json);
	}
	return arry;
}

// 获取申请人代理人信息数据
function getApplicantAgentValues() {
	var arry = new Array()
	var length = $("#applicant-agent-table")[0].rows.length - 1;
	var object = $("#applicant-agent-table").children("tbody");
	for (var i = 0; i < length; i++) {
		var json = {};
		json["caseId"] = $('#caseId').val();
		json["type"] = "1";
		json["id"] = object.children("tr:eq(" + (i + 1) + ")").children(
				"td:eq(1)").find("input").val();
		json["agentName"] = object.children("tr:eq(" + (i + 1) + ")").children(
				"td:eq(2)").find("input").val();
		json["agentTypeCode"] = object.children("tr:eq(" + (i + 1) + ")")
				.children("td:eq(3)").find("select").val();
		json["agentType"] = object.children("tr:eq(" + (i + 1) + ")").children(
				"td:eq(3)").find("option:selected").text();
		json["phone"] = object.children("tr:eq(" + (i + 1) + ")").children(
				"td:eq(4)").find("input").val();
		json["isAuthorization"] = object.children("tr:eq(" + (i + 1) + ")")
				.children("td:eq(5)").find("input").val();
		json["agentParent"] = object.children("tr:eq(" + (i + 1) + ")")
				.children("td:eq(6)").find("input").val();
		arry.push(json);
	}
	return arry;
}
// 获取被申请人代理人信息数据
function getRespondentAgentValues() {
	var arry = new Array()
	var length = $("#respondent-agent-table")[0].rows.length - 1;
	var object = $("#respondent-agent-table").children("tbody");
	for (var i = 0; i < length; i++) {
		var json = {};
		json["caseId"] = $('#caseId').val();
		json["type"] = "1";
		json["id"] = object.children("tr:eq(" + (i + 1) + ")").children(
				"td:eq(1)").find("input").val();
		json["agentName"] = object.children("tr:eq(" + (i + 1) + ")").children(
				"td:eq(2)").find("input").val();
		json["agentTypeCode"] = object.children("tr:eq(" + (i + 1) + ")")
				.children("td:eq(3)").find("select").val();
		json["agentType"] = object.children("tr:eq(" + (i + 1) + ")").children(
				"td:eq(3)").find("option:selected").text();
		json["phone"] = object.children("tr:eq(" + (i + 1) + ")").children(
				"td:eq(4)").find("input").val();
		json["isAuthorization"] = object.children("tr:eq(" + (i + 1) + ")")
				.children("td:eq(5)").find("input").val();
		json["agentParent"] = object.children("tr:eq(" + (i + 1) + ")")
				.children("td:eq(6)").find("input").val();
		arry.push(json);
	}
	return arry;
}

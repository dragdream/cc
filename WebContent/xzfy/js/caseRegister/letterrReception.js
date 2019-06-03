//处理结果类型
var dealResultCodeJson = "";
// 复议请求类型
var recertionTypeCodeJson = "";
/* 证件类型 */
var cardTypeCodeJson = "";
// 性别
var sexCodeJson = "";
// 复议机构名称
var reconsiderOrganCodeJson = "";
/* 来件类型代码 */
var letterTypeCodeJson = "";

var fileTypeCodeJson = "";

var url = DICT_URL;

// 加载初始化数据
function receptionInit() {

	var caseId = $('#caseId').val(); // 案件id
	applyType = $('#applyType option:selected').val();// 选中的值
	if (applyType == 1) {
		if ("" != caseId && null != caseId) {
			searchLetter();
		} else {
			param = {
				type : "LETTER_TYPE_CODE"
			};
			letterTypeCodeJson = tools.requestJsonRs(url, param);
			$("#letterTypeCode").html(
					createSelectHtml(letterTypeCodeJson, null));
		}
	} else {
		if ("" != caseId && null != caseId) {
			searchReception();
		} else {
			param = {
				type : "DEAL_RESULT_CODE"
			};
			dealResultCodeJson = tools.requestJsonRs(url, param);
			$("#dealResultCode").html(
					createSelectHtml(dealResultCodeJson, null));
			param = {
				type : "RECEPTION_TYPE_CODE"
			};
			recertionTypeCodeJson = tools.requestJsonRs(url, param);
			$("#recertionTypeCode").html(
					createSelectHtml(recertionTypeCodeJson, null));
			param = {
				type : "CERTIFICATE_TYPE_CODE"
			};
			cardTypeCodeJson = tools.requestJsonRs(url, param);
			$("#cardTypeCode").html(createSelectHtml(cardTypeCodeJson, null));

			param = {
				type : "SEX_CODE"
			};
			sexCodeJson = tools.requestJsonRs(url, param);
			$("#sexCode").html(createSelectHtml(sexCodeJson, null));
			param = {
				type : "RECONSIDER_ORGAN_CODE"
			};
			reconsiderOrganCodeJson = tools.requestJsonRs(url, param);
			$("#reconsiderOrganCode").html(
					createSelectHtml(reconsiderOrganCodeJson, null));
			param = {
				type : "FILE_TYPE_CODE"
			};
			fileTypeCodeJson = tools.requestJsonRs(url, param);
			$("#fileTypeCode").html(createSelectHtml(fileTypeCodeJson, null));

		}
	}
}

// 查询来件信息
function searchLetter() {
	var caseId = $('#caseId').val(); // 案件id
	$.ajax({
		type : 'GET',
		dataType : "json",
		url : GET_LETTER_URL,
		data : {
			caseId : caseId
		},
		success : function(res) {
			var fyLetterVo = res.rtData.fyLetterVo;
			bindJsonObj2Cntrl(fyLetterVo);
			param = {
				type : "LETTER_TYPE_CODE"
			};
			letterTypeCodeJson = tools.requestJsonRs(url, param);
			$("#letterTypeCode").html(
					createSelectHtml(letterTypeCodeJson,
							fyLetterVo.letterTypeCode));
		},
		error : function() {
		}
	});

}

// 查询接待信息
function searchReception() {
	var caseId = $('#caseId').val(); // 案件id
	$
			.ajax({
				type : 'GET',
				dataType : "json",
				url : GET_RECEPTION_URL,
				data : {
					caseId : caseId
				},
				success : function(res) {
					var fyReceptionVo = res.rtData.fyReceptionVo;
					var reconsiderMaterial = JSON
							.parse(fyReceptionVo.reconsiderMaterial);
					var visitor = JSON.parse(fyReceptionVo.visitor);
					recp_listdata = {
						isShowColumn : visitor
					}
					material_listdata = {
						isShowColumn : reconsiderMaterial
					}
					$("#recp-table").empty();
					$("#recp-table").append(juicer(recp_tpl, recp_listdata));
					$("#material-table").empty();
					$("#material-table").append(
							juicer(material_tpl1, material_listdata));
					bindJsonObj2Cntrl(fyReceptionVo);

					param = {
						type : "DEAL_RESULT_CODE"
					};
					dealResultCodeJson = tools.requestJsonRs(url, param);
					$("#dealResultCode").html(
							createSelectHtml(dealResultCodeJson,
									fyReceptionVo.dealResultCode));
					param = {
						type : "RECEPTION_TYPE_CODE"
					};
					recertionTypeCodeJson = tools.requestJsonRs(url, param);
					$("#recertionTypeCode").html(
							createSelectHtml(recertionTypeCodeJson,
									fyReceptionVo.recertionTypeCode));
					param = {
						type : "CERTIFICATE_TYPE_CODE"
					};
					cardTypeCodeJson = tools.requestJsonRs(url, param);
					for (var i = 0; i < visitor.length; i++) {
						$("#cardTypeCode").html(
								createSelectHtml(cardTypeCodeJson,
										visitor[i].cardTypeCode));
					}
					param = {
						type : "SEX_CODE"
					};
					sexCodeJson = tools.requestJsonRs(url, param);
					for (var i = 0; i < visitor.length; i++) {
						$("#sexCode").html(
								createSelectHtml(sexCodeJson,
										visitor[i].sexCode));
					}
					param = {
						type : "RECONSIDER_ORGAN_CODE"
					};
					reconsiderOrganCodeJson = tools.requestJsonRs(url, param);
					$("#reconsiderOrganCode").html(
							createSelectHtml(reconsiderOrganCodeJson,
									fyReceptionVo.reconsiderOrganCode));
					param = {
						type : "FILE_TYPE_CODE"
					};
					fileTypeCodeJson = tools.requestJsonRs(url, param);
					for (var i = 0; i < reconsiderMaterial.length; i++) {
						$("#fileTypeCode")
								.html(
										createSelectHtml(
												fileTypeCodeJson,
												reconsiderMaterial[i].fileTypeCode));
					}
				},
				error : function() {
				}
			});

}

// 来件信息修改/保存
function saveLetter() {
	var caseId = $('#caseId').val(); // 案件id
	var operationType = "01"; // 操作类型
	// "01"为登记/"02"为填报
	var applicationTypeCode = $('#applicationTypeCode').val(); // 复议申请方式代码
	var applicationType = $('#applicationType').val(); // 复议申请方式
	var letterTypeCode = $('#letterTypeCode').val(); // 收件类型代码
	var letterType = $('#letterTypeCode').find("option:selected").text(); // 收件类型
	var receiveDate = $('#receiveDate').val(); // 接收日期
	var senderName = $('#senderName').val(); // 来信人姓名
	var senderPhone = $('#senderPhone').val(); // 来信人电话
	var senderPostCode = $('#senderPostCode').val(); // 来信人邮编
	var senderAddress = $('#senderAddress').val(); // 来信人通信地址
	var letterNum = $('#letterNum').val(); // 来信人通信地址

	var jsonData = {
		"caseId" : caseId,
		"operationType" : operationType,
		"applicationTypeCode" : applicationTypeCode,
		"applicationType" : applicationType,
		"letterTypeCode" : letterTypeCode,
		"letterType" : letterType,
		"receiveDate" : receiveDate,
		"senderName" : senderName,
		"senderPhone" : senderPhone,
		"senderPostCode" : senderPostCode,
		"senderAddress" : senderAddress,
		"letterNum" : letterNum
	};
	var letter = {
		"letter" : jsonData
	};
	$.ajax({
		type : "POST",
		dataType : "json",
		async : false,
		contentType : 'application/json;charset=utf-8',
		url : LETTER_URL,
		data : JSON.stringify(letter),
		success : function(res) {
			$("#caseId").val(res.rtData.caseId);
		},
		error : function() {
		}
	});
}

// 接待信息修改/保存
function saveReception() {
	var caseId = $('#caseId').val(); // 案件id
	var operationType = "01"; // 操作类型
	// "01"为登记/"02"为填报
	var applicationTypeCode = $('#applicationTypeCode').val(); // 复议申请方式代码
	var applicationType = $('#applicationType').val(); // 复议申请方式
	var receptionDate = $('#receptionDate').val(); // 接待日期
	var place = $('#place').val(); // 接待地点
	var dealMan1Id = $('#dealMan1Id').val(); // 第一接待人
	var dealMan2Id = $('#dealMan2Id').val(); // 第二接待人
	var recertionTypeCode = $('#recertionTypeCode').val(); // 复议请求/接待类型代码（01 问卷
	// 02 法律咨询 03
	// 反应问题 04
	// 补充材料 05 催办案件 06 提交申请 99 其他）
	var dealResultCode = $('#dealResultCode').val(); // 处理结果代码
	var dealResult = $('#dealResultCode').find("option:selected").text(); // 处理结果代码

	var receptionDetail = $('#receptionDetail').val(); // 接待情况描述

	var isReconsiderTogether = $('input[name="isReconsiderTogether"]:checked')
			.val(); // 其他复议机关、法院是否受理同一复议申请
	var isReceiveMaterial = $('input[name="isReceiveMaterial"]:checked').val(); // 是否接收材料

	var reconsiderOrganCode = $('#reconsiderOrganName').val(); // 复议机构名称
	var reconsiderOrganName = $('#reconsiderOrganName').find("option:selected")
			.text(); // 复议机构名称

	/* 被接待人基本信息 */
	var visitor = getVisitorValues();

	/* 案件资料信息 */
	var reconsiderMaterial = getReconsiderMaterialValues();

	var jsonData = {
		"caseId" : caseId,
		"operationType" : operationType,
		"applicationTypeCode" : applicationTypeCode,
		"applicationType" : applicationType,
		"receptionDate" : receptionDate,
		"place" : place,
		"dealMan1Id" : dealMan1Id,
		"dealMan2Id" : dealMan2Id,
		"recertionTypeCode" : recertionTypeCode,
		"dealResultCode" : dealResultCode,
		"dealResult" : dealResult,
		"visitorVo" : visitor,
		"receptionDetail" : receptionDetail,
		"isReceiveMaterial" : isReceiveMaterial,
		"isReconsiderTogether" : isReconsiderTogether,
		"reconsiderOrganName" : reconsiderOrganName,
		"reconsiderMaterialVo" : reconsiderMaterial
	};
	var reception = {
		"reception" : jsonData
	};
	$.ajax({
		type : 'POST',
		dataType : "json",
		async : false,
		contentType : 'application/json;charset=utf-8',
		url : RECEPTION_URL,
		data : JSON.stringify(reception),
		success : function(res) {
			$("#caseId").val(res.rtData.caseId);
		},
		error : function() {
		}
	});
}

// 案件提取
function getCaseExtract() {
	$.ajax({
		type : 'GET',
		dataType : "json",
		url : GET_CASE_EXTRACT_URL,
		data : {
			caseId : caseId
		},
		success : function(res) {
			var data = res.rtData;
			bindJsonObj2Cntrl(data);
		},
		error : function() {
		}
	});
}

// 被申请人信息数据
function getVisitorValues() {
	var arry = new Array();
	var length = $("#recp-table")[0].rows.length - 1;
	var object = $("#recp-table").children("tbody");
	for (var i = 0; i < length; i++) {
		var json = {};
		json["visitorName"] = object.children("tr:eq(" + (i + 1) + ")")
				.children("td:eq(1)").find("input").val();
		json["cardTypeCode"] = object.children("tr:eq(" + (i + 1) + ")")
				.children("td:eq(2)").find("select").val();
		json["cardType"] = object.children("tr:eq(" + (i + 1) + ")").children(
				"td:eq(2)").find("option:selected").text();
		json["cardNum"] = object.children("tr:eq(" + (i + 1) + ")").children(
				"td:eq(3)").find("input").val();
		json["sexCode"] = object.children("tr:eq(" + (i + 1) + ")").children(
				"td:eq(4)").find("select").val();
		json["sex"] = object.children("tr:eq(" + (i + 1) + ")").children(
				"td:eq(4)").find("option:selected").text();
		json["postAddress"] = object.children("tr:eq(" + (i + 1) + ")")
				.children("td:eq(5)").find("input").val();
		json["postCode"] = object.children("tr:eq(" + (i + 1) + ")").children(
				"td:eq(6)").find("input").val();
		json["phoneNum"] = object.children("tr:eq(" + (i + 1) + ")").children(
				"td:eq(7)").find("input").val();
		arry.push(json);
	}
	return arry;
}

// 被申请人信息数据
function getReconsiderMaterialValues() {
	var arry = new Array();
	var length = $("#material-table")[0].rows.length - 1;
	var object = $("#material-table").children("tbody");
	for (var i = 0; i < length; i++) {
		var json = {};
		json["fileName"] = object.children("tr:eq(" + (i + 1) + ")").children(
				"td:eq(1)").find("input").val();
		json["fileTypeCode"] = object.children("tr:eq(" + (i + 1) + ")")
				.children("td:eq(2)").find("option:selected").val();
		json["fileType"] = object.children("tr:eq(" + (i + 1) + ")").children(
				"td:eq(2)").find("option:selected").text();
		json["copyNum"] = object.children("tr:eq(" + (i + 1) + ")").children(
				"td:eq(3)").find("input").val();
		json["pageNum"] = object.children("tr:eq(" + (i + 1) + ")").children(
				"td:eq(4)").find("input").val();
		arry.push(json);
	}
	return arry;
}

// 申请对规范性文件审查
function isReceivingMaterial(type) {
	if (type == 1) {
		$("#isReceiveMaterial").removeAttr("disabled");
	} else {
		$("#isReceiveMaterial").attr("disabled", "disabled");
	}
}

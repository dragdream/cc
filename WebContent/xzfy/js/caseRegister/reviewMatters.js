// 复议事项查询
var GET_REVIEW_MATTERS_URL = "/xzfy/caseManager/getReviewMatters.action";

// 复议事项登记/修改
var SAVE_REVIEW_MATTERS_URL = "/xzfy/caseManager/addReviewMatters.action";

var url = DICT_URL;

var categoryCodeJson ="";   // 行政类别管理
var applicationItemCodeJson ="";    // 申请事项
var nonfeasanceItemCodeJson ="";
var compensationTypeCodeJson ="";

function reviewMattersInit() {
	searchReviewMatters();
}

// 查询复议事项信息
function searchReviewMatters() {
	var caseId=$('#caseId').val(); // 案件id
	$.ajax({
		type : 'GET',
		dataType : "json",
		url : GET_REVIEW_MATTERS_URL,
		data : {
			caseId : caseId
		},
		success : function(res) {
			var fyCaseHandling = res.rtData.fyCaseHandling;
			bindJsonObj2Cntrl(fyCaseHandling);
			if ("" != fyCaseHandling && null != fyCaseHandling) {
				param = {
						type : "CATEGORY_CODE"
					};
				categoryCodeJson = tools.requestJsonRs(url, param);
				$("#categoryCode").html(
						createSelectHtml(categoryCodeJson,
								fyCaseHandling.categoryCode));
				param = {
						type : "APPLICATION_ITEM_CODE"
				};
				applicationItemCodeJson = tools.requestJsonRs(url, param);
				$("#applicationItemCode").html(
						createSelectHtml(applicationItemCodeJson,
								fyCaseHandling.applicationItemCode));
				param = {
						type : "NONFEASANCE_ITEM_CODE"
				};
				nonfeasanceItemCodeJson = tools.requestJsonRs(url, param);
				$("#nonfeasanceItemCode").html(
						createSelectHtml(nonfeasanceItemCodeJson,
								fyCaseHandling.nonfeasanceItemCode));
				param = {
						type : "COMPENSATION_TYPE_CODE"
				};
				compensationTypeCodeJson = tools.requestJsonRs(url, param);
				$("#compensationTypeCode").html(
						createSelectHtml(compensationTypeCodeJson,
								fyCaseHandling.compensationTypeCode));
			} else {
				param = {
						type : "CATEGORY_CODE"
					};
				categoryCodeJson = tools.requestJsonRs(url, param);
				$("#categoryCode").html(
						createSelectHtml(categoryCodeJson,
								null));
				param = {
						type : "APPLICATION_ITEM_CODE"
				};
				applicationItemCodeJson = tools.requestJsonRs(url, param);
				$("#applicationItemCode").html(
						createSelectHtml(applicationItemCodeJson,
								null));
				param = {
						type : "NONFEASANCE_ITEM_CODE"
				};
				nonfeasanceItemCodeJson = tools.requestJsonRs(url, param);
				$("#nonfeasanceItemCode").html(
						createSelectHtml(nonfeasanceItemCodeJson,
								null));
				param = {
						type : "COMPENSATION_TYPE_CODE"
				};
				compensationTypeCodeJson = tools.requestJsonRs(url, param);
				$("#compensationTypeCode").html(
						createSelectHtml(compensationTypeCodeJson,
								null));
			}
			
		},
		error : function() {
			alert("发生错误");
		}
	});
}

// 复议事项登记/修该
function saveOrUpdataReviewMatters() {

	var caseId=$('#caseId').val(); // 案件id
	var operationType = "01"; // 操作类型 "01"为登记/"02"为填报
	var caseNum = $('#caseNum').val(); // 案件编号
	var applicationDate = $('#applicationDate').val(); // 收到复议申请日期
	var categoryCode = $('#categoryCode').val(); // 行政管理类别代码
	var category = $('#categoryCode').find("option:selected").text(); // 行政管理类别
	var applicationItemCode = $('#applicationItemCode').val(); // 申请行政复议事项代码
	var applicationItem = $('#applicationItemCode').find("option:selected").text(); // 申请行政复议事项代码
	var isNonfeasance = $('input[name="isNonfeasance"]:checked').val(); // 是否行政不作为
	var specificAdministrativeNo = $('#specificAdministrativeNo').val(); // 具体行政行为文号
	var specificAdministrativeName = $('#specificAdministrativeName').val(); // 具体行政行为名称
	var specificAdministrativeDate = $('#specificAdministrativeDate').val(); // 具体行政行为作出时间
	var receivedPunishDate = $('#receivedPunishDate').val(); // 收到处罚决定书的日期
	var receivedSpecificWay = $('#receivedSpecificWay').val(); // 得知该具体行为途径
    var nonfeasanceItemCode = $('#nonfeasanceItemCode').val(); // 行政不作为事项编码
    var nonfeasanceItem = $('#nonfeasanceItemCode').find("option:selected").text(); // 行政不作为事项
    var nonfeasanceDate = $('#nonfeasanceDate').val(); // 履行职责日期
    var specificAdministrativeDetail = $('#specificAdministrativeDetail').val(); // 具体行政行为
    var requestForReconsideration = $('#requestForReconsideration').val(); // 复议请求
    var isReview = $('input[name="isReview"]:checked').val(); // 是否复议前置
    var isHoldHearing = $('input[name="isHoldHearing"]:checked').val(); // 是否申请听证会
    var isDocumentReview = $('input[name="isDocumentReview"]:checked').val(); // 是否申请规范性文件审查
    var documentReviewName = $('#documentReviewName').val(); // 规范性文件名称
    var isCompensation = $('input[name="isCompensation"]:checked').val(); // 是否申请赔偿
    var compensationTypeCode = $('#compensationTypeCode').val(); // 赔偿类型代码
    var compensationType = $('#compensationTypeCode').find("option:selected").text(); // 赔偿类型代码
    var compensationAmount = $('#compensationAmount').val(); // 事实和理由

	var caseHandling = {
		"operationType" : operationType,
		"caseId" : caseId,
		"applicationDate" : applicationDate,
		"categoryCode" : categoryCode,
		"category" : category,
		"applicationItemCode" : applicationItemCode,
		"applicationItem" : applicationItem,
		"isNonfeasance" : isNonfeasance,
		"specificAdministrativeName" : specificAdministrativeName,
		"specificAdministrativeNo" : specificAdministrativeNo,
		"specificAdministrativeDate" : specificAdministrativeDate,
		"receivedPunishDate" : receivedPunishDate,
		"receivedSpecificWay" : receivedSpecificWay,
		"nonfeasanceItemCode" : nonfeasanceItemCode,
		"nonfeasanceItem" : nonfeasanceItem,
		"nonfeasanceDate" : nonfeasanceDate,
		"specificAdministrativeDetail" : specificAdministrativeDetail,
		"requestForReconsideration" : requestForReconsideration,
		"isReview" : isReview,
		"isHoldHearing" : isHoldHearing,
		"isDocumentReview" : isDocumentReview,
		"compensationTypeCode" : compensationTypeCode,
		"compensationType" : compensationType,
		"compensationAmount" : compensationAmount,
		"documentReviewName" : documentReviewName
	};
	var jsonData = {
		"caseHandling" : caseHandling
	};
	$.ajax({
		type : "POST",
		dataType : "json",
		contentType : 'application/json;charset=utf-8',
		url : SAVE_REVIEW_MATTERS_URL,
		data : JSON.stringify(jsonData),
		success : function(res) {
			$("#caseId").val(res.rtData.caseId);
		},
		error : function() {
		}
	});
}